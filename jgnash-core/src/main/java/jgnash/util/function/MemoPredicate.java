/*
 * jGnash, a personal finance application
 * Copyright (C) 2001-2018 Craig Cavanaugh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jgnash.util.function;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jgnash.engine.Transaction;
import jgnash.util.SearchUtils;

/**
 * Filter for Transaction memos.
 *
 * @author Craig Cavanaugh
 */
public class MemoPredicate implements Predicate<Transaction> {

    private final String filter;

    private final Pattern pattern;

    public MemoPredicate(final String filter, final boolean useRegex) {
        if (useRegex && !filter.isEmpty()) {
            pattern = SearchUtils.createSearchPattern(Objects.requireNonNull(filter), false);
            this.filter = null;
        } else {
            pattern = null;
            this.filter = filter.toLowerCase(Locale.getDefault());
        }
    }

    @Override
    public boolean test(final Transaction transaction) {
        if (pattern != null) {
            final Matcher matcher = pattern.matcher(transaction.getMemo());
            return matcher.matches();
        } else if (filter != null && !filter.isEmpty()) {
            return transaction.getMemo().toLowerCase(Locale.getDefault()).contains(filter);
        }

        return true;
    }
}

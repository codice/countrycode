/**
 * Copyright (c) Codice Foundation
 *
 * <p>This is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details. A copy of the GNU Lesser General Public
 * License is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
package org.codice.countrycode.standard;

import java.util.Set;

public interface StandardProvider {

  /**
   * The {@link Standard} for which the {@code CountryCode}s belong to.
   *
   * @return the standard, cannot be null
   */
  Standard getStandard();

  /**
   * A set of {@code CountryCode}s for this provider's {@code Standard}. The standard returned by
   * each country code in the set must be equal to this provider's standard.
   *
   * @return country codes for this provider's standard, or empty if there are none or there was an
   *     error reading the source
   */
  Set<CountryCode> getStandardEntries();
}

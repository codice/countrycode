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

/** Represents an entry for any given standard (FIPS 10-4, ISO 3166, GENC, etc). */
public interface CountryCode {

  /**
   * Returns the country code in a specific format. Supported format names for this entry are
   * defined by this entry's {@link Standard#getFormatNames()}.
   *
   * @param formatName name of format to get this entry in
   * @return the format, or null if not a supported format
   */
  String getAsFormat(String formatName);

  /**
   * Returns the country name, entity, organization, etc. identified by this {@code CountryCode}.
   * For example, a FIPS country code with an alpha2 code of "AF" would correspond to the name
   * "AFGHANISTAN".
   *
   * @return the name, cannot be null
   */
  String getName();

  /**
   * Returns this {@code CountryCode}s standard
   *
   * @return the standard, cannot return null
   */
  Standard getStandard();
}

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

/**
 * A country code standard. The name and version of this standard should be unique from all other
 * standards.
 */
public interface Standard {

  /**
   * Returns the name of this standard.
   *
   * @return the standard's name, cannot be null or empty string
   */
  String getName();

  /**
   * The version of this country code standard.
   *
   * @return the version, or null if a version is not applicable
   */
  String getVersion();

  /**
   * Returns a list of formats available for this standard. For example, a standard that supports
   * alpha 2 country codes, like ISO 3166-1, may have a format of "alpha2". Other examples include
   * "numeric", "alpha3", etc.
   *
   * @return a set of standard formats
   */
  Set<String> getFormatNames();
}

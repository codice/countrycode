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
package org.codice.countrycode.converter;

import java.util.Set;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;

public interface MappingStrategy {

  /**
   * A Set of mappings supported by this mapping strategy
   *
   * @return the set of country code mappings or an empty set if there are none
   */
  Set<Set<CountryCode>> getMappings();

  /**
   * A set of standards that this mapping strategy supports mappings between.
   *
   * @return a set of standards, or an empty set if none are supported
   */
  Set<Standard> getMappedStandards();

  Set<CountryCode> getMappingFor(Standard standard, String value);
}

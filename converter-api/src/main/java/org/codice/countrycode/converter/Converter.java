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

public interface Converter {

  /**
   * Takes an alpha2 value which is a key to identify the country code in the {@code from} standard
   * and converts to the corresponding country code in the {@code to} standard using an appropriate
   * {@link MappingStrategy}.
   *
   * @param alpha2 the alpha2 identifier
   * @param from the standard which has a country code with the {@code alpha2} value
   * @param to the standard to map to
   * @return a set of country code conversions
   */
  Set<CountryCode> fromAlpha2(String alpha2, Standard from, Standard to);

  /**
   * Takes an alpha3 value which is a key to identify the country code in the {@code from} standard
   * and converts to the corresponding country code in the {@code to} standard using an appropriate
   * {@link MappingStrategy}.
   *
   * @param alpha3 the alpha3 identifier
   * @param from the standard which has a country code with the {@code alpha3} value
   * @param to the standard to map to
   * @return a set of country code conversions
   */
  Set<CountryCode> fromAlpha3(String alpha3, Standard from, Standard to);

  /**
   * Takes an numeric value which is a key to identify the country code in the {@code from} standard
   * and converts to the corresponding country code in the {@code to} standard using an appropriate
   * {@link MappingStrategy}.
   *
   * @param numeric the numeric identifier
   * @param from the standard which has a country code with the {@code numeric} value
   * @param to the standard to map to
   * @return a set of country code conversions
   */

  Set<CountryCode> fromNumeric(String numeric, Standard from, Standard to);

  Set<Standard> getSupportedStandards();

  Standard getSystemDefaultStandard();
}

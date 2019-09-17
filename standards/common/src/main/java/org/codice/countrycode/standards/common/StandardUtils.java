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
package org.codice.countrycode.standards.common;

import java.util.Objects;
import org.codice.countrycode.standard.CountryCode;

public class StandardUtils {

  private StandardUtils() {}

  public static boolean containsFormatValue(CountryCode countryCode, String value) {
    return countryCode
        .getStandard()
        .getFormatNames()
        .stream()
        .map(formatName -> countryCode.getAsFormat(formatName))
        .filter(Objects::nonNull)
        .anyMatch(formatName -> formatName.equalsIgnoreCase(value));
  }
}

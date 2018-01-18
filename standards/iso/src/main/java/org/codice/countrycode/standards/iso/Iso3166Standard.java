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
package org.codice.countrycode.standards.iso;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.codice.countrycode.standard.Standard;

public class Iso3166Standard implements Standard {

  public static final String ALPHA_2 = "alpha2";

  public static final String ALPHA_3 = "alpha3";

  public static final String NUMERIC = "numeric";

  private static final Set<String> FORMAT_NAMES = ImmutableSet.of(ALPHA_2, ALPHA_3, NUMERIC);

  @Override
  public String getName() {
    return "ISO3166";
  }

  @Override
  public String getVersion() {
    return "1";
  }

  @Override
  public Set<String> getFormatNames() {
    return FORMAT_NAMES;
  }
}

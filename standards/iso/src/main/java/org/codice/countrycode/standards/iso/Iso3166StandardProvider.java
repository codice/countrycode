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

import static org.codice.countrycode.standards.iso.Iso3166Standard.ALPHA_2;
import static org.codice.countrycode.standards.iso.Iso3166Standard.ALPHA_3;
import static org.codice.countrycode.standards.iso.Iso3166Standard.NUMERIC;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;
import org.codice.countrycode.standard.StandardProvider;
import org.codice.countrycode.standards.common.CountryCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Iso3166StandardProvider implements StandardProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(Iso3166StandardProvider.class);

  private static final String[] ISO3166_1_JSON = {
    "iso3166-1.json", "iso3166-1-transitional.json", "iso3166-1-user-assigned.json"
  };

  private static final Gson GSON = new Gson();

  private final Standard standard;

  private final Set<CountryCode> countryCodes;

  public Iso3166StandardProvider() {
    standard = new Iso3166Standard();
    countryCodes = new HashSet<>();
    init();
  }

  @Override
  public Standard getStandard() {
    return standard;
  }

  @Override
  public Set<CountryCode> getStandardEntries() {
    return ImmutableSet.copyOf(countryCodes);
  }

  private void init() {
    List<Iso3166Code> isoCodes = new ArrayList<>();

    for (String filename : ISO3166_1_JSON) {
      Iso3166Code[] iso3166Codes =
          GSON.fromJson(
              new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename)),
              Iso3166Code[].class);
      for (Iso3166Code iso3166Code : iso3166Codes) {
        isoCodes.add(iso3166Code);
      }
    }

    if (CollectionUtils.isEmpty(isoCodes)) {
      LOGGER.debug(
          "ISO 3166-1 files {} contained no codes. Provider will be empty.",
          Arrays.toString(ISO3166_1_JSON));
      return;
    }

    for (Iso3166Code isoCode : isoCodes) {
      CountryCode countryCode =
          new CountryCodeBuilder(standard, isoCode.getName())
              .formatValue(ALPHA_2, isoCode.getAlpha2())
              .formatValue(ALPHA_3, isoCode.getAlpha3())
              .formatValue(NUMERIC, isoCode.getNumeric())
              .build();
      countryCodes.add(countryCode);
    }
  }

  private final class Iso3166Code {
    private final String alpha2;
    private final String alpha3;
    private final String numeric;

    private final String name;

    Iso3166Code(String alpha2, String alpha3, String numeric, String name) {
      this.alpha2 = alpha2;
      this.alpha3 = alpha3;
      this.numeric = numeric;
      this.name = name;
    }

    public String getAlpha2() {
      return alpha2;
    }

    public String getAlpha3() {
      return alpha3;
    }

    public String getNumeric() {
      return numeric;
    }

    public String getName() {
      return name;
    }
  }
}

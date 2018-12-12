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
package org.codice.countrycode.standards.fips;

import static org.codice.countrycode.standards.fips.FipsStandard.ALPHA_2;

import com.google.gson.Gson;
import java.io.InputStreamReader;
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

/** Reads a JSON file containing FIPS 10-4 country code information. */
public class FipsJsonStandardProvider implements StandardProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(FipsJsonStandardProvider.class);

  private static final String FIPS_CODES_FILE = "fips_codes.json";

  private static final Gson GSON = new Gson();

  private final Standard standard;

  private Set<CountryCode> standardEntries;

  public FipsJsonStandardProvider() {
    standardEntries = new HashSet<>();
    standard = new FipsStandard();
    init();
  }

  @Override
  public Standard getStandard() {
    return standard;
  }

  @Override
  public Set<CountryCode> getStandardEntries() {
    return standardEntries;
  }

  private void init() {
    FipsCode[] fipsArray =
        GSON.fromJson(
            new InputStreamReader(
                this.getClass().getClassLoader().getResourceAsStream(FIPS_CODES_FILE)),
            FipsCode[].class);
    List<FipsCode> fipsCodes = Arrays.asList(fipsArray);

    if (CollectionUtils.isEmpty(fipsCodes)) {
      LOGGER.debug("FIPS file [{}] contained no codes. Provider will be empty.", FIPS_CODES_FILE);
      return;
    }

    for (FipsCode fipsCode : fipsCodes) {
      CountryCode countryCode =
          new CountryCodeBuilder(standard, fipsCode.getShortName())
              .formatValue(ALPHA_2, fipsCode.getAlpha2Code())
              .build();
      standardEntries.add(countryCode);
    }
  }

  private class FipsCode {
    private String alpha2Code;

    private String shortName;

    public FipsCode(String alpha2Code, String shortName) {
      this.alpha2Code = alpha2Code;
      this.shortName = shortName;
    }

    String getAlpha2Code() {
      return alpha2Code;
    }

    String getShortName() {
      return shortName;
    }
  }
}

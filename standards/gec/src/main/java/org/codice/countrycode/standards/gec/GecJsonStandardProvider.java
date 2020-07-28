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
package org.codice.countrycode.standards.gec;

import static org.codice.countrycode.standards.gec.GecStandard.ALPHA_2;

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

/** Reads a JSON file containing GEC country code information. */
public class GecJsonStandardProvider implements StandardProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(GecJsonStandardProvider.class);

  private static final String GEC_CODES_FILE = "gec_codes.json";

  private static final Gson GSON = new Gson();

  private final Standard standard;

  private Set<CountryCode> standardEntries;

  public GecJsonStandardProvider() {
    standardEntries = new HashSet<>();
    standard = new GecStandard();
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
    GecCode[] gecArray =
        GSON.fromJson(
            new InputStreamReader(
                this.getClass().getClassLoader().getResourceAsStream(GEC_CODES_FILE)),
            GecCode[].class);
    List<GecCode> gecCodes = Arrays.asList(gecArray);

    if (CollectionUtils.isEmpty(gecCodes)) {
      LOGGER.debug("GEC file [{}] contained no codes. Provider will be empty.", GEC_CODES_FILE);
      return;
    }

    for (GecCode gecCode : gecCodes) {
      CountryCode countryCode =
          new CountryCodeBuilder(standard, gecCode.getShortName())
              .formatValue(ALPHA_2, gecCode.getAlpha2Code())
              .build();
      standardEntries.add(countryCode);
    }
  }

  private class GecCode {
    private String alpha2Code;

    private String shortName;

    public GecCode(String alpha2Code, String shortName) {
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

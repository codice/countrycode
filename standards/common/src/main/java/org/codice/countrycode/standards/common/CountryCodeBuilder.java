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

import com.google.common.base.MoreObjects;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;

public class CountryCodeBuilder {

  private final Standard standard;

  private final String name;

  private Map<String, String> formatValues = new HashMap<>();

  /**
   * Builder for immutable {@link CountryCode}s.
   *
   * @param standard {@link Standard} of the country code, cannot be null
   * @param name name of country identified by this country code, cannot be null or empty
   */
  public CountryCodeBuilder(Standard standard, String name) {
    Validate.notNull(standard, "Country code standard may not be null");
    Validate.notEmpty(name, "Country code name may not be null or empty");
    this.standard = standard;
    this.name = name;
  }

  public CountryCodeBuilder formatValue(String key, String value) {
    if (standard.getFormatNames().contains(key)) {
      formatValues.put(key, value);
    }
    return this;
  }

  public CountryCodeBuilder formatValues(Map<String, String> formatValues) {
    this.formatValues = formatValues;
    return this;
  }

  public CountryCode build() {
    return new CountryCodeImpl(standard, name, formatValues);
  }

  private class CountryCodeImpl implements CountryCode {

    private final Standard standard;

    private final Map<String, String> formatValues;

    private final String name;

    CountryCodeImpl(Standard standard, String name, Map<String, String> formatValues) {
      this.standard = standard;
      this.name = name;
      this.formatValues = formatValues;
    }

    @Override
    public String getAsFormat(String formatName) {
      return formatValues.get(formatName);
    }

    @Override
    public String getName() {
      return name;
    }

    @Override
    public Standard getStandard() {
      return standard;
    }

    @Override
    public String toString() {
      return MoreObjects.toStringHelper(this)
          .add("standard", String.format("%s %s", standard.getName(), standard.getVersion()))
          .add("name", name)
          .add("formatValues", formatValues)
          .toString();
    }
  }
}

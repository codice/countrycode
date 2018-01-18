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

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.codice.countrycode.standards.fips.FipsJsonStandardProvider;
import org.codice.countrycode.standards.genc.provider.GencXmlStandardProvider;
import org.codice.countrycode.standards.iso.Iso3166StandardProvider;

public class StandardRegistryImpl implements StandardRegistry {

  private static StandardRegistry standardRegistry;

  private final Set<StandardProvider> standardProviders;

  public static StandardRegistry getInstance() {
    if (standardRegistry == null) {
      standardRegistry = new StandardRegistryImpl();
    }
    return standardRegistry;
  }

  private StandardRegistryImpl() {
    standardProviders = new HashSet<>();
    standardProviders.add(new FipsJsonStandardProvider());
    standardProviders.add(new GencXmlStandardProvider());
    standardProviders.add(new Iso3166StandardProvider());
  }

  @Override
  public Set<StandardProvider> lookup(String name) {
    if (StringUtils.isEmpty(name)) {
      return Collections.emptySet();
    }

    return standardProviders
        .stream()
        .filter(provider -> provider.getStandard().getName().equalsIgnoreCase(name))
        .collect(Collectors.toSet());
  }

  @Override
  public StandardProvider lookup(String name, String version) {
    if (StringUtils.isEmpty(name) || StringUtils.isEmpty(version)) {
      return null;
    }

    Optional<StandardProvider> standardProvider =
        standardProviders
            .stream()
            .filter(
                provider ->
                    provider.getStandard().getName().equalsIgnoreCase(name)
                        && provider.getStandard().getVersion().equalsIgnoreCase(version))
            .findFirst();

    return standardProvider.orElse(null);
  }

  @Override
  public Set<Standard> getRegisteredStandards() {
    return standardProviders
        .stream()
        .map(StandardProvider::getStandard)
        .collect(Collectors.toSet());
  }
}

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

/** A registry for {@link StandardProvider}s and their respective {@link Standard}s. */
public interface StandardRegistry {

  /**
   * Looks up {@link StandardProvider} found by the given name.
   *
   * @param name name of the standard to lookup
   * @return standard providers that match the given name, or empty set if not found
   */
  Set<StandardProvider> lookup(String name);

  /**
   * Looks up the {@link StandardProvider} by name and version.
   *
   * @param name name of the standard to lookup, cannot be null
   * @param version version of the standard, cannot be null
   * @return the standard provider, or null if not found
   */
  StandardProvider lookup(String name, String version);

  /** @return all standards in this registry */
  Set<Standard> getRegisteredStandards();
}

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

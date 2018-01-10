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

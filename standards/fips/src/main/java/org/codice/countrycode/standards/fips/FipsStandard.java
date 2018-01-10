package org.codice.countrycode.standards.fips;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.codice.countrycode.standard.Standard;

public class FipsStandard implements Standard {

  public static final String ALPHA_2 = "alpha2";

  private static final Set<String> SUPPORTED_FORMATS = ImmutableSet.of(ALPHA_2);

  @Override
  public String getName() {
    return "FIPS";
  }

  @Override
  public String getVersion() {
    return "10-4";
  }

  @Override
  public Set<String> getFormatNames() {
    return SUPPORTED_FORMATS;
  }
}

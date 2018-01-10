package org.codice.countrycode.standards.genc.provider;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import org.codice.countrycode.standard.Standard;

public class GencStandard implements Standard {

  public static final String ALPHA_2 = "alpha2";

  public static final String ALPHA_3 = "alpha3";

  public static final String NUMERIC = "numeric";

  private static final Set<String> SUPPORTED_FORMATS =
      ImmutableSet.of(ALPHA_2, ALPHA_3, NUMERIC);

  @Override
  public String getName() {
    return "GENC";
  }

  @Override
  public String getVersion() {
    return "3.0.0";
  }

  @Override
  public Set<String> getFormatNames() {
    return SUPPORTED_FORMATS;
  }
}

package org.codice.countrycode.standards.iso;

import static org.codice.countrycode.standards.iso.Iso3166Standard.ALPHA_2;
import static org.codice.countrycode.standards.iso.Iso3166Standard.ALPHA_3;
import static org.codice.countrycode.standards.iso.Iso3166Standard.NUMERIC;

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.boon.Boon;
import org.boon.IO;
import org.codice.countrycode.standard.CountryCode;
import org.codice.countrycode.standard.Standard;
import org.codice.countrycode.standard.StandardProvider;
import org.codice.countrycode.standards.common.CountryCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Iso3166StandardProvider implements StandardProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(Iso3166StandardProvider.class);

  private static final String ISO3166_1_JSON = "iso3166-1.json";

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
    List<Iso3166Code> isoCodes =
        Boon.fromJsonArray(
            IO.read(this.getClass().getClassLoader().getResourceAsStream(ISO3166_1_JSON), "UTF-8"),
            Iso3166Code.class);

    if (CollectionUtils.isEmpty(isoCodes)) {
      LOGGER.debug("ISO 3166-1 file [{}] contained no codes. Provider will be empty.", ISO3166_1_JSON);
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

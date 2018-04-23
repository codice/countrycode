# Country Code Library

The Country Code library provides facilities for converting between different country code standards.
This software is currently in a Beta form and is subject to change

## Supported Standards
Standards are available in various formats. For example, the ISO 3166-1 `alpha2` format for the country
Afghanistan is `AF`, while its `alpha3` and `numeric` formats are `AFG` and `004`, respectively.

There are 3 currently supported standards and their respective available formats (the `name` and `version`
in parenthesis, respectively, can be used to lookup the standard in the default `StandardRegistry`):
* FIPS 10-4 (`FIPS`, `10-4`)
  - alpha2
* ISO 3166-1 (`ISO3166`, `1`)
  - alpha2
  - alpha3
  - numeric
* GENC 3.0.0 (`GENC`, `3.0.0`)
  - alpha2
  - alpha3
  - numeric
  
A country code can be retrieved for a given format with the following:

    CountryCode countryCode = new CountryCode();
    String alpha2Format = countryCode.getAsFormat("alpha2"); // ex, "AF"

## Converting between Country Codes
The converter is used to convert between country code standards. Standards can be retrieved via the `StandardRegistry`.
    
```    
  Converter converter = new CountryCodeConverter();
  
  StandardRegistry registry = StandardRegistryImpl.getInstance();
  StandardProvider fipsStandard = registry.lookup("FIPS", "10-4");
  StandardProvider isoStandard = registry.lookup("ISO3166", "1");
  
  // converting from FIPS 10-4 to ISO 3166-1 with alpha2
  Set<CountryCode> convertedCountryCodes = converter.fromAlpha2("AF", fipsStandard.getStandard(), isoStandard.getStandard());
  
  // converting from FIPS 10-4 to ISO 3166-1 with alpha3
  Set<CountryCode> convertedCountryCodes = converter.fromAlpha3("AFG", fipsStandard.getStandard(), isoStandard.getStandard());
  
  // converting from FIPS 10-4 to ISO 3166-1 with numeric
  Set<CountryCode> convertedCountryCodes = converter.fromNumeric("004", fipsStandard.getStandard(), isoStandard.getStandard());

```

## Retrieve an alpha3 code from an alpha2 code

```
  StandardRegistry registry = StandardRegistryImpl.getInstance();
  StandardProvider isoStandard = registry.lookup("ISO3166", "1");

  // converting from ISO 3166-1 alpha2 to ISO 3166-1 alpha3
  Optional<CountryCode> optionalCountryCode =
          isoStandard
              .getStandardEntries()
              .stream()
              .filter(c -> c.getAsFormat("alpha2").equals("AT"))
              .findFirst();

  if (optionalCountryCode.isPresent())
    System.out.println(optionalCountryCode.get().getAsFormat("alpha3"));

```

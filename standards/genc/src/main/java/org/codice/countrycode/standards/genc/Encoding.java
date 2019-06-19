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
package org.codice.countrycode.standards.genc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "encoding", namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn")
@XmlAccessorType(XmlAccessType.FIELD)
public class Encoding {

  @XmlElement(name = "char2Code", namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn")
  private String char2Code;

  @XmlElement(
    name = "char2CodeURISet",
    namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn"
  )
  private CodeUriSet char2CodeURISet;

  @XmlElement(
    name = "char3CodeURISet",
    namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn"
  )
  private CodeUriSet char3CodeURISet;

  @XmlElement(
    name = "numericCodeURISet",
    namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn"
  )
  private CodeUriSet numericCodeURISet;

  @XmlElement(name = "char3Code", namespace = "http://api.nsgreg.nga.mil/schema/genc/3.0/genc-cmn")
  private String char3Code;

  public void setChar2Code(String char2Code) {
    this.char2Code = char2Code;
  }

  public void setChar2CodeURISet(CodeUriSet char2CodeURISet) {
    this.char2CodeURISet = char2CodeURISet;
  }

  public void setChar3CodeURISet(CodeUriSet char3CodeURISet) {
    this.char3CodeURISet = char3CodeURISet;
  }

  public void setNumericCodeURISet(CodeUriSet numericCodeURISet) {
    this.numericCodeURISet = numericCodeURISet;
  }

  public void setChar3Code(String char3Code) {
    this.char3Code = char3Code;
  }

  public void setNumericCode(String numericCode) {
    this.numericCode = numericCode;
  }

  private String numericCode;

  public String getChar2Code() {
    return char2Code;
  }

  public CodeUriSet getChar2CodeURISet() {
    return char2CodeURISet;
  }

  public CodeUriSet getChar3CodeURISet() {
    return char3CodeURISet;
  }

  public CodeUriSet getNumericCodeURISet() {
    return numericCodeURISet;
  }

  public String getChar3Code() {
    return char3Code;
  }

  public String getNumericCode() {
    return numericCode;
  }
}

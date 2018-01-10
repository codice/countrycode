package org.codice.countrycode.standards.genc;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "GeopoliticalEntityEntry")
@XmlAccessorType(XmlAccessType.FIELD)
public class GeopoliticalEntityEntry {

  @XmlElement
  private String entryType;

  @XmlElement
  private Encoding encoding;

  @XmlElement
  private String name;

  @XmlElement
  private String shortName;

  @XmlElement
  private List<Division> division;

  @XmlElement
  private String entryDate;

  @XmlElement
  private String usRecognition;

  @XmlElement
  private String entryNotesOnNaming;

  @XmlElement
  private String fullName;

  @XmlElement
  private String entryNotesOnTerritory;

  @XmlElement
  private String gencStatus;

  @XmlElement
  private String usRecognitionNote;

  @XmlElement
  private List<LocalShortName> localShortName;

  public void setEntryType(String entryType) {
    this.entryType = entryType;
  }

  public void setEncoding(
      Encoding encoding) {
    this.encoding = encoding;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public void setDivision(
      List<Division> division) {
    this.division = division;
  }

  public void setEntryDate(String entryDate) {
    this.entryDate = entryDate;
  }

  public void setUsRecognition(String usRecognition) {
    this.usRecognition = usRecognition;
  }

  public void setEntryNotesOnNaming(String entryNotesOnNaming) {
    this.entryNotesOnNaming = entryNotesOnNaming;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public void setEntryNotesOnTerritory(String entryNotesOnTerritory) {
    this.entryNotesOnTerritory = entryNotesOnTerritory;
  }

  public void setGencStatus(String gencStatus) {
    this.gencStatus = gencStatus;
  }

  public void setUsRecognitionNote(String usRecognitionNote) {
    this.usRecognitionNote = usRecognitionNote;
  }

  public void setLocalShortName(
      List<LocalShortName> localShortName) {
    this.localShortName = localShortName;
  }

  public String getEntryType() {
    return entryType;
  }

  public Encoding getEncoding() {
    return encoding;
  }

  public String getName() {
    return name;
  }

  public String getShortName() {
    return shortName;
  }

  public List<Division> getDivisions() {
    return division;
  }

  public String getEntryDate() {
    return entryDate;
  }

  public String getUsRecognition() {
    return usRecognition;
  }

  public String getEntryNotesOnNaming() {
    return entryNotesOnNaming;
  }

  public String getFullName() {
    return fullName;
  }

  public String getEntryNotesOnTerritory() {
    return entryNotesOnTerritory;
  }

  public String getGencStatus() {
    return gencStatus;
  }

  public String getUsRecognitionNote() {
    return usRecognitionNote;
  }

  public List<LocalShortName> getLocalShortNames() {
    return localShortName;
  }

  @XmlRootElement
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Encoding {

    @XmlElement
    private String char2Code;

    @XmlElement
    private CodeUriSet char2CodeURISet;

    @XmlElement
    private CodeUriSet char3CodeURISet;

    @XmlElement
    private CodeUriSet numericCodeURISet;

    @XmlElement
    private String char3Code;

    public void setChar2Code(String char2Code) {
      this.char2Code = char2Code;
    }

    public void setChar2CodeURISet(
        CodeUriSet char2CodeURISet) {
      this.char2CodeURISet = char2CodeURISet;
    }

    public void setChar3CodeURISet(
        CodeUriSet char3CodeURISet) {
      this.char3CodeURISet = char3CodeURISet;
    }

    public void setNumericCodeURISet(
        CodeUriSet numericCodeURISet) {
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

    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CodeUriSet {

      @XmlElement
      private String codespaceURNBasedShort;

      @XmlElement
      private String codespaceURL;

      @XmlElement
      private String codespaceURNBased;

      public void setCodespaceURNBasedShort(String codespaceURNBasedShort) {
        this.codespaceURNBasedShort = codespaceURNBasedShort;
      }

      public void setCodespaceURL(String codespaceURL) {
        this.codespaceURL = codespaceURL;
      }

      public void setCodespaceURNBased(String codespaceURNBased) {
        this.codespaceURNBased = codespaceURNBased;
      }

      public void setCodespaceURN(String codespaceURN) {
        this.codespaceURN = codespaceURN;
      }

      private String codespaceURN;

      public String getCodespaceURNBasedShort() {
        return codespaceURNBasedShort;
      }

      public String getCodespaceURL() {
        return codespaceURL;
      }

      public String getCodespaceURNBased() {
        return codespaceURNBased;
      }

      public String getCodespaceURN() {
        return codespaceURN;
      }
    }
  }


  @XmlRootElement
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class Division {

    @XmlAttribute
    private String codeSpace;

    @XmlValue
    private String content;

    public void setCodeSpace(String codeSpace) {
      this.codeSpace = codeSpace;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getCodeSpace() {
      return codeSpace;
    }

    public String getContent() {
      return content;
    }
  }

  @XmlRootElement
  @XmlAccessorType(XmlAccessType.FIELD)
  public static class LocalShortName {

    @XmlElement
    private String name;

    public void setName(String name) {
      this.name = name;
    }

    public void setIso6393Char3Code(String iso6393Char3Code) {
      this.iso6393Char3Code = iso6393Char3Code;
    }

    private String iso6393Char3Code;

    public String getIso6393Char3Code() {
      return iso6393Char3Code;
    }

    public String getName() {
      return name;
    }
  }
}

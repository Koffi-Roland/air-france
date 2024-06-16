/*
 * DQE Address API
 * Endpoints documentation
 *
 * OpenAPI spec version: 3.2.0
 * Contact: support_dqe@dqe-software.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.afklm.rigui.client.dqe;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

/**
 * RNVP1
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2023-07-26T10:57:41.966347600+02:00[Europe/Paris]")
public class RNVP1 {
  @JsonProperty("Roudis")
  private Integer roudis = null;

  @JsonProperty("DQECompte")
  private String dqECompte = null;

  @JsonProperty("Instance")
  private Integer instance = null;

  @JsonProperty("CodePostal")
  private String codePostal = null;

  @JsonProperty("Status_IrisIlot")
  private String statusIrisIlot = null;

  @JsonProperty("Latitude")
  private String latitude = null;

  @JsonProperty("Localite")
  private String localite = null;

  @JsonProperty("Province")
  private String province = null;

  @JsonProperty("NbNumero")
  private Integer nbNumero = null;

  @JsonProperty("Complement")
  private String complement = null;

  @JsonProperty("Voie")
  private String voie = null;

  @JsonProperty("Adresse")
  private String adresse = null;

  @JsonProperty("Cedex")
  private String cedex = null;

  @JsonProperty("Numero")
  private String numero = null;

  @JsonProperty("ListeNumero")
  private String listeNumero = null;

  @JsonProperty("IDHexaposte")
  private String idHexaposte = null;

  @JsonProperty("IDCle")
  private String idCle = null;

  @JsonProperty("LieuDit")
  private String lieuDit = null;

  @JsonProperty("ilot")
  private String ilot = null;

  @JsonProperty("TypeVoie")
  private String typeVoie = null;

  @JsonProperty("DQECodeDetail")
  private Integer dqECodeDetail = null;

  @JsonProperty("DQELibErreur")
  private String dqELibErreur = null;

  @JsonProperty("Longitude")
  private String longitude = null;

  @JsonProperty("CompNum")
  private String compNum = null;

  @JsonProperty("iris")
  private String iris = null;

  @JsonProperty("IDLocalite")
  private String idLocalite = null;

  @JsonProperty("DQECodeErreur")
  private String dqECodeErreur = null;

  @JsonProperty("Pays")
  private String pays = null;

  @JsonProperty("IDVoie")
  private String idVoie = null;

  @JsonProperty("NumSeul")
  private Integer numSeul = null;

  @JsonProperty("Ligne2")
  private String ligne2 = null;

  @JsonProperty("DQEPourcentErreur")
  private String dqEPourcentErreur = null;

  @JsonProperty("Code_Modification")
  private String codeModification = null;

  public RNVP1 roudis(Integer roudis) {
    this.roudis = roudis;
    return this;
  }

  /**
   * Roudis code
   * @return roudis
   **/
  @Schema(example = "1651", required = true, description = "Roudis code")
  public Integer getRoudis() {
    return roudis;
  }

  public void setRoudis(Integer roudis) {
    this.roudis = roudis;
  }

  public RNVP1 dqECompte(String dqECompte) {
    this.dqECompte = dqECompte;
    return this;
  }

  /**
   * Not used anymore (always empty)
   * @return dqECompte
   **/
  @Schema(required = true, description = "Not used anymore (always empty)")
  public String getDqECompte() {
    return dqECompte;
  }

  public void setDqECompte(String dqECompte) {
    this.dqECompte = dqECompte;
  }

  public RNVP1 instance(Integer instance) {
    this.instance = instance;
    return this;
  }

  /**
   * Instance from request
   * @return instance
   **/
  @Schema(example = "0", required = true, description = "Instance from request")
  public Integer getInstance() {
    return instance;
  }

  public void setInstance(Integer instance) {
    this.instance = instance;
  }

  public RNVP1 codePostal(String codePostal) {
    this.codePostal = codePostal;
    return this;
  }

  /**
   * Postal code of the city
   * @return codePostal
   **/
  @Schema(example = "92300", required = true, description = "Postal code of the city")
  public String getCodePostal() {
    return codePostal;
  }

  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
  }

  public RNVP1 statusIrisIlot(String statusIrisIlot) {
    this.statusIrisIlot = statusIrisIlot;
    return this;
  }

  /**
   * Source of IRIS and/or ILOT
   * @return statusIrisIlot
   **/
  @Schema(example = "INSEE", required = true, description = "Source of IRIS and/or ILOT")
  public String getStatusIrisIlot() {
    return statusIrisIlot;
  }

  public void setStatusIrisIlot(String statusIrisIlot) {
    this.statusIrisIlot = statusIrisIlot;
  }

  public RNVP1 latitude(String latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * Latitude of the center of the city
   * @return latitude
   **/
  @Schema(example = "48.819987", required = true, description = "Latitude of the center of the city")
  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public RNVP1 localite(String localite) {
    this.localite = localite;
    return this;
  }

  /**
   * City name
   * @return localite
   **/
  @Schema(example = "PARIS", required = true, description = "City name")
  public String getLocalite() {
    return localite;
  }

  public void setLocalite(String localite) {
    this.localite = localite;
  }

  public RNVP1 province(String province) {
    this.province = province;
    return this;
  }

  /**
   * Not used for France (\&quot;*\&quot; for France)
   * @return province
   **/
  @Schema(example = "*", required = true, description = "Not used for France (\"*\" for France)")
  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public RNVP1 nbNumero(Integer nbNumero) {
    this.nbNumero = nbNumero;
    return this;
  }

  /**
   * Used only if Proposition&#x3D;O
   * @return nbNumero
   **/
  @Schema(example = "0", required = true, description = "Used only if Proposition=O")
  public Integer getNbNumero() {
    return nbNumero;
  }

  public void setNbNumero(Integer nbNumero) {
    this.nbNumero = nbNumero;
  }

  public RNVP1 complement(String complement) {
    this.complement = complement;
    return this;
  }

  /**
   * Additional information of the address
   * @return complement
   **/
  @Schema(example = "TOUR RAVENNE", required = true, description = "Additional information of the address")
  public String getComplement() {
    return complement;
  }

  public void setComplement(String complement) {
    this.complement = complement;
  }

  public RNVP1 voie(String voie) {
    this.voie = voie;
    return this;
  }

  /**
   * Street name only
   * @return voie
   **/
  @Schema(example = "VICTOR HUGO", required = true, description = "Street name only")
  public String getVoie() {
    return voie;
  }

  public void setVoie(String voie) {
    this.voie = voie;
  }

  public RNVP1 adresse(String adresse) {
    this.adresse = adresse;
    return this;
  }

  /**
   * Number and street
   * @return adresse
   **/
  @Schema(example = "110 RUE VICTOR HUGO", required = true, description = "Number and street")
  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public RNVP1 cedex(String cedex) {
    this.cedex = cedex;
    return this;
  }

  /**
   * Indicate if it is a cedex
   * @return cedex
   **/
  @Schema(example = "C", required = true, description = "Indicate if it is a cedex")
  public String getCedex() {
    return cedex;
  }

  public void setCedex(String cedex) {
    this.cedex = cedex;
  }

  public RNVP1 numero(String numero) {
    this.numero = numero;
    return this;
  }

  /**
   * Number of the street identified in the request
   * @return numero
   **/
  @Schema(example = "110", required = true, description = "Number of the street identified in the request")
  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public RNVP1 listeNumero(String listeNumero) {
    this.listeNumero = listeNumero;
    return this;
  }

  /**
   * List of street numbers (Filled only when Proposition&#x3D;O)
   * @return listeNumero
   **/
  @Schema(example = "2;3;4;5", required = true, description = "List of street numbers (Filled only when Proposition=O)")
  public String getListeNumero() {
    return listeNumero;
  }

  public void setListeNumero(String listeNumero) {
    this.listeNumero = listeNumero;
  }

  public RNVP1 idHexaposte(String idHexaposte) {
    this.idHexaposte = idHexaposte;
    return this;
  }

  /**
   * Unique ID from the base HEXAPOSTE
   * @return idHexaposte
   **/
  @Schema(example = "32469", required = true, description = "Unique ID from the base HEXAPOSTE")
  public String getIdHexaposte() {
    return idHexaposte;
  }

  public void setIdHexaposte(String idHexaposte) {
    this.idHexaposte = idHexaposte;
  }

  public RNVP1 idCle(String idCle) {
    this.idCle = idCle;
    return this;
  }

  /**
   * Unique ID from the base HEXACLE
   * @return idCle
   **/
  @Schema(example = "7511322A6A", required = true, description = "Unique ID from the base HEXACLE")
  public String getIdCle() {
    return idCle;
  }

  public void setIdCle(String idCle) {
    this.idCle = idCle;
  }

  public RNVP1 lieuDit(String lieuDit) {
    this.lieuDit = lieuDit;
    return this;
  }

  /**
   * Place called
   * @return lieuDit
   **/
  @Schema(example = "AVORIAZ", required = true, description = "Place called")
  public String getLieuDit() {
    return lieuDit;
  }

  public void setLieuDit(String lieuDit) {
    this.lieuDit = lieuDit;
  }

  public RNVP1 ilot(String ilot) {
    this.ilot = ilot;
    return this;
  }

  /**
   * ILOT code
   * @return ilot
   **/
  @Schema(example = "1330012", required = true, description = "ILOT code")
  public String getIlot() {
    return ilot;
  }

  public void setIlot(String ilot) {
    this.ilot = ilot;
  }

  public RNVP1 typeVoie(String typeVoie) {
    this.typeVoie = typeVoie;
    return this;
  }

  /**
   * Type of street
   * @return typeVoie
   **/
  @Schema(example = "RUE", required = true, description = "Type of street")
  public String getTypeVoie() {
    return typeVoie;
  }

  public void setTypeVoie(String typeVoie) {
    this.typeVoie = typeVoie;
  }

  public RNVP1 dqECodeDetail(Integer dqECodeDetail) {
    this.dqECodeDetail = dqECodeDetail;
    return this;
  }

  /**
   * Error code
   * @return dqECodeDetail
   **/
  @Schema(example = "10", required = true, description = "Error code")
  public Integer getDqECodeDetail() {
    return dqECodeDetail;
  }

  public void setDqECodeDetail(Integer dqECodeDetail) {
    this.dqECodeDetail = dqECodeDetail;
  }

  public RNVP1 dqELibErreur(String dqELibErreur) {
    this.dqELibErreur = dqELibErreur;
    return this;
  }

  /**
   * OK or KO
   * @return dqELibErreur
   **/
  @Schema(example = "OK", required = true, description = "OK or KO")
  public String getDqELibErreur() {
    return dqELibErreur;
  }

  public void setDqELibErreur(String dqELibErreur) {
    this.dqELibErreur = dqELibErreur;
  }

  public RNVP1 longitude(String longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * Longitude of the center of the city
   * @return longitude
   **/
  @Schema(example = "2.363771", required = true, description = "Longitude of the center of the city")
  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public RNVP1 compNum(String compNum) {
    this.compNum = compNum;
    return this;
  }

  /**
   * Additional information about the number of the street
   * @return compNum
   **/
  @Schema(example = "B", required = true, description = "Additional information about the number of the street")
  public String getCompNum() {
    return compNum;
  }

  public void setCompNum(String compNum) {
    this.compNum = compNum;
  }

  public RNVP1 iris(String iris) {
    this.iris = iris;
    return this;
  }

  /**
   * Iris code
   * @return iris
   **/
  @Schema(example = "5102", required = true, description = "Iris code")
  public String getIris() {
    return iris;
  }

  public void setIris(String iris) {
    this.iris = iris;
  }

  public RNVP1 idLocalite(String idLocalite) {
    this.idLocalite = idLocalite;
    return this;
  }

  /**
   * City&#x27;s unique code (Insee code for France)
   * @return idLocalite
   **/
  @Schema(example = "75113", required = true, description = "City's unique code (Insee code for France)")
  public String getIdLocalite() {
    return idLocalite;
  }

  public void setIdLocalite(String idLocalite) {
    this.idLocalite = idLocalite;
  }

  public RNVP1 dqECodeErreur(String dqECodeErreur) {
    this.dqECodeErreur = dqECodeErreur;
    return this;
  }

  /**
   * Not used anymore (always empty)
   * @return dqECodeErreur
   **/
  @Schema(required = true, description = "Not used anymore (always empty)")
  public String getDqECodeErreur() {
    return dqECodeErreur;
  }

  public void setDqECodeErreur(String dqECodeErreur) {
    this.dqECodeErreur = dqECodeErreur;
  }

  public RNVP1 pays(String pays) {
    this.pays = pays;
    return this;
  }

  /**
   * ISO 3 letters of the country
   * @return pays
   **/
  @Schema(example = "FRA", required = true, description = "ISO 3 letters of the country")
  public String getPays() {
    return pays;
  }

  public void setPays(String pays) {
    this.pays = pays;
  }

  public RNVP1 idVoie(String idVoie) {
    this.idVoie = idVoie;
    return this;
  }

  /**
   * Street&#x27;s unique code
   * @return idVoie
   **/
  @Schema(required = true, description = "Street's unique code")
  public String getIdVoie() {
    return idVoie;
  }

  public void setIdVoie(String idVoie) {
    this.idVoie = idVoie;
  }

  public RNVP1 numSeul(Integer numSeul) {
    this.numSeul = numSeul;
    return this;
  }

  /**
   * Number on street without the additional information
   * @return numSeul
   **/
  @Schema(example = "110", required = true, description = "Number on street without the additional information")
  public Integer getNumSeul() {
    return numSeul;
  }

  public void setNumSeul(Integer numSeul) {
    this.numSeul = numSeul;
  }

  public RNVP1 ligne2(String ligne2) {
    this.ligne2 = ligne2;
    return this;
  }

  /**
   * Second addtional information about the adress
   * @return ligne2
   **/
  @Schema(example = "FLOOR 2", required = true, description = "Second addtional information about the adress")
  public String getLigne2() {
    return ligne2;
  }

  public void setLigne2(String ligne2) {
    this.ligne2 = ligne2;
  }

  public RNVP1 dqEPourcentErreur(String dqEPourcentErreur) {
    this.dqEPourcentErreur = dqEPourcentErreur;
    return this;
  }

  /**
   * Not used anymore (always empty)
   * @return dqEPourcentErreur
   **/
  @Schema(required = true, description = "Not used anymore (always empty)")
  public String getDqEPourcentErreur() {
    return dqEPourcentErreur;
  }

  public void setDqEPourcentErreur(String dqEPourcentErreur) {
    this.dqEPourcentErreur = dqEPourcentErreur;
  }

  public RNVP1 codeModification(String codeModification) {
    this.codeModification = codeModification;
    return this;
  }

  /**
   * Code to know which field has been corrected
   * @return codeModification
   **/
  @Schema(description = "Code to know which field has been corrected")
  public String getCodeModification() {
    return codeModification;
  }

  public void setCodeModification(String codeModification) {
    this.codeModification = codeModification;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RNVP1 rnVP1 = (RNVP1) o;
    return Objects.equals(this.roudis, rnVP1.roudis) &&
            Objects.equals(this.dqECompte, rnVP1.dqECompte) &&
            Objects.equals(this.instance, rnVP1.instance) &&
            Objects.equals(this.codePostal, rnVP1.codePostal) &&
            Objects.equals(this.statusIrisIlot, rnVP1.statusIrisIlot) &&
            Objects.equals(this.latitude, rnVP1.latitude) &&
            Objects.equals(this.localite, rnVP1.localite) &&
            Objects.equals(this.province, rnVP1.province) &&
            Objects.equals(this.nbNumero, rnVP1.nbNumero) &&
            Objects.equals(this.complement, rnVP1.complement) &&
            Objects.equals(this.voie, rnVP1.voie) &&
            Objects.equals(this.adresse, rnVP1.adresse) &&
            Objects.equals(this.cedex, rnVP1.cedex) &&
            Objects.equals(this.numero, rnVP1.numero) &&
            Objects.equals(this.listeNumero, rnVP1.listeNumero) &&
            Objects.equals(this.idHexaposte, rnVP1.idHexaposte) &&
            Objects.equals(this.idCle, rnVP1.idCle) &&
            Objects.equals(this.lieuDit, rnVP1.lieuDit) &&
            Objects.equals(this.ilot, rnVP1.ilot) &&
            Objects.equals(this.typeVoie, rnVP1.typeVoie) &&
            Objects.equals(this.dqECodeDetail, rnVP1.dqECodeDetail) &&
            Objects.equals(this.dqELibErreur, rnVP1.dqELibErreur) &&
            Objects.equals(this.longitude, rnVP1.longitude) &&
            Objects.equals(this.compNum, rnVP1.compNum) &&
            Objects.equals(this.iris, rnVP1.iris) &&
            Objects.equals(this.idLocalite, rnVP1.idLocalite) &&
            Objects.equals(this.dqECodeErreur, rnVP1.dqECodeErreur) &&
            Objects.equals(this.pays, rnVP1.pays) &&
            Objects.equals(this.idVoie, rnVP1.idVoie) &&
            Objects.equals(this.numSeul, rnVP1.numSeul) &&
            Objects.equals(this.ligne2, rnVP1.ligne2) &&
            Objects.equals(this.dqEPourcentErreur, rnVP1.dqEPourcentErreur) &&
            Objects.equals(this.codeModification, rnVP1.codeModification);
  }

  @Override
  public int hashCode() {
    return Objects.hash(roudis, dqECompte, instance, codePostal, statusIrisIlot, latitude, localite, province, nbNumero, complement, voie, adresse, cedex, numero, listeNumero, idHexaposte, idCle, lieuDit, ilot, typeVoie, dqECodeDetail, dqELibErreur, longitude, compNum, iris, idLocalite, dqECodeErreur, pays, idVoie, numSeul, ligne2, dqEPourcentErreur, codeModification);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RNVP1 {\n");

    sb.append("    roudis: ").append(toIndentedString(roudis)).append("\n");
    sb.append("    dqECompte: ").append(toIndentedString(dqECompte)).append("\n");
    sb.append("    instance: ").append(toIndentedString(instance)).append("\n");
    sb.append("    codePostal: ").append(toIndentedString(codePostal)).append("\n");
    sb.append("    statusIrisIlot: ").append(toIndentedString(statusIrisIlot)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    localite: ").append(toIndentedString(localite)).append("\n");
    sb.append("    province: ").append(toIndentedString(province)).append("\n");
    sb.append("    nbNumero: ").append(toIndentedString(nbNumero)).append("\n");
    sb.append("    complement: ").append(toIndentedString(complement)).append("\n");
    sb.append("    voie: ").append(toIndentedString(voie)).append("\n");
    sb.append("    adresse: ").append(toIndentedString(adresse)).append("\n");
    sb.append("    cedex: ").append(toIndentedString(cedex)).append("\n");
    sb.append("    numero: ").append(toIndentedString(numero)).append("\n");
    sb.append("    listeNumero: ").append(toIndentedString(listeNumero)).append("\n");
    sb.append("    idHexaposte: ").append(toIndentedString(idHexaposte)).append("\n");
    sb.append("    idCle: ").append(toIndentedString(idCle)).append("\n");
    sb.append("    lieuDit: ").append(toIndentedString(lieuDit)).append("\n");
    sb.append("    ilot: ").append(toIndentedString(ilot)).append("\n");
    sb.append("    typeVoie: ").append(toIndentedString(typeVoie)).append("\n");
    sb.append("    dqECodeDetail: ").append(toIndentedString(dqECodeDetail)).append("\n");
    sb.append("    dqELibErreur: ").append(toIndentedString(dqELibErreur)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
    sb.append("    compNum: ").append(toIndentedString(compNum)).append("\n");
    sb.append("    iris: ").append(toIndentedString(iris)).append("\n");
    sb.append("    idLocalite: ").append(toIndentedString(idLocalite)).append("\n");
    sb.append("    dqECodeErreur: ").append(toIndentedString(dqECodeErreur)).append("\n");
    sb.append("    pays: ").append(toIndentedString(pays)).append("\n");
    sb.append("    idVoie: ").append(toIndentedString(idVoie)).append("\n");
    sb.append("    numSeul: ").append(toIndentedString(numSeul)).append("\n");
    sb.append("    ligne2: ").append(toIndentedString(ligne2)).append("\n");
    sb.append("    dqEPourcentErreur: ").append(toIndentedString(dqEPourcentErreur)).append("\n");
    sb.append("    codeModification: ").append(toIndentedString(codeModification)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
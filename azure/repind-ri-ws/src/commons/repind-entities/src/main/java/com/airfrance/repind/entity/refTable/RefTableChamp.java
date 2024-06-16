//Source file: D:\\cc_views\\sic_intranet_v2_0_dev_M840433\\sic_intranet\\dev_java\\src\\tables_referencesJAVA\\src\\com\\airfrance\\reef\\reftable\\RefTableChamp.java

//Source file: m:\\sic_v6_0_dev_m840316\\sic\\TablesReferences\\java\\com\\airfrance\\reef\\reftable\\RefTableChamp.java

package com.airfrance.repind.entity.refTable;


/**
 * Classe de description d'une données de la table de référence
 * 
 * @author Charley FREDAIGUES
 */
public class RefTableChamp 
{
   
   /**
    * Le code
    */
   private String m_code;
   
   /**
    * Le libellé Francais
    */
   private String m_libelleFR;
   
   /**
    * Le libellé Anglais
    */
   private String m_libelleEN;
   
   /**
    * Le Code Pere
    */
   private String m_codePere;
   
   /**
    * La référence sur la classe CREF_Mere
    */
   private RefTableMere m_theRefTableMere;
   
   /**
    * Constructeur
    * @param pCode
    * @param pLibelleFR
    * @param pLibelleEN
    * @roseuid 3F93FD4E0314
    */
   public RefTableChamp(String pCode, String pLibelleFR, String pLibelleEN) {
      m_code = pCode;
      m_libelleFR = pLibelleFR;
      m_libelleEN = pLibelleEN;
      m_codePere = "";    
   }
   
   /**
    * Constructeur
    * @param pCode
    * @param pLibelleFR
    * @param pLibelleEN
    * @param pCodePere
    * @roseuid 3F8E5AC20384
    */
   public RefTableChamp(String pCode, String pLibelleFR, String pLibelleEN, String pCodePere) {
      m_code = pCode;
      m_libelleFR = pLibelleFR;
      m_libelleEN = pLibelleEN;
      m_codePere = pCodePere;    
   }
   
   /**
    * Access method for the m_code property.
    * 
    * @return   the current value of the m_code property
    */
   public String getCode() {
      return m_code;    
   }
   
   /**
    * Access method for the m_libelleFR property.
    * 
    * @return   the current value of the m_libelleFR property
    */
   public String getLibelleFR() {
      return m_libelleFR;    
   }
   
   /**
    * Access method for the m_libelleEN property.
    * 
    * @return   the current value of the m_libelleEN property
    */
   public String getLibelleEN() {
      return m_libelleEN;    
   }
   
   /**
    * Access method for the m_codePere property.
    * 
    * @return   the current value of the m_codePere property
    */
   public String getCodePere() {
      return m_codePere;    
   }
   
   /**
    * Access method for the m_theRefTableMere property.
    * 
    * @return   the current value of the m_theRefTableMere property
    */
   public RefTableMere getTheRefTableMere() {
      return m_theRefTableMere;    
   }
}

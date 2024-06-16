//Source file: D:\\cc_views\\sic_intranet_v2_0_dev_M840466\\sic_intranet\\dev_java\\src\\tables_referencesJAVA\\src\\com\\airfrance\\reef\\reftable\\RefDataComparator.java

package com.airfrance.repind.entity.refTable;

import java.util.Comparator;

public class RefDataComparator implements Comparator 
{
   private String m_compareBy;
   private String m_language;
   
   /**
    * @param compareBy
    * @param language
    * @roseuid 4228980703C5
    */
   public RefDataComparator(String compareBy, String language) {
      m_compareBy = compareBy;
      m_language = language;
   }
   
   /**
    * @param obj1
    * @param obj2
    * @return int
    * @roseuid 4228980703C0
    */
   public int compare(Object obj1, Object obj2) {
      RefTableChamp field1 = (RefTableChamp) obj1;
      RefTableChamp field2 = (RefTableChamp) obj2;
       if (m_compareBy.equals(RefTableMere.COMPARE_LIBELLE)) {
          if (m_language.equals(RefTableMere.LANGUE_FR)) {
             return field1.getLibelleFR().compareToIgnoreCase(field2.getLibelleFR());
          } else if (m_language.equals(RefTableMere.LANGUE_EN)) {
             return field1.getLibelleEN().compareToIgnoreCase(field2.getLibelleEN());
          } else {
             throw new NullPointerException("invalid language");
          }
       }
       else {
          return field1.getCode().compareToIgnoreCase(field2.getCode());    
       }    
   }
   
   /**
    * @param obj
    * @return boolean
    * @roseuid 4228980703C3
    */
   // REPIND-260 : SONAR - Ajouter hashCode() ou Supprimer equals()
   // public boolean equals(Object obj) {
   //  return true;
   // }
}

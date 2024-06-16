package com.afklm.rigui.entity.refTable;

public class RefTableREF_CIVILITE extends RefTableMere {


    private static RefTableREF_CIVILITE m_RefTableREF_CIVILITE = null;

    public static final String _REF_M_ = "M.";
    public static final String _REF_MISS = "MISS";
    public static final String _REF_MR = "MR";
    public static final String _REF_MRS = "MRS";
    public static final String _REF_MS = "MS";
    public static final String _REF_MX = "MX";



    private RefTableREF_CIVILITE() {

        m_lstChamps.add(new RefTableChamp(_REF_M_, "MADAME OU MONSIEUR", "MADAM OR MISTER"));
        m_lstChamps.add(new RefTableChamp(_REF_MISS, "MADEMOISELLE", "MISS"));
        m_lstChamps.add(new RefTableChamp(_REF_MR, "MONSIEUR", "MONSIEUR"));
        m_lstChamps.add(new RefTableChamp(_REF_MRS, "MADAME", "MADAME"));
        m_lstChamps.add(new RefTableChamp(_REF_MS, "MADAME OU MADEMOISELLE", "MADAM OU MISS"));
        m_lstChamps.add(new RefTableChamp(_REF_MX, "MX", "MX"));
    }

    public static RefTableREF_CIVILITE instance() {
        if (null == m_RefTableREF_CIVILITE) {
            m_RefTableREF_CIVILITE = new RefTableREF_CIVILITE();
        }
        return m_RefTableREF_CIVILITE;
    }


}


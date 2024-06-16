package com.airfrance.repind.entity.refTable;

import java.util.*;

public abstract class RefTableMere {
    public static final String LANGUE_FR = "fr";
    public static final String LANGUE_EN = "en";
    public static final String COMPARE_CODE = "code";
    public static final String COMPARE_LIBELLE = "libelle";
    private static final String OPTION_VALUE = "<option value='";
    private static final String SELECTED = "selected";
    private static final String OPTION = "</option>";

    /**
     * Liste des Champs pour une colonne de la table
     */
    protected ArrayList m_lstChamps = new ArrayList();

    /**
     * Retourne le libelle correspondant au code dans la langue spécifiée
     *
     * @param pCode       code de l'enregistrement
     * @param pCodeLangue code langue (FR ou EN)
     * @param pCodePere   code pere de l enregistrement
     * @return le libellé en fonction de la langue saisie
     * @roseuid 3F8D1E85033F
     */
    public String getLibelle(String pCode, String pCodeLangue, String pCodePere) {
        pCodeLangue = pCodeLangue.toLowerCase();

        for (int i = 0; i < m_lstChamps.size(); i++) {
            RefTableChamp refTableChamp = (RefTableChamp) m_lstChamps.get(i);
            if (refTableChamp.getCode().equals(pCode)) {

                // Si le Code Pere du champ courant n'est pas celui passé en paramètre
                // Alors le code passé n'est pas valide !
                if ((refTableChamp.getCodePere().equals(pCodePere))
                        || (pCodePere.equals(""))) {

                    // Si le libellé est vide alors Erreur !
                    if (pCodeLangue.equals(RefTableMere.LANGUE_FR)) {
                        return refTableChamp.getLibelleFR();
                    } else if (pCodeLangue.equals(RefTableMere.LANGUE_EN)) {
                        return refTableChamp.getLibelleEN();
                    } else {
                        return "";
                    }
                }
            }
        }
        // Non trouvé
        return "";
    }

    /**
     * Retourne le libelle correspondant au code dans la langue spécifiée avec code
     * pere vide
     *
     * @param pCode       code de l'enregistrement
     * @param pCodeLangue code langue (FR ou EN)
     * @return le libellé en fonction de la langue saisie
     * @roseuid 3F8E65950202
     */
    public String getLibelle(String pCode, String pCodeLangue) {
        return getLibelle(pCode, pCodeLangue, "");
    }

    /**
     * Retourne le code pere associé à un code
     *
     * @param pCode code de l'enregistrement
     * @return le code associé au code père
     * @roseuid 3F8D1F0A01AB
     */
    public String getCodePere(String pCode) {
        if (pCode.equals("")) {
            return "";
        }

        for (int i = 0; i < m_lstChamps.size(); i++) {
            RefTableChamp refTableChamp = (RefTableChamp) m_lstChamps.get(i);
            if (refTableChamp.getCode().equals(pCode)) {
                return refTableChamp.getCodePere();
            }
        }

        return ""; // Non trouve
    }

    /**
     * Retourne le Code pour le code pere entré
     *
     * @param pCodePere code pere de l enregistrement
     * @return le code en fonction du code père saisi en entrée
     * @roseuid 3F8D1F210243
     */
    public String getCode(String pCodePere) {
        if (pCodePere.equals("")) {
            return "";
        }

        for (int i = 0; i < m_lstChamps.size(); i++) {
            RefTableChamp refTableChamp = (RefTableChamp) m_lstChamps.get(i);
            if (refTableChamp.getCodePere().equals(pCodePere)) {
                return refTableChamp.getCode();
            }
        }

        return ""; // Non trouve
    }

    /**
     * Vérifie si le code passé en argument est dans la table.
     *
     * @param pCode     code de l'enregistrement
     * @param pCodePere code pere de l enregistrement
     * @return existence de la présence du couple code/codepere
     * @roseuid 3F8D1F40005C
     */
    public boolean estValide(String pCode, String pCodePere) {
        if (!"".equals(getLibelle(pCode, RefTableMere.LANGUE_FR, pCodePere)))
            return true;

        return false;
    }

    /**
     * Cette fonction n'est utilisable que pour la partie IHM
     * Retourne la liste des tags <option></option>
     * avec la bonne valeur sélectionnée
     *
     * @param pCode
     * @param pCodeLangue
     * @param pCodePere
     * @return java.lang.String
     * @roseuid 3F8D1F6102F7
     */
    public String getOptionsCombo(String pCode, String pCodeLangue, String pCodePere) {
        return getOptionsComboWithFilter(pCode, pCodeLangue, pCodePere, null);
    }

    /**
     * Cette fonction n'est utilisable que pour la partie IHM
     * Retourne la liste des tags <option></option>
     * avec la bonne valeur sélectionnée
     * Utilisable pour les combos contenant les 2 libelles
     *
     * @param pCode
     * @param pCodeLangue
     * @return java.lang.String
     * @roseuid 3F8D20370292
     */
    public String getOptionsCombo2Libelle(String pCode, String pCodeLangue) {
        pCodeLangue = pCodeLangue.toLowerCase();
        String listeOptions = "";

        sortAlphabetically(pCodeLangue, COMPARE_LIBELLE);

        for (int i = 0; i < m_lstChamps.size(); i++) {
            RefTableChamp refTableChamp = (RefTableChamp) m_lstChamps.get(i);

            listeOptions += OPTION_VALUE;
            listeOptions += refTableChamp.getCode();
            listeOptions += "' ";
            if (refTableChamp.getCode().equals(pCode)) {
                listeOptions += SELECTED;
            }
            listeOptions += ">";
            listeOptions += getLibelle(refTableChamp.getCode(), pCodeLangue);
            listeOptions += " / ";
            listeOptions += getCodePere(refTableChamp.getCode());
            listeOptions += OPTION;
        }
        return listeOptions;
    }

    /**
     * Cette fonction n'est utilisable que pour la partie IHM
     * Retourne la liste des tags <option></option>
     * avec la bonne valeur sélectionnée
     *
     * @param pCode
     * @param pCodeLangue
     * @param pCodePere
     * @return java.lang.String
     * @roseuid 3F8D3157018C
     */
    public String getOptionsComboAvecCode(String pCode, String pCodeLangue, String pCodePere) {
        pCodeLangue = pCodeLangue.toLowerCase();
        String listeOptions = "";

        sortAlphabetically(pCodeLangue, COMPARE_CODE);

        for (int i = 0; i < m_lstChamps.size(); i++) {
            RefTableChamp refTableChamp = (RefTableChamp) m_lstChamps.get(i);

            if ((refTableChamp.getCodePere().equals(pCodePere)) || (pCodePere.equals(""))) {
                listeOptions += OPTION_VALUE;
                listeOptions += refTableChamp.getCode();
                listeOptions += "' ";
                if (refTableChamp.getCode().equals(pCode)) {
                    listeOptions += SELECTED;
                }
                listeOptions += ">";
                listeOptions += refTableChamp.getCode();
                listeOptions += " - ";
                listeOptions += getLibelle(refTableChamp.getCode(), pCodeLangue, pCodePere);
                listeOptions += OPTION;
            }
        }
        return listeOptions;
    }

    /**
     * @param pRefTablePere
     * @param pLstSousCode
     * @param pSousCode
     * @param pCodeLangue
     * @param pCodePere
     * @return java.lang.String
     * @roseuid 3F8D318A0332
     */
    public String getTableauGestionSousTable(RefTableMere pRefTablePere, Vector pLstSousCode, String pSousCode, String pCodeLangue, String pCodePere) {
        String tableau;

        tableau = "<table width='100%'>";

        for (int iPere = 0; iPere < pRefTablePere.getLstChamps().size(); iPere++) {
            tableau += "<tr id='Titre' name='Titre' class='tabtitle'>";
            tableau += "<td valign='top' colspan='2'><b>";
            tableau
                    += pRefTablePere.getLibelle(((RefTableChamp) pRefTablePere.getLstChamps().get(iPere)).getCode(), pCodeLangue);
            tableau += "</b></td></tr>";
            for (int iFils = 0; iFils < m_lstChamps.size(); iFils++) {
                RefTableChamp refTableChamp = (RefTableChamp) m_lstChamps.get(iFils);

                String libelleCourant =
                        getLibelle(
                                refTableChamp.getCode(),
                                pCodeLangue,
                                ((RefTableChamp) (pRefTablePere.getLstChamps().get(iPere))).getCode());
                if (!libelleCourant.equals("")) {
                    tableau += "<tr><td align='right' width='50%'>";
                    tableau += libelleCourant;
                    tableau += "</td><td><input name='REF_";
                    tableau += refTableChamp.getCode();
                    tableau += "' type='checkbox' value='O' ";
                    if (pLstSousCode.contains(refTableChamp.getCode())) {
                        tableau += "checked";
                    }
                    tableau += "></td></tr>";
                }
            }
        }

        tableau += "</table>";
        return tableau;
    }

    /**
     * Retourne le code javascript pour la gestion des sous combos
     *
     * @param pId
     * @param pCodeLangue
     * @return java.lang.String
     * @roseuid 3F8D1FC401FB
     */
    public String valoriseCodeGestionSousCombo(String pId, String pCodeLangue) {
        String codeJavascript;
        codeJavascript = "sousCombo = new SousCombo('" + pId + "');";

        for (int i = 0; i < m_lstChamps.size(); i++) {
            RefTableChamp refTableChamp = (RefTableChamp) m_lstChamps.get(i);

            codeJavascript += "sousCombo.addSousOption('";
            codeJavascript += refTableChamp.getCode();
            codeJavascript += "', '";
            codeJavascript += refTableChamp.getCode();
            codeJavascript += " - ";
            codeJavascript
                    += getLibelle(refTableChamp.getCode(), pCodeLangue, refTableChamp.getCodePere());
            codeJavascript += "', '";
            codeJavascript += refTableChamp.getCodePere();
            codeJavascript += "');";
        }
        return codeJavascript;
    }

    /**
     * @param pCodeLangue
     * @param compareBy
     * @roseuid 42234D3101F0
     */
    private void sortAlphabetically(String pCodeLangue, String compareBy) {
        Collections.sort(m_lstChamps, new RefDataComparator(compareBy, pCodeLangue));
    }

    /**
     * @param pCode
     * @param pCodeLangue
     * @param pCodePere
     * @param pFilter
     * @return java.lang.String
     * @roseuid 4224677C0219
     */
    public String getOptionsComboWithFilter(String pCode, String pCodeLangue, String pCodePere, RefTableFilterInterface pFilter) {
        pCodeLangue = pCodeLangue.toLowerCase();
        String listeOptions = "";

        sortAlphabetically(pCodeLangue, COMPARE_LIBELLE);

        for (int i = 0; i < m_lstChamps.size(); i++) {
            RefTableChamp refTableChamp = (RefTableChamp) m_lstChamps.get(i);

            if ((refTableChamp.getCodePere().equals(pCodePere) || pCodePere.equals("")) &&
                    ((pFilter == null) || pFilter.allow(refTableChamp.getCode()))) {
                listeOptions += OPTION_VALUE;
                listeOptions += refTableChamp.getCode();
                listeOptions += "' ";
                if (refTableChamp.getCode().equals(pCode)) {
                    listeOptions += SELECTED;
                }
                listeOptions += ">";
                listeOptions += getLibelle(refTableChamp.getCode(), pCodeLangue, pCodePere);
                listeOptions += OPTION;
            }
        }
        return listeOptions;
    }

    /**
     * @param pCode
     * @param pCodeLangue
     * @param pCodePere
     * @return java.lang.String
     * @roseuid 4226EAFD007E
     */
    public String getUneOptionComboAvecLibelleParCode(String pCode, String pCodeLangue, String pCodePere) {
        pCodeLangue = pCodeLangue.toLowerCase();
        String option = "";

        sortAlphabetically(pCodeLangue, COMPARE_CODE);

        for (int i = 0; i < m_lstChamps.size(); i++) {
            RefTableChamp refTableChamp = (RefTableChamp) m_lstChamps.get(i);
            if (refTableChamp.getCode().equals(pCode)) {
                option += OPTION_VALUE;
                option += refTableChamp.getCode();
                option += "' ";
                option += ">";
                option += pCode + " - " + getLibelle(refTableChamp.getCode(), pCodeLangue, pCodePere);
                option += OPTION;
            }
        }
        return option;
    }

    /**
     * Access method for the m_lstChamps property.
     *
     * @return the current value of the m_lstChamps property
     * @roseuid 4226FF0F0136
     */
    public ArrayList getLstChamps() {
        return m_lstChamps;
    }

    /**
     * @param action
     * @return java.util.List
     * @roseuid 42283B1D01D6
     */
    public List iterateOnFields(RefFieldAction action) {
        List list = new ArrayList();
        for (Iterator it = m_lstChamps.iterator(); it.hasNext(); ) {
            list.add(action.doActionOnField((RefTableChamp) it.next()));
        }
        return list;
    }

    /**
     * @param codeLangue
     * @param addCodeToLibelle
     * @param addOptionFunctionName
     * @return java.lang.String
     * @roseuid 4228890D0153
     */
    public String getJavascriptSwitchCodePere(String codeLangue, boolean addCodeToLibelle, String addOptionFunctionName) {
        String result = "";
        HashMap mapPerCodePere = new HashMap();
        Iterator it = m_lstChamps.iterator();
        while (it.hasNext()) {
            RefTableChamp champ = (RefTableChamp) it.next();
            ArrayList list;
            if (!mapPerCodePere.containsKey(champ.getCodePere())) {
                list = new ArrayList();
                mapPerCodePere.put(champ.getCodePere(), list);
            } else {
                list = (ArrayList) mapPerCodePere.get(champ.getCodePere());
            }
            list.add(champ);
        }
        it = mapPerCodePere.keySet().iterator();
        while (it.hasNext()) {
            String codePere = (String) it.next();
            result += "case \"" + codePere + "\":\n";
            ArrayList list = (ArrayList) mapPerCodePere.get(codePere);
            Collections.sort(
                    list,
                    new RefDataComparator(
                            addCodeToLibelle ? COMPARE_CODE : COMPARE_LIBELLE,
                            codeLangue));
            Iterator itOneCodePere = list.iterator();
            while (itOneCodePere.hasNext()) {
                RefTableChamp champ = (RefTableChamp) itOneCodePere.next();
                result += "\t" + addOptionFunctionName + "(\"" + champ.getCode() + "\", \"" +
                        (addCodeToLibelle ? champ.getCode() + " - " : "") +
                        (codeLangue.equals(LANGUE_EN) ? champ.getLibelleEN() : champ.getLibelleFR()) + "\");\n";
            }
            result += "\tbreak;\n";
        }
        return result;
    }
}

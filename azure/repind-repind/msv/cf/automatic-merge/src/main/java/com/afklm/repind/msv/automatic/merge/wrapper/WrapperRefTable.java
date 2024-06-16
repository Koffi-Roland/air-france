package com.afklm.repind.msv.automatic.merge.wrapper;


import com.afklm.repind.msv.automatic.merge.model.individual.ModelRefTableData;

import java.util.List;

public class WrapperRefTable {

    List<ModelRefTableData> referenceDatas;

    /**
     * @return the referenceDatas
     */
    public List<ModelRefTableData> getReferenceDatas() {
        return referenceDatas;
    }

    /**
     * @param referenceDatas the referenceDatas to set
     */
    public void setReferenceDatas(List<ModelRefTableData> referenceDatas) {
        this.referenceDatas = referenceDatas;
    }

}

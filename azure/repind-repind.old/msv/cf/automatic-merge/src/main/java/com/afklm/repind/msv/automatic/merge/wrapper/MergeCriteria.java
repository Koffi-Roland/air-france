package com.afklm.repind.msv.automatic.merge.wrapper;

import javax.validation.constraints.NotNull;
import java.util.List;

public class MergeCriteria {

    /**
     * GIN of the individual Source to merge
     */
    @NotNull
    private String ginSource;

    /**
     * GIN of the individual Target to merge
     */
    @NotNull
    private String ginCible;

    /**
     * Blocs of data with ID selected by the front
     */
    @NotNull
    private List<WrapperMergeRequestBloc> blocs;

    /**
     * Should the modifications be commited ?
     */
    @NotNull
    private boolean commit;


    /**
     * Constructor to create the critaria with all datas
     *
     * @param ginSource, Gin from the individual source
     * @param ginCible,  Gin from the individual cible
     * @param blocs,     Blocs of data selected by the front to merge
     * @param commit,    Data should be committed on DB ?
     */
    public MergeCriteria(String ginSource, String ginCible, List<WrapperMergeRequestBloc> blocs, boolean commit) {
        this.setGinSource(ginSource);
        this.setGinCible(ginCible);
        this.setBlocs(blocs);
        this.setCommit(commit);
    }

    /**
     * Constructor to create the critaria, all boolean with be FALSE
     *
     * @param ginSource, Gin from the individual source
     * @param ginCible,  Gin from the individual cible
     * @param blocs,     Blocs of data selected by the front to merge
     */
    public MergeCriteria(String ginSource, String ginCible, List<WrapperMergeRequestBloc> blocs) {
        this.setGinSource(ginSource);
        this.setGinCible(ginCible);
        this.setBlocs(blocs);
        this.setCommit(false);
    }

    /**
     * @return the ginSource
     */
    public String getGinSource() {
        return ginSource;
    }

    /**
     * @param ginSource the ginSource to set, do a trim
     */
    public void setGinSource(String ginSource) {
        this.ginSource = ginSource.trim();
    }

    /**
     * @return the ginCible
     */
    public String getGinCible() {
        return ginCible;
    }

    /**
     * @param ginCible the ginCible to set, do a trim
     */
    public void setGinCible(String ginCible) {
        this.ginCible = ginCible.trim();
    }

    /**
     * @return the blocs
     */
    public List<WrapperMergeRequestBloc> getBlocs() {
        return blocs;
    }

    /**
     * @param blocs the blocs to set
     */
    public void setBlocs(List<WrapperMergeRequestBloc> blocs) {
        this.blocs = blocs;
    }

    /**
     * @return the commit
     */
    public boolean isCommit() {
        return commit;
    }

    /**
     * @param commit the commit to set
     */
    public void setCommit(boolean commit) {
        this.commit = commit;
    }

}

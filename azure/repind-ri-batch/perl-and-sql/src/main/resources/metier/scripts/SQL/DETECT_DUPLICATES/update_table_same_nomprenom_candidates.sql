/*
 - at the end of batch  this script will be called to update table of NOMPRENOM that we have treated
    so that we will not take them into consideration in next run of batch.
 */
BEGIN
EXECUTE IMMEDIATE '
    UPDATE DQ_MGE_SAME_NOMPRE_CANDIDATES
    set is_checked = 1
    where NOMPRENOM IN (
        select NOMPRENOM from DQ_MGE_SAME_NOMPRE
    )
   ';
END;
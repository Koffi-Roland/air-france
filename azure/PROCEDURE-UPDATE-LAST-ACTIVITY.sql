CREATE OR REPLACE PROCEDURE UPDATE_LAST_ACTIVITY (

	v_gin  IN VARCHAR2,
    v_modification_signature IN  VARCHAR2,
	v_modification_site IN VARCHAR2,
	v_modification_date IN DATE,
	v_last_modification_date IN  DATE,
	v_modification_table  IN VARCHAR2
)
IS
 
 BEGIN
 
	IF v_modification_date >= v_last_modification_date THEN
  
   	-- Update record into last activity table
			
				UPDATE LAST_ACTIVITY
				SET DDATE_MODIFICATION = v_modification_date,
				SSITE_MODIFICATION = v_modification_site,
				SSIGNATURE_MODIFICATION = v_modification_signature,
				SSOURCE_MODIFICATION = v_modification_table
				WHERE SGIN = v_gin;
				
			END IF;	
			
  END;
  

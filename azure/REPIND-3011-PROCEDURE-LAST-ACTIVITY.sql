create or replace PROCEDURE PROCEDURE_LAST_ACTIVITY (
	v_gin  IN VARCHAR2,
    v_modification_signature IN  VARCHAR2,
	v_modification_site IN VARCHAR2,
	v_modification_date IN DATE,
	v_modification_table  IN VARCHAR2,
	v_change_type VARCHAR2
)

AS 
 v_gin_count NUMBER := 0;
 v_last_modification_date  DATE;

 BEGIN
 
 
     SELECT count(*) as ginCount,DDATE_MODIFICATION INTO v_gin_count,v_last_modification_date
	 FROM LAST_ACTIVITY
	 WHERE SGIN = v_gin group by DDATE_MODIFICATION;
	 
	 -- Check if  type insertion and gin already insert in last activity

	 IF v_change_type = 'I' AND v_gin_count > 0 THEN

		-- Update record into last activity table

				UPDATE LAST_ACTIVITY
					SET DDATE_MODIFICATION = SYSDATE,
					SSITE_MODIFICATION = v_modification_site,
					SSIGNATURE_MODIFICATION = v_modification_signature,
					SSOURCE_MODIFICATION = v_modification_table
					WHERE SGIN = v_gin;

		END IF;	



	IF v_change_type = 'U' OR v_change_type = 'D'  THEN 

	 	 -- Check if  type update and gin already insert in last activity

		IF v_gin_count > 0 AND v_change_type = 'U' THEN

				IF  v_modification_date >= v_last_modification_date  THEN

		-- Update record into last activity table

					UPDATE LAST_ACTIVITY
					SET DDATE_MODIFICATION = SYSDATE,
					SSITE_MODIFICATION = v_modification_site,
					SSIGNATURE_MODIFICATION = v_modification_signature,
					SSOURCE_MODIFICATION = v_modification_table
					WHERE SGIN = v_gin;

        	END IF;	

	  END IF;

		-- Delete record in last activity table - no need for momment

		/*IF v_gin_count > 0 AND v_change_type = 'D' THEN

			DELETE FROM LAST_ACTIVITY WHERE SGIN = v_gin;

        	END IF;	*/


  END IF;

		-- Handle exception and insert in the last activity table if change type is update  

 EXCEPTION
     WHEN NO_DATA_FOUND THEN
        v_gin_count := 0;
		IF v_gin_count = 0 AND  (v_change_type = 'U' OR v_change_type = 'I')  THEN
			-- Insert record into last activity table

				INSERT INTO SIC2.LAST_ACTIVITY
				 (
						SGIN,
						DDATE_MODIFICATION,
						SSIGNATURE_MODIFICATION,
						SSITE_MODIFICATION,
						SSOURCE_MODIFICATION

				)VALUES
				(
				v_gin,
				SYSDATE,
				v_modification_signature,
				v_modification_site,
				v_modification_table
				);
			END IF;	

END;
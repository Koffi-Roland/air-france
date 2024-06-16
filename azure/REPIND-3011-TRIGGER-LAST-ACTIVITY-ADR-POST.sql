CREATE OR REPLACE TRIGGER SIC2.LAST_ACTIVITY_ADR_POST
 AFTER
	INSERT OR UPDATE
   ON
	SIC2.ADR_POST
   FOR EACH ROW

	DECLARE
	
	v_modification_table VARCHAR2(50 BYTE) := 'ADR_POST';
    v_change_type VARCHAR2(5 BYTE);

BEGIN

  -- give the ability to disable the trigger

  IF NOT SIC2.globals_variables.enable_triggers_events
  THEN RETURN;
  END IF;
  
  IF INSERTING THEN
    v_change_type := 'I';
    END IF;
    
  
  IF UPDATING THEN
    v_change_type := 'U';
    END IF;
	
    -- insert in the last activity with the procedure	

	IF  :new.SGIN IS NOT NULL THEN
	
		PROCEDURE_LAST_ACTIVITY (
		:new.SGIN,
		:new.SSIGNATURE_MODIFICATION,
		:new.SSITE_MODIFICATION,
		:new.DDATE_MODIFICATION,
		v_modification_table,
		v_change_type
		);
	  
    END IF;
  
  
END;





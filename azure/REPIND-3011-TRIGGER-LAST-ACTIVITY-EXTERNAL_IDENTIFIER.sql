CREATE OR REPLACE TRIGGER SIC2.LAST_ACTIVITY_EXTERNAL_IDENTIFIER
 AFTER
	INSERT OR UPDATE
   ON
	SIC2.EXTERNAL_IDENTIFIER
   FOR EACH ROW

DECLARE
	
	v_modification_table VARCHAR2(50 BYTE) := 'EXTERNAL_IDENTIFIER';
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
    
  IF :new.SGIN IS NOT NULL THEN

	
 -- insert in the last activity with the procedure	
	PROCEDURE_LAST_ACTIVITY (
    :new.SGIN,
    :new.MODIFICATION_SIGNATURE,
	:new.MODIFICATION_SITE,
	:new.MODIFICATION_DATE,
	v_modification_table,
    v_change_type
	);
    
  END IF;
  
END;




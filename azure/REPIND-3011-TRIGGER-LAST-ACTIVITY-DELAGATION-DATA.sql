TRIGGER SIC2.LAST_ACTIVITY_DELEGATION_DATA
 AFTER
    INSERT OR UPDATE
   ON
    SIC2.DELEGATION_DATA
   FOR EACH ROW
DECLARE
     
 
    v_modification_table VARCHAR2(50 BYTE) := 'DELEGATION_DATA';
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

  IF :new.SGIN_DELEGATOR IS NOT NULL THEN

 -- insert in the last activity with the procedure 
    PROCEDURE_LAST_ACTIVITY (
    :new.SGIN_DELEGATOR,
     :new.MODIFICATION_SIGNATURE,
    :new.MODIFICATION_SITE,
    :new.MODIFICATION_DATE,
    v_modification_table,
    v_change_type
    );

  END IF;

  IF :new.SGIN_DELEGATE IS NOT NULL THEN

-- insert in the last activity with the procedure  
    PROCEDURE_LAST_ACTIVITY (
    :new.SGIN_DELEGATE,
     :new.MODIFICATION_SIGNATURE,
    :new.MODIFICATION_SITE,
    :new.MODIFICATION_DATE,
    v_modification_table,
    v_change_type
    );

  END IF;  

END;
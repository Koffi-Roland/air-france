
-------- PROCEDURE DELETE_LAST_ACTIVITY  -------------

CREATE OR REPLACE PROCEDURE DELETE_LAST_ACTIVITY (

	v_gin  IN VARCHAR2
)
AS
 
 BEGIN
 
	DELETE FROM LAST_ACTIVITY
	WHERE SGIN = v_gin;
  
END;
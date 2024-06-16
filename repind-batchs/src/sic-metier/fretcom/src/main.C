
#include "BasicException.h"
#include "DebugTools.h"
#include "CoreHandler.h"
#include "WrapperTestData.h"
#include "interfaceOracle.h"
#include "rw/rwdate.h"
#include "ToolsDate.h"

Database *db;


main()
{

  MEM_TRACK_START;
  MEM_LEAK_START;
  {
    try {
      db = new Database("tacticAF","tacticAF");
    }
    catch (ExceptionOracle& e) {
      std::cout <<  e.why() << std::endl;
      delete db;
      exit(99);
    } 
 

   std::string updateStmt("UPDATE COFFEES SET  DFIN = to_date(:dfin,'DDMMYYYY')  WHERE ISEQ = :ISEQ");

    WrapperTestData data(updateStmt);
    data.init( 3 );
    RWDate date[] = { RWDate(), RWDate(), RWDate(0UL) };
    long   id[] = { 1 , 2 , 3 };
    
    for (int i=0;i<data.getSize();i++)
      {
	data.pKey->set( i,  id[i] );
	data.pDateFin->set( i, ToolsDate::OracleDate(date[i]) );
      }

    data.execute();
    TRACEVAR(data.getNumberOfRows());
    db->commit();

  }
  delete db;
  MEM_LEAK_STOP; 
  MEM_TRACK_STOP;
  return 0;

}

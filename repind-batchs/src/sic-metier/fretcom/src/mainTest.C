
#include "BasicException.h"
#include "DebugTools.h"
#include "CoreHandler.h"
#include "WrapperTestData.h"
#include "WrapperReadTestData.h"
#include "interfaceOracle.h"
#include "rw/rwdate.h"
#include "ToolsDate.h"

Database *db;


main()
{

  MEM_TRACK_START;
  MEM_LEAK_START;
  {
    /*
    db = 0;
    TRY {
      VERIFY_PTR(db);
    }
    CATCH_X {
      SHOW_X;
      // exit(99);
    } 
    */


    try {
      db = new Database("tacticAF","tacticAF");
    }
    catch (ExceptionOracle& e) {
      std::cout <<  e.why() << std::endl;
      delete db;
      exit(99);
    } 
 

    //    WrapperRead::readInDataBase();


    std::string updateStmt("UPDATE COFFEES SET COMM = :COMM , ISEQ = :ISEQ, DFIN = to_date(:dfin,'DDMMYYYY')  WHERE COF_NAME = :COF_NAME");
    // std::string updateStmt("UPDATE COFFEES SET COMM = :COMM, DFIN = to_date(:dfin,'DDMMYYYY')  WHERE COF_NAME = :COF_NAME");

    WrapperTestData1 data(updateStmt);
    data.init( 3 );
    std::string names[] = { "C", "CC", "CCC" };
    std::string comm[] = { "c8", "cc8" , "ccc8" };
    RWDate date[] = { RWDate(), RWDate(), RWDate(0UL) };
    long   id[] = { 15 , 25 , 35 };
    
    TRACEVAR(sizeof(WrapperTestData1::CommObj::Comm));
    TRACEVAR(sizeof(WrapperTestData1::NameObj::Name));
    TRACEVAR(sizeof(WrapperTestData1::KeyObj::Key));
    TRACEVAR(sizeof(WrapperTestData1::DateFinObj::DateFin));

    for (int i=0;i<3;i++)
      {
	data.pComm->set( i, comm[i] );
	data.pName->set( i, names[i] );
	data.pDateFin->set( i, ToolsDate::OracleDate(date[i]) );
	data.pKey->setIndicator( i, INDICATOR_NULL );
	// data.pKey->set( i, id[i] );
      }

    data.execute();
    TRACEVAR(data.getNumberOfRows());
    db->commit();

    WrapperRead::readInDataBase();

  }
  delete db;
  MEM_LEAK_STOP; 
  MEM_TRACK_STOP;
  return 0;

}

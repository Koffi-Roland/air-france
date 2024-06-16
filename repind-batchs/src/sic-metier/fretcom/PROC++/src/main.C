#include <iostream>
#include "interfaceOracle.h"
#include "CoreHandler.h"
//CoreHandler * CoreHandler::cCoreHandler = 0;

class Ind {
public:
  Indicator i;
  Ind() : i(Indicator(2,INDICATOR_NOT_NULL)) {}
  ~Ind() {}
};


main() {

  MEM_TRACK_START;
  MEM_LEAK_START;
  

  Ind* in = new Ind();
  // delete in;

  MEM_LEAK_STOP; 
  MEM_TRACK_STOP;
  return 0;

  Database* db;

  try {
    db = new Database("tacticaf","tacticaf");
   }
  catch (ExceptionOracle& e) {
    std::cout <<  e.why() << std::endl;
    delete db;
    exit(99);
  }
 

  std::cout << "ACCES par tacticaf/tacticaf" << std::endl;

  Request2* rq = db->newRequest();
//   char scod[20] = "A%";

//   const char* sel = "select scod_cie, slib_cie, snum_cie from compagnies where scod_cie like :scod order by scod_cie";

//   try {
//     rq->parse(sel);
//     rq->bindHostVar("scod",CHAR,20);  
//     rq->putHostVar("scod",&scod);
//     rq->execute();
//   }
//   catch (ExceptionOracle& e) {
//     std::cout << "EXECPTION : " << e.why() << std::endl;
//   }
  
//   std::cout << " NB ROWS sur db : " << rq->getNumberOfRows() << std::endl;
//   while (rq->getNextTuple()) {
//     std::cout << rq->getStringValue("scod_cie") << ":" << rq->getStringValue("slib_cie" )<< ":" << rq->getStringValue("snum_cie") << std::endl;
//   }


  rq->execute("create table TEST(F1 int , F2 DATE)");
  rq->execute("insert into TEST values (1,'01-feb-01')");
  rq->execute("insert into TEST values (2,'02-feb-01')");
  rq->execute("insert into TEST values (3,'03-feb-01')");
  rq->execute("insert into TEST values (4,'04-feb-01')");
  rq->execute("insert into TEST values (5,'05-feb-01')");

  //  char* aDate = "03-feb-01";
  std::string aDate = "03-feb-01";

  const char* sel1 = "select F1, F2 from TEST where F2 = :F2";

  try {
    rq->parse(sel1);
    rq->bindHostVar("F2",DATE);  
    rq->putHostVar("F2", (char*) aDate.data());
    rq->execute();
  }
  catch (ExceptionOracle& e) {
    std::cout << "EXECPTION : " << e.why() << std::endl;
  }

  std::cout << " NB ROWS  = : " << rq->getNumberOfRows() << std::endl;
  while (rq->getNextTuple()) {
    std::cout << "Egalite : " << rq->getIntegerValue("F1")<< " - " << rq->getStringValue("F2")  << std::endl;
  }

  const char* sel2 = "select F1, F2 from TEST where F2 < :F2";

  try {
    rq->parse(sel2);
    rq->bindHostVar("F2",DATE);  
    rq->putHostVar("F2", (char*) aDate.data());
    rq->execute();
  }
  catch (ExceptionOracle& e) {
    std::cout << "EXECPTION : " << e.why() << std::endl;
  }

  std::cout << " NB ROWS < : " << rq->getNumberOfRows() << std::endl;
  while (rq->getNextTuple()) {
    std::cout << "Inferieur : " << rq->getIntegerValue("F1")
	 << " - " << rq->getStringValue("F2") << std::endl;
  }


  const char* sel3 = "select F1, F2 from TEST where F2 > :F2";

  try {
    rq->parse(sel3);
    rq->bindHostVar("F2",DATE);  
    rq->putHostVar("F2", (char*) aDate.data());
    rq->execute();
  }
  catch (ExceptionOracle& e) {
    std::cout << "EXECPTION : " << e.why() << std::endl;
  }

  std::cout << " NB ROWS > : " << rq->getNumberOfRows() << std::endl;
  while (rq->getNextTuple()) {
    std::cout << "Superieur : " << rq->getIntegerValue("F1") << " - " << rq->getStringValue("F2")  << std::endl;
  }



  std::string s = "20010204";

  const char* selrw = "select F1, F2 from TEST where F2 = to_date(:F2,'YYYYMMDD')";

  try {
    rq->parse(selrw);
    rq->bindHostVar("F2",DATE);  
    rq->putHostVar("F2", (char*) s.data());
    rq->execute();
  }
  catch (ExceptionOracle& e) {
    std::cout << "EXECPTION : " << e.why() << std::endl;
  }

  std::cout << " NB ROWS  = by std::string & DATE : " << rq->getNumberOfRows() << std::endl;
  while (rq->getNextTuple()) {
    std::cout << "Egalite : " << rq->getIntegerValue("F1")<< " - " << rq->getStringValue("F2")  << std::endl;
  }

  try {
    rq->parse(selrw);
    rq->bindHostVar("F2",VARCHAR2, s.length());  
    rq->putHostVar("F2", (char*) s.data());
    rq->execute();
  }
  catch (ExceptionOracle& e) {
    std::cout << "EXECPTION : " << e.why() << std::endl;
  }

  std::cout << " NB ROWS  = by std::string and VARCHAR2 : " << rq->getNumberOfRows() << std::endl;
  while (rq->getNextTuple()) {
    std::cout << "Egalite : " << rq->getIntegerValue("F1")<< " - " << rq->getStringValue("F2")  << std::endl;
  }


  rq->execute("drop table TEST");

  delete rq;
  delete db;
}

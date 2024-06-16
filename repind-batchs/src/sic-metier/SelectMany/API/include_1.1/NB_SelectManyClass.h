#if !defined(NB_SELECT_MANY_OBJECT_H)
#define NB_SELECT_MANY_OBJECT_H

//============================================================
// File Information
//============================================================
//    File:     NB_SelectManyObject.h
//  Author:     Mireille HOT
//    Date:     31/01/2006
// Purpose:     Class representing NB_SelectManyObject instances
//              An object that implements the selectMany function
//              that existed in Persistence v3 and doesn't exist
//              anymore in Persistence V8

//============================================================
// Imports
//============================================================

#include <pthread.h> //. pthread_mutex_init

#include "NB_ResultObject_Cltn.h"
#include "NB_DBAgent.h"

//. not used to create the lib 
//. but needed to be known by the applications which only include 
//. NB_SelectManyClass.h
#include "NB_ResultValue.h"
#include "NB_ResultObject.h"
#include "BasicException.h"

#include <stdio.h>

#define lgUserName 127
#define lgPassword 30
#define lgBaseName 15

class Database ;
class Request ;

class NB_SelectManyClass {

public:

#ifdef DEBUG_TRACE
  FILE* _outFile ;
#endif

  // Instance Deletion:
  virtual ~NB_SelectManyClass();
  // Instance Operators:

  // Instance Modifiers:

  // Instance Accessors:
  //. char* getErrorMessage() { return errorMessage ; } ;
  //. int getErrorNumber() { return errorNumber ; } ;

  // getSelectMany
  static::NB_SelectManyClass* getInstance();

  void setUsername(char*);
  char* getUsername();

  void setPasswd(char*);
  char* getPasswd();

  void setBasename(char*);
  char* getBasename();
  
  //. Others :
  NB_ResultObject_Cltn* selectMany( const char* SQLrequest,
				    const char* dateFormat = "DD-MON-YY" );
  void NB_SelectManyConnect(const char* user,const char* pwd,const char* database );
  void NB_SelectManyConnect();
  void NB_SelectManyDisconnect();


private:

  //. members
  ////////////
  Database* db ;
  static NB_SelectManyClass* selectManyInterface;

  char userName[lgUserName];
  char password[lgPassword];
  char baseName[lgBaseName];

  static pthread_mutex_t mutexSelectMany; 
  //. static int isMutexInitialized ;
  //. char errorMessage[2048];
  //. int errorNumber ;

  //. NB_ResultObject_Cltn collection ;

  //. methods
  ////////////
  int dbConnect() ;
  Request* dbRequest( const char* rq );
  NB_DBAgent::DataType getColumnType( Request* rq, int pos );
  // Constructors
 
 NB_SelectManyClass() {
    userName[0] = '\0';
    password[0] = '\0';
    baseName[0] = '\0';
    db = NULL;
  };
 
};

#endif //. NB_SELECT_MANY_OBJECT_H


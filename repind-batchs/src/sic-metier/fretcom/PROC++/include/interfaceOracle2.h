// ---------------------------------------------------------------------- C++ -
// File : interfaceOracle2.h
// Author : Christophe Boban
// ----------------------------------------------------------------------------
//
// Interface C++ / Pro*C Oracle s'appuyant sur le Sql Dynamique Methode 4
// ----------------------------------------------------------------------
//
// Cette interface permet d'executer tous les ordres Sql sans ecrire de Pro*C
//
// ----------------------------------------------------------------------------
#include <stdarg.h>
//pel #include <exception.h>
#include <exception>
//#include <sqlda.h>
//#include <sqlca.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include <iostream>
#include <string>
#include <ctime>

#include "DebugTools.h"
//#include <mutex>

#ifndef _INTERFACE_ORACLE2_H
#define _INTERFACE_ORACLE2_H
#define  ORACA     1
#define  SQLCA     1
#include <sqlda.h>
//#include <sqlca.h>

#ifdef __MT_SAFE_SQL__
#define MT_GUARD( aMutex ) std::lock_guard<std::mutex> lock(aMutex)
#else
#define MT_GUARD( aMutex )
#endif

#ifndef _PERF_SQL_
#define _PERF_SQL_ 1
#ifndef DEBUGi //PEL TODO
#define PERF_DB(aSqlStmt) \
   PERF_SQL("PERF_DB -"); TRACEMSG_LVL(aSqlStmt, LevelTrace::Sql);
#else
#define PERF_DB(aSqlStmt)
#endif
#endif

#ifdef _TEST_Y2K_
// for testing year 2000
#include <stdlib.h>
#ifndef _SYSDATE_2000_
#define _SYSDATE_2000_ 1
#define DAYSTO2000   "INC_DAYS_TO_2000"
#define SYSDATEWORD  std::string("SYSDATE")
#define SYSDATE_2000 std::string( getenv(DAYSTO2000) ? std::string("+") + std::string(getenv(DAYSTO2000)) : std::string())
#define SYSDATE std::string(SYSDATEWORD + SYSDATE_2000).data()
#endif
#endif

//
// type oracle avec correspondance en c (page 11-12 du Guide Pro*C)
//

namespace fretcom{
//extern bool gVide;
enum TYPE_ORACLE { DOUBLE=0,  // pour differentier le double du float 
		   // mais en Oracle c'est le meme type
                   VARCHAR2_ORACLE=1,
		   // NUMBER=2, 
		   NUMBER=3, 
		   INTEGER=3,
		   FLOAT=4,  

		   STRING=5, 

		   VARCHAR2=5, // ces 2 types sont geres de la meme maniere
		   CHAR=5,     // en Sql Dynamique Methode 4  --- CB

		   VARNUM=6,
		   DECIMAL=7,
		   LONG=8,
		   VARCHAR=9,
		   ROWID=11,
		   DATE=12,
		   VARRAW=15,
		   RAW=23,
		   LONGRAW=24,
		   UNSIGNED=68,
		   DISPLAY=91,
		   LONGVARCHAR=94,
		   LONGVARRAW=95,
		   // type CHAR interne ORACLE 
		   CHAR_ORACLE=96,
		   // CHAR=96,
		   CHARF=96,
		   CHARZ=97,
		   MLSLABEL=106
};
		   


extern "C" {
  int oracle_connect(char*,char*,char*,char*);
  void sqlglm(unsigned char*, size_t*, size_t*);
  char* get_sql_statement();
  int alloc_descriptors(SQLDA **,SQLDA **);
  void desalloc_descriptors(SQLDA *,SQLDA *);
  int execSql(char*,SQLDA *,SQLDA *,char*,int);
  int readCursor(char*,SQLDA *,SQLDA *);
  int close_cursor(char*);
  int commit(char*);
  int rollback(char*);
  int savepoint(char*);
  int rollbacktosavepoint(char*);
  int disconnect(char*);
  int prepare_sql(char*,SQLDA *,SQLDA *,char* );
  int prepare_select(char*,SQLDA *,SQLDA *,char* );
  int exec_sql(char*,SQLDA *,SQLDA *,int );
  int exec_select(char*,SQLDA *,SQLDA *);
  int exec_fetch(char*,SQLDA *,SQLDA *);
  unsigned long getSqlContext();
  void setSqlContext(unsigned long context);
  int execute_immediate(char* db, const char* sql);
}


#ifndef _BOOLEEN_
#define _BOOLEEN_
//enum bool { false=0,true=1 };
#endif

const int SIZE_LONG_HEXA = 8;

const int ErrorSize = 512;
//pel char ErrorMessage[ErrorSize];
extern char ErrorMessage[ErrorSize];
enum IndicatorOracle { INDICATOR_INVALID = 99,
		       INDICATOR_NOT_NULL=0,
		       INDICATOR_NULL=-1
};

}

#ifndef __EXCEPTION_ORACLE_H_
#define __EXCEPTION_ORACLE_H_

#include "BasicException.h"

class ExceptionDB;

const int  EXCEPTION_FATAL_COMMUNICATION_PROTOCOL = -3106;
const int  EXCEPTION_NOT_LOGGED_ON                = -1012;
const int  EXCEPTION_END_OF_FILE_ON_COMMUNICATION_CHANNEL = -3113;
const int  EXCEPTION_NOT_CONNECTED_TO_ORACLE = -3114;

const	ExceptionModule			kExModule_DB      = 'ORAC';

const   unsigned long                   kExBeginCodeOracle    = 900;

enum {
  ExDB_None = kExBeginCodeOracle,
  ExDB_FatalCommunicationProtocol,
  ExDB_ReconnectOnFatalCommunicationProtocol  
};

#define	CATCH_DB			catch(ExceptionDB EXCEPTION)
#define IS_ERROR_DB()            	(EXCEPTION.GetExceptionModule() == kExModule_DB)

#ifdef __EXCEPTION_DEBUG__
#define FailDB(type,err)		throw ExceptionDB((type), (err), #err, __FILE__, __LINE__)
#else
#define FailDB(type,err)		throw ExceptionDB((type), (err))
#endif

class ExceptionDB : public BasicException {
  const char* _libelle;
public:
  // *** Methods
  ExceptionDB(ExceptionType aType, ExceptionCode aCode) 
    : BasicException(kExModule_DB,aType,aCode) 
  {}

  ExceptionDB(ExceptionType aType, ExceptionCode aCode,const char* msg) 
#ifdef __EXCEPTION_DEBUG__  
    : BasicException(kExModule_DB,aType,aCode,msg,__FILE__, __LINE__)  , _libelle(msg)
#else
    : BasicException(kExModule_DB,aType,aCode) , _libelle(msg)
#endif
  { 
  }
  
  void	Show(std::ostream& os = tout) { BasicException::Show(os); os << why() << std::endl; }

  const char* why() const { return _libelle; }
  
#ifdef __EXCEPTION_DEBUG__
  ExceptionDB(ExceptionType aType, ExceptionCode aCode, std::string aErrLib, std::string aFile, long aLine)
    : BasicException(kExModule_DB,aType,aCode,aErrLib,aFile,aLine)
  {}

  ExceptionDB(ExceptionType aType, ExceptionCode aCode, 
	      std::string aErrLib,std::string aFile, long aLine, const char* msg)
    : BasicException(kExModule_DB,aType,aCode,aErrLib,aFile,aLine),
      _libelle(msg)
  {}
#endif

};


class ExceptionOracle : public ExceptionDB {
private:
  int _no;
public:
  ExceptionOracle(const char* msg,int no) : _no(no) , ExceptionDB(kExType_Fatal,no,msg) {}
  ~ExceptionOracle() {}
  int getErrorOracle() const { return _no; }
};

class ExceptionSoft :  public ExceptionDB {
private:
  int _no;
public:
  ExceptionSoft(const char* msg,int no=0) : _no(no) , ExceptionDB(kExType_Fatal,no,msg) {}
  ~ExceptionSoft() {}
};

#endif

extern bool gVide;

#define MEM_OBJ
namespace fretcom{
class Indicator MEM_OBJ {
  short* _ind;
  int _size;
public:
  Indicator(const Indicator& pIndicator);
  Indicator(int=1,IndicatorOracle=INDICATOR_NOT_NULL);
  short* operator&();
  Indicator& operator=(const Indicator& pIndicator);
  short& operator[](int);
  short  operator[](int) const;
  void init(IndicatorOracle ind = INDICATOR_NULL);
  int getNumberOfRows() const { return _size; }
  ~Indicator();
};

//pel

//bool gVide;

class Database2;

class Request2 MEM_OBJ {
  friend class Database2;
  Database2* _db;
  bool _nextTupleFromCursor;
  SQLDA *_bind_dp;
  SQLDA *_select_dp;
  int _numberOfRows;
  int _numberOfCurrentTuple;
  char** _currentTuple;
  int _size;
  bool _select;
  std::string _sqlStmt;

  Request2(Database2* db);

public:

  ~Request2(); 

  // gestion d'un ordre select a partir d'un curseur ligne a ligne
  // si le booleen est a true
  //
  // ---> prevu pour un traitement batch ou pour un select trop important
  //      en donnes qui entrainerait un probleme d'allocation memoire
  //
  // si le booleen est a false, toutes les lignes associees a la requete
  // sont lues en une seule fois
  //
  //---------------------------------------------------------------------
//sic
//
  void prefetchResults();
    int totalCount();
    int hasNext();
    //void*  next();
//
  int select(const char*,bool=false);
  //JMA : 28/10/10 : Homogeneisation du code
  // alias 
  int fetch(const char* pSelect) { return select(pSelect, true); }


  int getNumberOfRows() { return _numberOfRows; }

  int getNumberOfColumns() { return _select_dp->N; }
  int getTupleSize() { return getNumberOfColumns(); }

  // execution d'un ordre SQL quelconque
  int execute(const char*);

  // execution d'un ordre SQL comportant des hotes variables 
  // qui a ete prepare par les methodes :
  //
  // - parse()
  
  int execute();

  //JMA : 28/10/10 : Homogeneisation du code
  // lecture ligne a ligne avec variables hotes - CB140906
  int fetch();

  bool getNextTuple();

  bool getTuple(int);

  bool resetTuple();

  // recup des valeurs

  bool isColumnNotNull(int) const;
  bool isColumnNotNull(const char*) ;

  bool isColumnNull(int) const;
  bool isColumnNull(const char*) ;

  const char* getStringValue(int) ;

  int getIntegerValue(int);

  //bool getboolValue(int) const;
  bool getboolValue(int);

  //float getFloatValue(int) const;
  float getFloatValue(int);

  //double getDoubleValue(int) const;
  double getDoubleValue(int);
  std::string fltToString(int length, double val);
  const char* getStringValue(const char*) ;

  int getIntegerValue(const char*) ;

  float getFloatValue(const char*) ;

  double getDoubleValue(const char*) ;

  bool getboolValue(const char*) ;

  // lecture de l'indicateur oracle

  IndicatorOracle getIndicatorValue(int) const;

  IndicatorOracle getIndicatorValue(const char*) ;

  // recuperation de l'adresse du tableau indicateur

  short* getIndicatorAddress(int) const;
  short* getIndicatorAddress(const char*) ;



  bool bindHostVar(int,TYPE_ORACLE,int=0);
  bool bindHostVar(const char*,TYPE_ORACLE,int=0);

  bool putHostVar(int , void*, IndicatorOracle=INDICATOR_NOT_NULL);
  bool putHostVar(const char* ,void* , IndicatorOracle=INDICATOR_NOT_NULL);

  bool putHostVar(int , void*, short*);
  bool putHostVar(const char* ,void* , short*);

  //
  // permet de decrire les variables hotes en 1 seule
  // ligne de code (remplace bindHostVar + putHostVar)
  //
  // WARNING : cette methode ne doit pas etre utilisee
  //           sur une colonne de type CHAR dans une 
  //           clause Where 
  //
  //           Dans ce cas il faut utiliser :
  //                  bindHostVar + putHostVar
  //
  //---------------------------------------------------

  bool setHostVar(int,void* ,IndicatorOracle , TYPE_ORACLE, int=0 );
  bool setHostVar(const char*, void* , IndicatorOracle, TYPE_ORACLE, int=0);


  bool setHostVar(int,void* ,short*,TYPE_ORACLE,int=0 );
  bool setHostVar(const char*, void* , short*, TYPE_ORACLE, int=0 );


  bool parse(const char* , int = 1);

  void setSizeOfHostArray(int);
  int getSizeOfHostArray();

  // cast d'une zone C char* ou char[] en CHAR Oracle (avec des ' ')
  // jusqu'a la fin de ligne

  char* coerceToCHAR(int numberOfColumn, void* address);
  char* coerceToCHAR(const char* nameOfColumn, void* address);
  
  // a faire //////////////////////////////////////////////////

  const char* getTupleColumnName(int);
  TYPE_ORACLE getTupleColumnType(int);
  TYPE_ORACLE getTupleColumnType(const char*);
  int getTupleColumnSize(int);
  int getTupleColumnSize(const char*);


private:
  void createTuple();
  void purgeTuple();
  bool assignTuple(int);
  int researchIndex(const char*,bool=false);
  void freeMemory(int size);
  void desallocData();
  void bindParticularType(int , void*& );
  void truncCharBlank(char*& );
  //16/09/10
  void majwithChaineVide(char*& );
  void majwithZero(char*& );

  void isColumnValid(const char* fct,int numberOfColumn,
		     const char* nameOfColumn=NULL) const ;

  const char* formatMessageSoft(const char* ,int) const ;
  const char* formatMessageSoft(const char* ,const char*) const ;
  const char* formatOtherMessageSoft(const char* nameFct,
				     const char* msg) const ;

};



class Relation {
  int _current;
  std::string _nameOfRelation;
  char** _columnName;
  TYPE_ORACLE* _columnType;
  char* _owner;
  int* _length;
  int _numberOfColumn;
  bool* _nullable;
public:
  Relation(char*,int);
  ~Relation();

  const char* getName() const { return _nameOfRelation.data(); }
  std::string getNameOfRelation() const { return _nameOfRelation; }
  const char* getOwner() const;
  void addColumn(const char*,TYPE_ORACLE,int,bool) ;
  int getNumberOfColumns() const { return _numberOfColumn;}
  TYPE_ORACLE getTypeOfColumn(int i) const { return _columnType[i]; }
  const char* getColumnName(int i) const { return _columnName[i]; }
  bool isNullableColumn(int i) const { return _nullable[i]; }
  virtual bool operator==(const Relation&)const;
  virtual bool operator<(const Relation&)const;
};


class Database2 MEM_OBJ {
  char* _user;
  char* _passwd;
  char* _dbName;
  char* _dbString;
  bool _isConnected;
  // bidouille pour gerer la multi-connexion
  unsigned long _context;

#ifdef __MT_SAFE_SQL__
  static std::mutex _mutex;
#endif

public:
  enum Level {
    DEFAULT=-1,
    LVL0=0,
    LVL1,
    LVL2,
    LVL3,
    LVL4,
    LVL5,
    LVL6,
    LVL7,
    LVL8,
    LVL9
  };
  
public:
  Database2( const char* user, const char* passwd, const char* dbString = NULL);
  ~Database2();

  char* getUser();
  char* getPasswd();
  char* getDbName();
  char* getDbString();
  bool isConnected() const;

  unsigned long getContext() const { return _context; }
  void setContext() { setSqlContext(_context); }

  void    reconnect();
  void    checkConnection();
  bool commit() const;
  bool rollback() const;
  bool disconnect() const;
  bool savePoint() const;
  bool rollbackToSavePoint() const;
  bool savePoint(Level aLevel) const;
  bool rollbackToSavePoint(Level aLevel) const;
  bool savePoint(const std::string& aLabel, 
		    Level aLevel=Database2::Level(DEFAULT)) const;
  bool rollbackToSavePoint(const std::string& aLabel, 
			      Level aLevel=Database2::Level(DEFAULT)) const;

  Request2* newRequest() { return new Request2(this); }

  long getNewSequenceID(const std::string &aSequenceName);

#ifdef __MT_SAFE_SQL__
  static std::mutex& getMutex() { return _mutex; }
#endif

private:
  void initDbNameAndDbString();

};



//f
//----------------------------------------------------------------------
//     CLASS       : DbKey
//
//     DESCRIPTION : Virtual class of database key 
//                   
//----------------------------------------------------------------------
//f

class DbKey MEM_OBJ
{
public:
    DbKey();
    DbKey( const DbKey & dbKey ) { operator=( dbKey ); } 
  // Ajout du destructeur virtuel - CB310707
    virtual ~DbKey() {}

    //pel virtual     bool     isValid() const = NULL;
    virtual     bool     isValid() const = 0;

    DbKey & operator= ( const DbKey & dbKey );

#ifdef DEBUG
    friend std::ostream & operator << ( std::ostream & os, const DbKey & key )
	{ key.display(os); return os; }
    virtual     void       display( std::ostream & os ) const;
#endif

    virtual     bool  isDirty() const             { return _dirty; }

    virtual     void       setDirty()                  { _dirty = true; }
    virtual     void       setDirty( bool value ) { _dirty = value; }
    virtual     void       unsetDirty()                { _dirty = false; }

    virtual     bool  isModified()  const                   { return _modified; }
    virtual     void       setModified( bool value = true ) { _modified = value; }

    virtual     std::string  getDbKeyLabel() { return "UNDEF"; }
    virtual     std::string  getOrderByClause() const { return ""; }

    virtual     std::string  getWhereClause(bool forUpdate = false) const { return "WHERE"; }
  // virtual     std::string  addWhereClause(bool inherited=true) const { return "UNDEF"; }
    virtual     std::string  addWhereClause() const { return "UNDEF"; }

    virtual     std::string  addUpdateClause() const { return " FOR UPDATE "; }
    virtual     std::string  getSearchClause() const { return "UNDEF"; }
    virtual     std::string  getJoinWhereClause(const std::string& aJoinName) const { return "UNDEF"; }
    virtual     std::string  getInsertIntoClause() const { return "UNDEF"; }
    virtual     std::string  getInsertValueClause() const { return "UNDEF"; }
//pel
    virtual	void	   setKeyHostVar(Request2 * request) = 0;
    virtual     void       bindAndPutHostVar( Request2 * sqlRequest ) const = 0;
    virtual     DbKey    * clone() = 0;
private:

    bool   _dirty;
    bool   _modified;

};




//f
//----------------------------------------------------------------------
//     CLASS       : DbKeyExample
//
//     DESCRIPTION : database key of a Example
//                   
//----------------------------------------------------------------------
//f

class DbKeyEmpty : public DbKey
{

public:

  DbKeyEmpty() : DbKey() {}
  DbKeyEmpty( const DbKeyEmpty & dbKey ) { operator=( dbKey ); }

  // Operators :

  DbKeyEmpty & operator= ( const DbKeyEmpty & dbKey ) { DbKey::operator=( dbKey ); return *this; }

  virtual  bool    isValid() const { return true; }
  void     setKeyHostVar( Request2 * request ) {}
  virtual  void   bindAndPutHostVar( Request2 * sqlRequest ) const {}
  virtual  DbKey* clone( ) { return new DbKeyEmpty( *this ); }
  virtual     std::string  getWhereClause(bool forUpdate = false) const { return ""; }
  virtual     std::string  addWhereClause() const { return "WHERE "; }

};






//f
//----------------------------------------------------------------------
//     CLASS       : DbKeyExample
//
//     DESCRIPTION : database key of a Example
//                   
//----------------------------------------------------------------------
//f

class DbKeyExample : public DbKey
{

public:

  DbKeyExample() : DbKey() {}
  DbKeyExample( const DbKeyExample & dbKey ) { operator=( dbKey ); }
  DbKeyExample( const std::string & aValue );
  DbKeyExample( Request2 * sqlRequest );

  // Accessors :

  const std::string & getValue() const { return _value; }
  void  setValue( const std::string & aValue )  { _value = aValue; }

  // Operators :

  DbKeyExample & operator= ( const DbKeyExample & dbKey );

#ifdef DEBUG
  virtual  void         display( std::ostream & os ) const;
#endif

  virtual  std::string    getWhereClause(bool forUpdate = false) const;
  //  virtual  std::string    addWhereClause(bool inherited = true ) const;
  virtual  std::string    addWhereClause() const;

  virtual  bool    isValid() const;

  void     setKeyHostVar( Request2 * request ) {}

  void     setKeyHostVar( Request2 * request , char * aTableValue ) const;
  void     updateStaticData( char * aTableValue ) const;

  virtual  void   bindAndPutHostVar( Request2 * sqlRequest ) const;
  virtual  DbKey* clone( ) { return new DbKeyExample( *this ); }

private:
  
  std::string     _value;

};






}
#endif

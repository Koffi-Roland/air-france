// ---------------------------------------------------------------------- C++ -
// File : interfaceOracle.h
// Author : Christophe Boban
// ----------------------------------------------------------------------------
//
// Interface C++ / Pro*C Oracle s'appuyant sur le Sql Dynamique Methode 4
// ----------------------------------------------------------------------
//
// Cette interface permet d'executer tous les ordres Sql sans ecrire de Pro*C
//
// ----------------------------------------------------------------------------
#ifndef _INTERFACE_ORACLE_H
#define _INTERFACE_ORACLE_H
#include <stdarg.h>
//#include <exception.h>
#include <sqlda.h>
//#include <sqlca.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <assert.h>
#include <iostream>

#include <stdio.h>

//
// type oracle avec correspondance en c (page 11-12 du Guide Pro*C)
//

enum TYPE_ORACLE { VARCHAR2=1,
		   NUMBER=2, 
		   INTEGER=3,
		   FLOAT=4,  
		   STRING=5, 
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
		   CHAR=96,
		   CHARZ=97
};
		   


extern "C" {
#ifdef DEBUG_TRACE
  int dinb_oracle_connect( char*, char*, char*, char*,
			   //. void**,
			   //			   const struct sqlca*,
			   FILE* );
#else
  int dinb_oracle_connect( char*, char*, char*, char*
			   //. void**,
			   //const struct sqlca* 
			   );
#endif
  void sqlglm(char*,int*,int*);
  char* get_sql_statement();
#ifdef DEBUG_TRACE
  int dinb_alloc_descriptor( SQLDA**, FILE* ); //. , void**
#else
  int dinb_alloc_descriptor( SQLDA ** ); //. , void**
#endif
  void dinb_desalloc_descriptors( SQLDA* ); //. , void**
  int execSql(char*,SQLDA *,SQLDA *,char*,int
	      //. void**,
	      //const struct sqlca*
	      );
  int dinb_readCursor( char*, SQLDA*  //. ,void**,
		       //const struct sqlca*
		       );
#ifdef DEBUG_TRACE
  int dinb_close_cursor( char*,  //. ,void**,
			 //const struct sqlca*,
			 FILE* );
#else
  int dinb_close_cursor( char*  //. ,void**,
			 //const struct sqlca*
			 );
#endif
  int dinb_disconnect( char* //. , void**,
		       //const struct sqlca*
		       );
#ifdef DEBUG_TRACE
  int dinb_prepare_sql( char*, SQLDA*, char*,
			//. void**,
			//const struct sqlca*,
			FILE* );
#else
  int dinb_prepare_sql( char*, SQLDA*, char*
			//. void**,
			//const struct sqlca*
			);
#endif
#ifdef DEBUG_TRACE
  int dinb_prepare_select( char*, SQLDA *, char*,
			   //. void** ,
			   //const struct sqlca*,
			   FILE* );
#else
  int dinb_prepare_select( char*, SQLDA *, char*
			   //. void**,
			   //const struct sqlca*
			   );
#endif
  int dinb_exec_sql( char*, SQLDA *, int //. void**,
		     //const struct sqlca*
		     );
#ifdef DEBUG_TRACE
  int dinb_exec_select( char*, SQLDA *, short**,
			//. void**,
			int,
			//const struct sqlca*,
			FILE* );
#else
  int dinb_exec_select( char*, SQLDA *, short**,
			//. void**,
			int
			//const struct sqlca*
			);
#endif
  unsigned long dinb_getSqlContext() ;
  void dinb_setSqlContext( unsigned long context );
  int execute_immediate( char* db, const char* sql, void**
			 //const struct sqlca*
			 );
}


#ifndef _BOOLEEN_
#define _BOOLEEN_
enum Booleen { False=0,True=1 };
#endif

const int SIZE_LONG_HEXA = 8;

//const int ErrorSize = 1024;
//char ErrorMessage[ErrorSize];


enum IndicatorOracle { INDICATOR_INVALID = 99,
		       INDICATOR_NOT_NULL=0,
		       INDICATOR_NULL=-1
};


#ifndef __EXCEPTION_ORACLE_H_
#define __EXCEPTION_ORACLE_H_

#include "BasicException.h"

class ExceptionDB;

const int EXCEPTION_KILLED_SESSION = -28 ;
const int EXCEPTION_BAD_SESSION_ID = -30 ;
const int EXCEPTION_NOT_LOGGED_ON = -1012 ;
const int EXCEPTION_CANCELLED_BY_USER = -1013 ;
const int EXCEPTION_INIT_OR_SHUTDOWN_IN_PROGRESS = -1033 ;
const int EXCEPTION_ORACLE_UNAVAILABLE = -1034 ;
const int EXCEPTION_IMMEDIATE_SHUTDOWN_IN_PROGRESS = -1089 ;
const int EXCEPTION_ORACLE_INSTANCE_TERMINATED = -1092 ;
const int EXCEPTION_MAX_CONNECT_TIME_REACHED = -2399 ;
const int EXCEPTION_FATAL_COMMUNICATION_PROTOCOL = -3106 ;
const int EXCEPTION_COMM_CHANNEL_BREAK = -3111 ;
const int EXCEPTION_END_OF_FILE_ON_COMMUNICATION_CHANNEL = -3113 ;
const int EXCEPTION_EOF_ON_COMM_CHANNEL = -3114 ;
const int EXCEPTION_TNS_UNABLE_TO_CONNECT = -12203 ;
const int EXCEPTION_TNS_NO_LISTENER = -12541 ;
const int EXCEPTION_TNS_PACKET_WRITE_FAILURE = -12571 ;
const int EXCEPTION_CNX_RESET_BY_PEER = -17002 ;
const int EXCEPTION_NO_MORE_DATA_TO_READ = -17410 ;

const	ExceptionModule			kExModule_DB      = 'ORAC';

const   unsigned long                   kExBeginCodeOracle    = 900;

enum {
  ExDB_None = kExBeginCodeOracle,
  ExDB_FatalCommunicationProtocol,
  ExDB_ReconnectOnFatalCommunicationProtocol  
};

class ExceptionDB : public BasicException {
  const char* _libelle;
public:
  // *** Methods
  ExceptionDB(ExceptionType aType, ExceptionCode aCode) 
    : BasicException(kExModule_DB,aType,aCode) {
  }

  ExceptionDB(ExceptionType aType, ExceptionCode aCode,const char* msg) 
    : BasicException(kExModule_DB,aType,aCode) , _libelle(msg) { 
  }
#ifdef __GNUC__  
  void	Show(std::ostream& os = std::cout) { BasicException::Show(os); os << why() << std::endl; }
#else
  void	Show(ostream& os = cout) { BasicException::Show(os); os << why() << endl; }
#endif
  const char* why() const { return _libelle; }
  
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

#endif //. __EXCEPTION_ORACLE_H_


class Indicator {
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


class Database;
class Request {

  friend class Database;
  Database* _db;
  Booleen _nextTupleFromCursor;
  SQLDA *_select_dp;
  short *_type_array ;
  int _numberOfRows;
  int _numberOfCurrentTuple;
  char** _currentTuple;
  int _size;
  Booleen _select;
  std::string _sqlStmt ;
  //struct sqlca _sqlca;

#ifdef DEBUG_TRACE
  Request( Database* db, FILE* of );
#else
  Request( Database* db );
#endif

public:

  ~Request(); 

  // gestion d'un ordre select a partir d'un curseur ligne a ligne
  // si le booleen est a True
  //
  // ---> prevu pour un traitement batch ou pour un select trop important
  //      en donnes qui entrainerait un probleme d'allocation memoire
  //
  // si le booleen est a False, toutes les lignes associees a la requete
  // sont lues en une seule fois
  //
  //---------------------------------------------------------------------

  int getNumberOfRows() { return _numberOfRows; }

  int getNumberOfColumns() { return _select_dp->N; }
  int getTupleSize() { return getNumberOfColumns(); }

  // execution d'un ordre SQL comportant des hotes variables 
  // qui a ete prepare par la methode parse()
  
  Booleen execute();

  Booleen getNextTuple();

  //. Booleen getTuple(int);

  Booleen resetTuple();

  // recup des valeurs

  Booleen isColumnNotNull(int) const;
  Booleen isColumnNotNull(const char*) ;

  Booleen isColumnNull(int) const;
  Booleen isColumnNull(const char*) ;

  const char* getStringValue(int) ;

  const int isNullValue(int) ;

  int getIntegerValue(int);

  Booleen getBooleenValue(int) const;

  float getFloatValue(int) const;

  double getDoubleValue(int) const;

  const char* getStringValue(const char*) ;

  int getIntegerValue(const char*) ;

  float getFloatValue(const char*) ;

  double getDoubleValue(const char*) ;

  Booleen getBooleenValue(const char*) ;

  // lecture de l'indicateur oracle

  IndicatorOracle getIndicatorValue(int) const;

  IndicatorOracle getIndicatorValue(const char*) ;

  // recuperation de l'adresse du tableau indicateur

  short* getIndicatorAddress(int) const;
  short* getIndicatorAddress(const char*) ;


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

  Booleen parse(const char* , int = 1);

  void setSizeOfHostArray(int);
  int getSizeOfHostArray();

  // cast d'une zone C char* ou char[] en CHAR Oracle (avec des ' ')
  // jusqu'a la fin de ligne

  char* coerceToCHAR(int numberOfColumn, void* address);
  char* coerceToCHAR(const char* nameOfColumn, void* address);
  
  // a faire //////////////////////////////////////////////////

  const char* getTupleColumnName(int);
  TYPE_ORACLE getTupleColumnTypeAsOracleType( int );
  //. const char* getTupleColumnTypeAsString( int );
  
  //. const char* getTupleColumnType(const char*);
  int getTupleColumnSize(int);
  int getTupleColumnSize(const char*);

  void trace() ;


private:
  void createTuple();
  void purgeTuple();
  Booleen assignTuple(int);
  int researchIndex(const char*,Booleen=False);
  void desallocData();
  void bindParticularType(int , void*& );
  void truncCharBlank(char*& );

  void isColumnValid(const char* fct,int numberOfColumn,
		     const char* nameOfColumn=NULL) const ;

  const char* formatMessageSoft(const char* ,int) const ;
  const char* formatMessageSoft(const char* ,const char*) const ;
  const char* formatOtherMessageSoft(const char* nameFct,
				     const char* msg) const ;

};



class Relation {
  int _current;
  //. RWCString _nameOfRelation;
  std::string _nameOfRelation ;
  char** _columnName;
  TYPE_ORACLE* _columnType;
  char* _owner;
  int* _length;
  int _numberOfColumn;
  Booleen* _nullable;
public:
  Relation(char*,int);
  ~Relation();

  const char* getName() const { return _nameOfRelation.data(); }
  //. RWCString getNameOfRelation() const { return _nameOfRelation; }
  std::string getNameOfRelation() const { return _nameOfRelation; }
  const char* getOwner() const;
  void addColumn(const char*,TYPE_ORACLE,int,Booleen) ;
  int getNumberOfColumns() const { return _numberOfColumn;}
  TYPE_ORACLE getTypeOfColumn(int i) const { return _columnType[i]; }
  const char* getColumnName(int i) const { return _columnName[i]; }
  Booleen isNullableColumn(int i) const { return _nullable[i]; }
  virtual Booleen operator==(const Relation&)const;
  virtual Booleen operator<(const Relation&)const;
};



class Database {

  char* _user;
  char* _passwd;
  char* _dbName;
  char* _dbString;
  int _dateFormatSize;
  Booleen _isConnected;
  // bidouille pour gerer la multi-connexion
  unsigned long _context;
  //struct sqlca _sqlca;
  //. char* _myContext;

public:
#ifdef DEBUG_TRACE
  FILE* _outFile ;
  Database( FILE* of, const char* user, const char* passwd, const char* dbString = NULL );
#else
  Database( const char* user, const char* passwd, const char* dbString = NULL );
#endif

  ~Database();

  char* getUser();
  char* getPasswd();
  char* getDbName();
  Booleen isConnected() const;

  //. unsigned long getContext() const { return _context; }
  void setContext() { dinb_setSqlContext(_context); }
  //. void** getSQLContext() {return ((void**)&_myContext);}
  void setDateFormatSize (int date) {_dateFormatSize= date;}
  int getDateFormatSize () {return _dateFormatSize;}
  void    reconnect();
  Booleen disconnect() const;

  Request* newRequest() ; //. { return new Request(this); }

  long getNewSequenceID( const std::string &aSequenceName );

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

class DbKey {

public:
    DbKey();
    DbKey( const DbKey & dbKey ) { operator=( dbKey ); } 
  //. virtual     RWBoolean     isValid() const = NULL;
  //virtual     Booleen     isValid() const = NULL ;
  virtual     Booleen     isValid() const = 0  ;

    DbKey & operator= ( const DbKey & dbKey );

  //. virtual     RWBoolean  isDirty() const             { return _dirty; }
  virtual     Booleen  isDirty() const             { return _dirty; }

  //. virtual     void       setDirty()                  { _dirty = TRUE; }
  virtual     void       setDirty()                  { _dirty = True ; }
  //. virtual     void       setDirty( RWBoolean value ) { _dirty = value; }
  virtual     void       setDirty( Booleen value ) { _dirty = value; }
  //. virtual     void       unsetDirty()                { _dirty = FALSE; }
  virtual     void       unsetDirty()                { _dirty = False ; }

  //. virtual     RWBoolean  isModified()  const                   { return _modified; }
  virtual     Booleen  isModified()  const                   { return _modified; }
  //. virtual     void       setModified( RWBoolean value = TRUE ) { _modified = value; }
  virtual     void       setModified( Booleen value = True ) { _modified = value; }

  //. virtual     RWCString  getDbKeyLabel() { return "UNDEF"; }
  virtual     std::string  getDbKeyLabel() { return "UNDEF"; }
  //. virtual     RWCString  getOrderByClause() const { return ""; }
  virtual     std::string  getOrderByClause() const { return ""; }

  //. virtual     RWCString  getWhereClause(RWBoolean forUpdate = FALSE) const {
  virtual     std::string  getWhereClause( Booleen forUpdate = False) const {
      return "WHERE";
    }
  //. virtual     RWCString  addWhereClause() const { return "UNDEF"; }
  virtual     std::string  addWhereClause() const { return "UNDEF"; }

  //. virtual     RWCString  addUpdateClause() const { return " FOR UPDATE "; }
  virtual     std::string  addUpdateClause() const { return " FOR UPDATE "; }
  //. virtual     RWCString  getSearchClause() const { return "UNDEF"; }
  virtual     std::string  getSearchClause() const { return "UNDEF"; }
  //. virtual     RWCString  getJoinWhereClause(const RWCString& aJoinName) const { return "UNDEF"; }
  virtual     std::string  getJoinWhereClause(const std::string& aJoinName) const { return "UNDEF"; }
  //. virtual     RWCString  getInsertIntoClause() const { return "UNDEF"; }
  virtual     std::string  getInsertIntoClause() const { return "UNDEF"; }
  //. virtual     RWCString  getInsertValueClause() const { return "UNDEF"; }
  virtual     std::string  getInsertValueClause() const { return "UNDEF"; }

    //virtual	void	   setKeyHostVar(Request * request) = NULL;
    virtual	void	   setKeyHostVar(Request * request) = 0;
    virtual     void       bindAndPutHostVar( Request * sqlRequest ) const = 0;
    //virtual     void       bindAndPutHostVar( Request * sqlRequest ) const = NULL;
    //virtual     DbKey    * clone() = NULL;
    virtual     DbKey    * clone() = 0;
private:

  //. RWBoolean   _dirty;
  Booleen   _dirty;
  //. RWBoolean   _modified;
  Booleen   _modified;

};


#endif

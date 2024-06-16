// ---------------------------------------------------------------------- C++ -
// File : interfaceOracle.C
// Author : Christophe Boban
// ----------------------------------------------------------------------------
//
// Interface C++ / Pro*C Oracle s'appuyant sur le Sql Dynamique Methode 4
// ----------------------------------------------------------------------
//
// Cette interface permet d'executer tous les ordres Sql sans ecrire de Pro*C
//
// ----------------------------------------------------------------------------
//
#include <stdlib.h>
#include <sqlca.h>
#include "interfaceOracle2.h"
#include "DebugTools.h"
#include <iostream>
#include <stdio.h>
#include <time.h>
#include <locale.h>
#include <string>
#include <cstdio>
#include <string.h>
#include <stdlib.h>
#include <sstream>

bool gVide = false;

namespace fretcom{
#ifdef __MT_SAFE_SQL__
std::mutex Database2::_mutex;
#endif

const char* twoTask  = "TWO_TASK";

const std::string _SAVEPOINT_("SAVEPOINT X_");
const std::string _ROLLBACK_TO_SAVEPOINT_("ROLLBACK TO SAVEPOINT X_");
char ErrorMessage[ErrorSize];

#define __ERROR_CODE_ORACLE__     sqlca.sqlcode

#define CATCH_FOR_RETRY   \
CATCH_DB { \
  if (__ERROR_CODE_ORACLE__ == EXCEPTION_FATAL_COMMUNICATION_PROTOCOL \
      || \
      __ERROR_CODE_ORACLE__ == EXCEPTION_END_OF_FILE_ON_COMMUNICATION_CHANNEL \
      || \
      __ERROR_CODE_ORACLE__ == EXCEPTION_NOT_CONNECTED_TO_ORACLE \
      || \
      __ERROR_CODE_ORACLE__ == EXCEPTION_NOT_LOGGED_ON) \
      { \
          TRY { \
            _db->reconnect(); \
          } \
          CATCH_DB { \
              FailDB(_FATAL_,ExDB_FatalCommunicationProtocol); \
          } \
          FailDB(_WARN_,ExDB_ReconnectOnFatalCommunicationProtocol); \
      } \
   PROPAGATE; \
}

#define CATCH_ALL_ERRORS_FOR_RETRY   \
CATCH_DB { \
  TRACEVAR(__ERROR_CODE_ORACLE__); \
  TRY { \
    this->reconnect(); \
  } \
  CATCH_DB { \
      FailDB(_FATAL_,ExDB_FatalCommunicationProtocol); \
  } \
}

//f
//----------------------------------------------------------------------
//     METHOD      : Database2::GetNewSequenceID
//
//     DESCRIPTION : Get from database a new unique ID from a sequence.
//
//     INPUT       : The name of the sequence.
//     OUTPUT      : Returns the new ID or 0 if an error occured.
//----------------------------------------------------------------------
//f

long Database2::getNewSequenceID(const std::string &aSequenceName)
{
    TRACEVAL("Database2::GetNewSequenceID", aSequenceName);
#ifdef __MT_SAFE_SQL__
    static std::mutex aMutex;
    std::lock_guard<std::mutex> aGuard ( aMutex );
#endif
    std::string aSqlOrder("SELECT ");
    aSqlOrder += aSequenceName;
    aSqlOrder += ".NEXTVAL FROM DUAL";

    AutoDel<Request2> aSqlRequest = this->newRequest();
    aSqlRequest->parse(aSqlOrder.c_str());
    aSqlRequest->execute();
    return aSqlRequest->getNextTuple() ? aSqlRequest->getIntegerValue(0) : 0;

} // GetNewSequenceID




//
// suppression des blancs superflus dans une string
// (by Dor)
//
//-------------------------------------------------

void SqueezeSpaces(const char *s)
{
  char *d;  
  const char *log = s;
  d = (char*) s;
  //std::cout << "SqueezeSpaces : d = (" << d <<")" << std::endl;
  //std::cout << "SqueezeSpaces : s = (" << s <<")" << std::endl;
  while (*s) {
    //std::cout << "SqueezeSpaces :  *d= (" << *d << ")" <<std::endl;
    //std::cout << "SqueezeSpaces :  *s= (" << *s << ")" <<std::endl;
    //Code initial :
    *d++ = *s;
    //*(d++) = *s;
    //std::cout << "SqueezeSpaces :  Apres affectation *d++= (" << *d++ << ")" <<std::endl;
    if (*s == '\t')
      {
	*--d = ' ';
	d++;
      }
    if (*s++ == ' ')
      while (*s == ' ') s++;
  }
  *d = '\0';  
  //std::cout << "SqueezeSpaces : s = (" << log <<")" << std::endl;
} // SqueezeSpaces


#ifdef _TEST_Y2K_
std::string SqueezeSpacesAndCheckSysdate(const char *s)
{
  char *d, *aTmp;  
  aTmp = d = (char*) s;
  while (*s) {
    *d++ = *s;
    if (*s++ == ' ')
      while (*s == ' ') s++;
  }
  *d = '\0';  

  std::string aString (aTmp);
  if ( ! getenv(DAYSTO2000) ) return aString;

  size_t aSize = 0;

  while ( (aSize = aString.index(SYSDATEWORD.data(), aSize, std::string::ignoreCase)) != RW_NPOS ) {
    aString.replace( aSize, SYSDATEWORD.length(), SYSDATE );
    aSize+=SYSDATEWORD.length();
  }
  
  return aString;
} // SqueezeSpacesAndCheckSysdate
#endif



//
// recuperation 
// du libelle de l'erreur Oracle
//------------------------------
//
const char* LibelleErrorOracle() {
  //int _lg = ErrorSize;
  size_t _lg = ErrorSize;
  //TODO faire attention à cette conversion !!!
  sqlglm((unsigned char*) ErrorMessage,&_lg,&_lg);
  return ErrorMessage;
}


Indicator::Indicator(const Indicator& pIndicator) 
  : _size(pIndicator.getNumberOfRows())
{
  _ind = new short[_size];
  for (int i=0;i<_size;i++)
    _ind[i] = pIndicator[i];
}

Indicator::Indicator(int size,IndicatorOracle ind) 
  : _size(size) {
  _ind = new short[_size];
  for (int i=0;i<_size;i++)
    _ind[i] = ind;
}

short* Indicator::operator&() {
  return _ind;
}


Indicator::~Indicator() {
  delete [] _ind;
  _ind = 0;
}

short& Indicator::operator[](int i) {
  assert (i>=0 && i < _size);
  return _ind[i];
}

short Indicator::operator[](int i) const {
  assert (i>=0 && i < _size);
  return _ind[i];
}

void Indicator::init(IndicatorOracle ind) {
  for (int i=0;i<_size;i++)
    _ind[i] = ind;
}

Indicator& Indicator::operator=(const Indicator& pIndicator)
{
  if (_ind)
    delete [] _ind;
  _size = pIndicator.getNumberOfRows();
  _ind = new short[_size];
  for (int i=0;i<_size;i++)
    _ind[i] = pIndicator[i];
  
  return *this;
 
}


///////////////////////////////////////////////////////////////
//
// clas Database2 : gestion des connexions deconnexions commit
//                 et rollback
//
///////////////////////////////////////////////////////////////
 

Database2::Database2(const char* user , const char* passwd , const char* dbString) 
  : _user((char*) user), _passwd((char*) passwd), _dbName(NULL), _dbString((char*) dbString) 
{
  PERF_DB( "Connection [" + std::string(user) + "/" + std::string(passwd) +"]");
  initDbNameAndDbString();
  MT_GUARD( getMutex() );
  // init du contexte
  setSqlContext(lrand48());
  int error = oracle_connect(_user,_passwd,_dbName,_dbString);
  _context = getSqlContext();
  if (error) {
    _isConnected = false;
    throw ExceptionOracle(LibelleErrorOracle(),error);
  }
  else
    _isConnected = true;
}


Database2::~Database2() {
  if (_dbName)  { delete _dbName;  _dbName=NULL; }
}


void Database2::initDbNameAndDbString() {
  // nom aleatoire en Hexa
  long l = lrand48();
  _dbName = new char[SIZE_LONG_HEXA+1];
  sprintf(_dbName, "%.*X", SIZE_LONG_HEXA, l);

  if (!_dbString) _dbString = getenv(twoTask);
}

char* Database2::getDbName() {
  return _dbName;
}

char* Database2::getDbString() {
  return _dbString;
}

void Database2::checkConnection() {  
  PERF_DB("Database2::checkConnection");
  AutoDel<Request2> request = newRequest();
  std::string aSysdate("SELECT SYSDATE FROM DUAL");
  // reconnect is made by execute method of request object (CATCH_FOR_RETRY)
  //request->execute(aSysdate);
  //JMA : 28/10/10 : Homogeneisation du code
   TRY {
    request->execute(aSysdate.c_str());
  }
  CATCH_ALL_ERRORS_FOR_RETRY {
  }
}


void Database2::reconnect() {  
  PERF_DB( "Connection [" + std::string(_user) + "/" + std::string(_passwd) + "]");
  TRACEMSG("\nTRY TO RECONNECT!\n");
  MT_GUARD( getMutex() );
  setSqlContext(0);
  int error = oracle_connect(_user,_passwd,_dbName,_dbString);
  _context = getSqlContext();
  if (error) {
    _isConnected = false;
    throw ExceptionOracle(LibelleErrorOracle(),error);
  }
  else
    _isConnected = true;

  TRACEMSG("\nRECONNECTION OK!\n");
}




char* Database2::getUser() {
  return _user;
}

char* Database2::getPasswd() {
  return _passwd;
}


bool Database2::isConnected() const {
  return _isConnected;
}

bool Database2::commit() const {
  MT_GUARD( getMutex() );
  TRACEMSG("\nCOMMIT!\n");
  return fretcom::commit(_dbName) == 0 ? true : false;
}

bool Database2::rollback() const {
  MT_GUARD( getMutex() );
  TRACEMSG("\nROLLBACK!\n");
  return fretcom::rollback(_dbName) == 0 ? true : false;
}

bool Database2::savePoint() const {
  MT_GUARD( getMutex() );
  TRACEMSG("\nSAVEPOINT!\n");
  return fretcom::savepoint(_dbName) == 0 ? true : false;
}

bool Database2::rollbackToSavePoint() const {
  MT_GUARD( getMutex() );
  TRACEMSG("\nROLLBACK TO SAVEPOINT!\n");
  return fretcom::rollbacktosavepoint(_dbName) == 0 ? true : false;
}

bool Database2::savePoint(Level aLevel) const {
  // the mutex is done in execute method
  TRACEMSG("\nSAVEPOINT ["  << aLevel << "]\n");
  std::string aStmt( _SAVEPOINT_        + 
		   std::string(_dbName) + 
		   to_string( (unsigned long) aLevel) ); 
  return execute_immediate( _dbName, aStmt.data() ) == 0 ? true : false;
}

bool Database2::rollbackToSavePoint(Level aLevel) const {
  // the mutex is done in execute method
  TRACEMSG("\nROLLBACK TO SAVEPOINT [" << aLevel << "]\n");
  std::string aStmt( _ROLLBACK_TO_SAVEPOINT_ +
		   std::string(_dbName)      +
		   to_string( (unsigned long) aLevel) );
  return execute_immediate( _dbName, aStmt.data() ) == 0 ? true : false;
}


bool Database2::savePoint(const std::string& aLabel, Level aLevel) const {
  // the mutex is done in execute method
  TRACEMSG("\nSAVEPOINT [" << aLabel << ":" << aLevel << "]\n");
  std::string aStmt( _SAVEPOINT_ + 
		   aLabel      + 
		   to_string( (unsigned long) aLevel) );
  return execute_immediate( _dbName, aStmt.data() ) == 0 ? true : false;
}

bool Database2::rollbackToSavePoint(const std::string& aLabel, Level aLevel) const {
  // the mutex is done in execute method
  TRACEMSG("\nROLLBACK TO SAVEPOINT [" << aLabel << ":" << aLevel << "]\n");
  std::string aStmt( _ROLLBACK_TO_SAVEPOINT_ +
		   aLabel                  + 
		   to_string( (unsigned long) aLevel) );
  return execute_immediate( _dbName, aStmt.data() ) == 0 ? true : false;
}

bool Database2::disconnect() const {
  MT_GUARD( getMutex() );
  TRACEMSG("\nDISCONNECT!\n");
  return fretcom::disconnect(_dbName) == 0 ? true : false;
}


//-----------------------------------
//
//
//        Class Request
//
//
//-----------------------------------


// Request::Request(char* db) : 
Request2::Request2(Database2* db) : 
  _db(db) ,
  _bind_dp(NULL) , 
  _select_dp(NULL) , 
  _nextTupleFromCursor(false) ,
  _numberOfRows(0) ,
  _numberOfCurrentTuple(0) ,
  _currentTuple(NULL)
{ 
  MT_GUARD( Database2::getMutex() );
  int error = alloc_descriptors(&_bind_dp,&_select_dp);
  if (error) 
    throw Xalloc("Probleme d'allocation memoire : fonction ' alloc_descriptors'");
}



Request2::~Request2() {
  // delete des tuples
  purgeTuple();
  // desallocation des sqlda
  desalloc_descriptors(_bind_dp,_select_dp);
}

int Request2::totalCount(){return getNumberOfRows();}
int Request2::hasNext(){
                 if (getNextTuple() != 0){
                   return 1;
                 }
                 else
                   return 0;}
 
int Request2::execute() {
  //TODO PERF_DB( _sqlStmt );
  MT_GUARD( Database2::getMutex() );
  _db->setContext();
  
  // execution d'un select ou d'un autre ordre Sql
  TRY {
    if (_select) {
      _nextTupleFromCursor = false;
      purgeTuple();
      
      desallocData();
      
      int error = exec_select(_db->getDbName(),_bind_dp,_select_dp);
      if (error)
	throw ExceptionOracle(LibelleErrorOracle(),error);
      _numberOfRows = sqlca.sqlerrd[2];
      createTuple();
      return 0;
    }
    else {
      int error = exec_sql(_db->getDbName(),_bind_dp,_select_dp,_size);
      if (error)
	throw ExceptionOracle(LibelleErrorOracle(),error);
      _numberOfRows = sqlca.sqlerrd[2];
      return error;  
    }
  }
  CATCH_FOR_RETRY {
  }
}

int Request2::fetch() {
  //TODO PERF_DB( _sqlStmt );
  MT_GUARD( Database2::getMutex() );
  _db->setContext();
  
  // execution d'un select ou d'un autre ordre Sql
  TRY {
    if (_select) {
      _nextTupleFromCursor = true;
      purgeTuple();      
      desallocData();
      int error = exec_fetch(_db->getDbName(),_bind_dp,_select_dp);
      if (error)
	throw ExceptionOracle(LibelleErrorOracle(),error);
      _numberOfRows = 1; // lecture ligne a ligne
      createTuple();
      return _numberOfRows;
      // le fetch est fait sur le getNextTuple()      
    }
    else {
      throw ExceptionSoft(formatOtherMessageSoft("Request::fetch", "Fetch order only apply with SELECT ")); 
    }
  }
  CATCH_FOR_RETRY {
  }
}

//
// execution d'un ordre Sql quelconque (hors select)
// sans variables hotes
//
// in : ordre Sql 
// out : nb lignes traitees
//
//--------------------------------------------------
//
int Request2::execute(const char* sql) {
  MT_GUARD( Database2::getMutex() );
  _db->setContext();

  _nextTupleFromCursor = false;
  // Pour eviter core dumped si const char *
  std::string stmt(sql);
  //SqueezeSpaces(stmt.data());
  sql=stmt.data();
  //SqueezeSpaces(sql);

  //TODO PERF_DB( sql );
  
  // desallocation memoire
  desallocData();
  purgeTuple();

  TRY {
    int error = execSql(_db->getDbName(),_bind_dp,_select_dp,(char*)sql,0);
    if (error)
      throw ExceptionOracle(LibelleErrorOracle(),error);
  }
  CATCH_FOR_RETRY {
  }
   
  _numberOfRows = sqlca.sqlerrd[2];
  createTuple();
  return _numberOfRows;
}


//
// execution d'un ordre 'select' sans variables hotes
//
// in : ordre 'select' , booleen de lecture ligne a ligne
//                       (dans le cas d'un traitement batch ou
//                        d'un gros volume de donnees)
// out : nb lignes lues
//
//--------------------------------------------------
//  
int Request2::select(const char* sql,bool simpleRead) {

  MT_GUARD( Database2::getMutex() );
  _db->setContext();

  _nextTupleFromCursor = simpleRead ?  true : false;
  // Pour eviter core dumped si const char *
  std::string stmt(sql);
  //SqueezeSpaces(stmt.data());
  sql=stmt.data();
  //SqueezeSpaces(sql);

  PERF_DB( sql );

  desallocData();
  purgeTuple();

  TRY {
    int error = execSql(_db->getDbName(),_bind_dp,_select_dp,
			(char*)sql,(int) simpleRead);
    if (error)
      throw ExceptionOracle(LibelleErrorOracle(),error);
  }
  CATCH_FOR_RETRY {
  }
  
  _numberOfRows = sqlca.sqlerrd[2];
  createTuple();
  if (_nextTupleFromCursor) _numberOfRows = 1;
  return _numberOfRows;
}

//
//  preparation de l'ordre Sql avec variables hotes
//
//
//--------------------------------------------------------
//
bool Request2::parse(const char* sql,int size) {
  MT_GUARD( Database2::getMutex() );
  _db->setContext();

  _select = false;
  int error;
  // Pour eviter core dumped si const char *
  std::string stmt(sql);
  //ajout traces SQL
  if(getenv("SQL_LOG")){
     std::cout << stmt << std::endl;
  }
  //SqueezeSpaces(stmt.data());
  sql=stmt.data();
  _sqlStmt = sql;
  
  TRY {
    // preparation du select ou d'un autre ordre Sql
    if (strncasecmp(sql, "select", 6) == 0) {
      _select = true;
      error =  prepare_select(_db->getDbName(),_bind_dp,_select_dp,(char*)sql);
      if (error)
	throw ExceptionOracle(LibelleErrorOracle(),error);   
    }
    else {
      error =  prepare_sql(_db->getDbName(),_bind_dp,_select_dp,(char*)sql);
      if (error)
	throw ExceptionOracle(LibelleErrorOracle(),error);   
    }
  }
  CATCH_FOR_RETRY {
  }

  // liberation de la memoire de l'ordre precedent 
  // et allocation pour l'ordre courant
  freeMemory(size);
  
  _size = size;
  return true;
}


//  indication du type et de la longueur de la Host Variable pour
//  un no de variable hote donne
//  
//  --> longueur de la zone (uniquement pour les types de la famille 'caractere')
//
//-------------------------------------------------------------------------------
//

bool Request2::bindHostVar(int numberOfColumn,TYPE_ORACLE t,int length) {
  if (numberOfColumn < 0 ||
      numberOfColumn >= _bind_dp->N)
    {
      throw ExceptionSoft(formatMessageSoft("bindHostVar", numberOfColumn));
    }
  int taille = 0;
  switch (t) 
    {
      // STRING est le type 5 --> CHAR et VARCHAR2 sont assimiles a STRING
    case STRING: 
      taille = length+1;  // pour l'\0
      break;
    case INTEGER:
      taille = sizeof(int);
      break;
    case FLOAT:
      taille = sizeof(float);
      break;
    case DOUBLE:
      t = FLOAT;
      taille = sizeof(double);
      break;
    case DATE:
       taille = 15;
       //        taille = 10;
       //        t=STRING;
      break;
    default:
      break;
    }
  
 _bind_dp->L[numberOfColumn] = (long) taille;
 _bind_dp->T[numberOfColumn] = t;

  return true;
}


bool Request2::bindHostVar(const char* nameOfColumn,TYPE_ORACLE t,int length) {
  int numberOfColumn = researchIndex(nameOfColumn,true);
  if (numberOfColumn < 0 ||
      numberOfColumn >= _bind_dp->N)
    {
      throw ExceptionSoft(formatMessageSoft("bindHostVar", nameOfColumn));
    }
  return bindHostVar(numberOfColumn,t,length);
}


//

//
// pour le CHAR : la zone doit etre remplie par des ' ' jusqu'a
//                la taille souhaitee
//
//--------------------------------------------------------------

char* Request2::coerceToCHAR(int numberOfColumn, void* address) {
  char* tmp = NULL;
  if (numberOfColumn < 0 || numberOfColumn >= _bind_dp->N) {
    throw ExceptionSoft(formatMessageSoft("coerceToCHAR", numberOfColumn));
  }
  _bind_dp->T[numberOfColumn] = CHAR_ORACLE;
  switch (_bind_dp->T[numberOfColumn])
    {
    case CHAR_ORACLE:
      //    case CHAR:
      //pel std::string s = (char*) address;
      std::string s((char*) address);
      std::string sb (' ', _bind_dp->L[numberOfColumn] - strlen((char*)address));
      s += sb;
      //
      // permet de gerer le cas ou la variable est declaree en tant que :
      // 
      // char * var = "toto" --->> pour une declaration en oracle CHAR 20
      // 
      // dans ce cas Pb d'alloc car var ne fait que 4 car.
      //
      tmp = new char[_bind_dp->L[numberOfColumn]+1];
      strncpy( tmp , (char*) s.data() ,_bind_dp->L[numberOfColumn]);
      break;
    }  
  return tmp;
}

char* Request2::coerceToCHAR(const char* nameOfColumn,void* address) {
  int numberOfColumn = researchIndex(nameOfColumn,true);
  if (numberOfColumn < 0 || numberOfColumn >= _bind_dp->N)
    throw ExceptionSoft(formatMessageSoft("coerceToCHAR", nameOfColumn));
  return coerceToCHAR(numberOfColumn,address);
}


//
// Traitement des variables hotes 
//
//  --> indique l'adresse de la Host Variable  que l'on peut 
//	eventuellement mettre a null (Indicateur Oracle) pour 
//	un no de variable hote donne
//
//=========================================================================

bool Request2::putHostVar(int numberOfColumn,void* address,IndicatorOracle ind) {
  if (numberOfColumn < 0 || numberOfColumn >= _bind_dp->N) 
    {
      throw ExceptionSoft(formatMessageSoft("putHostVar", numberOfColumn));
    }
  
  if (_bind_dp->T[numberOfColumn] == DATE && ind != INDICATOR_NULL) {
    _bind_dp->L[numberOfColumn] = strlen((char*) address) + 1;
    _bind_dp->T[numberOfColumn] = STRING;
  }

  _bind_dp->V[numberOfColumn] = (char*) address;
  for (int i = 0 ; i < _size ; i++) {
    _bind_dp->I[numberOfColumn][i] = (short) ind;
  }
  return true;  
}


bool Request2::putHostVar(const char* nameOfColumn,void* address,
			    IndicatorOracle ind) {
  int numberOfColumn = researchIndex(nameOfColumn,true);
  if (numberOfColumn < 0 ||
      numberOfColumn >= _bind_dp->N) 
    {
      throw ExceptionSoft(formatMessageSoft("putHostVar", nameOfColumn));
    }
  return putHostVar(numberOfColumn,address,ind);
}



//
// idem avec passage du tableau d'indicateurs
//
//-------------------------------------------

bool Request2::putHostVar(int numberOfColumn,void* address,short* ind) {
  if (numberOfColumn < 0 || numberOfColumn >= _bind_dp->N) 
    {
      throw ExceptionSoft(formatMessageSoft("putHostVar", numberOfColumn));
    }


  //
  //  
  //    ATTENTION   ??????????????????????????????????????
  //
  //  if (_bind_dp->T[numberOfColumn] == DATE && address != NULL) {
  if (_bind_dp->T[numberOfColumn] == DATE && address != NULL) {
    _bind_dp->L[numberOfColumn] = strlen((char*) address) + 1;
    _bind_dp->T[numberOfColumn] = STRING;
  }

  _bind_dp->V[numberOfColumn] = (char*) address;
  
  for (int i = 0 ; i < _size ; i++) {
    _bind_dp->I[numberOfColumn][i] = (short) ind[i];
  }

  return true;  
}


bool Request2::putHostVar(const char* nameOfColumn,void* address,
			    short* ind) {
  int numberOfColumn = researchIndex(nameOfColumn,true);
  if (numberOfColumn < 0 || numberOfColumn >= _bind_dp->N) 
    {
      throw ExceptionSoft(formatMessageSoft("putHostVar", nameOfColumn));
    }
  return putHostVar(numberOfColumn,address,ind);
}






bool Request2::setHostVar(int numberOfColumn,void* address,IndicatorOracle ind,
			    TYPE_ORACLE t,  int length ) {
  return (bool)
    (bindHostVar(numberOfColumn,t,length)
    |
    putHostVar(numberOfColumn,address,ind));
     
}



bool Request2::setHostVar(const char* nameOfColumn,void* address,
			    IndicatorOracle ind, TYPE_ORACLE t,
			    int length ) {
  int numberOfColumn = researchIndex(nameOfColumn,true);
  return setHostVar(numberOfColumn,address,ind,t,length);
}


bool Request2::setHostVar(int numberOfColumn,void* address,
			    short* ind, TYPE_ORACLE t, int length ) {
  return (bool)
    (bindHostVar(numberOfColumn,t,length)
    |
    putHostVar(numberOfColumn,address,ind));
     
}

bool Request2::setHostVar(const char* nameOfColumn,
			    void* address,
			    short* ind,
			    TYPE_ORACLE t,
			    int length) {
  int numberOfColumn = researchIndex(nameOfColumn,true);
  return setHostVar(numberOfColumn,address,ind,t,length);
}


bool Request2::getTuple(int numberOfTuple) {

  if (numberOfTuple < 0 || numberOfTuple >= _numberOfRows) return false;
  _numberOfCurrentTuple = numberOfTuple;
  return assignTuple(_numberOfCurrentTuple);
}

const char* Request2::getTupleColumnName(int id) {

	if (id >= 0 && id < _select_dp->N)
	{
		const int sizeColumn = _select_dp->C[id];
		char* name = new char[sizeColumn];

		std::string nom = "";
		
		for (int i = 0; i < sizeColumn; ++i)
			nom += _select_dp->S[id][i];
//			name[i] = _select_dp->S[id][i]; 
		//return name;
		return nom.c_str();

	}

	return NULL;

}

void Request2::isColumnValid(const char* fct,
			    int numberOfColumn,
			    const char* nameOfColumn
			    ) const {
  if (numberOfColumn < 0 || numberOfColumn >= _select_dp->N) 
    {
      if (nameOfColumn)
	throw ExceptionSoft(formatMessageSoft(fct, nameOfColumn));
      else
	throw ExceptionSoft(formatMessageSoft(fct, numberOfColumn));
	
    }
}

//
// test pour savoir si la valeur d'une colonne est non nulle
// 
//----------------------------------------------------------
//

bool Request2::isColumnNotNull(int numberOfColumn) const {
  isColumnValid("isColumnNotNull", numberOfColumn);
  //23/09/10
  if ( _numberOfCurrentTuple == 0 && _numberOfRows > 0 )
   return _select_dp->I[numberOfColumn][0] == INDICATOR_NULL ? false : true;
  /*
  if (!_numberOfCurrentTuple) 
    throw ExceptionSoft(formatOtherMessageSoft("isColumnNotNull",
					       "Don't Forget the 'getNextTuple'"));
  */
  else
  return _select_dp->I[numberOfColumn][_numberOfCurrentTuple-1] 
    == INDICATOR_NULL ? false : true;


}

bool Request2::isColumnNotNull(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);
  isColumnValid("isColumnNotNull", numberOfColumn, nameOfColumn);
  return isColumnNotNull(numberOfColumn);
}


bool Request2::isColumnNull(int numberOfColumn) const {
  isColumnValid("isColumnNull", numberOfColumn);
  //23/09/10
  if ( _numberOfCurrentTuple == 0 && _numberOfRows > 0 )
   return _select_dp->I[numberOfColumn][0] == INDICATOR_NULL ? false : true;
/*
  if (!_numberOfCurrentTuple) 
    throw ExceptionSoft(formatOtherMessageSoft("isColumnNull",
					       "Don't Forget the 'getNextTuple'"));
*/
  else
   return _select_dp->I[numberOfColumn][_numberOfCurrentTuple-1] 
    == INDICATOR_NULL ? true : false;


}

bool Request2::isColumnNull(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);
  isColumnValid("isColumnNull",  numberOfColumn, nameOfColumn);
  return isColumnNull(numberOfColumn);
}


//
// recuperation d'une ligne (soit du curseur soit en memoire)
//
//-----------------------------------------------------------
//

bool Request2::getNextTuple() {
  if (_nextTupleFromCursor) {
    MT_GUARD( Database2::getMutex() );
    int code = readCursor(_db->getDbName(),_bind_dp,_select_dp);
    switch (code) {
    case 0: 
      return resetTuple();
    case -1:
      close_cursor(_db->getDbName());
      return false;
    default:      
      close_cursor(_db->getDbName());    
      throw ExceptionOracle(LibelleErrorOracle(),code);
    }
  }
  else    
    return assignTuple(_numberOfCurrentTuple++);
}



//
// raz de l'indice de la ligne traitee
//
//--------------------------------------
//
bool Request2::resetTuple() {
  _numberOfCurrentTuple=0;
  return assignTuple(_numberOfCurrentTuple);
}
 

//
//
// creation d'un tuple
//
//------------------------------------------
//

void Request2::createTuple() {
  _numberOfCurrentTuple=0;
  _currentTuple = new char*[_select_dp->N];
  
  for (int i=0;i<_select_dp->N;i++)
   {
    _currentTuple[i] = new char[_select_dp->L[i] + 1];
    //JMA : 28/10/10 : homogeneisation du code
    _currentTuple[i][0]='\0';
   }
  
  assignTuple(_numberOfCurrentTuple);
}
 

//
// assignation d'un tuple
//
//-------------------------------------
//
bool Request2::assignTuple(int numberOfTuple) {
  if (numberOfTuple < 0 ||
      numberOfTuple >= _numberOfRows) return false;

  for (int i=0;i<_select_dp->N;i++) {
    strncpy(_currentTuple[i],
	    (char*) &_select_dp->V[i][ _select_dp->L[i] * numberOfTuple ],
	    _select_dp->L[i]);
    _currentTuple[i][_select_dp->L[i]] = '\0';
  }
  return true;

}


void Request2::purgeTuple() {
  if (_currentTuple == NULL) return;
  for (int j=0;j<_select_dp->N;j++)
    delete []  _currentTuple[j];
  delete [] _currentTuple;
}

   
int Request2::getIntegerValue(int numberOfColumn) {
  //23/09/10 : Ajout d'un boolean cf commentaire ci-dessous
  //-bool is_Column_Not_Null;
  isColumnValid("getIntegerValue", numberOfColumn);
  //23/09/10 : Pour respecter la philo quand il y a un seul item: i.e. quand getNextTuple n'est pas appelé 
  //-if ( _numberOfCurrentTuple == 0 && _numberOfRows > 0 )
  //-    is_Column_Not_Null = (_select_dp->I[numberOfColumn][0] == INDICATOR_NULL ? false : true);
  //-else
  //-    is_Column_Not_Null = isColumnNotNull(numberOfColumn);
  //16/09/10 : test isColumnNotNull 
  if ( !isColumnNotNull(numberOfColumn) )
  //-if (!is_Column_Not_Null)
    majwithZero (_currentTuple[numberOfColumn]); 
  return atoi(_currentTuple[numberOfColumn]);
}

int Request2::getIntegerValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);
  isColumnValid("getIntegerValue", numberOfColumn, nameOfColumn);
  return getIntegerValue(numberOfColumn);
}


//bool Request::getboolValue(int numberOfColumn) const {
bool Request2::getboolValue(int numberOfColumn) {
  //23/09/10 : Ajout d'un boolean cf commentaire ci-dessous
  //-bool is_Column_Not_Null;
  isColumnValid("getboolValue", numberOfColumn); 
  //23/09/10 : Pour respecter la philo quand il y a un seul item: i.e. quand getNextTuple n'est pas appelé 
  //-if ( _numberOfCurrentTuple == 0 && _numberOfRows > 0 )
  //-    is_Column_Not_Null = (_select_dp->I[numberOfColumn][0] == INDICATOR_NULL ? false : true);
  //-else
  //-   is_Column_Not_Null = isColumnNotNull(numberOfColumn);
  if ( !isColumnNotNull(numberOfColumn) )
    majwithZero (_currentTuple[numberOfColumn]);
  return *_currentTuple[numberOfColumn] == 'Y' ? true : false;
}

bool Request2::getboolValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);
  isColumnValid("getboolValue", numberOfColumn, nameOfColumn); 
  return getboolValue(numberOfColumn);
}


//float Request::getFloatValue(int numberOfColumn) const {
float Request2::getFloatValue(int numberOfColumn)  {
  //23/09/10 : Ajout d'un boolean cf commentaire ci-dessous
  //-bool is_Column_Not_Null;
  isColumnValid("getFloatValue", numberOfColumn);
  //23/09/10 : Pour respecter la philo quand il y a un seul item: i.e. quand getNextTuple n'est pas appelé 
  //-if ( _numberOfCurrentTuple == 0 && _numberOfRows > 0 )
  //-    is_Column_Not_Null = (_select_dp->I[numberOfColumn][0] == INDICATOR_NULL ? false : true);
  //-else
  //-    is_Column_Not_Null = isColumnNotNull(numberOfColumn);
  //16/09/10 : test isColumnNotNull 
  if ( !isColumnNotNull(numberOfColumn) )
  //-if (!is_Column_Not_Null)
    majwithZero (_currentTuple[numberOfColumn]); 
  return atof(_currentTuple[numberOfColumn]);
}

float Request2::getFloatValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);  
  isColumnValid("getFloatValue", numberOfColumn, nameOfColumn); 
  return getFloatValue(numberOfColumn);
}

//double Request::getDoubleValue(int numberOfColumn) const {
double Request2::getDoubleValue(int numberOfColumn) {
  //23/09/10 : Ajout d'un boolean cf commentaire ci-dessous
  //-bool is_Column_Not_Null;
  isColumnValid("getDoubleValue", numberOfColumn);
  //23/09/10 : Pour respecter la philo quand il y a un seul item: i.e. quand getNextTuple n'est pas appelé 
  //-if ( _numberOfCurrentTuple == 0 && _numberOfRows > 0 )
  //-    is_Column_Not_Null = (_select_dp->I[numberOfColumn][0] == INDICATOR_NULL ? false : true);
  //-else
  //-    is_Column_Not_Null = isColumnNotNull(numberOfColumn);
  //16/09/10 : test isColumnNotNull 
  if ( !isColumnNotNull(numberOfColumn) )
  //-if (!is_Column_Not_Null)
    majwithZero (_currentTuple[numberOfColumn]); 

	gVide = false;
	// quand _currentTuple[numberOfColumn] est égale "0"
	// la comparaison ne fonctionne pas
	// on passe par une std::string
	/*if(_currentTuple[numberOfColumn] == "0"){
		gVide = true;
	}
	if(_currentTuple[numberOfColumn] == 0){
		gVide = true;
	}*/

	std::string currentTupleStr = std::string(_currentTuple[numberOfColumn]);
	
	if(currentTupleStr == "0"){
		gVide = true;
	}
	 


  return atof(_currentTuple[numberOfColumn]);
}

double Request2::getDoubleValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);  
  isColumnValid("getDoubleValue", numberOfColumn, nameOfColumn); 
  return getDoubleValue(numberOfColumn);
}

std::string Request2::fltToString(int length, double val)
{
    std::string value;
    char valchar2[50];
    std::ostringstream ss;
    ss << "%." << length << "f";
    /* gestion de la chaine vide retournée par un getdouble*/
    if(gVide == true){
            value = "";
            gVide=false;
    }
    else{
            sprintf(valchar2,ss.str().c_str(),val);
            value = valchar2;
    }
    return value;
}
    
const char* Request2::getStringValue(int numberOfColumn)  {
  //23/09/10 : Ajout d'un boolean cf commentaire ci-dessous
  //-bool is_Column_Not_Null;
  isColumnValid("getStringValue", numberOfColumn);
  //23/09/10 : Pour respecter la philo quand il y a un seul item: i.e. quand getNextTuple n'est pas appelé 
  //-if ( _numberOfCurrentTuple == 0 && _numberOfRows > 0 )
  //-    is_Column_Not_Null = (_select_dp->I[numberOfColumn][0] == INDICATOR_NULL ? false : true);
  //-else
  //-    is_Column_Not_Null = isColumnNotNull(numberOfColumn); 
  // Modif CB190697
  truncCharBlank(_currentTuple[numberOfColumn]);
  //16/09/10 : test isColumnNotNull 
  if ( !isColumnNotNull(numberOfColumn) )
  //-if (!is_Column_Not_Null)
    majwithChaineVide (_currentTuple[numberOfColumn]);
  return _currentTuple[numberOfColumn];
}

const char* Request2::getStringValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);  
  isColumnValid("getStringValue", numberOfColumn, nameOfColumn); 
  return getStringValue(numberOfColumn);
}




IndicatorOracle Request2::getIndicatorValue(int numberOfColumn) const {
  if (numberOfColumn < 0 ||
      numberOfColumn >= _select_dp->N) return INDICATOR_INVALID;
  return (IndicatorOracle) _select_dp->I[numberOfColumn][_numberOfCurrentTuple-1];


}

IndicatorOracle Request2::getIndicatorValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);
  return getIndicatorValue(numberOfColumn);
}



short* Request2::getIndicatorAddress(int numberOfColumn) const {
  if (numberOfColumn < 0 ||
      numberOfColumn >= _select_dp->N) return NULL;
  return _select_dp->I[numberOfColumn];


}

short* Request2::getIndicatorAddress(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);
  return getIndicatorAddress(numberOfColumn);
}



//
// recherche du no de colonne associe au nom de la colonne
//
//---------------------------------------------------------
//
    
int Request2::researchIndex(const char* nameOfColumn,bool descriptorBind) {
  SQLDA* sqlda = descriptorBind ? _bind_dp : _select_dp;

  int sizeColumnName = strlen(nameOfColumn);

  int taille = sqlda->M[0];
  char* tmp = new char[taille+1];
  //std::string s = nameOfColumn;
  std::string s(nameOfColumn);
  std::string sb (' ', taille - sizeColumnName);
  s += sb;
  for (int i=0;i<sqlda->N;i++) {
    strncpy(tmp,sqlda->S[i],sqlda->C[i]);
    tmp[sqlda->C[i]]='\0';
    if (!strncasecmp(tmp,s.data(),sqlda->C[i]) 
	&& 
	sqlda->C[i] == sizeColumnName) {
      delete[] tmp;
      return i;
    }
  }
  delete[] tmp;
  return -1;
}

 
void Request2::setSizeOfHostArray(int size) {
  // liberation de la memoire de l'ordre precedent 
  // et allocation pour l'ordre courant
  freeMemory(size);

  _size = size;
}

int Request2::getSizeOfHostArray() {
  return _size;
}

void Request2::truncCharBlank(char*& s) {
  int size = strlen(s);
  int i;
  for (i= --size; i >= 0 && s[i] == ' ';i--);
  s[++i] = '\0';
}

//16/09/10
void Request2::majwithChaineVide(char*& s) {
  s[0] = '\0';
}

//16/09/10
void Request2::majwithZero(char*& s) {
  s[0] = '0';
  s[1] = '\0';
}

//
// liberation des zones datas et indicators dans la Sqlda
//
//-------------------------------------------------------
//
void Request2::freeMemory(int size) {
  int i;
  
  // les zones datas dans le cas des hosts var (ou tab) sont alloues 
  // dans le prg ou sont des var auto 
  // --> on ne les desalloue donc pas ici
  //
  // RAF sur la Sqlda _bind
  
  //   for ( i = 0; i < _bind_dp->N ; i++)
  //     {    
  //       if (_bind_dp->V[i] != (char *) 0)
  // 	free(_bind_dp->V[i]);
  //     }

  for ( i = 0; i < _bind_dp->N ; i++)
    {    
      _bind_dp->I[i] = (short *) realloc(_bind_dp->I[i],
					 sizeof(short)
					 * 
					 size );
    }

  //
  // desallocation de la zone data du _select_dp sur un select 
  // --> gere par le module Pro*C
  //

}

//
// liberation des zones datas et indicators dans la Sqlda
//
//-------------------------------------------------------
//
void Request2::desallocData() {
  //
  // desallocation de la zone data du _select_dp sur un select 
  //

  for (int i = 0; i < _select_dp->N ; i++)
    {    
      if (_select_dp->V[i] != (char *) 0)
 	free(_select_dp->V[i]);
      
      if (_select_dp->I[i] != (short *) 0)
	free(_select_dp->I[i]); 
    }
}


//
// formattage du msg de  
// l'exception Soft
//------------------------------
//
const char* Request2::formatMessageSoft(const char* nameFct,int no) const {
  char tmp[] = "Invalid column number";
  sprintf(ErrorMessage,"\t%s : %s  '%d'",nameFct,tmp,no);
  return ErrorMessage;
}


const char* Request2::formatMessageSoft(const char* nameFct,const char* nameColumn) const {
  char tmp[] = "Invalid column name";
  sprintf(ErrorMessage,"\t%s : %s  '%s'",nameFct,tmp,nameColumn);
  return ErrorMessage;
}


const char* Request2::formatOtherMessageSoft(const char* nameFct,
					    const char* msg) const {
  sprintf(ErrorMessage,"\t%s : '%s'",nameFct,msg);
  return ErrorMessage;
}


//-------------------------------------------------------------------

//
//
//     Classe NON UTILISEE  POUR L'INSTANT
//
//


Relation::Relation(char* name , int nb) 
  : _nameOfRelation(name) , _numberOfColumn(nb) , _current(0)
{
  _columnName = new char*[_numberOfColumn];
  _columnType = new TYPE_ORACLE[_numberOfColumn];
  _length = new int[_numberOfColumn];
  _nullable = new bool[_numberOfColumn];
}
  
Relation::~Relation() 
{
  delete [] _columnName;
  delete [] _columnType;
  delete [] _nullable;
}

bool Relation::operator==(const Relation& r) const {
  return _nameOfRelation == r.getNameOfRelation() ? true : false;
}

bool Relation::operator<(const Relation& r) const {
  return _nameOfRelation < r.getNameOfRelation() ? true : false;
}

void Relation::addColumn(const char* name,TYPE_ORACLE type,int lg,bool nullable) {
  _columnName[_current] = new char[strlen(name)];
  strcpy(_columnName[_current],name);
  _columnType[_current] = type;
  _length[_current] = lg;
  _nullable[_current] = nullable;
  
  _current++;
}



// ------------------------------------------------------------------------
//
// Body of Key classes.
//
// ------------------------------------------------------------------------

DbKey::DbKey( )
  : _dirty( false ) ,
    _modified( false )
{
}


#ifdef DEBUG
void DbKey::display( std::ostream & os ) const
{
  os << " d " << _dirty;
}
#endif



DbKey & DbKey::operator=( const DbKey & dbKey )
{
  _dirty    = dbKey._dirty;
  _modified = dbKey._modified;
  return *this;
}




DbKeyExample::DbKeyExample( const std::string & aValue )
  : DbKey() ,
    _value( aValue ) 

{}


DbKeyExample::DbKeyExample( Request2 * sqlRequest ) : DbKey()
{
  setValue( sqlRequest->getStringValue( "BIDON") );
}


DbKeyExample & DbKeyExample::operator= ( const DbKeyExample & right )
{
  DbKey::operator=( right );
  _value = right.getValue();
  return *this;
}


#ifdef DEBUG
void DbKeyExample::display( std::ostream & os ) const
{
  DbKey::display( os );
  os << " value : " << _value;
}
#endif


void DbKeyExample::updateStaticData( char * aTableValue ) const
{
#ifdef pel
//TODO
  strcpy( aTableValue , getValue() );
#endif
}

// ------------------------------------------------------------------------
// Semantique :
//
// This method bind all the key data of a Example into to sql request.
//
 
void DbKeyExample::setKeyHostVar( Request2 * request , char * aTableValue ) const
{
  request->bindHostVar("BIDON" ,VARCHAR2, 10 );
  request->putHostVar("BIDON", aTableValue );
}

// ------------------------------------------------------------------------
// Semantique :
//
// This method bind all the key data of a Example into to sql request.
//
 
void DbKeyExample::bindAndPutHostVar( Request2 * sqlRequest ) const
{
  sqlRequest->bindHostVar("BIDON",VARCHAR2, 10 );
  sqlRequest->putHostVar("BIDON", (char*) _value.data() );
}

// ------------------------------------------------------------------------
// Semantique :
//

bool DbKeyExample::isValid() const
{
  return getValue().length() != 0;
}


// ------------------------------------------------------------------------
// Semantique :
//

std::string DbKeyExample::getWhereClause(bool forUpdate) const
{
  std::string value(addWhereClause());
  if ( forUpdate ) value += addUpdateClause();
  return value;
}

// ------------------------------------------------------------------------
// Semantique :
//

std::string DbKeyExample::addWhereClause() const
{
  std::string value( " SESC_ORI=:SESC_ORI AND SESC_DES=:SESC_DES ");
  return value;
}

 } 

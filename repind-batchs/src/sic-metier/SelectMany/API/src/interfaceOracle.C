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
#include <sqlca.h>
#include "interfaceOracle.h"
#include "BasicException.h" //. NB_Error
#include "NB_SelectManyClass.h" //. static _outFile

#include <iostream>
#include <stdio.h>
#include <time.h>
#include <locale.h>
#include <sys/times.h>
#include <limits.h>
#include <pthread.h>
const char* twoTask  = "TWO_TASK";

//extern struct sqlca sqlca;

const int ErrorSize = 1024;
char ErrorMessage[ErrorSize];
//
//
// suppression des blancs superflus dans une string
// (by Dor)
//
//-------------------------------------------------

void SqueezeSpaces( const char *s ) {

  char *d ;
  d = (char*) s ;
  while( *s ) {
    *d++ = *s ;
    if( *s == '\t' ) {
      *--d = ' ' ;
      d++ ;
    }
    if( *s++ == ' ' ) {
      while( *s == ' ' ) {
	s++ ;
      }
    }
  }

  *d = '\0';  

} // SqueezeSpaces


//
// recuperation 
// du libelle de l'erreur Oracle
//------------------------------
//
#ifdef DEBUG_TRACE
const char* LibelleErrorOracle( FILE* fo ) {

  char* tmpLabel = new char[sqlca.sqlerrm.sqlerrml+1] ;
  memcpy( tmpLabel, sqlca.sqlerrm.sqlerrmc, sqlca.sqlerrm.sqlerrml );
  tmpLabel[sqlca.sqlerrm.sqlerrml] = 0 ;
  fprintf( fo, "t%d - LibelleErrorOracle gets a sqlca where error = %d and label = '%s' (lg %d)\n",
	   pthread_self(), sqlca.sqlcode, tmpLabel, sqlca.sqlerrm.sqlerrml );
  delete [] tmpLabel ;
#else
const char* LibelleErrorOracle() {
#endif

  
  int _lg =  ErrorSize ;

  int lg = strlen( sqlca.sqlerrm.sqlerrmc );

  //int lg ;
  if( sqlca.sqlerrm.sqlerrml < ErrorSize ) {
    lg = sqlca.sqlerrm.sqlerrml + 1 ;
  } else {
    lg = ErrorSize ;
  }
 
  memset( ErrorMessage, '\0', ErrorSize );
  snprintf( ErrorMessage, lg, "%s", sqlca.sqlerrm.sqlerrmc );

  return ErrorMessage;
}

Indicator::Indicator( const Indicator& pIndicator )
  : _size( pIndicator.getNumberOfRows() ) {

  //. NB_SelectManyClass::_outFile << (void*) this << " - Indicator::constr" << std::endl ;
  try {
    _ind = new short[_size];
    for( int i=0; i<_size; i++ ) {
      _ind[i] = pIndicator[i] ;
    }
  } catch( ... ) {
    throw NB_Error( "Indicator::constr( Indic& ) failed" );
  }

}

Indicator::Indicator( int size, IndicatorOracle ind ) 
  : _size( size ) {

  //. NB_SelectManyClass::_outFile << (void*) this << " - Indicator::constr2" << std::endl ;
  try {
    _ind = new short[_size];
    for( int i=0; i<_size; i++ ) {
      _ind[i] = ind ;
    }
  } catch( ... ) {
    throw NB_Error( "Indicator::constr( int, IndicOracle) failed" );
  }

}

short* Indicator::operator&() {
  return _ind;
}


Indicator::~Indicator() {
  //. NB_SelectManyClass::_outFile << (void*) this << " - Indicator::destr" << std::endl ;
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

void Indicator::init( IndicatorOracle ind ) {
  for( int i=0; i<_size; i++ ) {
    _ind[i] = ind ;
  }
}

Indicator& Indicator::operator=( const Indicator& pIndicator ) {
  if( _ind ) {
    delete [] _ind ;
  }
  _size = pIndicator.getNumberOfRows();

  try {
    _ind = new short[_size];
    for( int i=0; i<_size; i++ ) {
      _ind[i] = pIndicator[i] ;
    }
  } catch( ... ) {
    throw NB_Error( "Indicator::operator= failed" );
  }
  
  return *this;
 
}


///////////////////////////////////////////////////////////////
//
// clas Database : gestion des connexions deconnexions commit
//                 et rollback
//
///////////////////////////////////////////////////////////////
 
#ifdef DEBUG_TRACE
Database::Database( FILE* of, const char* user, const char* passwd , const char* dbString ) 
#else
Database::Database( const char* user, const char* passwd , const char* dbString ) 
#endif
  : _user( (char*) user), _passwd( (char*) passwd ),
    _dbName(NULL), _dbString((char*) dbString ) {

  //. NB_SelectManyClass::_outFile << (void*) this << " - Database::constr( '" << user << "/" << passwd << "@" << dbString << "' )" << std::endl ;

#ifdef DEBUG_TRACE
  _outFile = of ;
  fprintf( _outFile, "t%d - Database::constr - begin\n", pthread_self() );
  fprintf( _outFile, "t%d - Database::constr( '%s/%s@%s' )\n", pthread_self(), user, passwd, dbString );
  fflush( _outFile );
#endif

  // init du contexte
  initDbNameAndDbString();
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - Database::constr - dbName '%s' and dbString '%s'\n",
	   pthread_self(), _dbName, _dbString );
  fflush( _outFile );
#endif

  dinb_setSqlContext( lrand48() );  
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - Database::constr - before dinb_oracle_connect\n", pthread_self() );
  fflush( _outFile );
  int error = dinb_oracle_connect( _user,
				   _passwd,
				   _dbName,
				   _dbString,
				   //. (void**)&_myContext,
				   //&_sqlca,
				   of );
  fprintf( _outFile, "t%d - Database::constr - after dinb_oracle_connect\n",
	   pthread_self() );
  fflush( _outFile );
#else
  int error = dinb_oracle_connect( _user,
				   _passwd,
				   _dbName,
				   _dbString
				   //. (void**)&_myContext,
				   //&_sqlca
				   );
#endif
  _context = dinb_getSqlContext() ;
  if( error ) {
    _isConnected = False ;
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - Error in Database::constr '%s' err '%d'\n",
	     pthread_self(), LibelleErrorOracle(/*&_sqlca,*/ _outFile), error );
    fflush( _outFile );
    throw NB_Error( LibelleErrorOracle(/*&_sqlca,*/ _outFile), error );
#else
    throw ExceptionOracle( LibelleErrorOracle(/*&_sqlca*/), error );
#endif
  } else {
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - No error in Database::constr\n", pthread_self() );
    fflush( _outFile );
#endif
    _isConnected = True ;
  }
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - Database::constr - end\n", pthread_self() );
  fflush( _outFile );
#endif
}


Database::~Database() {
  //. NB_SelectManyClass::_outFile << (void*) this << " - Database::destr" << std::endl ;
  if( _dbName ) {
    delete [] _dbName ;
    _dbName = NULL ;
  }
}


void Database::initDbNameAndDbString() {
  // nom aleatoire en Hexa
  long l = lrand48() ;
  try {
    _dbName = new char[SIZE_LONG_HEXA+1];
    snprintf( _dbName, SIZE_LONG_HEXA+1, "%.*X", SIZE_LONG_HEXA, l );
  } catch( ... ) {
    throw NB_Error( "Database::initDbNameAndDbString failed" );
  }

  if( !_dbString ) {
    _dbString = getenv( twoTask );
  }
}

char* Database::getDbName() {
  return _dbName;
}

void Database::reconnect() {  
 dinb_setSqlContext( 0 );
#ifdef DEBUG_TRACE
  int error = dinb_oracle_connect( _user,_passwd,_dbName,_dbString,
				   //. (void**)&_myContext,
				   //&_sqlca,
				   _outFile );
#else
  int error = dinb_oracle_connect( _user, _passwd, _dbName, _dbString
				   //. (void**)&_myContext,
				   //&_sqlca
				   );
#endif
  _context = dinb_getSqlContext() ;
  if( error ) {
    _isConnected = False ;
    //. throw ExceptionOracle( LibelleErrorOracle(&_sqlca), error );
#ifdef DEBUG_TRACE
    throw NB_Error( LibelleErrorOracle(/*&_sqlca,*/ _outFile), error );
#else
    throw NB_Error( LibelleErrorOracle(/*&_sqlca*/), error );
#endif
  } else {
    _isConnected = True ;
  }
}

Booleen Database::disconnect() const {
  if( dinb_disconnect( _dbName
		       //. (void**)&_myContext,
		       //&_sqlca
		       ) ) {
    throw NB_Error( "Error in disconnect Pro*C function" );
  }
  return True ;
}

Request* Database::newRequest() {
  try {
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - Database::newRequest - get in\n", pthread_self() );
    fflush( _outFile );
    return new Request( this, _outFile );
#else
    return new Request( this );
#endif
  } catch( std::bad_alloc e ) {
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - new Request failed in Database::newRequest : '%s'\n",
	     pthread_self(), e.what() );
    fflush( _outFile );
#endif
    throw NB_Error( "Database::newRequest failed in new Request", 99005 );
  } catch( NB_Error e ) {
    throw ;
  } catch( ... ) {
    throw NB_Error( "Database::newRequest failed", 99005 );
  }
}


//-----------------------------------
//
//
//        Class Request
//
//
//-----------------------------------


#ifdef DEBUG_TRACE
Request::Request( Database* db, FILE* of ) : 
#else
Request::Request( Database* db ) : 
#endif
  _db(db) ,
  _select_dp(NULL) , 
  _type_array( NULL ),
  _nextTupleFromCursor(False) ,
  _numberOfRows(0) ,
  //.   _numberOfCurrentTuple(0) ,
  _numberOfCurrentTuple( -1 ),
  _currentTuple(NULL) { 

  int error = 0;
#ifdef DEBUG_TRACE
  fprintf( of, "t%d - Request::constr - get in\n", pthread_self() );
  fflush( of );
  error = dinb_alloc_descriptor( &_select_dp, of ); //. , _db->getSQLContext() );
  fprintf( of, "t%d - Request::constr - after dinb_alloc_descriptor\n", pthread_self() );
  fflush( of );
#else
  error = dinb_alloc_descriptor( &_select_dp ); //. , _db->getSQLContext() );
#endif

  if( error ) {
    throw NB_Error( "Probleme d'allocation memoire : fonction ' dinb_alloc_descriptors'", 99005 );
  }
#ifdef DEBUG_TRACE
  fprintf( of, "t%d - Request::constr - get out\n", pthread_self() );
  fflush( of );
#endif
}

Request::~Request() {

  // delete des tuples
  purgeTuple();
  // desallocation des sqlda
  dinb_desalloc_descriptors( _select_dp ); //. , _db->getSQLContext() );
  if( _type_array ) {
    //. delete[]_type_array ; allocated by C language in dynamicsql.pc
    free( _type_array );
  }
}
 
Booleen Request::execute() {

  static long nbExecToSendFail = 0 ;
  nbExecToSendFail++ ;

  _db->setContext();
  
  // execution d'un select ou d'un autre ordre Sql
  try {
    if( _select ) {
      
      _nextTupleFromCursor = False;
      purgeTuple();

      desallocData();

#ifdef DEBUG_TRACE      
      int error = dinb_exec_select( _db->getDbName(),
				    _select_dp,
				    &_type_array,
				    //. _db->getSQLContext(),
				    _db->getDateFormatSize(),
				    //&_sqlca,
				    _db->_outFile );
#else
      int error = dinb_exec_select( _db->getDbName(),
				    _select_dp,
				    &_type_array,
				    //. _db->getSQLContext(),
				    _db->getDateFormatSize()
				    //&_sqlca
				    );
#endif
      if( error ) {
	if( error == 99 ) {
	  throw NB_Error( "Number of returned columns greater than MAX_ITEM",
			  99002 ); //. MAX_ITEM defined in pro*C file
	} else {
#ifdef DEBUG_TRACE
	  const char* tmp = LibelleErrorOracle( /*&_sqlca,*/ _db->_outFile );
	  fprintf( _db->_outFile,
		   "t%d - Request::execute sends error after dinb_exec_select '%s' or '%s' with n° %d\n",
		   pthread_self(), tmp, sqlca.sqlerrm.sqlerrmc, error );
	  throw ExceptionOracle( LibelleErrorOracle(/*&_sqlca,*/ _db->_outFile), error );
#else
	  throw ExceptionOracle( LibelleErrorOracle(/*&_sqlca*/), error );
#endif
	}
      }
      _numberOfRows = sqlca.sqlerrd[2];
      //_numberOfRows = 0;

#ifdef DEBUG_TRACE
      fprintf( _db->_outFile, "t%d - Request::execute - calls createTuple(), numberOfRows: %d\n",
	       pthread_self(), _numberOfRows );
#endif
      createTuple();
    }
    else {
      int error = dinb_exec_sql( _db->getDbName(),
				 _select_dp,
				 _size
				 //. _db->getSQLContext(),
				 //&_sqlca
				 );
      if( error ) {
#ifdef DEBUG_TRACE
	fprintf( _db->_outFile, "Request::execute sends error after dinb_exec_sql\n" );
	throw ExceptionOracle( LibelleErrorOracle(/*&_sqlca,*/ _db->_outFile ), error );
#else
	throw ExceptionOracle( LibelleErrorOracle(/*&_sqlca*/), error );
#endif
      }
      _numberOfRows = sqlca.sqlerrd[2] ;
      //_numberOfRows = 0 ;
    }

  } catch( ExceptionOracle &e ) {

    if( sqlca.sqlcode == EXCEPTION_KILLED_SESSION
	|| sqlca.sqlcode == EXCEPTION_BAD_SESSION_ID
	|| sqlca.sqlcode == EXCEPTION_NOT_LOGGED_ON
	|| sqlca.sqlcode == EXCEPTION_CANCELLED_BY_USER
	|| sqlca.sqlcode == EXCEPTION_INIT_OR_SHUTDOWN_IN_PROGRESS
	|| sqlca.sqlcode == EXCEPTION_ORACLE_UNAVAILABLE
	|| sqlca.sqlcode == EXCEPTION_IMMEDIATE_SHUTDOWN_IN_PROGRESS
	|| sqlca.sqlcode == EXCEPTION_ORACLE_INSTANCE_TERMINATED
	|| sqlca.sqlcode == EXCEPTION_MAX_CONNECT_TIME_REACHED
	|| sqlca.sqlcode == EXCEPTION_FATAL_COMMUNICATION_PROTOCOL
	|| sqlca.sqlcode == EXCEPTION_COMM_CHANNEL_BREAK
	|| sqlca.sqlcode == EXCEPTION_END_OF_FILE_ON_COMMUNICATION_CHANNEL
	|| sqlca.sqlcode == EXCEPTION_EOF_ON_COMM_CHANNEL
	|| sqlca.sqlcode == EXCEPTION_TNS_UNABLE_TO_CONNECT
	|| sqlca.sqlcode == EXCEPTION_TNS_NO_LISTENER
	|| sqlca.sqlcode == EXCEPTION_TNS_PACKET_WRITE_FAILURE
	|| sqlca.sqlcode == EXCEPTION_CNX_RESET_BY_PEER
	|| sqlca.sqlcode == EXCEPTION_NO_MORE_DATA_TO_READ ) {
      try {
	_db->reconnect() ;
      } catch( NB_Error &e1 ) {
	throw ;
      }
      throw ;
    } else {
    
  throw NB_Error( e.why(), e.getErrorOracle() );
    }
  }

  return True ;
}

//
//  preparation de l'ordre Sql avec variables hotes
//
//
//--------------------------------------------------------
//
Booleen Request::parse( const char* sql, int size ) {

#ifdef DEBUG_TRACE
  fprintf( _db->_outFile, "t%d - Request::parse - get in with '%s'\n",
	   pthread_self(), sql );
#endif

  static long nbExecToSendFail = 0 ;
  nbExecToSendFail++ ;

  _db->setContext();
#ifdef DEBUG_TRACE
  fprintf( _db->_outFile, "t%d - Request::parse - ctx set\n", pthread_self() );
#endif

  _select = False ;
  int error ;

  //SqueezeSpaces(sql);
#ifdef DEBUG_TRACE
  fprintf( _db->_outFile, "t%d - Request::parse - spaces squeezed\n", pthread_self() );
#endif

  _sqlStmt = sql ;
  
  try {
    // preparation du select ou d'un autre ordre Sql
    if( strncasecmp( sql, "select", 6 ) == 0 ) {
      _select = True ;

#ifdef DEBUG_TRACE
      fprintf( _db->_outFile, "t%d - Request::parse - call dinb_prepare_select\n", pthread_self() );
      fflush( _db->_outFile );
      error =  dinb_prepare_select( _db->getDbName(),
				    _select_dp,
				    (char*)sql,
				    //. _db->getSQLContext(),
				    //&_sqlca,
				    _db->_outFile );
      fprintf( _db->_outFile, "t%d - Request::parse - after dinb_prepare_select - error %d\n",
	       pthread_self(), error );
      fflush( _db->_outFile );
#else
      error =  dinb_prepare_select( _db->getDbName(),
				    _select_dp,
				    (char*)sql
				    //. _db->getSQLContext(),
				    //&_sqlca
				    );
#endif
      if( error ) {
#ifdef DEBUG_TRACE
	fprintf( _db->_outFile, "Request::parse - error %d\n", error );
	throw ExceptionOracle( LibelleErrorOracle(/*&_sqlca,*/ _db->_outFile ), error );
#else
	throw ExceptionOracle( LibelleErrorOracle(/*&_sqlca*/), error );
#endif
      }
      if( ( nbExecToSendFail > 4 ) && ( nbExecToSendFail < 5 ) ) {
#ifdef DEBUG_TRACE
	fprintf( _db->_outFile, "set Request::parse in error\n" );
#endif
//	sqlca.sqlcode = -1012 ;
//	throw ExceptionOracle( "set error manually", sqlca.sqlcode );
      } else {
	//. NB_SelectManyClass::_outFile << "no error in parse" << std::endl ;
      }
    }
    else {
#ifdef DEBUG_TRACE
      fprintf( _db->_outFile, "t%d - Request::parse - call dinb_prepare_sql on '%s'\n",
	       pthread_self(), sql );
      fflush( _db->_outFile );
      error =  dinb_prepare_sql( _db->getDbName(),
				 _select_dp,
				 (char*)sql,
				 //. _db->getSQLContext(),
				 //&_sqlca,
				 _db->_outFile );
      fprintf( _db->_outFile, "t%d - Request::parse - dinb_prepare_sql returns %d\n",
	       pthread_self(), error );
      fflush( _db->_outFile );
#else
      error =  dinb_prepare_sql( _db->getDbName(),
				 _select_dp,
				 (char*)sql
				 //. _db->getSQLContext(),
				 //&_sqlca
				 );
#endif

      if( error ) {
#ifdef DEBUG_TRACE
	throw ExceptionOracle( LibelleErrorOracle(/*&_sqlca,*/ _db->_outFile ), error );
#else
	throw ExceptionOracle( LibelleErrorOracle(/*&_sqlca*/), error );
#endif
      }
    }
    }
    // catch( ExceptionDB EXCEPTION ) {  }
    catch( ExceptionOracle &e ) {
    if( sqlca.sqlcode == EXCEPTION_KILLED_SESSION
	|| sqlca.sqlcode == EXCEPTION_BAD_SESSION_ID
	|| sqlca.sqlcode == EXCEPTION_NOT_LOGGED_ON
	|| sqlca.sqlcode == EXCEPTION_CANCELLED_BY_USER
	|| sqlca.sqlcode == EXCEPTION_INIT_OR_SHUTDOWN_IN_PROGRESS
	|| sqlca.sqlcode == EXCEPTION_ORACLE_UNAVAILABLE
	|| sqlca.sqlcode == EXCEPTION_IMMEDIATE_SHUTDOWN_IN_PROGRESS
	|| sqlca.sqlcode == EXCEPTION_ORACLE_INSTANCE_TERMINATED
	|| sqlca.sqlcode == EXCEPTION_MAX_CONNECT_TIME_REACHED
	|| sqlca.sqlcode == EXCEPTION_FATAL_COMMUNICATION_PROTOCOL
	|| sqlca.sqlcode == EXCEPTION_COMM_CHANNEL_BREAK
	|| sqlca.sqlcode == EXCEPTION_END_OF_FILE_ON_COMMUNICATION_CHANNEL
	|| sqlca.sqlcode == EXCEPTION_EOF_ON_COMM_CHANNEL
	|| sqlca.sqlcode == EXCEPTION_TNS_UNABLE_TO_CONNECT
	|| sqlca.sqlcode == EXCEPTION_TNS_NO_LISTENER
	|| sqlca.sqlcode == EXCEPTION_TNS_PACKET_WRITE_FAILURE
	|| sqlca.sqlcode == EXCEPTION_CNX_RESET_BY_PEER
	|| sqlca.sqlcode == EXCEPTION_NO_MORE_DATA_TO_READ ) {
      try {
#ifdef DEBUG_TRACE
	fprintf( _db->_outFile, "parse calls reconnect\n" );
#endif
	_db->reconnect();
      } catch( NB_Error &e1 ) {
	throw ; //. throws NB_Error
      }
      throw ; //. throws ExceptionOracle
    } else {
      throw NB_Error( e.why(), e.getErrorOracle() );
    }
  }
  
  _size = size ;
  return True ;
}

/*
commented when fix bug NULL value because I do not know
if _numberOfCurrentType = numberOfTuple
or _numberOfCurrentType = numberOfTuple - 1
????
Booleen Request::getTuple(int numberOfTuple) {

  if (numberOfTuple < 0 || numberOfTuple >= _numberOfRows) return False;
  _numberOfCurrentTuple = numberOfTuple;
  return assignTuple(_numberOfCurrentTuple);
}
*/

void Request::isColumnValid( const char* fct,
			     int numberOfColumn,
			     const char* nameOfColumn
			     ) const {
  //. NB_SelectManyClass::_outFile << "Request::isColumnValid check " 
  //. 	    << numberOfColumn << " < "
  //. 	    << _select_dp->N << std::endl ;
  if( numberOfColumn < 0 || numberOfColumn >= _select_dp->N ) {
    if( nameOfColumn ) {
      throw ExceptionSoft( formatMessageSoft( fct, nameOfColumn ) );
    } else {
      throw ExceptionSoft( formatMessageSoft( fct, numberOfColumn ) );
    }
  }
}

//
// test pour savoir si la valeur d'une colonne est non nulle
// 
//----------------------------------------------------------
//

Booleen Request::isColumnNotNull(int numberOfColumn) const {

  isColumnValid("isColumnNotNull", numberOfColumn);
  //. if (!_numberOfCurrentTuple) { 
  if( _numberOfCurrentTuple == -1 ) { 
    //. NB_SelectManyClass::_outFile << "_numberOfCurrentTuple = " << _numberOfCurrentTuple
    //.           << " so throw error" << std::endl ;
    throw ExceptionSoft(formatOtherMessageSoft("isColumnNotNull",
					       "Don't Forget the 'getNextTuple'"));
  }
  //. return _select_dp->I[numberOfColumn][_numberOfCurrentTuple-1]

  /*
  NB_SelectManyClass::_outFile << "Request::isColumnNotNull get _select_dp->I[" << numberOfColumn << "]["
	    << _numberOfCurrentTuple << "] at @" 
	    << (void*) &( _select_dp->I[numberOfColumn][_numberOfCurrentTuple] )
	    << std::endl ;
  */

  return ( (short) _select_dp->I[numberOfColumn][_numberOfCurrentTuple] 
    == (short) INDICATOR_NULL ? False : True );
  

}

//
// recuperation d'une ligne (soit du curseur soit en memoire)
//
//-----------------------------------------------------------
//

Booleen Request::getNextTuple() {
  //. NB_SelectManyClass::_outFile << "Request::getNextTuple" << std::endl ;
  if( _nextTupleFromCursor ) {
    int code = dinb_readCursor( _db->getDbName(),
				_select_dp
				//. _db->getSQLContext(),
				//&_sqlca
				);
#ifdef DEBUG_TRACE
      fprintf( _db->_outFile,
	       "t%d - Request::getNextTuple : dinb_readCursor returns %d\n",
	       pthread_self(), code );
      fflush( _db->_outFile );
#endif
    switch( code ) {
    case 0:
      //. NB_SelectManyClass::_outFile << "Request::getNextTuple received 0 from dinb_readCursor"
      //. 		<< " so return resetTuple()" << std::endl ;
    //. return resetTuple();
    case -1:
#ifdef DEBUG_TRACE
      fprintf( _db->_outFile,
	       "t%d - Request::getNextTuple received 0 from dinb_readCursor so close\n",
	       pthread_self() );
      dinb_close_cursor( _db->getDbName(),
			 //. _db->getSQLContext(),
			 //&_sqlca,
			 _db->_outFile );
#else
      dinb_close_cursor( _db->getDbName()
			 //. _db->getSQLContext(),
			 //&_sqlca//
			 );
#endif
      return False ;
    default:      
#ifdef DEBUG_TRACE
      fprintf( _db->_outFile,
	       "t%d - Request::getNextTuple received 'default' from dinb_readCursor so close\n",
	       pthread_self() );
      dinb_close_cursor( _db->getDbName(),
			 //. _db->getSQLContext(),
			 //&_sqlca,
			 _db->_outFile );
#else
      dinb_close_cursor( _db->getDbName()
			 //. _db->getSQLContext(),
			 //&_sqlca
			 );
#endif

      //. throw ExceptionOracle( LibelleErrorOracle(&_sqlca), code );
      //. NB_SelectManyClass::_outFile << "Request::getNextTuple received X from dinb_readCursor"
      //. 	<< " so throw error" << std::endl ;
#ifdef DEBUG_TRACE
      throw NB_Error( LibelleErrorOracle(/*&_sqlca,*/ _db->_outFile ), code );
#else
      throw NB_Error( LibelleErrorOracle(/*&_sqlca*/), code );
#endif
    }
  } else {

#ifdef DEBUG_TRACE
      fprintf( _db->_outFile,
	       "t%d - Request::getNextTuple calls assignTuple\n", pthread_self() );
      fflush( _db->_outFile );
#endif
    return assignTuple( ++_numberOfCurrentTuple );
    /*
    Booleen reess = assignTuple( ++_numberOfCurrentTuple );
    NB_SelectManyClass::_outFile << "\tgetNextTuple end" << std::endl ;
    return reess ;
    */
  }
}

//
// raz de l'indice de la ligne traitee
//
//--------------------------------------
//
Booleen Request::resetTuple() {
  _numberOfCurrentTuple=0;
  return assignTuple(_numberOfCurrentTuple);
}
 

//
//
// creation d'un tuple
//
//------------------------------------------
//

void Request::createTuple() {

 _numberOfCurrentTuple=0;
 //. _numberOfCurrentTuple=1; //. assigned to 1 because 0 throw error in isColumnNotNull

 try {
   _currentTuple = new char*[_select_dp->N];
  
   for( int i=0; i<_select_dp->N; i++ ) {
     _currentTuple[i] = new char[_select_dp->L[i] + 1];
   }
  
   //. NB_SelectManyClass::_outFile << "Request::createTuple calls assignTuple( "
   //. 	     << _numberOfCurrentTuple << ")" << std::endl ;
   assignTuple( _numberOfCurrentTuple );

 } catch( ... ) {
   throw NB_Error( "Request::createTuple failed" );
 }

}
 

//
// assignation d'un tuple
//
//-------------------------------------
//
Booleen Request::assignTuple( int numberOfTuple ) {

  if( numberOfTuple < 0 ||
      numberOfTuple >= _numberOfRows ) {
    //. NB_SelectManyClass::_outFile << "\t****** Request::assignTuple return False" << std::endl ;
    return False ;
  }

  for( int i=0; i<_select_dp->N; i++ ) {
    //. NB_SelectManyClass::_outFile << "Request::assignTuple calls isColumNotNull for col " << ( i+1) << std::endl ;
    if( isColumnNotNull(i) ) {
      
      //. NB_SelectManyClass::_outFile << "col " << i << " tuple " << numberOfTuple << " : not null" << std::endl ;

      strncpy( _currentTuple[i],
	       (char*) &_select_dp->V[i][ _select_dp->L[i] * numberOfTuple ],
	       _select_dp->L[i]);
      _currentTuple[i][_select_dp->L[i]] = '\0';
      if (i>40) {
	//NB_SelectManyClass::_outFile << "Thread:"<< pthread_self() <<" col " << i << " tuple " << numberOfTuple << " : " << _select_dp->L[i] << " chars" << std::endl
	//	  << "\t=>\t'" << _currentTuple[i] << "'" << std::endl
	//	  << "\t=>\t'" << _select_dp->V[i] << "'" << std::endl;
	//	NB_SelectManyClass::_outFile << "Thread:"<< pthread_self() <<"_currentTuple address: " << &_currentTuple << " _select_dp: " << _select_dp << "_select_dp->V[" << i <<"]: " << &_select_dp->V[i]<<std::endl;
	
      } 
    } else {
      //. NB_SelectManyClass::_outFile << "col " << i << " tuple " << numberOfTuple << " : null" << std::endl ;
      /*
      strncpy( _currentTuple[i],
	       (char*) &_select_dp->V[i][ _select_dp->L[i] * numberOfTuple ],
	       _select_dp->L[i]);
      */
      //. _currentTuple[i][_select_dp->L[i]] = '\0';
      _currentTuple[i][0] = '\0';
      //. NB_SelectManyClass::_outFile << "col " << i << " tuple " << numberOfTuple << " : "
      //.           << _select_dp->L[i] << " chars" << std::endl
      //. 		<< "\t=>\t'" << _currentTuple[i] << "'" << std::endl ;
    }

    /*
    strncpy( _currentTuple[i],
	     (char*) &_select_dp->V[i][ _select_dp->L[i] * numberOfTuple ],
	     _select_dp->L[i]);
    _currentTuple[i][_select_dp->L[i]] = '\0';
    NB_SelectManyClass::_outFile << "col " << i << " tuple " << numberOfTuple << " : " << _select_dp->L[i] << " chars" << std::endl
	      << "\t=>\t'" << _currentTuple[i] << "'" << std::endl ;
    */
  }

  return True ;

}


void Request::purgeTuple() {
  if (_currentTuple == NULL) return;
  for (int j=0;j<_select_dp->N;j++)
    delete [] _currentTuple[j];
  delete [] _currentTuple;
}

  
int Request::getIntegerValue(int numberOfColumn) {
  isColumnValid("getIntegerValue", numberOfColumn); 
  return atoi(_currentTuple[numberOfColumn]);
}

int Request::getIntegerValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);
  isColumnValid("getIntegerValue", numberOfColumn, nameOfColumn);
  return getIntegerValue(numberOfColumn);
}


Booleen Request::getBooleenValue(int numberOfColumn) const {
  isColumnValid("getBooleenValue", numberOfColumn); 
  return *_currentTuple[numberOfColumn] == 'Y' ? True : False;
}

Booleen Request::getBooleenValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);
  isColumnValid("getBooleenValue", numberOfColumn, nameOfColumn); 
  return getBooleenValue(numberOfColumn);
}


float Request::getFloatValue(int numberOfColumn) const {
  isColumnValid("getFloatValue", numberOfColumn); 
  return atof(_currentTuple[numberOfColumn]);
}

float Request::getFloatValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);  
  isColumnValid("getFloatValue", numberOfColumn, nameOfColumn); 
  return getFloatValue(numberOfColumn);
}

double Request::getDoubleValue(int numberOfColumn) const {
  isColumnValid("getDoubleValue", numberOfColumn); 
  return atof(_currentTuple[numberOfColumn]);
}

double Request::getDoubleValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);  
  isColumnValid("getDoubleValue", numberOfColumn, nameOfColumn); 
  return getDoubleValue(numberOfColumn);
}
    
const char* Request::getStringValue(int numberOfColumn)  {
  isColumnValid("getStringValue", numberOfColumn); 
  if( _currentTuple[numberOfColumn][0] == '\0' ) {
    return NULL ;
  } else {
    truncCharBlank(_currentTuple[numberOfColumn]);
    return _currentTuple[numberOfColumn];
  }
}

const char* Request::getStringValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);  
  isColumnValid("getStringValue", numberOfColumn, nameOfColumn); 
  return getStringValue(numberOfColumn);
}

const int Request::isNullValue( int numberOfColumn ) {
  //. NB_SelectManyClass::_outFile << "Request::isNullValue returns " << (_currentTuple[numberOfColumn][0] == 0)
  //. 	    << std::endl ;
  return ( _currentTuple[numberOfColumn][0] == '\0' );
}

const char* Request::getTupleColumnName( int numberOfColumn ) {

  isColumnValid( "getTupleColumnName",  numberOfColumn );
  try {
    char* tmp = new char[ _select_dp->M[0] ];
    strncpy( tmp, _select_dp->S[numberOfColumn], _select_dp->C[numberOfColumn] );
    tmp[ _select_dp->C[numberOfColumn] ] = '\0' ;
    return tmp ;
  } catch( ... ) {
    throw NB_Error( "Request::getTupleColumnName failed" );
  }

}

TYPE_ORACLE Request::getTupleColumnTypeAsOracleType(int numberOfColumn) {
  isColumnValid( "getTupleColumnType", numberOfColumn );
  TYPE_ORACLE typeOracle = (TYPE_ORACLE) _type_array[numberOfColumn];
  return typeOracle ;
}

int Request::getTupleColumnSize(int numberOfColumn)  {
  isColumnValid("getTupleColumnSize", numberOfColumn);
  return _select_dp->L[numberOfColumn];
}

int Request::getTupleColumnSize(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);  
  isColumnValid("getTupleColumnType", numberOfColumn, nameOfColumn); 
  return getTupleColumnSize(numberOfColumn);
}


IndicatorOracle Request::getIndicatorValue(int numberOfColumn) const {
  if (numberOfColumn < 0 ||
      numberOfColumn >= _select_dp->N) return INDICATOR_INVALID;
  return (IndicatorOracle) _select_dp->I[numberOfColumn][_numberOfCurrentTuple-1];


}

IndicatorOracle Request::getIndicatorValue(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);
  return getIndicatorValue(numberOfColumn);
}



short* Request::getIndicatorAddress(int numberOfColumn) const {
  if (numberOfColumn < 0 ||
      numberOfColumn >= _select_dp->N) return NULL;
  return _select_dp->I[numberOfColumn];


}

short* Request::getIndicatorAddress(const char* nameOfColumn)  {
  int numberOfColumn = researchIndex(nameOfColumn);
  return getIndicatorAddress(numberOfColumn);
}



//
// recherche du no de colonne associe au nom de la colonne
//
//---------------------------------------------------------
//
    
int Request::researchIndex( const char* nameOfColumn, Booleen descriptorBind ) {

  SQLDA* sqlda ;
  if( descriptorBind ) {
    printf( "ERROR : look for the bind structure !\n" );
    return -1 ;
  } else {
    sqlda = _select_dp ;
  }

  int sizeColumnName = strlen( nameOfColumn );

  int taille = sqlda->M[0] ;
  try {
    char* tmp = new char[taille+1] ;
    std::string s = nameOfColumn;
    std::string sb (' ', taille - sizeColumnName);
    s += sb;

    for( int i=0; i<sqlda->N; i++ ) {
      strncpy( tmp, sqlda->S[i], sqlda->C[i] );
      tmp[ sqlda->C[i] ] = '\0' ;
      if( !strncasecmp( tmp, s.data(), sqlda->C[i] ) 
	  && 
	  sqlda->C[i] == sizeColumnName ) {
	delete tmp ;
	return i ;
      }
    }

    delete tmp;
    return -1 ;
  } catch( ... ) {
    throw NB_Error( "Request::researchIndex failed" );
  }

}

void Request::truncCharBlank( char*& s ) {
  int size = strlen(s);
  int i;
  for( i = --size; i >= 0 && s[i] == ' '; i-- );
  s[++i] = '\0' ;
}


//
// liberation des zones datas et indicators dans la Sqlda
//
//-------------------------------------------------------
//
void Request::desallocData() {
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
const char* Request::formatMessageSoft(const char* nameFct,int no) const {
  char tmp[] = "Invalid column number";
  sprintf(ErrorMessage,"\t%s : %s  '%d'",nameFct,tmp,no);
  return ErrorMessage;
}


const char* Request::formatMessageSoft(const char* nameFct,const char* nameColumn) const {
  char tmp[] = "Invalid column name";
  sprintf(ErrorMessage,"\t%s : %s  '%s'",nameFct,tmp,nameColumn);
  return ErrorMessage;
}


const char* Request::formatOtherMessageSoft(const char* nameFct,
					    const char* msg) const {
  sprintf(ErrorMessage,"\t%s : '%s'",nameFct,msg);
  return ErrorMessage;
}

void Request::trace() {
#ifdef DEBUG_TRACE
  fprintf( _db->_outFile, "\n\n" );
  int nbRow = getNumberOfRows() ;
  int nbCol = getNumberOfColumns() ;

  //. column names
  for( int i=0; i<nbCol; i++ ) {
    fprintf( _db->_outFile,  "col %d name = '%s'\n", i, _select_dp->S[i] );
  }

  //. record values
  for( int i=0; i<nbRow; i++ ) {
    for( int j=0; j<nbCol; j++ ) {
      fprintf( _db->_outFile, "thread %d [ %d , %d ] = '%s'\n", pthread_self(), i, j, _select_dp->V[j] );
    }
  }

  fprintf( _db->_outFile, "\n\n" );
#endif
}

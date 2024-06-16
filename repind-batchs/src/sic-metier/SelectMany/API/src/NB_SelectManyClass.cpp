//============================================================
// File Information
//============================================================
//    File:     NB_SelectManyObject.cpp
//  Author:     Mireille HOT
//    Date:     01/02/2006
// Purpose:     implements NB_SelectManyObject
//============================================================

#include <iostream>
#include <string>

#include <sys/times.h> //. times
#include <limits.h>

#include "NB_SelectManyClass.h"
#include "NB_ResultProperty.h"
#include "NB_ResultValue.h"
#include "NB_PropertyList.h"
#include "NB_ResultObject.h"
#include "interfaceOracle.h"
/* strlcpy based on OpenBSDs strlcpy */
#include <sys/types.h>

/*
 *  * Copy src to string dst of size siz.  At most siz-1 characters
 *   * will be copied.  Always NUL terminates (unless siz == 0).
 *    * Returns strlen(src); if retval >= siz, truncation occurred.
 *     */
size_t
strlcpy(char *dst, const char *src, size_t siz)
{
        char *d = dst;
        const char *s = src;
        size_t n = siz;

        /* Copy as many bytes as will fit */
        if (n != 0 && --n != 0) {
                do {
                        if ((*d++ = *s++) == 0)
                                break;
                } while (--n != 0);
        }

        /* Not enough room in dst, add NUL and traverse rest of src */
        if (n == 0) {
                if (siz != 0)
                        *d = '\0';                /* NUL-terminate dst */
                while (*s++)
                        ;
        }

        return(s - src - 1);        /* count does not include NUL */
}

pthread_mutex_t NB_SelectManyClass::mutexSelectMany ;
NB_SelectManyClass* NB_SelectManyClass::selectManyInterface = NULL;

NB_SelectManyClass* NB_SelectManyClass::getInstance() {
  if (selectManyInterface == NULL) {
    selectManyInterface = new NB_SelectManyClass();
  }
  return selectManyInterface;
}


void NB_SelectManyClass::setUsername(char* user) {
    strlcpy( userName, user, lgUserName );
}
char* NB_SelectManyClass::getUsername() {
  return(userName);
}

void NB_SelectManyClass::setPasswd(char* pwd) {
    strlcpy( password, pwd, lgPassword );
}

char* NB_SelectManyClass::getPasswd() {
  return(password);
}

void NB_SelectManyClass::setBasename(char* database) {
  strlcpy( baseName, database, lgBaseName );
}

char* NB_SelectManyClass::getBasename() {
  return (baseName);
}

void NB_SelectManyClass::NB_SelectManyConnect( const char* user,
					const char* pwd,
					const char* database ) {
#ifdef DEBUG_TRACE
  //. if( ( _outFile = fopen( "/tmp/mireille.log", "a" ) ) == NULL ) {
  //.   std::cerr << "No log file" << std::endl ;
  //. }
  _outFile = stderr ;
#endif

  printf( "NB_SelectManyClass::NB_SelectManyConnect 1\n" );
  fflush( stdout );

  if (db != NULL) {
    throw NB_Error( "SelectManyConnect failed, already connected", 99008 );
  }
  strlcpy( userName, user, lgUserName );
  strlcpy( password, pwd, lgPassword );
  strlcpy( baseName, database, lgBaseName );
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - DB connection parameters defined\n", pthread_self() );
  fflush( _outFile );
#endif

#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - DB connection parameters defined : '%s'/'%s'@'%s'\n",
	   pthread_self(), userName, password, baseName );
  fflush( _outFile );
#endif

  //. initalise mutex
  int res ;
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - before pthread_mutex_init\n", pthread_self() ); 
  fflush( _outFile );
#endif
  if( res = pthread_mutex_init( &mutexSelectMany, NULL ) ) {
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - pthread_mutex_init returns error %d\n", pthread_self(), res ); 
    fflush( _outFile );
#endif
    throw NB_Error( "SelectMany constructor failed in mutex init", 99002 );
  }
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - after pthread_mutex_init\n", pthread_self() ); 
  fflush( _outFile );
#endif

  //. connection to the DB
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - before dbConnect\n", pthread_self() ); 
  fflush( _outFile );
#endif
  try {
    dbConnect();
  } catch( NB_Error e ) {
    db = NULL ;
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - dbConnect catched NB_Error\n", pthread_self() ); 
#endif
    throw ;
  } catch( ... ) {
    db = NULL ;
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - dbConnect catched unknown error\n", pthread_self() ); 
#endif
    throw NB_Error( "dbConnect failed with undefined exception", 99002 );
  }
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - after dbConnect\n", pthread_self() ); 
#endif

}


void NB_SelectManyClass::NB_SelectManyConnect() {
#ifdef DEBUG_TRACE
  //. if( ( _outFile = fopen( "/tmp/mireille.log", "a" ) ) == NULL ) {
  //.   std::cerr << "No log file" << std::endl ;
  //. }
  _outFile = stderr ;
#endif

  printf( "NB_SelectManyClass::NB_SelectManyConnect 2 \n" );
  fflush( stdout );

  if (strcmp(userName,"")==0) {
    throw NB_Error( "SelectManyConnect failed, no username", 99005 );
  }
  if (strcmp(password,"")==0) {
    throw NB_Error( "SelectManyConnect failed, no password", 99006 );
  }
  if (strcmp(baseName,"")==0) {
    throw NB_Error( "SelectManyConnect failed, no baseName", 99007 );
  }

#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - DB connection parameters defined : '%s'/'%s'@'%s'\n",
	   pthread_self(), userName, password, baseName );
  fflush( _outFile );
#endif

  //. initalise mutex
  int res ;
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - before pthread_mutex_init\n", pthread_self() ); 
  fflush( _outFile );
#endif
  if( res = pthread_mutex_init( &mutexSelectMany, NULL ) ) {
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - pthread_mutex_init returns error %d\n", pthread_self(), res ); 
    fflush( _outFile );
#endif
    throw NB_Error( "SelectMany constructor failed in mutex init", 99002 );
  }
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - after pthread_mutex_init\n", pthread_self() ); 
  fflush( _outFile );
#endif

  //. connection to the DB
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - before dbConnect\n", pthread_self() ); 
  fflush( _outFile );
#endif
  try {
    dbConnect();
  } catch( NB_Error e ) {
    db = NULL ;
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - dbConnect catched NB_Error\n", pthread_self() ); 
#endif
    throw ;
  } catch( ... ) {
    db = NULL ;
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - dbConnect catched unknown error\n", pthread_self() ); 
#endif
    throw NB_Error( "dbConnect failed with undefined exception", 99002 );
  }
#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - after dbConnect\n", pthread_self() ); 
#endif

}

void NB_SelectManyClass::NB_SelectManyDisconnect() {
  try {
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - NB_SelectManyClass::NB_SelectManyDisconnect() - get in\n",
	     pthread_self() );
#endif
    if( db != NULL) {
#ifdef DEBUG_TRACE
      fprintf( _outFile, "NB_SelectManyClass::NB_SelectManyDisconnect() - calls db->disconnect\n" );
#endif
      db->disconnect() ;
      delete db ;
      db = NULL;
      if (selectManyInterface != NULL) {
	delete selectManyInterface;
	selectManyInterface = NULL;
      } else {
#ifdef DEBUG_TRACE
	fprintf( _outFile, "NB_SelectManyClass::NB_SelectManyDisconnect() - db NULL\n" );
#endif
      }
    }
#ifdef DEBUG_TRACE
    fprintf( _outFile, "NB_SelectManyClass::NB_SelectManyDisconnect() - get out\n" );
#endif
  } catch( ... ) {
    throw ;
  }
}

NB_SelectManyClass::~NB_SelectManyClass() {

  //. delete mutex
  int res ;
  //. _outFile << "t" << pthread_self() << " - before pthread_mutex_destroy" << std::endl ; 
  if( res = pthread_mutex_destroy( &mutexSelectMany ) ) {
#ifdef DEBUG_TRACE
    fprintf( _outFile, "pthread_mutex_destroy failed with error %d (%s)\n",
	     res, strerror( res ) );
#endif
  }

  //. disconnect
  try {
    if( db != NULL) {
      db->disconnect() ;
      delete db ;
    }
  } catch( ... ) {
    throw ;
  }

#ifdef DEBUG_TRACE
  fclose( _outFile );
#endif

}

int NB_SelectManyClass::dbConnect() {

#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - NB_SelectManyclass::dbConnect('%s'/'%s'@'%s') - begin\n",
	   pthread_self(), userName, password, baseName );
  fflush( _outFile );
#endif
  try {
    printf( "NB_SelectMany::dbConnect calls new Database\n" );
#ifdef DEBUG_TRACE
    db = new Database( _outFile, userName, password, baseName );
#else
    db = new Database( userName, password, baseName );
#endif
    printf( "NB_SelectMany::dbConnect : new Database called\n" );
  } catch( NB_Error &e ) {
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - NB_SelectManyClass::dbConnect - NB_Error returned by Database::constr\n", pthread_self() );
    fflush( _outFile );
#endif
    throw ;
  } catch( ExceptionOracle &e ) {
 #ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - NB_SelectManyClass::dbConnect - ExceptionOracle '%s' (%d) returned by Database::constr\n",
	     pthread_self(), e.why(), e.getErrorOracle() );
    fflush( _outFile );
#endif
   throw NB_Error( e.why(), e.getErrorOracle() );
  } catch( ... ) {
#ifdef DEBUG_TRACE
    fprintf( _outFile, "NB_SelectManyClass::dbConnect - unknown error returned by Database::constr\n" );
#endif
    throw NB_Error( "Can not create Database", 99002 );
  }
#ifdef DEBUG_TRACE
  fprintf( _outFile, "NB_SelectManyclass::dbConnect - end\n" );
#endif
  return 0 ;
}

Request* NB_SelectManyClass::dbRequest( const char* req ) {

#ifdef DEBUG_TRACE
  fprintf( _outFile, "t%d - NB_SelectManyClass::dbRequest - begin '%s'\n",
	   pthread_self(), req );
  fflush( _outFile );
#endif

  if( db == NULL ) {
    throw NB_Error( "Database not connected", 99002 );
  }

  //. request
  Request *rq = NULL ;
  try {
    rq = db->newRequest() ;
  } catch( ... ) {
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - SelectMany::dbRequest : db->newRequest failed\n", pthread_self() );
    fflush( _outFile );
#endif
    throw ;
  }

  try {
    rq->parse( req );
  } catch( ... ) {
    delete rq ;
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - SelectMany::dbRequest : rq->parse failed\n", pthread_self() );
    fflush( _outFile );
#endif
    throw ;
  }
  
  try {
    rq->execute() ;
  } catch( ... ) {
    delete rq ;
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - SelectMany::dbRequest : rq->execute failed\n", pthread_self() );
    fflush( _outFile );
#endif
    throw ;
  }

#ifdef DEBUG_TRACE
  //.   _outFile << "NB_SelectManyclass::dbRequest - end" << std:endl ;
  fprintf( _outFile, "t%d - NB_SelectManyclass::dbRequest - end '%s'\n",
	   pthread_self(), req );
  fflush( _outFile );
#endif

#ifdef DEBUG_TRACE
  fprintf( _outFile, "NB_SelectManyclass::dbRequest - end\n" );
#endif

  return rq ;

}

NB_DBAgent::DataType NB_SelectManyClass::getColumnType( Request* rq, int pos ) {

  NB_DBAgent::DataType nbTyp ;

  TYPE_ORACLE oraType = rq->getTupleColumnTypeAsOracleType( pos );
  switch( oraType ) {
  case VARCHAR2 :
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case NUMBER :
    nbTyp = NB_DBAgent::PS_LONG ;
    break ;
  case INTEGER :
    nbTyp = NB_DBAgent::PS_INT ;
    break ;
  case FLOAT :
    nbTyp = NB_DBAgent::PS_FLOAT ;
    break ;
  case STRING :
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case VARNUM :
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case DECIMAL :
    nbTyp = NB_DBAgent::PS_DECIMAL ;
    break ;
  case LONG :
    nbTyp = NB_DBAgent::PS_LONG ;
    break ;
  case VARCHAR :
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case ROWID : //. type interne Oracle
    nbTyp = NB_DBAgent::PS_UNKNOWN ;
    break ;
  case DATE :
    nbTyp = NB_DBAgent::PS_DATETIME ;
    break ;
  case VARRAW :
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case RAW : 
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case LONGRAW :
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case UNSIGNED :
    nbTyp = NB_DBAgent::PS_LONG ;
    break ;
  case DISPLAY :
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case LONGVARCHAR : 
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case LONGVARRAW :
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case CHAR :
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  case CHARZ :
    nbTyp = NB_DBAgent::PS_TEXT ;
    break ;
  default :
    nbTyp = NB_DBAgent::PS_UNKNOWN ;
  }
  return nbTyp ;
  //. CHAR2(13) : len = 13
  //. VARCHAR2(13) : chaine variable, 0 < len < 20
}

NB_ResultObject_Cltn* NB_SelectManyClass::selectMany( const char* request,
						      const char* dateFormat ) {

  static clock_t startTime = 0 ;
  static struct tms t_struct ;
  static clock_t lockTime = 0 ;
  static clock_t alterTime = 0 ;
  static clock_t sqlTime = 0 ;
  static clock_t endTime = 0 ;
  int currRow = 0;

  startTime = times( &t_struct );

  printf( "NB_SelectMany::getColumnType, dateFormat '%s'\n", dateFormat );
  fflush( stdout );
  int param = strlen( dateFormat );
  printf( "NB_SelectMany::getColumnType, len %d\n", param );
  fflush( stdout );
  //. db->setDateFormatSize(strlen(dateFormat)+1);
  printf( "NB_SelectMany::getColumnType, call setDateFormatSize\n" );
  fflush( stdout );
  db->setDateFormatSize( param + 1 );
  printf( "NB_SelectMany::getColumnType, after setDateFormatSize\n" );
  fflush( stdout );
  
  try {

    int self = pthread_self() ;
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - starting NB_SelectManyClass::selectMany as '%s'/'%s'@'%s'\n",
	   pthread_self(), userName, password, baseName );
    fflush( _outFile );
#endif

    //. lock the mutex so that selectMany shall be executed once at a time only
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - before pthread_mutex_lock\n", pthread_self() ); 
    fflush( _outFile );
#endif
    pthread_mutex_lock( &mutexSelectMany );
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - after pthread_mutex_lock\n", pthread_self() ); 
    fflush( _outFile );
#endif

    char alterRequest[255] ;
    sprintf( alterRequest, "alter Session set NLS_DATE_FORMAT='%s'", dateFormat );
  
    //. do NOT give the string to dbRequest or you get a Segmentation Fault !
    //. Request* rq0 = dbRequest( "alter Session ..." );
    int nbRetry = 5 ;
    Booleen res = False ; //. will be true if no error occured
    Request* rq = NULL ;
    while( ( nbRetry > 0 ) && ( !res ) ) {
      try {
	lockTime = times( &t_struct );
	Request* rq0 ;
	try {
	  rq0 = dbRequest( alterRequest );
	} catch( ... ) {
#ifdef DEBUG_TRACE
	  fprintf( _outFile, "t%d - error in dbRequest alterSession\n", pthread_self() );
#endif
	  throw ;
	}
	alterTime = times( &t_struct );
	if( rq0 == NULL ) {
	  //. unlock the mutex
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - before pthread_mutex_unlock 1\n", pthread_self() ); 
    fflush( _outFile );
#endif
	  pthread_mutex_unlock( &mutexSelectMany );
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - after pthread_mutex_unlock 1\n", pthread_self() ); 
    fflush( _outFile );
#endif
	} else {
	  res = True ;
	}

	if( res ) {
	  delete rq0 ;
	  try {
	    rq = dbRequest( request ); //. SQLrequest );
	  } catch (...) {
#ifdef DEBUG_TRACE
	    fprintf( _outFile, "t%d - error in dbRequest select\n", pthread_self() );
#endif
	    throw ;
	  }
	  sqlTime = times( &t_struct );
	  if( rq == NULL ) {
#ifdef DEBUG_TRACE
	    fprintf( _outFile, "error in request\n" );
#endif
	    //. delete [] SQLrequest ; 
	    //. unlock the mutex
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - before pthread_mutex_unlock 2\n", pthread_self() ); 
    fflush( _outFile );
#endif
	    pthread_mutex_unlock( &mutexSelectMany );
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - after pthread_mutex_unlock 2\n", pthread_self() ); 
    fflush( _outFile );
#endif
	    res = False ;
	  } else {
	    res = True ;
	  }
	} //. end alter session succeeded

	//nbRetry-- ; OA there is already a -- at the end of loop
      } catch( ExceptionOracle &e ) {
	//. there has been an Oracle error 
	//. and reconnect has already been called
	res = False ;
      } catch( ... ) {
#ifdef DEBUG_TRACE
	fprintf( _outFile, "throw ...\n" );
#endif
	throw ;
      }

      nbRetry-- ;
    } //. end while

    if( !res ) {
      //. the re-connection succeeded but there is still a pb
#ifdef DEBUG_TRACE
      fprintf( _outFile, "SQL failed after 5 reconnections\n" );
#endif
      throw NB_Error( "SQL failed after 5 reconnections", 99002 );
    }

    int nbRow = rq->getNumberOfRows() ;
    int nbCol = rq->getNumberOfColumns() ;
#ifdef DEBUG_TRACE
    fprintf( _outFile, "found %d rows of %d colums\n",
	     nbRow, nbCol );
#endif

    NB_DBAgent::DataType currType ;
    char* currValue ;

    NB_ResultProperty* propertyObject = NULL ;
    NB_ResultValue* valueObject = NULL ;
    NB_ResultObject* resuObject = NULL ;
    NB_ResultObject_Cltn* clt = NULL ;
    try {
      clt = new NB_ResultObject_Cltn( nbRow, nbCol );
      //. _outFile << "collection created for " << nbRow
      //. 	<< " records of " << nbCol << " fields"
      //. 	<< std::endl ;
    } catch( ... ) {
      //. unlock the mutex
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - before pthread_mutex_unlock 3\n", pthread_self() ); 
    fflush( _outFile );
#endif
      pthread_mutex_unlock( &mutexSelectMany );
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - after pthread_mutex_unlock 3\n", pthread_self() ); 
    fflush( _outFile );
#endif
      throw NB_Error( "new NB_ResultObject_Cltn failed in NB_SelectManyClass::selectMany", 99002 );
    }

    if( rq->getNumberOfRows() > 0 ){
#ifdef DEBUG_TRACE
      fprintf( _outFile, "t%d - there are %d records to fetch\n",
	       pthread_self(), rq->getNumberOfRows() ); 
      fflush( _outFile );
#endif
      do {

	//. a new record so a new ResultObject
	try {
#ifdef DEBUG_TRACE
	  fprintf( _outFile, "t%d - create a new NB_ResultObject\n", pthread_self() ); 
	  fflush( _outFile );
#endif
	  resuObject = clt->newNB_ResultObject();
	} catch( ... ) {
	  //. unlock the mutex
#ifdef DEBUG_TRACE
	  fprintf( _outFile, "t%d - before pthread_mutex_unlock 4\n", pthread_self() ); 
	  fflush( _outFile );
#endif
	  pthread_mutex_unlock( &mutexSelectMany );
#ifdef DEBUG_TRACE
	  fprintf( _outFile, "t%d - after pthread_mutex_unlock 4\n", pthread_self() ); 
	  fflush( _outFile );
#endif
	  throw NB_Error( "new NB_ResultObject failed in NB_SelectmanyClass::selectMany", 99002 );
	}
	//cerr << "in thread:"<< pthread_self() <<" Dealing with record: "<< currRow << endl;
	currRow++;
	for( int j=0; j<nbCol; j++ ) { //. each column
	  currType = getColumnType( rq, j );
#ifdef DEBUG_TRACE
	  int oraType = (int) rq->getTupleColumnTypeAsOracleType( j );
	  fprintf( _outFile, "SelectMany - col %d : Oracle %d converted into %d\n",
		   j, oraType, currType );
#endif
	  //. nbCol is used to check if the propertyList is built
	  resuObject->add( currType, rq->getStringValue(j),
			   dateFormat, rq->isNullValue(j),
			   nbCol, clt, j );
	} //. end each column
      } while( rq->getNextTuple() ) ;
    }
    //. deletes the request and the database
    //. next selectMany needs a new Request because a request is 
    //. linked to a SQL select string
    delete rq ;

    //. unlock the mutex
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - before pthread_mutex_unlock 5\n", pthread_self() ); 
    fflush( _outFile );
#endif
    pthread_mutex_unlock( &mutexSelectMany );
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - after pthread_mutex_unlock 5\n", pthread_self() ); 
    fflush( _outFile );
#endif

    endTime = times( &t_struct );

    /*
    _outFile << "lock alter sql build" 
	      << " " << ( lockTime - startTime ) * 10 
	      << " " << ( alterTime - lockTime ) * 10
	      << " " << ( sqlTime - alterTime ) * 10
	      << " " << ( endTime - sqlTime ) * 10 << std::endl ;
    */

#ifdef TRACE_MI
    NB_ResultObject_Cltn* reponse = clt ;
    try {
      int nbColSelect = reponse->getProperties().extent() ;
      _outFile << "nbLig = " << reponse->length()
		<< ", nbCol = " << nbColSelect << std::endl ;
      
      if( reponse->length() > 0 ) { 
	  
	const NB_ResultObject* pResultObj = NULL ;
	const NB_ResultValue* value = NULL ;
	
	for( int irep=0; irep<10/*reponse->length()*/; irep++ ) { 
	  
	  pResultObj = reponse->at( irep );
	  
	  for( int j=0; j<nbColSelect; j++ ) {
	    
	    _outFile << "record " << irep << " - col " << j << " - " ;
	    value = pResultObj->valueAt( j );
	    NB_DBAgent::DataType typeVal = pResultObj->propertyAt( j )->getType();
	    
	    switch( typeVal ) {
	    case NB_DBAgent::PS_CHAR :
	      std::cerr << "Found PS_CHAR '" << ( (const char*) (*value) )[0]
			<< "'" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_BINARY :
	      std::cerr << "Found PS_BINARY - not displayed" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_SHORT :
	      std::cerr << "Found PS_SHORT '" << (short) *value << "'" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_INT :
	      std::cerr << "Found PS_INT '" << (int) *value << "'" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_LONG :
	      std::cerr << "Found PS_LONG '" << (long) *value << "'" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_FLOAT :
	      std::cerr << "Found PS_FLOAT - not displayed" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_DOUBLE :
	      std::cerr << "Found PS_DOUBLE '" << (double) *value << "'" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_TEXT :
	      std::cerr << "Found PS_TEXT '" << (const char*) *value
			<< "'" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_IMAGE :
	      std::cerr << "Found PS_IMAGE - not displayed" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_MONEY :
	      std::cerr << "Found PS_MONEY - not displayed" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_DATETIME :
	      {
		/*
		//. ( (const struct tm) (*value) )
		std::cerr << "Time found '" << t->hour() << ":" << t->minute()
		<< ":" << t->second() << "'" << std::endl ;
		
		//. ( (const struct tm) (*value ) )
		std::cerr << "Date found '" << d->dayOfMonth() << "/"
		<< d->month() << "/" << d->year() << "'" << std::endl ;
		
		*/	      
	      }
	    break ;
	    case NB_DBAgent::PS_DECIMAL :
	      std::cerr << "Found PS_DECIMAL - not displayed" << std::endl ;
	      break ;
	    case NB_DBAgent::PS_UNKNOWN :
	      std::cerr << "Found PS_UNKNOWN - not displayed" << std::endl ;
	      break ;
	    default:
	      std::cerr << "default : not displayed" << std::endl ;
			      break ;
	    } //. end switch type
	  } //. loop j : loop on column
	  std::cerr << std::endl ;
	} //. loop irep : loop on records
      } //. end reponse->length() > 0
    } catch( NB_Error &e ) {
      std::cerr << "Error " << e.getErrorId() << " : '"
		<< e.getErrorString() << "'" << std::endl ;
    }
#endif //. TRACE_MI

#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d ending NB_SelectManyClass::selectMany\n", pthread_self() );
#endif
    return clt ;

  } catch( NB_Error e ) {
    //. unlock the mutex
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - before pthread_mutex_unlock 6\n", pthread_self() ); 
    fflush( _outFile );
#endif
    pthread_mutex_unlock( &mutexSelectMany );
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - after pthread_mutex_unlock 6\n", pthread_self() ); 
    fflush( _outFile );
#endif
    throw ;

  } catch( ... ) {
    //. std::cerr << "selectMany caught unknown error" << std::endl ;
    char errorMessage[2048] ;
    snprintf( errorMessage, 2048, "'selectMany caught unknown error(%d)' in '%s'",
	      99002, request ); //. SQLrequest ); 
    //. unlock the mutex
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - before pthread_mutex_unlock 7\n", pthread_self() ); 
    fflush( _outFile );
#endif
    pthread_mutex_unlock( &mutexSelectMany );
#ifdef DEBUG_TRACE
    fprintf( _outFile, "t%d - after pthread_mutex_unlock 7\n", pthread_self() ); 
    fflush( _outFile );
#endif
    throw NB_Error( errorMessage, 99002 );
  }

  //. code never reaches here !!!
  //. std::cerr << "NB_SelectManyClass::selectMany - get OUT" << std::endl ;
}


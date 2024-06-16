//============================================================
// File Information
//============================================================
//    File:     NB_ResultValue.cpp
//  Author:     Mireille HOT
//    Date:     01/02/2006
// Purpose:     implements NB_ResultValue
//============================================================

#include <iostream>
#include <cstring>
#include <cstdio>
#include <errno.h> //. errno

#include <stdlib.h> //. atoi

#include "NB_ResultValue.h"
#include "BasicException.h" //. NB_Error

NB_ResultValue::NB_ResultValue() {
  union_type = NO_TYPE ;
  union_data.s = NULL ; //. otherwise UMR in purify
  isNullFlag = -1 ; 
}

NB_ResultValue::NB_ResultValue( const NB_ResultValue& val ) {
  union_type = val.union_type ;
  isNullFlag = val.isNullFlag ; 
  if( !isNullFlag ) {
    switch( union_type ) {
    case NO_TYPE :
      break ;
    case CHAR_ETOILE_TYPE :
      try {
	union_data.s = new char[ strlen( val.union_data.s ) ];
	strcpy( union_data.s, val.union_data.s );
      } catch( ... ) {
	throw NB_Error( "new char* failed in NB_ResultValue::copy_constr", 99001 );
      }
      break ;
    case INT_TYPE :
      union_data.i = val.union_data.i ;
      break ;
    case SHORT_TYPE :
      union_data.n = val.union_data.n ;
      break ;
    case LONG_TYPE :
      union_data.l = val.union_data.l ;
      break ;
    case DOUBLE_TYPE :
      union_data.d = val.union_data.d ;
      break ;
    case TIME_TYPE :
      memcpy( &(union_data.t), &(val.union_data.t), sizeof( union_data ) );
      break ;
    default :
      throw NB_Error( "Default type is an error in NB_ResultValue::copy_const", 99001 );
      break ;
    } //. end switch
  }
}

NB_ResultValue::~NB_ResultValue() {
  if( !isNullFlag ) {
    if( union_type == CHAR_ETOILE_TYPE ) {
      if( union_data.s != NULL ) {
	delete [] union_data.s ;
      }
    }
  }
}

const int NB_ResultValue::isNull() const {
  return isNullFlag ;
}

NB_ResultValue::operator const char * () const {
  if( isNullFlag ) {
    throw NB_Error( "cast NB_ResultValue to const char* : check first that the value is not null", 99001 );
  }
  if( union_type == CHAR_ETOILE_TYPE ) {
    return( union_data.s );
  } else {
    throw NB_Error( "Cannot cast NB_ResultValue to const char*", 99001 );
  }
}

NB_ResultValue::operator char * () const {
  if( isNullFlag ) {
    throw NB_Error( "cast NB_ResultValue to char* : check first that the value is not null", 99001 );
  }
  if( union_type == CHAR_ETOILE_TYPE ) {
    return( union_data.s );
  } else {
    throw NB_Error( "Cannot cast NB_ResultValue to char*", 99001 );
  }
}

NB_ResultValue::operator int () const {
  if( isNullFlag ) {
    throw NB_Error( "cast NB_ResultValue to int : check first that the value is not null", 99001 );
  }
  if( union_type == INT_TYPE ) {
    return( union_data.i );
  } else {
#ifdef DEBUG_TRACE
    fprintf( stderr, "Try to cast type %d into int (%d)\n",
	     union_type, INT_TYPE );
#endif
    throw NB_Error( "Cannot cast NB_ResultValue to int", 99001 );
  }
}

NB_ResultValue::operator short () const {
  if( isNullFlag ) {
    throw NB_Error( "cast NB_ResultValue to short : check first that the value is not null", 99001 );
  }
  if( union_type == SHORT_TYPE ) {
    return( union_data.n );
  } else {
    throw NB_Error( "Cannot cast NB_ResultValue to short", 99001 );
  }
}

NB_ResultValue::operator long () const {
  if( isNullFlag ) {
    throw NB_Error( "cast NB_ResultValue to long : check first that the value is not null", 99001 );
  }
  if( union_type == LONG_TYPE ) {
    return( union_data.l );
  } else {
    char msg[200] ;
    snprintf( msg, 200, "Cannot cast NB_ResultValue to long, type %d", union_type );
    //. throw NB_Error( "Cannot cast NB_ResultValue to long", 99001 );
    throw NB_Error( msg, 99001 );
  }
}

NB_ResultValue::operator float () const {
  if( isNullFlag ) {
    throw NB_Error( "cast NB_ResultValue to float : check first that the value is not null", 99001 );
  }
  if( union_type == FLOAT_TYPE ) {
    return( union_data.f );
  } else if( union_type == INT_TYPE ) {
    return( (float) union_data.i ) ;
  } else if( union_type == DOUBLE_TYPE ) {
    return( (float) union_data.d ) ;
  } else {
    char msg[512] ;
    snprintf( msg, 512, "Cannot cast NB_ResultValue type %d to float",
	      union_type );
    throw NB_Error( msg, 99001 );
  }
}

NB_ResultValue::operator double () const {
  if( isNullFlag ) {
    throw NB_Error( "cast NB_ResultValue to double : check first that the value is not null", 99001 );
  }
  if( union_type == DOUBLE_TYPE ) {
    return( union_data.d );
  } else if( union_type == INT_TYPE ) {
    return( (double) union_data.i );
  } else if( union_type == FLOAT_TYPE ) {
    return( (double) union_data.f );
  } else if( union_type == LONG_TYPE ) {
    return( (double) union_data.l );
  } else {
    throw NB_Error( "Cannot cast NB_ResultValue to double", 99001 );
  }
}

NB_ResultValue::operator const struct tm () const {
  if( isNullFlag ) {
    throw NB_Error( "cast NB_ResultValue to const struct tm : check first that the value is not null", 99001 );
  }
  if( union_type == TIME_TYPE ) {
    return( union_data.t );
  } else {
    throw NB_Error( "Cannot cast NB_ResultValue to struct tm", 99001 );
  }
}


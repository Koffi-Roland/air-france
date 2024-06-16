//============================================================
// File Information
//============================================================
//    File:     NB_ValueList.cpp
//  Author:     Mireille HOT
//    Date:     01/02/2006
// Purpose:     implements NB_ValueList
//============================================================

#include <iostream>
#include <cstring>
#include <errno.h> //. errno
#include <cctype> //. toupper
#include <string>
#include <algorithm> //. transform
#include <pthread.h>

#include "NB_ValueList.h"
#include "NB_ResultValue.h"
#include "NB_ResultObject_Cltn.h"
#include "BasicException.h"

NB_ValueList::NB_ValueList( NB_ResultObject_Cltn* cltn ) {
  //. std::cerr << (void*) this << " - NB_ValueList::constr" << std::endl ;
  begin = cltn->posArrayResultValue ;
  end = begin ;
  empty = 1 ;
  list = cltn ;
}

NB_ValueList::~NB_ValueList() {
  //. std::cerr << (void*) this << " - NB_ValueList::destr" << std::endl ;
  //. collection.clear() ;
  if( empty == 1 ) {
    //. std::cerr << "NB_ValueList::destr - list already empty" << std::endl ;
  } else if( list == NULL ) {
    //. std::cerr << "NB_ValueList::destr - list not empty but pointer NULL to it"
    //.           << std::endl ;
  } else {
    for( int pos=begin; pos<end; pos++ ) {
      //. delete char* allocated in NB_Value.List::add
      if( list->memArrayResultValue[pos].union_type == NB_ResultValue::CHAR_ETOILE_TYPE ) {
	delete [] list->memArrayResultValue[pos].union_data.s ;
	list->memArrayResultValue[pos].union_data.s = NULL ;
	list->memArrayResultValue[pos].union_type == NB_ResultValue::NO_TYPE ;
      }
    } 
    begin = 0 ;
    end = 0 ;
    empty = 1 ;
  }
  list = NULL ;
}

NB_ResultValue * NB_ValueList::operator[]( unsigned int idx ) {
  //. std::cerr << "NB_ValueList::operator[" << idx << "]" << std::endl ;
  //. return &collection[idx] ;
  if( end < begin + idx ) {
    //. std::cerr << "ValList - The elem " << idx << " does not exist in list [" << begin
    //.       << "," << end << "]" << std::endl ;
    return NULL ;
  } else {
    return &( list->memArrayResultValue[begin+idx] );
  }
}

/*
void NB_ValueList::add( const NB_ResultValue* val ) {
  //. std::cerr << "NB_ValueList::add '" << val->getName() << "'" << std::endl ;
  //. collection.push_back( *val );
  selectManyObject->memArrayResultValue[end] = *val ;
  end++ ;
  empty = 0 ;
}
*/

void NB_ValueList::add( NB_DBAgent::DataType t,
			const char* val,
			const char* dateFormat,
			const int isNullData, int index ) {

  //. std::cerr << "NB_ValueList::add t=" << t << std::endl;
  // std::cerr << "'"<< val->getName() << "'" << std::endl ;
  //. collection.push_back( *val );
 
  switch( t ) {
  case NB_DBAgent::PS_CHAR :
    list->memArrayResultValue[end].union_type = NB_ResultValue::CHAR_ETOILE_TYPE ;

    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
      if( val == NULL ) {
	throw NB_Error( "A char can not have a NULL value", 99003 );
      }
      try {
	//. std::cerr << (void*) &( list->memArrayResultValue[end] )
	//. 	    << " new char len 2" << std::endl ;
	list->memArrayResultValue[end].union_data.s = new char[2] ;
	list->memArrayResultValue[end].union_data.s[0] = val[0] ;
	list->memArrayResultValue[end].union_data.s[1] = 0 ;
      } catch( ... ) {
	throw NB_Error( "new char* failed in NB_ResultValue::constr", 99003 );
      }
    }

    break ;

  case NB_DBAgent::PS_BINARY :
    list->memArrayResultValue[end].union_type = NB_ResultValue::NO_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
      throw NB_Error( "Binary type not supported", 99003 );
    }
    break ;

  case NB_DBAgent::PS_SHORT :
    list->memArrayResultValue[end].union_type = NB_ResultValue::SHORT_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
      list->memArrayResultValue[end].union_data.n = (short) atoi( val );
    }
    break ;

  case NB_DBAgent::PS_INT :
    list->memArrayResultValue[end].union_type = NB_ResultValue::INT_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
      list->memArrayResultValue[end].union_data.n = atoi( val );
    }
    break ;

  case NB_DBAgent::PS_LONG :
    //. it may be a float or an int

    if( isNullData ) {
      //. do not change type,
      //. it is not taken into account in memArrayResultProperty
      //. because the memArrayResultProperty array
      //. is defined for all the records
      list->memArrayResultValue[end].union_type = NB_ResultValue::LONG_TYPE ;
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;

      char* ptrToNotConvertedString ;

      //. std::cerr << "Try to convert into integer" << std::endl ;
      errno = 0 ;
      int tryFloat = 0 ;

      long resuLong = strtol( val, &ptrToNotConvertedString, 10 );

      if( errno ) {

	//. std::cerr << "errno = " << errno << " (" << strerror( errno ) << ")" << std::endl ;
	tryFloat = 1 ;

      } else {

	if( ptrToNotConvertedString[0] == 0 ) {
	  //. std::cerr << "The long result is " << resuLong << std::endl ;
	  tryFloat = 0 ;
	  //. *t = NB_DBAgent::PS_INT ;
	  list->memArrayResultValue[end].union_type = NB_ResultValue::LONG_TYPE ;
	  list->memArrayResultValue[end].union_data.l = resuLong ;
	  //. std::cerr << "val '" << union_data.l << "'" << std::endl ;
	} else {
	  //. std::cerr << "Partial result " << resuLong << " so try float" << std::endl ;
	  tryFloat = 1 ;
	}
      }

      if( tryFloat ) {

	//. std::cerr << std::endl << "Try to convert into float" << std::endl ;
	errno = 0 ;
	double resuFloat = strtod( val, &ptrToNotConvertedString );

	if( errno ) {

	  //. std::cerr << "errno = " << errno << " (" << strerror( errno ) << ")" << std::endl ;
	  //. *t = NB_DBAgent::PS_CHAR ;
	  list->memArrayResultValue[end].union_type = NB_ResultValue::CHAR_ETOILE_TYPE ;
	  try {
	    int len = strlen( val ) + 1 ;
	    list->memArrayResultValue[end].union_data.s = new char[ len ] ;
	    strlcpy( list->memArrayResultValue[end].union_data.s, val, len );
	    //. std::cerr << "val '" << union_data.s << "'" << std::endl ;
	  } catch( ... ) {
	    throw NB_Error( "new char* failed in NB_ResultValue::constr", 99003 );
	  }

	} else {

	  if( ptrToNotConvertedString[0] == 0 ) {
	    //. std::cerr << "The result is " << resuFloat << std::endl ;
	    //. *t = NB_DBAgent::PS_DOUBLE ;
	    list->memArrayResultValue[end].union_type = NB_ResultValue::DOUBLE_TYPE ;
	    list->memArrayResultValue[end].union_data.d = resuFloat ;
	    //. std::cerr << "val '" << union_data.d << "'" << std::endl ;
	  } else {
	    //. std::cerr << "Partial result " << resuFloat << std::endl ;
	    //. std::cerr << "Remaining string '" << ptrToNotConvertedString << "'" << std::endl ;
	    //. *t = NB_DBAgent::PS_CHAR ;
	    list->memArrayResultValue[end].union_type = NB_ResultValue::CHAR_ETOILE_TYPE ;
	    int len ;
	    try {
	      len = strlen( val ) + 1 ;
	      list->memArrayResultValue[end].union_data.s = new char[ len ] ;
	      strlcpy( list->memArrayResultValue[end].union_data.s, val, len );
	      //. std::cerr << "val '" << union_data.s << "'" << std::endl ;
	    } catch( ... ) {
	      throw NB_Error( "new char* failed in NB_ResultValue::constr", 99003 );
	    }
	  }
	}
      }
    }

    break ;

  case NB_DBAgent::PS_FLOAT :
    list->memArrayResultValue[end].union_type = NB_ResultValue::FLOAT_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
      list->memArrayResultValue[end].union_data.f = (float) atof( val );
    }
    break ;

  case NB_DBAgent::PS_DOUBLE :
    list->memArrayResultValue[end].union_type = NB_ResultValue::DOUBLE_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
      list->memArrayResultValue[end].union_data.d = atof( val );
    }
    break ;

  case NB_DBAgent::PS_TEXT :
    list->memArrayResultValue[end].union_type = NB_ResultValue::CHAR_ETOILE_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;

      int len ;
      try {
	len = strlen( val ) + 1 ;
	//. std::cerr << "PS_TEXT gets '" << val << "' with len " << len << std::endl ;
	list->memArrayResultValue[end].union_data.s = new char[len];
	strlcpy( list->memArrayResultValue[end].union_data.s, val, len );
	//. std::cerr << "PS_TEXT '" << list->memArrayResultValue[end].union_data.s << "'" << std::endl ;
      } catch( ... ) {
	throw NB_Error( "new char* failed in NB_ResultValue::constr", 99003 );
      }
    }
    break ;

  case NB_DBAgent::PS_IMAGE :
    list->memArrayResultValue[end].union_type = NB_ResultValue::NO_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
      throw NB_Error( "Image type not supported", 99003 );
    }
    break ;

  case NB_DBAgent::PS_MONEY :
    list->memArrayResultValue[end].union_type = NB_ResultValue::NO_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
      throw NB_Error( "Money type not supported", 99003 );
    }
    break ;

  case NB_DBAgent::PS_DATETIME :
    {
      list->memArrayResultValue[end].union_type = NB_ResultValue::TIME_TYPE ;

      if( isNullData ) {
	list->memArrayResultValue[end].isNullFlag = 1 ;
      } else {
	list->memArrayResultValue[end].isNullFlag = 0 ;
	std::string fmt = dateFormat ;
	std::string fmtUpper = dateFormat ;
	std::transform( fmt.begin(), fmt.end(),
			fmtUpper.begin(), (int(*)(int)) std::toupper );
	const char* currFmt = fmtUpper.c_str() ;
	
	std::string valStr = val ;
	std::string valUpper = val ;
	std::transform( valStr.begin(), valStr.end(),
			valUpper.begin(), (int(*)(int)) std::toupper );
	const char* currVal = valUpper.c_str() ;
	
	//. initialize the structure
	list->memArrayResultValue[end].union_data.t.tm_year = 70 ;
	list->memArrayResultValue[end].union_data.t.tm_mon = 0 ;
	list->memArrayResultValue[end].union_data.t.tm_mday = 1 ;
	//. list->memArrayResultValue[end].union_data.t.tm_wday = 1 ;
	list->memArrayResultValue[end].union_data.t.tm_hour = 0 ;
	list->memArrayResultValue[end].union_data.t.tm_min  = 0 ;
	list->memArrayResultValue[end].union_data.t.tm_sec  = 0 ;
	list->memArrayResultValue[end].union_data.t.tm_isdst = 0 ;
	
	if( currVal[0] == 0 ) {
	  //. std::cerr << "no date for val '" << val << "'" << std::endl ;
	  break ;
	}

	//. std::cerr << "case NB_DBAgent::PS_DATETIME - val '" << currVal << "' (val '" << val << "') - fmt '" << currFmt << "'" << std::endl ;
	
	//. The following code goes through the format string,
	//. looks for an Oracle date format
	//. get in value the corresponding field
	//. passes the not recognized chars
	int stepFmt ; //. nb chars in the format string for the current field
	int stepVal ; //. nb chars in the value string for the current field
	int remainingLen ; //. in the format string, chars that have not been managed yet
	char tmpStr[12] ; //. temporary value of the field, before the cast 
	int nothingFound = 1 ; //. char in the format that is kept as is
	while( currFmt[0] != 0 ) {
	  remainingLen = strlen( currFmt );
	  if( remainingLen > 4 ) {
	    if( memcmp( currFmt, "MONTH", 5 ) == 0 ) {
	      int l = 0 ;
	      while( currVal[l] != ' ' ) {
		tmpStr[l] = currVal[l] ;
		l++ ;
	      }
	      tmpStr[l] = 0 ;
	      //. std::cerr << "found month with len " << l << " : '" << tmpStr << "'" << std::endl ;
	      //. the month returned by Oracle ends with a space character
	      if( memcmp( tmpStr, "JANUARY", 7 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 0 ;
		stepVal = 8 ;
	      } else if( memcmp( tmpStr, "FEBRUARY", 8 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 1 ;
		stepVal = 9 ;
	      } else if( memcmp( tmpStr, "MARCH", 5 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 2 ;
		stepVal = 6 ;
	      } else if( memcmp( tmpStr, "APRIL", 5 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 3 ;
		stepVal = 6 ;
	      } else if( memcmp( tmpStr, "MAY", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 4 ;
		stepVal = 4 ;
	      } else if( memcmp( tmpStr, "JUNE", 4 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 5 ;
		stepVal = 5 ;
	      } else if( memcmp( tmpStr, "JULY", 4 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 6 ;
		stepVal = 5 ;
	      } else if( memcmp( tmpStr, "AUGUST", 6 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 7 ;
		stepVal = 8 ;
	      } else if( memcmp( tmpStr, "SEPTEMBER", 9 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 8 ;
		stepVal = 10 ;
	      } else if( memcmp( tmpStr, "OCTOBER", 7 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 9 ;
		stepVal = 8 ;
	      } else if( memcmp( tmpStr, "NOVEMBER", 8 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 10 ;
		stepVal = 9 ;
	      } else if( memcmp( tmpStr, "DECEMBER", 8 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 11 ;
		stepVal = 9 ;
	      }
	      //. std::cerr << "tm_month = " << union_data.t.tm_mon << std::endl ;
	      stepFmt = 5 ;
	      nothingFound = 0 ;
	    }
	  }
	  if( ( remainingLen > 3 ) && ( nothingFound ) ) {
	    if( memcmp( currFmt, "YYYY", 4 ) == 0 ) {
	      memcpy( tmpStr, currVal, 4 );
	      tmpStr[4] = 0 ;
	      list->memArrayResultValue[end].union_data.t.tm_year = atoi( tmpStr ) - 1900 ;
	      //. std::cerr << "tm_year = " << union_data.t.tm_year << std::endl ;
	      stepFmt = 4 ;
	      stepVal = 4 ;
	      nothingFound = 0 ;
	    } else if( memcmp( currFmt, "HH24", 4 ) == 0 ) {
	      //. std::cerr << "'HH24' found" << std::endl ;
	      memcpy( tmpStr, currVal, 2 );
	      tmpStr[2] = 0 ;
	      list->memArrayResultValue[end].union_data.t.tm_hour = atoi( tmpStr );
	      //. std::cerr << "tm_hour = " << list->memArrayResultValue[end].union_data.t.tm_hour << std::endl ;
	      stepFmt = 4 ;
	      stepVal = 2 ;
	      nothingFound = 0 ;
	    } else if( memcmp( currFmt, "HH12", 4 ) == 0 ) {
	      memcpy( tmpStr, currVal, 2 );
	      tmpStr[2] = 0 ;
	      list->memArrayResultValue[end].union_data.t.tm_hour = atoi( tmpStr );
	      //. std::cerr << "tm_hour = " << list->memArrayResultValue[end].union_data.t.tm_hour << std::endl ;
	      stepFmt = 4 ;
	      stepVal = 2 ;
	      nothingFound = 0 ;
	    }
	  }
	  if( ( remainingLen > 2 ) && ( nothingFound ) ) {
	    if( memcmp( currFmt, "MON", 3 ) == 0 ) {
	      //. std::cerr << "'MON' found" << std::endl ;
	      memcpy( tmpStr, currVal, 3 );
	      tmpStr[3] = 0 ;
	      
	      if( memcmp( tmpStr, "JAN", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 0 ;
		//. std::cerr << "tm_mon set to 1" << std::endl ;
	      } else if( memcmp( tmpStr, "FEB", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 1 ;
		//. std::cerr << "tm_mon set to 2" << std::endl ;
	      } else if( memcmp( tmpStr, "MAR", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 2 ;
		//. std::cerr << "tm_mon set to 3" << std::endl ;
	      } else if( memcmp( tmpStr, "APR", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 3 ;
		//. std::cerr << "tm_mon set to 4" << std::endl ;
	      } else if( memcmp( tmpStr, "MAY", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 4 ;
		//. std::cerr << "tm_mon set to 5" << std::endl ;
	      } else if( memcmp( tmpStr, "JUN", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 5 ;
		//. std::cerr << "tm_mon set to 6" << std::endl ;
	      } else if( memcmp( tmpStr, "JUL", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 6 ;
		//. std::cerr << "tm_mon set to 7" << std::endl ;
	      } else if( memcmp( tmpStr, "AUG", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 7 ;
		//. std::cerr << "tm_mon set to 8" << std::endl ;
	      } else if( memcmp( tmpStr, "SEP", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 8 ;
		//. std::cerr << "tm_mon set to 9" << std::endl ;
	      } else if( memcmp( tmpStr, "OCT", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 9 ;
		//. std::cerr << "tm_mon set to 10" << std::endl ;
	      } else if( memcmp( tmpStr, "NOV", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 10 ;
		//. std::cerr << "tm_mon set to 11" << std::endl ;
	      } else if( memcmp( tmpStr, "DEC", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_mon = 11 ;
		//. std::cerr << "tm_mon set to 12" << std::endl ;
		//. } else {
		//. std::cerr << "no tm_mon set" << std::endl ;
	      }
	      stepFmt = 3 ;
	      stepVal = 3 ;
	      nothingFound = 0 ;
	    }
	  }
	  if( ( remainingLen > 1 ) && ( nothingFound ) ) {
	    if( memcmp( currFmt, "YY", 2 ) == 0 ) {
	      memcpy( tmpStr, currVal, 2 );
	      tmpStr[2] = 0 ;
	      list->memArrayResultValue[end].union_data.t.tm_year = atoi( tmpStr ) + 100 ;
	      //. std::cerr << "tm_year = " << union_data.t.tm_year << std::endl ;
	      stepFmt = 2 ;
	      stepVal = 2 ;
	      nothingFound = 0 ;
	    } else if( memcmp( currFmt, "RR", 2 ) == 0 ) {
	      //. std::cerr << "'RR' found" << std::endl ;
	      memcpy( tmpStr, currVal, 2 );
	      tmpStr[2] = 0 ;
	      list->memArrayResultValue[end].union_data.t.tm_year = atoi( tmpStr );
	      if( list->memArrayResultValue[end].union_data.t.tm_year < 50 ) {
		list->memArrayResultValue[end].union_data.t.tm_year += 100 ;
	      }
	      //. std::cerr << "tm_year = " << list->memArrayResultValue[end].union_data.t.tm_year << std::endl ;
	      stepFmt = 2 ;
	      stepVal = 2 ;
	      nothingFound = 0 ;
	    } else if( memcmp( currFmt, "MM", 2 ) == 0 ) {
	      memcpy( tmpStr, currVal, 2 );
	      tmpStr[2] = 0 ;
	      list->memArrayResultValue[end].union_data.t.tm_mon = atoi( tmpStr ) - 1 ;
	      //. std::cerr << "tm_mon = " << union_data.t.tm_mon << std::endl ;
	      stepFmt = 2 ;
	      stepVal = 2 ;
	      nothingFound = 0 ;
	    } else if( memcmp( currFmt, "DD", 2 ) == 0 ) {
	      //. std::cerr << "'DD' found" << std::endl ;
	      memcpy( tmpStr, currVal, 2 );
	      tmpStr[2] = 0 ;
	      list->memArrayResultValue[end].union_data.t.tm_mday = atoi( tmpStr );
	      //. std::cerr << "tm_day = " << list->memArrayResultValue[end].union_data.t.tm_mday << std::endl ;
	      stepFmt = 2 ;
	      stepVal = 2 ;
	      nothingFound = 0 ;
	    } else if( memcmp( currFmt, "DY", 2 ) == 0 ) {
	      memcpy( tmpStr, currVal, 3 );
	      tmpStr[3] = 0 ;
	      //. std::cerr << "DY found '" << tmpStr << "'" << std::endl ;
	      if( memcmp( tmpStr, "SUN", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_wday = 0 ;
		//. std::cerr << "tm_wday set to 0" << std::endl ;
	      } else if( memcmp( tmpStr, "MON", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_wday = 1 ;
		//. std::cerr << "tm_wday set to 1" << std::endl ;
	      } else if( memcmp( tmpStr, "TUE", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_wday = 2 ;
		//. std::cerr << "tm_wday set to 2" << std::endl ;
	      } else if( memcmp( tmpStr, "WED", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_wday = 3 ;
		//. std::cerr << "tm_wday set to 3" << std::endl ;
	      } else if( memcmp( tmpStr, "THU", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_wday = 4 ;
		//. std::cerr << "tm_wday set to 4" << std::endl ;
	      } else if( memcmp( tmpStr, "FRI", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_wday = 5 ;
		//. std::cerr << "tm_wday set to 5" << std::endl ;
	      } else if( memcmp( tmpStr, "SAT", 3 ) == 0 ) {
		list->memArrayResultValue[end].union_data.t.tm_wday = 6 ;
		//. std::cerr << "tm_wday set to 6" << std::endl ;
		//. } else {
		//. std::cerr << "no tm_wday set" << std::endl ;
	      }
	      //. std::cerr << "tm_wday = " << union_data.t.tm_wday << std::endl ;
	      stepFmt = 2 ;
	      stepVal = 3 ;
	      nothingFound = 0 ;
	    } else if( memcmp( currFmt, "HH", 2 ) == 0 ) {
	      //. std::cerr << "'HH' found" << std::endl ;
	      memcpy( tmpStr, currVal, 2 );
	      tmpStr[2] = 0 ;
	      list->memArrayResultValue[end].union_data.t.tm_hour = atoi( tmpStr );
	      //. std::cerr << "tm_hour = " << list->memArrayResultValue[end].union_data.t.tm_hour << std::endl ;
	      stepFmt = 2 ;
	      stepVal = 2 ;
	      nothingFound = 0 ;
	    } else if( memcmp( currFmt, "MI", 2 ) == 0 ) {
	      //. std::cerr << "'MI' found" << std::endl ;
	      memcpy( tmpStr, currVal, 2 );
	      tmpStr[2] = 0 ;
	      list->memArrayResultValue[end].union_data.t.tm_min = atoi( tmpStr );
	      //. std::cerr << "tm_min = " << list->memArrayResultValue[end].union_data.t.tm_min << std::endl ;
	      stepFmt = 2 ;
	      stepVal = 2 ;
	      nothingFound = 0 ;
	    } else if( memcmp( currFmt, "SS", 2 ) == 0 ) {
	      //. std::cerr << "'SS' found" << std::endl ;
	      memcpy( tmpStr, currVal, 2 );
	      tmpStr[2] = 0 ;
	      list->memArrayResultValue[end].union_data.t.tm_sec = atoi( tmpStr );
	      //. std::cerr << "tm_sec = " << list->memArrayResultValue[end].union_data.t.tm_sec << std::endl ;
	      stepFmt = 2 ;
	      stepVal = 2 ;
	      nothingFound = 0 ;
	    } else if( memcmp( currFmt, "AM", 2 ) == 0 ) {
	      //. std::cerr << "'AM' found" << std::endl ;
	      memcpy( tmpStr, currVal, 2 );
	      tmpStr[2] = 0 ;
	      if( ( ! memcmp( currVal, "PM", 2 ) ) &&
		  ( list->memArrayResultValue[end].union_data.t.tm_hour < 12 ) ) {
		list->memArrayResultValue[end].union_data.t.tm_hour += 12 ;
	      } else if( ( ! memcmp( currVal, "AM", 2 ) ) &&
		  ( list->memArrayResultValue[end].union_data.t.tm_hour == 12 ) ) {
		list->memArrayResultValue[end].union_data.t.tm_hour = 0 ;
	      }
	      //. std::cerr << "corrected tm_hour = " << list->memArrayResultValue[end].union_data.t.tm_hour << std::endl ;
	      stepFmt = 2 ;
	      stepVal = 2 ;
	      nothingFound = 0 ;
	    }
	  }
	  if( nothingFound ) {
	    //. std::cerr << "char not found '" << currFmt[0] << "'" << std::endl ;
	    if( currFmt[0] != currVal[0] ) {
	      //. std::cerr << "in thread:"<< pthread_self() <<"  value '" << currVal[0] << "' in value '" << currVal << "' does not correspond to waited format '" << currFmt[0] << "' in format '" << currFmt << "'" << "Champ: " << index << std::endl ;
	    }
	    stepFmt = 1 ;
	    stepVal = 1 ;
	  } else {
	    nothingFound = 1 ;
	  }
	  currFmt += stepFmt ;
	  currVal += stepVal ;
	} //. end while curr[0] != 0
	list->memArrayResultValue[end].union_data.t.tm_isdst = 0 ;
      }
    }
    break ;

  case NB_DBAgent::PS_DECIMAL :
    list->memArrayResultValue[end].union_type = NB_ResultValue::NO_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
      throw NB_Error( "Decimal type not supported", 99003 );
    }
    break ;

  case NB_DBAgent::PS_UNKNOWN :
    list->memArrayResultValue[end].union_type = NB_ResultValue::NO_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
      throw NB_Error( "Unknown type recognized", 99003 );
    }
    break ;

  default :
    list->memArrayResultValue[end].union_type = NB_ResultValue::NO_TYPE ;
    if( isNullData ) {
      list->memArrayResultValue[end].isNullFlag = 1 ;
    } else {
      list->memArrayResultValue[end].isNullFlag = 0 ;
    }
    throw NB_Error( "Default type : what is it ?", 99003 );
    break ;
  }

  //.*****************************************************************


  empty = 0 ;
  end++ ;
  list->posArrayResultValue = end ;

} //. end NB_ValueList::add

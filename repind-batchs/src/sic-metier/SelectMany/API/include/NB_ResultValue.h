#if !defined(NB_RESULT_VALUE_H)
#define NB_RESULT_VALUE_H

//============================================================
// File Information
//============================================================
//    File:     NB_ResultValue.h
//  Author:     Mireille HOT
//    Date:     31/01/2006
// Purpose:     Class representing NB_ResultObject instances
//              An object that implements an element of the list
//              NB_ResultObject : it is a row in an SQL statement
//              and looks like the persistence PS_ResultObject

//============================================================
// Imports
//============================================================

#include "NB_DBAgent.h"

class NB_ResultValue {

public:

  friend class NB_ResultObject ; //. see NB_ResultObject::copy_constr
  friend class NB_ValueList ; //. see NB_ValueList::add

  // Constructors
  NB_ResultValue() ;
#ifdef MIREILLE
  NB_ResultValue( NB_DBAgent::DataType* t,
		  const char* val,
		  const char* formatDate,
		  const int isNulldata );
#endif
  NB_ResultValue( const NB_ResultValue& val );

  // Instance Deletion:
  virtual ~NB_ResultValue();

  // Instance Operators:

  // Instance Modifiers:

  // Instance Accessors:
  const int isNull() const ;

  //. cast operators
  operator const char * () const ;
  operator char * () const ;
  operator int () const ;
  operator short () const ;
  operator long () const ;
  operator float () const ;
  operator double () const ;
  operator const struct tm () const ;


private:

  union UNION_DATA {
    char* s ;
    int i ;
    short n ;
    long l ;
    float f ;
    double d ;
    struct tm t ;
  } /* UNION_DATA */ ;
  
  enum DATA_TYPE {
    NO_TYPE = 0,
    CHAR_ETOILE_TYPE = 1,
    INT_TYPE = 2,
    SHORT_TYPE = 3,
    LONG_TYPE = 4,
    FLOAT_TYPE = 5,
    DOUBLE_TYPE = 6,
    TIME_TYPE = 7
  };

  DATA_TYPE union_type ;
  union UNION_DATA union_data ;
  int isNullFlag ;

};

#endif //. NB_RESULT_OBJECT_H


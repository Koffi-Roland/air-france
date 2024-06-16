#if !defined(NB_VALUE_LIST_H)
#define NB_VALUE_LIST_H

//============================================================
// File Information
//============================================================
//    File:     NB_ValueList.h
//  Author:     Mireille HOT
//    Date:     31/01/2006
// Purpose:     Class representing NB_ValueList instances
//              An object that implements the list of 
//              NB_ResultValue with the neque template of 
//              lib stl

//============================================================
// Imports
//============================================================

//. #include <vector>

#include "NB_DBAgent.h"

class NB_ResultValue ;
class NB_ResultObject_Cltn ;

class NB_ValueList {

  friend class NB_ResultObject ; //. see NB_ResultObject::copy_constr

public:

  // Constructors
  //. NB_ValueList();
  NB_ValueList( NB_ResultObject_Cltn* cltn );

  // Instance Deletion:
  virtual ~NB_ValueList();

  // Instance Operators:
  /* const */ NB_ResultValue * operator[]( unsigned int idx ) /* const */ ;

  // Instance Modifiers:
  //. void add( const NB_ResultValue* val );
  void add( NB_DBAgent::DataType t, 
	    const char* val,
	    const char* formatDate,
	    const int isNullData, int index );

  // Instance Accessors:

private:

  //. std::vector<NB_ResultValue> collection ;
  int begin ;
  int end ;
  int empty ;

  NB_ResultObject_Cltn* list ;

};
#endif //. NB_VALUE_LIST_H


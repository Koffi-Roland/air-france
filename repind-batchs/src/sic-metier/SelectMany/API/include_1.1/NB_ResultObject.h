#if !defined(NB_RESULT_OBJECT_H)
#define NB_RESULT_OBJECT_H

//============================================================
// File Information
//============================================================
//    File:     NB_ResultObject.h
//  Author:     Mireille HOT
//    Date:     31/01/2006
// Purpose:     Class representing NB_ResultObject instances
//              An object that implements an element of the list
//              NB_ResultObject : it is a row in an SQL statement
//              and looks like the persistence PS_ResultObject

//============================================================
// Imports
//============================================================

#include "NB_ValueList.h"
#include "NB_PropertyList.h"
#include "NB_ResultValue.h"
#include "NB_ResultProperty.h"

class NB_ResultObject_Cltn ;

class NB_ResultObject {

public:

  // Constructors
  NB_ResultObject() ;
  NB_ResultObject( const NB_ResultObject& val );

  // Instance Deletion:
  virtual ~NB_ResultObject();

  // Instance Operators:

  // Instance Modifiers:
  //. add at end of list
  /*
  void add( NB_ResultValue* val,
	    NB_ResultProperty* prop ) ;
  void add( NB_ResultValue* val,
	    NB_PropertyList* propList );
  */
  void add( NB_DBAgent::DataType t,
	    const char* val,
	    const char* formatDate,
	    const int isNullData,
	    int nbCol,
	    NB_ResultObject_Cltn* cltn, int index );

  // Instance Accessors:
  const NB_ResultValue * valueAt( unsigned int idx ) const ;
  const NB_ResultProperty * propertyAt( unsigned int idx ) const ;
  const NB_ValueList *getValues() const;
  const NB_PropertyList &getProperties() const;


private:

  NB_ValueList* values ;
  NB_PropertyList* properties ;

};

#endif //. NB_RESULT_OBJECT_H


#if !defined(NB_RESULT_OBJECT_CLTN_H)
#define NB_RESULT_OBJECT_CLTN_H

//============================================================
// File Information
//============================================================
//    File:     NB_ResultObject_Cltn.h
//  Author:     Mireille HOT
//    Date:     31/01/2006
// Purpose:     Class representing NB_ResultObject_Cltn instances
//              An object that implements the collection of 
//              NB_ResultObject with the neque template of 
//              lib stl

//============================================================
// Imports
//============================================================

//. #include <vector>

//. class NB_ResultObject ;
//. class NB_PropertyList ;
#include "NB_ResultObject.h"
#include "NB_ResultValue.h"
#include "NB_ResultProperty.h"

class NB_ResultObject_Cltn {

public:

  // Constructors
  NB_ResultObject_Cltn() ;
  NB_ResultObject_Cltn( int nbLig, int nbCol );

  // Instance Deletion:
  virtual ~NB_ResultObject_Cltn();

  // Instance Operators:
  NB_ResultObject_Cltn& operator=( const NB_ResultObject_Cltn & );
  const NB_ResultObject * operator[]( unsigned int idx ) const ;

  // Instance Modifiers:
  //. void add( const NB_ResultObject* obj );

  // Instance Accessors:
  const NB_ResultObject * at( unsigned int idx ) const ;
  int length() const ;
  int extent() const ;
  const NB_PropertyList & getProperties() const ;

  //. others
  NB_ResultObject* newNB_ResultObject() ;

private:

  //. std::vector<NB_ResultObject> collection ;

private :
  //. memory management
//. get up to 1 000 000 records
#define MAX_RECORDS 1000000
//. get up to 5 columns
#define MAX_COLUMNS 1
//. get up to ( columns x records ) values
#define MAX_RESULT_VALUES MAX_COLUMNS * MAX_RECORDS

public :
 
  //. NB_ResultObject memArrayResultObject[MAX_RECORDS] ;
  //. NB_ResultValue memArrayResultValue[MAX_RESULT_VALUES] ;
  //. NB_ResultProperty memArrayResultProperty[MAX_COLUMNS] ;
  NB_ResultObject* memArrayResultObject ;
  NB_ResultValue* memArrayResultValue ;
  NB_ResultProperty* memArrayResultProperty ;
  unsigned int posArrayResultObject ;
  unsigned int posArrayResultValue ;
  unsigned int posArrayResultProperty ;



};

#endif //. NB_SELECT_MANY_OBJECT_H


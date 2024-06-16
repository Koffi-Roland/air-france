#if !defined(NB_PROPERTY_LIST_H)
#define NB_PROPERTY_LIST_H

//============================================================
// File Information
//============================================================
//    File:     NB_PropertyList.h
//  Author:     Mireille HOT
//    Date:     31/01/2006
// Purpose:     Class representing NB_PropertyList instances
//              An object that implements the list of 
//              NB_ResultProperty with the neque template of 
//              lib stl

//============================================================
// Imports
//============================================================

//. #include <deque>
//. #include <vector>

#include "NB_DBAgent.h"

class NB_ResultProperty ;
class NB_ResultObject_Cltn  ;

class NB_PropertyList {

  friend class NB_ResultObject ; //. see NB_ResultObject::copy_constr

public:

  // Constructors
  NB_PropertyList( NB_ResultObject_Cltn* cltn );

  // Instance Deletion:
  virtual ~NB_PropertyList();

  // Instance Operators:
  const NB_ResultProperty * operator[]( unsigned int idx ) const ;

  // Instance Modifiers:
  //. void setProperties(const PS_PropertyList &prpList);
  //. void setValues(const PS_ValueList *valList);
  //. void add( const NB_ResultProperty* prop );
  void add( NB_DBAgent::DataType t, int nbCol );

  // Instance Accessors:
  //. PS_ResultProperty * propertyAt(unsigned int indx);
  //. PS_ResultProperty * propertyAt(unsigned int indx) const;
  //. PS_ResultValue * valueAt(unsigned int indx);
  //. PS_ResultValue * valueAt(unsigned int indx) const;
  //. const PS_PropertyList &getProperties() const;
  //. const PS_ValueList *getValues() const;
  //. int length() ;
  int extent() const ;

private:

  //. std::vector<NB_ResultProperty> collection ;
  int begin ;
  int end ;
  int empty ;

  NB_ResultObject_Cltn *list ;

};

#endif //. NB_PROPERTY_LIST_H


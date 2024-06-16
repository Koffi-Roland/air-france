#if !defined(NB_RESULT_PROPERTY_H)
#define NB_RESULT_PROPERTY_H

//============================================================
// File Information
//============================================================
//    File:     NB_ResultProperty.h
//  Author:     Mireille HOT
//    Date:     31/01/2006
// Purpose:     Class representing NB_ResultProperty instances
//              An object that gets some types that are defined
//              by Persistence in PS_DBAgent and got through a
//              static method of PS_ResultProperty

//============================================================
// Imports
//============================================================

#include "NB_DBAgent.h"

class NB_ResultProperty {

public:

  friend class NB_ResultObject ; //. see NB_ResultObject::copy_constr
  friend class NB_PropertyList ; //. see NB_PropertyList::add

  // Constructors
  NB_ResultProperty() ;
  NB_ResultProperty( NB_DBAgent::DataType t );
  NB_ResultProperty( const NB_ResultProperty& val );

  // Instance Deletion:
  virtual ~NB_ResultProperty();

  // Instance Operators:

  // Instance Modifiers:

  // Instance Accessors:
  NB_DBAgent::DataType getType() const ;
  

private:

  NB_DBAgent::DataType type ;

  void setType( NB_DBAgent::DataType t );
};

#endif //. NB_RESULT_PROPERTY_H


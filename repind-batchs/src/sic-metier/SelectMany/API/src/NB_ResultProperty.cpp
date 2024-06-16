//============================================================
// File Information
//============================================================
//    File:     NB_ResultProperty.cpp
//  Author:     Mireille HOT
//    Date:     01/02/2006
// Purpose:     implements NB_ResultProperty
//============================================================

#include <iostream>

#include "NB_ResultProperty.h"

NB_ResultProperty::NB_ResultProperty() {
  type = NB_DBAgent::PS_UNKNOWN ;
}

NB_ResultProperty::NB_ResultProperty( NB_DBAgent::DataType t ) {
  type = t ;
}

NB_ResultProperty::NB_ResultProperty( const NB_ResultProperty& val ) {
  type = val.type ;
}

NB_ResultProperty::~NB_ResultProperty() {
}

NB_DBAgent::DataType NB_ResultProperty::getType() const {
  return type ;
}


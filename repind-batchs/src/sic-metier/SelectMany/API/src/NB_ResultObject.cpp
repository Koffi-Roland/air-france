//============================================================
// File Information
//============================================================
//    File:     NB_ResultObject.cpp
//  Author:     Mireille HOT
//    Date:     01/02/2006
// Purpose:     implements NB_ResultObject
//============================================================

#include <iostream>

#include "BasicException.h" //. NB_Error
#include "NB_ResultObject_Cltn.h" //. param for add
#include "NB_ResultObject.h"

NB_ResultObject::NB_ResultObject() {
  values = NULL ;
  properties = NULL ;
}


NB_ResultObject::NB_ResultObject( const NB_ResultObject& val ) {
  //. std::cerr << (void*) this << " - NB_ResultObject::copy_constr" << std::endl ;
}


NB_ResultObject::~NB_ResultObject() {
  if( values ) {
    delete values ;
    values = NULL ;
  }
  if( properties ) {
    delete properties ;
    properties = NULL ;
  }
}

void NB_ResultObject::add( NB_DBAgent::DataType t,
			   const char* val,
			   const char* formatDate,
			   const int isNullData,
			   int nbCol,
			   NB_ResultObject_Cltn* cltn, int index ) {

  //. std::cerr << (void*) this << " - NB_ResultObject::add col " << nbCol << std::endl ;
  if( values == NULL ) {
    try {
      values = new NB_ValueList( cltn );
    } catch( ... ) {
      throw NB_Error( "new NB_ValueList failed in NB_ResultObject::add( val, list )", 99000 );
    }
  }
  values->add( t, val, formatDate, isNullData, index );

  if( properties == NULL ) {
    try {
      properties = new NB_PropertyList( cltn );
    } catch( ... ) {
      throw NB_Error( "new NB_PropertyList failed in NB_ResultObject::add", 99000 );
    }
  }
  properties->add( t, nbCol );
}

const NB_ResultValue* NB_ResultObject::valueAt( unsigned int idx ) const {
  return (*values)[ idx ];
}

const NB_ResultProperty* NB_ResultObject::propertyAt( unsigned int idx ) const {
  return (*properties)[ idx ];
}

const NB_ValueList* NB_ResultObject::getValues() const {
  return values ;
}

const NB_PropertyList& NB_ResultObject::getProperties() const {
  return *properties ;
}

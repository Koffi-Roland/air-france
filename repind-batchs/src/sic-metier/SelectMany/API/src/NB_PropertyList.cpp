//============================================================
// File Information
//============================================================
//    File:     NB_PropertyList.cpp
//  Author:     Mireille HOT
//    Date:     01/02/2006
// Purpose:     implements NB_PropertyList
//============================================================

#include <iostream>

#include "NB_PropertyList.h"
#include "NB_ResultProperty.h"
#include "NB_ResultObject_Cltn.h"
#include "BasicException.h"

NB_PropertyList::NB_PropertyList( NB_ResultObject_Cltn* cltn ) {
  begin = 0 ;
  end = cltn->posArrayResultProperty ;
  //. only one property list for all the resultValue list
  //. it is build by the first record analysis
  if( end != 0 ) {
    empty = 0 ;
  } else {
    empty = 1 ;
  }
  list = cltn ;
}

NB_PropertyList::~NB_PropertyList() {
  if( empty != 1 ) {
    begin = 0 ;
    end = 0 ;
    empty = 1 ;
  }
  list = NULL ;
}

const NB_ResultProperty * NB_PropertyList::operator[]( unsigned int idx ) const {
  if( end < begin + idx ) {
    throw NB_Error( "NB_PropertyList::operator[] failed" );
  } else {
    return &( list->memArrayResultProperty[begin+idx] );
  }
}

void NB_PropertyList::add( NB_DBAgent::DataType t, int nbCol ) {
  if( end < nbCol ) {
    list->memArrayResultProperty[end].type = t ;
    end++ ;
    list->posArrayResultProperty = end ;
    empty = 0 ;
    //. std::cerr << "end (" << end << ") < nbcol (" << nbCol
    //.       << ") so type[" << end << "] = "
    //.       << t << std::endl ;
  } else {
    //. std::cerr << "end (" << end << ") >= nbcol (" << nbCol
    //.       << ") so does nothing" << std::endl ;
  }
}

int NB_PropertyList::extent() const {
  return ( end - begin );
}

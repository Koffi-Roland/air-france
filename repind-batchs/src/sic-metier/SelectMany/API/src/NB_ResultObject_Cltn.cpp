//============================================================
// File Information
//============================================================
//    File:     NB_ResultObject_Cltn.cpp
//  Author:     Mireille HOT
//    Date:     01/02/2006
// Purpose:     implements NB_ResultObject_Cltn
//============================================================

#include <iostream>

#include "NB_ResultObject_Cltn.h"
#include "NB_ResultObject.h"
#include "BasicException.h"

NB_ResultObject_Cltn::NB_ResultObject_Cltn() {
  std::cerr << std::endl << "*****************************************"
	    << std::endl << "Why is it called ?"
	    << std::endl << "*****************************************"
	    << std::endl ;
  posArrayResultObject = 0 ;
  posArrayResultValue = 0 ;
  posArrayResultProperty = 0 ;
}

NB_ResultObject_Cltn::NB_ResultObject_Cltn( int nbLig, int nbCol ) {
  //. std::cerr << (void*) this << " - NB_ResultObject_Cltn::constr" << std::endl ;

  memArrayResultObject = new NB_ResultObject[ nbLig ];
  memArrayResultValue = new NB_ResultValue[ nbLig * nbCol ];
  memArrayResultProperty = new NB_ResultProperty[ nbCol ];

  posArrayResultObject = 0 ;
  posArrayResultValue = 0 ;
  posArrayResultProperty = 0 ;
}

NB_ResultObject_Cltn::~NB_ResultObject_Cltn() {
  delete [] memArrayResultObject ;
  //. if (!memArrayResultValue->isNull()){
  //. std::cerr << "OA & MH : try to understand why test isNull needed !!!" << std::endl ;
  delete [] memArrayResultValue ;
    //. }
  delete [] memArrayResultProperty ;
}

NB_ResultObject_Cltn& NB_ResultObject_Cltn::operator=( const NB_ResultObject_Cltn & c ) {
  return *this ;
}

const NB_ResultObject* NB_ResultObject_Cltn::operator[]( unsigned int idx ) const {
  return &( memArrayResultObject[idx] );
}

const NB_ResultObject* NB_ResultObject_Cltn::at( unsigned int idx ) const {
  if( idx < MAX_RECORDS ) {
    return &( memArrayResultObject[ idx ] );
  } else {
    throw NB_Error( "NB_ResultObject_Cltn failed", 99004  );
  }
}

int NB_ResultObject_Cltn::length() const {
  return posArrayResultObject ;
}

int NB_ResultObject_Cltn::extent() const {
  return posArrayResultObject ;
}

const NB_PropertyList & NB_ResultObject_Cltn::getProperties() const {
  return at(0)->getProperties() ;
}

NB_ResultObject* NB_ResultObject_Cltn::newNB_ResultObject() {
  if( posArrayResultObject < MAX_RECORDS ) {
    return &( memArrayResultObject[posArrayResultObject++] );
  } else {
    throw NB_Error( "No place left for an object", 99004 );
  }
}

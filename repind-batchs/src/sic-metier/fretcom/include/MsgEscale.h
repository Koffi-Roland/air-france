//f
//----------------------------------------------------------------------
//     FILE        : MsgEscale.h
//
//     DESCRIPTION : msg station object
//                   
//     AUTHOR      : apie
//     DATE        : 19/02/03
//----------------------------------------------------------------------
//f

#ifndef _MSGESCALE_H
#define _MSGESCALE_H

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <iostream>
#include <string>
#include <ctime>
#include <ctime>
#include "MsgLevel.h"
#include "DefinesTools.h"
#include "MemoryMgr.h"
//#include "ExceptionMsg.h"
#include "Msg.h"
#ifdef ADHESION
#include "COMCOM_Common.h"
#endif


class MsgEscale : public Msg
{
public:
  MsgEscale(){_codeEscale = "";}
  MsgEscale(std::string oCodeEscale);
  virtual ~MsgEscale(){}
  MsgEscale( const MsgEscale & msg )  { operator=(msg); }
  MsgEscale & operator=( const MsgEscale & right ); 
#ifdef ADHESION
  void decode( WCodeEscale_Type  & in ); 
  void encode( WCodeEscale_Type  & out );
  void decode( WCodeSecteur_Type  & in ); 
  void encode( WCodeSecteur_Type  & out );
#endif
#ifdef DEBUG
  virtual void display( std::ostream & os ) const ;
  
#endif
  const std::string getCodeEscale() const {return _codeEscale;}
  void setCodeEscale(const std::string& value){_codeEscale = value ;}
private:
    std::string  _codeEscale;  // Msg is valid.
  
};

#endif

//f
//----------------------------------------------------------------------
//     FILE        : Msg.h
//
//     DESCRIPTION : Common msg object
//                   
//     AUTHOR      : nes
//     DATE        : 15/10/97
//----------------------------------------------------------------------
//f

#ifndef _MSG_H
#define _MSG_H

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <iostream>
#include <string>
#include <ctime>
#include <ctime>
#include "MsgLevel.h"
#include "ExceptionMsgCom.h"

#ifdef ADHESION
#include "COMCOM_Common.h"
#endif


class Msg MEM_OBJ
{
public:
  
    Msg( ) : _valid( false ) {}
    Msg( bool valid ) : _valid( valid ) {}
    Msg( const Msg & msg ) : _valid( msg.getValid() ) {}

    virtual ~Msg(){}
    Msg & operator=( const Msg & right );

    bool   getValid() const { return _valid; }
    bool   isValid()  const { return _valid; }
    void  	setValid( bool value = true ) { _valid = value; }

    virtual void handleException(ExceptionCode aErr, const std::string &aErrStr) {}

#ifdef DEBUG
    virtual void display( std::ostream & os ) const { os << _valid; }
    friend std::ostream & operator<<( std::ostream & os , const Msg & msg ) { msg.display( os ); return os; }
#endif

    // Encoding tools
    static void		Copy(char* dst, const std::string& s, short length);
    static void		Copy(char* dst, const struct tm & d);
    static void		Copy(char* dst, const time_t & t, bool withDate=true);
    static void		Copy(char* dst, long l, short length);
    static void		Copy(char* dst, double d, short length);
    static void		Copy(char* dst, double d, short decimalNb, short length);
    static std::string	Padd(const std::string& s, short length , char paddChar = '#' );

    // Decoding tools
    static double	Copy(char* s, short decimalNb);

    // Conversion tools
    static std::string	IHMString(const struct tm &d );
    static std::string	IHMString(long l);
    static struct tm	IHMStringDate(const std::string &s);
    static time_t	IHMStringTime(const std::string &s);
  static std::string getFormatedStringValue(double val,
					  int decimalPlaces,
					  double approx);
  static std::string IHMTimeOnlyString(const time_t &t);
private:
    bool  _valid;  // Msg is valid.
  
};

#endif

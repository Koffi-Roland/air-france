//f
//----------------------------------------------------------------------
//     FILE NAME   : ToolsDate.h
//
//     DESCRIPTION : Common Date tools interface.
//
//     DATE        : 30/09/1997
//----------------------------------------------------------------------
//f

#ifndef ToolsDate_h
#define ToolsDate_h 1
#include <string>
class ToolsDate;

// for compatibility with Yield
typedef ToolsDate ToolsYMF;


// *** Definitions
//class std::string;
//class struct tm;
//class struct tm;

// IHM number types
const char __IHMNumberVolume	= 'V';
const char __IHMNumberWeight	= 'P';
const char __IHMNumberClassNb	= 'C';


// *** Class definition
class ToolsDate
{
public:
    // Tools for converting struct tm and struct tm
    static std::string	OracleDate(const struct tm &d);
    static std::string	OracleDateReal(const struct tm &d);
    static struct tm	OracleDate(const std::string &s);
    static struct tm	OracleDateLEP(const std::string& s);

    static std::string	OracleTime(const struct tm &t);
    static struct tm	OracleTime(const std::string &s);
    static struct tm	OracleTimeWithMonth(const std::string &s);

    static std::string	PelicanDate(const struct tm &d);
    static std::string	PelicanTime(const struct tm &t);
    static std::string	PelicanOnlyTime(const struct tm &t);

    static struct tm	PelicanTime(const std::string &s);
    static struct tm	PelicanTime(const std::string &s,const struct tm &d);
    static struct tm	PelicanDate(const std::string &s);
  
    static std::string	AbbrevMonth(const struct tm &d);

    // Tools for sending and receiving string to IHM
    static std::string	IHMStringTyped(const struct tm &d);
    static std::string    IHMStringForLog( const struct tm &t);
    //static std::string	IHMString(const struct tm &d);
    static std::string	IHMString(const struct tm &t);
    static std::string	IHMStringTime(const struct tm &t);
    static std::string	IHMStringSmallTime(const struct tm &t);
    static std::string	IHMStringTyped(double d, char aNumberType);
    static std::string	IHMString(double d);
    static std::string	IHMStringTyped(long l, char aNumberType);
    static std::string	IHMString(long l);

    static struct tm	IHMStringTime(const std::string &s);
    static struct tm	IHMStringDate(const std::string &s);
};

// Class ToolsDate 

#endif

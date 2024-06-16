//f
//----------------------------------------------------------------------
//     FILE NAME   : ToolsDate.C
//
//     DESCRIPTION : Common Date tools implementation.
//
//     DATE        : 30/09/1997
//----------------------------------------------------------------------
//f

#include "ToolsDate.h"
#include "DefinesTools.h"
#include <string>
#include <ctime>
#include <ctime>
#include <stdlib.h>

// for MT_Safe
//#include <mutex>

const char __IHMDatePrefix   = '$';
const char __IHMNumberPrefix = '#';


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::OracleDate
//
//     DESCRIPTION : Convert a date to a string that can be sent
//                   to Oracle.
//                   Format : DDMMYYYY
//                       Ex : 01101997
//
//     INPUT       : The date to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::OracleDate(const struct tm &d)
{

  char buffer[80];
  
  strftime(buffer,sizeof(buffer),"%d%m%Y",&d);

  return std::string(buffer);

} // OracleDate

//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::OracleDateReal
//
//     DESCRIPTION : Convert a date to a string that can be sent
//                   to DIRECTLY Oracle.
//                   Format : DD-MMM-YY
//                       Ex : 01-OCT-97
//		     ###WARNING### : this format is NOT savy for
//                   dates upper than 31-DEC-99 !!!!
//
//     INPUT       : The date to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::OracleDateReal(const struct tm &d)
{
    DEBUGMSG("NOT YEAR 2000 SAVVY");

    char buffer[80];

    strftime(buffer,sizeof(buffer),"%F %T",&d);

    return std::string(buffer);

} // OracleDateReal


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::OracleDate
//
//     DESCRIPTION : Convert a string from Oracle to a date.
//                   String format 1 : DD-MMM-YY
//                                Ex : 01-OCT-97
//                     ###WARNING### : this format is NOT savy for
//                     dates upper than 31-DEC-99 !!!!
//                     e.g. 01-OCT-05 will give 01/10/1905...
//                   String format 2 : DD-MMM-YYYY
//                                Ex : 01-OCT-1997
//
//     INPUT       : The formatted string. Please use format 2.
//     OUTPUT      : The resulting date.
//----------------------------------------------------------------------
//f

struct tm ToolsDate::OracleDate(const std::string &s)
{
//pel TODO to check 
   ASSERT(s.size() >= 9);
   // std::string s_with_century = 
   //   s.size() == 11 ? s : 
     // s.substubstr(0,7) + (atoi( std::string( s.substr(8, 2) ).data() )  < 90 ? "20" : "19")  + s.substr(8,2);

#ifdef DEBUG
    struct tm tm;
    if (s.size() == 11)
      strptime(s.c_str(), "%d-%m-%Y", &tm);
    else if(s.size() == 9)
      strptime(s.c_str(), "%d-%m-%y", &tm);
    //ASSERT(d.isValid() == true);
    return tm;
#else
    struct tm tm;
    if (s.size() == 11)
      strptime(s.c_str(), "%d-%m-%Y", &tm);
    else if(s.size() == 9)
      strptime(s.c_str(), "%d-%m-%y", &tm);
    return tm;
#endif

} // OracleDate


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::OracleDateLEP
//
//     DESCRIPTION : Convert a std::string to a struct tm
//                   Format : DDMMYYYY
//
//     INPUT       : The string to convert.
//     OUTPUT      : struct tm
//---------------------------------------------------------------------- 
//f

struct tm ToolsDate::OracleDateLEP(const std::string& s)
{
    ASSERT(s.size() == 8);  

#ifdef DEBUG
    //struct tm d(s(2, 2) + "/" + s(0, 2) + "/" + s(4, 4));
    //ASSERT(d.isValid() == true);
    struct tm tm;
    strptime(s.c_str(), "%d/%m/%Y", &tm);
    return tm;
#else
    struct tm tm;
    strptime(s.c_str(), "%d/%m/%Y", &tm);
    return tm;
#endif

} // OracleDateLEP


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::OracleTime
//
//     DESCRIPTION : Convert a time to a string that can be sent
//                   to Oracle.
//                   Format : DDMMYYYYHHMMSS
//                       Ex : 01101997121123
//
//     INPUT       : The time to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::OracleTime(const struct tm  &t)
{
   // ASSERT(t.isValid() == true);
//pel    return t.asString('d') + t.asString('m') + t.asString('Y') +
//	   t.asString('H') + t.asString('M') + t.asString('S');
  char buffer[80];

  strftime(buffer,sizeof(buffer),"%d%m%Y%H%M%s",&t);

  return std::string(buffer);

} // OracleTime


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::OracleTime
//
//     DESCRIPTION : Convert a string from Oracle to a time.
//                   String format : DDMMYYYYHHMMSS
//                              ex : 01101997121123
//
//     INPUT       : The formatted string.
//     OUTPUT      : The resulting time.
//----------------------------------------------------------------------
//f

struct tm ToolsDate::OracleTime(const std::string &s)
{
    ASSERT(s.size() == 14);
#ifdef DEBUG
//    ASSERT(t.isValid() == true);
    struct tm tm;
    strptime(s.c_str(), "%d%m%Y%H%M%S", &tm);
    return tm;
#else
    struct tm tm;
    //strptime(s.c_str(), "%d/%m/%Y%H:%M:%S", &tm);
    strptime(s.c_str(), "%d%m%Y%H%M%S", &tm);
    return tm;
#endif

} // OracleTime


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::OracleTimeWithMonth
//
//     DESCRIPTION : Convert a string from Oracle to a time.
//                   String format : DD-MMM-YYYYHHMMSS
//                              ex : 01-OCT-1997121123
//
//     INPUT       : The formatted string.
//     OUTPUT      : The resulting time.
//----------------------------------------------------------------------
//f

struct tm ToolsDate::OracleTimeWithMonth(const std::string &s)
{
    ASSERT(s.size() == 17);
#ifdef DEBUG
    //ASSERT(t.isValid() == true);
    struct tm tm;
    strptime(s.c_str(), "%F %T%H:%M:%S", &tm);
    return tm;
#else
    //return RWTime(struct tm(s(0, 11)),                               // Date
//	          s(11, 2) + ":" + s(13, 2) + ":" + s(15, 2));    // Time
    struct tm tm;
    strptime(s.c_str(), "%F %T%H:%M:%S", &tm);
    return tm;
#endif

} // OracleTimeWithMonth


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::PelicanDate
//
//     DESCRIPTION : Convert a date to a string that can be sent
//                   to Pelican.
//                   Format : DDMMMYYYY
//                       Ex : 01OCT1997
//
//     INPUT       : The date to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::PelicanDate(const struct tm &d)
{
    //ASSERT(d.isValid() == true);
//    return d.asString('d') + AbbrevMonth(d) + d.asString('Y');
    char buffer[80];

    strftime(buffer,sizeof(buffer),"%d%b%Y",&d);

    return std::string(buffer);

} // PelicanDate


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::PelicanTime
//
//     DESCRIPTION : Convert a time to a string that can be sent
//                   to Pelican.
//                   Format : DDMMMYYYYHHMM
//                       Ex : 01OCT19971223
//
//     INPUT       : The time to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::PelicanTime(const struct tm &t)
{
    //ASSERT(t.isValid() == true);
  //  return t.asString('d') + AbbrevMonth(struct tm(t)) + t.asString('Y') +
//	   t.asString('H') + t.asString('M');
    char buffer[80];

    strftime(buffer,sizeof(buffer),"%d%b%Y%H%M",&t);

    return std::string(buffer);

} // PelicanTime


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::PelicanTime
//
//     DESCRIPTION : Convert a string from Pelican to a time.
//                   String format : DDMMMYYYYHHMM
//                              ex : 01OCT19971211
//
//     INPUT       : The formatted string.
//     OUTPUT      : The resulting time.
//----------------------------------------------------------------------
//f

struct tm ToolsDate::PelicanTime(const std::string &s)
{
    ASSERT(s.size() == 13);

#ifdef DEBUG
//    ASSERT(t.isValid() == true);
    struct tm tm;
    strptime(s.c_str(), "%F %T%H:%M:00", &tm);
    return tm;

#else
  //  return RWTime(struct tm(s(0, 2) + "-" + s(2, 3) + "-" + s(5, 4) ), // Date
//		  s(9, 2) + ":" + s(11, 2) + ":00" );    // Time
    struct tm tm;
    strptime(s.c_str(), "%F %T%H:%M:00", &tm);
    return tm;
#endif

} // PelicanTime


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::PelicanTime
//
//     DESCRIPTION : Convert a string from Pelican to a time for the
//                   d date.
//                   String format : HHMM
//                              ex : 1211
//
//     INPUT       : The formatted string.
//     OUTPUT      : The resulting time.
//----------------------------------------------------------------------
//f

struct tm ToolsDate::PelicanTime(const std::string &s, const struct tm &d)
{
    ASSERT(s.size() == 4);

#ifdef DEBUG
    //ASSERT(t.isValid() == true);
    struct tm tm;
    strptime(s.c_str(), "%H:%M:00", &tm);
    return tm;
#else
  //  return RWTime( d, s(0, 2) + ":" + s(2, 2) + ":00" );    // Time
    struct tm tm;
    strptime(s.c_str(), "%H:%M:00", &tm);
    return tm;
#endif

} // PelicanTime


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::PelicanDate
//
//     DESCRIPTION : Convert a string from Pelican file to a date.
//                   String format : DDMMMYYYY
//                              ex : 01OCT1997
//
//     INPUT       : The formatted string.
//     OUTPUT      : The resulting time.
//----------------------------------------------------------------------
//f

struct tm ToolsDate::PelicanDate(const std::string &s)
{
    ASSERT(s.size() == 9);

#ifdef DEBUG
    //ASSERT(d.isValid() == true);
    struct tm tm;
    strptime(s.c_str(), "%F %T", &tm);
    return tm;
#else 
    //struct tm d(%d-%M-%Y); // Date
    struct tm tm;
    strptime(s.c_str(), "%F %T", &tm);
    return tm;
#endif

} // PelicanDate


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::PelicanOnlyTime
//
//     DESCRIPTION : Convert a time to a string that can be sent
//                   to Pelican.
//                   Format : HHMM
//                       Ex : 1223
//
//     INPUT       : The time to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::PelicanOnlyTime(const struct tm &t)
{
  //  ASSERT(t.isValid() == true);
//    return t.asString('H') + t.asString('M');
    char buffer[80];

    strftime(buffer,sizeof(buffer),"%H%M",&t);

    return std::string(buffer);
    

} // PelicanOnlyTime


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::AbbrevMonth
//
//     DESCRIPTION : Get the abbreviated name of the given date month.
//                   Ex : JAN, FEB, MAR, APR, etc.
//
//     INPUT       : The reference date.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::AbbrevMonth(const tm&)
{
const char * months[12] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

return std::string(months[0]);

} // AbbrevMonth



//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMStringTyped
//
//     DESCRIPTION : Convert a date to a string that can be sent
//                   to the IHM.
//                   Format : $YYYYMMDD
//
//     INPUT       : The date to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::IHMStringTyped(const struct tm &d)
{
    std::string s;
    s+=__IHMDatePrefix;
    //ASSERT(d.isValid() == true);
    s += IHMString(d);
    return s;

} // IHMStringTyped


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMString
//
//     DESCRIPTION : Convert a date to a string that can be sent
//                   to the IHM.
//                   Format : YYYYMMDD
//
//     INPUT       : The date to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::IHMString(const struct tm &d)
{
  //  if (d.isValid())
//	return d.asString('Y') + d.asString('m') + d.asString('d');
  char buffer[80];

  strftime(buffer,sizeof(buffer),"%Y%m%d",&d);

  return std::string(buffer);

} // IHMString


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMString
//
//     DESCRIPTION : Convert a time to a string that can be sent
//                   to the IHM.
//                   Format : YYYYMMDDHHMM
//
//     INPUT       : The time/date to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

/*std::string ToolsDate::IHMString(const struct tm &t)
{
//    if (t.isValid())
//	return t.asString('Y') + t.asString('m') + t.asString('d') + t.asString('H') + t.asString('M');
  //  return "";
  char buffer[80];

  strftime(buffer,sizeof(buffer),"%Y%m%d%H%M",&d);

  return std::string(buffer);

} // IHMString

*/
//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMStringTime
//
//     DESCRIPTION : Convert a time to a string that can be sent
//                   to the IHM.
//                   Format : HHMMSS
//
//     INPUT       : The time/date to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::IHMStringTime(const struct tm &t)
{
//    ASSERT(t.isValid() == true);
//    return t.asString('H') + t.asString('M') + t.asString('S');
  char buffer[80];

  strftime(buffer,sizeof(buffer),"%H%M%S",&t);

  return std::string(buffer);

} // IHMStringTime


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMStringSmallTime
//
//     DESCRIPTION : Convert a time to a string that can be sent
//                   to the IHM.
//                   Format : HHMM
//
//     INPUT       : The time/date to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::IHMStringSmallTime(const struct tm &t)
{
//    ASSERT(t.isValid() == true);
//    return t.asString('H') + t.asString('M');
  char buffer[80];

  strftime(buffer,sizeof(buffer),"%H%M",&t);

  return std::string(buffer);

} // IHMStringSmallTime


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMStringDate
//
//     DESCRIPTION : Convert a string to a date received from the IHM.
//                   Format : YYYYMMDD -> struct tm
//
//     INPUT       : The string to convert.
//     OUTPUT      : The struct tm
//----------------------------------------------------------------------
//f

struct tm ToolsDate::IHMStringDate(const std::string & s)
{
    // ASSERT(s.size() == 8);
#ifdef DEBUG
    //struct tm d(s(4, 2) + "/" + s(6, 2) + "/" + s(0, 4));
    struct tm d;
    strptime(s.c_str(), "%Y/%m/%d", &d);
   // ASSERT(d.isValid() == true);
    return d;
#else
    struct tm d;
    strptime(s.c_str(), "%Y/%m/%d", &d);
    return d;
#endif

} // IHMStringDate


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMStringTime
//
//     DESCRIPTION : Convert a string to a time received from the IHM.
//                   Format : YYYYMMDDHHMM -> RWTime
//
//     INPUT       : The string to convert.
//     OUTPUT      : The RWTime
//----------------------------------------------------------------------
//f

struct tm ToolsDate::IHMStringTime(const std::string & s)
{
    ASSERT(s.size() == 12);
#ifdef DEBUG
    struct tm tm;
    strptime(s.c_str(), "%Y%m%d%H%m", &tm);
//    RWTime t(struct tm(s(4, 2) + "/" + s(6, 2) + "/" + s(0, 4)),	// Date
//	     s(8, 2) + ":" + s(10, 2) + ":00");			// Time
  //  ASSERT(t.isValid() == true);
    return tm;
#else
   // return RWTime(struct tm(s(4, 2) + "/" + s(6, 2) + "/" + s(0, 4)),	// Date
//		  s(8, 2) + ":" + s(10, 2) + ":00");			// Time
    struct tm tm;
    strptime(s.c_str(), "%Y%m%d%H%m", &tm);
    return tm;
#endif

} // IHMStringTime


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMStringTyped
//
//     DESCRIPTION : Convert a double to a string that can be sent
//                   to the IHM.
//                   Format : #TNNNN.DD (where T is the type)
//
//     INPUT       : The double to convert and its type.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::IHMStringTyped(double d, char aNumberType)
{
    std::string s;
    s+=__IHMNumberPrefix;
    s += aNumberType;
    s += IHMString(d);
    return s;

} // IHMStringTyped


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMString
//
//     DESCRIPTION : Convert a double to a string that can be sent
//                   to the IHM.
//                   Format : NNNN.DD
//
//     INPUT       : The double to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::IHMString(double d)
{
  //  return RWLocale::global().asString(d, 2);
  std::string date;
  return date;

} // IHMString


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMStringTyped
//
//     DESCRIPTION : Convert a long integer to a string that can be sent
//                   to the IHM.
//                   Format : #TNNNN (where T is the type)
//
//     INPUT       : The long integer to convert and its type.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::IHMStringTyped(long l, char aNumberType)
{
    std::string s;
    s+=__IHMNumberPrefix;
    s += aNumberType;
    s += IHMString(l);
  //  return s;
  std::string date;
  return date;

} // IHMStringTyped


//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMString
//
//     DESCRIPTION : Convert a long integer to a string that can be sent
//                   to the IHM.
//                   Format : NNNN
//
//     INPUT       : The long integer to convert.
//     OUTPUT      : The formatted string.
//----------------------------------------------------------------------
//f

std::string ToolsDate::IHMString(long l)
{
  //  return RWLocale::global().asString(l);
  std::string date;
  return date;

} // IHMString

//f
//----------------------------------------------------------------------
//     METHOD      : ToolsDate::IHMStringForLog
//
//     DESCRIPTION : return tiem for log : ddMMMYY - HH:MM
//                   22MAR98 - 08:52
//----------------------------------------------------------------------
//f

std::string ToolsDate::IHMStringForLog( const struct tm &t)
{
  //return t.asString('d') + t.asString('b') + t.asString('Y') + " - " +
    //t.asString('H') + ":" + t.asString('M');
  char buffer[80];

  strftime(buffer,sizeof(buffer),"%d%m%Y%H%M",&t);

  return std::string(buffer);

}

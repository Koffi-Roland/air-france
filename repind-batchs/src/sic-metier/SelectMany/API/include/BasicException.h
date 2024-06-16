/****************************************************************************
*   Source Name : BasicException.h
*       Version : 27/05/1997 20:04
*        Author : Pierre
*       Purpose : Exception Handling.
****************************************************************************/

#ifndef __BASIC_EXCEPTION_H_
#define __BASIC_EXCEPTION_H_

#include <ctype.h>
//#include <rw/cstring.h>

#ifndef HAVE_NO_IOSTREAM
#ifdef __GNUC__
#include <iostream>
#else
#include <iostream.h>
#endif
#endif

#include <exception>


// *** Definitions
typedef unsigned long			ExceptionType;
typedef long				ExceptionCode;
typedef long				ExceptionModule;

typedef char				ExceptionRejectType;


#ifdef __GNUC__
const	ExceptionModule			kExModule_NULL  	= 0;
const	ExceptionType			kExType_Cancel		= 1;
const	ExceptionType			kExType_Warning 	= 2;
const	ExceptionType			kExType_Fatal		= 3;
const	ExceptionType			kExType_Silent		= 4;
const	ExceptionType			kExType_Altern    	= 5;
#else
const	ExceptionModule			kExModule_NULL  	= 'NULL';
const	ExceptionType			kExType_Cancel		= 'CNCL';
const	ExceptionType			kExType_Warning 	= 'WARN';
const	ExceptionType			kExType_Fatal		= 'FATA';
const	ExceptionType			kExType_Silent		= 'SLNT';
const	ExceptionType			kExType_Altern    	= 'ALTN';
#endif
const	ExceptionType			_CANCEL_ = kExType_Cancel;
const	ExceptionType			_WARN_ 	= kExType_Warning;
const	ExceptionType			_FATAL_ = kExType_Fatal;
const	ExceptionType			_SILENT_ = kExType_Silent;
const	ExceptionType			_ALTERN_ = kExType_Altern;

const	ExceptionCode			kDefaultExcepCode	= 1L;
 
const   ExceptionRejectType             kExReject_Control    = 'G';  // Gestion
const   ExceptionRejectType             kExReject_Technic    = 'T';  // Technique
const   ExceptionRejectType             kExReject_Manual     = 'M';  // Manuel
const   ExceptionRejectType             kExReject_ClassCapa  = 'C';  // Capacite Class
const   ExceptionRejectType             kExReject_FlightCapa = 'V';  // Capacite Vol
const   ExceptionRejectType             kExReject_Pricing    = 'H';  // Hors Seuil
const   ExceptionRejectType             kExReject_Palletisation = 'P';  // Hors Seuil
const   ExceptionRejectType             kExReject_System = 'S';  // System
/////* strlcpy based on OpenBSDs strlcpy */
#include <sys/types.h>
size_t strlcpy(char *, const char *, size_t);

// *** Class definition
class BasicException {
#ifndef HAVE_NO_IOSTREAM
#ifdef __GNUC__
    static std::ostream& translate(long , std::ostream& ostr = std::cout);
#else
    static ostream& translate(long , ostream& os = cout);
#endif
#endif

public:
    BasicException();

#ifndef HAVE_NO_IOSTREAM
#ifdef __GNUC__
    BasicException( ExceptionModule aModule, 
		    ExceptionType aType,
		    ExceptionCode aCode,
		    std::string aInfo = "",
		    ExceptionRejectType aReject = kExReject_System );
#else
    BasicException( ExceptionModule aModule, 
		    ExceptionType aType,
		    ExceptionCode aCode,
		    RWCString aInfo = "",
		    ExceptionRejectType aReject = kExReject_System );
#endif
#endif
//    BasicException( const BasicException& aException ) { operator=( aException ); }
    BasicException& operator=( const BasicException& aException );

//#ifndef HAVE_NO_IOSTREAM
//#ifdef __GNUC__
    virtual void		Show(std::ostream& ostr = std::cout);
//#else
//    virtual void		Show(ostream& os = cout);
//#endif
//#endif
    inline  ExceptionModule	GetExceptionModule(void)	{ return _module; }
    inline  ExceptionType	GetExceptionType(void)		{ return _type; }
    inline  ExceptionCode	GetExceptionCode(void)		{ return _code; }
    inline  ExceptionRejectType	GetExceptionRejectType(void)   	{ return _reject; }
  virtual std::string		GetExceptionCodeStr(void);
  virtual std::string		GetExceptionInfo(void);

    // *** Instance variables
    ExceptionModule	_module;	// Exception module
    ExceptionType       _type;		// Exception type
    ExceptionCode       _code;		// Exception code 
    ExceptionRejectType _reject;        // Exception reject type
  std::string		_info;          // Additional Text Info

    // Following data members should be used only under debug mode
  std::string		_errLib;	// Error label
  std::string		_file;		// Name of the source file where the exception occurred
    long		_line;		// Line number in the source file
};


class Exception__ : public std::exception
{
public:
  Exception__(const char* str) : msg_(str) {}  // 6.1
  virtual ~Exception__() throw () {} ;

  const char* why() const throw () { return msg_.data(); }
  const char* what() const throw() { return why(); }
  void raise() { throw *this; }

protected:
  Exception__() {}
  std::string msg_;
};


class Xalloc : public Exception__ {
public:
  Xalloc(const char * str, size_t size = 0): Exception__(str), size_(size) { }
  virtual ~Xalloc() throw() {}

  size_t requested() const  { return size_; }

private:
    size_t size_;
};



class CoreDumpException : public Exception__ {
  int _signal;
public:
  CoreDumpException(int aSignal, const char* aSignalStr) 
    : _signal(aSignal), Exception__(aSignalStr) {}

  int getSignalNumber() const { return _signal; }
};



class NullPointerException : public Exception__ {

public:

  NullPointerException(const char* aStr) : Exception__(aStr) {}
};

class NB_Error : public Exception__ {

public :
  NB_Error( const char* str ) : Exception__( str ) {
    errorCode = 0 ;
  }
  NB_Error( const char* str, const int i ) : Exception__( str ) {
    errorCode = i ;
    
  }

  const char* getErrorString() const { return msg_.data(); }
  const int getErrorId() const { return errorCode ;}

private :
  int errorCode ;
};


#endif

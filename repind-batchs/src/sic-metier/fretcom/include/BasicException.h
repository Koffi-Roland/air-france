/****************************************************************************
*   Source Name : BasicException.h
*       Version : 27/05/1997 20:04
*        Author : Pierre
*       Purpose : Exception Handling.
****************************************************************************/

#ifndef __BASIC_EXCEPTION_H_
#define __BASIC_EXCEPTION_H_

#include <iostream>
//#include <rw/cstring.h>
#include <exception>
#include "DebugTools.h"

// #include <new.h>


// *** Definitions
typedef unsigned long			ExceptionType;
typedef long				ExceptionCode;
typedef long				ExceptionModule;

typedef char				ExceptionRejectType;

const	ExceptionModule			kExModule_NULL  	= 'NULL';

const	ExceptionType			kExType_Cancel		= 'CNCL';
const	ExceptionType			kExType_Warning 	= 'WARN';
const	ExceptionType			kExType_Fatal		= 'FATA';
const	ExceptionType			kExType_Silent		= 'SLNT';
const	ExceptionType			kExType_Altern    	= 'ALTN';

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


#define EXCEPTION			__ex
#define TRY				try
#define	CATCH				catch(BasicException& EXCEPTION)
#define	CATCH_CORE			catch(CoreDumpException& EXCEPTION)
#define CATCH_X				catch(xmsg& EXCEPTION)
//#define CATCH_X				catch(RWxmsg& EXCEPTION)
#define CATCH_ALL			catch(...)
#define PROPAGATE			throw
#define SHOW     			EXCEPTION.Show()
#define SHOW_X				tout << "#EXCEPTION# : " << GET_XCODE_STR << std::endl
#define SHOW_LOG(x)                     EXCEPTION.Show((x))
#define GET_REJECT			EXCEPTION.GetExceptionRejectType()
#define GET_CODE			EXCEPTION.GetExceptionCode()
#define GET_CODE_SIGNAL			EXCEPTION.getSignalNumber()
#define GET_CODE_STR                    EXCEPTION.GetExceptionCodeStr()
#define GET_INFO                        EXCEPTION.GetExceptionInfo()
//pel #define GET_XCODE_STR			EXCEPTION.what()
#define GET_XCODE_STR			EXCEPTION.why()
#define IS_SILENT			(EXCEPTION.GetExceptionType() == kExType_Silent)
#define IS_CANCEL			(EXCEPTION.GetExceptionType() == kExType_Cancel)
#define IS_WARNING			( EXCEPTION.GetExceptionType() == kExType_Warning || EXCEPTION.GetExceptionType() == kExType_Altern )
#define IS_FATAL			(EXCEPTION.GetExceptionType() == kExType_Fatal)
#define IS_ALTERN 			(EXCEPTION.GetExceptionType() == kExType_Altern)
#define IS_TYPE(x)       		(EXCEPTION.GetExceptionType() == (x))
#define SilentFail()			throw BasicException(kExModule_NULL, kExType_Silent, kDefaultExcepCode)
#define FatalFail()			throw BasicException(kExModule_NULL, kExType_Fatal, kDefaultExcepCode)
#define FatalNullPtr(x)			throw FreightNullPointerException((x))
#define FailCoreDump(x,y)		throw CoreDumpException((x),(y))


// Workaround for multi-threads programs which use exceptions (see SparkWork's READMEs/c++)
//pel #define THREAD_EXCEPTION_BUG	set_terminate( (std::terminate_handler) std::abort); set_unexpected( (std::unexpected_handler) std::abort);

#define THREAD_EXCEPTION_BUG	std::set_terminate( (std::terminate_handler) std::abort); std::set_unexpected( (std::unexpected_handler) std::abort);

// *** Class definition
class BasicException {
    static std::ostream& translate(long , std::ostream& os = tout);

public:
    BasicException();

    BasicException(ExceptionModule aModule, 
		   ExceptionType aType,
		   ExceptionCode aCode,
		   std::string aInfo = "",
		   ExceptionRejectType aReject = kExReject_System); //pel!!!!

    BasicException(const BasicException& aException) { operator=(aException); }

#ifdef __EXCEPTION_DEBUG__
    BasicException(ExceptionModule aModule, 
		   ExceptionType aType, 
		   ExceptionCode aCode,
		   std::string aFile, 
		   long aLine,
		   std::string aInfo = "",
		   ExceptionRejectType aReject = kExReject_System);

    BasicException(ExceptionModule aModule, ExceptionType aType, ExceptionCode aCode,
		   std::string aErrlib, std::string aFile, long aLine, std::string aInfo = "",
		   ExceptionRejectType aReject = kExReject_System);
#endif

    BasicException& operator=(const BasicException& aException);

    virtual void		Show(std::ostream& os = tout);
    inline  ExceptionModule	GetExceptionModule(void)	{ return _module; }
    inline  ExceptionType	GetExceptionType(void)		{ return _type; }
    inline  ExceptionCode	GetExceptionCode(void)		{ return _code; }
    inline  ExceptionRejectType	GetExceptionRejectType(void)   	{ return _reject; }
    virtual std::string		GetExceptionCodeStr(void);
    virtual std::string		GetExceptionInfo(void);
#ifdef __EXCEPTION_DEBUG__
    virtual void		DebugException(void);
#endif

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




class Exception_ : public std::exception
{
public:
  Exception_(const char* str) : msg_(str) {}  // 6.1
  virtual ~Exception_()  throw() {}

  const char* why() const { return msg_.data(); }
  const char* what() const throw(){ return why(); }
  void raise() { throw *this; }

protected:
  Exception_() {}
  std::string msg_;
};


class Xalloc : public Exception_ {
public:
  Xalloc(const char * str, size_t size = 0): Exception_(str), size_(size) { }
  virtual ~Xalloc() throw() {}

  size_t requested() const  { return size_; }

private:
    size_t size_;
};



class CoreDumpException : public Exception_ {
  int _signal;
public:
  CoreDumpException(int aSignal, const char* aSignalStr) 
    : _signal(aSignal), Exception_(aSignalStr) {}

  int getSignalNumber() const { return _signal; }
};



class FreightNullPointerException : public Exception_ {

public:

  FreightNullPointerException(const char* aStr) : Exception_(aStr) {}
};


#endif

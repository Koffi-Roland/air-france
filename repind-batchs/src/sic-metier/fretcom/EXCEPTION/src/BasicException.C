/****************************************************************************
*   Source Name : BasicException.C
*       Version : 03/05/1997 17:37
*        Author : Pierre
*       Purpose : Exception Handling implementation.
*    SuperClass : <None>
****************************************************************************/

#include "BasicException.h"

//using namespace fretcom;
/****************************************************************************
*	BasicException
*
*	Exception constructor.
****************************************************************************/

BasicException::BasicException()
    : _module(kExModule_NULL), _type(kExType_Silent), _code(kDefaultExcepCode), 
      _line(0L), _reject(kExReject_System), _info("")
{}

BasicException::BasicException(ExceptionModule aModule,
			       ExceptionType aType,
			       ExceptionCode aCode,
			       std::string aInfo,
			       ExceptionRejectType aReject)
    : _module(aModule), _type(aType), 
      _code(aCode ? aCode : kDefaultExcepCode), _reject(kExReject_System), _info(aInfo)
{

#ifdef __EXCEPTION_DEBUG__
    _errLib = "UNDEF";
    _line = 0L;
    DebugException();
#endif

} // BasicException


#ifdef __EXCEPTION_DEBUG__

/****************************************************************************
*	BasicException
*
*	Exception constructors under debug mode.
****************************************************************************/

BasicException::BasicException(ExceptionModule     aModule,
			       ExceptionType	   aType,
			       ExceptionCode       aCode,
			       std::string	   aFile,
			       long		   aLine,
			       std::string           aInfo,
			       ExceptionRejectType aReject)
    : _module(aModule), _type(aType), _code(aCode ? aCode : kDefaultExcepCode),
      _file(aFile), _line(aLine), _reject(aReject), _info(aInfo)
{
    DebugException();

} // BasicException


BasicException::BasicException(ExceptionModule  aModule,
			       ExceptionType	aType,
			       ExceptionCode	aCode,
			       std::string	aErrLib,
			       std::string	aFile,
			       long		aLine,
			       std::string        aInfo,
			       ExceptionRejectType aReject)
    : _module(aModule), _type(aType), _code(aCode ? aCode : kDefaultExcepCode),
      _errLib(aErrLib), _file(aFile), _line(aLine), _reject(aReject), _info(aInfo)
{
    DebugException();

} // BasicException

#endif


BasicException& BasicException::operator=(const BasicException& aException)
{
    _module = aException._module;
    _type = aException._type;
    _code = aException._code;
    _errLib = aException._errLib;
    _file = aException._file;
    _line = aException._line;
    _reject = aException._reject;
    _info = aException._info;
    return *this;

} // operator=


/****************************************************************************
*	Show
*
*	Display an error message according to the exception data.
****************************************************************************/

void BasicException::Show(std::ostream& os)
{
    if (_type != kExType_Silent)
    {
	if (_type == kExType_Fatal)
	    os << "#EXCEPTION# : ";
	else
	    os << "EXCEPTION : ";
	translate(_module, os);
	os << '\t';
	translate(_type, os);
	os << '\t' << _code;
	os << '\t' << _reject << std::endl;
    }

} // Show

std::string BasicException::GetExceptionCodeStr(void) { return _errLib; } 
std::string BasicException::GetExceptionInfo(void) { return _info; } 


#ifdef __EXCEPTION_DEBUG__

/****************************************************************************
*	DebugException
*
*	Under debug mode an exception causes a debugger break.
****************************************************************************/

void BasicException::DebugException(void)
{
    if (_type != kExType_Silent)
    {
	Show();
	TRACEMSG('\t' << _errLib << " " << _info << '\t' << _file << '\t' << _line);
    }

} // DebugException

#endif

std::ostream& BasicException::translate(long value , std::ostream& os)
{
    char* tmp = (char *) &value;
    for (int i = 0; i < sizeof(long); i++)
	os << tmp[i];
    return os;

} // translate

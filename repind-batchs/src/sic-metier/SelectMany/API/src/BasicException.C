/****************************************************************************
*   Source Name : BasicException.C
*       Version : 03/05/1997 17:37
*        Author : Pierre
*       Purpose : Exception Handling implementation.
*    SuperClass : <None>
****************************************************************************/

#include "BasicException.h"


/****************************************************************************
*	BasicException
*
*	Exception constructor.
****************************************************************************/

BasicException::BasicException()
    : _module(kExModule_NULL), _type(kExType_Silent), _code(kDefaultExcepCode), 
      _line(0L), _reject(kExReject_System), _info("") {

  //. std::cerr << (void*) this << " - BasicException::constr" << std::endl ;
}

BasicException::BasicException( ExceptionModule aModule,
				ExceptionType aType,
				ExceptionCode aCode,
				//. RWCString aInfo,
				std::string aInfo,
				ExceptionRejectType aReject )
    : _module(aModule),
      _type(aType), 
      _code(aCode ? aCode : kDefaultExcepCode),
      _reject(kExReject_System),
      _info(aInfo) {

  //. std::cerr << (void*) this << " - BasicException::constr2" << std::endl ;
} // BasicException

/*
BasicException::~BasicException() {
  std::cerr << (void*) this << " - BasicException::destr" << std::endl ;
}
*/

BasicException& BasicException::operator=(const BasicException& aException) {

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

#ifdef __GNUC__
void BasicException::Show(std::ostream& ostr)
#else
void BasicException::Show(ostream& os)
#endif
{
    if (_type != kExType_Silent)
    {
	if (_type == kExType_Fatal)
#ifdef __GNUC__
            ostr << "#EXCEPTION# : ";
#else
	    os << "#EXCEPTION# : ";
#endif
	else
#ifdef __GNUC__
	    ostr << "EXCEPTION : ";
	translate(_module, ostr);
	ostr << '\t';
	translate(_type, ostr);
	ostr << '\t' << _code;
	ostr << '\t' << _reject << std::endl;
#else
	    os << "EXCEPTION : ";
	translate(_module, os);
	os << '\t';
	translate(_type, os);
	os << '\t' << _code;
	os << '\t' << _reject << endl;
#endif
    }

} // Show

//. RWCString BasicException::GetExceptionCodeStr(void) { return _errLib; } 
std::string BasicException::GetExceptionCodeStr(void) { return _errLib; } 
//. RWCString BasicException::GetExceptionInfo(void) { return _info; } 
std::string BasicException::GetExceptionInfo(void) { return _info; } 


#ifdef __GNUC__
std::ostream& BasicException::translate( long value , std::ostream& ostr ) {
    char* tmp = (char *) &value;

    for (int i = 0; i < sizeof(long); i++)
	ostr << tmp[i];
    return ostr;
} // translate
#else
ostream& BasicException::translate( long value , ostream& os ) {
    char* tmp = (char *) &value;i

    for (int i = 0; i < sizeof(long); i++)
	os << tmp[i];
    return os;
} // translate
#endif

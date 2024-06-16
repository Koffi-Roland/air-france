/****************************************************************************
*   Source Name : ExceptionMsg.h
*       Version : 27/05/1997 20:04
*        Author : chris
*       Purpose : Exception Handling For Msg Module
****************************************************************************/

#ifndef __EXCEPTION_MSGCOM_H_
#define __EXCEPTION_MSGCOM_H_

#include "BasicException.h"

const ExceptionModule	kExModule_MSGCOM	= 'MSGC';
const ExceptionCode	kExBeginCodeMsgCom 	= 100;
const ExceptionCode	kExEndCodeMsgCom    	= 199;

//
//  ## WARNING ## : Add Exception at the end of enum JUST BEFORE kExEndCodeMsgCom
//

enum
{
    ExMsgCom_Unknow = kExBeginCodeMsgCom,
    ExMsgCom_DatabaseNotConnected,
    ExMsgCom_MissingMandatoryDate,
    ExMsgCom_BadDate,
    ExMsgCom_InternalError,
    ExMsgCom_DatabaseError,
    // add your exception between the 2 comment lines (down)
    ExMsgCom_AccesNotAuthorized,
    ExMsgCom_LastExceptionOfMsgModule = kExEndCodeMsgCom
};


#define	CATCH_MSGCOM			catch(ExceptionMSGCOM& EXCEPTION)
#define IS_ERROR_MSGCOM()  	       	(EXCEPTION.GetExceptionModule() == kExModule_MSGCOM)

#ifdef __EXCEPTION_DEBUG__
#define FailMSGCOM(type,err)		throw ExceptionMSGCOM((type), (err), #err, __FILE__, __LINE__)
#define FailMSGCOMWithInfo(type,err,info)	throw ExceptionMSGCOM((type), (err), #err, __FILE__, __LINE__, (info))
#else
#define FailMSGCOM(type,err)		throw ExceptionMSGCOM((type), (err))
#define FailMSGCOMWithInfo(type,err,info)	throw ExceptionMSGCOM((type), (err), (info))
#endif

class ExceptionMSGCOM : public BasicException
{
public:
    // *** Methods
    ExceptionMSGCOM(ExceptionType aType, ExceptionCode aCode, std::string aInfo="") 
	: BasicException(kExModule_MSGCOM, aType, aCode, aInfo) 
    {}

#ifdef __EXCEPTION_DEBUG__
    ExceptionMSGCOM(ExceptionType aType, ExceptionCode aCode,
		   std::string aErrLib, std::string aFile, long aLine, std::string aInfo="")
	: BasicException(kExModule_MSGCOM, aType, aCode, aErrLib, aFile, aLine, aInfo)
    {}
#endif

};

#endif

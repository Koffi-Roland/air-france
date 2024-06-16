//f
//----------------------------------------------------------------------
//     FILE NAME   : FreightTraces.C
//
//     DESCRIPTION : Freight message handlers.
//
//     AUTHOR      : dor
//     DATE        : 13/10/1997
//----------------------------------------------------------------------
//f

#ifndef __cplusplus
#define __cplusplus
#endif

#include "FreightTraces.h"
#include "CoreHandler.h"
#include <iostream>
#include <stdlib.h>
#include <ctime>
//JMA : 28/10/10: Homogénéisation du code
// deb-TMPHM
//pel extern long getpid();
// fin-TMPHM
#ifdef DEBUG
#include <sys/stat.h>
#endif


// *** Local definitions
#define __ROLLBACK_CONTROL__	1      // ###WARNING### : Should not be defined in final release

#ifdef DEBUG
#define __TRACE_PATH_VAR__	"PROJECT_TRACES"
#define __FAILMAIL_PATH_VAR__	"PROJECT_FAILMAILS"
#define __TIMEOUT__	        "PROJECT_TIMEOUT"
#endif


extern char gCurrentUserID[];

#if __ROLLBACK_CONTROL__
extern int COMMIT_ON;
#endif

#ifdef DEBUG
// Class variable initializations
std::string FreightTraces::cTracePath;
std::string FreightTraces::cTracePrdPath;
std::string FreightTraces::cTraceFrmPath;
std::string FreightTraces::cFailMailPath;
//JMA: 28/10/10: Homogeneisation du code
    // deb-TMPHM-Appending the pid and the call counter to the trace name
unsigned int FreightTraces::cTraceCounter;
    // fin-TMPHM
double    FreightTraces::cCriticalThreshold;
#endif



#ifdef DEBUG

//f
//----------------------------------------------------------------------
//     METHOD      : Init
//
//     DESCRIPTION : Perform needed initializations to redirect
//		     trace messages to files.
//----------------------------------------------------------------------
//f

void FreightTraces::Init()
{
    char *aTracePath = getenv(__TRACE_PATH_VAR__);
    if (aTracePath)
	cTracePath = aTracePath;

    aTracePath = getenv(__FAILMAIL_PATH_VAR__);
    if (aTracePath)
	cFailMailPath = aTracePath;

    aTracePath = getenv(__TIMEOUT__);
    if (aTracePath)
	cCriticalThreshold = atof(aTracePath);
    else
        cCriticalThreshold = kCriticalThreshold;

  // CB10042008 - to supervize filesystem full
  MANAGE_FILESYSTEM_FULL;


} // Init

//f
//----------------------------------------------------------------------
//     METHOD      : Init
//
//     DESCRIPTION : Perform needed initializations to redirect
//		     trace messages to files.
//----------------------------------------------------------------------
//f

void FreightTraces::Init(const char* pTracePath, 
			 const char* pFailmailPath, 
			 const char* pCriticalThreshold)
{
    char *aTracePath = getenv(pTracePath);
    if (aTracePath) 
      {
	cTracePath = aTracePath;
	aTracePath = 0;
      }
    if (pFailmailPath)
      {
	aTracePath = getenv(pFailmailPath);
	cFailMailPath = aTracePath;
	aTracePath = 0;
      }	
    if (pCriticalThreshold)
      {
	aTracePath = getenv(pCriticalThreshold);
	cCriticalThreshold = atof(aTracePath);
      }
    else
      cCriticalThreshold = kCriticalThreshold;

  // CB10042008 - to supervize filesystem full
  MANAGE_FILESYSTEM_FULL;


} // Init

//f
//----------------------------------------------------------------------
//     METHOD      : SendCoredTracesMails
//
//     DESCRIPTION : Parse the coredTraces file searching traces
//		     containing core dump notifications. Send a mail
//		     for each.
//----------------------------------------------------------------------
//f

void FreightTraces::SendCoredTracesMails()
{
    std::string aCoredTracesFilePath(CoreHandler::getInfosFile());
    if (!aCoredTracesFilePath.empty())
    {
	FreightTraces aTraceMgr;
	aTraceMgr.parseTraceList(aCoredTracesFilePath);
    }

} // SendCoredTracesMails


//f
//----------------------------------------------------------------------
//     METHOD      : FreightTraces::redirectTraces
//
//     DESCRIPTION : Redirect the traces.
//----------------------------------------------------------------------
//f

void FreightTraces::redirectTraces()
{
    if (!cTracePath.empty())
	TraceMgr::redirectTraces();

    PERF_INIT;
    PERF_START("");

} // redirectTraces


//f
//----------------------------------------------------------------------
//     METHOD      : FreightTraces::restoreTraces
//
//     DESCRIPTION : Restore the traces.
//----------------------------------------------------------------------
//f

void FreightTraces::restoreTraces()
{
    PERF_CLOSE;

    if (!cTracePath.empty())
	TraceMgr::restoreTraces();

    CHECK_DURATION;

} // restoreTraces


//f
//----------------------------------------------------------------------
//     METHOD      : FreightTraces::parseTraceFile
//
//     DESCRIPTION : Parse a trace file searching eventual failures.
//
//     INPUT	   : The path of a trace file.
//----------------------------------------------------------------------
//f

void FreightTraces::parseTraceFile(const std::string& aTraceFilePath)
{
    char *s = (char *) aTraceFilePath.data();
    char *c = s;
    while (*c++); // Go to the end
    while ((--c != s) && (*c != '/')); // Find last slash
    if (c != s)
    {
	char aRqt[7];
	strncpy(aRqt, c - 6, 6);
	aRqt[6] = 0;
	_rqtName = aRqt;
    }
    else
	_rqtName = "";

    TraceMgr::parseTraceFile(aTraceFilePath);

} // parseTraceFile


//f
//----------------------------------------------------------------------
//     METHOD      : FreightTraces::getTraceFilePath
//
//     DESCRIPTION : Get the full path of the trace file to generate.
//----------------------------------------------------------------------
//f

std::string FreightTraces::getTraceFilePath()
{
    // Create directory if not already
    std::string aTraceDirPath(cTracePath + _rqtName + "/");
    // mkdir(aTraceDirPath.data(), S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);
    mkdir(aTraceDirPath.data(), S_IRWXU | S_IRWXG | S_IRWXO);

    //JMA: 28/10/10: Homogénéisation du code 
    // deb-TMPHM-Appending the pid and the call counter to the trace name
    //_traceName = t.asString('m') + t.asString('d') + "." +
    //	           t.asString('H') + t.asString('M') + t.asString('S');
    // Appending the pid and the call counter to the trace name in order to avoid
    // multiple use of the same name. Ex : 0203.124923.02
    char strCounter[3];
    char strPid[6];
    long intPid = getpid();
    time_t t;
    struct tm * timeinfo;
    char buffer[80];
    
    sprintf(strCounter,"%02i",cTraceCounter++ % 100U);
    sprintf(strPid,"%05i",intPid);

    time(&t);
    timeinfo = localtime(&t);

    strftime(buffer,sizeof(buffer),"%m%d.%H%M%S",timeinfo);

    _traceName =
      buffer 
      + std::string(".") + strPid
      + std::string(".") + strCounter;
    // fin-TMPHM
    std::string aTraceFilePath(aTraceDirPath + _traceName);
    CoreHandler::setInfos(aTraceFilePath);

    return aTraceFilePath;

} // getTraceFilePath


//f
//----------------------------------------------------------------------
//     METHOD      : FreightTraces::updateTraceFile
//
//     DESCRIPTION : Move trace file in proper directory.
//----------------------------------------------------------------------
//f

void FreightTraces::updateTraceFile()
{
    if (_environ != kEdiEnviron_None)
    {
	std::string aNewPath;
	if (_environ == kEdiEnviron_Prd)
	    aNewPath = cTracePrdPath;
	else if (_environ == kEdiEnviron_Frm)
	    aNewPath = cTraceFrmPath;
	if (!aNewPath.empty())
	{
	    aNewPath += _rqtName + "/";
	    // mkdir(aNewPath.data(), S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);
	    mkdir(aNewPath.data(), S_IRWXU | S_IRWXG | S_IRWXO);
	    aNewPath += _traceName;
	    if (!rename(_traceFilePath.data(), aNewPath.data()))
	    {
		_traceFilePath = aNewPath;
		CoreHandler::setInfos(aNewPath);
	    }
	}
    }

} // updateTraceFile


//f
//----------------------------------------------------------------------
//     METHOD      : FreightTraces::isFailMailEnabled
//
//     DESCRIPTION : Returns true if failures must be notified by mail.
//----------------------------------------------------------------------
//f

bool FreightTraces::isFailMailEnabled()
{
    return !cFailMailPath.empty();

} // isFailMailEnabled


//f
//----------------------------------------------------------------------
//     METHOD      : FreightTraces::getFailMailAddr
//
//     DESCRIPTION : Get the mail address of the users to notify.
//----------------------------------------------------------------------
//f

std::string FreightTraces::getFailMailAddr(const std::string& aFileName)
{
    std::string aMailUsers;

    std::string aSrvFailFilePath(cFailMailPath + aFileName);
    std::ifstream aSrvFailFile(aSrvFailFilePath.data());
    if (aSrvFailFile.good())
    {
	char s[1024];

	if (aSrvFailFile.getline(s, 1024))
	    aMailUsers = s;
	aSrvFailFile.close();
    }

    return aMailUsers;

} // getFailMailAddr


//f
//----------------------------------------------------------------------
//     METHOD      : FreightTraces::getFailMailSubject
//
//     DESCRIPTION : Get the failure mail's subject.
//----------------------------------------------------------------------
//f

std::string FreightTraces::getFailMailSubject()
{
    return "ADH SERVICE [" + _rqtName + "]..." + (_coredTrace ? " CORE DUMP" : " FAILURE");

} // getFailMailSubject

#endif // DEBUG


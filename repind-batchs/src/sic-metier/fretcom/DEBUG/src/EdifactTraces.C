//f
//----------------------------------------------------------------------
//     FILE NAME   : EdifactTraces.C
//
//     DESCRIPTION : EdifactTraces handler.
//
//     AUTHOR      : CB
//     DATE        : 08/08/2001
//----------------------------------------------------------------------
//f
#ifdef pel
#ifndef __cplusplus
#define __cplusplus
#endif

#include <stdlib.h>

#include "EdifactTraces.h"
// #include "interfaceOracle.h"
#include "CoreHandler.h"
#include "DefinesTools.h"
#include <iostream>
#include <rw/rwtime.h>

#include "Ressource.h"


#ifdef DEBUG
#include <sys/stat.h>
#include <unistd.h>
//pel extern long getpid();

#endif


#ifdef DEBUG
#define __FAILMAIL_PATH_VAR__	"TCPSERV_FAILMAILS"
#endif

class Database2;

// extern Ressource* aConfigOracle;
RWTValHashMap < std::string, Database2*, string_hash, std::equal_to<std::string>  >  aPoolConnection(hFun);
RWTValHashMap < std::string, std::string, string_hash, std::equal_to<std::string>  >  aPoolTrace(hFun);

const char* aNullTrace = "/dev/null";

// *** Globals
Database2 *db = NULL;

// extern char gCurrentUserID[16] = "Pelican";

#ifdef DEBUG
// Class variable initializations
std::string EdifactTraces::cTracePath;
std::string EdifactTraces::cFailMailPath;
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

void EdifactTraces::Init(Ressource* aConfigOracle)
{
  char *aTracePath = 0;
  RessourceIterator iter = aConfigOracle->iter();
  while (++iter)
    {
      std::string* aSystem = iter.key();
      char* aVarEnv = (char*) aConfigOracle->getValue(*aSystem, "path").data();
      aTracePath = getenv(aVarEnv);
      if (aTracePath) {
	TRACEMSG("Host [" << *aSystem << "] - Var Env [" << aVarEnv << "] - Path Traces [" << aTracePath << "]");
      }
      else {
	TRACEMSG("##### Host [" << *aSystem << "] - Var Env [" << aVarEnv << "] NOT DEFINED ##### --> redirected to [" << aNullTrace << "]");
	aTracePath = (char*) aNullTrace;
      }
      aPoolTrace.insertKeyAndValue(*aSystem, aTracePath);
      cTracePath = aTracePath;
    }

  aTracePath = getenv(__FAILMAIL_PATH_VAR__);
  if (aTracePath)
    cFailMailPath = aTracePath;

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

void EdifactTraces::SendCoredTracesMails()
{
    std::string aCoredTracesFilePath(CoreHandler::getInfosFile());
    if (!aCoredTracesFilePath.empty())
    {
	EdifactTraces aTraceMgr;
	aTraceMgr.parseTraceList(aCoredTracesFilePath);
    }

} // SendCoredTracesMails


//f
//----------------------------------------------------------------------
//     METHOD      : EdifactTraces::redirectTraces
//
//     DESCRIPTION : Redirect the traces.
//----------------------------------------------------------------------
//f

void EdifactTraces::redirectTraces()
{
    if (!cTracePath.empty())
	TraceMgr::redirectTraces();

    PERF_INIT;
    PERF_START("");

} // redirectTraces


//f
//----------------------------------------------------------------------
//     METHOD      : EdifactTraces::restoreTraces
//
//     DESCRIPTION : Restore the traces.
//----------------------------------------------------------------------
//f

void EdifactTraces::restoreTraces()
{
    PERF_CLOSE;

    if (!cTracePath.empty())
	TraceMgr::restoreTraces();

} // restoreTraces



//f
//----------------------------------------------------------------------
//     METHOD      : EdifactTraces::getTraceFilePath
//
//     DESCRIPTION : Get the full path of the trace file to generate.
//----------------------------------------------------------------------
//f

std::string EdifactTraces::getTraceFilePath()
{
    // Create directory if not already
    std::string aTraceDirPath(cTracePath + _rqtName + "/");
    mkdir(aTraceDirPath.data(), S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);

    RWTime t;
    _traceName = t.asString('m') + t.asString('d') + "." +
	         t.asString('H') + t.asString('M') + t.asString('S') + ".";
    
    //pel _traceName += RWLocale::global().asString(getpid());
    _traceName += RWLocale::global().asString((long)getpid());
    std::string aTraceFilePath(aTraceDirPath + _traceName);
    CoreHandler::setInfos(aTraceFilePath);

    return aTraceFilePath;

} // getTraceFilePath


//f
//----------------------------------------------------------------------
//     METHOD      : EdifactTraces::updateTraceFile
//
//     DESCRIPTION : Move trace file in proper directory.
//----------------------------------------------------------------------
//f

void EdifactTraces::updateTraceFile()
{
  std::string aNewPath = aPoolTrace[_hostFirm];
  if (!aNewPath.empty())
    {
      aNewPath += _rqtName + "/";
      mkdir(aNewPath.data(), S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);
      aNewPath += _hostFirm  + "/";
      mkdir(aNewPath.data(), S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);
      aNewPath += _serviceName + "/";
      mkdir(aNewPath.data(), S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);
      aNewPath += _traceName;
      if (!rename(_traceFilePath.data(), aNewPath.data()))
	{
	  _traceFilePath = aNewPath;
	  CoreHandler::setInfos(aNewPath);
	}
    }

} // updateTraceFile


//f
//----------------------------------------------------------------------
//     METHOD      : EdifactTraces::isFailMailEnabled
//
//     DESCRIPTION : Returns true if failures must be notified by mail.
//----------------------------------------------------------------------
//f

bool EdifactTraces::isFailMailEnabled()
{
    return !cFailMailPath.empty();

} // isFailMailEnabled


//f
//----------------------------------------------------------------------
//     METHOD      : EdifactTraces::getFailMailAddr
//
//     DESCRIPTION : Get the mail address of the users to notify.
//----------------------------------------------------------------------
//f

std::string EdifactTraces::getFailMailAddr(const std::string& aFileName)
{
    std::string aMailUsers;

    std::string aSrvFailFilePath(cFailMailPath + TraceMgr::getFailMailAddr());
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
//     METHOD      : EdifactTraces::getFailMailSubject
//
//     DESCRIPTION : Get the failure mail's subject.
//----------------------------------------------------------------------
//f

std::string EdifactTraces::getFailMailSubject()
{
    return "TCPSERV " + _rqtName + (_coredTrace ? " CORE DUMP" : " FAILURE");

} // getFailMailSubject

#endif // DEBUG
#endif // pel


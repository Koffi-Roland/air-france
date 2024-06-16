//f
//----------------------------------------------------------------------
//     FILE NAME   : AdhFreightTraces.C
//
//     DESCRIPTION : ADHESION traces management.
//
//     AUTHOR      : CB
//     DATE        : 18/01/2005
//----------------------------------------------------------------------
//f

#ifdef DEBUG
#include "AdhFreightTraces.h"
#include "CoreHandler.h"
#include <stdlib.h>
//#include <regex>
#include <sys/types.h>
#include <sys/stat.h>
#include <string>
#include <ctime>

//using namespace fretcom;
// Class variable initializations
bool AdhTraces::cTraceMode = true;
std::string AdhTraces::cTracePath;
std::string AdhTraces::cFailMailPath;
std::string AdhTraces::cServerName;
struct tm *   AdhTraces::cLastFileSystemCheck;
unsigned int AdhTraces::cTraceCounter = 0;


//f
//----------------------------------------------------------------------
//     METHOD      : Init
//
//     DESCRIPTION : Perform needed initializations to redirect
//		     trace messages to files.
//----------------------------------------------------------------------
//f

void AdhTraces::Init(char *aProcessName, char *aAppli, char *aLevelName)
{
    if (cTraceMode)
    {
	//std::regex findName("Serv[A-z0-9_]+");
        //bool result = std::regex_match(aProcessName, findName);
	//std::string tmp(aProcessName);
	//cServerName = result;
	//std::regex extract1("Serv");
	//cServerName = std::regex_replace(cServerName, extract1, std::string ("Serv"));
	//std::regex extract2("_dbg");
	//cServerName = std::regex_replace(cServerName, extract2, std::string ("_dbg"));	
	//std::regex extract3("_fast");
	//cServerName = std::regex_replace(cServerName, extract3, std::string ("_fast"));	
	std::string aProcessName_str = std::string(aProcessName);
        std::size_t pos = aProcessName_str.find("ServS");
        cServerName = aProcessName_str.substr((pos+4),6);
	TRACEMSG("LastFileSystemCheck : [" << cLastFileSystemCheck << "]");    

	// Get the name of TracePath environement variable
	std::string aTraceEnv(aAppli);	
	aTraceEnv += "_ADHSERV_";	
	aTraceEnv += aLevelName;
	aTraceEnv += "_TRACES";

	// Get its value
	char *aTracePath = getenv(aTraceEnv.data());
	if (aTracePath)
	{
	    TRACEMSG("Variable Env. pour les Traces [" << aTraceEnv << "]");    
	    TRACEMSG("Traces redirigees dans [" << aTracePath << "]");
	    cTracePath = aTracePath;
	    std::string aFailPath(aAppli);
	    aFailPath += "_ADHSERV_FAILMAILS";
	    TRACEMSG("Variable ENV pour FAILURE [" << aFailPath << "]");
	    aTracePath = getenv(aFailPath.data());
	    if (aTracePath)
		cFailMailPath = aTracePath;
	}
	else
	  {
	    TRACEMSG(">>>>> Variable Env. Trace non definie [" << aTraceEnv << "]  <<<<<");    
	    cTraceMode = false;
	  }
    }
    
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

void AdhTraces::SendCoredTracesMails()
{
    std::string aCoredTracesFilePath(CoreHandler::getInfosFile());
    if (!aCoredTracesFilePath.empty())
    {
	AdhTraces aTraceMgr;
	aTraceMgr.parseTraceList(aCoredTracesFilePath);
    }

} // SendCoredTracesMails


//f
//----------------------------------------------------------------------
//     METHOD      : AdhTraces::AdhTraces
//
//     DESCRIPTION : Constructor. Redirect the traces.
//
//     INPUT       : The service number beeing entered.
//----------------------------------------------------------------------
//f

AdhTraces::AdhTraces(char *aServiceNumber) : _srvNumber(aServiceNumber), _coredTrace(false)
{
    if (cTraceMode)
	redirectTraces();

    PERF_INIT;
    PERF_START("");

} // AdhTraces


//f
//----------------------------------------------------------------------
//     METHOD      : AdhTraces::~AdhTraces
//
//     DESCRIPTION : Destructor. Restore the traces.
//----------------------------------------------------------------------
//f

AdhTraces::~AdhTraces()
{
    if (!_coredTrace)
    {
	PERF_CLOSE;

	if (cTraceMode)
	    restoreTraces();
    }

} // ~AdhTraces


//f
//----------------------------------------------------------------------
//     METHOD      : AdhTraces::parseTraceFile
//
//     DESCRIPTION : Parse a trace file searching eventual failures.
//
//     INPUT	   : The path of a trace file.
//----------------------------------------------------------------------
//f

void AdhTraces::parseTraceFile(const std::string& aTraceFilePath)
{
    char *s = (char *) aTraceFilePath.data();
    char *c = s;
    while (*c++); // Go to the end
    while ((--c != s) && (*c != '/')); // Find last slash
    if (c != s)
    {
	char aSrvNb[6];
	strncpy(aSrvNb, c - 5, 5);
	aSrvNb[5] = 0;
	_srvNumber = aSrvNb;
    }
    else
	_srvNumber = "";

    TraceMgr::parseTraceFile(aTraceFilePath);

} // parseTraceFile


//f
//----------------------------------------------------------------------
//     METHOD      : AdhTraces::getTraceFilePath
//
//     DESCRIPTION : Get the full path of the trace file to generate.
//----------------------------------------------------------------------
//f

std::string AdhTraces::getTraceFilePath()
{
    // Create directory if not already
    std::string aTraceDirPath(cTracePath + "S");
    aTraceDirPath += _srvNumber;
    aTraceDirPath += '/';
    mkdir(aTraceDirPath.data(), S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);

    // Appending the call counter to the trace name in order to avoid
    // multiple use of the same name. Ex : S36870.0120.170623.0002
    char strCounter[5];
    char strPid[6];
    long intPid = getpid();
    time_t t;
    struct tm * timeinfo;
    char buffer[80];

    time(&t);
    timeinfo = localtime(&t);

    strftime(buffer,sizeof(buffer),"%m%d.%H%M%S",timeinfo);

    sprintf(strPid,"%05i",intPid);
    cTraceCounter = (++cTraceCounter) % 100U;
    sprintf(strCounter,"%02u",cTraceCounter);
    std::string aTraceFilePath(aTraceDirPath + cServerName + "." +
			     buffer +
			     "." + strPid + "." + strCounter);

    CoreHandler::setInfos(aTraceFilePath);

    return aTraceFilePath;

} // getTraceFilePath


//f
//----------------------------------------------------------------------
//     METHOD      : AdhTraces::isFailMailEnabled
//
//     DESCRIPTION : Returns true if failures must be notified by mail.
//----------------------------------------------------------------------
//f

bool AdhTraces::isFailMailEnabled()
{
    return !cFailMailPath.empty();

} // isFailMailEnabled


//f
//----------------------------------------------------------------------
//     METHOD      : AdhTraces::getFailMailAddr
//
//     DESCRIPTION : Get the mail address of the users to notify.
//----------------------------------------------------------------------
//f

std::string AdhTraces::getFailMailAddr(const std::string& aFileName)
{
    std::string aMailUsers;

    std::string aSrvFailFilePath(cFailMailPath + TraceMgr::getFailMailAddr());
    std::ifstream aSrvFailFile(aSrvFailFilePath.data());
    if (aSrvFailFile.good())
    {
	char s[1024];

	long aSpeedCheck = *((long *) _srvNumber.data());
	while (aSrvFailFile.getline(s, 1024))
	{
	    if (*((long *) s) == aSpeedCheck)  // Works only with first four chars
	    {
		aMailUsers = s + 6;
		break;
	    }
	}
	aSrvFailFile.close();
    }

    return aMailUsers;

} // getFailMailAddr


//f
//----------------------------------------------------------------------
//     METHOD      : AdhTraces::getFailMailSubject
//
//     DESCRIPTION : Get the failure mail's subject.
//----------------------------------------------------------------------
//f

std::string AdhTraces::getFailMailSubject()
{
    return "ADH SERVICE S" + _srvNumber + (_coredTrace ? " CORE DUMP" : " FAILURE");

} // getFailMailSubject

//f
//----------------------------------------------------------------------
//     METHOD      : AdhTraces::checkSuperviseFileSystem
//
//     DESCRIPTION : Returns true if failures must be notified by mail.
//----------------------------------------------------------------------
//f

void AdhTraces::checkSuperviseFileSystem(int isec)
{
  time_t t, t1, t2;
  struct tm * tcurrent;
  struct tm *  tcalc = AdhTraces::cLastFileSystemCheck;
  char buffer[80];

  time(&t);
  tcurrent = localtime(&t);
  tcalc += isec;
//pel TODO verifier la comparaison
  t1 = mktime(tcurrent);
  t2 = mktime(tcalc);
  TRACEMSG("AdhTraces::checkSuperviseFileSystem : Last " << AdhTraces::cLastFileSystemCheck);
  //if ((tcalc->compareTo(tcurrent)) <= -1) {
  if (t2 < t1) {
    AdhTraces::cLastFileSystemCheck = tcurrent;
    TraceMgr::SuperviseFileSystemWithoutTimer();
    }


} // checkSuperviseFileSystem


#endif // DEBUG

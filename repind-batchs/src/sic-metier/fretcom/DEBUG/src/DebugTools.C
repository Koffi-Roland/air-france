//f
//----------------------------------------------------------------------
//     FILE NAME   : DebugTools.C
//
//     DESCRIPTION : Useful implementation for tracing and debugging.
//
//     AUTHOR      : dor
//     DATE        : 10/09/1997
//----------------------------------------------------------------------
//f

#include "DebugTools.h"

#ifdef DEBUG
#include <ctime>
#include <sys/times.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/statvfs.h>
unsigned long LEVEL_TRACE=LevelTrace::Full;
const char* aNullPath = "/dev/null";
std::ofstream aDevNullStream;


// Class variable initializations
std::ostream	   *TraceMgr::cTraceStream = &std::cout;		// std::ostream to use for traces
bool   TraceMgr::cDamagedTraceMode = false;
Timer      *TraceMgr::cTimer;



//pel long	    PerfMgr::cClockTicks = CLK_TCK;		// Number of ticks per second
long        PerfMgr::cClockTicks = CLOCKS_PER_SEC;              // Number of ticks per second
long	    PerfMgr::cCurPerf = 0L;			// Current running perf indice
std::string   PerfMgr::cPerfLabel[kPerfMgr_MaxPerf];	// Perf's labels
clock_t	    PerfMgr::cPerfStart[kPerfMgr_MaxPerf];	// Perf's starting ticks
double      PerfMgr::cDuration = 0.0;



/*

Function called by Timer to supervise the filesystem

*/
void TraceMgr::SuperviseFileSystem(int i) {
  
  // inactivate the timer --> no interruption during check ...
  TraceMgr::cTimer->inActivate();

  const char *filename = ".";
  struct statvfs buf;
  if (!statvfs(filename, &buf)) {
    float df = ((float) buf.f_bfree / (float) buf.f_blocks) * 100;
    //RWTime t;
    time_t t;
    struct tm * timeinfo;
    char buffer[80];

    time(&t);
    timeinfo = localtime(&t);

    strftime(buffer,sizeof(buffer),"%m%d.%H%M%S",timeinfo);


    TRACEMSG(  buffer << "...Disk[" << filename << "] - Total blocks[" <<  buf.f_blocks 
	     << "] - Free blocks[" <<  buf.f_bfree << "] - % Free[" << df << " %]");

    if (! TraceMgr::cDamagedTraceMode && df < _DEFAULT_POURCENTAGE_) {
      TRACEMSG(  buffer << "...Pass to Damaged Mode Trace" << " - % Free[" << df << " %]");
      TraceMgr::SetDamagedModeTrace(SIGUSR1);
    }
    else if ( TraceMgr::cDamagedTraceMode && df > _DEFAULT_POURCENTAGE_) {
      TraceMgr::SetNormalModeTrace(SIGUSR2);
      TRACEMSG(  buffer << "...Return to Normal Mode Trace" << " - % Free[" << df << " %]" );
    }

  } else {  
    TRACEMSG_LVL("Failed to stat" << filename << std::endl, LevelTrace::System);    
  }	

  // reactivate the timer
  TraceMgr::cTimer->activate();

}

void TraceMgr::SuperviseFileSystemWithoutTimer() {
  
  const char *filename = ".";
  struct statvfs buf;
  if (!statvfs(filename, &buf)) {
    time_t t;
    struct tm * timeinfo;
    char buffer[80];

    time(&t);
    timeinfo = localtime(&t);

    strftime(buffer,sizeof(buffer),"%m%d.%H%M%S",timeinfo);

    float df = ((float) buf.f_bfree / (float) buf.f_blocks) * 100;
    TRACEMSG( buffer << "...Disk[" << filename << "] - Total blocks[" <<  buf.f_blocks 
	     << "] - Free blocks[" <<  buf.f_bfree << "] - % Free[" << df << " %]");

    if (! TraceMgr::cDamagedTraceMode && df < _DEFAULT_POURCENTAGE_) {
      TRACEMSG( buffer << "...Pass to Damaged Mode Trace" << " - % Free[" << df << " %]");
      TraceMgr::SetDamagedModeTrace(SIGUSR1);
    }
    else if ( TraceMgr::cDamagedTraceMode && df > _DEFAULT_POURCENTAGE_) {
      TraceMgr::SetNormalModeTrace(SIGUSR2);
      TRACEMSG( buffer << "...Return to Normal Mode Trace" << " - % Free[" << df << " %]" );
    }

  } else {  
    TRACEMSG_LVL("Failed to stat" << filename << std::endl, LevelTrace::System);    
  }	

}



/*

   Damaged Mode Trace - write on /dev/null stream

*/

void TraceMgr::SetDamagedModeTrace(int i) {
  
  // close current log file (but not the std::cout stream)
  if (TraceMgr::cTraceStream->good() && TraceMgr::cTraceStream != &std::cout)
    {
      ((std::ofstream*) TraceMgr::cTraceStream)->close();
    }
  
  // damaged log way --> redirect to /dev/null
  TraceMgr::cDamagedTraceMode = true;
  aDevNullStream.open(aNullPath);

  if (aDevNullStream.good()) {
    TraceMgr::cTraceStream = &aDevNullStream;
  } else {
    TRACEMSG_LVL("Open /dev/null failed", LevelTrace::System);    
  }

}


/*

   Return to normal mode trace

*/

void TraceMgr::SetNormalModeTrace(int i) {

  // close file on /dev/null stream
  if (TraceMgr::cTraceStream->good() && TraceMgr::cTraceStream != &std::cout)
    {
      ((std::ofstream*) TraceMgr::cTraceStream)->close();
    }

  TraceMgr::cDamagedTraceMode = false;
  // by default use std::cout until the next transaction ...
  TraceMgr::cTraceStream = &std::cout;

}


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::~TraceMgr
//
//     DESCRIPTION : Destructor.
//----------------------------------------------------------------------
//f

TraceMgr::~TraceMgr()
{
  if (! cDamagedTraceMode) {
    cTraceStream = &std::cout;
  }

} // ~TraceMgr


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::redirectTraces
//
//     DESCRIPTION : Redirect trace messages to a file.
//----------------------------------------------------------------------
//f

void TraceMgr::redirectTraces()
{
  if (! cDamagedTraceMode) {
    _traceFilePath = getTraceFilePath();
    _traceFile.open(_traceFilePath.data());
    if (_traceFile.good())
	cTraceStream = &_traceFile;
    else
	TRACEVAL_LVL("Open trace file failed", _traceFilePath, LevelTrace::System);
  }

} // redirectTraces


// #ifdef DEBUG_CHILD
//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::redirectChildTraces
//
//     DESCRIPTION : Redirect child trace messages to a file.
//----------------------------------------------------------------------
//f

void TraceMgr::redirectChildTraces(pid_t aPid)
{
  if (! cDamagedTraceMode) {
    char aStringPid[10];
    sprintf(aStringPid, ".%u", aPid);
    
    _traceFilePath = CoreHandler::getInfos() + aStringPid;
    TRACEVAL_LVL("Open Child trace file : ", _traceFilePath, LevelTrace::System);
    
    _traceFile.open(_traceFilePath.data());
    if (_traceFile.good()) {
      cTraceStream = &_traceFile;
      CoreHandler::setInfos(_traceFilePath);
    }
    else
      TRACEVAL_LVL("Open trace file failed", _traceFilePath, LevelTrace::System);
  }
	  
} // redirectTraces
// #endif


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::restoreTraces
//
//     DESCRIPTION : Restore trace messages to standard output and
//		     check for failure inside traces.
//----------------------------------------------------------------------
//f

void TraceMgr::restoreTraces()
{
  if (! cDamagedTraceMode) {
    cTraceStream = &std::cout;
    if (_traceFile.good())
    {
	_traceFile.close();
	updateTraceFile();
	if (isFailMailEnabled())
	    parseTracesFailures();
    }
  }

} // restoreTraces


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::parseTraceList
//
//     DESCRIPTION : Parse a list of trace files searching eventual
//		     failures.
//
//     INPUT	   : The path of a file containing the trace file
//		     paths list.
//----------------------------------------------------------------------
//f

void TraceMgr::parseTraceList(const std::string& aTraceListPath)
{
    std::ifstream aTraceListFile(aTraceListPath.data());
    if (aTraceListFile.good())
    {
	if (isFailMailEnabled())
	{
	    try
	    {
		char s[1024];

		while (aTraceListFile.getline(s, 1024))
		    parseTraceFile(s);
	    }
	    catch(...) {}
	}

	aTraceListFile.close();
	remove(aTraceListPath.data());
    }

} // parseTraceList


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::parseTraceFile
//
//     DESCRIPTION : Parse a trace file searching eventual failures.
//
//     INPUT	   : The path of a trace file.
//----------------------------------------------------------------------
//f

void TraceMgr::parseTraceFile(const std::string& aTraceFilePath)
{
    _traceFilePath = aTraceFilePath;
    parseTracesFailures();

} // parseTraceFile


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::parseTracesFailures
//
//     DESCRIPTION : Parse trace file to find a failure such
//		     as assertions failed or exception thrown.
//		     If one is found, a mail is sent.
//----------------------------------------------------------------------
//f

void TraceMgr::parseTracesFailures()
{
    std::ifstream aTraceFile(_traceFilePath.data());
    if (aTraceFile.good())
    {
	char		s[1024];
	bool	aProblemFound = false;
	long		aLineNb = 0L;
	std::streampos	aHistPos[5] = {0L, 0L, 0L, 0L, 0L};

	while (aTraceFile.getline(s, 1024))
	{

//	    long aSpeedCheck = *((long *) s); Replaced this line with memcpy. SIGBUS anyone ?
            long aSpeedCheck;
	    memcpy(&aSpeedCheck, s, sizeof(long));

	    switch (aSpeedCheck)
	    {
	        case '#ASS' : aProblemFound = !strncmp(s, "#ASSERT FAIL#", 13);	   break;
	        case '#DEB' : aProblemFound = !strncmp(s, "#DEBUG#", 7);	   break;
	        case '#EXC' : aProblemFound = !strncmp(s, "#EXCEPTION#", 11);	   break;
	        case '#COR' : aProblemFound = !strncmp(s, "#CORE DUMP#", 11);	   break;
	        case '#MEM' : aProblemFound = !strncmp(s, "#MEMFAIL#", 9);	   break;
	    }
	    aLineNb++;
	    if (aProblemFound)
		break;
	    for (int i = 0; (i < 4);)
		aHistPos[i++] = aHistPos[i];
	    aHistPos[4] = aTraceFile.tellg();
	}

	if (aProblemFound)
	{
	    // Search if a mail has to be sent
	    std::string aMailUsers = getFailMailAddr();
	    if (!aMailUsers.empty())
	    {
		std::string aShellCmd("echo \"");
		int i = 0;
		aTraceFile.seekg(aHistPos[0]);
		while ((i++ < 9) && aTraceFile.getline(s, 1024))
		{
		    char *c = s - 1;
		    while (*++c)
		    {
			if (*c == '`')	// This char is interpreted by the shell
			    *c = ' ';
		    }
		    aShellCmd += s;
		    aShellCmd += '\n';
		}
		aShellCmd += "\nFound in file " + _traceFilePath;
		aShellCmd += "\nAt line " + to_string(aLineNb);
		aShellCmd += "\" | /usr/bin/mailx -s \"" + getFailMailSubject();
		aShellCmd += "\" " + aMailUsers;
		system(aShellCmd.data());
	    }
	}

	aTraceFile.close();
    }

} // parseTracesFailures


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::getTraceFilePath
//
//     DESCRIPTION : Get the full path of the trace file to generate.
//----------------------------------------------------------------------
//f

std::string TraceMgr::getTraceFilePath()
{
    return "traces";

} // getTraceFilePath


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::updateTraceFile
//
//     DESCRIPTION : Perform some updates after closing the trace file.
//----------------------------------------------------------------------
//f

void TraceMgr::updateTraceFile() {}  // Nothing to do by default


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::isFailMailEnabled
//
//     DESCRIPTION : Returns true if failures must be notified by mail.
//----------------------------------------------------------------------
//f

bool TraceMgr::isFailMailEnabled()
{
    return false;

} // isFailMailEnabled


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::getFailMailAddr
//
//     DESCRIPTION : Get the mail address of the users to notify.
//----------------------------------------------------------------------
//f

std::string TraceMgr::getFailMailAddr(const std::string& aFileName)
{
    return aFileName;

} // getFailMailAddr


//f
//----------------------------------------------------------------------
//     METHOD      : TraceMgr::getFailMailSubject
//
//     DESCRIPTION : Get the failure mail's subject.
//----------------------------------------------------------------------
//f

std::string TraceMgr::getFailMailSubject()
{
    return "### FAILURE ###";

} // getFailMailSubject


//f
//----------------------------------------------------------------------
//     METHOD      : PerfMgr::Init
//
//     DESCRIPTION : Erase all running perfs.
//----------------------------------------------------------------------
//f

void PerfMgr::Init()
{
    cCurPerf = 0L;

} // Init


//f
//----------------------------------------------------------------------
//     METHOD      : PerfMgr::Start
//
//     DESCRIPTION : Start a perf timer.
//----------------------------------------------------------------------
//f

void PerfMgr::Start(const std::string& aLabel)
{
    if (cCurPerf < kPerfMgr_MaxPerf)
    {
	if (aLabel.empty())
        {
	    time_t t;
	    struct tm * timeinfo;
	    char buffer[80];

	    time(&t);
	    timeinfo = localtime(&t);

	    strftime(buffer,sizeof(buffer),"%m%d.%H%M%S",timeinfo);
	    TRACEMSG_LVL("START TIME : " << buffer << '\n', LevelTrace::Duration);
        }
	else
	    TRACEMSG_LVL(aLabel << " START", LevelTrace::Perf);
	struct tms aTMS;
	cPerfLabel[cCurPerf] = aLabel;
	cPerfStart[cCurPerf++] = times(&aTMS);
    }
    else
	DEBUGMSG_LVL("Maximum number of running perfs reached (" 
		     << kPerfMgr_MaxPerf << ')', LevelTrace::System);

} // Start


//f
//----------------------------------------------------------------------
//     METHOD      : PerfMgr::Stop
//
//     DESCRIPTION : Stop last started perf.
//----------------------------------------------------------------------
//f

void PerfMgr::Stop()
{
    if (cCurPerf)
    {
	struct tms aTMS;
	double aDuration = ((double) (times(&aTMS) - cPerfStart[--cCurPerf])) / cClockTicks;
	std::string aLabel(cPerfLabel[cCurPerf]);
	if (aLabel.empty())
	{
            time_t t;
            struct tm * timeinfo;
            char buffer[80];

            time(&t);
            timeinfo = localtime(&t);

            strftime(buffer,sizeof(buffer),"%m%d.%H%M%S",timeinfo);

	    TRACEMSG_LVL("\nEND TIME : " <<  buffer, LevelTrace::Duration);
	    TRACEMSG_LVL("DURATION : " << aDuration << " secs", LevelTrace::Duration);
            cDuration = aDuration;
	}
	else
	    TRACEMSG_LVL(aLabel << " STOP - PERF : " << aDuration << " secs", 
			 LevelTrace::Perf);
    }
    else
	DEBUGMSG_LVL("PERF_STOP called without PERF_START", LevelTrace::System);

} // Stop


//f
//----------------------------------------------------------------------
//     METHOD      : PerfMgr::Close
//
//     DESCRIPTION : Stop ALL started perf.
//----------------------------------------------------------------------
//f

void PerfMgr::Close()
{
    while (cCurPerf)
	Stop();

} // Close


//f
//----------------------------------------------------------------------
//     METHOD      : PerfMgr::PerfMgr
//
//     DESCRIPTION : Start a perf which will be stopped with destructor.
//----------------------------------------------------------------------
//f

PerfMgr::PerfMgr(const std::string& aLabel, const LevelTrace& aLevel) 
  : _label(aLabel), _level(aLevel)
{
    TRACEMSG_LVL(_label << " START", _level);
    struct tms aTMS;
    _start = times(&aTMS);

} // PerfMgr


//f
//----------------------------------------------------------------------
//     METHOD      : PerfMgr::~PerfMgr
//
//     DESCRIPTION : Stop an auto perf.
//----------------------------------------------------------------------
//f

PerfMgr::~PerfMgr()
{
    struct tms aTMS;
    double aDuration = ((double) (times(&aTMS) - _start)) / cClockTicks;
    TRACEMSG_LVL(_label << " STOP - PERF : " << aDuration << " secs", _level);

} // ~PerfMgr

#endif

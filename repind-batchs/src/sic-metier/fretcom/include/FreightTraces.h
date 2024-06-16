//f
//----------------------------------------------------------------------
//     FILE NAME   : FreightTraces.h
//
//     DESCRIPTION : Freight message handlers.
//
//     AUTHOR      : dor
//     DATE        : 13/10/1997
//----------------------------------------------------------------------
//f

#ifndef FreightTraces_h
#define FreightTraces_h    1

#include <string>

#ifdef DEBUG
#include "DebugTools.h"


#define CHECK_DURATION \
    if ( isFailMailEnabled() && PERF_DURATION > cCriticalThreshold ) { \
       TRACEMSG("CHECK_DURATION"); \
       std::string aMailUsers = getFailMailAddr("srvTimeout.mail"); \
       TRACEVAR(aMailUsers); \
       std::string aShellCmd(cFailMailPath); \
       aShellCmd += "/shell/mailer.pl "; \
       aShellCmd += "\"" +  aMailUsers + "\" " ; \
       aShellCmd +=  " \" Timeout : " + to_string(PERF_DURATION) + " s -  Service : " + _rqtName + "\" " ; \
       aShellCmd += _traceFilePath; \
       TRACEVAR(aShellCmd); \
    }



enum
{
    kEdiEnviron_None,
    kEdiEnviron_Prd,
    kEdiEnviron_Frm
};


// Default Critical Threshold for Duration
const double kCriticalThreshold = 20.00;

// Traces management
class FreightTraces : public TraceMgr
{
public:
    // Class variables
    static std::string cTracePath;
    static std::string cTracePrdPath;
    static std::string cTraceFrmPath;
    static std::string cFailMailPath;
    //JMA : 28/10/10 : Homogénéisation du code
    // deb-TMPHM-Appending the pid and the call counter to the trace name
    static unsigned int cTraceCounter;
    // fin-TMPHM
    // Critical Threshold for Duration
    static double    cCriticalThreshold;

    // Data members
    std::string	_rqtName;
    std::string	_traceName;
    short	_environ;
    bool	_coredTrace;

    // Class methods
    static void Init();
    static void Init(const char* pTracePath, 
		     const char* pFailmailPath=0, 
		     const char* pCriticalThreshold=0);
    static void SendCoredTracesMails();

    // Methods
    FreightTraces() : _environ(kEdiEnviron_None), _coredTrace(true) {}
    FreightTraces(const std::string& aRqtName) : _rqtName(aRqtName), _environ(kEdiEnviron_None), _coredTrace(false) {}
    virtual void redirectTraces();
    virtual void restoreTraces();
    virtual void parseTraceFile(const std::string& aTraceFilePath);

protected:
    virtual std::string getTraceFilePath();
    virtual void      updateTraceFile();
    virtual bool isFailMailEnabled();
    virtual std::string getFailMailAddr(const std::string& aFileName="srvFailures.mail");
    virtual std::string getFailMailSubject();
};

#endif // DEBUG


#endif // FreightTraces_h

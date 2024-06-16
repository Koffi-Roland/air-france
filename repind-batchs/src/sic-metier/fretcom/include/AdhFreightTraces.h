//f
//----------------------------------------------------------------------
//     FILE NAME   : AdhTraces.h
//
//     DESCRIPTION : AHESION traces management.
//
//     AUTHOR      : dor
//     DATE        : 05/12/1997
//----------------------------------------------------------------------
//f

#ifndef AdhTraces_h
#define AdhTraces_h	1


#ifdef DEBUG
#include <string>
#include "DebugTools.h"
#include "DefinesTools.h"


//using namespace fretcom;
// Traces management
class AdhTraces : public TraceMgr
{
public:
    // Class variables
    static bool cTraceMode;
    static std::string cTracePath;
    static std::string cFailMailPath;
    static std::string cServerName;
    static unsigned int cTraceCounter;
    static struct tm* cLastFileSystemCheck;

    // Data members
    std::string	_srvNumber;
    bool	_coredTrace;

    // Class methods
    static void Init(char *aProcessName, char* aAppli, char *aLevelName);
    static void SendCoredTracesMails();
    static void checkSuperviseFileSystem(int isec);

    // Methods
    AdhTraces() : _coredTrace(true) {}
    AdhTraces(char *aServiceNumber);
    ~AdhTraces();
    virtual void parseTraceFile(const std::string& aTraceFilePath);

protected:
    virtual std::string getTraceFilePath();
    virtual bool isFailMailEnabled();
    virtual std::string getFailMailAddr(const std::string& aFileName);
    virtual std::string getFailMailSubject();
};

#endif  // DEBUG
#endif	// AdhTraces_h

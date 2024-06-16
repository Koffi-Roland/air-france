//f
//----------------------------------------------------------------------
//     FILE NAME   : EdifactTraces.h
//
//     DESCRIPTION : EdifactTraces handler.
//
//     AUTHOR      : CB
//     DATE        : 08/08/2001
//----------------------------------------------------------------------
//f

#ifndef EdifactTraces_h
#define EdifactTraces_h    1

//#include <rw/cstring.h>
#include <string>

#include "Ressource.h"

#ifdef DEBUG
#include "DebugTools.h"


// Traces management
class EdifactTraces : public TraceMgr
{
public:
    // Class variables
    static std::string cTracePath;
    static std::string cFailMailPath;

    // Data members
    std::string	_hostFirm;
    std::string	_rqtName;
    std::string	_serviceName;

    std::string	_traceName;
    bool	_coredTrace;

    // Class methods
    static void Init(Ressource*);
    static void SendCoredTracesMails();

    // Methods
    EdifactTraces() :  _coredTrace(true) {}
    EdifactTraces(const std::string& aRqtName) : _rqtName(aRqtName), _coredTrace(false) {}
    virtual void redirectTraces();
    virtual void restoreTraces();

protected:
    virtual std::string getTraceFilePath();
    virtual void      updateTraceFile();
    virtual bool isFailMailEnabled();
    virtual std::string getFailMailAddr(const std::string& aFileName);
    virtual std::string getFailMailSubject();
};

#endif // DEBUG

#endif // EdifactTraces_h

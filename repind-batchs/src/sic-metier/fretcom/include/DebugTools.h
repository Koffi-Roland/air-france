//f
//----------------------------------------------------------------------
//     FILE NAME   : DebugTools.h
//
//     DESCRIPTION : Useful definitions for tracing and debugging.
//
//     AUTHOR      : dor
//     DATE        : 10/09/1997
//----------------------------------------------------------------------
//f

#ifndef DebugTools_h 
#define DebugTools_h    1

// Debugging tools
#ifdef DEBUG

#include <sys/types.h>
#include <unistd.h>

#include <fstream>
#include <time.h>
#include <cstring>
#include <string>
#include <locale.h>

#include "MemoryMgr.h"

// #ifdef DEBUG_CHILD
#include "CoreHandler.h"
// #endif

#include <sstream>

template <class T>
inline std::string to_string (const T& t)
{
    std::stringstream ss;
    ss << t;
    return ss.str();
}
#define tout (*TraceMgr::cTraceStream)



#define NULL_SIGNAL_HANDLER	((void (*)(int)) -1)

// by default 1h00
const int  _DEFAULT_TIMER_TO_CHECK_FS_ ( 3600 );

const float _DEFAULT_POURCENTAGE_ ( 10.0 );

#include "Timer.h"
#include <ctime>

#include <signal.h>

class TraceMgr;
#define MANAGE_FILESYSTEM_FULL   \
    void (*_sig)(int); \
    _sig = (CFunction) sigset(SIGUSR1, (ExternCFunction) TraceMgr::SetDamagedModeTrace); \
    if (_sig == NULL_SIGNAL_HANDLER) \
	TRACEMSG_LVL("Damaged Mode Trace : error on SIGUSR1", LevelTrace::System); \
\
    _sig = (CFunction) sigset(SIGUSR2, (ExternCFunction) TraceMgr::SetNormalModeTrace); \
    if (_sig == NULL_SIGNAL_HANDLER) \
	TRACEMSG_LVL("Normal Mode Trace : error on SIGUSR2", LevelTrace::System); \
\
    TraceMgr::cTimer = new Timer( _DEFAULT_TIMER_TO_CHECK_FS_, TraceMgr::SuperviseFileSystem ); \



struct LevelTrace {
  enum {
    NoTrace       = 0x0,     // for environment variable YIELD_NO_TRACE (no trace on YIELD)
    Duration      = 0x1,     // for environment variable YIELD_DURATION_TRACE (only duration is traced)
    Perf          = 0x2,     // for environment variable YIELD_PERF_TRACE (all PERF_AUTO are traced)
    PerfDb        = 0x4,     // for environment variable YIELD_PERF_DB_TRACE (all PERF_DB are traced)
    Sql           = 0x8,     // for environment variable YIELD_SQL_TRACE (all stmt SQL are traced)
    System        = 0x10,     // for environment variable YIELD_SYSTEM_TRACE (all system call are traced)
    Other         = 0x20,     // for environment variable YIELD_OTHER_TRACE (all oher traces are traced)
    Level1        = 0x40,      // for env var YIELD_LEVEL1_TRACE (all Applicative traces level 1 are traced)
    Level2        = 0x80,      // for env var YIELD_LEVEL2_TRACE (all Applicative traces level 2 are traced)
    Level3        = 0x100,     // for env var YIELD_LEVEL3_TRACE (all Applicative traces level 3 are traced)
    Level4        = 0x200,     // for env var YIELD_LEVEL4_TRACE (all Applicative traces level 4 are traced)
    Level5        = 0x400,     // for env var YIELD_LEVEL5_TRACE (all Applicative traces level 5 are traced)
    Debug         = 0x800,     // for environment variable YIELD_DEBUG_TRACE (DEFAULT)
    Full          = 0xFFFFFFFF // mode Full Traces YIELD_FULL_TRACE (all stmt SQL are traced)
  };
  
  LevelTrace(unsigned long aLevel) : _level(aLevel) {} 

  operator unsigned long() const { return _level; }

private:
  unsigned long  _level;
};

extern unsigned long LEVEL_TRACE;
#define TRACEMSG(msg)       TRACEMSG_LVL(msg, LevelTrace::Debug)
#define TRACEVAR(var)       TRACEVAR_LVL(var, LevelTrace::Debug)
#define TRACEPTR(ptr)       TRACEPTR_LVL(ptr, LevelTrace::Debug)
#define TRACEMTD(mtd)       TRACEMTD_LVL(mtd, LevelTrace::Debug)
#define TRACEVAL(msg,val)   TRACEVAL_LVL(msg, val, LevelTrace::Debug)
#define TRACECUST(msg,obj)  TRACECUST_LVL(msg, obj, LevelTrace::Debug)
#define DEBUGMSG(msg)       DEBUGMSG_LVL(msg, LevelTrace::Debug)

// TRACE macros
#define COUT(msg, lvl)               if (lvl & LEVEL_TRACE) tout << msg; else 
#define TRACEMSG_LVL(msg, lvl)       if (lvl & LEVEL_TRACE) tout << msg << std::endl; else 
#define TRACEVAR_LVL(var, lvl)       if (lvl & LEVEL_TRACE) tout << #var << " : " << var << std::endl; else 
#define TRACEPTR_LVL(ptr, lvl)       if (lvl & LEVEL_TRACE) { tout << #ptr << " : "; if (ptr) tout << *(ptr); else tout << "NULL"; tout << std::endl; } else 
#define TRACEMTD_LVL(mtd, lvl)       if (lvl & LEVEL_TRACE) mtd; else 
#define TRACEVAL_LVL(msg, val, lvl)   if (lvl & LEVEL_TRACE) tout << msg << " : " << val << std::endl; else 
#define TRACECUST_LVL(msg, obj, lvl)  if (lvl & LEVEL_TRACE) tout << msg << " : " << (obj).customPrint( tout ) << std::endl; else 
#define ASSERT(cond)        (void)((cond) || ((tout << "#ASSERT FAIL# : " << #cond << " (" << __FILE__ << " #" << __LINE__ << ')' << std::endl)))
#define FAILASSERT(cond)    { if (!(cond)) { tout << "#ASSERT FAIL# (abort) : " << #cond << " (" << __FILE__ << " #" << __LINE__ << ')' << std::endl; FatalFail(); }}
#define DEBUGMSG_LVL(msg, lvl)       if (lvl & LEVEL_TRACE) ((tout << "#DEBUG# : " << msg << " (" << __FILE__ << " #" << __LINE__ << ')' << std::endl), getchar()); else 


#define VERIFY_PTR(ptr)        if (ptr == 0) { std::string error("Pointer ["#ptr"] is NULL : "); error+= __FILE__; error += " #"; error += std::to_string((long)__LINE__); TRACEVAR(error); FatalNullPtr(error); } else 


// PERF macros
#define PERF_INIT	    PerfMgr::Init()
#define PERF_AUTO(label)    PerfMgr __perf(label)
#define PERF_SQL(label)     PerfMgr __perf(label, LevelTrace::PerfDb)
#define PERF_START(label)   PerfMgr::Start(label)
#define PERF_STOP	    PerfMgr::Stop()
#define PERF_CLOSE	    PerfMgr::Close()
#define PERF_DURATION       PerfMgr::cDuration
// TRACE redirection class
class TraceMgr MEM_OBJ
{
public:
    // Trace stream
    static std::ostream *cTraceStream;
    static bool cDamagedTraceMode;
    static Timer* cTimer;

    // Methods
    virtual ~TraceMgr();
    virtual void redirectTraces();
  // #ifdef DEBUG_CHILD
    virtual void redirectChildTraces(pid_t aPid);
  // #endif
    virtual void restoreTraces();
    virtual void parseTraceList(const std::string& aTraceListPath);
    virtual void parseTraceFile(const std::string& aTraceFilePath);

  static void SetDamagedModeTrace(int);
  static void SetNormalModeTrace(int);
  static void SuperviseFileSystem(int);
  static void SuperviseFileSystemWithoutTimer();

protected:
    // Data members
    std::ofstream	_traceFile;
    std::string	_traceFilePath;

    // Methods
    virtual void parseTracesFailures();

    // Methods to override
//#ifdef bouchon
    virtual std::string getTraceFilePath();
//#endif
    virtual void      updateTraceFile();
    virtual bool isFailMailEnabled();
//#ifdef bouchon
    virtual std::string getFailMailAddr(const std::string& aFileName="srvFailures.mail");
    virtual std::string getFailMailSubject();
//#endif
};

// Perf traces
#define kPerfMgr_MaxPerf 64L
class PerfMgr
{
public:
    static void Init();
    static void Start(const std::string& aLabel);
    static void Stop();
    static void Close();

    PerfMgr(const std::string& aLabel, const LevelTrace& aLevel=LevelTrace::Perf);
    ~PerfMgr();

    static double       cDuration;

protected:
    static long		cClockTicks;
    static long		cCurPerf;
    static std::string	cPerfLabel[kPerfMgr_MaxPerf];
    static clock_t	cPerfStart[kPerfMgr_MaxPerf];

    std::string  _label;
    LevelTrace _level;
    clock_t    _start;
};

#else

#define tout std::cout

#define TRACEMSG(msg)
#define TRACEVAR(var)
#define TRACEPTR(ptr)
#define TRACEMTD(mtd)  
#define TRACEVAL(msg,val)
#define TRACECUST(msg,obj)
#define ASSERT(cond)
#define FAILASSERT(cond)
#define VERIFY_PTR(ptr)
#define DEBUGMSG(msg)

#define COUT(msg, lvl)
#define TRACEMSG_LVL(msg, lvl)
#define TRACEVAR_LVL(var, lvl)
#define TRACEPTR_LVL(ptr, lvl)
#define TRACEMTD_LVL(mtd, lvl)
#define TRACEVAL_LVL(msg, val, lvl)
#define TRACECUST_LVL(msg, obj, lvl)
#define DEBUGMSG_LVL(msg, lvl)

#define PERF_INIT
#define PERF_AUTO(label)
#define PERF_SQL(label)
#define PERF_START(label)
#define PERF_STOP
#define PERF_CLOSE
#define PERF_DURATION

#endif    // DEBUG

#endif

//f
//----------------------------------------------------------------------
//     FILE NAME   : CoreHandler.C
//
//     DESCRIPTION : Tool to handle signals before a core dump.
//
//     HOW TO USE  : The installation is pretty simple : just
//		     instanciate a static object in the first
//		     lines of your main{} proc.
//
//     AUTHOR      : dor
//     DATE        : 05/12/1997
//----------------------------------------------------------------------
//f

#include "CoreHandler.h"
#include "DebugTools.h"
#include <iostream>
#include <signal.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/stat.h>

//using namespace fretcom;
// Local definitions
#define NULL_SIGNAL_HANDLER	((void (*)(int)) -1)

// Class variable initializations
CoreHandler *CoreHandler::cCoreHandler = NULL;

#ifdef PARALLEL
#include "SharedMemory.h"
extern MgrDatabase2* aMgrDb;
extern Database2* db;
#endif

//f
//----------------------------------------------------------------------
//     METHOD      : CoreHandler::CoreHandler
//
//     DESCRIPTION : Constructor. Install all handlers.
//----------------------------------------------------------------------
//f

CoreHandler::CoreHandler()
{
    *_infosFile = 0;
    *_infos = 0;

    _ill = (CFunction) sigset(SIGILL, (ExternCFunction) CoreSignalHandler);
    if (_ill == NULL_SIGNAL_HANDLER)
	TRACEMSG_LVL("CoreHandler : error on SIGILL", LevelTrace::System);

    _abrt = (CFunction) sigset(SIGABRT, (ExternCFunction) CoreSignalHandler);
    if (_abrt == NULL_SIGNAL_HANDLER)
	TRACEMSG_LVL("CoreHandler : error on SIGABRT", LevelTrace::System);

    //pel_emt = (CFunction) sigset(SIGEMT, (ExternCFunction) CoreSignalHandler);
    //pelif (_emt == NULL_SIGNAL_HANDLER)
	//pel TRACEMSG_LVL("CoreHandler : error on SIGEMT", LevelTrace::System);

    _fpe = (CFunction) sigset(SIGFPE, (ExternCFunction) CoreSignalHandler);
    if (_fpe == NULL_SIGNAL_HANDLER)
	TRACEMSG_LVL("CoreHandler : error on SIGFPE", LevelTrace::System);

    _bus = (CFunction) sigset(SIGBUS, (ExternCFunction) CoreSignalHandler);
    if (_bus == NULL_SIGNAL_HANDLER)
	TRACEMSG_LVL("CoreHandler : error on SIGBUS", LevelTrace::System);

    _segv = (CFunction) sigset(SIGSEGV, (ExternCFunction) CoreSignalHandler);
    if (_segv == NULL_SIGNAL_HANDLER)
	TRACEMSG_LVL("CoreHandler : error on SIGSEGV", LevelTrace::System);

    _sys = (CFunction) sigset(SIGSYS, (ExternCFunction) CoreSignalHandler);
    if (_sys == NULL_SIGNAL_HANDLER)
	TRACEMSG_LVL("CoreHandler : error on SIGSYS", LevelTrace::System);

    if (cCoreHandler == NULL)
      cCoreHandler = this;

} // CoreHandler


//f
//----------------------------------------------------------------------
//     METHOD      : CoreHandler::setInfosFile
//
//     DESCRIPTION : Set the path of a file where to store core infos.
//----------------------------------------------------------------------
//f

void CoreHandler::setInfosFile(const std::string& aInfosFile)
{
  if (cCoreHandler == NULL) return;
  strcpy(cCoreHandler->_infosFile, aInfosFile.data());

} // setInfosFile


//f
//----------------------------------------------------------------------
//     METHOD      : CoreHandler::setInfos
//
//     DESCRIPTION : Set the infos to store in the infos file at the
//		     next core dump.
//----------------------------------------------------------------------
//f

void CoreHandler::setInfos(const std::string& aInfos)
{
  if (cCoreHandler == NULL) return;
  strcpy(cCoreHandler->_infos, aInfos.data());

} // setInfos


//f
//----------------------------------------------------------------------
//     METHOD      : CoreHandler::CoreSignalHandler
//
//     DESCRIPTION : Signal patch entry point.
//----------------------------------------------------------------------
//f

void CoreHandler::CoreSignalHandler(int aSignal)
{
    TRACEMSG_LVL("CORE HANDLER : " << aSignal, LevelTrace::System);

    // Mask core signals to avoid recursive calls
    sigset_t aSignalSet;
    sigemptyset(&aSignalSet);
    sigaddset(&aSignalSet, SIGILL);
    sigaddset(&aSignalSet, SIGABRT);
    //pel sigaddset(&aSignalSet, SIGEMT);
    sigaddset(&aSignalSet, SIGFPE);
    sigaddset(&aSignalSet, SIGBUS);
    sigaddset(&aSignalSet, SIGSEGV);
    sigaddset(&aSignalSet, SIGSYS);
    sigprocmask(SIG_BLOCK, &aSignalSet, NULL);

    // Signal dispatching
    void (*aNatHandler)(int) = NULL_SIGNAL_HANDLER;
    const char *aSignalStr = NULL;

    switch (aSignal)
    {
        case SIGILL :
	    aNatHandler = cCoreHandler->_ill;
	    aSignalStr = "Illegal instruction";
	    break;

        case SIGABRT :
	    aNatHandler = cCoreHandler->_abrt;
	    aSignalStr = "Abort";
	    break;
	    
        //pelcase SIGEMT :
	    //pel aNatHandler = cCoreHandler->_emt;
	    //pel aSignalStr = "EMT instruction";
	   //pel break;

        case SIGFPE :
 	    aNatHandler = cCoreHandler->_fpe;
	    aSignalStr = "Floating point exception";
	    break;

        case SIGBUS :
	    aNatHandler = cCoreHandler->_bus;
	    aSignalStr = "Bus error";
	    break;

        case SIGSEGV :
	    aNatHandler = cCoreHandler->_segv;
	    aSignalStr = "Segmentation violation";
	    break;

        case SIGSYS :
	    aNatHandler = cCoreHandler->_sys;
	    aSignalStr = "Bad argument to system call";
	    break;
    }

    if (aNatHandler != NULL_SIGNAL_HANDLER)
    {
#ifdef PARALLEL
      TRACEMSG_LVL("CoreHandler::CoreSignalHandler : unlockResource........", LevelTrace::System);
      if ( db ) db->rollback();
      // unlock db connection
      aMgrDb->get().unlockResource();
      // signal end of process ... because CoreDump ... (father don't wait)
      aMgrDb->signal();
#endif
	TRACEMSG_LVL("#CORE DUMP# : " << aSignalStr, LevelTrace::System);
	char *aInfosFile = cCoreHandler->_infosFile;
	char *aInfos = cCoreHandler->_infos;
	if (*aInfosFile && *aInfos)
	{
	    // Store infos in info file
	    int aFileID = open(aInfosFile, O_WRONLY | O_APPEND | O_CREAT);
	    if (aFileID != -1)
	    {
		fchmod(aFileID, S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP);
		write(aFileID, aInfos, strlen(aInfos));
		char aRC = '\n';
		write(aFileID, &aRC, 1);
		close(aFileID);
	    }
	}

	(*aNatHandler)(aSignal);   // ###WARNING### : if aNatHandle is NULL... it is correct !
    }

} // CoreSignalHandler

//f
//----------------------------------------------------------------------
//     FILE NAME   : TelegramTraces.C
//
//     DESCRIPTION : Telegram Traces handler.
//
//     AUTHOR      : CB
//     DATE        : 19/01/2004
//----------------------------------------------------------------------
//f

#include <stdlib.h>
#include "TelegramTraces.h"
#include <ctime>
#include <stdio.h>
#include <sys/stat.h>

// Class variable initializations

unsigned int TelegramTraces::cTlgCounter;

//f
//----------------------------------------------------------------------
//     METHOD      : TelegramTraces::Init
//
//     DESCRIPTION : Perform needed initializations to redirect
//		     trace messages to files.
//----------------------------------------------------------------------
//f

void TelegramTraces::Init()
{
  FreightTraces::Init();
  cTlgCounter = 0;   
} // Init


void TelegramTraces::Init(const char* pTracePath, 
		     const char* pFailmailPath, 
		     const char* pCriticalThreshold)
{
  FreightTraces::Init(pTracePath, pFailmailPath, pCriticalThreshold);
  cTlgCounter = 0;   
}
//f
//----------------------------------------------------------------------
//     METHOD      : TelegramTraces::parseTraceFile
//
//     DESCRIPTION : Parse a trace file searching eventual failures.
//
//     INPUT	   : The path of a trace file.
//----------------------------------------------------------------------
//f

void TelegramTraces::parseTraceFile(const std::string& aTraceFilePath)
{
    TraceMgr::parseTraceFile(aTraceFilePath);

} // parseTraceFile


//f
//----------------------------------------------------------------------
//     METHOD      : TelegramTraces::getTraceFilePath
//
//     DESCRIPTION : Get the full path of the trace file to generate.
//----------------------------------------------------------------------
//f

std::string TelegramTraces::getTraceFilePath()
{
  std::string aTraceFilePath = FreightTraces::getTraceFilePath();  
  cTlgCounter++;
  char suffix[10] ;
  sprintf(suffix,".%08d",cTlgCounter); 
  _traceName += suffix;
  CoreHandler::setInfos(aTraceFilePath + suffix);
  
  return aTraceFilePath + suffix;
  
} // getTraceFilePath



//f
//----------------------------------------------------------------------
//     METHOD      : TelegramTraces::getFailMailSubject
//
//     DESCRIPTION : Get the failure mail's subject.
//----------------------------------------------------------------------
//f

std::string TelegramTraces::getFailMailSubject()
{
    return "TLG [" + _rqtName + "]..." + (_coredTrace ? " CORE DUMP" : " FAILURE");
} // getFailMailSubject


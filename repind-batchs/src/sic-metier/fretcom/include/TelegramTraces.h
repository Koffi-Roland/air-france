//f
//----------------------------------------------------------------------
//     FILE NAME   : Telegram.h
//
//     DESCRIPTION : Telegram traces handler.
//
//     AUTHOR      : CB
//     DATE        : 19/01/2004
//----------------------------------------------------------------------
//f

#ifndef TelegramTraces_h
#define TelegramTraces_h    1

#include <string>
#include "FreightTraces.h"
#include "CoreHandler.h"


class TelegramTraces : public FreightTraces
{
public:
    static unsigned int cTlgCounter;

    // Class methods
    static void Init();
    static void Init(const char* pTracePath, 
		     const char* pFailmailPath=0, 
		     const char* pCriticalThreshold=0);

    // Methods
    TelegramTraces() : FreightTraces() {}
    TelegramTraces(const std::string& aTlgName) : FreightTraces(aTlgName) {}
    ~TelegramTraces() {}
    void parseTraceFile(const std::string& aTraceFilePath);
    
protected:
    std::string getTraceFilePath();
    std::string getFailMailSubject();

};

#endif // TelegramTrace_h

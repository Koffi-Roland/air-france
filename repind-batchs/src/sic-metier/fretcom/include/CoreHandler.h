//f
//----------------------------------------------------------------------
//     FILE NAME   : CoreHandler.h
//
//     DESCRIPTION : Tool to handle signals before a core dump.
//
//     AUTHOR      : dor
//     DATE        : 05/12/1997
//----------------------------------------------------------------------
//f

#ifndef CoreHandler_h
#define CoreHandler_h    1

#include <string>


extern "C" { 
  typedef void(*ExternCFunction)(int); 
}; 

typedef void(*CFunction)(int); 

class CoreHandler
{
public:
    CoreHandler();
    static void		setInfosFile(const std::string& aInfosFile);
    static void		setInfos(const std::string& aInfos);
    static std::string	getInfosFile() { return cCoreHandler->_infosFile; }
    static std::string	getInfos() { return cCoreHandler->_infos; }

private:
    static CoreHandler *cCoreHandler;
    static void CoreSignalHandler(int aSignal);

    // Infos management
    char _infosFile[256];
    char _infos[256];

    // Native handlers
    void (*_ill)(int);
    void (*_abrt)(int);
    void (*_emt)(int);
    void (*_fpe)(int);
    void (*_bus)(int);
    void (*_segv)(int);
    void (*_sys)(int);
};

#endif

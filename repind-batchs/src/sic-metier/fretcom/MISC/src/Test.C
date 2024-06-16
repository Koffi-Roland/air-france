#include <stdlib.h>
#include "Ressource.h"
#include "DebugTools.h"

// extern char *getenv();

Ressource* aFileException = new Ressource();


int main()
{
  char* aFile = getenv("TACTIC_EXCEPTION");
  if (aFile) 
    {
      aFileException->load(aFile);
    }
  else
    {
      TRACEMSG("Variable TACTIC_EXCEPTION non renseignee ......"); 
    }
  
  std::string aLabel = aFileException->getValue("ExMsg_Unknow");
  TRACEVAR(aLabel);

  return 0;
}

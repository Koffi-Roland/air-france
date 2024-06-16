#include "WrapperReadTestData.h"

WrapperRead::WrapperRead(const std::string& pStatment, const DbKeyEmpty& pDbKey, FctUpdateMembers fct)
  : WrapperInData(pStatment, &pDbKey, fct)
{

}


void WrapperRead::readInDataBase()
{
  WrapperRead aWrapper("SELECT COF_NAME FROM COFFEES");
  aWrapper.execute();
}

void* WrapperRead::updateDataMembers(Request* pRequest)
{
  while (pRequest->getNextTuple())
    {
      TRACEVAR( pRequest->getStringValue ("cof_name") );
    }
  return 0;
}

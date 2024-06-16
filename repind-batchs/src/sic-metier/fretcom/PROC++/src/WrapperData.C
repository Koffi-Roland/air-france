#include "WrapperData.h"
#include <list>

void Empty::setData(char* rcp, char* data, int len)
{
  if (data == NULL || strlen(data) == 0)
    { 
      *pIndic++ = INDICATOR_NULL;
    }
  else
    { 
      *pIndic++ = INDICATOR_NOT_NULL; 
      strncpy( rcp, data, len );
    }
  setActive(true);
}

void Empty::setData(int i, char* rcp, char* data, unsigned len)
{
  if (data == NULL || strlen(data) == 0)
    { 
      _indic[i] = INDICATOR_NULL;
    }
  else
    { 
      _indic[i] = INDICATOR_NOT_NULL; 
      strncpy( rcp, data, len );
    }

  setActive(true);
}

void Empty::setData(Request2* request, char* rcp, char* data, unsigned len)
{
  if (data == NULL || strlen(data) == 0)
    {
      _indic[0] = INDICATOR_NULL;
    }
  else
    {
      request->bindHostVar(this->column.c_str(), VARCHAR2, len);
      request->putHostVar(this->column.c_str(), data, this->getIndicator() );
      _indic[0] = INDICATOR_NOT_NULL;
      strncpy( rcp, data, len );
    }
}
void Empty::setIndicator(int i, IndicatorOracle ind)
{
  _indic[i] = ind;
  setActive(true);
}

void Empty::setIndicator(IndicatorOracle ind)
{
  *pIndic++ = ind;
  setActive(true);
}


WrapperInData::WrapperInData(const std::string& pStatment, 
			     const DbKey* pDbKey,
			     void* (WrapperInData::*fct)(Request2*))
  : WrapperData(pStatment),
    _dbKey(pDbKey),
    _updateDataMembers(fct)
{}



void  WrapperInData::setContext(const std::string& pStatment, FctUpdateMembers fct)
{
  PERF_AUTO(" Wrapper::setContext");
  setSqlStatment(pStatment);
  setUpdateFct(fct);
}   



void* WrapperInData::execute()
{
  PERF_AUTO("Wrapper::read");
  //std::string stmt(_sqlStatment + _dbKey->getWhereClause());
  std::string stmt(_sqlStatment + _dbKey->getWhereClause());
  _request->parse( stmt.data() ); 
  _dbKey->bindAndPutHostVar(_request);
  _request->execute();
  TRACEVAL("WrapperInData::read: rows" , _request->getNumberOfRows() );
  return (this->*_updateDataMembers)( _request );
}



WrapperDeleteData::WrapperDeleteData(const std::string& pStatment, 
				     const DbKey* pDbKey)
  : WrapperData(pStatment),
    _dbKey(pDbKey)
{}



void* WrapperDeleteData::execute()
{
  PERF_AUTO("WrapperDeleteData::execute");
  //std::string stmt(_sqlStatment + _dbKey->getWhereClause());
  std::string stmt(_sqlStatment + _dbKey->getWhereClause());
  _request->parse( stmt.data() ); 
  _dbKey->bindAndPutHostVar(_request);
  _request->execute();
  return 0;
}




void* WrapperOutData::execute()
{
  _request->parse( _sqlStatment.data() , getSize() ); 
  bindAndPutHostVar(_request);
  _request->execute();
  return 0;
}



void WrapperOutData::init(int pSize) 
{
  clear();
  setSize(pSize);
  initData();
}


void WrapperOutData::reset() 
{
  //std::listIterator<Empty> iter(_list);
  std::list<Empty> mylist;
  std::list<Empty>::iterator iter;
  Empty* element(0);
  for (iter=mylist.begin(); iter!=mylist.end(); ++iter)
    {
      element->reset();
    }  
}

void WrapperOutData::bindAndPutHostVar(Request2* request)
{
  //std::listIterator<Empty> iter(_list);
  //std::list<Empty> mylist;
  std::list<Empty>::iterator iter;
  iter=_list.begin();
  //Empty* element(0);
  for (iter=_list.begin(); iter!=_list.end(); ++iter)
  {
      iter->bindAndPutHostVar(request);
      //request->bindHostVar(column.c_str(), VARCHAR2, size); 
      //request->putHostVar(column.c_str(), this->data(), this->getIndicator() ); 
      if (!iter->isActive())
        iter++;
      //else
	//continue;
      //element->bindAndPutHostVar(request);
    }  
}


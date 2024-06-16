#ifndef WrapperData_h
#define WrapperData_h 1

#include "MemoryMgr.h"
#include <strings.h>
#include "DebugTools.h"
#include "list"
#include "interfaceOracle2.h"
//pel #include "typeinfo.h"
#include <typeinfo>

std::string ltrim(const std::string& s);
std::string rtrim(const std::string& s);
std::string trim(const std::string& s);

using namespace fretcom;
extern Database2* db;

/*-------------------------------------------------------------------------------------------------------
 *
 * CB07042004 - Ajout de l'attribut "active" permettant de prendre en compte (ou pas) l'instance 
 * ----------
 * Ceci permet d'utiliser le meme Wrapper pour executer plusieurs requetes <> (a priori sur la meme table)
 * Par defaut l'instance n'est pas prise en compte.
 * La prise en compte se fait lors de l'appel aux methodes "set".
 *
 *-------------------------------------------------------------------------------------------------------*/

class Empty MEM_OBJ
{
protected:
  Indicator _indic;
  short* pIndic;
  std::string column;
  bool active;
protected:
  Empty() : pIndic (&_indic), active(false) {}
  Empty(const std::string& pCol) : column(pCol), pIndic (&_indic), active(false) {}
  void setData(char* rcp, char* data, int len); 
  void setData(int i, char* rcp, char* data, unsigned len); 
  void setData(  Request2* request, char* rcp, char* data, unsigned len);
  short* getIndicator() { return & _indic; }
public:
  virtual ~Empty() {}
  virtual void reset() {}
  virtual void bindAndPutHostVar(Request2* request) {}
  void setActive(bool pActive) { active=pActive; }
  bool isActive() { return active==true; }
  void setIndicator(int i, IndicatorOracle ind);
  void setIndicator(IndicatorOracle ind);

};




class WrapperData MEM_OBJ 
{
protected:
  Request2*     _request;
  std::string    _sqlStatment;
public:
  WrapperData(const std::string& pStatment="") : _sqlStatment(pStatment), _request(db->newRequest()) {}
  virtual ~WrapperData() { if (_request) { delete _request; _request = 0; } }
  const std::string& getSqlStatment() const { return _sqlStatment; }
  void  setSqlStatment(const std::string& pStatment) { _sqlStatment = pStatment; }
  virtual void* execute()=0;
  int getNumberOfRows() const { return _request->getNumberOfRows(); }
}; 


class WrapperInData;

typedef void* (WrapperInData::*FctUpdateMembers)(Request2* pRequest);

class WrapperInData : public WrapperData 
{
public:

  WrapperInData(const std::string& pStatment, const DbKey* pDbKey = new DbKeyEmpty,
		void* (WrapperInData::*fct)(Request2*) = &WrapperInData::updateDataMembers);
		//pel  static void* (WrapperInData::*fct)(Request*) = WrapperInData::updateDataMembers);
 
  virtual void* updateDataMembers(Request2* pRequest)=0;
  void* execute();


  void setContext(const std::string& pStatment, 
		  FctUpdateMembers fct = &WrapperInData::updateDataMembers);
		//pel   FctUpdateMembers fct = WrapperInData::updateDataMembers);

  void* (WrapperInData::*_updateDataMembers)(Request2* pRequest);

  void setUpdateFct(FctUpdateMembers fct) { _updateDataMembers = fct; }

  //  FctUpdate _updateDataMembers;

 protected:
  
  const DbKey* _dbKey;

};



class WrapperOutData : public WrapperData 
{
protected:
  std::list<Empty> _list;
  int _size;
public:
  WrapperOutData() : _size(0) {}
  WrapperOutData(const std::string& pStatment) : WrapperData(pStatment), _size(0) {}
  WrapperOutData(const std::string& pStatment, int pSize) : WrapperData(pStatment) { init(pSize); }

  virtual ~WrapperOutData() { _list.clear(); }
  virtual void clear() { _list.clear(); _size = 0; }
  virtual void reset();
  virtual void initData()=0;
  void init(int pSize);
  void* execute();

  void setSize(int pSize) { _size = pSize; }
  int  getSize() { return _size; }
  std::list<Empty>&  getList() { return _list; }

  void bindAndPutHostVar(Request2* request);

};
 

class WrapperDeleteData : public WrapperData 
{
public:

  WrapperDeleteData(const std::string& pStatment, const DbKey* pDbKey);
 
  void* execute();

 protected:
  
  const DbKey* _dbKey;

};





#define INIT(name, colName) \
  p##name = new name##Obj(this, colName);
  //p##name = new name##Obj(this, ##colName);


#define STR_DATA(name, size) \
  struct name##Obj : public Empty { \
    union name { \
       char  data[size + 1]; \
    }; \
    void  init(WrapperOutData* pData) { \
       _indic = Indicator(pData->getSize()); \
       pIndic = &_indic; \
       p##name = new name[pData->getSize()]; \
       memset(p##name, 0, sizeof(*p##name) * pData->getSize()); \
       pData->getList().push_front(*this); \
       pCurrent##name = p##name; \
     } \
    name##Obj() : p##name(NULL), pCurrent##name(NULL) { ; } \
    name##Obj(WrapperOutData* pType, const std::string& colName) : Empty(colName) { this->init(pType); } \
    ~name##Obj() { if (p##name) { delete [] p##name; p##name = NULL; } } \
    void set(int i, char* data) { setData(i, p##name[i].data, data, sizeof(name)); } \
    void set(Request2* request,const std::string& data) { if ((std::string*) &data != NULL) {setData(request, p##name[0].data,(char*) data.c_str(), sizeof(name)); }} \
    void set(int i, const std::string& data) { set(i, (char*) data.data()); }\
    void add(char* data) { setData(pCurrent##name++->data, data, sizeof(name)); } \
    void add(const std::string& data) { add( (char*) data.data() ); } \
    name* data() { return p##name; } \
    void  reset() { pCurrent##name = p##name; } \
    void bindAndPutHostVar(Request2* request) { \
         request->bindHostVar(column.c_str(), VARCHAR2, size); \
         request->putHostVar(column.c_str(), this->data(), this->getIndicator() ); \
    } \
    name* p##name; \
    name* pCurrent##name; \
  }; \
  name##Obj* p##name \


#define NUM_DATA(name, typedata) \
  struct name##Obj : public Empty { \
    union name { \
       typedata  data; \
    }; \
    void  init(WrapperOutData* pData) { \
       _indic = Indicator(pData->getSize()); \
       pIndic = &_indic; \
       p##name = new name[pData->getSize()]; \
       memset(p##name, 0, sizeof(*p##name) * pData->getSize()); \
       pData->getList().insert(this); \
       p##nameCurrent = p##name; \
     } \
    name##Obj() : p##name(NULL), p##nameCurrent(NULL) { ; } \
    name##Obj(WrapperOutData* pType, const std::string& colName) : Empty(colName) { this->init(pType); } \
    ~name##Obj() { if (p##name) { delete [] p##name; p##name = NULL; } } \
    void  set(int i, typedata data) { p##name[i].data = data; setIndicator(i, INDICATOR_NOT_NULL); } \
    void  add(typedata data) { p##nameCurrent++->data = data; setIndicator(INDICATOR_NOT_NULL); } \
    void  reset() { p##nameCurrent = p##name; } \
    name* data() { return p##name; } \
    void bindAndPutHostVar(Request2* request) { \
         TYPE_ORACLE type = NUMBER; \
         if (typeid(typedata) == typeid(double)) \
             type = DOUBLE; \
         else if (typeid(typedata) == typeid(float)) \
             type = FLOAT; \
         request->bindHostVar(column.c_str(), type); \
         request->putHostVar(column.c_str(), this->data(), this->getIndicator() ); \
    } \
    name* p##name; \
    name* p##nameCurrent; \
  }; \
  name##Obj* p##name \



#define NEW_WRAPPER_UPDATE(name) \
struct name : public WrapperOutData { \
  name() : WrapperOutData() { } \
  name(const std::string& pStatment) : WrapperOutData(pStatment) { } \
  name(const std::string& pStatment, int pSize) : WrapperOutData(pStatment, pSize) { } \
  void initData();\

#define END_WRAPPER };

#define NEW_WRAPPER_UPDATE_FROM(name, name1) \
struct name : public name1 { \
  name() : name1() { } \
  name(const std::string& pStatment) : name1(pStatment) { } \
  name(const std::string& pStatment, int pSize) : name1(pStatment, pSize) { } \
  void initData();\


#define INIT_WRAPPER(name) \
void  name::initData() \
{ \
  ASSERT(_size > 0); \

#define INIT_WRAPPER_FROM(name, name1) \
void  name::initData() \
{ \
  ASSERT(_size > 0); \
  name1::initData(); \


#endif

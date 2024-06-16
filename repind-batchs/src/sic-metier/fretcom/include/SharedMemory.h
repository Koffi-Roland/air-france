#ifndef _SHARED_MEMORY_H_
#define _SHARED_MEMORY_H_

#include <stdlib.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <assert.h>
#include <mutex>
#include <vector>
//#include <rw/tvordvec.h> 
#include <sys/errno.h>

const int UMASK = 0600;

#define TRAIL_ALIGN(size)    (sizeof(long) - ((((size) - 1) & (sizeof(long) - 1)) + 1))

//----------------------------------------
//----------------------------------------
//
//       Object in shared Memory
//
//----------------------------------------
//----------------------------------------

template <class T>
class SharedObject {
public:
  //std::mutex    mutex;
  std::mutex    mutex;
  cond_t     cond;
  //std::mutex    mutexLock;
  std::mutex    mutexLock;
  cond_t     condLock;  
private:
  size_t     _size;
  int        _count;
  T*         _shared;
  //std::mutex*   _mutexes;
  std::mutex*   _mutexes;
  pid_t*     _pids;
public:
  SharedObject(size_t aSize, const T& aT) 
    : _size(aSize), _count(0), _shared(NULL)
    {
      // compute addresses of arrays
      unsigned long aVoid = (unsigned long) this + sizeof(SharedObject<T>);
      _shared = (T*) ( aVoid + TRAIL_ALIGN(aVoid) );
      aVoid += sizeof(T) * _size;
      _mutexes = (std::mutex*) ( aVoid + TRAIL_ALIGN(aVoid) );
      aVoid +=  sizeof(std::mutex) * _size;
      _pids = (pid_t*) ( aVoid + TRAIL_ALIGN(aVoid) );

      // initialization of attributs
      for (int i = 0; i < _size ; i++) {
	_shared[i] = aT;
	_mutexes[i] = mutex;
	_pids[i] = 0;
	mutex_init( &_mutexes[i], USYNC_PROCESS, 0);
      }

      mutex_init( &mutex, USYNC_PROCESS, 0);
      mutex_init( &mutexLock, USYNC_PROCESS, 0);
      cond_init( &cond, USYNC_PROCESS, 0); 
      cond_init( &condLock, USYNC_PROCESS, 0); 
    }

  //CB mig
  void init(void* aAddr, size_t aSize, const T& aT) 
    {
      TRACEMSG("SharedObject::init  at  " << aAddr);
      _size=aSize;
      _count=0;
      _shared=NULL;
      // compute addresses of arrays
      unsigned long aVoid = (unsigned long) aAddr + sizeof(SharedObject<T>);
      _shared = (T*) ( aVoid + TRAIL_ALIGN(aVoid) );
      aVoid += sizeof(T) * _size;
      _mutexes = (std::mutex*) ( aVoid + TRAIL_ALIGN(aVoid) );
      aVoid +=  sizeof(std::mutex) * _size;
      _pids = (pid_t*) ( aVoid + TRAIL_ALIGN(aVoid) );

      // initialization of attributs
      for (int i = 0; i < _size ; i++) {
	_shared[i] = aT;
	_mutexes[i] = mutex;
	_pids[i] = 0;
	mutex_init( &_mutexes[i], USYNC_PROCESS, 0);
      }

      mutex_init( &mutex, USYNC_PROCESS, 0);
      mutex_init( &mutexLock, USYNC_PROCESS, 0);
      cond_init( &cond, USYNC_PROCESS, 0); 
      cond_init( &condLock, USYNC_PROCESS, 0); 
    }


  //-------------------------------------
  //
  //   get item indexed by i
  //   --->>>> update operator
  //
  //-------------------------------------

  T& operator[](int i) { 
    //mutex.lock();
    mutex.lock();
    assert(i<_size);
    //mutex.unlock();
    mutex.unlock();
    return _shared[i]; 
  }   

  //-------------------------------------
  //
  // get and lock resource in SM
  // or wait first unlocked resource
  //
  //-------------------------------------

  T& lock() {
    mutex.lock();
    int ret=0;
    do {
      for (int i=0;i<_size;i++) {
	if ( std::mutexrylock( &_mutexes[i] ) == 0 ) {
	  _pids[i] = getpid();
	  TRACEMSG("Pid : " << _pids[i] << " lock the resource : " << i);
	  mutex.unlock();
	  return _shared[i];  
	}
	// s'ils sont tous lockes , attente du 1er unlock
      }
      TRACEMSG("no resource available for pid : " << getpid());
    }
    while ( cond_wait( &condLock, &mutexLock ) == 0 );
    mutex.unlock();   
  }


  //-------------------------------------
  //
  // unlock resource in SM
  //
  //-------------------------------------

  int unlock() {
    pid_t aPid = getpid();
    for (int i=0;i<_size;i++) {
      if ( _pids[i] != aPid ) continue;
      _pids[i] = 0;
      mutex_unlock(&_mutexes[i]);
      // envoi d'1 signal d'unlock
      TRACEMSG("Pid : " << aPid << " unlock Resource : " << i);
      cond_signal(&condLock);
      return true;
    }
    TRACEMSG("Pid : " << aPid << " have no Resource ");
    return false;
  }  


  //-------------------------------------
  //
  // unlock all resources in SM
  // (or all resources for a pid)
  //
  // Mutex are not used in this method 
  // because it's for unlock resource 
  // immediatly
  //
  //-------------------------------------

  void unlockAll(pid_t aPid = 0) {
    TRACEMSG("unlockAll ressources for pid : " << aPid
	     << " by process : " << getpid());
    for (int i=0;i<_size;i++) {
      if ( aPid && _pids[i] != aPid ) continue;
      TRACEMSG("UnlockAll - pid : " << _pids[i]);
      _pids[i] = 0;
      mutex_unlock(&_mutexes[i]);
      cond_signal(&condLock);
    }
  }

  void unlockAll( const std::vector<pid_t> aVector ) {    
    for (std::vector<pid>::iterator i=aVector.begin();it != aVector.end();i++) {
      unlockAll( *i );
      //unlockAll( aVector[i] );
    }
  }

  T getResource(pid_t aPid) {
    int i=0;
    for (i=0;i<_size;i++) {      
      if (_pids[i] == aPid) {
	TRACEMSG("getResource Pid : " << _pids[i] << " indice : " << i);
	return _shared[i];  
      }
    }
    TRACEMSG("getResource Pid : " << _pids[i] << " Not Found");  
    return (T) NULL;
  }

  //-------------------------------------
  //
  //   signal that one element of the 
  //   object has finished his job
  //
  //-------------------------------------  

  void signal() {
    operator++();
    cond_signal( &cond );
  }


  //-------------------------------------
  //
  //   increment the counter of updates
  //
  //-------------------------------------

  void operator++(void) { 
    mutex.lock();
    _count++;
    mutex.unlock();
  }   


  //-------------------------------------
  //
  //   all items are updated ????
  //
  //-------------------------------------

  bool isOk() const {
    return _size <= _count;
  }

  //-------------------------------------
  //
  //   at least one item is updated ????
  //
  //-------------------------------------

  bool oneIsOk() const {
    return _count > 0;
  }

  //-------------------------------------
  //
  //   destroy mutex and cond var
  //
  //-------------------------------------  

  void destroy() {
    mutex_destroy( &mutex );
    mutex_destroy( &mutexLock );
    cond_destroy( &cond );
    cond_destroy( &condLock );
    for (int i=0;i<_size;i++) mutex_destroy( &_mutexes[i] );
  }

  void display( std::ostream& os ) const {
    os << " Number Object : " << _size << std::endl
       << " Objects values : " << std::endl;
    for (int i=0;i<_size;i++)
      os << "\t\t " << _shared[i] << std::endl;
  }

  friend std::ostream& operator<<(std::ostream& os, const SharedObject& aObject) {
    aObject.display( os );
    return os;
  }

};



//----------------------------------------
//----------------------------------------
//
//   Manager of Object in share Memory
//
//----------------------------------------
//----------------------------------------


template <class T>
class SharedMemory {
  key_t        _key;
  // number of items in _sharedObject
  size_t       _size;
  // size of _sharedObject
  size_t       _sizeObject;
  int          _shmid;
  SharedObject<T>* _sharedObject;
public:
  enum WaitType { WaitAll=0, WaitOne=1 };
public:
  SharedMemory(size_t aSize, const T& aT) 
    : _size(aSize), _sharedObject(NULL)
    {
      setSize( _size, aT );
    }
  
  SharedMemory() 
    : _size(0), _sharedObject(NULL), _key(-1), _shmid(-1)
    {}
  
  bool isValid() const { return _size != 0; }
  
  void setSize(size_t aSize, const T& aT) 
    {
      _size = aSize;
      _sizeObject
	= sizeof(SharedObject<T>) + TRAIL_ALIGN( sizeof(SharedObject<T>) )
	+ sizeof(T) * aSize + TRAIL_ALIGN( sizeof(T) * aSize )
	+ sizeof(std::mutex) * aSize + TRAIL_ALIGN( sizeof(std::mutex) * aSize )
	+ sizeof(pid_t) * aSize + TRAIL_ALIGN( sizeof(pid_t) * aSize );
      
      int rand = lrand48();
      _key = ftok(getenv("PWD"), rand);
      TRACEMSG("Key : " << _key);
      _shmid = shmget( _key, _sizeObject, IPC_CREAT|UMASK );
      TRACEMSG("Shmid : " << _shmid);
      _sharedObject = get();
      TRACEMSG("Address of SHAREDOBJECT : " << _sharedObject);
      // write object in SM
      // *_sharedObject = SharedObject<T>(aSize, aT);
      _sharedObject->init(_sharedObject, aSize, aT);
      unget();
    }


  //-------------------------------------
  //
  // only unget segment in destructor
  // because if this object is used with
  // children (fork) this object will 
  // duplicate and each child will call
  // this destructor
  //
  // To destroy SM, the father must call
  // destroy method before this destruct
  //
  //-------------------------------------  

  virtual ~SharedMemory() {
    TRACEMSG("~SharedMemory - Pid : " << getpid());
    struct shmid_ds aStruct;
    shmctl(_shmid, IPC_STAT, &aStruct);
    // only the creator of segment must destroy it
    if ( aStruct.shm_cpid == getpid() )
      destroy();
    unget();
  }

  //-------------------------------------
  //
  //   destroy object in shared memory 
  //   and shared memory allocated
  //
  //   Normally call by the father
  //
  //-------------------------------------  

  void destroy() { 
    TRACEMSG("Pid : " << getpid() << " destroy shmid : " << _shmid);
    get();
    if ( _sharedObject != (SharedObject<T>*) -1 )
      _sharedObject->destroy();
    unget();
    shmctl(_shmid, IPC_RMID, 0);
  }

  // get key of shared memory
  key_t    getKey()  const { return _key; }

  // get id of shared memory  
  int      getId()   const { return _shmid; }

  // get number of elements in the object in SM
  size_t   getSize() const { return _size; }

  // cast operator 
  operator T&()  { return operator[](0); }

  // get and lock resource in SM
  T lockResource() {
    get();
    T aTmp = _sharedObject->lock();
    unget();
    return aTmp;
  }

  // unlock resource in SM
  int unlockResource() {
    get();
    int aRet = _sharedObject->unlock();
    unget();
    return aRet;
  }  

  T getResource(pid_t aPid) {
    get();
    T aTmp = _sharedObject->getResource(aPid);
    unget();
    return aTmp;    
  }

  void unlockAll(pid_t aPid = 0) {
    get();
    _sharedObject->unlockAll(aPid);
    unget();
  }

  void unlockAll(const std::vector<pid_t> aVector) {
    get();
    _sharedObject->unlockAll(aVector);
    unget();
  }

  
  //-------------------------------------
  //
  //  get segment of SM  (attachment)
  //
  //-------------------------------------  
  
  SharedObject<T>* get() {
    if ( ! _sharedObject ) {
      int shmid = shmget( _key, _sizeObject, 0 );
      _sharedObject = (SharedObject<T>*) shmat(shmid,0,0);
    }
    return _sharedObject;
  }


  //-------------------------------------
  //
  //  unget segment of SM  (detattachment)
  //
  //-------------------------------------  

  void unget() { 
    if ( _sharedObject ) {
      shmdt( (char*) _sharedObject ); 
      _sharedObject = NULL;
    }
  }

  // get i th element of the object in SM
  // -------->>>>    read operator
  T getValue(int i=0) { 
    assert(i<_size);
    get();
    T aT = _sharedObject->operator[](i);
    unget();
    return aT;     
  }

  // get the indice of element equal to parameter in SM
  // warning (operator== of T'object)
  // -------->>>>    read operator
  int getIndex(const T& aT) { 
    get();
    int index = 0;
    for (int i=0;i<_size;i++) {
      if ( aT == _sharedObject->operator[](i) ) {
	index = i;
	break;
      }
    }
    unget();
    return index;     
  }

  // get i th element of the object in SM
  // -------->>>>    update operator
  T& operator[](int i) { 
    assert(i<_size);
    get();
    T& aT = _sharedObject->operator[](i);
    return aT;     
  }

  void setValue(int i, const T& aRef) { 
    assert(i<_size);
    get();
    _sharedObject->operator[](i) = aRef;
    unget();
  }    

  void setValue(const T& aRef) { 
    for (int i=0;i<_size;i++) setValue(i, aRef);
  }    


  //-------------------------------------
  //
  //   wait condition in SM
  //
  //-------------------------------------
  //
  //   wait that child(or children) make
  //   signal()
  //
  //   by default wait all children
  //
  //-------------------------------------  
 
  //  int wait( WaitType aWait = WaitAll, timestruc_t * abstime = NULL ) {
  int wait( WaitType aWait = WaitAll, time_t seconds = 0 ) {

    int	retcode = 0;  
    get();
    mutex_lock( &getMutex() );

    if ( seconds ) {
      timestruc_t abstime;
      abstime.tv_sec  = time(NULL) + seconds;
      abstime.tv_nsec = 0;
      while ( ! (aWait == WaitAll ? _sharedObject->isOk() : _sharedObject->oneIsOk()) )
	{
	  retcode = cond_timedwait( &getCond(), &getMutex(), &abstime );
	  if ( retcode == ETIME ) break;
	}
    }
    else
      while ( ! (aWait == WaitAll ? _sharedObject->isOk() : _sharedObject->oneIsOk()) )
	{
	  cond_wait( &getCond(), &getMutex() );
	}
    
    mutex_unlock( &getMutex() ); 
    unget();
    return retcode;
  }


  //-------------------------------------
  //
  //   signal that one element of the 
  //   object has finished his job
  //
  //-------------------------------------  

  void signal() {
    get();
    _sharedObject->operator++();
    cond_signal(&getCond());
    unget();
  }


  // display method
  void display( std::ostream& os ) const {
    SharedObject<T>* aTmp = get();
    os << "Key : " << _key << std::endl
       << "Id : " << _shmid << std::endl;
    if ( aTmp == (SharedObject<T>*) -1 )
       os <<  " ---> Unallocated" << std::endl;
    else
      os << *aTmp << std::endl;
    unget(aTmp);
  }

  friend std::ostream& operator<<(std::ostream& os, const SharedMemory& aShare) {
    aShare.display( os );
    return os;
  }

private:

  // get mutex in SM
  std::mutex& getMutex() { return _sharedObject->mutex; }
  
  // get cond var in SM
  cond_t&  getCond()  { return _sharedObject->cond; }

  //  for display method
  SharedObject<T>*   get() const { 
    return (SharedObject<T>*) shmat(_shmid,0,0);
  }

  //  for display method
  void unget(SharedObject<T>* aShare) const { shmdt( (char*) aShare ); }


};



class SharedMutex : public SharedMemory<int> {  
public:
  SharedMutex() : SharedMemory<int>( 1, 0 ) {}
  void lock() { lockResource(); }
  void unlock() { unlockResource(); }
};



#include "interfaceOracle2.h"
#include <list> 
#include <vector> 

class MgrDatabase {
  std::list<Database>   _dbList;
  SharedMemory<Database*> _sharedDb;
  // SM id associated to child (to unlock if coredump in child)
  int _shmid;
  
public:
  MgrDatabase(char* aDbUser, char* aDbPwd, int nbConnect) : _shmid( -1 ) {
    _sharedDb.setSize(nbConnect, NULL);
    for (int i=0;i<nbConnect;i++) {
      Database* db = new Database(aDbUser, aDbPwd);
      _dbList.insert(db);
      _sharedDb.setValue(i, db);
    }
  }

  ~MgrDatabase() { _dbList.clearAndDestroy(); }

  SharedMemory<Database*>& get() { return _sharedDb; }

  void setShmid( int aShmid ) { _shmid = aShmid; }

  void signal() {
    TRACEMSG("Pid : " << getpid() << " use shmid : " << _shmid);
    if ( _shmid != -1 ) {
      SharedObject<bool>* sharedObject = (SharedObject<bool>*) shmat(_shmid,0,0);
      sharedObject->signal();
      shmdt( (char*) sharedObject );
    }
  }
 
};




#endif

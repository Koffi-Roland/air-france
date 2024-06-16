//f
//----------------------------------------------------------------------
//     FILE NAME   : MemoryMgr.h
//
//     DESCRIPTION : Tools to debug memory management.
//
//     AUTHOR      : dor
//     DATE        : 05/12/1997
//----------------------------------------------------------------------
//f

#ifndef MemoryMgr_h
#define MemoryMgr_h	1


#ifdef __USE_MEMMGR__

//pel #include <rw/mempool.h>   // MUST be included before MemoryMgr.h ('new' macro)
#include <unistd.h>
#include <pthread.h>

/*
    WARNING : Comment the next line when deleting an object's array
              which has been allocated with non-standard operator new
	      will work.
*/

#define __CC_ARRAY_BUG__	1

// *** Usefull macros
#define MEM_OBJ			: public MemoryMgrObj
#define MEM_SET_NO_PURGE(p)	MemoryMgr::SetBlockNotPurgeable((char*) (p))
#define MEM_SET_PURGE(p)	MemoryMgr::SetBlockPurgeable((char*) (p))
#define MEM_IS_PURGE(p)		MemoryMgr::IsBlockPurgeable((char*) (p))
#define MEM_DFLT_PURGE(b)	MemoryMgr::SetPurgeDefault(b)
#define MEM_PURGE_STACK		MemoryMgr::PurgeStack()

#ifdef DEBUG
#define NEW			new(__FILE__, __LINE__)
#define MEM_CHECK(p)		MemoryMgr::CheckUserBlock((char*) (p))
#define MEM_CHECK_ADDR(p)	MemoryMgr::CheckAddress((char*) (p))
#define MEM_CHECK_STACK		MemoryMgr::CheckStack()
#define MEM_SHOW(p)		MemoryMgr::DumpUserBlock((char*) (p), #p)
#define MEM_SHOW_CONTENT(p)	MemoryMgr::DumpUserBlock((char*) (p), #p, true)
#define MEM_SHOW_STACK		MemoryMgr::DumpStack()
#define MEM_SHOW_TOTAL		MemoryMgr::DumpStack(false)
#define MEM_LEAK_START		MemoryMgr::StartDetectLeaks()
#define MEM_LEAK_STOP		MemoryMgr::StopDetectLeaks()
#define MEM_MAX_SIZE(s)		MemoryMgr::cMaxSize = (s)
#define MEM_SET_INFO(p)		MemoryMgr::SetUserFileInfo((char*) (p), __FILE__, __LINE__)
#define MEM_TRACK_START		MemoryMgr::StartTrackMax()
#define MEM_TRACK_STOP		MemoryMgr::StopTrackMax()
#else
#define NEW			new
#define MEM_CHECK(p)
#define MEM_CHECK_ADDR(p)
#define MEM_CHECK_STACK
#define MEM_SHOW(p)
#define MEM_SHOW_CONTENT(p)
#define MEM_SHOW_STACK
#define MEM_SHOW_TOTAL
#define MEM_LEAK_START
#define MEM_LEAK_STOP
#define MEM_MAX_SIZE(s)
#define MEM_SET_INFO(p)
#define MEM_TRACK_START
#define MEM_TRACK_STOP
#endif



// *** Definitions
typedef short BlockFlag;
#ifdef DEBUG
// to overload standard new
#define _NEW_HDR
typedef char BlockState;
void* operator new(size_t aUserSize, const char *aFileName, int aLineNb);
void* operator new[](size_t aUserSize, const char *aFileName, int aLineNb);

#endif


// Class definitions
class MemoryMgrObj
{
public:
    virtual	~MemoryMgrObj() {}
    void	*operator new(size_t aUserSize);
    void	*operator new[](size_t aUserSize);
#ifdef DEBUG
    void	*operator new(size_t aUserSize, const char *aFileName, int aLineNb);
    void	*operator new[](size_t aUserSize, const char *aFileName, int aLineNb);
#endif
    void	operator delete(void *aUserBlock);
    void	operator delete[](void *aUserBlock);
};

class MemoryMgr
{
public:
#ifndef __MEMMGR_STUB__
    //pel static char*	cStack;		// Allocated blocks stack
    static char*	cStack;		// Allocated blocks stack
    static BlockFlag	cDefaultFlags;	// Default block flag
    static pthread_mutex_t	cLowLock;	// Mutex for multi threads env
    static pthread_mutex_t	cHighLock;	// Mutex for multi threads env
#ifdef DEBUG
    static size_t	cMaxSize;	// Max reasonable allocation size
    static short	cTrackMaxStat;	// true when max stat tracking is enabled
    static size_t	cTrackCurSize;	// Current total allocated size
    static size_t	cTrackMaxSize;	// Maximum total allocated size
    static long		cTrackCurNb;	// Current total number of blocks
    static long		cTrackMaxNb;	// Maximum total number of blocks
#endif
#endif

    // Debug tools
#ifdef DEBUG
    static BlockState	CheckUserBlock(char* aUserBlock, bool aShowSuccess = true);
    static BlockState	CheckBlock(char* aBlock, bool aDumpBlock = true);
    static void		CheckAddress(char* aAddress);
    static void		CheckStack(bool aShowSuccess = true);
    static void		DumpStack(bool aDetailed = true, char* aStopPoint = NULL);
    static void		DumpUserBlock(char* aUserBlock, char *aPrompt = NULL, bool aContent = false);
    static void		DumpBlock(char* aBlock, char *aPrompt = NULL,
				  bool aCheckBlock = true, bool aContent = false);
    static void		SetUserFileInfo(char* aUserBlock, const char *aFileName, int aLineNb);
    static void		SetFileInfo(char* aUserBlock, const char *aFileName, int aLineNb);
#endif

    // Memory leaks detection tools
#ifdef DEBUG
    static void		StartDetectLeaks();
    static void		StopDetectLeaks();
#endif

    // Memory statictics collector tools
#ifdef DEBUG
    static void		StartTrackMax();
    static void		StopTrackMax();
#endif

    // Garbage collector tools
    static void		SetBlockNotPurgeable(char* aUserBlock);
    static void		SetBlockPurgeable(char* aUserBlock);
    static bool	IsBlockPurgeable(char* aUserBlock);
    static void		SetPurgeDefault(bool aPurgeDefault);
    static void 	PurgeStack();

#ifndef __MEMMGR_STUB__
private:
    // Multi threads management
#ifdef __MEMMGR_SOFT__
    friend void *MemoryMgrObj::operator new(size_t);
    friend void MemoryMgrObj::operator delete(void *);

    friend void *MemoryMgrObj::operator new[](size_t);
    friend void MemoryMgrObj::operator delete[](void *);
#else
    friend void *operator new(size_t) throw(std::bad_alloc);
    friend void operator delete(void *) throw();

    friend void *operator new[](size_t) throw(std::bad_alloc);
    friend void operator delete[](void *) throw();
#endif
    static int		ThreadLowLock()		{ return pthread_mutex_lock(&cLowLock); }
    static int		ThreadLowUnlock()	{ return pthread_mutex_unlock(&cLowLock); }
    static int		ThreadHighLock()	{ return pthread_mutex_lock(&cHighLock); }
    static int		ThreadHighUnlock()	{ return pthread_mutex_unlock(&cHighLock); }
#endif

};

#ifdef DEBUG
#define new NEW
#endif

#else

#include <cstdlib> // for NULL

// *** Empty macros
#define NEW new
#define MEM_OBJ
#define MEM_SET_NO_PURGE(p)
#define MEM_SET_PURGE(p)
#define MEM_IS_PURGE(p)
#define MEM_DFLT_PURGE(b)
#define MEM_PURGE_STACK
#define MEM_CHECK(p)
#define MEM_CHECK_ADDR(p)
#define MEM_CHECK_STACK
#define MEM_SHOW(p)
#define MEM_SHOW_CONTENT(p)
#define MEM_SHOW_STACK
#define MEM_SHOW_TOTAL
#define MEM_LEAK_START
#define MEM_LEAK_STOP
#define MEM_MAX_SIZE(s)
#define MEM_SET_INFO(p)
#define MEM_TRACK_START
#define MEM_TRACK_STOP

#endif	// __USE_MEMMGR__



//----------------------------------------------------------------------
//
//     Automatic deallocation tools
//
//----------------------------------------------------------------------

// Use AutoDel for simple object. Sample :
//   AutoDel<MyClass> aObject = new MyClass;

template <class PtrType> class AutoDel
{
public:
  AutoDel() : _p(NULL) {}
  AutoDel(PtrType *p) : _p(p) {}
  AutoDel(const AutoDel<PtrType>& p) : _p(p._p) {}
  virtual ~AutoDel() { if (_p) { delete _p; _p = NULL; }}
  PtrType* operator->() const { return _p; }
  PtrType& operator*() const { return *_p; }
  operator PtrType*() const { return _p; }
  PtrType *_p;
};


// Use AutoDelArr for arrays. Sample :
//   AutoDelArr<MyClass> aObject = new MyClass[8];

template <class PtrType> class AutoDelArr
{
public:
  AutoDelArr() : _p(NULL) {}
  AutoDelArr(PtrType *p) : _p(p) {}
  AutoDelArr(const AutoDelArr<PtrType>& p) : _p(p._p) {}
  virtual ~AutoDelArr() { if (_p) { delete [] _p; _p = NULL; }}
  PtrType* operator->() const { return _p; }
  PtrType& operator*() const { return *_p; }
  operator PtrType*() const { return _p; }
  PtrType *_p;
};

#include <list>

// Use AutoDelList for std::list. Sample :
//   AutoDelList<MyClass> aList = new std::list<MyClass>();

template <class PtrType> class AutoDelList
{
public:
  AutoDelList() : _p(NULL) {}
  AutoDelList(std::list<PtrType> *p) : _p(p) {}
  AutoDelList(const AutoDelList<PtrType>& p) : _p(p._p) {}
  virtual ~AutoDelList() { if (_p) { _p->clearAndDestroy(); delete _p; _p = NULL; }}
  std::list<PtrType>* operator->() const { return _p; }
  std::list<PtrType>& operator*() const { return *_p; }
  operator std::list<PtrType>*() const { return _p; }
  std::list<PtrType> *_p;
};

#endif	// MemoryMgr_h

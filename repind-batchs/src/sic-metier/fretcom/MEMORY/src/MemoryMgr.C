//f
//----------------------------------------------------------------------
//     FILE NAME   : MemoryMgr.C
//
//     DESCRIPTION : Tools to debug memory management.
//
//     AUTHOR      : dor
//     DATE        : 05/12/1997
//----------------------------------------------------------------------
//f


#ifdef __USE_MEMMGR__

#include <stdio.h>
#include <stdlib.h>
#include <exception.h>
#include <iostream>
#include "DebugTools.h"
#include "MemoryMgr.h"

#include "BasicException.h"

// *** Compilation options check
#if defined(__MEMMGR_STUB__) && defined(__MEMMGR_SOFT__)
#error Cannot define both SOFT and STUB
#endif


// *** Local definitions
#ifdef __MEMMGR_SOFT__
#define __DETECT_DELETE_NULL__		1
#else
#define __DETECT_DELETE_NULL__		0	// Disabled because of RogueWave (who does that very often...)
#endif

typedef unsigned long BlockMagic;

typedef struct
{
    caddr_t	prev;	// Pointer to previous block
    caddr_t	next;	// Pointer to next block
    BlockFlag	flags;	// Block flags
#ifdef DEBUG
    caddr_t	where;	// Line number and file name of allocation (if available)
    size_t	size;	// User block size
    BlockMagic	magic;	// Magic value to detect overwrite/state/etc
#endif
} MemBlockHeader;

#ifdef DEBUG
typedef struct
{
    BlockMagic	magic;	// Magic value to detect overwrite
} MemBlockTrailer;

#define TRAIL_ALIGN(size)	(sizeof(long) - ((((size) - 1) & (sizeof(long) - 1)) + 1))
#undef new
#endif

#define TO_MEMBLOCK(ub)		(((char *) (ub)) - sizeof(MemBlockHeader))
#define TO_USRBLOCK(mb)		(((char *) (mb)) + sizeof(MemBlockHeader))

// Flags
const BlockFlag kBlockFlag_Purgeable	= 0x0001;
const BlockFlag kBlockFlag_Object	= 0x0002;
const BlockFlag kBlockFlag_LeakPoint	= 0x0004;

// Magic values
#ifdef DEBUG
const BlockMagic kBlockMagic_Allocated	= 0xCCBABECC;
const BlockMagic kBlockMagic_Released	= 0x00DEAD00;
const char	 kMagicAlign		= 0xBB;
#endif

// Block states
#ifdef DEBUG
const BlockState kBlockState_OK		= 0;
const BlockState kBlockState_BadTrail	= 1;
const BlockState kBlockState_Bad	= 2;
#endif

// Class variables initializations
#ifndef __MEMMGR_STUB__
caddr_t   MemoryMgr::cStack		= NULL;
BlockFlag MemoryMgr::cDefaultFlags	= 0;
mutex_t   MemoryMgr::cLowLock		= DEFAULTMUTEX;
mutex_t   MemoryMgr::cHighLock		= DEFAULTMUTEX;
#ifdef DEBUG
size_t	  MemoryMgr::cMaxSize		= 0x00100000;	// 1 Mo
short	  MemoryMgr::cTrackMaxStat	= 0;
size_t	  MemoryMgr::cTrackCurSize	= 0L;
size_t	  MemoryMgr::cTrackMaxSize	= 0L;
long	  MemoryMgr::cTrackCurNb	= 0L;
long	  MemoryMgr::cTrackMaxNb	= 0L;
#endif
#endif


#ifndef __MEMMGR_STUB__

//f
//----------------------------------------------------------------------
//     METHOD      : operator new
//
//     DESCRIPTION : Memory allocation override.
//----------------------------------------------------------------------
//f

#ifdef __MEMMGR_SOFT__
void* MemoryMgrObj::operator new(size_t aUserSize)
#else
void *operator new(size_t aUserSize) throw(std::bad_alloc)
#endif
{
    register caddr_t aBlock = NULL;

#ifdef DEBUG
    if (aUserSize > MemoryMgr::cMaxSize)
	TRACEMSG_LVL("#MEMFAIL# : Ridiculous requested size of " << aUserSize, LevelTrace::System);
    size_t aAlignSize = TRAIL_ALIGN(aUserSize);
    size_t aRealSize = sizeof(MemBlockHeader) + aUserSize + aAlignSize + sizeof(MemBlockTrailer);
#else
    size_t aRealSize = sizeof(MemBlockHeader) + aUserSize;
#endif

#ifdef __MEMMGR_SOFT__
    aBlock = (caddr_t) ::operator new(aRealSize);
#else
    aBlock = (caddr_t) malloc(aRealSize);
#endif
    if (aBlock)
    {
	MemoryMgr::ThreadLowLock();

	MemBlockHeader *aBlockHeader = (MemBlockHeader *) aBlock;
	aBlockHeader->prev = NULL;
	aBlockHeader->next = MemoryMgr::cStack;
#ifdef __MEMMGR_SOFT__
	aBlockHeader->flags = MemoryMgr::cDefaultFlags | kBlockFlag_Object;
#else
	aBlockHeader->flags = MemoryMgr::cDefaultFlags;
#endif

#ifdef DEBUG
	aBlockHeader->where = NULL;
	aBlockHeader->size = aUserSize;
	aBlockHeader->magic = kBlockMagic_Allocated;

	// Statistics collector
	if (MemoryMgr::cTrackMaxStat)
	{
	    MemoryMgr::cTrackCurSize += aUserSize;
	    if (MemoryMgr::cTrackCurSize > MemoryMgr::cTrackMaxSize)
		MemoryMgr::cTrackMaxSize = MemoryMgr::cTrackCurSize;
	    MemoryMgr::cTrackCurNb++;
	    if (MemoryMgr::cTrackCurNb > MemoryMgr::cTrackMaxNb)
		MemoryMgr::cTrackMaxNb = MemoryMgr::cTrackCurNb;	    
	}
#endif

	if (MemoryMgr::cStack)
	    ((MemBlockHeader *) MemoryMgr::cStack)->prev = aBlock;
	MemoryMgr::cStack = aBlock;

	aBlock += sizeof(MemBlockHeader);	// So becomes user block

#ifdef DEBUG
	char *aBlockTrailer = ((char *) aBlock) + aUserSize;
	while (aAlignSize--)
	    *aBlockTrailer++ = kMagicAlign;
        ((MemBlockTrailer *) aBlockTrailer)->magic = kBlockMagic_Allocated;
#endif

	MemoryMgr::ThreadLowUnlock();
    }

    if (aBlock)
	return aBlock;
    throw Xalloc("memFullErr", aUserSize);

} // operator new


//
// CB 100103 : for compatibility with Forte 6.1
//
#ifdef __MEMMGR_SOFT__
void* MemoryMgrObj::operator new[](size_t aUserSize)
{
  return MemoryMgrObj::operator new(aUserSize);
}
#else
void *operator new[](size_t aUserSize) throw(std::bad_alloc)
{
  return operator new(aUserSize);
}
#endif


#endif


#ifdef DEBUG

//f
//----------------------------------------------------------------------
//     METHOD      : operator new
//
//     DESCRIPTION : Debug version to get where the blocks has been
//		     allocated.
//----------------------------------------------------------------------
//f

void* operator new(size_t aUserSize, const char *aFileName, int aLineNb)
{

#if !defined(__MEMMGR_STUB__) && !defined(__MEMMGR_SOFT__)
    if (*aFileName)
    {
	register caddr_t aUserBlock = (caddr_t) ::operator new(aUserSize);
	MemoryMgr::SetFileInfo(aUserBlock, aFileName, aLineNb);
	return aUserBlock;
    }
#endif

    return ::operator new(aUserSize);

} // operator new


void* operator new[](size_t aUserSize, const char *aFileName, int aLineNb)
{
  return operator new(aUserSize, aFileName, aLineNb);
}

#endif


#ifndef __MEMMGR_SOFT__

//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgrObj::operator new
//
//     DESCRIPTION : Object memory allocation override.
//----------------------------------------------------------------------
//f

void* MemoryMgrObj::operator new(size_t aUserSize)
{

#ifdef __MEMMGR_STUB__
    return ::operator new(aUserSize);
#else
    register caddr_t aUserBlock = (caddr_t) ::operator new(aUserSize);
    register MemBlockHeader *aBlockHeader = (MemBlockHeader *) TO_MEMBLOCK(aUserBlock);
    aBlockHeader->flags |= kBlockFlag_Object;
    return aUserBlock;
#endif

} // MemoryMgrObj::operator new

void* MemoryMgrObj::operator new[](size_t aUserSize)
{
    return MemoryMgrObj::operator new(aUserSize);
}

#endif


#ifdef DEBUG

//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgrObj::operator new
//
//     DESCRIPTION : Debug version to get where the blocks has been
//		     allocated.
//----------------------------------------------------------------------
//f

void* MemoryMgrObj::operator new(size_t aUserSize, const char *aFileName, int aLineNb)
{

#if __MEMMGR_SOFT__
    if (*aFileName)
    {
	register caddr_t aUserBlock = (caddr_t) operator new(aUserSize);
	MemoryMgr::SetFileInfo(aUserBlock, aFileName, aLineNb);
	return aUserBlock;
    }

    return operator new(aUserSize);
#else
    register caddr_t aUserBlock = (caddr_t) ::operator new(aUserSize, aFileName, aLineNb);

#ifndef __MEMMGR_STUB__
    register MemBlockHeader *aBlockHeader = (MemBlockHeader *) TO_MEMBLOCK(aUserBlock);
    aBlockHeader->flags |= kBlockFlag_Object;
#endif

    return aUserBlock;
#endif

} // MemoryMgrObj::operator new


void* MemoryMgrObj::operator new[](size_t aUserSize, const char *aFileName, int aLineNb)
{
  return MemoryMgrObj::operator new(aUserSize, aFileName, aLineNb);
}


#endif


#ifndef __MEMMGR_STUB__

//f
//----------------------------------------------------------------------
//     METHOD      : operator delete
//
//     DESCRIPTION : Memory deallocation override.
//----------------------------------------------------------------------
//f

#ifdef __MEMMGR_SOFT__
void MemoryMgrObj::operator delete(void *aUserBlock)
#else
void operator delete(void *aUserBlock) throw()
#endif
{

#if __DETECT_DELETE_NULL__ && defined(DEBUG)
    MemoryMgr::CheckUserBlock((caddr_t) aUserBlock, false);
#endif

    if (aUserBlock)	  // Mandatory to avoid core dump (RogueWave generates NULL pointer deallocation)
    {

#if !__DETECT_DELETE_NULL__ && defined(DEBUG)
	MemoryMgr::CheckUserBlock((caddr_t) aUserBlock, false);
#endif

	register caddr_t aBlock = (caddr_t) TO_MEMBLOCK(aUserBlock);
	MemBlockHeader *aBlockHeader = (MemBlockHeader *) aBlock;

#ifdef DEBUG
	if (aBlockHeader->magic == kBlockMagic_Allocated)
#endif
	{
	    MemoryMgr::ThreadLowLock();

	    if (aBlockHeader->prev)
	    {
		((MemBlockHeader *) aBlockHeader->prev)->next = aBlockHeader->next;
		if (aBlockHeader->next)
		    ((MemBlockHeader *) aBlockHeader->next)->prev = aBlockHeader->prev;
	    }
	    else
	    {
		ASSERT(aBlock == MemoryMgr::cStack);
		MemoryMgr::cStack = aBlockHeader->next;
		if (MemoryMgr::cStack)
		    ((MemBlockHeader *) MemoryMgr::cStack)->prev = NULL;
	    }

#ifdef DEBUG
	    if (aBlockHeader->where)
	    {
		free(aBlockHeader->where);
		aBlockHeader->where = NULL;
	    }
	    aBlockHeader->magic = kBlockMagic_Released;
	    size_t aAlignSize = TRAIL_ALIGN(aBlockHeader->size);
	    MemBlockTrailer *aBlockTrailer = (MemBlockTrailer *) (((char *) aUserBlock) + aBlockHeader->size + aAlignSize);
	    aBlockTrailer->magic = kBlockMagic_Released;

	    // Statistics collector
	    if (MemoryMgr::cTrackMaxStat)
	    {
		MemoryMgr::cTrackCurSize -= aBlockHeader->size;
		MemoryMgr::cTrackCurNb--;
	    }
#endif

	    MemoryMgr::ThreadLowUnlock();
#ifdef __MEMMGR_SOFT__
	    ::operator delete(aBlock);
#else
	    free(aBlock);
#endif
	}
    }

} // operator delete


#ifdef __MEMMGR_SOFT__
void MemoryMgrObj::operator delete[](void *aUserBlock)
{
  MemoryMgrObj::operator delete(aUserBlock);
}
#else
void operator delete[](void *aUserBlock) throw()
{
  operator delete(aUserBlock);
}
#endif


#endif


#ifndef __MEMMGR_SOFT__

//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgrObj::operator delete
//
//     DESCRIPTION : Provided just for an easy link purpose when
//		     switching from or to SOFT version.
//----------------------------------------------------------------------
//f

void MemoryMgrObj::operator delete(void *aUserBlock)
{
    ::operator delete(aUserBlock);

} // MemoryMgrObj::operator delete


void MemoryMgrObj::operator delete[](void *aUserBlock)
{
    MemoryMgrObj::operator delete(aUserBlock);

} // MemoryMgrObj::operator delete[]

#endif


#ifdef DEBUG

//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::CheckUserBlock
//
//     DESCRIPTION : Check if a user block is valid.
//----------------------------------------------------------------------
//f

BlockState MemoryMgr::CheckUserBlock(caddr_t aUserBlock, bool aShowSuccess)
{

#ifdef __MEMMGR_STUB__
    return kBlockState_OK;
#else
    if (aUserBlock)
    {
	BlockState aBlockState = CheckBlock((caddr_t) TO_MEMBLOCK(aUserBlock));
	if (aShowSuccess && (aBlockState == kBlockState_OK))
	    TRACEMSG_LVL("Block is OK", LevelTrace::System);
	return aBlockState;
    }
    TRACEMSG_LVL("#MEMFAIL# : NULL ptr", LevelTrace::System);
    return kBlockState_Bad;
#endif

} // CheckUserBlock


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::CheckBlock
//
//     DESCRIPTION : Check if a block is valid.
//----------------------------------------------------------------------
//f

BlockState MemoryMgr::CheckBlock(caddr_t aBlock, bool aDumpBlock)
{

#ifdef __MEMMGR_STUB__
    return kBlockState_OK;
#else
    register MemBlockHeader *aBlockHeader = (MemBlockHeader *) aBlock;
    caddr_t aUserBlock = (caddr_t) TO_USRBLOCK(aBlock);
    switch (aBlockHeader->magic)  // Verify header
    {
        case kBlockMagic_Allocated :
	    {
		size_t aAlignSize = TRAIL_ALIGN(aBlockHeader->size);
		char *aBlockTrailer = ((char *) aUserBlock) + aBlockHeader->size;
		while (aAlignSize)
		{
		    if (*aBlockTrailer++ != kMagicAlign)
			break;
		    aAlignSize--;
		}
		if (!aAlignSize && (((MemBlockTrailer *) aBlockTrailer)->magic == kBlockMagic_Allocated))
		    return kBlockState_OK;
		TRACEMSG_LVL("#MEMFAIL# : Write occured past the end of block !", LevelTrace::System);
		if (aDumpBlock)
		    DumpBlock(aBlock, (char*)"Block", false);
		return kBlockState_BadTrail;
	    }
	    break;

        case kBlockMagic_Released :
	    TRACEMSG_LVL("#MEMFAIL# : Freed block !", LevelTrace::System);
	    if (aDumpBlock)
		DumpBlock(aBlock, (char*)"Block", false);
	    break;

        default :
	    TRACEMSG_LVL("#MEMFAIL# : Invalid/corrupted block ! [" 
			 << (void *) aUserBlock << ']', LevelTrace::System);
    }

    return kBlockState_Bad;
#endif

} // CheckBlock


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::CheckAddress
//
//     DESCRIPTION : Check if an address is valid, that is, if it is
//                   in an allocated block. If yes then displays it.
//----------------------------------------------------------------------
//f

void MemoryMgr::CheckAddress(caddr_t aAddress)
{

#ifndef __MEMMGR_STUB__
    ThreadLowLock();

    register MemBlockHeader *aBlockHeader = (MemBlockHeader *) cStack;
    while (aBlockHeader && (CheckBlock((caddr_t) aBlockHeader) != kBlockState_Bad))
    {
	register char *aStart = TO_USRBLOCK(aBlockHeader);
	if ((aAddress >= aStart) && (aAddress <= (aStart + aBlockHeader->size)))
	{
	    DumpBlock((caddr_t) aBlockHeader, (char*)"Found in", false);
	    break;
	}
	aBlockHeader = (MemBlockHeader *) aBlockHeader->next;
    }
    if (!aBlockHeader)
	TRACEMSG_LVL("#MEMFAIL# : address [" << (void *) aAddress 
		     << "] not in allocated block", LevelTrace::System);

    ThreadLowUnlock();
#endif

} // CheckAddress


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::CheckStack
//
//     DESCRIPTION : Check if each block in the stack is valid.
//----------------------------------------------------------------------
//f

void MemoryMgr::CheckStack(bool aShowSuccess)
{

#ifndef __MEMMGR_STUB__
    ThreadLowLock();

    register MemBlockHeader *aBlockHeader = (MemBlockHeader *) cStack;
    while (aBlockHeader && (CheckBlock((caddr_t) aBlockHeader) != kBlockState_Bad))
	aBlockHeader = (MemBlockHeader *) aBlockHeader->next;
    if (aShowSuccess && !aBlockHeader)
	TRACEMSG_LVL("MEM_CHECK_STACK : OK", LevelTrace::System);

    ThreadLowUnlock();
#endif

} // CheckStack


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::DumpStack
//
//     DESCRIPTION : Display the block's stack.
//----------------------------------------------------------------------
//f

void MemoryMgr::DumpStack(bool aDetailed, caddr_t aStopPoint)
{

#ifndef __MEMMGR_STUB__
    if (aDetailed)
    {
	if (aStopPoint)
	    TRACEMSG_LVL("*** MEMORY LEAKS ***", LevelTrace::System);
	else
	    TRACEMSG_LVL("*** MEM STACK DUMP ***", LevelTrace::System);
    }

    ThreadLowLock();
    if (cStack)
    {
	register MemBlockHeader *aBlockHeader = (MemBlockHeader *) cStack;
	int i = 0;
	int aNbObj = 0;
	int aNbData = 0;
	size_t aTotalSize = 0;
	bool aCorrupted = false;
	do
	{
	    if (aDetailed)
	    {
		COUT( ++i << " : ", LevelTrace::System);
		DumpBlock((caddr_t) aBlockHeader, NULL, false);
	    }

	    if (aBlockHeader->flags & kBlockFlag_Object)
		aNbObj++;
	    else
		aNbData++;

	    aTotalSize += aBlockHeader->size;

	    // Check prev
	    if (aBlockHeader->prev && (((MemBlockHeader *) aBlockHeader->prev)->next != (caddr_t) aBlockHeader))
		aCorrupted = true;

	    // Check next
	    if (aBlockHeader->next && (((MemBlockHeader *) aBlockHeader->next)->prev != (caddr_t) aBlockHeader))
		aCorrupted = true;

	    // Check block
	    BlockState aBlockState = CheckBlock((caddr_t) aBlockHeader, !aDetailed);
	    if (aBlockState != kBlockState_OK)
	    {
		aCorrupted = true;
		if (aBlockState == kBlockState_Bad)
		    break;
	    }

	    aBlockHeader = (MemBlockHeader *) aBlockHeader->next;
	}
	while (aBlockHeader != ((MemBlockHeader *) aStopPoint));

	if (aStopPoint)
	    COUT( "Leak -> ", LevelTrace::System );
	else
	    COUT( "Stack -> ", LevelTrace::System );
	COUT( "NbObj=" << aNbObj << " NbData=" << aNbData, LevelTrace::System );
	COUT( " TotalSize=" << aTotalSize, LevelTrace::System );
	if (aCorrupted)
	    COUT( " #CORRUPTED#", LevelTrace::System );
	COUT( std::endl, LevelTrace::System );
    }
    else
	TRACEMSG_LVL("Stack : empty", LevelTrace::System);
    ThreadLowUnlock();
#endif

} // DumpStack

    
//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::DumpUserBlock
//
//     DESCRIPTION : Display user block's infos.
//----------------------------------------------------------------------
//f

void MemoryMgr::DumpUserBlock(caddr_t aUserBlock, char *aPrompt, bool aContent)
{

#ifndef __MEMMGR_STUB__
#ifdef DEBUG
    if (CheckUserBlock(aUserBlock, false) == kBlockState_OK)
#endif
	DumpBlock((caddr_t) TO_MEMBLOCK(aUserBlock), aPrompt, true, aContent);
#endif

} // DumpUserBlock


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::DumpBlock
//
//     DESCRIPTION : Display block's infos.
//----------------------------------------------------------------------
//f

void MemoryMgr::DumpBlock(caddr_t aBlock, char *aPrompt, bool aCheckBlock, bool aContent)
{

#ifndef __MEMMGR_STUB__
    register MemBlockHeader *aBlockHeader = (MemBlockHeader *) aBlock;

    // Prompt
    if (aPrompt)
	COUT( aPrompt << " : ", LevelTrace::System );

    // Block address
    COUT( '[' << (void *) TO_USRBLOCK(aBlock) << ']', LevelTrace::System );

    // Block type
    if (aBlockHeader->flags & kBlockFlag_Object)
	COUT( " Obj ", LevelTrace::System );
    else if (aBlockHeader->flags & kBlockFlag_LeakPoint)
	COUT( " LSP ", LevelTrace::System );
    else
	COUT( " Data", LevelTrace::System );

    // Block status
    if (aBlockHeader->flags & kBlockFlag_Purgeable)
	COUT( " Purge  ", LevelTrace::System );
    else
	COUT( " NoPurge", LevelTrace::System );

    // Block size
    COUT( " Size=" << aBlockHeader->size, LevelTrace::System );

    // File and line number of allocation
    if (aBlockHeader->where)
    {
	COUT( " (" << (((char *) aBlockHeader->where) + sizeof(long)), LevelTrace::System );
	COUT( " #" << *((long *) aBlockHeader->where) << ')', LevelTrace::System );
    }

    // Check prev
    if (aBlockHeader->prev && (((MemBlockHeader *) aBlockHeader->prev)->next != (caddr_t) aBlockHeader))
	COUT( " #BAD PREV#", LevelTrace::System );

    // Check next
    if (aBlockHeader->next && (((MemBlockHeader *) aBlockHeader->next)->prev != (caddr_t) aBlockHeader))
	COUT( " #BAD NEXT#", LevelTrace::System );

    COUT( std::endl, LevelTrace::System);

    // Check block
    bool aIsOK = aCheckBlock ? (CheckBlock((caddr_t) aBlockHeader) != kBlockState_Bad) : true;

    // Dump content
    if (aContent && aIsOK && aBlockHeader->size)
    {
	long aLineNb = ((aBlockHeader->size - 1) >> 4) + 1;
	long *aAddress = (long *) TO_USRBLOCK(aBlock);

	for (long aLine = 0L; (aLine < aLineNb); aLine++)
	{
	    // Dump address
	    char s[32];
	    sprintf(s, "%08X  ", (long) aAddress);
	    COUT(s, LevelTrace::System);

	    // Hex dump
	    long *l = aAddress;
	    for (int i = 0; (i < 4); i++)
	    {
		sprintf(s, "%08X ", *l++);
		COUT(s, LevelTrace::System);
	    }

	    // Ascii dump
	    char *c = (char *) aAddress;
	    for (int i = 0; (i < 16); i++, c++)
	    {
		if (*c >= 32)
		    COUT(*c, LevelTrace::System);
		else
		    COUT('.', LevelTrace::System);
	    }

	    COUT( std::endl, LevelTrace::System );
	    aAddress += 4;
	}
    }
#endif

} // DumpBlock


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::SetUserFileInfo
//
//     DESCRIPTION : Set the file name and line number of the
//                   given user block. Verify the block before.
//----------------------------------------------------------------------
//f

void MemoryMgr::SetUserFileInfo(caddr_t aUserBlock, const char *aFileName, int aLineNb)
{

#ifndef __MEMMGR_STUB__
#ifdef DEBUG
    if (CheckUserBlock(aUserBlock, false) == kBlockState_OK)
#endif
	SetFileInfo(aUserBlock, aFileName, aLineNb);
#endif

} // SetUserFileInfo


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::SetFileInfo
//
//     DESCRIPTION : Set the file name and line number of the
//                   given user block.
//----------------------------------------------------------------------
//f

void MemoryMgr::SetFileInfo(caddr_t aUserBlock, const char *aFileName, int aLineNb)
{

#ifndef __MEMMGR_STUB__
    ThreadLowLock();

    register MemBlockHeader *aBlockHeader = (MemBlockHeader *) TO_MEMBLOCK(aUserBlock);
    caddr_t aWhere = (caddr_t) malloc(sizeof(long) + strlen(aFileName) + 1);
    if (aWhere)
    {
	*((long *) aWhere) = aLineNb;
	strcpy(((char *) aWhere) + sizeof(long), aFileName);
	if (aBlockHeader->where)
	    free(aBlockHeader->where);
	aBlockHeader->where = aWhere;
    }

    ThreadLowUnlock();
#endif

} // SetFileInfo


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::StartDetectLeaks
//
//     DESCRIPTION : Put a start point in the stack to detect leaks.
//----------------------------------------------------------------------
//f

void MemoryMgr::StartDetectLeaks()
{

#ifndef __MEMMGR_STUB__
    TRACEMSG_LVL("MEM_LEAK_START", LevelTrace::System);
#ifdef __MEMMGR_SOFT__
    register caddr_t aUserBlock = (caddr_t) new MemoryMgrObj;
#else
    register caddr_t aUserBlock = (caddr_t) ::operator new(0);
#endif
    register MemBlockHeader *aBlockHeader = (MemBlockHeader *) TO_MEMBLOCK(aUserBlock);
    aBlockHeader->flags |= kBlockFlag_LeakPoint;
#endif

} // StartDetectLeaks


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::StopDetectLeaks
//
//     DESCRIPTION : Search for the last leaks start point and
//		     display all allocated blocks from it.
//----------------------------------------------------------------------
//f

void MemoryMgr::StopDetectLeaks()
{

#ifndef __MEMMGR_STUB__
    CheckStack(false);

    // Search the last LeakStartPoint pushed on the stack
    ThreadLowLock();
    caddr_t aLeakBlock = NULL;
    register MemBlockHeader *aBlockHeader = (MemBlockHeader *) cStack;
    while (aBlockHeader)
    {
	if (aBlockHeader->flags & kBlockFlag_LeakPoint)
	{
	    aLeakBlock = (caddr_t) aBlockHeader;
	    break;
	}
	aBlockHeader = (MemBlockHeader *) aBlockHeader->next;
    }
    ThreadLowUnlock();

    if (aLeakBlock)
    {
	if (aLeakBlock == cStack)
	    TRACEMSG_LVL("MEM_LEAK_STOP : no leak detected", LevelTrace::System);
	else
	    DumpStack(true, aLeakBlock);
#ifdef __MEMMGR_SOFT__
	delete (MemoryMgrObj *) TO_USRBLOCK(aLeakBlock);
#else
	::operator delete ((caddr_t) TO_USRBLOCK(aLeakBlock));
#endif
    }
    else
	TRACEMSG_LVL("MEM_LEAK_STOP : no start point found", LevelTrace::System);
#endif

} // StopDetectLeaks


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::StartTrackMax
//
//     DESCRIPTION : Enable tracking of max allocation size and block
//		     number. ###WARNING### : only one track can be
//		     enabled at a time.
//----------------------------------------------------------------------
//f

void MemoryMgr::StartTrackMax()
{

#ifndef __MEMMGR_STUB__
    if (cTrackMaxStat)
	TRACEMSG_LVL("MEM_TRACK_START : already enabled", LevelTrace::System);
    else
    {
	TRACEMSG_LVL("MEM_TRACK_START", LevelTrace::System);
	cTrackMaxStat = 1;
	cTrackCurSize = 0L;
	cTrackMaxSize = 0L;
	cTrackCurNb = 0L;
	cTrackMaxNb = 0;
    }
#endif

} // StartTrackMax


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::StopTrackMax
//
//     DESCRIPTION : Search for the last leaks start point and
//		     display all allocated blocks from it.
//----------------------------------------------------------------------
//f

void MemoryMgr::StopTrackMax()
{

#ifndef __MEMMGR_STUB__
    if (cTrackMaxStat)
    {
	TRACEMSG_LVL("MEM_TRACK_STOP : maxBlock=" << cTrackMaxNb 
		     << " maxSize=" << cTrackMaxSize, LevelTrace::System);
	cTrackMaxStat = 0;
    }
    else
	TRACEMSG_LVL("MEM_TRACK_STOP : mem track not enabled", LevelTrace::System);
#endif

} // StopTrackMax

#endif	// DEBUG


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::SetBlockNotPurgeable
//
//     DESCRIPTION : Set a block so it will *not* be deleted by
//		     a PurgeStack() call.
//----------------------------------------------------------------------
//f

void MemoryMgr::SetBlockNotPurgeable(caddr_t aUserBlock)
{

#ifndef __MEMMGR_STUB__
#ifdef DEBUG
    if (CheckUserBlock(aUserBlock, false) == kBlockState_OK)
#endif
    {
	register MemBlockHeader *aBlockHeader = (MemBlockHeader *) TO_MEMBLOCK(aUserBlock);
	aBlockHeader->flags &= ~kBlockFlag_Purgeable;
    }
#endif

} // SetBlockNotPurgeable


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::SetBlockPurgeable
//
//     DESCRIPTION : Set a block so it will be deleted by the next
//		     PurgeStack() call (set by default).
//----------------------------------------------------------------------
//f

void MemoryMgr::SetBlockPurgeable(caddr_t aUserBlock)
{

#ifndef __MEMMGR_STUB__
#ifdef DEBUG
    if (CheckUserBlock(aUserBlock, false) == kBlockState_OK)
#endif
    {
	register MemBlockHeader *aBlockHeader = (MemBlockHeader *) TO_MEMBLOCK(aUserBlock);
	aBlockHeader->flags |= kBlockFlag_Purgeable;
    }
#endif

} // SetBlockPurgeable


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::IsBlockPurgeable
//
//     DESCRIPTION : Check if a block will be deleted by the next
//		     PurgeStack() call.
//----------------------------------------------------------------------
//f

bool MemoryMgr::IsBlockPurgeable(caddr_t aUserBlock)
{

#ifdef __MEMMGR_STUB__
    return false;
#else
#ifdef DEBUG
    if (CheckUserBlock(aUserBlock, false) == kBlockState_OK)
#endif
    {
	register MemBlockHeader *aBlockHeader = (MemBlockHeader *) TO_MEMBLOCK(aUserBlock);
	return (aBlockHeader->flags & kBlockFlag_Purgeable) ? true : false;
    }
#endif

} // IsBlockPurgeable


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::SetPurgeDefault
//
//     DESCRIPTION : Set the default purge state of blocks when
//		     they are created.
//		     Pass true to make them purgeables by default.
//----------------------------------------------------------------------
//f

void MemoryMgr::SetPurgeDefault(bool aPurgeDefault)
{

#ifndef __MEMMGR_STUB__
    cDefaultFlags = aPurgeDefault ? kBlockFlag_Purgeable : 0;
#endif

} // SetPurgeDefault


//f
//----------------------------------------------------------------------
//     METHOD      : MemoryMgr::PurgeStack
//
//     DESCRIPTION : Delete all purgeable blocks in the stack.
//----------------------------------------------------------------------
//f

void MemoryMgr::PurgeStack()
{

#ifndef __MEMMGR_STUB__
    TRACEMSG_LVL("MEM_PURGE_STACK", LevelTrace::System);

#ifdef DEBUG
    CheckStack(false);
    int aNbObj = 0;
    int aNbData = 0;
    size_t aTotalSize = 0;
#endif

    ThreadHighLock();
    register MemBlockHeader *aBlockHeader = (MemBlockHeader *) cStack;
    while (aBlockHeader)
    {
	caddr_t aNext = aBlockHeader->next;
	if (aBlockHeader->flags & kBlockFlag_Purgeable)
	{
#ifdef DEBUG
	    if (aBlockHeader->flags & kBlockFlag_Object)
		aNbObj++;
	    else
		aNbData++;
	    aTotalSize += aBlockHeader->size;
#endif
	    delete (caddr_t) TO_USRBLOCK(aBlockHeader);
	}
	aBlockHeader = (MemBlockHeader *) aNext;
    }
    ThreadHighUnlock();

#ifdef DEBUG
    if (aTotalSize)
    {
	COUT("PURGED MEMORY -> NbObj=" << aNbObj << " NbData=" << aNbData, LevelTrace::System);
	COUT(" TotalSize=" << aTotalSize << std::endl, LevelTrace::System);
    }
#endif
#endif

} // PurgeStack

#endif	// __USE_MEMMGR__

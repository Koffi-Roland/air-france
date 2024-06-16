//f
//----------------------------------------------------------------------
//     FILE NAME   : DefinesTools.h
//
//     DESCRIPTION : Useful general definitions.
//
//     DATE        : 10/09/1997
//----------------------------------------------------------------------
//f

#ifndef DefinesTools_h 
#define DefinesTools_h    1

// pel #include <rw/defs.h>
#include "DebugTools.h"
#include "MemoryMgr.h"


#ifndef _MIN_AND_MAX_
#define _MIN_AND_MAX_
#define MAX(a,b) ((a) > (b) ? (a) : (b))
#define MIN(a,b) ((a) < (b) ? (a) : (b))
#define MAX3(a,b,c) ( MAX ( (a) , MAX ( (b) , (c) ) ) )
#define MIN3(a,b,c) ( MIN ( (a) , MIN ( (b) , (c) ) ) )
#define MIDDLE(a,b,c) (MAX3 (MIN((a),(b)) , MIN((a),(c)) , MIN((b),(c))))
#endif


// Shortcuts for unsigned types
#ifndef _UNSIGNED_TYPES_
#define _UNSIGNED_TYPES_
typedef unsigned char		uchar;
typedef unsigned short		ushort;
typedef unsigned long		ulong;
typedef unsigned long long	ulonglong;
#endif


// ABS mapped to inline C++ functions
#ifndef _ABS_
#define _ABS_
inline double                _abs(double        a) {return a>0.0? a : -a;}
inline int                   _abs(int           a) {return a>0? a : -a;}
inline unsigned              _abs(unsigned      a) {return a;}
inline unsigned long         _abs(unsigned long a) {return a;}
#define ABS(a)              _abs(a)
#endif


// Inline to check if two doubles are nearly equal
const double __Equal_Float_Threshold__ = 0.01;
inline bool equalFloat(double a, double b)      {return _abs(a - b) <= __Equal_Float_Threshold__;}
inline bool upperFloat(double a, double b)      {return (a - b)     >  __Equal_Float_Threshold__;}
inline bool lowerFloat(double a, double b)      {return (b - a)     >  __Equal_Float_Threshold__;}
inline bool upperOrEqualFloat(double a , double b) {return ( upperFloat(a,b) || equalFloat(a, b) );}
inline bool lowerOrEqualFloat(double a , double b) {return ( lowerFloat(a,b) || equalFloat(a, b) );}

inline bool equalFloat(double a, double b, double t)      {return _abs(a - b) <= t;}
inline bool upperFloat(double a, double b, double t)      {return (a - b)     >  t;}
inline bool lowerFloat(double a, double b, double t)      {return (b - a)     >  t;}
inline bool upperOrEqualFloat(double a , double b, double t) {return ( upperFloat(a,b,t) || equalFloat(a,b,t) );}
inline bool lowerOrEqualFloat(double a , double b, double t) {return ( lowerFloat(a,b,t) || equalFloat(a,b,t) );}



// round to integral lower or greater value 
// example :
// 15/10 = 2
// 16/10 = 2
// 14/10 = 1
template<class T>  T CEIL( T dim, T step ) {
  return ceil( dim / step ) + (( (dim % step) >= (step/2)) ? 1 : 0);
}

// concatenation de 2 listes
template<class LIST>  void 
ConcatList( LIST & l1,  LIST  l2 ) {
  while ( l2.entries() ) l1.insert( l2.get() );
}


// transformation d'1 vecteur (ordonne ou pas) en liste
// fucking template - Template types must be in parameters
template<class LIST, class VECTOR> void 
VectorToList( const VECTOR& aVector, LIST& aList ) {
  for(int i=0;i<aVector.length();i++) aList.insert(aVector[i]);
}

// transformation d'1 vecteur (ordonne ou pas) en liste
// fucking template - Template types must be in parameters
template<class LIST, class VECTOR> void 
ListToVector( LIST aList, VECTOR& aVector ) {
  if ( aList.isEmpty() ) return;
  int i = 0;
  while ( aList.entries() )
    aVector[i++] = aList.get();    
}



#endif

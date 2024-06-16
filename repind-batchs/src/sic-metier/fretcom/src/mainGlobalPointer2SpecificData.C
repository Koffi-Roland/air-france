//----------------------------------------------------------------------
//
// Pour voir la difference de comportement entre la version MT Safe et 
// la version avec pointeur global, decommenter ou commenter cette ligne
//
// ------------>>>>  #define  _MT_SPECIFIC_DATA_
//
//----------------------------------------------------------------------

#include <stdlib.h>
#include <iostream>
#include <fstream>

struct A {
  int i;
};

#define  _MT_SPECIFIC_DATA_

#ifdef _MT_SPECIFIC_DATA_
#include "SpecificData.h"

struct SD {
  // global variables
  A*  globalPointer;
};

MT_Static_Specific_Data<SD> aSD;
template<> pthread_key_t  MT_Static_Specific_Data<SD>::Key = PTHREAD_ONCE_INIT;
template<> pthread_once_t MT_Static_Specific_Data<SD>::KeyOnce = PTHREAD_ONCE_INIT;

#define globalPointer      (aSD->globalPointer)

#else
// for test with global variable
#define _REENTRANT
#include <pthread.h>
//#include <thread.h>
A* globalPointer;
#endif 


#include <unistd.h>


void* MT_fct(void* arg) {
  static int i = 0;
  i++; 
  int j = i;
  globalPointer->i = 10 * j;
  std::cout << "Avant sleep g->i : " <<  globalPointer->i << "  for j = " << j << std::endl;
  sleep( j%2 ? 10 - j : 0);
  std::cout << "Apres sleep g->i : " <<  globalPointer->i << "  for j = " << j << std::endl;
  return 0;
}



#define NB_THREAD 10

/********************************************************************
*    Fork management test.
********************************************************************/
int main(int argc, char *argv[])
{
  globalPointer = new A;
  globalPointer->i = -1;

  std::cout << "globalPointer->i : " << globalPointer->i << std::endl;

  pthread_t tid[NB_THREAD];
  for (int i = 0;i < NB_THREAD;i++) {    
    int ret = pthread_create(&tid[i],NULL, MT_fct, 0);
    
std::cout << "Creation de la thread : " << tid[i] << " - ret[" << ret << "]" << std::endl;
  }

  while (pthread_join(0,0) == 0);
  
  std::cout << "END" << std::endl;
  std::cout << "globalPointer->i : " << globalPointer->i << std::endl;
  
  return 0;
} // main


/*
// .h

#ifdef _THREAD_CONSTRAINT_
#include "SpecificData.h"

struct Constraint_SD {
  // global variables
  MetaConstraint*  metaConstraint;
  ...
};

extern MT_Static_Specific_Data<Constraint_SD> aSD;

#define metaConstraint      (aSD->metaConstraint)
#define palletCurrent       (aSD->palletCurrent)
#define parcelCurrent       (aSD->parcelCurrent)
#define reservationCurrent  (aSD->reservationCurrent)

#else
MetaConstraint* metaConstraint;
#endif 

// .C

// Specific Data for Classes Constraint(s)
 MT_Static_Specific_Data<Constraint_SD> aSD;
// pthread_key_t  MT_Static_Specific_Data<Constraint_SD>::Key;
// pthread_once_t MT_Static_Specific_Data<Constraint_SD>::KeyOnce = PTHREAD_ONCE_INIT;

*/

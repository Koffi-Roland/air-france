#include <stdlib.h>
#include <iostream>
#include <fstream>

#include "SpecificData.h"


struct SD {
  int i;
  SD& operator=(const SD& aSD) {
    i = aSD.i;
    return *this;
  }
};

// for testing with SD Object
template<> pthread_key_t  MT_Static_Specific_Data<SD>::Key = PTHREAD_ONCE_INIT;
template<> pthread_once_t MT_Static_Specific_Data<SD>::KeyOnce = PTHREAD_ONCE_INIT;

struct BIDON {
  MT_Specific_Data<SD> aSD;
  MT_Static_Specific_Data<SD> aSSD;
};

struct MT_Solution {
  BIDON* b1;
  BIDON* b2;
};


#include <unistd.h>


void* MT_searchSolution(void* arg) {
  static int i = 0;
  MT_Solution* aStruct = (MT_Solution*) arg;
  i++; 
  int j = i;
  std::cout << "MT_searchSolution ++++++++++++++++++++++++++++++++ i : " << j << std::endl;
  aStruct->b1->aSD->i = 10 * j;
  aStruct->b1->aSSD->i = 10 * j;
  aStruct->b2->aSD->i = 20 * j;
  aStruct->b2->aSSD->i = 20 * j;
  std::cout << "Avant sleep b1->aSD->i : " <<  aStruct->b1->aSD->i << "  for j = " << j << std::endl;
  std::cout << "Avant sleep b1->aSSD->i : " <<  aStruct->b1->aSSD->i << "  for j = " << j << std::endl;
  std::cout << "Avant sleep b2->aSD->i : " <<  aStruct->b2->aSD->i << "  for j = " << j << std::endl;
  std::cout << "Avant sleep b2->aSSD->i : " <<  aStruct->b2->aSSD->i << "  for j = " << j << std::endl;
  sleep( j%2 ? 10 - j : 0);
  std::cout << "Apres sleep b1->aSD->i : " <<  aStruct->b1->aSD->i << "  for j = " << j << std::endl;
  std::cout << "Apres sleep b1->aSSD->i : " <<  aStruct->b1->aSSD->i << "  for j = " << j << std::endl;
  std::cout << "Apres sleep b2->aSD->i : " <<  aStruct->b2->aSD->i << "  for j = " << j << std::endl;
  std::cout << "Apres sleep b2->aSSD->i : " <<  aStruct->b2->aSSD->i << "  for j = " << j << std::endl;
  return 0;
}



#define NB_T 5

/********************************************************************
*    Fork management test.
********************************************************************/
int main(int argc, char *argv[])
{
  MT_Specific_Data<SD> aSD;
  aSD->i = 111;
  std::cout << "aSD->i : " <<  aSD->i << std::endl;

  MT_Static_Specific_Data<SD> aSSD;
  aSSD->i = 222;
  std::cout << "aSSD->i : " <<  aSSD->i << std::endl;


  BIDON b1;
  b1.aSD->i = 1;
  b1.aSSD->i = 1;
  BIDON b2;
  b2.aSD->i = 2;
  b2.aSSD->i = 2;

  std::cout << "b1.aSD->i : " << b1.aSD->i << std::endl;
  std::cout << "b1.aSSD->i : " << b1.aSSD->i << std::endl;
  std::cout << "b2.aSD->i : " << b2.aSD->i << std::endl;
  std::cout << "b2.aSSD->i : " << b2.aSSD->i << std::endl;

  pthread_t tid[NB_T];
  for (int i = 0;i < NB_T;i++) {    
    MT_Solution aStruct;
    aStruct.b1 = &b1;
    aStruct.b2 = &b2;
    //int ret = th_create(NULL,0,MT_searchSolution,(void*) &aStruct,0,&tid[i]);
  int ret = pthread_create(&tid[i],NULL, MT_searchSolution,(void*) &aStruct);
    std::cout << "Creation de la thread : " << tid[i] << " - ret[" << ret << "]" << std::endl;
  }

  //while (thr_join(NULL, NULL, NULL) == 0);
  while (pthread_join(0,0) == 0);
  
  std::cout << "END" << std::endl;
  std::cout << "b1.aSD->i : " << b1.aSD->i << std::endl;
  std::cout << "b1.aSSD->i : " << b1.aSSD->i << std::endl;
  std::cout << "b2.aSD->i : " << b2.aSD->i << std::endl;
  std::cout << "b2.aSSD->i : " << b2.aSSD->i << std::endl;
  
  return 0;
} // main

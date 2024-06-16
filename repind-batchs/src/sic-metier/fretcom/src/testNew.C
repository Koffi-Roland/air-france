
#include <rw/mempool.h>   // MUST be included before MemoryMgr.h ('new' macro)
#include <unistd.h>
#include <thread.h>

#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <new.h>
#include <exception.h>
#include <iostream>

#include "testNew.h"

#include "BasicException.h"
#include "DebugTools.h"

void *operator new(size_t aUserSize) throw(std::bad_alloc)
{
  //  std::cout << "New : " <<  aUserSize << std::endl;
  printf( "New : %d \n", aUserSize);

  return malloc( aUserSize );
}

main()
{
    int *db = 0;
    try {
      VERIFY_PTR(db);
    }
    catch (NullPointerException& e) {
      std::cout <<  e.why() << std::endl;
      exit(99);
    } 
 


    base *pB1;
    base *pB2;
    base *pB3;
    base *pB4;
    pool mem;

    pB1 = new( &mem ) base_one( one );
    pB2 = new( &mem ) base_two( two );
    pB3 = new( &mem ) base_three( three );
    pB4 = new( &mem ) base_four( four );

    pB1->pr();
    pB2->pr();
    pB3->pr();
    pB4->pr();

    int* i = new int();

}

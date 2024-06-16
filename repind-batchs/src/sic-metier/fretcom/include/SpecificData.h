//--------------------------------------------------------------------------
//
// Author : Christophe BOBAN
//
//--------------------------------------------------------------------------
//
// Classes permettant de transformer des donnees statiques en Data Specific
// pour multithreader le process.
// 2 classes sont dispos :
//
// MT_Specific_Data<T> : 
//----------------------
// Chaque instance a sa propre donnee et dans un thread cette donnee est
// dupliquee pour chaque instance
//
//
// MT_Static_Specific_Data<T> :
//----------------------------- 
// Toutes les instances ont une donnee (attribut) statique specifique au Thread
// Cette donnee est dupliquee pour chaque Thread.
// --> Variable statique pour chaque Thread
//
//--------------------------------------------------------------------------
//
// Historique :
//-------------
//
// La classe MT_Specific_Data<T> a ete developpe dans un 1er temps, pour 
// redefinir des variables globales.
// Mais elle ne convenait pas aux attributs statiques de classe.
// La classe MT_Static_Specific_Data<T> sert donc a definir des attributs
// statiques de classe.
//
//--------------------------------------------------------------------------
//
// A faire lors de l'utilisation de la classe MT_Static_Specific_Data<T>
// --> Initialiser les variables statiques suivantes dans le .C 
//
//  pthread_key_t  MT_Static_Specific_Data<T>::Key;
//  pthread_once_t MT_Static_Specific_Data<T>::KeyOnce = PTHREAD_ONCE_INIT;
//
//--------------------------------------------------------------------------
//

#ifndef _Specific_Data_
#define _Specific_Data_

//#define _REENTRANT
#include <pthread.h>


//--------------------------------------------------------------------------
//
// MultiThread STATIC Specific Data - Obsolete 
//---------------------------------
//                 ---> Replacing by Template Class MT_Static_Specific_Data
//
//
// This is a MACRO because Functions Create and Delete must be Global
//
// all instances have one static data which is duplicate in each Thread
//
//--------------------------------------------------------------------------

#define MT_STATIC_SPECIFIC_DATA(type) \
  static void MTS_MakeKey_##type(); \
  static void MTS_FreeSpecificData_##type(void*); \
  extern pthread_once_t KeyOnce_##type; \
  extern pthread_key_t  Key_##type; \
\
struct MT_Static_Specific_Data_##type { \
\
  virtual ~MT_Static_Specific_Data_##type() {} \
  MT_Static_Specific_Data_##type() {} \
  MT_Static_Specific_Data_##type(const MT_Static_Specific_Data_##type& aData) {} \
  MT_Static_Specific_Data_##type(type* aPtrData) {} \
  type* operator->() const { return MTS_GetSpecificData(); } \
  type& operator*()  const { return *MTS_GetSpecificData(); } \
  operator type*()   const { return MTS_GetSpecificData(); } \
\
  static type* MTS_GetSpecificData(void) { \
    type* aSpecificData = NULL; \
    pthread_once(&KeyOnce_##type, MTS_MakeKey_##type); \
\
    if ((aSpecificData = (type*) pthread_getspecific(Key_##type)) == NULL) \
      { \
	aSpecificData = new type; \
	pthread_setspecific(Key_##type, aSpecificData); \
      } \
    return aSpecificData; \
  } \
}; \
\
 static void MTS_MakeKey_##type() { \
    pthread_key_create(&Key_##type, MTS_FreeSpecificData_##type); \
  } \
\
void MTS_FreeSpecificData_##type(void* aStruct) \
{ \
  type* aPtr = (type*) aStruct; \
  if (aPtr) { \
    delete aPtr; \
    aPtr = NULL; \
  } \
} \


// Now available Template to replace Macro
//--------------------------------------------------------------------------
//
// MultiThread STATIC Specific Data
//---------------------------------
//
// All instances have one static data which is duplicate in each Thread
//
//--------------------------------------------------------------------------

template <class T>
struct MT_Static_Specific_Data {

  static pthread_once_t KeyOnce;
  static pthread_key_t  Key;
  static void MTS_MakeKey() { 
    std::cout << " MTS_MakeKey" << std::endl;
    pthread_key_create(&Key, MTS_FreeSpecificData);
  }
  static void MTS_FreeSpecificData(void* aStruct)
  {
    std::cout << " MTS_FreeSpecificData" << std::endl;
    T* aPtr = (T*) aStruct;
    if (aPtr) {
      delete aPtr;
      aPtr = NULL;
    }
  }
  virtual ~MT_Static_Specific_Data() {}
  MT_Static_Specific_Data() {}
  MT_Static_Specific_Data(const MT_Static_Specific_Data<T>& aData) {}
  MT_Static_Specific_Data(T* aPtrData) {}
  T* operator->() const { return MTS_GetSpecificData(); } 
  T& operator*()  const { return *MTS_GetSpecificData(); }
  operator T*()   const { return MTS_GetSpecificData(); } 


  static T* MTS_GetSpecificData(void) {
    T* aSpecificData = NULL; 
    pthread_once(&KeyOnce, &MTS_MakeKey);
    if ((aSpecificData = (T*) pthread_getspecific(Key)) == NULL)
      { 
	aSpecificData = new T;
	pthread_setspecific(Key, aSpecificData);
      }
    return aSpecificData;
  }
}; 


//--------------------------------------------------------------------------
//
// MultiThread NON STATIC Specific Data
//-------------------------------------
//
// Each instance has his proper data and in a thread this data his duplicate
// for each instance 
//
//--------------------------------------------------------------------------

template <class T>
struct MT_Specific_Data { 
  pthread_key_t  aKey;

  virtual ~MT_Specific_Data() {
    T* aSpecificData = MT_GetSpecificData();
    if ( aSpecificData ) delete aSpecificData;
  }
  MT_Specific_Data() { createKey(); }
  MT_Specific_Data(const MT_Specific_Data<T>& aData) { createKey(); }
  MT_Specific_Data(T* aPtrData) { 
    createKey(); 
    pthread_setspecific(aKey, aPtrData);
  } 
  T* operator->() const { return MT_GetSpecificData(); }
  T& operator*()  const { return *MT_GetSpecificData(); }
  operator T*()   const { return MT_GetSpecificData(); } 
  MT_Specific_Data<T>& operator=(const MT_Specific_Data<T>& aData) {
    MT_GetSpecificData()->operator=( *aData.MT_GetSpecificData() );
    //MTS_GetSpecificData()->operator=( *aData.MTS_GetSpecificData() );
    return *this;
  }
  void createKey() { pthread_key_create(&aKey, NULL); }
  T* MT_GetSpecificData(void) const {
    T* aSpecificData = NULL;
    if ((aSpecificData = (T*) pthread_getspecific(aKey)) == NULL) { 
      aSpecificData = new T;
      pthread_setspecific(aKey, aSpecificData);
    }
    return aSpecificData;
  }
}; 




#endif

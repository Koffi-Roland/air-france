// ---------------------------------------------------------------------- C++ -
// File : array.h
// Author : Christophe Boban
// ----------------------------------------------------------------------------


//  - 1 - Macro
// ----------------------------------------------------------------------------
// 
// Definition of 1-dimensional array of any pointer type with sort (qsort)
//
// ----------------------------------------------------------------------------
//

//  - 2 - Template
// ----------------------------------------------------------------------------
//
// Definition of 4-dimensional arrays of pointers of Any Type 
//
// ----------------------------------------------------------------------------

//
// Remarque 1 :   Pour des raisons de simplicite de compilation , ce fichier (.h) 
// ------------   contient les traitements
//
//
// Remarque 2 :   Les classes crees sont des Handles sur leur classe 
// ------------   d'implementation
//
//
// Remarque 3 :   Les methodes de destruction (destructeurs explicites)
// ------------   "remove()" et "removeAndDestroy()" sont a utiliser avec
//                beaucoup de precaution
//                ----------------------
//                car apres une des 2 methodes l'instance de l'objet est 
//                vide --> il ne reste que le Handle
//
//             ---> pour eviter quelques surprises, utiliser la methode 
//                 getImpl() pour etre sur que l'implementation de la 
//                 classe existe bien 
//
//        remove : raz du tableau de pointeurs sur Type
//
//        removeAndDestroy : raz du tableau de pointeurs sur Type et 
//                           des instances Type pointees --> attention
//
//
//---------------------------------------------------------------------



//////////////////////////////////////////////////////////////////
//
//   - 1 - Macro
//
//  Permet de gerer des tableaux a 1 Dimension que l'on peut 
//  trier sur un attribut quelconque du type d'objet stocke
//  (dans ordre croissant ou decroissant)
//
//  On peut changer l'ordre de tri en cours de vie du tableau
//  ce qui n'est pas le cas avec les classes RW
//
//  Ceci existe dans les classes RW mais on fixe a l'instanciation
//  la fonction de tri.
//
//////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////
//
//   - 2 - Template 
//
//  Permet de gerer des tableaux ayant jusqu'a 4 dimensions
// 
//
//////////////////////////////////////////////////////////////////



/////////////////////////////////////////////////////////////////
//
// Mode d'emploi de la macro
// -------------------------
//
// 1/ Declaration de la classe de type Array(Type) dans le .h
//
//    par   ANYARRAY_DECLARE(classe ou Type);
//
//  ---->>>>  ceci genere la classe TypeArray 
//
//
// 2/ Implementation de la classe de type Array(Type) dans le .cc
//
//    par   ANYARRAY_IMPLEMENT(classe ou Type);
//
//
// 3/ si on a besoin de trier le tableau , il faut definir la
//    fonction de Tri par
//
//
//   static int compare(const Type* p1, const Type* p2)
//   {
//      return p2->getVolume() - p1->getVolume();
//   }
//
//  que l'on appelle par l'instance de l'objet cree 
//
//  
//  exemple :
//  ---------
//  PalletArray array(10);
// 
//  creation des palettes ...
//
//  array.sort(compare);
//
//
//  Nota :
//  ------
//
//  l'interet de cette macro est que l'on peut definir plusieurs
//  fonctions de Tri sur le meme objet genere , il suffit dans ce
//  cas d'appeler la fonction de Tri desiree
//
/////////////////////////////////////////////////////////////////

#ifndef _ARRAY_H
#define _ARRAY_H

#include <string.h>
#include <iostream>
#include <assert.h>
#include <generic.h>
#include <stdarg.h>
#include <stdlib.h>

#include "MemoryMgr.h"

// #define MEM_OBJ

typedef void* Any;

#ifndef _BOOLEEN_
#define _BOOLEEN_
enum Booleen { False=0,True=1 };
#endif

const int _DEFAULT_CAPACITY_ARRAY_ = 30;
const int IBOB_NULL = -1;

//
//  Macros to help to define arrays of objects of any kind
//
class AnyArray;
class AnyArrayI;


#define ANYARRAY_DECLARE(type) \
typedef int (*name2(type,ComparisonFunction))(const type* i, const type* j); \
class name2(type,Array) : public AnyArray { \
public: \
    static name2(type,ComparisonFunction) _fct; \
public: \
    name2(type,Array)():AnyArray(0) {} \
\
    name2(type,Array)(int size,const type** values) \
       : AnyArray(size, (Any*)values) {} \
\
    name2(type,Array)(int size):AnyArray(size) {} \
\
    name2(type,Array)(const name2(type,Array)& array) \
     : AnyArray(new AnyArrayI(array.getSize(),(Any*)array.getArray())) {} \
\
    name2(type,Array)(int size, type* value, \
		      type* value1 ...) \
       : AnyArray(size) { \
      (*this)[0] = value; \
      (*this)[1] = value1; \
      va_list ap; \
      va_start(ap, value1); \
      for (int i=2; i<size; i++) { \
	(*this)[i] = va_arg(ap, type*); \
      } \
      va_end(ap); \
    } \
\
    ~name2(type,Array)() {} \
\
    void operator=(const name2(type,Array) & array) { \
	AnyArray::~AnyArray(); \
        _impl = new AnyArrayI(array.getSize(),(Any*)array.getArray()); \
    } \
\
    type*& operator[](int i) const { \
        return (type*&)_impl->operator[](i); \
    } \
\
    type* first() const { \
	return (type*) _impl->first(); \
    } \
\
    type* last() const { \
	return (type*) _impl->last(); \
    } \
\
    void insert(type* value) { \
	_impl->insert(value); \
    } \
\
    type* find(type* value) { \
	return (type*)_impl->find(value); \
    } \
\
    void removeAndDestroy(); \
\
   void removeAndDestroy(type* value); \
\
   type* removeAt(int index) { \
     return (type*) _impl->removeAt(index); \
   } \
\
   type* remove(type* value) { \
     return (type*) _impl->remove(value); \
   } \
\
   type* find(int (*testFun) ( type* t, void* v), void* value) const { \
     int len = length(); \
     const type* tmp = NULL; \
     const type** values = getArray(); \
     for (int i=0;i<len;i++) { \
	if ( testFun( (type*) values[i] , value ) ) { \
           tmp = values[i]; \
	   break; \
	} \
      } \
      return ( type* ) tmp; \
    } \
    const type& name2(get,type)(int i) const { \
	return *(type*)_impl->getAnyElem(i); \
    } \
    const type** getArray() const { \
        return (const type**)AnyArray::getArray(); \
    } \
    void sort(name2(type,ComparisonFunction) f); \
\
}; 

#define ANYARRAY_IMPLEMENT(type) \
name2(type,ComparisonFunction) name2(type,Array)::_fct = 0; \
extern "C" int name2(type,InternalComparisonFunction)(const void* i, const void* j) \
{ \
    name2(type,ComparisonFunction) f = name2(type, Array)::_fct; \
    return f(*(const type**)i, *(const type**)j); \
} \
void name2(type,Array)::sort(name2(type,ComparisonFunction) f) \
{ \
    _fct = f; \
    qsort(getArray(), getSize(), sizeof(type*), \
	  &name2(type,InternalComparisonFunction)); \
} \
void name2(type,Array)::removeAndDestroy(type* value) { \
    delete (type*) _impl->remove(value); \
} \
\
void name2(type,Array)::removeAndDestroy() { \
    if (_impl) { \
       int size = _impl->getSize(); \
       for (int i=0;i<size;i++) { \
 	 type* value = operator[](i); \
 	 if (value) { \
 	   delete value; \
 	   value = NULL; \
 	 } \
       } \
       _impl->setSize(0); \
    } \
} \




//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


// ------------------------
//
// verification des indices
//
// ------------------------

#define CHECKINDEXES(i, j, k, l) { \
  assert (i>=0 && i<_imax); \
  assert ((j==0 && !_jmax) || (_jmax && j>=0 && j<_jmax)); \
  assert ((k==0 && !_kmax) || (_kmax && k>=0 && k<_kmax)); \
  assert ((l==0 && !_lmax) || (_lmax && l>=0 && l<_lmax)); \
}

#include <rw/math.h>

//---------------------------------------------
class AnyArrayI MEM_OBJ { 

  int _computeCapacity(int size) {
    int s = ceil((double) size / _DEFAULT_CAPACITY_ARRAY_) + 1;
    return s;
  }

  Any _search(const Any any) {
    int i ;
    for( i=0;i<_size && _array[i] != any;i++);
    return i == _size ? NULL : _array[i];
  }

  void _init() { 
    for (int i=0;i<_capacity;i++) _array[i]=NULL; 
  }

  int _index(Any any,int index) const {
    if (index>=_size) return IBOB_NULL;
    int i ;
    for( i=index;i<_size && _array[i] != any;i++);
    return i == _size ? IBOB_NULL : i;
  }


protected: 
  int   _capacity; // capacite du tableau
  int   _size;     // nombre d'elements presents dans le tableau
  Any*  _array;    // valeurs du tableau
  char* _name;     // nom du tableau
public: 
  ~AnyArrayI() {
    if (_capacity) delete [] _array;    
    if (_name)     delete [] _name;
  }
  
  AnyArrayI() 
    : _size(0) , _capacity(_DEFAULT_CAPACITY_ARRAY_), _name(0)
    { 
      _array = new Any [_capacity]; 
      _init();    
    } 
  
  AnyArrayI(int size) 
    : _size(0) , _capacity(size), _name(0)
    { 
      if (!size) _capacity = _DEFAULT_CAPACITY_ARRAY_;
      _array = new Any [_capacity]; 
      _init();
    } 
  
  AnyArrayI(int size, Any* values) 
    : _size(size) , _capacity(_computeCapacity(size) 
			      * _DEFAULT_CAPACITY_ARRAY_), _name(0)
    {
      _array = new Any [_capacity];
      _init();
      for (int i=0;i<size;i++)
	_array[i] = values[i];
    }
  
  Any first() const { 
    return _size == 0 ? NULL : _array[0]; 
  } 
  
  Any last() const { 
    return _size == 0 ? NULL : _array[_size-1]; 
  } 
  
  int getSize() const { 
    return _size; 
  } 
  
  void setSize(int size)  { 
    if (size > _capacity) resize(size);
    _size = size;
  } 

  int getCapacity() const { 
    return _capacity; 
  } 
  
  Booleen contains(Any any) {
    Any tmp = _search(any);
    return tmp ? True : False;
  }

  Any find(Any any) {
    return _search(any);
  }

  int index(Any any) const {
    int i ;
    for(i=0;i<_size && _array[i] != any;i++);
    return i == _size ? IBOB_NULL : i;
  }

  void insert(Any any) {
    if (_size >= _capacity) {
      _capacity+=_DEFAULT_CAPACITY_ARRAY_;
      reshape(_capacity);
    }      
    _array[_size++] = any;
  }

  void remove() {
    clear();
  }

  void clear() {
    delete [] _array;
    _capacity = _DEFAULT_CAPACITY_ARRAY_;
    _array = new Any[_capacity];
    for (int i=0;i<_capacity;i++) _array[i] = NULL;
    _size = 0;
  }

  Any remove(Any any) {
    int at = index(any);
    if (at == IBOB_NULL) return NULL;
    Any tmp = _array[at];
    int i ;
    for( i=at;i<_size-1;i++)
      _array[i] = _array[i+1];
    _array[i] = NULL;
    _size--;
    return tmp;
  }

  void removeAll(Any any) {
    int at = index(any);
    if (at == IBOB_NULL) return ;
    int i ;
    while (at <= _size) {
      for(i=at;i<_size-1;i++)
	_array[i] = _array[i+1];
      _array[i] = NULL;
      _size--;
      at = _index(any,at);
    }
  }

  Any removeAt(int index) {
    assert (index>=0 && index<_capacity);
    if (index >= _size) return NULL;
    Any tmp = _array[index];
    int i ;
    for(i=index;i<_size-1;i++)
      _array[i] = _array[i+1];
    _array[i] = NULL;
    _size--;
    return tmp;
  }

  void reshape(int capacity)                                 
  {                                                         
    Any* newArray = new Any[capacity];                             
    int i = _size;
    while (i--) newArray[i] = _array[i]; 
    for (i=_size+1;i<capacity;i++) newArray[i] = NULL;
    delete [] _array;                          
    _array = newArray;                                      
  }                                                         
  
  void resize(int newSize)                                 
  {                                                         
    if(newSize==_size)return; 
    _capacity = newSize;
    Any* newArray = new Any[_capacity];                             
    int i = (newSize <= _size) ? newSize : _size;               
    while (i--) newArray[i] = _array[i];                            
    delete [] _array;                          
    _array = newArray;                                      
    _size = newSize;                                              
  }                                                         
  
  Any* getArray() const { return _array; } 

  Any& operator[] (int index) const { 
    assert (index>=0 && index<_capacity); 
    return _array[index];
  } 
 
  const Any& getAnyElem(int index) const { 
    assert (index>=0 && index<_capacity); 
    return _array[index]; 
  } 

  const char* getName() const { return _name ? _name : ""; }

  void setName(const char* const name) {
    int len = strlen(name);
    if (_name && len>=strlen(_name)) {
      delete [] _name; 
      _name = 0;
    }
    if (!_name)
      _name = new char[len+1];
    strcpy(_name, name);
  }

};



class AnyArray MEM_OBJ { 
protected: 
  AnyArrayI* _impl;    // pteur sur implementation
public: 

  AnyArray() : _impl(0) {}

  AnyArray(AnyArrayI* impl) 
    : _impl(impl) {} 

  AnyArray::AnyArray(int size, Any* values) 
    : _impl(new AnyArrayI(size,values)) {}


  // actif
  //   AnyArray(AnyArray& array,const Booleen b=False) 
  //      : _impl(array.getImpl()) {}

  // inactif
  AnyArray(const AnyArray& array) 
    : _impl(new AnyArrayI( array.length(), array.getArray() ) ) {}

  AnyArray(int size) 
    : _impl(new AnyArrayI(size)) {} 

  ~AnyArray() { 
    if (_impl) {      
      delete _impl;
      _impl = 0;
    }    
  }

  void operator=(const AnyArray& array) { 
    _impl = array.getImpl(); 
  } 

  AnyArrayI* getImpl() const { return _impl; } 

  void setSize(int size)  { 
    assert (_impl); 
    _impl->setSize(size); 
  } 

  int getSize() const { 
    assert (_impl); 
    return _impl->getSize(); 
  } 

  int getCapacity() const { 
    assert (_impl); 
    return _impl->getCapacity(); 
  } 

  int length() const { 
    assert (_impl); 
    return _impl->getSize(); 
  } 

  void resize(int newSize) {
    if (_impl)
      _impl->resize(newSize);
    else {
      _impl = new AnyArrayI(newSize);
      setSize(newSize);
    }
  }

  void remove() {
    clear();
  }

  void clear() {
    if (_impl) _impl->clear();
  } 
  
  void insert(Any any) {
    if (_impl) {
      _impl->insert(any);
    }  
  }

  Any remove(Any any) {
    if (_impl) {
      return _impl->remove(any);
    }  
    return NULL;
  }

  void removeAll(Any any) {
    if (_impl) {
      _impl->removeAll(any);
    }  
  }

  Any removeAt(int index) {
    if (_impl) {
      return _impl->removeAt(index);
    }  
    return NULL;
  }

  Any find(Any any) {
    if (_impl) {
      return _impl->find(any);
    } 
    return NULL;
  }

  Booleen contains(Any any) {
    if (_impl) {
      return _impl->contains(any);
    } 
    return False;
  }
    
  Any& at(int index) const { 
    assert (_impl); 
    return _impl->operator[](index); 
  } 

  int index(Any any) const { 
    assert (_impl); 
    return _impl->index(any); 
  } 

  Any& operator [] (int index) const { 
    assert (_impl); 
    return _impl->operator[](index); 
  } 

  const Any* getArray() const { 
    assert(_impl); 
    return _impl->getArray(); 
  } 

  const char* getName() const {
    assert(_impl); 
    return _impl->getName();
  }

  void setName(const char* name) {
    assert(_impl); 
    _impl->setName(name);
  }

//   void display(std::ostream& str) const { 
//     assert(_impl); 
//     _impl->display(str); 
//   } 

};

// inline std::ostream& operator << (std::ostream& os,IntArray array) {
//   array.display(os);
//   return os;
// }

template<class T> 
class ArrayBase : public AnyArray {

public:

  ArrayBase() : AnyArray() {}

  ArrayBase(int size) : AnyArray(size) {}

  ArrayBase(int size, const T** values) : AnyArray( size, (Any*) values ) {}

  virtual ~ArrayBase() {}

  // accesseurs sur la 1ere dim
  virtual T*& operator[] (int index) const = 0;

  virtual T*& operator()(int i) const {
    return operator[](i);
  }

  const T& ref(int i) const {
    return *operator[](i); 
  }

  T* find(int (*testFun) (const T* t, void* v), void* value) const;
  
  T* find(T* value) {
    assert (getImpl());
    return (T*) getImpl()->find(value); 
  }

  T* first() const { 
    assert (getImpl());
    return (T*) getImpl()->first();
  } 

  T* last() const {
    assert (getImpl());
    return (T*) getImpl()->last();
  } 

  void removeAndDestroy();

  void remove() {
    assert (getImpl());
    getImpl()->remove();
  } 

  const T** getArray() const {
    return (const T**) AnyArray::getArray();
  } 


};



template<class T>
T* ArrayBase<T>::find(int (*testFun) (const T* t, void* v), void* value) const { 
  int len = length();
  const T* tmp = NULL;
  const T** values = getArray();
  for (int i=0;i<len;i++) { 
    if ( testFun( values[i] , value ) ) {
      tmp = values[i]; 
      break;
    } 
  }
  return (T*) tmp;
} 


template<class T> 
void ArrayBase<T>::removeAndDestroy() 
{
  int size = getSize();
  for (int i=0;i<size;i++) { 
    T* value = (T*) AnyArray::operator[](i);
    if (value) { 
      delete value;
      value = NULL; 
    } 
  } 
  setSize(0); 
} 





//-----------------------------------
//
// Class ArrayX --> classe Template
//
//
// ---> Gestion jusqu'a 4 Dimensions
//      
//
//----------------------------------
//
// Attention : ArrayX<Type> est un
//             tableau de pointeurs
//             sur Type
//
//     Nota :  X pour multi-dims
//
//----------------------------------
//
// Instanciation :
//----------------
//
//  ArrayX<Type> array;
//
// 
//----------------------------------
//
template<class T> 
class ArrayI;

template<class T> 
class ArrayX : public ArrayBase<T> {

public:

  // lecture de l'implementation du tableau
  ArrayI<T>* getImpl() const {
    return (ArrayI<T>*)_impl;
  }

  // constructeur de partage d'1 meme tableau (equiv a 1 Reference)
  ArrayX(ArrayI<T>* impl) {
    _impl = impl;
  }
  
  // constructeur par defaut
  //  ArrayX() {}

  //  constructeur de copie
  ArrayX(const ArrayX<T>& array) {
    _impl = new  ArrayI<T>(array.getFirstSize(),
			   array.getSecondSize(),
			   array.getThirdSize(),
			   array.getFourthSize(),
			   array.getArray());
  }

  //  constructeur a partir d'1 Array<T>
  ArrayX(const Array<T>& array) {
    _impl = new  ArrayI<T>(array.getSize(),
			   array.getArray());
  }



  // constructeur a partir d'1 tableau a 1 dim pouvant etre 
  // genere par la MACRO ANYARRAY_DECLARE
  ArrayX(int size, const T** values) {
    _impl = new  ArrayI<T>(size, values);
  }

  // constructeur d'1 tableau pouvant aller jusqu'a 4 dims
  ArrayX(int imax, int jmax = 0, int kmax = 0, int lmax = 0) {
    _impl = new  ArrayI<T>(imax, jmax, kmax, lmax);
  }

  // accesseur sur la 1ere dim
  T*& operator[] (int index) const { 
    assert (index>=0 && index<getImpl()->getFirstSize()); 
    return getImpl()->getElem(index); 
  }

  // accesseur sur les <> dims
  T*& operator()(int i) const {
    assert (getImpl());
    return getImpl()->getElem(i); 
  }

  T*& operator()(int i, int j) const {
    assert (getImpl());
    return getImpl()->getElem(i, j); 
  }

  T*& operator()(int i, int j, int k) const {
    assert (getImpl());
    return getImpl()->getElem(i, j, k); 
  }

  T*& operator()(int i, int j, int k, int l) const {
    assert (getImpl());
    return getImpl()->getElem(i, j, k, l); 
  }

  // operator de copy
  void operator=(const ArrayX<T>& array) {
    AnyArray::~AnyArray();
    _impl = new  ArrayI<T>(array.getFirstSize(),
			   array.getSecondSize(),
			   array.getThirdSize(),
			   array.getFourthSize(),
			   array.getArray());
  }

  // lecture des <> tailles des dims
  int getFirstSize() const {
    assert(getImpl());
    return getImpl()->getFirstSize();
  }

  int getSecondSize() const {
    assert(getImpl());
    return getImpl()->getSecondSize();
  }

  int getThirdSize() const {
    assert(getImpl());
    return getImpl()->getThirdSize();
  }

  int getFourthSize() const {
    assert(getImpl());
    return getImpl()->getFourthSize();
  }

  T*& getElem(int i, int j=0, int k=0, int l=0) const {
    assert (getImpl());
    return getImpl()->getElem(i, j, k, l); 
  }

};





template<class T> 
class ArrayI : public AnyArrayI {
protected:
  static  int ComputeSize(int imax, int jmax, int kmax, int lmax);

  const int _imax;
  const int _jmax;
  const int _kmax;
  const int _lmax;
public:
  ArrayI(int imax, 
	 int jmax = 0, 
	 int kmax = 0, 
	 int lmax = 0);
  ArrayI(int size, const T** values);
  ArrayI(int imax, int jmax, int kmax, int lmax,
 	 const T** values);
  ~ArrayI();
  // -------------------------
  int getFirstSize()  const { return _imax; }
  int getSecondSize() const { return _jmax; }
  int getThirdSize()  const { return _kmax; }
  int getFourthSize() const { return _lmax; }
  T*& getElem(int i, int j=0, int k=0, int l=0) const;
  Any& getAnyElem(int i, int j=0, int k=0, int l=0) const;
  //  virtual void display(std::ostream& stream) const;
};


// ----------------------------------------------------------------------------

template<class T> 
ArrayI<T>::ArrayI(int imax,
		  int jmax,
		  int kmax,
		  int lmax)
  : AnyArrayI(ComputeSize(imax,jmax,kmax,lmax)),
    _imax(imax), _jmax(jmax), _kmax(kmax), _lmax(lmax)
{
  resize( ComputeSize(imax,jmax,kmax,lmax) );
}

// ----------------------------------------------------------------------------

template<class T> 
ArrayI<T>::ArrayI(int size,
  		  const T** values)
  : AnyArrayI(size, (Any*) values),
    _imax(size), _jmax(0), _kmax(0), _lmax(0)
{
}


// ----------------------------------------------------------------------------

template<class T> 
ArrayI<T>::ArrayI(int imax,
		  int jmax,
		  int kmax,
		  int lmax,
		  const T** values)
  : AnyArrayI(imax*jmax*kmax*lmax, (Any*) values),
    _imax(imax), _jmax(jmax), _kmax(kmax), _lmax(lmax)
{
}

// ----------------------------------------------------------------------------

template<class T> 
ArrayI<T>::~ArrayI()
{
}

// ----------------------------------------------------------------------------

template<class T> 
T*& ArrayI<T>::getElem(int i, int j, int k, int l) const
{
  CHECKINDEXES(i, j, k, l);

  return ((T**) _array)[i+_imax*j+_imax*_jmax*k+_imax*_jmax*_kmax*l];
}


// ----------------------------------------------------------------------------

template<class T> 
Any& ArrayI<T>::getAnyElem(int i, int j, int k, int l) const
{
  CHECKINDEXES(i, j, k, l);

  return ((Any*)_array)[i+_imax*j+_imax*_jmax*k+_imax*_jmax*_kmax*l];
}

// ----------------------------------------------------------------------------

template<class T> 
int ArrayI<T>::ComputeSize(int imax, int jmax, int kmax, int lmax)
{
  if (jmax && kmax && lmax)
    return imax*jmax*kmax*lmax;
  if (jmax && kmax)
    return imax*jmax*kmax;
  if (jmax)
    return imax*jmax;
  return imax;
}






//-----------------------------------
//
// Class Array --> classe Template
//
//----------------------------------
//
// Attention : Array<Type> est un
//             tableau de pointeurs
//             sur Type
//
//----------------------------------
//
// Instanciation :
//----------------
//
//  Array<Type> array;
//
// 
//----------------------------------
//

template<class T> 
class Array : public ArrayBase<T> {

public:

  // constructeur par defaut
  Array() : ArrayBase<T>(0) {} 

  // constructeur de copie
  Array(const Array<T>& array) 
    : ArrayBase<T>( array.getSize(), array.getArray() )  {
  }

  // constructeur a partir d'1 tableau a 1 dim pouvant etre 
  // genere par la MACRO ANYARRAY_DECLARE
  Array(int size, const T** values) : ArrayBase<T>( size, values ) {
  }

  // accesseur sur la 1ere dim
  T*& operator[] (int index) const { 
    return getElem(index); 
  }

  // accesseur sur les <> dims
  T*& operator()(int i) const {
    return getElem(i); 
  }

  // operator de copy
  void operator=(const Array<T>& array) {
    AnyArray::~AnyArray();
    AnyArray::AnyArray(array.getSize(), (Any*) array.getArray());
  }

  T*& getElem(int i) const {
    assert (getImpl());
    return (T*&) getImpl()->getAnyElem(i);
  }

  void insert(T* value) { 
    getImpl()->insert(value);
  } 

  void removeAndDestroy(T* value) {
    delete (T*) getImpl()->remove(value);
  }

  T* removeAt(int index) { 
    return (T*) getImpl()->removeAt(index); 
  } 

  T* remove(T* value) {
     return (T*) getImpl()->remove(value);
  } 

  void sort(int (*)(const T*, const T*));
  
  static int (*_fct)(const T*, const T*); 

  static int InternalComparisonFunction(const void* i, const void* j); 

};


template<class T>
int (*Array<T>::_fct)(const T*, const T*) = 0;


template<class T> 
void Array<T>::sort(int (*fct)(const T*, const T*)) {
  _fct = fct;
  qsort(getArray(), (int) getSize(), sizeof(T*), 
 	&InternalComparisonFunction); 
}


template<class T> 
int Array<T>::InternalComparisonFunction(const void* i, const void* j) 
{ 
   return _fct(*(const T**)i, *(const T**)j); 
} 


// inline std::ostream& operator<<(std::ostream& stream, const Array& array)
// {
//   assert(array.getImpl());
//   array.getImpl()->display(stream);
//   return stream;
// }









#endif




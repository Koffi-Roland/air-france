//#ifdef pel
#ifndef _Ressource_h_
#define _Ressource_h_

//#include <rw/tphdict.h>
//#include <rw/tvhdict.h>
//#include <rw/tvordvec.h>
//#include <rw/rstream.h>
//#include <rw/ctoken.h>
#include <string>
#include <map>
#include <vector>
#include <fstream>

//#include "common_hasher.h"

unsigned hFun(const std::string& s);

// edifact
// typedef std::map <  std::string,  std::map < std::string, std::string, string_hash, std::equal_to<std::string>  >  ,  string_hash, std::equal_to< std::string>  >::iterator  RessourceIterator;

const std::string DefaultLanguage("en");




class Structure {
  int occurence;
  int debut;
  int fin;   
public:
  Structure() : occurence(1), debut(-1), fin(-1) {}
  Structure(const Structure& pStruct) { operator=(pStruct); }
  Structure(int pOccurence, int pDebut, int pFin) 
    : occurence(pOccurence), debut(pDebut), fin(pFin) {}
  Structure& operator=(const Structure& pStruct) { 
    occurence = pStruct.getOccurence(); 
    debut = pStruct.getDebut(); 
    fin = pStruct.getFin();
    return *this;
  }
  bool operator==(const Structure& pStruct) const { 
    return true;
  }
  int getOccurence() const { return occurence; }
  int getDebut() const { return debut; }
  int getFin() const { return fin; }
  bool isNull() const { return occurence == 0; }
};



class Ressource {
  
private:
  //std::map <  std::string,  std::map < std::string, std::string, string_hash, std::equal_to<std::string>  >  ,  string_hash, std::equal_to< std::string>  >  _hash;

  std::map <  std::string,  std::map < std::string, std::string> >  _hash;
  std::string  _fileName;

protected:

  std::vector<std::string> parseHeader(std::ifstream& is);
  virtual bool parseData(std::ifstream& is, const std::vector<std::string>& vector);  

public:

  Ressource() {}
  Ressource(const std::string& aFileName);
  ~Ressource();

  //std::map <  std::string,  std::map < std::string, std::string, string_hash, std::equal_to<std::string>  >  ,  string_hash, std::equal_to< std::string>  >::iterator  iter();
  //std::map <  std::string,  std::vector<long> >::iterator iter;  
  bool load(const std::string& aFileName);
  bool reload();
  void unload();
  const std::string& getFileName() { return _fileName; }
  void setFileName(const std::string& aFileName) { _fileName=aFileName; }
  virtual bool contains(const std::string& key);
  virtual std::string getValue(const std::string& key,
			     const std::string& lang=DefaultLanguage);
};


#ifdef edifact
class Parseur : public Ressource {
  
private:

  int _step;
  //std::map <  std::string,  std::vector<long> ,  string_hash, std::equal_to< std::string>  >  _hash;
  std::map <  std::string,  std::vector<long> >  _hash;
  //std::map <  std::string,  std::vector<Structure> ,  string_hash, std::equal_to< std::string>  >  _struct;
  std::map <  std::string,  std::vector<Structure> >  _struct;

protected:

  bool parseData(std::ifstream& is, const std::vector<std::string>& vector);  
  bool checkStructure(const std::string& pStruct, int pMaxIndex, 
			   int& pOccurs, int& pDebut, int& pFin);  

public:

  Parseur(const std::string& aFileName);
  virtual bool contains(const std::string& key);

  std::vector<long> getValues(const std::string& key);
  std::vector<Structure> getStructure(const std::string& key);

  int getStep() const { return _step; }
};


#endif
#endif


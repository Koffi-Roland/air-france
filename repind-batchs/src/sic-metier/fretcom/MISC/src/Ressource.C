//#ifdef pel
#include "Ressource.h"
#include <signal.h>
#include "DebugTools.h"
#include "BasicException.h"
#include <stdlib.h>
#include <locale>
#include <algorithm>
#include <cctype>
#include <map>

//using namespace fretcom;
#define NULL_SIGNAL_HANDLER	((void (*)(int)) -1)
#ifdef edifact
typedef std::map < std::string, std::map < std::string, std::string, string_hash, std::equal_to<std::string>  > , string_hash, std::equal_to<std::string>  >::iterator  RessourceIterator;


#endif

/*unsigned hFun(const std::string& s)
{
  //return s.hash();
  return std::hash<std::string>()(s);;
}*/

Ressource::Ressource(const std::string& aFileName) 
 // : _hash(hFun)
{
  load(aFileName);
}

Ressource::~Ressource() 
{
  unload();
}


/*std::map < std::string, std::map < std::string, std::string, string_hash, std::equal_to<std::string>  > , string_hash, std::equal_to<std::string>  >::iterator  Ressource::iter()
{
  std::map < std::string, std::map < std::string, std::string, string_hash, std::equal_to<std::string>  > , string_hash, std::equal_to<std::string>  >::iterator  iter(_hash);
  return iter;
}*/


bool Ressource::reload()
{
  PERF_AUTO("Ressource::reload");
  unload();
  return load(getFileName());
}


bool Ressource::load(const std::string& aFileName)
{
  setFileName(aFileName);

  bool ret(false);
  std::ifstream file(aFileName.data());
  if (file.bad()) 
    {
      TRACEMSG("#PROBLEM# : .... File not found ..... : " << aFileName);
      return ret;
    }

  TRACEMSG("Ressource::load [" << aFileName << "]");
 
  ret = parseData(file, parseHeader(file));
  file.close();
  return ret;
}


void Ressource::unload()
{
//  std::map < std::string, std::map < std::string, std::string, string_hash, std::equal_to<std::string>  > , string_hash, std::equal_to<std::string>  >::iterator  iter;
  // (_hash);
  std::map <  std::string, std::map<std::string, std::string> >::iterator outer_iter ;
  for (outer_iter = _hash.begin(); outer_iter!= _hash.end();++outer_iter)
  {

     //outer_iter->second->clear();
     for(std::map<std::string, std::string>::iterator inner_iter=outer_iter->second.begin(); inner_iter!=outer_iter->second.end(); ++inner_iter)
     {
        inner_iter->second.clear();
      //iter.value()->clearAndDestroy();
     }
  }

  //_hash.clearAndDestroy();
  _hash.clear();

}


bool Ressource::contains(const std::string& key)
{
  //TODO return _hash.contains(&key);
  return true;
}

//get value from  file / string
std::string Ressource::getValue(const std::string& key, const std::string& lang)
{
  std::string value;
  std::map < std::string, std::string >  keyValue;

  if (_hash.find(key) != _hash.end()){
    keyValue = _hash[key];
    //std::transform(((std::string&)lang).begin(), ((std::string&)lang).end(), ((std::string&)lang).begin(), tolower);
    //if (keyValue.find(key) != keyValue.end()){i
    //lang="0:system";
    //if (keyValue.find(lang) != keyValue.end()){
    value = keyValue[lang];
    }
  //}
  //std::map < std::string, std::string, string_hash, std::equal_to<std::string>  > * keyValue;
  //if ( (keyValue = _hash.findValue(&key)) == 0 ) return "";
  //((std::string&)lang).toLower();
  //std::transform(((std::string&)lang).begin(), ((std::string&)lang).end(), ((std::string&)lang).begin(), tolower);
  //std::string* value = keyValue->findValue(&lang);
  //return value ? value : std::string();
  // TODO check return
  return value;
}


std::vector<std::string> Ressource::parseHeader(std::ifstream& file)
{
  std::cout << "parseHeader" << std::endl; 
  std::vector<std::string> vector;
  std::string s("");
  // read first line 
  //pel s.readLine(file);
  std::getline (file,s);
  //pel RWCTokenizer next(s);
  char * next1 = std::strtok(&s[0], " ");
  char * next = std::strtok(next1, "=");
  std::string token, sValue, sName;
  // and parse structure of filei
  // !!!! à verifier pel
  while ( next != NULL ) {
    next = std::strtok(NULL, "=");
    //RWCTokenizer nexttok(token);
    //sName  = nexttok("=");
    //sValue = nexttok("=");
    if ( next != NULL )
      vector.push_back(next);
  }
  return vector;
}
bool Ressource::parseData(std::ifstream& file, const std::vector<std::string>& vector)
{  
  std::cout << "parseData" << std::endl; 
  if (vector.empty()) return false;
// TODO!!!
  std::string s("");
  std::getline (file,s);
  char *  next1 = std::strtok(&s[0]," ");
  char *  next = std::strtok(next1,":");
  //while (!s.readLine(file).eof())
  while (!s.empty())
    {
      std::getline (file,s);
      std::cout << "s " << s << std::endl; 
      // comment lines 
      if (s.find("#") == 0 || s.empty()) continue;
      next1 = std::strtok(&s[0]," ");
      next = std::strtok(next1,":");
        std::cout << "next " << next << std::endl; 
      //std::string token;
      std::string key = next;
      //std::map < std::string, std::string, string_hash, std::equal_to<std::string>  > * value = new std::map < std::string, std::string, string_hash, std::equal_to<std::string>  > (hFun);
      //std::map <std::string, std::string> *value = new std::map <std::string, std::string> (hFun);
      std::map <std::string, std::string> value;
      int i = 0;
      //while ( !(token=next(":")).empty() ) {
      while ( next != NULL ) {
	//value->insertKeyAndValue(new std::string(vector[++i]), new std::string(next));
        value[vector[i++]]=next;
        std::cout << "find " << next << std::endl; 
        next = std::strtok(NULL,":");
      }
      //_hash[next]=value;
      _hash[key]=value;
      //_hash.insertKeyAndValue(new std::string(next), value);
    }
  return true;
}

#ifdef edifact
Parseur::Parseur(const std::string& aFileName)
  : _hash(hFun), _struct(hFun)
{
  load(aFileName);
}


bool Parseur::contains(const std::string& key)
{
  return _hash.contains(key);
}


std::vector<long> Parseur::getValues(const std::string& key)
{
  std::vector<long> values;
  _hash.findValue(key, values);
  return values;
}


std::vector<Structure> Parseur::getStructure(const std::string& key)
{
  std::vector<Structure> values;
  _struct.findValue(key, values);
  return values;
}




bool Parseur::parseData(std::ifstream& file, const std::vector<std::string>& vector)
{
  _step = vector.length() - 1;
  std::string s;
  std::getline (file,s);
  //while (!s.readLine(file).eof())
  while (!s.empty());
  {
      // comment lines 
      if (s.index("#") == 0 || s.empty()) continue;
      //RWCTokenizer tok(s);
      // Cut Detail Part and Structure Part
      //std::string detail = tok("&");
      std::string structure = tok("&");

      // Parse Detail Part
      char *  next = std::strtok(&s[0],"&");
      char *  structure = std::strtok(&s[0],"&");
      //std::string token;
      std::string key = next(":");
      std::vector<long> vLong;
      int i = 0;
      //while ( !(token=next(":")).empty() ) {
      while ( next != NULL ) {
        next = std::strtok(NULL,":");
	vLong.insert(atol(next.data()));
      }	  
      //_hash.insertKeyAndValue(key, vLong);
      _hash[key]=vLong;
      // Parse Structure Part, if exists
      std::vector<Structure> vStructure;
      //if (structure.empty())
      if (structure := NULL)
	{
	  // Nb Occurence = 1, --> All items of vector
	  vStructure.insert(Structure(1, 0, vLong.entries()-1));
          structure = std::strtok(NULL,"&");
	}
      else
	{
	  //RWCTokenizer next(structure);
	  char * next =std::strtok(&stucture[0],":");
	  int occ, deb, fin;
	  //while ( !(token=next(":")).empty() ) {
	  while ( next != NULL ) {
	    checkStructure(std::string(next), vLong.entries(), occ, deb, fin);
	    Structure s(occ, deb, fin);	    
	    vStructure.insert(s);
            next = strtok (NULL, ":");
	  }	  
	}
      //_struct.insertKeyAndValue(key, vStructure);
      _struct[key]=vStructure;
      std::getline (file,s);
    }
  return true;
}



bool Parseur::checkStructure(const std::string& pToken, int pMaxIndex, int& pOccurs, int& pDebut, int& pFin)
{
  //RWCTokenizer next(pToken);  
  char* next=std::strtok(&pToken[0],"");
  while ( next != NULL ) {
     next = std::strtok(NULL,"*-");
  }
  pOccurs = atol(next);
  //pOccurs = atol(next("*-").data());
  FAILASSERT(pOccurs > 0);
  while ( next != NULL ) {
     next = std::strtok(NULL,"*-");
  }
  pDebut  = atol(next) - 1;
  //pDebut  = atol(next("*-").data()) - 1;
  FAILASSERT(pDebut >= 0 && pDebut < pMaxIndex);
  while ( next != NULL ) {
     next = std::strtok(NULL,"*-");
  }
  pFin    = atol(next) - 1;
  //pFin    = atol(next("*-").data()) - 1;
  FAILASSERT(pFin > 0 && pFin < pMaxIndex);
  return true;
}
#endif //edifact

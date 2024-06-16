/*********************************************************/
/* CLASSE  ESCALECONFIG                                  */
/* GENERATED : 14/02/03                                 */
/* AUTHOR : apie                                         */
/*********************************************************/

#ifndef EscaleConfig_h
#define EscaleConfig_h 1
#include <list>
#include "interfaceOracle2.h" 
#include "ToolsMetCom.h"
#include "DefinesMetCom.h"
#include <iostream>

class EscaleConfig MEM_OBJ{
public:
  /* Constructeur */
  EscaleConfig(){_codeEscale = "";}; 
  EscaleConfig(std::string* pCodeEscale);
  /* Destructeur */
  virtual ~EscaleConfig(){};
  
  /* --- Equality Operations --- */
  bool operator==(const EscaleConfig &right) const;
  bool operator!=(const EscaleConfig &right) const;
  EscaleConfig & operator=(const EscaleConfig &right);
  /* Accesseurs */
  const std::string& getCodeEscale() const     { return _codeEscale; }
  void setCodeEscale(const std::string & value) { _codeEscale = value; }  
  /* --- Display --- */
#ifdef DEBUG
  friend std::ostream & operator<< ( std::ostream & os , const EscaleConfig & param ) { param.display(os); return os; };
  virtual void  display(std::ostream& os) const;       
#endif
private:
  /* Attributs */
  // Le nom de l'escale 
  std::string _codeEscale;      

};


/*************************/
/* CLASS ESCALECONFIGLIST */
/*************************/

typedef std::list<EscaleConfig>::iterator  EscaleIterator;

class EscaleConfigList : public std::list<EscaleConfig>
{
public:
  
  /* --- Constructors ---- */
  EscaleConfigList(){};
  /* --- Destructor --- */
  virtual ~EscaleConfigList();

#ifdef DEBUG
  friend std::ostream& operator<<(std::ostream& os, const EscaleConfigList& obj)
    { obj.display(os); return os; }
  virtual void display(std::ostream& os) const;
#endif

};  /* End Class EscaleConfigList */



/***************************/
/* CLASS SOUS SECTEUR           */
/*  */
/***************************/
class SousSecteur MEM_OBJ {
public:
  /* Constructeur */
  SousSecteur(){_codeSousSecteur = "";};
  SousSecteur(std::string *pCode);
  //virtual ~SousSecteur(){_listeEscale.clearAndDestroy();};
  virtual ~SousSecteur(){_listeEscale.clear();};
   /* --- Equality Operations --- */
  bool operator==(const SousSecteur &right) const;
  bool operator!=(const SousSecteur &right) const;
  SousSecteur & operator=(const SousSecteur &right);
  /* Accesseurs*/
  EscaleConfigList getListeEscale(){return _listeEscale;};
  void setListeEscale(EscaleConfigList &value){_listeEscale = value;};

  const std::string getCodeSousSecteur() const {return _codeSousSecteur;};
  void setCodeSousSecteur(const std::string &value){_codeSousSecteur = value ; };

private:
  /* Liste d'escale */
  EscaleConfigList _listeEscale;
  /* Code du sous secteur */
  std::string _codeSousSecteur;

};
/***************************/
/* CLASS SECTEUR           */
/*  */
/***************************/
class Secteur MEM_OBJ {
public:
  /* Constructeur */
  Secteur(){ _codeSecteur = "";};
  Secteur(std::string *pCode);
  virtual ~Secteur(){_listeSousSecteur.clear();};
  /* --- Equality Operations --- */
  bool operator==(const Secteur &right) const;
  bool operator!=(const Secteur &right) const;
  Secteur & operator=(const Secteur &right);
  /* Accesseurs*/
  std::list<SousSecteur> getListeSousSecteur(){return _listeSousSecteur;};
  void setListeSousSecteur(std::list<SousSecteur> &value){_listeSousSecteur = value;};
  /* Code Secteur */
 const std::string getCodeSecteur() const {return _codeSecteur;};
  void setCodeSecteur( const std::string &value){_codeSecteur = value ; };

private:
  /* Liste de sous secteur */
  std::list<SousSecteur> _listeSousSecteur;
  /* Code Secteur */
  std::string _codeSecteur ;

};

#endif


#if !defined(NB_DB_AGENT_H)
#define NB_DB_AGENT_H

//============================================================
// File Information
//============================================================
//    File:     NB_DBAgent.h
//  Author:     Mireille HOT
//    Date:     01/02/2006
// Purpose:     Class representing NB_DBAgent instances
//              An object that is needed only to hold the 
//              enum type copied from Persistence PS_DBAgent
//              because it is used by some AF applictions

//============================================================
// Imports
//============================================================

class NB_DBAgent {

public:

  // Nested types
  enum DataType{ PS_CHAR, PS_BINARY, PS_SHORT, PS_INT, PS_LONG /* 4 */, PS_FLOAT,
		 PS_DOUBLE, PS_TEXT /* 7 */, PS_IMAGE, PS_MONEY, PS_DATETIME,
		 PS_DECIMAL, PS_UNKNOWN };

  // Constructors
  NB_DBAgent();
  //. NB_DBAgent( const NB_ResultObject & );

  // Instance Deletion:
  virtual ~NB_DBAgent();

  // Instance Operators:
  //. NB_SelectManyObject& operator=(const NB_SelectManyObject &);
  //. int operator==(const PS_ResultObject &) const;
  //. int operator!=(const PS_ResultObject &obj) const
  //. 	{ return !(*this == obj); }
  //. PS_ResultValue * operator[](unsigned int indx);
  //. NB_ResultObject * operator[]( unsigned int idx ) const ;

  // Instance Modifiers:
  //. void setProperties(const PS_PropertyList &prpList);
  //. void setValues(const PS_ValueList *valList);

  // Instance Accessors:
  //. PS_ResultProperty * propertyAt(unsigned int indx);
  //. PS_ResultProperty * propertyAt(unsigned int indx) const;
  //. PS_ResultValue * valueAt(unsigned int indx);
  //. PS_ResultValue * valueAt(unsigned int indx) const;
  //. const PS_PropertyList &getProperties() const;
  //. const PS_ValueList *getValues() const;


private:

};

#endif //. NB_DB_AGENT_H


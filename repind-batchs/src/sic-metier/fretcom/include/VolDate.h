#ifndef VolDate_h
#define VolDate_h

// Standard library
//#include <rw/rwdate.h>
//#include <rw/rwtime.h>
//#include <rw/cstring.h>
//#include <rw/tpslist.h>
#include <ctime>
// Standard Fret Tools library
#include "interfaceOracle2.h"

// Metier
//cgoops #include "SegmentVolDate.h"
//cgoops class SegmentVolDate;

//f
//------------------------------------------------------------------------------
// Class VolDate
//
// AFL	43	Facultatif	Vol d'apport et Date et heure de livraison
// 43	AFL/Code compagnie a[2..3]/Code compagnie conjointe a[2..3]
//         /Numero de vol a[2..4]/Suffixe du numero de vol a[1]/Date du vol
//         (depart ETL) n[8]/Date et heure de livraison n[14]CRLF
//------------------------------------------------------------------------------
//f

class VolDate MEM_OBJ
{
public:
  // Constructeurs
  VolDate();
  // Destructeur
  virtual ~VolDate() {};
  // Accesseurs
  void setAirlineCode(const std::string& param) { _airlineCode = param; }
  const std::string getAirlineCode() const { return _airlineCode;}
  void setCodeShareAirlineCode(const std::string& param) { _codeShareAirlineCode = param; }
  const std::string getCodeShareAirlineCode() const { return _codeShareAirlineCode;}
  void setFlightNumber(const std::string& param) { _flightNumber = param; }
  const std::string getFlightNumber() const { return _flightNumber;}
  void setFlightSuffix(const std::string& param) { _flightSuffix = param; }
  const std::string getFlightSuffix() const { return _flightSuffix;}
  void setDepartureDate(const struct tm& param) { _departureDate = param; }
  const struct tm getDepartureDate() const { return _departureDate;}
  void setImmatriculation(const std::string& param) { _immatriculation = param; }
  const std::string getImmatriculation() const { return _immatriculation;}
  void setOperatingAirlineCode(const std::string& param) { _operatingAirlineCode = param; }
  const std::string getOperatingAirlineCode() const { return _operatingAirlineCode;}
  void setOperatingFlightNumber(const std::string& param) { _operatingFlightNumber = param; }
  const std::string getOperatingFlightNumber() const { return _operatingFlightNumber;}
  void setOperatingDepartureDate(const struct tm& param) { _operatingDepartureDate = param; }
  const struct tm getOperatingDepartureDate() const { return _operatingDepartureDate;}
  //IMPORTANT
  // Need to call WrapperInVolDate::readFlightTime to have correct values, otherwise you get the current time
  void setLocalDepartureTime(const time_t & param){_localDepartureTime=param;}
  const time_t & getLocalDepartureTime() const { return _localDepartureTime;}
  void setLocalArrivalTime(const time_t & param){_localArrivalTime=param;}
  const time_t & getLocalArrivalTime() const { return _localArrivalTime;}
  //returns true if the real local and departure time were set (by the wrapper)
  const bool & isLocalDepTimeValid() const { return _localDepTimeValid;}
  void setLocalDepTimeValid(const bool & value) { _localDepTimeValid=value;}
  const bool & isLocalArrTimeValid() const { return _localArrTimeValid;}
  void setLocalArrTimeValid(const bool & value) { _localArrTimeValid=value;}
  //cgoops std::list<SegmentVolDate> & getSegmentList() { return _segmentList; }
  //cgoops void setSegmentList(const std::list<SegmentVolDate> & value) { _segmentList = value; }

  
  //affecte le flight number et suffix d'après la concaténation des 2 (chaine venant de la base pelora).
  void parseFlightNumberAndSuffix(const std::string & fnas);


  // Autres methodes
#ifdef DEBUG
  friend std::ostream& operator<<(std::ostream& os, const VolDate& obj)
    { obj.display(os); return os; }
  virtual void display(std::ostream& os) const;
#endif
  
  /* --- Surcharge des operateurs --- */
  bool operator==(const VolDate &right) const;
  bool operator!=(const VolDate &right) const;
  VolDate & operator=(const VolDate &right);

private:
  // Attributs
  // - ALOS/CLUS/LTAS.scod_cie_app et SEGMENTS.scod_cie
  std::string _airlineCode; // Code compagnie a[2..3]
  // - ALOS/CLUS/LTAS.scod_cie_cnj_app et SEGMENTS.scod_cie_conj
  std::string _codeShareAirlineCode; // Code compagnie conjointe a[2..3]
  // - ALOS/CLUS/LTAS.snum_vol_app et SEGMENTS.snum_vol
  std::string _flightNumber; // Numero de vol a[2..4]
  // - ALOS/CLUS/LTAS.ssuff_vol_app et SEGMENTS.ssuff_vol
  std::string _flightSuffix; // Suffixe du numero de vol a[1]
  // -  ALOS/CLUS/LTAS.ddat_vol_app SEGMENTS.ddat_vol
  struct tm _departureDate; // Date du vol (depart ETL) n[8]
  std::string _immatriculation; // Immatriculation de l'avion a[5]
  std::string _operatingAirlineCode; // Code compagnie operationnel a[2..3]
  std::string _operatingFlightNumber; // Numero de vol operationnel a[2..5]
  struct tm _operatingDepartureDate; // Date du vol operationnel n[8]

  //local departure and arrival time
  //IMPORTANT
  //these attributes are filled by the wrapper's readFlightTime method
  //otherwise they contains the current time (default constructor)
  //the wrapper set _timeSet to true when it founds the time values
  time_t _localDepartureTime;
  time_t _localArrivalTime;
  bool _localDepTimeValid;
  bool _localArrTimeValid;

  //cgoops std::list<SegmentVolDate> _segmentList;

}; // class VolDate

#endif VolDate_h

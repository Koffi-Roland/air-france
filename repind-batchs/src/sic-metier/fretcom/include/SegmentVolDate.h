#ifndef SegmentVolDate_h
#define SegmentVolDate_h

// Standard library
//#include <rw/rwdate.h>
//#include <rw/rwtime.h>
//#include <rw/cstring.h>

// Standard Fret Tools library
#include "interfaceOracle2.h"

// Metier
#include "VolDate.h"
#include "EscaleConfig.h"
class VolDate;

//f
//------------------------------------------------------------------------------
// Class SegmentVolDate
//
// For allotment AFL and CFL
// AFL	40	Facultatif	Vol d'apport
// CFL  40      Facultatif      Vol de connection
// AFL	1	40	Facultatif    Vol d'apport
// 40	AFL/Code compagnie a[2..3]/Code compagnie conjointe a[2..3]
//         /Numero de vol a[2..4]/Suffixe du numero de vol a[1]
//         /Fréquence a[8]/Origin station a[5]/Destination station a[5]CRLF
//------------------------------------------------------------------------------
//f

class SegmentVolDate MEM_OBJ
{
public:

  // Constructeurs
  SegmentVolDate();
  SegmentVolDate(const SegmentVolDate & aSegVolDate) { operator=( aSegVolDate ); };
  SegmentVolDate(VolDate * refVolDate,
		 EscaleConfig * originStation ,
		 EscaleConfig * destinationStation);

  // Destructeur
  ~SegmentVolDate();

  // Operateurs
  SegmentVolDate & operator=(const SegmentVolDate & right);

  // Accesseurs
  VolDate * getRefVolDate() const { return _refVolDate; }
  EscaleConfig * getOriginStation() const { return _originStation; }
  EscaleConfig * getDestinationStation() const { return _destinationStation; }
  void setRefVolDate(VolDate * value) { _refVolDate = value; }
  void setOriginStation(EscaleConfig * value) { _originStation = value; }
  void setDestinationStation(EscaleConfig * value) { _destinationStation = value; }

  // Autres methodes
#ifdef DEBUG
  friend std::ostream& operator<<(std::ostream& os, const SegmentVolDate& obj)
    { obj.display(os); return os; }
  virtual void display(std::ostream& os) const;
#endif

private:

  // Attributs
  VolDate * _refVolDate;
  EscaleConfig * _originStation;
  EscaleConfig * _destinationStation;

}; // class SegmentVolDate

#endif SegmentVolDate_h

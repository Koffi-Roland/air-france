//f
//----------------------------------------------------------------------
//     FILE        : Msgsecurity.h
//
//     DESCRIPTION : Common security data.
//                   
//     AUTHOR      : annick
//     DATE        : 03/04/01
//----------------------------------------------------------------------
//f

#ifndef _MsgSecurity_H
#define _MsgSecurity_H

#include "Msg.h"

// During migration METCOM/MSGCOM/COMCOM to FRETCOM. In test.
//#ifdef ADHESION
#include "COMCOM_Common.h"
//#endif

class MsgSecuLevel : public Msg
{
public :
  MsgSecuLevel() {}

  virtual ~MsgSecuLevel(){} ;

  MsgSecuLevel(const MsgSecuLevel& id) { operator=(id); }
  MsgSecuLevel& operator=(const MsgSecuLevel& right);

  //pel TODO void toUpper() { _level.toUpper() ;}

  void decode(WSecuLevel_Type& in);

  std::string _level;
};


class MsgSecurity : public Msg
{
public:
    MsgSecurity() {}
    virtual ~MsgSecurity() {}
    MsgSecurity(const MsgSecurity& id) { operator=(id); }
    MsgSecurity& operator=(const MsgSecurity& right);

//#ifdef ADHESION
    MsgSecurity(WSecu_Type& in)  { decode(in); }
    void decode(WSecu_Type& in);
    void checkSecurity(const std::string& srv);

//#endif

    void toUpper();

#ifdef DEBUG
    virtual void display(std::ostream& os) const;
#endif

    std::string _agent;
    std::string _ciehote;
    std::string _langue;
    std::string _stationsi;
    std::string _usrgrp;
    MsgSecuLevel _usrlvl[__maxUlvl];
 
};


#endif

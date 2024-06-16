//f
//----------------------------------------------------------------------
//     FILE        : MsgHabilitation.C
//
//     DESCRIPTION : Common habilitation data.
//                   
//     AUTHOR      : chris
//     DATE        : 29/01/01
//----------------------------------------------------------------------
//f

#ifndef _MsgHabilitation_H
#define _MsgHabilitation_H

#include "Msg.h"

#ifdef ADHESION
#include "COMCOM_Common.h"
#endif


class MsgHabilitation : public Msg
{
public:
    MsgHabilitation() {}
    MsgHabilitation(const MsgHabilitation& id) { operator=(id); }
    MsgHabilitation& operator=(const MsgHabilitation& right);

#ifdef ADHESION
    MsgHabilitation(WUser_Type& in) { decode(in); }
    void decode(WUser_Type& in);
#endif

    //pel TODO void toUpper() { _user.toUpper(); _qualif.toUpper(); _role.toUpper(); }

#ifdef DEBUG
    virtual void display(std::ostream& os) const;
#endif

    std::string _user;
    std::string _password;
    std::string _qualif;
    std::string _key;
    std::string _newPassword;
    std::string _role;
};

#endif

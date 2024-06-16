//f
//----------------------------------------------------------------------
//     FILE        : MsgReject.h
//
//     DESCRIPTION : 
//                   
//     AUTHOR      : chris
//     DATE        : 29/01/01
//----------------------------------------------------------------------
//f

#ifndef _MsgReject_h
#define _MsgReject_h

#include "Msg.h"

class MsgReject : public Msg
{
public:
  // Constructors
  MsgReject() : _nbError( 0L ) {}
  virtual ~MsgReject() {}
  MsgReject(const MsgReject& reject) { operator=( reject ); }

  // Operators
  MsgReject & operator=(const MsgReject&);

#ifdef ADHESION
    void encode(WErr_Type & out); 
#endif
  
  
  // Attributes
  long       _nbError;	// Error number
  std::string  _text;     // Error text (only for debug purpose)

  void       setNbError( long value ) { _nbError = value; }
  void       setText( const std::string & value ) { _text = value ; }
  void	     set(long aErr, const std::string& aText) { _nbError = aErr; _text = aText; }

        long       get_NbError();
  const std::string& get_Error();
#ifdef DEBUG
    virtual void    display( std::ostream & os ) const;
#endif
};

#endif    // _MsgReject_h

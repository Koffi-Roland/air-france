////////////////////////////////////////////////////////////////////////////////
//     FILE NAME   : ToolsMetCom.C
//
//     DESCRIPTION : Common tools implementation.
//
//     DATE        : 
////////////////////////////////////////////////////////////////////////////////
//f
#ifndef ToolsMetCom_h
#define ToolsMetCom_h 1

// Definitions
//class std::string;
//class RWDate;
//class RWTime;

// Class definition
class ToolsMetCom
{
public:
  // Tools for converting string into RWDate and RWTime
  static struct tm ReferenceDate(const std::string &s);
  static time_t    ReferenceTime(const std::string &s);
};
// Class ToolsYMF 

#endif // ToolsMetCom_h


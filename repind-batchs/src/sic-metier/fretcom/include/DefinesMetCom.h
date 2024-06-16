////////////////////////////////////////////////////////////////////////////////
//     FILE NAME   : DefinesMetCom.h
//
//     DESCRIPTION : Useful general definitions for METCOM
//
//     DATE        : 
////////////////////////////////////////////////////////////////////////////////

#ifndef DefinesMetCom_h 
#define DefinesMetCom_h    1

//#include <rw/defs.h>
//#include "DebugTools.h"
//#include "MemoryMgr.h
#define SOH 0x01
#define STX 0x02
#define EOM 0x03
#define LF  0x0a
#define CR  0x0d

#define __SEQUENCE_REFERENCE__ "SEQ_REFERENCE"

#ifdef DEBUG
#define MSG_TRACE_LVL1 "01"
#define MSG_TRACE_LVL2 "  02"
#define MSG_TRACE_LVL3 "    03"
#define MSG_TRACE_LVL4 "      04"
#define MSG_TRACE_LVL5 "        05"
#define MSG_TRACE_LVL6 "          06"
#define MSG_TRACE_LVLU "              "
#endif

#endif // DefinesMetCom_h

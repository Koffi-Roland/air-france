#ifndef _TIMER_H_
#define _TIMER_H_

#include <unistd.h>
#include <signal.h>
#include <iostream>
#include <stdlib.h>
#include <vector>

extern "C" { 
  typedef void(*ExternCFunction)(int); 
}; 

typedef void(*CFunction)(int); 


const int TimerDefaultValue = 30;

class Timer {

  std::vector<pid_t> _pids;
  ExternCFunction _handler;
  int _timeout;

public:
  enum ActionOnAlarm {
    ExitOnAlarm, ContinueOnAlarm
  };

public:

  Timer() {}

  Timer( ActionOnAlarm aAction, int timeout=TimerDefaultValue );
  
  Timer( int timeout, void (*handle)(int) );

  ~Timer();

  void insert( pid_t aPid );
  
  const std::vector<pid_t>& getPids() const;

  void terminate( pid_t aPid=0 ) ;

  void setAction( ActionOnAlarm aAction=ExitOnAlarm, int timeout=TimerDefaultValue );

  // if timeout = 0, desactivate the timer
  void setTime( int timeout=TimerDefaultValue );


  void setTimeout(int timeout)
  {
    _timeout = timeout;
  }

  int getTimeout() { return _timeout; }


  void setHandler(ExternCFunction handler)
  {
    _handler = handler;
  }


  ExternCFunction getHandler() { return _handler; }


  void activate();

  void inActivate() {
    alarm( 0 );
  }

  int initTimer( void (*handle)(int) ) ;

  static void sigalarmExit( int sig );

  static void sigalarmContinue( int sig );
  
};

#endif

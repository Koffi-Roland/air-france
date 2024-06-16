#include "Timer.h"

#include "DebugTools.h"



Timer::Timer( ActionOnAlarm aAction, int timeout ) {
    setAction( aAction, timeout );
}



Timer::Timer( int timeout, void (*handle)(int) ) {
    setTimeout( timeout );
    initTimer( handle );
    if ( timeout ) alarm( timeout );
}


Timer::~Timer() { _pids.clear(); alarm( 0 ); }

std::vector<pid_t>::iterator it;
void Timer::insert( pid_t aPid ) {it = _pids.begin();  _pids.insert(it, aPid ); }


const std::vector<pid_t>& Timer::getPids() const { return _pids; }



void Timer::terminate( pid_t aPid ) {
//pel  for (int i=0;i<_pids.length();i++) {
//pel    if ( aPid == 0 || _pids[i] == aPid )
//pel      sigsend( P_PID, _pids[i], SIGALRM );
//pel  }
signal(SIGTERM, getHandler()); //pel
}



void Timer::setAction( ActionOnAlarm aAction, int timeout ) {
  initTimer( aAction == ExitOnAlarm ? sigalarmExit : sigalarmContinue );
  setTime( timeout );
}


// if timeout = 0, desactivate the timer
void Timer::setTime( int timeout ) {
  setTimeout( timeout );
  alarm( timeout );
}


void Timer::activate() {
  signal( SIGALRM, getHandler() );
  alarm( getTimeout() );
}


int Timer::initTimer( void (*handle)(int) ) {
  setHandler( (handle == NULL) ? (ExternCFunction) sigalarmContinue : (ExternCFunction) handle );
  signal( SIGALRM, getHandler() );
  /*
    if ( handle == NULL ) 
    signal( SIGALRM, (ExternCFunction) sigalarmContinue );
    else 
    signal( SIGALRM, (ExternCFunction) handle );
  */
  return 0;
}



void Timer::sigalarmExit( int sig )  {
  alarm( 0 );
  TRACEMSG("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
  TRACEMSG("Sigalarm sig = " << sig << " --> Exit");
  TRACEMSG("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
  TRACEMSG("End Process Child : " << getpid());
  exit( 0 );       
} 



void Timer::sigalarmContinue( int sig )  {
  alarm( 0 );
  TRACEMSG("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
  TRACEMSG("sigalarm sig = " << sig << " --> Continue");
  TRACEMSG("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
  TRACEMSG("Process continue : " << getpid());
} 




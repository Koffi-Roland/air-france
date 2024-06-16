#ifndef ExceptionMETIER_h
#define ExceptionMETIER_h

#include "BasicException.h"

const ExceptionModule	kExModule_METIER	= 'MET ';
const ExceptionCode	kExBeginCodeMetier	= 100;

// ###WARNING### : VERY IMPORTANT, NEW EXCEPTIONS MUST BE PUT AT THE END !
//	           NO INSERTION OR MOVE IS ALLOWED !

enum
  {
    ExMETIER_Unknow = kExBeginCodeMetier,
    ExMET_AllotmentIdentification,
    ExMET_CluIdentification,
    ExMET_LtaIdentification,
    ExMET_SegmentIdentification,
    ExMET_Transaction,
    ExMET_Routing,
    ExMET_CustomerIdentification,
    ExMET_GoodsDescription,
    ExMET_CommercialCodes,
    ExMET_SplCode,
    ExMET_ArrivingFlightAndDateOfDelivery,
    ExMET_Receipt,
    ExMET_Remarq,
    ExMET_YieldError,
    ExMET_YieldSegmentError,
    ExMET_SegmentCapacite,
    ExMET_SegmentReceipt,
    ExMET_Teletype,
    ExMET_OhgCut,
    ExMET_ParcelDimension,
    ExMET_FreeText,
    ExMET_AllotmentArrivingOrConnectingFlight,
    ExMET_SalesManager
  };

#define CATCH_MET                       catch(ExceptionMET& EXCEPTION)
#define IS_ERROR_MET()                  (EXCEPTION.GetExceptionModule() == kExModule_METIER)

#ifdef __EXCEPTION_DEBUG__
#define FailMET(type,err)               throw ExceptionMET((type), (err), #err, __FILE__ , __LINE__)
#define RejectMET(type,err,reject)      throw ExceptionMET((type), (err), #err, __FILE__ , __LINE__, (reject))
#else
#define FailMET(type,err)               throw ExceptionMET((type), (err))
#define RejectMET(type,err,reject)      throw ExceptionMET((type), (err), (reject))
#endif
class ExceptionMET : public BasicException
{
public:
  ExceptionMET() : BasicException() {}
  ExceptionMET(ExceptionType aType, ExceptionCode aCode) 
    : BasicException(kExModule_METIER, aType, aCode) {}
  ExceptionMET(const ExceptionMET& aException) { operator=(aException); }
  
#ifdef __EXCEPTION_DEBUG__
  ExceptionMET(ExceptionType aType, ExceptionCode aCode,
	       std::string aErrLib, std::string aFile, long aLine, ExceptionRejectType aReject = kExReject_System)
    : BasicException(kExModule_METIER, aType, aCode, aErrLib, aFile, aLine, aReject) {}
#endif
  
  ExceptionMET& operator=(const ExceptionMET& aException)
  {
    BasicException::operator=(aException); return *this;
  }
};

#endif // ExceptionMETIER_h

#include "WrapperTestData.h"


INIT_WRAPPER(WrapperTestData)
  INIT(Comm, "COMM");
  INIT(Name, "cof_name");
END_WRAPPER

INIT_WRAPPER_FROM(WrapperTestData1, WrapperTestData)
  INIT(Key, "iseq");
  INIT(DateFin, "dfin");
END_WRAPPER

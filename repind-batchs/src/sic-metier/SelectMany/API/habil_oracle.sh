#!/bin/bash

# !/bin/sh : does not know local

# habil_oracle.sh

nb_params=$#
if [ ${nb_params} -ne 2 ] ; then
  echo "${nb_params} parametres"
  echo "usage : $0 <oracle_version> <back_script>"
  exit -1
fi

oracle_version=$1
back_script=$2
echo "${oracle_version}"
cat /dev/null > ${back_script}
# echo "file '${back_script}'"
if [ ! -f ${back_script} ] ; then
  echo "Can not create file '${back_script}'"
  exit -1
fi

if [ "${oracle_version}" = "8" ] ; then
  ora_whole_vers=8.1.7
  # echo "oracle version set to '${ora_whole_vers}'"
elif [ "${oracle_version}" = "9" ] ; then
  ora_whole_vers=9.2.0
  # echo "oracle version set to '${ora_whole_vers}'"
elif [ "${oracle_version}" = "10" ] ; then
  ora_whole_vers=10.0.0
  # echo "oracle version set to '${ora_whole_vers}'"
else
  echo "Oracle_version not recognized; expected : 8, 9 or 10"
  exit -1
fi

set_env()
{
  local var=$1;
  local val=$2;
  local env=$3;

#  echo
  echo "Variable '${var}'"
  echo "Value '${val}'"
  echo "Env '${env}'"

  if [ "-${val}-" = "--" ] ; then
    echo "unset ${var}" >> ${back_script}
    echo "unset ${var} in script - export ${var}=${env}"
    export ${var}=${env}
    global_return=0
  else
    local res=`echo ${val} | grep -i oracle`
    local res1=`echo ${res} | grep -iv ${ora_whole_vers}`
    echo "res '${res}' and res1 '${res1}'"
    if [ "-${res}-" = "--" ] ; then
      echo "export ${var}=${val}" >> ${back_script}
      echo "export ${var}=${val} in script - export ${var}=${env}"
      export ${var}=${env}
      global_return=0
    else
      # ${var} contains the string "oracle"
      if [ "-${res1}-" = "--" ] ; then
        # ${var} contains the required version : nothing to do
        echo "The version '${ora_whole_vers}' is already set"
	global_return=0
      else
        echo "Oracle already set to another version, cannot do anything for you !"
        global_return=1
        global_return=0
      fi
    fi
  fi
#  echo "********************************"
}

# set_env envVarName envVarCurrentValue envVarNextValue
# the function sets the new value to the variable
# and write in the ${back_script} file the commande to go back
# to the previous environnement
global_return=0

set_env "ORACLE_HOME" "${ORACLE_HOME}" "/exploit/ORACLE/server/${ora_whole_vers}"
if [ "${global_return}" = "1" ] ; then
  exit -1 ;
fi

set_env "ORACLE_LIB" "${ORACLE_LIB}" "/exploit/ORACLE/server/${ora_whole_vers}/lib"
echo "oracle home '${ORACLE_LIB}'"
if [ "${global_return}" = "1" ] ; then
  exit -1 ;
fi

set_env "ORACLE_PATH" "${ORACLE_PATH}" "/exploit/ORACLE/server/${ora_whole_vers}/bin"
if [ "${global_return}" = "1" ] ; then
  exit -1 ;
fi

set_env "PATH" "${PATH}" "${PATH}:/exploit/ORACLE/server/${ora_whole_vers}/bin"
# set_env "PATH" "${PATH}" "${PATH}:${ORACLE_PATH}"
if [ "${global_return}" = "1" ] ; then
  exit -1 ;
fi

set_env "LD_LIBRARY_PATH" "${LD_LIBRARY_PATH}" \
        ""/exploit/oracle/client/12102/lib:${LD_LIBRARY_PATH}"
# set_env "LD_LIBRARY_PATH" "${LD_LIBRARY_PATH}" "${ORACLE_LIB}:${LD_LIBRARY_PATH}"
if [ "${global_return}" = "1" ] ; then
  exit -1 ;
fi

set_env "LD_RUN_PATH" "${LD_RUN_PATH}" \
        "${LD_RUN_PATH}:"/exploit/oracle/client/12102/lib"
# set_env "PATH" "${PATH}" "${PATH}:${ORACLE_PATH}"
if [ "${global_return}" = "1" ] ; then
  exit -1 ;
fi

# echo "************"
# cat ${back_script}
# echo "************"

chmod u+x ${back_script}

# echo "******** END**********"
# echo

# DO NOT finish with 
# exit 0
# or you will have your session closed !


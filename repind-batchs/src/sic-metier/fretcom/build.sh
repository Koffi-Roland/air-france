#!/bin/bash
export TOOLS_REF_PUBLIC=../fretcom_outils_public
export EDIFACT_HOME=/exploit/edifact/prod/bin
export ROOT_MAKEFILE_NOYAU=/app/REPIND/tools/Template
export ORACLE_HOME=/exploit/oracle/client/12102
export LD_LIBRARY_PATH=/exploit/oracle/client/12102:/exploit/oracle/client/12102/lib
export OS=Linux
make -j 2

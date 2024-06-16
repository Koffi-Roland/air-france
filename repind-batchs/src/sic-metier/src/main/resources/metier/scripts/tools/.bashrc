############################
### GENERAL BASH UTILITY ###
############################

alias ls='ls --color=auto'
alias ll='ls -l'

export HISTCONTROL=ignoredups

# ENVIRONMENT Directory
FS_DIR=${fs.directory}
hostType=${host.type}
export FS_DIR
export hostType

# Application name
APPLICATION=${app.name}
export APPLICATION

# Home directory
APPLICATION_RACINE=/app/$FS_DIR
export APPLICATION_RACINE

# override perl binary with one compatible with the libary DBI.pm for GENERIC REPLICATION "rg.pl"
PATH=/tech/oracle/client/12102/perl/bin/:$PATH
# java
PATH=$PATH:/tech/java/oracle/jdk-1.8.0_162/bin/
# sqlplus
PATH=$PATH:/tech/oracle/client/12102/bin/
# xfiles
PATH=$PATH:/tech/xfb/
# secureSIC
PATH=$PATH:$APPLICATION_RACINE/tools/secureSIC
export PATH
# Oracle
ORACLE_HOME=/tech/oracle/client/12102
export ORACLE_HOME

LD_LIBRARY_PATH=$ORACLE_HOME/lib:$APPLICATION_RACINE/tools/rw/2018/lib:$APPLICATION_RACINE/prod/bin/cpp/lib
export LD_LIBRARY_PATH

export PERL5LIB=/tech/oracle/client/12102/perl/lib:/tech/oracle/client/12102/perl/lib/site_perl/5.14.1/x86_64-linux-thread-multi

hostName=`hostname`

FRT_APL_SYS=DEV

SECURE_DATABASE_ACCESS_FILE_SIC=$APPLICATION_RACINE/tools/secureSIC/sicsec.${sicsec.db.name}

export SECURE_DATABASE_ACCESS_FILE_SIC

export TWO_TASK=$(secureSIC $SECURE_DATABASE_ACCESS_FILE_SIC READSIC | cut -d @ -f 2)

# TNSNAME.ORA path
export TNS_ADMIN=/var/opt/oracle/

# HISTORICAL PATH ACCES FOR XFILES
export XFB=/tech/xfb/

#Java
export JAVA_HOME=/tech/java/oracle/jdk-1.8.0_162/

export BASE_DIR=$APPLICATION_RACINE
export BASE_EXE_DIR=$BASE_DIR/prod/bin/scripts/EXE
export BASE_LOG_DIR=$BASE_DIR/logs
export BASE_DATA_DIR=$BASE_DIR/data
export BASE_FTP_DIR=$BASE_DIR/ftp
export BASE_PERL_DIR=$BASE_DIR/prod/bin/scripts/PERL
export BASE_SQL_DIR=$BASE_DIR/prod/bin/scripts/SQL
export BASE_TOOL_DIR=$BASE_DIR/prod/bin/scripts/tools
export BASE_EXE_CPP_DIR=$BASE_DIR/prod/bin/cpp
export BASE_EXE_JAVA_DIR=$BASE_DIR/prod/bin/java
export BASE_DESC_DIR=$BASE_DATA_DIR/DESC
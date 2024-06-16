#!/bin/bash

#Positionnement des variables de la VM

JAVA_HOME=/tech/java/openjdk/1.17
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.airfrance.batch.cleanContactData
CLASSPATH_RPD=${JAVA_RPD}/lib/*
memory=1024m

fatal_error()
{
	echo "Erreur fatale :" $*
	exit 3
}

execute() {
  echo $*
  $*
}


# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"


cd $JAVA_RPD
# Lancer / relancer process
execute ${JAVA_HOME}/bin/java  ${JAVA17_ARG} -Djava.security.krb5.kdc=$KDCSERVER -Djava.security.krb5.realm=$REALMENV \
                               -Djava.security.auth.login.config=$JAASCONF -Dsun.security.krb5.debug=false -Dzookeeper.sasl.client=false \
                               -Dbatch=BatchCleanContactData -server -Xmx${memory} \
                               -cp ${CLASSPATH_RPD}:${JAVA_RPD}/sic-metier-root-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchCleanContactData $*

exit 0

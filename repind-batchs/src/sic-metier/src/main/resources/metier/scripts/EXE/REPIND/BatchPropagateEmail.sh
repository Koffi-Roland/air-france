#!/bin/bash

# Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_RPD=/app/REPIND/prod/bin/tcsic/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
BATCH_PKG=com.airfrance.batch.propagate.email
CLASSPATH_SIC=${JAVA_RPD}/lib/*
memory=8192m

# Read the last offset value from the file or use 0 if the file doesn't exist
if [ -f /app/REPIND/travail/Loha/offset.txt ]; then
  offset=$(cat /app/REPIND/travail/Loha/offset.txt)
else
  offset=0
fi

execute()
{
  echo $*
  $*
}

# Verification de la presence de java
[ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

cd $JAVA_RPD

# Lancer / relancer process
execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Dbatch=BatchPropagateEmail -server -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_RPD}/sic-metier-root-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchPropagateEmail $offset

# Increment the offset value by 100000
offset=$((offset+600000))

# Write the new offset value to the file
echo $offset > /app/REPIND/travail/Loha/offset.txt

exit 0

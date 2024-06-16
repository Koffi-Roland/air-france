#!/bin/bash

#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA_TCSIC=/app/REPIND/prod/bin/tcsic/java
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
JAVA_RPD=/app/REPIND/prod/bin/java
BATCH_PKG=com.afklm.batch.cleanupcreditcards
CLASSPATH_SIC=${JAVA_RPD}/lib/*
memory=8192m

# Read the last offset value from the file or use 0 if the file doesn't exist
if [ -f /app/REPIND/travail/Mjadar/offset-cards.txt ]; then
  offset=$(cat /app/REPIND/travail/Mjadar/offset-cards.txt)
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
execute ${JAVA_HOME}/bin/java ${JAVA17_ARG} -Dbatch=BatchCleanupCreditCards -server -Xmx${memory} \
                                -cp ${CLASSPATH_SIC}:${JAVA_TCSIC}/cleanupcreditcards-0.0.1-SNAPSHOT.jar ${BATCH_PKG}.BatchCleanupCreditCards $offset

# Increment the offset value by 400000
offset=$((offset+400000))

# Write the new offset value to the file
echo $offset > /app/REPIND/travail/Mjadar/offset-cards.txt

exit 0

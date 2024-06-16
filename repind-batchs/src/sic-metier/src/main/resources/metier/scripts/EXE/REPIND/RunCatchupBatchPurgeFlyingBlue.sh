#!/bin/sh

###################################################################
# Shell de lancement des fichiers Catchup de la Purge Flying Blue
###################################################################

USAGE="Usage: $0 [-C] (->COMMIT_MODE))"

echo "Start Script Shell RunCatchupBatchPurgeFlyingBlue"

# . $APPLICATION_RACINE/prod/bin/scripts/tools/setenv.sh

# positionnement des variables propres au flux
WORK_DIR=$BASE_DATA_DIR/ISIS

if [ ! -d "$WORK_DIR" ]; then
        mkdir "$WORK_DIR"
fi
if [ ! -d "$WORK_DIR/BACKUP" ]; then
        mkdir "$WORK_DIR/BACKUP"
fi

dateTraitementYesterday=`/bin/date -d "yesterday" '+%d%m%Y'`

COMMIT_MODE="false";

# Parsing des options
while getopts ":C" opt;
do
        case $opt in
                C)      COMMIT_MODE="true";;
                [?])    echo $USAGE
                        exit 1;;
        esac
done

# call parameters java batch
args=""
if test "$COMMIT_MODE" = "true"
then
        args="$args -C"
fi

CATCHUP_FILE=FBPurge_CatchUp_$dateTraitementYesterday
TRACE_FILE=$WORK_DIR/$CATCHUP_FILE"_"$dateTraitementYesterday.trace

echo `/bin/date '+%Y-%m-%d %H:%M:%S'`" - Debut du script: " >>$TRACE_FILE
echo `/bin/date '+%Y-%m-%d %H:%M:%S'`" - COMMIT_MODE ["$COMMIT_MODE"]" >>$TRACE_FILE

# recupere la liste des fichiers transmis

lstFiles=`ls $WORK_DIR/$CATCHUP_FILE"-"* 2>>/dev/null`

if test $? -ne 0
then
        echo "Aucun fichier a traiter sur $WORK_DIR"
        echo `/bin/date '+%Y-%m-%d %H:%M:%S'`" - Aucun fichier a traiter" >>$TRACE_FILE
        exit 2
fi

#Positionnement des variables de la VM
JAVA_HOME=/tech/java/openjdk/1.17
JAVA17_ARG="--add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.management/javax.management.openmbean=ALL-UNNAMED --add-opens=java.management/javax.management=ALL-UNNAMED --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
JAVA_SIC=/app/REPIND/prod/bin/java
CLASSPATH_SIC=${JAVA_SIC}/lib/*
BATCH_PKG=com.airfrance.jraf.batch.contract
memory=1024m

fatal_error()
{
        echo "Erreur fatale :" $*
        exit 3
}

execute()
{
        echo $*
        $*
}

for myFile in $lstFiles; do
        echo "Traitement du fichier:" $myFile
        echo `/bin/date '+%Y-%m-%d %H:%M:%S'`" - Traitement du fichier:" $myFile >>$TRACE_FILE

        params="-f $myFile $args"
        heureTraitement=`/bin/date '+%Y%m%d%H%M%S'`

        cd $JAVA_SIC

        echo `/bin/date '+%Y-%m-%d %H:%M:%S'`" - Debut du RunCatchupBatchPurgeFlyingBlue avec params:" $params >>$TRACE_FILE

        # Verification de la presence de java
        [ -f ${JAVA_HOME}/bin/java ] || fatal_error "Runtime java absent ${JAVA_HOME}/bin/java"

        # Lancement de la commande
        # Le nom de la commande est immediatement suivi de l'option -Dbatch= pour permettre sa recherche
        # dans le script stopBatch : unix limite le nombre de caracteres lors d'une recherche a 80 caracteres

        execute ${JAVA_HOME}/bin/java -Dbatch=BatchPurgeFlyingBlue ${JAVA17_ARG} -server -Xmx${memory} -cp ${CLASSPATH_SIC}:${JAVA_SIC}/Myaccount-batch-0.1.0-SNAPSHOT.jar ${BATCH_PKG}.BatchPurgeFlyingBlue $params

        echo `/bin/date '+%Y-%m-%d %H:%M:%S'`" - Fin du RunCatchupBatchPurgeFlyingBlue" >>$TRACE_FILE

        echo `/bin/date '+%Y-%m-%d %H:%M:%S'`" - Backup du fichier d'entree" $myFile "vers" $WORK_DIR"/BACKUP/" >>$TRACE_FILE
        mv $myFile $WORK_DIR/BACKUP/. 2>>$TRACE_FILE

        myFileBaseName=`basename $myFile`

        echo `/bin/date '+%Y-%m-%d %H:%M:%S'`" - Compression du fichier de backup" $WORK_DIR/BACKUP/$myFileBaseName >>$TRACE_FILE
        gzip -f $WORK_DIR/BACKUP/$myFileBaseName 2>>$TRACE_FILE

done

echo `/bin/date '+%Y-%m-%d %H:%M:%S'`" - Fin du script."  >>$TRACE_FILE
echo "####################################################################################################################################" >>$TRACE_FILE

echo "End Script Shell RunCatchupBatchPurgeFlyingBlue"
###############################################################################

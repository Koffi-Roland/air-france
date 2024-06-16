#!/bin/bash 
execute() 
{ 
	echo $* 
	$* 
} 

if [ $# -eq 0 ]
  then
    echo "No arguments supplied"
fi

event_type="-f"

# Deleted old previous logs
execute find $BASE_DATA_DIR/EVENT_NOTIFICATIONS -type f -mtime +10 -exec rm -f {} \;

if [ "$event_type" != "-f" ]
then
	echo "not acceptable argument"
	exit 1
fi

# Boucle pour detecter le moment ou le process est en stand by et l'arrêter
while [ ! -f $BASE_DATA_DIR/events$event_type ]
do
	pid=$(ps -ea -o "pid,args" | grep "Dbatch=BatchFirmAgencyUpdateNotification" | grep -v grep | awk '{print $1}')
    for i in $pid;
    do
        echo "killing process $i"
        kill $i
    done

	touch $BASE_DATA_DIR/events$event_type
done

rm -f $BASE_DATA_DIR/events$event_type 

execute $BASE_EXE_DIR/MY_ACCOUNT/runPurgeTriggerChange.sh $event_type
execute $BASE_EXE_DIR/FIRMES/BatchFirmAgencyUpdateNotification.sh &

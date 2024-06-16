#!/bin/sh

service=$(echo "$1" | tr '[:lower:]' '[:upper:]')
nbImageKeep=3
file=tags.txt
formatFile=format-tags.txt
outputfile=$2
fileITE=$3
fileCAE=$4
filePROD=$5


# Retrieve tags for an service
git tag --sort=-committerdate -l $service-[0-9]* > debug-file.txt

# Remove the X first line (keep these images)
sed -e "1,${nbImageKeep}d" debug-file.txt |tee $file

REGEX_VERSION='[0-9]*.[0-9]*.[0-9]*$'
REGEX_TAG='^[a-z-]*'

# Tags format
rm $formatFile
touch $formatFile
cat $file | while read line 
do
    NAME=$(echo "$line" | tr '[:upper:]' '[:lower:]')
    NAME=$(echo "$NAME" | grep -o $REGEX_TAG | sed 's/.$//')
    echo $NAME
    VERSION=$(echo "$line" | grep -o $REGEX_VERSION)
    echo $VERSION

    FULL_TAG="$NAME:$VERSION"
    echo $FULL_TAG

    # Check if he is used in one cluster (CAE, ITE, PROD)
    if grep -v -q $FULL_TAG "$fileCAE" && grep -v -q $FULL_TAG "$fileITE" && grep -v -q $FULL_TAG "$filePROD"; then
        echo -n "$VERSION," >> $formatFile
    fi
done

cat $formatFile | tr '\n' ',' | sed 's/.$//' |tee $formatFile

result=$(cat $formatFile)
if [ -z "$result" ]
then
    echo "oldTags=EMPTY" > $outputfile
else
    echo "oldTags=$result" > $outputfile
fi

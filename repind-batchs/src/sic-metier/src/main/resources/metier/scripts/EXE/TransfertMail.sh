#!/bin/bash

if [ $# -eq 1 ] ;
then
echo "Usage : $0 fichier email_1 [email_2 ... email_N] [subject]"
exit 1
fi

DIR_DATA=$BASE_DATA_DIR/TRANSMAIL

_Fichier=$1
_Email=$2
_Subject=$3

FILE_TEMP=$DIR_DATA/$(basename "$_Fichier").$$.temp
FILE_TRANSF=$DIR_DATA/$(basename "$_Fichier").$$.transf
if [ "$_Subject" ] ;
then
  echo "mailx -s '$_Subject' $_Email << ENDMAIL" > $FILE_TEMP
else
  echo "mailx $_Email << ENDMAIL" > $FILE_TEMP
fi

cat "$FILE_TEMP" "$_Fichier" > "$FILE_TRANSF"
echo "ENDMAIL" >> "$FILE_TRANSF"

# Envoie Message
chmod +x "$FILE_TRANSF"
$FILE_TRANSF

# Purge fichiers temporaires
\rm "$FILE_TEMP" "$FILE_TRANSF"

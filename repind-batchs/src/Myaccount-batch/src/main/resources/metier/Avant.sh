#!/bin/sh
# Script used to move and classify script files
# in appropriate folder for delivery

if [ -f ~/livraison/bin/sic/scripts/tools/Avant_Sic.sh ]
then
	echo "Executing Avant Sic sh"
	~/livraison/bin/sic/scripts/tools/Avant_Sic.sh
else
	echo "No Avant Sic sh found"
fi

if [ -f ~/livraison/bin/tcsic/scripts/tools/Avant_Tcsic.sh ]
then
	echo "Executing Avant Tcsic sh"
	~/livraison/bin/tcsic/scripts/tools/Avant_Tcsic.sh 
else
	echo "No Avant Tcsic sh found"
fi

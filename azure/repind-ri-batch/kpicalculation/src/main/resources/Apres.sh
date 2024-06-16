#!/bin/sh
# Script used to create work folder

if [ -f ~/prod/bin/scripts/tools/Apres_Sic.sh ]
then
	echo "Executing Apres Sic sh"
	~/prod/bin/scripts/tools/Apres_Sic.sh
else
	echo "No Apres Sic sh found"
fi

if [ -f ~/prod/bin/scripts/tools/Apres_Tcsic.sh ]
then
	echo "Executing Apres Tcsic sh"
	~/prod/bin/scripts/tools/Apres_Tcsic.sh 
else
	echo "No Apres Tcsic sh found"
fi

if [ -f ~/prod/bin/scripts/tools/Apres_cpp.sh ]
then
	echo "Executing Apres cpp sh"
	~/prod/bin/scripts/tools/Apres_cpp.sh 
else
	echo "No Apres cpp sh found"
fi

if [ -f ~/prod/bin/biz/scripts/tools/Apres_Biz.sh ]; then
  echo "Executing Apres Biz sh"
  ~/prod/bin/biz/scripts/tools/Apres_Biz.sh
else
  echo "No Apres Biz sh found"
fi
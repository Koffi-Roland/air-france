#!/bin/sh
#
# Utilitaire permettant de completer toutes les lignes d'un fichier 
# a une taille fixe passee en parametre.
#
# Attention : cet utilitaire ne coupe pas les lignes si elles sont trops longues
# Le fichier en entre doit donc avoir toutes ces lignes avec un nombre de caracteres
# inferieur a la taille passee en parametre.
#

if [ $# -ne 2 ]
then
  echo "FormatLigne <file name> <taille fixe>"
  exit 1
fi

if test -r $1
then
	mv $1 $1.back_
	cat $1.back_ | nawk -v KK=$2 ' BEGIN {}
	{
  		format=sprintf("%%#-%ss\n",KK);
  		printf(format,$0); 
	}' > $1 

	if test -s $1
	then
		rm $1.back_
	fi
fi


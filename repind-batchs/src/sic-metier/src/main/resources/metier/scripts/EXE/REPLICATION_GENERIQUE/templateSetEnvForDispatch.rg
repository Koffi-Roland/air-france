#!/bin/sh

# Nom du repertoire dans lequel se trouve les fichiers de donnees d'origine
# Generalement le numero du process PID.
# C'est le repertoire d'extraction des donnees par le binaire.
# Si le chemin est relatif (debut != /), celui-ci sera calcule par
# rapport a $REP_ENCOURS
RG_DISPATCH_PID=PPPPPP
export RG_DISPATCH_PID


# Path du repertoire qui servira a l'archivage des fichiers dispatches.
# Pour etre conforme a la specification il faut que le nom du
# repertoire et le nom du fichier soit les memes.
# Si le chemin est relatif (debut != /), celui-ci sera calcule par
# rapport a $REP_DATA
RG_DISPATCH_DIRNAME=DDDDDD
export RG_DISPATCH_DIRNAME


# Racine des noms de fichiers de donnees d'origine.
# Cette racine servira aussi a generer les noms des fichiers dispatches.
# Il ne faut pas utiliser le caractere '.' dans le nom.
RG_DISPATCH_FILENAME=FFFFFF
export RG_DISPATCH_FILENAME

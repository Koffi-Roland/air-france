#!/bin/sh
#
#     Scritp de Mise en forme de Fichiers Generique
#  Author : Mori Stephane  11/09/2003
#    Usage : FormatFichierReplicGen.sh <Nom du fichier> <Code traite> <taille champ 1> <taille champ 2> ...
#
#  Controles effectues :
#    - suppression des lignes ne commencant pas par le code a traiter
#    - transformation du caractere ':' en '~' avant traitement transformation inverse en fin
#    -

# Controle et Usage
###################
if [ $# = 1 ]
then
  echo "$0 <file name> <taille champ fixe> <taille champ fixe> <taille champ fixe>  ...."
  exit 1
fi

# Recuperaion paramettres
#########################
FileName=$1
shift 1
Code=$1
shift 1
EditFile="cat ${FileName}"


# Init Variables
################
Spaces=" "
MaxLength=1
# Line size = 5 : 1 pour le cr et 4 pour le code qui ne rentre pas en compte
LineSize=5
FieldNum=0

for i in $*
do
  #CheckFieldNum="${CheckFieldNum}\([^:]*\):#:"
  LineSize=` expr $LineSize + $i `
  FieldNum=` expr $FieldNum + 1 `
  while [ ` expr $i \> $MaxLength ` -ne 0 ]
  do
    Spaces="${Spaces} "
    MaxLength=` expr $MaxLength + 1 `
  done
done

# Traces avant traitement
#########################
echo "Taille de la ligne : $LineSize"
echo "Taille Maximum d'un champ : $MaxLength"
#echo "#$Spaces#"
echo "Params : $*"
echo "Code : $Code"
echo "FieldNum : $FieldNum"


# construction de la commande
#############################
echo "$EditFile | sed -n '/^${Code}/ p' | sed  \\" > ${FileName}.exe
# pre-controles sur le fichier et mise en forme
# ajout separateur de fin pour eviter les effets de bord
# transformation des ':' isoles en '~'
echo " -e 's/$/:#: /' -e 's/:#:/${Spaces}###/g' -e s/:/~/g  -e 's/###/:#:/g' \\" >> ${FileName}.exe
echo " -e 's/^${Code}\ *:#:\(.*\)/${Code}:#:\1/' \\" >> ${FileName}.exe
TruncatRegulExpr=" -e 's/^\([^:]*\):#:\(.\{Field_Size\}\)\([^:]*\):#:/\1\2:#:/' \\"
#TruncatRegulExpr=" -e 's/^\([^\a:#:a]*\):#:\(.\{Field_Size\}\)\([^\a:#:a]*\):#:\(.*\)$/\1\2:#:\4/' \\"

echo $Commande

# creation de l'expression reguliere representant la ligne
fieldNumb=0
for i in $*
do
  echo ${TruncatRegulExpr} | sed s/Field_Size/${i}/ >> ${FileName}.exe
done

# post corrections
# suppression des fins de ligne au dela du dernier separateur traite
# reaffectation des ':'
echo " -e 's/:#:.*$//' -e s/~/:/g > ${FileName}.fixe" >> ${FileName}.exe

sed 's/ *$//' ${FileName}.exe >> ${FileName}.exe.tmp
mv ${FileName}.exe.tmp ${FileName}.exe
chmod +x ${FileName}.exe
${FileName}.exe

# Controle du fichier a posteriori
##################################

WC=`wc ${FileName}.fixe`
NbLines=`echo $WC | cut -d' ' -f 1 `
FileSize=`echo $WC | cut -d' ' -f 3 `

if [ ${NbLines} != 0 ]
then
    LineSizeCalc=` expr ${FileSize} / ${NbLines} `
    echo "LineSizeCalc : $LineSizeCalc"
    if [ $LineSizeCalc != $LineSize ]
    then
    echo " GROSSE ERREUR ....."
    else
    echo "Traitement Correct"
    fi
else
    echo "Pas de changement : Traitement Correct"
fi

\rm ${FileName}.exe


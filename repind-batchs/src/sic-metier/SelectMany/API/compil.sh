# compil.sh

echo
echo "=================="
echo "Forte 6.1 compiler"
echo "=================="
echo

echo
echo "************************"
echo "Compilation Oracle 8.1.7"
echo "************************"
echo
. ./habil_oracle.sh 8 env8.sh ; make api6.1ora8clean api6.1ora8 ; . ./env8.sh
\rm -f env8.sh

echo
echo "************************"
echo "Compilation Oracle 9.2.0"
echo "************************"
echo
. ./habil_oracle.sh 9 env9.sh ; make api6.1ora9clean api6.1ora9 ; . ./env9.sh
\rm -f env9.sh

# echo "Compilation Oracle 10g"
# . ./habil_oracle.sh 8 env8.sh ; make clean api8 ; . ./env8.sh
# rm env8.sh

echo
echo "==================="
echo "SunOne 8.0 compiler"
echo "==================="
echo

echo
echo "************************"
echo "Compilation Oracle 8.1.7"
echo "************************"
echo
. ./habil_oracle.sh 8 env8.sh ; make api8.0ora8clean api8.0ora8 ; . ./env8.sh
\rm -f env8.sh

echo
echo "************************"
echo "Compilation Oracle 9.2.0"
echo "************************"
echo
. ./habil_oracle.sh 9 env9.sh ; make api8.0ora9clean api8.0ora9 ; . ./env9.sh
\rm -f env9.sh

# echo "Compilation Oracle 10g"
# . ./habil_oracle.sh 8 env8.sh ; make clean api8 ; . ./env8.sh
# rm env8.sh


echo
echo "END Compilation"

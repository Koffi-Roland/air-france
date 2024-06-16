#!/bin/sh
# Script used to move and classify script files
# in appropriate folder for delivery of tcsic project

# Define target project
project=${app.name}

# Define env
env=${hostType}

# Clean and Create work directory
rm -Rf ~/livraison/bin/scripts/EXE
rm -Rf ~/livraison/bin/scripts/SQL
rm -Rf ~/livraison/bin/scripts/PERL

mkdir -p ~/livraison/bin/scripts/EXE
mkdir -p ~/livraison/bin/scripts/SQL
mkdir -p ~/livraison/bin/scripts/PERL
mkdir -p ~/livraison/bin/scripts/tools

# Clean dependencies
if [[ $env != "dev" ]]
then
  rm -f ~/prod/bin/tcsic/java/lib/ref-commons-0.1.0-*.jar
  rm -f ~/prod/bin/tcsic/java/lib/sic-commons-0.1.0-*.jar
  rm -f ~/prod/bin/tcsic/java/lib/sic-entities-0.1.0-*.jar
  rm -f ~/prod/bin/tcsic/java/lib/sic-payment-preferences-commons-*.jar
  rm -f ~/prod/bin/tcsic/java/lib/sicutf8-commons-0.1.0-*.jar
  rm -f ~/prod/bin/tcsic/java/lib/tables_referencesJAVA-0.1.0-*.jar
fi

# Process for RAP delivery
if [[ $project == "RAP" ]]
then
	# EXE
	mv ~/livraison/bin/tcsic/scripts/EXE/BatchAlimentationBSP.sh ~/livraison/bin/scripts/EXE
	mv ~/livraison/bin/tcsic/scripts/EXE/AGENCES ~/livraison/bin/scripts/EXE
	mv ~/livraison/bin/tcsic/scripts/EXE/EXPLOIT ~/livraison/bin/scripts/EXE
	mv ~/livraison/bin/tcsic/scripts/EXE/MY_ACCOUNT ~/livraison/bin/scripts/EXE

	# SQL
	mv ~/livraison/bin/tcsic/scripts/SQL/AGENCES ~/livraison/bin/scripts/SQL
	mv ~/livraison/bin/tcsic/scripts/SQL/MY_ACCOUNT ~/livraison/bin/scripts/SQL
else
	# Process for REPIND delivery
	if [[ $project == "REPIND" ]]
	then
		# EXE
		mv ~/livraison/bin/tcsic/scripts/EXE/BatchMigrationKLSubscriptions.sh ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/FormatLigne.sh ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/TransfertMail.sh ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/ADR_POST ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/CARTEFI ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/CLEAN_MEDIA ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/CONTRACTS ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/COM_PREF ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/DOUBLONS ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/EXPLOIT ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/MEMBRE ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/MY_ACCOUNT ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/QUALIF_ADRESSES ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/REPLICATION ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/REPLICATION_GENERIQUE ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/TELECOMS ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/BatchDeleteDoctorContractInPID.sh ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/BatchUpdateMarketLanguage.sh ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/BatchCleanDuplicateUTFData.sh ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/BatchFixAfComPref.sh ~/livraison/bin/scripts/EXE
    mv ~/livraison/bin/tcsic/scripts/EXE/REPIND/*.sh ~/livraison/bin/scripts/EXE

		# PERL
		mv ~/livraison/bin/tcsic/scripts/PERL/* ~/livraison/bin/scripts/PERL

		# SQL
		mv ~/livraison/bin/tcsic/scripts/SQL/* ~/livraison/bin/scripts/SQL

		# JAVA
		cp ~/livraison/bin/scripts/EXE/TELECOMS/INVALIDATION/BatchInvalidationSMS.sh ~/livraison/bin/java/

	else 
		# Process for REPFIRM delivery
		if [[ $project == "REPFIRM" ]]
		then
			# EXE
			mv ~/livraison/bin/tcsic/scripts/EXE/EXPLOIT ~/livraison/bin/scripts/EXE
			mv ~/livraison/bin/tcsic/scripts/EXE/FIRMES ~/livraison/bin/scripts/EXE
			mv ~/livraison/bin/tcsic/scripts/EXE/MY_ACCOUNT ~/livraison/bin/scripts/EXE

			# SQL
			mv ~/livraison/bin/tcsic/scripts/SQL/MY_ACCOUNT ~/livraison/bin/scripts/SQL
		fi
	fi
fi

# Move profile file and bashrc
mv ~/livraison/bin/tcsic/scripts/tools/.bashrc ~/
mv ~/livraison/bin/tcsic/scripts/tools/.profile ~/

mv ~/livraison/bin/tcsic/scripts/tools/Apres_Tcsic.sh ~/livraison/bin/scripts/tools

# SecureSIC and rw
# mkdir -p ~/tools
# cp -r /tmp/REPIND/* ~/tools

rm -Rf ~/livraison/bin/tcsic/scripts

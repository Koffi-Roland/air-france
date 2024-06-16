#!/tech/oracle/client/12102/perl/bin/perl -w
use lib ;

use strict;
use Getopt::Long;
use File::Copy;

# script d'appel pour les envoi XFB de la replication Generique
# Modification pour SALTO
# CE SCRIPT DOIT ETRE APPELE AVEC LES PARAMETRES PROVENANT DU SCRIPT ManageReplicationSic.pl
# POUR ENVOYER LES BON FICHIER ( DELTA OU GLOBAUX)
# Ce script est lance depuis ManageReplicationSic.pl en un nouveau processus

my $error = 0;

sub displayHelp()
{
	print("usage: manageReplicationSicSending {-h | -d | -g} endDate\n");
	print("where option can be:\n");
	print("    -h : displays this help\n");
	print("    -d : replicates delta\n");
	print("    -g : replicates global\n");
}

my $help;
my $delta;
my $global;
my $endDate;
my $endDateWithoutSeconds;
my $deltaGlobalString;
my $dataPath;
my $tempNspDir;
my $rgDataDir;
my $nspDirToSend;

if (!GetOptions (
		"h" => \$help,
		"d" => \$delta,
		"g" => \$global,
		"endDate=s" => \$endDate)) {
	displayHelp();
	exit(1);
}

if ($help) {
	displayHelp();
	exit(1);
}

my @allFiles = ("0100", "0110", "0120", "0121", "0123", "0124", "0130", "0140", "0150", "0160", "0170", "0180", "0190",
	"0200", "0201", "0210", "0211", "0220", "0230", "0240", "0241", "0242", "0250", "0251", "0260", "0261",
	"0310", "0320", "0330", "0340","0350",
	"0400", "0410", "0420", "0430", "0440", "0480", "0490",
	"0820", "0830", "0840", "0860", 
	"0910", "0920", "0930", "0940", "0950", "0960", "0970", "0980",
	"0990", "0991", "0992", "0993", "0994", "0995", "0996",
	"1007","1015");

my @reggaeFiles = ("0400","0410","1006");

my @OCPFiles = ("0110","0210","0230","0320");

my @hopRAPFiles = ("0400","0410","0160","0930");

my @nspFiles = ( "0110", "0121", "0150", "0160", "0190", 
	"0210", "0220", "0230", "0241", "0251", "0261", 
	"0310", "0320", "0350", 
	"0400", "0410", "0420", "0480", "0490", 
	"0910", "0920", "0930", "0940", "0950", "0960", "999", 
	"1000", "1001", "1002", "1003", "1004", "1005", "1006");

sub compressAndSendNSPFile 
{
	my ($fluxName) = @_;
	$nspDirToSend = "$rgDataDir/$deltaGlobalString/tempNspDir";
	my $errorNsp = 0;

	# Creation d'un repertoire temporaire destin� a etre compresse et envoy�
	if ( ( system( "mkdir -p $nspDirToSend" ) >> 8 ) == 0) {
		print("NSP temp directory created \n");
	}
	else {
		print( "ERROR while creating NSP temp directory\n" );
		# $error = 1; remove due a non supress of $nspDirToSend
	}

	# Chaque fichier NSP est copié dans le repertoire temp
	foreach my $oneFile ( @nspFiles ) {
		if ( ( system( "cp $dataPath/$deltaGlobalString-$endDateWithoutSeconds.$oneFile.txt.gz $nspDirToSend/$deltaGlobalString-$endDateWithoutSeconds.$oneFile.txt.gz") >> 8 )  == 0) {
			print("NSP".$oneFile." added to folder\n" );
		}
		else {
			print("Error while copying ".$oneFile."\n" );
			$errorNsp = 1; # add to not try supress $nspDirToSend if not exist
			$error = 1;
		}
	}

	if (!$errorNsp) {

		#Compression du repertoire temp et envoi par le flux
		if ( ( system( "tar -cf - $nspDirToSend | gzip -c > $rgDataDir/$deltaGlobalString/$deltaGlobalString-$endDateWithoutSeconds.tar.gz" ) >> 8) == 0) {
			print( "NSP file compressed\n" );
			if ( ( system( "gwqvi1.sh $rgDataDir/$deltaGlobalString/$deltaGlobalString-$endDateWithoutSeconds.tar.gz $fluxName $fluxName"."_"."$deltaGlobalString-$endDateWithoutSeconds.tar.gz" ) >> 8 ) == 0 ) {
				print( "NSP file sent\n" );
				if ( ( system( "rm $rgDataDir/$deltaGlobalString/$deltaGlobalString-$endDateWithoutSeconds.tar.gz" ) >> 8 ) == 0 ) {
					print( "NSP temp dir deleted successfully \n" );
				}
				else {
					print("ERROR while deleting NSP temp dir \n")
				}
			} 
			else {
				print( "ERROR while sending NSP replication file\n" );
				$error = 1;
			}
		} else {
			print("ERROR unable to compress NSP file\n");
			$error = 1;
		}

		# Suppression du contenu du repertoire intermediaire
		if ( ( system( "rm -rf $nspDirToSend/* ") >> 8) == 0) {
			if ( ( system( "rmdir $nspDirToSend" ) >> 8) == 0) {
				print("Temporary folder deleted successfully\n");
			} else {
				print("ERROR while deleting temporary directory\n");
			}
		}
		else {
			print("ERROR while deleting temporary directory\n");
		}
	} else {
		print("ERROR $error: unable to compress AND send NSP file\n");
	}
}

if ( !$error ) {
	$endDate =~ /^(.*)..$/;
	$endDateWithoutSeconds = $1;
	$deltaGlobalString = defined($delta)?"MVT":"GLOBAL";
	$dataPath = $ENV{BASE_DATA_DIR}."/REPLICATION_GENERIQUE/$deltaGlobalString/$deltaGlobalString-$endDateWithoutSeconds";
	$rgDataDir = $ENV{BASE_DATA_DIR}."/REPLICATION_GENERIQUE";

	# Verification de la presence du fichier OK.txt

	if ( ( system( "ls $dataPath/OK.txt" ) >> 8) == 0 ) {
		print( "OK file is existing\n" );
	} else {
		print( "ERROR OK file '$dataPath/OK.txt' is not existing\n" );
		$error = 1;
	}
		
	if ( defined( $delta ) ) {
		# post process
		if ( !$error ) {
			if ( $ENV{hostType} eq "prd" ) {
				if ( ( system( $ENV{BASE_EXE_DIR}."/REPLICATION_GENERIQUE/TRANSFERT_FIXE/transf_STAR.sh $dataPath" ) >> 8 ) == 0 ) {
					print( "transf_STAR.sh executed\n" );
				} else {
					print( "ERROR while executing transf_STAR.sh\n" );
					$error = 1;
				}

				if ( ( system( $ENV{BASE_EXE_DIR}."/REPLICATION_GENERIQUE/TRANSFERT_FIXE/transf_CONCORDIA.sh $dataPath" ) >> 8 ) == 0 ) {
					print( "transf_CONCORDIA.sh executed\n" );
				} else {
					print( "ERROR while executing transf_CONCORDIA.sh\n" );
					$error = 1;
				}	
			}		

			if ( ( system( $ENV{BASE_EXE_DIR}."/REPLICATION_GENERIQUE/transfert_mvt_dwh.sh" ) >> 8 ) == 0 ) {
				print( "transfert_mvt_dwh.sh executed\n" );
			} else {
				print( "ERROR while executing transfert_mvt_dwh.sh\n" );
				$error = 1;
			}

			if ( ( system( $ENV{BASE_EXE_DIR}."/REPLICATION_GENERIQUE/transfert_dwhref_rap.sh" ) >> 8 ) == 0 ) {
				print( "transfert_dwhref_rap.sh executed\n" );
			} else {
				print( "ERROR while executing transfert_dwhref_rap.sh\n" );
				$error = 1;
			}

			#Execution script ocp
			if ( ( system( $ENV{BASE_EXE_DIR}."/REPLICATION_GENERIQUE/transfert_mvt_ocp.sh" ) >> 8 ) == 0 ) {
				print( "transfert_mvt_ocp.sh executed\n" );
			} else {
				print( "ERROR while executing transfert_mvt_ocp.sh\n" );
				$error = 1;
			}
		}	

	# GLOBAL    
	} else {
		if ( $ENV{hostType} eq "prd" ) {#Flux de prod
			foreach my $oneFile ( @hopRAPFiles ) {
				if ( ( system( "gwqvi1.sh $dataPath/$deltaGlobalString-$endDateWithoutSeconds.$oneFile.txt.gz PRAZV1 PRAZV1_$deltaGlobalString-$endDateWithoutSeconds.$oneFile.txt.gz" ) >> 8 ) == 0 ) {
					print("HOP RAP ".$oneFile." file sent\n");
				} else {
					print( "ERROR while sending HOP RAP replication ".$oneFile." file\n" ); 
					$error = 1;
				}
			}

			foreach my $oneFile ( @reggaeFiles ) {
				if ( ( system( "gwqvi1.sh $dataPath/$deltaGlobalString-$endDateWithoutSeconds.$oneFile.txt.gz PRAGG1 PRAGG1_$deltaGlobalString-$endDateWithoutSeconds.$oneFile.txt.gz" ) >> 8 ) == 0 ) {
					print( "Reggae".$oneFile." file sent\n" );
				} else {
					print( "ERROR while sending RAGGAE replication".$oneFile."file\n" );
					$error = 1;
				}
			}
		}

		if ( ( system( $ENV{BASE_EXE_DIR}."/REPLICATION_GENERIQUE/transfert_global_dwh.sh $endDateWithoutSeconds" ) >> 8 ) == 0 ) {
			print( "transfert_global_dwh.sh executed\n" );
		} else {
			print( "ERROR while executing transfert_global_dwh.sh\n" );
			$error = 1;
		}

		if ( ( system( $ENV{BASE_EXE_DIR}."/REPLICATION_GENERIQUE/transfert_global_planetPartner.sh $endDateWithoutSeconds" ) >> 8 ) == 0 ) {
			print( "transfert_global_planetPartner.sh executed\n" );
		} else {
			print( "ERROR while executing transfert_global_planetPartner.sh\n" );
			$error = 1;
		}
	} # End GLOBAL


	# Special process for NSP Files that have to be send on different
	# flux depending on GLOBAL or MVT process and depending on 
	# the system (RCT or PRD)
	if ( $ENV{hostType} eq "prd" ) {
		if ( $deltaGlobalString eq "MVT" ) {
			compressAndSendNSPFile("PNSP01");
		} 
		else { # Traitement des GLO
			compressAndSendNSPFile("PNSP02");
		}  
	} 
	else {      
		# Not on PRD system
		if ( $ENV{hostType} eq "rct" ) {
			if ( $deltaGlobalString eq "MVT" ) {
				compressAndSendNSPFile("TNSP01");
			}
			else { #Traitement des GLO 
				compressAndSendNSPFile("TNSP02"); 
			}
		}
	}
}

exit($error);

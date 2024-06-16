#!/tech/oracle/client/12102/perl/bin/perl -w
###############################################
# Simplification des phone number.
#
# Supprimer (logiquement) les telecoms pour un individu (GIN)
# et un terminal sauf le plus recent (DDate_modification) ayant l'AIN
# le plus grand (le dernier cree).
###############################################

#import
use lib $ENV{BASE_PERL_DIR};
use strict;
use DBI;
use IO::File;
use POSIX qw/strftime/;
use Time::HiRes;         # Timer
use phoneNumber;         # variable global des phone numbers.
use phoneNumberArgs;     # Arguments des phone numbers.
use phoneNumberRequest;  # Generateur de requete
require ("moduleBD.pl"); # Module de gestion base de donnee.

###############################################
# variable globale
###############################################
my $error 			= 0;
my $nbASupprimes 	= 0;
###############################################
# index des champs
###############################################
my $sainIndex				= 0;
my $ginIndex				= 1;
my $sterminalIndex			= 2;
my $scodeMediumIndex		= 3;
my $dateModificationIndex	= 4;
my $statutIndex				= 5;
my $signatureIndex			= 6;

##################################
# gestion des arguments.
$phoneNumberArgs::isHelp    = $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMarket  = $phoneNumberArgs::NO;
$phoneNumberArgs::isContrat = $phoneNumberArgs::NO;
$phoneNumberArgs::isTerminal= $phoneNumberArgs::NO;
$phoneNumberArgs::isMyDebug = $phoneNumberArgs::NO;
$phoneNumberArgs::isCommit  = $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isTest    = $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isDaily       = $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isStartDate   = $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isEndDate     = $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isReprise = $phoneNumberArgs::NO;
$phoneNumberArgs::isStat    = $phoneNumberArgs::NO;
$phoneNumberArgs::isMaxTelecom = $phoneNumberArgs::NO;

&phoneNumberArgs::gererArgs(@ARGV);


###############################################
#creation du fichier de log
my $logPath 	= $phoneNumber::logPathPhoneNumber."SIMPLIFICATION/";

my $traceFile 	= $logPath."simplificationPhoneNumber-synthesis-".$phoneNumber::dateDuJour.".txt";
my $sqlFile 	= $logPath."simplificationPhoneNumber-sql-".$phoneNumber::dateDuJour.".sql";
my $rollbackFile= $logPath."simplificationPhoneNumber-rollback-".$phoneNumber::dateDuJour.".sql";
my $extractFile = $logPath."simplificationPhoneNumber-".$phoneNumber::dateDuJour.".csv";

if ( defined( $traceFile ) ) { open( TRACE, ">$traceFile" ); }
if ( defined( $extractFile ) ) { open( EXTRACT, ">$extractFile" ); }
if ( defined( $sqlFile ) ) { open( SQL, ">$sqlFile" ); }
if ( defined( $rollbackFile ) ) { open( ROLLBACK, ">$rollbackFile" ); }

###############################################
#Connexion a la base de donnee
my $dbConnexion = BdConnexion();

###############################################
# Methode principale
# 1. Recuperer les telecoms.
# 2. Clean des phone number.
# 3. Normalisation des phone number.
###############################################
sub main(){

	# 1. Recuperer les telecoms


	# requete pour testcase.
	my $requete;

	if($phoneNumberArgs::test == 1){
		$requete  = "(select '1' ,'400032323232','M', 'D', '20141211100000', 'V', 'MYSIGN' from dual) union ";
		$requete .= "(select '2' ,'400032323232','M', 'D', '20141211100000', 'I', 'MYSIGN'  from dual) union ";
		$requete .= "(select '3' ,'400032323232','M', 'P', '20141211100000', 'V', 'MYSIGN'   from dual) union ";
		$requete .= "(select '4' ,'400032323233','T', 'D', '20141211100000', 'V', 'MYSIGN'   from dual) union ";
		$requete .= "(select '5' ,'400032323233','T', 'D', '', 'I', 'MYSIGN'  from dual) union ";
		$requete .= "(select '6' ,'400032323233','M', 'D', '20141211100000', 'V', 'MYSIGN'   from dual) union ";
		$requete .= "(select '7' ,'400032323233','M', 'D', '', 'V', 'MYSIGN'  from dual) union ";
		$requete .= "(select '8' ,'400032323233','M', 'D', '20141211100000','V', 'MYSIGN'  from dual) union ";
		$requete .= "(select '9' ,'400032323233','M', 'D', '20141211100000', 'V', 'MYSIGN'   from dual) union ";
		$requete .= "(select 'A' ,'400032323233','M', 'D', '20141211100000', 'V', 'MYSIGN'   from dual) union ";
		$requete .= "(select 'B' ,'400032323233','M', 'D', '20141211100000', 'V', 'MYSIGN'   from dual)";
	}
    else
    {
       $requete = genererRequete();
    }

	print "$requete\n";
	my $req = executeSQL($dbConnexion, $requete);

	# 2. Clean Des phone Number
	simplificationTelecom($req);

}
###############################################
# Generer la requete SQL
#
# Telecom pour un marche ($phoneNumberArgs::market) trie par
# individu, type (mobile ou fixe), dateModification, ain
# Generer la requete SQL
sub genererRequete ()
{
    return &phoneNumberRequest::getRequestSimplification($phoneNumberArgs::daily,
														 $phoneNumberArgs::startDate,
														 $phoneNumberArgs::endDate);
}

###############################################
# Recuperer les telecoms et creer le sql de suppression.
sub simplificationTelecom ($)
{
    my ($reqML) = @_;
    my $data= $reqML->fetchall_arrayref();
    $reqML->finish;

	$phoneNumber::nbTelecoms  = @$data;

	my $i;
	my $change=0;
	my $sgin;
	my $sain;
	my $sterminal;
	my $scodeMedium;

	my $aSupprimer = 0;

	# gestion des lignes avec meme (GIN, codeMedium, terminal)
	my $sginCourant 		= -1;
	my $sterminalCourant 	= -1;
	my $scodeMediumCourant 	= -1;

	my $requeteSimplification;
	my $requeteRollback;
    print SQL "-- Telecom a supprime (Simplification)\n";

	#Parcours de l'ensemble des telecoms
	#Les lignes sont trie dans l'ordre croissant SGIN, STERMINAL, DDATE_MODIFICATION, SAIN
	#Il faut supprimer logiquement toutes les lignes sauf la premiere pour un gin et un terminal precis.
	for $i ( 0 .. ($phoneNumber::nbTelecoms - 1) )
	{


		if(defined $data->[$i][$sainIndex]){
			$sain = $data->[$i][$sainIndex];
		}

		$aSupprimer = 0;

		if(defined $data->[$i][$ginIndex]){
			$sgin = $data->[$i][$ginIndex];
			if($sginCourant eq $sgin)
			{
				$aSupprimer = 1;
			}
			else
			{
				$aSupprimer = 0;
			}
			$sginCourant = $data->[$i][$ginIndex];
		}

		if(defined $data->[$i][$scodeMediumIndex]){
			$scodeMedium = $data->[$i][$scodeMediumIndex];
			if($scodeMediumCourant eq $scodeMedium && $aSupprimer == 1)
			{
				$aSupprimer = 1;
			}
			else
			{
				$aSupprimer = 0;
			}
			$scodeMediumCourant = $data->[$i][$scodeMediumIndex];
		}

		if(defined $data->[$i][$sterminalIndex]){
			$sterminal = $data->[$i][$sterminalIndex];
			if($sterminalCourant eq $sterminal && $aSupprimer == 1)
			{
				$aSupprimer = 1;
			}
			else
			{
				$aSupprimer = 0;
			}
			$sterminalCourant = $data->[$i][$sterminalIndex];
		}

		if($aSupprimer == 1)
		{

			$nbASupprimes++;
			$aSupprimer = 0;

			$requeteSimplification = "UPDATE SIC2.TELECOMS ";
			$requeteSimplification .= "SET sstatut_medium='X', ssignature_modification='$phoneNumber::SIGNATURE_SIMPLIF'  ";
			$requeteSimplification .= "WHERE sain='$data->[$i][$sainIndex]'";
			print SQL "$requeteSimplification;\n";

			if(defined $data->[$i][$statutIndex] && defined $data->[$i][$signatureIndex] && defined $data->[$i][$dateModificationIndex])
			{

				$requeteRollback = "UPDATE SIC2.TELECOMS ";
				$requeteRollback .= "SET sstatut_medium='$data->[$i][$statutIndex]', ssignature_modification='$data->[$i][$signatureIndex]', ddate_modification=to_date('$data->[$i][$dateModificationIndex]','YYYYMMDDHHMISS')  ";
				$requeteRollback .= "WHERE sain='$data->[$i][$sainIndex]'";

				print ROLLBACK "$requeteRollback;\n";
			}
			else{

			}


			if($phoneNumberArgs::daily == 1){
            	my $start = Time::HiRes::gettimeofday();
				my $req = executeSQL ($dbConnexion, $requeteSimplification );
            	my $end = Time::HiRes::gettimeofday();
				my $time = sprintf "%.3f", ($end - $start);

				print EXTRACT "$data->[$i][0];$data->[$i][1];$data->[$i][2];$data->[$i][3];X -> $time s\n";
			}
			else{
				print EXTRACT "$data->[$i][0];$data->[$i][1];$data->[$i][2];$data->[$i][3];X\n";
			}
		}
		else{
			print EXTRACT "$data->[$i][0];$data->[$i][1];$data->[$i][2];$data->[$i][3];V\n";

		}
    }

}

sub afficherSynthese ()
{
    print TRACE "Telecoms a Supprimer :  $nbASupprimes\n";
    print TRACE "Telecoms parcourus   :  $phoneNumber::nbTelecoms\n";
}


###############################################
# Traitement Principal
###############################################

main();
afficherSynthese();

# Commit changes to database if needed
commitBD($dbConnexion, $phoneNumberArgs::commit);

if ( defined( $sqlFile ) ) { close( SQL ); }
if ( defined( $rollbackFile ) ) { close( ROLLBACK ); }
if ( defined( $extractFile ) ) { close( EXTRACT ); }
if ( defined( $traceFile ) ) { close( TRACE ); }

$dbConnexion->disconnect();
exit( $error );
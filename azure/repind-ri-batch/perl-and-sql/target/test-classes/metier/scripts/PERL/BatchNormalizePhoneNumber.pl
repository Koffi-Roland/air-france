#!/tech/oracle/client/12102/perl/bin/perl -w
###############################################
# Clean et normalisation des phone number.
###############################################
#import
use lib $ENV{BASE_PERL_DIR};
use strict;
use DBI;
use IO::File;
use POSIX qw/strftime/;
use phoneNumber;         		# variable global des phone numbers.
use phoneNumberArgs;     		# Arguments des phone numbers.
use phoneNumberRequest;  		# Generateur de requete
use phoneNumberReprise;  		# Gestion des reprise des execution n'ayant pas abouti.
require ("moduleBD.pl"); 		# Module de gestion base de donnee.
require ("moduleRollback.pl"); 	# Module de gestion rollback.

###############################################
# variable global
my $errorJava = 0;


##################################
# gestion des arguments.
$phoneNumberArgs::isHelp    	= $phoneNumberArgs::OPTIONNAL;

$phoneNumberArgs::isMarket  	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isContrat 	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isTerminal	= $phoneNumberArgs::OPTIONNAL;

$phoneNumberArgs::isMyDebug 	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isCommit 		= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isStat   		= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isReprise		= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isTest    	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isRollback	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isDaily    	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isStartDate  	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isEndDate    	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMaxTelecom  = $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isResidu      = $phoneNumberArgs::OPTIONNAL;


&phoneNumberArgs::gererArgs(@ARGV);

###############################################
# Fichier de Log
my $logPath = $phoneNumber::logPathPhoneNumber;

if (defined $phoneNumberArgs::daily && $phoneNumberArgs::daily == 1)
{
    $logPath .= "DAILY/";
}

$logPath .= "NORMALISATION/";


my $exportJavaFile = $logPath."normalizePhoneNumber".$phoneNumber::dateDuJour;
my $logPathStat = $phoneNumber::logPathPhoneNumber."STATISTIQUE/";

my $statFile="statPhoneNumber-".$phoneNumber::dateDuJour.".txt";

###############################################
# script SH qui lance le java
my $scriptJava="BatchNormalizedPhoneNumber.sh";
my $paramsScriptJava="-t -f ".$exportJavaFile;

if($phoneNumberArgs::commit==1){
	$paramsScriptJava.=" -C";
}

	$paramsScriptJava.=" -l $logPath";

###############################################
# Gestion Reprise
&phoneNumberReprise::preparer($logPath, $phoneNumberArgs::myDebug );


###############################################
#Connexion a la base de donnee
#Open DB connection
my $dbConnexion = BdConnexion();

###############################################
#creation du fichier de log
if ( defined( $exportJavaFile ) ) {
  open( EXPORT, ">$exportJavaFile" );
}
###############################################
# Methode principale
# 1. Recuperer les telecoms.
# 2. Clean des phone number.
# 3. Normalisation des phone number.
###############################################
sub main(){

	# 1. Recuperer les telecoms
	if($phoneNumberArgs::rollback eq "0" )
	{

		my $requete;
		if($phoneNumberArgs::test == 1){
			$requete = "select '+33',0,622172013,'FR',400403120152,1000001 from dual";
		}
    	else
    	{
        	if($phoneNumberArgs::reprise == 0 )
        	{
				if(!defined $phoneNumberArgs::market && ! defined $phoneNumberArgs::daily){
					print("WARNING : No Market found !\n");
            	}

				if(!defined $phoneNumberArgs::contrat && ! defined $phoneNumberArgs::daily){
					print("No Contrat found !\n");
					exit(1);
            	}

				if(!defined $phoneNumberArgs::terminal && ! defined $phoneNumberArgs::daily){
					print("No Terminal found !\n");
					exit(1);
            	}

				$requete = genererRequete();
        	}
    	}

    	my $reqML;
    	if($phoneNumberArgs::reprise == 0 || $phoneNumberArgs::test == 1 ){
        	$reqML = executeSQL ($dbConnexion, $requete );
			print("request executed.\n");
    	}

		GenererExportPourNormJava($reqML);

		if ((system($ENV{BASE_PERL_DIR}."/$scriptJava $paramsScriptJava") >> 8) == 0)
		{
		  print("$scriptJava executed.\n");
		}
		else
		{
		  print("ERROR while executing $scriptJava\n");
		  $errorJava = 1;
		}
	}
	else
	{
		gererRollback($dbConnexion, $phoneNumberArgs::rollback);
	}
}

# Generer la requete SQL
sub genererRequete ()
{
    if($phoneNumberArgs::daily == 1){
        return &phoneNumberRequest::getRequestDaily($phoneNumberArgs::startDate, $phoneNumberArgs::endDate);
	}
    else{
        return &phoneNumberRequest::getRequest($phoneNumberArgs::market,$phoneNumberArgs::contrat,$phoneNumberArgs::terminal, 1, 0, $phoneNumberArgs::maxTelecom, $phoneNumberArgs::residu);
    }
}

###############################################
# Generer le fichier plat des telecoms pour traitement java
sub GenererExportPourNormJava ($)
{
    my ($reqML) = @_;
    my $data;
    if($phoneNumberArgs::reprise == 0 || $phoneNumberArgs::test == 1 ){
		print ("Mode sans reprise.\n");
		$data= $reqML->fetchall_arrayref();

        $reqML->finish;

		my $nbTaille = @$data;

		if($nbTaille == 0){
			print ("WARN : Aucun telecom a traitee. \n");
			if ( defined( $exportJavaFile ) ) {
  				close( EXPORT );
			}
			exit(0);
		}
        &phoneNumberReprise::generer($data);

    }
    else
    {
		print ("Mode reprise.\n");
        $data = &phoneNumberReprise::recuperer($phoneNumberArgs::reprise);
    }

	$phoneNumberArgs::nbTelecoms = @$data;


	#Generation du java
	my $i;

	print ("phone Number to compute :$phoneNumberArgs::nbTelecoms\n");
	for $i ( 0 .. ($phoneNumberArgs::nbTelecoms - 1) )
	{

	 	if(defined $data->[$i][$phoneNumber::indicatifIndex]){
			print EXPORT "$data->[$i][$phoneNumber::indicatifIndex]";
		}
		print EXPORT $phoneNumber::separator;
		if(defined $data->[$i][$phoneNumber::codeRegionIndex]){
			print EXPORT "$data->[$i][$phoneNumber::codeRegionIndex]";
		}
		print EXPORT $phoneNumber::separator;
		if(defined $data->[$i][$phoneNumber::numeroIndex]){
			print EXPORT "$data->[$i][$phoneNumber::numeroIndex]";
		}
		print EXPORT $phoneNumber::separator;
		if(defined $data->[$i][$phoneNumber::scodePaysIndex]){
			print EXPORT "$data->[$i][$phoneNumber::scodePaysIndex]";
		}
		print EXPORT $phoneNumber::separator;
		if(defined $data->[$i][$phoneNumber::sginIndex]){
			print EXPORT "$data->[$i][$phoneNumber::sginIndex]";
		}
		print EXPORT $phoneNumber::separator;
		if(defined $data->[$i][$phoneNumber::sainIndex]){
			print EXPORT "$data->[$i][$phoneNumber::sainIndex]";
		}
       	print EXPORT "\n";
    }
}

###############################################
# Traitement Principal
###############################################

main();

# Commit changes to database if needed
commitBD($dbConnexion, $phoneNumberArgs::commit);

# Suppression du fichier de reprise.
&phoneNumberReprise::supprimer();


if ( defined( $exportJavaFile ) ) {
  close( EXPORT );
}

$dbConnexion->disconnect();
exit( $phoneNumberArgs::error );
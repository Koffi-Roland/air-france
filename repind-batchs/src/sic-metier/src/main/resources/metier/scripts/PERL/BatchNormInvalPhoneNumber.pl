#!/tech/oracle/client/12102/perl/bin/perl -w
###################################################
# Invalidations  des phone number non normalisable.
###################################################
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
use phoneNumberReprise;  # Gestion des reprise des execution n'ayant pas abouti.
require ("moduleBD.pl"); # Module de gestion base de donnee.


##################################
# gestion des arguments.
$phoneNumberArgs::isHelp    	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMarket  	= $phoneNumberArgs::NO;
$phoneNumberArgs::isContrat		= $phoneNumberArgs::NO;
$phoneNumberArgs::isTerminal	= $phoneNumberArgs::NO;
$phoneNumberArgs::isMyDebug 	= $phoneNumberArgs::NO;
$phoneNumberArgs::isCommit  	= $phoneNumberArgs::NO;
$phoneNumberArgs::isTest    	= $phoneNumberArgs::NO;
$phoneNumberArgs::isReprise 	= $phoneNumberArgs::NO;
$phoneNumberArgs::isStat    	= $phoneNumberArgs::NO;
$phoneNumberArgs::isDaily   	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isStartDate 	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isEndDate   	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMaxTelecom  = $phoneNumberArgs::NO;
$phoneNumberArgs::isResidu      = $phoneNumberArgs::NO;

&phoneNumberArgs::gererArgs(@ARGV);

###############################################
# variable globale
###############################################
my $nbInvalidated = 0;

###############################################
# variable pour statistique

my $logPath = $phoneNumber::logPathPhoneNumber;

$logPath .= "DAILY/";
$logPath .= "NORMINVAL/";

my $traceFile = $logPath."normInvalPhoneNumber-synthesis-".$phoneNumber::dateDuJour.".txt";
my $extractFile = $logPath."normInvalPhoneNumber-".$phoneNumber::dateDuJour.".csv";


###############################################
#Connexion a la base de donnee
#Open DB connection
my $dbConnexion = BdConnexion();
#my $dbConnexion = BdConnexionEnv("SIC_RCT");

###############################################
#creation du fichier de log
if ( defined( $traceFile ) ) { open( TRACE, ">$traceFile" ); }
if ( defined( $extractFile ) ) { open( EXTRACT, ">$extractFile" ); }
###############################################
# Methode principale
# 1. Recuperation des telecoms non normalises de la journee.
# 2. Invalidation des telecoms non noramlisable.
###############################################
sub main(){

	# 1. Recuperer les telecoms.
	my $requete;
	if($phoneNumberArgs::test == 1){
		$requete = "select '0a',1,'34a',3, 4, 5 from dual";
	}
	else{
		$requete = genererRequete();
	}

	my $reqML;
	$reqML = executeSQL ($dbConnexion, $requete );

	# 2. Invalider les telecoms non normalisables.
	invalidationTelecom($reqML);
}

# Generer la requete SQL
sub genererRequete ()
{
   	return &phoneNumberRequest::getRequestNormInval($phoneNumberArgs::startDate, $phoneNumberArgs::endDate);
}

# Invalider les telecoms.
sub invalidationTelecom ($)
{
	my ($reqML) = @_;

	my $data;
	# Execution requete
	$data= $reqML->fetchall_arrayref();
	$phoneNumber::nbTelecoms  = $reqML->rows();
	$reqML->finish;

	$phoneNumber::nbTelecoms  = @$data;

    if($phoneNumber::nbTelecoms  == 0){
        print ("WARN : No Telecom has been processed. \n");
        if ( defined( $extractFile ) ) {
            close( EXTRACT );
        }
        if ( defined( $traceFile ) ) {
            close( TRACE );
        }
        exit(0);
    }

	my $i;
	my $requeteInvalidation;

	afficherHeader();

	for $i ( 0 .. (@$data - 1) )
	{
			$nbInvalidated++;
			$requeteInvalidation = "UPDATE SIC2.TELECOMS t ";
			$requeteInvalidation.= "SET t.sstatut_medium='I', t.ddate_modification=sysdate,";
			$requeteInvalidation.= " t.ssignature_modification='$phoneNumber::SIGNATURE_NORMINVAL' ";
			$requeteInvalidation.= "WHERE t.sain='$data->[$i][$phoneNumber::sainIndex]' ";

            my $start = Time::HiRes::gettimeofday();
			my $req = executeSQL($dbConnexion, $requeteInvalidation);
            my $end = Time::HiRes::gettimeofday();

			my $time = sprintf "%.3f", ($end - $start);


		if(defined $data->[$i][$phoneNumber::indicatifIndex]){
           			 print EXTRACT "$data->[$i][$phoneNumber::indicatifIndex]";
  		}
  		print EXTRACT $phoneNumber::separator;


  		if(defined $data->[$i][$phoneNumber::codeRegionIndex]){
            		print EXTRACT "$data->[$i][$phoneNumber::codeRegionIndex]";
  		}
		print EXTRACT $phoneNumber::separator;


  		if(defined $data->[$i][$phoneNumber::numeroIndex]){
          		 print EXTRACT "$data->[$i][$phoneNumber::numeroIndex]";
  		}
		print EXTRACT $phoneNumber::separator;

  		if(defined $data->[$i][$phoneNumber::scodePaysIndex]){
          		 print EXTRACT "$data->[$i][$phoneNumber::scodePaysIndex]";
  		}
		print EXTRACT $phoneNumber::separator;

  		if(defined $data->[$i][$phoneNumber::sginIndex]){
           		print EXTRACT "$data->[$i][$phoneNumber::sginIndex]";
 		 }
		print EXTRACT $phoneNumber::separator;

 		if(defined $data->[$i][$phoneNumber::sainIndex]){
           		print EXTRACT "$data->[$i][$phoneNumber::sainIndex]";
 		 }

		print EXTRACT $phoneNumber::separator;
		print EXTRACT "NORM_INVAL in $time s \n";

        print "Execution of line : $i / $phoneNumber::nbTelecoms / ";
		print "AIN $data->[$i][$phoneNumber::sainIndex] NORM_INVAL in $time s \n";
    }#for()

} #sub()

# Afficher le header
sub afficherHeader ()
{
    print EXTRACT "Indicatif";
    print EXTRACT $phoneNumber::separator;
    print EXTRACT "Code region";
    print EXTRACT $phoneNumber::separator;
    print EXTRACT "Numero";
    print EXTRACT $phoneNumber::separator;
    print EXTRACT "Code pays";
    print EXTRACT $phoneNumber::separator;
    print EXTRACT "GIN";
    print EXTRACT $phoneNumber::separator;
    print EXTRACT "AIN";
    print EXTRACT $phoneNumber::separator;
    print EXTRACT "Statut (Y/N)";
    print EXTRACT $phoneNumber::separator;
    print EXTRACT "\n";
}


# Afficher la synthese.
sub afficherSynthese ()
{

	print TRACE "date d'execution   : $phoneNumber::dateFormattee\n";
   	print TRACE "Telecom traites    : $phoneNumber::nbTelecoms\n";
	print TRACE "Telecom invalides  : $nbInvalidated\n";
}

###############################################
# Traitement Principal
###############################################

main();
afficherSynthese();

# Commit changes to database if needed
commitBD($dbConnexion, $phoneNumberArgs::commit);


if ( defined( $traceFile ) ) { close( TRACE ); }
if ( defined( $extractFile ) ) { close( EXTRACT ); }

$dbConnexion->disconnect();
exit( $phoneNumberArgs::error );
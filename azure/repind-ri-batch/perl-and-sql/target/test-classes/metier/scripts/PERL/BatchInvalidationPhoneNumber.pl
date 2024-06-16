#!/tech/oracle/client/12102/perl/bin/perl -w
###############################################
# Invalidations  des phone number.
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
use phoneNumberReprise;  # Gestion des reprise des execution n'ayant pas abouti.
require ("moduleBD.pl"); # Module de gestion base de donnee.


##################################
# gestion des arguments.
$phoneNumberArgs::isHelp    	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMarket  	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isContrat		= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isTerminal	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMyDebug 	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isCommit  	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isTest    	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isReprise 	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isStat    	= $phoneNumberArgs::NO;
$phoneNumberArgs::isDaily   	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isStartDate 	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isEndDate   	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMaxTelecom  = $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isResidu      = $phoneNumberArgs::OPTIONNAL;

&phoneNumberArgs::gererArgs(@ARGV);

###############################################
# variable globale
###############################################
my $nbInvalidated = 0;

###############################################
# variable pour statistique

my $logPath = $phoneNumber::logPathPhoneNumber;
if (defined $phoneNumberArgs::daily && $phoneNumberArgs::daily == 1)
{
	$logPath .= "DAILY/";
}
$logPath .= "INVALIDATION/";

my $traceFile = $logPath."invalPhoneNumber-synthesis-".$phoneNumber::dateDuJour.".txt";
my $extractFile = $logPath."invalPhoneNumber-".$phoneNumber::dateDuJour.".csv";

###############################################
# Gestion Reprise
&phoneNumberReprise::preparer($logPath, $phoneNumberArgs::myDebug );

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
# 1. Recuperation des telecoms.
# 2. Invalidation des telecoms.
###############################################
sub main(){

	# 1. Recuperer les telecoms.
	my $requete;
	if($phoneNumberArgs::test == 1){
		$requete = "select '0a',1,'34a',3, 4, 5 from dual";
	}
	else{
		if($phoneNumberArgs::reprise == 0)
		{
			$requete = genererRequete();
		}
	}

	my $reqML;
	if($phoneNumberArgs::reprise == 0 || $phoneNumberArgs::test == 1 ){
		$reqML = executeSQL ($dbConnexion, $requete );
	}

	# 2. Invalider les telecoms.
	invalidationTelecom($reqML);
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

# Invalider les telecoms.
sub invalidationTelecom ($)
{
	my ($reqML) = @_;

	my $data;
	# Execution requete
	if($phoneNumberArgs::reprise == 0 || $phoneNumberArgs::test == 1 ){
		$data= $reqML->fetchall_arrayref();
		$phoneNumber::nbTelecoms  = $reqML->rows();
		$reqML->finish;
		&phoneNumberReprise::generer($data);
	}
	else
	{
		print "Mode Reprise\n";
		$data = &phoneNumberReprise::recuperer($phoneNumberArgs::reprise);
	}

	$phoneNumber::nbTelecoms  = @$data;

    if($phoneNumber::nbTelecoms  == 0){
        print ("WARN : Aucun telecom a traiter. \n");
        if ( defined( $extractFile ) ) {
            close( EXTRACT );
        }
        if ( defined( $traceFile ) ) {
            close( TRACE );
        }
        exit(0);
    }

	my $i;
	my $change=0;
	my $requeteInvalidation;

	afficherHeader();

	for $i ( 0 .. (@$data - 1) )
	{
		if(defined $data->[$i][$phoneNumber::indicatifIndex]){
			if(1 == appliquerInvalidation($data->[$i][$phoneNumber::indicatifIndex]))
			{
				$change=1;
			}
		}

		if(defined $data->[$i][$phoneNumber::codeRegionIndex]){
			if(1 == appliquerInvalidation($data->[$i][$phoneNumber::codeRegionIndex]))
			{
				$change=1;
			}
		}

		if(defined $data->[$i][$phoneNumber::numeroIndex]){
			if(1 == appliquerInvalidation($data->[$i][$phoneNumber::numeroIndex]))
			{
				$change=1;
			}
		}

		if($change == 1)
		{
			$phoneNumberReprise::ain = $data->[$i][$phoneNumber::sainIndex];
			$change = 0;
			$nbInvalidated++;
			$requeteInvalidation = "UPDATE SIC2.TELECOMS t ";
			$requeteInvalidation.= "SET t.sstatut_medium='I', t.ddate_modification=sysdate,";
			$requeteInvalidation.= " t.ssignature_modification='$phoneNumber::SIGNATURE_INVALID' ";
			$requeteInvalidation.= "WHERE t.sain='$phoneNumberReprise::ain' ";

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

		print EXTRACT "INVAL : Yes in $time s \n";

            print "Execution of line : $i / $phoneNumber::nbTelecoms / ";
			print "AIN $data->[$i][$phoneNumber::sainIndex] INVAL : Yes in $time s \n";

		}
		else{


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

			print EXTRACT "INVAL : No \n";

            print "Execution of line : $i / $phoneNumber::nbTelecoms / ";
			print "AIN $data->[$i][$phoneNumber::sainIndex] INVAL : No \n";
		}
    }

}

# verification de la chaine de caractere pour determiner si le telecom est a invalider.
sub appliquerInvalidation($)
{
	my ($str) = @_;
	my $result = 0;
	# caractere autorise de 0-9,"espace", /, (, ), .
	if( $str =~ m/^[0-9\ \/\(\)\-\.\+]*$/ ) {
		$result = 0;
	}
	else
	{
		$result = 1;
	}
	return $result;
}

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

    if(defined $phoneNumberArgs::market){
        print TRACE "market  : $phoneNumberArgs::market\n";
    }

    if(defined $phoneNumberArgs::contrat){
        print TRACE "contrat  : $phoneNumberArgs::contrat\n";
    }

    if(defined $phoneNumberArgs::terminal){
        print TRACE "terminal  : $phoneNumberArgs::terminal\n";
    }

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

# Suppression du fichier de reprise.
&phoneNumberReprise::supprimer();

if ( defined( $traceFile ) ) { close( TRACE ); }
if ( defined( $extractFile ) ) { close( EXTRACT ); }

$dbConnexion->disconnect();
exit( $phoneNumberArgs::error );
#!/tech/oracle/client/12102/perl/bin/perl -w
###############################################
# Clean des phone number.
###############################################
#import
use lib $ENV{BASE_PERL_DIR};
use strict;
use DBI;
use IO::File;
use POSIX qw/strftime/;
use Time::HiRes; 		 # Timer
use phoneNumber;         # variable global des phone numbers.
use phoneNumberArgs;     # Arguments des phone numbers.
use phoneNumberReprise;  # Gestion des reprise des execution n'ayant pas abouti.
use phoneNumberRequest;  # Generateur de requete
require ("moduleBD.pl"); # Module de gestion base de donnee.

##################################
# gestion des arguments.
$phoneNumberArgs::isHelp     	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMarket   	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isContrat  	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isTerminal 	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMyDebug  	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isCommit   	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isTest     	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isReprise  	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isStat     	= $phoneNumberArgs::NO;
$phoneNumberArgs::isDaily    	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isStartDate	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isEndDate  	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMaxTelecom 	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isResidu      = $phoneNumberArgs::OPTIONNAL;

&phoneNumberArgs::gererArgs(@ARGV);

###############################################
# variable globale
###############################################
my $nbClean 	= 0;

###############################################
# Fichier de Log
my $logPath = $phoneNumber::logPathPhoneNumber;

if (defined $phoneNumberArgs::daily && $phoneNumberArgs::daily == 1)
{
    $logPath .= "DAILY/";
}
$logPath	.= "CLEAN/";

my $traceFile 	= $logPath."cleanPhoneNumber-synthesis".$phoneNumber::dateDuJour.".txt";
my $extractFile = $logPath."cleanPhoneNumber".$phoneNumber::dateDuJour.".csv";

###############################################
# Gestion Reprise
&phoneNumberReprise::preparer($logPath, $phoneNumberArgs::myDebug );


###############################################
#Connexion a la base de donnee
my $dbConnexion = BdConnexion();

###############################################
#creation du fichier de log
if ( defined( $traceFile ) ) { open( TRACE, ">$traceFile" ); }
if ( defined( $extractFile ) ) { open( EXTRACT, ">$extractFile" ); }

###############################################
# Methode principale
# 1. Recuperer les telecoms.
# 2. Clean des phone number.
###############################################
sub main(){

	# 1. Recuperer les telecoms.
	my $requete;
	if($phoneNumberArgs::test == 1)
	{
		$requete  = "select '', '', '+', '', '400188009011', '53181482' from dual";
	}
	else
	{
        if($phoneNumberArgs::reprise == 0)
        {
            $requete = genererRequete();
        }
	}
    my $reqML;
    if($phoneNumberArgs::reprise == 0 || $phoneNumberArgs::test == 1 ){
        $reqML = executeSQL ($dbConnexion, $requete );
    }
	# 2. Clean les telecoms.
	traiterTelecom($reqML);
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

# Nettoyer les telecoms.
sub traiterTelecom ($)
{
	my ($reqML) = @_;
    my $data;
    if($phoneNumberArgs::reprise == 0 || $phoneNumberArgs::test == 1 ){
        $data= $reqML->fetchall_arrayref();
        $reqML->finish;
        &phoneNumberReprise::generer($data);
    }
    else
    {
        $data = &phoneNumberReprise::recuperer($phoneNumberArgs::reprise);
    }

    $phoneNumber::nbTelecoms  = @$data;

    if($phoneNumber::nbTelecoms  == 0){
        print ("WARN : Aucun telecom a traitee. \n");
        if ( defined( $extractFile ) ) {
            close( EXTRACT );
        }
        if ( defined( $traceFile ) ) {
            close( TRACE );
        }
        exit(0);
    }

	my $i;

	my $old;
	my $newIndicatif;
	my $newCodeRegion;
	my $newNumero;

	my $sain;

	my $change=0;
	my $requeteUpdate;
	my $isFirstParam = 0;
	afficherHeader();

	for $i ( 0 .. (@$data - 1) )
	{
		undef $newIndicatif;
		undef $newCodeRegion;
		undef $newNumero;

		if(defined $data->[$i][$phoneNumber::indicatifIndex]){
			$old= $data->[$i][$phoneNumber::indicatifIndex];
			$newIndicatif=appliquerNettoyage($data->[$i][$phoneNumber::indicatifIndex]);
			if(length($old)!=length($newIndicatif))
			{
				$change=1;
			}
		}

		if(defined $data->[$i][$phoneNumber::codeRegionIndex]){
			$old= $data->[$i][$phoneNumber::codeRegionIndex];
			$newCodeRegion=appliquerNettoyage($data->[$i][$phoneNumber::codeRegionIndex]);
			if(length($old)!=length($newCodeRegion))
			{
				$change=1;
			}
		}

		if(defined $data->[$i][$phoneNumber::numeroIndex]){
			$old= $data->[$i][$phoneNumber::numeroIndex];
			$newNumero=appliquerNettoyage($data->[$i][$phoneNumber::numeroIndex]);
			if(length($old)!=length($newNumero))
			{
				$change=1;
			}
		}

		if($change == 1)
		{

            $phoneNumberReprise::ain = $data->[$i][$phoneNumber::sainIndex];

			$change=0;
			$nbClean++;

			$requeteUpdate = "UPDATE SIC2.TELECOMS t SET t.ddate_modification=sysdate,";
			if($newNumero eq ""){
				$requeteUpdate.="t.ssignature_modification='$phoneNumber::SIGNATURE_INV_CLEAN', t.sstatut_medium='I' ";
				$isFirstParam = 1;
			}
			else{
				$requeteUpdate.="t.ssignature_modification='$phoneNumber::SIGNATURE_CLEAN' ";
				$isFirstParam = 1;
			}

			if(defined $newIndicatif)
			{
				if($isFirstParam == 0)
				{
					$requeteUpdate .= "t.sindicatif="."'".$newIndicatif."' ";
					$isFirstParam = 1;
				}
				else{
					$requeteUpdate .= ", t.sindicatif="."'".$newIndicatif."' ";
				}
			}

			if(defined $newCodeRegion)
			{
				if($isFirstParam == 0)
				{
					$requeteUpdate .= "t.scode_region="."'".$newCodeRegion."' ";
					$isFirstParam = 1;
				}
				else{
					$requeteUpdate .= ", t.scode_region="."'".$newCodeRegion."' ";
				}
			}

			if(defined $newNumero && $newNumero ne "")
			{
				if($isFirstParam == 0)
				{
					$requeteUpdate .= "t.snumero="."'".$newNumero."' ";
					$isFirstParam = 1;
				}
				else{
					$requeteUpdate .= ", t.snumero="."'".$newNumero."' ";
				}
			}

			$requeteUpdate.="WHERE t.sain='$data->[$i][$phoneNumber::sainIndex]'";


			my $start = Time::HiRes::gettimeofday();

			my $req = executeSQL($dbConnexion, $requeteUpdate);

			my $end = Time::HiRes::gettimeofday();

			my $time = sprintf "%.3f" , ($end - $start);

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
			print EXTRACT "CHANGE : Yes / in $time s. \n";

			print "Execution of line : $i / $phoneNumber::nbTelecoms / ";
			print "AIN $data->[$i][$phoneNumber::sainIndex] / CHANGE : Yes /  in $time s. \n";


			$isFirstParam=0;

		}
		else {

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

			print EXTRACT "CHANGE : No \n";

			print "Execution of line : $i / $phoneNumber::nbTelecoms / AIN $data->[$i][$phoneNumber::sainIndex] CHANGE : No \n";

		}
    }

}

# Nettoyage de la chaine de caractere selon les regles de gestion.
sub appliquerNettoyage($)
{
	my ($str) = @_;
	$str =~ s/ //g;
	$str =~ s/\.//g;
	$str =~ s/\+//g;
	$str =~ s/\(//g;
	$str =~ s/\)//g;
	$str =~ s/\-//g;

	return $str;
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
    if(defined $phoneNumberReprise::ain)
    {
        print TRACE "dernier ain traite : $phoneNumberReprise::ain\n";

    }
	print TRACE "date d'execution 	: $phoneNumber::dateFormattee\n";
	print TRACE "Telecom traites 	: $phoneNumber::nbTelecoms\n";
	print TRACE "Telecom nettoyes	: $nbClean\n";

	if(defined $phoneNumberArgs::market){
		print TRACE "market   : $phoneNumberArgs::market\n";
	}
	if(defined $phoneNumberArgs::contrat){
   		print TRACE "contrat  : $phoneNumberArgs::contrat\n";
	}
	if(defined $phoneNumberArgs::terminal){
   	print TRACE "terminal : $phoneNumberArgs::terminal\n";
	}

}

###############################################
# Traitement Principal
###############################################

main();
afficherSynthese();

commitBD($dbConnexion, $phoneNumberArgs::commit);

# Suppression du fichier de reprise.
&phoneNumberReprise::supprimer();

if (defined( $traceFile ) ) { close(TRACE); }
if (defined( $extractFile ) ) { close(EXTRACT); }

$dbConnexion->disconnect();
exit( $phoneNumberArgs::error );
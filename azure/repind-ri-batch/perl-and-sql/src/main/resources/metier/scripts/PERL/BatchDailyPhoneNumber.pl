#!/tech/oracle/client/12102/perl/bin/perl -w
###############################################
# Normalisation Journaliere des telecoms.
###############################################
use lib $ENV{BASE_PERL_DIR};
use strict;
use DBI;
use IO::File;
use POSIX qw/strftime/;
use phoneNumber;         		# variable global des phone numbers.
use phoneNumberArgs;     		# Arguments des phone numbers.
use phoneNumberRequest;     	# Recuperer modification du jour.
require ("moduleBD.pl"); 		# Module de gestion base de donnee.

##################################
# gestion des arguments.
$phoneNumberArgs::isHelp    	= $phoneNumberArgs::OPTIONNAL;

$phoneNumberArgs::isMarket  	= $phoneNumberArgs::NO;
$phoneNumberArgs::isContrat 	= $phoneNumberArgs::NO;
$phoneNumberArgs::isTerminal	= $phoneNumberArgs::NO;

$phoneNumberArgs::isMyDebug 	= $phoneNumberArgs::NO;
$phoneNumberArgs::isCommit 		= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isStat   		= $phoneNumberArgs::NO;
$phoneNumberArgs::isReprise		= $phoneNumberArgs::NO;
$phoneNumberArgs::isTest    	= $phoneNumberArgs::NO;
$phoneNumberArgs::isRollback	= $phoneNumberArgs::NO;

$phoneNumberArgs::isStartDate	= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isEndDate		= $phoneNumberArgs::OPTIONNAL;
$phoneNumberArgs::isMaxTelecom	= $phoneNumberArgs::NO;

&phoneNumberArgs::gererArgs(@ARGV);

###############################################
# Chemin du fichier de Log
my $logPath = $phoneNumber::logPathPhoneNumber."DAILY/";
my $logPathInvalidation = $logPath."INVALIDATION";
my $logPathClean = $logPath."CLEAN";
my $logPathNormalisation = $logPath."NORMALISATION";

my $logFile = $logPath."dailyPhoneNumber".$phoneNumber::dateDuJour;
my $recover = $logPath."recover.txt";

###############################################
#creation du fichier de log
open( LOGDAILY, ">$logFile" );
open( REPRISE, ">$recover" );

###############################################
# script SH qui lance le java
my $paramsScript = " -daily";
my $scriptInvalidation 	="BatchInvalidationPhoneNumber.pl";
my $scriptClean 		="BatchCleanPhoneNumber.pl";
my $scriptNormalisation ="BatchNormalizePhoneNumber.pl";
my $scriptNormInval ="BatchNormInvalPhoneNumber.pl";
my $scriptSimplification ="BatchSimplificationPhoneNumber.pl";

if($phoneNumberArgs::commit==1){
	print "Commit mode\n";
    $paramsScript.=" -C  ";
}
# Stat Countrycode
my $nbFB 		= 0;
my $nbMYA 		= 0;
my $nbBDC 		= 0;
my $nbNotFound 	= 0;

my $startDate = undef;
my $endDate   = undef;

my $dateArgs = "-daily ";
###############################################
#Connexion a la base de donnee
#Open DB connection
my $dbConnexion = BdConnexion();

###############################################
# Methode principale
# 1. Recuperer la date du dernier traitement en BD
# 2. Invalider les telecoms qui comporte des caratere different de [0-9] et -, , .,( ,),+.
# 3. Clean des phone number nettoye les caracteres -, , ., (, ), +.
# 4. Normalisation des phone number via librairie google (JAVA).
# 5. Positionner la date du dernier traitement en BD.
###############################################
sub main(){

	# step 1 : Get Last computing date.
	$dateArgs .= &prepareDate();

	#step 2 : Get Telecom since computing date.
	# request for residu daily
	my $request = &phoneNumberRequest::getRequestDate(undef, undef, undef, 0, 1, $startDate, $endDate, undef, 1);
	print "$request\n";
    my $requestExecute = executeSQL ($dbConnexion, $request );
    my $data = $requestExecute->fetchall_arrayref();
    $phoneNumberArgs::nbTelecoms = $requestExecute->rows();
    $requestExecute->finish;


	# Cas : Aucun Telecom
	if( $phoneNumberArgs::nbTelecoms == 0)
	{
		print "Pas de telecom a traiter";
		print LOGDAILY "Pas de telecom a traiter";
  	  	if ( defined( $logFile ) ) {
  			close( LOGDAILY );
      	}
	  	if ( defined( $recover ) ) {
  			close( REPRISE );
	  	}

	  	return 0;
	}
	else
	{
		# mode reprise pour les batch en partant du premier ain
    	$paramsScript .=" -reprise $data->[0][$phoneNumber::sainIndex]";
	}

	#step 3 : create recover file and complete it.
	my $countryCode;
	my $i;
 	for $i ( 0 .. ($phoneNumberArgs::nbTelecoms - 1) )
    {
		$countryCode = undef;

        if(defined $data->[$i][$phoneNumber::indicatifIndex]){
            print REPRISE "$data->[$i][$phoneNumber::indicatifIndex]";
        }
        print REPRISE $phoneNumber::separator;
        if(defined $data->[$i][$phoneNumber::codeRegionIndex]){
            print REPRISE "$data->[$i][$phoneNumber::codeRegionIndex]";
        }
        print REPRISE $phoneNumber::separator;
        if(defined $data->[$i][$phoneNumber::numeroIndex]){
            print REPRISE "$data->[$i][$phoneNumber::numeroIndex]";
        }
        print REPRISE $phoneNumber::separator;

		print "$data->[$i][$phoneNumber::sainIndex] : ";

	    $countryCode = manageCountryCode($data->[$i][$phoneNumber::sginIndex]);

		if(defined $countryCode){
        		print REPRISE "$countryCode";
		}

        print REPRISE $phoneNumber::separator;
        if(defined $data->[$i][$phoneNumber::sginIndex]){
            print REPRISE "$data->[$i][$phoneNumber::sginIndex]";
        }
        print REPRISE $phoneNumber::separator;
        if(defined $data->[$i][$phoneNumber::sainIndex]){
            print REPRISE "$data->[$i][$phoneNumber::sainIndex]";
        }
        print REPRISE "\n";
    } #for()

	if ( defined( $recover ) ) {
  		close( REPRISE );
	}

	#step 4.1 : create recover file backup.
	if ((system("cp $recover $logPathInvalidation/recover.txt ") >> 8) == 0)
	{
	  print("cp $recover $logPathInvalidation/recover.txt with success.\n");
	}
	else
	{
	  print("cp $recover $logPath/recover.txt FAILED.\n");
	}


	# step 5 : Invalidation Phone Number daily.
	if ((system($ENV{BASE_PERL_DIR}."/$scriptInvalidation $paramsScript") >> 8) == 0)
	{
	  print("$scriptInvalidation $paramsScript executed.\n");
	}
	else
	{
	  	print("ERROR : $scriptInvalidation $paramsScript failed.\n");
 	  	if ( defined( $logFile ) ) {
  			close( LOGDAILY );
		}

		if ( defined( $recover ) ) {
  		close( REPRISE );
		}
	  	exit(1);
	}

	#step 4.2 : create recover file backup.
	if ((system("cp $recover $logPathClean/recover.txt ") >> 8) == 0)
	{
	  print("cp $recover $logPathClean/recover.txt with success.\n");
	}
	else
	{
	  print("cp $recover $logPathClean/recover.txt FAILED.\n");
	}

	# step 6 : Clean Phone Number daily.
	if ((system($ENV{BASE_PERL_DIR}."/$scriptClean $paramsScript") >> 8) == 0)
	{
	  print("$scriptClean executed.\n");
	}
	else
	{
	  print("ERROR : $scriptClean failed.\n");
  	  if ( defined( $logFile ) ) {
  			close( LOGDAILY );
      }
	if ( defined( $recover ) ) {
  		close( REPRISE );
	}
	  exit(1);
	}

	#step 4.3 : create recover file backup.
	if ((system("cp $recover $logPathNormalisation/recover.txt ") >> 8) == 0)
	{
	  print("cp $recover $logPathNormalisation/recover.txt with success.\n");
	}
	else
	{
	  print("cp $recover $logPathNormalisation/recover.txt FAILED.\n");
	}
	# step 7 : Normalize Phone Number daily.
	if ((system($ENV{BASE_PERL_DIR}."/$scriptNormalisation $paramsScript") >> 8) == 0)
	{
	  print("$scriptNormalisation executed.\n");
	}
	else
	{
	  print("ERROR : $scriptNormalisation failed.\n");

  	  if ( defined( $logFile ) ) {
  			close( LOGDAILY );
      }
	if ( defined( $recover ) ) {
  		close( REPRISE );
	}
	  exit(1);
	}

	# step 8 : Invalidate phone number not normalizable.
	print $ENV{BASE_PERL_DIR}."/$scriptNormInval $dateArgs -C";
	if ((system($ENV{BASE_PERL_DIR}."/$scriptNormInval $dateArgs -C") >> 8) == 0)
	{
	  print("$scriptNormInval executed.\n");
	}
	else
	{
	  print("ERROR : $scriptNormInval failed.\n");

  	  if ( defined( $logFile ) ) {
  			close( LOGDAILY );
      }
	  exit(1);
	}

	# step 9 : Simplification phone number
	print $ENV{BASE_PERL_DIR}."/$scriptSimplification $dateArgs -C";
	if ((system($ENV{BASE_PERL_DIR}."/$scriptSimplification $dateArgs -C") >> 8) == 0)
	{
	  print("$scriptSimplification executed.\n");
	}
	else
	{
	  print("ERROR : $scriptSimplification failed.\n");

  	  if ( defined( $logFile ) ) {
  			close( LOGDAILY );
      }
	  exit(1);
	}

	# step 10 : Set last computing date.
	&enregistrerDate();

	# step 11 : erase cover file.
	if ((system("rm $recover ") >> 8) == 0)
	{
	  print("rm $recover with success.\n");
	}
	else
	{
	  print("rm $recover FAILED.\n");
	}

	print LOGDAILY "nbFB : $nbFB\n";
	print LOGDAILY "nbMYA : $nbMYA\n";
	print LOGDAILY "nbBDC : $nbBDC\n";
	print LOGDAILY "nbNotFound : $nbNotFound\n";


}

# Recuperer la date du dernier traitement en BD.
sub prepareDate()
{
	my $rgDatesConstraints = "";

	my $selectRequest = $dbConnexion->prepare(
    	"SELECT TO_CHAR(DDATE, 'YYYYMMDDHH24MISS'), TO_CHAR(sysdate, 'YYYYMMDDHH24MISS') FROM DATE_REPLIC d WHERE d.STYPE = 'NORMPHONE'");

	if ($selectRequest->execute())
	{
    	while (my @row = $selectRequest->fetchrow_array())
    	{
			if(! defined $phoneNumberArgs::startDate)
			{
                $rgDatesConstraints .= " -startDate ".$row[0];
				$startDate = $row[0];
			}else{
                $rgDatesConstraints .= " -startDate ".$phoneNumberArgs::startDate;
				$startDate =$phoneNumberArgs::startDate;
			}

			if(! defined $phoneNumberArgs::endDate){
				$endDate = $row[1];
                $rgDatesConstraints .= " -endDate ".$row[1];
				$phoneNumberArgs::endDate = $row[1];
			}
			else
			{
				$endDate = $phoneNumberArgs::endDate;
                $rgDatesConstraints .= " -endDate ".$phoneNumberArgs::endDate;
			}
    	}
	}

	print LOGDAILY "INFO : date debut ($startDate) date fin ($endDate)\n" ;
	return $rgDatesConstraints;
}

#manageCountryCode
#sgin : gin de l'individu
#return : countryCode
sub manageCountryCode ($)
{
	my $gin = shift;
	my $countryCode = undef;

	$countryCode = isFB($gin);
	if(! defined $countryCode )
	{
		$countryCode = isMYA($gin);
	}
	else
	{
		++$nbFB;
		print " FB $countryCode\n";
	}
	if(! defined $countryCode )
	{
		$countryCode = isBDC($gin);
	}
	else{
		++$nbMYA;
		print " MYA $countryCode\n";
	}

	if(defined $countryCode)
	{
		++$nbBDC;
		print " BDC $countryCode\n";
	}
	else
	{
		++$nbNotFound;
		print " NOT FOUND\n";
	}

	return $countryCode;
}


#ISFB
# l'utilisateur est-il FB et quelle est son code pays.
# sgin : gin de l'individu dont on cherche le code pays.
# return : countryCode FB.
sub isFB ($)
{
	my $sgin = shift;
	my $countryCode = undef;

	my $selectRequest = $dbConnexion->prepare(
    	"select distinct ad.scode_pays ".
        "from  SIC2.adr_post ad inner join ROLE_CONTRATS rc on rc.sgin=ad.sgin ".
        "inner join usage_mediums um on um.sain_adr=ad.sain ".
		"inner join TELECOMS t on t.sgin=ad.sgin ".
		"where ad.scode_medium in ('D','P') and ad.sgin='$sgin' and ad.sstatut_medium='V' ".
		"and um.srole1='M' and um.scode_application='ISI' and rownum < 2");

	if ($selectRequest->execute())
	{
    	if (my @row = $selectRequest->fetchrow_array())
    	{
        		$countryCode = $row[0];
    	}
	}


	return $countryCode;
}

#ISMYA
# l'utilisateur est-il FB et quelle est son code pays.
# sgin : gin de l'individu dont on cherche le code pays.
# return : countryCode or undef.
sub isMYA ($)
{
	my $sgin = shift;
	my $countryCode = undef;

	my $selectRequest = $dbConnexion->prepare(
	"select ad.enrolement_point_of_sell  ".
	"from account_data ad inner join telecoms t on t.sgin=ad.sgin  ".
	"where ad.fb_identifier is null and ad.account_identifier is not null ".
	"and t.sgin_pm is null and t.sgin is not null and t.snumero is not null and t.sstatut_medium='V' ".
	"and rownum < 2 ".
	"and ad.sgin='$sgin' ");

	if ($selectRequest->execute())
	{
    	if (my @row = $selectRequest->fetchrow_array())
    	{
        		$countryCode = $row[0];
    	}
	}

	return $countryCode;

}


#ISBDC
# l'utilisateur est-il BDC et quelle est son code pays.
# sgin : gin de l'individu dont on cherche le code pays.
# Enregistrer la date du traitement pour le prochain traitement.
# return : countryCode or undef.
sub isBDC ($)
{
	my $sgin = shift;
	my $countryCode = undef;

	my $selectRequest = $dbConnexion->prepare(
	"select ap.scode_pays ".
	"from adr_post ap inner join telecoms t on ap.sgin=t.sgin ".
	"WHERE ap.scode_medium in ('D', 'P') ".
	"and ap.sstatut_medium='V' ".
	"and ap.sgin='$sgin' ".
	"and 0 = (select count(*) from USAGE_MEDIUMS um where um.sain_adr=ap.sain and um.scode_application='BDC') ".
	"ORDER BY ap.ddate_modification DESC "
	);

	if ($selectRequest->execute())
	{
    	if (my @row = $selectRequest->fetchrow_array())
    	{
        		$countryCode = $row[0];
    	}
	}

	return $countryCode;

}
# Enregistrement de la date de dernier dailyCleaning
sub enregistrerDate()
{
        # update the delta date field
        if ($dbConnexion->do("UPDATE DATE_REPLIC SET DDATE = TO_DATE('$phoneNumberArgs::endDate', 'YYYYMMDDHH24MISS') WHERE STYPE = 'NORMPHONE'")) {
            print("replication date updated\n");
        }
        else
        {
            print("ERROR: unable to update replication date: ".$dbConnexion->errstr."\n");
        }
}

###############################################
# Lancement Traitement Principal
###############################################
main();

# Commit changes to database if needed
commitBD($dbConnexion, $phoneNumberArgs::commit);

$dbConnexion->disconnect();
exit( $phoneNumberArgs::error );


if ( defined( $logFile ) ) {
  close( LOGDAILY );
}
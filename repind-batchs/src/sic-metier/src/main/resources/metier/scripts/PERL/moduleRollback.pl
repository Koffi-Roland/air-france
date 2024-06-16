#!/tech/oracle/client/12102/perl/bin/perl -w
###############################################
# Module de connection au base de donnee RI. 
###############################################

#import
use lib;
use strict; 
use DBI;
use IO::File;

# ERROR management 
our $ERROR_ROLLBACK_NOT_EXIST = "ERROR : rollback file doesn't exist.";
our $ERROR_ROLLBACK_AIN_EMPTY = "ERROR : Ain isEmpty in rollback file.";

#####################################
# Gestion du commit
# db : connexion bd.
# commit : 1 commit sinon rollback. 
sub gererRollback($)
{
	my $dbConnection = shift;
	my $file = shift;

    my $indicatif;
    my $codeRegion;
    my $numero;
    my $codePays;
    my $sgin;
    my $sain;

	# ouvrir le fichier
	open(ROLLBACK, "<$file") or die "$ERROR_ROLLBACK_NOT_EXIST\n"; 
    while (my $ligne = <ROLLBACK>){
    	($indicatif,$codeRegion,$numero,$codePays,$sgin, $sain) = split /;/,$ligne;
		restaurer($dbConnection, $indicatif, $codeRegion, $numero, $sain);
    }

    close(ROLLBACK);

}

########################################
# Restaurer les telecoms.
sub restaurer($){

	# recuperer des parametres.
 	my $dbConnection 	= shift;
 	my $indicatif 		= shift;
	my $codeRegion 		= shift;
	my $numero			= shift;
	my $sain			= shift;

	my $requeteUpdate;

	if(defined $sain)
	{
		$requeteUpdate = "UPDATE SIC2.TELECOMS ";

		# Vider les champs normaliser
		$requeteUpdate .= "SET ISNORMALIZED=NULL ";
		$requeteUpdate .= ", SNORMALIZED_NUMERO=NULL";
		$requeteUpdate .= ", SNORMALIZED_COUNTRY=NULL";
		$requeteUpdate .= ", SNORM_NAT_PHONE_NUMBER=NULL";
		$requeteUpdate .= ", SNORM_NAT_PHONE_NUMBER_CLEAN=NULL";
		$requeteUpdate .= ", SNORM_INTER_COUNTRY_CODE=NULL";
		$requeteUpdate .= ", SNORM_INTER_PHONE_NUMBER=NULL";
		$requeteUpdate .= ", SNORM_TERMINAL_TYPE_DETAIL=NULL";
			
		
		# Retablir les champs par defaut.	
		$requeteUpdate .= ", SNUMERO=";
		if(defined $numero){
			$requeteUpdate .= "'".$numero."' ";
		} 
		else
		{
			$requeteUpdate .= "NULL";
		}
	
		$requeteUpdate .= ", SINDICATIF=";
		if(defined $indicatif){
			$requeteUpdate .= "'".$indicatif."' ";
		} 
		else{
			$requeteUpdate .= "NULL";
		}	
	
		$requeteUpdate .= ", SCODE_REGION=";
		if(defined $codeRegion){
			$requeteUpdate .= "'".$codeRegion."' ";
		} 
		else{
			$requeteUpdate .= "NULL";
		}

		$requeteUpdate .= " WHERE SAIN='".$sain."'";

		print ("rattrapage : $sain\n");    	
		my $req = $dbConnection->prepare($requeteUpdate )
     		or die "Couldn't prepare statement: " . $dbConnection->errstr;
    	$req->execute() or die "Couldn't execute statement: " . $req->errstr;

	}
	else
	{
		print "$ERROR_ROLLBACK_AIN_EMPTY\n"; 
	}
}

1;

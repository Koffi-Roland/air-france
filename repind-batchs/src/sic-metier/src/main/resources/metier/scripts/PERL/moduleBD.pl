#!/tech/oracle/client/12102/perl/bin/perl -w
###############################################
# Module de connection au base de donnee RI. 
###############################################

#import
use lib;
use strict; 
use DBI;
use IO::File;

##############################################################
# Connection a la base de donnee SIC de l'environnement courant
sub BdConnexion()
{
	if ( !( `secureSIC \$SECURE_DATABASE_ACCESS_FILE_SIC READSIC` =~ /^([^\/]*)\/([^@]*)@(.*)$/) )
	{
    	print( "ERROR: unable to get database connexion parameters\n" );
    	exit( 1 );
	}
	my $dbConnexion =  DBI->connect( "dbi:Oracle:$3", $1, $2, 
                    { RaiseError => 1, AutoCommit => 0 });

	if ( !$dbConnexion )
	{
  		print( "ERROR: unable to connect to database\n" );
 	 	exit( 1 );
  	}

	print ("Connexion a la base : $3 \n");

	return $dbConnexion; 

}
################################################################################
# Connection a la base de donnee de l'environnement specifie (seulement non UTF8)
sub BdConnexionEnv($)
{
	my ($env) = @_;

	if ( !( `secureSIC \$SECURE_DATABASE_ACCESS_FILE_SIC READSIC` =~ /^([^\/]*)\/([^@]*)@(.*)$/) )
	{
    	print( "ERROR: unable to get database connexion parameters\n" );
    	exit( 1 );
	}
	my $dbConnexion =  DBI->connect( "dbi:Oracle:".$env, $1, $2, 
                    { RaiseError => 1, AutoCommit => 0 });

	if ( !$dbConnexion )
	{
  		print( "ERROR: unable to connect to database\n" );
 	 	exit( 1 );
  	}

	return $dbConnexion; 
}

########################################
# Executer un script
# db : connexion base de donnee
# requete : requete a executer
sub executeSQL($)
{
	my ($db) = shift;
	my ($requete) = shift;
    my $req = $db->prepare($requete ) 
     or die "Couldn't prepare statement: " . $db->errstr;
    $req->execute() or die "Couldn't execute statement: " . $req->errstr;
	return $req;
}

######################################
# Gestion du commit
# db : connexion bd.
# commit : 1 commit sinon rollback. 
sub commitBD($)
{
	my $dbConnexion = shift;
	my $commit = shift;

	# Commit changes to database if needed
	if ( $commit == 1 ){ 
  		$dbConnexion->commit;
		print ("COMMIT\n");
	}
	else{
  		$dbConnexion->rollback;
		print ("ROLLBACK\n");
	}
}
########################################################
# Obligatoire pour le fonctionnement des volumes en Java
"vrai";

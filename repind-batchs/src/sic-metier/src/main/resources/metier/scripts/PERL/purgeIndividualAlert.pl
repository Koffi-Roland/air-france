#!/tech/oracle/client/12102/perl/bin/perl -w
###############################################
# Suppression des alert et de leur données (PromoAlert).
###############################################
#import
use lib ;
use strict; 
use DBI;
use IO::File;

my $programName = $0;
$programName =~ s/^(.*\/)?([^\/]*)$/$2/;
###############################################
# Afficher l'aide

sub displayHelp() {
  print( "usage: $programName [-h] -f file [-t <trace file> ] [-C] \n" );
  print( "where options can be:\n" );
  print( "    -h : displays this help\n" );
  print( "    -d : debug mode\n" );
  print( "    -e : end date (if not, the first day of the last month) 	(format : mm/yyyy )\n" );
  print( "    -t <trace file> : trace file\n" );
  print( "    -C : to COMMIT changes in database\n" );
}

###############################################
# variable global
my $help;
my $traceFile;
my $commit = 0;
my $myDebug = 0;
my $endDate;

my $nbAlert = 0;
my $nbAD = 0;

###############################################
# traiter les arguments
while( $ARGV = shift ){

  if( $ARGV eq "-h" ){
    displayHelp();
    exit( 0 );
  }

  if( $ARGV eq "-t" ){
    $ARGV = shift;
    if( $ARGV eq "" || $ARGV =~ /-/ ){
      displayHelp();
      exit( 0 );
    }
    else{
      $traceFile = $ARGV;
    }
  }

  if( $ARGV eq "-e" ){
    $ARGV = shift;
    if( $ARGV eq "" || !($ARGV =~ /^(\d{2})\/(\d{2})\/(\d{4})$/)){
	  print("error : -e empty or bad format\n");
      displayHelp();
      exit( 0 );
    }
    else{
      $endDate = $ARGV;
    }
  }

  if( $ARGV eq "-d" ){
	$myDebug = 1;
  }
  
  if( $ARGV eq "-C" ){
    $commit = 1;
  }
  
}

if ( $help || @ARGV ) {
  displayHelp();
  exit( 1 );
}

###############################################
#Connexion a la base de donnee
#Open DB connection
if ( !( `secureSIC \$SECURE_DATABASE_ACCESS_FILE_SIC READSIC` =~ /^([^\/]*)\/([^@]*)@(.*)$/) )
{
    print( "ERROR: unable to get database connexion parameters\n" );
    exit( 1 );
}
my $dbConnexion = DBI->connect( "dbi:Oracle:$3", $1, $2, 
                    { RaiseError => 1, AutoCommit => 0 });


#verification de la connexion actuelle
if ( !$dbConnexion )
{
  print( "ERROR: unable to connect to database\n" );
  if ( defined( $traceFile ) ) {
    print TRACE "ERROR: unable to connect to database\n";
  }
  exit( 1 );
}

if($myDebug == 1){
print("connexion bd  OK\n");
}

###############################################
#creation du fichier de log
if ( defined( $traceFile ) ) {
  open( TRACE, ">$traceFile" );
}

###############################################
# Methode principale
# ( suppression market_languages, compref, prospect_localisation, prospect_telecoms)
###############################################
sub main(){

	#open( TRACE, ">$traceFile" );
	my $selReq = "SELECT al.alert_id FROM alert al"; 
	my $whereSelReq = " WHERE al.optin = 'N'"; 
	$whereSelReq .= "OR TRUNC(TO_DATE((SELECT ad.value FROM ALERT_DATA ad WHERE ad.key = 'END_DATE' AND ad.alert_id = al.alert_id), 'DDMMYYYY'), 'MM')";
	$whereSelReq .= " <= TRUNC(ADD_MONTHS(sysdate,-1), 'MM')"; 
 
	my $selectRequest = "$selReq"."$whereSelReq";

	if($myDebug == 1){
		print ($selectRequest);
	}
	my $reqAL = $dbConnexion->prepare( $selectRequest ) or die "Couldn't prepare statement: " . $dbConnexion->errstr;
	$reqAL->execute() or die "Couldn't execute statement: " . $reqAL->errstr;

	# retrieve the response
	my $data= $reqAL->fetchall_arrayref();
	my $rows = $reqAL->rows();
	$reqAL->finish;
	my $i;

	if($myDebug == 1){
		print("Nombre d'Alertes a traiter  : $rows \n");
	}
	for $i ( 0 .. ($rows-1) )
  	{
		if($myDebug == 1 && $i % 300 == 0 && $i != 0)
		{
			print "Alert data de l'alerte $i supprime(s) \n";
		}
		eraseAlertData($data->[$i][0]);
		
		
		if($myDebug == 1 && $i % 300 == 0 && $i != 0)
		{
			print "Alerte $i supprime(s) \n";
		}
		eraseAlert($data->[$i][0]);

		$nbAlert++;		

	}
}
###############################################
# Suppression des alertes data. 
# @param : alert_id 
###############################################
sub eraseAlertData($) {
  	#traitement des arguments 
	my ($alert_id) = @_;
	my $del = "delete from ALERT_DATA where alert_id='$alert_id'";
	my $req = $dbConnexion->prepare( $del ) or die "Couldn't prepare statement: " . $dbConnexion->errstr;
	$req->execute()	or die "Couldn't execute statement: " . $req->errstr;
	$req->finish;
	if($myDebug == 1){
		print("Request Alert Data : " . $del . "\n Deleted Alert_data for : " . $alert_id . "\n");
	}
	
}



###############################################
# Suppression des alertes. 
# @param : alert_id 
###############################################
sub eraseAlert($) {
  	#traitement des arguments 
	my ($alert_id) = @_;
	my $del = "delete from ALERT where alert_id='$alert_id'";
	my $req = $dbConnexion->prepare( $del ) or die "Couldn't prepare statement: " . $dbConnexion->errstr;
	$req->execute() or die "Couldn't execute statement: " . $req->errstr;
	$req->finish;
	if($myDebug == 1){
		print("Request Alert : " . $del . "\n Deleted Alert : " . $alert_id . "\n");
	}
}


###############################################
# Traitement Principale
###############################################
main();
my $error = 0;

# Commit changes to database if needed
if ( $commit == 1 ){
  $dbConnexion->commit;
  if ( defined( $traceFile ) ) {
    print TRACE "main : changes commited \n";
  }
}
else{
  $dbConnexion->rollback;
  if ( defined( $traceFile ) ) {
    print TRACE "main : changes NOT commited \n";
  }
}

if ( defined( $traceFile ) ) {
  print TRACE "nombre d'alertes Language 		traite : $nbAlert  \n";
}

if ( defined( $traceFile ) ) {
  close( TRACE );
}

$dbConnexion->disconnect();
exit( $error );

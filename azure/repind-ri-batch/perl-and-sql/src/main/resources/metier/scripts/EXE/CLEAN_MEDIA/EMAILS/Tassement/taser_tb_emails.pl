#!/tech/oracle/client/12102/perl/bin/perl -w

use DBI;
use IO::File;

my $SEPARATOR = ";";
#my $OUTPUT_DIR = "/app/SIC/data/MIGRATION_MYACCOUNT/";
my $OUTPUT_DIR = "";

my $dbConnexion;

my $error = 0;
my $nbErrors = 0;
my $nbUpdated = 0;
my $modeTrace = 0;
my $modeCommit = 0;

my $programName = $0;
$programName =~ s/^(.*\/)?([^\/]*)$/$2/;

##################################################
# Fonctions declaration
##################################################
#
# Usage
sub usage() {
  print("USAGE: $programName <file_name> [-t] [-C]\n");
  print("    <file_name>: Name of the file containing list of GIN\n");
  print("    -t: trace on stdout, default: trace in a file\n");
  print("    -C: COMMIT transaction on DB\n");
  print("Generate a trace file\n");
  print("All output files are written in the directory where the batch is called\n");
}
print ("\n\n ####### Traces d'execution de suppresion des tabulations dans les emails ##### \n");
#
# Database administration
# Connect
sub connectToDB () {
  if (!(`secureSIC \$SECURE_DATABASE_ACCESS_FILE_SIC READSIC` =~ /^([^\/]*)\/([^@]*)@(.*)$/))
    {
      print TRACE ("ERROR: unable to get database connexion parameters\n");
      exit(1);
    }
  $dbConnexion = DBI->connect("dbi:Oracle:$3", $1, $2, { RaiseError => 1, AutoCommit => 0 });
  print TRACE ("Connecting to DB : $3 as $1\n");
  if (!$dbConnexion)
    {
      print TRACE ("ERROR: unable to connect to database\n");
      exit(1);
    }
  return $dbConnexion;
}

#
# Disconnect
sub disconnectToDB {
  $dbConnexion->disconnect();
}

#
# Get the Today date in the format DDMMYYYY
sub getTodayDate_DDMMYYYY() {
  my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime(time);
  my $month = $mon+1;
  if ($mday >= 1 && $mday <= 9) {
    $mday = "0".$mday;
  }
  if ($month >= 1 && $month <= 9) {
    $month = "0".$month;
  }
  return $mday."".$month."".(1900+$year);
}
##################################################

if ($#ARGV eq 0) {
  usage();
  exit(1);
}
# Get input
my $file = $ARGV[0];

# Options managment
if($#ARGV == 1){
if ($ARGV[1] eq "-C"){
	$modeCommit = 1;
	}
	if ($ARGV[1] eq "-t") {
	$modeTrace = 1;
  }
}
if($#ARGV == 2){
if ($ARGV[1] eq "-t") {
  $modeTrace = 1;
  if ($ARGV[2] eq "-C") {
    $modeCommit = 1;
  }
} else {
  if ($ARGV[1] eq "-C") {
    $modeCommit = 1;
    if ($ARGV[2] eq "-t") {
      $modeTrace = 1;
    }
  }
}
}


# Controle les parametres
# Code Pays existe
my $resultFile = $file."_result_tab_".getTodayDate_DDMMYYYY();
my $rejectFile = $file."_rej_tab_".getTodayDate_DDMMYYYY();
my $traceFile = $file."_trace_tab_".getTodayDate_DDMMYYYY();
my $nbLignes = 0;
my $nbLignesToDispl = 0;
my $NB_LIGNES_DISPLAY = 50;

# Trace mode
if ($modeTrace) {
  open(TRACE, ">&STDOUT") || die ("Error during opening STDOUT");
} else {
  open(TRACE, ">".$OUTPUT_DIR.$traceFile) || die ("Error during opening ".$traceFile);
}

# Fichier existe

open(INPUT_FILE, $file) || die ("Error during opening file " . $file);
open(REJECT, ">".$OUTPUT_DIR.$rejectFile) || die ("Error during opening reject file " . $rejectFile);
print TRACE ("OPTIONS : file name: $file, mode trace: $modeTrace , mode commit: $modeCommit\n");

connectToDB();

my $code_medium = "";
my $no_usage = "";

while (my $line = <INPUT_FILE>)
  {
	$line =~ s/\r|\n//g;
	if($line ne ""){
	my $sgin2 ="";
    $nbLignes += 1;
    $nbLignesToDispl += 1;
	my @values = split(';', $line);
    my $sgin = $values[0];
    print TRACE ("--> $line\n");

    my $sRequest = "select SAIN from emails where sain='".$sgin."' and (semail like '% %' or semail like '%'||CHR(9)||'%') and sstatut_medium='V'";
	#print TRACE ($sRequest);
    my $selectRqt = $dbConnexion->prepare($sRequest);
    if ($selectRqt->execute()) {
      while (my @rowGin = $selectRqt->fetchrow_array()) {

	$sgin2 = $rowGin[0];

	print TRACE ("    GIN : $sgin2\n");

	# Update SigntureModif
	$requestUpdate = "update emails set semail=REPLACE(SEMAIL,CHR(9),'' ) , SSITE_MODIFICATION='VLB',SSIGNATURE_MODIFICATION='RetraitBlanc',DDATE_MODIFICATION=sysdate
	where sain='".$sgin2."'";
	print TRACE (" requete: ".$requestUpdate."\n");
	my $nb = 0;
	if ($nb = $dbConnexion->do($requestUpdate)) {
	  if ($nb=="1") {
	    print TRACE ("    Update AccountData successfull\n");
	    $nbUpdated = $nbUpdated + 1;
	  } else {
	    print TRACE ("ERROR: unable to update\n");
	    print REJECT $line;
	    $nbErrors = $nbErrors + 1;
	  }
	} else {
	  print TRACE ("ERROR: unable to update: ".$dbConnexion->errstr."\n");
	  print REJECT $line;
	  $nbErrors = $nbErrors + 1;
	}
      }
      if ($sgin2 eq "") {
	print TRACE ("   No result found\n");
	$nbErrors += 1;
	print REJECT ("$line\n");
      }
    } else {
      print TRACE ("    Error during request execution.\n");
      $nbErrors += 1;
      print REJECT ("$line\n");
    }
    if ($nbLignesToDispl eq $NB_LIGNES_DISPLAY) {
      # Transaction managment
      if ($modeCommit) {
	$dbConnexion->commit();
	print TRACE ("COMMIT !\n");
      } else {
	$dbConnexion->rollback();
	print TRACE ("ROLLBACK !\n");
      }
      $nbLignesToDispl = 0;
      print ("* $nbLignes SGINs processed.\n");
    }
	}
  }
print ("END\n--\n");
if (!$modeTrace) {
  print ("$nbUpdated/$nbLignes updated");
  if ($nbErrors gt 0) {
    print (" with $nbErrors errors.");
  } else {
    print (".");
  }
}
print TRACE ("$nbUpdated/$nbLignes updated");
if ($nbErrors gt 0) {
  print TRACE (" with $nbErrors errors.\n");
} else {
  print TRACE (".\n");
}
print (" See logs for details.\n");
close(INPUT_FILE);
close(REJECT);

# Transaction managment
if ($modeCommit) {
  $dbConnexion->commit();
  print TRACE ("COMMIT !\n");
  print ("COMMIT !\n");
} else {
  $dbConnexion->rollback();
  print TRACE ("ROLLBACK !\n");
  print ("ROLLBACK !\n");
}

# Close connexion
disconnectToDB();

# Delete Reject file if no errors
if ($nbErrors == 0) {
  # Suppress the reject file
  if (!unlink($rejectFile)) {
    $error = 1;
  }
} else {
  print TRACE ("Errors are detecting on some lines, please have a look to ".$rejectFile."\n");
}
close(TRACE);
close(REJECT);
exit($error);
#!/tech/oracle/client/12102/perl/bin/perl -w
use lib ;

use strict;

use DBI;
use Getopt::Long;
use Time::Piece;
use Time::Seconds;

$0 =~ /^((.*)\/)?([^\/]*)$/;

sub displayHelp()
{
    print("usage: manageReplicationSic {-h | -d | -g}\n");
    print("where option can be:\n");
    print("    -h : displays this help\n");
    print("    -d : replicates delta\n");
    print("    -g : replicates global\n");
}
my $help;
my $delta;
my $global;
if (!GetOptions (
    "h" => \$help,
    "d" => \$delta,
    "g" => \$global))
{
    displayHelp();
    exit(1);
}
if ($help || @ARGV)
{
    displayHelp();
    exit(1);
}

if (((!defined($delta)) && (!defined($global))) || (defined($delta) && defined($global)))
{
    print("ERROR: one and only one type of replication must be set (-d or -g)\n");
    displayHelp();
    exit(1);
}

if (!(`secureSIC \$SECURE_DATABASE_ACCESS_FILE_SIC READSIC` =~ /^([^\/]*)\/([^@]*)@(.*)$/))
{
    print("ERROR: unable to get database connexion parameters\n");
    exit(1);
}
my $dbConnexion = DBI->connect("dbi:Oracle:$3", $1, $2);
if (!$dbConnexion)
{
    print("ERROR: unable to connect to database\n");
    exit(1);
}

my $error = 0;

#  get the start and end dates
my $selectRequest = $dbConnexion->prepare(
    "SELECT TO_CHAR(DDATE, 'YYYYMMDDHH24MISS'), TO_CHAR(sysdate, 'YYYYMMDDHH24MISS') FROM DATE_REPLIC d WHERE d.STYPE = 'GEN'");
my $rgDatesConstraints = "";
my $endDate;
my $effectiveEndDate;
if ($selectRequest->execute())
{
    while (my @row = $selectRequest->fetchrow_array())
    {
        if (defined($delta))
        {
            $rgDatesConstraints .= " -startDate ".$row[0];
        }

        $endDate = $row[1];
        if (defined($global))
        {
        	# in case this is a global replication: the endDate is set to the day after
            my $format = "%Y%m%d%H%M%S";
            $effectiveEndDate = (Time::Piece->strptime($endDate, $format) + ONE_DAY)->strftime($format);
        }
        else
        {
            $effectiveEndDate = $endDate;
        }
        $rgDatesConstraints .= " -endDate ".$effectiveEndDate;
    }
    if (!defined($endDate))
    {
        print("ERROR: no record found in DATE_REPLIC table for STYPE = 'GEN'\n");
        $error = 1;
    }
}
else
{
    print("ERROR: cannot execute request: ".$selectRequest->errstr."\n");
    $error = 1;
}
$selectRequest->finish();

my $endDateWithoutSeconds;
my $deltaGlobalString;
my $dataPath;

if (!$error)
{
    $endDate =~ /^(.*)..$/;
    $endDateWithoutSeconds = $1;
    $deltaGlobalString = defined($delta)?"MVT":"GLOBAL";
    $dataPath = $ENV{BASE_DATA_DIR}."/REPLICATION_GENERIQUE/$deltaGlobalString/$deltaGlobalString-$endDateWithoutSeconds";

    # SC - Make a purge of the last GLOBAL generic replication files
    if (!defined($delta)) {
      print("-----------------------\n");
      print("Purge for GLOBAL replication of directory:\n");
      my $REP_RG_GLOBAL = $ENV{BASE_DATA_DIR}."/REPLICATION_GENERIQUE/GLOBAL";
      my $cmd = "du -sk $REP_RG_GLOBAL/GLOBAL* 2>/dev/null";
      my @resultLine = split('\n',`$cmd`);
      my $nbLines = 0;
      my $line = "";
      foreach $line (@resultLine) {
	# Split Size and directory name
	my @infos = split(' ', $line);
	my $dirSize = $infos[0];
	my $dirName = $infos[1];
	if ($dirSize > 40000) {
	  $nbLines = $nbLines + 1;
	  print ("$dirName\n");
	  foreach(<$dirName/GLOBAL-*.txt.gz>) {
	    if (!unlink($_)) {
	      $error = 1;
	    }
	  }
	  if (!$error) {
	    print("Purge Successfully Done.\n");
	  } else {
	    print("ERROR during the purge.\n");
	  }
	}
      }
      if ($nbLines == 0) {
	print("Nothing to purge!\n");
      }
      print ("-----------------------\n");
    }
    # End of purge
    
    # Create directory
    if ((system("mkdir -p $dataPath") >> 8) == 0)
    {
        print("data path: '$dataPath'\n");
    }
    else
    {
        print("ERROR: data directory '$dataPath' cannot be created\n");
        $error = 1;
    }
}

sub launchOneReplication($)
{
    my $nbEssai = 5;
    my $oneFile = $_[0];
    my $notificationFile = "$dataPath/$oneFile.done";
    my $stdErrFile = "$dataPath/replicStdErr.log";
    my $cmd = "(".$ENV{BASE_EXE_DIR}."/REPLICATION_GENERIQUE/rg.pl -r $oneFile$rgDatesConstraints -notify $notificationFile | gzip > $dataPath/$deltaGlobalString-$endDateWithoutSeconds.$oneFile.txt.gz) 3>&2 2>&1 1>&3 | tee $stdErrFile";
    my $findOraError = "grep -i ora- $stdErrFile > /dev/null";

    if($oneFile eq "2000")
    {
       $ENV{NLS_LANG} = "AMERICAN_AMERICA.WE8ISO8859P15";
       print("\nConfig UTF8 ok\n");
    }

    print("Launch replication of file $oneFile\n");
    system($cmd);
    while ((((system($findOraError) >> 8) == 0) || (not -e $notificationFile)) && ($nbEssai > 0)) {
      # ko: we re-launch the process
      print("file $oneFile replication failed: we re-launch it ( $nbEssai attempt(s) left )\n");
      --$nbEssai;
      system($cmd);
    }
    if (-e $notificationFile && ((system($findOraError) >> 8) != 0) ) {
      # ok: the extraction worked fine
      print("file $oneFile replicated\n");
      unlink($notificationFile);
    } else {
      # ko: we re-launch the process
      print("file $oneFile replication failed: attempt number reached\n");
      unlink($notificationFile);
      $error = 1;
    }
    unlink($stdErrFile);
}

my @allFiles = 
  ("0100", "0101", "0110", "0111", "0115", "0120", "0125", "0126", 
   "0123", "0124",
   "0127", "0128", 
   "0121", "0130", "0140", "0150", "0160", "0170", "0180", "0190",
   "0200", "0201", "0210", "0211", "0212", "0220", "0230", "0240", "0241", "0242", "0250", "0251", "0260", "0261","0270","0280", "0290",
   "0300", "0310", "0320", "0330", "0340","0350", "0360",
   "0400", "0410", "0420", "0430", "0440", "0480", "0490",
   "0820", "0830", "0840", "0860", "0910", "0920", "0930", "0940", "0950", "0960", "0970", "0980", "0990", "0991",
   "0992","0993", "0994", "0995", "0996", "997", "998", "999",
   "1000", "1001", "1002", "1003", "1004", "1005", "1006", "1007", "1008", "1009", "1010", "1011", "1012", "1013","1014","1015"
#   );
   ,
   "2000", "2100", "2200", "2300", "2400", "2500", "2600");

if (!$error)
{
#    my %files;

    # launch all replications
    foreach my $oneFile (@allFiles)
    {
        launchOneReplication($oneFile);
    }

#    system($ENV{BASE_EXE_DIR}."/REPLICATION/replicationDelorean.pl$rgDatesConstraints");

    # make the checksum of all replication files
    if ((system("cd $dataPath ; /usr/bin/md5sum * > md5sum.txt") >> 8) == 0)
    {
        print("md5 sum done\n");
    }
    else
    {
        print("ERROR while creating md5 checksum file '$dataPath/md5sum.txt'\n");
        $error = 1;
    }
}

if (!$error)
{
    if (defined($delta))
    {
        # update the delta date field
        if ($dbConnexion->do("UPDATE DATE_REPLIC SET DDATE = TO_DATE('$endDate', 'YYYYMMDDHH24MISS') WHERE STYPE = 'GEN'"))
        {
            print("replication date updated\n");
        }
        else
        {
            print("ERROR: unable to update replication date: ".$dbConnexion->errstr."\n");
            $error = 1;
        }
    }
}

if (!$error)
{
    # finally, create the OK file
    if ((system("touch $dataPath/OK.txt") >> 8) == 0)
    {
        print("OK file created\n");
    }
    else
    {
        print("ERROR while creating OK file '$dataPath/OK.txt'\n");
        $error = 1;
    }
}


if ((system($ENV{BASE_EXE_DIR}."/REPLICATION_GENERIQUE/manageReplicationSicSending.pl".(defined($delta)?" -d":" -g")." -endDate $endDate &") >> 8) == 0)
  {
    print("Sending Replication files\n");
  }
else
  {
    print("ERROR: Could not send files \n");
    $error = 1;
  }

print("Disconnect from Database.\n");

$dbConnexion->disconnect();

print("End. Return : $error\n");
exit($error);

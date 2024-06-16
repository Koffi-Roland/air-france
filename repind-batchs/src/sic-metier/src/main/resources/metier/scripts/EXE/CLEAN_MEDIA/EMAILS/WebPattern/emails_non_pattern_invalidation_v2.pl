#!/tech/oracle/client/12102/perl/bin/perl -w

use strict;

use DBI;
use Getopt::Long;

my $programName = $0;
my $modeCommit = 0;
my $nbUpdated = 0;
my $modeTrace = 0;
my $cle ="";
$programName =~ s/^(.*\/)?([^\/]*)$/$2/;

sub displayHelp()
{
    print("usage: $programName {-h | -f <file>}\n");
    print("where option can be:\n");
    print("    -h : displays this help\n");
    print("    -f <input file> : file containing sgin\n");
	print("    -C: COMMIT transaction on DB\n");
}
print ("\n\n ####### Traces d'execution d'invalisation des emails ne respectant pas le webpattern ##### \n");
my $help;
my $fichierSGIN;
if (!GetOptions (
    "h" => \$help,
    "f=s" => \$fichierSGIN,
	"-C" => \$modeCommit ,
	"-t" => \$modeTrace ))
{
    displayHelp();
    exit(1);
}

if ($help || @ARGV)
{
    displayHelp();
    exit(1);
}

if ((!defined($fichierSGIN)))
{
    print("ERROR: input file must be provide\n");
    displayHelp();
    exit(1);
}

if (!(`secureSIC \$SECURE_DATABASE_ACCESS_FILE_SIC READSIC` =~ /^([^\/]*)\/([^@]*)@(.*)$/))
{
    print("ERROR: unable to get database connexion parameters\n");
    exit(1);
}
my $dbConnexion = DBI->connect("dbi:Oracle:$3", $1, $2,
			{
                          RaiseError => 1,
                          AutoCommit => 0
                        }
);
if (!$dbConnexion)
{
    print("ERROR: unable to connect to database\n");
    exit(1);
}

# Perl trim function to remove whitespace from the start and end of the string
sub trim($)
{
	my $string = shift;
	$string =~ s/^\s+//;
	$string =~ s/\s+$//;
	return $string;
}


open(SGIN_FILE, "<$fichierSGIN");
 while (<SGIN_FILE>)
 {
    my($cle) = $_;
    chomp($cle);
	if($cle ne ""){
	my @values = split(';',$cle);
	$cle=$values[0];
    #$cle =~ s/;//;
    print "sain : $cle\n";

	#requjte sur caracthres spiciaux
    my $requete = q{
		update sic2.emails set
		SSTATUT_MEDIUM='I' , SSITE_MODIFICATION='VLB',SSIGNATURE_MODIFICATION='invalidPattern',DDATE_MODIFICATION=sysdate
		WHERE SAIN = ?
    };
    my $sth = $dbConnexion->prepare_cached($requete)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	$sth->finish;
	print "invalidation ok : $cle\n";

	$nbUpdated = $nbUpdated + 1;
	}
	print ("* $nbUpdated SGINs processed.\n");
 }

my $error = 0;

close(SGIN_FILE);

if ($modeCommit) {
  $dbConnexion->commit();
  print ("COMMIT !\n");
}
else {
  $dbConnexion->rollback();
  print ("ROLLBACK !\n");
}
$dbConnexion->disconnect();

exit($error);
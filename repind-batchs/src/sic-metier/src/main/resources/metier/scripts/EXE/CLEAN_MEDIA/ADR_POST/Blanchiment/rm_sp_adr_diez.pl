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

#####################################################
	#requjte sur caracthres spiciaux SCOMPLEMENT_ADRESSE
	#requjte sur accentuations
	my $requete2 = q{
		UPDATE ADR_POST SET
		SCOMPLEMENT_ADRESSE=TRANSLATE(SCOMPLEMENT_ADRESSE,CHR(35),
		' ')
		WHERE SAIN = ?
    };
	my $sth = $dbConnexion->prepare_cached($requete2)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#$sth->finish;
	print "remplacement accentuation SCOMPLEMENT_ADRESSE ok : $cle\n";


#####################################################
	#requjte sur caracthres spiciaux SNO_ET_RUE
	#requjte sur accentuations
	my $requete4 = q{
		UPDATE ADR_POST SET
		SNO_ET_RUE=TRANSLATE(SNO_ET_RUE,CHR(35),
		' ')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete4)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#$sth->finish;
	print "remplacement accentuation SNO_ET_RUE ok : $cle\n";
#####################################################
	#requjte sur caracthres spiciaux SLOCALITE
	#requjte sur accentuations
	my $requete6 = q{
		UPDATE ADR_POST SET
		SLOCALITE=TRANSLATE(SLOCALITE,CHR(35),
		' ')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete6)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#$sth->finish;
	print "remplacement accentuation SLOCALITE ok : $cle\n";
#####################################################
	#requjte sur caracthres spiciaux SVILLE
	#requjte sur accentuations
	my $requete8 = q{
		UPDATE ADR_POST SET
		SVILLE=TRANSLATE(SVILLE,CHR(35),
		' ')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete8)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#$sth->finish;
	print "remplacement accentuation SVILLE ok : $cle\n";
#####################################################
	#requjte sur caracthres spiciaux SCODE_POSTAL
	#requjte sur accentuations
	my $requete10 = q{
		UPDATE ADR_POST SET
		SCODE_POSTAL=TRANSLATE(SCODE_POSTAL,CHR(35),
		' ')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete10)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#$sth->finish;
	print "remplacement accentuation SCODE_POSTAL ok : $cle\n";
#####################################################
	#requjte sur caracthres spiciaux SCODE_PROVINCE
	#requjte sur accentuations
	my $requete12 = q{
		UPDATE ADR_POST SET
		SCODE_PROVINCE=TRANSLATE(SCODE_PROVINCE,CHR(35),
		' ')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete12)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#$sth->finish;
	print "remplacement accentuation SCODE_PROVINCE ok : $cle\n";
#####################################################
	#requjte sur caracthres spiciaux SRAISON_SOCIALE
	#requjte sur accentuations
	my $requete13 = q{
		UPDATE ADR_POST SET
		SRAISON_SOCIALE=TRANSLATE(SRAISON_SOCIALE,CHR(35),
		' ')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete13)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#$sth->finish;
	print "remplacement accentuation SRAISON_SOCIALE ok : $cle\n";
################# signature sans invalidation
	my $requete50 = q{
		UPDATE ADR_POST SET
		SSITE_MODIFICATION='VLB',SSIGNATURE_MODIFICATION='TransfDiez',DDATE_MODIFICATION=sysdate
		WHERE SAIN = ?
	};
    $sth = $dbConnexion->prepare_cached($requete50)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	$sth->finish;
	print "Signature ok : $cle\n";

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
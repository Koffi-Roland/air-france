#!/tech/oracle/client/12102/perl/bin/perl -w

use strict;

use DBI;
use Getopt::Long;

my $programName = $0;
my $modeCommit = 0;
my $modeTrace=0;
my $nbUpdated = 0;
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

print ("\n\n ####### Traces d'execution de suppresion des caract�res sp�ciaux dans les emails ##### \n");
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

	#requ�te sur caract�res sp�ciaux
    my $requete = q{
		UPDATE EMAILS SET
		SEMAIL=TRANSLATE(SEMAIL, CHR(123)||CHR(124)||CHR(125)||CHR(126)||CHR(33)||CHR(35)||CHR(176)||CHR(1)||CHR(2)||CHR(3)||CHR(4)||CHR(5)||CHR(6)||CHR(7)||CHR(8)||CHR(9)||CHR(10)||CHR(11)||CHR(12)||CHR(13)||CHR(14)||CHR(15)||CHR(16)||CHR(17)||CHR(18)||CHR(19)||CHR(20)||CHR(21)||CHR(22)||CHR(23)||CHR(24)||CHR(25)||CHR(26)||CHR(27)||CHR(28)||CHR(29)||CHR(30)||CHR(31)||CHR(32)||CHR(34)||CHR(91)||CHR(92)||CHR(93)||CHR(94)||CHR(96)||CHR(127)||CHR(128)||CHR(129)||CHR(130)||CHR(131)||CHR(132)||CHR(133)||CHR(134)||CHR(135)||CHR(136)||CHR(137)||CHR(138)||CHR(139)||CHR(140)||CHR(141)||CHR(142)||CHR(143)||CHR(144)||CHR(145)||CHR(146)||CHR(147)||CHR(148)||CHR(149)||CHR(150)||CHR(151)||CHR(152)||CHR(153)||CHR(154)||CHR(155)||CHR(156)||CHR(157)||CHR(158)||CHR(159)||CHR(160)||CHR(161)||CHR(162)||CHR(163)||CHR(164)||CHR(165)||CHR(166)||CHR(167)||CHR(168)||CHR(169)||CHR(170)||CHR(171)||CHR(172)||CHR(173)||CHR(174)||CHR(175)||CHR(177)||CHR(178)||CHR(179)||CHR(180)||CHR(181)||CHR(182)||CHR(183)||CHR(184)||CHR(185)||CHR(186)||CHR(187)||CHR(188)||CHR(189)||CHR(190)||CHR(191)||CHR(198)||CHR(215)||CHR(216)||CHR(222)||CHR(223)||CHR(230)||CHR(240)||CHR(247)||CHR(248)||CHR(254),
		'                                                                                                                       ')
		WHERE SAIN = ?
    };
    my $sth = $dbConnexion->prepare_cached($requete)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	$sth->finish;
	print "netoyage caracteres speciaux ok : $cle\n";


	#################
	my $requete3 = q{
		UPDATE EMAILS SET
		SSTATUT_MEDIUM='I' , SSITE_MODIFICATION='VLB',SSIGNATURE_MODIFICATION='InvalCarSpecial',DDATE_MODIFICATION=sysdate
		WHERE SAIN = ?
	};
    $sth = $dbConnexion->prepare_cached($requete3)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	$sth->finish;
	print "Invalidation ok : $cle\n";

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
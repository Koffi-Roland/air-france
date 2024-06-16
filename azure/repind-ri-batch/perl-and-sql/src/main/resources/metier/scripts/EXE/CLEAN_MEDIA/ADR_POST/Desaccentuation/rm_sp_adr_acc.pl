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
print ("\n\n ####### Traces d'execution de netoyage et d'invalisation des adresse postales (ISIS)##### \n");
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
		SCOMPLEMENT_ADRESSE=TRANSLATE(SCOMPLEMENT_ADRESSE, CHR(192)||CHR(193)||CHR(194)||CHR(195)||CHR(196)||CHR(197)||CHR(199)||CHR(200)||CHR(201)||CHR(202)||CHR(203)||CHR(204)||CHR(205)||CHR(206)||CHR(207)||CHR(208)||CHR(209)||CHR(210)||CHR(211)||CHR(212)||CHR(213)||CHR(214)||CHR(217)||CHR(218)||CHR(219)||CHR(220)||CHR(221)||CHR(224)||CHR(225)||CHR(226)||CHR(227)||CHR(228)||CHR(229)||CHR(231)||CHR(232)||CHR(233)||CHR(234)||CHR(235)||CHR(236)||CHR(237)||CHR(238)||CHR(239)||CHR(240)||CHR(241)||CHR(242)||CHR(243)||CHR(244)||CHR(245)||CHR(246)||CHR(249)||CHR(250)||CHR(251)||CHR(252)||CHR(253)||CHR(255),
		'AAAAAACEEEEIIIIDNOOOOOUUUUYAAAAAACEEEEIIIIDNOOOOOUUUUYY')
		WHERE SAIN = ?
    };
	my $sth = $dbConnexion->prepare_cached($requete2)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement AE
	my $requete22 = q{
	UPDATE ADR_POST SET
	scomplement_adresse=REPLACE(scomplement_adresse, CHR(198), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete22)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement ae
	my $requete23 = q{
	update adr_post set
	scomplement_adresse=REPLACE(scomplement_adresse, CHR(230), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete23)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement OE
	my $requete24 = q{
	update adr_post set
	scomplement_adresse=REPLACE(scomplement_adresse, CHR(140), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete24)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement oe
	my $requete25 = q{
	update adr_post set
	scomplement_adresse=REPLACE(scomplement_adresse, CHR(156), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete25)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	print "remplacement accentuation SCOMPLEMENT_ADRESSE ok : $cle\n";


#####################################################
	#requjte sur caracthres spiciaux SNO_ET_RUE
	#requjte sur accentuations
	my $requete4 = q{
		UPDATE ADR_POST SET
		SNO_ET_RUE=TRANSLATE(SNO_ET_RUE, CHR(192)||CHR(193)||CHR(194)||CHR(195)||CHR(196)||CHR(197)||CHR(199)||CHR(200)||CHR(201)||CHR(202)||CHR(203)||CHR(204)||CHR(205)||CHR(206)||CHR(207)||CHR(208)||CHR(209)||CHR(210)||CHR(211)||CHR(212)||CHR(213)||CHR(214)||CHR(217)||CHR(218)||CHR(219)||CHR(220)||CHR(221)||CHR(224)||CHR(225)||CHR(226)||CHR(227)||CHR(228)||CHR(229)||CHR(231)||CHR(232)||CHR(233)||CHR(234)||CHR(235)||CHR(236)||CHR(237)||CHR(238)||CHR(239)||CHR(240)||CHR(241)||CHR(242)||CHR(243)||CHR(244)||CHR(245)||CHR(246)||CHR(249)||CHR(250)||CHR(251)||CHR(252)||CHR(253)||CHR(255),
		'AAAAAACEEEEIIIIDNOOOOOUUUUYAAAAAACEEEEIIIIDNOOOOOUUUUYY')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete4)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement AE
	my $requete42 = q{
	update adr_post set
	SNO_ET_RUE=REPLACE(SNO_ET_RUE, CHR(198), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete42)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement ae
	my $requete43 = q{
	update adr_post set
	SNO_ET_RUE=REPLACE(SNO_ET_RUE, CHR(230), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete43)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement OE
	my $requete44 = q{
	update adr_post set
	SNO_ET_RUE=REPLACE(SNO_ET_RUE, CHR(140), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete44)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement oe
	my $requete45 = q{
	update adr_post set
	SNO_ET_RUE=REPLACE(SNO_ET_RUE, CHR(156), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete45)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	print "remplacement accentuation SNO_ET_RUE ok : $cle\n";
#####################################################
	#requjte sur caracthres spiciaux SLOCALITE
	#requjte sur accentuations
	my $requete6 = q{
		UPDATE ADR_POST SET
		SLOCALITE=TRANSLATE(SLOCALITE, CHR(192)||CHR(193)||CHR(194)||CHR(195)||CHR(196)||CHR(197)||CHR(199)||CHR(200)||CHR(201)||CHR(202)||CHR(203)||CHR(204)||CHR(205)||CHR(206)||CHR(207)||CHR(208)||CHR(209)||CHR(210)||CHR(211)||CHR(212)||CHR(213)||CHR(214)||CHR(217)||CHR(218)||CHR(219)||CHR(220)||CHR(221)||CHR(224)||CHR(225)||CHR(226)||CHR(227)||CHR(228)||CHR(229)||CHR(231)||CHR(232)||CHR(233)||CHR(234)||CHR(235)||CHR(236)||CHR(237)||CHR(238)||CHR(239)||CHR(240)||CHR(241)||CHR(242)||CHR(243)||CHR(244)||CHR(245)||CHR(246)||CHR(249)||CHR(250)||CHR(251)||CHR(252)||CHR(253)||CHR(255),
		'AAAAAACEEEEIIIIDNOOOOOUUUUYAAAAAACEEEEIIIIDNOOOOOUUUUYY')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete6)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement AE
	my $requete62 = q{
	update adr_post set
	SLOCALITE=REPLACE(SLOCALITE, CHR(198), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete62)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement ae
	my $requete63 = q{
	update adr_post set
	SLOCALITE=REPLACE(SLOCALITE, CHR(230), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete63)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement OE
	my $requete64 = q{
	update adr_post set
	SLOCALITE=REPLACE(SLOCALITE, CHR(140), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete64)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement oe
	my $requete65 = q{
	update adr_post set
	SLOCALITE=REPLACE(SLOCALITE, CHR(156), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete65)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	print "remplacement accentuation SLOCALITE ok : $cle\n";
#####################################################
	#requjte sur caracthres spiciaux SVILLE
	#requjte sur accentuations
	my $requete8 = q{
		UPDATE ADR_POST SET
		SVILLE=TRANSLATE(SVILLE, CHR(192)||CHR(193)||CHR(194)||CHR(195)||CHR(196)||CHR(197)||CHR(199)||CHR(200)||CHR(201)||CHR(202)||CHR(203)||CHR(204)||CHR(205)||CHR(206)||CHR(207)||CHR(208)||CHR(209)||CHR(210)||CHR(211)||CHR(212)||CHR(213)||CHR(214)||CHR(217)||CHR(218)||CHR(219)||CHR(220)||CHR(221)||CHR(224)||CHR(225)||CHR(226)||CHR(227)||CHR(228)||CHR(229)||CHR(231)||CHR(232)||CHR(233)||CHR(234)||CHR(235)||CHR(236)||CHR(237)||CHR(238)||CHR(239)||CHR(240)||CHR(241)||CHR(242)||CHR(243)||CHR(244)||CHR(245)||CHR(246)||CHR(249)||CHR(250)||CHR(251)||CHR(252)||CHR(253)||CHR(255),
		'AAAAAACEEEEIIIIDNOOOOOUUUUYAAAAAACEEEEIIIIDNOOOOOUUUUYY')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete8)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement AE
	my $requete82 = q{
	update adr_post set
	SVILLE=REPLACE(SVILLE, CHR(198), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete82)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement ae
	my $requete83 = q{
	update adr_post set
	SVILLE=REPLACE(SVILLE, CHR(230), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete83)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement OE
	my $requete84 = q{
	update adr_post set
	SVILLE=REPLACE(SVILLE, CHR(140), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete84)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement oe
	my $requete85 = q{
	update adr_post set
	SVILLE=REPLACE(SVILLE, CHR(156), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete85)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	print "remplacement accentuation SVILLE ok : $cle\n";
#####################################################
	#requjte sur caracthres spiciaux SCODE_POSTAL
	#requjte sur accentuations
	my $requete10 = q{
		UPDATE ADR_POST SET
		SCODE_POSTAL=TRANSLATE(SCODE_POSTAL, CHR(192)||CHR(193)||CHR(194)||CHR(195)||CHR(196)||CHR(197)||CHR(199)||CHR(200)||CHR(201)||CHR(202)||CHR(203)||CHR(204)||CHR(205)||CHR(206)||CHR(207)||CHR(208)||CHR(209)||CHR(210)||CHR(211)||CHR(212)||CHR(213)||CHR(214)||CHR(217)||CHR(218)||CHR(219)||CHR(220)||CHR(221)||CHR(224)||CHR(225)||CHR(226)||CHR(227)||CHR(228)||CHR(229)||CHR(231)||CHR(232)||CHR(233)||CHR(234)||CHR(235)||CHR(236)||CHR(237)||CHR(238)||CHR(239)||CHR(240)||CHR(241)||CHR(242)||CHR(243)||CHR(244)||CHR(245)||CHR(246)||CHR(249)||CHR(250)||CHR(251)||CHR(252)||CHR(253)||CHR(255),
		'AAAAAACEEEEIIIIDNOOOOOUUUUYAAAAAACEEEEIIIIDNOOOOOUUUUYY')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete10)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement AE
	my $requete102 = q{
	update adr_post set
	SCODE_POSTAL=REPLACE(SCODE_POSTAL, CHR(198), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete102)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement ae
	my $requete103 = q{
	update adr_post set
	SCODE_POSTAL=REPLACE(SCODE_POSTAL, CHR(230), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete103)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement OE
	my $requete104 = q{
	update adr_post set
	SCODE_POSTAL=REPLACE(SCODE_POSTAL, CHR(140), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete104)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement oe
	my $requete105 = q{
	update adr_post set
	SCODE_POSTAL=REPLACE(SCODE_POSTAL, CHR(156), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete105)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	print "remplacement accentuation SCODE_POSTAL ok : $cle\n";
#####################################################
	#requjte sur caracthres spiciaux SCODE_PROVINCE
	#requjte sur accentuations
	my $requete12 = q{
		UPDATE ADR_POST SET
		SCODE_PROVINCE=TRANSLATE(SCODE_PROVINCE, CHR(192)||CHR(193)||CHR(194)||CHR(195)||CHR(196)||CHR(197)||CHR(199)||CHR(200)||CHR(201)||CHR(202)||CHR(203)||CHR(204)||CHR(205)||CHR(206)||CHR(207)||CHR(208)||CHR(209)||CHR(210)||CHR(211)||CHR(212)||CHR(213)||CHR(214)||CHR(217)||CHR(218)||CHR(219)||CHR(220)||CHR(221)||CHR(224)||CHR(225)||CHR(226)||CHR(227)||CHR(228)||CHR(229)||CHR(231)||CHR(232)||CHR(233)||CHR(234)||CHR(235)||CHR(236)||CHR(237)||CHR(238)||CHR(239)||CHR(240)||CHR(241)||CHR(242)||CHR(243)||CHR(244)||CHR(245)||CHR(246)||CHR(249)||CHR(250)||CHR(251)||CHR(252)||CHR(253)||CHR(255),
		'AAAAAACEEEEIIIIDNOOOOOUUUUYAAAAAACEEEEIIIIDNOOOOOUUUUYY')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete12)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement AE
	my $requete122 = q{
	update adr_post set
	SCODE_PROVINCE=REPLACE(SCODE_PROVINCE, CHR(198), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete122)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement ae
	my $requete123 = q{
	update adr_post set
	SCODE_PROVINCE=REPLACE(SCODE_PROVINCE, CHR(230), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete123)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement OE
	my $requete124 = q{
	update adr_post set
	SCODE_PROVINCE=REPLACE(SCODE_PROVINCE, CHR(140), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete124)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement oe
	my $requete125 = q{
	update adr_post set
	SCODE_PROVINCE=REPLACE(SCODE_PROVINCE, CHR(156), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete125)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	print "remplacement accentuation SCODE_PROVINCE ok : $cle\n";
#####################################################
	#requjte sur caracthres spiciaux SRAISON_SOCIALE
	#requjte sur accentuations
	my $requete13 = q{
		UPDATE ADR_POST SET
		SRAISON_SOCIALE=TRANSLATE(SRAISON_SOCIALE, CHR(192)||CHR(193)||CHR(194)||CHR(195)||CHR(196)||CHR(197)||CHR(199)||CHR(200)||CHR(201)||CHR(202)||CHR(203)||CHR(204)||CHR(205)||CHR(206)||CHR(207)||CHR(208)||CHR(209)||CHR(210)||CHR(211)||CHR(212)||CHR(213)||CHR(214)||CHR(217)||CHR(218)||CHR(219)||CHR(220)||CHR(221)||CHR(224)||CHR(225)||CHR(226)||CHR(227)||CHR(228)||CHR(229)||CHR(231)||CHR(232)||CHR(233)||CHR(234)||CHR(235)||CHR(236)||CHR(237)||CHR(238)||CHR(239)||CHR(240)||CHR(241)||CHR(242)||CHR(243)||CHR(244)||CHR(245)||CHR(246)||CHR(249)||CHR(250)||CHR(251)||CHR(252)||CHR(253)||CHR(255),
		'AAAAAACEEEEIIIIDNOOOOOUUUUYAAAAAACEEEEIIIIDNOOOOOUUUUYY')
		WHERE SAIN = ?
    };
	$sth = $dbConnexion->prepare_cached($requete13)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement AE
	my $requete132 = q{
	update adr_post set
	SRAISON_SOCIALE=REPLACE(SRAISON_SOCIALE, CHR(198), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete132)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement ae
	my $requete133 = q{
	update adr_post set
	SRAISON_SOCIALE=REPLACE(SRAISON_SOCIALE, CHR(230), 'AE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete133)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement OE
	my $requete134 = q{
	update adr_post set
	SRAISON_SOCIALE=REPLACE(SRAISON_SOCIALE, CHR(140), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete134)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	#Remplacement oe
	my $requete135 = q{
	update adr_post set
	SRAISON_SOCIALE=REPLACE(SRAISON_SOCIALE, CHR(156), 'OE')
	where sain= ?
	};
	$sth = $dbConnexion->prepare_cached($requete135)
            or die "Couldn't prepare statement: " . $dbConnexion->errstr;
 	$sth->execute($cle)
            or die "Couldn't execute statement: " . $sth->errstr;
	print "remplacement accentuation SRAISON_SOCIALE ok : $cle\n";
################# signature sans invalidation
	my $requete50 = q{
		UPDATE ADR_POST SET
		SSITE_MODIFICATION='VLB',SSIGNATURE_MODIFICATION='RetraitAccent',DDATE_MODIFICATION=sysdate
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
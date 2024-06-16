#!/tech/oracle/client/12102/perl/bin/perl -w
# This code can be simplified a lot, maybe check bdc_sql.sh, or other idea.
use lib ;

use strict;

use DBI;
use Getopt::Long;

my $programName = $0;
$programName =~ s/^(.*\/)?([^\/]*)$/$2/;

sub displayHelp()
{
    print("usage: $programName {-h | -application <application>}\n");
    print("where option can be:\n");
    print("    -h : displays this help\n");
    print("    -application <application> : selects the application (must be ISI or BDC)\n");
}
my $help;
my $application;
my $fileDate;
if (!GetOptions (
    "h" => \$help,
    "application=s" => \$application,
    "fileDate=s" => \$fileDate))
{
    displayHelp();
    exit(1);
}
if ($help || @ARGV)
{
    displayHelp();
    exit(1);
}

if ((!defined($application)) && ($application ne "ISI") && ($application ne "BDC"))
{
    print("ERROR: application must be set to 'ISI' or 'BDC'\n");
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

my $dateField = "DATE_".(($application eq "ISI")?"ISIS":"BDC");

my $startDate;
my $endDate;
my $selectRequest = $dbConnexion->prepare(
    "SELECT TO_CHAR($dateField, 'YYYYMMDDHH24MISS'), TO_CHAR(sysdate, 'YYYYMMDDHH24MISS') ".
    "FROM DATES_REPLICATIONS");
$selectRequest->execute();
while (my @row = $selectRequest->fetchrow_array())
{
    $startDate = $row[0];
    $endDate = $row[1];
}
$selectRequest->finish();
print("start date: '$startDate'\n");
print("end date: '$endDate'\n");

$endDate =~ /^\d{4}(\d{2})(\d{2})(\d{2})\d{2}\d{2}$/;
my $dataFile = $ENV{BASE_DATA_DIR}."/REPLICATION/ISI_BDC/REP$application.DAT.$fileDate";
my $dataFileTemp = $ENV{BASE_DATA_DIR}."/REPLICATION/ISI_BDC/REP$application.TMP.$fileDate";
print("data file: '$dataFile'\n");

open(DATA_FILE, ">$dataFile");

sub dropIfExistTable($$$)
{
  my $dbConnexion = $_[0];
  my $error = $_[1];
  my $tableName = $_[2];
  # See if tableName exists
  my $selectRequestTableTemp = ${$dbConnexion}->prepare("select count(*) from user_tables where table_name = '$tableName'");
  my $tableTempExists = 0;
  if ($selectRequestTableTemp->execute()) {
    while (my @row = $selectRequestTableTemp->fetchrow_array()) {
      $tableTempExists = $row[0];
    }
  } else {	
    ${$error} = 1;
    print("ERROR: cannot execute request: ".$selectRequestTableTemp->errstr."\n");
  }
  $selectRequestTableTemp->finish();
  if ($tableTempExists) {
    ${$dbConnexion}->do("DROP TABLE $tableName");	
  }
}

sub putLine($)
{
    my $line = $_[0];
    # we want fix sized lines: 600 characters (including trailing \n)
    $line =~ s/^(.{599}).*$/$1/;
    for (my $i=length($line); $i<599; ++$i)
    {
        $line .= " ";
    }
    print(DATA_FILE "$line\n");
}


sub FormatLineWithBlanks($$) # FormatLineWithBlanks(Line, LineSize)
{
    my $line = $_[0];
	my $size = $_[1];
		
    # we want fix sized line
    $line =~ s/^(.{$size}).*$/$1/;	
    for (my $i=length($line); $i<$size; ++$i)
    {
        $line .= " ";
    }
	return $line;
}

sub putLineTemp($)
{
    my $line = $_[0];
    # we want fix sized lines: 600 characters (including trailing \n)
    $line =~ s/^(.{599}).*$/$1/;
    for (my $i=length($line); $i<599; ++$i)
    {
        $line .= " ";
    }
    print(DATA_FILE_TEMP "$line\n");
}


sub simpleSqlSelect($$)
{
    my $error = $_[0];
    my $requestString = $_[1];
    my $selectRequest = $dbConnexion->prepare($requestString);
    my $counter = 0;
    if ($selectRequest->execute())
    {
        while (my @row = $selectRequest->fetchrow_array())
        {
            putLine($row[0]);
            ++$counter;
        }
    }
    else
    {
        ${$error} = 1;
        print("ERROR: unable to execute request: ".$selectRequest->errstr."\n");
    }
    $selectRequest->finish();
    return $counter;
}

sub simpleSqlSelectTemp($$)
{
	open(DATA_FILE_TEMP, ">$dataFileTemp");
    
	my $error = $_[0];
    my $requestString = $_[1];
    my $selectRequest = $dbConnexion->prepare($requestString);
    my $counter = 0;
    if ($selectRequest->execute())
    {
        while (my @row = $selectRequest->fetchrow_array())
        {
            putLineTemp($row[0]);
            ++$counter;
        }
    }
    else
    {
        ${$error} = 1;
        print("ERROR: unable to execute request: ".$selectRequest->errstr."\n");
    }
    $selectRequest->finish();
	
	close(DATA_FILE_TEMP);
	
    return $counter;
}


# Perl trim function to remove whitespace from the start and end of the string
sub trim($)
{
	my $string = shift;
	$string =~ s/^\s+//;
	$string =~ s/\s+$//;
	return $string;
}

putLine("REPLI_REP$endDate");
putLine("REPLI_REP$endDate");
print("Header created\n");

my $counter = 0;
my $error = 0;

$dbConnexion->do("alter session set hash_area_size=64000000");
$dbConnexion->do("alter session set sort_area_size=64000000");
$dbConnexion->do("alter session set db_file_multiblock_read_count=128");

# Create temporary table
dropIfExistTable(\$dbConnexion, \$error, "UPDATED_IND");
$dbConnexion->do("CREATE TABLE UPDATED_IND NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) AS (SELECT I.SGIN FROM INDIVIDUS I, USAGE_CLIENTS U ".
		"WHERE I.SGIN = U.SGIN ".
		"AND U.SCODE = '$application' ".
		"AND I.DDATE_MODIFICATION IS NOT NULL ".
		"AND I.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') ".
		"AND I.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))");
  
$counter += simpleSqlSelect(\$error,
"SELECT ".
	"'AI' || LPAD(TRIM(NVL(I.SGIN, '0')), 12, '0') || LPAD(TRIM(NVL(I.SGIN_FUSION, '0')), 12, ' ') || ".
	"DECODE(I.DDATE_FUSION, NULL, RPAD(' ', 8, ' '), TO_CHAR(I.DDATE_FUSION, 'DDMMYYYY')) ".
"FROM ".
	"INDIVIDUS I, USAGE_CLIENTS U ".
"WHERE ".    	
	"U.SGIN = I.SGIN AND ".
	"U.SCODE = '$application' AND ".
	"I.SPROV_AMEX = 'A' AND ".
	"I.DDATE_FUSION IS NOT NULL AND ".
	"I.DDATE_FUSION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') AND ".
	"I.DDATE_FUSION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')");
print("AI lines created\n");


$counter += simpleSqlSelect(\$error,
    "SELECT ".
        "'MI' || RPAD('$application', 3, ' ') || LPAD(TRIM(NVL(I.SGIN, '0')), 12, '0') || LPAD(NVL(I.IVERSION, 0), 12, '0') || ".
        "RPAD(' ', 10, ' ') || RPAD(NVL(I.SNOM, ' '), 35, ' ') || RPAD(NVL(I.SSEXE, ' '), 1, ' ') || ".
        "RPAD(NVL(I.SIDENTIFIANT_PERSONNEL, ' '), 16, ' ') || DECODE(I.DDATE_NAISSANCE, NULL, RPAD(' ', 8, ' '), TO_CHAR(I.DDATE_NAISSANCE, 'DDMMYYYY')) || ".
        "RPAD(NVL(I.SNATIONALITE, ' '), 2, ' ') || RPAD(NVL(I.SAUTRE_NATIONALITE, ' '), 2, ' ') || ".
        "RPAD(NVL(I.SSECOND_PRENOM, ' '), 25, ' ') || RPAD(NVL(I.SPRENOM, ' '), 25, ' ') || RPAD(NVL(I.SNON_FUSIONNABLE, ' '), 1, ' ') || ".
        "RPAD(NVL(I.SSTATUT_INDIVIDU, ' '), 1, ' ') || RPAD(NVL(I.SFRAUDEUR_CARTE_BANCAIRE, ' '), 1, ' ') || ".
        "RPAD(NVL(I.STIER_UTILISE_COMME_PIEGE, ' '), 1, ' ') || RPAD(NVL(I.SCIVILITE, ' '), 4, ' ') || ".
        "RPAD(NVL(I.SALIAS, ' '), 35, ' ') || RPAD(NVL(I.SALIAS_PRENOM, ' '), 25, ' ') || ".
        "RPAD(NVL(I.SALIAS_NOM1, ' '), 35, ' ') || RPAD(NVL(I.SALIAS_PRE1, ' '), 25, ' ') || RPAD(NVL(I.SALIAS_CIV1, ' '), 4, ' ') || ".
        "RPAD(NVL(I.SALIAS_NOM2, NVL(I.SALIAS_NOM1, ' ')), 35, ' ') || RPAD(NVL(I.SALIAS_PRE2, NVL(I.SALIAS_PRE1, ' ')), 25, ' ') || RPAD(NVL(I.SALIAS_CIV2, NVL(I.SALIAS_CIV1, ' ')), 4, ' ') || ".
        "RPAD(NVL(I.SCODE_TITRE, ' '), 3, ' ') || RPAD(NVL(C.SLIBELLE, ' '), 15, ' ') || RPAD(' ', 15, ' ') || 'M' || ". # NVL(C.SLIBELLE_EN, ' ') a la place du dernier ' '
        "RPAD(NVL(I.SSITE_MODIFICATION, ' '), 10, ' ') || RPAD(NVL(I.SSIGNATURE_MODIFICATION, ' '), 16, ' ') || ".
        "DECODE(I.DDATE_MODIFICATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(I.DDATE_MODIFICATION, 'DDMMYYYYHH24MISS')) || ".
        "LPAD(NVL(P.SRIN, ' '), 16, '0') || RPAD(NVL(P.SMAILING_AUTORISE, ' '), 1, ' ') || RPAD(NVL(P.SSOLVABILITE, ' '), 1, ' ') || ".
        "RPAD(NVL(P.SCODE_PROFESSIONNEL, ' '), 2, ' ') || RPAD(NVL(D.SLIBELLE_DOMAINE, ' '), 40, ' ') || ".
        "RPAD(NVL(P.SCODE_MARITALE, ' '), 1, ' ') || RPAD(NVL(P.SCODE_LANGUE, ' '), 2, ' ') || RPAD(NVL(P.SCODE_FONCTION, ' '), 2, ' ') || ".
        "RPAD(NVL(F.SLIBELLE_FONCTION, ' '), 30, ' ') || LPAD(NVL(P.INB_ENFANTS, 0), 2, '0') || RPAD(NVL(P.SSEGMENT, ' '), 10, ' ') || ".
        "RPAD(NVL(P.SETUDIANT, ' '), 1, ' ') || 'C' || ".
        "RPAD(NVL(I.SSITE_CREATION, ' '), 10, ' ') || RPAD(NVL(I.SSIGNATURE_CREATION, ' '), 16, ' ') || ".
        "DECODE(I.DDATE_CREATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(I.DDATE_CREATION, 'DDMMYYYYHH24MISS')) ".
    "FROM ".
        "INDIVIDUS I, REF_CODE_TITRE C, PROFILS P, DOM_PRO D, FCT_PRO F, UPDATED_IND UI ".
    "WHERE ".
        "I.SCODE_TITRE = C.SCODE (+) AND I.SGIN = P.SGIN (+) ".
        "AND P.SCODE_PROFESSIONNEL = D.SCODE_PROFESSIONNEL (+) ".
        "AND P.SCODE_FONCTION = F.SCODE_FONCTION (+) ".
	"AND I.SGIN = UI.SGIN");
print("MI lines created\n");

$counter += simpleSqlSelect(\$error,
    "SELECT ".
        "'PI' || LPAD(TRIM(NVL(I.SGIN, '0')), 12, '0') ".
    "FROM ".
        "INDIVIDUS I ".
    "WHERE ".
        "NOT EXISTS (SELECT SGIN FROM ROLE_CONTRATS WHERE ROLE_CONTRATS.SGIN = I.SGIN) ".
        "AND ".
        "( ".
            "NOT EXISTS (SELECT I.SGIN FROM USAGE_CLIENTS WHERE USAGE_CLIENTS.SGIN = I.SGIN) ".
            "OR ".
            "( ".
                "EXISTS(SELECT SGIN FROM USAGE_CLIENTS WHERE USAGE_CLIENTS.SGIN = I.SGIN AND SCODE='SCL') ".
                "AND ".
                "EXISTS (SELECT SGIN FROM USAGE_CLIENTS WHERE USAGE_CLIENTS.SGIN = I.SGIN GROUP BY SGIN HAVING COUNT(SCODE)=1) ".
            ") ".
        ") ".
        "AND I.DDATE_MODIFICATION IS NOT NULL ".
        "AND I.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') AND I.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')");
print("PI lines created\n");

my $TMPSEP=":#:";      		# Temporary separator
my $LigneAdr4MaxSize = 32;	# Max size for the 4th address line

$counter += simpleSqlSelectTemp(\$error,
"SELECT ".
	"DECODE(A.SSIGNATURE_MODIFICATION, NULL, 'CA', 'MA') || ".
	"LPAD(TRIM(NVL(A.SGIN, '0')), 12, '0') || RPAD('$application', 3, ' ') || ".
	"DECODE(A.SSIGNATURE_MODIFICATION, NULL, ".
		"'C' || RPAD(NVL(A.SSITE_CREATION, ' '), 10, ' ') || RPAD(NVL(A.SSIGNATURE_CREATION, ' '), 16, ' ') || ".
			"DECODE(A.DDATE_CREATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(A.DDATE_CREATION, 'DDMMYYYYHH24MISS')), ".
		 "'M' || RPAD(NVL(A.SSITE_MODIFICATION, ' '), 10, ' ') || RPAD(NVL(A.SSIGNATURE_MODIFICATION, ' '), 16, ' ') || ".
			"DECODE(A.DDATE_MODIFICATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(A.DDATE_MODIFICATION, 'DDMMYYYYHH24MISS'))) || ".
	"LPAD(TRIM(NVL(A.SAIN, '0')), 16, '0') || LPAD(NVL(A.IVERSION, 0), 12, '0') || LPAD(NVL(U.INUM, 0), 2, '0') || ".
	"RPAD(NVL(A.SCODE_MEDIUM, ' '), 1, ' ') || RPAD(NVL(A.SFORCAGE, ' '), 1, ' ') || RPAD(NVL(A.SSTATUT_MEDIUM, ' '), 1, ' ') || ".
	"RPAD(NVL(A.SRAISON_SOCIALE, ' '), 42, ' ') || '$TMPSEP' || RPAD(NVL(A.SVILLE, ' '), 32, ' ') || '$TMPSEP' || P.FORMAT_ADR || '$TMPSEP' || RPAD(NVL(A.SCOMPLEMENT_ADRESSE, ' '), 42, ' ') || ".
	"RPAD(NVL(A.SNO_ET_RUE, ' '), 42, ' ') || RPAD(NVL(A.SLOCALITE, ' '), 42, ' ') || '$TMPSEP' || RPAD(NVL(A.SCODE_POSTAL, ' '), 10, ' ') || '$TMPSEP' || ".
	"RPAD(NVL(A.SCODE_PAYS, ' '), 2, ' ') || '$TMPSEP' || RPAD(NVL(A.SCODE_PROVINCE, ' '), 2, ' ') || '$TMPSEP' || RPAD(NVL(U.SROLE1, ' '), 1, ' ') || ".
	"RPAD(NVL(U.SROLE2, ' '), 1, ' ') || RPAD(NVL(U.SROLE3, ' '), 1, ' ') || RPAD(NVL(U.SROLE4, ' '), 1, ' ') || ".
	"RPAD(NVL(U.SROLE5, ' '), 1, ' ') ".
"FROM ".
	 "USAGE_MEDIUMS U, ADR_POST A, PAYS P ".
"WHERE ".
	"U.SAIN_ADR = A.SAIN ".
"AND A.SCODE_PAYS=P.SCODE_PAYS ".
	"AND U.SCODE_APPLICATION = '$application' ".
	"AND A.SGIN IN ".
	"( ".
	"SELECT SGIN FROM UPDATED_IND ".
	"UNION ".
	"SELECT A2.SGIN FROM ADR_POST A2 ".
	"WHERE ".
		"(A2.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') AND A2.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')) ".
		"OR ".
		"( ".
			"'$application' = 'ISI' ".
			"AND ".
			"(A2.DDATE_CREATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') AND A2.DDATE_CREATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')) ".
		") ".
	") ".
	"AND A.SSTATUT_MEDIUM <> 'H'");
	

# Open temporary file 
my $AddressLine;
open(DATA_FILE_TEMP,, "<$dataFileTemp");
while (<DATA_FILE_TEMP>) 
{	
	# Test if the current line contains a '\r' (carriage return)
	if ($_ =~ /\r/)
	{
		# We found an '\r', so the line is a part of an address line
		
		# This command never worked, I just commented it to avoid error log
		$_ =~ s/[\n\r]/\n/g; # Replace '\n' and '\r' with '\s' (space character)
		
		$AddressLine .= $_;	 # Add the current line to AddressLine
	}
	else
	{	
		$AddressLine .= $_;
		my @columns = split(/$TMPSEP/, $AddressLine);
		my $TEMPLINE = $columns[0];		# Start of the Select result
		my $VILLE = ($columns[1]);		# VILLE
		my $FORMAT_ADR = $columns[2];	# Address format value (depending of the PAYS)
		my $PART1_ADR = $columns[3];	# Begin part of the address (adresse, numero et rue, localite)
		my $CODE_POSTAL = $columns[4];	# CODE_POSTAL
		my $CODE_PAYS = $columns[5];	# CODE_PAYS
		my $CODE_PROVINCE = $columns[6];# CODE_PROVINCE
		my $TEMPFIN = $columns[7];		# Usage information
		my $LigneAdr4;					# 4th address line
		#print("Format : (". $FORMAT_ADR .")\n");
		# Check if we have a correct Address format value
		if ($FORMAT_ADR =~ /[12345678]/) # Be carrefull : only 1 character checked with this line
		{
			# Check if the CODE_POSTAL is in the VILLE.
			my $lengthCP = length(trim($CODE_POSTAL));
			if ($lengthCP>0)
			{
				my $pos = index ($VILLE, trim($CODE_POSTAL));
				if ($pos > -1)
				{
					# Delete the CODE_POSTAL present in the VILLE 
					$VILLE = trim(substr($VILLE,0,$pos) . substr($VILLE, $pos + $lengthCP));
				}		
			}	
			
			# Create the correct 4th address line depending of the Address format value
		#print("Format : ". $FORMAT_ADR ."\n");			
			if ($FORMAT_ADR eq "1")
			{
				$LigneAdr4 = trim($CODE_POSTAL) . " " . trim($VILLE);
			}
			elsif ($FORMAT_ADR eq "2") 
			{
				$LigneAdr4 = trim($VILLE) . " " . trim($CODE_POSTAL);	
			}
			elsif ($FORMAT_ADR eq "3")
			{
				$LigneAdr4 = trim($VILLE) . " " . trim($CODE_PROVINCE) . " " .trim($CODE_POSTAL);
			}
			elsif ($FORMAT_ADR eq "4")
			{
				$LigneAdr4 = trim($CODE_PAYS) . " " . trim($CODE_POSTAL) . " " . trim($VILLE);		
			}
			elsif ($FORMAT_ADR eq "5")
			{
				$LigneAdr4 = trim($VILLE) . " " . trim($CODE_PAYS) . " " .trim($CODE_POSTAL);		
			}
			elsif ($FORMAT_ADR eq "6")
			{
				$LigneAdr4 = trim($CODE_POSTAL) . " " . trim($VILLE) . " " .trim($CODE_PROVINCE);		
			}
			elsif ($FORMAT_ADR eq "7")
			{
				$LigneAdr4 = trim($VILLE) . " " . trim($CODE_POSTAL) . " " .trim($CODE_PROVINCE);		
			}
			elsif ($FORMAT_ADR eq "8")
			{
				$LigneAdr4 = trim($VILLE) . " " . trim($CODE_POSTAL) . " " .trim($CODE_PROVINCE);		
			}			
			$LigneAdr4 = trim($LigneAdr4);				
			#print("  > 4eme Ligne d'adresse - Format(" . $FORMAT_ADR . ") : '" . $LigneAdr4 . "'\n");
		}	
		else
		{
			# The address format is not recognized, we put only the VILLE
			$LigneAdr4 = trim($VILLE);
			#print("  > 4eme Ligne d'adresse = ville : "  . $LigneAdr4 . "' - Format inconnu(" . $FORMAT_ADR . ")\n");
		}		
		$TEMPLINE = $TEMPLINE . FormatLineWithBlanks($LigneAdr4,$LigneAdr4MaxSize) . $PART1_ADR . $CODE_POSTAL . $CODE_PAYS . $CODE_PROVINCE . $TEMPFIN;
		putLine($TEMPLINE);	
					 
		$AddressLine = ""; # Reset AddressLine
	} 
}

# Close and remove the temporary file		
close(DATA_FILE_TEMP);
unlink($dataFileTemp);
		
print("CA and MA lines created\n");

$selectRequest = $dbConnexion->prepare(
	"SELECT ".
		"LPAD(TRIM(NVL(T.SGIN, '0')), 12, '0'), RPAD(NVL(T.SSTATUT_MEDIUM, ' '), 1, ' ') || RPAD(NVL(T.SCODE_MEDIUM, ' '), 1, ' ') || RPAD(NVL(T.STERMINAL, ' '), 1, ' ') || ".
		"RPAD(NVL(T.SINDICATIF, ' '), 4, ' ') || RPAD(NVL(T.SCODE_REGION, ' '), 4, ' ') || RPAD(NVL(T.SNUMERO, ' '), 15, ' ') || ".
		"DECODE(T.DDATE_CREATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(T.DDATE_CREATION, 'DDMMYYYYHH24MISS')) || ".
		"DECODE(T.DDATE_MODIFICATION, NULL, RPAD(' ', 14, ' '), TO_CHAR(T.DDATE_MODIFICATION, 'DDMMYYYYHH24MISS')) ".
	"FROM ".
		"TELECOMS T ".
	"WHERE ".
		"T.SGIN IN ".
		"( ".
		"SELECT SGIN FROM UPDATED_IND ".
		"UNION ".
			"SELECT ".
				"T.SGIN ".
			"FROM ".
				"TELECOMS T ".
			"WHERE ".
				"( ".
					"(T.DDATE_CREATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') AND T.DDATE_CREATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')) ".
					"OR ".
					"( ".
						"T.DDATE_MODIFICATION IS NOT NULL ".
						"AND T.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') ".
						"AND T.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS') ".
					") ".
				") ".
				"AND T.SSTATUT_MEDIUM <> 'H' ".
		") ".
		"AND T.SSTATUT_MEDIUM <> 'H' ".
	"ORDER BY ".
		"T.SGIN");
if ($selectRequest->execute())
{
    my $currentGin;
    my $telecoms;
    while (1)
    {
        my @row = $selectRequest->fetchrow_array();
        if ((!@row) || ($currentGin ne $row[0]))
        {
            if (defined($currentGin))
            {
                putLine("MT$currentGin$telecoms");
                ++$counter;
            }
            if (@row)
            {
                $currentGin = $row[0];
                $telecoms = $row[1];
            }
            else
            {
                last;
            }
        }
        else
        {
            $telecoms .= $row[1];
        }
    }
}
else
{
    print("ERROR: unable to execute request: ".$selectRequest->errstr."\n");
    $error = 1;
}
$selectRequest->finish();
print("MT lines created\n");

$dbConnexion->do("DROP TABLE UPDATED_IND");

putLine("REPLI_REP$endDate$counter");
print("tailer created\n");

close(DATA_FILE);

if (!$error)
{
    $dbConnexion->do("UPDATE DATES_REPLICATIONS SET $dateField = TO_DATE('$endDate', 'YYYYMMDDHH24MISS')");
    print("replication date updated\n");
}

exit($error);

#!/tech/oracle/client/12102/perl/bin/perl -w
use lib ;

use strict;

use DBI;
use Getopt::Long;

my $programName = $0;
$programName =~ s/^(.*\/)?([^\/]*)$/$2/;

sub displayHelp()
{
    print("usage: $programName {-h | -option <option>}\n");
    print("where option can be:\n");
    print("    -h : displays this help\n");
    print("    -option <option> : selects the option (must be MVT or GLO)\n");
}
my $help;
my $option;
if (!GetOptions (
    "h" => \$help,
    "option=s" => \$option))
{
    displayHelp();
    exit(1);
}
if ($help || @ARGV)
{
    displayHelp();
    exit(1);
}

if ((!defined($option)) && ($option ne "MVT") && ($option ne "GLO"))
{
    print("ERROR: option must be set to 'MVT' or 'GLO' \n");
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

my $startDate;
my $endDate;
my $selectRequest = $dbConnexion->prepare(
    "SELECT TO_CHAR(DDATE, 'DDMMYYYY'), TO_CHAR(sysdate, 'DDMMYYYY') ".
    "FROM DATE_REPLIC WHERE STYPE='MYACCOUNT' ");
	
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
my $dataFile ="";

my $dataFileMVT = $ENV{BASE_DATA_DIR}."/REPLICATION/MYACCOUNT/MVT/MVT-$endDate";
my $dataFileGLO = $ENV{BASE_DATA_DIR}."/REPLICATION/MYACCOUNT/GLOBAL/GLO-$endDate";

if ($option eq "MVT")
{
	$dataFile = $dataFileMVT;
}

if ($option eq "GLO")
{
	$dataFile = $dataFileGLO;
}

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
	print($tableName." dropped ! \n");
  }
}

sub putLine($)
{
    my $line = $_[0];
    # we want fix sized lines: 600 characters (including trailing \n)
    # $line =~ s/^(.{599}).*$/$1/;
    # for (my $i=length($line); $i<599; ++$i)
    # {
    #    $line .= " ";
    # }
    print(DATA_FILE "$line");
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

# Perl trim function to remove whitespace from the start and end of the string
sub trim($)
{
	my $string = shift;
	$string =~ s/^\s+//;
	$string =~ s/\s+$//;
	return $string;
}

print("START REPLI_MYACCOUNT $endDate");

my $counter = 0;
my $counter_comprefs = 0;
my $counter_addresses = 0;
my $counter_phones = 0;

my $error = 0;

# Create temporary table
  dropIfExistTable(\$dbConnexion, \$error, "ACCOUNT_TEMP");
  $dbConnexion->do("alter session set hash_area_size=64000000");
  $dbConnexion->do("alter session set sort_area_size=64000000");
  $dbConnexion->do("alter session set db_file_multiblock_read_count=128");

  # Requete pour l option MVT (daily)
  if ($option eq "MVT")
  {
    $dbConnexion->do(
        "CREATE TABLE ACCOUNT_TEMP NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) as (SELECT ".
        "IND.SGIN, MACC.FB_IDENTIFIER, IND.SCIVILITE, IND.SNOM, IND.SPRENOM, IND.SSEXE, IND.SNATIONALITE, PF.SCODE_LANGUE, ".
        "EM.SCODE_MEDIUM, EM.SSTATUT_MEDIUM, ".
        "MACC.EMAIL_IDENTIFIER, MACC.ENROLEMENT_POINT_OF_SELL, MACC.STATUS, MACC.DDATE_CREATION, MACC.DDATE_MODIFICATION, MACC.ACCOUNT_DELETION_DATE ".
        "FROM    ACCOUNT_DATA MACC ".
        "INNER JOIN INDIVIDUS IND ON MACC.SGIN = IND.SGIN ".
        "LEFT OUTER JOIN PROFILS PF ON MACC.SGIN = PF.SGIN ".
        "LEFT OUTER JOIN EMAILS EM ON MACC.SGIN = EM.SGIN ".
        "AND MACC.EMAIL_IDENTIFIER = EM.SEMAIL "

  		."WHERE ".
				"(FB_IDENTIFIER IS NULL AND ".
					"( ".
						"( ".
						"MACC.DDATE_CREATION >= TO_DATE('".$startDate."' , 'DDMMYYYYHH24MISS' ) ".
						" AND MACC.DDATE_CREATION < TO_DATE('".$endDate."' , 'DDMMYYYYHH24MISS' ) ".
						") ".
						" OR ".
						"( ".
							"MACC.DDATE_MODIFICATION IS NOT NULL ".
							"AND MACC.DDATE_MODIFICATION >= TO_DATE('".$startDate."' , 'DDMMYYYYHH24MISS' ) ".
							"AND MACC.DDATE_MODIFICATION < TO_DATE('".$endDate."' , 'DDMMYYYYHH24MISS' ) ".
						") ".
						"OR ".
						"( ".
						" MACC.ACCOUNT_DELETION_DATE >= TO_DATE('".$startDate."' , 'DDMMYYYYHH24MISS' ) ".
						"AND MACC.ACCOUNT_DELETION_DATE < TO_DATE('".$endDate."' , 'DDMMYYYYHH24MISS' ) ".
						")".
					")".
				")".
				" OR ".
				"( MACC.STATUS = 'U' AND".
					"( ".
						"MACC.ACCOUNT_UPGRADE_DATE IS NOT NULL ".
						"AND MACC.ACCOUNT_UPGRADE_DATE >= TO_DATE('".$startDate."' , 'DDMMYYYYHH24MISS' ) ".
						"AND MACC.ACCOUNT_UPGRADE_DATE < TO_DATE('".$endDate."' , 'DDMMYYYYHH24MISS' ) ".
					") ".
				")".
			")"
	 );
	
    print("ACC0UNT_TEMP created start date:".$startDate." end date: ".$endDate."\n");
  }
	
  # Requete pour l option GLO (init)
  if ($option eq "GLO")
  {	
    $dbConnexion->do(
        "CREATE TABLE ACCOUNT_TEMP NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) as (SELECT ".
        "IND.SGIN, MACC.FB_IDENTIFIER, IND.SCIVILITE, IND.SNOM, IND.SPRENOM, IND.SSEXE, IND.SNATIONALITE, PF.SCODE_LANGUE, ".
        "EM.SCODE_MEDIUM, EM.SSTATUT_MEDIUM, ".
        "MACC.EMAIL_IDENTIFIER, MACC.ENROLEMENT_POINT_OF_SELL, MACC.STATUS, MACC.DDATE_CREATION, MACC.DDATE_MODIFICATION, MACC.ACCOUNT_DELETION_DATE ".
        "FROM    ACCOUNT_DATA MACC ".
        "INNER JOIN INDIVIDUS IND ON MACC.SGIN = IND.SGIN ".
        "LEFT OUTER JOIN PROFILS PF ON MACC.SGIN = PF.SGIN ".
        "LEFT OUTER JOIN EMAILS EM ON MACC.SGIN = EM.SGIN ".
        "AND MACC.EMAIL_IDENTIFIER = EM.SEMAIL "

  		."WHERE ".
				"(FB_IDENTIFIER IS NULL AND ".
					"( ".
						"( ".
						"MACC.DDATE_CREATION >= TO_DATE('".$startDate."' , 'DDMMYYYYHH24MISS' ) ".
						" AND MACC.DDATE_CREATION < TO_DATE('".$endDate."' , 'DDMMYYYYHH24MISS' ) ".
						") ".
						" OR ".
						"( ".
							"MACC.DDATE_MODIFICATION IS NOT NULL ".
						") ".
						"OR ".
						"( ".
						" MACC.ACCOUNT_DELETION_DATE IS NOT NULL ".
						")".
					")".
				")".
				" OR ".
				"( MACC.STATUS = 'U' AND".
					"( ".
						"MACC.ACCOUNT_UPGRADE_DATE IS NOT NULL ".
					") ".
				")".
			")"
	 );
	
		print("ACC0UNT_TEMP created GLOBAL\n");	


	}
		
	# Get Unique elements
	$selectRequest = $dbConnexion->prepare(
	"SELECT 'C;' || SGIN || ".
	" ';' || TRIM(FB_IDENTIFIER) || ';' || TRIM(SCIVILITE) || ';' || TRIM(SNOM) || ';' || TRIM(SPRENOM) || ';' || TRIM(SSEXE) || ';' || TRIM(SNATIONALITE) || ';' || TRIM(SCODE_LANGUE) ||".
	" ';' || TRIM(SCODE_MEDIUM) || ';' || TRIM(SSTATUT_MEDIUM) ||".
	" ';' || TRIM(EMAIL_IDENTIFIER) || ';' || TRIM(ENROLEMENT_POINT_OF_SELL) || ';' || TRIM(STATUS) ||".
	" ';' || TO_CHAR(DDATE_CREATION, 'DDMMYYYYHH24MISS') ||".
	" ';' || TO_CHAR(NVL(DDATE_MODIFICATION, DDATE_CREATION), 'DDMMYYYYHH24MISS') ||".
	" ';' || TO_CHAR(ACCOUNT_DELETION_DATE, 'DDMMYYYYHH24MISS') || ';'".
	" FROM ACCOUNT_TEMP ");
	
    $counter = 0;
    if ($selectRequest->execute())
    {

        # Add first line (mandatory)
	putLine("Civility Code | Individual identifer (GIN) | N¹Flying Blue (CIN) | Title | Last Name | First Name | Gender | Nationality  | Code Langue | Email Medium code | Email status | Email Address | Enrolment POS | Account status | Account creation date | Account update date | Account deletion date | Mail subcriptions code | Communication Group | Communication Type  | Subscription ? OPT-IN | Mail subcriptions code | Communication Group | Communication Type  | Subscription ? OPT-IN | Mail subcriptions code | Communication Group | Communication Type  | Subscription ? OPT-IN | Postal Address Code  | Postal Adress ? Medium Code  | Postal Adress - Status | Number and street | Locality | State code | Zip code | City | Country code | Address complement | Phone Number 1 code | Phone Number 1 Medium Code | Phone Number 1 Terminal type | Phone Number 1  Statuts  | Phone Number 1 | Phone Number 2 code | Phone Number 2 Medium Code | Phone Number 2 Terminal type | Phone Number 2  Statuts  | Phone Number 2 | Phone Number 3 code | Phone Number 3 Medium Code | Phone Number 3 Terminal type | Phone Number 3  Statuts  | Phone Number 3 | Phone Number 4 code | Phone Number 4 Medium Code | Phone Number 4 Terminal type | Phone Number 4  Statuts  | Phone Number 4 | Code Pays Edatis\n");

        while (my @row = $selectRequest->fetchrow_array())
        {
			# add first line of the select
			# split current line
			my @columns = split(";", $row[0]);
			my $CIVILITY_CODE = $columns[0];		# Start of the Select result
			my $SGIN = $columns[1];
			
			putLine($row[0]);
		        print("gin:".$SGIN."\n");
			
			# Get Multiples comprefs elements associated to a GIN 
			$counter_comprefs = simpleSqlSelect(\$error, "SELECT ".
			"'M;' || TRIM(COM_GROUP_TYPE) || ';' || TRIM(COM_TYPE) || ';' || TRIM(SUBSCRIBE) || ';' ".
			"FROM COMMUNICATION_PREFERENCES comm ".
			"WHERE (comm.SGIN = '".$SGIN."' ) AND ROWNUM < 4 ");
			# print("MyAccount comprefs lines created\n");
			if ($counter_comprefs == "0")
			{
			  putLine("M;;;;M;;;;M;;;;");
			  print("Add 3 blank comprefs blocks");
			}
			elsif ($counter_comprefs == "1")
			{
			  putLine("M;;;;M;;;;");
			  print("Add 2 blank comprefs blocks");
			}
			elsif ($counter_comprefs == "2")
			{
			  putLine("M;;;;");
			  print("Add 1 blank comprefs block");
			}
			
			# Get Multiples Address elements associated to a GIN 
			$counter_addresses = simpleSqlSelect(\$error, "SELECT ".
			"'A;' || TRIM(SCODE_MEDIUM) || ';' || TRIM(SSTATUT_MEDIUM) || ';' || TRIM(SNO_ET_RUE) || ';' || TRIM(SLOCALITE) || ';' || TRIM(SCODE_PROVINCE) || ';' || TRIM(SCODE_POSTAL) || ';' || TRIM(SVILLE) ||".
			" ';' || TRIM(SCODE_PAYS) || ';' || TRIM(SCOMPLEMENT_ADRESSE) || ';' ".
			"FROM ADR_POST adr ".
			"WHERE (adr.SGIN = '".$SGIN."') AND ROWNUM < 2 ORDER BY adr.DDATE_MODIFICATION DESC");
			# print("MyAccount Adress lines created\n");
			if ($counter_addresses == 0)
			{
			  putLine("A;;;;;;;;;;");
			  print("Add 1 blank postal address block");
			}
			
			# Get Multiples Telecoms elements associated to a GIN
			$counter_phones = simpleSqlSelect(\$error, "SELECT ".
			"'P;' || TRIM(t.SCODE_MEDIUM) || ';' || TRIM(t.STERMINAL) || ';' || TRIM(t.SSTATUT_MEDIUM) || ';' ||  NVL2( t.SNORMALIZED_NUMERO, t.SNORMALIZED_COUNTRY||t.SNORMALIZED_NUMERO, NVL(t.SINDICATIF||t.SCODE_REGION||t.SNUMERO , '')) || ';' ".
			"FROM TELECOMS t ".
			"WHERE (t.SGIN = '".$SGIN."' ) AND ROWNUM < 5 ORDER BY t.DDATE_MODIFICATION DESC");
			# print("MyAccount Phone lines created\n");
			if ($counter_phones == "0")
			{
			  putLine("P;;;;;P;;;;;P;;;;;P;;;;;");
			  print("Add 4 blank phone blocks");
			}
			elsif ($counter_phones == 1)
			{
			  putLine("P;;;;;P;;;;;P;;;;;");
			  print("Add 3 blank phone blocks");
			}
			elsif ($counter_phones == 2)
			{
			  putLine("P;;;;;P;;;;;");
			  print("Add 2 blank phone blocks");
			}
			elsif ($counter_phones == 3)
			{
			  putLine("P;;;;;");
			  print("Add 1 blank phone block");
			}

			# Individu suivant : saut de ligne necessaire
			# print("Next individual asked\n");
			putLine("\n");	
			++$counter;
        }
    }
    else
    {
        ${$error} = 1;
        print("ERROR: unable to execute request: ".$selectRequest->errstr."\n");
    }
	$selectRequest->finish();

#$dbConnexion->do("DROP TABLE ACCOUNT_TEMP");

print("END REPLI_MYACCOUNT $endDate $counter entries");

close(DATA_FILE);

if (!$error)
{
    $dbConnexion->do("update DATE_REPLIC set DDATE = TO_DATE('$endDate', 'DDMMYYYYHH24MISS') WHERE STYPE = 'MYACCOUNT'");
    print("replication date updated\n");
}

exit($error);

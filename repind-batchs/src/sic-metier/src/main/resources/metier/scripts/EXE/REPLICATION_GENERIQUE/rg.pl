#!/tech/oracle/client/12102/perl/bin/perl -w
 
use lib ;
use strict;
use DBI;
use Getopt::Long;

my $programName = $0;
$programName =~ s/^(.*\/)?([^\/]*)$/$2/;

sub displayHelp()
{
    print(STDERR "usage: $programName {-h | {-r <number> [-d] [-n <notification file>]}}\n");
    print(STDERR "where option can be:\n");
    print(STDERR "    -h : displays this help\n");
    print(STDERR "    -r <number> : replicates the corresponding file\n");
    print(STDERR "    -startDate <date> : replicates only records that changed after or at date (YYYYMMDDHH24MISS)\n");
    print(STDERR "    -endDate <date> : replicates only records that changed before date (YYYYMMDDHH24MISS)\n");
    print(STDERR "    -notify <notification file> : notification file that will be touched when the replication has ended\n");
}

my $help;
my $replicationNumber;
my $startDate;
my $endDate;
my $notificationFile;
if (!GetOptions (
    "h" => \$help,
    "r=s" => \$replicationNumber,
    "startDate=s" => \$startDate,
    "endDate=s" => \$endDate,
    "notify=s" => \$notificationFile))
{
    displayHelp();
    exit(1);
}
if ($help || @ARGV)
{
    displayHelp();
    exit(1);
}

if (!defined($replicationNumber))
{
    print(STDERR "ERROR: replication file number must be set\n");
    displayHelp();
    exit(1);
}

sub simpleSqlSelect($$$)
{
    my $dbConnexion = $_[0];
    my $error = $_[1];
    my $requestString = $_[2];
    if (!${$error})
    {
        my $selectRequest = ${$dbConnexion}->prepare($requestString);
        if ($selectRequest->execute())
        {
            while (my @row = $selectRequest->fetchrow_array())
            {
                print($row[0]."\n");
            }
        }
        else
        {
            ${$error} = 1;
            print(STDERR "ERROR: cannot execute request: ".$selectRequest->errstr."\n");
        }
        $selectRequest->finish();
    }
}

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

sub getTempTableName($)
{
  my $tablename = $_[0];
  my $suffixe = "_TEMP_G";
  if (defined($startDate))
  {
    $suffixe = "_TEMP_M";
  }
  return $tablename.$suffixe;
}

sub getTimeWindowConditionString($$$$)
{
    my $tableOrAliasName = $_[0];
    my $startDate = $_[1];
    my $endDate = $_[2];
    my $firstWhere = $_[3];
    my $result = "";
    if (defined($startDate))
    {
        $result .= " ".($firstWhere?"WHERE":"AND")." ($tableOrAliasName.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') OR ".
            "($tableOrAliasName.DDATE_MODIFICATION IS NULL AND $tableOrAliasName.DDATE_CREATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')))";
        $firstWhere = 0;
    }
    if (defined($endDate))
    {
        $result .= " ".($firstWhere?"WHERE":"AND")." ($tableOrAliasName.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS') OR ".
            "($tableOrAliasName.DDATE_MODIFICATION IS NULL AND $tableOrAliasName.DDATE_CREATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')))";
    }
    return $result;
}

sub getTimeWindowConditionString2($$$$)
{
    my $tableOrAliasName = $_[0];
    my $startDate = $_[1];
    my $endDate = $_[2];
    my $firstWhere = $_[3];
    my $result = "";
    if (defined($startDate))
    {
        $result .= " ".($firstWhere?"WHERE":"AND")." ($tableOrAliasName.DDATE_MAJ >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
        $firstWhere = 0;
    }
    if (defined($endDate))
    {
        $result .= " ".($firstWhere?"WHERE":"AND")." ($tableOrAliasName.DDATE_MAJ < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
    }
    return $result;
}

sub get180Request($$)
{
    my $startDate = $_[0];
    my $endDate = $_[1];
    my $request =
        "SELECT REPLACE(REPLACE('0180:#:'||':#:'||d.ICLE||':#:'||d.SINDICT_NP||':#:'||d.SNOM||':#:'||d.SPRENOM||':#:'||d.SGIN||':#:'||TO_CHAR(d.DDATE_NAISSANCE, 'DDMMYYYY')||':#:'||".
        "d.NUM_GROUPE||':#:'||d.SSEXE||':#:'||TO_CHAR(d.DDATE_TRAITEMENT, 'DDMMYYYY')||':#:'||d.SGIN_PM||':#:'||d.SGIN_FUSION||':#:'||d.SPAYS||':#:'||".
        "d.SFP||':#:'||d.SABO||':#:'||d.SRC||':#:'||d.SMR||':#:'||d.SAMEX||':#:'||d.SBO||':#:'||d.SGP||':#:'||d.SAUTRES||':#:'||d.STYPE_NUM||':#:'||".
        "d.STYPE_FIRME||':#:'||d.SSTATUT||':#:'||d.SACT_LOCALE||':#:'||d.SSECTEUR_ACT||':#:'||d.SCF||':#:'||d.SCC||':#:'||d.SMARCHE||':#:', chr(10), '' ), chr(13), '' ) FROM DOUBLONS d, INDIVIDUS i ".
        "WHERE (d.SGIN_PM is null) AND d.SGIN = i.SGIN";
    if (defined($startDate) || defined($endDate))
    {
            $request .= getTimeWindowConditionString("i", $startDate, $endDate, 0);
    }
    $request .=
        " UNION SELECT REPLACE(REPLACE('0180:#:'||':#:'||d.ICLE||':#:'||d.SINDICT_NP||':#:'||d.SNOM||':#:'||d.SPRENOM||':#:'||d.SGIN||':#:'||TO_CHAR(d.DDATE_NAISSANCE, 'DDMMYYYY')||':#:'||".
        "d.NUM_GROUPE||':#:'||d.SSEXE||':#:'||TO_CHAR(d.DDATE_TRAITEMENT, 'DDMMYYYY')||':#:'||d.SGIN_PM||':#:'||d.SGIN_FUSION||':#:'||d.SPAYS||':#:'||".
        "d.SFP||':#:'||d.SABO||':#:'||d.SRC||':#:'||d.SMR||':#:'||d.SAMEX||':#:'||d.SBO||':#:'||d.SGP||':#:'||d.SAUTRES||':#:'||d.STYPE_NUM||':#:'||".
        "d.STYPE_FIRME||':#:'||d.SSTATUT||':#:'||d.SACT_LOCALE||':#:'||d.SSECTEUR_ACT||':#:'||d.SCF||':#:'||d.SCC||':#:'||d.SMARCHE||':#:', chr(10), '' ), chr(13), '' ) FROM DOUBLONS d, PERS_MORALE pm ".
        "WHERE (d.SGIN is null) AND d.SGIN = pm.SGIN";
    if (defined($startDate) || defined($endDate))
    {
            $request .= getTimeWindowConditionString("pm", $startDate, $endDate, 0);
    }


    return $request;
}

sub get210Request($$$)
{
    my $thirdField = $_[0];
    my $fourthField = $_[1];
    my $otherTablesAndWhereClause = $_[2];
    return
        "SELECT REPLACE(REPLACE(REPLACE(REPLACE('0210:#:'||TRIM(a.SAIN)||':#:'||$thirdField||':#:'||$fourthField||".
        "':#:'||a.SSTATUT_MEDIUM||':#:'||TRIM(a.SCODE_MEDIUM)||':#:'||TRIM(a.SRAISON_SOCIALE)||".
        "':#:'||TRIM(a.SCOMPLEMENT_ADRESSE)||':#:'||TRIM(a.SLOCALITE)||':#:'||TRIM(a.SNO_ET_RUE)||':#:'||TRIM(a.SVILLE)||".
        "':#:'||TRIM(a.SCODE_POSTAL)||':#:'||TRIM(a.SCODE_PAYS)||':#:'||TRIM(a.SCODE_PROVINCE)||".
        "':#:'||TO_CHAR(a.DDATE_CREATION, 'DDMMYYYY')||".
        "':#:'||TO_CHAR(NVL(a.DDATE_MODIFICATION, a.DDATE_CREATION), 'DDMMYYYY')||".
        "':#:'||TRIM(a.SSITE_CREATION)||':#:'||TRIM(a.SSIGNATURE_CREATION)||".
        "':#:'||TRIM(a.SSITE_MODIFICATION)||':#:'||TRIM(a.SSIGNATURE_MODIFICATION)||':#:'||TRIM(a.SFORCAGE)||".
	"':#:'||TRIM(a.ICOD_ERR)||':#:'||TRIM(a.ICOD_WARNING), chr(00), '' ), chr(10), '' ), chr(13), '' ), chr(26), '' )||".
	"':#:'||TO_CHAR(a.DDATE_FONCTIONNEL, 'DDMMYYYY')||':#:'||TRIM(a.SSITE_FONCTIONNEL)||".
	"':#:'||TRIM(a.SSIGNATURE_FONCTIONNEL)||':#:'||TRIM(a.SCOD_ERR_SIMPLE)||".
	"':#:'||TRIM(a.SCOD_ERR_DETAILLE)||':#:'||TRIM(a.STYPE_INVALIDITE)||':#:'||TRIM(a.SENVOI_POSTAL)".

        "FROM ".getTempTableName("ADR_POST")." a".$otherTablesAndWhereClause;
}

sub get212Request($$$)
{
    my $thirdField = $_[0];
    my $fourthField = $_[1];
    my $otherTablesAndWhereClause = $_[2];
    return
        "SELECT REPLACE(REPLACE(REPLACE(REPLACE('0212:#:'||TRIM(a.SAIN)||':#:'||$thirdField||':#:'||$fourthField||".
        "':#:'||a.SSTATUT_MEDIUM||':#:'||TRIM(a.SCODE_MEDIUM)||':#:'||TRIM(a.SRAISON_SOCIALE)||".
        "':#:'||TRIM(a.SCOMPLEMENT_ADRESSE)||':#:'||TRIM(a.SLOCALITE)||':#:'||TRIM(a.SNO_ET_RUE)||':#:'||TRIM(a.SVILLE)||".
        "':#:'||TRIM(a.SCODE_POSTAL)||':#:'||TRIM(a.SCODE_PAYS)||':#:'||TRIM(a.SCODE_PROVINCE)||".
        "':#:'||TO_CHAR(a.DDATE_CREATION, 'DDMMYYYY')||".
        "':#:'||TO_CHAR(NVL(a.DDATE_MODIFICATION, a.DDATE_CREATION), 'DDMMYYYY')||".
        "':#:'||TRIM(a.SSITE_CREATION)||':#:'||TRIM(a.SSIGNATURE_CREATION)||".
        "':#:'||TRIM(a.SSITE_MODIFICATION)||':#:'||TRIM(a.SSIGNATURE_MODIFICATION)||':#:'||TRIM(a.SFORCAGE)||".
	"':#:'||TRIM(a.ICOD_ERR)||':#:'||TRIM(a.ICOD_WARNING), chr(00), '' ), chr(10), '' ), chr(13), '' ), chr(26), '' )||".
	"':#:'||TO_CHAR(a.DDATE_FONCTIONNEL, 'DDMMYYYY')||':#:'||TRIM(a.SSITE_FONCTIONNEL)||".
	"':#:'||TRIM(a.SSIGNATURE_FONCTIONNEL)||':#:'||TRIM(a.SCOD_ERR_SIMPLE)||".
	"':#:'||TRIM(a.SCOD_ERR_DETAILLE)||':#:'||TRIM(a.STYPE_INVALIDITE)||':#:'||TRIM(a.SENVOI_POSTAL)".

        "FROM ".getTempTableName("ADR_POST")." a".$otherTablesAndWhereClause;
}

sub get400Request($$$$$)
{
    my $codeTiers = $_[0];
    my $codeZone = $_[1];
    my $otherTablesAndWhereClause = $_[2];
    my $startDate = $_[3];
    my $endDate = $_[4];
    my $request =
        "SELECT '0400:#:'||TRIM(p.ICLE_PMZ)||':#:'||$codeTiers||':#:'||p.SGIN||':#:'||$codeZone||':#:'||p.IGIN_ZONE||':#:'||".
        "TO_CHAR(p.DDATE_OUVERTURE, 'DDMMYYYY')||':#:'||TO_CHAR(p.DDATE_FERMETURE, 'DDMMYYYY')||':#:'||p.SLIEN_PRIVILEGIE ".
        "FROM PM_ZONE p";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", PERS_MORALE pm";
    }
    $request .= $otherTablesAndWhereClause;
    if (defined($startDate) || defined($endDate))
    {
        $request .= " AND pm.SGIN = p.SGIN".getTimeWindowConditionString("pm", $startDate, $endDate, 0);
    }
    return $request;
}

sub get920Request($$$$$)
{
    my $type = $_[0];
    my $otherTables = $_[1];
    my $otherWhereClauses = $_[2];
    my $startDate = $_[3];
    my $endDate = $_[4];
    my $request = "SELECT ".
        "'0920:#:'||TRIM(s.ICLE)||':#:'||$type||':#:'||TRIM(s.SGIN)||':#:'||TRIM(s.SNOM)||':#:'||s.STYPE||':#:'||".
        "TO_CHAR(s.DDATE_MODIFICATION_SNOM, 'DDMMYYYY')||':#:' ".
        "FROM SYNONYME s";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", PERS_MORALE pm";
    }
    $request .= $otherTables." WHERE s.SSTATUT = 'V'";
    if (defined($startDate) || defined($endDate))
    {
        $request .= " AND s.sgin = pm.sgin".getTimeWindowConditionString("pm", $startDate, $endDate, 0);
    }
    $request .= $otherWhereClauses;
    return $request;
}

sub get930Request($$$$$$)
{
    my $type = $_[0];
    my $otherTables = $_[1];
    my $whereClauses = $_[2];
    my $dateWhereClause = $_[3];
    my $startDate = $_[4];
    my $endDate = $_[5];
    my $request = "SELECT ".
        "'0930:#:'||TRIM(n.IKEY)||':#:'||$type||':#:'||TRIM(n.SGIN)||':#:'||TO_CHAR(n.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||".
        "n.STYPE||':#:'||n.SNUMERO||':#:'||n.SLIBELLE||':#:'||".
        "TO_CHAR(n.DDATE_OUVERTURE, 'DDMMYYYY')||':#:'||TO_CHAR(n.DDATE_FERMETURE, 'DDMMYYYY')||':#:' ".
        "FROM NUMERO_IDENT n";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", PERS_MORALE pm";
    }
    $request .= $otherTables.$whereClauses;
    if (defined($startDate) || defined($endDate))
    {
        $request .= $dateWhereClause.getTimeWindowConditionString("pm", $startDate, $endDate, 0);
    }
    return $request;
}

sub get940Request($$$$$$)
{
    my $type = $_[0];
    my $otherTables = $_[1];
    my $whereClauses = $_[2];
    my $dateWhereClause = $_[3];
    my $startDate = $_[4];
    my $endDate = $_[5];
    my $request = "SELECT ".
        "'0940:#:'||TRIM(c.IKEY)||':#:'||$type||':#:'||c.SGIN||':#:'||c.STYPE||':#:'||".
        "TO_CHAR(c.DDATE_DEBUT, 'DDMMYYYY')||':#:'||TO_CHAR(c.DDATE_FIN, 'DDMMYYYY')||':#:'||c.SLIBELLE||':#:'||c.SMONNAIE||':#:'||".
        "NVL(c.NMONTANT,0)||':#:'||TO_CHAR(c.DDATE_MAJ, 'DDMMYYYY')||':#:' ".
        "FROM CHIFFRE c";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", PERS_MORALE pm";
    }
    $request .= $otherTables.$whereClauses;
    if (defined($startDate) || defined($endDate))
    {
        $request .= $dateWhereClause.getTimeWindowConditionString("pm", $startDate, $endDate, 0);
    }
    return $request;
}

sub get960Request($$$$$$)
{
    my $type = $_[0];
    my $otherTables = $_[1];
    my $whereClauses = $_[2];
    my $dateWhereClause = $_[3];
    my $startDate = $_[4];
    my $endDate = $_[5];
    my $request = "SELECT ".
        "'0960:#:'||TRIM(s.ICLE)||':#:'||$type||':#:'||TRIM(s.SGIN)||':#:'||s.STYPE||':#:'||s.SNIVEAU||':#:'||".
        "TO_CHAR(s.DDATE_ENTREE, 'DDMMYYYY')||':#:'||TO_CHAR(s.DDATE_SORTIE, 'DDMMYYYY')||':#:'||s.POTENTIEL||':#:'||NVL(s.MONTANT,0)||'.00:#:'||".
        "s.MONNAIE||':#:'||s.POLITIQUE_VOYAGE||':#:' ".
        "FROM SEGMENTATION s";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", PERS_MORALE pm";
    }
    $request .= $otherTables.$whereClauses;
    if (defined($startDate) || defined($endDate))
    {
        $request .= $dateWhereClause.getTimeWindowConditionString("pm", $startDate, $endDate, 0);
    }
    return $request;
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

# header
print("0010:#:SEPARATEUR:#:\n");
my $startDateString = "195001010000";
my $endDateString = `date +%Y%m%d%H%M`;
$endDateString =~ s/[\r\n]*$//;
if (defined($startDate))
{
    $startDateString = $startDate;
    $startDateString =~ s/..$//;
}
if (defined($endDate))
{
    $endDateString = $endDate;
    $endDateString =~ s/..$//;
}
print("0020:#:DATE DEBUT:#:$startDateString:#:DATE FIN:#:$endDateString:#:\n");
print("0090:#:TRAITEMENT_OK:#:\n");

if ($replicationNumber == 100)
{
    my $request = "SELECT u.SGIN, u.SCODE FROM USAGE_CLIENTS u";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", INDIVIDUS i ".
            "WHERE u.SGIN = i.SGIN".getTimeWindowConditionString("i", $startDate, $endDate, 0);
    }
    $request .= " ORDER BY u.SGIN";
    my $selectRequest = $dbConnexion->prepare($request);
    if ($selectRequest->execute())
    {
        my $currentGin = "";
        my $first = 1;
        while (my @row = $selectRequest->fetchrow_array())
        {
            if ($currentGin != $row[0])
            {
                if (!$first)
                {
                    print("\n");
                }
                else
                {
                    $first = 0;
                }
                print("0100:#:".$row[0].":#:0110:#:".$row[0].":#:");
                $currentGin = $row[0];
            }
            print($row[1].":#:");
        }
        if (!$first)
        {
            print("\n");
        }
    }
    else
    {
        $error = 1;
        print(STDERR "ERROR: cannot execute request: ".$selectRequest->errstr."\n");
    }
    $selectRequest->finish();
}
elsif ($replicationNumber == 101)
{
    my $request = "SELECT ".
        "'0101:#:'||DELEGATION_DATA_ID||':#:'||STATUS||':#:'||STYPE||':#:'||TO_CHAR(CREATION_DATE, 'DDMMYYYY')||':#:'||CREATION_SIGNATURE||':#:'||CREATION_SITE||':#:'||".
        "TO_CHAR(MODIFICATION_DATE, 'DDMMYYYY')||':#:'||MODIFICATION_SIGNATURE||':#:'||MODIFICATION_SITE||':#:'||SENDER||':#:'||SGIN_DELEGATOR||':#:'||SGIN_DELEGATE".
        " FROM DELEGATION_DATA ".
        " WHERE DELEGATION_DATA_ID IS NOT NULL";
    if (defined($startDate))
    {
        $request .= " AND MODIFICATION_DATE >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')";
	}
    if (defined($endDate))
    {
        $request .= " AND MODIFICATION_DATE < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')";
    }
	
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 110)
{
  # See if INDIVIDUS_TEMP exists
  my $individuTempTableName = getTempTableName("INDIVIDUS");
  dropIfExistTable(\$dbConnexion, \$error, $individuTempTableName);

  my $individuTempTableNameBis = getTempTableName("INDIVIDUS_BIS");
  dropIfExistTable(\$dbConnexion, \$error, $individuTempTableNameBis);
	
  # $dbConnexion->do("alter session set max_dump_file_size=unlimited");
  # $dbConnexion->do("alter session set events='10046 trace name context forever, level 8'");
  $dbConnexion->do("alter session set hash_area_size=64000000");
  $dbConnexion->do("alter session set sort_area_size=64000000");
  $dbConnexion->do("alter session set db_file_multiblock_read_count=128");

  $dbConnexion->do("CREATE TABLE ".$individuTempTableName." NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) as SELECT ".
        "i.SGIN, i.SSTATUT_INDIVIDU, i.SNOM, i.SALIAS, i.SPRENOM, i.SALIAS_PRENOM, i.SSECOND_PRENOM, ".
	"i.SCIVILITE, i.SCODE_TITRE, i.SSEXE, i.SNATIONALITE, i.SAUTRE_NATIONALITE, i.SIDENTIFIANT_PERSONNEL, ".
        "i.DDATE_NAISSANCE, i.SFRAUDEUR_CARTE_BANCAIRE, i.SSITE_CREATION, i.DDATE_CREATION, i.SSIGNATURE_CREATION, ".
        "i.SSITE_MODIFICATION, i.DDATE_MODIFICATION, i.SSIGNATURE_MODIFICATION, i.SGIN_FUSION ".
        "FROM INDIVIDUS i ".getTimeWindowConditionString("i", $startDate, $endDate, 1));

  $dbConnexion->do("create table ".$individuTempTableNameBis." NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) as ". 
  "SELECT REPLACE(REPLACE('0110:#:'||i.SGIN||':#::#::#:'||i.SSTATUT_INDIVIDU||':#:'".
  "||TRIM(i.SNOM)||':#:'||TRIM(i.SALIAS)||':#:'||TRIM(i.SPRENOM)||':#:'".
  "||TRIM(i.SALIAS_PRENOM)||':#:'||TRIM(i.SSECOND_PRENOM)||':#:'||i.SCIVILITE".
  "||':#:'||i.SCODE_TITRE||':#:'||i.SSEXE||':#:'||i.SNATIONALITE||':#:'||i.SAUTRE_NATIONALITE".
  "||':#:'||i.SIDENTIFIANT_PERSONNEL||':#:'".
  "||TO_CHAR(i.DDATE_NAISSANCE, 'DDMMYYYY')".
  "||':#:'||i.SFRAUDEUR_CARTE_BANCAIRE||':#:'||p.SMAILING_AUTORISE||':#:'||p.SSOLVABILITE||':#:'".
  "||p.SCODE_PROFESSIONNEL||':#:'||p.SCODE_MARITALE||':#:'||p.SCODE_FONCTION||':#:'||p.SCODE_LANGUE".
  "||':#:'||p.INB_ENFANTS||':#:'||p.SSEGMENT||':#:'||p.SETUDIANT||':#:'||TRIM(i.SSITE_CREATION)||':#:'".
  "||TO_CHAR(i.DDATE_CREATION, 'DDMMYYYY')||':#:'||TRIM(i.SSIGNATURE_CREATION)||':#:'||TRIM(i.SSITE_MODIFICATION)".
  "||':#:'||TO_CHAR(i.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||TRIM(i.SSIGNATURE_MODIFICATION)||':#:'".
  "||TRIM(i.SGIN_FUSION)||':#:', chr(10), '' ), chr(13), '' )".
  "AS CHAMP_UNIQUE FROM ".$individuTempTableName." i, PROFILS p WHERE i.SGIN = p.SGIN(+) ");





  #  simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
  #      "REPLACE(REPLACE('0110:#:'||i.SGIN||':#::#::#:'||i.SSTATUT_INDIVIDU||':#:'||TRIM(i.SNOM)||".
  #      "':#:'||TRIM(i.SALIAS)||':#:'||TRIM(i.SPRENOM)||':#:'||TRIM(i.SALIAS_PRENOM)||':#:'||TRIM(i.SSECOND_PRENOM)||".
  #		"':#:'||i.SCIVILITE||':#:'||i.SCODE_TITRE||':#:'||i.SSEXE||':#:'||i.SNATIONALITE||".
  #      "':#:'||i.SAUTRE_NATIONALITE||':#:'||i.SIDENTIFIANT_PERSONNEL||".
  #      "':#:'||TO_CHAR(i.DDATE_NAISSANCE, 'DDMMYYYY')||".
  #      "':#:'||i.SFRAUDEUR_CARTE_BANCAIRE||".
  #      "':#:'||p.SMAILING_AUTORISE||':#:'||p.SSOLVABILITE||':#:'||p.SCODE_PROFESSIONNEL||':#:'||p.SCODE_MARITALE||".
  #      "':#:'||p.SCODE_FONCTION||':#:'||p.SCODE_LANGUE||':#:'||p.INB_ENFANTS||':#:'||p.SSEGMENT||':#:'||p.SETUDIANT||".
  #      "':#:'||TRIM(i.SSITE_CREATION)||':#:'||TO_CHAR(i.DDATE_CREATION, 'DDMMYYYY')||':#:'||TRIM(i.SSIGNATURE_CREATION)||".
  #      "':#:'||TRIM(i.SSITE_MODIFICATION)||':#:'||TO_CHAR(i.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||TRIM(i.SSIGNATURE_MODIFICATION)||".
  #	"':#:'||TRIM(i.SGIN_FUSION)||".
  #      "':#:', chr(10), '' ), chr(13), '' )".
  #      " FROM ".$individuTempTableName." i, PROFILS p WHERE i.SGIN = p.SGIN(+)");

  simpleSqlSelect(\$dbConnexion, \$error, "SELECT CHAMP_UNIQUE from ".$individuTempTableNameBis );


  $dbConnexion->do("DROP TABLE ".$individuTempTableName);
  $dbConnexion->do("DROP TABLE ".$individuTempTableNameBis);
}
elsif ($replicationNumber == 111)
{
  # See if INDIVIDUS_TEMP exists
  my $individuTempTableName = getTempTableName("INDIVIDUS_ALL");
  dropIfExistTable(\$dbConnexion, \$error, $individuTempTableName);

  my $individuTempTableNameBis = getTempTableName("INDIVIDUS_ALL_BIS");
  dropIfExistTable(\$dbConnexion, \$error, $individuTempTableNameBis);

  # $dbConnexion->do("alter session set max_dump_file_size=unlimited");
  # $dbConnexion->do("alter session set events='10046 trace name context forever, level 8'");
  $dbConnexion->do("alter session set hash_area_size=64000000");
  $dbConnexion->do("alter session set sort_area_size=64000000");
  $dbConnexion->do("alter session set db_file_multiblock_read_count=128");

  $dbConnexion->do("CREATE TABLE ".$individuTempTableName." NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) as SELECT ".
        "i.SGIN, i.SSTATUT_INDIVIDU, i.SNOM, i.SALIAS, i.SPRENOM, i.SALIAS_PRENOM, i.SSECOND_PRENOM, ".
	"i.SCIVILITE, i.SCODE_TITRE, i.SSEXE, i.SNATIONALITE, i.SAUTRE_NATIONALITE, i.SIDENTIFIANT_PERSONNEL, ".
        "i.DDATE_NAISSANCE, i.SFRAUDEUR_CARTE_BANCAIRE, i.SSITE_CREATION, i.DDATE_CREATION, i.SSIGNATURE_CREATION, ".
        "i.SSITE_MODIFICATION, i.DDATE_MODIFICATION, i.SSIGNATURE_MODIFICATION, i.SGIN_FUSION, i.STYPE ".
#		"FROM INDIVIDUS_ALL i WHERE i.STYPE <> 'W' ".getTimeWindowConditionString("i", $startDate, $endDate, 0));
        "FROM INDIVIDUS_ALL i ".getTimeWindowConditionString("i", $startDate, $endDate, 1));
		

  $dbConnexion->do("create table ".$individuTempTableNameBis." NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) as ". 
  "SELECT REPLACE(REPLACE('0111:#:'||i.SGIN||':#::#::#:'||i.SSTATUT_INDIVIDU||':#:'".
  "||TRIM(i.SNOM)||':#:'||TRIM(i.SALIAS)||':#:'||TRIM(i.SPRENOM)||':#:'".
  "||TRIM(i.SALIAS_PRENOM)||':#:'||TRIM(i.SSECOND_PRENOM)||':#:'||i.SCIVILITE".
  "||':#:'||i.SCODE_TITRE||':#:'||i.SSEXE||':#:'||i.SNATIONALITE||':#:'||i.SAUTRE_NATIONALITE".
  "||':#:'||i.SIDENTIFIANT_PERSONNEL||':#:'".
  "||TO_CHAR(i.DDATE_NAISSANCE, 'DDMMYYYY')".
  "||':#:'||i.SFRAUDEUR_CARTE_BANCAIRE||':#:'||p.SMAILING_AUTORISE||':#:'||p.SSOLVABILITE||':#:'".
  "||p.SCODE_PROFESSIONNEL||':#:'||p.SCODE_MARITALE||':#:'||p.SCODE_FONCTION||':#:'||p.SCODE_LANGUE".
  "||':#:'||p.INB_ENFANTS||':#:'||p.SSEGMENT||':#:'||p.SETUDIANT||':#:'||TRIM(i.SSITE_CREATION)||':#:'".
  "||TO_CHAR(i.DDATE_CREATION, 'DDMMYYYY')||':#:'||TRIM(i.SSIGNATURE_CREATION)||':#:'||TRIM(i.SSITE_MODIFICATION)".
  "||':#:'||TO_CHAR(i.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||TRIM(i.SSIGNATURE_MODIFICATION)||':#:'".
  "||TRIM(i.SGIN_FUSION)||':#:'||i.STYPE||':#:', chr(10), '' ), chr(13), '' )".
  "AS CHAMP_UNIQUE FROM ".$individuTempTableName." i, PROFILS p WHERE i.SGIN = p.SGIN(+) ");

  simpleSqlSelect(\$dbConnexion, \$error, "SELECT CHAMP_UNIQUE from ".$individuTempTableNameBis );


  $dbConnexion->do("DROP TABLE ".$individuTempTableName);
  $dbConnexion->do("DROP TABLE ".$individuTempTableNameBis);

}
elsif ($replicationNumber == 115)
{
    my $request = "SELECT ".
        "'0115:#:'||ID||':#:'||IVERSION||':#:'||ACCOUNT_IDENTIFIER||':#:'||SGIN||':#:'||STATUS||':#:'||PASSWORD_TO_CHANGE||':#:'||".
        "TO_CHAR(TEMPORARY_PWD_END_DATE, 'DDMMYYYY')||':#:'||NB_FAILURE_AUTHENTIFICATION||':#:'||NB_FAILURE_SECRET_QUESTION_ANS||':#:'||".
        "ENROLEMENT_POINT_OF_SELL||':#:'||CARRIER||':#:'||TO_CHAR(LAST_CONNECTION_DATE, 'DDMMYYYY')||':#:'||".
        "TO_CHAR(LAST_PWD_RESET_DATE, 'DDMMYYYY')||':#:'||TO_CHAR(ACCOUNT_DELETION_DATE, 'DDMMYYYY')||':#:'||".
        "SSITE_CREATION||':#:'||SSIGNATURE_CREATION||':#:'||TO_CHAR(DDATE_CREATION, 'DDMMYYYY')||':#:'||".
        "SSITE_MODIFICATION||':#:'||SSIGNATURE_MODIFICATION||':#:'||TO_CHAR(DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||".
	"SOCIAL_NETWORK_ID||':#:'||LAST_SOCIAL_NETWORK_LOGON_DATE||':#:'||LAST_SOCIAL_NETWORK_USED||':#:'||".
	"LAST_SOCIAL_NETWORK_ID".
        " FROM ACCOUNT_DATA ".
        " WHERE ID IS NOT NULL";
    if (defined($startDate))
    {
        $request .= " AND DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')";
	}
    if (defined($endDate))
    {
        $request .= " AND DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')";
    }
	
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 120)
{
    my $selectRequest = $dbConnexion->prepare("SELECT m.IKEY, ".
        "'0120:#:'||m.IKEY||':#::#::#:'|| m.IVERSION||':#:'||TO_CHAR(m.DDATE_DEBUT_VALIDITE, 'DDMMYYYY')||':#:'||".
        "TO_CHAR(m.DDATE_FIN_VALIDITE, 'DDMMYYYY')||':#:0110:#:'||m.SGIN_INDIVIDUS||':#:'||".
        "DECODE(p.STYPE, 'A', DECODE(a.SAGENCE_RA2, 'N', '0160', '0170'), 'T', '0150', 'E', '0140', 'G', '0130', 'S', '9999')||':#:'||m.SGIN_PM||':#:', ".
        "':#:'||m.SCLIENT||':#:'||m.SCONTACT||':#:'||m.SCONTACT_AF||':#:'||".
        "m.SERVICE_AF||':#:'||m.SEMISSION_HS ".
        "FROM AGENCE a, MEMBRE m, PERS_MORALE p ".
        "WHERE a.SGIN = p.SGIN AND m.SGIN_PM = p.SGIN".getTimeWindowConditionString("m", $startDate, $endDate, 0));
    if ($selectRequest->execute())
    {
        while (my @row = $selectRequest->fetchrow_array())
        {
            my $codeFonction = "";
            my $subSelectRequest = $dbConnexion->prepare("SELECT * FROM ".
                "(SELECT SFONCTION FROM FONCTION WHERE IKEY_MEMBRE = ".$row[0]." ORDER BY DDATE_MODIFICATION DESC) ".
                "WHERE ROWNUM <=2");
            if ($subSelectRequest->execute())
            {
                while (my @subRow = $subSelectRequest->fetchrow_array())
                {
		  if ($subRow[0] ne "SAL")
		  {
		      $codeFonction = $subRow[0];

		  }
                }
                print($row[1].$codeFonction.$row[2]."\n");
            }
            else
            {
                $error = 1;
                print(STDERR "ERROR: cannot execute request: ".$subSelectRequest->errstr."\n");
            }
        }
    }
    else
    {
        $error = 1;
        print(STDERR "ERROR: cannot execute request: ".$selectRequest->errstr."\n");
    }
    $selectRequest->finish();
}
elsif ($replicationNumber == 121)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "REPLACE(REPLACE(REPLACE(REPLACE('0121:#:'||TRIM(m.IKEY)||':#::#::#:'||TRIM(m.IVERSION)||':#:'||TO_CHAR(m.DDATE_DEBUT_VALIDITE,'DDMMYYYY')||':#:'".
        "||TO_CHAR(m.DDATE_FIN_VALIDITE, 'DDMMYYYY')||':#:0110:#:'||TRIM(m.SGIN_INDIVIDUS)||':#:'".
        "||DECODE(p.STYPE,'A','0160','E','0140','T','0150','G','0130','S','9999')||':#:'||TRIM(m.SGIN_PM)||':#:'".
		"||TRIM(m.SCLIENT)||':#:'||TRIM(m.SCONTACT)||':#:'||TRIM(m.SCONTACT_AF) ".
		", chr(00), '' ), chr(10), '' ), chr(13), '' ), chr(26), '' ) ".
		"FROM MEMBRE m, PERS_MORALE p ".
        "WHERE m.SGIN_PM = p.SGIN".getTimeWindowConditionString("m", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 123)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0123:#:'||co.CONSENT_ID||':#:'||co.SGIN||':#:'||co.STYPE||':#:'||TO_CHAR(co.DDATE_CREATION,'DDMMYYYY')||':#:'||co.SSIGNATURE_CREATION||':#:'||co.SSITE_CREATION||':#:'".
        "||TO_CHAR(co.DDATE_MODIFICATION,'DDMMYYYY')||':#:'||co.SSIGNATURE_MODIFICATION||':#:'||co.SSITE_MODIFICATION ".
		"FROM CONSENT co ".
        getTimeWindowConditionString("co", $startDate, $endDate, 1));
}
elsif ($replicationNumber == 124)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0124:#:'||cod.CONSENT_DATA_ID||':#:'||cod.CONSENT_ID||':#:'||cod.STYPE||':#:'||cod.SCONSENT||':#:'||TO_CHAR(cod.DDATE_CONSENT,'DDMMYYYY')||':#:'||TO_CHAR(cod.DDATE_CREATION,'DDMMYYYY')||':#:'".
        "||cod.SSIGNATURE_CREATION||':#:'||cod.SSITE_CREATION||':#:'||TO_CHAR(cod.DDATE_MODIFICATION,'DDMMYYYY')||':#:'||cod.SSIGNATURE_MODIFICATION||':#:'||cod.SSITE_MODIFICATION ".
		"FROM CONSENT_DATA cod ".
        getTimeWindowConditionString("cod", $startDate, $endDate, 1));
}
elsif ($replicationNumber == 125)
{
	# REPIND-1144 : Send to DWH a file of Comm Pref with or without Account
    my $request = "SELECT ".
        "'0125:#:'||cp.COM_PREF_ID||':#:'||a.ACCOUNT_IDENTIFIER||':#:'||cp.SGIN||':#:'||cp.DOMAIN||':#:'||cp.COM_GROUP_TYPE||':#:'||cp.COM_TYPE||':#:'".
        "||cp.MEDIA1||':#:'||cp.MEDIA2||':#:'||cp.MEDIA3||':#:'||cp.MEDIA4||':#:'||cp.MEDIA5||':#:'||cp.SUBSCRIBE||':#:'||".
        "TO_CHAR(cp.DATE_OPTIN, 'DDMMYYYY')||':#:'||cp.CHANNEL||':#:'||cp.OPTIN_PARTNERS||':#:'||".
        "TO_CHAR(cp.DATE_OPTIN_PARTNERS, 'DDMMYYYY')||':#:'||TO_CHAR(cp.DATE_OF_ENTRY, 'DDMMYYYY')||':#:'||".
        "TO_CHAR(cp.CREATION_DATE, 'DDMMYYYY')||':#:'||cp.CREATION_SIGNATURE||':#:'||cp.CREATION_SITE||':#:'||".
        "TO_CHAR(cp.MODIFICATION_DATE, 'DDMMYYYY')||':#:'||cp.MODIFICATION_SIGNATURE||':#:'||cp.MODIFICATION_SITE ".
        " FROM COMMUNICATION_PREFERENCES  cp LEFT OUTER JOIN ACCOUNT_DATA a ".
        " ON cp.SGIN = a.SGIN WHERE 1 = 1";
		
    if (defined($startDate))
    {
        $request .= " AND (cp.modification_date >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
    }
    if (defined($endDate))
    {
        $request .= " AND (cp.modification_date < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
    }
	
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 126)
{
	# REPIND-1144 : Send to DWH a file of Comm Pref with or without Account
    my $request = "SELECT ".
        "'0126:#:'||ml.market_language_id||':#:'||ml.com_pref_id||':#:'||ml.market||':#:'||ml.language_code||':#:'||ml.optin||':#:'||".
	  "TO_CHAR(NVL(ml.date_optin, ml.date_optin), 'DDMMYYYY')||':#:'||ml.media1||':#:'||ml.media2||':#:'||ml.media3||':#:'||ml.media4||':#:'||ml.media5||':#:'||".
	  "TO_CHAR(NVL(ml.creation_date, ml.creation_date), 'DDMMYYYY')||':#:'||ml.creation_signature||':#:'||ml.creation_site||':#:'||".
	  "TO_CHAR(NVL(ml.modification_date, ml.modification_date), 'DDMMYYYY')||':#:'||ml.modification_signature||':#:'||ml.modification_site ".
        " FROM MARKET_LANGUAGE ml, COMMUNICATION_PREFERENCES cp ".
        " WHERE cp.com_pref_id = ml.com_pref_id ";
		
    if (defined($startDate))
    {
        $request .= " AND (cp.modification_date >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') OR ml.modification_date >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
    }
    if (defined($endDate))
    {
        $request .= " AND (cp.modification_date < TO_DATE('$endDate', 'YYYYMMDDHH24MISS') OR ml.modification_date < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
    }
	
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}	
elsif ($replicationNumber == 127)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0127:#:'||p.PREFERENCE_ID||':#:'||p.SGIN||':#:'||p.STYPE||':#:'||p.ILINK||".        
        "':#:'||TO_CHAR(p.DDATE_CREATION, 'DDMMYYYY')||':#:'||p.SSITE_CREATION||':#:'||p.SSIGNATURE_CREATION||".
		"':#:'||TO_CHAR(p.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||p.SSITE_MODIFICATION||':#:'||p.SSIGNATURE_MODIFICATION ".
        "FROM PREFERENCE p ".
		getTimeWindowConditionString("p", $startDate, $endDate, 1));
}
# REPIND-1579 : We based on 127 PREF to get who are been treat on 128 PREF DATA because of date storage prob on PREF DATA 
elsif ($replicationNumber == 128)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "REPLACE(REPLACE('0128:#:'||pd.PREFERENCE_DATA_ID||':#:'||pd.PREFERENCE_ID||':#:'||pd.SKEY||':#:'||pd.SVALUE||".        
        "':#:'||TO_CHAR(pd.DDATE_CREATION, 'DDMMYYYY')||':#:'||pd.SSITE_CREATION||':#:'||pd.SSIGNATURE_CREATION||".
		"':#:'||TO_CHAR(pd.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||pd.SSITE_MODIFICATION||':#:'||pd.SSIGNATURE_MODIFICATION, chr(10), ' '), chr(13), ' ')".
        "FROM PREFERENCE_DATA pd, PREFERENCE p ".
        "WHERE pd.PREFERENCE_ID = p.PREFERENCE_ID ".
        getTimeWindowConditionString("pd", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 130)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0130:#:'||g.SGIN||':#::#::#:'|| pm.SSTATUT||':#:'||TO_CHAR(pm.DDATE_CREATION, 'DDMMYYYY')||".
		"':#:'||TO_CHAR(pm.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||pm.SNOM||':#:'||pm.SACTIVITE_LOCAL||".
		"':#:'||pm.SGIN_FUSION||':#:'||pm.SCODE_INDUSTRIE||':#:'||pm.STYPE_DEMARCHAGE||':#:'||pm.SSITE_INTERNET||".
		"':#:'||pm.SCIE_GEST||':#:'||pm.SCODE_SOURCE||':#:'||pm.SCODE_SUPPORT||':#:'||pf.SEXPORT||".
		"':#:'||pf.SIMPORT||':#:'||pf.SMAILING||':#:'||pf.SCODE_INSEE_EMP||':#:'||pf.SDEFAUT_PAIEMENT||".
		"':#:'||pf.SINTERDICTION_VENTE||':#:'||pf.SLANGUE_ECRITE||':#:'||pf.SLANGUE_PARLEE||':#:'||pf.SNATIONALITE||".
		"':#:'||pf.INOMBRE_EMPLOYE||':#:'||g.SCODE ".
        "FROM GROUPE g, PERS_MORALE pm, PROFIL_FIRME pf ".
		"WHERE g.SGIN = pm.SGIN AND g.SGIN = pf.SGIN(+)".getTimeWindowConditionString("pm", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 140)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0140:#:'||e.SGIN||':#:'||LPAD(g.SGIN+130-g.SGIN,4,'0')||':#:'||g.SGIN||':#:'||pm.SSTATUT||':#:'||TO_CHAR(pm.DDATE_CREATION, 'DDMMYYYY')||".
        "':#:'||TO_CHAR(pm.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||TRIM(pm.SNOM)||':#:'||pm.SACTIVITE_LOCAL||".
		"':#:'||pm.SGIN_FUSION||':#:'||pm.SCODE_INDUSTRIE||':#:'||pm.STYPE_DEMARCHAGE||':#:'||pm.SSITE_INTERNET||".
		"':#:'||pm.SCIE_GEST||':#:'||pm.SCODE_SOURCE||':#:'||pm.SCODE_SUPPORT||':#:'||pf.SEXPORT||".
		"':#:'||pf.SIMPORT||':#:'||pf.SMAILING||':#:'||pf.SCODE_INSEE_EMP||':#:'||pf.SDEFAUT_PAIEMENT||".
		"':#:'||pf.SINTERDICTION_VENTE||':#:'||pf.SLANGUE_ECRITE||':#:'||pf.SLANGUE_PARLEE||':#:'||pf.SNATIONALITE||".
		"':#:'||pf.INOMBRE_EMPLOYE||':#:'||e.SSIREN ".
        "FROM ENTREPRISE e, PERS_MORALE pm, PROFIL_FIRME pf, GROUPE g ".
        "WHERE e.SGIN = pm.SGIN ".
        "AND e.SGIN = pf.SGIN(+) ".
        "AND pm.SGIN_PERE = g.SGIN(+)".getTimeWindowConditionString("pm", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 150)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
		"'0150:#:'||et.SGIN||':#:'||LPAD(pm.SGIN_PERE+140-pm.SGIN_PERE,4,'0')||':#:'||pm.SGIN_PERE||':#:'||pm.SSTATUT||':#:'||TO_CHAR(pm.DDATE_CREATION, 'DDMMYYYY')||".
        "':#:'||TO_CHAR(pm.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||TRIM(pm.SNOM)||':#:'||pm.SACTIVITE_LOCAL||".
		"':#:'||pm.SGIN_FUSION||':#:'||pm.SCODE_INDUSTRIE||':#:'||pm.STYPE_DEMARCHAGE||':#:'||pm.SSITE_INTERNET||".
		"':#:'||pm.SCIE_GEST||':#:'||pm.SCODE_SOURCE||':#:'||pm.SCODE_SUPPORT||':#:'||pf.SEXPORT||".
		"':#:'||pf.SIMPORT||':#:'||pf.SMAILING||':#:'||pf.SCODE_INSEE_EMP||':#:'||pf.SDEFAUT_PAIEMENT||".
		"':#:'||pf.SINTERDICTION_VENTE||':#:'||pf.SLANGUE_ECRITE||':#:'||pf.SLANGUE_PARLEE||':#:'||pf.SNATIONALITE||".
		"':#:'||pf.INOMBRE_EMPLOYE||':#:'||et.SSIRET||':#:'||et.SSIEGE_SOCIAL ||".
        "':#:'||et.SCE||':#:'||TRIM(et.SREM)||':#:'||TRIM(et.STYPE) ".
        "FROM ETABLISSEMENT et, PERS_MORALE pm, PROFIL_FIRME pf ".
        "WHERE et.SGIN = pm.SGIN AND et.SGIN = pf.SGIN(+)".getTimeWindowConditionString("pm", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 160)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0160:#:'||a.SGIN||':#:'||DECODE(pm.SGIN_PERE, null, '', DECODE(pm.STYPE, 'A', '0160', 'E', '0150', 'T', '0140', 'G', '130', '' ))||':#:'||pm.SGIN_PERE||".
        "':#:'||pm.SSTATUT||':#:'||TO_CHAR(pm.DDATE_CREATION, 'DDMMYYYY')||".
        "':#:'||TO_CHAR(pm.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||TRIM(pm.SNOM)||':#:'||pm.SACTIVITE_LOCAL||".
		"':#:'||pm.SGIN_FUSION||':#:'||pm.SCODE_INDUSTRIE||':#:'||pm.STYPE_DEMARCHAGE||':#:'||pm.SSITE_INTERNET||".
		"':#:'||pm.SCIE_GEST||':#:'||pm.SCODE_SOURCE||':#:'||pm.SCODE_SUPPORT||':#:'||pf.SEXPORT||".
		"':#:'||pf.SIMPORT||':#:'||pf.SMAILING||':#:'||pf.SCODE_INSEE_EMP||':#:'||pf.SDEFAUT_PAIEMENT||".
		"':#:'||pf.SINTERDICTION_VENTE||':#:'||pf.SLANGUE_ECRITE||':#:'||pf.SLANGUE_PARLEE||':#:'||pf.SNATIONALITE||".
		"':#:'||pf.INOMBRE_EMPLOYE||':#:'||a.STYPE||':#:'||a.SSTATUT_IATA ||".
		"':#:'||TO_CHAR(a.DDAT_MODIF_STA_IATA, 'DDMMYYYY')||':#:'||TO_CHAR(a.DDATE_DEB, 'DDMMYYYY')||':#:'||TO_CHAR(a.DDATE_FIN, 'DDMMYYYY') ||".
		"':#:'||a.STYPE_AGREMENT||':#:'||TO_CHAR(a.DDATE_AGREMENT, 'DDMMYYYY')||':#:'||TO_CHAR(a.DDATE_RADIATION, 'DDMMYYYY') ||".
		"':#:'||a.SINFRA||':#:'||a.STYPE_VENTE||':#:'||a.SLOCALISATION ||".
		"':#:'||a.SDOMAINE||':#:'||a.SSOUS_DOMAINE||':#:'||a.SCIBLE ||".
		"':#:'||a.SBSP||':#:'||a.SEXCLUSIF_GRD_CPT||':#:'||a.SENVOI_SI ||".
		"':#:'||a.SGSA||':#:'||a.SNUMERO_IATA_MERE||':#:'||a.SCOD_VIL_ISO ||".
		"':#:'||a.SOBSERVATION||':#:'||a.SZONE_CHALANDISE||':#:'||a.SCODE_SERVICE ||".
		"':#:'||a.SAGENCE_RA2||':#:'||a.SIATA_STATION_AIRPORT_CODE ||".
		"':#:'||a.SFORCING_AIRPORT_CODE_UPDATE||':#:' ".
        "FROM AGENCE a, PERS_MORALE pm, PROFIL_FIRME pf ".
        "WHERE a.SAGENCE_RA2 = 'N' ".
        "AND  a.SGIN = pm.SGIN ".
        "AND a.SGIN = pf.SGIN(+)".getTimeWindowConditionString("pm", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 170)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0170:#:'||a.SGIN||':#::#::#:'||pm.SSTATUT||':#:'||TO_CHAR(pm.DDATE_CREATION, 'DDMMYYYY')||".
		"':#:'||TO_CHAR(pm.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||pm.SNOM||':#:'||pm.SACTIVITE_LOCAL||".
		"':#:'||pm.SGIN_FUSION||':#:'||pm.SCODE_INDUSTRIE||':#:'||pm.STYPE_DEMARCHAGE||':#:'||pm.SSITE_INTERNET||".
		"':#:'||pm.SCIE_GEST||':#:'||pm.SCODE_SOURCE||':#:'||pm.SCODE_SUPPORT||':#:'||pf.SEXPORT||".
		"':#:'||pf.SIMPORT||':#:'||pf.SMAILING||':#:'||pf.SCODE_INSEE_EMP||':#:'||pf.SDEFAUT_PAIEMENT||".
		"':#:'||pf.SINTERDICTION_VENTE||':#:'||pf.SLANGUE_ECRITE||':#:'||pf.SLANGUE_PARLEE||':#:'||pf.SNATIONALITE||".
		"':#:'||pf.INOMBRE_EMPLOYE||':#:'||a.STYPE||':#:'||a.SSTATUT_IATA ||".
		"':#:'||TO_CHAR(a.DDAT_MODIF_STA_IATA, 'DDMMYYYY')||':#:'||TO_CHAR(a.DDATE_DEB, 'DDMMYYYY')||':#:'||TO_CHAR(a.DDATE_FIN, 'DDMMYYYY') ||".
		"':#:'||a.STYPE_AGREMENT||':#:'||TO_CHAR(a.DDATE_AGREMENT, 'DDMMYYYY')||':#:'||TO_CHAR(a.DDATE_RADIATION, 'DDMMYYYY') ||".
		"':#:'||a.SINFRA||':#:'||a.STYPE_VENTE||':#:'||a.SLOCALISATION ||".
		"':#:'||a.SDOMAINE||':#:'||a.SSOUS_DOMAINE||':#:'||a.SCIBLE ||".
		"':#:'||a.SBSP||':#:'||a.SEXCLUSIF_GRD_CPT||':#:'||a.SENVOI_SI ||".
		"':#:'||a.SGSA||':#:'||a.SNUMERO_IATA_MERE||':#:'||a.SCOD_VIL_ISO ||".
		"':#:'||a.SOBSERVATION||':#:'||a.SZONE_CHALANDISE||':#:'||a.SCODE_SERVICE ||".
		"':#:'||a.SAGENCE_RA2||':#:' ".
        "FROM AGENCE a, PERS_MORALE pm, PROFIL_FIRME pf ".
        "WHERE a.SAGENCE_RA2 = 'Y' ".
        "AND a.SGIN = pm.SGIN ".
        "AND a.SGIN = pf.SGIN(+)".getTimeWindowConditionString("pm", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 180)
{
    simpleSqlSelect(\$dbConnexion, \$error, get180Request($startDate, $endDate));
}
elsif ($replicationNumber == 190)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "REPLACE(REPLACE(REPLACE(REPLACE('0190:#:'||TRIM(f.ICLE)||':#:0121:#:'||TRIM(f.IKEY_MEMBRE)||':#:'||TRIM(f.IVERSION)||':#:'".
        "||TRIM(f.SFONCTION)||':#:'||TO_CHAR(f.DDATE_DEBUT_VALIDITE,'DDMMYYYY')||':#:'||TO_CHAR(f.DDATE_FIN_VALIDITE,'DDMMYYYY')".
        ", chr(00), '' ), chr(10), '' ), chr(13), '' ), chr(26), '' ) ".
        "FROM FONCTION f, MEMBRE m WHERE f.IKEY_MEMBRE = m.IKEY".getTimeWindowConditionString("m", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 200)
{
    my $request = "SELECT TRIM(u.SAIN_ADR), u.SCODE_APPLICATION, u.INUM FROM USAGE_MEDIUMS u";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", ADR_POST a";
    }
    $request .= " WHERE u.SAIN_ADR IS NOT NULL";
    if (defined($startDate) || defined($endDate))
    {
        $request .= " AND u.SAIN_ADR = a.SAIN".getTimeWindowConditionString("a", $startDate, $endDate, 0);
    }
    $request .= " ORDER BY u.SAIN_ADR";
    my $selectRequest = $dbConnexion->prepare($request);
    if ($selectRequest->execute())
    {
        my $currentAin = "";
        my $first = 1;
        while (my @row = $selectRequest->fetchrow_array())
        {
            if ($currentAin != $row[0])
            {
                if (!$first)
                {
                    print("\n");
                }
                else
                {
                    $first = 0;
                }
                print("0200:#:".$row[0].":#:0210:#:".$row[0].":#:");
                $currentAin = $row[0];
            }
            print($row[1].":#:".$row[2].":#:");
        }
        if (!$first)
        {
            print("\n");
        }
    }
    else
    {
        $error = 1;
        print(STDERR "ERROR: cannot execute request: ".$selectRequest->errstr."\n");
    }
    $selectRequest->finish();
}
elsif ($replicationNumber == 201)
{
    my $request = "SELECT ".
        "REPLACE(REPLACE('0201:#:'||TRIM(u.SRIN)||':#:0210:#:'||TRIM(u.SAIN_ADR)||':#:'".
        "||u.SCODE_APPLICATION||':#:'||u.INUM||':#:', chr(10), ''), chr(13), ''), ".
        "u.SROLE1, u.SROLE2, u.SROLE3, u.SROLE4, u.SROLE5 ".
        "FROM USAGE_MEDIUMS u";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", ADR_POST a";
    }
    $request .= " WHERE u.SAIN_ADR IS NOT NULL";
    if (defined($startDate) || defined($endDate))
    {
        $request.= " AND u.SAIN_ADR = a.SAIN".getTimeWindowConditionString("a", $startDate, $endDate, 0);
    }
    $request .= " ORDER BY u.SAIN_ADR";
    my $selectRequest = $dbConnexion->prepare($request);
    if ($selectRequest->execute())
    {
        while (my @row = $selectRequest->fetchrow_array())
        {
            print($row[0]);
            for (my $i=1; $i<@row; ++$i)
            {
                if (defined($row[$i]))
                {
                    print($row[$i].":#:");
                }
            }
            print("\n");
        }
    }
    else
    {
        $error = 1;
        print(STDERR "ERROR: cannot execute request: ".$selectRequest->errstr."\n");
    }
    $selectRequest->finish();
}
elsif ($replicationNumber == 210)
{
  # See if ADR_POST_TEMP exists
  my $adrPostTempTableName = getTempTableName("ADR_POST");
  dropIfExistTable(\$dbConnexion, \$error, $adrPostTempTableName);
	
  # $dbConnexion->do("alter session set max_dump_file_size=unlimited");
  # $dbConnexion->do("alter session set events='10046 trace name context forever, level 8'");
  $dbConnexion->do("alter session set hash_area_size=64000000");
  $dbConnexion->do("alter session set sort_area_size=64000000");
  $dbConnexion->do("alter session set db_file_multiblock_read_count=128");

  $dbConnexion->do("CREATE TABLE ".$adrPostTempTableName." NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) as SELECT ".
		   "a.SAIN, a.SGIN, a.SGIN_PM, a.SSTATUT_MEDIUM, a.SCODE_MEDIUM, a.SRAISON_SOCIALE, a.SCOMPLEMENT_ADRESSE, a.SLOCALITE, ".
		   "a.SNO_ET_RUE, a.SVILLE, a.SCODE_POSTAL, a.SCODE_PAYS, a.SCODE_PROVINCE, a.DDATE_CREATION, a.DDATE_MODIFICATION, ".
		   "a.SSITE_CREATION, a.SSIGNATURE_CREATION, a.SSITE_MODIFICATION, ".
		   "a.SSIGNATURE_MODIFICATION, a.SFORCAGE, a.ICOD_ERR, a.ICOD_WARNING, a.ICLE_ROLE, a.DDATE_FONCTIONNEL, a.SSITE_FONCTIONNEL, ".
		   "a.SSIGNATURE_FONCTIONNEL, a.SCOD_ERR_SIMPLE, a.SCOD_ERR_DETAILLE, a.STYPE_INVALIDITE, a.SENVOI_POSTAL ".
		   "FROM ADR_POST a ".
		   getTimeWindowConditionString("a", $startDate, $endDate, 1));


    simpleSqlSelect(\$dbConnexion, \$error, get210Request("'0110'", "TRIM(a.SGIN)", " WHERE a.SGIN is not null"));
    simpleSqlSelect(\$dbConnexion, \$error, get210Request("DECODE(b.STYPE,'A','0330','C','0330','G','0320','0310')", "TRIM(a.ICLE_ROLE)",
        ", BUSINESS_ROLE b WHERE a.ICLE_ROLE = b.ICLE_ROLE"));
    simpleSqlSelect(\$dbConnexion, \$error, get210Request("DECODE(g.SAGENCE_RA2,'N','0160','0170')", "TRIM(a.SGIN_PM)",
        ", AGENCE g WHERE a.SGIN_PM = g.SGIN"));
    simpleSqlSelect(\$dbConnexion, \$error, get210Request("DECODE(pm.STYPE,'A','0160','T','0150','E','0140','G','0130','S','9999','')",
        "TRIM(a.SGIN_PM)", ", PERS_MORALE pm WHERE (a.SGIN_PM = pm.SGIN) AND (pm.STYPE <> 'A')"));
    $dbConnexion->do("DROP TABLE ".$adrPostTempTableName);
}
elsif ($replicationNumber == 211)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0211:#:'||TRIM(f.SAIN_ADR)||':#:0210:#:'||TRIM(a.SAIN)||".
        "':#:'||f.SFORMATED_LINE_1||':#:'||f.SFORMATED_LINE_2||':#:'||f.SFORMATED_LINE_3||".
        "':#:'||f.SFORMATED_LINE_4||':#:'||f.SFORMATED_LINE_5||':#:'||f.SFORMATED_LINE_6||".
        "':#:'||f.SFORMATED_LINE_7||".
        "':#:'||f.SFORMATED_LINE_8||".
        "':#:'||f.SFORMATED_LINE_9 ".
        "FROM ADR_POST a, FORMALIZED_ADR f ".
        "WHERE a.SAIN = f.SAIN_ADR and (a.SGIN is not null or a.ICLE_ROLE is not null or a.SGIN_PM is not null)".getTimeWindowConditionString("a", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 212)
  {
    # See if ADR_POST_TEMP exists
    my $adrPostTempTableName = getTempTableName("ADR_POST");
    dropIfExistTable(\$dbConnexion, \$error, $adrPostTempTableName);
    
    # $dbConnexion->do("alter session set max_dump_file_size=unlimited");
    # $dbConnexion->do("alter session set events='10046 trace name context forever, level 8'");
    $dbConnexion->do("alter session set hash_area_size=64000000");
    $dbConnexion->do("alter session set sort_area_size=64000000");
    $dbConnexion->do("alter session set db_file_multiblock_read_count=128");
    
    $dbConnexion->do("CREATE TABLE ".$adrPostTempTableName." NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) as SELECT ".
		     "a.SAIN, a.SGIN, a.SGIN_PM, a.SSTATUT_MEDIUM, a.SCODE_MEDIUM, a.SRAISON_SOCIALE, a.SCOMPLEMENT_ADRESSE, a.SLOCALITE, ".
		     "a.SNO_ET_RUE, a.SVILLE, a.SCODE_POSTAL, a.SCODE_PAYS, a.SCODE_PROVINCE, a.DDATE_CREATION, a.DDATE_MODIFICATION, ".
		     "a.SSITE_CREATION, a.SSIGNATURE_CREATION, a.SSITE_MODIFICATION, ".
		     "a.SSIGNATURE_MODIFICATION, a.SFORCAGE, a.ICOD_ERR, a.ICOD_WARNING, a.ICLE_ROLE, a.DDATE_FONCTIONNEL, a.SSITE_FONCTIONNEL, ".
		     "a.SSIGNATURE_FONCTIONNEL, a.SCOD_ERR_SIMPLE, a.SCOD_ERR_DETAILLE, a.STYPE_INVALIDITE, a.SENVOI_POSTAL ".
		     "FROM ADR_POST a ".
		     getTimeWindowConditionString("a", $startDate, $endDate, 1));
    
    
    simpleSqlSelect(\$dbConnexion, \$error, get212Request("'0110'", "TRIM(a.SGIN)", " WHERE a.SCODE_MEDIUM = 'L' AND a.SGIN is not null "));
    simpleSqlSelect(\$dbConnexion, \$error, get212Request("DECODE(b.STYPE,'A','0330','C','0330','G','0320','0310')", "TRIM(a.ICLE_ROLE)",
							  ", BUSINESS_ROLE b WHERE a.SCODE_MEDIUM = 'L' AND a.ICLE_ROLE = b.ICLE_ROLE "));
    simpleSqlSelect(\$dbConnexion, \$error, get212Request("DECODE(g.SAGENCE_RA2,'N','0160','0170')", "TRIM(a.SGIN_PM)",
							    ", AGENCE g WHERE a.SCODE_MEDIUM = 'L' AND a.SGIN_PM = g.SGIN "));

    simpleSqlSelect(\$dbConnexion, \$error, get212Request("DECODE(pm.STYPE,'A',DECODE(g.SAGENCE_RA2,'N','0160','0170'),'T','0150','E','0140','G','0130','S','9999','')",
							  "TRIM(a.SGIN_PM)", ", PERS_MORALE pm, AGENCE g WHERE (a.SGIN_PM = g.SGIN) AND a.SCODE_MEDIUM = 'L' AND (a.SGIN_PM = pm.SGIN) AND (pm.STYPE <> 'A')"));
   
    # Recuperation des ADR_POST des AGENCES modifiees dans le 160
    if( defined( $startDate ) || defined( $endDate ) ){
      simpleSqlSelect(\$dbConnexion, \$error, "SELECT REPLACE(REPLACE(REPLACE(REPLACE('0212:#:'||TRIM(a.SAIN)||':#:'||'0160'||':#:'||TRIM(a.SGIN_PM)||".
		      "':#:'||a.SSTATUT_MEDIUM||':#:'||TRIM(a.SCODE_MEDIUM)||':#:'||TRIM(a.SRAISON_SOCIALE)||".
		      "':#:'||TRIM(a.SCOMPLEMENT_ADRESSE)||':#:'||TRIM(a.SLOCALITE)||':#:'||TRIM(a.SNO_ET_RUE)||':#:'||TRIM(a.SVILLE)||".
		      "':#:'||TRIM(a.SCODE_POSTAL)||':#:'||TRIM(a.SCODE_PAYS)||':#:'||TRIM(a.SCODE_PROVINCE)||".
		      "':#:'||TO_CHAR(a.DDATE_CREATION, 'DDMMYYYY')||".
		      "':#:'||TO_CHAR(NVL(a.DDATE_MODIFICATION, a.DDATE_CREATION), 'DDMMYYYY')||".
		      "':#:'||TRIM(a.SSITE_CREATION)||':#:'||TRIM(a.SSIGNATURE_CREATION)||".
		      "':#:'||TRIM(a.SSITE_MODIFICATION)||':#:'||TRIM(a.SSIGNATURE_MODIFICATION)||':#:'||TRIM(a.SFORCAGE)||".
		      "':#:'||TRIM(a.ICOD_ERR)||':#:'||TRIM(a.ICOD_WARNING), chr(00), '' ), chr(10), '' ), chr(13), '' ), chr(26), '' )||".
		      "':#:'||TO_CHAR(a.DDATE_FONCTIONNEL, 'DDMMYYYY')||':#:'||TRIM(a.SSITE_FONCTIONNEL)||".
		      "':#:'||TRIM(a.SSIGNATURE_FONCTIONNEL)||':#:'||TRIM(a.SCOD_ERR_SIMPLE)||".
		      "':#:'||TRIM(a.SCOD_ERR_DETAILLE)||':#:'||TRIM(a.STYPE_INVALIDITE)||':#:'||TRIM(a.SENVOI_POSTAL)".
		      " FROM ADR_POST a, PERS_MORALE pm, AGENCE ag WHERE ag.SAGENCE_RA2 = 'N' AND  ag.SGIN = pm.SGIN AND a.SCODE_MEDIUM = 'L' ".
		      " AND a.SGIN_PM = pm.SGIN ".getTimeWindowConditionString("pm", $startDate, $endDate, 0));
  }

    $dbConnexion->do("DROP TABLE ".$adrPostTempTableName);
}
elsif ($replicationNumber == 220)
{
      my $telecomsTempTableName = getTempTableName("TELECOMS");
      dropIfExistTable(\$dbConnexion, \$error, $telecomsTempTableName);
	
      # $dbConnexion->do("alter session set max_dump_file_size=unlimited");
      # $dbConnexion->do("alter session set events='10046 trace name context forever, level 8'");
      $dbConnexion->do("alter session set hash_area_size=64000000");
      $dbConnexion->do("alter session set sort_area_size=64000000");
      $dbConnexion->do("alter session set db_file_multiblock_read_count=128");

      $dbConnexion->do("CREATE TABLE ".$telecomsTempTableName." NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) as SELECT ".
                       "t.SAIN, t.SGIN, t.SGIN_PM, t.SSTATUT_MEDIUM, t.SCODE_MEDIUM, t.STERMINAL, t.SINDICATIF, t.SCODE_REGION, ".
                       "t.SNUMERO, t.SDESCRIPTIF_COMPLEMENTAIRE, t.DDATE_CREATION, t.DDATE_MODIFICATION, t.DDATE_INVALIDATION, t.SSITE_CREATION, t.SSIGNATURE_CREATION, ".
                      "t.SSITE_MODIFICATION, t.SSIGNATURE_MODIFICATION, t.SNORM_NAT_PHONE_NUMBER, ".
                      "t.SNORM_INTER_COUNTRY_CODE, t.SNORM_INTER_PHONE_NUMBER, ".
                      "t.SNORM_TERMINAL_TYPE_DETAIL, t.ISNORMALIZED, t.SNORM_NAT_PHONE_NUMBER_CLEAN ".
                      "FROM TELECOMS t WHERE (NVL(t.SGIN,t.SGIN_PM) IS NOT NULL)".getTimeWindowConditionString("t", $startDate, $endDate, 0));


       simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
                      "REPLACE(REPLACE('0220:#:'||TRIM(t.SAIN)||':#:'||".
                      "DECODE(pm.STYPE, 'A', DECODE(g.SAGENCE_RA2, 'N', '0160', '0170'), 'T', '0150', 'E', '0140', 'G', '0130', 'S', '0130', '0110')||':#:'||".
                      "TRIM(NVL(t.SGIN,t.SGIN_PM))||':#:'||TRIM(t.SSTATUT_MEDIUM)||':#:'||TRIM(t.SCODE_MEDIUM)||':#:'||TRIM(t.STERMINAL)||':#:'||".
                      "TRIM(t.SINDICATIF)||':#:'||TRIM(t.SCODE_REGION)||':#:'||TRIM(t.SNUMERO)||':#:'||TRIM(t.SDESCRIPTIF_COMPLEMENTAIRE)||':#:'||".
                      "TO_CHAR(t.DDATE_CREATION, 'DDMMYYYY')||':#:'||TO_CHAR(t.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||".
                      "TRIM(t.SSITE_CREATION)||':#:'||TRIM(t.SSIGNATURE_CREATION)||".
                     "':#:'||TRIM(t.SSITE_MODIFICATION)||':#:'||TRIM(t.SSIGNATURE_MODIFICATION)||".	
                     "':#:'||t.SNORM_NAT_PHONE_NUMBER||".
                     "':#:'||t.SNORM_INTER_COUNTRY_CODE||':#:'||t.SNORM_INTER_PHONE_NUMBER||".
                     "':#:'||t.SNORM_TERMINAL_TYPE_DETAIL||':#:'||t.ISNORMALIZED||':#:'||t.SNORM_NAT_PHONE_NUMBER_CLEAN||".
					 "':#:'||TO_CHAR(t.DDATE_INVALIDATION,'DDMMYYYY') ".
                     ", chr(10), '' ), chr(13), '' ) ".
                      "FROM ".$telecomsTempTableName." t, PERS_MORALE pm, AGENCE g WHERE t.SGIN_PM = pm.SGIN(+) AND pm.SGIN = g.SGIN(+)");


      $dbConnexion->do("DROP TABLE ".$telecomsTempTableName);
}
elsif ($replicationNumber == 230)
{
  # See if EMAILS_TEMP exists
  my $emailsTempTableName = getTempTableName("EMAILS");
  dropIfExistTable(\$dbConnexion, \$error, $emailsTempTableName);
	
  # $dbConnexion->do("alter session set max_dump_file_size=unlimited");
  # $dbConnexion->do("alter session set events='10046 trace name context forever, level 8'");
  $dbConnexion->do("alter session set hash_area_size=64000000");
  $dbConnexion->do("alter session set sort_area_size=64000000");
  $dbConnexion->do("alter session set db_file_multiblock_read_count=128");

  $dbConnexion->do("CREATE TABLE ".$emailsTempTableName." NOLOGGING TABLESPACE SIC_TEMP STORAGE (INITIAL 100M next 100M) as SELECT ".
		   "e.SAIN, e.SGIN, e.SGIN_PM, e.ICLE_ROLE, e.SSTATUT_MEDIUM, e.SCODE_MEDIUM, e.SEMAIL, e.SAUTORISATION_MAILING, ".
		   "e.SDESCRIPTIF_COMPLEMENTAIRE, e.DDATE_CREATION, e.DDATE_MODIFICATION, e.SSITE_CREATION, e.SSIGNATURE_CREATION, ".
		   "e.SSITE_MODIFICATION, e.SSIGNATURE_MODIFICATION ".
		   "FROM EMAILS e ".
		   getTimeWindowConditionString("e", $startDate, $endDate, 1));

    my $request = "SELECT TRIM(e.SAIN), e.SGIN, e.SGIN_PM, b.STYPE, p.STYPE, p.SACTIVITE_LOCAL, ".
        "REPLACE(REPLACE(TRIM(NVL(NVL(e.SGIN,e.SGIN_PM),e.ICLE_ROLE))||':#:'||e.SSTATUT_MEDIUM||':#:'||e.SCODE_MEDIUM||".
        "':#:'||TRIM(e.SEMAIL)||':#:'||e.SAUTORISATION_MAILING||':#:'||e.SDESCRIPTIF_COMPLEMENTAIRE||".
        "':#:'||TO_CHAR(e.DDATE_CREATION, 'DDMMYYYY')||':#:'||TO_CHAR(e.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||".
        "TRIM(e.SSITE_CREATION)||':#:'||TRIM(e.SSIGNATURE_CREATION)||".
        "':#:'||TRIM(e.SSITE_MODIFICATION)||':#:'||TRIM(e.SSIGNATURE_MODIFICATION), chr(10), '' ), chr(13), '' ) ".
        "FROM ".$emailsTempTableName." e, PERS_MORALE p, BUSINESS_ROLE b ".
        "WHERE e.SGIN_PM = p.SGIN(+) AND e.ICLE_ROLE = b.ICLE_ROLE(+)";
    my $selectRequest = $dbConnexion->prepare($request);
    if ($selectRequest->execute())
    {
        while (my @row = $selectRequest->fetchrow_array())
        {
            my $codeP;
            if (defined($row[1])) # e.SGIN
            {
                $codeP = "0110"; # individu
            }
            elsif (defined($row[2])) # e.SGIN_PM
            {
                if (defined($row[4]))  # p.STYPE
                {
                    if ($row[4] eq "A") # agence
                    {
                        if ($row[5] eq "RA2") # p.SACTIVITE_LOCAL
                        {
                            $codeP = "0170"; # agence ra2
                        }
                        else
                        {
                            $codeP = "0160"; # agence autre
                        }
                    }
                    elsif ($row[4] eq "E") # entreprise
                    {
                        $codeP = "0140";
                    }
                    elsif ($row[4] eq "G") # groupe
                    {
                        $codeP = "0130";
                    }
                    elsif ($row[4] eq "T") # etablissement
                    {
                        $codeP = "0150";
                    }
                    elsif ($row[4] eq "S") # service
                    {
                        $codeP = "9999";
                    }
                }
            }
            elsif (defined($row[3])) # b.STYPE
            {
                if ($row[3] eq "A")
                {
                    $codeP = "0330"; # role affaire
                }
                elsif ($row[3] eq "C")
                {
                    $codeP = "0330"; # role contrat
                }
                elsif ($row[3] eq "G")
                {
                    $codeP = "0320"; # role gp
                }
                else
                {
                    $codeP = "0310"; # role firme
                }
            }
            if (defined($codeP))
            {
                print("0230:#:".$row[0].":#:".$codeP.":#:".$row[6]."\n");
            }
        }
	$dbConnexion->do("DROP TABLE ".$emailsTempTableName);
    }
    else
    {
        $error = 1;
        print(STDERR "ERROR: cannot execute request: ".$selectRequest->errstr."\n");
    }
    $selectRequest->finish();
}
elsif ($replicationNumber == 240)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0240:#:'||a.IKEY_TEMP||':#:0120:#:'||TRIM(f.IKEY_MEMBRE)||".
        "':#:'||TRIM(a.SSTATUT_MEDIUM)||':#:'||TRIM(a.SCODE_MEDIUM)||':#:'||TRIM(a.SRAISON_SOCIALE)||".
        "':#:'||TRIM(a.SCOMPLEMENT_ADRESSE)||':#:'||TRIM(a.SLOCALITE)||':#:'||TRIM(a.SNO_ET_RUE)||':#:'||TRIM(a.SVILLE)||".
        "':#:'||TRIM(a.SCODE_POSTAL)||':#:'||TRIM(a.SCODE_PAYS)||':#:'||TRIM(a.SCODE_PROVINCE)||".
        "':#:'||TO_CHAR(a.DDATE_CREATION, 'DDMMYYYY')||".
        "':#:'||TO_CHAR(NVL(a.DDATE_MODIFICATION, a.DDATE_CREATION), 'DDMMYYYY')||':#:'||TRIM(a.SAIN)||".
	"':#:'||TRIM(a.SSITE_CREATION)||':#:'||TRIM(a.SSIGNATURE_CREATION)||".
    	"':#:'||TRIM(a.SSITE_MODIFICATION)||':#:'||TRIM(a.SSIGNATURE_MODIFICATION) ".
        "FROM FONCTION f, ADR_POST a ".
        "WHERE (f.ICLE = a.ICLE_FONCTION)".getTimeWindowConditionString("a", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 241)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "REPLACE(REPLACE(REPLACE(REPLACE('0241:#:'||TRIM(a.SAIN)||':#:0190:#:'||TRIM(ICLE_FONCTION)||':#:'||TRIM(SSTATUT_MEDIUM)||".
		"':#:'||TRIM(SCODE_MEDIUM)||':#:'||TRIM(SRAISON_SOCIALE)||':#:'||TRIM(SCOMPLEMENT_ADRESSE)||".
		"':#:'||TRIM(SLOCALITE)||':#:'||TRIM(SNO_ET_RUE)||':#:'||TRIM(SVILLE)||':#:'||TRIM(SCODE_POSTAL)||".
		"':#:'||TRIM(SCODE_PAYS)||':#:'||TRIM(SCODE_PROVINCE)||':#:'||TO_CHAR(a.DDATE_CREATION,'DDMMYYYY')||".
		"':#:'||TO_CHAR(a.DDATE_MODIFICATION,'DDMMYYYY')||':#:'||TRIM(a.SSITE_CREATION)||':#:'||TRIM(a.SSIGNATURE_CREATION)||".
		"':#:'||TRIM(a.SSITE_MODIFICATION)||':#:'||TRIM(a.SSIGNATURE_MODIFICATION)||':#:'||TRIM(a.SFORCAGE)||".
		"':#:'||TRIM(a.ICOD_ERR)||':#:'||TRIM(a.ICOD_WARNING), chr(00), '' ), chr(10), '' ), chr(13), '' ), chr(26), '' ) ||".

	"':#:'||TO_CHAR(a.DDATE_FONCTIONNEL, 'DDMMYYYY')||':#:'||TRIM(a.SSITE_FONCTIONNEL)||".
	"':#:'||TRIM(a.SSIGNATURE_FONCTIONNEL)||':#:'||TRIM(a.SCOD_ERR_SIMPLE)||".
	"':#:'||TRIM(a.SCOD_ERR_DETAILLE)||':#:'||TRIM(a.STYPE_INVALIDITE)||':#:'||TRIM(a.SENVOI_POSTAL)".

        "FROM ADR_POST a, FONCTION f, MEMBRE m ".
		"WHERE a.ICLE_FONCTION = f.ICLE AND f.IKEY_MEMBRE = m.IKEY".getTimeWindowConditionString("m", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 242)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0242:#:'||TRIM(fa.SAIN_ADR)||':#:0241:#:'||TRIM(a.SAIN)||".
        "':#:'||fa.SFORMATED_LINE_1||':#:'||fa.SFORMATED_LINE_2||':#:'||fa.SFORMATED_LINE_3||".
        "':#:'||fa.SFORMATED_LINE_4||':#:'||fa.SFORMATED_LINE_5||':#:'||fa.SFORMATED_LINE_6||".
        "':#:'||fa.SFORMATED_LINE_7||".
        "':#:'||fa.SFORMATED_LINE_8||".
        "':#:'||fa.SFORMATED_LINE_9 ".
        "FROM ADR_POST a, FORMALIZED_ADR fa, FONCTION f, MEMBRE m ".
        "WHERE a.SAIN = fa.SAIN_ADR AND a.ICLE_FONCTION = f.ICLE AND f.IKEY_MEMBRE = m.IKEY".getTimeWindowConditionString("m", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 250)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0250:#:'||t.IKEY_TEMP||':#:0120:#:'||TRIM(f.IKEY_MEMBRE)||".
        "':#:'||t.SSTATUT_MEDIUM||':#:'||t.SCODE_MEDIUM||':#:'||t.STERMINAL||".
        "':#:'||TRIM(t.SINDICATIF)||':#:'||TRIM(t.SCODE_REGION)||':#:'||TRIM(t.SNUMERO)||".
        "':#:'||TRIM(t.SDESCRIPTIF_COMPLEMENTAIRE)||".
        "':#:'||TO_CHAR(t.DDATE_CREATION, 'DDMMYYYY')||".
        "':#:'||TO_CHAR(t.DDATE_MODIFICATION, 'DDMMYYYY')||".
        "':#:'||TRIM(t.SAIN)||':#:'||".
        "TRIM(t.SSITE_CREATION)||':#:'||TRIM(t.SSIGNATURE_CREATION)||".
        "':#:'||TRIM(t.SSITE_MODIFICATION)||':#:'||TRIM(t.SSIGNATURE_MODIFICATION)|| ".
	"':#:'||t.SNORM_NAT_PHONE_NUMBER||".
        "':#:'||t.SNORM_INTER_COUNTRY_CODE||':#:'||t.SNORM_INTER_PHONE_NUMBER||".
        "':#:'||t.SNORM_TERMINAL_TYPE_DETAIL||':#:'||t.ISNORMALIZED||':#:'||t.SNORM_NAT_PHONE_NUMBER_CLEAN||".
        "':#:'||TO_CHAR(t.DDATE_INVALIDATION, 'DDMMYYYY') ".
        "FROM FONCTION f, TELECOMS t ".
        "WHERE f.ICLE = t.ICLE_FONCTION".getTimeWindowConditionString("t", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 251)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "REPLACE(REPLACE(REPLACE(REPLACE('0251:#:'||TRIM(t.SAIN)||':#:0190:#:'||TRIM(ICLE_FONCTION)||':#:'||TRIM(SSTATUT_MEDIUM)||".
		"':#:'||TRIM(SCODE_MEDIUM)||':#:'||TRIM(STERMINAL)||':#:'||TRIM(SINDICATIF)||".
		"':#:'||TRIM(SCODE_REGION)||':#:'||TRIM(SNUMERO)||':#:'||TRIM(SDESCRIPTIF_COMPLEMENTAIRE)||':#:'||TO_CHAR(t.DDATE_CREATION,'DDMMYYYY')||".
        "':#:'||TO_CHAR(t.DDATE_MODIFICATION,'DDMMYYYY')||':#:'||".
        "TRIM(t.SSITE_CREATION)||':#:'||TRIM(t.SSIGNATURE_CREATION)||".
        "':#:'||TRIM(t.SSITE_MODIFICATION)||':#:'||TRIM(t.SSIGNATURE_MODIFICATION)||".
        "':#:'||t.SNORM_NAT_PHONE_NUMBER||".
        "':#:'||t.SNORM_INTER_COUNTRY_CODE||':#:'||t.SNORM_INTER_PHONE_NUMBER||".
        "':#:'||t.SNORM_TERMINAL_TYPE_DETAIL||':#:'||t.ISNORMALIZED||':#:'||t.SNORM_NAT_PHONE_NUMBER_CLEAN||".
        "':#:'||TO_CHAR(t.DDATE_INVALIDATION,'DDMMYYYY') ".
	", chr(00), '' ), chr(10), '' ), chr(13), '' ), chr(26), '' ) ".
        "FROM TELECOMS t, FONCTION f, MEMBRE m ".
		"WHERE t.ICLE_FONCTION = f.ICLE AND f.IKEY_MEMBRE = m.IKEY".getTimeWindowConditionString("m", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 260)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0260:#:'||TRIM(e.IKEY_TEMP)||':#:0120:#:'||TRIM(f.IKEY_MEMBRE)||':#:'||e.SSTATUT_MEDIUM||':#:'||e.SCODE_MEDIUM||".
        "':#:'||TRIM(e.SEMAIL)||':#:'||e.SAUTORISATION_MAILING||':#:'||TRIM(e.SDESCRIPTIF_COMPLEMENTAIRE)||".
        "':#:'||TO_CHAR(e.DDATE_CREATION, 'DDMMYYYY')||':#:'||TO_CHAR(e.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||TRIM(e.SAIN)||".
        "':#:'||TRIM(e.SSITE_CREATION)||':#:'||TRIM(e.SSIGNATURE_CREATION)||".
        "':#:'||TRIM(e.SSITE_MODIFICATION)||':#:'||TRIM(e.SSIGNATURE_MODIFICATION)".
        "FROM FONCTION f, EMAILS e ".
        "WHERE (f.ICLE = e.ICLE_FONCTION)".getTimeWindowConditionString("e", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 261)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "REPLACE(REPLACE(REPLACE(REPLACE('0261:#:'||TRIM(e.SAIN)||':#:0190:#:'||TRIM(ICLE_FONCTION)||':#:'||TRIM(SSTATUT_MEDIUM)||".
        "':#:'||TRIM(SCODE_MEDIUM)||':#:'||TRIM(SEMAIL)||':#:'||TRIM(SAUTORISATION_MAILING)||".
        "':#:'||TRIM(SDESCRIPTIF_COMPLEMENTAIRE)||".
        "':#:'||TO_CHAR(e.DDATE_CREATION, 'DDMMYYYY')||':#:'||TO_CHAR(e.DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||".
        "TRIM(e.SSITE_CREATION)||':#:'||TRIM(e.SSIGNATURE_CREATION)||".
        "':#:'||TRIM(e.SSITE_MODIFICATION)||':#:'||TRIM(e.SSIGNATURE_MODIFICATION)".
        ", chr(00), '' ), chr(10), '' ), chr(13), '' ), chr(26), '' ) ".
        "FROM EMAILS e, FONCTION f, MEMBRE m ".
        "WHERE e.ICLE_FONCTION = f.ICLE AND f.IKEY_MEMBRE = m.IKEY".getTimeWindowConditionString("m", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 270)
{

	my $request = "SELECT ".
	"'0270:#:'||ext.IDENTIFIER_ID||".
		"':#:'||ext.SGIN||".
		"':#:'||ext.IDENTIFIER||".
		"':#:'||ext.TYPE||".
		"':#:'||TO_CHAR(ext.LAST_SEEN_DATE, 'DDMMYYYY')||".
		"':#:'||TO_CHAR(ext.CREATION_DATE, 'DDMMYYYY')||".
		"':#:'||ext.CREATION_SIGNATURE||".
		"':#:'||ext.CREATION_SITE||".
		"':#:'||TO_CHAR(ext.MODIFICATION_DATE, 'DDMMYYYY')||".
		"':#:'||ext.MODIFICATION_SIGNATURE||".
		"':#:'||ext.MODIFICATION_SITE".
		" FROM EXTERNAL_IDENTIFIER ext";
		
    if (defined($startDate)) {
        $request .= " WHERE (ext.MODIFICATION_DATE >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
    }
    if (defined($endDate)) {
        $request .= " ".(defined($startDate)?"AND":"WHERE")." (ext.MODIFICATION_DATE < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
    }
	
    simpleSqlSelect(\$dbConnexion, \$error, $request);

}
elsif ($replicationNumber == 280)
{

	my $request = "SELECT REPLACE(REPLACE(".
	"'0280:#:'||ext.IDENTIFIER_DATA_ID||".
		"':#:'||ext.IDENTIFIER_ID||".
		"':#:'||ext.KEY||".
		"':#:'||ext.VALUE||".
		"':#:'||TO_CHAR(ext.CREATION_DATE, 'DDMMYYYY')||".
		"':#:'||ext.CREATION_SIGNATURE||".
		"':#:'||ext.CREATION_SITE||".
		"':#:'||TO_CHAR(ext.MODIFICATION_DATE, 'DDMMYYYY')||".
		"':#:'||ext.MODIFICATION_SIGNATURE||".
		"':#:'||ext.MODIFICATION_SITE, chr(10), ' '), chr(13), ' ')".
		" FROM EXTERNAL_IDENTIFIER_DATA ext";
		
    if (defined($startDate)) {
        $request .= " WHERE (ext.MODIFICATION_DATE >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
    }
    if (defined($endDate)) {
        $request .= " ".(defined($startDate)?"AND":"WHERE")." (ext.MODIFICATION_DATE < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
    }
	
    simpleSqlSelect(\$dbConnexion, \$error, $request);

}
elsif ($replicationNumber == 290)
{

	my $request = "SELECT ".
	"'0290:#:'||al.ALERT_ID||".
		"':#:'||al.SGIN||".
		"':#:'||al.TYPE||".
		"':#:'||al.OPTIN||".
		"':#:'||TO_CHAR(al.CREATION_DATE, 'DDMMYYYY')||".
		"':#:'||al.CREATION_SIGNATURE||".
		"':#:'||al.CREATION_SITE||".
		"':#:'||TO_CHAR(al.MODIFICATION_DATE, 'DDMMYYYY')||".
		"':#:'||al.MODIFICATION_SIGNATURE||".
		"':#:'||al.MODIFICATION_SITE".
		" FROM ALERT al WHERE al.sgin is not null ";
		
    if (defined($startDate)) {
        $request .= " AND (al.MODIFICATION_DATE >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
    }
    if (defined($endDate)) {
        $request .= " AND (al.MODIFICATION_DATE < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
    }
	
    simpleSqlSelect(\$dbConnexion, \$error, $request);

}
elsif ($replicationNumber == 300)
{

	my $request = "SELECT ".
	"'0300:#:'||ald.ALERT_DATA_ID||".
		"':#:'||ald.ALERT_ID||".
		"':#:'||ald.KEY||".
		"':#:'||ald.VALUE||".
		"':#:'||TO_CHAR(al.CREATION_DATE, 'DDMMYYYY')||".
		"':#:'||al.CREATION_SIGNATURE||".
		"':#:'||al.CREATION_SITE||".
		"':#:'||TO_CHAR(al.MODIFICATION_DATE, 'DDMMYYYY')||".
		"':#:'||al.MODIFICATION_SIGNATURE||".
		"':#:'||al.MODIFICATION_SITE".
		" FROM ALERT_DATA ald INNER JOIN ALERT al ON ald.ALERT_ID = al.ALERT_ID INNER JOIN INDIVIDUS_ALL i on al.SGIN = i.SGIN ";
		
    if (defined($startDate)) {
        $request .= " AND (al.MODIFICATION_DATE >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
    }
    if (defined($endDate)) {
        $request .= " AND (al.MODIFICATION_DATE < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
    }
	
    simpleSqlSelect(\$dbConnexion, \$error, $request);

}
elsif ($replicationNumber == 310)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0310:#:'||TRIM(TO_NUMBER(TRIM(r.ICLE_ROLE)))||':#:'||".
        "DECODE(pm.STYPE, 'A', DECODE(g.SAGENCE_RA2, 'N', '0160', '0170'), 'T', '0150', 'E', '0140', 'G', '0130', 'S', '9999', '')||':#:'||".
        "TRIM(b.SGIN_PM)||':#:'||r.SETAT||".
        "':#:'||r.STYPE||':#:'||r.SSOUS_TYPE||':#:'||r.SNUMERO||".
        "':#:'||TO_CHAR(r.DDEBUT_VALIDITE, 'DDMMYYYY')||':#:'||TO_CHAR(r.DFIN_VALIDITE, 'DDMMYYYY')||".
        "':#:'||r.SMC||':#:'||r.SREF_PRINCIPALE||':#:'||r.SREF_ASSOCIEE||".
        "':#:'||r.SMOT_DIRECTEUR||':#:'||r.SOPTIN||':#:'||r.SNUM_ENCOMPTE||':#:'||r.SPAYS||':#:'||r.CTIER ".
        "FROM ROLE_FIRME r, BUSINESS_ROLE b, PERS_MORALE pm, AGENCE g ".
        "WHERE r.ICLE_ROLE = b.ICLE_ROLE AND b.SGIN_PM = pm.SGIN AND pm.SGIN = g.SGIN(+)".getTimeWindowConditionString("r", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 320)
{
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "REPLACE(REPLACE('0320:#:'||TRIM(r.ICLE_ROLE)||':#:0110:#:'||r.SGIN||':#:'||r.SETAT||':#:'||r.STYPE_CONTRAT||".
        "':#:'||r.SSOUS_TYPE||':#:'||r.SNUMERO_CONTRAT||':#:'||TO_CHAR(r.DDEBUT_VALIDITE, 'DDMMYYYY')||".
        "':#:'||TO_CHAR(r.DFIN_VALIDITE, 'DDMMYYYY')||':#:'||r.STIER||':#:'||r.SCODE_COMPAGNIE||':#:'||".
        "r.SFAMILLE_PRODUIT||':#:'||r.SAGENCE_IATA||':#:'||NVL(r.IMILES_QUALIF,0)||':#:'||NVL(r.IMILES_QUALIF_PREC,0)||':#:'||".
        "r.SPERMISSION_PRIME||':#:'||NVL(r.ISEGMENTS_QUALIF,0)||':#:'||NVL(r.ISEGMENTS_QUALIF_PREC,0)||':#:'||".
        "NVL(r.ISOLDE_MILES,0)||':#:'||r.SSOURCE_ADHESION, chr(10), '' ), chr(13), '' ) ".
        "FROM ROLE_CONTRATS r".getTimeWindowConditionString("r", $startDate, $endDate, 1));
}
elsif ($replicationNumber == 330)
{
   # simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
   #     "'0330:#:'||TO_NUMBER(TRIM(b.ICLE_ROLE))||':#:'||DECODE(b.SGIN_IND, null, '0150', '0110')||':#:'||NVL(b.SGIN_IND,b.SGIN_PM)||':#:'||".
   #     "':#:'||r.SNUMERO_AFFAIRE||':#:'||r.STYPE||':#:'||r.SSOUS_TYPE||':#:'||".
   #     "TO_CHAR(r.DDATE_OUVERTURE, 'DDMMYYYY')||':#:'||TO_CHAR(r.DDATE_FERMETURE, 'DDMMYYYY')||':#:'||r.SCAUSE||':#:'||".
   #     "r.SFAMILLE_TRAITEMENT ".
   #     "FROM ROLE_RCS r,BUSINESS_ROLE b ".
   #     "WHERE r.ICLE_ROLE = b.ICLE_ROLE".getTimeWindowConditionString("r", $startDate, $endDate, 0));

   simpleSqlSelect(\$dbConnexion, \$error, "SELECT 1 from BUSINESS_ROLE b where 1=2");
}
elsif ($replicationNumber == 340)
{
    #simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
    #    "'0340:#:'||TRIM(r.ICLE_ROLE)||':#:0110:#:'||TRIM(l.SGIN)||':#:'||TRIM(l.SMATRICULE)||':#:'||".
    #   "TRIM(l.SRANG)||':#:'||TRIM(r.SVERSION)||':#:'||TRIM(r.SETAT)||':#:'||TRIM(l.SREFERENCE_R)||':#:'||".
    #  "TRIM(l.STYPOLOGIE)||':#:'||TRIM(l.SCODE_ORIGINE)||':#:'||".
    # "TO_CHAR(r.DDEBUT_VALIDITE, 'DDMMYYYY')||':#:'||TO_CHAR(r.DFIN_VALIDITE, 'DDMMYYYY') ".
    #    "FROM LIEN_IND_RI l, BUSINESS_ROLE b, ROLE_GP r, INDIVIDUS i ".
    #    "WHERE l.SGIN = b.SGIN_IND AND i.SGIN = l.SGIN AND r.ICLE_ROLE = b.ICLE_ROLE and r.smatricule = ltrim(l.smatricule, '0')".getTimeWindowConditionString("i", $startDate, $endDate, 0).
    #   " UNION SELECT ".
    #  "'0340:#:'||TRIM(r.ICLE_ROLE)||':#:0110:#:'||TRIM(i.SGIN)||':#:'||TRIM(pa.SMATRICULE)||':#:'||".
    # "TRIM(pa.SRANG)||':#:'||TRIM(r.SVERSION)||':#:'||TRIM(r.SETAT)||':#:'||TRIM(pa.SREFERENCE_R)||':#:'||".
    #    "TRIM(pa.STYPOLOGIE)||':#:'||TRIM(pa.SCODE_ORIGINE)||':#:'||".
    #    "TO_CHAR(r.DDEBUT_VALIDITE, 'DDMMYYYY')||':#:'||TO_CHAR(r.DFIN_VALIDITE, 'DDMMYYYY') ".
    #    "FROM INDIVIDUS i, BUSINESS_ROLE b, PROFIL_MERE pm, PROFIL_AF pa, ROLE_GP r ".
    #    "WHERE  i.SGIN = b.SGIN_IND AND b.SGIN_IND=pm.SGIN_IND and pm.ICLE_PRF=pa.icle_prf and b.ICLE_ROLE = r.ICLE_ROLE and r.smatricule = ltrim(pa.smatricule, '0') ".getTimeWindowConditionString("i", $startDate, $endDate, 0));

    simpleSqlSelect(\$dbConnexion, \$error, "SELECT 1 from BUSINESS_ROLE b where 1=2");
}
elsif ($replicationNumber == 350)
{
	# Gestion des contrats SEQUOIA
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0350:#:'||TRIM(TO_NUMBER(TRIM(r.ICLE_ROLE)))||':#:'||".
        "DECODE(pm.STYPE, 'A', DECODE(g.SAGENCE_RA2, 'N', '0160', '0170'), 'T', '0150', 'E', '0140', 'G', '0130', 'S', '9999', '')||':#:'||".
        "TRIM(b.SGIN_PM)||':#:'||r.SETAT||".
        "':#:'||r.STYPE||':#:'||r.SSOUS_TYPE||':#:'||r.SNUMERO||".
        "':#:'||TO_CHAR(r.DDEBUT_VALIDITE, 'DDMMYYYY')||':#:'||TO_CHAR(r.DFIN_VALIDITE, 'DDMMYYYY')||".
        "':#:'||r.SNOM_CONTRAT||':#:'||r.SSTATUS_CRT_SEQ||':#:'||ref2.SLIBELLE||':#:'||ref2.SLIBELLE_EN||':#:'||r.SCONTRACTING||':#:'||r.SEXCLUSION||".
        "':#:'||t.SCODE_TYPE||':#:'||ref1.SLIBELLE||':#:'||ref1.SLIBELLE_EN||':#:'||t.SNUMERO_TC||':#:'||t.SNOM_TC||':#:'||t.DDATE_DEBUT||':#:'||t.DDATE_FIN  ".
        "FROM ROLE_AGENCE r, BUSINESS_ROLE b, TYPE_CONTRAT_TC_SEQ t, REF_TYP_TC_SEQ ref1, REF_STA_CONT_SEQ ref2, PERS_MORALE pm, AGENCE g ".
        "WHERE r.ICLE_ROLE = b.ICLE_ROLE AND r.SNUMERO_TC=t.SNUMERO_TC AND t.SCODE_TYPE=ref1.SCODE AND r.SSTATUS_CRT_SEQ=ref2.SCODE AND b.SGIN_PM = pm.SGIN AND pm.SGIN = g.SGIN(+)".getTimeWindowConditionString("r", $startDate, $endDate, 0));
}
elsif ($replicationNumber == 360)
{
    # Build a query with all fields to send to generic replication
    my $query = "SELECT '0360:#:'||t.ICLE_ROLE||
                         ':#:'||t.SGIN||
                         ':#:'||TO_CHAR(t.DLAST_RECOGNITION_DATE,'DDMMYYYY')||
                         ':#:'||t.SMATCHING_RECOGNITION_CODE||
                         ':#:'||TO_CHAR(t.DDATE_CREATION, 'DDMMYYYY')||
                         ':#:'||t.SSIGNATURE_CREATION||
                         ':#:'||t.SSITE_CREATION||
                         ':#:'||TO_CHAR(t.DDATE_MODIFICATION, 'DDMMYYYY')||
                         ':#:'||t.SSIGNATURE_MODIFICATION||
                         ':#:'||t.SSITE_MODIFICATION FROM ROLE_TRAVELERS t";

    # Replication is on date
    if (defined($startDate)) {
        $query .= " WHERE (t.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
    }
    if (defined($endDate)) {
        $query .= " ".(defined($startDate)?"AND":"WHERE")." (t.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
    }

    # query execution
    simpleSqlSelect(\$dbConnexion, \$error, $query);

}
elsif ($replicationNumber == 400)
{
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("'0130'", "'0410'", ", GROUPE g, ZONE_VENTE zv WHERE g.SGIN = p.SGIN AND zv.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("'0130'", "'0420'", ", GROUPE g, ZONE_COMM zc WHERE g.SGIN = p.SGIN AND zc.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("'0130'", "'0430'", ", GROUPE g, ZONE_FINANC zf WHERE g.SGIN = p.SGIN AND zf.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("'0140'", "'0410'", ", ENTREPRISE e, ZONE_VENTE zv WHERE e.SGIN = p.SGIN AND zv.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("'0140'", "'0420'", ", ENTREPRISE e, ZONE_COMM zc WHERE e.SGIN = p.SGIN AND zc.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("'0140'", "'0430'", ", ENTREPRISE e, ZONE_FINANC zf WHERE e.SGIN = p.SGIN AND zf.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("'0150'", "'0410'", ", ETABLISSEMENT e, ZONE_VENTE zv WHERE e.SGIN = p.SGIN AND zv.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("'0150'", "'0420'", ", ETABLISSEMENT e, ZONE_COMM zc WHERE e.SGIN = p.SGIN AND zc.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("'0150'", "'0430'", ", ETABLISSEMENT e, ZONE_FINANC zf WHERE e.SGIN = p.SGIN AND zf.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("DECODE(g.SAGENCE_RA2, 'N', '0160', '0170')", "'0410'", ", AGENCE g, ZONE_VENTE zv WHERE g.SGIN = p.SGIN AND zv.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("DECODE(g.SAGENCE_RA2, 'N', '0160', '0170')", "'0420'", ", AGENCE g, ZONE_COMM zc WHERE g.SGIN = p.SGIN AND zc.IGIN = p.IGIN_ZONE", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get400Request("DECODE(g.SAGENCE_RA2, 'N', '0160', '0170')", "'0430'", ", AGENCE g, ZONE_FINANC zf WHERE g.SGIN = p.SGIN AND zf.IGIN = p.IGIN_ZONE", $startDate, $endDate));
}
elsif ($replicationNumber == 410)
{
    my $request = "SELECT ".
        "'0410:#:'||TRIM(zv.IGIN)||':#::#::#:'||zd.SSOUS_TYPE||':#:'||zd.SNATURE||':#:'||zd.SSTATUT||':#:'||".
        "TO_CHAR(zd.DDATE_OUVERTURE, 'DDMMYYYY')||':#:'||TO_CHAR(zd.DDATE_FERMETURE, 'DDMMYYYY')||':#:'||".
        "LPAD(zv.ZV0,2,'0')||':#:'||LPAD(zv.ZV1,2,'0')||':#:'||LPAD(zv.ZV2,2,'0')||':#:'||LPAD(zv.ZV3,2,'0')||':#:'||".
        "zv.ZVALPHA||':#:'||zv.SLIB_ZVALPHA||':#:'||zv.SCODE_MONNAIE||':#:', zv.ZV0, zv.ZV1, zv.ZV2, zv.ZV3, ".
        "TO_CHAR(zd.DDATE_OUVERTURE, 'YYYYMMDDHH24MISS'), TO_CHAR(zd.DDATE_FERMETURE, 'YYYYMMDDHH24MISS') ".
        "FROM ZONE_VENTE zv, ZONE_DECOUP zd ".
        "WHERE zv.IGIN = zd.IGIN";
    if (defined($startDate))
    {
        $request .= " AND zd.DDATE_MAJ >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')";
    }
    if (defined($endDate))
    {
        $request .= " AND zd.DDATE_MAJ < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')";
    }
    $request .= " ORDER BY zv.IGIN";
    my $selectRequest = $dbConnexion->prepare($request);
    if ($selectRequest->execute())
    {
        while (my @row = $selectRequest->fetchrow_array())
        {
            my $zv0 = $row[1];
            my $zv1 = $row[2];
            my $zv2 = $row[3];
            my $zv3 = $row[4];
            my $dateOuverture = $row[5];
            my $dateFermeture = $row[6];

            my $subSelectRequest = $dbConnexion->prepare("SELECT ".
                "zv.ZV0, zv.ZV1, zv.ZV2, zv.ZV3, zv.SCODE_MONNAIE, zv.ZVALPHA, TO_CHAR(zd.DDATE_OUVERTURE, 'YYYYMMDDHH24MISS'), ".
                "TO_CHAR(zd.DDATE_FERMETURE, 'YYYYMMDDHH24MISS') ".
                "FROM ZONE_VENTE zv, ZONE_DECOUP zd ".
                "WHERE ((zv.IGIN = zd.IGIN) AND (zv.ZV3='$zv3' OR zv.ZV3 IS NULL) AND (zv.ZV2='$zv2' OR zv.ZV2 IS NULL) AND ".
                "(zv.ZV1='$zv1' OR zv.ZV1 IS NULL) AND zv.ZV0='$zv0') ".
                "ORDER BY ZV0, ZV1, ZV2, ZV3");
            if ($subSelectRequest->execute())
            {
                my $zvAlpha0 = "";
                my $zvAlpha1 = "";
                my $zvAlpha2 = "";
                my $zvAlpha3 = "";
                my $monnaie0 = "";
                my $monnaie1 = "";
                my $monnaie2 = "";
                my $monnaie3 = "";
                while (my @subRow = $subSelectRequest->fetchrow_array())
                {
                    my $subZv0 = $subRow[0];
                    my $subZv1 = $subRow[1];
                    my $subZv2 = $subRow[2];
                    my $subZv3 = $subRow[3];
                    my $subCodeMannaie = $subRow[4];
                    my $subZvAlpha = $subRow[5];
                    my $subDateOuverture = $subRow[6];
                    my $subDateFermeture = $subRow[7];

                    if (($subDateOuverture <= $dateOuverture) &&
                        ((!defined($dateOuverture)) || (!defined($subDateFermeture)) || ($dateOuverture <= $subDateFermeture)) &&
                        ((!defined($dateFermeture)) || (!defined($subDateFermeture)) || ($dateFermeture <= $subDateFermeture)))
                    {
                        if (defined($subZv3)) # ZV3
                        {
                            $zvAlpha3 = $subZvAlpha;
                            $zvAlpha3 =~ s/^.{3}//; # remove the first 3 characters
                            while (length($zvAlpha3) != 5)
                            {
                                $zvAlpha3 .= " ";
                            }
                            $monnaie3 = $subCodeMannaie;
                        }
                        if (defined($subZv2)) # ZV2
                        {
                            $zvAlpha2 = $subZvAlpha;
                            $zvAlpha2 =~ s/^.{3}//; # remove the first 3 characters
                            while (length($zvAlpha2) != 5)
                            {
                                $zvAlpha2 .= " ";
                            }
                            $monnaie2 = $subCodeMannaie;
                        }
                        if (defined($subZv1)) # ZV1
                        {
                            $zvAlpha1 = $subZvAlpha;
                            $zvAlpha1 =~ s/^(.{3}).*$/$1/; # keep only the first 3 characters
                            while (length($zvAlpha1) != 3)
                            {
                                $zvAlpha1 .= " ";
                            }
                            $monnaie1 = $subCodeMannaie;
                        }
                        if (defined($subZv0)) # ZV0
                        {
                            $zvAlpha0 = $subZvAlpha;
                            $zvAlpha0 =~ s/^(.{3}).*$/$1/; # keep only the first 3 characters
                            while (length($zvAlpha0) != 3)
                            {
                                $zvAlpha0 .= " ";
                            }
                            $monnaie0 = $subCodeMannaie;
                        }
                    }
                    elsif (($subDateOuverture > $dateOuverture) || ($dateOuverture > $subDateFermeture) ||
                        ($dateFermeture > $subDateFermeture))
                    {
                        if (defined($subZv3) && ($zvAlpha3 eq ""))
                        {
                            $zvAlpha3 = "     ";
                            $monnaie3 = "   ";
                        }
                        elsif (defined($subZv2) && ($zvAlpha2 eq ""))
                        {
                            $zvAlpha2 = "     ";
                            $monnaie2 = "   ";
                        }
                        elsif (defined($subZv1) && ($zvAlpha1 eq ""))
                        {
                            $zvAlpha1 = "   ";
                            $monnaie1 = "   ";
                        }
                        elsif (defined($subZv0) && ($zvAlpha0 eq ""))
                        {
                            $zvAlpha0 = "   ";
                            $monnaie0 = "   ";
                        }
                    }
                }
                my $zvAlpha = $zvAlpha0.$zvAlpha1.$zvAlpha2.$zvAlpha3;
                $zvAlpha =~ s/^\s*(.*[^\s])\s*$/$1/;
                my $monnaie = $monnaie0.$monnaie1.$monnaie2.$monnaie3;
                $monnaie =~ s/^\s*(.*[^\s])\s*$/$1/;
                print($row[0]."$zvAlpha:#:$monnaie:#:\n");
            }
            else
            {
                $error = 1;
                print(STDERR "ERROR: cannot execute request: ".$subSelectRequest->errstr."\n");
            }
        }
    }
    else
    {
        $error = 1;
        print(STDERR "ERROR: cannot execute request: ".$selectRequest->errstr."\n");
    }
    $selectRequest->finish();
}
elsif ($replicationNumber == 420)
{
    my $request = "SELECT ".
        "'0420:#:'||TRIM(zc.IGIN)||':#::#::#:'||zd.SSOUS_TYPE||':#:'||zd.SNATURE||':#:'||zd.SSTATUT||':#:'||".
        "TO_CHAR(zd.DDATE_OUVERTURE, 'DDMMYYYY')||':#:'||TO_CHAR(zd.DDATE_FERMETURE, 'DDMMYYYY')||':#:'||".
        "zc.SZC1||':#:'||zc.SZC2||':#:'||zc.SZC3||':#:'||zc.SZC4||':#:'||zc.SZC5||':#:' ".
        "FROM ZONE_COMM zc, ZONE_DECOUP zd ".
        "WHERE zc.IGIN = zd.IGIN";
    if (defined($startDate))
    {
        $request .= " AND zd.DDATE_MAJ >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')";
    }
    if (defined($endDate))
    {
        $request .= " AND zd.DDATE_MAJ < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')";
    }
    $request .= " ORDER BY zc.IGIN";
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 430)
{
    my $request = "SELECT ".
        "'0430:#:'||TRIM(zf.IGIN)||':#::#::#:'||zd.SSOUS_TYPE||':#:'||zd.SNATURE||':#:'||zd.SSTATUT||':#:'||".
        "TO_CHAR(zd.DDATE_OUVERTURE, 'DDMMYYYY')||':#:'||TO_CHAR(zd.DDATE_FERMETURE, 'DDMMYYYY')||':#:'||".
        "zf.SZONE_GEO||':#:'||zf.SCODE_UF||':#:'||zf.SCODE_VILLE||':#:'||zf.SPAYS||':#:' ".
        "FROM ZONE_FINANC zf, ZONE_DECOUP zd ".
        "WHERE zf.IGIN = zd.IGIN";
    if (defined($startDate))
    {
        $request .= " AND zd.DDATE_MAJ >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')";
    }
    if (defined($endDate))
    {
        $request .= " AND zd.DDATE_MAJ < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')";
    }
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 440)
{
    my $request = "SELECT ".
        "'0440:#:'||z.ICLE_IZD||':#:'||TRIM(z.SGIN)||':#:'||TRIM(z.IGIN_ZONE)||':#:'||".
        "TO_CHAR(z.DDATE_OUVERTURE, 'DDMMYYYY')||':#:'||TO_CHAR(z.DDATE_FERMETURE, 'DDMMYYYY')||':#:'||".
        "z.SCODE_ROLE||':#:'||z.SCODE_FONCTION||':#:'||z.SLIEN_PRIVILEGIE||':#:'||z.STYPE_LIEN_IND_ZC||':#:' ".
        "FROM IND_ZONE z";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", INDIVIDUS i WHERE z.SGIN = i.SGIN".getTimeWindowConditionString("i", $startDate, $endDate, 0);
    }
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 480)
{
    my $request = "SELECT ".
        "'0480:#:'||TO_NUMBER(TRIM(m.ICLE_MBR))||':#:'||DECODE(a.SAGENCE_RA2, 'N', '0160', '0170')||':#:'||m.SGIN||':#:'||m.SCODE||':#:'||".
        "TO_CHAR(m.DDATE_DEBUT, 'DDMMYYYY')||':#:'||TO_CHAR(m.DDATE_FIN, 'DDMMYYYY')||':#:'".
        "FROM MEMBRE_RESEAU m";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", AGENCE a, PERS_MORALE pm WHERE m.SGIN = a.SGIN AND a.SGIN = pm.SGIN".getTimeWindowConditionString("pm", $startDate, $endDate, 0);
    }
    else 
    {
	$request .= ", AGENCE a WHERE m.SGIN = a.SGIN";
    }
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 490)
{
    # no time components
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT ".
        "'0490:#:'||TRIM(r.SCODE)||':#:'||DECODE(r.SCODE_PERE, '', '', '0490')||':#:'||r.SCODE_PERE||':#:'||".
        "r.STYPE||':#:'||r.SNATURE||':#:'||TRIM(r.SNOM)||':#:'||r.SPAYS||':#:'||".
        "TO_CHAR(r.DDATE_CREATION, 'DDMMYYYY')||':#:'||TO_CHAR(r.DDATE_FERMETURE, 'DDMMYYYY')||':#:' ".
        "FROM RESEAU r");
}
elsif ($replicationNumber == 820)
{
    my $request = "SELECT ".
        "'0820:#:'||TRIM(p.ICLE_PRF)||':#:0310:#:'||TRIM(NVL(NVL(pm.SGIN_IND, pm.SGIN_PM), pm.ICLE_ROLE))||':#:'||p.SCODE_REGLEMENT||':#:'||".
        "p.SLIGNE_CREDIT||':#:'||NVL(p.IMONTANT_CREDIT,0)||':#:'||NVL(p.IDELAI_REGLEMENT,0)||':#:'||".
        "TO_CHAR(p.DDEB_FACT_EURO, 'DDMMYYYY')||':#:'||TO_CHAR(p.DFIN_FACT_EURO, 'DDMMYYYY')||':#:'||NVL(p.INBR_BONS_BILLETS,0)||':#:'||".
        "NVL(p.INBR_EX_FACT_PAPIER,0)||':#:'||NVL(p.INBR_RELEVE_FACT,0)||':#:'||NVL(p.INBR_RELEVE_MENSUEL,0)||':#:'||".
        "NVL(p.ITAUX_COMMISSION_RISTOURNE,0)||':#:'||p.SCOD_MONNAIE||':#:'||p.SCOMMISSION_RISTOURNE||':#:'||".
        "p.SFIL_DE_L_EAU||':#:'||p.SJUSTIFICATIF||':#:'||p.SLANGUE_FACTURE||':#:'||".
        "p.SPASSAGE1||':#:'||p.SPASSAGE2||':#:'||p.SPASSAGE3||':#:'||p.SPASSAGE4||':#:'||p.SPASSAGE5||':#:'||p.SPASSAGE6||':#:'||".
        "p.SRUP_NUM_BON||':#:'||p.SRUP_DATE_OPERATION||':#:'||p.SRUP_NBR_BONS||':#:'||".
        "p.SRUP_NBR_BILLETS||':#:'||p.SRUP_NOM_PAX||':#:'||p.SRUP_COD_AGENT||':#:'||p.SSUIVI_DEFI2_SAP||':#:'||".
        "p.SSUPPORT_MAGNETIQUE||':#:'||p.STRI_NUM_BONS||':#:'||p.STRI_DAT_OPERATION||':#:'||p.STRI_NUM_BILLETS||':#:'||".
        "p.STRI_NOM_PAX||':#:'||p.STRI_COD_REGULATION||':#:'||p.STVA_FACTURE||':#:'||p.STYPE_ENVOI||':#:'||".
        "p.SVALIDATION_FACT||':#:'||p.SAGAF_RATACHEMENT||':#:'||p.SDOMICILIATION_UF||':#:'||".
        "p.SNUMERO_ARIES||':#:'||p.SNUM_CONTRAT_MARCHE||':#:' ".
        "FROM PROFIL_FACT p, PROFIL_MERE pm";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", ROLE_FIRME r";
    }
    $request .= " WHERE p.ICLE_PRF = pm.ICLE_PRF";
    if (defined($startDate) || defined($endDate))
    {
        $request .=  " AND pm.ICLE_ROLE = r.ICLE_ROLE".getTimeWindowConditionString("r", $startDate, $endDate, 0);
    }
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 830)
{
    my $request = "SELECT ".
        "'0830:#:'||TRIM(p.ICLE_PRF)||':#:0310:#:'||TRIM(NVL(pm.SGIN_PM, pm.ICLE_ROLE))||':#:'||p.SGROUPE||':#:'||".
        "p.SGESTIONNAIRE||':#:'||p.SBRANCHE||':#:'||".
        "p.SCATEGORIE||':#:' ".
        "FROM PROFIL_FINANC p, PROFIL_MERE pm";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", ROLE_FIRME r";
    }
    $request .= " WHERE p.ICLE_PRF = pm.ICLE_PRF";
    if (defined($startDate) || defined($endDate))
    {
        $request .=  " AND pm.ICLE_ROLE = r.ICLE_ROLE".getTimeWindowConditionString("r", $startDate, $endDate, 0);
    }
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 840)
{
    my $request = "SELECT ".
        "'0840:#:'||TRIM(pm.ICLE_PRF)||':#:'||DECODE(a.SAGENCE_RA2, 'Y', '0170', '0160')||':#:'||TRIM(pm.SGIN_PM)||':#:'||".
        "p.SDEMARCHAGE||':#:'||p.STYPE_MAILING||':#:'||p.SVITRINE||':#:'||".
        "p.SEMPLACEMENT||':#:'||p.SJOUR_OUVERTURE||':#:'||p.SHEURE_OUV||':#:'||p.SHEURE_FERM||':#:'||".
        "p.SLANGUE_PAR||':#:'||p.SLANGUE_ECR||':#:'||".
        "TO_CHAR(p.DDATE_TY, 'DDMMYYYY')||':#:'||TO_CHAR(p.DDATE_RETRAIT_TY, 'DDMMYYYY') ".
        "FROM PR_DEMARCHAGE p, PROFIL_MERE pm, AGENCE a";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", PERS_MORALE pmo";
    }
    $request .= " WHERE p.ICLE_PRF = pm.ICLE_PRF AND pm.SGIN_PM = a.SGIN";
    if (defined($startDate) || defined($endDate))
    {
        $request .=  " AND a.SGIN = pmo.SGIN".getTimeWindowConditionString("pmo", $startDate, $endDate, 0);
    }
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 860)
{
    my $request = "SELECT ".
        "'0860:#:'||TRIM(pm.ICLE_PRF)||':#:'||DECODE(a.SAGENCE_RA2, 'N', '0160', '0170')||':#:'||TRIM(NVL(pm.SGIN_PM, pm.ICLE_ROLE))||':#:'||p.SDEFAUT_PAIEMENT||':#:'||".
        "TO_CHAR(p.DDATE_DEB_DEFAUT, 'DDMMYYYY')||':#:'||TO_CHAR(p.DDATE_FIN_DEFAUT, 'DDMMYYYY')||':#:'||p.SREDRESSEMENT||':#:'||".
        "TO_CHAR(p.DDATE_DEB_RED, 'DDMMYYYY')||':#:'||TO_CHAR(p.DDATE_FIN_RED, 'DDMMYYYY')||':#:'||".
        "TO_CHAR(p.DDATE_LIQUIDATION, 'DDMMYYYY')||':#:'||p.SAGREMENT_AF||':#:'||p.SMISE_EN_CASH||':#:'||".
        "TO_CHAR(p.DDATE_REPRISE_EMISSION, 'DDMMYYYY')||':#:'||TO_CHAR(p.DDATE_SUSPEN_EMISSION, 'DDMMYYYY')||':#:' ".
        "FROM PR_CONTENTIEUX p, PROFIL_MERE pm, AGENCE a";
    if (defined($startDate) || defined($endDate))
    {
        $request .= ", PERS_MORALE pmo";
    }
    $request .= " WHERE p.ICLE_PRF = pm.ICLE_PRF AND pm.SGIN_PM = a.SGIN";
    if (defined($startDate) || defined($endDate))
    {
        $request .=  " AND a.SGIN = pmo.SGIN".getTimeWindowConditionString("pmo", $startDate, $endDate, 0);
    }
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 910)
{
    my $request = "SELECT ".
        "'0910:#:'||TO_NUMBER(TRIM(g.ICLE))||':#:'||DECODE(a.SAGENCE_RA2, 'N', '0160', '0170')||':#:'||g.SGIN_GERANTE||':#:0150:#:'||g.SGIN_GEREE||':#:'||g.STYPE_LIEN||':#:'||".
        "TO_CHAR(g.DDATE_DEB_LIEN, 'DDMMYYYY')||':#:'||TO_CHAR(g.DDATE_FIN_LIEN, 'DDMMYYYY')||':#:'||g.SPRIVILEGIE||':#:'||g.LIEN_ZC_FIRME||':#:' ".
        "FROM GESTION_PM g, AGENCE a WHERE g.SGIN_GERANTE = a.SGIN";
    if (defined($startDate))
    {
        $request .= " AND g.DDATE_MAJ >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')";
    }
    if (defined($endDate))
    {
        $request .= " AND g.DDATE_MAJ < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')";
    }
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 920)
{
    simpleSqlSelect(\$dbConnexion, \$error, get920Request("'0130'", ", GROUPE g", " AND s.SGIN = g.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get920Request("'0140'", ", ENTREPRISE e", " AND s.SGIN = e.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get920Request("'0150'", ", ETABLISSEMENT e", " AND s.SGIN = e.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get920Request("'0160'", ", AGENCE a", " AND s.SGIN = a.SGIN", $startDate, $endDate));
}
elsif ($replicationNumber == 930)
{
    simpleSqlSelect(\$dbConnexion, \$error, get930Request("'0130'", ", GROUPE g", " WHERE n.SGIN = g.SGIN", " AND g.SGIN = pm.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get930Request("'0140'", ", ENTREPRISE e", " WHERE n.SGIN = e.SGIN", " AND e.SGIN = pm.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get930Request("'0150'", ", ETABLISSEMENT e", " WHERE n.SGIN = e.SGIN", " AND e.SGIN = pm.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get930Request("DECODE(a.SAGENCE_RA2, 'N', '0160', '0170')", ", AGENCE a", " WHERE n.SGIN = a.SGIN", " AND a.SGIN = pm.SGIN", $startDate, $endDate));
}
elsif ($replicationNumber == 940)
{
    simpleSqlSelect(\$dbConnexion, \$error, get940Request("'0130'", ", GROUPE g", " WHERE c.SGIN = g.SGIN", " AND g.SGIN = pm.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get940Request("'0140'", ", ENTREPRISE e", " WHERE c.SGIN = e.SGIN", " AND e.SGIN = pm.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get940Request("'0150'", ", ETABLISSEMENT e", " WHERE c.SGIN = e.SGIN", " AND e.SGIN = pm.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get940Request("DECODE(a.SAGENCE_RA2, 'N', '0160', '0170')", ", AGENCE a", " WHERE c.SGIN = a.SGIN", " AND a.SGIN = pm.SGIN", $startDate, $endDate));
}
elsif ($replicationNumber == 950)
{
    my $request = "SELECT ".
        "'0950:#:'||TRIM(o.ICLE)||':#:'||DECODE(a.SAGENCE_RA2, 'Y', '0170', '0160')||':#:'||o.SGIN||':#:'||o.SOFFICE_ID||':#:'||".
        "o.SCODE_GDS||':#:'||o.SLETTRE_COMPTOIR||':#:'||r.SLIBELLE||':#:'||TO_CHAR(o.DDATE_LAST_RESA, 'DDMMYYYY')||':#:' ".
        "FROM OFFICE_ID o, REF_CODE_GDS r, AGENCE a";
    $request .= " WHERE o.SCODE_GDS = r.SCODE_IATA AND o.SGIN = a.SGIN";
    if (defined($startDate) || defined($endDate))
    {
        $request .=  getTimeWindowConditionString2("o", $startDate, $endDate, 0);
    }
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
elsif ($replicationNumber == 960)
{
    simpleSqlSelect(\$dbConnexion, \$error, get960Request("'0130'", ", GROUPE g", " WHERE s.SGIN = g.SGIN", " AND g.SGIN = pm.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get960Request("'0140'", ", ENTREPRISE e", " WHERE s.SGIN = e.SGIN", " AND e.SGIN = pm.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get960Request("'0150'", ", ETABLISSEMENT e", " WHERE s.SGIN = e.SGIN", " AND e.SGIN = pm.SGIN", $startDate, $endDate));
    simpleSqlSelect(\$dbConnexion, \$error, get960Request("DECODE(a.SAGENCE_RA2, 'N', '0160', '0170')", ", AGENCE a", " WHERE s.SGIN = a.SGIN", " AND a.SGIN = pm.SGIN", $startDate, $endDate));
}
elsif ($replicationNumber == 970)
{
    my $commonSelect = "'0970:#:'||SCODE||':#:'||SLIBELLE||':#:'||SLIBELLE_EN||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM REF_NIV_SEG_A UNION SELECT $commonSelect FROM REF_NIV_SEG_F");
}
elsif ($replicationNumber == 980)
{
    my $commonSelect = "'0980:#:'||TRIM(SCODE)||':#:'||SLIBELLE||':#:'||SLIBELLE_EN||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM REF_TYP_SEG_A UNION SELECT $commonSelect FROM REF_TYP_SEG_F");
}
elsif ($replicationNumber == 990)
{
    my $commonSelect = "'0990:#:'||TRIM(p.ICLE_PRF)||':#::#::#:'||TRIM(p.SMATRICULE)||':#:'||TRIM(p.SNOM_PRF_HAB)";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM PROFIL_AF p");
}
elsif ($replicationNumber == 991)
{
    my $commonSelect = "'0991:#:'||TRIM(c.SCODE_MEDIUM)||':#:'||TRIM(c.SCTG_MEDIUM)||':#:'||TRIM(c.SCTG_MEDIUM_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM cat_med c");
}
elsif ($replicationNumber == 992)
{
    my $commonSelect = "'0992:#:'||TRIM(d.SCODE_PROFESSIONNEL)||':#:'||TRIM(d.SLIBELLE_DOMAINE)||':#:'||TRIM(d.SLIBELLE_DOMAINE_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM dom_pro d");
}
elsif ($replicationNumber == 993)
{
    my $commonSelect = "'0993:#:'||TRIM(m.SCODE_APPLICATION)||':#:'||TRIM(m.SMETIER)||':#:'||TRIM(m.SAPPLICATION)||':#:'||TRIM(m.SMETIER_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM metiers m");
}
elsif ($replicationNumber == 994)
{
    my $commonSelect = "'0994:#:'||TRIM(r.SCODE)||':#:'||TRIM(r.SLIBELLE)||':#:'||TRIM(r.SLIBELLE_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_typ_term r");
}
elsif ($replicationNumber == 995)
{
    my $commonSelect = "'0995:#:'||TRIM(s.SCODE_MARITALE)||':#:'||TRIM(s.SLIBELLE_MARITALE)||':#:'||TRIM(s.SLIBELLE_MARITALE_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM sit_maritales s");
}
elsif ($replicationNumber == 996)
{
    my $commonSelect = "'0996:#:'||TRIM(t.SCODE_TITRE)||':#:'||TRIM(t.SLIBELLLE_TITRE_CIVIL)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM titre_civils t");
}
elsif ($replicationNumber == 997)
{
    my $commonSelect = "'0997:#:'||TRIM(t.SCODE)||':#:'||TRIM(t.SLIBELLE)||':#:'||TRIM(t.SLIBELLE_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_typ_vent t");
}
elsif ($replicationNumber == 998)
{
    my $commonSelect = "'0998:#:'||TRIM(t.SCODE)||':#:'||TRIM(t.SLIBELLE)||':#:'||TRIM(t.SLIBELLE_EN)||':#:'||TRIM(t.SCODE_VENTE)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_domaine t");
}
elsif ($replicationNumber == 999)
{
    my $commonSelect = "'0999:#:'||TRIM(t.SCODE)||':#:'||TRIM(t.SALIAS)||':#:'||TRIM(t.SLIBELLE)||':#:'||TRIM(t.SLIBELLE_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM code_indus t");
}
elsif ($replicationNumber == 1000)
{
    my $commonSelect = "'1000:#:'||TRIM(t.SCODE_FONCTION)||':#:'||TRIM(t.SLIBELLE_FONCTION)||':#:'||TRIM(t.SLIBELLE_FONCTION_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM fct_pro t");
}
elsif ($replicationNumber == 1001)
{
    my $commonSelect = "'1001:#:'||TRIM(t.SCODE)||':#:'||TRIM(t.SLIBELLE)||':#:'||TRIM(t.SLIBELLE_EN)||':#:'||TRIM(t.SCODE_DOMAINE)||':#:'||TRIM(t.ICLE)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_ss_domaine t");
}
elsif ($replicationNumber == 1002)
{
    my $commonSelect = "'1002:#:'||TRIM(t.SCODE)||':#:'||TRIM(t.SLIBELLE)||':#:'||TRIM(t.SLIBELLE_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_typ_agen t");
}
elsif ($replicationNumber == 1003)
{
    my $commonSelect = "'1003:#:'||TRIM(t.SCODE)||':#:'||TRIM(t.SLIBELLE)||':#:'||TRIM(t.SLIBELLE_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_typ_ctr t");
}
elsif ($replicationNumber == 1004)
{
    my $commonSelect = "'1004:#:'||TRIM(t.SCODE)||':#:'||TRIM(t.SLIBELLE)||':#:'||TRIM(t.SLIBELLE_EN)||':#:'||TRIM(t.STYPE_CTR)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_styp_ctr t");
}
elsif ($replicationNumber == 1005)
{
    my $commonSelect = "'1005:#:'||TRIM(t.SCODE)||':#:'||TRIM(t.SLIBELLE)||':#:'||TRIM(t.SLIBELLE_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_nat_reso t");
}
elsif ($replicationNumber == 1006)
{
	
    my $request = "SELECT '1006:#:'||TRIM(t.icle)||':#:'||TRIM(t.igin_zone)||':#:'||TRIM(t.icle_int_cp)||':#:'||TRIM(t.scode_pays)||':#:'||TRIM(t.scode_prov)||':#:'||TRIM(t.scode_ville)||':#:'||TRIM(t.susage)||':#:'||TO_CHAR(t.DDATE_DEB_LIEN, 'DDMMYYYY')||':#:'||TO_CHAR(t.DDATE_FIN_LIEN, 'DDMMYYYY')||':#:'||TO_CHAR(t.DDATE_MAJ, 'DDMMYYYY')||':#:'||TRIM(t.SSIGNATURE_MAJ)||':#:' FROM lien_int_cp_zd t";
	if (defined($startDate) || defined($endDate))
    {
	
        $request .=  getTimeWindowConditionString2("t", $startDate, $endDate, 1);
		print(STDERR $request."\n");
    }
    simpleSqlSelect(\$dbConnexion, \$error, "$request");
}
elsif ($replicationNumber == 1007)
{
    my $commonSelect = "'1007:#:'||TRIM(p.SCODE_PAYS)||':#:'||TRIM(p.STELECOM_COUNTRY_CODE)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM pays p");
}
elsif ($replicationNumber == 1008)
{
    my $commonSelect = "'1008:#:'||TRIM(d.SCODE_DOMAIN)||':#:'||TRIM(d.SLIBELLE_DOMAIN)||':#:'||TRIM(d.SLIBELLE_DOMAIN_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_compref_domain d");
}
elsif ($replicationNumber == 1009)
{
    my $commonSelect = "'1009:#:'||TRIM(t.SCODE_TYPE)||':#:'||TRIM(t.SLIBELLE_TYPE)||':#:'||TRIM(t.SLIBELLE_TYPE_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_compref_type t");
}
elsif ($replicationNumber == 1010)
{
    my $commonSelect = "'1010:#:'||TRIM(g.SCODE_GTYPE)||':#:'||TRIM(g.SLIBELLE_GTYPE)||':#:'||TRIM(g.SLIBELLE_GTYPE_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_compref_gtype g");
}
elsif ($replicationNumber == 1011)
{
    my $commonSelect = "'1011:#:'||TRIM(m.SCODE_MEDIA)||':#:'||TRIM(m.SLIBELLE_MEDIA)||':#:'||TRIM(m.SLIBELLE_MEDIA_EN)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_compref_media m");
}
elsif ($replicationNumber == 1012)
{
    my $commonSelect = "'1012:#:'||TRIM(c.CODE_PAYS)||':#:'||TRIM(c.MARKET)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_compref_country_market c");
}
elsif ($replicationNumber == 1013)
{
    my $commonSelect = "'1013:#:'||TRIM(c.REF_COMPREF_ID)||':#:'||TRIM(c.DOMAIN)||':#:'||TRIM(c.COM_GROUP_TYPE)||':#:'||TRIM(c.COM_TYPE)||':#:'||TRIM(c.DESCRIPTION)||':#:'||TRIM(c.MANDATORY_OPTIN)||':#:'||TRIM(c.MARKET)||':#:'||TRIM(c.DEFAULT_LANGUAGE_1)||':#:'||TRIM(c.DEFAULT_LANGUAGE_2)||':#:'||TRIM(c.DEFAULT_LANGUAGE_3)||':#:'||TRIM(c.DEFAULT_LANGUAGE_4)||':#:'||TRIM(c.DEFAULT_LANGUAGE_5)||':#:'||TRIM(c.DEFAULT_LANGUAGE_6)||':#:'||TRIM(c.DEFAULT_LANGUAGE_7)||':#:'||TRIM(c.DEFAULT_LANGUAGE_8)||':#:'||TRIM(c.DEFAULT_LANGUAGE_9)||':#:'||TRIM(c.DEFAULT_LANGUAGE_10)||':#:'||TRIM(c.A)||':#:'||TRIM(c.N)||':#:'||TRIM(c.T)||':#:'||TRIM(c.MEDIA)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_compref c");
}
elsif ($replicationNumber == 1014)
{
    my $commonSelect = "'1014:#:'||TRIM(a.KEY)||':#:'||TRIM(a.VALUE)||':#:'||TRIM(a.USAGE)||':#:'||TRIM(a.TYPE)||':#:'||TRIM(a.MANDATORY)||':#:'";
    simpleSqlSelect(\$dbConnexion, \$error, "SELECT $commonSelect FROM ref_alert a");
}
elsif ($replicationNumber == 1015)
{
    my $request = "SELECT ".
    	"'1015:#:'||BSP_DATA_ID||':#:'||SGIN||':#:'||TYPE||':#:'||COMPANY||':#:'||AUTHORIZATION||':#:'||".
	"TO_CHAR(DDATE_CREATION, 'DDMMYYYY')||':#:'||SSIGNATURE_CREATION||':#:'||SSITE_CREATION||':#:'||".
	"TO_CHAR(DDATE_MODIFICATION, 'DDMMYYYY')||':#:'||SSIGNATURE_CREATION||':#:'||SSITE_MODIFICATION||':#:'".
    	"FROM BSP_DATA";
    if (defined($startDate))
    {
        $request .= " WHERE DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')";
    }
    if (defined($endDate))
    {
        $request .= " ".(defined($startDate)?"AND":"WHERE")." DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')";
    }
    simpleSqlSelect(\$dbConnexion, \$error, $request);
}
 elsif ($replicationNumber == 2000)
 {
 	 my $request = "select '2000:#:'||i.SGIN||':#:'||i.SGIN||':#:'||e.SEMAIL||':#:'||i.SCIVILITE||':#:'||i.SNOM||':#:'||i.SNOM_TYPO||':#:'||i.SPRENOM||':#:'||i.SPRENOM_TYPO||':#:'||i.SSECOND_PRENOM||':#::#::#:'||i.SNATIONALITE||':#:'||i.SAUTRE_NATIONALITE||':#::#::#:'||i.SSTATUT_INDIVIDU ".
  "||':#::#:'||TO_CHAR(NVL(i.DDATE_CREATION, i.DDATE_CREATION), 'DDMMYYYY')||':#:'||i.SSIGNATURE_CREATION||':#:'||i.SSITE_CREATION||':#:'||TO_CHAR(NVL(i.DDATE_MODIFICATION, i.DDATE_MODIFICATION), 'DDMMYYYY') ".
  "||':#:'||i.SSIGNATURE_MODIFICATION||':#:'||i.SSITE_MODIFICATION||':#:'||e.SSTATUT_MEDIUM ".
  "from INDIVIDUS_ALL i INNER JOIN EMAILS e ON i.SGIN = e.SGIN WHERE i.STYPE = 'W' AND e.SCODE_MEDIUM = 'D' ";
 
  if (defined($startDate))
 	 {		
                   $request .= " and (i.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') OR e.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')) ";

 	 }
 	 if (defined($endDate))
 	 {
                   $request .= " and (i.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS') OR e.DDATE_MODIFICATION < TO_DATE('$startDate', 'YYYYMMDDHH24MISS')) ";
  }
 
 	 simpleSqlSelect(\$dbConnexion, \$error, $request);
 }
 elsif ($replicationNumber == 2100)
 {
 	 my $request = "select REPLACE(REPLACE('2100:#:'||ap.SAIN||':#:'||i.SGIN||':#:'||ap.SNO_ET_RUE||':#:'||ap.SCODE_POSTAL||':#:'||ap.SVILLE||':#:'||ap.SCODE_PROVINCE||':#:'||ap.SCODE_PAYS||':#:'||pref_data.SVALUE||':#:'||ap.SSTATUT_MEDIUM||':#:'||ap.SCOD_ERR_SIMPLE||':#:'||ap.SCOD_ERR_DETAILLE||':#:'||".
  "TO_CHAR(NVL(ap.DDATE_CREATION, ap.DDATE_CREATION), 'DDMMYYYY')||':#:'||ap.SSIGNATURE_CREATION||':#:'||ap.SSITE_CREATION||':#:'||TO_CHAR(NVL(ap.DDATE_MODIFICATION, ap.DDATE_MODIFICATION), 'DDMMYYYY')||':#:'||ap.SSIGNATURE_MODIFICATION||':#:'||ap.SSITE_MODIFICATION, chr(10), ' '), chr(13), ' ')".
  "from ADR_POST ap INNER JOIN INDIVIDUS_ALL i ON ap.SGIN = i.SGIN INNER JOIN PREFERENCE pref ON i.SGIN = pref.SGIN INNER JOIN PREFERENCE_DATA pref_data ON pref.PREFERENCE_ID = pref_data.PREFERENCE_ID WHERE i.STYPE = 'W' AND pref_data.SKEY = 'preferredAirport' ";
 
  if (defined($startDate))
 	 {
 		  $request .= " and (ap.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') or i.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
 	 }
 	 if (defined($endDate))
 	 {
 		  $request .= " and (ap.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS') or i.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
  }
 
 	 simpleSqlSelect(\$dbConnexion, \$error, $request);
 }
 elsif ($replicationNumber == 2200)
 {
 	 my $request = "select '2200:#:'||pt.SAIN||':#:'||pt.SGIN||':#:'||pt.SSTATUT_MEDIUM||':#:'||pt.SCODE_MEDIUM||':#:'||pt.STERMINAL||':#:'||ap.SCODE_PAYS||':#:'||pt.SCODE_REGION||':#:'||pt.SNUMERO||':#:'||pt.SNORMALIZED_COUNTRY||':#:'||pt.SNORMALIZED_NUMERO||':#:'||".
  "TO_CHAR(NVL(pt.DDATE_CREATION, pt.DDATE_CREATION), 'DDMMYYYY')||':#:'||pt.SSIGNATURE_CREATION||':#:'||pt.SSITE_CREATION||':#:'||".
  "TO_CHAR(NVL(pt.DDATE_MODIFICATION, pt.DDATE_MODIFICATION), 'DDMMYYYY')||':#:'||pt.SSIGNATURE_MODIFICATION||':#:'||pt.SSITE_MODIFICATION||".
 	 "':#:'||pt.SNORM_NAT_PHONE_NUMBER||".
 	 "':#:'||pt.SNORM_INTER_COUNTRY_CODE||':#:'||pt.SNORM_INTER_PHONE_NUMBER||".
 	 "':#:'||pt.SNORM_TERMINAL_TYPE_DETAIL||':#:'||pt.ISNORMALIZED||':#:'||pt.SNORM_NAT_PHONE_NUMBER_CLEAN ".
  "from TELECOMS pt INNER JOIN INDIVIDUS_ALL i ON pt.SGIN = i.SGIN INNER JOIN ADR_POST ap ON i.SGIN = ap.SGIN WHERE i.STYPE = 'W' ";
 
  if (defined($startDate))
 	 {
 		  $request .= " and (pt.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') or i.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')) ";
 	 }
 	 if (defined($endDate))
 	 {
 		  $request .= " and (pt.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS') or i.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
  }
 
 	 simpleSqlSelect(\$dbConnexion, \$error, $request);
 
 }
 elsif ($replicationNumber == 2300)
 {
 	 my $request = "select '2300:#:'||cp.COM_PREF_ID||':#:'||cp.SGIN||':#:'||cp.DOMAIN||':#:'||cp.COM_GROUP_TYPE||':#:'||cp.COM_TYPE||':#:'||cp.MEDIA1||':#:'||cp.MEDIA2||':#:'||cp.MEDIA3||':#:'||cp.MEDIA4||':#:'||cp.MEDIA5||':#:'||cp.SUBSCRIBE||':#:'||".
  "TO_CHAR(NVL(cp.DATE_OPTIN, cp.DATE_OPTIN), 'DDMMYYYY')||':#:'||cp.CHANNEL||':#:'||cp.OPTIN_PARTNERS||':#:'||
 TO_CHAR(NVL(cp.DATE_OPTIN_PARTNERS, cp.DATE_OPTIN_PARTNERS), 'DDMMYYYY')||':#:'||TO_CHAR(NVL(cp.DATE_OF_ENTRY, cp.DATE_OF_ENTRY), 'DDMMYYYY')||':#:'||".
 	 "TO_CHAR(NVL(cp.CREATION_DATE, cp.CREATION_DATE), 'DDMMYYYY')||':#:'||cp.CREATION_SIGNATURE||':#:'||cp.CREATION_SITE||':#:'||TO_CHAR(NVL(cp.MODIFICATION_DATE, cp.MODIFICATION_DATE), 'DDMMYYYY')||':#:'||cp.MODIFICATION_SIGNATURE||':#:'||cp.MODIFICATION_SITE ".
  "from COMMUNICATION_PREFERENCES cp INNER JOIN INDIVIDUS_ALL i ON cp.SGIN = i.SGIN WHERE i.STYPE = 'W' ";
 
  if (defined($startDate))
 	 {
 		  $request .= " and (cp.MODIFICATION_DATE >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') or i.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
 	 }
 	 if (defined($endDate))
 	 {
 		  $request .= " and (cp.MODIFICATION_DATE < TO_DATE('$endDate', 'YYYYMMDDHH24MISS') or i.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
  }
 
 	 simpleSqlSelect(\$dbConnexion, \$error, $request);
 }
 elsif ($replicationNumber == 2400)
 {
 	  my $request = "select '2400:#:'||ml.MARKET_LANGUAGE_ID||':#:'||ml.COM_PREF_ID||':#:'||ml.MARKET||':#:'||ml.LANGUAGE_CODE||':#:'||ml.OPTIN||':#:'||TO_CHAR(NVL(ml.DATE_OPTIN, ml.DATE_OPTIN), 'DDMMYYYY')||':#:'||ml.MEDIA1||':#:'||ml.MEDIA2||':#:'||ml.MEDIA3||':#:'||ml.MEDIA4||':#:'||ml.MEDIA5||':#:'||".
   "TO_CHAR(NVL(ml.CREATION_DATE, ml.CREATION_DATE), 'DDMMYYYY')||':#:'||ml.CREATION_SIGNATURE||':#:'||ml.CREATION_SITE||':#:'||TO_CHAR(NVL(ml.MODIFICATION_DATE, ml.MODIFICATION_DATE), 'DDMMYYYY')||':#:'||ml.modification_signature||':#:'||ml.modification_site ".
   "from MARKET_LANGUAGE ml INNER JOIN COMMUNICATION_PREFERENCES cp ON ml.COM_PREF_ID = cp.COM_PREF_ID INNER JOIN INDIVIDUS_ALL i ON cp.SGIN = i.SGIN WHERE i.STYPE = 'W'  ";
 
 
 	if (defined($startDate))
     {
 		  $request .= " and (cp.MODIFICATION_DATE >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') or ml.MODIFICATION_DATE >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') or i.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
 	}
 	if (defined($endDate))
 	{
 		  $request .= " and (cp.MODIFICATION_DATE < TO_DATE('$endDate', 'YYYYMMDDHH24MISS') or ml.MODIFICATION_DATE < TO_DATE('$endDate', 'YYYYMMDDHH24MISS') or i.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')) ";
 	}
 	simpleSqlSelect(\$dbConnexion, \$error, $request);
 }
 elsif ($replicationNumber == 2500)
 {
 	my $request = "SELECT ".
 	"'2500:#:'||al.ALERT_ID||".
 		"':#:'||al.SGIN||".
 		"':#:'||al.TYPE||".
 		"':#:'||al.OPTIN||".
 		"':#:'||TO_CHAR(al.CREATION_DATE, 'DDMMYYYY')||".
 		"':#:'||al.CREATION_SIGNATURE||".
 		"':#:'||al.CREATION_SITE||".
 		"':#:'||TO_CHAR(al.MODIFICATION_DATE, 'DDMMYYYY')||".
 		"':#:'||al.MODIFICATION_SIGNATURE||".
 		"':#:'||al.MODIFICATION_SITE".
 		" FROM ALERT al INNER JOIN INDIVIDUS_ALL i ON al.SGIN = i.SGIN WHERE i.STYPE = 'W' ";
 		
 	if (defined($startDate)) {
 	  $request .= " AND (al.MODIFICATION_DATE >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
 	}
 	if (defined($endDate)) {
 	  $request .= " AND (al.MODIFICATION_DATE < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
 	}
 
 	simpleSqlSelect(\$dbConnexion, \$error, $request);
 }
 elsif ($replicationNumber == 2600)
 {
 
 	my $request = "SELECT ".
 	"'2600:#:'||ald.ALERT_DATA_ID||".
 		"':#:'||ald.ALERT_ID||".
 		"':#:'||ald.KEY||".
 		"':#:'||ald.VALUE||".
 		"':#:'||TO_CHAR(al.CREATION_DATE, 'DDMMYYYY')||".
 		"':#:'||al.CREATION_SIGNATURE||".
 		"':#:'||al.CREATION_SITE||".
 		"':#:'||TO_CHAR(al.MODIFICATION_DATE, 'DDMMYYYY')||".
 		"':#:'||al.MODIFICATION_SIGNATURE||".
 		"':#:'||al.MODIFICATION_SITE".
 		" FROM ALERT_DATA ald INNER JOIN ALERT al ON ald.ALERT_ID = al.ALERT_ID INNER JOIN INDIVIDUS_ALL i ON al.SGIN = i.SGIN WHERE i.STYPE = 'W' ";
 		
 	if (defined($startDate)) {
 	  $request .= " AND (al.MODIFICATION_DATE >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))";
 	}
 	if (defined($endDate)) {
 	  $request .= " AND (al.MODIFICATION_DATE < TO_DATE('$endDate', 'YYYYMMDDHH24MISS'))";
 	}
 
 	simpleSqlSelect(\$dbConnexion, \$error, $request);
 
 }
else
{
	 print(STDERR "ERROR: unknown replication file number: '$replicationNumber'\n");
	 $error = 1;
}

# Deconnexion base SIC
$dbConnexion->disconnect();

if ((!$error) && defined($notificationFile))
{
    # we notify that the process terminated correctly
    system("touch $notificationFile");
}

exit($error);

#!/tech/oracle/client/12102/perl/bin/perl -w
 
use lib ;
use strict;
use DBI;
use Getopt::Long;

my $programName = $0;
$programName =~ s/^(.*\/)?([^\/]*)$/$2/;

my $xfb=$ENV{XFB};

sub displayHelp()
{
    print(STDERR "usage: $programName {-h | {[-d] [-dataPath <path>]}}\n");
    print(STDERR "where option can be:\n");
    print(STDERR "    -h : displays this help\n");
    print(STDERR "    -startDate <date> : replicates only records that changed after or at date (YYYYMMDDHH24MISS)\n");
    print(STDERR "    -endDate <date> : replicates only records that changed before date (YYYYMMDDHH24MISS)\n");
}

sub addTimeCondition($$){

    my $startDate = $_[0];
    my $endDate = $_[1];
    my $request="";
    if(defined($startDate))
    {
        $request .= " AND pm.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS') OR ".
            	"(pm.DDATE_MODIFICATION IS NULL AND pm.DDATE_CREATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')) ";
    }
    if(defined($endDate))
    {
        $request.= "AND pm.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS') OR ".
           	"(pm.DDATE_MODIFICATION IS NULL AND pm.DDATE_CREATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')) ";
    } 
    return $request;
}
my $sizeList;
my $compteur;
my $help;
my $startDate;
my $endDate;

if (!GetOptions (
    "h" => \$help,
    "startDate=s" => \$startDate,
    "endDate=s" => \$endDate))
{
    displayHelp();
    exit(1);
}

my $environment;

if ((`secureSIC \$SECURE_DATABASE_ACCESS_FILE_SIC READSIC` =~ /^([^\/]*)\/([^@]*)@(.*)$/))
{
   $environment=$3;
}

my $flux1;
my $flux2;

if($environment eq 'SICL_PRD')
{
   $flux1="RDR";
   $flux2="RDR";
}

elsif($environment eq 'SIC_RCT')
{
   $flux1="TDR";
   $flux2="TDR";
}

my $fileNameRA1= $ENV{BASE_DATA_DIR}."/AGENCES/DELOREAN";
my $fileNameRA2= $ENV{BASE_DATA_DIR}."/AGENCES/DELOREAN";

if (defined($startDate) && defined($endDate))
{
    $fileNameRA1.="/MVT/RC02.";
    $fileNameRA2.="/MVT/RC02.";

    $flux1.="M01";
    $flux2.="M02";
}
else
{
    $fileNameRA1.="/GLOBAL/RC02.";
    $fileNameRA2.="/GLOBAL/RC02.";

    $flux1.="G01";
    $flux2.="G02";
}


if($environment eq 'SIC_DEV')
{
    $fileNameRA1.="DEV";
    $fileNameRA2.="DEV";
}

elsif($environment eq 'SIC_RCT')
{
    $fileNameRA1.="TST";
    $fileNameRA2.="TST";
}

elsif($environment eq 'SICL_PRD')
{
    $fileNameRA1.="TMP";
    $fileNameRA2.="TMP";
}



$fileNameRA1.=".AF.AGENTS.RA1.";
$fileNameRA2.=".AF.AGENTS.RA2.";

if (defined($startDate) && defined($endDate))
{
    $fileNameRA1.="M";
    $fileNameRA2.="M";
}
else
{
    $fileNameRA1.="G";
    $fileNameRA2.="G";
}

my @time=localtime;
my $year=substr (($time[5]+1900), 2);
$year=($year<10)?"0".$year:$year;
my $month=$time[4]+1;
$month=($month<10)?"0".$month:$month;
my $day=($time[3]<10)?"0".$time[3]:$time[3];
my $hour=($time[2]<10)?"0".$time[2]:$time[2];
my $min=($time[1]<10)?"0".$time[1]:$time[1];
my $sec=($time[0]<10)?"0".$time[0]:$time[0];

$fileNameRA1.=".D$year$month$day.T$hour$min$sec";
$fileNameRA2.=".D$year$month$day.T$hour$min$sec";

open(FILERA1, ">$fileNameRA1");
open(FILERA2, ">$fileNameRA2");

if ($help || @ARGV)
{
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

# header
print FILERA1 "0010:#:SEPARATEUR:#:\n";
print FILERA2 "0010:#:SEPARATEUR:#:\n";

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
print FILERA1 "0020:#:DATE DEBUT:#:$startDateString:#:DATE FIN:#:$endDateString:#:\n";
print FILERA2 "0020:#:DATE DEBUT:#:$startDateString:#:DATE FIN:#:$endDateString:#:\n";

my $requestRA1="SELECT DISTINCT n.snumero AS IATA,".
"  ag.stype                AS Agent_Type,".
"  ag.snumero_iata_mere    AS IATA_Mere,".
"  pm.snom                 AS Nom,".
"  sy.snom                 AS otherName,".
"  sy.stype                AS nameType,".
"  ag.sdomaine             AS Channel,".
"  ag.ssous_domaine        AS SubChannel,".
"  zc.szc1                 AS ZC1,".
"  zc.szc2                 AS ZC2,".
"  zc.szc3                 AS ZC3,".
"  zc.szc4                 AS ZC4,".
"  zc.szc5                 AS ZC5,".
"  zv.zv0                  AS ZV0,".
"  zv.zv1                  AS ZV1,".
"  zv.zv2                  AS ZV2,".
"  zv.zv3                  AS ZV3,".
"  zv.zvalpha              AS ZV_ALPHA,".
"  ag.sstatut_iata         AS statut_iata,".
"  TO_CHAR(ag.ddate_deb,'DD-MM-YYYY')            AS date_debut,".
"  TO_CHAR(ag.ddate_fin,'DD-MM-YYYY')            AS date_fin,".
"  pm.sgin                 AS Gin,".
"  oi.soffice_id           AS Office_ID,".
"  oi.scode_gds            AS CODE_GDS,".
"  ag.stype_vente          AS Type_Vente,".
"  adr.scode_pays          AS Code_Pays,".
"  pm.ssite_internet       AS site_internet,".
"  pm.scie_gest            AS compagnie_gest,".
"  TO_CHAR(pmz.ddate_ouverture,'DD-MM-YYYY')     AS date_link_creation,".
"  TO_CHAR(pmz.ddate_fermeture,'DD-MM-YYYY')     AS date_link_closure,".
"  pm.sstatut              AS Agent_Status,".
"  TO_CHAR(n.ddate_fermeture,'DD-MM-YYYY')       AS Date_Fin_Agrement,".
"  TO_CHAR(ag.ddate_radiation,'DD-MM-YYYY')       AS Date_Radiation,".
"  mr.scode                AS reseau,".
"  re.stype                AS type_Reseau".
" FROM SIC2.pers_morale pm".
" LEFT JOIN SIC2.agence ag".
" ON (pm.sgin = ag.sgin)".
" LEFT JOIN SIC2.synonyme sy".
" ON (sy.sgin = pm.sgin)".
" LEFT JOIN SIC2.NUMERO_IDENT n".
" ON (n.sgin = pm.sgin)".
" LEFT JOIN SIC2.ADR_POST adr".
" ON (adr.sgin_pm = pm.sgin)".
" LEFT JOIN SIC2.telecoms tel".
" ON (tel.sgin_pm = pm.sgin)".
" LEFT JOIN SIC2.office_id oi".
" ON (oi.sgin = pm.sgin)".
" LEFT JOIN SIC2.pm_zone pmz".
" ON (pmz.sgin = pm.sgin)".
" LEFT JOIN SIC2.zone_decoup zd".
" ON (zd.igin = pmz.igin_zone)".
" LEFT JOIN SIC2.zone_comm zc".
" ON (zc.igin = zd.igin)".
" LEFT JOIN SIC2.zone_vente zv".
" ON (zv.igin     = zd.igin)".
" LEFT JOIN SIC2.membre_reseau mr".
" ON (mr.sgin = pm.sgin)".
" LEFT JOIN SIC2.reseau re".
" ON (re.scode = mr.scode)".
" WHERE sy.stype IN ('U', 'M')".
" AND ag.stype != '9'";

if (defined($startDate) || defined($endDate))
    {
        $requestRA1 .= addTimeCondition($startDate,$endDate);
    }
$requestRA1 .= " ORDER BY pm.snom";
my $selectRequestRA1 = $dbConnexion->prepare($requestRA1);
if ($selectRequestRA1->execute())
{  
   while (my @row = $selectRequestRA1->fetchrow_array())
   {           
       $sizeList=@row;
       $compteur=0;
       foreach my $element (@row)
       {
           if($compteur<$sizeList-1)
           {
               if(defined($element))
	       {
                   print FILERA1 $element.":#:";
               }
               else
               {
                   print FILERA1 ":#:";
               }
           }
           else
           {
               if(defined($element))
	       {
                   print FILERA1 $element;
               }
           }
           $compteur++;
       }
       print FILERA1 "\n";
   }
}
else
{
   ${$error} = 1;
   print(STDERR "ERROR: cannot execute request: ".$selectRequestRA1->errstr."\n");
}
$selectRequestRA1->finish();

my $requestRA2="SELECT DISTINCT n.snumero AS IATA,".
"  ag.stype                AS Agent_Type,".
"  ag.snumero_iata_mere    AS IATA_Mere,".
"  pm.snom                 AS Nom,".
"  sy.snom                 AS otherName,".
"  sy.stype                 AS nameType,".
"  ag.sdomaine             AS Channel,".
"  ag.ssous_domaine        AS SubChannel,".
"  zc.szc1                 AS ZC1,".
"  zc.szc2                 AS ZC2,".
"  zc.szc3                 AS ZC3,".
"  zc.szc4                 AS ZC4,".
"  zc.szc5                 AS ZC5,".
"  zv.zv0                  AS ZV0,".
"  zv.zv1                  AS ZV1,".
"  zv.zv2                  AS ZV2,".
"  zv.zv3                  AS ZV3,".
"  zv.zvalpha              AS ZV_ALPHA,".
"  ag.sstatut_iata         AS statut_iata,".
"  TO_CHAR(ag.ddate_deb,'DD-MM-YYYY')            AS date_debut,".
"  TO_CHAR(ag.ddate_fin,'DD-MM-YYYY')            AS date_fin,".
"  pm.sgin                 AS Gin,".
"  oi.soffice_id           AS Office_ID,".
"  oi.scode_gds            AS CODE_GDS,".
"  ag.stype_vente          AS Type_Vente,".
"  adr.scode_pays          AS Code_Pays,".
"  pm.ssite_internet       AS site_internet,".
"  pm.scie_gest            AS compagnie_gest,".
"  TO_CHAR(pmz.ddate_ouverture,'DD-MM-YYYY')     AS date_link_creation,".
"  TO_CHAR(pmz.ddate_fermeture,'DD-MM-YYYY')     AS date_link_closure,".
"  pm.sstatut              AS Agent_Status,".
"  TO_CHAR(n.ddate_fermeture,'DD-MM-YYYY')       AS Date_Fin_Agrement,".
"  TO_CHAR(ag.ddate_radiation,'DD-MM-YYYY')       AS Date_Radiation,".
"  mr.scode                AS reseau,".
"  re.stype                AS type_Reseau".
" FROM SIC2.pers_morale pm".
" LEFT JOIN SIC2.agence ag".
" ON (pm.sgin = ag.sgin)".
" LEFT JOIN SIC2.synonyme sy".
" ON (sy.sgin = pm.sgin)".
" LEFT JOIN SIC2.NUMERO_IDENT n".
" ON (n.sgin = pm.sgin)".
" LEFT JOIN SIC2.ADR_POST adr".
" ON (adr.sgin_pm = pm.sgin)".
" LEFT JOIN SIC2.telecoms tel".
" ON (tel.sgin_pm = pm.sgin)".
" LEFT JOIN SIC2.office_id oi".
" ON (oi.sgin = pm.sgin)".
" LEFT JOIN SIC2.pm_zone pmz".
" ON (pmz.sgin = pm.sgin)".
" LEFT JOIN SIC2.zone_decoup zd".
" ON (zd.igin = pmz.igin_zone)".
" LEFT JOIN SIC2.zone_comm zc".
" ON (zc.igin = zd.igin)".
" LEFT JOIN SIC2.zone_vente zv".
" ON (zv.igin     = zd.igin)".
" LEFT JOIN SIC2.membre_reseau mr".
" ON (mr.sgin = pm.sgin)".
" LEFT JOIN SIC2.reseau re".
" ON (re.scode = mr.scode)".
" WHERE ag.stype = '9'".
" AND sy.stype  IN ('U', 'M')";

if (defined($startDate) || defined($endDate))
    {
        $requestRA2 .= addTimeCondition($startDate,$endDate);
    }
$requestRA2 .= " ORDER BY pm.snom";

my $selectRequestRA2 = $dbConnexion->prepare($requestRA2);
if ($selectRequestRA2->execute())
{
   while (my @row = $selectRequestRA2->fetchrow_array())
   {
       $sizeList=@row;
       $compteur=0;
       foreach my $element (@row)
       {
           if($compteur<$sizeList-1)
           {
               if(defined($element))
	       {
                   print FILERA2 $element.":#:";
               }
               else
               {
                   print FILERA2 ":#:";
               }
           }
           else
           {
               if(defined($element))
	       {
                   print FILERA2 $element;
               }
           }
           $compteur++;
       }
       print FILERA2 "\n";
   }
}
else
{
   ${$error} = 1;
   print(STDERR "ERROR: cannot execute request: ".$selectRequestRA2->errstr."\n");
}
$selectRequestRA2->finish();

$dbConnexion->disconnect();
close(FILERA1);
close(FILERA2);

system("gzip $fileNameRA1");
system("gzip $fileNameRA2");

if($environment eq 'SICL_PRD')
{
   system("$xfb/gwqvi1.sh $fileNameRA1.gz $flux1");
   system("$xfb/gwqvi1.sh $fileNameRA2.gz $flux2");
}
elsif($environment eq 'SIC_RCT')
{
   system("$xfb/gwqvi1.sh $fileNameRA1.gz $flux1");
   system("$xfb/gwqvi1.sh $fileNameRA2.gz $flux2");
}

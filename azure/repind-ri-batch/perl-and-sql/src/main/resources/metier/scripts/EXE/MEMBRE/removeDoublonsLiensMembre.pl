#!/tech/oracle/client/12102/perl/bin/perl -w
use lib ;

use strict;
use DBI;

my $num;

# connection to database

if (!(`secureSIC \$SECURE_DATABASE_ACCESS_FILE_SIC READSIC` =~ /^([^\/]*)\/([^@]*)@(.*)$/))
{
    print("ERROR: unable to get database connexion parameters\n"); 
    exit(1);
}
my $dbh = DBI->connect("dbi:Oracle:$3", $1, $2);

if (!$dbh) {
   print "Couldn't connect to database"; 
   die "Couldn't connect to database: " . DBI->errstr;
}


my $sql = qq{select count(*), SGIN_INDIVIDUS,SGIN_PM from MEMBRE group by SGIN_INDIVIDUS,SGIN_PM having count(*) > 1};
my $sth = $dbh->prepare( $sql );

$sth->execute();

# retrieve the response
my $data;
$data= $sth->fetchall_arrayref();

my $rows;
$rows = $sth->rows();

my $i;
for $i ( 0 .. ($rows-1) ){
    print "Traitement ligne : $data->[$i][0] $data->[$i][1] $data->[$i][2] $data->[$i][3] $data->[$i][4] $data->[$i][5] $data->[$i][6] \n";

	my $sql2 = "select IKEY from MEMBRE where SGIN_INDIVIDUS = '".$data->[$i][1]."' and SGIN_PM = '".$data->[$i][2]."'";

	my $sth2 = $dbh->prepare( $sql2 );
    $sth2->execute();

    my $data2;
    $data2 = $sth2->fetchall_arrayref();
#	print "Taille de data2:$#{$data2}";
    my $j;
    for $j (0 .. ($#{$data2})){
		if ($j == 0){
			print"IKEY $data2->[$j][0] Do nothing\n";
		}else{
			print"IKEY $data2->[$j][0] erase\n";

			my $sql3 = "begin delete_cascade_no_comment('MEMBRE','IKEY = ''$data2->[$j][0]''');end;";
			my $sth3 = $dbh->prepare( $sql3 );

			print "SQL : $sql3";
			$sth3->execute();
		}
    }
}

#$dbh->commit();
$sth->finish();
$dbh->disconnect();

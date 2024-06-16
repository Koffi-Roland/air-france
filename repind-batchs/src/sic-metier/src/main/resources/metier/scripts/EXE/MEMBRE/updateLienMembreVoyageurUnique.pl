#!/tech/oracle/client/12102/perl/bin/perl -w
use lib ;

use strict;
use DBI;
use Getopt::Long;

DBI->trace(0);

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

my $sql = qq{select count(*), M.SGIN_INDIVIDUS from MEMBRE M, PERS_MORALE P where M.SGIN_PM=P.SGIN AND M.SCLIENT='O' and P.STYPE!='A' group by M.SGIN_INDIVIDUS having count(*) > 1};
my $sth = $dbh->prepare( $sql );

$sth->execute();

# retrieve the response
my $data;
$data= $sth->fetchall_arrayref();

my $rows;
$rows = $sth->rows();

my $i;
for $i ( 0 .. ($rows-1) )
  {
        print "Traitement ligne : $i $data->[$i][1] \n";

	my $sql2 = qq{select M.IKEY from MEMBRE M, PERS_MORALE P where M.SGIN_PM=P.SGIN AND M.SGIN_INDIVIDUS = '$data->[$i][1]' and M.SCLIENT = 'O' and P.STYPE!='A' order by M.ddate_modification desc};


	my $sth2 = $dbh->prepare( $sql2 );
    $sth2->execute();

    my $data2;
    $data2 = $sth2->fetchall_arrayref();
    my $j;
    for $j (0 .. ($#{$data2}))
      {
        #print"IKEY $data2->[$j][0]\n";
	if ($j == 0)
	  {
	    print"IKEY $data2->[$j][0] Do nothing\n";
	  }
	else
	  {
	    print"IKEY $data2->[$j][0] Update Traveller Flag to No\n";
	    my $sql3 = "update membre set sclient='N', ddate_modification=sysdate, ssignature_modification='PURGE LIEN' where ikey='".$data2->[$j][0]."'";
            my $sth3 = $dbh->prepare( $sql3 );
            $sth3->execute();
            
            print"IKEY $data2->[$j][0] Update Date fermeture\n";
            my $sql4 = "update fonction set ddate_fin_validite=sysdate where ikey_membre='".$data2->[$j][0]."' and sfonction='SAL'";
            my $sth4 = $dbh->prepare( $sql4 );
            $sth4->execute();

	    # IM00921836 - add ddate_fin_validite=sysdate 
	    # in order to correct the problem for creating new contact on old existing contacts
	    # IM00952811 - only if the member is not contact & client
	    my $sql5 = "update membre set ddate_fin_validite=sysdate, ddate_modification=sysdate, ssignature_modification='PURGE LIEN' where sclient='N' and scontact='N' and scontact_af='N' and ddate_fin_validite is null and ikey='".$data2->[$j][0]."'";
            my $sth5 = $dbh->prepare( $sql5 );
            $sth5->execute();

          }
      }
}

#$dbh->commit();
$sth->finish();
$dbh->disconnect();

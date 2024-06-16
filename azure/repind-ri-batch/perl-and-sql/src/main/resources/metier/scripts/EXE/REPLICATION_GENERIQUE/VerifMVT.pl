#!/tech/oracle/client/12102/perl/bin/perl -w
use lib ;

use strict;
use Net::SMTP;

my $BASE_DATA_DIR ="/app/SIC/data";
my $BASE_EXE_DIR ="/app/SIC/prod/bin/scripts/EXE";
my $REP_RG = $BASE_EXE_DIR . "/REPLICATION_GENERIQUE";
my $REP_DATA = $BASE_DATA_DIR . "/REPLICATION_GENERIQUE";

my $subject = "Replication OK\n";

# 1 => on envoie le mail, 0 => on affiche tout sur la sortie standard (stdout)
my $mailResult = 1;

# Redirection de la sortie standard vers un fichier de log
my $date = `date +%Y%m%d`; chomp $date;
$mailResult && open(STDOUT, ">/tmp/verifMvt.$date.txt");
printf("subject: Compte-rendu du test de la replication generique des mouvements\n\n");

my $path = $REP_DATA . "/MVT";

# Recherche des deux derniers repertoires crees
my $cmd = "ls -r $path | grep -v \"OK.txt\" | head -n 2";
my @fileList = split('\n', `$cmd`);
my $lastDateDir = $fileList[0];
my $firstDateDir = $fileList[1];


# Verification que le repertoire n'a pas deja ete envoye
verifLastDate();

# On verifie que le dernier repertoire n'est pas vide
verifDirNonVide($lastDateDir);

# On verifie que l'avant-dernier repertoire n'est pas vide non plus
verifDirNonVide($firstDateDir);

# Recuperation des dates de debut et fin du dernier repertoire
(my $beginDateFile1, my $endDateFile1) = getDatesFromFile($firstDateDir);

# Recuperation des dates de debut et fin de l'avant dernier repertoire
(my $beginDateFile2, my $endDateFile2) = getDatesFromFile($lastDateDir);


# Affichage du resultat
printf("$beginDateFile1 _ $endDateFile1 _ $beginDateFile2 _ $endDateFile2\n");


# Comparaison de la date de debut du dernier repertoire avec la date
# de fin de l'avant-dernier afin de verifier la continuite des donnees
if ($endDateFile1 eq $beginDateFile2) {
  printf("\n");
  printf("----------------------------------------------\n");
  printf("OK => Il y a continuit des donnes rcupres\n");

  # Creation du fichier OK.txt pour confirmer la validite des donnees
  printf("\n\n Creation du fichier $path/$lastDateDir/OK.txt\n");
  `touch $path/$lastDateDir/OK.txt`;

  # Lancement de la copie des fichiers par ftp
  printf("\n\n Transfert des fichiers par ftp\n");

  $cmd = "$BASE_EXE_DIR/TransfertFTP.sh XLW01TLSOFV Anonymous null put bin $path/$lastDateDir OK.txt \"/msg\" OK.txt";
  printf("Commande : $cmd\n");
  `$cmd`;

  $cmd = "$REP_RG/TRANSFERT_FIXE/transf_STAR.sh ${path}/${lastDateDir}";
  printf("Commande : $cmd\n");
  `$cmd`;

  $cmd = "cd $REP_RG/TRANSFERT_FIXE";
  printf("Commande : $cmd\n");
  `$cmd`;

  $cmd = "$REP_RG/TRANSFERT_FIXE/transf_email_fblue.sh ${path}/${lastDateDir}";
  printf("Commande : $cmd\n");
  `$cmd`;

  $cmd = "cd $REP_RG";
  printf("Commande : $cmd\n");
  `$cmd`;

  $cmd = "$REP_RG/bipbip_mvt_dwh.sh";
  printf("Commande : $cmd\n");
  `$cmd`;

} else {
  printf("\n");
  printf("----------------------------------------------------\n");
  printf("KO => Il n'y a pas continuit des donnes rcupres\n");
  $subject="KO Replication KO\n";
}

# Ecriture du fichier de controle des signatures (MD5)
$cmd = "(cd $path/$lastDateDir ; $REP_RG/md5sum * | tee md5sum.txt)";
`$cmd`;

# On arrete la redirection de l'entree standard
$mailResult && close (STDOUT);

# Et on envoie le mail
$mailResult && sendMail();


##################################################
# sub : sendMail                                 #
# entree : -                                     #
# sortie : envoi un mail contenu dans un fichier #
##################################################
sub sendMail() {
  my $hostname = "mailsmtp.airfrance.fr";
  my $smtp = Net::SMTP->new($hostname);

  if (defined $smtp) {
    $smtp->mail($ENV{USER});
    $smtp->to('ldif_reef_tma@airfrance.fr');
    $smtp->data();
    $smtp->datasend("from: SIC\@SUNTP\n");
    $smtp->datasend("subject: $subject\n\n\n");
    open (FIC, "</tmp/verifMvt.$date.txt");

    while (<FIC>) {
      $smtp->datasend($_);
    }

    $smtp->dataend();
    $smtp->quit();

  } else {
    print STDERR "VerifMVT [Envoi du mail]: Impossible de se connecter sur le host $hostname\n";
    exit 0;
  }
}


sub verifLastDate() {
  my $nbFichiers = `ls $path/MVT-${date}* | grep OK.txt | wc -l`;

  if ($nbFichiers == 1) {
    printf("La replication s'est mal passee. Dernier repertoire correct : $path/MVT-${date}????\n");
    $mailResult && close(STDOUT);
    $subject="KO Replication KO\n";
    $mailResult && sendMail();
    exit 0;
  }
}


sub verifDirNonVide() {
  my ($rep) = @_;

  my $nbFichiers = `ls $path/$rep | grep -v OK.txt | grep -v md5sum.txt | wc -l`;

  if ($nbFichiers < 1) {
    printf("Le rpertoire $path/$rep est vide !\n");
    $mailResult && close(STDOUT);
    $subject="KO Replication KO\n";
    $mailResult && sendMail();
    exit 0;
  }
}


sub getDatesFromFile() {
  my ($rep) = @_;

  my $cmd;
  my $fichier = `ls $path/$rep | grep -v OK.txt | grep -v md5sum.txt | head -n 1`;

  chomp $fichier;
  $fichier = "$path/$rep/$fichier";

  printf("Rcupration des infos  partir du fichier $fichier\n");
  $_ = $fichier;

  if (/.gz$/) {
    $cmd = "gzip -d -c $fichier | head -n 3 | egrep \"^0020\" | cut -d':' -f5,9"
  } else {
    $cmd = "cat $fichier | head -n 3 | egrep \"^0020\" | cut -d':' -f5,9"
  }

  my $result = `$cmd`;
  chomp $result;
  (my $dateDeb, my $dateFin) = split(':', $result);

  my $msg = "";
  $msg = "Rsultat avant suppression des minutes => ";
  $msg .=  "Deb: " . $dateDeb . " - Fin: " . $dateFin . "\n";

  printf($msg);

  # Format des dates avant modification YYYYMMDDHHMI
  # Format des dates apres modification YYYYMMDDHH
  $dateDeb = substr($dateDeb, 0, 10);
  $dateFin = substr($dateFin, 0, 10);

  $msg = "Rsultat aprˆs suppression des minutes => ";
  $msg .=  "Deb: " . $dateDeb . " - Fin: " . $dateFin . "\n";

  printf($msg);


  return ($dateDeb, $dateFin);
}

#!/tech/oracle/client/12102/perl/bin/perl -w
use lib ;

use FileHandle;
use IO::File;

# my($file_wsp, $outputFilename) = $ARGV;
  my $file_wsp = $ARGV[0];
  print "\$ARGV[0] (1er argument): $file_wsp\n";
  my $outputFilename = $ARGV[1];
  print "\$ARGV[1] (2eme argument): $outputFilename\n";
# my $file_wsp="./MVT-200508290100.0230.txt.fixe";
# my $outputFilename="MVT-200508290100.0230.txt.norc";

# Appel du main et renvoi du code de retour de la fonction
exit &main;

##############################################################################
#
# Main
#
##############################################################################
sub main {
    printf("Lecture du fichier gènèrè par utl_file\n");
    open (FILE_WSP, "$file_wsp");
    my $ligne;
    my $resultFile = new IO::File ">" . $outputFilename; # fichier de sortie
    while (<FILE_WSP>) {
        $ligne = $_;
        $ligne =~ s/([\012])//g; # Suppression des caractàres de fin de ligne
        print $resultFile $ligne;
    }
    printf("Fin gènèration fichier filtrè\n");

}


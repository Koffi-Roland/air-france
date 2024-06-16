package phoneNumberReprise;
##########################################################
# Gestion des fichiers de reprise des batch phone number.
##########################################################

use lib;
use strict; 
use DBI;
use IO::File;

#Variables globales
our $file		= "";
our $lastAin	= "";
our $debug		= 0;

#Constantes
my $RECOVER_FILE_NAME="recover.txt";

# variable ERROR.
my $ERROR_NO_FILE 		= "Recover file is missing.\n";
my $ERROR_AIN_NOT_FOUND = "Ain not found in recover file.\n";

# variable DEBUG
my $DEBUG_GENERATE 	= "Generate recover file";
my $DEBUG_GET 		= "Get recover file";
my $DEBUG_DELETE 	= "Delete recover file";


#########################################################
# Preparer la reprise de donnee.
# path  : chemin ou positionner le fichier de reprise.
# debug : activation mode debug (0 ou 1).
sub preparer($)
{
	#recuperation du chemin du fichier reprise.
	$file 	= shift;
	$file 	.= $RECOVER_FILE_NAME;
	$debug 	= shift;

}

########################################################
# Recuperer le fichier de reprise. 
# ain : identifiant permettant de recuperer la ligne ou le traitement reprend.
sub recuperer($)
{
	my $ain = shift;
	my $data;

		my $ainFound = 0;
		my $i = 0;
		my $indicatif;
		my $codeRegion;
		my $numero;
		my $codePays;
		my $sgin;
		my $sain;

		if($debug == 1 && -e "$file")
		{
			print $DEBUG_GET." : $file\n";
		}
		open( REPRISE_LECTURE, "<$file" ) or die $ERROR_NO_FILE;

		while (my $ligne = <REPRISE_LECTURE>){
		
		($indicatif,$codeRegion,$numero,$codePays,$sgin, $sain) = split /;/,$ligne;
			# Se positionner sur l'ain
			if($ainFound == 1 || $ain == $sain){

				$data->[$i][0] = $indicatif;
				$data->[$i][1] = $codeRegion;
				$data->[$i][2] = $numero;
				$data->[$i][3] = $codePays;
				$data->[$i][4] = $sgin;
				$data->[$i][5] = $sain;

				$ainFound=1;
				$i++;
			}
		}	

		close(REPRISE_LECTURE);
		
		if($ainFound == 0){
			print $ERROR_AIN_NOT_FOUND;		
			exit 1;
		}

	return $data;
}

#################################
# Supprimer le fichier de reprise
sub supprimer()
{
	if (-e "$file"){
		if($debug == 1)
		{
			print $DEBUG_DELETE."\n";
		}
		unlink "$file";
	}
}

#################################
# Generer le fichier de reprise
sub generer($)
{
	my $data = shift; 
	my $dataSize = @$data; 
	# Creation du fichier de reprise.	
	open( REPRISE_ECRITURE, ">$file" );

	# Ecriture des donnees.	
    my $i;  
    for $i ( 0 .. ($dataSize - 1) )
    {
        if(defined $data->[$i][$phoneNumber::indicatifIndex]){
            print REPRISE_ECRITURE "$data->[$i][$phoneNumber::indicatifIndex]";
        }
        print REPRISE_ECRITURE $phoneNumber::separator;      
        if(defined $data->[$i][$phoneNumber::codeRegionIndex]){
            print REPRISE_ECRITURE "$data->[$i][$phoneNumber::codeRegionIndex]";
        }
        print REPRISE_ECRITURE $phoneNumber::separator;      
        if(defined $data->[$i][$phoneNumber::numeroIndex]){
            print REPRISE_ECRITURE "$data->[$i][$phoneNumber::numeroIndex]";
        }
        print REPRISE_ECRITURE $phoneNumber::separator;      
        if(defined $data->[$i][$phoneNumber::scodePaysIndex]){
        print REPRISE_ECRITURE "$data->[$i][$phoneNumber::scodePaysIndex]";
        }
        print REPRISE_ECRITURE $phoneNumber::separator;      
        if(defined $data->[$i][$phoneNumber::sginIndex]){
            print REPRISE_ECRITURE "$data->[$i][$phoneNumber::sginIndex]";
        }
        print REPRISE_ECRITURE $phoneNumber::separator;
        if(defined $data->[$i][$phoneNumber::sainIndex]){
            print REPRISE_ECRITURE "$data->[$i][$phoneNumber::sainIndex]";
        }
        print REPRISE_ECRITURE "\n";
    }
	
	close(REPRISE_ECRITURE);		

	if($debug == 1 && -e "$file")
	{
		print $DEBUG_GENERATE." : $file\n";
	}
}

#fin de package
1;

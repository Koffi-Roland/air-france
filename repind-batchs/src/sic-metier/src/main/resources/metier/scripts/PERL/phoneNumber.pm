#!/tech/oracle/client/12102/perl/bin/perl -w

###############################################
# Constantes des scripts phone number. 
###############################################
package phoneNumber;
use POSIX qw/strftime/;
# Chemin d'acces au log.
our $logPathPhoneNumber=$ENV{BASE_DATA_DIR}."/NEW_PHONE_NUMBER/";
our $dateDuJour 	= strftime("%Y%m%d%H%M%S", localtime());
our $dateFormattee 	= strftime("%d/%m/%Y - %H:%M:%S", localtime());
our $nbTelecoms = 0;
 
# Ordre des champs dans la requete
our $indicatifIndex  = 0;
our $codeRegionIndex = 1;
our $numeroIndex     = 2;    
our $scodePaysIndex  = 3;
our $sginIndex       = 4;
our $sainIndex       = 5;

# Signature Batch
our $SIGNATURE_CLEAN	 = "StockCleaning";
our $SIGNATURE_INV_CLEAN = "StockInvNoDigit";
our $SIGNATURE_INVALID	 = "StockInvalid";
our $SIGNATURE_SIMPLIF	 = "StockSimplif";
our $SIGNATURE_NORMINVAL = "InvalBadStockRi";

# Caractere separateur pour les fichiers a plat.
our $separator       = ";";
1;

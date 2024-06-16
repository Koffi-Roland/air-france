package phoneNumberArgs;
###########################################
# Gestion des arguments des batch phone number.

# Flag possibles pour argument.
our $NO 		= 0; # argument non utilise.
our $OPTIONNAL 	= 1; # argument optionnel.
our $MANDATORY 	= 2; # argument obligatoire.

# Arguments autorises pour le Batch (par defaut : absent) 
our $isHelp			= $NO;
our $isMarket   	= $NO;
our $isMyDebug  	= $NO;
our $isCommit		= $NO;
our $isTest			= $NO;
our $isStat			= $NO;
our $isContrat		= $NO;
our $isTerminal		= $NO;
our $isReprise		= $NO;
our $isRollback		= $NO;
our $isDaily		= $NO;
our $isStartDate	= $NO;
our $isEndDate		= $NO;
our $isMaxTelecom	= $NO;
our $isResidu		= $NO;

# variable booleenne utilise par les batchs. 
our $help		= 0;
our $market		;
our $myDebug 	= 0;
our $commit 	= 0;
our $test	 	= 0;
our $error	 	= 0;
our $contrat 	;
our $terminal 	;
our $stat	 	= 0;
our $reprise 	= 0;
our $rollback	= 0;
our $daily		= 0;
our $startDate;
our $endDate;
our $maxTelecom = 750000;
our $residu = 0;

# nom des arguments
our $helpArgs 		= "-h";
our $marketArgs		= "-m";
our $myDebugArgs	= "-d";
our $commitArgs		= "-C";
our $testArgs		= "-test";
our $statArgs		= "-s";
our $contratArgs	= "-contrat";
our $terminalArgs	= "-terminal";
our $repriseArgs	= "-reprise";
our $rollbackArgs	= "-rollback";
our $dailyArgs		= "-daily";
our $startDateArgs	= "-startDate";
our $endDateArgs	= "-endDate";
our $maxTelecomArgs	= "-maxTelecom";
our $residuArgs		= "-residu";

# texte pour le message d'aide
our $helpText		= "$helpArgs 		: help";
our $marketText		= "$marketArgs 		: filter by marchee 2 letters";
our $myDebugText	= "$myDebugArgs		: mode debug";
our $commitText		= "$commitArgs 		: mode commit";
our $testText		= "$testArgs 		: mode test";
our $statText		= "$statArgs		: mode statistique";
our $contratText	= "$contratArgs		: filter by contract 2 letters (FP, MA,etc.) ";
our $terminalText	= "$terminalArgs 	: filter by terminal (M,T,F, etc.)";
our $repriseText	= "$repriseArgs 	: mode reprise precise AIN (only number)";
our $rollbackText	= "$rollbackArgs    : mode rollback precise file to rollback";
our $dailyText		= "$dailyArgs 		: mode daily";
our $startDateText	= "$startDateArgs 	: start Date mode daily";
our $endDateText	= "$endDateArgs 	: end Date mode daily";
our $maxTelecomText	= "$maxTelecomArgs 	: maximum Telecom Number (Default : 750000)";
our $residuText		= "$residuArgs 		: process residual cases ";

####################################################
# Afficher L'aide selon les arguments autorises.
sub displayHelp() {
	my $programName = $0;
	$programName =~ s/^(.*\/)?([^\/]*)$/$2/;
  	print( "usage: $programName " );
	&printArgs($isHelp ,$helpArgs);
	&printArgs($isCommit ,$commitArgs );
	&printArgs($isMyDebug  ,$myDebugArgs );
	&printArgs($isMarket  ,$marketArgs );
	&printArgs($isTest  ,$testArgs );
	&printArgs($isStat  ,$statArgs );
	&printArgs($isContrat  ,$contratArgs );
	&printArgs($isTerminal  ,$terminalArgs );
	&printArgs($isDaily  ,$dailyArgs );
	&printArgs($isStartDate  ,$startDateArgs );
	&printArgs($isEndDate  ,$endDateArgs );
	&printArgs($isMaxTelecom ,$maxTelecomArgs );
	&printArgs($isResidu ,$residuArgs );
  	
	print( "\nwhere options can be:\n" );

	&printTextArgs($isHelp ,$helpArgs);
	&printTextArgs($isCommit ,$commitArgs );
	&printTextArgs($isMyDebug  ,$myDebugArgs );
	&printTextArgs($isMarket  ,$marketArgs );
	&printTextArgs($isTest  ,$testArgs );
	&printTextArgs($isStat  ,$statArgs );
	&printTextArgs($isContrat  ,$contratArgs );
	&printTextArgs($isTerminal  ,$terminalArgs );
	&printTextArgs($isDaily  ,$dailyArgs );
	&printTextArgs($isStartDate  ,$startDateArgs );
	&printTextArgs($isEndDate  ,$endDateArgs );
	&printTextArgs($isMaxTelecom ,$maxTelecomArgs );
	&printTextArgs($isResidu ,$residuArgs );
	
}

##################################
# Afficher le nom de l'argument
sub printArgs($)
{
	my $isArgs = shift;
	my $argsName = shift;

	if($isArgs != $NO)
	{
		print "[$argsName] ";
	}

}

##################################
# Afficher le texte de l'argument
sub printTextArgs($)
{

	my $isArgs = shift;
	my $argsText = shift;

	if($isArgs != $NO)
	{
		print "$argsText\n";
	}
}

##################################
#Gerer les arguments generiquement
sub gererArgs($)
{
	my @argumentsTab = @_;
 	my $argument;
	while( $argument = shift @argumentsTab ){
	    if( $argument eq "$helpArgs" ){
	        displayHelp();
	        exit( 0 );
	    }
	    elsif( $argument eq "$myDebugArgs" ){
	        $myDebug = 1;
	    }
	    elsif( $argument eq "$dailyArgs" ){
	        $daily = 1;
	    }
	    elsif( $argument eq "$testArgs" ){
	        $test 	= 1;
	    }
	    elsif( $argument eq "$commitArgs" ){
	        $commit = 1;
	    }
	    elsif( $argument eq "$statArgs" ){
	        $stat = 1;
	    }
	    elsif( $argument eq "$residuArgs" ){
	        $residu = 1;
	    }
	    elsif( $argument eq "$maxTelecomArgs"){  
	        
			$argument = shift @argumentsTab;
	        if(! defined $argument || $argument eq "" ){			
	    	    print("error : $maxTelecomArgs empty or bad format\n");
	        	displayHelp();
	        	exit(1);
	    	}
			else{
				$maxTelecom = $argument;
			}
	  	}
	    elsif( $argument eq "$marketArgs"){  
	        
			$argument = shift @argumentsTab;
	        if(! defined $argument || $argument eq "" ){			
	    	    print("error : $marketArgs empty or bad format\n");
	        	displayHelp();
	        	exit(1);
	    	}
			else{
				$market = $argument;
			}
	  	}
	    elsif( $argument eq "$startDateArgs"){  
	        
			$argument = shift @argumentsTab;
	        if(! defined $argument || $argument eq ""){			
	    	    print("error : $startDateArgs empty or bad format\n");
	        	displayHelp();
	        	exit(1);
	    	}
			else{
				$startDate = $argument;
			}
	  	}
	    elsif( $argument eq "$endDateArgs"){  
	        
			$argument = shift @argumentsTab;
	        if(! defined $argument || $argument eq ""){ 		
	    	    print("error : $endDateArgs empty or bad format\n");
	        	displayHelp();
	        	exit(1);
	    	}
			else{
				$endDate = $argument;
			}
	  	}
	    elsif( $argument eq "$contratArgs"){  
	        
			$argument = shift @argumentsTab;
			# 2 caracteres lettre ou chiffre autorises
	        if(! defined $argument || $argument eq "" || !( $argument =~ /^[A-Z0-9]{2}$/) ){
	    	    print("error : $contratArgs empty or bad format\n");
	        	displayHelp();
	        	exit(1);
	    	}
			else{
				$contrat = $argument;
			}
	  	}
	    elsif( $argument eq "$terminalArgs"){  
	        
			$argument = shift @argumentsTab;
			# 1 caractere lettre autorise
	        if(! defined $argument || $argument eq "" || !( $argument =~ /^[A-Z]{1}$/) ){
	    	    print("error : $terminalArgs empty or bad format\n");
	        	displayHelp();
	        	exit(1);
	    	}
			else{
				$terminal = $argument;
			}
	  	}
	    elsif( $argument eq "$repriseArgs"){  
	        
			$argument = shift @argumentsTab;
			# 2 caracteres lettre ou chiffre autorises
	        if(! defined $argument || $argument eq "" || !( $argument =~ /^[0-9 ]*$/) ){
	    	    print("error : $repriseArgs empty or bad format\n");
	        	displayHelp();
	        	exit(1);
	    	}
			else{
				$reprise = $argument;
			}
	  	}
	    elsif( $argument eq "$rollbackArgs"){  
	        
			$argument = shift @argumentsTab;
			# 2 caracteres lettre ou chiffre autorises
	        if(!defined $argument || ! (-e $argument) ){
	    	    print("error : $rollbackArgs file doesn't exist.\n");
	        	displayHelp();
	        	exit(1);
	    	}
			else{
				$rollback = $argument;
			}

	  	}
		else{
			
	    	    print("error : $argument doesn't exist.\n");
	        	displayHelp();
	        	exit(1);
		}
	}

	if( ($isTest 		== $MANDATORY && $test ==0) 	||
		($isResidu 		== $MANDATORY && $residu == 0) 	||
		($isHelp 		== $MANDATORY && $help == 0) 	||
		($isMyDebug 	== $MANDATORY && $myDebug == 0)	||
    	($isCommit 		== $MANDATORY && $commit == 0)	||
		($isTerminal 	== $MANDATORY && ((! defined $terminal) or (!($terminal=~ /^[A-Z0-9]{1}$/))))	||
		($isContrat 	== $MANDATORY && ((! defined $contrat ) or (!($contrat =~ /^[A-Z0-9]{2}$/))))	||
		($isMarket 		== $MANDATORY && ((! defined $market  )))
	)
	{
		displayHelpAndExit();
	} 				

}

# Afficher l'aide et quitter.
sub displayHelpAndExit(){

		displayHelp();
		exit(1);
}

#fin de package
1;

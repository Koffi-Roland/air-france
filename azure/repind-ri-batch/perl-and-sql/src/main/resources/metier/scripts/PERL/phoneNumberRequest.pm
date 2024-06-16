#!/tech/oracle/client/12102/perl/bin/perl -w
use phoneNumber;
package phoneNumberRequest;


##################################################
# Requete  
# market 	: code pays considere (ES, FR, etc.)
# contrat 	: contrat considere (FB, MA, etc.)
# terminal	: terminal considere (M, T, F, etc.)
# isInvalid	: mode invalid. 
# daily	: mode daily. 
# startDate	: start date. 
# endDate	: end date. 
# maxTelecom: nombre maximum de telecom traite. 
sub getRequestDate ($)
{

	# recuperation parametres 
	my $market 		= shift;
	my $contrat 	= shift;
	my $terminal 	= shift;
	my $isInvalid 	= shift;
	my $daily 		= shift;

	my $startDate 	= shift;
	my $endDate 	= shift;
	my $maxTelecom  = shift;
	my $residu  	= shift;
	my $request;

	if(defined $residu && $residu == 1)
	{
		
    	$request = "SELECT t.sindicatif, t.scode_region, t.snumero, '', t.sgin, t.sain ";
		$request .= "FROM TELECOMS t ";
    	$request .= "WHERE  t.snumero is not null ";
    	$request .= "and t.sgin is not null and t.sgin_pm is null ";
    	$request .= "and t.sstatut_medium = 'V' ";
		
		if($daily != 1){
			$request .= " and rownum < $maxTelecom and t.isnormalized is null"; 
		}
		else
		{
			$request .= &addDateDaily($startDate, $endDate); 	
		}
	}
	else 
	{
		#Contrat FlyingBlue.
		if(defined $contrat && $contrat eq "FP")
		{
    		$request = "SELECT t.sindicatif, t.scode_region, t.snumero, ad.scode_pays, t.sgin, t.sain ";
			$request .= bodyRequest($market, $contrat, $terminal, $isInvalid, 0, $daily, $startDate, $endDate);
			if($daily != 1){
				$request .= " and rownum < $maxTelecom and t.isnormalized is null"; 
			}
		}
		#Contrat MyAccount pur. 
		elsif (defined $contrat && $contrat eq "MA")
		{
			$request = "SELECT t.sindicatif, t.scode_region, t.snumero, ada.enrolement_point_of_sell, t.sgin, t.sain ";
			$request .= bodyRequest($market, $contrat, $terminal, $isInvalid, 0, $daily, $startDate, $endDate);
			if($daily != 1){
				$request .= " and rownum < $maxTelecom and t.isnormalized is null"; 
			}
		}
		#Contrat autres.
		else {
			$request .= "SELECT * FROM ";
			$request .= "(";
			$request .= "(";
			$request .= "SELECT t.sindicatif, t.scode_region, t.snumero, ad.scode_pays, t.sgin, t.sain ";
			$request .= bodyRequest($market, undef, $terminal, $isInvalid, 0, $daily, $startDate, $endDate);
			if($daily != 1){
				$request .= " and t.isnormalized is null and rownum < $maxTelecom"; 
			}
			
			$request .= ")";
			$request .= " UNION ";
			$request .= "(";
			$request .= "SELECT t.sindicatif, t.scode_region, t.snumero, ad.scode_pays, t.sgin, t.sain ";
			$request .= bodyRequest($market, undef, $terminal, $isInvalid, 1, $daily, $startDate, $endDate);
			if($daily != 1){
				$request .= " and t.isnormalized is null and rownum < $maxTelecom"; 
			}
			$request .= ")";
			$request .= ")";
			
			if($daily != 1){
				$request .= " where rownum < $maxTelecom "; 
			}
	
		}
	}
	return $request;
}

sub addDateDaily($)
{
    my $startDate = shift;
    my $endDate = shift;
    my $result = "";

    if (defined($startDate))
    {
        $result .= " AND ((t.DDATE_MODIFICATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS')) OR (t.DDATE_MODIFICATION IS NULL AND t.DDATE_CREATION >= TO_DATE('$startDate', 'YYYYMMDDHH24MISS'))) ";
    }

    if (defined($endDate))
    {
        $result .= " AND ((t.DDATE_MODIFICATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')) OR (t.DDATE_MODIFICATION IS NULL AND t.DDATE_CREATION < TO_DATE('$endDate', 'YYYYMMDDHH24MISS')))";
    }

    return $result;
}

##################################################
# Requete pour les batch phone number par marche contrat et terminal.
# market 	: code pays considere (ES, FR, etc.)
# contrat 	: contrat considere (FB, MA, etc.)
# terminal	: terminal considere (M, T, F, etc.)
# isInvalid	: mode invalid. 
# daily	: mode daily. 
sub getRequest ($)
{
	# recuperation parametres 
	my $market 		= shift;
	my $contrat 	= shift;
	my $terminal 	= shift;
	my $isInvalid 	= shift;
	my $daily 	= shift;
	my $maxTelecom 	= shift;
	my $residu	    = shift;

	&getRequestDate($market, $contrat, $terminal, $isInvalid, $daily, undef, undef, $maxTelecom, $residu);
}

##################################################
# Requete sur tous les contrats et marche par date de modification. 
sub getRequestDaily($)
{
	# recuperation parametres 
	my $startDate 	= shift;
	my $endDate 	= shift;
	
	my $requestDaily = "( "; 
	$requestDaily .= &getRequestDate(undef, undef, undef, 0, 1, $startDate, $endDate); 	
	$requestDaily .= " )";
	
	return $requestDaily;
}

##################################################
# Requete sur tous les contrats et marche par date de modification. 
sub getRequestNormInval($)
{
	# recuperation parametres 
	my $startDate 	= shift;
	my $endDate 	= shift;
	
	my $requestDaily = "SELECT t.sindicatif, t.scode_region, t.snumero, '', t.sgin, t.sain ";
	
	$requestDaily .= "FROM SIC2.TELECOMS t ";
	$requestDaily .= "WHERE sgin is not null and sgin_pm is null ";
	$requestDaily .= "AND t.isnormalized = 'N' AND t.sstatut_medium='V' and t.snorm_inter_phone_number is null ";
	$requestDaily .= &addDateDaily($startDate, $endDate); 	
	
	return $requestDaily;
}

##################################################
# Partie SELECT de la requete SIMPLIFICATION. 
# daily   : mode daily
# startDate : date debut du daily

# endDate   : date fin du daily
sub getRequestSimplification ($)
{
    my $daily       = shift;
    my $startDate   = shift;
    my $endDate     = shift;
    
	my $request;

        if(defined $daily) 
        {
            $request .= "SELECT t3.sain, t3.sgin, t3.sterminal, t3.scode_medium, to_char (t3.ddate_modification, 'YYYYMMDDHHMISS'), t3.sstatut_medium, t3.ssignature_modification ";
            $request .= "FROM SIC2.telecoms t3 ";
            $request .= "where t3.sstatut_medium      IN ('V','I') ";
            $request .= "AND t3.scode_medium IN ('D','P') ";
            $request .= "AND t3.sterminal      IN ('M','T') ";
            $request .= "AND t3.sgin           IS NOT NULL ";
            $request .= " AND t3.sgin_pm        IS NULL ";
            $request .= " AND t3.sgin in (SELECT distinct sgin ";
            $request .= "FROM SIC2.TELECOMS t ";
            $request .= "WHERE t.sstatut_medium      IN ('V','I') ";
            $request .= "AND t.scode_medium IN ('D','P') ";
            $request .= "AND t.sterminal      IN ('M','T') ";
            $request .= "AND t.sgin           IS NOT NULL ";
            $request .= "AND t.sgin_pm        IS NULL ";
            $request .= &addDateDaily($startDate, $endDate);    
            $request .= " )";
            $request .= " ORDER BY t3.sgin ASC, t3.scode_medium ASC, t3.sterminal ASC, t3.sstatut_medium DESC, t3.ddate_modification DESC, t3.sain DESC";

        }
        else
        {
            $request .= "SELECT t3.sain, t3.sgin, t3.sterminal, t3.scode_medium, to_char (t3.ddate_modification, 'YYYYMMDDHHMISS'), t3.sstatut_medium, t3.ssignature_modification ";
            $request .= "FROM SIC2.telecoms t3 ";
            $request .= "where t3.sain in ( ";
            $request .= "select t2.sain from SIC2.telecoms t2 ";
            $request .= "where t2.scode_medium in ('D','P') ";
            $request .= "and t2.sstatut_medium IN ('V','I') ";
            $request .= "and t2.sterminal in ('M','T') ";
            $request .= "and t2.sgin is not null ";
            $request .= "and t2.sgin_pm is null ";
            $request .= "and 0 < (SELECT count(*) from SIC2.TELECOMS t ";
            $request .= "   where t2.sain<>t.sain and ";
            $request .= "           t2.sterminal=t.sterminal and ";
            $request .= "           t2.scode_medium=t.scode_medium and ";
            $request .= "           t2.sstatut_medium IN ('V','I') and ";
            $request .= "           t2.sgin = t.sgin ";
            $request .= "           )) ";
            $request .= " ORDER BY t3.sgin ASC, t3.scode_medium ASC, t3.sterminal ASC, t3.sstatut_medium DESC, t3.ddate_modification DESC, t3.sain DESC";
	}
	
	return $request;
}

##################################################
# Construction de la partie FROM et WHERE des request phone Number. 
# market 	: code pays considere (ES, FR, etc.)
# contrat 	: contrat considere (FB, MA, etc.)
# terminal	: terminal considere (M, T, F, etc.)
# isInvalid : 1 == les telecoms invalides sont dans le perimetre. 
# subRequest: requete considere (0 = premiere, 1 = seconde). 
# daily   : mode daily
# startDate : date debut du daily
# endDate   : date fin du daily

sub bodyRequest ($){

	# recuperation parametres 
	my $market 		= shift;
	my $contrat 	= shift;
	my $terminal	= shift;
	my $isInvalid	= shift;
	my $subRequest  = shift;
	my $daily	    = shift;
	my $startDate   = shift;
	my $endDate     = shift;

	my $request;	
	#Contrat FlyingBlue.
	if(defined $contrat && $contrat eq "FP")
	{
    	$request .= "FROM sic2.telecoms t, SIC2.role_contrats rc, sic2.adr_post ad, SIC2.usage_mediums um ";
    	$request .= "WHERE  t.snumero is not null ";
    	$request .= "and t.sgin is not null and t.sgin_pm is null ";
    	$request .= " and t.sstatut_medium = 'V' ";
   
    	$request .= "and t.sgin = rc.sgin ";
    	
		if(defined $terminal){
        	$request .= "and t.sterminal = '$terminal' ";
		}
        $request .= "and rc.stype_contrat = 'FP' ";
    	$request .= "and ad.sgin = rc.sgin ";
    	$request .= "and um.scode_application = 'ISI' ";
    	$request .= "and um.srole1 = 'M' ";
    	$request .= "and ad.sain = um.sain_adr ";
    	$request .= "and ad.scode_medium in ('D','P') ";
    	$request .= "and ( ad.sstatut_medium = 'V' ";
		if($isInvalid){
    		$request .= " or ad.sstatut_medium = 'I' ";
		}
		$request .= ")";
    
		if(defined $daily)
		{
			$request .= &addDateDaily($startDate, $endDate); 	
		}
		elsif(defined $market){
        	$request .= " and ad.scode_pays IN($market)";
    	}
	 }
	#Contrat MyAccount pur. 
	elsif (defined $contrat && $contrat eq 'MA')
	{
		$request .= " FROM sic2.telecoms t, sic2.account_data ada ";
		$request .= " WHERE t.snumero is not null ";
    	$request .= " and t.sgin is not null and t.sgin_pm is null ";
    	$request .= " and ( t.sstatut_medium = 'V' ";
		if($isInvalid == 1){
    		$request .= " or t.sstatut_medium = 'I' ";
		}
		$request .= ")";
		
		if(defined $terminal){
        	$request .= " and t.sterminal = '$terminal' ";
		}

    	$request .= " and t.sgin = ada.sgin ";
    	$request .= " and ada.fb_identifier is null ";
    	$request .= " and ada.account_identifier is not null ";

		if(defined $daily)
		{
			$request .= &addDateDaily($startDate, $endDate); 	
		}
    	elsif(defined $market){
        	$request .= " and ada.enrolement_point_of_sell IN($market)";
    	}
	}
	#Contrat autres.
	else{
		if($subRequest == 0)
		{
			$request .= " FROM sic2.telecoms t, sic2.adr_post ad, SIC2.usage_mediums um ";
			$request .= " WHERE  t.snumero is not null ";
    		$request .= " and (SELECT count(*) FROM sic2.account_data ada WHERE ada.sgin = t.sgin) = 0 ";
    		$request .= " and t.sgin is not null and t.sgin_pm is null ";
    		$request .= " and ( t.sstatut_medium = 'V' ";
			if($isInvalid == 1){
    			$request .= " or t.sstatut_medium = 'I' ";
			}
			$request .= ")";

			if(defined $terminal){
        		$request .= " and t.sterminal = '$terminal' ";
			}

    		$request .= " and ad.sgin = t.sgin ";
    		$request .= " and um.scode_application = 'BDC' ";
    		$request .= " and ad.sain = um.sain_adr ";
    		$request .= " and ad.scode_medium in ('D','P') ";
    		$request .= " and ad.sstatut_medium = 'V' ";

			if(defined $daily)
			{
				$request .= &addDateDaily($startDate, $endDate); 	
			}
    		elsif(defined $market){
        		$request .= " and ad.scode_pays IN($market)";
    		}
		}		
		else
		{
			$request .= " FROM sic2.telecoms t, sic2.adr_post ad ";
			$request .= " WHERE  t.snumero is not null ";
			$request .= " and (SELECT count(1) FROM sic2.account_data ada WHERE ada.sgin = t.sgin) = 0 ";
			$request .= " and t.sgin is not null and t.sgin_pm is null  ";
			$request .= " and ( t.sstatut_medium = 'V' ";
			
			if($isInvalid == 1){
    			$request .= " or  t.sstatut_medium = 'I' ";
			}
			$request .= ")";
			
			if(defined $terminal){
        		$request .= " and t.sterminal = '$terminal' ";
			}
			
			$request .= " and ad.sgin = t.sgin ";
			$request .= " and ad.scode_medium in ('D','P') ";
			$request .= " and ad.sstatut_medium='V' ";

			$request .= " and 0 = (SELECT count(1) FROM SIC2.usage_mediums um ";
			$request .= " WHERE ad.sain = um.sain_adr and um.scode_application<>'BDC') ";
			$request .= " and ad.ddate_modification = ";
			$request .= " (  ";
			$request .= " SELECT MAX(ad2.ddate_modification) ";
			$request .= " FROM  sic2.adr_post ad2 ";
			$request .= " WHERE ad2.sgin = t.sgin ";
			$request .= " and ad2.scode_medium in ('D','P') ";
			$request .= " and ad2.sstatut_medium = 'V' ";

			if(defined $daily)
			{
				$request .= &addDateDaily($startDate, $endDate); 	
			}
    		elsif(defined $market){
        		$request .= " and ad.scode_pays IN($market)";
    		}

			$request .= " and 0 = (SELECT count(1) FROM SIC2.usage_mediums um2 ";
			$request .= " WHERE ad2.sain = um2.sain_adr and um2.scode_application <>'BDC') ";
			$request .= " ) ";
		}
	}
	return $request;
}
1;

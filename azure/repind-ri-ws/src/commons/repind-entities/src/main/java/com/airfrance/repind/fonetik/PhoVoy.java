package com.airfrance.repind.fonetik;

public class PhoVoy {
	public static boolean isVoyelle(char car) {
		boolean result = false;
		if ((car == 'A') || (car == 'E') || (car == 'I') ||
		    (car == 'O') || (car == 'U') || (car == 'Y') ||
		    (car == '!') || (car == '&') || (car == '*') ) {
			result = true;
		}
		return result;		
	}
	
	public static boolean isConsonne(char car) {
		return !isVoyelle(car);
	}
}

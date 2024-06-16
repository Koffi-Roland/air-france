package com.airfrance.batch.common.utils;

import java.util.*;

/**
 * @author T528182
 *
 */
public class PeriodUtils {

    // Constantes du DateUtil
    /**Constante*/
    private static final int C1 = 1;
    /**Constante*/
    private static final int C2 = 2;
    /**Constante*/
    private static final int C3 = 3;
    /**Constante*/
    private static final int C4 = 4;
    /**Constante*/
    private static final int C7 = 7;
    /**Constante*/
    private static final int C8 = 8;
    /**Constante*/
    private static final int C11 = 11;
    /**Constante*/
    private static final int C12 = 12;
    /**Constante*/
    private static final int C14 = 14;
    /**Constante*/
    private static final int C15 = 15;
    /**Constante*/
    private static final int C17 = 17;
    /**Constante*/
    private static final int C19 = 19;
    /**Constante*/
    private static final int C22 = 22;
    /**Constante*/
    private static final int C25 = 25;
    /**Constante*/
    private static final int C30 = 30;
    /**Constante*/
    private static final int C31 = 31;
    /**Constante*/
    private static final int C32 = 32;
    /**Constante*/
    private static final int C39 = 39;
    /**Constante*/
    private static final int C49 = 49;
    /**Constante*/
    private static final int C100 = 100;
    /**Constante*/
    private static final int C144 = 144;
    /**Constante*/
    private static final int C451 = 451;

    /**Constante*/
    private static final int JANVIER = 0;
    /**Constante*/
    private static final int FEVRIER = 1;
    /**Constante*/
    private static final int MARS = 2;
    /**Constante*/
    private static final int AVRIL = 3;
    /**Constante*/
    private static final int MAI = 4;
    /**Constante*/
    private static final int JUIN = 5;
    /**Constante*/
    private static final int JUILLET = 6;
    /**Constante*/
    private static final int AOUT = 7;
    /**Constante*/
    private static final int NOVEMBRE = 10;
    /**Constante*/
    private static final int DECEMBRE = 11;
    
   /**
    *
    * @param year l'ann�e
    * @return retourne les jours f�ri�s d'une ann�e sous forme d'une ArrayList d'objets java.util.Date
    */
   public static ArrayList<Date> getPublicHolidays(final int year) {
       final ArrayList<Date> ph = new ArrayList<Date>();

       //Jours Fixes
       ph.add(new GregorianCalendar(year, JANVIER, C1).getTime()); // jour de l'an
       ph.add(new GregorianCalendar(year, MAI, C1).getTime()); // f�te du travail
       ph.add(new GregorianCalendar(year, MAI, C8).getTime()); // victoire 1945
       ph.add(new GregorianCalendar(year, JUILLET, C14).getTime()); // f�te nationale
       ph.add(new GregorianCalendar(year, AOUT, C15).getTime()); // assomption
       ph.add(new GregorianCalendar(year, NOVEMBRE, C11).getTime()); // armistice 1918
       ph.add(new GregorianCalendar(year, NOVEMBRE, C1).getTime()); // Toussaint
       ph.add(new GregorianCalendar(year, DECEMBRE, C25).getTime()); // no�l

       //Calcul du Jours de P�ques et du Lundi de P�ques
       int m, c, y, s, t, p, q, e, b, d, l, h;
       int moispaques;
       int jourpaques;
       m = (year) % C19;
       c = year / C100;
       y = (year) % C100;
       s = c / C4;
       t = (c) % C4;
       p = (c + C8) / C25;
       q = (c - p + 1) / C3;
       e = (((C19 * m) + c) - s - q + C15) % C30;
       b = y / C4;
       d = y % C4;
       l = ((C32 + (C2 * t) + (C2 * b)) - e - d) % C7;
       h = (m + (C11 * e) + (C22 * l)) / C451;
       moispaques = (((e + l) - (C17 * h) + C144) / C31) - C2;
       jourpaques = (((e + l) - (C17 * h) + C144) % C31) + C2;

       ph.add(new GregorianCalendar(year, moispaques, jourpaques).getTime()); // Dianche de p�ques
       ph.add(new GregorianCalendar(year, moispaques, jourpaques + 1).getTime()); // Lundi de p�ques

       // Calcul du jour de la Pentec�te et du lundi de Pentec�te
       int jourpentecote = C1;
       int moispentecote = FEVRIER;

       if (moispaques == MARS) {
           jourpentecote = C49 - (C31 - jourpaques + C30);
           moispentecote = MAI;
       }

       if ((moispaques == AVRIL) && (jourpaques <= C12)) {
           jourpentecote = C49 - (C30 - jourpaques);
           moispentecote = MAI;
       }

       if ((moispaques == AVRIL) && (jourpaques > C12)) {
           jourpentecote = C49 - (C30 - jourpaques + C31);
           moispentecote = JUIN;
       }

       ph.add(new GregorianCalendar(year, moispentecote, jourpentecote).getTime()); // Dianche de pentec�te
       ph.add(new GregorianCalendar(year, moispentecote, jourpentecote + 1).getTime()); // Lundi de pentec�te

       // Calcul du jour de l'Ascension
       int jourAscension = C1;
       int moisAscension = FEVRIER;

       if ((jourpaques == C22) && (moispaques == MARS)) {
           jourAscension = C30;
           moisAscension = AVRIL;
       }

       if ((jourpaques > C22) && (moispaques == MARS)) {
           jourAscension = C39 - (C31 - jourpaques + C30);
           moisAscension = MAI;
       }

       if ((jourpaques <= C22) && (moispaques == AVRIL)) {
           jourAscension = C39 - (C30 - jourpaques);
           moisAscension = MAI;
       }

       if ((jourpaques > C22) && (moispaques == AVRIL)) {
           jourAscension = C39 - (C30 - jourpaques + C31);
           moisAscension = JUIN;
       }

       ph.add(new GregorianCalendar(year, moisAscension, jourAscension).getTime()); // Ascension

       return ph;
   }

   /**
    *
    * @param day = jour � tester au format Date
    * @return Retourne si la date pass�e en entr�e correspond � un jour feri�
    */
   public static boolean isPublicHoliday(final Date day) {
       if (day != null) {
           
           final GregorianCalendar gc = new GregorianCalendar();
           gc.setTime(day);

           final ArrayList<Date> ph = getPublicHolidays(gc.get(Calendar.YEAR));

           for (final Iterator<Date> iter = ph.iterator(); iter.hasNext();) {
               final Date d = (Date) iter.next();

               if (d.equals(day)) {
                   return true;
               }
           }
       } 
       
       return false;
   }
	   
}

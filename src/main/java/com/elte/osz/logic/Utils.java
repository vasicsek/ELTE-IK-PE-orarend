package com.elte.osz.logic;

import java.sql.Timestamp;
import java.util.Random;
import java.util.logging.Logger;

/**
 *
 * @author RMUGLK
 */
public class Utils {
    
   public static final Logger logger = java.util.logging.Logger.getGlobal();
    
   private static final Random random = new Random(System.currentTimeMillis());
   
   public static java.sql.Timestamp getRandomTimeStamp(){
       long offset = Timestamp.valueOf("2015-01-01 00:00:00").getTime();
       long end = Timestamp.valueOf("2017-01-01 00:00:00").getTime();
       long diff = end - offset + 1;
       
       if ( diff ==  0)
           return new Timestamp(offset);
       
       return new Timestamp(offset + ( Math.abs(random.nextLong()) % diff ));
   }
   
   public static int getRandomInt(int a, int b){
       int diff = b-a +1 ;
       if ( diff ==  0)
           return a;
       
       return ( Math.abs(random.nextInt()) % diff) +a;
   }
    public static Long getRandomLong(long a, long b){
        long diff = b-a+1;
        if ( diff ==  0)
           return a;
       
       return ( Math.abs(random.nextLong()) % diff ) + a;
   }
    
   public static boolean dateRangesAreOverlaping(
           Timestamp start1, Timestamp end1,
           Timestamp start2, Timestamp end2
   ) {
        return (((end1 == null) || (start2 == null) || end1.after(start2)) &&
         ((start1 == null) || (end2 == null) || start1.before(end2)));
   }
}

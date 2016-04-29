/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elte.osz.logic;

import java.sql.Timestamp;
import java.util.Random;

/**
 *
 * @author Tóth Ákos
 */
public class Utils {
   private static final Random random = new Random(System.currentTimeMillis());
   
   public static java.sql.Timestamp getRandomTimeStamp(){
       long offset = Timestamp.valueOf("2015-01-01 00:00:00").getTime();
       long end = Timestamp.valueOf("2017-01-01 00:00:00").getTime();
       long diff = end - offset + 1;
       return new Timestamp(offset + (random.nextLong() % diff ));
   }
   
   public static int getRandomInt(int a, int b){
       int diff = b-a +1 ;
       return ( Math.abs(random.nextInt()) % diff) +a;
   }
    public static Long getRandomLong(long a, long b){
        long diff = b-a+1;
       return ( Math.abs(random.nextLong()) % diff ) + a;
   }
}

package com.ebiggerr.sims.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class idService {

    /**
     * <h1> Create a new id by doing increment on the previous max id in the database </h1>
     * For example : A6 to A7, A2323 to A2324
     *
     * @param id max id from database
     * @return new id that is increment from the previous max id
     */
    public static String idIncrement(String id){

        String alphabet = null;
        String digit = null;
        int increment = 0;

        Pattern pattern=Pattern.compile("([a-zA-Z]+)(\\d+)");
        Matcher matcher=pattern.matcher(id);

        if( matcher.find() && matcher.groupCount() ==2 ){
            alphabet=matcher.group(1);
            digit=matcher.group(2);

            increment= Integer.parseInt(digit);
            increment++;

            digit= String.valueOf(increment);
        }

        return alphabet+digit;
    }
}

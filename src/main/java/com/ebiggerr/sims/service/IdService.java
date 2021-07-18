/*
 * Copyright (c) 2021 EbiggerR
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ebiggerr.sims.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdService {

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

/*
 *  Copyright (C) 2020  Alexious Yong (Ebiggerr)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ebiggerr.sims.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class timeDate {

    private static final DateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");

    public static String convertFromTimeStamp(Timestamp timestamp){

        Date date=new Date(timestamp.getTime());

        String dateString= dateFormat.format(date);

        return dateString;
    }

    public static String convertFromUnixTimeStamp(Long unixTimeStamp){

        Date date=new Date(unixTimeStamp);

        String dateTime = dateFormat.format(date);

        return dateTime;
    }
}

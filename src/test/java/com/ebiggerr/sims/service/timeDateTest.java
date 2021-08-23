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

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class timeDateTest {

    @Test
    void convertFromTimeStampUsingTimeFROMSystemCurrentTimeMillis() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // System.out.println(TimeDate.convertFromTimeStamp(timestamp));

        //System.out.println(date);

    }

    @Test
    void convertFromTimeStampUsingEpoch(){

        Long unixTime = System.currentTimeMillis();
        long ut1 = Instant.now().getEpochSecond();

        System.out.println(unixTime);
        System.out.println(ut1);

        // System.out.println(timeDate.convertFromUnixTimeStamp(ut1));



    }
}
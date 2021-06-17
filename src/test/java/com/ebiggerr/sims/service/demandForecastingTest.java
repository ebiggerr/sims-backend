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

import com.ebiggerr.sims.service.DoubleExponentialSmoothing.demandForecasting;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class demandForecastingTest {

    @Test
    void exponentialSmoothing_TimeSeries() {

        double[] testData = {17.55, 21.86, 23.89, 26.93, 26.89, 28.83, 30.08, 30.95, 30.19, 31.58, 32.58, 33.48, 39.02, 41.39, 41.60};
        int forecast_size=3;

        demandForecasting d=new demandForecasting();
        double[]result=d.exponentialSmoothing_TimeSeries(testData,forecast_size);

        System.out.println( Arrays.toString(result) ) ;

    }
}
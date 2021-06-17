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

package com.ebiggerr.sims.service.DoubleExponentialSmoothing;

public class Model {

    private final double[] smoothedData;
    private final double[] trends;
    private final double[] levels;
    private final double sse;

    public Model(double[] smoothedData, double[] trends, double[] levels, double sse) {
        this.smoothedData = smoothedData;
        this.trends = trends;
        this.levels = levels;
        this.sse = sse;
    }

    /**
     * Forecasts future values.
     *
     * @param size size of the forecast
     * @return forecast data
     */
    public double[] forecast(int size) {
        double[] forecastData = new double[size];
        for (int i = 0; i < size; i++) {

            //forecast =( L <sub>t –1<sub> ) + ( T <sub>t –1<sub> )
            forecastData[i] = levels[levels.length - 1] + (i + 1) * trends[trends.length - 1];
        }
        return forecastData;
    }

    public double[] getSmoothedData() {
        return smoothedData;
    }

    public double[] getTrend() {
        return trends;
    }

    public double[] getLevel() {
        return levels;
    }

    public double getSSE() {
        return sse;
    }
}

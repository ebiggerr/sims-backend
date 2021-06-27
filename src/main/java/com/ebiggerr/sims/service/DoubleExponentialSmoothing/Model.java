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

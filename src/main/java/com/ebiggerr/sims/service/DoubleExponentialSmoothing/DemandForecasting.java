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

public class DemandForecasting {

    //ALPHA and BETA constant
    private final double ALPHA=0.8;
    private final double BETA=0.2;

    private static DemandForecasting demandForecasting;

    //singleton Pattern
    public static DemandForecasting getDemandForecasting(){

        if(demandForecasting == null){
            return new DemandForecasting();
        }

        return demandForecasting;
    }

    /**
     *
     * @param data Time series data
     * @param size size of the forecast
     * @return
     */
    public static double[] exponentialSmoothing_TimeSeries( double[] data,int size){

        double[]temp = getDemandForecasting().fit(data,size);

        for(int index=0; index< temp.length; index++){
            temp[index] = Math.ceil(temp[index]);
        }

        return temp;

    }

    private static int[] roundUpInt(double[] fit) {

        int index=fit.length;
        int[] temp=new int[index];

        for(int i=0; i<index; index++){
            temp[i]=(int)(fit[i]);
        }

        return temp;
    }

    private double[] fit(double[] data,int size){

        double[] smoothedData=new double[data.length];

        double[] trends= new double[data.length+1];
        double[] levels=new double[data.length+1];

        //initializing values of parameters
        smoothedData[0]=data[0];
        trends[0]= data[1] - data[0];
        levels[0] = data[0];

        for (int t = 0; t < data.length; t++) {
            smoothedData[t] = trends[t] + levels[t];

            levels[t+1] = ALPHA * data[t] + (1 - ALPHA) * (levels[t] + trends[t]);
            trends[t+1] = BETA * (levels[t+1] - levels[t]) + (1 - BETA) * trends[t];
        }

        //TODO enhancement#1 considering refactoring to Singleton Pattern
        Model model=new Model(smoothedData, trends, levels, calculateSSE(data,smoothedData) );

        return model.forecast(size);

    }

    /**
     * calculate the sum of squared error
     * used to determine what value of @ALPHA and @BETA to used in this model.
     * Values of @ALPHA and @BETA with the least SSE is the most optimum value.
     *
     * @param data
     * @param smoothedData
     * @return sum of squared error (SSE)
     */
    private static double calculateSSE(double[] data, double[] smoothedData) {
        double sse = 0;
        for (int i = 0; i < data.length; i++) {
            sse+= Math.pow(smoothedData[i] - data[i],2);
        }
        return sse;
    }

}

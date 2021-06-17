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

public class demandForecasting {

    //ALPHA and BETA constant
    private final double ALPHA=0.8;
    private final double BETA=0.2;

    private static demandForecasting demandForecasting;

    //singleton Pattern
    public static demandForecasting getDemandForecasting(){

        if(demandForecasting == null){
            return new demandForecasting();
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

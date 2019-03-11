package com.predictiontest.testh2o;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Da supa pc jr
 */
public class Util {
  
    //calculate mean fot a determined feature and class
    public static float mean(float[][] val,  int var,int number) {
        float mean = 0;
        for (int i = 0; i < val.length; i++) {
            
                mean += val[i][var];
            
        }
        return mean / (number);
    }

}

package com.predictiontest.testh2o;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.prediction.*;
import hex.genmodel.MojoModel;
import hex.genmodel.algos.word2vec.Word2VecMojoModel;

import hex.genmodel.easy.exception.PredictException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Da supa pc jr
 */
public class test {

    public static void main(String[] args) throws IOException, PredictException {
        System.out.println("Test h2o genmodel:");
        // we load our MOJOs
         EasyPredictModelWrapper model = new EasyPredictModelWrapper(MojoModel.load("C:\\Users\\l440\\Documents\\NetBeansProjects\\testh2o\\gbm.zip"));
         Word2VecMojoModel s= (Word2VecMojoModel)MojoModel.load("C:\\Users\\l440\\Documents\\NetBeansProjects\\testh2o\\w2v.zip");
         //string query
         String test="celebrate their birthdays";
         predict(test,model,s);
         
         
         Scanner input= new Scanner(System.in);
         String testQuery="";
         do{
             testQuery= input.nextLine();
             predict(testQuery, model, s);
             
         }while(!testQuery.equals("exit"));
         
    }
    //calculate aggregate avergage not available in Java for transform
    public static float[] aggregateAV(float[][] inp){
    float[] res= new float[inp[0].length];
    for(int c=0;c<res.length;c++){
    res[c]=Util.mean(inp, c, inp.length);
    }
    return res;
    }
    //create  row from transform sorry I didnt use string builder
    public static RowData toRow(float []inp){
    RowData query= new RowData();
    for(int c=0;c<inp.length;c++){
    query.put("C"+(c+1), inp[c]+"");
    }
    return query;
    }
    public static void predict(String test, EasyPredictModelWrapper model,Word2VecMojoModel s){
        try {
            //split data
            String res[]= test.split(" ");
            // create the matrix for each one of the transformations usin w2v   model
            float [][]sx= new float[res.length][100];
            for (int c=0;c<res.length;c++) {         
                s.transform0(res[c], sx[c]);
            }
            //print each word output vector
            for (float[] fs : sx) {
                System.out.println(Arrays.toString(fs));
            }
            System.out.println("aggregate");
            //apply aggregate Average
            RowData query= toRow(aggregateAV(sx));
            //predict
            MultinomialModelPrediction pred=model.predictMultinomial(query);
            //get probabilities
            System.out.println(Arrays.toString(pred.classProbabilities));
            //what kind of mail is it
            System.out.println(pred.label);
        } catch (PredictException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
 
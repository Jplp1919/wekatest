package com.mycompany.wekatest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class WekaTest {

    public static void main(String[] args) {
        try {

            Connection con = new ConnectionFactory().establishConnection();
            
            J48 j48Classifier = new J48();
            
            String sql = "SELECT * FROM pessoa";
            String fileName= "test";
            CsvMaker cs = new CsvMaker();
            
            cs.sqlToCSV(sql, fileName, con);

            ArffConverter ac = new ArffConverter();
            
            ac.Convert();
           
            String covidDataset
                    = "C:/Users/Usuário/Downloads/weka/test2.arff";
                         

            BufferedReader bufferedReader
                    = new BufferedReader(
                            new FileReader(covidDataset));

            Instances datasetInstances
                    = new Instances(bufferedReader);

            datasetInstances.setClassIndex(
                    datasetInstances.numAttributes() - 1);

            Evaluation evaluation
                    = new Evaluation(datasetInstances);

          evaluation.crossValidateModel(
                  j48Classifier, datasetInstances, 10,
                 new Random(1));

            System.out.println(evaluation.toSummaryString(
                    "\nResults", false));

        } catch (Exception e) {
            System.out.println("ERRO " + e.getMessage());
        }
    }
}

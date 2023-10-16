/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.wekatest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class ArffConverter {

    String f1 = "C:/Users/Usuário/Downloads/weka/test.csv";
    String f2 = "C:/Users/Usuário/Downloads/weka/test2.arff";

    public void Convert() {
        try {
            //carrega o csv
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File(f1));
            Instances data = loader.getDataSet();

           /* FastVector values = new FastVector(2);
            values.addElement("0");
            values.addElement("1");
            Attribute newAttribute = new Attribute("ValorQualitativo", values);
             */
          
            int attributeIndex = data.attribute("ValorQualitativo").index(); //index do atributo "ValorQualitativo"

         
            NumericToNominal numericToNominal = new NumericToNominal();//cria um filtro que muda numericos para numerais
            numericToNominal.setAttributeIndices("" + (attributeIndex + 1)); // +1 because Weka uses 1-based indexing

            
            numericToNominal.setInputFormat(data); // escolhe o dataset a ser filtrado
            Instances newData = Filter.useFilter(data, numericToNominal); // Aplica o filtro a o dataset
            
            ArffSaver saver = new ArffSaver();
            saver.setInstances(newData); // escolhe o dataset para ser convertido

            saver.setFile(new File(f2)); //esccolhe o output
            saver.writeBatch(); //escreve o arquivo

        } catch (IOException e) {

            System.out.println(e.getMessage());
        } catch(Exception e){
            System.out.println("Erro inesperado");
            System.out.println(e.getMessage());
        }
    }
}

package com.mycompany.wekatest;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CsvMaker {

    public void sqlToCSV(String query, String filename, Connection con) {

        try {
            Statement statement = con.createStatement();
            FileWriter fw = new FileWriter(filename + ".csv");
            ResultSet rs = statement.executeQuery(query);

            int cols = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= cols; i++) {
                fw.append(rs.getMetaData().getColumnLabel(i));
                if (i < cols) {
                    fw.append(',');
                } else {
                    fw.append('\n');
                }

            }

            while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    if (rs.getString(i) == null) {
                        fw.append("0");
                    } else {
                        fw.append(rs.getString(i));
                        /*   if(i != 17){
                         fw.append(rs.getString(i));   
                        } else{
                            String app =  " '"+ rs.getString(i) +"' "; 
                            fw.append(app);
                        }*/

                    }

                    if (i < cols) {
                        fw.append(',');
                    }
                }
                fw.append('\n');
            }

            fw.flush();
            fw.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

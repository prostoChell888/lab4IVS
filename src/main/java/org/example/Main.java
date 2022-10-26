package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {


try {
    File file = new File("D:\\sem5\\interfases\\Теория к лабам\\2 лаба\\20210930 1 (2).raw");
    String DB_USERNAME = "postgres";
    String DB_PASSWORD = "123";
    String DB_URL = "jdbc:postgresql://localhost:5432/interfacec2";

    DBConector dbConector = new DBConector(DB_URL, DB_USERNAME, DB_PASSWORD);
    dbConector.addInfoFromRafFile(file);
} catch (Exception ex){
    throw ex;
}




        //App fileSelectExample = new App();
        //fileSelectExample.setVisible(true);

    }
}
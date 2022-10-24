package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {



        File file = new File("D:\\sem5\\interfases\\Теория к лабам\\2 лаба\\20210930 1 (2).raw");
        DBConector dbConector = new DBConector();
        dbConector.addInfoFromRafFile(file);



        //App fileSelectExample = new App();
        //fileSelectExample.setVisible(true);

    }
}
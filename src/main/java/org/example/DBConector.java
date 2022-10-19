package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class DBConector {


    public void addInfoFromRafFile(File file) throws Exception {

        String bufString;
        try (InputStream is = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {




            while ((bufString = br.readLine()) != null) {

                boolean f = true;
                String[] arrOfLocationDataInStrings;
                do {
                    int startOfStandartStr = bufString.indexOf("$GP");
                    if (startOfStandartStr == -1) continue;

                    arrOfLocationDataInStrings = bufString.substring(startOfStandartStr).split(",");
                    switch (arrOfLocationDataInStrings[0]){
                        case "$GPGGA":
                            ParserCordFormats.GGA_Parser(arrOfLocationDataInStrings);
                            break;
                        case "$GPGSV":
                            ParserCordFormats.GSV_Parser(arrOfLocationDataInStrings);
                            break;
                        case "$GPGSA":
                            ParserCordFormats.GSA_Parser(arrOfLocationDataInStrings);
                            break;
                        case "$GPRMC":
                            ParserCordFormats.RMC_Parser(arrOfLocationDataInStrings);
                            f = false;
                            break;
                        default:
                            //throw new Exception("Ошибка считывания файла");
                    }
                } while ((bufString = br.readLine()) != null && f);
            }


        } catch (Exception ex) {
            throw new Exception("Ошибка преобразования файла\n" +
                    ex.getMessage());
        }

    }
}

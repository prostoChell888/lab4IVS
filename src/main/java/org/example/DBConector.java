package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class DBConector {


    public void addInfoFromRafFile(File file) throws Exception {


        try (InputStream is = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            var standarts = new Standarts();
            while (getFormatsFromBuffer(br, standarts)) {


            }


        } catch (Exception ex) {
            throw new Exception("Ошибка преобразования файла\n" +
                    ex.getMessage());
        }

    }

    private static boolean getFormatsFromBuffer(BufferedReader br, Standarts standarts) throws Exception {
        String bufString = null;
        boolean f = true;
        String[] arrOfLocationDataInStrings;

        while (f && (bufString = br.readLine()) != null) {
            int startOfStandartStr = bufString.indexOf("$GP");
            if (startOfStandartStr == -1) continue;

            f = getStrWithFormat(standarts, bufString, f, startOfStandartStr);
        }
        return bufString != null;
    }

    private static boolean getStrWithFormat(Standarts standarts, String bufString, boolean f, int startOfStandartStr) throws Exception {
        String[] arrOfLocationDataInStrings;
        arrOfLocationDataInStrings = bufString.substring(startOfStandartStr).split(",");
        switch (arrOfLocationDataInStrings[0]) {
            case "$GPGGA":
                ParserCordFormats.GGA_Parser(arrOfLocationDataInStrings, standarts.gga);break;
            case "$GPGSV":
                ParserCordFormats.GSV_Parser(arrOfLocationDataInStrings, standarts.gsv);break;
            case "$GPGSA":
                ParserCordFormats.GSA_Parser(arrOfLocationDataInStrings, standarts.gsa);break;
            case "$GPRMC":
                ParserCordFormats.RMC_Parser(arrOfLocationDataInStrings, standarts.rmc);
                f = false;
                break;
            default: throw new Exception("Ошибка считывания файла");
        }
        return f;
    }
}

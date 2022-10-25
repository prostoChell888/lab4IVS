package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.*;

public class DBConector {

    private static final Logger logger = Logger.getLogger(DBConector.class.getName());


    static {
        Handler h = new ConsoleHandler();
        h.setFormatter(new MyFormater());

        logger.setUseParentHandlers(false);
        logger.addHandler(h);
    }

    public void addInfoFromRafFile(File file) throws Exception {

        try (InputStream is = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            var standarts = new Standarts();
            while (getFormatsFromBuffer(br, standarts)) {

                standarts = new Standarts();
            }
        } catch (Exception ex) {
            int chexk;
            throw new Exception("Ошибка преобразования файла\n" + ex.getMessage());
        }
    }

    private static boolean getFormatsFromBuffer(BufferedReader br, Standarts standarts) throws Exception {

        String bufString = null;
        boolean f = true;
        // String[] arrOfLocationDataInStrings;
        try {
            while (f && (bufString = br.readLine()) != null) {
                if (bufString.contains("08:19:24.192\t$GPGSA")) {
                    int stop;
                }

                logger.info(bufString);
                f = getStrWithFormat(standarts, bufString, f);
            }
        } catch (Exception ex) {
            throw ex;
        }

        return bufString != null;
    }

    private static boolean getStrWithFormat(Standarts standarts, String bufString, boolean f)
            throws Exception {
        try {
            String[] arrOfLocationDataInStrings;

            int startOfStandartStr = bufString.indexOf("$GP");
            if (startOfStandartStr == -1) return false;

            arrOfLocationDataInStrings = bufString.substring(startOfStandartStr).split("[*,]");

            switch (arrOfLocationDataInStrings[0]) {
                case "$GPGGA":
                    standarts.gga = ParserCordFormats.GGA_Parser(arrOfLocationDataInStrings);
                    logger.info("========$GPGGA распарсен========");
                    break;
                case "$GPGSV":
                    standarts.gsv.add(ParserCordFormats.GSV_Parser(arrOfLocationDataInStrings));
                    logger.info("========$GPGSV распарсен========");
                    ;
                    break;
                case "$GPGSA":
                    standarts.gsa = ParserCordFormats.GSA_Parser(arrOfLocationDataInStrings);
                    logger.info("========$GPGSA распарсен========");
                    break;
                case "$GPRMC":
                    standarts.rmc = ParserCordFormats.RMC_Parser(arrOfLocationDataInStrings);
                    logger.info("========$GPRMC распарсен========");
                    f = false;
                    break;
                default:
                    throw new Exception("Ошибка считывания файла");
            }
        } catch (Exception ex) {
            throw ex;
        }
        return f;
    }
}

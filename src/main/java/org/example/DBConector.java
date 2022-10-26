package org.example;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Date;
import java.util.logging.*;

public class DBConector {

    {
        Logger logger = Logger.getLogger(DBConector.class.getName());
        Handler h = new ConsoleHandler();
        h.setFormatter(new MyFormater());

        logger.setUseParentHandlers(false);
        logger.addHandler(h);
    }
    private final Connection connection;
    public DBConector(String DB_URL, String DB_USERNAME, String DB_PASSWORD) throws SQLException {
         connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

    }


    public void addInfoFromRafFile(File file) throws Exception {

        try (InputStream is = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            var standarts = new Standarts();
            while (getFormatsFromBuffer(br, standarts)) {
               putInformationToBD(standarts);
                standarts = new Standarts();
            }
        } catch (Exception ex) {
            throw new Exception("Ошибка преобразования файла\n" + ex.getMessage());
        }
    }

    private void putInformationToBD(Standarts standarts) throws SQLException {
        int idOfLocationInformation = sendLocationInformToBd(standarts);
        /* language=SQL */
        //String sql = "insert intogsa  (device_id, UTC_date, date_of_locate) " +
                //"values (?, ?, ?) Returning *;";

    }

    private int sendLocationInformToBd(Standarts standarts) throws SQLException {
        Float time;
        if (standarts.rmc != null) time = standarts.rmc.getTimeUTC();
        else if(standarts.gga != null) time = standarts.gga.getTimeUTC();
        else return - 1;

        /* language=SQL */
        String sql = "insert into location_information (device_id, UTC_date, date_of_locate) " +
                "values (?, ?, ?) Returning location_information_id;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, 1);
        preparedStatement.setFloat(2, time);
        preparedStatement.setDate(3, standarts.rmc.getDate());
        ResultSet result = preparedStatement.executeQuery();

        result.next();
        return result.getInt(1);
    }

    private boolean getFormatsFromBuffer(BufferedReader br, Standarts standarts) throws Exception {

        String bufString = null;
        boolean f = true;
        try {
            while (f && (bufString = br.readLine()) != null) {
                //logger.info(bufString);
                f = getStrWithFormat(standarts, bufString, f);
            }
        } catch (Exception ex) {
            throw ex;
        }

        return bufString != null;
    }

    private boolean getStrWithFormat(Standarts standarts, String bufString, boolean f) throws Exception {
        try {
            int startOfStandartStr = bufString.indexOf("$GP");
            if (startOfStandartStr == -1) return false;

            String[] arrOfLocationDataInStrings = bufString.substring(startOfStandartStr).split("[*,]");
            switch (arrOfLocationDataInStrings[0]) {
                case "$GPGGA":
                    standarts.gga = ParserCordFormats.GGA_Parser(arrOfLocationDataInStrings);
                    //logger.info("========$GPGGA распарсен========");
                    break;
                case "$GPGSV":
                    standarts.gsv.add(ParserCordFormats.GSV_Parser(arrOfLocationDataInStrings));
                    //logger.info("========$GPGSV распарсен========");
                    break;
                case "$GPGSA":
                    standarts.gsa = ParserCordFormats.GSA_Parser(arrOfLocationDataInStrings);
                    //logger.info("========$GPGSA распарсен========");
                    break;
                case "$GPRMC":
                    standarts.rmc = ParserCordFormats.RMC_Parser(arrOfLocationDataInStrings);
                    //logger.info("========$GPRMC распарсен========");
                    System.out.println(standarts.rmc.getDate()
                            + " " + standarts.rmc.getTimeUTC());
                    f = false;
                    break;
                default:
                    throw new Exception("Ошибка считывания файла");
            }
        } catch (Exception ex) {throw ex;}
        return f;
    }
}

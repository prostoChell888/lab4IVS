package org.example;

import org.example.FormatClases.GSV;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.logging.*;

public class DBConector {
    Logger logger;

    {
        logger =Logger.getLogger(DBConector.class.getName());
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
            int i = 0;
            while (getFormatsFromBuffer(br, standarts)) {
                System.out.println("=====( " + i++ + " )=======");
                standarts.gsv.forEach(System.out::println);

                int idOfLocationInformation = sendLocationInformToBd(standarts);
                int idOfGSA = sendGSAToBd(standarts, idOfLocationInformation);
                putInformationToBD(standarts, idOfLocationInformation, idOfGSA);
                System.out.println(idOfGSA);

                standarts = new Standarts();
            }
        } catch (Exception ex) {
            throw new Exception("Ошибка преобразования файла\n" + ex.getMessage());
        }
    }

    private void putInformationToBD(Standarts standarts, int idOfLocationInformation, int idOfGSA)
            throws Exception {
        if (standarts.gsv.size() == 0) return;
        if (idOfGSA == -1)throw new Exception("отстутствует внешний ключ idOfGSA");
        /* language=SQL */
        String sql = "INSERT INTO gsv (location_information_id, GSA_id, satellite_id, elevation, azimuth, snr_c_no) " +
                "VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement;

        for (GSV gsvInf:standarts.gsv) {
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setObject(1, idOfLocationInformation, java.sql.Types.INTEGER);
            preparedStatement.setObject(2, idOfGSA, java.sql.Types.INTEGER);
            preparedStatement.setObject(3, gsvInf.getSatelliteID(), java.sql.Types.INTEGER);
            preparedStatement.setObject(4, gsvInf.getElevation(), java.sql.Types.INTEGER);
            preparedStatement.setObject(5, gsvInf.getAzimuth(), java.sql.Types.INTEGER);
            preparedStatement.setObject(6, gsvInf.getSNR_C_No(), java.sql.Types.INTEGER);

             preparedStatement.executeUpdate();
        }
    }

    private Integer sendGSAToBd(Standarts standarts, int idOfLocationInformation) throws Exception {
        if (standarts.gsa == null) return -1;
        if (idOfLocationInformation == -1){
            throw new Exception("отсутствует внешний ключ idOfLocationInformation\n");
        }
        /* language=SQL */
        String sql = "INSERT INTO gsa (location_information_id, mode1, mode2, PDOP, HDOP, VDOP) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING GSA_id;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (idOfLocationInformation != -1) preparedStatement.setInt(1, idOfLocationInformation);
        preparedStatement.setString(2, String.valueOf(standarts.gsa.getMode1()));
        preparedStatement.setInt(3, standarts.gsa.getMode2());
        preparedStatement.setDouble(4, standarts.gsa.getPDOP());
        preparedStatement.setDouble(5, standarts.gsa.getHDOP());
        preparedStatement.setDouble(6, standarts.gsa.getVDOP());

        ResultSet result = preparedStatement.executeQuery();

        result.next();
        return result.getInt(1);
    }

    private int sendLocationInformToBd(Standarts standarts) throws SQLException {
        Float time;
        if (standarts.rmc != null) time = standarts.rmc.getTimeUTC();
        else if (standarts.gga != null) time = standarts.gga.getTimeUTC();
        else return -1;

        /* language=SQL */
        String sql = "INSERT INTO location_information (device_id, UTC_date, date_of_locate) " +
                "VALUES (?, ?, ?) RETURNING location_information_id;";
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
                    standarts.gsv.addAll(ParserCordFormats.GSV_Parser(arrOfLocationDataInStrings));
                    //logger.info("========$GPGSV распарсен========");

                    break;
                case "$GPGSA":
                    standarts.gsa = ParserCordFormats.GSA_Parser(arrOfLocationDataInStrings);
                    //logger.info("========$GPGSA распарсен========");
                    break;
                case "$GPRMC":
                    standarts.rmc = ParserCordFormats.RMC_Parser(arrOfLocationDataInStrings);
                    //logger.info("========$GPRMC распарсен========");
                    //System.out.println(standarts.rmc.getDate()
                    // + " " + standarts.rmc.getTimeUTC());
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

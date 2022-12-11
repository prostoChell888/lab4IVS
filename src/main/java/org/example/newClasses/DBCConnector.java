package org.example.newClasses;

import org.example.FormatClases.GSV;
import org.example.FormatClases.PosInform;
import org.example.FormatClases.Standarts;
import org.example.notUse.MyFormater;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.renderer.DefaultPolarItemRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import static org.example.Main.createPolarChart;

public class DBCConnector {
    Logger logger;

    {
        logger = Logger.getLogger(DBCConnector.class.getName());
        Handler h = new ConsoleHandler();
        h.setFormatter(new MyFormater());

        logger.setUseParentHandlers(false);
        logger.addHandler(h);
    }

    private final Connection connection;

    public DBCConnector(String DB_URL, String DB_USERNAME, String DB_PASSWORD) throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }


    public void addInfoFromRafFile(File file) throws Exception {
        try (InputStream is = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            var standarts = new Standarts();
            boolean isEmpty = true;
            while (getFormatsFromBuffer(br, standarts)) {

                isEmpty = checkAvailabilityOfInfo(standarts);
                int idOfLocationInformation = sendLocationInformToBd(standarts);
                int idOfGSA = sendGSAToBd(standarts, idOfLocationInformation);
                sendGSVToBD(standarts, idOfLocationInformation, idOfGSA);
                int pos_inform_id = sendPosInform(standarts);
                sendRmcToBd(standarts, idOfLocationInformation, pos_inform_id);
                sendGgaToBd(standarts, idOfLocationInformation, pos_inform_id);

                standarts = new Standarts();
            }
            if (isEmpty) throw new IOException("Файл не содержит нужной информации");
        } catch (IOException ex) {
            throw new Exception("Ошибка преобразования файла\n" + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("Ошибка преобразования файла\n" + ex.getMessage());
        }
    }

    private boolean checkAvailabilityOfInfo(Standarts standarts) {
        return standarts.gga == null && standarts.rmc == null &&
                standarts.gsa == null && standarts.gsv.size() == 0;
    }

    private void sendGgaToBd(Standarts standarts, int idOfLocationInformation, int pos_inform_id) throws SQLException {
        if (standarts.gga == null) return;
        /* language=SQL */
        String sql = "INSERT INTO GGA (location_information_id," +
                " pos_inform_id," +
                " position_fix_indicator," +
                " satellites_used, HDOP," +
                "  MSL_altitude, units1," +
                " geoid_separation1," +
                " units2," +
                " age_of_diff_corr," +
                " diff_ref_station_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, idOfLocationInformation, Types.INTEGER);
        ps.setObject(2, pos_inform_id, Types.INTEGER);
        ps.setObject(3, standarts.gga.getPositionFixIndicator(), Types.INTEGER);
        ps.setObject(4, standarts.gga.getSatellitesUsed(), Types.INTEGER);
        ps.setObject(5, standarts.gga.getHDOP(), Types.DOUBLE);
        ps.setObject(6, standarts.gga.getMSLAltitude(), Types.DOUBLE);
        ps.setObject(7, standarts.gga.getUnits1(), Types.CHAR);
        ps.setObject(8, standarts.gga.getGeoidSeparation1(), Types.DOUBLE);
        ps.setObject(9, standarts.gga.getUnits2(), Types.CHAR);
        ps.setObject(10, standarts.gga.getDiffrefstationID(), Types.INTEGER);
        ps.setString(11, standarts.gga.getAgeOfDiffCorr());

        ps.executeUpdate();
    }

    private void sendRmcToBd(Standarts standarts, int idOfLocationInformation, int pos_inform_id) throws SQLException {
        if (standarts.rmc == null) return;

        String sql = "INSERT INTO RMC (location_information_id, pos_inform_id, status," +
                " speed_over_ground, course_over_ground,  magnetic_variation) " +
                "VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, idOfLocationInformation, Types.INTEGER);
        ps.setObject(2, pos_inform_id, Types.INTEGER);
        ps.setObject(3, standarts.rmc.getStatus(), Types.CHAR);
        ps.setObject(4, standarts.rmc.getSpeedOverGround(), Types.FLOAT);
        ps.setObject(5, standarts.rmc.getCourseOverGround(), Types.FLOAT);
        ps.setObject(6, standarts.rmc.getMagneticVariation(), Types.CHAR);

        ps.executeUpdate();
    }

    private int sendPosInform(Standarts standarts) throws Exception {
        if (standarts.gga == null || standarts.rmc == null) return -1;

        PosInform posInform = new PosInform(standarts.gga, standarts.rmc);

        String sql = "INSERT INTO pos_inform (latitude, N_S_indicator, longitude, E_W_Indicator) " +
                "VALUES (?, ?, ?, ?) RETURNING pos_inform_id;";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setObject(1, posInform.getLatitude(), Types.FLOAT);
        ps.setObject(2, posInform.getIndicatorNS(), Types.CHAR);
        ps.setObject(3, posInform.getLongitude(), Types.FLOAT);
        ps.setObject(4, posInform.getIndicatorEW(), Types.CHAR);

        ResultSet result = ps.executeQuery();
        result.next();
        return result.getInt(1);
    }

    private void sendGSVToBD(Standarts standarts, int idOfLocationInformation, int idOfGSA) throws Exception {
        if (standarts.gsv.size() == 0) return;
        if (idOfGSA == -1) throw new Exception("отстутствует внешний ключ idOfGSA");
        /* language=SQL */
        String sql = "INSERT INTO gsv (location_information_id, GSA_id, satellite_id, elevation, azimuth, snr_c_no) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps;

        for (GSV gsvInf : standarts.gsv) {
            ps = connection.prepareStatement(sql);

            ps.setObject(1, idOfLocationInformation, java.sql.Types.INTEGER);
            ps.setObject(2, idOfGSA, java.sql.Types.INTEGER);
            ps.setObject(3, gsvInf.getSatelliteID(), java.sql.Types.INTEGER);
            ps.setObject(4, gsvInf.getElevation(), java.sql.Types.INTEGER);
            ps.setObject(5, gsvInf.getAzimuth(), java.sql.Types.INTEGER);
            ps.setObject(6, gsvInf.getSNR_C_No(), java.sql.Types.INTEGER);

            ps.executeUpdate();
        }
    }

    private Integer sendGSAToBd(Standarts standarts, int idOfLocationInformation) throws Exception {
        if (standarts.gsa == null) return -1;
        if (idOfLocationInformation == -1) {
            throw new Exception("отсутствует внешний ключ idOfLocationInformation\n");
        }
        /* language=SQL */
        String sql = "INSERT INTO gsa (location_information_id, mode1, mode2, PDOP, HDOP, VDOP) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING GSA_id;";

        PreparedStatement ps = connection.prepareStatement(sql);
        if (idOfLocationInformation != -1) ps.setInt(1, idOfLocationInformation);
        ps.setString(2, String.valueOf(standarts.gsa.getMode1()));
        ps.setInt(3, standarts.gsa.getMode2());
        ps.setDouble(4, standarts.gsa.getPDOP());
        ps.setDouble(5, standarts.gsa.getHDOP());
        ps.setDouble(6, standarts.gsa.getVDOP());

        ResultSet result = ps.executeQuery();
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
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, 1);
        ps.setFloat(2, time);
        ps.setDate(3, standarts.rmc.getDate());
        ResultSet result = ps.executeQuery();
        result.next();

        return result.getInt(1);
    }

    private boolean getFormatsFromBuffer(BufferedReader br, Standarts standarts) throws Exception {

        String bufString = null;
        boolean f = true;

        try {
            while (f && (bufString = br.readLine()) != null) {
                logger.info(bufString);
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
                    break;
                case "$GPGSV":
                    standarts.gsv.addAll(ParserCordFormats.GSV_Parser(arrOfLocationDataInStrings));
                    break;
                case "$GPGSA":
                    standarts.gsa = ParserCordFormats.GSA_Parser(arrOfLocationDataInStrings);
                    break;
                case "$GPRMC":
                    standarts.rmc = ParserCordFormats.RMC_Parser(arrOfLocationDataInStrings);
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

    public List<List<String>> getGSA_inf() throws SQLException {
        ResultSet set = getResultSetGSAInf();

        List<List<String>> table = new ArrayList<>();
        while (set.next()) {
            var str = new ArrayList<String>();
            str.add(Float.toString(set.getFloat("UTC_date")));
            str.add(set.getDate("date_of_locate").toString());
            str.add(set.getString("mode1"));
            str.add(Integer.toString(set.getInt("mode2")));
            str.add(Float.toString(set.getFloat("PDOP")));
            str.add(Float.toString(set.getFloat("HDOP")));
            str.add(Float.toString(set.getFloat("VDOP")));

            table.add(str);
        }

        return table;
    }

    private ResultSet getResultSetGSAInf() throws SQLException {
        /* language=SQL */
        String sql = "SELECT UTC_date, " +
                "       date_of_locate, " +
                "       PDOP, " +
                "       HDOP, " +
                "       VDOP, " +
                "       mode1, " +
                "       mode2 " +
                "FROM gsa " +
                "         JOIN location_information USING (location_information_id) " +
                "ORDER BY  UTC_date;";


        Statement st = connection.createStatement();
        ResultSet set = st.executeQuery(sql);
        return set;
    }

    public List<List<String>> getRMC_inf() throws SQLException {
        ResultSet set = getResultSetRMCInf();

        List<List<String>> table = new ArrayList<>();
        while (set.next()) {
            var str = new ArrayList<String>();
            str.add(Float.toString(set.getFloat("UTC_date")));
            str.add(set.getDate("date_of_locate").toString());
            str.add(Float.toString(set.getFloat("latitude")));
            str.add(set.getString("N_S_indicator"));
            str.add(Float.toString(set.getFloat("longitude")));
            str.add(set.getString("E_W_Indicator"));
            str.add(Float.toString(set.getFloat("speed_over_ground")));
            str.add(Float.toString(set.getFloat("course_over_ground")));

            table.add(str);
        }

        return table;
    }

    private ResultSet getResultSetRMCInf() throws SQLException {
        /* language=SQL */
        String sql = "SELECT UTC_date, " +
                "       date_of_locate, " +
                "       latitude, " +
                "       N_S_indicator, " +
                "       longitude, " +
                "       E_W_Indicator, " +
                "       speed_over_ground, " +
                "       course_over_ground " +
                "FROM rmc" +
                "         JOIN location_information USING (location_information_id) " +
                "         JOIN pos_inform USING (pos_inform_id) " +
                "ORDER BY UTC_date;";


        Statement st = connection.createStatement();
        ResultSet set = st.executeQuery(sql);
        return set;
    }

    public List<List<String>> getGGA_inf() throws SQLException {
        ResultSet set = getResultSetOfGGAQary();

        List<List<String>> table = new ArrayList<>();
        while (set.next()) {
            var str = new ArrayList<String>();
            str.add(Float.toString(set.getFloat("UTC_date")));
            str.add(set.getDate("date_of_locate").toString());
            str.add(Float.toString(set.getFloat("latitude")));
            str.add(set.getString("N_S_indicator"));
            str.add(Float.toString(set.getFloat("longitude")));
            str.add(set.getString("E_W_Indicator"));
            str.add(Integer.toString(set.getInt("position_fix_indicator")));
            str.add(Integer.toString(set.getInt("satellites_used")));
            str.add(Float.toString(set.getFloat("HDOP")));
            str.add(set.getString("units2"));

            table.add(str);
        }

        return table;
    }

    private ResultSet getResultSetOfGGAQary() throws SQLException {
        /* language=SQL */
        String sql = "SELECT UTC_date, " + " date_of_locate, " + " latitude, " + " N_S_indicator, " + " longitude, " +
                "       E_W_Indicator, " +
                "       position_fix_indicator, " +
                "       satellites_used, " +
                "       HDOP, " +
                "       units2 " +
                "FROM gga " +
                "         JOIN location_information USING (location_information_id) " +
                "         JOIN pos_inform USING (pos_inform_id) " +
                "ORDER BY  UTC_date;";

        Statement st = connection.createStatement();
        ResultSet set = st.executeQuery(sql);
        return set;
    }

    public List<List<String>> getGSV_inf() throws SQLException {
        ResultSet set = getResultSetGSVinf();

        List<List<String>> table = new ArrayList<>();
        while (set.next()) {
            var str = new ArrayList<String>();
            str.add(Float.toString(set.getFloat("UTC_date")));
            str.add(set.getDate("date_of_locate").toString());
            str.add(Integer.toString(set.getInt("satellite_id")));
            str.add(Integer.toString(set.getInt("elevation")));
            str.add(Integer.toString(set.getInt("azimuth")));
            str.add(Integer.toString(set.getInt("snr_c_no")));

            table.add(str);
        }

        return table;
    }

    private ResultSet getResultSetGSVinf() throws SQLException {
        /* language=SQL */
        String sql = "SELECT UTC_date, " + " date_of_locate, " + " satellite_id, " + " azimuth, " + " elevation, " +
                "       snr_c_no " + "FROM gsv " + "  JOIN location_information USING (location_information_id) " +
                "ORDER BY  UTC_date;";

        Statement st = connection.createStatement();
        ResultSet set = st.executeQuery(sql);
        return set;
    }

    public List<Float> getLocationInf(String parameter) throws SQLException {
        // language=SQL
        String sql = "SELECT " + parameter + " FROM  gga " +
                "JOIN location_information USING (location_information_id) " +
                "         JOIN pos_inform USING (pos_inform_id) " +
                "ORDER BY UTC_date";

        Statement st = connection.createStatement();
        ResultSet set = st.executeQuery(sql);

        List<Float> data = new ArrayList<>();

        while (set.next()) data.add(set.getFloat(parameter));

        return data;
    }


    public JFreeChart getSputniksPosighions() throws SQLException {
        List<Integer> listOfSputniksId = getListOfSputniksId();
        XYSeriesCollection xyDataset = new XYSeriesCollection();
        for (var sputId : listOfSputniksId) {
            /* language=SQL */
            StringBuilder sql2 = new StringBuilder("SELECT elevation, azimuth " +
                    "FROM gsv " +
                    "WHERE satellite_id = ");
            Statement state1 = connection.createStatement();
            sql2.append(sputId);
            ResultSet cordinates = state1.executeQuery(sql2.toString());

            List<Integer> listOfElevation = new ArrayList<>();
            List<Integer> listOfAzimut = new ArrayList<>();

            XYSeries series = new XYSeries(sputId);
            while (cordinates.next()) {
                listOfAzimut.add(cordinates.getInt(1));
                listOfElevation.add(cordinates.getInt(2));

                series.add(90 - listOfAzimut.get(listOfAzimut.size() - 1),
                        listOfElevation.get(listOfElevation.size() - 1));
            }
            xyDataset.addSeries(series);
        }

        return createPolarChart("Skyplot", xyDataset, true, false, true);
    }

    private List<Integer> getListOfSputniksId() throws SQLException {
        String sql = "SELECT DISTINCT(satellite_id)" +
                "FROM gsv;";

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        List<Integer> listOfSputniksId = new ArrayList<>();
        while (result.next()) listOfSputniksId.add(result.getInt(1));
        return listOfSputniksId;
    }

    public static JFreeChart createPolarChart(String title, XYDataset dataset,
                                              boolean legend, boolean tooltips, boolean urls) {
        PolarPlot plot = new PolarPlot();
        plot.setDataset(dataset);
        NumberAxis rangeAxis = new NumberAxis();
        rangeAxis.setAxisLineVisible(false);
        rangeAxis.setTickMarksVisible(false);
        rangeAxis.setTickLabelInsets(new RectangleInsets(0.0, 0.0, 0.0, 0.0));
        plot.setAxis(rangeAxis);
        plot.setRenderer(new DefaultPolarItemRenderer());

        return new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);
    }


    public void deletingTableData() throws SQLException {
        String sql = "TRUNCATE TABLE  pos_inform CASCADE; " +
                " TRUNCATE TABLE  location_information CASCADE;";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void addIfoFromCSVFile(File file) throws Exception {
        if (file == null) throw new Exception("file == null");

        try (InputStream is = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            

        } catch (IOException ex) {
            throw new Exception("Ошибка преобразования файла\n" + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("Ошибка преобразования файла\n" + ex.getMessage());
        }
    }
}



package org.example;

import org.example.newClasses.DBCConnector;
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
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        //createBD();
       // testBD();
//todo здесь раздереление
        App fileSelectExample = new App();
        fileSelectExample.setVisible(true);

    }

    private static void createBD() throws Exception {
        try {
            File file = new File("D:\\sem5\\interfases\\Теория к лабам\\2 лаба\\20210930 1 (2).raw");
            String DB_USERNAME = "postgres";
            String DB_PASSWORD = "123";
            String DB_URL = "jdbc:postgresql://localhost:5432/interfacec2";

            DBCConnector dbConector = new DBCConnector(DB_URL, DB_USERNAME, DB_PASSWORD);
            dbConector.addInfoFromRafFile(file);
        } catch (Exception ex) {
            throw ex;
        }
    }

    private static void testBD() throws SQLException {
        File file = new File("D:\\sem5\\interfases\\Теория к лабам\\2 лаба\\20210930 1 (2).raw");
        String DB_USERNAME = "postgres";
        String DB_PASSWORD = "123";
        String DB_URL = "jdbc:postgresql://localhost:5432/interfacec2";

        Connection connection;

        connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        String sql = "SELECT DISTINCT(satellite_id)" +
                "FROM gsv;";

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);

        List<Integer> listOfSputniksId = new ArrayList<>();
        while (result.next()) listOfSputniksId.add(result.getInt(1));

        XYSeriesCollection xyDataset = new XYSeriesCollection();
        for (var sputId : listOfSputniksId) {

            /* language=SQL */
            StringBuilder sql2 = new StringBuilder("SELECT elevation, azimuth " +
                    "FROM gsv " +
                    "WHERE satellite_id = ");
            Statement state1 = connection.createStatement();
            sql2.append(sputId);
            ResultSet cordinates = state1.executeQuery(sql2.toString());

            List<Integer> listOfAzimut = new ArrayList<>();
            List<Integer> listOfElevation = new ArrayList<>();
            System.out.println("================================" + sputId
                    + "=================================");
            XYSeries series = new XYSeries(sputId);
            while (cordinates.next()) {

                listOfAzimut.add(cordinates.getInt(1));
                listOfElevation.add(cordinates.getInt(2));
                series.add(listOfAzimut.get(listOfAzimut.size() - 1),
                        listOfElevation.get(listOfElevation.size() - 1));
                System.out.println("Azimut = " +
                        listOfAzimut.get(listOfAzimut.size() - 1) + "; Elevation = "
                        + listOfElevation.get(listOfElevation.size() - 1));
            }
            xyDataset.addSeries(series);

        }


        JFreeChart chart = createPolarChart("пРОБУЮ", xyDataset, true, false, true);
        JFrame frame =
                new JFrame("MinimalStaticChart");
        // Помещаем график на фрейм
        frame.getContentPane()
                .add(new ChartPanel(chart));
        frame.setSize(600,600);
        frame.show();

        listOfSputniksId.forEach(System.out::println);
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

        return new JFreeChart(
                title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);

    }



}
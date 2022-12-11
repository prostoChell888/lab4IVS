package org.example;

import org.example.newClasses.DBCConnector;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.tabbedui.VerticalLayout;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FramePrinter {
    public static void printDownloudWindow(JFrame frame, String fileStatus, DBCConnector dbConector) throws Exception {
        dbConector.deletingTableData();
        frame.setSize(500, 500);
        //frame.setResizable(false);
        frame.getRootPane().setBorder
                (BorderFactory.createEmptyBorder
                        (20, 10, 20, 10));

        Container container = frame.getContentPane();
        container.removeAll();

        container.setLayout(new GridLayout
                (3, 3, 10, 50));

        JLabel jl = new JLabel("Выбирите первый файл");
        List<File> files = new ArrayList<>();
        JLabel status = new JLabel(fileStatus);
        JButton buttonChooseFile = ButtonFactory.createButtonChooseFile(status, files);

        //////
        JLabel jl2 = new JLabel("Выбирите второй файл");
        JLabel status2 = new JLabel(fileStatus);
        JButton buttonChooseFile2 = ButtonFactory.createButtonChooseFile(status2, files);
        //////



        JButton buttonOpen = ButtonFactory.createOpenButton(frame, files, dbConector);

        container.add(jl);
        container.add(buttonChooseFile);
        container.add(status);

        container.add(jl2);
        container.add(buttonChooseFile2);
        container.add(status2);

        container.add(buttonOpen);

        frame.revalidate();
        frame.repaint();
    }

    public static void printNewTableWindow(JFrame frame, DBCConnector dbConector) throws Throwable {
        printNewTableWindow(frame, dbConector, ButtonFactory.getGGA_table(dbConector,
                0), ButtonFactory.getGGA_table(dbConector, 1),"GGA");
    }

    public static void printNewTableWindow(JFrame frame, DBCConnector connector,JTable jTable1, JTable jTable2,
                                           String defaultChoice) throws Throwable {
        Container container = setSize(frame);

        JToolBar jToolBar = new JToolBar("Запросы");
        container.add(jToolBar, BorderLayout.NORTH);

        jToolBar.add(new JButton("Таблица"));
        jToolBar.add(ButtonFactory.createNewRouteButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewSkyPlotButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewGraphbutton(frame, connector));
        jToolBar.add(ButtonFactory.createLoadFileButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewSKOButton(frame, connector));

        JPanel jPanel = createSetingsPanel(frame, connector, defaultChoice);

        container.add(jPanel, BorderLayout.WEST);

        JScrollPane scrollPane = new JScrollPane(jTable1);
        JScrollPane scrollPane2 = new JScrollPane(jTable2);

        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new GridLayout
                (1, 2, 2, 2));

        jPanel2.add(scrollPane);
        jPanel2.add(scrollPane2);


        container.add(jPanel2, BorderLayout.CENTER);


        frame.revalidate();
        frame.repaint();
    }

    private static JPanel createSetingsPanel(JFrame frame, DBCConnector connector,
                                             String defaultChoice) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 1, 5, 12));

        JPanel format = new JPanel();
        format.setLayout(new VerticalLayout());

        format.add(new JLabel("<html>Выберите<br/>формат</html>"));
        format.add(ButtonFactory.createFormatChoserButton(frame, connector, defaultChoice));

        jPanel.add(format);
        format.setLayout(new VerticalLayout());

        JPanel from = new JPanel();
        from.add(new JLabel("от"));
        from.add(new JTextField(10));
        format.add(new JLabel("<html>Выберите<br/>время</html>"));
        format.add(from);
        JPanel till = new JPanel();
        till.add(new JLabel("до"));
        till.add(new JTextField(10));
        format.add(till);
        format.add(new JButton("Обновить"));
        
        return jPanel;
    }



    public static void printNewGraphWindow(JFrame frame, DBCConnector connector,
                                           XYDataset dataset, NamesOfAxes namesOfAxes) throws Throwable {
        Container container = setSize(frame);

        JToolBar jToolBar = new JToolBar("Запросы");
        container.add(jToolBar, BorderLayout.NORTH);

        jToolBar.add(ButtonFactory.creteNewTablesButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewRouteButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewSkyPlotButton(frame, connector));
        jToolBar.add(new JButton("Графики"));
        jToolBar.add(ButtonFactory.createLoadFileButton(frame, connector));

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 1, 5, 12));
        container.add(jPanel, BorderLayout.WEST);

        jPanel.add(new JLabel("<html>Выберите<br/>навигационные<br/>параметры</html>"));
        jPanel.add(ButtonFactory.createComboBoxChooseAxis(frame, connector, namesOfAxes));

        JFreeChart chart = ChartFactory.createScatterPlot(
                "График зависимости",
                namesOfAxes.getNameOfX(),//x
                namesOfAxes.getNameOfY(),//y
                dataset);

        ChartPanel panel = new ChartPanel(chart);
        container.add(panel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    private static Container setSize(JFrame frame) {
        frame.setSize(1000, 800);
        frame.setResizable(true);

        Container container = frame.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());
        return container;
    }
    
    
    private static XYDataset createDatasetForSpeedWithTime
            (List<Double> listXAxis, List<Double> listYAxis) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series = new XYSeries("");

        for (int i = 0; i < listXAxis.size(); i++) {
            series.add(listXAxis.get(i), listYAxis.get(i));
        }

        dataset.addSeries(series);
        return dataset;
    }

    public static void printNewSkyplotWindow(JFrame frame, DBCConnector connector) throws Throwable {
        Container container = setSize(frame);

        JToolBar jToolBar = new JToolBar("Запросы");
        container.add(jToolBar, BorderLayout.NORTH);

        jToolBar.add(ButtonFactory.creteNewTablesButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewRouteButton(frame, connector));
        jToolBar.add(new JButton("Скайплот"));
        jToolBar.add(ButtonFactory.createNewGraphbutton(frame, connector));
        jToolBar.add(ButtonFactory.createLoadFileButton(frame, connector));

        JFreeChart chart = connector.getSputniksPosighions();

        container.add(new ChartPanel(chart), BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    public static void printNewRotePrinter(JFrame frame, DBCConnector connector, XYDataset dataset) throws Throwable {
        Container container = setSize(frame);

        JToolBar jToolBar = new JToolBar("Запросы");
        container.add(jToolBar, BorderLayout.NORTH);

        jToolBar.add(ButtonFactory.creteNewTablesButton(frame, connector));
        jToolBar.add(new JButton("Маршрут"));
        jToolBar.add(ButtonFactory.createNewSkyPlotButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewGraphbutton(frame, connector));
        jToolBar.add(ButtonFactory.createLoadFileButton(frame, connector));
        
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Маршрут",
                "latitude",//x
                "longitude",//y
                dataset);

        ChartPanel panel = new ChartPanel(chart);
        container.add(panel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    public static void printNewSKOWindow(JFrame frame, DBCConnector connector) throws Throwable {

        Container container = setSize(frame);

        JToolBar jToolBar = new JToolBar("Запросы");
        container.add(jToolBar, BorderLayout.NORTH);

        jToolBar.add(ButtonFactory.creteNewTablesButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewRouteButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewSkyPlotButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewGraphbutton(frame, connector));
        jToolBar.add(ButtonFactory.createLoadFileButton(frame, connector));
        jToolBar.add(new JButton("CKO"));

        List<Double> avgPNSCord1 = getAvgPNSCord(connector, 0);
        List<Double> avgPNSCord2 = getAvgPNSCord(connector, 1);
        List<Double> avgPNSCordDif = getDifOfMatr(avgPNSCord1, avgPNSCord2);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 3, 5, 12));

        jPanel.add(new JLabel("<html>Средние значения из 1 файла <br/>" + getParametrs(avgPNSCord1)));
        jPanel.add(new JLabel("<html>Средние значения из 1 файла <br/>" + getParametrs(avgPNSCord2)));
        jPanel.add(new JLabel("<html>Разница средних значений <br/>" + getParametrs(avgPNSCordDif)));

        container.add(jPanel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }


    private static String getParametrs(List<Double> list) {
        return "Высота: " + list.get(0) + " градусов<br/>"+
                "Широта: " + list.get(1) + " градусов<br/>" +
                "Долгота: " + list.get(2) + " метров";
    }

    private static List<Double> getDifOfMatr(List<Double> avgPNSCord1, List<Double> avgPNSCord2) {
        List<Double> avgPNSCordDif = new ArrayList<>();

        for (int i = 0; i < avgPNSCord1.size(); i++) {
            Double dif = avgPNSCord2.get(i) - avgPNSCord1.get(i);
            avgPNSCordDif.add(dif);
        }

        return avgPNSCordDif;
    }

    private static List<Double> getAvgPNSCord(DBCConnector connector, Integer numOFDevice)
            throws SQLException {
        List<Double> avgPNSCord = new ArrayList<>();
        avgPNSCord.addAll(connector.getAVGLatAndLong(numOFDevice));


        return avgPNSCord;
    }
}

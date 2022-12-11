package org.example;

import org.example.newClasses.DBCConnector;
import org.example.newClasses.FilesHolder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FramePrinter {

//в вектк даи 4
    public static void printDownloudWindow(JFrame frame, String fileStatus, DBCConnector dbConector) throws Exception {
        frame.setSize(300, 200);
        frame.setResizable(false);
        frame.getRootPane().setBorder
                (BorderFactory.createEmptyBorder
                        (20, 10, 20, 10));

        Container container = frame.getContentPane();
        container.removeAll();

        container.setLayout(new GridLayout
                (2, 0, 10, 50));

        JLabel jl = new JLabel("Выбирите файл");
        var holderFirstFile = new FilesHolder();
        JLabel status = new JLabel(fileStatus);
        JButton buttonChooseFile = ButtonFactory.createButtonChooseFile(status, holderFirstFile);

        JButton buttonOpen = ButtonFactory.createOpenButton(frame, holderFirstFile, dbConector);

        container.add(jl);
        container.add(buttonChooseFile);
        container.add(status);
        container.add(buttonOpen);

        frame.revalidate();
        frame.repaint();
    }

    public static void printNewTableWindow(JFrame frame, DBCConnector connector, JTable jTable, String defaultChoice) throws Throwable {

        frame.setSize(1000, 800);
        frame.setResizable(true);

        Container container = frame.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());


        JToolBar jToolBar = new JToolBar("Запросы");
        container.add(jToolBar, BorderLayout.NORTH);

        jToolBar.add(new JButton("Таблица"));
        jToolBar.add(ButtonFactory.createNewRouteButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewSkyPlotButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewGraphbutton(frame, connector));

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 1, 5, 12));


        jPanel.add(new JLabel("<html>Выберите<br/>формат</html>"));
        jPanel.add(ButtonFactory.createFormatChoserButton(frame, connector, defaultChoice));


        container.add(jPanel, BorderLayout.WEST);

        JScrollPane scrollPane = new JScrollPane(jTable);

        container.add(scrollPane, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();

    }


//    public static void printTableWindow(JFrame frame, List<Location> listWithCoordinates) throws Exception {
//
//        if (listWithCoordinates == null) {
//            throw new Exception("Невозможно считать содерижиое файла");
//        }
//        Container container = frame.getContentPane();
//        container.removeAll();
//        container.setLayout(new BorderLayout());
//
//        frame.setResizable(true);
//        frame.setSize(1300, 700);
//
//        JTable table  = new JTable(); // = getjTable(listWithCoordinates);
//        JScrollPane scrollPane = new JScrollPane(table);
//        container.add(scrollPane, BorderLayout.CENTER);
//
//
//        List<Double> listXAxis = new ArrayList<>();
//        List<Double> listYAxis = new ArrayList<>();
//
//        StringBuilder nameOfXAxis = new StringBuilder();
//        StringBuilder nameOfYAxis = new StringBuilder();
//        JComboBox boxAxisX = ButtonFactory.createComboBoxChooseAxis(listXAxis, listWithCoordinates, nameOfXAxis);
//        JComboBox boxAxisY = ButtonFactory.createComboBoxChooseAxis(listYAxis, listWithCoordinates, nameOfYAxis);
//
//        JButton loadFileButton = ButtonFactory.createLoadFileButton(frame);
//
//        JButton graphButton = ButtonFactory.createGraphButton(frame, listXAxis, listYAxis, nameOfXAxis, nameOfYAxis, listWithCoordinates);
//
//
//
//        JPanel grid = new JPanel(new GridLayout(6, 1, 0, 5));
//
//        grid.add(loadFileButton);
//        grid.add(new Label("Ось X"));
//        grid.add(boxAxisX);
//        grid.add(new Label("Ось Y"));
//        grid.add(boxAxisY);
//        grid.add(graphButton);
//
//
//        container.add(grid, BorderLayout.WEST);
//        frame.revalidate();
//        frame.repaint();
//
//    }

    public static void printNewGraphWindow(JFrame frame, DBCConnector connector,
                                           XYDataset dataset, NamesOfAxes namesOfAxes) throws Throwable {
        frame.setSize(1000, 800);
        frame.setResizable(true);

        Container container = frame.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());

        JToolBar jToolBar = new JToolBar("Запросы");
        container.add(jToolBar, BorderLayout.NORTH);

        jToolBar.add(ButtonFactory.creteNewTablesButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewRouteButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewSkyPlotButton(frame, connector));
        jToolBar.add(new JButton("Графики"));

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


//    public static void printGraphWindow
//            (JFrame frame, List<Double> listXAxis,
//             List<Double> listYAxis, StringBuilder nameOfXAxis,
//             StringBuilder nameOfYAxis, List<Location> listWithCoordinates) {
//        Container container = frame.getContentPane();
//        container.removeAll();
//        container.setLayout(new BorderLayout());
//
//        XYDataset dataset = createDatasetForSpeedWithTime(listXAxis, listYAxis);
//
//        JFreeChart chart = ChartFactory.createScatterPlot(
//                "График зависимости",
//                nameOfXAxis.toString(),//x
//                nameOfYAxis.toString(),//y
//                dataset);
//        XYPlot plot = (XYPlot) chart.getPlot();
//        plot.setBackgroundPaint(new Color(196, 217, 255));
//
//        ChartPanel panel = new ChartPanel(chart);
//        container.add(panel, BorderLayout.CENTER);
//
//        JPanel grid = new JPanel(new GridLayout(2, 1, 0, 5));
//
//        JButton loadFileButton = ButtonFactory.createLoadFileButton(frame);
//        JButton tableBitton = ButtonFactory.createTable(frame, listWithCoordinates);
//
//        grid.add(loadFileButton);
//        grid.add(tableBitton);
//        container.add(grid, BorderLayout.WEST);
//
//
//        frame.revalidate();
//        frame.repaint();
//
//    }

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
        frame.setSize(1000, 800);
        frame.setResizable(true);

        Container container = frame.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());

        JToolBar jToolBar = new JToolBar("Запросы");
        container.add(jToolBar, BorderLayout.NORTH);

        jToolBar.add(ButtonFactory.creteNewTablesButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewRouteButton(frame, connector));
        jToolBar.add(new JButton("Скайплот"));
        jToolBar.add(ButtonFactory.createNewGraphbutton(frame, connector));

        JFreeChart chart = connector.getSputniksPosighions();

        container.add(new ChartPanel(chart), BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();

    }

    public static void printNewRotePrinter(JFrame frame, DBCConnector connector, XYDataset dataset) throws Throwable {
        frame.setSize(1000, 800);
        frame.setResizable(true);

        Container container = frame.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());

        JToolBar jToolBar = new JToolBar("Запросы");
        container.add(jToolBar, BorderLayout.NORTH);

        jToolBar.add(ButtonFactory.creteNewTablesButton(frame, connector));
        jToolBar.add(new JButton("Маршрут"));
        jToolBar.add(ButtonFactory.createNewSkyPlotButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewGraphbutton(frame, connector));


        JFreeChart chart = ChartFactory.createScatterPlot(
                "Маршрут",
                "latitude",//x
                "longitude",//y
                dataset);

        ChartPanel panel = new ChartPanel(chart);
        container.add(panel, BorderLayout.CENTER);

        //container.add(new ChartPanel(chart), BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }
}

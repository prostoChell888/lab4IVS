package org.example;

import org.example.newClasses.DBCConnector;
import org.example.newClasses.FilesHolder;
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

    public static void printNewTableWindow(JFrame frame, DBCConnector connector, JTable jTable, String defaultChoice)
            throws Throwable {
        Container container = setSize(frame);

        JToolBar jToolBar = new JToolBar("Запросы");
        container.add(jToolBar, BorderLayout.NORTH);

        jToolBar.add(new JButton("Таблица"));
        jToolBar.add(ButtonFactory.createNewRouteButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewSkyPlotButton(frame, connector));
        jToolBar.add(ButtonFactory.createNewGraphbutton(frame, connector));
        jToolBar.add(ButtonFactory.createLoadFileButton(frame, connector));

        JPanel jPanel = createSetingsPanel(frame, connector, defaultChoice);

        container.add(jPanel, BorderLayout.WEST);

        JScrollPane scrollPane = new JScrollPane(jTable);

        container.add(scrollPane, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    private static JPanel createSetingsPanel(JFrame frame, DBCConnector connector, String defaultChoice) {
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
}

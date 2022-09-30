package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FramePrinter {

    public static void printDownloudWindow(JFrame frame, String fileStatus) {
        frame.setSize(300, 200);
        frame.setResizable(false);
        frame.getRootPane().setBorder
                (BorderFactory.createEmptyBorder
                        (20, 10, 20, 10));

        // Панель содержимого
        Container container = frame.getContentPane();
        container.removeAll();
        // Устанавливаем менеджер последовательного расположения
        container.setLayout(new GridLayout
                (2, 0, 10, 50));

        JLabel jl = new JLabel("Выбирите файл");
        JButton buttonOpen = ButtomFactory.createButtonOpen(frame);
        JLabel status = new JLabel(fileStatus);

        container.add(jl);
        container.add(buttonOpen);
        container.add(status);

        frame.revalidate();
        frame.repaint();
    }


    public static void printTableWindow(JFrame frame, List<Location> listWithCoordinates) throws Exception {

        if (listWithCoordinates == null) {
            throw new Exception("Невозможно считать содерижиое файла");
        }
        Container container = frame.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());

        frame.setResizable(true);
        frame.setSize(1300, 700);

        JTable table = getjTable(listWithCoordinates);
        JScrollPane scrollPane = new JScrollPane(table);
        container.add(scrollPane, BorderLayout.CENTER);
        //container.add(scrollPane);

        List<Double> listXAxis = new ArrayList<>();
        List<Double> listYAxis = new ArrayList<>();

        StringBuilder nameOfXAxis = new StringBuilder();
        StringBuilder nameOfYAxis = new StringBuilder();
        JComboBox boxAxisX = ButtomFactory.createComboBoxChooseAxis(listXAxis, listWithCoordinates, nameOfXAxis);
        JComboBox boxAxisY = ButtomFactory.createComboBoxChooseAxis(listYAxis, listWithCoordinates, nameOfYAxis);

        JButton loadFileButton = ButtomFactory.createLoadFileButton(frame);

        JButton graphButton = ButtomFactory.createGraphButton(frame, listXAxis, listYAxis, nameOfXAxis, nameOfYAxis, listWithCoordinates);


        //var boxLayout = ;
        JPanel grid = new JPanel(new GridLayout(6, 1, 0, 5));

        grid.add(loadFileButton);
        grid.add(new Label("Ось X"));
        grid.add(boxAxisX);
        grid.add(new Label("Ось Y"));
        grid.add(boxAxisY);
        grid.add(graphButton);


        container.add(grid, BorderLayout.WEST);

//


        frame.revalidate();
        frame.repaint();

    }

    private static JTable getjTable(List<Location> listWithCoordinates) {

        String[] columnNames = {"Идентификационный Код",
                "Дата", "Время (часы)", "Широта(градусы)", "N/S", "Долгота(градусы)",
                "E/W", "Скорость (км/ч)",
                "Направление(градусы)", "Высота(градусы)",
        };

        Object[][] data = new Object[listWithCoordinates.size()][columnNames.length];

        for (int i = 0; i < listWithCoordinates.size(); i++) {
            data[i][0] = listWithCoordinates.get(i).getDeviceId();
            data[i][1] = listWithCoordinates.get(i).getDate();
            data[i][2] = listWithCoordinates.get(i).getTimeInHours();
            data[i][3] = listWithCoordinates.get(i).getLatitudeInDegrees();
            data[i][4] = listWithCoordinates.get(i).getN_s();
            data[i][5] = listWithCoordinates.get(i).getLongitudeInDegrees();
            data[i][6] = listWithCoordinates.get(i).getE_v();
            data[i][7] = listWithCoordinates.get(i).getSpeedInKilPerHour();
            data[i][8] = listWithCoordinates.get(i).getCourse();
            data[i][9] = listWithCoordinates.get(i).getAltitudeInDegrees();
        }

        return new JTable(data, columnNames);
    }

    public static void printGraphWindow
            (JFrame frame, List<Double> listXAxis,
             List<Double> listYAxis, StringBuilder nameOfXAxis,
             StringBuilder nameOfYAxis, List<Location> listWithCoordinates) {
        Container container = frame.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());

        XYDataset dataset = createDatasetForSpeedWithTime(listXAxis, listYAxis);

        JFreeChart chart = ChartFactory.createScatterPlot(
                "График зависимости",
                nameOfXAxis.toString(),//x
                nameOfYAxis.toString(),//y
                dataset);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(196, 217, 255));

        ChartPanel panel = new ChartPanel(chart);
        container.add(panel, BorderLayout.CENTER);

        JPanel grid = new JPanel(new GridLayout(2, 1, 0, 5));

        JButton loadFileButton = ButtomFactory.createLoadFileButton(frame);
        JButton tableBitton = ButtomFactory.createTable(frame, listWithCoordinates);

        grid.add(loadFileButton);
        grid.add(tableBitton);
        container.add(grid, BorderLayout.WEST);


        frame.revalidate();
        frame.repaint();

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
}

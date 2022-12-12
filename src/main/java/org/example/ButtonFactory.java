package org.example;

import org.example.newClasses.DBCConnector;
import org.example.newClasses.FilesHolder;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static java.awt.Component.LEFT_ALIGNMENT;

public class ButtonFactory {

//    public static JButton createTable(JFrame frame, List<Location> listWithCoordinates) {
//        JButton jButton = new JButton("Таблица");
//
//        jButton.addActionListener(x -> {
//            try {
//                FramePrinter.printTableWindow(frame, listWithCoordinates);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        return jButton;
//    }

    public static JButton createOpenButton(JFrame frame, FilesHolder holderFirstFile, DBCConnector dbConector) throws Exception {
        JButton jButton = new JButton("Открыть");

        jButton.addActionListener(x -> {
            try {

                //dbConector.addInfoFromRafFile(holderFirstFile.file); //todo раскоментировать
                dbConector.addIfoFromCSVFile(holderFirstFile.file);
                System.out.println("все ок");
                FramePrinter.printNewTableWindow(frame, dbConector, getCSV_table(dbConector), "GGA");
            } catch (Exception e) {
                try {
                    if (holderFirstFile.file == null) {
                        FramePrinter.printDownloudWindow(frame, "выбереите файл", dbConector);
                    } else
                        FramePrinter.printDownloudWindow(frame, "в файле отсутствует информация", dbConector);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        return jButton;
    }




    public static JButton createButtonChooseFile(JLabel jl, FilesHolder files) {
        JButton jButton = new JButton("Выбрать");

        jButton.addActionListener(x -> {
            String flag = x.getActionCommand();
            if (flag.equals("Выбрать")) {
                JFileChooser jC = new JFileChooser();
                int dialoVal = jC.showOpenDialog(null);
                //если выбор был сделан
                if (dialoVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        files.file = jC.getSelectedFile();
                        jl.setText(files.file.getName());
                    } catch (Exception e) {
                        jl.setText(e.getMessage());
                    }
                }
            }
        });

        return jButton;
    }

    public static JButton createLoadFileButton(JFrame frame, DBCConnector dbConector) {
        JButton jButton = new JButton("Выбрать файл");
        jButton.addActionListener(x -> {
            try {
                FramePrinter.printDownloudWindow(frame, "Файл не выбран", dbConector);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return jButton;
    }

    public static JButton creteNewTablesButton(JFrame frame, DBCConnector connector) {
        JButton jButton = new JButton("Таблицы");
        jButton.addActionListener(x -> {
            try {
                FramePrinter.printNewTableWindow(frame, connector,
                        getGGA_table(connector), "GGA");
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });


        return jButton;
    }


    public static JComboBox createFormatChoserButton(JFrame frame, DBCConnector connection, String defaultChoice) {
        var choose = new String[]{"GGA", "RMC", "GSA", "GSV"};
        JComboBox<String> comboBox = new JComboBox<>(choose);
        comboBox.setSelectedItem(defaultChoice);

        comboBox.addActionListener(al -> {
            JComboBox box = (JComboBox) al.getSource();
            String item = (String) box.getSelectedItem();

            JTable jTable = new JTable();
            try {
                switch (Objects.requireNonNull(item)) {
                    case "GGA":
                        jTable = getGGA_table(connection);
                        break;
                    case "RMC":
                        jTable = getRMC_table(connection);
                        break;
                    case "GSA":
                        jTable = getGSA_table(connection);
                        break;
                    case "GSV":
                        jTable = getGSV_table(connection);
                        break;
                }
                FramePrinter.printNewTableWindow(frame, connection, jTable, item);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
        Font font = new Font("Verdana", Font.PLAIN, 18);
        comboBox.setFont(font);
        comboBox.setAlignmentX(LEFT_ALIGNMENT);

        return comboBox;
    }


    private static JTable getGGA_table(DBCConnector connector) throws SQLException {
        String[] header = {"Время ч.", "Дата", "Широта гр.", "N/S Indicator", "Долгота", "E/W Indicator", "фиксация",
                "Используемы\n спутники", "Горизонтальное\n разбавление\n точности", "Units"
        };
        List<List<String>> data = connector.getGGA_inf();

        return getJTable(data, header);
    }

    private static JTable getCSV_table(DBCConnector connector) {
        String[] header = {"Дата", "Время ч.", "Скорость км/ч", "Долгота гр.", "Широта гр.", "Положение"};
        List<List<String>> data;
        try {
            data = connector.getCSV_inf();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return getJTable(data, header);
    }

    private static JTable getRMC_table(DBCConnector connector) throws SQLException {
        String[] header = {"Время ч.", "Дата", "Широта гр.", "N/S Indicator", "Долгота", "E/W Indicator",
                "Speed Over Ground", "Course Over Ground"
        };
        List<List<String>> data = connector.getRMC_inf();

        return getJTable(data, header);
    }

    private static JTable getGSA_table(DBCConnector connector) throws SQLException {
        String[] header = {"Время ч.", "Дата", "PDOP", "HDOP", "VDOP", "Режим 1", "Режим 2"};
        List<List<String>> data = connector.getGSA_inf();

        return getJTable(data, header);
    }

    private static JTable getGSV_table(DBCConnector connector) throws SQLException {
        String[] header = {"Время ч.", "Дата", "ID спутник", "азмут", "радиус", "Сила сигнала"};
        List<List<String>> data = connector.getGSV_inf();

        return getJTable(data, header);
    }

    private static JTable getJTable(List<List<String>> data, String[] handle) {
        var dataForTable = new String[data.size()][data.get(0).size()];

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(0).size(); j++) {
                dataForTable[i][j] = data.get(i).get(j);
            }
        }

        return new JTable(dataForTable, handle);
    }

    public static JComboBox<String> createComboBoxChooseAxis
            (JFrame frame, DBCConnector connection, NamesOfAxes namesOfAxes) {

        String[] items = {"latitude", "longitude", "MSL_altitude"};
        JComboBox<String> comboBox = new JComboBox<>(items);

        comboBox.setSelectedItem(namesOfAxes.getNameOfX());
        comboBox.setSelectedItem(namesOfAxes.getNameOfY());

        comboBox.addActionListener(e -> {
            JComboBox box = (JComboBox) e.getSource();
            String item = (String) box.getSelectedItem();

            namesOfAxes.setNameOfY(item);
            namesOfAxes.setNameOfX("UTC_date");

            try {
                List<Float> listXCord = connection.getLocationInf("UTC_date");
                List<Float> listYCord = connection.getLocationInf(item);
                XYDataset dataset = createDataset(listXCord, listYCord);

                FramePrinter.printNewGraphWindow(frame, connection, dataset, namesOfAxes);

            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
        });

        Font font = new Font("Verdana", Font.PLAIN, 18);
        comboBox.setFont(font);
        comboBox.setAlignmentX(LEFT_ALIGNMENT);

        return comboBox;
    }


    private static XYDataset createDataset
            (List<Float> listXAxis, List<Float> listYAxis) throws Throwable {
        if (listXAxis.size() != listYAxis.size()) {
            throw new Throwable("Данные листы не сопастовимы");
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("");

        for (int i = 0; i < listXAxis.size(); i++) {
            if (listXAxis.get(i) != 0 && listYAxis.get(i) != 0){
                series.add(listXAxis.get(i), listYAxis.get(i));
            }
        }

        dataset.addSeries(series);
        return dataset;
    }

//    public static JButton createGraphButton
//            (JFrame frame, List<Double> listXAxis,
//             List<Double> listYAxis, StringBuilder nameOfXAxis,
//             StringBuilder nameOfYAxis, List<Location> listWithCoordinates) {
//
//        JButton jButton = new JButton("Графики");
//
//        ActionListener actionListener = e -> {
//            FramePrinter.printGraphWindow(frame, listXAxis,
//                    listYAxis, nameOfXAxis, nameOfYAxis, listWithCoordinates);
//        };
//        jButton.addActionListener(actionListener);
//
//        return jButton;
//    }

    public static JButton createNewGraphbutton(JFrame frame, DBCConnector connector) throws Throwable {
        JButton jButton = new JButton("Графики");

        NamesOfAxes namesOfAxes = new NamesOfAxes("latitude", "UTC_date");

        List<Float> listXCord = connector.getLocationInf("UTC_date");
        List<Float> listYCord = connector.getLocationInf("latitude");
        XYDataset dataset = createDataset(listXCord, listYCord);

        jButton.addActionListener(x ->
        {
            try {
                FramePrinter.printNewGraphWindow(frame, connector, dataset, namesOfAxes);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        return jButton;
    }


    public static JButton createNewSkyPlotButton(JFrame frame, DBCConnector connector) {
        var jButton = new JButton("Скайплот");

        jButton.addActionListener(x -> {
            try {
                FramePrinter.printNewSkyplotWindow(frame, connector);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        return jButton;
    }

    public static JButton createNewRouteButton(JFrame frame, DBCConnector connector) throws Throwable {
        var jButton = new JButton("Маршрут");

        List<Float> listXCord = connector.getLocationInf("longitude");
        List<Float> listYCord = connector.getLocationInf("latitude");
        XYDataset dataset = createDataset(listXCord, listYCord);

        jButton.addActionListener(x -> {
            try {
                FramePrinter.printNewRotePrinter(frame, connector, dataset);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        return jButton;

    }
}

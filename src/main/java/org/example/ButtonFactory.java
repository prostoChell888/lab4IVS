package org.example;

import org.example.notUse.Location;
import org.example.newClasses.DBCConnector;
import org.example.newClasses.FilesHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static java.awt.Component.LEFT_ALIGNMENT;

public class ButtonFactory {

    public static JButton createTable(JFrame frame, List<Location> listWithCoordinates) {
        JButton jButton = new JButton("Таблица");

        jButton.addActionListener(x -> {
            try {
                FramePrinter.printTableWindow(frame, listWithCoordinates);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return jButton;
    }

    public static JButton createOpenButton(JFrame frame, FilesHolder holderFirstFile) throws Exception {
        JButton jButton = new JButton("Открыть");

        jButton.addActionListener(x -> {
            String DB_USERNAME = "postgres";
            String DB_PASSWORD = "123";
            String DB_URL = "jdbc:postgresql://localhost:5432/interfacec2";

            try {
                DBCConnector dbConector = new DBCConnector(DB_URL, DB_USERNAME, DB_PASSWORD);
                //dbConector.addInfoFromRafFile(holderFirstFile.file); todo раскоментировать
                System.out.println("все ок");
                FramePrinter.printNewTableWindow(frame, dbConector, new JTable());
            } catch (Exception e) {
                try {
                    if (holderFirstFile.file == null) {
                        FramePrinter.printDownloudWindow(frame, "выбереите файл");
                    } else
                        FramePrinter.printDownloudWindow(frame, "в файле отсутствует информация");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
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

    public static JButton createLoadFileButton(JFrame frame) {
        JButton jButton = new JButton("Выбрать файл");
        jButton.addActionListener(x -> {
            try {
                FramePrinter.printDownloudWindow(frame, "Файл не выбран");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return jButton;
    }


    public static JComboBox createFormatChoserButton(JFrame frame, DBCConnector connection) {
        var choose = new String[]{"GGA", "RMC", "GSA", "GSV"};
        JComboBox<String> comboBox = new JComboBox<>(choose);

        comboBox.addActionListener(al -> {
            JComboBox box = (JComboBox) al.getSource();
            String item = (String) box.getSelectedItem();
            JTable jTable = new JTable();
            try {
                switch (Objects.requireNonNull(item)) {
                    case "GGA": jTable = getGGA_table(connection);break;
                    case "RMC": jTable = getRMC_table(connection);break;
                    case "GSA": jTable = getGSA_table(connection);break;
                    case "GSV": jTable = getGSV_table(connection);break;
                }

                FramePrinter.printNewTableWindow(frame, connection, jTable);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return comboBox;
    }



    private static JTable getGGA_table(DBCConnector connector) throws SQLException {
        String[] header = {"Время ч.", "Дата", "Широта гр.", "N/S Indicator", "Долгота", "E/W Indicator", "фиксация",
                "Используемы\n спутники", "Горизонтальное\n разбавление\n точности", "Units"
        };
        List<List<String>> data = connector.getGGA_inf();

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
            (List<Double> cords, List<Location> locationList, StringBuilder nameOfAxis) {
        Font font = new Font("Verdana", Font.PLAIN, 18);

        String[] items = {
                "Направление",
                "Время",
                "Широта",
                "Долгота",
                "Высота",
                "скорость"
        };

        ActionListener actionListener = e -> {
            JComboBox box = (JComboBox) e.getSource();
            String item = (String) box.getSelectedItem();
            nameOfAxis.setLength(0);
            nameOfAxis.append(item);
            switch (item) {
                case "Время":

                    for (var cord : locationList)
                        cords.add(cord.getTimeInHours());
                    break;
                case "Широта":
                    for (var cord : locationList)
                        cords.add(cord.getLatitudeInDegrees());
                    break;
                case "Долгота":
                    for (var cord : locationList)
                        cords.add(cord.getLongitudeInDegrees());
                    break;
                case "Высота":
                    for (var cord : locationList)
                        cords.add(cord.getAltitudeInDegrees());
                    break;
                case "Направление":
                    for (var cord : locationList)
                        cords.add(cord.getCourse());
                    break;
                case "скорость":
                    for (var cord : locationList)
                        cords.add(cord.getSpeedInKilPerHour());
                    break;
            }
        };

        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(font);
        comboBox.setAlignmentX(LEFT_ALIGNMENT);
        comboBox.addActionListener(actionListener);

        return comboBox;
    }

    public static JButton createGraphButton
            (JFrame frame, List<Double> listXAxis,
             List<Double> listYAxis, StringBuilder nameOfXAxis,
             StringBuilder nameOfYAxis, List<Location> listWithCoordinates) {

        JButton jButton = new JButton("Построить график");

        ActionListener actionListener = e -> {
            FramePrinter.printGraphWindow(frame, listXAxis,
                    listYAxis, nameOfXAxis, nameOfYAxis, listWithCoordinates);

        };
        jButton.addActionListener(actionListener);


        return jButton;
    }
}

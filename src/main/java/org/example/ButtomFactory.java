package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import static java.awt.Component.LEFT_ALIGNMENT;

public class ButtomFactory {

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


    public static JButton createButtonOpen(JFrame frame) {
        JButton jButton = new JButton("Открыть");

        jButton.addActionListener(x -> {
            String flag = x.getActionCommand();
            if (flag.equals("Открыть")) {
                JFileChooser jC = new JFileChooser();
                int dialoVal = jC.showOpenDialog(null);
                //если выбор был сделан
                if (dialoVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        File chosenFile = jC.getSelectedFile();
                        List<Location> listWithCoordinates =
                                CordParser.parseTxtFile(chosenFile);
                        FramePrinter.printTableWindow(frame, listWithCoordinates);

                    } catch (Exception e) {
                        FramePrinter.printDownloudWindow(frame, e.getMessage());
                    }
                }
            }
        });

        return jButton;
    }

    public static JButton createLoadFileButton(JFrame frame) {
        JButton jButton = new JButton("Выбрать файл");
        jButton.addActionListener(x -> {
            FramePrinter.printDownloudWindow(frame, "Файл н евыбран");
        });
        return jButton;
    }


    public static JComboBox createComboBoxChooseAxis
            (List<Double> cords, List<Location> locationList, StringBuilder nameOfAxis) {
        Font font = new Font("Verdana", Font.PLAIN, 18);

        String[] items = {
                "Направление (градусы)",
                "Время (часы)",
                "Широта (градусы)",
                "Долгота (градусы)",
                "Высота (градусы)",
                "скорость (км/ч)"
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

        JComboBox comboBox = new JComboBox(items);
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

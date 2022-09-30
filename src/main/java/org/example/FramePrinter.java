package org.example;

import javax.swing.*;
import java.awt.*;
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

       if (listWithCoordinates == null){
            throw new Exception("Невозможно считать содерижиое файла");
        }
        Container container= frame.getContentPane();
        container.removeAll();
        container.setLayout(new BorderLayout());

        frame.setResizable(true);
        frame.setSize(1300, 700);

        JTable table = getjTable(listWithCoordinates);
        JScrollPane scrollPane = new JScrollPane(table);
        container.add(scrollPane, BorderLayout.CENTER);


//        JPanel grid = new JPanel(new GridLayout(1, 2, 5, 0) );
//        JPanel flow = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        flow.add(grid);
//        flow.setPreferredSize(new Dimension(100,10));


        frame.revalidate();
        frame.repaint();

    }

    private static JTable getjTable(List<Location> listWithCoordinates) {

        String[] columnNames = { "Идентификационный Код",
                "Дата", "Время (часы)","Широта(градусы)","N/S","Долгота(градусы)",
                "E/W","Скорость (км/ч)",
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
}

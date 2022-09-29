package org.example;

import javax.swing.*;
import java.awt.*;

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


}

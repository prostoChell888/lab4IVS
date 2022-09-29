package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ButtomFactory {


    public static JButton createButtonOpen(JFrame frame) {
        JButton jButton = new JButton("Открыть");

        jButton.addActionListener(x -> {
            String flag = x.getActionCommand();
            if (flag.equals("Открыть")) {
                JFileChooser jC = new JFileChooser();
                int dialoVal = jC.showOpenDialog(null);
                //если выбор был сделан
                if (dialoVal == JFileChooser.APPROVE_OPTION) {
                    File chosenFile = jC.getSelectedFile();
                    try {
                        List<Cordinates> listWithCordinates =
                                CoordParser.parseTxtFile(fileWithCoordinates);
                        FramePrinter.printTableWindow(listWithCordinates);

                    }catch (Exception e) {
                        FramePrinter.printDownloudWindow(frame, e.getMessage());
                    }


                }
            }
        });

       return  jButton;
    }


}

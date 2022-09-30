package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

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
                    try {
                        File chosenFile = jC.getSelectedFile();
                        List<Location> listWithCoordinates =
                                CordParser.parseTxtFile(chosenFile);
                        FramePrinter.printTableWindow(frame, listWithCoordinates);

                    }catch (Exception e) {
                        FramePrinter.printDownloudWindow(frame, e.getMessage());
                    }


                }
            }
        });

       return  jButton;
    }


}

package org.example;

import org.example.newClasses.DBCConnector;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

public class App extends JFrame {


    public App() throws Throwable {
        super("ИВС");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String DB_USERNAME = "postgres";
        String DB_PASSWORD = "123";
        String DB_URL = "jdbc:postgresql://localhost:5432/interfacec2";
        DBCConnector dbConector = new DBCConnector(DB_URL, DB_USERNAME, DB_PASSWORD);

        super.addWindowListener(new WindowListener() {

            public void windowActivated(WindowEvent event) {}

            public void windowClosed(WindowEvent event) {}

            public void windowClosing(WindowEvent event) {
                Object[] options = { "Да", "Нет" };
                int n = JOptionPane
                        .showOptionDialog(event.getWindow(), "Сохранить загруженные данные?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if (n != 0) {
                    try {
                        dbConector.deletingTableData();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("действаие при нажатии Нет");
                }
                event.getWindow().setVisible(false);
                System.exit(0);
            }

            public void windowDeactivated(WindowEvent event) {}

            public void windowDeiconified(WindowEvent event) {}

            public void windowIconified(WindowEvent event) {}

            public void windowOpened(WindowEvent event) {}

        });
        if (dbConector.isAvailabilityOfData()){
            FramePrinter.printNewTableWindow
                    (this, dbConector, ButtonFactory.getGGA_table(dbConector), "GGA");
        }
        else {
            FramePrinter.printDownloudWindow(this, "файл не выбран", dbConector);
        }

    }
}

package org.example;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {


    public App() throws Exception {
        super("ИВС");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FramePrinter.printDownloudWindow(this, "файл не выбран");
    }
}

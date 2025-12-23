package main;

import view.MainFrame;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Menjalankan GUI di Event Dispatch Thread (Best Practice)
        SwingUtilities.invokeLater(() -> {
            MainFrame app = new MainFrame();
            app.setVisible(true);
        });
    }
}
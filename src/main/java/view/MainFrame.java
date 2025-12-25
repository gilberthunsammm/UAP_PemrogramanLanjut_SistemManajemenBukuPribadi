package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Sistem Manajemen Buku Pribadi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // ---  PANEL BARU ---
        mainPanel.add(new LoginPanel(this), "login");       // <--- HALAMAN 1
        mainPanel.add(new RegisterPanel(this), "register"); // <--- HALAMAN 2

        mainPanel.add(new DashboardPanel(this), "dashboard");
        mainPanel.add(new FormBukuPanel(this), "input");
        mainPanel.add(new ListBukuPanel(this), "list");
        mainPanel.add(new HistoryPanel(this), "history");

        add(mainPanel, BorderLayout.CENTER);

        // ---  DEFAULT MUNCUL LOGIN DULUAN ---
        cardLayout.show(mainPanel, "login");
    }

    public void showView(String viewName) {
        cardLayout.show(mainPanel, viewName);

        // Auto Refresh Logic
        if (viewName.equals("history")) {
            for (Component comp : mainPanel.getComponents()) {
                if (comp instanceof HistoryPanel) ((HistoryPanel) comp).updateLaporan();
            }
        } else if (viewName.equals("list")) {
            for (Component comp : mainPanel.getComponents()) {
                if (comp instanceof ListBukuPanel) ((ListBukuPanel) comp).loadData();
            }
        }
    }
}
package view;

import model.Buku;
import util.CsvHandler;
import util.LogHandler; // Wajib Import LogHandler
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;

public class HistoryPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextArea txtSummary;
    private JTable tableLog;
    private DefaultTableModel logModel;
    private Image backgroundImage;

    public HistoryPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        try {
            URL imgUrl = getClass().getResource("/bacgggground.jpg");
            if (imgUrl != null) backgroundImage = ImageIO.read(imgUrl);
        } catch (Exception e) { e.printStackTrace(); }

        setLayout(new GridBagLayout());

        JPanel glassPanel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(64, 64, 64, 200));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        glassPanel.setOpaque(false);
        glassPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        glassPanel.setPreferredSize(new Dimension(750, 550));

        // Judul
        JLabel lblTitle = new JLabel("Laporan & Log Aktivitas", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        glassPanel.add(lblTitle, BorderLayout.NORTH);

        // Panel Tengah
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setOpaque(false);

        // Statistik
        txtSummary = new JTextArea();
        txtSummary.setEditable(false);
        txtSummary.setFont(new Font("Monospaced", Font.BOLD, 14));
        centerPanel.add(new JScrollPane(txtSummary));

        // Tabel Log
        String[] columns = {"Waktu", "Aktivitas", "Detail Buku"};
        logModel = new DefaultTableModel(columns, 0);
        tableLog = new JTable(logModel);
        tableLog.setRowHeight(25);
        centerPanel.add(new JScrollPane(tableLog));

        glassPanel.add(centerPanel, BorderLayout.CENTER);

        // Tombol
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setOpaque(false);

        JButton btnRefresh = new JButton("Refresh Data");
        btnRefresh.setBackground(new Color(46, 204, 113));
        btnRefresh.setForeground(Color.WHITE);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.setBackground(new Color(231, 76, 60));
        btnKembali.setForeground(Color.WHITE);

        btnRefresh.addActionListener(e -> updateLaporan());
        btnKembali.addActionListener(e -> mainFrame.showView("dashboard"));

        btnPanel.add(btnRefresh);
        btnPanel.add(btnKembali);
        glassPanel.add(btnPanel, BorderLayout.SOUTH);

        add(glassPanel);
    }

    public void updateLaporan() {
        // 1. Update Statistik
        CsvHandler handler = new CsvHandler();
        List<Buku> list = handler.load();
        long selesai = list.stream().filter(b -> b.getStatus().equalsIgnoreCase("Selesai")).count();
        long sedang = list.stream().filter(b -> b.getStatus().equalsIgnoreCase("Sedang Dibaca")).count();
        long belum = list.size() - selesai - sedang;

        StringBuilder sb = new StringBuilder();
        sb.append("📊 RINGKASAN DATA\n=================\n");
        sb.append("Total Koleksi : ").append(list.size()).append(" Buku\n");
        sb.append("Selesai Baca  : ").append(selesai).append("\n");
        sb.append("Sedang Baca   : ").append(sedang).append("\n");
        sb.append("Belum Baca    : ").append(belum).append("\n");
        txtSummary.setText(sb.toString());

        // 2. Update Log
        logModel.setRowCount(0);
        List<String[]> logs = LogHandler.readLogs();

        // Loop Terbalik (Paling baru di atas)
        for (int i = logs.size() - 1; i >= 0; i--) {
            logModel.addRow(logs.get(i));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        else { g.setColor(Color.DARK_GRAY); g.fillRect(0, 0, getWidth(), getHeight()); }
    }
}
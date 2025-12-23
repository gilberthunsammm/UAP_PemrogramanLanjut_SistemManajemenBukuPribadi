package view;

import model.Buku;
import util.CsvHandler;
import util.LogHandler; // Wajib Import LogHandler

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;

public class ListBukuPanel extends JPanel {
    private MainFrame mainFrame;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtSearch;
    private Image backgroundImage;

    public ListBukuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Load Background
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
        glassPanel.setPreferredSize(new Dimension(750, 500));

        // Header (Search & Tombol)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);

        JLabel lblSearch = new JLabel("Cari:");
        lblSearch.setForeground(Color.WHITE);
        txtSearch = new JTextField(15);

        JButton btnSearch = new JButton("Cari");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnHapus = new JButton("Hapus");
        JButton btnKembali = new JButton("Kembali");

        styleButton(btnSearch, new Color(52, 152, 219));
        styleButton(btnRefresh, new Color(46, 204, 113));
        styleButton(btnHapus, new Color(192, 57, 43)); // Merah
        styleButton(btnKembali, Color.GRAY);

        topPanel.add(lblSearch);
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnRefresh);
        topPanel.add(btnHapus);
        topPanel.add(btnKembali);

        glassPanel.add(topPanel, BorderLayout.NORTH);

        // Tabel
        String[] columnNames = {"ID", "Judul", "Penulis", "Penerbit", "Tahun", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        glassPanel.add(scrollPane, BorderLayout.CENTER);

        // Events
        btnRefresh.addActionListener(e -> loadData());
        btnKembali.addActionListener(e -> mainFrame.showView("dashboard"));

        btnSearch.addActionListener(e -> {
            String text = txtSearch.getText();
            if (text.trim().length() == 0) sorter.setRowFilter(null);
            else sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        });

        // --- LOGIKA HAPUS BUKU (DENGAN LOG) ---
        btnHapus.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Yakin hapus buku ini?", "Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    String idToDelete = (String) tableModel.getValueAt(modelRow, 0);
                    // Ambil Judul untuk dicatat di Log
                    String judulToDelete = (String) tableModel.getValueAt(modelRow, 1);

                    CsvHandler handler = new CsvHandler();
                    List<Buku> allBooks = handler.load();
                    allBooks.removeIf(b -> b.getId().equals(idToDelete));
                    handler.save(allBooks);

                    // --- [FIX] MENCATAT LOG PENGHAPUSAN ---
                    LogHandler.addLog("HAPUS BUKU", "Menghapus: " + judulToDelete);
                    // --------------------------------------

                    JOptionPane.showMessageDialog(this, "Data dihapus!");
                    loadData();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(glassPanel);
        loadData();
    }

    public void loadData() {
        tableModel.setRowCount(0);
        CsvHandler handler = new CsvHandler();
        List<Buku> list = handler.load();
        for (Buku b : list) {
            tableModel.addRow(new Object[]{b.getId(), b.getJudul(), b.getPenulis(), b.getPenerbit(), b.getTahun(), b.getStatus()});
        }
    }

    private void styleButton(JButton btn, Color c) {
        btn.setBackground(c);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        else { g.setColor(Color.DARK_GRAY); g.fillRect(0, 0, getWidth(), getHeight()); }
    }
}
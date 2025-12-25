package view;

import model.Buku;
import util.CsvHandler;
import util.LogHandler; // Wajib Import LogHandler
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import javax.imageio.ImageIO;

public class FormBukuPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField txtJudul, txtPenulis, txtPenerbit, txtTahun;
    private JComboBox<String> cmbStatus;
    private Image backgroundImage;

    public FormBukuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Load Background
        try {
            URL imgUrl = getClass().getResource("/bacgggground.jpg");
            if (imgUrl != null) backgroundImage = ImageIO.read(imgUrl);
        } catch (Exception e) { e.printStackTrace(); }

        setLayout(new GridBagLayout());

        // Panel Kaca
        JPanel glassPanel = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(64, 64, 64, 200));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        glassPanel.setOpaque(false);
        glassPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        glassPanel.setPreferredSize(new Dimension(500, 450));

        // Judul
        JLabel title = new JLabel("Tambah Buku Baru", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        glassPanel.add(title, BorderLayout.NORTH);

        // Form Input
        JPanel formGrid = new JPanel(new GridLayout(5, 2, 10, 15));
        formGrid.setOpaque(false);

        formGrid.add(createLabel("Judul Buku:"));
        txtJudul = new JTextField();
        formGrid.add(txtJudul);

        formGrid.add(createLabel("Penulis:"));
        txtPenulis = new JTextField();
        formGrid.add(txtPenulis);

        formGrid.add(createLabel("Penerbit:"));
        txtPenerbit = new JTextField();
        formGrid.add(txtPenerbit);

        formGrid.add(createLabel("Tahun Terbit:"));
        txtTahun = new JTextField();
        formGrid.add(txtTahun);

        formGrid.add(createLabel("Status:"));
        String[] statusOptions = {"Belum Dibaca", "Sedang Dibaca", "Selesai"};
        cmbStatus = new JComboBox<>(statusOptions);
        formGrid.add(cmbStatus);

        glassPanel.add(formGrid, BorderLayout.CENTER);

        // Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton btnSimpan = new JButton("Simpan");
        styleButton(btnSimpan, new Color(46, 204, 113));

        JButton btnKembali = new JButton("Kembali");
        styleButton(btnKembali, new Color(231, 76, 60));

        // --- AKSI SIMPAN ---
        btnSimpan.addActionListener(e -> simpanData());
        btnKembali.addActionListener(e -> mainFrame.showView("dashboard"));

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnKembali);
        glassPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(glassPanel);
    }

    private void simpanData() {
        try {
            String judul = txtJudul.getText();
            String penulis = txtPenulis.getText();
            String penerbit = txtPenerbit.getText();

            if (judul.isEmpty() || penulis.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Judul dan Penulis wajib diisi!");
                return;
            }

            int tahun = Integer.parseInt(txtTahun.getText());
            String status = (String) cmbStatus.getSelectedItem();
            String id = UUID.randomUUID().toString().substring(0, 8);

            Buku bukuBaru = new Buku(id, judul, penulis, penerbit, tahun, status);
            CsvHandler handler = new CsvHandler();

            List<Buku> currentList = handler.load();
            currentList.add(bukuBaru);
            handler.save(currentList);


            // Mencatat ke LogHandler setelah simpan berhasil
            LogHandler.addLog("TAMBAH BUKU", "Input buku: " + judul + " (" + penulis + ")");
            // ------------------------------------------

            JOptionPane.showMessageDialog(this, "Berhasil menyimpan buku!");
            resetForm();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Tahun harus angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        txtJudul.setText("");
        txtPenulis.setText("");
        txtPenerbit.setText("");
        txtTahun.setText("");
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return lbl;
    }

    private void styleButton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(44, 62, 80));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
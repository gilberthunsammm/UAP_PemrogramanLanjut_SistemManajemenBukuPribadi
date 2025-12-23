package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class DashboardPanel extends JPanel {
    private Image backgroundImage;

    public DashboardPanel(MainFrame mainFrame) {
        // --- BAGIAN LOAD GAMBAR ---
        try {
            // SAYA SUDAH GANTI NAMA FILENYA DI SINI:
            URL imgUrl = getClass().getResource("/bacgggground.jpg");

            if (imgUrl == null) {
                System.err.println("⚠️ ERROR: File 'bacgggground.jpg' tidak ditemukan!");
                System.err.println("Pastikan file ada di folder: src/main/resources/");
            } else {
                backgroundImage = ImageIO.read(imgUrl);
                System.out.println("✅ SUKSES: Background berhasil dimuat!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Layout Utama
        setLayout(new GridBagLayout());

        // --- PANEL KACA (Transparan) ---
        JPanel glassPanel = new JPanel(new GridLayout(5, 1, 15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                // Warna Abu-abu Transparan (R, G, B, Alpha)
                g.setColor(new Color(64, 64, 64, 180));
                // Sudut melengkung (Rounded)
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };

        glassPanel.setOpaque(false); // Agar background belakang terlihat
        glassPanel.setBorder(new EmptyBorder(30, 40, 30, 40)); // Padding dalam kotak

        // --- ISI MENU ---
        JLabel title = new JLabel("Perpustakaan Pribadi", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        JButton btnInput = createStyledButton("Tambah Buku Baru");
        JButton btnList = createStyledButton("Lihat Daftar Buku");
        JButton btnHistory = createStyledButton("Laporan / History");
        JButton btnExit = createStyledButton("Keluar");

        // Navigasi
        btnInput.addActionListener(e -> mainFrame.showView("input"));
        btnList.addActionListener(e -> mainFrame.showView("list"));
        btnHistory.addActionListener(e -> mainFrame.showView("history"));
        btnExit.addActionListener(e -> System.exit(0));

        glassPanel.add(title);
        glassPanel.add(btnInput);
        glassPanel.add(btnList);
        glassPanel.add(btnHistory);
        glassPanel.add(btnExit);

        add(glassPanel);
    }

    // --- MENGGAMBAR BACKGROUND ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Jika gambar ketemu, tampilkan full screen
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Jika gambar GAGAL/ERROR, pakai warna gradasi keren ini sebagai cadangan
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint(0, 0, new Color(44, 62, 80), getWidth(), getHeight(), new Color(76, 161, 175));
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Helper untuk mempercantik tombol
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(new Color(50, 50, 50));
        btn.setBackground(new Color(245, 245, 245));
        btn.setFocusPainted(false);
        return btn;
    }
}
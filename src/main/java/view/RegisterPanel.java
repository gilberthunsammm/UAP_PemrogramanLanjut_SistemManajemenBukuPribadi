package view;

import util.UserHandler;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;

public class RegisterPanel extends JPanel {
    private Image backgroundImage;
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public RegisterPanel(MainFrame mainFrame) {
        try {
            URL imgUrl = getClass().getResource("/bacgggground.jpg");
            if (imgUrl != null) backgroundImage = ImageIO.read(imgUrl);
        } catch (Exception e) { e.printStackTrace(); }

        setLayout(new GridBagLayout());

        JPanel glassPanel = new JPanel(new GridLayout(6, 1, 10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(64, 64, 64, 200));
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
            }
        };
        glassPanel.setOpaque(false);
        glassPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        glassPanel.setPreferredSize(new Dimension(400, 350));

        JLabel lblTitle = new JLabel("DAFTAR AKUN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        JButton btnSimpan = new JButton("Simpan");
        styleButton(btnSimpan, new Color(46, 204, 113));

        JButton btnKembali = new JButton("Batal");
        styleButton(btnKembali, new Color(231, 76, 60));

        // LOGIKA REGISTER
        btnSimpan.addActionListener(e -> {
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!");
                return;
            }

            if (UserHandler.register(user, pass)) {
                JOptionPane.showMessageDialog(this, "Registrasi Sukses! Silakan Login.");
                txtUsername.setText("");
                txtPassword.setText("");
                mainFrame.showView("login"); // Balik ke Login
            } else {
                JOptionPane.showMessageDialog(this, "Username sudah terpakai!");
            }
        });

        btnKembali.addActionListener(e -> mainFrame.showView("login"));

        glassPanel.add(lblTitle);
        glassPanel.add(createLabel("Username Baru:"));
        glassPanel.add(txtUsername);
        glassPanel.add(createLabel("Password Baru:"));
        glassPanel.add(txtPassword);

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSimpan);
        btnPanel.add(btnKembali);
        glassPanel.add(btnPanel);

        add(glassPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return l;
    }

    private void styleButton(JButton btn, Color c) {
        btn.setBackground(c);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
    }
}
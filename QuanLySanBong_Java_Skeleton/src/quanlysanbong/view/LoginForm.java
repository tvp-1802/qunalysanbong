package quanlysanbong.view;

import quanlysanbong.dao.TaiKhoanDAO;
import quanlysanbong.model.TaiKhoan;
import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public LoginForm() {
        setTitle("Đăng nhập - Quản lý sân bóng");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        panel.add(new JLabel("Tên đăng nhập:"));
        txtTenDangNhap = new JTextField();
        panel.add(txtTenDangNhap);

        panel.add(new JLabel("Mật khẩu:"));
        txtMatKhau = new JPasswordField();
        panel.add(txtMatKhau);

        btnDangNhap = new JButton("Đăng nhập");
        panel.add(new JLabel());
        panel.add(btnDangNhap);

        btnDangNhap.addActionListener(e -> xuLyDangNhap());

        add(panel);
    }

    private void xuLyDangNhap() {
        String tenDN = txtTenDangNhap.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());

        if (tenDN.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        TaiKhoan tk = taiKhoanDAO.dangNhap(tenDN, matKhau);
        if (tk != null) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            new MainForm(tk).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản, mật khẩu hoặc tài khoản đã bị khóa!");
        }
    }
}

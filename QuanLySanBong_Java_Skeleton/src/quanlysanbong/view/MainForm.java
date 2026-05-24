package quanlysanbong.view;

import quanlysanbong.model.TaiKhoan;
import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {
    private TaiKhoan taiKhoanDangNhap;

    public MainForm(TaiKhoan tk) {
        this.taiKhoanDangNhap = tk;
        setTitle("Phần mềm quản lý sân bóng");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel menu = new JPanel(new GridLayout(8, 1, 8, 8));
        menu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnTaiKhoan = new JButton("Quản lý tài khoản");
        JButton btnSanBong = new JButton("Quản lý sân bóng");
        JButton btnKhachHang = new JButton("Quản lý khách hàng");
        JButton btnDatSan = new JButton("Quản lý đặt sân");
        JButton btnHoaDon = new JButton("Quản lý hóa đơn");
        JButton btnDoanhThu = new JButton("Thống kê doanh thu");
        JButton btnDangXuat = new JButton("Đăng xuất");

        menu.add(new JLabel("Xin chào: " + taiKhoanDangNhap.getHoTen()));
        menu.add(btnTaiKhoan);
        menu.add(btnSanBong);
        menu.add(btnKhachHang);
        menu.add(btnDatSan);
        menu.add(btnHoaDon);
        menu.add(btnDoanhThu);
        menu.add(btnDangXuat);

        if (!"Quản lý".equalsIgnoreCase(taiKhoanDangNhap.getVaiTro())) {
            btnTaiKhoan.setEnabled(false);
            btnSanBong.setEnabled(false);
            btnDoanhThu.setEnabled(false);
        }

        btnTaiKhoan.addActionListener(e -> new TaiKhoanForm().setVisible(true));
        btnSanBong.addActionListener(e -> new SanBongForm().setVisible(true));
        btnKhachHang.addActionListener(e -> new KhachHangForm().setVisible(true));
        btnDatSan.addActionListener(e -> new LichDatForm(taiKhoanDangNhap).setVisible(true));
        btnHoaDon.addActionListener(e -> new HoaDonForm().setVisible(true));
        btnDoanhThu.addActionListener(e -> new DoanhThuForm().setVisible(true));
        btnDangXuat.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });

        add(menu, BorderLayout.WEST);
        add(new JLabel("HỆ THỐNG QUẢN LÝ SÂN BÓNG", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}

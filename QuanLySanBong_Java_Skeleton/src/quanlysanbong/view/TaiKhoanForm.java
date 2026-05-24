package quanlysanbong.view;

import quanlysanbong.dao.TaiKhoanDAO;
import quanlysanbong.model.TaiKhoan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TaiKhoanForm extends JFrame {
    private JTextField txtMaTK, txtTenDangNhap, txtHoTen, txtSDT, txtEmail, txtTimKiem;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboVaiTro, cboTrangThai;
    private JTable tblTaiKhoan;
    private DefaultTableModel model;
    private TaiKhoanDAO dao = new TaiKhoanDAO();

    public TaiKhoanForm() {
        setTitle("Quản lý tài khoản");
        setSize(850, 500);
        setLocationRelativeTo(null);
        initComponents();
        loadTable();
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridLayout(8, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin tài khoản"));

        txtMaTK = new JTextField(); txtMaTK.setEditable(false);
        txtTenDangNhap = new JTextField();
        txtMatKhau = new JPasswordField();
        txtHoTen = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();
        cboVaiTro = new JComboBox<>(new String[]{"Quản lý", "Nhân viên"});
        cboTrangThai = new JComboBox<>(new String[]{"Hoạt động", "Khóa"});

        form.add(new JLabel("Mã TK:")); form.add(txtMaTK);
        form.add(new JLabel("Tên đăng nhập:")); form.add(txtTenDangNhap);
        form.add(new JLabel("Mật khẩu:")); form.add(txtMatKhau);
        form.add(new JLabel("Họ tên:")); form.add(txtHoTen);
        form.add(new JLabel("SĐT:")); form.add(txtSDT);
        form.add(new JLabel("Email:")); form.add(txtEmail);
        form.add(new JLabel("Vai trò:")); form.add(cboVaiTro);
        form.add(new JLabel("Trạng thái:")); form.add(cboTrangThai);

        model = new DefaultTableModel(new String[]{"Mã", "Tên đăng nhập", "Họ tên", "SĐT", "Email", "Vai trò", "Trạng thái"}, 0);
        tblTaiKhoan = new JTable(model);

        JPanel buttons = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");
        txtTimKiem = new JTextField(15);
        JButton btnTim = new JButton("Tìm");

        buttons.add(btnThem); buttons.add(btnSua); buttons.add(btnXoa); buttons.add(btnLamMoi);
        buttons.add(new JLabel("Từ khóa:")); buttons.add(txtTimKiem); buttons.add(btnTim);

        btnThem.addActionListener(e -> them());
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnLamMoi.addActionListener(e -> loadTable());
        btnTim.addActionListener(e -> loadTable(dao.timKiem(txtTimKiem.getText().trim())));

        tblTaiKhoan.getSelectionModel().addListSelectionListener(e -> chonDong());

        FormUiHelper.layoutCrudWithSplit(this, form, tblTaiKhoan, buttons, 300);
    }

    private TaiKhoan getFormData() {
        TaiKhoan tk = new TaiKhoan();
        if (!txtMaTK.getText().isEmpty()) tk.setMaTK(Integer.parseInt(txtMaTK.getText()));
        tk.setTenDangNhap(txtTenDangNhap.getText().trim());
        tk.setMatKhau(new String(txtMatKhau.getPassword()));
        tk.setHoTen(txtHoTen.getText().trim());
        tk.setSoDienThoai(txtSDT.getText().trim());
        tk.setEmail(txtEmail.getText().trim());
        tk.setVaiTro(cboVaiTro.getSelectedItem().toString());
        tk.setTrangThai(cboTrangThai.getSelectedItem().toString());
        return tk;
    }

    private void them() {
        TaiKhoan tk = getFormData();
        if (tk.getTenDangNhap().isEmpty() || tk.getMatKhau().isEmpty() || tk.getHoTen().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ tên đăng nhập, mật khẩu và họ tên!");
            return;
        }
        JOptionPane.showMessageDialog(this, dao.them(tk) ? "Thêm thành công!" : "Thêm thất bại!");
        loadTable();
    }

    private void sua() {
        TaiKhoan tk = getFormData();
        JOptionPane.showMessageDialog(this, dao.sua(tk) ? "Cập nhật thành công!" : "Cập nhật thất bại!");
        loadTable();
    }

    private void xoa() {
        if (txtMaTK.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, dao.xoa(Integer.parseInt(txtMaTK.getText())) ? "Xóa thành công!" : "Xóa thất bại!");
            loadTable();
        }
    }

    private void loadTable() {
        loadTable(dao.layDanhSach());
    }

    private void loadTable(List<TaiKhoan> list) {
        model.setRowCount(0);
        for (TaiKhoan tk : list) {
            model.addRow(new Object[]{
                tk.getMaTK(), tk.getTenDangNhap(), tk.getHoTen(), tk.getSoDienThoai(),
                tk.getEmail(), tk.getVaiTro(), tk.getTrangThai()
            });
        }
    }

    private void chonDong() {
        int row = tblTaiKhoan.getSelectedRow();
        if (row >= 0) {
            txtMaTK.setText(model.getValueAt(row, 0).toString());
            txtTenDangNhap.setText(model.getValueAt(row, 1).toString());
            txtHoTen.setText(model.getValueAt(row, 2).toString());
            txtSDT.setText(model.getValueAt(row, 3).toString());
            txtEmail.setText(model.getValueAt(row, 4).toString());
            cboVaiTro.setSelectedItem(model.getValueAt(row, 5).toString());
            cboTrangThai.setSelectedItem(model.getValueAt(row, 6).toString());
        }
    }
}

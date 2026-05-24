package quanlysanbong.view;

import quanlysanbong.dao.KhachHangDAO;
import quanlysanbong.model.KhachHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KhachHangForm extends JFrame {
    private JTextField txtMaKH, txtHoTen, txtSDT, txtDiaChi, txtGhiChu, txtTimKiem;
    private JTable tblKhachHang;
    private DefaultTableModel model;
    private final KhachHangDAO dao = new KhachHangDAO();

    public KhachHangForm() {
        setTitle("Quản lý khách hàng");
        setSize(900, 520);
        setLocationRelativeTo(null);
        initComponents();
        loadTable();
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        txtMaKH = new JTextField();
        txtMaKH.setEditable(false);
        txtHoTen = new JTextField();
        txtSDT = new JTextField();
        txtDiaChi = new JTextField();
        txtGhiChu = new JTextField();

        form.add(new JLabel("Mã KH:"));
        form.add(txtMaKH);
        form.add(new JLabel("Họ tên:"));
        form.add(txtHoTen);
        form.add(new JLabel("SĐT:"));
        form.add(txtSDT);
        form.add(new JLabel("Địa chỉ:"));
        form.add(txtDiaChi);
        form.add(new JLabel("Ghi chú:"));
        form.add(txtGhiChu);

        model = new DefaultTableModel(new String[]{"Mã", "Họ tên", "SĐT", "Địa chỉ", "Ghi chú"}, 0);
        tblKhachHang = new JTable(model);

        JPanel buttons = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");
        txtTimKiem = new JTextField(15);
        JButton btnTim = new JButton("Tìm");

        buttons.add(btnThem);
        buttons.add(btnSua);
        buttons.add(btnXoa);
        buttons.add(btnLamMoi);
        buttons.add(new JLabel("Từ khóa:"));
        buttons.add(txtTimKiem);
        buttons.add(btnTim);

        btnThem.addActionListener(e -> them());
        btnSua.addActionListener(e -> sua());
        btnXoa.addActionListener(e -> xoa());
        btnLamMoi.addActionListener(e -> loadTable());
        btnTim.addActionListener(e -> loadTable(dao.timKiem(txtTimKiem.getText().trim())));

        tblKhachHang.getSelectionModel().addListSelectionListener(e -> chonDong());

        FormUiHelper.layoutCrudWithSplit(this, form, tblKhachHang, buttons, 280);
    }

    private KhachHang getFormData() {
        KhachHang kh = new KhachHang();
        if (!txtMaKH.getText().isEmpty()) {
            kh.setMaKH(Integer.parseInt(txtMaKH.getText()));
        }
        kh.setHoTen(txtHoTen.getText().trim());
        kh.setSoDienThoai(txtSDT.getText().trim());
        kh.setDiaChi(txtDiaChi.getText().trim());
        kh.setGhiChu(txtGhiChu.getText().trim());
        return kh;
    }

    private void them() {
        if (txtHoTen.getText().trim().isEmpty() || txtSDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên và số điện thoại!");
            return;
        }
        JOptionPane.showMessageDialog(this, dao.them(getFormData()) ? "Thêm thành công!" : "Thêm thất bại!");
        loadTable();
    }

    private void sua() {
        if (txtMaKH.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần sửa!");
            return;
        }
        JOptionPane.showMessageDialog(this, dao.sua(getFormData()) ? "Cập nhật thành công!" : "Cập nhật thất bại!");
        loadTable();
    }

    private void xoa() {
        if (txtMaKH.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    dao.xoa(Integer.parseInt(txtMaKH.getText())) ? "Xóa thành công!" : "Xóa thất bại! "
                            + "(Có thể khách đang có lịch đặt)");
            loadTable();
        }
    }

    private void loadTable() {
        loadTable(dao.layDanhSach());
    }

    private void loadTable(List<KhachHang> list) {
        model.setRowCount(0);
        for (KhachHang kh : list) {
            model.addRow(new Object[]{
                    kh.getMaKH(), kh.getHoTen(), kh.getSoDienThoai(), kh.getDiaChi(), kh.getGhiChu()
            });
        }
    }

    private void chonDong() {
        int row = tblKhachHang.getSelectedRow();
        if (row >= 0) {
            txtMaKH.setText(model.getValueAt(row, 0).toString());
            txtHoTen.setText(model.getValueAt(row, 1).toString());
            txtSDT.setText(model.getValueAt(row, 2).toString());
            txtDiaChi.setText(model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "");
            txtGhiChu.setText(model.getValueAt(row, 4) != null ? model.getValueAt(row, 4).toString() : "");
        }
    }
}

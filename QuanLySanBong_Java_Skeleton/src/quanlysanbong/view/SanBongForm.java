package quanlysanbong.view;

import quanlysanbong.dao.SanBongDAO;
import quanlysanbong.model.SanBong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SanBongForm extends JFrame {
    private JTextField txtMaSB, txtTenSB, txtGiaThue, txtGhiChu, txtTimKiem;
    private JComboBox<String> cboLoaiSB, cboTrangThai;
    private JTable tblSanBong;
    private DefaultTableModel model;
    private final SanBongDAO dao = new SanBongDAO();

    public SanBongForm() {
        setTitle("Quản lý sân bóng");
        setSize(900, 520);
        setLocationRelativeTo(null);
        initComponents();
        loadTable();
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridLayout(6, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin sân bóng"));

        txtMaSB = new JTextField();
        txtMaSB.setEditable(false);
        txtTenSB = new JTextField();
        cboLoaiSB = new JComboBox<>(new String[]{"5 người", "7 người", "11 người"});
        txtGiaThue = new JTextField();
        cboTrangThai = new JComboBox<>(new String[]{"Trống", "Đang thuê", "Bảo trì"});
        txtGhiChu = new JTextField();

        form.add(new JLabel("Mã sân:"));
        form.add(txtMaSB);
        form.add(new JLabel("Tên sân:"));
        form.add(txtTenSB);
        form.add(new JLabel("Loại sân:"));
        form.add(cboLoaiSB);
        form.add(new JLabel("Giá thuê/giờ:"));
        form.add(txtGiaThue);
        form.add(new JLabel("Trạng thái:"));
        form.add(cboTrangThai);
        form.add(new JLabel("Ghi chú:"));
        form.add(txtGhiChu);

        model = new DefaultTableModel(
                new String[]{"Mã", "Tên sân", "Loại", "Giá thuê", "Trạng thái", "Ghi chú"}, 0);
        tblSanBong = new JTable(model);

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

        tblSanBong.getSelectionModel().addListSelectionListener(e -> chonDong());

        FormUiHelper.layoutCrudWithSplit(this, form, tblSanBong, buttons, 280);
    }

    private SanBong getFormData() {
        SanBong sb = new SanBong();
        if (!txtMaSB.getText().isEmpty()) {
            sb.setMaSB(Integer.parseInt(txtMaSB.getText()));
        }
        sb.setTenSB(txtTenSB.getText().trim());
        sb.setLoaiSB(cboLoaiSB.getSelectedItem().toString());
        sb.setGiaThue(Double.parseDouble(txtGiaThue.getText().trim()));
        sb.setTrangThai(cboTrangThai.getSelectedItem().toString());
        sb.setGhiChu(txtGhiChu.getText().trim());
        return sb;
    }

    private void them() {
        if (txtTenSB.getText().trim().isEmpty() || txtGiaThue.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sân và giá thuê!");
            return;
        }
        try {
            JOptionPane.showMessageDialog(this, dao.them(getFormData()) ? "Thêm thành công!" : "Thêm thất bại!");
            loadTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá thuê phải là số!");
        }
    }

    private void sua() {
        if (txtMaSB.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sân cần sửa!");
            return;
        }
        try {
            JOptionPane.showMessageDialog(this, dao.sua(getFormData()) ? "Cập nhật thành công!" : "Cập nhật thất bại!");
            loadTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá thuê phải là số!");
        }
    }

    private void xoa() {
        if (txtMaSB.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sân cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    dao.xoa(Integer.parseInt(txtMaSB.getText())) ? "Xóa thành công!" : "Xóa thất bại! "
                            + "(Có thể sân đang được dùng trong lịch đặt)");
            loadTable();
        }
    }

    private void loadTable() {
        loadTable(dao.layDanhSach());
    }

    private void loadTable(List<SanBong> list) {
        model.setRowCount(0);
        for (SanBong sb : list) {
            model.addRow(new Object[]{
                    sb.getMaSB(), sb.getTenSB(), sb.getLoaiSB(),
                    String.format("%,.0f", sb.getGiaThue()), sb.getTrangThai(), sb.getGhiChu()
            });
        }
    }

    private void chonDong() {
        int row = tblSanBong.getSelectedRow();
        if (row >= 0) {
            txtMaSB.setText(model.getValueAt(row, 0).toString());
            txtTenSB.setText(model.getValueAt(row, 1).toString());
            cboLoaiSB.setSelectedItem(model.getValueAt(row, 2).toString());
            txtGiaThue.setText(model.getValueAt(row, 3).toString().replace(",", ""));
            cboTrangThai.setSelectedItem(model.getValueAt(row, 4).toString());
            txtGhiChu.setText(model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "");
        }
    }
}

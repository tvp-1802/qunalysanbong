package quanlysanbong.view;

import quanlysanbong.dao.KhachHangDAO;
import quanlysanbong.dao.LichDatDAO;
import quanlysanbong.dao.SanBongDAO;
import quanlysanbong.dao.TaiKhoanDAO;
import quanlysanbong.model.KhachHang;
import quanlysanbong.model.LichDat;
import quanlysanbong.model.LichDatView;
import quanlysanbong.model.SanBong;
import quanlysanbong.model.TaiKhoan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class LichDatForm extends JFrame {
    private JTextField txtMaDatSan, txtNgayDat, txtGioBatDau, txtGioKetThuc, txtGhiChu, txtTimKiem;
    private JComboBox<ComboItem> cboTaiKhoan, cboKhachHang, cboSanBong;
    private JComboBox<String> cboTrangThai;
    private JTable tblLichDat;
    private DefaultTableModel model;
    private final LichDatDAO dao = new LichDatDAO();
    private final TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();
    private final KhachHangDAO khachHangDAO = new KhachHangDAO();
    private final SanBongDAO sanBongDAO = new SanBongDAO();
    private final TaiKhoan nguoiDung;

    public LichDatForm(TaiKhoan nguoiDung) {
        this.nguoiDung = nguoiDung;
        setTitle("Quản lý đặt sân");
        setSize(1050, 560);
        setLocationRelativeTo(null);
        initComponents();
        loadCombos();
        loadTable();
    }

    private void initComponents() {
        JPanel form = FormUiHelper.createColumnForm("Thông tin đặt sân");

        txtMaDatSan = new JTextField();
        txtMaDatSan.setEditable(false);
        cboTaiKhoan = new JComboBox<>();
        cboKhachHang = new JComboBox<>();
        cboSanBong = new JComboBox<>();
        txtNgayDat = new JTextField();
        txtGioBatDau = new JTextField();
        txtGioKetThuc = new JTextField();
        cboTrangThai = new JComboBox<>(new String[]{"Chờ xác nhận", "Đã xác nhận", "Đã hủy", "Hoàn thành"});
        txtGhiChu = new JTextField();

        int row = 0;
        row = FormUiHelper.addField(form, row, "Mã đặt:", txtMaDatSan);
        row = FormUiHelper.addField(form, row, "Nhân viên:", cboTaiKhoan);
        row = FormUiHelper.addField(form, row, "Khách hàng:", cboKhachHang);
        row = FormUiHelper.addField(form, row, "Sân bóng:", cboSanBong);
        row = FormUiHelper.addField(form, row, "Ngày đặt (yyyy-MM-dd):", txtNgayDat);
        row = FormUiHelper.addField(form, row, "Giờ bắt đầu (HH:mm):", txtGioBatDau);
        row = FormUiHelper.addField(form, row, "Giờ kết thúc (HH:mm):", txtGioKetThuc);
        row = FormUiHelper.addField(form, row, "Trạng thái:", cboTrangThai);
        row = FormUiHelper.addField(form, row, "Ghi chú:", txtGhiChu);
        JLabel hint = new JLabel("<html><i>VD: 2026-05-22, 08:00 - 10:00</i></html>");
        row = FormUiHelper.addField(form, row, "Gợi ý:", hint);
        FormUiHelper.finalizeColumnForm(form, row);

        model = new DefaultTableModel(
                new String[]{"Mã", "Khách hàng", "Sân", "Ngày", "Bắt đầu", "Kết thúc", "NV", "Trạng thái"}, 0);
        tblLichDat = new JTable(model);

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
        btnLamMoi.addActionListener(e -> {
            loadCombos();
            loadTable();
        });
        btnTim.addActionListener(e -> loadTable(dao.timKiem(txtTimKiem.getText().trim())));

        tblLichDat.getSelectionModel().addListSelectionListener(e -> chonDong());

        FormUiHelper.layoutCrudWithSplit(this, form, tblLichDat, buttons, 320);
    }

    private void loadCombos() {
        cboTaiKhoan.removeAllItems();
        for (TaiKhoan tk : taiKhoanDAO.layDanhSach()) {
            if ("Hoạt động".equals(tk.getTrangThai())) {
                cboTaiKhoan.addItem(new ComboItem(tk.getMaTK(), tk.getHoTen() + " (" + tk.getTenDangNhap() + ")"));
            }
        }
        cboKhachHang.removeAllItems();
        for (KhachHang kh : khachHangDAO.layDanhSach()) {
            cboKhachHang.addItem(new ComboItem(kh.getMaKH(), kh.getHoTen() + " - " + kh.getSoDienThoai()));
        }
        cboSanBong.removeAllItems();
        for (SanBong sb : sanBongDAO.layDanhSach()) {
            cboSanBong.addItem(new ComboItem(sb.getMaSB(),
                    sb.getTenSB() + " - " + String.format("%,.0f", sb.getGiaThue()) + "đ/giờ"));
        }
        chonMacDinhNhanVien();
    }

    private void chonMacDinhNhanVien() {
        if (nguoiDung == null) return;
        for (int i = 0; i < cboTaiKhoan.getItemCount(); i++) {
            if (cboTaiKhoan.getItemAt(i).id == nguoiDung.getMaTK()) {
                cboTaiKhoan.setSelectedIndex(i);
                break;
            }
        }
    }

    private LichDat getFormData() throws Exception {
        LichDat ld = new LichDat();
        if (!txtMaDatSan.getText().isEmpty()) {
            ld.setMaDatSan(Integer.parseInt(txtMaDatSan.getText()));
        }
        ld.setMaTK(((ComboItem) cboTaiKhoan.getSelectedItem()).id);
        ld.setMaKH(((ComboItem) cboKhachHang.getSelectedItem()).id);
        ld.setMaSB(((ComboItem) cboSanBong.getSelectedItem()).id);
        ld.setNgayDat(Date.valueOf(txtNgayDat.getText().trim()));
        ld.setGioBatDau(parseTime(txtGioBatDau.getText().trim()));
        ld.setGioKetThuc(parseTime(txtGioKetThuc.getText().trim()));
        ld.setTrangThai(cboTrangThai.getSelectedItem().toString());
        ld.setGhiChu(txtGhiChu.getText().trim());
        return ld;
    }

    private Time parseTime(String gio) {
        if (gio.length() == 5) {
            gio = gio + ":00";
        }
        return Time.valueOf(gio);
    }

    private void them() {
        try {
            if (cboKhachHang.getItemCount() == 0 || cboSanBong.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Cần có khách hàng và sân bóng trước khi đặt!");
                return;
            }
            JOptionPane.showMessageDialog(this, dao.them(getFormData()) ? "Thêm thành công!" : "Thêm thất bại!");
            loadTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ! Kiểm tra ngày/giờ (yyyy-MM-dd, HH:mm).");
        }
    }

    private void sua() {
        if (txtMaDatSan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch đặt cần sửa!");
            return;
        }
        try {
            JOptionPane.showMessageDialog(this, dao.sua(getFormData()) ? "Cập nhật thành công!" : "Cập nhật thất bại!");
            loadTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ! Kiểm tra ngày/giờ.");
        }
    }

    private void xoa() {
        if (txtMaDatSan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch đặt cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    dao.xoa(Integer.parseInt(txtMaDatSan.getText())) ? "Xóa thành công!" : "Xóa thất bại! "
                            + "(Có thể đã có hóa đơn)");
            loadTable();
        }
    }

    private void loadTable() {
        loadTable(dao.layDanhSachChiTiet());
    }

    private void loadTable(List<LichDatView> list) {
        model.setRowCount(0);
        for (LichDatView ld : list) {
            model.addRow(new Object[]{
                    ld.getMaDatSan(), ld.getHoTenKhachHang(), ld.getTenSan(),
                    ld.getNgayDat(), ld.getGioBatDau(), ld.getGioKetThuc(),
                    ld.getHoTenNhanVien(), ld.getTrangThai()
            });
        }
    }

    private void chonDong() {
        int row = tblLichDat.getSelectedRow();
        if (row < 0) return;
        int maDatSan = Integer.parseInt(model.getValueAt(row, 0).toString());
        LichDat ld = dao.layTheoMa(maDatSan);
        if (ld == null) return;

        txtMaDatSan.setText(String.valueOf(ld.getMaDatSan()));
        selectCombo(cboTaiKhoan, ld.getMaTK());
        selectCombo(cboKhachHang, ld.getMaKH());
        selectCombo(cboSanBong, ld.getMaSB());
        txtNgayDat.setText(ld.getNgayDat().toString());
        txtGioBatDau.setText(ld.getGioBatDau().toString().substring(0, 5));
        txtGioKetThuc.setText(ld.getGioKetThuc().toString().substring(0, 5));
        cboTrangThai.setSelectedItem(ld.getTrangThai());
        txtGhiChu.setText(ld.getGhiChu() != null ? ld.getGhiChu() : "");
    }

    private void selectCombo(JComboBox<ComboItem> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).id == id) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    static class ComboItem {
        final int id;
        final String label;

        ComboItem(int id, String label) {
            this.id = id;
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}

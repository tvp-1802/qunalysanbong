package quanlysanbong.view;

import quanlysanbong.dao.HoaDonDAO;
import quanlysanbong.dao.LichDatDAO;
import quanlysanbong.dao.SanBongDAO;
import quanlysanbong.model.HoaDon;
import quanlysanbong.model.HoaDonView;
import quanlysanbong.model.LichDat;
import quanlysanbong.model.LichDatView;
import quanlysanbong.model.SanBong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HoaDonForm extends JFrame {
    private JTextField txtMaHD, txtNgayLap, txtTienSan, txtTienDichVu, txtTongTien, txtGhiChu, txtTimKiem;
    private JComboBox<ComboItem> cboDatSan;
    private JComboBox<String> cboPhuongThuc, cboTrangThaiTT;
    private JTable tblHoaDon;
    private DefaultTableModel model;
    private final HoaDonDAO dao = new HoaDonDAO();
    private final LichDatDAO lichDatDAO = new LichDatDAO();
    private final SanBongDAO sanBongDAO = new SanBongDAO();
    private List<LichDatView> danhSachDatSan = new ArrayList<>();

    public HoaDonForm() {
        setTitle("Quản lý hóa đơn");
        setSize(1050, 560);
        setLocationRelativeTo(null);
        initComponents();
        loadComboDatSan(true);
        loadTable();
    }

    private void initComponents() {
        JPanel form = new JPanel(new GridLayout(9, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));

        txtMaHD = new JTextField();
        txtMaHD.setEditable(false);
        cboDatSan = new JComboBox<>();
        txtNgayLap = new JTextField();
        txtTienSan = new JTextField();
        txtTienDichVu = new JTextField("0");
        txtTongTien = new JTextField();
        txtTongTien.setEditable(false);
        cboPhuongThuc = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản", "QR Code"});
        cboTrangThaiTT = new JComboBox<>(new String[]{"Chưa thanh toán", "Đã thanh toán"});
        txtGhiChu = new JTextField();

        form.add(new JLabel("Mã HĐ:"));
        form.add(txtMaHD);
        form.add(new JLabel("Lịch đặt sân:"));
        form.add(cboDatSan);
        form.add(new JLabel("Ngày lập (yyyy-MM-dd):"));
        form.add(txtNgayLap);
        form.add(new JLabel("Tiền sân:"));
        form.add(txtTienSan);
        form.add(new JLabel("Tiền dịch vụ:"));
        form.add(txtTienDichVu);
        form.add(new JLabel("Tổng tiền:"));
        form.add(txtTongTien);
        form.add(new JLabel("Phương thức TT:"));
        form.add(cboPhuongThuc);
        form.add(new JLabel("Trạng thái TT:"));
        form.add(cboTrangThaiTT);
        form.add(new JLabel("Ghi chú:"));
        form.add(txtGhiChu);

        model = new DefaultTableModel(
                new String[]{"Mã HĐ", "Mã đặt", "Khách", "Sân", "Ngày lập", "Tổng tiền", "PTTT", "Trạng thái"}, 0);
        tblHoaDon = new JTable(model);

        cboDatSan.addActionListener(e -> tuDongTinhTienSan());
        txtTienSan.addActionListener(e -> capNhatTongTien());
        txtTienDichVu.addActionListener(e -> capNhatTongTien());

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
            txtMaHD.setText("");
            txtNgayLap.setText(Date.valueOf(java.time.LocalDate.now()).toString());
            txtTienDichVu.setText("0");
            loadComboDatSan(true);
            loadTable();
        });
        btnTim.addActionListener(e -> loadTable(dao.timKiem(txtTimKiem.getText().trim())));

        tblHoaDon.getSelectionModel().addListSelectionListener(e -> chonDong());

        FormUiHelper.layoutCrudWithSplit(this, form, tblHoaDon, buttons, 320);
        txtNgayLap.setText(Date.valueOf(java.time.LocalDate.now()).toString());
    }

    private void loadComboDatSan(boolean chiLayChuaCoHoaDon) {
        cboDatSan.removeAllItems();
        danhSachDatSan.clear();
        List<LichDatView> nguon = chiLayChuaCoHoaDon
                ? lichDatDAO.layLichChuaCoHoaDon()
                : lichDatDAO.layDanhSachChiTiet();
        danhSachDatSan.addAll(nguon);
        for (LichDatView ld : nguon) {
            cboDatSan.addItem(new ComboItem(ld.getMaDatSan(),
                    "#" + ld.getMaDatSan() + " - " + ld.getHoTenKhachHang() + " - " + ld.getTenSan()
                            + " (" + ld.getNgayDat() + ")"));
        }
    }

    private void tuDongTinhTienSan() {
        if (cboDatSan.getSelectedItem() == null) return;
        int maDatSan = ((ComboItem) cboDatSan.getSelectedItem()).id;
        LichDat ld = lichDatDAO.layTheoMa(maDatSan);
        if (ld == null) return;
        SanBong sb = sanBongDAO.layTheoMa(ld.getMaSB());
        if (sb == null) return;
        long ms = ld.getGioKetThuc().getTime() - ld.getGioBatDau().getTime();
        long gio = Math.max(1, ms / (1000 * 60 * 60));
        double tien = sb.getGiaThue() * gio;
        txtTienSan.setText(String.valueOf((long) tien));
        capNhatTongTien();
    }

    private void capNhatTongTien() {
        try {
            double san = txtTienSan.getText().trim().isEmpty() ? 0 : Double.parseDouble(txtTienSan.getText().trim());
            double dv = txtTienDichVu.getText().trim().isEmpty() ? 0 : Double.parseDouble(txtTienDichVu.getText().trim());
            txtTongTien.setText(String.format("%,.0f", san + dv));
        } catch (NumberFormatException ex) {
            txtTongTien.setText("0");
        }
    }

    private HoaDon getFormData() throws Exception {
        HoaDon hd = new HoaDon();
        if (!txtMaHD.getText().isEmpty()) {
            hd.setMaHD(Integer.parseInt(txtMaHD.getText()));
        }
        hd.setMaDatSan(((ComboItem) cboDatSan.getSelectedItem()).id);
        hd.setNgayLap(Date.valueOf(txtNgayLap.getText().trim()));
        hd.setTienSan(Double.parseDouble(txtTienSan.getText().trim()));
        hd.setTienDichVu(Double.parseDouble(txtTienDichVu.getText().trim()));
        hd.tinhTongTien();
        hd.setPhuongThucThanhToan(cboPhuongThuc.getSelectedItem().toString());
        hd.setTrangThaiThanhToan(cboTrangThaiTT.getSelectedItem().toString());
        hd.setGhiChu(txtGhiChu.getText().trim());
        return hd;
    }

    private void them() {
        if (cboDatSan.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có lịch đặt nào chưa lập hóa đơn!");
            return;
        }
        try {
            JOptionPane.showMessageDialog(this, dao.them(getFormData()) ? "Thêm thành công!" : "Thêm thất bại!");
            loadComboDatSan(true);
            loadTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    }

    private void sua() {
        if (txtMaHD.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần sửa!");
            return;
        }
        try {
            JOptionPane.showMessageDialog(this, dao.sua(getFormData()) ? "Cập nhật thành công!" : "Cập nhật thất bại!");
            loadComboDatSan(true);
            loadTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    }

    private void xoa() {
        if (txtMaHD.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    dao.xoa(Integer.parseInt(txtMaHD.getText())) ? "Xóa thành công!" : "Xóa thất bại!");
            loadComboDatSan(true);
            loadTable();
        }
    }

    private void loadTable() {
        loadTable(dao.layDanhSachChiTiet());
    }

    private void loadTable(List<HoaDonView> list) {
        model.setRowCount(0);
        for (HoaDonView hd : list) {
            model.addRow(new Object[]{
                    hd.getMaHD(), hd.getMaDatSan(), hd.getHoTenKhachHang(), hd.getTenSan(),
                    hd.getNgayLap(), String.format("%,.0f", hd.getTongTien()),
                    hd.getPhuongThucThanhToan(), hd.getTrangThaiThanhToan()
            });
        }
    }

    private void chonDong() {
        int row = tblHoaDon.getSelectedRow();
        if (row < 0) return;
        loadComboDatSan(false);
        int maHD = Integer.parseInt(model.getValueAt(row, 0).toString());
        int maDatSan = Integer.parseInt(model.getValueAt(row, 1).toString());

        for (HoaDon hd : dao.layDanhSach()) {
            if (hd.getMaHD() == maHD) {
                txtMaHD.setText(String.valueOf(hd.getMaHD()));
                selectCombo(cboDatSan, maDatSan);
                txtNgayLap.setText(hd.getNgayLap().toString());
                txtTienSan.setText(String.valueOf((long) hd.getTienSan()));
                txtTienDichVu.setText(String.valueOf((long) hd.getTienDichVu()));
                capNhatTongTien();
                cboPhuongThuc.setSelectedItem(hd.getPhuongThucThanhToan());
                cboTrangThaiTT.setSelectedItem(hd.getTrangThaiThanhToan());
                txtGhiChu.setText(hd.getGhiChu() != null ? hd.getGhiChu() : "");
                break;
            }
        }
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

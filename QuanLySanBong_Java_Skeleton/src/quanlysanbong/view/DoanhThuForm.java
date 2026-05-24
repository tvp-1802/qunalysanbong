package quanlysanbong.view;

import quanlysanbong.dao.HoaDonDAO;
import quanlysanbong.model.ThongKeMuc;
import quanlysanbong.model.ThongKeThang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

public class DoanhThuForm extends JFrame {
    private static final String[] LOAI_THONG_KE = {"Theo tuần", "Theo tháng", "Theo quý", "Theo năm"};

    private JLabel lblTong, lblDaTT, lblChuaTT, lblSoHD, lblKhoang;
    private JTable tblChiTiet;
    private DefaultTableModel model;
    private JComboBox<String> cboLoai;
    private JPanel pnlBoLoc;
    private CardLayout cardLayout;
    private JSpinner spNamTuan, spTuan;
    private JSpinner spNamThang, spThang;
    private JSpinner spNamQuy;
    private JComboBox<String> cboQuy;
    private JSpinner spNam;
    private final HoaDonDAO dao = new HoaDonDAO();

    public DoanhThuForm() {
        setTitle("Thống kê doanh thu");
        setSize(820, 560);
        setLocationRelativeTo(null);
        initComponents();
        capNhatBoLoc();
        thongKe();
    }

    private void initComponents() {
        JPanel top = new JPanel(new GridLayout(5, 1, 6, 6));
        top.setBorder(BorderFactory.createTitledBorder("Tổng quan kỳ đã chọn"));
        lblKhoang = new JLabel();
        lblTong = new JLabel();
        lblDaTT = new JLabel();
        lblChuaTT = new JLabel();
        lblSoHD = new JLabel();
        top.add(lblKhoang);
        top.add(lblTong);
        top.add(lblDaTT);
        top.add(lblChuaTT);
        top.add(lblSoHD);

        JPanel filter = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        filter.setBorder(BorderFactory.createTitledBorder("Bộ lọc thống kê"));
        cboLoai = new JComboBox<>(LOAI_THONG_KE);
        filter.add(new JLabel("Loại:"));
        filter.add(cboLoai);

        cardLayout = new CardLayout();
        pnlBoLoc = new JPanel(cardLayout);

        int namHienTai = LocalDate.now().getYear();
        WeekFields wf = WeekFields.of(Locale.getDefault());

        JPanel pnlTuan = new JPanel(new FlowLayout(FlowLayout.LEFT));
        spNamTuan = new JSpinner(new SpinnerNumberModel(namHienTai, 2020, 2100, 1));
        spTuan = new JSpinner(new SpinnerNumberModel(
                LocalDate.now().get(wf.weekOfWeekBasedYear()), 1, 53, 1));
        pnlTuan.add(new JLabel("Năm:"));
        pnlTuan.add(spNamTuan);
        pnlTuan.add(new JLabel("Tuần:"));
        pnlTuan.add(spTuan);

        JPanel pnlThang = new JPanel(new FlowLayout(FlowLayout.LEFT));
        spNamThang = new JSpinner(new SpinnerNumberModel(namHienTai, 2020, 2100, 1));
        spThang = new JSpinner(new SpinnerNumberModel(LocalDate.now().getMonthValue(), 1, 12, 1));
        pnlThang.add(new JLabel("Năm:"));
        pnlThang.add(spNamThang);
        pnlThang.add(new JLabel("Tháng:"));
        pnlThang.add(spThang);

        JPanel pnlQuy = new JPanel(new FlowLayout(FlowLayout.LEFT));
        spNamQuy = new JSpinner(new SpinnerNumberModel(namHienTai, 2020, 2100, 1));
        cboQuy = new JComboBox<>(new String[]{"Quý 1", "Quý 2", "Quý 3", "Quý 4"});
        int quyHienTai = (LocalDate.now().getMonthValue() - 1) / 3;
        cboQuy.setSelectedIndex(quyHienTai);
        pnlQuy.add(new JLabel("Năm:"));
        pnlQuy.add(spNamQuy);
        pnlQuy.add(new JLabel("Quý:"));
        pnlQuy.add(cboQuy);

        JPanel pnlNam = new JPanel(new FlowLayout(FlowLayout.LEFT));
        spNam = new JSpinner(new SpinnerNumberModel(namHienTai, 2020, 2100, 1));
        pnlNam.add(new JLabel("Năm:"));
        pnlNam.add(spNam);

        pnlBoLoc.add(pnlTuan, "TUAN");
        pnlBoLoc.add(pnlThang, "THANG");
        pnlBoLoc.add(pnlQuy, "QUY");
        pnlBoLoc.add(pnlNam, "NAM");
        filter.add(pnlBoLoc);

        JButton btnThongKe = new JButton("Thống kê");
        filter.add(btnThongKe);

        model = new DefaultTableModel(new String[]{"Kỳ", "Số hóa đơn (đã TT)", "Doanh thu (VNĐ)"}, 0);
        tblChiTiet = new JTable(model);

        cboLoai.addActionListener(e -> capNhatBoLoc());
        btnThongKe.addActionListener(e -> thongKe());

        JPanel center = new JPanel(new BorderLayout(0, 8));
        center.add(filter, BorderLayout.NORTH);
        center.add(new JScrollPane(tblChiTiet), BorderLayout.CENTER);

        setLayout(new BorderLayout(8, 8));
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
    }

    private void capNhatBoLoc() {
        String loai = (String) cboLoai.getSelectedItem();
        if ("Theo tuần".equals(loai)) {
            cardLayout.show(pnlBoLoc, "TUAN");
        } else if ("Theo tháng".equals(loai)) {
            cardLayout.show(pnlBoLoc, "THANG");
        } else if ("Theo quý".equals(loai)) {
            cardLayout.show(pnlBoLoc, "QUY");
        } else {
            cardLayout.show(pnlBoLoc, "NAM");
        }
    }

    private void thongKe() {
        LocalDate tu = null;
        LocalDate den = null;
        String moTaKhoang = "";
        List<ThongKeMuc> chiTiet;
        String loai = (String) cboLoai.getSelectedItem();

        if ("Theo tuần".equals(loai)) {
            int nam = (Integer) spNamTuan.getValue();
            int tuan = (Integer) spTuan.getValue();
            WeekFields iso = WeekFields.ISO;
            tu = LocalDate.of(nam, 1, 1)
                    .with(iso.weekOfWeekBasedYear(), tuan)
                    .with(iso.dayOfWeek(), 1);
            den = tu.plusDays(6);
            moTaKhoang = "Tuần " + tuan + " năm " + nam + " (" + tu + " → " + den + ")";
            chiTiet = dao.thongKeTheoNgay(toSqlDate(tu), toSqlDate(den));
        } else if ("Theo tháng".equals(loai)) {
            int nam = (Integer) spNamThang.getValue();
            int thang = (Integer) spThang.getValue();
            YearMonth ym = YearMonth.of(nam, thang);
            tu = ym.atDay(1);
            den = ym.atEndOfMonth();
            moTaKhoang = "Tháng " + thang + "/" + nam;
            chiTiet = dao.thongKeTheoNgay(toSqlDate(tu), toSqlDate(den));
        } else if ("Theo quý".equals(loai)) {
            int nam = (Integer) spNamQuy.getValue();
            int quy = cboQuy.getSelectedIndex() + 1;
            int thangDau = (quy - 1) * 3 + 1;
            tu = LocalDate.of(nam, thangDau, 1);
            den = tu.plusMonths(3).minusDays(1);
            moTaKhoang = "Quý " + quy + " năm " + nam;
            chiTiet = dao.thongKeTheoThangTrongKhoang(toSqlDate(tu), toSqlDate(den));
        } else {
            int nam = (Integer) spNam.getValue();
            tu = LocalDate.of(nam, 1, 1);
            den = LocalDate.of(nam, 12, 31);
            moTaKhoang = "Năm " + nam;
            chiTiet = chuyenTuThongKeThang(dao.thongKeTheoThang(nam));
        }

        Date sqlTu = toSqlDate(tu);
        Date sqlDen = toSqlDate(den);
        double tong = dao.tongDoanhThuTrongKhoang(sqlTu, sqlDen);
        double daTT = dao.tongDoanhThuTrongKhoang(sqlTu, sqlDen, "Đã thanh toán");
        double chuaTT = dao.tongDoanhThuTrongKhoang(sqlTu, sqlDen, "Chưa thanh toán");
        int soDaTT = dao.demHoaDonTrongKhoang(sqlTu, sqlDen, "Đã thanh toán");
        int soChuaTT = dao.demHoaDonTrongKhoang(sqlTu, sqlDen, "Chưa thanh toán");

        lblKhoang.setText("Kỳ: " + moTaKhoang);
        lblTong.setText("Tổng giá trị hóa đơn: " + formatMoney(tong) + " VNĐ");
        lblDaTT.setText("Đã thanh toán: " + formatMoney(daTT) + " VNĐ (" + soDaTT + " hóa đơn)");
        lblChuaTT.setText("Chưa thanh toán: " + formatMoney(chuaTT) + " VNĐ (" + soChuaTT + " hóa đơn)");
        lblSoHD.setText("Tổng số hóa đơn trong kỳ: " + (soDaTT + soChuaTT));

        model.setRowCount(0);
        double tongChiTiet = 0;
        int tongSoHd = 0;
        for (ThongKeMuc muc : chiTiet) {
            model.addRow(new Object[]{muc.getTenMuc(), muc.getSoHoaDon(), formatMoney(muc.getTongTien())});
            tongChiTiet += muc.getTongTien();
            tongSoHd += muc.getSoHoaDon();
        }
        if (!chiTiet.isEmpty()) {
            model.addRow(new Object[]{"Tổng cộng", tongSoHd, formatMoney(tongChiTiet)});
        }
    }

    private List<ThongKeMuc> chuyenTuThongKeThang(List<ThongKeThang> list) {
        List<ThongKeMuc> ketQua = new java.util.ArrayList<>();
        for (ThongKeThang tk : list) {
            ketQua.add(new ThongKeMuc("Tháng " + tk.getThang(), tk.getSoHoaDon(), tk.getTongTien()));
        }
        return ketQua;
    }

    private Date toSqlDate(LocalDate d) {
        return Date.valueOf(d);
    }

    private String formatMoney(double value) {
        return String.format("%,.0f", value);
    }
}

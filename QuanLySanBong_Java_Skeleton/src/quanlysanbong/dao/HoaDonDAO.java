package quanlysanbong.dao;

import quanlysanbong.database.DBConnection;
import quanlysanbong.model.HoaDon;
import quanlysanbong.model.HoaDonView;
import quanlysanbong.model.ThongKeMuc;
import quanlysanbong.model.ThongKeThang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    private static final String SQL_JOIN =
            "SELECT hd.*, kh.hoTen AS hoTenKH, sb.tenSB, ld.ngayDat AS ngayDatSan "
                    + "FROM HoaDon hd "
                    + "JOIN LichDat ld ON hd.maDatSan = ld.maDatSan "
                    + "JOIN KhachHang kh ON ld.maKH = kh.maKH "
                    + "JOIN SanBong sb ON ld.maSB = sb.maSB ";

    public boolean them(HoaDon hd) {
        hd.tinhTongTien();
        String sql = "INSERT INTO HoaDon(maDatSan, ngayLap, tienSan, tienDichVu, tongTien, "
                + "phuongThucThanhToan, trangThaiThanhToan, ghiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hd.getMaDatSan());
            ps.setDate(2, hd.getNgayLap());
            ps.setDouble(3, hd.getTienSan());
            ps.setDouble(4, hd.getTienDichVu());
            ps.setDouble(5, hd.getTongTien());
            ps.setString(6, hd.getPhuongThucThanhToan());
            ps.setString(7, hd.getTrangThaiThanhToan());
            ps.setString(8, hd.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(HoaDon hd) {
        hd.tinhTongTien();
        String sql = "UPDATE HoaDon SET maDatSan=?, ngayLap=?, tienSan=?, tienDichVu=?, tongTien=?, "
                + "phuongThucThanhToan=?, trangThaiThanhToan=?, ghiChu=? WHERE maHD=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hd.getMaDatSan());
            ps.setDate(2, hd.getNgayLap());
            ps.setDouble(3, hd.getTienSan());
            ps.setDouble(4, hd.getTienDichVu());
            ps.setDouble(5, hd.getTongTien());
            ps.setString(6, hd.getPhuongThucThanhToan());
            ps.setString(7, hd.getTrangThaiThanhToan());
            ps.setString(8, hd.getGhiChu());
            ps.setInt(9, hd.getMaHD());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(int maHD) {
        String sql = "DELETE FROM HoaDon WHERE maHD=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HoaDon> layDanhSach() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon ORDER BY ngayLap DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonView> layDanhSachChiTiet() {
        List<HoaDonView> list = new ArrayList<>();
        String sql = SQL_JOIN + "ORDER BY hd.ngayLap DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapView(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonView> timKiem(String tuKhoa) {
        List<HoaDonView> list = new ArrayList<>();
        String sql = SQL_JOIN
                + "WHERE CAST(hd.maHD AS CHAR) LIKE ? OR kh.hoTen LIKE ? OR sb.tenSB LIKE ? "
                + "OR hd.phuongThucThanhToan LIKE ? OR hd.trangThaiThanhToan LIKE ? "
                + "ORDER BY hd.ngayLap DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String key = "%" + tuKhoa + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ps.setString(4, key);
            ps.setString(5, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapView(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public double tongDoanhThuTheoTrangThai(String trangThaiThanhToan) {
        String sql = "SELECT COALESCE(SUM(tongTien), 0) FROM HoaDon WHERE trangThaiThanhToan=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThaiThanhToan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double tongDoanhThu() {
        String sql = "SELECT COALESCE(SUM(tongTien), 0) FROM HoaDon";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int demHoaDonTheoTrangThai(String trangThaiThanhToan) {
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE trangThaiThanhToan=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, trangThaiThanhToan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double tongDoanhThuTrongKhoang(Date tu, Date den) {
        String sql = "SELECT COALESCE(SUM(tongTien), 0) FROM HoaDon WHERE ngayLap BETWEEN ? AND ?";
        return querySum(sql, tu, den);
    }

    public double tongDoanhThuTrongKhoang(Date tu, Date den, String trangThaiThanhToan) {
        String sql = "SELECT COALESCE(SUM(tongTien), 0) FROM HoaDon "
                + "WHERE ngayLap BETWEEN ? AND ? AND trangThaiThanhToan=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, tu);
            ps.setDate(2, den);
            ps.setString(3, trangThaiThanhToan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int demHoaDonTrongKhoang(Date tu, Date den, String trangThaiThanhToan) {
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE ngayLap BETWEEN ? AND ? AND trangThaiThanhToan=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, tu);
            ps.setDate(2, den);
            ps.setString(3, trangThaiThanhToan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int demHoaDonTrongKhoang(Date tu, Date den) {
        String sql = "SELECT COUNT(*) FROM HoaDon WHERE ngayLap BETWEEN ? AND ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, tu);
            ps.setDate(2, den);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<ThongKeMuc> thongKeTheoNgay(Date tu, Date den) {
        List<ThongKeMuc> list = new ArrayList<>();
        String sql = "SELECT ngayLap, COUNT(*) AS soHD, COALESCE(SUM(tongTien), 0) AS tong "
                + "FROM HoaDon WHERE ngayLap BETWEEN ? AND ? AND trangThaiThanhToan='Đã thanh toán' "
                + "GROUP BY ngayLap ORDER BY ngayLap";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, tu);
            ps.setDate(2, den);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Date ngay = rs.getDate("ngayLap");
                list.add(new ThongKeMuc(
                        "Ngày " + ngay,
                        rs.getInt("soHD"),
                        rs.getDouble("tong")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ThongKeMuc> thongKeTheoThangTrongKhoang(Date tu, Date den) {
        List<ThongKeMuc> list = new ArrayList<>();
        String sql = "SELECT MONTH(ngayLap) AS thang, COUNT(*) AS soHD, COALESCE(SUM(tongTien), 0) AS tong "
                + "FROM HoaDon WHERE ngayLap BETWEEN ? AND ? AND trangThaiThanhToan='Đã thanh toán' "
                + "GROUP BY MONTH(ngayLap) ORDER BY thang";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, tu);
            ps.setDate(2, den);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ThongKeMuc(
                        "Tháng " + rs.getInt("thang"),
                        rs.getInt("soHD"),
                        rs.getDouble("tong")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ThongKeThang> thongKeTheoThang(int nam) {
        List<ThongKeThang> list = new ArrayList<>();
        String sql = "SELECT MONTH(ngayLap) AS thang, COUNT(*) AS soHD, COALESCE(SUM(tongTien), 0) AS tong "
                + "FROM HoaDon WHERE YEAR(ngayLap)=? AND trangThaiThanhToan='Đã thanh toán' "
                + "GROUP BY MONTH(ngayLap) ORDER BY thang";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ThongKeThang(
                        rs.getInt("thang"),
                        rs.getInt("soHD"),
                        rs.getDouble("tong")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private HoaDon mapResultSet(ResultSet rs) throws SQLException {
        HoaDon hd = new HoaDon();
        hd.setMaHD(rs.getInt("maHD"));
        hd.setMaDatSan(rs.getInt("maDatSan"));
        hd.setNgayLap(rs.getDate("ngayLap"));
        hd.setTienSan(rs.getDouble("tienSan"));
        hd.setTienDichVu(rs.getDouble("tienDichVu"));
        hd.setTongTien(rs.getDouble("tongTien"));
        hd.setPhuongThucThanhToan(rs.getString("phuongThucThanhToan"));
        hd.setTrangThaiThanhToan(rs.getString("trangThaiThanhToan"));
        hd.setGhiChu(rs.getString("ghiChu"));
        return hd;
    }

    private HoaDonView mapView(ResultSet rs) throws SQLException {
        HoaDonView v = new HoaDonView();
        v.setMaHD(rs.getInt("maHD"));
        v.setMaDatSan(rs.getInt("maDatSan"));
        v.setNgayLap(rs.getDate("ngayLap"));
        v.setTienSan(rs.getDouble("tienSan"));
        v.setTienDichVu(rs.getDouble("tienDichVu"));
        v.setTongTien(rs.getDouble("tongTien"));
        v.setPhuongThucThanhToan(rs.getString("phuongThucThanhToan"));
        v.setTrangThaiThanhToan(rs.getString("trangThaiThanhToan"));
        v.setGhiChu(rs.getString("ghiChu"));
        v.setHoTenKhachHang(rs.getString("hoTenKH"));
        v.setTenSan(rs.getString("tenSB"));
        Date ngay = rs.getDate("ngayDatSan");
        v.setNgayDatSan(ngay != null ? ngay.toString() : "");
        return v;
    }

    private double querySum(String sql, Date tu, Date den) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, tu);
            ps.setDate(2, den);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

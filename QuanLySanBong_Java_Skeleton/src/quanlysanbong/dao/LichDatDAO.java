package quanlysanbong.dao;

import quanlysanbong.database.DBConnection;
import quanlysanbong.model.LichDat;
import quanlysanbong.model.LichDatView;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LichDatDAO {

    private static final String SQL_JOIN =
            "SELECT ld.*, kh.hoTen AS hoTenKH, sb.tenSB, tk.hoTen AS hoTenTK "
                    + "FROM LichDat ld "
                    + "JOIN KhachHang kh ON ld.maKH = kh.maKH "
                    + "JOIN SanBong sb ON ld.maSB = sb.maSB "
                    + "JOIN TaiKhoan tk ON ld.maTK = tk.maTK ";

    public boolean them(LichDat ld) {
        String sql = "INSERT INTO LichDat(maTK, maKH, maSB, ngayDat, gioBatDau, gioKetThuc, trangThai, ghiChu) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ld.getMaTK());
            ps.setInt(2, ld.getMaKH());
            ps.setInt(3, ld.getMaSB());
            ps.setDate(4, ld.getNgayDat());
            ps.setTime(5, ld.getGioBatDau());
            ps.setTime(6, ld.getGioKetThuc());
            ps.setString(7, ld.getTrangThai());
            ps.setString(8, ld.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(LichDat ld) {
        String sql = "UPDATE LichDat SET maTK=?, maKH=?, maSB=?, ngayDat=?, gioBatDau=?, gioKetThuc=?, "
                + "trangThai=?, ghiChu=? WHERE maDatSan=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ld.getMaTK());
            ps.setInt(2, ld.getMaKH());
            ps.setInt(3, ld.getMaSB());
            ps.setDate(4, ld.getNgayDat());
            ps.setTime(5, ld.getGioBatDau());
            ps.setTime(6, ld.getGioKetThuc());
            ps.setString(7, ld.getTrangThai());
            ps.setString(8, ld.getGhiChu());
            ps.setInt(9, ld.getMaDatSan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(int maDatSan) {
        String sql = "DELETE FROM LichDat WHERE maDatSan=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDatSan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LichDat> layDanhSach() {
        List<LichDat> list = new ArrayList<>();
        String sql = "SELECT * FROM LichDat ORDER BY ngayDat DESC, gioBatDau";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LichDatView> layDanhSachChiTiet() {
        List<LichDatView> list = new ArrayList<>();
        String sql = SQL_JOIN + "ORDER BY ld.ngayDat DESC, ld.gioBatDau";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapView(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LichDatView> layLichChuaCoHoaDon() {
        List<LichDatView> list = new ArrayList<>();
        String sql = SQL_JOIN
                + "LEFT JOIN HoaDon hd ON ld.maDatSan = hd.maDatSan "
                + "WHERE hd.maDatSan IS NULL AND ld.trangThai IN ('Đã xác nhận', 'Hoàn thành') "
                + "ORDER BY ld.ngayDat DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapView(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public LichDat layTheoMa(int maDatSan) {
        String sql = "SELECT * FROM LichDat WHERE maDatSan=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maDatSan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<LichDatView> timKiem(String tuKhoa) {
        List<LichDatView> list = new ArrayList<>();
        String sql = SQL_JOIN
                + "WHERE CAST(ld.maDatSan AS CHAR) LIKE ? OR kh.hoTen LIKE ? OR sb.tenSB LIKE ? "
                + "OR ld.trangThai LIKE ? OR ld.ghiChu LIKE ? "
                + "ORDER BY ld.ngayDat DESC";
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

    private LichDat mapResultSet(ResultSet rs) throws SQLException {
        LichDat ld = new LichDat();
        ld.setMaDatSan(rs.getInt("maDatSan"));
        ld.setMaTK(rs.getInt("maTK"));
        ld.setMaKH(rs.getInt("maKH"));
        ld.setMaSB(rs.getInt("maSB"));
        ld.setNgayDat(rs.getDate("ngayDat"));
        ld.setGioBatDau(rs.getTime("gioBatDau"));
        ld.setGioKetThuc(rs.getTime("gioKetThuc"));
        ld.setTrangThai(rs.getString("trangThai"));
        ld.setGhiChu(rs.getString("ghiChu"));
        return ld;
    }

    private LichDatView mapView(ResultSet rs) throws SQLException {
        LichDatView v = new LichDatView();
        v.setMaDatSan(rs.getInt("maDatSan"));
        v.setMaTK(rs.getInt("maTK"));
        v.setMaKH(rs.getInt("maKH"));
        v.setMaSB(rs.getInt("maSB"));
        v.setNgayDat(rs.getDate("ngayDat"));
        v.setGioBatDau(rs.getTime("gioBatDau"));
        v.setGioKetThuc(rs.getTime("gioKetThuc"));
        v.setTrangThai(rs.getString("trangThai"));
        v.setGhiChu(rs.getString("ghiChu"));
        v.setHoTenKhachHang(rs.getString("hoTenKH"));
        v.setTenSan(rs.getString("tenSB"));
        v.setHoTenNhanVien(rs.getString("hoTenTK"));
        return v;
    }
}

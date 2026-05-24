package quanlysanbong.dao;

import quanlysanbong.database.DBConnection;
import quanlysanbong.model.KhachHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    public boolean them(KhachHang kh) {
        String sql = "INSERT INTO KhachHang(hoTen, soDienThoai, diaChi, ghiChu) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getDiaChi());
            ps.setString(4, kh.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(KhachHang kh) {
        String sql = "UPDATE KhachHang SET hoTen=?, soDienThoai=?, diaChi=?, ghiChu=? WHERE maKH=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getSoDienThoai());
            ps.setString(3, kh.getDiaChi());
            ps.setString(4, kh.getGhiChu());
            ps.setInt(5, kh.getMaKH());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(int maKH) {
        String sql = "DELETE FROM KhachHang WHERE maKH=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<KhachHang> layDanhSach() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang ORDER BY maKH";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public KhachHang layTheoMa(int maKH) {
        String sql = "SELECT * FROM KhachHang WHERE maKH=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhachHang> timKiem(String tuKhoa) {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang WHERE hoTen LIKE ? OR soDienThoai LIKE ? OR diaChi LIKE ? ORDER BY maKH";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String key = "%" + tuKhoa + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private KhachHang mapResultSet(ResultSet rs) throws SQLException {
        return new KhachHang(
                rs.getInt("maKH"),
                rs.getString("hoTen"),
                rs.getString("soDienThoai"),
                rs.getString("diaChi"),
                rs.getString("ghiChu")
        );
    }
}

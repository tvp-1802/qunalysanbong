package quanlysanbong.dao;

import quanlysanbong.database.DBConnection;
import quanlysanbong.model.SanBong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanBongDAO {

    public boolean them(SanBong sb) {
        String sql = "INSERT INTO SanBong(tenSB, loaiSB, giaThue, trangThai, ghiChu) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sb.getTenSB());
            ps.setString(2, sb.getLoaiSB());
            ps.setDouble(3, sb.getGiaThue());
            ps.setString(4, sb.getTrangThai());
            ps.setString(5, sb.getGhiChu());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(SanBong sb) {
        String sql = "UPDATE SanBong SET tenSB=?, loaiSB=?, giaThue=?, trangThai=?, ghiChu=? WHERE maSB=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sb.getTenSB());
            ps.setString(2, sb.getLoaiSB());
            ps.setDouble(3, sb.getGiaThue());
            ps.setString(4, sb.getTrangThai());
            ps.setString(5, sb.getGhiChu());
            ps.setInt(6, sb.getMaSB());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(int maSB) {
        String sql = "DELETE FROM SanBong WHERE maSB=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSB);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<SanBong> layDanhSach() {
        List<SanBong> list = new ArrayList<>();
        String sql = "SELECT * FROM SanBong ORDER BY maSB";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public SanBong layTheoMa(int maSB) {
        String sql = "SELECT * FROM SanBong WHERE maSB=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maSB);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SanBong> timKiem(String tuKhoa) {
        List<SanBong> list = new ArrayList<>();
        String sql = "SELECT * FROM SanBong WHERE tenSB LIKE ? OR loaiSB LIKE ? OR trangThai LIKE ? ORDER BY maSB";
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

    private SanBong mapResultSet(ResultSet rs) throws SQLException {
        return new SanBong(
                rs.getInt("maSB"),
                rs.getString("tenSB"),
                rs.getString("loaiSB"),
                rs.getDouble("giaThue"),
                rs.getString("trangThai"),
                rs.getString("ghiChu")
        );
    }
}

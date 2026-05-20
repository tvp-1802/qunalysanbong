package quanlysanbong.dao;

import quanlysanbong.database.DBConnection;
import quanlysanbong.model.TaiKhoan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiKhoanDAO {

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap=? AND matKhau=? AND trangThai='Hoạt động'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean them(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan(tenDangNhap, matKhau, hoTen, soDienThoai, email, vaiTro, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tk.getTenDangNhap());
            ps.setString(2, tk.getMatKhau());
            ps.setString(3, tk.getHoTen());
            ps.setString(4, tk.getSoDienThoai());
            ps.setString(5, tk.getEmail());
            ps.setString(6, tk.getVaiTro());
            ps.setString(7, tk.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(TaiKhoan tk) {
        String sql = "UPDATE TaiKhoan SET tenDangNhap=?, matKhau=?, hoTen=?, soDienThoai=?, email=?, vaiTro=?, trangThai=? WHERE maTK=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tk.getTenDangNhap());
            ps.setString(2, tk.getMatKhau());
            ps.setString(3, tk.getHoTen());
            ps.setString(4, tk.getSoDienThoai());
            ps.setString(5, tk.getEmail());
            ps.setString(6, tk.getVaiTro());
            ps.setString(7, tk.getTrangThai());
            ps.setInt(8, tk.getMaTK());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(int maTK) {
        String sql = "DELETE FROM TaiKhoan WHERE maTK=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maTK);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TaiKhoan> layDanhSach() {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapResultSet(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TaiKhoan> timKiem(String tuKhoa) {
        List<TaiKhoan> list = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap LIKE ? OR hoTen LIKE ? OR vaiTro LIKE ?";
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

    private TaiKhoan mapResultSet(ResultSet rs) throws SQLException {
        return new TaiKhoan(
            rs.getInt("maTK"),
            rs.getString("tenDangNhap"),
            rs.getString("matKhau"),
            rs.getString("hoTen"),
            rs.getString("soDienThoai"),
            rs.getString("email"),
            rs.getString("vaiTro"),
            rs.getString("trangThai")
        );
    }
}

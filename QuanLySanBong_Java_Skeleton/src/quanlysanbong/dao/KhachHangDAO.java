package quanlysanbong.dao;

import quanlysanbong.database.DBConnection;
import quanlysanbong.model.KhachHang;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    public boolean them(KhachHang obj) {
        // TODO: Viết câu lệnh INSERT cho bảng KhachHang
        return false;
    }

    public boolean sua(KhachHang obj) {
        // TODO: Viết câu lệnh UPDATE cho bảng KhachHang
        return false;
    }

    public boolean xoa(int id) {
        // TODO: Viết câu lệnh DELETE theo khóa chính maKH
        return false;
    }

    public List<KhachHang> layDanhSach() {
        List<KhachHang> list = new ArrayList<>();
        // TODO: SELECT * FROM KhachHang
        return list;
    }

    public List<KhachHang> timKiem(String tuKhoa) {
        List<KhachHang> list = new ArrayList<>();
        // TODO: SELECT có điều kiện LIKE
        return list;
    }
}

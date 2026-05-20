package quanlysanbong.dao;

import quanlysanbong.database.DBConnection;
import quanlysanbong.model.HoaDon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {

    public boolean them(HoaDon obj) {
        // TODO: Viết câu lệnh INSERT cho bảng HoaDon
        return false;
    }

    public boolean sua(HoaDon obj) {
        // TODO: Viết câu lệnh UPDATE cho bảng HoaDon
        return false;
    }

    public boolean xoa(int id) {
        // TODO: Viết câu lệnh DELETE theo khóa chính maHD
        return false;
    }

    public List<HoaDon> layDanhSach() {
        List<HoaDon> list = new ArrayList<>();
        // TODO: SELECT * FROM HoaDon
        return list;
    }

    public List<HoaDon> timKiem(String tuKhoa) {
        List<HoaDon> list = new ArrayList<>();
        // TODO: SELECT có điều kiện LIKE
        return list;
    }
}

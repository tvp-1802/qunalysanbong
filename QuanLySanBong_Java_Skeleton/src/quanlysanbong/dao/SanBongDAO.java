package quanlysanbong.dao;

import quanlysanbong.database.DBConnection;
import quanlysanbong.model.SanBong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanBongDAO {

    public boolean them(SanBong obj) {
        // TODO: Viết câu lệnh INSERT cho bảng SanBong
        return false;
    }

    public boolean sua(SanBong obj) {
        // TODO: Viết câu lệnh UPDATE cho bảng SanBong
        return false;
    }

    public boolean xoa(int id) {
        // TODO: Viết câu lệnh DELETE theo khóa chính maSB
        return false;
    }

    public List<SanBong> layDanhSach() {
        List<SanBong> list = new ArrayList<>();
        // TODO: SELECT * FROM SanBong
        return list;
    }

    public List<SanBong> timKiem(String tuKhoa) {
        List<SanBong> list = new ArrayList<>();
        // TODO: SELECT có điều kiện LIKE
        return list;
    }
}

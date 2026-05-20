package quanlysanbong.dao;

import quanlysanbong.database.DBConnection;
import quanlysanbong.model.LichDat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LichDatDAO {

    public boolean them(LichDat obj) {
        // TODO: Viết câu lệnh INSERT cho bảng LichDat
        return false;
    }

    public boolean sua(LichDat obj) {
        // TODO: Viết câu lệnh UPDATE cho bảng LichDat
        return false;
    }

    public boolean xoa(int id) {
        // TODO: Viết câu lệnh DELETE theo khóa chính maDatSan
        return false;
    }

    public List<LichDat> layDanhSach() {
        List<LichDat> list = new ArrayList<>();
        // TODO: SELECT * FROM LichDat
        return list;
    }

    public List<LichDat> timKiem(String tuKhoa) {
        List<LichDat> list = new ArrayList<>();
        // TODO: SELECT có điều kiện LIKE
        return list;
    }
}

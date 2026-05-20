package quanlysanbong.model;

import java.sql.Date;
import java.sql.Time;

public class LichDat {
    private int maDatSan;
    private int maTK;
    private int maKH;
    private int maSB;
    private Date ngayDat;
    private Time gioBatDau;
    private Time gioKetThuc;
    private String trangThai;
    private String ghiChu;

    public LichDat() {}

    public int getMaDatSan() { return maDatSan; }
    public void setMaDatSan(int maDatSan) { this.maDatSan = maDatSan; }

    public int getMaTK() { return maTK; }
    public void setMaTK(int maTK) { this.maTK = maTK; }

    public int getMaKH() { return maKH; }
    public void setMaKH(int maKH) { this.maKH = maKH; }

    public int getMaSB() { return maSB; }
    public void setMaSB(int maSB) { this.maSB = maSB; }

    public Date getNgayDat() { return ngayDat; }
    public void setNgayDat(Date ngayDat) { this.ngayDat = ngayDat; }

    public Time getGioBatDau() { return gioBatDau; }
    public void setGioBatDau(Time gioBatDau) { this.gioBatDau = gioBatDau; }

    public Time getGioKetThuc() { return gioKetThuc; }
    public void setGioKetThuc(Time gioKetThuc) { this.gioKetThuc = gioKetThuc; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}

package quanlysanbong.model;

import java.sql.Date;

public class HoaDon {
    private int maHD;
    private int maDatSan;
    private Date ngayLap;
    private double tienSan;
    private double tienDichVu;
    private double tongTien;
    private String phuongThucThanhToan;
    private String trangThaiThanhToan;
    private String ghiChu;

    public HoaDon() {}

    public double tinhTongTien() {
        this.tongTien = this.tienSan + this.tienDichVu;
        return this.tongTien;
    }

    public int getMaHD() { return maHD; }
    public void setMaHD(int maHD) { this.maHD = maHD; }

    public int getMaDatSan() { return maDatSan; }
    public void setMaDatSan(int maDatSan) { this.maDatSan = maDatSan; }

    public Date getNgayLap() { return ngayLap; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }

    public double getTienSan() { return tienSan; }
    public void setTienSan(double tienSan) { this.tienSan = tienSan; }

    public double getTienDichVu() { return tienDichVu; }
    public void setTienDichVu(double tienDichVu) { this.tienDichVu = tienDichVu; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public String getPhuongThucThanhToan() { return phuongThucThanhToan; }
    public void setPhuongThucThanhToan(String phuongThucThanhToan) { this.phuongThucThanhToan = phuongThucThanhToan; }

    public String getTrangThaiThanhToan() { return trangThaiThanhToan; }
    public void setTrangThaiThanhToan(String trangThaiThanhToan) { this.trangThaiThanhToan = trangThaiThanhToan; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}

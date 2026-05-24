package quanlysanbong.model;

public class ThongKeThang {
    private int thang;
    private int soHoaDon;
    private double tongTien;

    public ThongKeThang() {}

    public ThongKeThang(int thang, int soHoaDon, double tongTien) {
        this.thang = thang;
        this.soHoaDon = soHoaDon;
        this.tongTien = tongTien;
    }

    public int getThang() { return thang; }
    public void setThang(int thang) { this.thang = thang; }

    public int getSoHoaDon() { return soHoaDon; }
    public void setSoHoaDon(int soHoaDon) { this.soHoaDon = soHoaDon; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}

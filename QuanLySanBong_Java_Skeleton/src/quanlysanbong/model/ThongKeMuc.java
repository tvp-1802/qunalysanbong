package quanlysanbong.model;

public class ThongKeMuc {
    private String tenMuc;
    private int soHoaDon;
    private double tongTien;

    public ThongKeMuc() {}

    public ThongKeMuc(String tenMuc, int soHoaDon, double tongTien) {
        this.tenMuc = tenMuc;
        this.soHoaDon = soHoaDon;
        this.tongTien = tongTien;
    }

    public String getTenMuc() { return tenMuc; }
    public void setTenMuc(String tenMuc) { this.tenMuc = tenMuc; }

    public int getSoHoaDon() { return soHoaDon; }
    public void setSoHoaDon(int soHoaDon) { this.soHoaDon = soHoaDon; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}

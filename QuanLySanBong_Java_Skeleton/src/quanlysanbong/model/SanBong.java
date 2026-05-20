package quanlysanbong.model;

public class SanBong {
    private int maSB;
    private String tenSB;
    private String loaiSB;
    private double giaThue;
    private String trangThai;
    private String ghiChu;

    public SanBong() {}

    public SanBong(int maSB, String tenSB, String loaiSB, double giaThue, String trangThai, String ghiChu) {
        this.maSB = maSB;
        this.tenSB = tenSB;
        this.loaiSB = loaiSB;
        this.giaThue = giaThue;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public int getMaSB() { return maSB; }
    public void setMaSB(int maSB) { this.maSB = maSB; }

    public String getTenSB() { return tenSB; }
    public void setTenSB(String tenSB) { this.tenSB = tenSB; }

    public String getLoaiSB() { return loaiSB; }
    public void setLoaiSB(String loaiSB) { this.loaiSB = loaiSB; }

    public double getGiaThue() { return giaThue; }
    public void setGiaThue(double giaThue) { this.giaThue = giaThue; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
}

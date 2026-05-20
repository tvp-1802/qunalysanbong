package quanlysanbong.model;

public class TaiKhoan {
    private int maTK;
    private String tenDangNhap;
    private String matKhau;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String vaiTro;
    private String trangThai;

    public TaiKhoan() {}

    public TaiKhoan(int maTK, String tenDangNhap, String matKhau, String hoTen,
                    String soDienThoai, String email, String vaiTro, String trangThai) {
        this.maTK = maTK;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    public int getMaTK() { return maTK; }
    public void setMaTK(int maTK) { this.maTK = maTK; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }

    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}

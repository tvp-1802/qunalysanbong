CREATE DATABASE IF NOT EXISTS quanlysanbong CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE quanlysanbong;

CREATE TABLE TaiKhoan (
    maTK INT PRIMARY KEY AUTO_INCREMENT,
    tenDangNhap VARCHAR(50) NOT NULL UNIQUE,
    matKhau VARCHAR(100) NOT NULL,
    hoTen VARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(15),
    email VARCHAR(100),
    vaiTro VARCHAR(20) NOT NULL,
    trangThai VARCHAR(30) NOT NULL
);

CREATE TABLE SanBong (
    maSB INT PRIMARY KEY AUTO_INCREMENT,
    tenSB VARCHAR(100) NOT NULL,
    loaiSB VARCHAR(50),
    giaThue DOUBLE NOT NULL,
    trangThai VARCHAR(30),
    ghiChu TEXT
);

CREATE TABLE KhachHang (
    maKH INT PRIMARY KEY AUTO_INCREMENT,
    hoTen VARCHAR(100) NOT NULL,
    soDienThoai VARCHAR(15) NOT NULL UNIQUE,
    diaChi VARCHAR(255),
    ghiChu TEXT
);

CREATE TABLE LichDat (
    maDatSan INT PRIMARY KEY AUTO_INCREMENT,
    maTK INT NOT NULL,
    maKH INT NOT NULL,
    maSB INT NOT NULL,
    ngayDat DATE NOT NULL,
    gioBatDau TIME NOT NULL,
    gioKetThuc TIME NOT NULL,
    trangThai VARCHAR(30),
    ghiChu TEXT,
    FOREIGN KEY (maTK) REFERENCES TaiKhoan(maTK),
    FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
    FOREIGN KEY (maSB) REFERENCES SanBong(maSB)
);

CREATE TABLE HoaDon (
    maHD INT PRIMARY KEY AUTO_INCREMENT,
    maDatSan INT NOT NULL UNIQUE,
    ngayLap DATE NOT NULL,
    tienSan DOUBLE NOT NULL,
    tienDichVu DOUBLE DEFAULT 0,
    tongTien DOUBLE NOT NULL,
    phuongThucThanhToan VARCHAR(50),
    trangThaiThanhToan VARCHAR(30),
    ghiChu TEXT,
    FOREIGN KEY (maDatSan) REFERENCES LichDat(maDatSan)
);

INSERT INTO TaiKhoan(tenDangNhap, matKhau, hoTen, soDienThoai, email, vaiTro, trangThai)
VALUES 
('admin', '123456', 'Quản lý hệ thống', '0900000000', 'admin@gmail.com', 'Quản lý', 'Hoạt động'),
('nhanvien', '123456', 'Nhân viên sân bóng', '0911111111', 'nv@gmail.com', 'Nhân viên', 'Hoạt động');
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

-- Dữ liệu demo: Sân bóng
INSERT INTO SanBong(tenSB, loaiSB, giaThue, trangThai, ghiChu) VALUES
('Sân A - Khu 1', '5 người', 200000, 'Trống', 'Sân cỏ nhân tạo'),
('Sân B - Khu 1', '7 người', 300000, 'Đang thuê', 'Có đèn chiếu sáng'),
('Sân C - Khu 2', '11 người', 500000, 'Trống', 'Sân tiêu chuẩn'),
('Sân D - Khu 2', '5 người', 180000, 'Bảo trì', 'Đang sửa mặt sân');

-- Dữ liệu demo: Khách hàng
INSERT INTO KhachHang(hoTen, soDienThoai, diaChi, ghiChu) VALUES
('Nguyễn Văn An', '0901234567', 'Quận 1, TP.HCM', 'Khách thường xuyên'),
('Trần Thị Bình', '0912345678', 'Quận 3, TP.HCM', ''),
('Lê Hoàng Cường', '0923456789', 'Quận 7, TP.HCM', 'Đặt sân cuối tuần'),
('Phạm Minh Dũng', '0934567890', 'Thủ Đức, TP.HCM', '');

-- Dữ liệu demo: Lịch đặt sân (maTK 1=admin, 2=nhanvien)
INSERT INTO LichDat(maTK, maKH, maSB, ngayDat, gioBatDau, gioKetThuc, trangThai, ghiChu) VALUES
(2, 1, 1, '2026-05-20', '08:00:00', '10:00:00', 'Đã xác nhận', 'Đặt sáng'),
(2, 2, 2, '2026-05-21', '14:00:00', '16:00:00', 'Đã xác nhận', ''),
(1, 3, 3, '2026-05-22', '18:00:00', '20:00:00', 'Chờ xác nhận', 'Khách VIP'),
(2, 4, 1, '2026-05-23', '19:00:00', '21:00:00', 'Đã xác nhận', 'Giải nội bộ'),
(2, 1, 3, '2026-05-24', '06:00:00', '08:00:00', 'Hoàn thành', '');

-- Dữ liệu demo: Hóa đơn (gắn lịch đặt 1,2,4,5 - lịch 3 chưa có HĐ)
INSERT INTO HoaDon(maDatSan, ngayLap, tienSan, tienDichVu, tongTien, phuongThucThanhToan, trangThaiThanhToan, ghiChu) VALUES
(1, '2026-05-20', 400000, 50000, 450000, 'Tiền mặt', 'Đã thanh toán', ''),
(2, '2026-05-21', 600000, 80000, 680000, 'Chuyển khoản', 'Đã thanh toán', 'Có nước uống'),
(4, '2026-05-23', 400000, 100000, 500000, 'QR Code', 'Chưa thanh toán', 'Chờ thanh toán'),
(5, '2026-05-24', 1000000, 0, 1000000, 'Tiền mặt', 'Đã thanh toán', '');
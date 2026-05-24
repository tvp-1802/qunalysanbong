-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 24, 2026 lúc 07:52 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlysanbong`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `maHD` int(11) NOT NULL,
  `maDatSan` int(11) NOT NULL,
  `ngayLap` date NOT NULL,
  `tienSan` double NOT NULL,
  `tienDichVu` double DEFAULT 0,
  `tongTien` double NOT NULL,
  `phuongThucThanhToan` varchar(50) DEFAULT NULL,
  `trangThaiThanhToan` varchar(30) DEFAULT NULL,
  `ghiChu` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`maHD`, `maDatSan`, `ngayLap`, `tienSan`, `tienDichVu`, `tongTien`, `phuongThucThanhToan`, `trangThaiThanhToan`, `ghiChu`) VALUES
(11, 1, '2026-05-20', 400000, 50000, 450000, 'Tiền mặt', 'Đã thanh toán', ''),
(12, 2, '2026-05-21', 600000, 80000, 680000, 'Chuyển khoản', 'Đã thanh toán', 'Có nước uống'),
(13, 4, '2026-05-23', 400000, 100000, 500000, 'QR Code', 'Chưa thanh toán', 'Chờ thanh toán'),
(14, 5, '2026-05-24', 1000000, 0, 1000000, 'Tiền mặt', 'Đã thanh toán', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `maKH` int(11) NOT NULL,
  `hoTen` varchar(100) NOT NULL,
  `soDienThoai` varchar(15) NOT NULL,
  `diaChi` varchar(255) DEFAULT NULL,
  `ghiChu` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`maKH`, `hoTen`, `soDienThoai`, `diaChi`, `ghiChu`) VALUES
(1, 'Nguyễn Văn An', '0901234567', 'Quận 1, TP.HCM', 'Khách thường xuyên'),
(2, 'Trần Thị Bình', '0912345678', 'Quận 3, TP.HCM', ''),
(3, 'Lê Hoàng Cường', '0923456789', 'Quận 7, TP.HCM', 'Đặt sân cuối tuần'),
(4, 'Phạm Minh Dũng', '0934567890', 'Thủ Đức, TP.HCM', ''),
(15, 'Trần Anh Tuân', '0395751904', 'Hà Nội', 'Khách VIP');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `lichdat`
--

CREATE TABLE `lichdat` (
  `maDatSan` int(11) NOT NULL,
  `maTK` int(11) NOT NULL,
  `maKH` int(11) NOT NULL,
  `maSB` int(11) NOT NULL,
  `ngayDat` date NOT NULL,
  `gioBatDau` time NOT NULL,
  `gioKetThuc` time NOT NULL,
  `trangThai` varchar(30) DEFAULT NULL,
  `ghiChu` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `lichdat`
--

INSERT INTO `lichdat` (`maDatSan`, `maTK`, `maKH`, `maSB`, `ngayDat`, `gioBatDau`, `gioKetThuc`, `trangThai`, `ghiChu`) VALUES
(1, 1, 3, 3, '2026-05-22', '18:00:00', '20:00:00', 'Chờ xác nhận', 'Khách VIP'),
(2, 2, 4, 1, '2026-05-23', '19:00:00', '21:00:00', 'Đã xác nhận', 'Giải nội bộ'),
(3, 2, 1, 3, '2026-05-24', '06:00:00', '08:00:00', 'Hoàn thành', ''),
(4, 2, 1, 1, '2026-05-20', '08:00:00', '10:00:00', 'Đã xác nhận', 'Đặt sáng'),
(5, 2, 2, 2, '2026-05-21', '14:00:00', '16:00:00', 'Đã xác nhận', ''),
(6, 1, 3, 3, '2026-05-22', '18:00:00', '20:00:00', 'Chờ xác nhận', 'Khách VIP'),
(7, 2, 4, 1, '2026-05-23', '19:00:00', '21:00:00', 'Đã xác nhận', 'Giải nội bộ'),
(8, 2, 1, 3, '2026-05-24', '06:00:00', '08:00:00', 'Hoàn thành', ''),
(18, 2, 1, 1, '2026-05-20', '08:00:00', '10:00:00', 'Đã xác nhận', 'Đặt sáng'),
(19, 2, 2, 2, '2026-05-21', '14:00:00', '16:00:00', 'Đã xác nhận', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanbong`
--

CREATE TABLE `sanbong` (
  `maSB` int(11) NOT NULL,
  `tenSB` varchar(100) NOT NULL,
  `loaiSB` varchar(50) DEFAULT NULL,
  `giaThue` double NOT NULL,
  `trangThai` varchar(30) DEFAULT NULL,
  `ghiChu` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sanbong`
--

INSERT INTO `sanbong` (`maSB`, `tenSB`, `loaiSB`, `giaThue`, `trangThai`, `ghiChu`) VALUES
(1, 'Sân A - Khu 1', '5 người', 200000, 'Trống', 'Sân cỏ nhân tạo'),
(2, 'Sân B - Khu 1', '7 người', 300000, 'Đang thuê', 'Có đèn chiếu sáng'),
(3, 'Sân C - Khu 2', '11 người', 500000, 'Trống', 'Sân tiêu chuẩn'),
(4, 'Sân D - Khu 2', '5 người', 180000, 'Bảo trì', 'Đang sửa mặt sân');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `maTK` int(11) NOT NULL,
  `tenDangNhap` varchar(50) NOT NULL,
  `matKhau` varchar(100) NOT NULL,
  `hoTen` varchar(100) NOT NULL,
  `soDienThoai` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `vaiTro` varchar(20) NOT NULL,
  `trangThai` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`maTK`, `tenDangNhap`, `matKhau`, `hoTen`, `soDienThoai`, `email`, `vaiTro`, `trangThai`) VALUES
(1, 'admin', '123456', 'Quản lý hệ thống', '0900000000', 'admin@gmail.com', 'Quản lý', 'Hoạt động'),
(2, 'nhanvien', '123456', 'Nhân viên sân bóng', '0911111111', 'nv@gmail.com', 'Nhân viên', 'Hoạt động');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`maHD`),
  ADD UNIQUE KEY `maDatSan` (`maDatSan`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`maKH`),
  ADD UNIQUE KEY `soDienThoai` (`soDienThoai`);

--
-- Chỉ mục cho bảng `lichdat`
--
ALTER TABLE `lichdat`
  ADD PRIMARY KEY (`maDatSan`),
  ADD KEY `maTK` (`maTK`),
  ADD KEY `maKH` (`maKH`),
  ADD KEY `maSB` (`maSB`);

--
-- Chỉ mục cho bảng `sanbong`
--
ALTER TABLE `sanbong`
  ADD PRIMARY KEY (`maSB`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`maTK`),
  ADD UNIQUE KEY `tenDangNhap` (`tenDangNhap`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `maHD` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `maKH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `lichdat`
--
ALTER TABLE `lichdat`
  MODIFY `maDatSan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT cho bảng `sanbong`
--
ALTER TABLE `sanbong`
  MODIFY `maSB` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `maTK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`maDatSan`) REFERENCES `lichdat` (`maDatSan`);

--
-- Các ràng buộc cho bảng `lichdat`
--
ALTER TABLE `lichdat`
  ADD CONSTRAINT `lichdat_ibfk_1` FOREIGN KEY (`maTK`) REFERENCES `taikhoan` (`maTK`),
  ADD CONSTRAINT `lichdat_ibfk_2` FOREIGN KEY (`maKH`) REFERENCES `khachhang` (`maKH`),
  ADD CONSTRAINT `lichdat_ibfk_3` FOREIGN KEY (`maSB`) REFERENCES `sanbong` (`maSB`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

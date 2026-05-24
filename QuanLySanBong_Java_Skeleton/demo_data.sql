-- Chạy file này nếu đã có CSDL nhưng chưa có dữ liệu demo
-- (phpMyAdmin: chọn database quanlysanbong -> SQL -> dán và chạy)

USE quanlysanbong;

DELETE FROM HoaDon;
DELETE FROM LichDat;
DELETE FROM KhachHang;
DELETE FROM SanBong;

INSERT INTO SanBong(tenSB, loaiSB, giaThue, trangThai, ghiChu) VALUES
('Sân A - Khu 1', '5 người', 200000, 'Trống', 'Sân cỏ nhân tạo'),
('Sân B - Khu 1', '7 người', 300000, 'Đang thuê', 'Có đèn chiếu sáng'),
('Sân C - Khu 2', '11 người', 500000, 'Trống', 'Sân tiêu chuẩn'),
('Sân D - Khu 2', '5 người', 180000, 'Bảo trì', 'Đang sửa mặt sân');

INSERT INTO KhachHang(hoTen, soDienThoai, diaChi, ghiChu) VALUES
('Nguyễn Văn An', '0901234567', 'Quận 1, TP.HCM', 'Khách thường xuyên'),
('Trần Thị Bình', '0912345678', 'Quận 3, TP.HCM', ''),
('Lê Hoàng Cường', '0923456789', 'Quận 7, TP.HCM', 'Đặt sân cuối tuần'),
('Phạm Minh Dũng', '0934567890', 'Thủ Đức, TP.HCM', '');

INSERT INTO LichDat(maTK, maKH, maSB, ngayDat, gioBatDau, gioKetThuc, trangThai, ghiChu) VALUES
(2, 1, 1, '2026-05-20', '08:00:00', '10:00:00', 'Đã xác nhận', 'Đặt sáng'),
(2, 2, 2, '2026-05-21', '14:00:00', '16:00:00', 'Đã xác nhận', ''),
(1, 3, 3, '2026-05-22', '18:00:00', '20:00:00', 'Chờ xác nhận', 'Khách VIP'),
(2, 4, 1, '2026-05-23', '19:00:00', '21:00:00', 'Đã xác nhận', 'Giải nội bộ'),
(2, 1, 3, '2026-05-24', '06:00:00', '08:00:00', 'Hoàn thành', '');

INSERT INTO HoaDon(maDatSan, ngayLap, tienSan, tienDichVu, tongTien, phuongThucThanhToan, trangThaiThanhToan, ghiChu) VALUES
(1, '2026-05-20', 400000, 50000, 450000, 'Tiền mặt', 'Đã thanh toán', ''),
(2, '2026-05-21', 600000, 80000, 680000, 'Chuyển khoản', 'Đã thanh toán', 'Có nước uống'),
(4, '2026-05-23', 400000, 100000, 500000, 'QR Code', 'Chưa thanh toán', 'Chờ thanh toán'),
(5, '2026-05-24', 1000000, 0, 1000000, 'Tiền mặt', 'Đã thanh toán', '');

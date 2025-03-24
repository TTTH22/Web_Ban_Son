create table HoaDon
(
    id            int not null
        primary key,
    ma            varchar(10)
        unique,
    idNhanVien    int
        references NhanVien,
    idKhachHang   int
        references KhachHang,
    idVoucher     int
        references Voucher,
    ngayTao       date,
    tongTien      decimal(18, 2),
    ghiChu        nvarchar(250),
    loaiThanhToan nvarchar(50),
    loaiVanChuyen nvarchar(50),
    phiVanChuyen  nvarchar(50),
    trangThai     bit
)
go


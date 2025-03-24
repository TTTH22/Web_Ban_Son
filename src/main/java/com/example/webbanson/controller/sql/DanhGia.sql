create table DanhGia
(
    id          int not null
        primary key,
    idKhachHang int
        references KhachHang,
    idHoaDon    int
        references HoaDon,
    binhLuan    nvarchar(500),
    sao         int,
    trangThai   bit
)
go


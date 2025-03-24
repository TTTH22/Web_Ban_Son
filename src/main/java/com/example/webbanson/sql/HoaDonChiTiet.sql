create table HoaDonChiTiet
(
    id               int not null
        primary key,
    idHoaDon         int
        references HoaDon,
    idSanPhamChiTiet int
        references SanPhamChiTiet,
    soLuong          int,
    donGia           float
)
go


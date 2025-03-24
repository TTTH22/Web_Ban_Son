create table GioHangChiTiet
(
    idGioHang        int not null
        references GioHang,
    idSanPhamChiTiet int not null
        references SanPhamChiTiet,
    soLuong          int,
    donGia           float,
    primary key (idGioHang, idSanPhamChiTiet)
)
go


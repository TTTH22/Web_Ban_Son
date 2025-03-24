create table GioHang
(
    id          int not null
        primary key,
    idKhachHang int
        references KhachHang,
    trangThai   bit
)
go


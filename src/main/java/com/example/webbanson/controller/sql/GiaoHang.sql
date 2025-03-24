create table GiaoHang
(
    id        int not null
        primary key,
    idHoaDon  int
        references KhachHang,
    ngayGiao  date,
    ngayNhan  date,
    trangThai bit
)
go


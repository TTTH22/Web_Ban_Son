create table DiaChi
(
    id           int not null
        primary key,
    idKhachHang  int
        references KhachHang,
    tenNguoiNhan nvarchar(100),
    sdt          varchar(15),
    diaChiCuThe  nvarchar(100),
    xa           nvarchar(50),
    huyen        nvarchar(50),
    thanhPho     nvarchar(50),
    trangThai    bit
)
go


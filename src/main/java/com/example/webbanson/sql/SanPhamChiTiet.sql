create table SanPhamChiTiet
(
    id           int not null
        primary key,
    ma           varchar(10)
        unique,
    idSanPham    int
        references SanPham,
    idMauSac     int
        references MauSac,
    idChatLong   int
        references ChatLong,
    idKhoiLuong  int
        references KhoiLuong,
    soLuong      int,
    donGia       float,
    moTa         nvarchar(255),
    soLuongBan   int,
    soSaoDanhGia float
        check ([soSaoDanhGia] >= 0 AND [soSaoDanhGia] <= 5),
    trangThai    bit,
    donGiaGiam   float,
    ngayMoBan    date
)
go


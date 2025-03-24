create table KhachHang_Voucher
(
    idKhachHang int not null
        references KhachHang,
    idVoucher   int not null
        references Voucher,
    trangThai   bit,
    primary key (idKhachHang, idVoucher)
)
go


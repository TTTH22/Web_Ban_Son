create table SanPham
(
    id            int not null
        primary key,
    ten           nvarchar(50),
    moTa          nvarchar(255),
    trangThai     bit,
    AnhSanPham    varchar(100),
    idNSX         int
        constraint SanPham_fk_NSX
            references NSX,
    idDongSanPham int
        constraint SanPham_fk_DongSanPham
            references DongSanPham
)
go


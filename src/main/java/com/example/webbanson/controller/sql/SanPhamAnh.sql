create table SanPhamAnh
(
    id        int identity
        primary key,
    tenAnh    nvarchar(255),
    idSanPham int
        constraint SanPhamAnh_fk_SanPham
            references SanPham
)
go


create table ThanhPhan_SanPham
(
    idThanhPhan int not null
        references ThanhPhan,
    idSanPham   int not null
        references SanPham,
    primary key (idThanhPhan, idSanPham)
)
go


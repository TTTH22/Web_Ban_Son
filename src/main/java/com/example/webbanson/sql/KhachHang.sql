create table KhachHang
(
    id          int not null
        primary key,
    ma          varchar(10)
        unique,
    ten         nvarchar(50),
    ngaySinh    date,
    sdt         nvarchar(15),
    email       nvarchar(100),
    matKhau     nvarchar(100),
    gioiTinh    bit,
    diaChi      nvarchar(255),
    tongChiTieu int,
    idRank      int
        references Rank,
    trangThai   bit
)
go


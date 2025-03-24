create table NhanVien
(
    id        int not null
        primary key,
    ma        varchar(10)
        unique,
    ten       nvarchar(50),
    ngaySinh  date,
    gioiTinh  bit,
    sdt       nvarchar(15),
    email     nvarchar(100),
    matKhau   varchar(255),
    diaChi    nvarchar(255),
    chucVu    nvarchar(25),
    trangThai bit
)
go


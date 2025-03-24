create table Voucher
(
    id              int not null
        primary key,
    ma              varchar(10)
        unique,
    ten             nvarchar(50),
    moTa            nvarchar(255),
    dieuKien        float,
    loaiGiam        nvarchar(50),
    giaTriGiam      float,
    giaTriGiamToiDa float,
    ngayBatDau      date,
    ngayKetThuc     date,
    trangThai       bit
)
go


create table Rank
(
    id                  int not null
        primary key,
    ma                  varchar(10)
        unique,
    ten                 nvarchar(50),
    moTa                nvarchar(255),
    tongChiTieuDieuKien float,
    tongDonHangDieuKien int,
    mucGiam             float,
    mucGiamToiDa        float,
    trangThai           bit
)
go


create table ChatLong
(
    id        int not null
        primary key,
    ten       nvarchar(50),
    trangThai bit
)
go

create table DongSanPham
(
    ten       nvarchar(50),
    moTa      nvarchar(255),
    trangThai bit,
    ma        varchar(10),
    id        int identity
        constraint DongSanPham_pk
            primary key
)
go

create table KhoiLuong
(
    id        int identity
        constraint KhoiLuong_pk
            primary key,
    ma        varchar(10) not null,
    ten       int,
    moTa      nvarchar(255),
    trangThai bit
)
go

create table MauSac
(
    ten       nvarchar(50),
    ma        nvarchar(10),
    trangThai bit,
    moTa      nvarchar(255),
    id        int identity
        constraint MauSac_pk
            primary key
)
go

create table NSX
(
    ten       nvarchar(50),
    ma        nvarchar(10),
    trangThai bit,
    moTa      nvarchar(255),
    xuatSu    nvarchar(50),
    id        int identity
        constraint NSX_pk
            primary key
)
go

create table NhanVien
(
    id        int identity
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

create table Rank
(
    id                  int identity
        primary key,
    ma                  varchar(10)
        unique,
    ten                 nvarchar(50),
    moTa                nvarchar(255),
    tongChiTieuDieuKien float,
    mucGiam             float,
    mucGiamToiDa        float,
    trangThai           bit
)
go

create table KhachHang
(
    id          int identity
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
    tongChiTieu float,
    idRank      int
        references Rank,
    trangThai   bit,
    ngayTao     date
)
go

create table DiaChi
(
    id           int identity
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

create table GiaoHang
(
    id        int identity
        primary key,
    idHoaDon  int
        references KhachHang,
    ngayGiao  date,
    ngayNhan  date,
    trangThai bit
)
go

create table GioHang
(
    id          int identity
        primary key,
    idKhachHang int
        references KhachHang,
    trangThai   bit
)
go

create table SanPham
(
    ten           nvarchar(50),
    moTa          nvarchar(255),
    trangThai     bit,
    AnhSanPham    varchar(100),
    idNSX         int
        constraint SanPham_fk_NSX
            references NSX,
    idDongSanPham int
        constraint SanPham_fk_DongSanPham
            references DongSanPham,
    ma            varchar(10),
    id            int identity
        constraint PK_SanPham
            primary key
)
go

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

create table SanPhamChiTiet
(
    ma           varchar(10)
        unique,
    idSanPham    int
        constraint SanPhamChiTiet_fk_SanPham
            references SanPham,
    idMauSac     int
        constraint SanPhamChiTiet_fk_MauSac
            references MauSac,
    idKhoiLuong  int
        constraint SanPhamChiTiet_fk_KhoiLuong
            references KhoiLuong,
    soLuong      int,
    donGia       float,
    moTa         nvarchar(255),
    soLuongBan   int,
    soSaoDanhGia float
        check ([soSaoDanhGia] >= 0 AND [soSaoDanhGia] <= 5),
    trangThai    bit,
    donGiaGiam   float,
    ngayMoBan    date,
    id           int identity
        constraint SanPhamChiTiet_pk
            primary key
)
go

create table GioHangChiTiet
(
    idSanPhamChiTiet int not null
        constraint GioHangChiTiet_fk_SanPhamChiTiet
            references SanPhamChiTiet,
    soLuong          int,
    donGia           float,
    idGioHang        int not null
        constraint GioHangChiTiet_fk_GioHang
            references GioHang,
    id               int identity
        constraint GioHangChiTiet_pk
            primary key
)
go

create table ThanhPhan
(
    id   int identity
        primary key,
    ten  nvarchar(50),
    moTa nvarchar(255)
)
go

create table ThanhPhan_SanPham
(
    idThanhPhan int not null
        references ThanhPhan,
    idSanPham   int not null
        constraint ThanhPhan_SanPham___fk
            references SanPham,
    primary key (idThanhPhan, idSanPham)
)
go

create table Voucher
(
    id              int identity
        primary key,
    ma              varchar(10)
        unique,
    ten             nvarchar(50),
    moTa            nvarchar(255),
    dieuKien        float,
    loaiGiam        bit not null,
    giaTriGiam      float,
    giaTriGiamToiDa float,
    ngayBatDau      date,
    ngayKetThuc     date,
    trangThai       bit,
    hinhThucGiam    bit
)
go

create table HoaDon
(
    id                    int identity
        primary key,
    ma                    varchar(10)
        unique,
    idNhanVien            int
        references NhanVien,
    idKhachHang           int
        references KhachHang,
    idVoucher             int
        references Voucher,
    ngayTao               date,
    tongTien              decimal(18, 2),
    ghiChu                nvarchar(250),
    loaiThanhToan         nvarchar(50),
    phiVanChuyen          decimal(18, 2),
    trangThai             int,
    hinhThuc              bit,
    idVoucherPhiVanChuyen int
        constraint HoaDon_fk_Voucher
            references Voucher,
    lyDoHuy               nvarchar(50),
    lyDoHoanHang          nvarchar(50),
    danhGia               bit,
    ngayNhan              date,
    ghiChuHoanHang        nvarchar(255),
    idDiaChi              int
        constraint HoaDon_fk_DiaChi
            references DiaChi,
    ngayHuyHang           date,
    ngayHoanHang          date
)
go

create table DanhGia
(
    id        int identity
        primary key,
    idHoaDon  int
        references HoaDon,
    binhLuan  nvarchar(500),
    sao       int,
    trangThai bit
)
go

create table HoaDonChiTiet
(
    id               int identity
        primary key,
    idHoaDon         int
        constraint HoaDonChiTiet_fk_HoaDon
            references HoaDon,
    idSanPhamChiTiet int
        constraint HoaDonChiTiet_fk_SanPhamChiTiet
            references SanPhamChiTiet,
    soLuong          int,
    donGia           float,
    tenSanPham       nvarchar(50)
)
go

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





create schema dbo
go

create table dbo.ChatLong
(
    id        int not null
        primary key,
    ten       nvarchar(50),
    trangThai bit
)
go

create table dbo.DongSanPham
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

create table dbo.KhoiLuong
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

create table dbo.MauSac
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

create table dbo.NSX
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

create table dbo.NhanVien
(
    id        int identity not null
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

create table dbo.Rank
(
    id                  int identity not null
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

create table dbo.KhachHang
(
    id          int identity not null
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
        references dbo.Rank,
    trangThai   bit
)
go

create table dbo.DiaChi
(
    id           int identity not null
        primary key,
    idKhachHang  int
        references dbo.KhachHang,
    tenNguoiNhan nvarchar(100),
    sdt          varchar(15),
    diaChiCuThe  nvarchar(100),
    xa           nvarchar(50),
    huyen        nvarchar(50),
    thanhPho     nvarchar(50),
    trangThai    bit
)
go

create table dbo.GiaoHang
(
    id        int identity not null
        primary key,
    idHoaDon  int
        references dbo.KhachHang,
    ngayGiao  date,
    ngayNhan  date,
    trangThai bit
)
go

create table dbo.GioHang
(
    id          int identity not null
        primary key,
    idKhachHang int
        references dbo.KhachHang,
    trangThai   bit
)
go

create table dbo.GioHangChiTiet
(
    idGioHang        int identity not null
        references dbo.GioHang,
    idSanPhamChiTiet int not null,
    soLuong          int,
    donGia           float,
    primary key (idGioHang, idSanPhamChiTiet)
)
go

create table dbo.SanPham
(
    ten           nvarchar(50),
    moTa          nvarchar(255),
    trangThai     bit,
    AnhSanPham    varchar(100),
    idNSX         int
        constraint SanPham_fk_NSX
            references dbo.NSX,
    idDongSanPham int
        constraint SanPham_fk_DongSanPham
            references dbo.DongSanPham,
    ma            varchar(10),
    id            int identity
        constraint PK_SanPham
            primary key
)
go

create table dbo.SanPhamAnh
(
    id        int identity
        primary key,
    tenAnh    nvarchar(255),
    idSanPham int
        constraint SanPhamAnh_fk_SanPham
            references dbo.SanPham
)
go

create table dbo.SanPhamChiTiet
(
    ma           varchar(10)
        unique,
    idSanPham    int
        constraint SanPhamChiTiet_fk_SanPham
            references dbo.SanPham,
    idMauSac     int
        constraint SanPhamChiTiet_fk_MauSac
            references dbo.MauSac,
    idKhoiLuong  int
        constraint SanPhamChiTiet_fk_KhoiLuong
            references dbo.KhoiLuong,
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

create table dbo.ThanhPhan
(
    id   int identity not null
        primary key,
    ten  nvarchar(50),
    moTa nvarchar(255)
)
go

create table dbo.ThanhPhan_SanPham
(
    idThanhPhan int not null
        references dbo.ThanhPhan,
    idSanPham   int not null
        constraint ThanhPhan_SanPham___fk
            references dbo.SanPham,
    primary key (idThanhPhan, idSanPham)
)
go

create table dbo.Voucher
(
    id              int identity not null
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

create table dbo.HoaDon
(
    id            int identity not null
        primary key,
    ma            varchar(10)
        unique,
    idNhanVien    int
        references dbo.NhanVien,
    idKhachHang   int
        references dbo.KhachHang,
    idVoucher     int
        references dbo.Voucher,
    ngayTao       date,
    tongTien      decimal(18, 2),
    ghiChu        nvarchar(250),
    loaiThanhToan nvarchar(50),
    phiVanChuyen  nvarchar(50),
    trangThai     bit,
    hinhThuc      bit,
    xacNhan       bit
)
go

create table dbo.DanhGia
(
    id          int identity not null
        primary key,
    idKhachHang int
        references dbo.KhachHang,
    idHoaDon    int
        references dbo.HoaDon,
    binhLuan    nvarchar(500),
    sao         int,
    trangThai   bit
)
go

create table dbo.HoaDonChiTiet
(
    id               int identity not null
        primary key,
    idHoaDon         int
        constraint HoaDonChiTiet_fk_HoaDon
            references dbo.HoaDon,
    idSanPhamChiTiet int
        constraint HoaDonChiTiet_fk_SanPhamChiTiet
            references dbo.SanPhamChiTiet,
    soLuong          int,
    donGia           float
)
go

create table dbo.KhachHang_Voucher
(
    idKhachHang int not null
        references dbo.KhachHang,
    idVoucher   int not null
        references dbo.Voucher,
    trangThai   bit,
    primary key (idKhachHang, idVoucher)
)
go

create user INFORMATION_SCHEMA
go

create user dbo with default_schema = dbo
go

create role db_accessadmin authorization dbo
go

create role db_backupoperator authorization dbo
go

create role db_datareader authorization dbo
go

create role db_datawriter authorization dbo
go

create role db_ddladmin authorization dbo
go

create role db_denydatareader authorization dbo
go

create role db_denydatawriter authorization dbo
go

create role db_owner authorization dbo
go

create role db_securityadmin authorization dbo
go

create user guest with default_schema = guest
go

create role [public] authorization dbo
go

create user sys
go

INSERT INTO dbo.SanPham (ten, moTa, trangThai, AnhSanPham, idNSX, idDongSanPham, ma)
VALUES
    (N'Son MAC Ruby Woo', N'Son lì màu đỏ', 1, N'/img/SanPham/AnhSanPham1.jpg', 1, 1, N'SP01'),
    (N'Son Dior Addict', N'Son bóng dưỡng ẩm', 1, N'/img/SanPham/AnhSanPham2.png', 3, 2, N'SP02'),
    (N'Son Test', N'Sản phẩm test cho id = 3', 1, N'/img/SanPham/AnhSanPham3.png', 4, 3, N'SP03'),
    (N'Oreal Rouge', N'Son lì màu đỏ cổ điển', 1, N'/img/SanPham/AnhSanPham4.png', 4, 4, N'SP04'),
    (N'Son YSL Tatouage', N'Son kem lì hot trend', 1, N'/img/SanPham/AnhSanPham5.png', 5, 5, N'SP05'),
    (N'Son Chanel Coco', N'Dòng son dưỡng có màu', 1, N'/img/SanPham/AnhSanPham6.png', 6, 6, N'SP06'),
    (N'Son Etude House Dear Darling', N'Son tint bóng mịn', 1, N'/img/SanPham/AnhSanPham7.png', 7, 7, N'SP07'),
    (N'Son Innisfree Vivid Cotton', N'Son kem mịn nhẹ', 1, N'/img/SanPham/AnhSanPham8.png', 8, 8, N'SP08'),
    (N'Son Maybelline SuperStay', N'Son lì lâu trôi', 1, null, 2, 1, N'SP09'),
    (N'Son Velvet Lip Tint', N'Son kem lì Hàn Quốc', 1, null, 3, 2, N'SP10'),
    (N'Son Black Rouge Air Fit', N'Son tint mịn nhẹ', 1, null, 4, 3, N'SP11'),
    (N'Son Bbia Last Velvet', N'Son lì mịn môi', 1, null, 1, 4, N'SP12'),
    (N'Son Peripera Ink Airy', N'Son tint nhẹ môi', 1, null, 6, 5, N'SP13'),
    (N'Son Laneige Stained Glow', N'Son dưỡng bóng', 1, null, 7, 6, N'SP14'),
    (N'Son Tom Ford Lip Color', N'Son cao cấp', 1, null, 8, 7, N'SP15'),
    (N'Son Fenty Beauty Mattemoiselle', N'Son lì sắc nét', 1, null, 5, 8, N'SP16'),
    (N'Son Charlotte Tilbury Pillow Talk', N'Son nude hồng đất', 1, null, 4, 1, N'SP17'),
    (N'Son Shu Uemura Rouge', N'Son lì Nhật Bản', 1, null, 3, 2, N'SP18'),
    (N'Son Clio Mad Matte', N'Son lì không trôi', 1, null, 2, 3, N'SP19'),
    (N'Son ColourPop Ultra Matte', N'Son lì giá tốt', 1, null, 1, 4, N'SP20'),
    (N'Son Romand Zero Velvet', N'Son nhẹ môi', 1, null, 6, 5, N'SP21'),
    (N'Son NYX Soft Matte', N'Son lì nhẹ nhàng', 1, null, 7, 6, N'SP22'),
    (N'Son Pat McGrath Labs', N'Son high-end', 1, null, 8, 7, N'SP23'),
    (N'Son Huda Beauty Power Bullet', N'Son lì Middle East', 1, null, 6, 8, N'SP24'),
    (N'Son Gucci Satin', N'Son satin sang trọng', 1, null, 7, 1, N'SP25'),
    (N'Son Armani Lip Maestro', N'Son kem lì đẳng cấp', 1, null, 8, 2, N'SP26'),
    (N'Son Bourjois Velvet', N'Son lì Pháp', 1, null, 4, 3, N'SP27'),
    (N'Son Urban Decay Vice', N'Son lì đa dạng màu', 1, null, 5, 4, N'SP28'),
    (N'Hoàn', N'Hoàn đẹp trai siêu cấp vip pro', 1, N'/img/SanPham/AnhSanPham98918075.png', 4, 1, N'SP45629921'),
    (N'sdssf', N'fsdfdsfdsff', 1, N'/img/SanPham/AnhSanPham07593900.png', 1, 1, N'SP07593900');

INSERT INTO dbo.SanPhamAnh (tenAnh, idSanPham)
VALUES  (N'/img/SanPhamChiTiet/AnhSanPhamChiiTiet1.png', 1),
        (N'/img/SanPhamChiTiet/AnhSanPhamChiiTiet2.png', 1),
        (N'/img/SanPhamChiTiet/AnhSanPhamChiiTiet3.png', 1),
        (N'/img/SanPhamChiTiet/AnhSanPhamChiiTiet4.png', 1);

INSERT INTO dbo.KhoiLuong (ma, ten, moTa, trangThai)
VALUES
    (N'KL001', 3, N'Son mini', 1),
    (N'KL002', 5, N'Son tiêu chuẩn', 1),
    (N'KL003', 7, N'Son dung lượng lớn', 1),
    (N'KL004', 12, N'Phiên bản đặc biệt 12g', 1),
    (N'KL005', 8, N'Bản tiêu chuẩn 8g', 1),
    (N'KL006', 18, N'Kích thước lớn dùng lâu dài', 1),
    (N'KL007', 25, N'Kích thước extra size', 1),
    (N'KL008', 6, N'Size mini di động', 0),
    (N'KL20168857', 426, N'fdfdsf', 1),
    (N'KL19048324', 425, N'fdfdsf', 1),
    (N'KL35025190', 125, N'Vừa vừa', 1);

INSERT INTO dbo.MauSac (ten, ma, trangThai, moTa)
VALUES
    (N'Đỏ', N'#FF0000', 1, NULL),
    (N'Hồng', N'#FFC0CB', 1, NULL),
    (N'Cam', N'#FFA500', 1, NULL),
    (N'Hồng Đào', N'#F88379', 1, NULL),
    (N'Nâu Đất', N'#A0522D', 1, NULL),
    (N'Cam San Hô', N'#FF7F50', 1, NULL),
    (N'Tím Lavender', N'#E6E6FA', 1, NULL),
    (N'Beige Nhạt', N'#F5F5DC', 1, NULL),
    (N'Hoàn', N'#1a1aff', 1, N'Hoàn');

INSERT INTO dbo.DongSanPham (ten, moTa, trangThai, ma)
VALUES
    (N'Son lì cao cấp', N'Dòng cao cấp', 1, NULL),
    (N'Son dưỡng', N'Dưỡng môi', 1, NULL),
    (N'Son thuần chay', N'Dưỡng môi', 1, NULL),
    (N'Son Lì Siêu Mịn', N'Dòng son lì không khô môi', 1, NULL),
    (N'Son Bóng 3D', N'Dòng son bóng ánh kim', 1, NULL),
    (N'Son Dưỡng Tự Nhiên', N'Dưỡng môi từ thiên nhiên', 1, NULL),
    (N'Son Kem Siêu Nhẹ', N'Chất kem mịn môi nhẹ nhàng', 1, N''),
    (N'Son Thảo Mộc', N'Thành phần thảo mộc an toàn', 1, NULL),
    (N'Hoàn', N'Hoàn', 1, N'DSP5803412'),
    (N'Dior', N'đssfgdsf', 1, N'DSP5937424');

INSERT INTO dbo.NSX (ten, ma, trangThai, moTa, xuatSu)
VALUES
    (N'MAC', N'MAC01', 1, NULL, NULL),
    (N'Maybelline', N'MAY02', 1, NULL, NULL),
    (N'Dior', N'DIO03', 1, NULL, NULL),
    (N'TH', N'TH22', 1, NULL, NULL),
    (N'YSL', N'YSL05', 1, NULL, NULL),
    (N'Chanel', N'CHAN6', 1, NULL, NULL),
    (N'Etude House', N'ETUD7', 1, NULL, NULL),
    (N'Innisfree', N'INNI8', 1, NULL, NULL),
    (N'Hoàn', N'NSX9346505', 1, N'fdfd', N'Trung Quốc');

insert into dbo.SanPhamChiTiet (ma, idSanPham, idMauSac, idKhoiLuong, soLuong, donGia, moTa, soLuongBan, soSaoDanhGia, trangThai, donGiaGiam, ngayMoBan)
values  (N'SPCT01', 1, 3, 2, 100, 550000, N'Màu đỏ lì 5g', 50, 4.5, 1, 495000, N'2024-12-01'),
        (N'SPCT02', 2, 2, 1, 80, 750000, N'Màu hồng bóng 3g', 30, 4.7, 1, 675000, N'2024-12-01'),
        (N'SPCT03', 1, 3, 3, 50, 600000, N'Son MAC Ruby Woo màu cam lì 7g', 20, 4.6, 1, 540000, N'2024-12-01'),
        (N'SPCT04', 2, 1, 2, 70, 800000, N'Son Dior Addict màu đỏ bóng 5g', 35, 4.8, 1, 720000, N'2024-12-01'),
        (N'SPCT05', 1, 2, 1, 60, 580000, N'Son MAC Ruby Woo màu hồng lì 3g', 25, 4.3, 1, 522000, N'2024-12-01'),
        (N'SPCT06', 2, 3, 3, 40, 820000, N'Son Dior Addict màu cam bóng 7g', 18, 4.9, 1, 738000, N'2024-12-01'),
        (N'SPCT07', 7, 7, 7, 130, 900000, N'Màu đỏ rượu metallic 25g', 90, 4.7, 1, 810000, N'2024-12-01'),
        (N'SPCT08', 8, 8, 8, 140, 950000, N'Màu cam san hô shimmer 6g', 100, 4.8, 1, 855000, N'2024-12-01'),
        (N'SPCT09', 1, 2, 4, 150, 600000, N'Màu đỏ bóng 12g', 110, 4.9, 1, 540000, N'2024-12-01'),
        (N'SPCT10', 2, 3, 5, 160, 650000, N'Màu hồng kem 8g', 120, 5, 1, 585000, N'2024-12-01'),
        (N'SPCT11', 3, 4, 6, 170, 700000, N'Màu cam glossy 18g', 130, 4.6, 1, 630000, N'2023-12-21'),
        (N'SPCT12', 4, 5, 7, 180, 750000, N'Màu nude sheer 25g', 140, 4.7, 1, 675000, N'2023-12-21'),
        (N'SPCT13', 5, 6, 8, 190, 800000, N'Màu tím cream matte 6g', 150, 4.8, 1, 720000, N'2023-12-21'),
        (N'SPCT14', 6, 7, 1, 200, 850000, N'Màu hồng đất metallic 3g', 160, 4.9, 1, 765000, N'2023-12-21'),
        (N'SPCT15', 7, 8, 2, 210, 900000, N'Màu đỏ rượu shimmer 5g', 170, 5, 1, 810000, N'2023-12-21'),
        (N'SPCT16', 8, 1, 3, 220, 950000, N'Màu cam san hô lì 7g', 180, 4.7, 1, 855000, N'2023-12-21'),
        (N'SPCT17', 1, 3, 7, 230, 600000, N'Màu đỏ kem 12g', 190, 4.8, 1, 540000, N'2023-12-21'),
        (N'SPCT18', 2, 4, 8, 240, 650000, N'Màu hồng glossy 8g', 200, 4.9, 1, 585000, N'2023-12-21'),
        (N'SPCT19', 3, 5, 1, 250, 700000, N'Màu cam sheer 18g', 210, 5, 1, 630000, N'2023-12-21'),
        (N'SPCT20', 4, 6, 2, 260, 750000, N'Màu nude cream matte 25g', 220, 4.6, 1, 675000, N'2023-12-21'),
        (N'SPCT21', 5, 7, 3, 270, 800000, N'Màu tím metallic 6g', 230, 4.7, 1, 720000, N'2024-01-21'),
        (N'SPCT22', 6, 8, 4, 280, 850000, N'Màu hồng đất shimmer 3g', 240, 4.8, 1, 750000, N'2024-01-21'),
        (N'SPCT23', 7, 1, 5, 290, 900000, N'Màu đỏ rượu lì 5g', 250, 4.9, 1, 900000, N'2024-01-21'),
        (N'SPCT24', 8, 2, 6, 300, 950000, N'Màu cam san hô bóng 7g', 260, 5, 1, 900000, N'2024-01-21'),
        (N'SPCT25', 1, 4, 8, 310, 600000, N'Màu đỏ kem 12g', 270, 4.7, 1, 550000, N'2024-01-21'),
        (N'SPCT26', 3, 6, 7, 268, 650000, N'Màu đặc biệt matte 25g', 12, 4.7, 1, 617500, N'2024-01-21'),
        (N'SPCT27', 20, 8, 4, 48, 700000, N'Màu đặc biệt matte 7g', 193, 4.7, 1, 665000, N'2024-01-21'),
        (N'SPCT28', 15, 7, 4, 90, 550000, N'Màu đặc biệt shimmer 11g', 216, 4.4, 1, 522500, N'2024-01-21'),
        (N'SPCT29', 19, 3, 7, 100, 750000, N'Màu đặc biệt shimmer 16g', 68, 4.9, 1, 712500, N'2024-01-21'),
        (N'SPCT30', 10, 3, 2, 188, 950000, N'Màu đặc biệt matte 21g', 222, 4.8, 1, 902500, N'2024-01-21'),
        (N'SPCT31', 1, 7, 3, 275, 800000, N'Màu đặc biệt lì 8g', 91, 5, 1, 760000, N'2024-02-15'),
        (N'SPCT32', 17, 7, 5, 50, 950000, N'Màu đặc biệt shimmer 5g', 114, 4.6, 1, 902500, N'2024-02-15'),
        (N'SPCT33', 6, 6, 1, 290, 600000, N'Màu đặc biệt matte 14g', 111, 4.5, 1, 570000, N'2024-02-15'),
        (N'SPCT34', 27, 1, 5, 157, 650000, N'Màu đặc biệt lì 12g', 80, 4.5, 1, 617500, N'2024-02-15'),
        (N'SPCT35', 1, 3, 6, 294, 950000, N'Màu đặc biệt shimmer 7g', 34, 4.8, 1, 902500, N'2024-02-15'),
        (N'SPCT36', 19, 4, 4, 257, 950000, N'Màu đặc biệt shimmer 21g', 161, 4.4, 1, 902500, N'2024-02-15'),
        (N'SPCT37', 25, 5, 7, 260, 900000, N'Màu đặc biệt shimmer 13g', 77, 4.4, 1, 855000, N'2024-02-15'),
        (N'SPCT38', 21, 3, 1, 170, 900000, N'Màu đặc biệt matte 3g', 74, 4.9, 1, 855000, N'2024-02-15'),
        (N'SPCT39', 11, 8, 4, 156, 650000, N'Màu đặc biệt lì 3g', 200, 4.5, 1, 617500, N'2024-02-15'),
        (N'SPCT40', 21, 2, 7, 74, 750000, N'Màu đặc biệt bóng 20g', 149, 4.6, 1, 712500, N'2024-02-15'),
        (N'SPCT41', 28, 4, 7, 148, 650000, N'Màu đặc biệt matte 24g', 112, 5, 1, 617500, N'2024-02-15'),
        (N'SPCT42', 12, 2, 5, 256, 600000, N'Màu đặc biệt shimmer 22g', 151, 4.8, 1, 570000, N'2024-02-15'),
        (N'SPCT43', 9, 5, 6, 177, 550000, N'Màu đặc biệt bóng 13g', 130, 4.8, 1, 522500, N'2024-02-15'),
        (N'SPCT44', 3, 7, 7, 81, 600000, N'Màu đặc biệt bóng 22g', 258, 5, 1, 570000, N'2024-02-15'),
        (N'SPCT45', 9, 7, 5, 131, 900000, N'Màu đặc biệt lì 16g', 164, 4.9, 1, 855000, N'2024-02-15'),
        (N'SPCT46', 13, 4, 6, 50, 800000, N'Màu đặc biệt lì 5g', 156, 4.4, 1, 760000, N'2024-02-15'),
        (N'SPCT47', 1, 3, 6, 265, 750000, N'Màu đặc biệt matte 5g', 39, 4.7, 1, 712500, N'2024-02-15'),
        (N'SPCT48', 17, 5, 5, 228, 950000, N'Màu đặc biệt bóng 20g', 199, 4.9, 1, 902500, N'2024-02-15'),
        (N'SPCT49', 3, 7, 7, 200, 650000, N'Màu đặc biệt lì 15g', 129, 4.5, 1, 617500, N'2024-02-15'),
        (N'SPCT50', 13, 2, 2, 55, 650000, N'Màu đặc biệt bóng 18g', 264, 4.7, 1, 617500, N'2024-02-15'),
        (N'SPCT51', 5, 6, 6, 147, 800000, N'Màu đặc biệt lì 4g', 116, 4.9, 1, 776000, N'2023-11-15'),
        (N'SPCT52', 26, 7, 3, 289, 650000, N'Màu đặc biệt shimmer 7g', 251, 4.7, 1, 630500, N'2023-11-15'),
        (N'SPCT53', 22, 3, 2, 104, 700000, N'Màu đặc biệt bóng 10g', 144, 4.4, 1, 679000, N'2023-11-15'),
        (N'SPCT54', 7, 4, 7, 95, 700000, N'Màu đặc biệt lì 6g', 162, 4.7, 1, 679000, N'2023-11-15'),
        (N'SPCT55', 24, 1, 4, 46, 950000, N'Màu đặc biệt bóng 13g', 232, 4.8, 1, 921500, N'2023-11-15'),
        (N'SPCT56', 23, 4, 6, 76, 700000, N'Màu đặc biệt matte 3g', 75, 4.4, 1, 679000, N'2023-11-15'),
        (N'SPCT57', 1, 6, 6, 106, 700000, N'Màu đặc biệt matte 11g', 200, 4.8, 1, 679000, N'2023-11-15'),
        (N'SPCT614287', 5, 1, 1, 2, 232323, N'fdfdfd', null, null, 1, 23, N'2025-03-20'),
        (N'SPCT345461', 5, 9, 1, 2, 232323, N'fdfdfd', null, null, 1, 23, N'2025-03-20'),
        (N'SPCT282286', 5, 8, 1, 2, 232323, N'fdfdfd', null, null, 1, 23, N'2025-03-20');



INSERT INTO dbo.NhanVien (ma, ten, ngaySinh, gioiTinh, sdt, email, matKhau, diaChi, chucVu, trangThai)
VALUES
    ('NV001', N'Nguyễn Văn An', '1995-04-10', 1, '0901234567', 'an.nguyen@example.com', '123456', N'Hà Nội', N'Quản lý', 1),
    ('NV002', N'Trần Thị Bích', '1998-07-15', 0, '0912345678', 'bich.tran@example.com', 'abcdef', N'TP Hồ Chí Minh', N'Nhân viên', 1),
    ('NV003', N'Phạm Văn Cường', '1993-09-20', 1, '0923456789', 'cuong.pham@example.com', 'qwerty', N'Đà Nẵng', N'Nhân viên', 1),
    ('NV004', N'Lê Thị Dung', '1996-02-28', 0, '0934567890', 'dung.le@example.com', 'letidung@123', N'Hải Phòng', N'Nhân viên', 1),
    ('NV005', N'Hoàng Minh Đức', '1994-12-05', 1, '0945678901', 'duc.hoang@example.com', 'minhduc321', N'Cần Thơ', N'Quản lý', 1),
    ('NV006', N'Vũ Thị Hằng', '1999-06-18', 0, '0956789012', 'hang.vu@example.com', 'vu_hang99', N'Bình Dương', N'Nhân viên', 1),
    ('NV007', N'Đỗ Văn Kiên', '1997-08-22', 1, '0967890123', 'kien.do@example.com', 'doKien789', N'Quảng Ninh', N'Nhân viên', 1),
    ('NV008', N'Ngô Thị Lan', '2000-10-30', 0, '0978901234', 'lan.ngo@example.com', 'lanngo2020', N'Huế', N'Nhân viên', 1),
    ('NV009', N'Bùi Anh Tuấn', '1992-05-14', 1, '0989012345', 'tuan.bui@example.com', 'tuanbui999', N'Đồng Nai', N'Nhân viên', 1),
    ('NV010', N'Nguyễn Thị Xuân', '1991-11-25', 0, '0990123456', 'xuan.nguyen@example.com', 'xuan5678', N'Nghệ An', N'Quản lý', 1);

INSERT INTO dbo.Rank (ma, ten, moTa, tongChiTieuDieuKien, tongDonHangDieuKien, mucGiam, mucGiamToiDa, trangThai)
VALUES
    ('RANK1', N'Silver', N'Khách hàng thân thiết với chi tiêu tối thiểu 5 triệu.', 5000000, 10, 5.0, 50000, 1),
    ('RANK2', N'Gold', N'Khách hàng VIP với chi tiêu tối thiểu 10 triệu.', 10000000, 20, 10.0, 100000, 1),
    ('RANK3', N'Diamond', N'Khách hàng cao cấp với chi tiêu tối thiểu 20 triệu.', 20000000, 40, 15.0, 200000, 1);


INSERT INTO dbo.KhachHang (ma, ten, ngaySinh, sdt, email, matKhau, gioiTinh, diaChi, tongChiTieu, idRank, trangThai)
VALUES
    ('KH001', N'Nguyễn Văn A', '1990-05-20', '0901234567', 'a.nguyen@example.com', 'pass123', 1, N'Hà Nội', 5000000, 1, 1),
    ('KH002', N'Trần Thị B', '1995-08-15', '0912345678', 'b.tran@example.com', '123abc', 0, N'TP Hồ Chí Minh', 8000000, 2, 1),
    ('KH003', N'Lê Văn C', '1988-12-10', '0923456789', 'c.le@example.com', 'letung888', 1, N'Đà Nẵng', 3000000, 1, 1),
    ('KH004', N'Phạm Thị D', '2000-07-25', '0934567890', 'd.pham@example.com', 'dpham2020', 0, N'Cần Thơ', 10000000, 3, 1),
    ('KH005', N'Hoàng Văn E', '1992-11-05', '0945678901', 'e.hoang@example.com', 'hoang456', 1, N'Hải Phòng', 1500000, 1, 1),
    ('KH006', N'Vũ Thị F', '1997-09-18', '0956789012', 'f.vu@example.com', 'vuF123', 0, N'Quảng Ninh', 6500000, 2, 1),
    ('KH007', N'Đỗ Văn G', '1991-04-22', '0967890123', 'g.do@example.com', 'dog789', 1, N'Bình Dương', 7200000, 2, 1),
    ('KH008', N'Ngô Thị H', '1999-01-30', '0978901234', 'h.ngo@example.com', 'ngoH2023', 0, N'Huế', 12000000, 3, 1),
    ('KH009', N'Bùi Anh I', '1993-06-14', '0989012345', 'i.bui@example.com', 'buii999', 1, N'Đồng Nai', 4000000, 1, 1),
    ('KH010', N'Nguyễn Thị J', '1989-03-25', '0990123456', 'j.nguyen@example.com', 'nguyenJ5678', 0, N'Nghệ An', 9500000, 3, 1);

INSERT INTO dbo.HoaDon (ma, idNhanVien, idKhachHang, idVoucher, ngayTao, tongTien, ghiChu, loaiThanhToan, phiVanChuyen, trangThai, hinhThuc, xacNhan)
VALUES
    (N'HD036', 1, 2, NULL, '2025-02-25', 800000, N'Khách lẻ', N'Tiền mặt', 18000, 1, 1, 1),
    (N'HD037', 2, 3, NULL, '2025-02-24', 900000, NULL, N'Chuyển khoản', 20000, 1, 0, 1),
    (N'HD038', 3, 4, NULL, '2025-02-23', 750000, N'Khách VIP', N'Tiền mặt', 17000, 1, 1, 0),
    (N'HD039', 4, 5, NULL, '2025-02-22', 1100000, NULL, N'Chuyển khoản', 22000, 1, 0, 1),
    (N'HD040', 5, 6, NULL, '2025-02-21', 950000, N'Khách sỉ', N'Tiền mặt', 19000, 1, 1, 1),
    (N'HD041', 1, 7, NULL, '2025-02-20', 720000, NULL, N'Chuyển khoản', 16000, 1, 0, 1),
    (N'HD042', 2, 8, NULL, '2025-02-19', 880000, N'Khách thân thiết', N'Tiền mặt', 20000, 1, 1, 0),
    (N'HD043', 3, 9, NULL, '2025-02-18', 1020000, NULL, N'Chuyển khoản', 18000, 1, 0, 1),
    (N'HD044', 4, 10, NULL, '2025-02-17', 1300000, N'Khách hàng online', N'Tiền mặt', 25000, 1, 1, 1),
    (N'HD045', 5, 11, NULL, '2025-02-16', 780000, NULL, N'Chuyển khoản', 17000, 1, 0, 1),
    (N'HD046', 1, 2, NULL, '2025-02-15', 910000, N'Đơn online', N'Tiền mặt', 21000, 1, 1, 0),
    (N'HD047', 2, 3, NULL, '2025-02-14', 1200000, NULL, N'Chuyển khoản', 23000, 1, 0, 1),
    (N'HD048', 3, 4, NULL, '2025-02-13', 670000, N'Khách lẻ', N'Tiền mặt', 15000, 1, 1, 1),
    (N'HD049', 4, 5, NULL, '2025-02-12', 990000, NULL, N'Chuyển khoản', 19000, 1, 0, 1),
    (N'HD050', 5, 6, NULL, '2025-02-11', 1150000, N'Khách VIP', N'Tiền mặt', 24000, 1, 1, 0),
    (N'HD051', 1, 7, NULL, '2025-02-10', 760000, NULL, N'Chuyển khoản', 18000, 1, 0, 1),
    (N'HD052', 2, 8, NULL, '2025-02-09', 1340000, N'Khách sỉ', N'Tiền mặt', 25000, 1, 1, 1),
    (N'HD053', 3, 9, NULL, '2025-02-08', 860000, NULL, N'Chuyển khoản', 17000, 1, 0, 1),
    (N'HD054', 4, 10, NULL, '2025-02-07', 980000, N'Khách VIP', N'Tiền mặt', 20000, 1, 1, 0),
    (N'HD055', 5, 11, NULL, '2025-02-06', 720000, NULL, N'Chuyển khoản', 18000, 1, 0, 1),
    (N'HD056', 1, 2, NULL, '2025-02-05', 1030000, N'Khách lẻ', N'Tiền mặt', 22000, 1, 1, 1),
    (N'HD057', 2, 3, NULL, '2025-02-04', 640000, NULL, N'Chuyển khoản', 15000, 1, 0, 1),
    (N'HD058', 3, 4, NULL, '2025-02-03', 1250000, N'Khách sỉ', N'Tiền mặt', 23000, 1, 1, 0),
    (N'HD059', 4, 5, NULL, '2025-02-02', 890000, NULL, N'Chuyển khoản', 19000, 1, 0, 1),
    (N'HD060', 5, 6, NULL, '2025-02-01', 1120000, N'Khách thân thiết', N'Tiền mặt', 25000, 1, 1, 1);

INSERT INTO dbo.DiaChi (idKhachHang, tenNguoiNhan, sdt, diaChiCuThe, xa, huyen, thanhPho, trangThai)
VALUES
    (2,  N'Nguyễn Văn A',  '0912345678', N'123 Đường ABC',  N'Phường 1',  N'Quận 1',  N'TP.HCM', 1),
    (3,  N'Trần Thị B',    '0987654321', N'456 Đường XYZ',  N'Phường 2',  N'Quận 3',  N'TP.HCM', 1),
    (4,  N'Lê Minh C',     '0938123456', N'789 Đường DEF',  N'Phường 5',  N'Quận 7',  N'Hà Nội',  1),
    (5,  N'Hoàng Duy D',   '0977123456', N'12 Đường GHI',   N'Xã Bình An', N'Huyện Củ Chi', N'TP.HCM', 1),
    (6,  N'Phạm Văn E',    '0945123456', N'34 Đường JKL',   N'Phường 3',  N'Quận 4',  N'Đà Nẵng', 1),
    (7,  N'Bùi Hữu F',     '0965123456', N'56 Đường MNO',   N'Phường 6',  N'Quận 10', N'Cần Thơ', 1),
    (8,  N'Đỗ Thanh G',    '0956123456', N'78 Đường PQR',   N'Xã Hòa Bình', N'Huyện Nhà Bè', N'TP.HCM', 1),
    (9,  N'Ngô Đức H',     '0927123456', N'90 Đường STU',   N'Phường 8',  N'Quận 5',  N'Hà Nội',  1),
    (10, N'Vũ Hải I',      '0918123456', N'23 Đường VWX',   N'Phường 9',  N'Quận 6',  N'Đà Nẵng', 1),
    (11, N'Phan Gia K',    '0909123456', N'45 Đường YZ',    N'Phường 10', N'Quận 2',  N'Cần Thơ', 1);


update HoaDon
set idNhanVien = null where hinhThuc = 1

INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (35, 12, 3, 150.50, N'Son Đỏ Ruby');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (42, 25, 2, 200.00, N'Son Cam Đất');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (50, 37, 1, 320.75, N'Son Hồng Baby');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (39, 18, 4, 180.30, N'Son Đỏ Tươi');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (56, 5, 6, 220.40, N'Son Nude Nhẹ');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (37, 29, 2, 290.90, N'Son Hồng Đào');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (47, 42, 5, 250.20, N'Son Đỏ Đậm');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (53, 8, 3, 310.00, N'Son Đỏ Rượu');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (41, 31, 7, 170.50, N'Son Đỏ Cam');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (33, 11, 1, 400.80, N'Son Hồng Tím');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (44, 45, 9, 360.60, N'Son Cam Đậm');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (48, 23, 2, 290.20, N'Son Hồng Cánh Sen');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (57, 9, 3, 420.00, N'Son Mận Chín');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (55, 60, 4, 380.50, N'Son Đỏ Nâu');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (36, 14, 6, 280.75, N'Son Đỏ Đất');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (38, 49, 3, 310.60, N'Son Đỏ Gạch');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (40, 20, 8, 190.00, N'Son Cam Nude');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (45, 33, 2, 220.30, N'Son Nâu Đất');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (49, 55, 5, 270.90, N'Son Hồng San Hô');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (43, 4, 3, 390.40, N'Son Đỏ Rượu Vang');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (51, 21, 1, 450.60, N'Son Hồng Sen');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (46, 36, 7, 210.80, N'Son Cam Đỏ');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (34, 50, 2, 340.00, N'Son Đỏ Tím');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (52, 22, 3, 390.90, N'Son Nâu Socola');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (54, 41, 4, 330.40, N'Son Đỏ Gạch Sáng');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (56, 22, 6, 270.00, N'Son Cam Đào');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (41, 17, 5, 310.30, N'Son Đỏ Hồng');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (50, 30, 2, 220.90, N'Son Hồng Cam');
INSERT INTO dbo.HoaDonChiTiet (idHoaDon, idSanPhamChiTiet, soLuong, donGia, tenSanPham) VALUES (44, 6, 3, 290.40, N'Son Đỏ Mận');


declare @i int = 1;

while @i <= 50
    begin
        -- Chèn dữ liệu theo điều kiện
        insert into dbo.Voucher
        (
            ma,
            ten,
            moTa,
            dieuKien,
            loaiGiam,
            giaTriGiam,
            giaTriGiamToiDa,
            ngayBatDau,
            ngayKetThuc,
            trangThai
        )
        values
            (
                'V' + right('000' + cast(@i as varchar), 3),  -- Tạo mã voucher (V001, V002, ...)
                'Voucher ' + cast(@i as nvarchar),            -- Tên voucher (Voucher 1, Voucher 2, ...)
                'Mô tả voucher số ' + cast(@i as nvarchar),  -- Mô tả
                case
                    when @i % 2 = 0 then 500000             -- Với các bản ghi chẵn, dieuKien là 500,000
                    else 1000000                             -- Với các bản ghi lẻ, dieuKien là 1,000,000
                    end,
                case
                    when @i % 2 = 0 then 1  -- Với các bản ghi chẵn, loaiGiam là 'Giảm trực tiếp'
                    else 0          -- Với các bản ghi lẻ, loaiGiam là 'Giảm theo % giá trị mua'
                    end,
                case
                    when @i % 2 = 0 then null               -- Nếu là 'Giảm trực tiếp', giaTriGiam là null
                    else rand() * 50                        -- Nếu là 'Giảm theo % giá trị mua', giaTriGiam là tỷ lệ giảm ngẫu nhiên (0-50%)
                    end,
                100000,                                      -- Gia tri giam toi da mặc định là 1,000,000
                dateadd(day, -(@i % 30), getdate()),          -- Ngày bắt đầu ngẫu nhiên trong vòng 30 ngày qua
                dateadd(day, 30, getdate()),                  -- Ngày kết thúc là 30 ngày sau ngày bắt đầu
                case
                    when @i % 2 = 0 then 1                -- Với các bản ghi chẵn, trạng thái là 'Hoạt động' (1)
                    else 0                                  -- Với các bản ghi lẻ, trạng thái là 'Không hoạt động' (0)
                    end
            )

        set @i = @i + 1;
    end
go

update Voucher
set giaTriGiamToiDa = 100000 where id between 1 and 100

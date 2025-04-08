document.getElementById("chonDiaChiBtn").addEventListener("click", function () {
    const selected = document.querySelector('input[name="diaChi"]:checked');
    if (selected) {
        const ten = selected.getAttribute('data-ten');
        const sdt = selected.getAttribute('data-sdt');
        const diachi = selected.getAttribute('data-diachi');

        // Gán vào vùng địa chỉ mặc định
        document.getElementById("tenVaSdt").textContent = ten + " - " + sdt;
        document.getElementById("diaChiChiTiet").textContent = diachi;
    }
});
const savedRadio = document.getElementById('savedAddress');
const newRadio = document.getElementById('newAddress');
const defaultAddress = document.getElementById('defaultAddress');
const newAddressForm = document.getElementById('newAddressForm');


savedRadio.addEventListener('change', () => {
    defaultAddress.classList.remove('d-none');
    newAddressForm.classList.add('d-none');
});

newRadio.addEventListener('change', () => {
    defaultAddress.classList.add('d-none');
    newAddressForm.classList.remove('d-none');
});

document.getElementById("chonDiaChiBtn").addEventListener("click", function () {
    const selected = document.querySelector('input[name="diaChi"]:checked');
    if (selected) {
        const diaChiId = selected.value;
        const ten = selected.getAttribute('data-ten');
        const sdt = selected.getAttribute('data-sdt');
        const diachi = selected.getAttribute('data-diachi');

        // Gán vào vùng địa chỉ mặc định
        document.getElementById("tenVaSdt").textContent = ten + " - " + sdt;
        document.getElementById("diaChiChiTiet").textContent = diachi;

        // Gọi API cập nhật trạng thái
        fetch(`/dia-chi/${diaChiId}/chon`, {
            method: 'PATCH'
        })
            .then(response => {
                if (!response.ok) throw new Error("Không thể cập nhật trạng thái địa chỉ");
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Thành công',
                        text: 'Đã chọn địa chỉ giao hàng!'
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Thất bại',
                        text: data.message || 'Không thể chọn địa chỉ!'
                    });
                }
            })
            .catch(err => {
                console.error("Lỗi khi cập nhật trạng thái địa chỉ:", err);
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: 'Có lỗi xảy ra khi chọn địa chỉ!'
                });
            });

        // Gọi lại tính tiền ship
        tinhTienShip();
    } else {
        Swal.fire({
            icon: 'warning',
            title: 'Chưa chọn địa chỉ',
            text: 'Vui lòng chọn một địa chỉ để tiếp tục.'
        });
    }
});


function tinhTienShip() {
    const diaChi1 = "10 ngo 99 Cau Dien, Bac Tu Liem, Ha Noi";

    const diaChiDayDu = document.getElementById("diaChiChiTiet").innerText;
    const diaChiParts = diaChiDayDu.split(",");

    // Lấy huyện và thành phố (2 phần cuối)
    const huyen = diaChiParts[diaChiParts.length - 2]?.trim();
    const thanhPho = diaChiParts[diaChiParts.length - 1]?.trim();
    const diaChi2 = `${huyen}, ${thanhPho}`;

    const url = `/tinh-khoang-cach?diaChi1=${encodeURIComponent(diaChi1)}&diaChi2=${encodeURIComponent(diaChi2)}`;

    fetch(url)
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                const khoangCach = data.khoangCachKm;
                let tienShip = 0;

                if (khoangCach <= 200) {
                    tienShip = Math.ceil(khoangCach / 20) * 5000;
                } else if (khoangCach <= 500) {
                    tienShip = Math.ceil(khoangCach / 20) * 3000;
                } else {
                    tienShip = Math.ceil(khoangCach / 30) * 2000;
                }
                document.getElementById("tienShip").innerText = ` ${tienShip.toLocaleString()}đ`;
                document.getElementById("tienShipInput").value = ` ${tienShip}`;
            } else {
                document.getElementById("tienShip").innerText = "Không thể tính khoảng cách";
            }
        })
        .catch(err => {
            document.getElementById("tienShip").innerText = "Không thể tính khoảng cách";
        });
}

document.addEventListener("DOMContentLoaded", tinhTienShip);

function tinhTongTien() {
    const shippingFee = document.getElementById("tienShipInput").value;
    const giamGiaSanPham = document.getElementById("discountProductInput").value;
    const giamGiaVanChuyen = document.getElementById("discountShipInput").value;
    const mucGiam = document.getElementById("mucGiam").value;
    const toiDa = document.getElementById("toiDa").value;



    function formatCurrency(value) {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(value);
    }

    function updateTotals() {
        const quantityInputs = document.querySelectorAll('.quantity-input');
        let subtotal = 0;
        let discountRank = 0;

        quantityInputs.forEach(input => {
            const price = document.getElementById("donGia").value;
            const quantity = parseInt(input.value) || 0;
            subtotal += price * quantity;
        });
        let total = 0;
        total = Number(subtotal) + Number(shippingFee);
        if (giamGiaVanChuyen !== null) total -= Number(giamGiaVanChuyen);
        if (giamGiaSanPham !== null) total -= Number(giamGiaSanPham);
        if (mucGiam && toiDa) {
            discountRank = Math.floor((subtotal * mucGiam) / 100);
            if (discountRank > toiDa) {
                discountRank = toiDa;
            }
            total -= discountRank;
        }

        document.getElementById('cartSubtotal').textContent = formatCurrency(subtotal);
        document.getElementById('cartSubtotalInput').value = subtotal;
        document.getElementById('discountRank').textContent = '- ' + formatCurrency(discountRank);
        document.getElementById('discountRankInput').value = discountRank;
        document.getElementById('orderTotal').textContent = formatCurrency(total);

    }

    // Khởi tạo tính toán ban đầu
    updateTotals();

    // Gắn sự kiện thay đổi số lượng
    document.querySelectorAll('.quantity-input').forEach(input => {
        input.addEventListener('input', updateTotals);
    });
}

document.addEventListener("DOMContentLoaded", function () {
    setTimeout(tinhTongTien, 3000); // 3000 milliseconds = 3 giây
});


document.addEventListener("DOMContentLoaded", function () {
    const modal = document.getElementById("voucherModal");

    modal.addEventListener("shown.bs.modal", function () {
        const subtotal = parseFloat(document.getElementById("cartSubtotalInput").value) || 0;

        // Lấy tất cả các dòng voucher
        const voucherItems = modal.querySelectorAll(".voucher-item");

        voucherItems.forEach(item => {
            // Lấy điều kiện tối thiểu từ data
            const condition = parseFloat(item.getAttribute("data-dieukien")) || 0;
            const radio = item.querySelector("input[type='radio']");

            if (subtotal >= condition) {
                item.style.opacity = "1";
                item.querySelector("input[type='radio']").disabled = false;
                radio.disabled = false;
            } else {
                item.style.opacity = "0.5";
                item.querySelector("input[type='radio']").disabled = true;
                radio.disabled = true;
                radio.checked = false;
            }
        });
    });
});

function chonVoucher() {
    // Lấy id voucher sản phẩm đã chọn
    const voucherProductId = document.querySelector('input[name="voucherProduct"]:checked')?.value;
    const voucherShipId = document.querySelector('input[name="voucherShip"]:checked')?.value;

    // Giả sử bạn có mảng chứa toàn bộ voucher bên dưới (render dưới dạng JSON trong script)
    const allProductVouchers = window.productVouchers || [];
    const allShipVouchers = window.shipVouchers || [];

    // Lấy dữ liệu đơn hàng hiện tại
    const subtotal = parseFloat(document.getElementById("cartSubtotalInput").value) || 0;
    const shippingFee = parseFloat(document.getElementById("tienShipInput").value) || 0;


    let productDiscount = 0;
    let shipDiscount = 0;

    // Xử lý voucher sản phẩm
    const selectedProductVoucher = allProductVouchers.find(v => v.id == voucherProductId);
    if (selectedProductVoucher) {
        document.getElementById("idVoucherSanPham").value = selectedProductVoucher.id;
        if (selectedProductVoucher.loaiGiam) {
            // Giảm trực tiếp
            productDiscount = selectedProductVoucher.giaTriGiamToiDa;
        } else {
            // Giảm theo %
            const phanTram = selectedProductVoucher.giaTriGiam;
            productDiscount = subtotal * (phanTram / 100);
            // Giới hạn tối đa
            if (productDiscount > selectedProductVoucher.giaTriGiamToiDa) {
                productDiscount = selectedProductVoucher.giaTriGiamToiDa;
            }
        }
    }

    // Xử lý voucher phí vận chuyển
    const selectedShipVoucher = allShipVouchers.find(v => v.id == voucherShipId);
    if (selectedShipVoucher) {
        document.getElementById("idVoucherVanChuyen").value = selectedShipVoucher.id;
        if (selectedShipVoucher.loaiGiam) {
            shipDiscount = selectedShipVoucher.giaTriGiamToiDa;
            if (shipDiscount > shippingFee) {
                shipDiscount = shippingFee;
            }
        } else {
            const phanTram = selectedShipVoucher.giaTriGiam;
            shipDiscount = shippingFee * (phanTram / 100);
            if (shipDiscount > selectedShipVoucher.giaTriGiamToiDa) {
                shipDiscount = selectedShipVoucher.giaTriGiamToiDa;
            }
        }
    }

    // Cập nhật giao diện
    document.getElementById("discountProduct").innerText = `- ${productDiscount.toLocaleString()}đ`;
    document.getElementById("discountShip").innerText = `- ${shipDiscount.toLocaleString()}đ`;
    document.getElementById("discountProductInput").value = productDiscount;
    document.getElementById("discountShipInput").value = shipDiscount;
    tinhTongTien()


    // Đóng modal
    const modalElement = document.getElementById('voucherModal');
    const modalInstance = bootstrap.Modal.getInstance(modalElement);
    if (modalInstance) {
        modalInstance.hide();
    }
}

document.getElementById("orderButton").addEventListener("click", function () {
    // Lấy danh sách sản phẩm (mỗi input quantity tương ứng 1 sp)
    const products = [];
    const productInputs = document.querySelectorAll("input#donGia");

    productInputs.forEach((input, index) => {
        const productId = input.getAttribute("data-id");
        const quantity = document.querySelectorAll(".quantity-input")[index].value;
        products.push({
            idSanPhamChiTiet: productId,
            soLuong: parseInt(quantity)
        });
    });

    // Lấy ID voucher sản phẩm và vận chuyển
    const voucherProductId = document.getElementById("idVoucherSanPham").value || "";
    const voucherShipId = document.getElementById("idVoucherVanChuyen").value || "";
    const idKhachHang = document.getElementById("idKhachHang").value;

    // Lấy tổng tiền và phí ship
    const orderTotal = document.getElementById("orderTotal").innerText.replace(/\D/g, ""); // bỏ kí tự tiền tệ
    const shippingFee = document.getElementById("tienShip").innerText.replace(/\D/g, "");

    // Chuyển hướng tới trang xác nhận đơn hàng
    fetch("/xac-nhan-don-hang", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            idKhachHang: idKhachHang,
            products: products,
            voucherProduct: voucherProductId,
            voucherShip: voucherShipId,
            total: orderTotal,
            shipFee: shippingFee
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                Swal.fire({
                    icon: 'success',
                    title: 'Thành công',
                    text: 'Đặt hàng thành công!',
                    showCancelButton: true,
                    confirmButtonText: 'Xem đơn hàng',
                    cancelButtonText: 'Đóng',
                }).then((result) => {
                    if (result.isConfirmed) {
                        // Chuyển hướng tới trang đơn hàng
                        window.location.href = `/don-hang/${idKhachHang}`;
                    }
                    else {
                        window.location.href = `/trang-chu`;
                    }
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Thất bại',
                    text: "Đặt hàng thất bại!",
                });
            }
        })
});





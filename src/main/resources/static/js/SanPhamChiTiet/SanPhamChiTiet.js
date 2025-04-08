// Script to handle thumbnail clicks and switching images
document.addEventListener('DOMContentLoaded', function () {
    const thumbnails = document.querySelectorAll('.product-thumbnail');
    const carousel = new bootstrap.Carousel(document.getElementById('productCarousel'), {
        interval: false
    });

    thumbnails.forEach((thumbnail, index) => {
        thumbnail.addEventListener('click', function () {
            carousel.to(index);
        });
    });

    // Quantity selector
    const minusBtn = document.querySelector('.fa-minus').parentElement;
    const plusBtn = document.querySelector('.fa-plus').parentElement;
    const quantityInput = document.querySelector('.quantity-selector input');

    minusBtn.addEventListener('click', function () {
        let value = parseInt(quantityInput.value);
        if (value > 1) {
            quantityInput.value = value - 1;
        }
    });

    plusBtn.addEventListener('click', function () {
        let value = parseInt(quantityInput.value);
        quantityInput.value = value + 1;
    });
});


document.addEventListener('DOMContentLoaded', function () {
    const thumbnails = document.querySelectorAll('.product-thumbnail');

    thumbnails.forEach(thumbnail => {
        thumbnail.addEventListener('click', function () {
            // Bỏ active ở tất cả thumbnail
            thumbnails.forEach(thumb => thumb.classList.remove('active'));
            // Thêm active cho thumbnail được chọn
            this.classList.add('active');

            // Nếu bạn có ảnh lớn, muốn đổi theo:
            const mainImage = document.querySelector('.main-image');
            if (mainImage) {
                mainImage.src = this.src;
            }
        });
    });
});

// Zoom ảnh
const carousel = document.getElementById('productCarousel');

carousel.addEventListener('mousemove', function (e) {
    const activeItem = carousel.querySelector('.carousel-item.active');
    const image = activeItem.querySelector('.main-image');
    const rect = activeItem.getBoundingClientRect();

    const x = e.clientX - rect.left;
    const y = e.clientY - rect.top;

    const xPercent = (x / rect.width) * 100;
    const yPercent = (y / rect.height) * 100;

    image.style.transformOrigin = `${xPercent}% ${yPercent}%`;
    image.style.transform = 'scale(2)';
});

carousel.addEventListener('mouseleave', function () {
    const activeItem = carousel.querySelector('.carousel-item.active');
    const image = activeItem.querySelector('.main-image');
    image.style.transform = 'scale(1)';
    image.style.transformOrigin = 'center center';
});

function showToast(message, type = 'success') {
    Swal.fire({
        toast: true,
        position: 'top-end',
        icon: type,
        title: message,
        showConfirmButton: false,
        timer: 2500,
        timerProgressBar: true,
        customClass: {
            popup: 'swal-toast-custom'
        }
    });
}

// ✅ Viết hàm mở dropdown giỏ hàng
function showCartDropdown() {
    let cartDropdown = document.getElementById("cartDropdown");
    if (cartDropdown) {
        cartDropdown.classList.add("show");
    }
}

function updateQuantity(gioHangChiTietId, delta) {
    let cartItem = document.querySelector(`#cart-item-${gioHangChiTietId}`);
    let quantityElement = cartItem.querySelector(".quantity");
    let maxQuantity = parseInt(cartItem.getAttribute("data-max"));
    let currentQuantity = parseInt(quantityElement.innerText);
    let newQuantity = currentQuantity + delta;

    if (newQuantity > maxQuantity) {
        showToast("Bạn đã đạt số lượng tối đa trong kho", "warning");
        return;
    }

    if (newQuantity <= 0) {
        // Xác nhận xóa nếu muốn (tùy bạn)
    }

    fetch('/cart/updateQuantity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: gioHangChiTietId,
            quantityChange: delta
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                calculateTotal();
                if (newQuantity > 0) {
                    quantityElement.innerText = newQuantity;
                    showToast("Cập nhật số lượng thành công");
                } else {
                    cartItem.remove();
                    showToast("Sản phẩm đã bị xóa khỏi giỏ hàng");
                }

                showCartDropdown();
            } else {
                showToast("Cập nhật thất bại", "error");
            }
        });
}


function removeFromCart(gioHangChiTietId) {
    fetch('/cart/remove/' + gioHangChiTietId, {
        method: 'DELETE'
    }).then(response => response.json())
        .then(data => {
            if (data.success) {
                calculateTotal();
                document.getElementById(`cart-item-${gioHangChiTietId}`).remove();
                showToast("Xóa sản phẩm thành công");

                let cartCountElement = document.getElementById("cart-count");
                cartCountElement.innerText = parseInt(cartCountElement.innerText) - 1;

                // ✅ Mở dropdown giỏ hàng sau khi xóa sản phẩm
                showCartDropdown();
            } else {
                showToast("Xóa thất bại", "error");
            }
        });
}

// ✅ Khi load trang, dropdown giỏ hàng luôn mở
// document.addEventListener("DOMContentLoaded", showCartDropdown);

// Lắng nghe sự kiện thay đổi màu sắc
document.getElementById("mauSac").addEventListener("change", function () {
    let selectedMauSacId = this.value; // Lấy id của màu sắc đã chọn
    let sanPhamId = document.getElementById("sanPham").value;

    // Nếu không chọn màu sắc thì không làm gì
    if (!selectedMauSacId) {
        return;
    }

    // Gửi yêu cầu AJAX để lấy danh sách KhoiLuong từ server
    fetch(`/cart/getKhoiLuongByMauSac?idMauSac=${selectedMauSacId}&idSanPham=${sanPhamId}`)
        .then(response => response.json()) // Parse dữ liệu JSON trả về từ backend
        .then(data => {
            let khoiLuongSelect = document.getElementById("khoiLuong");
            khoiLuongSelect.innerHTML = ""; // Xóa hết các tùy chọn hiện tại

            if (data.length === 0) {
                let option = document.createElement("option");
                option.value = "";
                option.textContent = "Không có khối lượng nào";
                khoiLuongSelect.appendChild(option);
            } else {
                // Thêm các khối lượng vào dropdown
                data.forEach((khoiLuong, index) => {
                    let option = document.createElement("option");
                    option.value = khoiLuong.id;
                    option.textContent = khoiLuong.ten + 'g';

                    // Chọn phần tử đầu tiên mặc định
                    if (index === 0) {
                        option.selected = true;
                    }

                    khoiLuongSelect.appendChild(option);
                });
                calculateTotal();
            }
        })
        .catch(error => console.error("Lỗi khi tải danh sách Khối lượng:", error));
});


function updateMauSac() {
    let gioHangChiTietId = document.querySelector('.cart-item-info #idChiTietGioHang').value;

    let newMauSacId = document.querySelector('.cart-item-info #mauSac').value;

    let newKhoiLuongId = document.querySelector('.cart-item-info #khoiLuong').value;

    // Gửi request tới server
    fetch('/cart/updateMauSac', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            id: gioHangChiTietId,
            mauSacId: newMauSacId,
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                calculateTotal();
                showToast("Cập nhật màu sắc và khối lượng thành công");

                // Cập nhật đơn giá mới trong giao diện
                let donGiaElement = document.getElementById("donGia");
                donGiaElement.textContent = data.donGiaMoi.toLocaleString() + " đ"; // Format tiền tệ
            } else {
                showToast("Cập nhật thất bại", "error");
            }
        })
        .catch(error => {
            showToast("Có lỗi xảy ra, vui lòng thử lại", "error");
            console.error("Lỗi:", error);
        });
}


function updateKhoiLuong() {
    let gioHangChiTietId = document.querySelector('.cart-item-info #idChiTietGioHang').value;

    let newMauSacId = document.querySelector('.cart-item-info #mauSac').value;

    let newKhoiLuongId = document.querySelector('.cart-item-info #khoiLuong').value;

    // Gửi request tới server
    fetch('/cart/updateKhoiLuong', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            id: gioHangChiTietId,
            mauSacId: newMauSacId,
            khoiLuongId: newKhoiLuongId
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                calculateTotal();
                showToast("Cập nhật màu sắc và khối lượng thành công");

                // Cập nhật đơn giá mới trong giao diện
                let donGiaElement = document.getElementById("donGia");
                donGiaElement.textContent = data.donGiaMoi.toLocaleString() + " đ"; // Format tiền tệ
            } else {
                showToast("Cập nhật thất bại", "error");
            }
        })
        .catch(error => {
            showToast("Có lỗi xảy ra, vui lòng thử lại", "error");
            console.error("Lỗi:", error);
        });
}

function calculateTotal() {
    // Lấy ID giỏ hàng từ thuộc tính 'data-id' của ul hoặc lấy trực tiếp từ input
    var idGioHang = document.getElementById("idGioHang").value; // Đảm bảo input này có id="idGioHang"

    // Gửi ID giỏ hàng đến server để tính tổng tiền
    fetch('/cart/calculateTotal', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(idGioHang) // Gửi ID giỏ hàng trực tiếp
    })
        .then(response => response.json())
        .then(data => {
            // Cập nhật tổng tiền vào giao diện
            var tongTien = document.getElementById("tongTien");
            if (data.tongTien != null) {
                console.log(data.tongTien)
                tongTien.textContent = data.tongTien.toLocaleString() + ' đ'; // Hiển thị tổng tiền với định dạng tiền tệ
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Gọi hàm tính tổng khi trang load và khi thay đổi số lượng
window.addEventListener('load', function() {
    setTimeout(calculateTotal, 100); // Gọi hàm tính tổng sau một khoảng thời gian nhỏ để đảm bảo DOM đã load xong
});

document.getElementById("addToCartButton").onclick = function (event) {
    const checkLogin = document.getElementById("checkLoginValue")?.value === 'true';
    if (!checkLogin) {
        event.preventDefault();  // Ngừng hành động mặc định của nút
        Swal.fire({
            title: 'Bạn chưa đăng nhập!',
            text: 'Hãy đăng nhập để tiếp tục hoặc quay lại.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Đăng nhập',
            cancelButtonText: 'Quay lại',
        }).then((result) => {
            if (result.isConfirmed) {
                let idSanPham = document.getElementById("sanPhamId").value;
                let idMauSac = document.querySelector(".color-option.active")?.getAttribute("data-name") || 'default';
                let idKhoiLuong = document.getElementById("khoiLuongId")?.value || 'default';
                let soLuong = document.querySelector(".quantity-selector input").value || '1';
                // Chuyển hướng đến trang đăng nhập và lưu trạng thái sản phẩm đang chọn
                let redirectUrl = `/login?redirect=/chi-tiet-san-pham?idSanPham=${idSanPham}&idMauSac=${idMauSac}&idKhoiLuong=${idKhoiLuong}&soLuong=${soLuong}`;
                window.location.href = redirectUrl;
            } else {
                // Chỉ tắt thông báo khi chọn Quay lại
                Swal.close(); // Đóng SweetAlert mà không làm gì thêm
            }
        });
    } else {
        let idSanPham = document.getElementById("sanPhamId").value;
        let idKhachHang = document.getElementById("khachHangId").value;
        let idMauSac = document.querySelector(".color-option.active")?.getAttribute("data-name") || 'default';
        let idKhoiLuong = document.getElementById("khoiLuongId")?.value || 'default';
        let soLuong = document.querySelector(".quantity-selector input").value || '1';

        fetch('/cart/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                idSanPham: idSanPham,
                idMauSac: idMauSac,
                idKhoiLuong: idKhoiLuong,
                idKhachHang: idKhachHang,
                soLuong: soLuong
            })
        }).then(response => response.json())
            .then(data => {
                if (data.success) {
                    Swal.fire({
                        title: 'Thêm vào giỏ hàng thành công!',
                        text: 'Sản phẩm đã được thêm vào giỏ hàng của bạn.',
                        icon: 'success',
                        confirmButtonText: 'OK'
                    }).then(() => {
                        // Cập nhật số lượng sản phẩm trong giỏ hàng
                        location.reload();
                    });
                } else {
                    Swal.fire({
                        title: 'Lỗi!',
                        text: "Thêm vào giỏ hàng thất bại.",
                        icon: 'error',
                        confirmButtonText: 'OK'
                    });
                }
            });
    }
};

document.getElementById("muaNgayButton").onclick = function (event) {
    const checkLogin = document.getElementById("checkLoginValue")?.value === 'true';
    if (!checkLogin) {
        event.preventDefault();  // Ngừng hành động mặc định của nút
        Swal.fire({
            title: 'Bạn chưa đăng nhập!',
            text: 'Hãy đăng nhập để tiếp tục hoặc quay lại.',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Đăng nhập',
            cancelButtonText: 'Quay lại',
        }).then((result) => {
            if (result.isConfirmed) {
                let idSanPham = document.getElementById("sanPhamId").value;
                let idMauSac = document.querySelector(".color-option.active")?.getAttribute("data-name") || 'default';
                let idKhoiLuong = document.getElementById("khoiLuongId")?.value || 'default';
                let soLuong = document.querySelector(".quantity-selector input").value || '1';
                // Chuyển hướng đến trang đăng nhập và lưu trạng thái sản phẩm đang chọn
                let redirectUrl = `/login?redirect=/chi-tiet-san-pham?idSanPham=${idSanPham}&idMauSac=${idMauSac}&idKhoiLuong=${idKhoiLuong}&soLuong=${soLuong}`;
                window.location.href = redirectUrl;
            } else {
                // Chỉ tắt thông báo khi chọn Quay lại
                Swal.close(); // Đóng SweetAlert mà không làm gì thêm
            }
        });
    } else {
        let idSanPham = document.getElementById("sanPhamId").value;
        let idKhachHang = document.getElementById("khachHangId").value;
        let idMauSac = document.querySelector(".color-option.active")?.getAttribute("data-name") || 'default';
        let idKhoiLuong = document.getElementById("khoiLuongId")?.value || 'default';
        let soLuong = document.querySelector(".quantity-selector input").value || '1';
        let redirectUrl = `/san-pham-chi-tiet/thanh-toan?idSanPham=${idSanPham}&idMauSac=${idMauSac}&idKhoiLuong=${idKhoiLuong}&soLuong=${soLuong}&idKhachHang=${idKhachHang}`;
        window.location.href = redirectUrl;
    }
};






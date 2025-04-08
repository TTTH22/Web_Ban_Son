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




let lastScrollTop = 0;
const navbar = document.querySelector('.navbar');

window.addEventListener('scroll', function () {
    let scrollTop = window.scrollY || document.documentElement.scrollTop;

    if (scrollTop > lastScrollTop) {
        // Cuộn xuống -> Ẩn navbar
        navbar.classList.add('hide');
    } else {
        // Cuộn lên -> Hiện navbar
        navbar.classList.remove('hide');

        // Nếu không phải ở top
        if (scrollTop > 0) {
            navbar.classList.add('scrolled'); // Có nền trắng
        } else {
            navbar.classList.remove('scrolled'); // Về top -> Trong suốt
        }
    }

    lastScrollTop = scrollTop <= 0 ? 0 : scrollTop; // Fix cho Safari
});

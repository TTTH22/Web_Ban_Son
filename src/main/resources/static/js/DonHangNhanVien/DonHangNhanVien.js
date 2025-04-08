function XanNhanDonHang(idDonHang) {
    Swal.fire({
        title: 'Xác nhận đơn hàng?',
        text: "Bạn có chắc chắn muốn xác nhận đơn hàng này không?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Hủy',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            fetch('/nhan-vien/don-hang/xac-nhan/' + idDonHang, {
                method: 'PUT'
            })
                .then(response => {
                    if (response.ok) {
                        Swal.fire({
                            title: 'Thành công!',
                            text: 'Đơn hàng đã được xác nhận.',
                            icon: 'success',
                            timer: 2000,
                            showConfirmButton: false
                        }).then(() => {
                            // Reload lại trang hoặc cập nhật giao diện
                            location.reload();
                        });
                    } else {
                        Swal.fire('Lỗi!', 'Không thể xác nhận đơn hàng.', 'error');
                    }
                })
                .catch(error => {
                    console.error('Lỗi khi gọi API:', error);
                    Swal.fire('Lỗi!', 'Đã xảy ra lỗi khi xác nhận.', 'error');
                });
        }
    });
}

function confirmHoanHang(hoaDonId) {
    Swal.fire({
        title: 'Xác nhận hoàn hàng?',
        text: 'Bạn có chắc chắn muốn xác nhận hoàn hàng cho đơn này?',
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Xác nhận',
        cancelButtonText: 'Hủy',
        reverseButtons: true
    }).then((result) => {
        if (result.isConfirmed) {
            // Gọi API hoặc submit form
            fetch(`/nhan-vien/don-hang/hoan-hang/${hoaDonId}`, {
                method: 'POST'
            }).then(response => {
                if (response.ok) {
                    Swal.fire('Thành công!', 'Đã xác nhận hoàn hàng.', 'success')
                        .then(() => {
                            location.reload(); // reload lại trang
                        });
                } else {
                    Swal.fire('Lỗi!', 'Không thể cập nhật trạng thái.', 'error');
                }
            });
        }
    });
}
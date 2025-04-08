function showDaNhanDonModal(idDonHang) {
    Swal.fire({
        title: 'Xác nhận đã nhận hàng?',
        text: "Bạn có chắc đã nhận được đơn hàng này?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đã nhận hàng',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            // Gọi API cập nhật trạng thái
            fetch('/don-hang/da-nhan/' + idDonHang, {
                method: 'POST'
            }).then(res => {
                if (res.ok) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Cảm ơn bạn!',
                        text: 'Đơn hàng đã được xác nhận là đã nhận.',
                        confirmButtonText: 'OK'
                    }).then(() => {
                        location.reload(); // hoặc cập nhật lại UI
                    });
                } else {
                    Swal.fire('Lỗi!', 'Không thể cập nhật trạng thái đơn hàng.', 'error');
                }
            });
        }
    });
}
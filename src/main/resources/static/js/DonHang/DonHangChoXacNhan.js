function showHuyDonModal(id) {
    document.getElementById('huyDonId').value = id;
    let modal = new bootstrap.Modal(document.getElementById('huyDonModal'));
    modal.show();
}

function xacNhanHuy() {
    const id = document.getElementById('huyDonId').value;
    const lyDo = document.getElementById('lyDoHuy').value;
    console.log(id)
    console.log(lyDo)
    huyDonHang(id, lyDo);
    bootstrap.Modal.getInstance(document.getElementById('huyDonModal')).hide();
}

function huyDonHang(id, lyDo) {
    fetch('/don-hang/huy-don/' + id + '?lyDo=' + encodeURIComponent(lyDo), {
        method: 'POST'
    }).then(res => {
        if (res.ok) {
            Swal.fire({
                icon: 'success',
                title: 'Thành công',
                text: 'Hủy đơn hàng thành công!',
                confirmButtonText: 'OK!'
            }).then((result) => {
                if (result.isConfirmed) {
                    location.reload(); // Hoặc chuyển hướng nếu cần
                }
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Không thể hủy đơn hàng.'
            });
        }
    }).catch(error => {
        console.error('Lỗi:', error);
        Swal.fire({
            icon: 'error',
            title: 'Lỗi hệ thống',
            text: 'Vui lòng thử lại sau.'
        });
    });
}

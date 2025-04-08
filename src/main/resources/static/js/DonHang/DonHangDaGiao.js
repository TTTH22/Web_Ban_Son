function moDanhGia(idDonHang) {
    Swal.fire({
        title: 'Đánh giá đơn hàng',
        html: `
            <div style="font-size: 1.5rem;" id="star-rating">
                ${[1, 2, 3, 4, 5].map(i => `
                    <i class="bi bi-star" id="star-${i}" onclick="chonSao(${i})" style="cursor:pointer;color:gray;"></i>
                `).join('')}
            </div>
            <textarea id="danhGiaText" class="swal2-textarea" placeholder="Nhận xét của bạn..."></textarea>
        `,
        showCancelButton: true,
        confirmButtonText: 'Gửi',
        preConfirm: () => {
            const soSao = window.soSaoChon || 0;
            const noiDung = document.getElementById('danhGiaText').value.trim();
            if (soSao === 0 || noiDung === '') {
                Swal.showValidationMessage("Vui lòng chọn sao và nhập nhận xét");
                return false;
            }
            return {soSao, noiDung};
        }
    }).then((result) => {
        if (result.isConfirmed) {
            const {soSao, noiDung} = result.value;
            // Gửi đánh giá về server
            fetch('/don-hang/danh-gia', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    idDonHang: idDonHang,
                    soSao: soSao,
                    noiDung: noiDung
                })
            }).then(res => {
                if (res.ok) {
                    Swal.fire('Cảm ơn bạn!', 'Đánh giá của bạn đã được ghi nhận.', 'success')
                        .then(() => location.reload());
                } else {
                    Swal.fire('Lỗi', 'Không thể gửi đánh giá', 'error');
                }
            });
        }
    });
}

function chonSao(sao) {
    window.soSaoChon = sao;
    for (let i = 1; i <= 5; i++) {
        const star = document.getElementById('star-' + i);
        if (i <= sao) {
            star.classList.remove('bi-star');
            star.classList.add('bi-star-fill');
            star.style.color = 'orange';
        } else {
            star.classList.remove('bi-star-fill');
            star.classList.add('bi-star');
            star.style.color = 'gray';
        }
    }
}

function muaLai(idHoaDon) {
    window.location.href = `/don-hang/mua-lai?idHoaDon=${idHoaDon}`;
}

function hoanHang(id, ngayNhan) {
    const ngayNhanDate = new Date(ngayNhan);
    const ngayHienTai = new Date();
    const msMotNgay = 24 * 60 * 60 * 1000;
    const soNgayDaQua = Math.floor((ngayHienTai - ngayNhanDate) / msMotNgay);

    if (soNgayDaQua > 7) {
        Swal.fire({
            icon: 'warning',
            title: 'Không thể hoàn hàng',
            text: 'Bạn đã nhận hàng quá 7 ngày nên không thể yêu cầu hoàn hàng.',
        });
    } else {
        document.getElementById("hoanHang_hoaDonId").value = id;
        document.getElementById("modalHoanHang").style.display = "block";
    }
}


function dongModal() {
    document.getElementById("modalHoanHang").style.display = "none";
}

function guiYeuCauHoanHang() {
    const hoaDonId = document.getElementById("hoanHang_hoaDonId").value;
    const lyDo = document.getElementById("hoanHang_lyDo").value;
    const moTa = document.getElementById("hoanHang_moTa").value;

    fetch('/don-hang/hoan-hang', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            hoaDonId: hoaDonId,
            lyDo: lyDo,
            moTa: moTa
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Gửi yêu cầu thất bại');
            }
            return response.text();
        })
        .then(data => {
            dongModal();
            Swal.fire({
                icon: 'success',
                title: 'Đã gửi yêu cầu',
                text: 'Yêu cầu hoàn hàng của bạn đã được ghi nhận.',
            }).then(() => {
                location.reload();
            });
        })
        .catch(error => {
            dongModal();
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: error.message
            });
        });
}



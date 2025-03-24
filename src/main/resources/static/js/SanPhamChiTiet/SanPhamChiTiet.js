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


// Chọn màu sắc
// const colorOptions = document.querySelectorAll('.color-option');
// const selectedInfo = document.getElementById('selected-color-info');
// const selectedCircle = document.getElementById('selected-color-circle');
// const selectedName = document.getElementById('selected-color-name');
// const selectedCode = document.getElementById('selected-color-code');
//
// colorOptions.forEach(option => {
//     option.addEventListener('click', () => {
//         colorOptions.forEach(opt => opt.classList.remove('active'));
//         option.classList.add('active');
//
//         const colorName = option.getAttribute('data-name');
//         const colorCode = option.getAttribute('data-code');
//         const bgColor = option.style.backgroundColor;
//
//         selectedInfo.style.display = 'flex';
//         selectedCircle.style.backgroundColor = bgColor;
//         selectedName.textContent = colorName;
//         selectedCode.textContent = `Mã màu: ${colorCode}`;
//     });
// });




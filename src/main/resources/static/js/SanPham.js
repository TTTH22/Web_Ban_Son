


// ------------Header.js----------------
let lastScrollTop = 0;
const navbar = document.querySelector('.navbar');

window.addEventListener('scroll', function() {
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



// ------------RangePrice.js----------------
var priceSlider = document.getElementById('price-slider');

// Lấy giá trị từ data attribute
var minPrice = parseInt(priceSlider.getAttribute('data-min-price'));
var maxPrice = parseInt(priceSlider.getAttribute('data-max-price'));
var startMin = parseInt(priceSlider.getAttribute('data-start-min'));
var startMax = parseInt(priceSlider.getAttribute('data-start-max'));

noUiSlider.create(priceSlider, {
    start: [startMin, startMax],
    connect: true,
    step: 1000,
    range: {
        'min': minPrice,
        'max': maxPrice
    },
    format: {
        to: function (value) {
            return Math.round(value) + '₫';
        },
        from: function (value) {
            return Number(value.replace('₫', ''));
        }
    }
});

var priceValue = document.getElementById('price-value');

priceSlider.noUiSlider.on('update', function (values) {
    priceValue.innerHTML = values.join(' - ');
});


// -----------------ThanhHeaderCuon.js----------------
document.addEventListener("DOMContentLoaded", function() {
    var swiper = new Swiper(".mySwiper", {
        slidesPerView: 4,
        spaceBetween: 30,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        grabCursor: true,
        loop: false,
    });
});


// -----------------LọcPrice.js----------------
var priceValue = document.getElementById('price-value');
var links = document.querySelectorAll('.dongSPLink');
var linkFillter = document.querySelectorAll('.filter-search');


function updateLinks() {
    var priceText = priceValue.innerHTML;
    var prices = priceText.split(' - ');
    var minPrice = parseInt(prices[0].replace('₫', '').trim());
    var maxPrice = parseInt(prices[1].replace('₫', '').trim());

    var colors = [];
    document.querySelectorAll('.filter-color:checked').forEach(cb => {
        colors.push(cb.value);
    });

    // Lấy NSX
    var nsxList = [];
    document.querySelectorAll('.filter-nsx:checked').forEach(cb => {
        nsxList.push(cb.value);
    });

    // Lấy category
    var category = document.querySelector('.filter-category').value;

    var tenSearch = document.getElementById('tenSearch').value;


    links.forEach(link => {
        let url = new URL(link.href);

        // Giữ các tham số khác
        url.searchParams.set('minPrice', minPrice);
        url.searchParams.set('maxPrice', maxPrice);

        url.searchParams.delete('idMauSac');
        colors.forEach(color => {
            url.searchParams.append('idMauSac', color);
        });

        url.searchParams.delete('idNsx');
        nsxList.forEach(nsx => {
            url.searchParams.append('idNsx', nsx);
        });

        url.searchParams.set('category', category); // Thêm category

        url.searchParams.set('tenSearch', tenSearch); // Thêm tenSearch


        // Gán lại
        link.href = url.toString();
    });

    linkFillter.forEach(link => {
        let url = new URL(link.href);

        // Giữ các tham số khác
        url.searchParams.set('minPrice', minPrice);
        url.searchParams.set('maxPrice', maxPrice);

        url.searchParams.delete('idMauSac');
        colors.forEach(color => {
            url.searchParams.append('idMauSac', color);
        });

        url.searchParams.delete('idNsx');
        nsxList.forEach(nsx => {
            url.searchParams.append('idNsx', nsx);
        });

        url.searchParams.set('category', category); // Thêm category


        url.searchParams.delete('tenSearch');
        if (tenSearch !== null)
        url.searchParams.set('tenSearch', tenSearch); // Thêm tenSearch



        // Gán lại
        link.href = url.toString();
    });
}

// Update ngay khi slider khởi tạo xong
priceSlider.noUiSlider.on('update', function () {
    updateLinks();
});

// Gọi khi thay đổi màu hoặc NSX
document.querySelectorAll('.filter-color, .filter-nsx').forEach(cb => {
    cb.addEventListener('change', updateLinks);
});

// Khi thay đổi xong (nếu muốn xử lý thêm)
priceSlider.noUiSlider.on('change', function () {
    updateLinks();
});

// Khi select thay đổi
document.querySelector('.filter-category').addEventListener('change', updateLinks);

// Khi tên search thay đổi
document.getElementById('tenSearch').addEventListener('input', function (e) {
    updateLinks(); // Cập nhật lại tất cả link ngay khi người dùng nhập
});



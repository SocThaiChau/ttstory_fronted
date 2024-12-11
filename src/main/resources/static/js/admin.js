document.addEventListener("DOMContentLoaded", () => {
    // Chuyển hướng đến trang statistics.html
    const statisticsNav = document.querySelector('.navbar-general:nth-child(3)'); // Giả định "Thống kê" là mục thứ 3
    if (statisticsNav) {
        statisticsNav.addEventListener('click', () => {
            window.location.href = '/assets/css/statistics.html';
        });
    }

    // Chuyển hướng đến trang admin_user.html
    const userNav = document.querySelector('.navbar-general:nth-child(2)'); // Giả định "Người dùng" là mục thứ 2
    if (userNav) {
        userNav.addEventListener('click', () => {
            window.location.href = '/assets/css/admin_user.html';
        });
    }

    // Xử lý nút Edit trên bảng người dùng
    const userTable = document.getElementById('user-list'); // Bảng người dùng
    if (userTable) {
        userTable.addEventListener('click', (event) => {
            const target = event.target;
            if (target && target.classList.contains('btn-edit')) { // Kiểm tra nếu bấm vào nút Edit
                window.location.href = '/form_edit_user.html'; // Chuyển hướng đến trang chỉnh sửa người dùng
            }
        });
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const userLink = document.querySelector('a[href="#user"]');
    const adminUserContent = document.getElementById("admin-user-content");
    const mainContent = document.querySelector(".container-right-main");

    // Xử lý hiển thị nội dung "Người dùng"
    userLink.addEventListener("click", (e) => {
        e.preventDefault(); // Ngăn thay đổi URL
        mainContent.classList.add("hidden"); // Ẩn nội dung chính
        adminUserContent.classList.remove("hidden"); // Hiển thị giao diện admin_user
        renderUserTable(); // Gọi hàm hiển thị danh sách người dùng
    });

    // Hàm hiển thị danh sách người dùng
    function renderUserTable() {
        const userList = document.getElementById("user-list");
        userList.innerHTML = ""; // Xóa danh sách cũ

        users.forEach((user, index) => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.registerDate}</td>
                <td>${user.status === "active" ? "Hoạt động" : "Bị chặn"}</td>
            `;
            userList.appendChild(row);
        });
    }
});

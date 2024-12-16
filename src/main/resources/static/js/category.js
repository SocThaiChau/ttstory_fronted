// category.js
const categories = [
    { id: 1, name: "Thời trang", description: "Quần áo và phụ kiện" },
    { id: 2, name: "Điện tử", description: "Thiết bị và phụ kiện điện tử" },
    { id: 3, name: "Gia dụng", description: "Sản phẩm cho gia đình" },
];
document.addEventListener("DOMContentLoaded", () => {
    const tableBody = document.getElementById("category-list");

    if (categories && categories.length > 0) {
        categories.forEach((category) => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${category.id}</td>
                <td>${category.name}</td>
                <td>${category.description}</td>
                <td>
                    <button class="action-btn view-btn" onclick="viewCategory(${category.id})">Xem chi tiết</button>
                    <button class="action-btn edit-btn" onclick="editCategory(${category.id})">Chỉnh sửa</button>
                </td>
            `;

            tableBody.appendChild(row);
        });
    } else {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td colspan="4" style="text-align: center;">Không có dữ liệu</td>
        `;
        tableBody.appendChild(row);
    }
});

// Hàm xử lý khi nhấn nút "Xem chi tiết"
function viewCategory(id) {
    alert(`Xem chi tiết danh mục với ID: ${id}`);
    // Thêm logic mở modal hoặc điều hướng trang chi tiết
}

// Hàm xử lý khi nhấn nút "Chỉnh sửa"
function editCategory(id) {
    alert(`Chỉnh sửa danh mục với ID: ${id}`);
    // Thêm logic điều hướng tới trang chỉnh sửa hoặc mở modal chỉnh sửa
}

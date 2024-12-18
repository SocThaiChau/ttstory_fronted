// Giả lập dữ liệu đơn hàng từ backend
const orders = [
{
        id: 1,
        storeId: 101,
        storeName: "Cửa hàng A",
        status: "Đang xử lý",
        total: 150000,
        createdDate: "2024-12-01 10:30:00",
        lastModifiedDate: "2024-12-10 12:00:00",
        address: "123 Đường A, Quận B, TP. C",
        carrier: null  // Chưa chọn đơn vị vận chuyển
    },
    {
        id: 2,
        storeId: 102,
        storeName: "Cửa hàng B",
        status: "Hoàn thành",
        total: 200000,
        createdDate: "2024-12-02 11:00:00",
        lastModifiedDate: "2024-12-10 14:00:00",
        address: "456 Đường X, Quận Y, TP. Z",
        carrier: "Giao hàng nhanh"  // Đã chọn đơn vị vận chuyển
    },
    {
        id: 3,
        storeId: 103,
        storeName: "Cửa hàng C",
        status: "Đang xử lý",
        total: 350000,
        createdDate: "2024-12-05 09:15:00",
        lastModifiedDate: "2024-12-10 16:45:00",
        address: "789 Đường M, Quận N, TP. O",
        carrier: null  // Chưa chọn đơn vị vận chuyển
    },
    {
        id: 4,
        storeId: 104,
        storeName: "Cửa hàng D",
        status: "Hoàn thành",
        total: 120000,
        createdDate: "2024-12-06 14:30:00",
        lastModifiedDate: "2024-12-10 15:20:00",
        address: "101 Đường P, Quận Q, TP. R",
        carrier: "SPX Express"  // Đã chọn đơn vị vận chuyển
    },
    {
        id: 5,
        storeId: 105,
        storeName: "Cửa hàng E",
        status: "Đang xử lý",
        total: 450000,
        createdDate: "2024-12-07 08:00:00",
        lastModifiedDate: "2024-12-10 11:30:00",
        address: "123 Đường T, Quận U, TP. V",
        carrier: null  // Chưa chọn đơn vị vận chuyển
    }
];
let currentPage = 1;
let ordersPerPage = 10;
let filteredOrders = [...orders]; // Initial filtered orders
// Danh sách đơn vị vận chuyển
const carriers = ["Giao hàng nhanh", "Ninjavan", "SPX Express", "Ahamove", "VietellPost"];

// Render danh sách đơn hàng
// Render danh sách đơn hàng
// Render danh sách đơn hàng
// Render danh sách đơn hàng
function renderOrders() {
    const orderList = document.getElementById("order-list");
    orderList.innerHTML = "";  // Xóa danh sách cũ

    // Paginate orders for current page
    const startIndex = (currentPage - 1) * ordersPerPage;
    const paginatedOrders = filteredOrders.slice(startIndex, startIndex + ordersPerPage);

    paginatedOrders.forEach(order => {
        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${order.id}</td>
            <td>${order.storeId}</td>
            <td>${order.status}</td>
            <td>${order.total.toLocaleString()} VND</td>
            <td>${order.createdDate}</td>
            <td>${order.lastModifiedDate}</td>
            <td>${order.address}</td>
            <td>
                ${order.status === "Đang xử lý" ?
                    (order.carrier ?
                        `<button class="action-btn" disabled>${order.carrier}</button>` :
                        `<button class="action-btn" onclick="openCarrierModal(${order.id})">Chọn vận chuyển</button>`) :
                (order.status === "Đang giao" ?
                    `<button class="action-btn" onclick="changeStatus(${order.id}, 'Hoàn thành')">Hoàn thành</button>
                     <button class="action-btn" onclick="changeStatus(${order.id}, 'Hủy')">Hủy</button>` :
                (order.status === "Hoàn thành" || order.status === "Hủy" ?
                    (order.carrier ?
                        `<button class="action-btn" disabled>${order.carrier}</button>` : '') : ''))
                }
            </td>
        `;
        orderList.appendChild(row);
    });
}

// Hàm thay đổi trạng thái đơn hàng
function changeStatus(orderId, newStatus) {
    const order = orders.find(order => order.id === orderId);

    // Thay đổi trạng thái đơn hàng
    order.status = newStatus;

    // Hiển thị thông báo
    alert(`Đã thay đổi trạng thái đơn hàng ID: ${orderId} thành "${newStatus}"`);

    // Cập nhật lại giao diện
    renderOrders();
}




/// Hàm phân trang và hiển thị đơn hàng
 function paginateOrders() {
     const orderList = document.getElementById("order-list");
     const startIndex = (currentPage - 1) * ordersPerPage;
     const endIndex = currentPage * ordersPerPage;
     const paginatedOrders = filteredOrders.slice(startIndex, endIndex);

     orderList.innerHTML = "";  // Xóa danh sách cũ
     paginatedOrders.forEach(order => {
         const row = document.createElement("tr");
         row.innerHTML = `
             <td>${order.id}</td>
             <td>${order.storeId}</td>
             <td>${order.status}</td>
             <td>${order.total.toLocaleString()} VND</td>
             <td>${order.createdDate}</td>
             <td>${order.lastModifiedDate}</td>
             <td>${order.address}</td>
             <td>
                 ${order.status === "Đang xử lý" ?
                     (order.carrier ?
                         `<button class="action-btn" disabled>${order.carrier}</button>` :
                         `<button class="action-btn" onclick="openCarrierModal(${order.id})">Chọn vận chuyển</button>`) :
                     (order.status === "Đang giao" && order.carrier ?
                         `<button class="action-btn" disabled>${order.carrier}</button>` : '')}
             </td>
         `;
         orderList.appendChild(row);
     });

     updatePageInfo();  // Cập nhật thông tin phân trang
 }


// Hàm lọc đơn hàng
// Hàm lọc đơn hàng
function filterOrders() {
    const orderStatus = document.getElementById("order-status").value;  // Lấy giá trị trạng thái đơn hàng
    const sortDate = document.getElementById("sort-date").value;

    filteredOrders = [...orders];  // Reset lại danh sách đơn hàng đã lọc

    // Lọc theo trạng thái đơn hàng
    if (orderStatus !== "all") {
        filteredOrders = filteredOrders.filter(order => order.status === orderStatus);
    }

    // Sắp xếp theo ngày tạo
    if (sortDate === "asc") {
        filteredOrders.sort((a, b) => new Date(a.createdDate) - new Date(b.createdDate));
    } else if (sortDate === "desc") {
        filteredOrders.sort((a, b) => new Date(b.createdDate) - new Date(a.createdDate));
    }

    // Quay lại trang 1 sau khi lọc
    currentPage = 1;
    paginateOrders();
}




// Gọi hàm phân trang lần đầu
paginateOrders();

// Hàm mở modal để chọn đơn vị vận chuyển
function openCarrierModal(orderId) {
    const modal = document.getElementById("carrier-modal");
    const carrierList = document.getElementById("carrier-list");

    // Xóa các nút cũ nếu có
    carrierList.innerHTML = "";

    // Thêm nút cho từng đơn vị vận chuyển
    carriers.forEach(carrier => {
        const button = document.createElement("button");
        button.innerText = carrier;
        button.className = "carrier-btn";
        button.onclick = () => selectCarrier(orderId, carrier);
        carrierList.appendChild(button);
    });

    // Hiển thị modal
    modal.style.display = "block";
}

// Xử lý khi chọn đơn vị vận chuyển
// Xử lý khi chọn đơn vị vận chuyển
function selectCarrier(orderId, carrier) {
    const order = orders.find(order => order.id === orderId);

    // Cập nhật đơn vị vận chuyển
    order.carrier = carrier;

    // Cập nhật trạng thái đơn hàng thành "Đang giao"
    order.status = "Đang giao";

    // Hiển thị thông báo
    alert(`Đã chọn đơn vị vận chuyển "${carrier}" cho đơn hàng ID: ${orderId}`);

    // Cập nhật giao diện sau khi thay đổi trạng thái
    renderOrders();

    // Đóng modal
    closeModal();
}


// Đóng modal
function closeModal() {
    const modal = document.getElementById("carrier-modal");
    modal.style.display = "none";
}

// Phân trang trang tiếp theo
function nextPage() {
    if (currentPage * ordersPerPage < filteredOrders.length) {
        currentPage++;
        paginateOrders();
    }
}

// Phân trang trang trước đó
function prevPage() {
    if (currentPage > 1) {
        currentPage--;
        paginateOrders();
    }
}
// Hàm cập nhật thông tin phân trang
function updatePageInfo() {
    const totalPages = Math.ceil(filteredOrders.length / ordersPerPage);
    document.getElementById("current-page").textContent = currentPage;
    document.getElementById("total-pages").textContent = totalPages;
}


// Lắng nghe sự kiện thay đổi giá trị từ các filter
document.getElementById("order-status").addEventListener("change", filterOrders);
document.getElementById("carrier-status").addEventListener("change", filterOrders);
document.getElementById("sort-date").addEventListener("change", filterOrders);

document.addEventListener("DOMContentLoaded", () => {
    renderOrders();
});

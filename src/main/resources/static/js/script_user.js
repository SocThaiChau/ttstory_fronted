// Dữ liệu mẫu người dùng
const users = [
  { id: 1, name: "Nguyễn Văn Nhật", email: "nguyenvannhat@gmail.com", registerDate: "04/12/2024 10:30", status: "active" },
    { id: 2, name: "Trần Thị Ngọc Hiền", email: "ngochien@gmail.com", registerDate: "03/12/2024 14:15", status: "blocked" },
    { id: 3, name: "Phạm Minh Phúc", email: "phucpham.132@gmail.com", registerDate: "02/12/2024 09:45", status: "active" },
    { id: 4, name: "Lê Hoàng Minh", email: "lehoangminh99@gmail.com", registerDate: "01/12/2024 18:20", status: "active" },
    { id: 5, name: "Trần Quốc Anh", email: "quocanh@gmail.com", registerDate: "28/11/2024 12:05", status: "blocked" },
    { id: 6, name: "Đặng Thị Mai", email: "mai.dang@gmail.com", registerDate: "25/11/2024 08:00", status: "active" },
    { id: 7, name: "Ngô Thanh Sơn", email: "ngothanhson@gmail.com", registerDate: "22/11/2024 15:45", status: "active" },
    { id: 8, name: "Võ Văn Hùng", email: "hvo@gmail.com", registerDate: "20/11/2024 11:00", status: "blocked" },
    { id: 9, name: "Nguyễn Thị Lan", email: "lan.nt@gmail.com", registerDate: "18/11/2024 16:25", status: "active" },
    { id: 10, name: "Phạm Đức Huy", email: "huypham@gmail.com", registerDate: "15/11/2024 13:40", status: "active" },
    { id: 11, name: "Bùi Văn Hoàng", email: "buihoang@gmail.com", registerDate: "10/11/2024 09:00", status: "blocked" },
    { id: 12, name: "Đỗ Minh Trí", email: "dotri@gmail.com", registerDate: "05/11/2024 19:30", status: "active" },
    { id: 13, name: "Nguyễn Văn Hậu", email: "nguyenhau@gmail.com", registerDate: "02/11/2024 14:15", status: "blocked" },
    { id: 14, name: "Hoàng Minh Phát", email: "phathoang@gmail.com", registerDate: "28/10/2024 10:50", status: "active" },
    { id: 15, name: "Lý Gia Bảo", email: "lygiabao@gmail.com", registerDate: "25/10/2024 08:35", status: "active" },
    { id: 16, name: "Vũ Ngọc Hân", email: "hanngoc@gmail.com", registerDate: "20/10/2024 13:00", status: "blocked" },
    { id: 17, name: "Trần Văn Tài", email: "taivantran@gmail.com", registerDate: "18/10/2024 11:30", status: "active" },
    { id: 18, name: "Phạm Thị Thu", email: "thupham@gmail.com", registerDate: "15/10/2024 17:45", status: "active" },
    { id: 19, name: "Nguyễn Hồng Anh", email: "honganh@gmail.com", registerDate: "10/10/2024 20:20", status: "blocked" },
    { id: 20, name: "Đỗ Thanh Tùng", email: "dothanhtung@gmail.com", registerDate: "05/10/2024 09:15", status: "active" },
    { id: 20, name: "Đỗ Thanh Tùng", email: "dothanhtung@gmail.com", registerDate: "05/10/2024 09:15", status: "active" },

  ];
  // Biến quản lý phân trang
  let currentPage = 1;
  const itemsPerPage = 10;

 function renderUsers() {
     const userList = document.getElementById("user-list");
     userList.innerHTML = ''; // Xóa hết dữ liệu cũ

     // Tính toán dữ liệu phân trang
     const startIndex = (currentPage - 1) * itemsPerPage;
     const endIndex = startIndex + itemsPerPage;
     const currentUsers = users.slice(startIndex, endIndex);

     // Thêm dữ liệu vào bảng
     currentUsers.forEach(user => {
         const row = document.createElement("tr");

         const statusButton = user.status === "active"
             ? `<button class="btn-block" onclick="toggleStatus(${user.id})">Block</button>`
             : `<button class="btn-unblock" onclick="toggleStatus(${user.id})">Unblock</button>`;

         row.innerHTML = `
             <td>${user.id}</td>
             <td>${user.name}</td>
             <td>${user.email}</td>
             <td>${user.registerDate}</td>
             <td>${user.status === "active" ? "Active" : "Blocked"}</td>
             <td>
                 ${statusButton}
                 <button class="btn-edit" onclick="editUser(${user.id})">Edit</button>
             </td>
         `;

         userList.appendChild(row);
     });

     renderPagination(); // Hiển thị phân trang
 }

 // Hàm phân trang
 function renderPagination() {
     const pagination = document.getElementById("pagination");
     pagination.innerHTML = ''; // Xóa các nút cũ

     const totalPages = Math.ceil(users.length / itemsPerPage);

     for (let i = 1; i <= totalPages; i++) {
         const pageButton = document.createElement("button");
         pageButton.textContent = i;
         pageButton.className = i === currentPage ? "active" : "";
         pageButton.onclick = () => {
             currentPage = i;
             renderUsers();
         };

         pagination.appendChild(pageButton);
     }
 }

 // Hàm thay đổi trạng thái người dùng
 function toggleStatus(userId) {
     const user = users.find(u => u.id === userId);
     if (user) {
         user.status = user.status === "active" ? "blocked" : "active";
         renderUsers();
     }
 }

 // Hàm chỉnh sửa thông tin người dùng
 function editUser(userId) {
     const user = users.find(u => u.id === userId);
     if (user) {
         alert(`Chỉnh sửa thông tin người dùng: ${user.name}`);
         // Thêm logic chỉnh sửa ở đây
     }
 }

 // Khởi động hiển thị
 document.addEventListener("DOMContentLoaded", () => {
     renderUsers();
 });

// Hiển thị danh sách người dùng mới nhất
function renderNewestUsers() {
  const userList = document.getElementById("user-list");
  userList.innerHTML = ""; // Xóa dữ liệu cũ

  // Sắp xếp người dùng theo ngày đăng ký giảm dần
  const sortedUsers = [...users].sort((a, b) => {
    const dateA = new Date(a.registerDate.split(" ")[0].split("/").reverse().join("-"));
    const dateB = new Date(b.registerDate.split(" ")[0].split("/").reverse().join("-"));
    return dateB - dateA;
  });

  // Lấy 5 người dùng mới nhất
  const newestUsers = sortedUsers.slice(0, 5);

  // Tạo các hàng dữ liệu
  newestUsers.forEach(user => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${user.id}</td>
      <td>${user.name}</td>
      <td>${user.email}</td>
      <td>${user.registerDate}</td>
      <td>${user.status === "active" ? "Active" : "Blocked"}</td>
    `;
    userList.appendChild(row);
  });
}

// Gọi hàm khi tải trang để hiển thị danh sách người dùng mới nhất
document.addEventListener("DOMContentLoaded", renderNewestUsers);


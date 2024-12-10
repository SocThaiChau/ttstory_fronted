// Dữ liệu mẫu người dùng
const users = [
    { id: 1, name: "Nguyễn Văn A", email: "nguyenvana@gmail.com", registerDate: "04/12/2024 10:30", status: "active" },
    { id: 2, name: "Trần Thị B", email: "tranthib@gmail.com", registerDate: "03/12/2024 14:15", status: "blocked" },
    { id: 3, name: "Phạm Minh C", email: "phaminhc@gmail.com", registerDate: "02/12/2024 09:45", status: "active" },
    // Thêm các người dùng khác ở đây
  ];
  
  // Hàm để tạo HTML cho mỗi người dùng
  function renderUsers() {
    const userList = document.getElementById("user-list");
    userList.innerHTML = ''; // Xóa hết dữ liệu cũ trước khi chèn mới
  
    users.forEach(user => {
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
  }
  
  // Hàm để thay đổi trạng thái người dùng
  function toggleStatus(userId) {
    const user = users.find(u => u.id === userId);
    if (user) {
      user.status = user.status === "active" ? "blocked" : "active";
      renderUsers(); // Cập nhật lại danh sách người dùng sau khi thay đổi trạng thái
    }
  }
  
  // Hàm chỉnh sửa thông tin người dùng
  function editUser(userId) {
    const user = users.find(u => u.id === userId);
    if (user) {
      alert(`Chỉnh sửa thông tin người dùng: ${user.name}`);
      // Đây là nơi bạn có thể thực hiện chức năng chỉnh sửa người dùng
      // Ví dụ: hiển thị form chỉnh sửa với thông tin người dùng
    }
  }
  
  // Gọi hàm renderUsers để hiển thị danh sách người dùng ngay khi trang tải
  document.addEventListener("DOMContentLoaded", renderUsers);
  
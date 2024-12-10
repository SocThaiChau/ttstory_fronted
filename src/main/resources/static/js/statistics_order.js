// Lấy các phần tử DOM
const orderDateRangeSelect = document.getElementById("order-date-range");
const orderStatusSelect = document.getElementById("order-status");
const ctxOrder = document.getElementById("orderStatusChart").getContext("2d");

// Khởi tạo dữ liệu mẫu
let orderData = {
    day: { success: 30, failed: 5, inProgress: 10 },
    month: { success: 600, failed: 100, inProgress: 200 },
    year: { success: 7200, failed: 1200, inProgress: 2400 },
};

// Hàm cập nhật biểu đồ
function updateOrderChart(timeRange, status) {
    // Dữ liệu gốc dựa trên phạm vi thời gian
    const timeData = orderData[timeRange];

    // Lọc dữ liệu theo trạng thái
    let chartData;
    if (status === "all") {
        chartData = [timeData.success, timeData.failed, timeData.inProgress];
    } else if (status === "success") {
        chartData = [timeData.success, 0, 0];
    } else if (status === "failed") {
        chartData = [0, timeData.failed, 0];
    } else if (status === "in-progress") {
        chartData = [0, 0, timeData.inProgress];
    }

    // Cập nhật biểu đồ
    orderChart.data.datasets[0].data = chartData;
    orderChart.update();
}

// Tạo biểu đồ ban đầu
const orderChart = new Chart(ctxOrder, {
    type: "bar",
    data: {
        labels: ["Thành công", "Thất bại", "Đang giao"],
        datasets: [{
            label: "Số lượng đơn hàng",
            data: [orderData.day.success, orderData.day.failed, orderData.day.inProgress],
            backgroundColor: [
                "rgba(75, 192, 192, 0.2)",
                "rgba(255, 99, 132, 0.2)",
                "rgba(255, 206, 86, 0.2)"
            ],
            borderColor: [
                "rgba(75, 192, 192, 1)",
                "rgba(255, 99, 132, 1)",
                "rgba(255, 206, 86, 1)"
            ],
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    }
});

// Gắn sự kiện thay đổi cho dropdown
orderDateRangeSelect.addEventListener("change", () => {
    const selectedRange = orderDateRangeSelect.value;
    const selectedStatus = orderStatusSelect.value;
    updateOrderChart(selectedRange, selectedStatus);
});

orderStatusSelect.addEventListener("change", () => {
    const selectedRange = orderDateRangeSelect.value;
    const selectedStatus = orderStatusSelect.value;
    updateOrderChart(selectedRange, selectedStatus);
});

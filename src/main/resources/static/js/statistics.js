// Dữ liệu mẫu cho biểu đồ doanh thu
const revenueData = {
    day: [120, 150, 130, 160, 200, 180, 220],
    month: [3000, 2500, 4000, 3500, 5000, 5500, 6000],
    year: [35000, 45000, 38000, 42000, 48000, 52000, 60000]
};

// Thiết lập biểu đồ
const ctx = document.getElementById('revenueChart').getContext('2d');
let revenueChart = new Chart(ctx, {
    type: 'line', // Loại biểu đồ: Line Chart
    data: {
        labels: ['2024-12-01', '2024-12-02', '2024-12-03', '2024-12-04', '2024-12-05', '2024-12-06', '2024-12-07'],
        datasets: [{
            label: 'Doanh thu',
            data: revenueData.day, // Mặc định chọn doanh thu theo ngày
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 2,
            fill: false
        }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

// Xử lý sự kiện thay đổi thời gian
document.getElementById('date-range').addEventListener('change', function() {
    const selectedValue = this.value;
    let labels, data;

    switch(selectedValue) {
        case 'month':
            labels = ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7'];
            data = revenueData.month;
            break;
        case 'year':
            labels = ['2023', '2024', '2025', '2026', '2027', '2028', '2029'];
            data = revenueData.year;
            break;
        default:
            labels = ['2024-12-01', '2024-12-02', '2024-12-03', '2024-12-04', '2024-12-05', '2024-12-06', '2024-12-07'];
            data = revenueData.day;
    }

    // Cập nhật biểu đồ với dữ liệu mới
    revenueChart.data.labels = labels;
    revenueChart.data.datasets[0].data = data;
    revenueChart.update();
});

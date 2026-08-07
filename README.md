# Laptop E-Commerce Platform

Dự án website thương mại điện tử chuyên cung cấp và bán các sản phẩm Laptop, được xây dựng bằng hệ sinh thái Spring Boot mạnh mẽ kết hợp với giao diện Thymeleaf.

## Công nghệ sử dụng

*   **Backend:** Java, Spring Boot, Spring MVC, Spring Data JPA, Spring Security.
*   **Frontend:** HTML, CSS, JavaScript, Bootstrap, Thymeleaf.
*   **Database:** MySQL.
*   **Build Tool:** Maven.

## Chức năng chính

*   **Dành cho Khách hàng:**
    *   Xem danh sách sản phẩm, lọc và tìm kiếm laptop.
    *   Xem chi tiết thông số kỹ thuật sản phẩm.
    *   Thêm sản phẩm vào giỏ hàng và thanh toán.
    *   Quản lý tài khoản cá nhân, xem lịch sử đơn hàng.
*   **Dành cho Quản trị viên (Admin):**
    *   Quản lý danh mục sản phẩm.
    *   Thêm, sửa, xóa sản phẩm và hình ảnh.
    *   Quản lý người dùng.
    *   Quản lý và cập nhật trạng thái đơn hàng.
    *   Thống kê doanh thu cơ bản.

## Hướng dẫn cài đặt và chạy dự án

1.  **Clone dự án:**
    ```bash
    git clone https://github.com/TrongPhapDev/Laptopshop.git
    ```
2.  **Cấu hình Database:**
    *   Tạo một database mới trong MySQL (ví dụ: `laptopshop`).
    *   Mở file `src/main/resources/application.properties`.
    *   Thay đổi các thông tin `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password` cho phù hợp với MySQL của bạn.
3.  **Chạy dự án:**
    *   Mở dự án bằng IDE (IntelliJ IDEA, Eclipse, VS Code...).
    *   Chạy file main `LaptopshopApplication.java`.
    *   Truy cập vào ứng dụng qua đường dẫn: `http://localhost:8080`

## Cấu trúc thư mục đáng chú ý

*   `src/main/java`: Chứa mã nguồn xử lý logic (Controller, Service, Repository, Entity...).
*   `src/main/resources/templates`: Chứa các file giao diện HTML (Thymeleaf).
*   `src/main/resources/static`: Chứa các tài nguyên tĩnh như CSS, JS, hình ảnh.
*   `uploads/` (bị bỏ qua bởi git): Thư mục chứa hình ảnh sản phẩm do admin tải lên trong quá trình chạy ứng dụng.

## Trạng thái dự án
*   [x] Chức năng Đăng ký/Đăng nhập (Thymeleaf & Spring Security).
*   [ ] Chức năng Giỏ hàng nâng cao.
*   [ ] Tích hợp thanh toán trực tuyến.
*   [ ] Hoàn thiện Dashboard Admin.

---
*Dự án được phát triển với mục đích học tập và xây dựng nền tảng thương mại điện tử.*

# Quy Trình Xây Dựng Chức Năng Xem Chi Tiết Sản Phẩm (Spring Boot MVC)

Luồng công việc sẽ luôn đi theo một chu trình chuẩn: từ lúc người dùng click chuột cho đến khi database trả dữ liệu lên màn hình.

**Bức tranh tổng quan (Luồng dữ liệu):**
`Click link (HTML)` ➡️ `Controller (Bắt ID)` ➡️ `Service (Yêu cầu tìm ID)` ➡️ `Repository (Lục tìm Database)` ➡️ `Trả dữ liệu ngược về Controller` ➡️ `Controller đưa dữ liệu ra trang HTML chi tiết`.

---

## Bước 1: Chuẩn bị ở tầng Repository (Móc nối Database)
Đầu tiên, bạn cần một công cụ để lục tìm sản phẩm trong cơ sở dữ liệu dựa trên số `ID`.
Với Spring Data JPA, việc này gần như được làm sẵn. Bạn chỉ cần interface `ProductRepository`:

```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Boot đã viết sẵn hàm findById(Long id) cho bạn ở đây rồi!
    // Bạn không cần viết thêm gì cả.
}
```

## Bước 2: Viết logic ở tầng Service (Xử lý nghiệp vụ)
Tầng Service đóng vai trò làm trung gian, nó nhận lệnh từ Controller và gọi Repository để lấy dữ liệu. Nếu cần xử lý logic (như tính toán giảm giá, đếm số lượt xem...), bạn sẽ làm ở đây.

```java
@Service
public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Viết hàm lấy sản phẩm theo ID
    public Product getProductById(Long id) {
        // Tìm sản phẩm, nếu không thấy thì trả về null (hoặc quăng lỗi)
        return productRepository.findById(id).orElse(null); 
    }
}
```

## Bước 3: Đón đường dẫn ở tầng Controller (Điều hướng)
Đây là nơi quan trọng nhất. Controller sẽ "giăng lưới" chờ người dùng truy cập vào đường dẫn có chứa ID (ví dụ: `/client/product/1`), sau đó lấy số `1` này đi hỏi Service.

```java
@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    // Đón Request GET có chứa {id} trên thanh URL
    @GetMapping("/client/product/{id}") 
    public String getProductPage(Model model, @PathVariable Long id) {
        
        // 1. Nhờ Service tìm sản phẩm có cái ID này
        Product product = productService.getProductById(id);
        
        // 2. Kiểm tra xem có tìm thấy hay không?
        if (product != null) {
            // 3. Nếu thấy: Gói sản phẩm vào một cái túi tên là "product" (Model) để gửi ra giao diện
            model.addAttribute("product", product);
            
            // 4. Mở file detail.html lên
            return "client/product/detail"; 
        } else {
            // Nếu ai đó nhập bậy ID không tồn tại, tự động đẩy họ về trang chủ
            return "redirect:/";
        }
    }
}
```

## Bước 4: Hiển thị ở tầng View (Giao diện HTML/Thymeleaf)
Phần giao diện sẽ chia làm 2 nơi:

**A. Tại trang danh sách sản phẩm (Ví dụ: `show.html`)**
Bạn phải gắn ID của sản phẩm vào đường link để khi người dùng click, nó báo cho Controller biết là đang click vào sản phẩm nào:

```html
<!-- Dùng Thymeleaf để nhúng id động vào URL -->
<div class="fruite-img">
    <a th:href="@{/client/product/{id}(id=${product.id})}">
        <img th:src="@{/uploads/product/{id}(id=${product.image})}" class="img-fluid" />
    </a>
</div>
```

**B. Tại trang chi tiết sản phẩm (`detail.html`)**
Lúc này, túi đồ (Model) mang tên `"product"` từ Controller đã được gửi ra. Bạn chỉ cần bóc túi đồ ra và dán dữ liệu vào các thẻ HTML:

```html
<!-- Lấy tên sản phẩm -->
<h1 th:text="${product.name}"></h1>

<!-- Lấy giá sản phẩm -->
<span th:text="${product.price}"></span>

<!-- Lấy hình ảnh (đường dẫn src) -->
<img th:src="@{/uploads/product/{image}(image=${product.image})}" />

<!-- Lấy mô tả chi tiết -->
<p th:text="${product.detailDesc}"></p>
```

---
**Tóm tắt quy trình Code thực tế:** 
Nghĩ ra đường dẫn (URL) ➡️ Viết hàm lấy dữ liệu trong `Repository/Service` ➡️ Viết hàm trong `Controller` để gọi Service và nhét dữ liệu vào Model ➡️ Gắn link ở trang danh sách ➡️ Dùng `th:text`, `th:src` để hiển thị trên trang chi tiết.

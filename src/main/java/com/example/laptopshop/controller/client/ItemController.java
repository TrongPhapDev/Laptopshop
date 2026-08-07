package com.example.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.laptopshop.domain.Product;
import com.example.laptopshop.service.ProductService;

@Controller
public class ItemController {
    
    private final ProductService productService;
    
    public ItemController(ProductService productService) {
        this.productService = productService;
    }
    
    @GetMapping("/client/product/{id}")
    public String getProductPage(Model model, @PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
        } else {
            // Xử lý trường hợp sản phẩm không tìm thấy
            return "redirect:/";
        }
        return "client/product/detail";
    }
}

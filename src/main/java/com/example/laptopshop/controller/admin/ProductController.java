package com.example.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.laptopshop.domain.Product;
import com.example.laptopshop.service.ProductService;
import com.example.laptopshop.service.UploadService;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("products", products);

        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(
            @ModelAttribute("product") Product product,
            @RequestParam("imageFile") MultipartFile file) {

        if (!file.isEmpty()) {
            String image = uploadService.handleSaveUploadFile(file, "product");
            product.setImage(image);
        }

        productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductPage(Model model, @PathVariable Long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable Long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(
            @ModelAttribute("product") @Valid Product product,
            BindingResult bindingResult,
            @RequestParam("imageFile") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            return "admin/product/update";
        }

        if (!file.isEmpty()) {
            String image = uploadService.handleSaveUploadFile(file, "product");
            product.setImage(image);
        }

        productService.handleSaveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable Long id) {
        Product product = this.productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productService.deleteProduct(product.getId());
        return "redirect:/admin/product";
    }

}

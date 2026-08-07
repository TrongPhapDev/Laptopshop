package com.example.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.laptopshop.domain.Product;
import com.example.laptopshop.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }
}

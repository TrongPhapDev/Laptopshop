package com.example.laptopshop.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Tìm thư mục uploads - thử cả 2 vị trí có thể
        Path projectDir = Paths.get(System.getProperty("user.dir"));
        Path uploadsDir = projectDir.resolve("uploads");

        // Nếu uploads không tồn tại ở user.dir, thử tìm trong laptopshop
        if (!uploadsDir.toFile().exists()) {
            uploadsDir = projectDir.resolve("laptopshop").resolve("uploads");
        }

        // Tạo URI chuẩn cho Windows (file:///D:/...)
        String uploadUri = uploadsDir.toUri().toString();

        System.out.println("============================================");
        System.out.println(">>> user.dir = " + System.getProperty("user.dir"));
        System.out.println(">>> Upload URI = " + uploadUri);
        System.out.println(">>> uploads exists = " + uploadsDir.toFile().exists());
        System.out.println("============================================");

        registry.addResourceHandler("/avatar/**")
                .addResourceLocations(uploadUri + "avatar/");

        registry.addResourceHandler("/product/**")
                .addResourceLocations(uploadUri + "product/");
    }
}

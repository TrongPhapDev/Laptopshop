package com.example.laptopshop.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

    /**
     * Tìm thư mục uploads - tự động phát hiện đúng vị trí
     * dù user.dir là TH3 hay TH3/laptopshop
     */
    private Path getUploadBasePath() {
        Path projectDir = Paths.get(System.getProperty("user.dir"));
        Path uploadsDir = projectDir.resolve("uploads");

        if (!uploadsDir.toFile().exists()) {
            // Nếu uploads không tồn tại ở user.dir, thử tìm trong laptopshop
            Path altDir = projectDir.resolve("laptopshop").resolve("uploads");
            if (altDir.toFile().exists()) {
                return altDir;
            }
        }
        return uploadsDir;
    }

    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {
        try {
            if (file.isEmpty()) return null;

            // Dùng chung logic tìm thư mục uploads
            File uploadDir = getUploadBasePath().resolve(targetFolder).toFile();
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filename = System.currentTimeMillis()
                    + "-" + file.getOriginalFilename();

            File destination = new File(uploadDir, filename);

            file.transferTo(destination);

            System.out.println(">>> UPLOAD OK: " + destination.getAbsolutePath());
            return filename;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Upload failed");
        }
    }
}

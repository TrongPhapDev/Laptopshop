package com.example.laptopshop.controller.client;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.laptopshop.domain.User;
import com.example.laptopshop.domain.dto.RegisterDTO;
import com.example.laptopshop.service.ProductService;
import com.example.laptopshop.service.RoleService;
import com.example.laptopshop.service.UserService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public HomePageController(ProductService productService, UserService userService,
            PasswordEncoder passwordEncoder, RoleService roleService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "client/auth/register";
        }

        User user = userService.registerDTOtoUser(registerDTO);

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setRole(this.roleService.getRoleByName("USER"));

        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "client/auth/login";
    }

    @PostMapping("/login")
    public String getLoginPage(Model model) {
        return "client/auth/login";
    }
    @GetMapping("/access-deny")
    public String getAccessDenyPage() {
        return "client/auth/access-deny";
    }
}

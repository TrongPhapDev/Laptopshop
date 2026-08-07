package com.example.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.laptopshop.domain.User;
import com.example.laptopshop.repository.UserRepository;
import com.example.laptopshop.service.RoleService;
import com.example.laptopshop.service.UploadService;
import com.example.laptopshop.service.UserService;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleService RoleService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UserRepository userRepository, RoleService RoleService,
            UploadService uploadService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.RoleService = RoleService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String home(Model model) {
        List<User> users = this.userService.getAllUsersByEmail("trongphap.0509@gmail.com");
        System.out.println(users);
        model.addAttribute("message", "test");
        return "index";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable Long id) {
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String showCreateUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", RoleService.getAllRoles());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String createUserPage(
            @ModelAttribute("user") @Valid User user,
            BindingResult newUserBindingResult,
            @RequestParam(value = "roleId", required = false) Long roleId,
            @RequestParam("avatarFile") MultipartFile file,
            Model model) {

        // validate
        if (newUserBindingResult.hasErrors()) {
            model.addAttribute("roles", RoleService.getAllRoles());
            model.addAttribute("roleId", roleId);
            return "admin/user/create";
        }

        // check email exists
        if (this.userService.checkEmailExists(user.getEmail())) {
            newUserBindingResult.addError(new FieldError("user", "email", "Email đã tồn tại"));
            model.addAttribute("roles", RoleService.getAllRoles());
            model.addAttribute("roleId", roleId);
            return "admin/user/create";
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        if (roleId != null) {
            user.setRole(RoleService.getRoleById(roleId));
        }

        if (!file.isEmpty()) {
            String avatar = uploadService.handleSaveUploadFile(file, "avatar");
            user.setAvatar(avatar);
        }

        userService.saveUser(user);
        return "redirect:/admin/user";
    }

    @RequestMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable Long id) {
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("user", currentUser);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(@ModelAttribute("user") User hoidanit) {
        User currentUser = this.userService.getUserById(hoidanit.getId());
        if (currentUser != null) {
            currentUser.setFullName(hoidanit.getFullName());
            currentUser.setPhone(hoidanit.getPhone());
            currentUser.setAddress(hoidanit.getAddress());
            this.userRepository.save(currentUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeleteUserPage(Model model, @PathVariable Long id) {
        User user = new User();
        user.setId(id);
        model.addAttribute("user", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String DeleteUser(Model model, @ModelAttribute("user") User trongphap) {
        this.userRepository.deleteById(trongphap.getId());
        return "redirect:/admin/user";
    }

}

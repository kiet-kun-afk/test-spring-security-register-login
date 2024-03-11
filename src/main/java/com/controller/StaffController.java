package com.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Role;
import com.entity.Staff;
import com.entity.Role.RoleEnum;
import com.repository.RoleRepository;
import com.repository.StaffRepository;

@Controller
@RequestMapping("staff")
public class StaffController {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @SuppressWarnings("null")
    @PostMapping("/handle-register")
    public String handleRegister(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(name = "role", required = false) String role,
            @RequestParam("captcha") String captcha,
            @RequestParam("hiddenCaptcha") String hiddenCaptcha) {

        Role newRole = null;
        Map<String, RoleEnum> roleMap = Map.of("manager", RoleEnum.MANAGER, "staff", RoleEnum.STAFF);

        if (role != null) {
            RoleEnum roleEnum = roleMap.get(role.toLowerCase());
            if (roleEnum != null) {
                newRole = roleRepository.findByName(roleEnum.name());
                if (newRole == null) {
                    newRole = new Role(roleEnum);
                }
            }
        } else {
            Role defaultRole = roleRepository.findByName("STAFF");
            if (defaultRole == null) {
                newRole = new Role(RoleEnum.STAFF);
            } else {
                newRole = defaultRole;
            }
        }

        roleRepository.save(newRole);

        if (captcha.equals(hiddenCaptcha)) {
            staffRepository.save(
                    Staff
                            .builder()
                            .username(username)
                            .password(passwordEncoder.encode(password))
                            .role(newRole)
                            .build());
        } else {
            return "redirect:/register/staff";
        }
        return "redirect:/login";
    }
}

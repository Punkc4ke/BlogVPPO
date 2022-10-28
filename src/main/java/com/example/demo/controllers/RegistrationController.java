package com.example.demo.controllers;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.AutorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AutorizationService authService = new AutorizationService();

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model){
        authService.authModelAdvice(model, userRepository);
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if(bindingResult.hasErrors()) {
            return "registration";
        }
        if(userFromDb != null)
        {
            model.addAttribute("message", "Пользователь с таким логином уже зарегистрирован");
            return "registration";
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        System.out.println(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }
}

package com.example.demo.service;

import com.example.demo.models.User;
import com.example.demo.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class AutorizationService {

        public void authModelAdvice(Model model, UserRepository userRepository) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getName() != "anonymousUser") {
                User user = userRepository.findByUsername(auth.getName());
                if(user.getRoles().contains("ADMIN") == true) {
                    model.addAttribute("admin", true);
                    model.addAttribute("auth", true);
                }
            } else {
                model.addAttribute("auth", false);
            }
        }
}


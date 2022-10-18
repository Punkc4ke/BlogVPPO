package com.example.demo.controllers;

import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public String blogUserMain(Model model)
    {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "blog-main";
    }

    @GetMapping("/blog/adduser")
    public String blogAdd(Model model)
    {
        return "blog-adduser";
    }

    @PostMapping("/blog/adduser")
    public String blogUserAdd(@RequestParam String login,
                              @RequestParam String password,
                              @RequestParam String email,
                              @RequestParam Integer age,
                              @RequestParam Integer iq, Model model)
    {
        User user = new User(login, password, email, age, iq);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/blog/filter1")
    public String blogUserFilter(Model model)
    {
        return "blog-filteruser";
    }

    @PostMapping("/blog/filter1/result1")
    public String blogUserResult(@RequestParam String login, Model model)
    {

        List<User> result = userRepository.findByLoginLike(login);
        model.addAttribute("result", result);
        List<User> result1 = userRepository.findByLoginContains(login);
        model.addAttribute("result1", result1);

        return "blog-filteruser";
    }

}

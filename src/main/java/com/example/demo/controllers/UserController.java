package com.example.demo.controllers;

import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public String blogAdd(User user,Model model)
    {
        return "blog-add-user";
    }

    @PostMapping("/blog/adduser")
    public String blogUserAdd(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
    Model model)
    {
        if(bindingResult.hasErrors()) {
            return "blog-add-user";
        }
        userRepository.save(user);

        return "redirect:/user";
    }

    @GetMapping("/blog/filter1")
    public String blogUserFilter(Model model)
    {
        return "blog-filter-user";
    }

    @PostMapping("/blog/filter1/result1")
    public String blogUserResult(@RequestParam String login, Model model)
    {

        List<User> result = userRepository.findByLoginLike(login);
        model.addAttribute("result", result);
        List<User> result1 = userRepository.findByLoginContains(login);
        model.addAttribute("result1", result1);

        return "blog-filter-user";
    }

    @GetMapping("/blog/user/{id}")
    public String blogUserDetails(@PathVariable(value = "id") long id, Model model){
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        if(!userRepository.existsById(id))
        {
            return "redirect:/blog";
        }
        return  "blog-details-user";
    }

    @GetMapping("/blog/user/{id}/edit")
    public String blogUserEdit(@PathVariable("id") long id, Model model){
        if (!userRepository.existsById(id)) {
            return "redirect:/";
        }
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return  "blog-edit-user";
    }

    @PostMapping("/blog/user/{id}/edit")
    public String blogUserUpdate( @ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                 @RequestParam String login,
                                 @RequestParam String password,
                                 @RequestParam String email,
                                 @RequestParam Integer age,
                                 @RequestParam Integer iq,
                                 Model model)
    {

        if (bindingResult.hasErrors()) {
            return "blog-edit-user";
        }
         user= userRepository.findById(user.getId()).get();
        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setAge(user.getAge());
        user.setIq(user.getIq());
        userRepository.save(user);
        return "redirect:/";
    }

    @PostMapping("/blog/user/{id}/remove")
    public  String  blogUserDelete (@PathVariable("id") long id, Model model){
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/";
    }
}

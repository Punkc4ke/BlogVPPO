package com.example.demo.controllers;

import com.example.demo.models.Avto;
import com.example.demo.models.Role;
import com.example.demo.repo.UserRepository;
import com.example.demo.repo.AvtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.models.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAnyAuthority('USER')")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvtoRepository avtoRepository;

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
        Iterable<Avto> avto = avtoRepository.findAll();
        model.addAttribute("avto", avto);
        model.addAttribute("user", user);
        return "blog-add-user";
    }

    @PostMapping("/blog/adduser")
    public String blogUserAdd(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                              Model model)
    {

        if(bindingResult.hasErrors()) {
            return "blog-add-user";
        }
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setAge(user.getAge());
        user.setIq(user.getIq());

        userRepository.save(user);

        return "redirect:/user";
    }

    @GetMapping("/blog/filter1")
    public String blogUserFilter(User user, Model model)
    {
        return "blog-filter-user";
    }

    @PostMapping("/blog/filter1/result1")
    public String blogUserResult(@RequestParam String username, Model model)
    {

        List<User> result = userRepository.findByUsernameLike(username);
        model.addAttribute("result", result);
        List<User> result1 = userRepository.findByUsernameContains(username);
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

    @GetMapping("blog/user/{id}/edit")
    public String blogUserEdit(@PathVariable("id") long id, Model model){
        if (!userRepository.existsById(id)) {
            return "redirect:/";
        }
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return  "blog-edit-user";
    }

    @PostMapping("blog/user/{id}/edit")
    public String blogUserUpdate( @ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                 @RequestParam String username,
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
        user.setUsername(user.getUsername());
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

//    @GetMapping
//    public String userList(Model model){
//        model.addAttribute("users", userRepository.findAll());
//        return "blog-filter-user";
//    }
//
//    // Доп код
//
//    @GetMapping("/{id}/edit")
//    public String userEdit(@PathVariable(value = "id") long id, Model model){
//        Optional<User> user = userRepository.findById(id);
//        ArrayList<User> res = new ArrayList<>();
//        user.ifPresent(res::add);
//        model.addAttribute("user", res);
//        model.addAttribute("roles", Role.values());
//        return "blog-edit-user";
//    }
//
//    @PostMapping
//    public String userSave(@RequestParam String username,
//                           @RequestParam(name="roles[]", required = false) String[] roles,
//                           @RequestParam("userId") User user){
//        user.setUsername(username);
//        user.getRoles().clear();
//        if(roles!=null)
//        {
//            Arrays.stream(roles).forEach(r->user.getRoles().add(Role.valueOf(r)));
//        }
//        userRepository.save(user);
//
//        return "redirect:/admin";
//    }

}

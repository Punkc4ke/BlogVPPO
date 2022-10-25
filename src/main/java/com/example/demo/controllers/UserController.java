package com.example.demo.controllers;

import com.example.demo.models.Avto;
import com.example.demo.models.Role;
import com.example.demo.repo.AvtoRepository;
import com.example.demo.repo.RoleRepository;
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

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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

        Iterable<Role> role = roleRepository.findAll();
        Iterable<Avto> avto = avtoRepository.findAll();
//        Role role = roleRepository.findById(role_id).get();
//        user.setRole(role);
        model.addAttribute("avto", avto);
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        return "blog-add-user";
    }

    @PostMapping("/blog/adduser")
    public String blogUserAdd(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @Valid Role role,
                              Model model)
    {

        if(bindingResult.hasErrors()) {
            return "blog-add-user";
        }
        System.out.println(user.getRole());
        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setAge(user.getAge());
        user.setIq(user.getIq());
        user.setRole(user.getRole());
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
        Iterable<Role> role = roleRepository.findAll();
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        model.addAttribute("role", role);
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
        Iterable<Role> role = roleRepository.findAll();
        User user = userRepository.findById(id).get();
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        return  "blog-edit-user";
    }

    @PostMapping("/blog/user/{id}/edit")
    public String blogUserUpdate( @ModelAttribute("user") @Valid User user, BindingResult bindingResult, @Valid Role role,
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
        System.out.println(user.getRole());
        user= userRepository.findById(user.getId()).get();
        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setAge(user.getAge());
        user.setIq(user.getIq());
        user.setRole(user.getRole());
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

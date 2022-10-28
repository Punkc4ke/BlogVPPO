package com.example.demo.controllers;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.AutorizationService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rolesControl")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getMainPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());

        Iterable<User> users = userRepository.findAllByUsernameNot(user.getUsername());
        model.addAttribute("users", users);

        return "permission-add";
    }

//    @GetMapping("/selectedUser")
//    public String getOneUser(@RequestParam(required = false) String text,
//                             @RequestParam long id,
//                             Model model) {
//        if (!userRepository.existsById(id)) {
//            return "redirect:/";
//        }
//        User user = userRepository.findById(id).get();
//        model.addAttribute("user", user);
//
//        return "blog-details-user";
//    }

    @GetMapping("/blog/user/{id}")
    public String blogUserDetails(@PathVariable(value = "id") long id, Model model){
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        model.addAttribute("user", res);
        if(!userRepository.existsById(id))
        {
            return "redirect:/rolesControl";
        }
        return  "blog-details-user";
    }

    @GetMapping("/blog/user/{id}/edit")
    public String blogUserEdit(@PathVariable("id") long id, Model model){
        if (!userRepository.existsById(id)) {
            return "redirect:/";
        }
        User user = userRepository.findById(id).get();
        model.addAttribute("roles", Role.values());
        model.addAttribute("user", user);

        return  "blog-edit-user";
    }

    @PostMapping("/blog/user/{id}/edit")
    public String blogUserUpdate(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
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
        return "redirect:/rolesControl";
    }


    @PostMapping("/editRoles")
    public String EditRole(@ModelAttribute("post")
                                 @RequestParam long id,
                                 @RequestParam(required = false) Boolean avtoModerator,
                                 @RequestParam(required = false) Boolean admin,
                                 Model model) {

        User user = userRepository.findById(id).get();

        if (avtoModerator == null ? false : avtoModerator)
            user.getRoles().add(Role.AVTO_MODERATOR);
        else
            user.getRoles().remove(Role.AVTO_MODERATOR);

        if (admin == null ? false : admin)
            user.getRoles().add(Role.ADMIN);
        else
            user.getRoles().remove(Role.ADMIN);


        userRepository.save(user);

        return "redirect:/rolesControl";
    }

    @PostMapping
    public String userSave(@RequestParam String username,
                           @RequestParam(name="roles[]", required = false) String[] roles,
                           @RequestParam("userId") User user){
        user.setUsername(username);
        user.getRoles().clear();
        if(roles!=null)
        {
            Arrays.stream(roles).forEach(r->user.getRoles().add(Role.valueOf(r)));
        }
        userRepository.save(user);

        return "redirect:/rolesControl";
    }


}

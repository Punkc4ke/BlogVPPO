package com.example.demo.controllers;

import com.example.demo.models.Avto;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.AvtoRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AvtoController {

    @Autowired
    private AvtoRepository avtoRepository;

    @Autowired
    private UserRepository userRepository;

    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());

        return user;
    }

    @GetMapping("/avto")
    public String blogAvtoMain(Model model)
    {
        Iterable<Avto> avtos = avtoRepository.findAll();
        model.addAttribute("avtos", avtos);
        return "blog-main";
    }

    @GetMapping("/blog/addavto")
    public String blogAdd(Avto avto, Model model)
    {
        return "blog-add-avto";
    }

    @PostMapping("/blog/addavto")
    public String blogAvtoAdd(@ModelAttribute("avto") @Valid Avto avto, BindingResult bindingResult,
                              Model model)
    {
        if(bindingResult.hasErrors()) {
            return "blog-add-avto";
        }
        avtoRepository.save(avto);

        return "redirect:/avto";
    }

    @GetMapping("/blog/filter2")
    public String blogAvtoFilter(Model model)
    {
        return "blog-filter-avto";
    }

    @PostMapping("/blog/filter2/result2")
    public String blogUserResult(@RequestParam String name, Model model)
    {
        List<Avto> result = avtoRepository.findByNameLike(name);
        model.addAttribute("result", result);
        List<Avto> result1 = avtoRepository.findByNameContains(name);
        model.addAttribute("result1", result1);

        return "blog-filter-avto";
    }

    @GetMapping("/blog/avto/{id}")
    public String blogAvtoDetails(@PathVariable(value = "id") long id, Model model){
        Optional<Avto> avto = avtoRepository.findById(id);
        ArrayList<Avto> res = new ArrayList<>();
        avto.ifPresent(res::add);
        model.addAttribute("avto", res);
        if(!avtoRepository.existsById(id))
        {
            return "redirect:/blog";
        }
        return  "blog-details-avto";
    }

    @GetMapping("/blog/avto/{id}/edit")
    public String blogAvtoEdit(@RequestParam(required = false) String text,
                               @PathVariable("id") long id, Model model){
        if (!avtoRepository.existsById(id)) {
            return "redirect:/";
        }
        Avto avto = avtoRepository.findById(id).get();
        model.addAttribute("avto", avto);

        if (getAuthUser().getRoles().contains(Role.ADMIN))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);

        if (getAuthUser().getRoles().contains(Role.AVTO_MODERATOR))
            model.addAttribute("isFishModerator", true);
        else
            model.addAttribute("isFishModerator", false);

        if (avto.getUser().equals(getAuthUser()))
            model.addAttribute("entitled", true);
        else
            model.addAttribute("entitled", false);
        return  "blog-edit-avto";
    }

    @PostMapping("/blog/avto/{id}/edit")
    public String blogAvtoUpdate(@ModelAttribute("avto") @Valid Avto avto, BindingResult bindingResult,

                                 Model model)
    {
        if (bindingResult.hasErrors()) {
            return "blog-edit-avto";
        }
        avto= avtoRepository.findById(avto.getId()).get();
        avto.setName(avto.getName());
        avto.setMarka(avto.getName());
        avto.setNomer(avto.getNomer());
        avto.setProbeg(avto.getProbeg());
        avto.setHorse(avto.getHorse());
        avtoRepository.save(avto);
        return "redirect:/";
    }

    @PostMapping("/blog/avto/{id}/remove")
    public  String  blogAvtoDelete (@PathVariable("id") long id, Model model){
        Avto avto = avtoRepository.findById(id).orElseThrow();
        avtoRepository.delete(avto);
        return "redirect:/";
    }
}


package com.example.demo.controllers;

import com.example.demo.models.Avto;
import com.example.demo.models.User;
import com.example.demo.repo.AvtoRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AvtoController {

    @Autowired
    private AvtoRepository avtoRepository;

    @GetMapping("/avto")
    public String blogAvtoMain(Model model)
    {
        Iterable<Avto> avtos = avtoRepository.findAll();
        model.addAttribute("avtos", avtos);
        return "blog-main";
    }

    @GetMapping("/blog/addavto")
    public String blogAdd(Model model)
    {
        return "blog-addavto";
    }

    @PostMapping("/blog/addavto")
    public String blogAvtoAdd(@RequestParam String name,
                              @RequestParam String marka,
                              @RequestParam String nomer,
                              @RequestParam Double probeg,
                              @RequestParam Integer horse, Model model)
    {
        Avto avto = new Avto(name, marka, nomer, probeg, horse);
        avtoRepository.save(avto);
        return "redirect:/";
    }

    @GetMapping("/blog/filter2")
    public String blogAvtoFilter(Model model)
    {
        return "blog-filteravto";
    }

    @PostMapping("/blog/filter2/result2")
    public String blogUserResult(@RequestParam String name, Model model)
    {
        List<Avto> result = avtoRepository.findByNameLike(name);
        model.addAttribute("result", result);
        List<Avto> result1 = avtoRepository.findByNameContains(name);
        model.addAttribute("result1", result1);

        return "blog-filteravto";
    }
}


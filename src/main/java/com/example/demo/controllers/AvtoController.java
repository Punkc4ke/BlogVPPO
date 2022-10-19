package com.example.demo.controllers;

import com.example.demo.models.Avto;
import com.example.demo.repo.AvtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return "blog-add-avto";
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
    public String blogAvtoEdit(@PathVariable("id") long id, Model model){
        Optional<Avto> avto = avtoRepository.findById(id);
        ArrayList<Avto> res = new ArrayList<>();
        avto.ifPresent(res::add);
        model.addAttribute("avto", res);
        if(!avtoRepository.existsById(id)){
            return "redirect:/blog";
        }
        return  "blog-edit-avto";
    }

    @PostMapping("/blog/avto/{id}/edit")
    public String blogAvtoUpdate(@PathVariable("id")long id,
                                 @RequestParam String name,
                                 @RequestParam String marka,
                                 @RequestParam String nomer,
                                 @RequestParam Double probeg,
                                 @RequestParam Integer horse,
                                 Model model)
    {
        Avto avto = avtoRepository.findById(id).orElseThrow();
        avto.setName(name);
        avto.setMarka(marka);
        avto.setNomer(nomer);
        avto.setProbeg(probeg);
        avto.setHorse(horse);
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


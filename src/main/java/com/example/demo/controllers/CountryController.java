package com.example.demo.controllers;

import com.example.demo.models.Country;
import com.example.demo.models.User;
import com.example.demo.repo.CountryRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CountryController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/user/add-country")
    private String Main(Model model){
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        Iterable<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);

        return "blog-add-country-to-user";
    }

    @PostMapping("/user/add-country")
    public String blogPostAdd(@RequestParam String user, @RequestParam String countries, Model model)
    {
        User user2 = userRepository.findByLogin(user);
        Country country2 = countryRepository.findByName(countries);
        user2.getCountries().add(country2);
        country2.getUser().add(user2);
        System.out.println(user2);

        userRepository.save(user2);
        countryRepository.save(country2);

        return "blog-main";
    }
}

package com.example.demo.controllers;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.models.Post;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController  {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getMainPage(@AuthenticationPrincipal User currentUser,
                              @RequestParam(required = false) String title,
                              @RequestParam(required = false) Boolean exactSearch, Model model) {

        Iterable<Post> posts = new ArrayList<Post>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        model.addAttribute("userName", user.getUsername());
        if (user.getRoles().contains(Role.ADMIN))
            model.addAttribute("isAdmin", true);
        else
            model.addAttribute("isAdmin", false);

        if (title != null && title != "") {
            if (exactSearch != null && exactSearch == true)
                posts = postRepository.findByTitle(title);
            else
                posts = postRepository.findByTitleContains(title);
        } else
            posts = postRepository.findAll();

        model.addAttribute("posts", posts);

        return "blog-main";

    }

   @GetMapping("/blog/add")
    public String blogAdd(Model model)
    {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model)
    {
        Post post = new Post(title, anons, full_text);
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/blog/filter")
    public String blogFilter(Model model)
    {
        return "blog-filter";
    }

    @PostMapping("/blog/filter/result")
    public String blogResult(@RequestParam String title, Model model)
    {
        List<Post> result = postRepository.findByTitleContains(title);
        model.addAttribute("result", result);
        return "blog-filter";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model){
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if(!postRepository.existsById(id))
        {
            return "redirect:/blog";
        }
        return  "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable("id") long id, Model model){
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        return  "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable("id")long id,
                                    @RequestParam String title,
                                    @RequestParam String anons,
                                    @RequestParam String full_text,
                                    Model model)
    {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/";
    }

    @PostMapping("/blog/{id}/remove")
    public  String  blogPostDelete (@PathVariable("id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/";
    }



}

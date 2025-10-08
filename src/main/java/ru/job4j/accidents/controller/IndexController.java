package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class IndexController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        return "index";
    }
}

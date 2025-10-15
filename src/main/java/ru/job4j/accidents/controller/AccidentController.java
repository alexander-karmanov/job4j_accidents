package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidents;

    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidents.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String edit(@RequestParam("id") int id, Model model) {
        Optional<Accident> accident = this.accidents.getById(id);
        if (accident.isPresent()) {
            model.addAttribute("accident", accident);
            return "editAccident";
        }
        model.addAttribute("message", "Not Found");
        return "errors/404";
    }

    @PostMapping("/editAccident")
    public String update(@ModelAttribute Accident accident, Model model) {
        if (accidents.update(accident)) {
            return "redirect:/index";
        }
        model.addAttribute("message", "Update error");
        return "errors/404";
    }
}

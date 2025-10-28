package ru.job4j.accidents.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.*;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;
    private final AccidentTypeService type;

    private final RuleService rule;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = type.findAll();
        Set<Rule> rules = rule.findAll();
        model.addAttribute("types", types);
        model.addAttribute("rules", rules);
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        if (ids != null) {
            Set<Rule> rules = rule.findAllById(ids);
            accident.setRules(new HashSet<>(rules));
        }
        accidentService.create(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String edit(@RequestParam("id") int id, Model model) {
        Optional<Accident> accident = accidentService.getById(id);
        if (accident.isPresent()) {
            model.addAttribute("accident", accident);
            model.addAttribute("types", type.findAll());
            return "editAccident";
        }
        model.addAttribute("message", "Accident not found");
        return "errors/404";
    }

    @PostMapping("/editAccident")
    public String update(@ModelAttribute Accident accident, @RequestParam("typeId") int typeId, Model model) {
        AccidentType selectedType = type.getById(typeId);
        if (selectedType == null) {
            model.addAttribute("accident", accident);
            model.addAttribute("types", type.findAll());
            model.addAttribute("error", "Invalid accident type selected.");
            return "editAccident";
        }
        accident.setType(selectedType);

        if (accident.getName() == null || accident.getName().trim().isEmpty()) {
            model.addAttribute("accident", accident);
            model.addAttribute("types", type.findAll());
            model.addAttribute("error", "Name cannot be empty.");
            return "editAccident";
        }

        if (accidentService.update(accident)) {
            return "redirect:/index";
        } else {
            model.addAttribute("accident", accident);
            model.addAttribute("types", type.findAll());
            model.addAttribute("error", "Update failed. Please try again.");
            return "editAccident";
        }
    }
}

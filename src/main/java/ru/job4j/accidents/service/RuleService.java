package ru.job4j.accidents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RuleService {
    private final RuleMem ruleMem;

    @Autowired
    public RuleService(RuleMem memory) {
        this.ruleMem = memory;
    }

    public Set<Rule> findAll() {
        return ruleMem.getAll();
    }

    public Set<Rule> findAllById(String[] ids) {
        Set<String> idSet = new HashSet<>(Arrays.asList(ids));
        return ruleMem.getAll().stream()
                .filter(rule -> idSet.contains(rule.getId()))
                .collect(Collectors.toSet());
    }
}


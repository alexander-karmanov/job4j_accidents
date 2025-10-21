package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RuleMem {
    private final Set<Rule> rules = ConcurrentHashMap.newKeySet();

    public RuleMem() {
        create(new Rule(1, "Статья 1."));
        create(new Rule(2, "Статья 2."));
        create(new Rule(3, "Статья 3."));
    }

    public void create(Rule rule) {
        rules.add(rule);
    }

    public Set<Rule> getAll() {
        return rules;
    }
}

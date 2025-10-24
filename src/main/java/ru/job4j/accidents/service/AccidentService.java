package ru.job4j.accidents.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;

import java.util.Set;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentService {

    private final AccidentTypeService type;
    private final RuleService rule;
    private final AccidentJdbcTemplate accidentsRepository;

    @Autowired
    public AccidentService(AccidentTypeService type, RuleService rule, AccidentJdbcTemplate accidentsRepository) {
        this.type = type;
        this.rule = rule;
        this.accidentsRepository = accidentsRepository;
    }

    @PostConstruct
    public void init() {
        try {
            AccidentType type1 = this.type.getById(1);
            Set<Rule> allRules = this.rule.findAll();
            if (type1 != null && allRules != null) {
                create(new Accident(1, "name1", "text1", "address1", type1, allRules));
                create(new Accident(2, "name2", "text2", "address2", this.type.getById(2), allRules));
                create(new Accident(3, "name3", "text3", "address3", this.type.getById(3), allRules));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create(Accident accident) {
        accidentsRepository.save(accident);
    }

    public List<Accident> findAll() {
        List<Accident> accidents = accidentsRepository.getAll();
        return accidents;
    }

    public boolean update(Accident accident) {
        boolean updated = accidentsRepository.update(accident);
        return updated;
    }

    public Optional<Accident> getById(int id) {
        Optional<Accident> accident = accidentsRepository.findById(id);  // Предполагаю метод в AccidentJdbcTemplate
        return accident;
    }
}

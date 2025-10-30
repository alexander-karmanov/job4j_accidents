package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentHibernate;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.ArrayList;
import java.util.Set;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentTypeService type;
    private final RuleService rule;

    private final AccidentRepository accidentsRepository;

    public void create(Accident accident) {
        accidentsRepository.save(accident);
    }

    public List<Accident> findAll() {
        Iterable<Accident> accidentsIterable = accidentsRepository.findAll();
        return StreamSupport.stream(accidentsIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public boolean update(Accident accident) {
        try {
            accidentsRepository.save(accident);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Accident> getById(int id) {
        Optional<Accident> accident = accidentsRepository.findById(id);
        return accident;
    }
}

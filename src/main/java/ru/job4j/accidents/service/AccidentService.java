package ru.job4j.accidents.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentHibernate;
import ru.job4j.accidents.repository.AccidentMem;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;

import java.util.Set;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {

    private final AccidentTypeService type;
    private final RuleService rule;
    private final AccidentHibernate accidentsRepository;

    public void create(Accident accident) {
        accidentsRepository.save(accident);
    }

    public List<Accident> findAll() {
        List<Accident> accidents = accidentsRepository.getAll();
        return accidents != null ? accidents : List.of();
    }

    public boolean update(Accident accident) {
        boolean updated = accidentsRepository.update(accident);
        return updated;
    }

    public Optional<Accident> getById(int id) {
        Optional<Accident> accident = accidentsRepository.findById(id);
        return accident;
    }
}

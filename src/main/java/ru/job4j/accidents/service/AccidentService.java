package ru.job4j.accidents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;

@Service
public class AccidentService {

    private final AccidentMem accidentMem;

    @Autowired
    public AccidentService(AccidentMem accidentMem) {
        this.accidentMem = accidentMem;

        create(new Accident(1, "name1", "text1", "address1"));
        create(new Accident(2, "name2", "text2", "address2"));
        create(new Accident(3, "name3", "text3", "address3"));
    }

    public void create(Accident accident) {
        accidentMem.create(accident);
    }

    public List<Accident> findAll() {
        return this.accidentMem.getAll();
    }
}

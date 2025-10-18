package ru.job4j.accidents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.Optional;

@Service
public class AccidentService {

    private final AccidentMem accidentMem;

    private final AccidentTypeService type;

    @Autowired
    public AccidentService(AccidentMem accidentMem, AccidentTypeService type) {
        this.accidentMem = accidentMem;
        this.type = type;

        create(new Accident(1, "name1", "text1", "address1", this.type.getById(1)));
        create(new Accident(2, "name2", "text2", "address2", this.type.getById(2)));
        create(new Accident(3, "name3", "text3", "address3", this.type.getById(3)));
    }

    public void create(Accident accident) {
        accidentMem.create(accident);
    }

    public List<Accident> findAll() {
        return this.accidentMem.getAll();
    }

    public boolean update(Accident accident) {
        return accidentMem.update(accident);
    }

    public Optional<Accident> getById(int id) {
        return accidentMem.findById(id);
    }
}

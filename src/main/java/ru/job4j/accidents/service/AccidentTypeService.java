package ru.job4j.accidents.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.List;

@Service
public class AccidentTypeService {
    private final AccidentTypeMem accidentTypeMem;

    @Autowired
    public AccidentTypeService(AccidentTypeMem memoryTypes) {
        this.accidentTypeMem = memoryTypes;
    }

    public List<AccidentType> findAll() {
        return accidentTypeMem.getAll();
    }

    public AccidentType getById(int id) {
        return accidentTypeMem.findById(id);
    }
}

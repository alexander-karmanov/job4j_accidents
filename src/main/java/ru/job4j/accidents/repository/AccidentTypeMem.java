package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeMem {
    private final Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public AccidentTypeMem() {

        create(new AccidentType(1, "Две машины"));
        create(new AccidentType(2, "Машина и человек"));
        create(new AccidentType(3, "Машина и велосипед"));

    }

    public void create(AccidentType accidentType) {
        accidentType.setId(counter.addAndGet(1));
        accidentTypes.put(accidentType.getId(), accidentType);
    }

    public List<AccidentType> getAll() {
        return new ArrayList<AccidentType>(accidentTypes.values());
    }

    public AccidentType findById(int id) {
        return accidentTypes.get(id);
    }
}


package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    public void create(Accident accident) {
        accident.setId(counter.addAndGet(1));
        accidents.put(accident.getId(), accident);
    }

    public List<Accident> getAll() {
        List<Accident> result = new ArrayList<>(accidents.values());
        return new ArrayList<Accident>(accidents.values());
    }

    public boolean update(Accident accident) {
        var newAccident = accidents.computeIfPresent(accident.getId(), (key, value) -> accident);
        return newAccident != null;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }
}

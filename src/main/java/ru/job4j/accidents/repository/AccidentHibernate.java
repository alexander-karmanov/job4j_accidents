package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final SessionFactory sf;

    private final CrudRepository crudRepository;

    public Accident save(Accident accident) {
        crudRepository.run(session -> session.save(accident));
        return accident;
    }

    public List<Accident> getAll() {
        return crudRepository.query("""
                    select distinct a from Accident a
                    left join a.type t
                    left join fetch a.rules r
                    order by a.id
                    """,
                Accident.class);
    }

    public boolean update(Accident accident) {
        try {
            crudRepository.run(session -> session.update(accident));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional(Accident.class, id);
    }
}


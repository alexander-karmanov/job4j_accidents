package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;

    public Accident save(Accident accident) {
        jdbc.update("insert into accidents (name) values (?)",
                accident.getName());
        return accident;
    }

    public List<Accident> getAll() {
        return jdbc.query("SELECT id, name, text, address, type_id FROM accidents",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    return accident;
                });
    }

    public boolean update(Accident accident) {
        int rowsAffected = jdbc.update(
                "UPDATE accidents SET name = ? WHERE id = ?",
                accident.getName(), accident.getId()
        );
        return rowsAffected > 0;
    }

    public Optional<Accident> findById(int id) {
        try {
            Accident accident = jdbc.queryForObject(
                    "SELECT id, name FROM accidents WHERE id = ?",
                    (rs, rowNum) -> {
                        Accident acc = new Accident();
                        acc.setId(rs.getInt("id"));
                        acc.setName(rs.getString("name"));
                        return acc;
                    },
                    id
            );
            return Optional.of(accident);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}

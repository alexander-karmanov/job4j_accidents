CREATE TABLE IF NOT EXISTS accident_rule (
    id SERIAL primary key,
    accident_id INT NOT NULL REFERENCES accidents (id) NOT NULL,
    rule_id INT NOT NULL REFERENCES rules (id) NOT NULL
);
CREATE TABLE IF NOT EXISTS accidents (
  id serial primary key,
  name text not null,
  text text not null,
  address text not null,
  type_id int references types (id) not null
);
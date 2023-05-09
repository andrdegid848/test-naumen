CREATE TABLE IF NOT EXISTS person
(
    id         BIGSERIAL PRIMARY KEY,
    firstname  VARCHAR(64) UNIQUE NOT NULL,
    lastname   VARCHAR(64),
    birth_date DATE,
    age        INT                NOT NULL
);
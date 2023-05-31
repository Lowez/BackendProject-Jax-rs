CREATE DATABASE postgres;

CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    message VARCHAR(255)
);
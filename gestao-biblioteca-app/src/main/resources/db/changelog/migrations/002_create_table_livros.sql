--liquibase formatted sql

--changeset brunoestrai:1

CREATE TABLE LIVROS (
    ID SERIAL PRIMARY KEY,
    TITULO VARCHAR(255) NOT NULL,
    AUTOR VARCHAR(50) NOT NULL,
    ISBN VARCHAR(13) NOT NULL,
    CATEGORIA INTEGER NOT NULL,
    DATA_PUBLICACAO DATE NOT NULL
);

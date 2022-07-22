-- liquibase formatted sql

--changeset AntonFedorovich95:1
CREATE INDEX student_name_index ON student (name);

--changeset AntonFedorovich95:2
CREATE INDEX faculty_name_and_color_index ON faculty (nameOrColor, nameOrColor);
CREATE TABLE residents (
                           id CHAR(36) PRIMARY KEY,
                           name VARCHAR(150) NOT NULL,
                           phone VARCHAR(20) NOT NULL,
                           email VARCHAR(150) NOT NULL UNIQUE,
                           active BOOLEAN NOT NULL DEFAULT TRUE,
                           unit_id CHAR(36) NOT NULL,
                           CONSTRAINT fk_residents_unit
                               FOREIGN KEY (unit_id) REFERENCES units(id)
);
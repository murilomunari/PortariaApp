CREATE TABLE packages (
                          id CHAR(36) PRIMARY KEY,
                          description VARCHAR(150) NOT NULL,
                          sender VARCHAR(150) NOT NULL,
                          tracking_code VARCHAR(100) UNIQUE,
                          received_at TIMESTAMP NOT NULL,
                          picked_up_at TIMESTAMP NULL,
                          status VARCHAR(30) NOT NULL,
                          notes VARCHAR(255),
                          resident_id CHAR(36) NOT NULL,
                          received_by CHAR(36) NOT NULL,
                          delivered_by CHAR(36) NULL,

                          CONSTRAINT fk_packages_resident
                              FOREIGN KEY (resident_id) REFERENCES residents(id),

                          CONSTRAINT fk_packages_received_by
                              FOREIGN KEY (received_by) REFERENCES users(id),

                          CONSTRAINT fk_packages_delivered_by
                              FOREIGN KEY (delivered_by) REFERENCES users(id)
);
CREATE TABLE units (
                       id CHAR(36) PRIMARY KEY,
                       block VARCHAR(50) NOT NULL,
                       number VARCHAR(20) NOT NULL,
                       floor INT,
                       description VARCHAR(255),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150),
    description VARCHAR(255),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    start_date TIMESTAMP,
    finish_date TIMESTAMP,
    status VARCHAR(10) DEFAULT 'CREATED'
);

CREATE INDEX idx_title ON task(title);
CREATE INDEX idx_description ON task(description);

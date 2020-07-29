CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    username VARCHAR(50),
    password VARCHAR(50),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  role varchar(100) NOT NULL,
  UNIQUE (role)
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO roles (role) VALUES('admin');
INSERT INTO roles (role) VALUES('manager');
INSERT INTO roles (role) VALUES('suport');

INSERT INTO users (name, username, password, create_date, active) VALUES('Cristiano Lima', 'crisun', 'f7ad64c647d8e0711386f8311061dcf7', CURRENT_TIMESTAMP(), TRUE);
INSERT INTO users (name, username, password, create_date, active) VALUES('Manager 1', 'manager', 'e10adc3949ba59abbe56e057f20f883e', CURRENT_TIMESTAMP(), TRUE);
INSERT INTO users (name, username, password, create_date, active) VALUES('Operador 2', 'operator', 'e10adc3949ba59abbe56e057f20f883e', CURRENT_TIMESTAMP(), TRUE);

INSERT INTO user_roles (user_id, role_id) VALUES((SELECT id FROM users WHERE username = 'crisun'), (SELECT id FROM roles WHERE role = 'admin'));
INSERT INTO user_roles (user_id, role_id) VALUES((SELECT id FROM users WHERE username = 'crisun'), (SELECT id FROM roles WHERE role = 'manager'));
INSERT INTO user_roles (user_id, role_id) VALUES((SELECT id FROM users WHERE username = 'manager'), (SELECT id FROM roles WHERE role = 'suport'));
INSERT INTO user_roles (user_id, role_id) VALUES((SELECT id FROM users WHERE username = 'operator'), (SELECT id FROM roles WHERE role = 'manager'));

CREATE TABLE app_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_role (
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- Insert roles
INSERT INTO role(name) VALUES ('ROLE_READER'), ('ROLE_ADMIN');

-- Insert users with bcrypt encoded passwords (password: 'password' for both)
INSERT INTO app_user(username, password, enabled) VALUES 
('reader', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', TRUE),
('admin', '$2a$10$EunVq8zAZJ.9MRw/BmHpd.pgttPZFnLB0BCZkWsBsGKq1A6meuSK.', TRUE);

-- Assign roles to users
INSERT INTO user_role(user_id, role_id) 
SELECT u.id, r.id FROM app_user u, role r WHERE u.username = 'reader' AND r.name = 'ROLE_READER';

INSERT INTO user_role(user_id, role_id) 
SELECT u.id, r.id FROM app_user u, role r WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

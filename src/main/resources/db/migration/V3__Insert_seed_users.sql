-- Password "123" (bcrypt hash)
INSERT INTO users(name, email, password, admin)
VALUES ('admin', 'admin@gmail.com', '$2a$10$764UawEZRZYZ4dBpIKjuUuEVpR1siuzOu9.LLWpFHJV88ZVLaPLvG', true),
       ('user', 'user@gmail.com', '$2a$10$764UawEZRZYZ4dBpIKjuUuEVpR1siuzOu9.LLWpFHJV88ZVLaPLvG', false);

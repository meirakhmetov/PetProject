DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@ok.kz','password'),
('Admin','admin@ok.kz','admin');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER',100000),
('ROLE_ADMIN',100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES  ('2023-01-01 10:00:00','Завтрак',500,100000),
        ('2023-01-01 13:00:00','Обед',1000,100000),
        ('2023-01-01 20:00:00','Ужин',500,100000),
        ('2023-01-02 10:00:00','Завтрак',500,100000),
        ('2023-01-02 13:00:00','Обед',1000,100000),
        ('2023-01-02 20:00:00','Ужин',510,100000),
        ('2023-01-03 14:00:00','Админ ланч',510,100001),
        ('2023-01-03 21:00:00','Админ ужин',1500,100001);



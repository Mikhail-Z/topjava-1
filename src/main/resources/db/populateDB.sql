DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals(description, user_id, calories, date_time)
VALUES ('Завтрак', 100000, 90, to_timestamp('01.04.2021 09:00', 'DD.MM.YYYY HH24:MI'));
INSERT INTO meals(description, user_id, calories, date_time)
VALUES ('Обед', 100000, 150, to_timestamp('01.04.2021 15:00', 'DD.MM.YYYY HH24:MI'));
INSERT INTO meals(description, user_id, calories, date_time)
VALUES ('Ужин', 100000, 120, to_timestamp('01.04.2021 20:00', 'DD.MM.YYYY HH24:MI'));
INSERT INTO meals(description, user_id, calories, date_time)
VALUES ('Завтрак', 100000, 100, to_timestamp('02.04.2021 09:00', 'DD.MM.YYYY HH24:MI'));
INSERT INTO meals(description, user_id, calories, date_time)
VALUES ('Обед', 100000, 140, to_timestamp('02.04.2021 15:00', 'DD.MM.YYYY HH24:MI'));
INSERT INTO meals(description, user_id, calories, date_time)
VALUES ('Ужин', 100000, 110, to_timestamp('02.04.2021 20:00', 'DD.MM.YYYY HH24:MI'));
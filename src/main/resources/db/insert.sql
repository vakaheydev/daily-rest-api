INSERT INTO user_type (user_type_name)
VALUES ('user'),
       ('vip'),
       ('admin'),
       ('developer');

INSERT INTO daily_user (user_login, user_password, user_first_name, user_second_name,
                        user_patronymic, user_telegram_id, id_user_type)
VALUES ('vaka', 'vaka123', 'Иван', 'Новгородов', 'Андреевич', 1531192384, 4),
       ('retere', 'retere123', 'Павел', 'Новгородов', 'Андреевич', 5393306493, 1);

INSERT INTO daily_user (user_login, user_password, user_first_name, user_second_name,
                        user_patronymic, id_user_type)
VALUES ('aka', 'aka123', 'Анна', 'Новгородова', 'Андреевна', 1);

INSERT INTO schedule (schedule_name, id_user)
VALUES ('main', 1),
       ('main', 2),
       ('main', 3);

INSERT INTO task_type (task_type_name)
VALUES ('singular'),
       ('repetitive'),
       ('regular');

INSERT INTO task (task_name, task_description, task_deadline, task_status, id_schedule, id_task_type)
VALUES ('Прочитать книгу', 'Прочитать книгу Java Core', '2023-11-30', true, 1, 1),
       ('Разработать REST API', 'Полностью сделать REST API Vaka Daily', '2025-05-30', false, 1, 1),
       ('Прочитать книгу', 'Закончить книгу Pro Spring 6', '2024-07-31', true, 1, 1),
       ('Таска', 'Чем-то заняться', '2025-05-29', false, 2, 1),
       ('Таска', 'Чем-то заняться', '2025-05-28', false, 3, 1);


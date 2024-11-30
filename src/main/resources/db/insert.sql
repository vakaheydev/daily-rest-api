INSERT INTO
    user_type (user_type_name)
VALUES
    ('user'),
    ('vip'),
    ('admin'),
    ('developer');

INSERT INTO
    daily_user (user_login, user_password, user_first_name, user_second_name, user_patronymic, user_telegram_id, user_type_id)
VALUES
    ('vaka', 'vaka123', 'Иван', 'Новгородов', 'Андреевич', 1531192384, 4);

INSERT INTO
    daily_user (user_login, user_password, user_first_name, user_second_name, user_patronymic, user_type_id)
VALUES
    ('aka', 'aka123', 'Анна', 'Новгородова', 'Андреевна', 1),
    ('retere', 'retere123', 'Павел', 'Новгородов', 'Андреевич', 1);

INSERT INTO
    schedule (schedule_name, id_user)
VALUES
    ('main', 1),
    ('main', 2),
    ('main', 3);

INSERT INTO
    task (task_name, task_description, task_deadline, task_status, id_schedule)
VALUES
    ('Прочитать книгу', 'Прочитать книгу Java Core', '2023-11-30', true, 1),
    ('Разработать REST API', 'Полностью сделать REST API Vaka Daily', '2024-08-31', false, 1),
    ('Прочитать книгу', 'Закончить книгу Pro Spring 6', '2024-07-31', false, 1),
    ('Почилить в Турции', 'Словить дикий чилл в Турции', '2024-07-01', false, 2),
    ('Почилить в Турции', 'Словить дикий чилл в Турции', '2024-07-01', false, 3);
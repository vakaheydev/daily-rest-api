CREATE TABLE IF NOT EXISTS User_Type (
    user_type_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_type_name VARCHAR(100) CONSTRAINT UQ_User_Type_Name UNIQUE
);

CREATE TABLE IF NOT EXISTS Daily_User (
    user_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_login VARCHAR(100) CONSTRAINT Daily_User_Login UNIQUE,
    user_password VARCHAR(100) NOT NULL,
    user_first_name VARCHAR(100) NOT NULL,
    user_second_name VARCHAR(100) NOT NULL,
    user_patronymic VARCHAR(100),
    user_type_id INTEGER REFERENCES User_Type (user_type_id)
);

CREATE TABLE IF NOT EXISTS Schedule (
    schedule_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    schedule_name VARCHAR(100) NOT NULL,
    id_user INTEGER REFERENCES Daily_User (user_id),
    UNIQUE (schedule_name, id_user)
);

CREATE TABLE IF NOT EXISTS Task (
    task_id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    task_name VARCHAR(100) NOT NULL,
    task_description VARCHAR(100) NOT NULL,
    task_deadline TIMESTAMP NOT NULL,
    task_status BOOLEAN NOT NULL,
    id_schedule INTEGER REFERENCES Schedule (schedule_id),
    UNIQUE (task_name, task_description, id_schedule)
);

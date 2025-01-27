ALTER SEQUENCE daily_user_user_id_seq RESTART WITH 1;
ALTER SEQUENCE task_task_id_seq RESTART WITH 1;
ALTER SEQUENCE schedule_schedule_id_seq RESTART WITH 1;
ALTER SEQUENCE user_type_user_type_id_seq RESTART WITH 1;
ALTER SEQUENCE task_type_task_type_id_seq RESTART WITH 1;
DROP TABLE task;
DROP TABLE schedule;
DROP TABLE user_notification;
DROP TABLE daily_user;
DROP TABLE user_type;
DROP TABLE task_type;



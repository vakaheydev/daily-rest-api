# REST API для управления задачами

Это REST API для управления задачами (приложение для списка дел). Оно позволяет создавать, читать, обновлять и удалять задачи, пользователей, типы пользователей, расписания и другое с помощью HTTP-запросов.

## Особенности

- **Управление задачами**: Добавление, редактирование, просмотр и удаление задач.
- **Планирование задач**: Привязка сроков и напоминаний к задачам.
- **Операции CRUD**: Создание, чтение, обновление и удаление сущностей через API.
- **Уведомления**: Управление и отправка уведомлений пользователям в Telegram.

## Проект с несколькими репозиториями

Проект состоит из четырёх связанных репозиториев:

1. **[REST API](https://github.com/vakaheydev/daily-rest-api)**  
   Репозиторий REST API, которое предоставляет функциональность для управления задачами (CRUD операции с задачами, пользователями и другими сущностями) и отправки уведомлений в [Телеграм бот](https://github.com/vakaheydev/daily-tgbot).

2. **[Client](https://github.com/vakaheydev/daily-rest-client)**  
   Репозиторий клиента для работы с [REST API](https://github.com/vakaheydev/daily-rest-api). Включает классы и методы для удобной работы с API через HTTP-запросы.

3. **[MVC](https://github.com/vakaheydev/daily-mvc)**  
   Репозиторий MVC сайта, использующий [клиент](https://github.com/vakaheydev/daily-rest-client) для взаимодействия с [REST API](https://github.com/vakaheydev/daily-rest-api). Предоставляет веб-интерфейс для управления задачами через браузер.

4. **[Telegram Bot](https://github.com/vakaheydev/daily-tgbot)**  
   Репозиторий Telegram-бота, который использует [клиент](https://github.com/vakaheydev/daily-rest-client) для работы с [REST API](https://github.com/vakaheydev/daily-rest-api). Позволяет пользователям управлять задачами и получать уведомления через Telegram.

## Установка

Проект контейнеризован с использованием Docker, что позволяет быстро и удобно осуществить запуск всех сервисов (REST API, MVC, Telegram-бот) локально.

## API Endpoints

Документация по использованию этого REST API включена в проект и доступна при запуске сервера по адресу: localhost:<port>/swagger-ui/index.html  
Корневой URL: /api

Ниже представлены некоторые из наиболее часто используемых эндпоинтов и примеры:

### Task

- **GET /task**  
  Получить список всех задач.
  
- **GET /task/{id}**  
 Получить задачу по ID.
  
- **POST /task**  
  Создать новую задачу.

- **PUT /task/{id}**  
  Обновить задачу по ID.
  
- **DELETE /task/{id}**  
  Удалить задачу по ID.

---

### User

- **GET /user**  
  Получить список всех пользователей.

- **GET /user/{id}**  
  Получить информацию о пользователе по ID.

- **GET /user/search**  
  Выполняет поиск пользователей по одному из критериев:
  - **user_type_name**: Список пользователей с заданным типом пользователя.
  - **login**: Пользователь с уникальным логином.
  - **tgId**: Пользователь с уникальным Telegram ID.
    
  Пример запроса:  
  `GET api/user/search?tgId=123456789`

- **POST /user**  
  Создать нового пользователя.

- **PUT /user/{id}**  
  Обновить информацию о пользователе по ID.

- **DELETE /user/{id}**  
  Удалить пользователя по ID.

---

### UserType

- **GET /user_type**  
  Получить список всех типов пользователей.

- **GET /user_type/{id}**  
  Получить тип пользователя по ID.

- **GET /user_type/search**  
  Выполняет поиск типов пользователей по одному из критериев:
  - **name**: Уникальный тип пользователя с заданным именем.
  
  Пример запроса:  
  `GET api/user_type/search?name=user`

- **POST /user_type**  
  Создать новый тип пользователя.

- **PUT /user_type/{id}**  
  Обновить тип пользователя по ID.

- **DELETE /user_type/{id}**  
  Удалить тип пользователя по ID.

---

### Schedule

- **GET /schedule**  
  Получить список всех расписаний.

- **GET /schedule/{id}**  
  Получить расписание по ID.

- **POST /schedule**  
  Создать новое расписание.

- **PUT /schedule/{id}**  
  Обновить расписание по ID.

- **DELETE /schedule/{id}**  
  Удалить расписание по ID.

---

### TaskType

- **GET /task_type**  
  Получить список всех типов задач.

- **GET /task_type/{id}**  
  Получить тип задачи по ID.

- **POST /task_type**  
  Создать новый тип задачи.

- **PUT /task_type/{id}**  
  Обновить тип задачи по ID.

- **DELETE /task_type/{id}**  
  Удалить тип задачи по ID.

### Требования для запуска приложения

- Docker
- Docker Compose

### Запуск приложения

Чтобы запустить все сервисы (REST API, клиент, MVC и Telegram-бот), выполните следующие команды:

Склонируйте этот репозиторий:
```sh
git clone https://github.com/vakaheydev/daily-rest-api
```
или просто скопируйте/скачайте файлы [docker-compose.yaml](https://github.com/vakaheydev/daily-rest-api/blob/master/docker/remote/docker-compose.yaml) и [.env.origin](https://github.com/vakaheydev/daily-rest-api/blob/master/docker/remote/.env.origin) из папки [docker/remote](https://github.com/vakaheydev/daily-rest-api/tree/master/docker/remote)

Перейдите в папку с докером:
```sh
cd ./docker/remote
```

В файле .env.origin исправьте ключ TG_BOT_TOKEN на токен Вашего телеграм бота  

Переименуйте .env.origin в .env
```sh
move .env.origin .env
```

Запустите все сервисы:
```sh
docker compose up --build -d 
```

🔥 Отлично! Все сервисы теперь должны быть запущены, проверить это можно с помощью команды:
```sh
docker ps
```

Для полного доступа ко всем возможностям [сайта](https://github.com/vakaheydev/daily-mvc) можете использовать следующую учётную запись:  
Login:
```sh
vaka
```
Password:
```sh
vaka123
```

## Дополнительная информация:

Если Вам понадобится пересоздать структуру БД, следуйте следующим инструкциям:  
Подключитесь к консоли контейнера db:
```sh
docker exec -it db bash
```

Выполните скрпт пересоздания структуры БД и заполнения её вводными данными:
```sh
sh re-create.sh
```

Выйдите из консоли контейнера:
```sh
exit
```
#
Чтобы запустить конкретный сервис, используйте команду ниже, заменив <service name> именем нужного сервиса (rest-api, mvc, tg-bot, etc.)
```sh
docker compose up --build -d <service name> 
```

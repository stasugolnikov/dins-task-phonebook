## Требования для запуска проекта
* Java 11
* PostgreSQL
* JUnit 5
* Apache Maven 3.8.1 


## Для инициализация базы данных
```
CREATE USER phonebook_user WITH PASSWORD '123654';
CREATE DATABASE phonebook;
```
Или настроить самостоятельно данные конфигурации БД в файле src/main/resources/application.properties и выполнить аналогичные команды.

## Запуск
```
git clone https://github.com/stasugolnikov/dins-task-phonebook.git
```
#### Для UNIX-подобных ОС
Для запуска приложения:
```
./mvnw spring-boot:run
```
Для запуска тестов:
```
./mvnw test
```

#### Для Windows ОС
Для запуска приложения:
```
./mvnw.cmd spring-boot:run
```
Для запуска тестов:
```
./mvnw.cmd test
```

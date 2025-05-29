# Goodreads Parser Service

Этот проект представляет собой **Spring Boot**-приложение для асинхронного парсинга книг с сайта [Goodreads](https://www.goodreads.com/list/show/1.Best_Books_Ever) и сохранения данных в PostgreSQL.

---

## 📌 Описание

Приложение запускает многопоточный веб-скрейпер на основе **Selenium WebDriver**, извлекает информацию о книгах (название, автор, рейтинг, количество оценок, ссылка) и сохраняет в базу данных через **Spring Data JPA**.

---

## 🧰 Технологии

- **Java 21**
- **Spring Boot 3.5**
  - `spring-boot-starter-web`
  - `spring-boot-starter-data-jpa`
  - `spring-boot-starter-jdbc`
- **PostgreSQL 15**
- **Docker + Docker Compose**
- **Selenium 4 (ChromeDriver)**
- **WebDriverManager**
- **Liquibase**
- **Lombok**
- **Jsoup (для доп. парсинга)**
- **JUnit 5 (тесты)**


Результаты работы:
<img width="1560" alt="image" src="https://github.com/user-attachments/assets/29e9c3b5-b9c3-4f9f-8429-980d50f568ac" />

**От старта парсинга до окончания парсинага 50 страниц - 53 секунды данных 5000 записей в БД**
<img width="1592" alt="Снимок экрана 2025-05-29 в 17 07 08" src="https://github.com/user-attachments/assets/61e57ee8-2ae0-4ba7-bc47-5a6e17bcca60" />
<img width="1580" alt="Снимок экрана 2025-05-29 в 17 06 49" src="https://github.com/user-attachments/assets/d5bd7e73-90b3-4afd-a170-da4d1a61ed2c" />



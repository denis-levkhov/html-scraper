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

CREATE TABLE book (
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      author VARCHAR(255),
                      rating VARCHAR(10),
                      votes VARCHAR(50),
                      page_url TEXT
);
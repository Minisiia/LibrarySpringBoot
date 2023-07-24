CREATE DATABASE library_springboot;
USE library_springboot;
DROP TABLE book;
DROP TABLE person;

CREATE TABLE person (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255) NOT NULL,
                        year INT
);

-- Создание таблицы для хранения информации о книгах
CREATE TABLE book (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      title VARCHAR(100) NOT NULL,
                      author VARCHAR(30) NOT NULL,
                      year INT,
                      person_id INT,
                      FOREIGN KEY (person_id) REFERENCES person(id) -- Ограничение внешнего ключа
);

-- Добавление человека
INSERT INTO person (id, name, year) VALUES
                                        (1, 'John Smith', 1985),
                                        (2, 'Alice Johnson', 1990),
                                        (3, 'Michael Brown', 1982),
                                        (4, 'Emily Davis', 1988),
                                        (5, 'Christopher Wilson', 1995),
                                        (6, 'Sophia Martinez', 1980);

-- Добавление книг и привязка их к человеку по person_id
INSERT INTO book (id, title, author, year, person_id) VALUES
                                                          (101, 'Book', 'Jane One', 2000, 1),
                                                          (102, 'Two', 'Scott Two', 2010, 1),
                                                          (103, 'The Book of Secrets', 'John Doe', 2005, 2),
                                                          (104, 'The Power of Now', 'Eckhart Tolle', 1997, 2),
                                                          (105, 'Harry Potter and the Sorcerer\'s Stone', 'J.K. Rowling', 1997, 2),
                                                          (106, '1984', 'George Orwell', 1949, 1),
                                                          (107, 'Pride and Prejudice', 'Jane Austen', 1813, 4),
                                                          (108, 'To Kill a Mockingbird', 'Harper Lee', 1960, 4),
                                                          (109, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 5),
                                                          (110, 'The Catcher in the Rye', 'J.D. Salinger', 1951, 5),
                                                          (111, 'The Lord of the Rings', 'J.R.R. Tolkien', 1954, 6),
                                                          (112, 'The Hobbit', 'J.R.R. Tolkien', 1937, 6);
CREATE DATABASE library_springboot;
USE library_springboot;
DROP TABLE book;
DROP TABLE security;
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
                      taken_at DATETIME,
                      returned_at DATETIME,
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

CREATE TABLE security (
                          id int AUTO_INCREMENT PRIMARY KEY,
                          username varchar(30) NOT NULL,
                          password varchar(255),
                          role VARCHAR(30),
                          person_id INT,
                          FOREIGN KEY (person_id) REFERENCES person(id)
);

INSERT INTO security (id, username, password,role,person_id) VALUES
                                        (1, 'John1985','$2a$10$tFJYWqGx0wfC93EzZRLd3ekMa/XYh9cfWtWgngnONWfUR/8OEZPe2','ROLE_USER',1),
                                        (2, 'Alice','$2a$10$YRzlKB8gIkcbp4xlNFgI0.33bq7tGQBByfZe9QUyhrs.XZ9lhWyc6','ROLE_USER',2),
                                        (3, 'M82','$2a$10$U6o79gmmBoasig8CkKlhAOC7V.pqIfYpeBQUctnJfoW52r2rjF3XG','ROLE_USER',3),
                                        (4, 'Emi88','$2a$10$EcVcqNqQOcHB9RiG1/1ruezVl2At.wx0IIyx9vYQn0VKbvXCYetXy','ROLE_USER',4),
                                        (5, 'Ch95','$2a$10$C9QHx1R9oTH7Z2hDxic..u61gkZbDLAITaoPWIW6A.TM450JWAtJK','ROLE_USER',5),
                                        (6, 'Sophia','$2a$10$SRoLLW8rP7DkqW93UhoyQuO7Mo2yuuNHpfPl1Sbnog3eU072ssZsm','ROLE_USER',6),
                                        (7, 'admin','$2a$10$7YarVioOE6HgmGKmyvIUV.38UB7IAN4x9r70InagocTvKhtTZZTJK','ROLE_ADMIN',7);


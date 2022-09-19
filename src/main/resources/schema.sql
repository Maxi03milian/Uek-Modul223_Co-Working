DROP TABLE IF EXISTS USER CASCADE;
CREATE TABLE USER
(
    id int,
    name VARCHAR(50) NOT NULL,
    lastname VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS BOOKING CASCADE;
CREATE TABLE BOOKING
(
    id int,
    user int NOT NULL,
    day_duration int NOT NULL,
    date date NOT NULL,
    status VARCHAR(50) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (category) REFERENCES CATEGORY (id)
);

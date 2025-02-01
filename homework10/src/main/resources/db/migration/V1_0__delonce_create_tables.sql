CREATE TABLE IF NOT EXISTS Products (
    id SERIAL PRIMARY KEY,
    article VARCHAR(200) NOT NULL,
    amount INT,
    total FLOAT
);

INSERT INTO Products (article, amount, total)
VALUES ('12345', 10, 100.0);

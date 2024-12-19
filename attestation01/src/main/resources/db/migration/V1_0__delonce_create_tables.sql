CREATE TABLE IF NOT EXISTS Authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_date DATE
);

CREATE TABLE IF NOT EXISTS Categories (
    id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    fk_author_id INT REFERENCES Authors(id),
    fk_category_id INT REFERENCES Categories(id),
    published_date DATE
);

CREATE TABLE IF NOT EXISTS Members (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    membership_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Loans (
    id SERIAL PRIMARY KEY,
    fk_book_id INT REFERENCES Books(id),
    fk_member_id INT REFERENCES Members(id),
    loan_date DATE NOT NULL,
    return_date DATE
);
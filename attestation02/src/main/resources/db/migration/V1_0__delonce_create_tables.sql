CREATE TABLE IF NOT EXISTS Authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birth_date DATE
);

COMMENT ON TABLE Authors IS 'Таблица, содержащая информацию об авторах';
COMMENT ON COLUMN Authors.id IS 'Уникальный идентификатор автора';
COMMENT ON COLUMN Authors.name IS 'Имя автора';
COMMENT ON COLUMN Authors.birth_date IS 'Дата рождения автора';

CREATE TABLE IF NOT EXISTS Categories (
    id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL
);

COMMENT ON TABLE Categories IS 'Таблица, содержащая категории книг';
COMMENT ON COLUMN Categories.id IS 'Уникальный идентификатор категории';
COMMENT ON COLUMN Categories.category_name IS 'Название категории';

CREATE TABLE IF NOT EXISTS Books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    fk_author_id INT REFERENCES Authors(id),
    fk_category_id INT REFERENCES Categories(id),
    published_date DATE
);

COMMENT ON TABLE Books IS 'Таблица, содержащая информацию о книгах';
COMMENT ON COLUMN Books.id IS 'Уникальный идентификатор книги';
COMMENT ON COLUMN Books.title IS 'Название книги';
COMMENT ON COLUMN Books.fk_author_id IS 'Идентификатор автора книги';
COMMENT ON COLUMN Books.fk_category_id IS 'Идентификатор категории книги';
COMMENT ON COLUMN Books.published_date IS 'Дата публикации книги';

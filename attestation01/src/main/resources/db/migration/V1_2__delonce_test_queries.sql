-- 1. Получение всех книг с информацией об авторах и категориях
SELECT 
    b.title AS book_title,
    a.name AS author_name,
    c.category_name,
    b.published_date
FROM 
    Books b
JOIN 
    Authors a ON b.fk_author_id = a.id
JOIN 
    Categories c ON b.fk_category_id = c.id;

-- 2. Количество книг, написанных каждым автором
SELECT 
    a.name AS author_name,
    COUNT(b.id) AS book_count
FROM 
    Authors a
LEFT JOIN 
    Books b ON a.id = b.fk_author_id
GROUP BY 
    a.name;

-- 3. Члены, которые взяли книги в определенный период
SELECT 
    m.full_name,
    COUNT(l.id) AS loans_count
FROM 
    Members m
JOIN 
    Loans l ON m.id = l.fk_member_id
WHERE 
    l.loan_date BETWEEN '2023-01-01' AND '2023-06-30'
GROUP BY 
    m.full_name;

-- 4. Книги, которые были возвращены позже срока
SELECT 
    b.title AS book_title,
    m.full_name AS member_name,
    l.loan_date,
    l.return_date
FROM 
    Loans l
JOIN 
    Books b ON l.fk_book_id = b.id
JOIN 
    Members m ON l.fk_member_id = m.id
WHERE 
    l.return_date > l.loan_date + INTERVAL '15 days';

-- 5. Список категорий и количества книг в каждой категории
SELECT 
    c.category_name,
    COUNT(b.id) AS book_count
FROM 
    Categories c
LEFT JOIN 
    Books b ON c.id = b.fk_category_id
GROUP BY 
    c.category_name;

-- 6. Авторы, которые не имеют книг
SELECT 
    a.name AS author_name
FROM 
    Authors a
LEFT JOIN 
    Books b ON a.id = b.fk_author_id
WHERE 
    b.id IS NULL;

-- 7. Самый старый автор и его дата рождения
SELECT 
    a.name AS author_name,
    a.birth_date
FROM 
    Authors a
ORDER BY 
    a.birth_date ASC
LIMIT 1;

-- 8. Книги, опубликованные после 1900 года, и их авторы
SELECT 
    b.title AS book_title,
    a.name AS author_name,
    b.published_date
FROM 
    Books b
JOIN 
    Authors a ON b.fk_author_id = a.id
WHERE 
    b.published_date > '1900-01-01';

-- 9. Члены, которые взяли больше всего книг
SELECT 
    m.full_name,
    COUNT(l.id) AS loans_count
FROM 
    Members m
JOIN 
    Loans l ON m.id = l.fk_member_id
GROUP BY 
    m.full_name
ORDER BY 
    loans_count DESC
LIMIT 1;

-- 10. Книги, у которых нет записей о выдаче
SELECT 
    b.title AS book_title
FROM 
    Books b
LEFT JOIN 
    Loans l ON b.id = l.fk_book_id
WHERE 
    l.id IS NULL;
INSERT INTO users VALUES ('user', crypt('userpass', gen_salt('bf')), ARRAY['USER']),
                         ('admin', crypt('adminpass', gen_salt('bf')), ARRAY['ADMIN']),
                         ('viewer', crypt('viewerpass', gen_salt('bf')), ARRAY['VIEWER']);

INSERT INTO tasks VALUES (default, 'Сходить в магазин', 'Список покупок: молоко, сыр, хлеб, варенье.', '2025-03-25T14:02:33'),
                         (default, 'Игра престолов', 'К следующему понедельнику просмотреть все сезоны Игры Престолов!', '2025-03-24T22:17:05'),
                         (default, 'Инноплоис', 'Сделать домашнее задание по Spring Security!', '2025-03-23T18:19:49');
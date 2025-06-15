/* --------------------- Таблицы рабочего журнала ------------------- */

CREATE TABLE IF NOT EXISTS working_logs_table (
    id SERIAL NOT NULL PRIMARY KEY,
    driver_phone VARCHAR(20) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NULL
);

COMMENT ON TABLE working_logs_table IS 'Таблица с данным о рабочих сменах';
COMMENT ON COLUMN working_logs_table.id IS 'Идентификатор смены';
COMMENT ON COLUMN working_logs_table.driver_phone IS 'Номер телефона водителя';
COMMENT ON COLUMN working_logs_table.start_time IS 'Время начала смены';
COMMENT ON COLUMN working_logs_table.end_time IS 'Время конца смены';
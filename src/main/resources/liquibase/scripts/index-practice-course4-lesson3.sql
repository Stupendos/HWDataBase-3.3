--liquibase formatted sql

-- changeset Stupendos:1
CREATE INDEX IF NOT EXISTS user_name_index ON student (name);

-- changeset Stupendos:2
CREATE INDEX IF NOT EXISTS user_id_index ON student (id);

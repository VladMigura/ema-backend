CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "citext";

CREATE TYPE USER_ROLE AS ENUM ('DEVELOPER', 'MANAGER', 'ADMIN');

CREATE TABLE ema_user
(
    id            UUID        DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT ema_user_pkey PRIMARY KEY,
    first_name    TEXT                                   NOT NULL,
    last_name     TEXT                                   NOT NULL,
    email         CITEXT                                 NOT NULL
        CONSTRAINT ema_user_email_unique UNIQUE,
    phone_number  TEXT,
    title         TEXT                                   NOT NULL,
    role          USER_ROLE                              NOT NULL,
    password_hash TEXT                                   NOT NULL,
    created_at    TIMESTAMPTZ DEFAULT now()              NOT NULL,
    updated_at    TIMESTAMPTZ,
    deleted_at    TIMESTAMPTZ
);

-- Password: Admin
INSERT INTO ema_user(first_name, last_name, email, title, role, password_hash)
VALUES ('Admin', 'Admin', 'admin@domain.com', 'Admin', 'ADMIN', '$2y$10$rgFMN2DxgTBwUBDrTuefaOtueae3Gvsn3FDsSf6BYnlpqConVY/Y.')

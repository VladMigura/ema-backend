CREATE TYPE PROJECT_STATUS AS ENUM ('PENDING', 'IN_PROGRESS', 'SUPPORT', 'COMPLETED');

CREATE TABLE project
(
    id                  UUID           DEFAULT uuid_generate_v4()                NOT NULL
        CONSTRAINT project_id_pkey PRIMARY KEY,
    name                TEXT                                                     NOT NULL,
    customer_name       TEXT                                                     NOT NULL,
    status              PROJECT_STATUS DEFAULT CAST('PENDING' AS PROJECT_STATUS) NOT NULL,
    estimation_in_weeks INTEGER        DEFAULT 0                                 NOT NULL,
    description         TEXT                                                     NOT NULL,
    manager_id          UUID                                                     NOT NULL
        CONSTRAINT project_manager_id_fkey REFERENCES ema_user,
    scrum_master_id     UUID                                                     NOT NULL
        CONSTRAINT project_scrum_master_id_fkey REFERENCES ema_user,
    created_at          TIMESTAMPTZ    DEFAULT now()                             NOT NULL,
    updated_at          TIMESTAMPTZ,
    deleted_at          TIMESTAMPTZ,
    CONSTRAINT project_name_deleted_at_unique UNIQUE (name, deleted_at)
);

CREATE TABLE project_user
(
    id         UUID        DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT project_user_pkey PRIMARY KEY,
    project_id UUID                                   NOT NULL
        CONSTRAINT project_user_project_id_fkey REFERENCES project,
    user_id    UUID                                   NOT NULL
        CONSTRAINT project_user_user_id_fkey REFERENCES ema_user,
    added_at   TIMESTAMPTZ DEFAULT now()              NOT NULL,
    CONSTRAINT project_user_project_id_user_id_unique UNIQUE (project_id, user_id)
);
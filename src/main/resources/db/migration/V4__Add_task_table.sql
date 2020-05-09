CREATE TYPE TASK_TYPE AS ENUM ('BUG', 'FEATURE', 'INVESTIGATION', 'DESIGN', 'IMPLEMENTATION', 'FIX');
CREATE TYPE TASK_PRIORITY AS ENUM ('LOW', 'MEDIUM', 'HIGH');
CREATE TYPE TASK_STATUS AS ENUM ('PENDING', 'IN_PROGRESS', 'QA_REVIEW', 'COMPLETED', 'ACCEPTED');

CREATE TABLE task
(
    id                   UUID        DEFAULT uuid_generate_v4()             NOT NULL
        CONSTRAINT task_id_pkey PRIMARY KEY,
    title                TEXT                                               NOT NULL,
    description          TEXT                                               NOT NULL,
    type                 TASK_TYPE                                          NOT NULL,
    priority             TASK_PRIORITY                                      NOT NULL,
    status               TASK_STATUS DEFAULT CAST('PENDING' AS TASK_STATUS) NOT NULL,
    project_id           UUID                                               NOT NULL
        CONSTRAINT task_project_id_fkey REFERENCES project,
    dev_owner_id         UUID                                               NOT NULL
        CONSTRAINT task_dev_ownew_id_fkey REFERENCES ema_user,
    estimation_in_hours  INTEGER     DEFAULT 0                              NOT NULL,
    estimation_in_points INTEGER     DEFAULT 1                              NOT NULL
        CONSTRAINT task_estimation_in_points_check CHECK ( estimation_in_points >= 1 AND estimation_in_points <= 5 ),
    created_by           UUID                                               NOT NULL
        CONSTRAINT task_created_by_fkey REFERENCES ema_user,
    created_at           TIMESTAMPTZ DEFAULT now()                          NOT NULL,
    updated_at           TIMESTAMPTZ,
    deleted_at           TIMESTAMPTZ
);
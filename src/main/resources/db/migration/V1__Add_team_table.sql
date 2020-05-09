CREATE TABLE team
(
    id          UUID        DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT team_pkey PRIMARY KEY,
    name        TEXT                                   NOT NULL,
    description TEXT                                   NOT NULL,
    manager_id  UUID                                   NOT NULL
        CONSTRAINT team_manager_id_fkey REFERENCES ema_user,
    created_at  TIMESTAMPTZ DEFAULT now()              NOT NULL,
    updated_at  TIMESTAMPTZ,
    deleted_at  TIMESTAMPTZ,
    CONSTRAINT team_name_deleted_at_unique UNIQUE (name, deleted_at)
);

CREATE TABLE team_user
(
    id       UUID        DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT team_user_pkey PRIMARY KEY,
    team_id  UUID                                   NOT NULL
        CONSTRAINT team_user_team_id_fkey REFERENCES team,
    user_id  UUID                                   NOT NULL
        CONSTRAINT team_user_user_id_fkey REFERENCES ema_user,
    added_at TIMESTAMPTZ DEFAULT now()              NOT NULL,
    CONSTRAINT team_user_team_id_user_id_unique UNIQUE (team_id, user_id)
);
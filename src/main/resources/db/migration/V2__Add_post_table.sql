CREATE TABLE post
(
    id          UUID        DEFAULT uuid_generate_v4() NOT NULL
        CONSTRAINT post_id_pkey PRIMARY KEY,
    author_id   UUID                                   NOT NULL
        CONSTRAINT post_author_id_fkey REFERENCES ema_user,
    title       TEXT                                   NOT NULL,
    description TEXT                                   NOT NULL,
    created_at  TIMESTAMPTZ DEFAULT now()              NOT NULL,
    updated_at  TIMESTAMPTZ,
    deleted_at  TIMESTAMPTZ
);
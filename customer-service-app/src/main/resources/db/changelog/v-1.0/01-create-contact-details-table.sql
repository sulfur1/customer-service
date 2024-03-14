CREATE TABLE IF NOT EXISTS contact_details
(
    id          bigint                      NOT NULL,
    email       varchar(255)                NOT NULL,
    telegram_id varchar(255)                NOT NULL,
    created_at  timestamp with time zone    NOT NULL,
    updated_at  timestamp with time zone    NOT NULL,
    CONSTRAINT contact_details_pkey PRIMARY KEY (id)
)

GO

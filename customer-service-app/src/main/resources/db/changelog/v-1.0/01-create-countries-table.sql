CREATE TABLE IF NOT EXISTS countries
(
    id              bigint                      NOT NULL,
    country_code    varchar(3)                NOT NULL,
    name            varchar(30)                NOT NULL,
    created_at      timestamp with time zone    NOT NULL,
    updated_at      timestamp with time zone    NOT NULL,
    CONSTRAINT countries_pkey PRIMARY KEY (id)
)

GO

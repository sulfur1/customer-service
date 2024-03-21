CREATE SEQUENCE my_sequence START 1;

GO

CREATE TABLE IF NOT EXISTS countries
(
    id              bigint DEFAULT nextval('my_sequence'::regclass) NOT NULL,
    country_code    varchar(3)                  NOT NULL,
    name            varchar(100)                NOT NULL,
    created_at      timestamp with time zone    NOT NULL DEFAULT NOW(),
    updated_at      timestamp with time zone    NOT NULL DEFAULT NOW(),
    CONSTRAINT countries_pkey   PRIMARY KEY (id)
)

GO

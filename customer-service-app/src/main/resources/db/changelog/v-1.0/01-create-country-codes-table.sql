CREATE TABLE IF NOT EXISTS country_codes
(
    code            varchar(3)                  NOT NULL,
    name            varchar(255)                NOT NULL,
    created_at      timestamp with time zone    NOT NULL DEFAULT NOW(),
    updated_at      timestamp with time zone    NOT NULL DEFAULT NOW(),
    CONSTRAINT county_codes_pkey PRIMARY KEY (code)
)

GO

CREATE TABLE IF NOT EXISTS countries
(
    id              bigint                      NOT NULL,
    country_code    varchar(3)                  NOT NULL,
    name            varchar(30)                 NOT NULL,
    created_at      timestamp with time zone    NOT NULL,
    updated_at      timestamp with time zone    NOT NULL,
    CONSTRAINT countries_pkey   PRIMARY KEY (id),
    CONSTRAINT fk_country_code  FOREIGN KEY(country_code)
            REFERENCES country_codes(code)
)

GO

CREATE INDEX  IF NOT EXISTS countries_country_code_idx ON countries (country_code);

GO

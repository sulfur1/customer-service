CREATE TABLE IF NOT EXISTS customers
(
    id                  bigint      NOT NULL,
    name                varchar(30) NOT NULL,
    surname             varchar(30) NOT NULL,
    country_id          bigint      NOT NULL,
    contact_details_id  bigint      NOT NULL,
    created_at          timestamp with time zone NOT NULL,
    updated_at          timestamp with time zone NOT NULL,
    CONSTRAINT customers_pkey PRIMARY KEY (id),
    CONSTRAINT fk_contact_details_id FOREIGN KEY(contact_details_id)
        REFERENCES contact_details(id),
    CONSTRAINT fk_country_id        FOREIGN KEY(country_id)
        REFERENCES countries(id)
)

GO

CREATE INDEX IF NOT EXISTS customers_country_id_idx ON customers (country_id);

GO

CREATE INDEX IF NOT EXISTS customers_contact_details_id_idx ON customers (contact_details_id);

GO

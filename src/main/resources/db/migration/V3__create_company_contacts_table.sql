CREATE TABLE company_contacts
(
    id           BIGSERIAL PRIMARY KEY,
    company_id   BIGINT NOT NULL REFERENCES companies,
    first_name   VARCHAR(255),
    last_name    VARCHAR(255),
    phone_number VARCHAR(50),
    email        VARCHAR(255)
);

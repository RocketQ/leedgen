CREATE TABLE IF NOT EXISTS "user".user_info(
    id uuid not null,
    phone_number varchar,
    mail varchar not null,
    sex varchar not null,
    first_name varchar not null
)
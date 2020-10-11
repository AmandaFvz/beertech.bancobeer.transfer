
create table if not exists CURRENT_ACCOUNT (
    ID numeric not null PRIMARY KEY,
    HASH_VALUE varchar(60) UNIQUE,
    BALANCE numeric null
);

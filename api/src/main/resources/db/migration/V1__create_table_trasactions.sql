
create table if not exists TRANSACTION (
    ID numeric not null PRIMARY KEY,
    OPERATION int null,
    VALUE_TRANSACTION numeric null,
    DATA_TRANSACTION TIMESTAMP not null
);

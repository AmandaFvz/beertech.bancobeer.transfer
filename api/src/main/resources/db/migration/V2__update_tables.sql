
create table if not exists CURRENT_ACCOUNT (
    ID numeric not null PRIMARY KEY,
    HASH_VALUE varchar(60) UNIQUE,
    BALANCE numeric null
);

alter table TRANSACTION
add column DESTINATION_HASH_VALUE varchar(60),
add column ACCOUNT_HASH_VALUE varchar(60),
add CONSTRAINT fk_account
    FOREIGN KEY (ACCOUNT_HASH_VALUE)
    REFERENCES CURRENT_ACCOUNT(HASH_VALUE);

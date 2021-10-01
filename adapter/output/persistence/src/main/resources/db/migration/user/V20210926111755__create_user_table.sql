create table "user"
(
    id                   serial       not null primary key,
    email                varchar(256) not null unique,
    registered_date_time timestamp    not null,
    is_deleted           boolean      not null,
    password_hash        varchar(256) not null
);
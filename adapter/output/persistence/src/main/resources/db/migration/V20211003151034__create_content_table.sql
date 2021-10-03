create table content
(
    id        serial       not null primary key,
    file_name varchar(256) not null,
    file_type varchar(256) not null,
    data      bytea        not null
);
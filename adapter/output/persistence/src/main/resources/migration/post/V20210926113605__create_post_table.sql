create table post
(
    id               serial        not null primary key,
    description      varchar(2200) not null,
    owner_id         integer       not null,
    create_date_time timestamp     not null,
    is_deleted       boolean,
    constraint owner_id_fkey foreign key (owner_id) references "user" (id)
);
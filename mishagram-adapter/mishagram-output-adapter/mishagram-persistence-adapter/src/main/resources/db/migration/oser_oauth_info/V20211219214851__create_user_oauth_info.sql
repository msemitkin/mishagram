create table user_oauth_info
(
    id             serial  not null primary key,
    user_id        integer not null,
    oauth_id       varchar not null unique,
    oauth_provider varchar not null,
    constraint user_id_fkey foreign key (user_id) references "user" (id)
);
create table subscription
(
    id             serial  not null primary key,
    user_id        integer not null,
    target_user_id integer not null,
    constraint user_id_fkey foreign key (user_id) references "user" (id),
    constraint target_user_id_fkey foreign key (target_user_id) references "user" (id),
    constraint unique_pair unique (user_id, target_user_id)
);
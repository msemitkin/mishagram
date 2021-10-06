create table favourite_post
(
    id      serial  not null primary key,
    post_id integer not null,
    user_id integer not null,
    constraint post_id_fkey foreign key (post_id) references post (id),
    constraint user_id_fkey foreign key (user_id) references "user" (id),
    constraint favourite_post_unique_pair unique (post_id, user_id)
);
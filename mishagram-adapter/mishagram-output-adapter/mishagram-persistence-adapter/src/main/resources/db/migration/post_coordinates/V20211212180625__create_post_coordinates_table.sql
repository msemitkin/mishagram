create table post_coordinates
(
    id        serial  not null primary key,
    post_id   integer not null,
    longitude real  not null,
    latitude  real  not null,
    constraint post_id_fkey foreign key (post_id) references post (id)
);
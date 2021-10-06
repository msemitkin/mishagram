alter table post
    add content_id integer not null default -1,
    add constraint content_id_fkey foreign key (content_id) references content (id);
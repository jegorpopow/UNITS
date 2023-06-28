create sequence progress_seq start with 1 increment by 50;

create table progress
(
    id       bigint  not null,
    solved_easy     integer not null,
    solved_medium   integer not null,
    solved_hard     integer not null,
    total_easy     integer not null,
    total_medium   integer not null,
    total_hard     integer not null,
    tag_id   bigint,
    user_uid bigint,
    primary key (id)
);

alter table if exists progress
    add constraint tag_by_progress foreign key (tag_id) references task_tags;

-- alter table if exists percentage_of_progress
--     add constraint FK36o1y0bg8fe869aqtjwj96pe8 foreign key (user_uid) references users;
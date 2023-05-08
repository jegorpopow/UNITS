create sequence answers_seq start with 1 increment by 50;

create table answers
(
    id        bigint  not null,
    answer    varchar(255),
    result    boolean not null,
    timestamp timestamp(6),
    task_id   bigint,
    user_uid  bigint,
    primary key (id)
);

alter table if exists answers add constraint task_by_answer foreign key (task_id) references task;
alter table if exists answers add constraint author_by_answer foreign key (user_uid) references users;



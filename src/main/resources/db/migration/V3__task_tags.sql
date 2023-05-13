create sequence task_tags_seq start with 1 increment by 50;

create table task_tags
(
    id   bigint not null,
    info varchar(255),
    name varchar(255),
    primary key (id)
);

create table tag_by_task
(
    task_id bigint not null,
    tag_id  bigint not null,
    primary key (task_id, tag_id)
);

alter table if exists tag_by_task
    add constraint tags_are_real foreign key (tag_id) references task_tags;

alter table if exists tag_by_task
    add constraint tasks_are_real foreign key (task_id) references task;
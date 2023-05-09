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

alter table if exists answers
    add constraint task_by_answer foreign key (task_id) references task;
alter table if exists answers
    add constraint author_by_answer foreign key (user_uid) references users;

create sequence form_responses_seq start with 1 increment by 50;

create table form_responses
(
    id        bigint not null,
    timestamp timestamp(6),
    form_id   bigint,
    user_uid  bigint,
    primary key (id)
);

create table answers_by_response
(
    response_id bigint not null,
    answer_id   bigint not null
);

alter table if exists answers_by_response
    add constraint answers_are_real foreign key (answer_id) references answers;
alter table if exists answers_by_response
    add constraint responses_are_real foreign key (response_id) references form_responses;

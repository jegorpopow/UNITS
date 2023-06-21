-- noinspection SqlNoDataSourceInspectionForFile
create sequence forms_seq start with 1 increment by 50;

create table form_contains
(
    form_id bigint not null,
    task_id bigint not null
);

create table forms
(
    id       bigint  not null,
    creator_id  bigint,
    info     varchar(255),
    name     varchar(255),
    shuffled boolean not null,
    primary key (id)
);

alter table if exists form_contains
    add constraint form_contains_tasks foreign key (task_id) references task;
alter table if exists form_contains
    add constraint tasks_contained_by_forms foreign key (form_id) references forms;




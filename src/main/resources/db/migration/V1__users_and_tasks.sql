-- noinspection SqlNoDataSourceInspectionForFile

create sequence task_seq start with 1 increment by 50;

create sequence users_seq start with 1 increment by 50;

create table task
(
    id         bigint  not null,
    answer     varchar(255),
    author     bigint  not null,
    body       varchar(255),
    standalone boolean not null,
    checkable  boolean not null,
    title      varchar(255),
    task_type  varchar(255),
    correct_choice integer,
    correct_choices varchar(255),
    options varchar(255),
    primary key (id)
);

create table users
(
    uid             bigint not null,
    info            varchar(255),
    name            varchar(255),
    first_name      varchar(255),
    last_name       varchar(255),
    email           varchar(255),
    activation_code varchar(255),
    password        varchar(255),
    available_forms varchar(1024),
    primary key (uid)
);

alter table if exists users
    add constraint users_unique_names unique (name);

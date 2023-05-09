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
    primary key (id)
);

create table users
(
    uid   bigint not null,
    info  varchar(255),
    name  varchar(255),
    email varchar(255),
    primary key (uid)
);

alter table if exists users
    add constraint users_unique_names unique (name);


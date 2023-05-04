-- noinspection SqlNoDataSourceInspectionForFile

create sequence forms_seq start with 1 increment by 50;

create table form_contains
(
    id    bigint not null,
    tasks bigint
);

create table forms
(
    id      bigint not null,
    creator bigint,
    info    varchar(255),
    name    varchar(255),
    primary key (id)
);
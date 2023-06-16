-- noinspection SqlNoDataSourceInspectionForFile

create sequence token_seq start with 1 increment by 50;

create table token
(
    id         bigint  not null,
    token      varchar(255),
    token_type varchar(255),
    revoked    integer,
    expired    integer not null,
    user_id    bigint  not null
);

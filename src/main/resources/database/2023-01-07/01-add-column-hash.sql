--liquibase formatted sql
--changeset mzielinski:23

alter table users add hash varchar(120);

--changeset mzielinski:24
alter table users add hash_date datetime;

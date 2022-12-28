--liquibase formatted sql
--changeset mzielinski:9

alter table review add moderated boolean default false;

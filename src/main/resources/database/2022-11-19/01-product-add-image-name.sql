--liquibase formatted sql
--changeset rzielinski:2

alter table product add image varchar(128) after currency;

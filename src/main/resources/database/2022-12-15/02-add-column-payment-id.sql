--liquibase formatted sql
--changeset mzielinski:15

alter table `order` ADD payment_id bigint;

update `order` set payment_id = 1;

alter table `order` MODIFY payment_id bigint not null;



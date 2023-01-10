--liquibase formatted sql
--changeset mzielinski:24

delete from category where id = 1;

--liquibase formatted sql
--changeset mzielinski:17

create table users(
    id bigint not null auto_increment PRIMARY KEY ,
    username varchar(50) not null UNIQUE,
    password varchar(500) not null,
    enabled boolean not null
);

--changeset mzielinski:18
create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);


--changeset mzielinski:19
create unique index ix_auth_username on authorities (username,authority);

--changeset mzielinski:20

INSERT INTO users(id, username, password, enabled)
values ( 1, 'admin', '{bcrypt}$2a$10$upzXFsFUOClFRR69OMKF8eajGMRs0vhcSHqvNDKy9yfW45w7o9z6O', true);
INSERT INTO authorities (username, authority) values ( 'admin', 'ROLE_ADMIN');

drop table persistent_logins;
drop table group_members;
drop table group_authorities;
drop table groups;
drop table authorities;
drop table users;

create table users (
    username varchar(50) not null primary key,
    password varchar(100) not null,
    first_name varchar(80) not null,
    last_name varchar(80) not null,
    middle_name varchar(80) not null,
    enabled tinyint(1) not null,
    EMAIL VARCHAR(100)
) engine = InnoDb;

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    foreign key (username) references users (username),
    unique index authorities_idx_1 (username, authority)
) engine = InnoDb;


create table groups (
    id bigint unsigned not null auto_increment primary key,
    group_name varchar(50) not null
) engine = InnoDb;

create table group_authorities (
    group_id bigint unsigned not null,
    authority varchar(50) not null,
    foreign key (group_id) references groups (id)
) engine = InnoDb;

create table group_members (
    id bigint unsigned not null auto_increment primary key,
    username varchar(50) not null,
    group_id bigint unsigned not null,
    foreign key (group_id) references groups (id)
) engine = InnoDb;


create table persistent_logins (
    username varchar(64) not null,
    series varchar(64) primary key,
    token varchar(64) not null,
    last_used timestamp not null
) engine = InnoDb;
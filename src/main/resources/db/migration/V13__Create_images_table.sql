create table images
(
    id   bigint auto_increment
        primary key,
    name varchar(256),
    data mediumblob
);
create table comment
(
    id           BIGINT auto_increment
        primary key not null,
    parent_id    BIGINT not null,
    type         int not null,
    commentator  int    not null,
    gmt_create   BIGINT not null,
    gmt_modified BIGINT not null,
    like_count   BIGINT default 0
);
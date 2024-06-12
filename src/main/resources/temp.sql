CREATE SEQUENCE sequence_auto_inc START WITH 1 INCREMENT BY 1;

alter table QUESTION alter column ID SET DEFAULT NEXT VALUE FOR sequence_auto_inc;

alter table QUESTION alter column ID BIGINT default (NEXT VALUE FOR sequence_auto_inc) NOT NULL auto_increment;

CREATE TABLE users (
    id INT DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_XXXX),
    name VARCHAR(255),
    email VARCHAR(255),
    PRIMARY KEY (id)
);



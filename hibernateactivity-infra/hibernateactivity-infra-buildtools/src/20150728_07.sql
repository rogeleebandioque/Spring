CREATE TABLE USERS(
    id int not null,
    username text not null,
    password text not null,
    role_name text not null CHECK(role_name ='ROLE_ADMIN' OR role_name= 'ROLE_USER'),
    PRIMARY KEY (username)
);

CREATE SEQUENCE user_generator START WITH 1 INCREMENT BY 1;
INSERT INTO USERS VALUES (1,'rogelee','rogelee','ROLE_ADMIN');

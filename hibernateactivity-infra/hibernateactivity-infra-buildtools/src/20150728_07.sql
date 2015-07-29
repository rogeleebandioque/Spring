CREATE TABLE USERS(
    id int not null,
    username text not null,
    password text not null,
    role_id int not null,
    PRIMARY KEY (username),
    FOREIGN KEY(role_id) references UserRole(roleId)
);


INSERT INTO USERS VALUES (1,'rogelee','rogelee',1);

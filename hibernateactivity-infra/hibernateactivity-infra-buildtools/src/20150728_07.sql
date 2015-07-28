CREATE TABLE USERS(
    id int not null, 
    username text not null,
    password text not null,
    role_id int not null,
    PRIMARY KEY (id),
    FOREIGN KEY(role_id) references UserRole(roleId)
);


CREATE SEQUENCE userid_generator START WITH 1 INCREMENT BY 1 ;

INSERT INTO USERS VALUES (1,'rogelee','rogelee',1);

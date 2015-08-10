CREATE TABLE USERS(
    id int not null,
    username text not null,
    password text not null,
    enabled boolean default true,
    role text not null CHECK(role ='ROLE_ADMIN' OR role= 'ROLE_USER'),
    PRIMARY KEY (id,username)
);

CREATE SEQUENCE user_generator START WITH 1 INCREMENT BY 1;
INSERT INTO USERS VALUES (0,'rogelee','$2a$10$75pBjapg4Nl8Pzd.3JRnUe7PDJmk9qBGwNEJDAlA3V.dEJxcDKn5O',true,'ROLE_ADMIN');

password:koala

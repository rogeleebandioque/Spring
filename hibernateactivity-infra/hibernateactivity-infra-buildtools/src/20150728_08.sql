CREATE TABLE UserRole(
    roleId int not null, 
    roleName text not null,
    PRIMARY KEY (roleId)
);

INSERT INTO UserRole VALUES (1, 'Administrator');
INSERT INTO UserRole VALUES (2, 'Staff');



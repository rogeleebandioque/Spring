CREATE TABLE ROLE(
    id int not null,
    roleName text CHECK(roleName='Police' or roleName='Politician' or roleName='Soldier' or roleName='Celebrity' or roleName='Worker'),
    PRIMARY KEY(id)      
);

Insert into role values (1,'Police');
Insert into role values (2,'Politician');
Insert into role values (3,'Soldier');
Insert into role values (4,'Celebrity');
Insert into role values (5,'Worker');

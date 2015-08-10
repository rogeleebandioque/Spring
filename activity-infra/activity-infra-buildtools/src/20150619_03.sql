CREATE TABLE Contacts(
    id int not null, 
    contact text not null, 
    person_id int default null, 
    type text not null CHECK (type='e-mail' or type ='cellphone' or type = 'telephone'), 
    PRIMARY KEY (id),
    FOREIGN KEY(person_id) references person(id) on delete cascade
);


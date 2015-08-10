CREATE TABLE PERSON(
    id int not null, 
    first_name text not null,
    last_name text not null,
    address text not null,
    age int not null CHECK (age>0), 
    gender text not null CHECK(gender ='male' OR gender= 'female'), 
    bday date not null, 
    grade int not null CHECK(grade>0), 
    date_hired date not null, 
    currently_employed text not null CHECk (currently_employed = 'yes' or currently_employed = 'no'), 
    PRIMARY KEY (id)
);


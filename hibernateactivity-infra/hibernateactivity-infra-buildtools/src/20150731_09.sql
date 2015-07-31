CREATE TABLE PROJECTS (
    project_id int not null,
    project_name text not null,
    start_date date not null,
    end_date date not null,
    PRIMARY KEY (project_id)
);

CREATE SEQUENCE project_generator START WITH 1 INCREMENT BY 1 ;

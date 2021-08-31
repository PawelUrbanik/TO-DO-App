drop table if exists schema_version;
drop table if exists tasks;
create table tasks(
    id int primary key auto_increment,
    description varchar(100) not null,
    done boolean
);
drop table if exists notes;
create table  notes(
    id int primary key auto_increment,
    description varchar(100) not null,
    content varchar(100) not NULL
);
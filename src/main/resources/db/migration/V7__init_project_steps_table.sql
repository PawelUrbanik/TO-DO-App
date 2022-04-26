drop table if exists project_steps;
create table project_steps(
                         id int primary key auto_increment,
                         description varchar(100) not null,
                         project_id int null,
                         days_to_deadline int
);
alter table project_steps add foreign key (project_id) references projects (id);
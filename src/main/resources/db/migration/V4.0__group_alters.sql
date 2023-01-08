create table university_group (
    id int primary key auto_increment,
    description nvarchar(10)
);

alter table task
    add column owner_id int references users(id) after id;

alter table users
    add column group_id int references university_group(id);

create table teacher_group (
    teacher_id int not null,
    group_id int not null
);

create table group_task (
    group_id int not null,
    task_id int not null
);
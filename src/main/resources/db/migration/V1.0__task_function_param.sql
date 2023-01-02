create table task (
    id int primary key auto_increment,
    name varchar(32),
    description varchar(512),
    created_at datetime
);

create table function (
    id int primary key auto_increment,
    task_id int references task(id),
    name varchar(32)
);

create table param (
    id int primary key auto_increment,
    function_id int references function(id),
    type varchar(32),
    name varchar(32)
);

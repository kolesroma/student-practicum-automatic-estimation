create table `role` (
    id int primary key auto_increment,
    authority varchar(32)
);

create table users (
    id int primary key auto_increment,
    username varchar(32) unique,
    `password` nvarchar(60),
    role_id int references `role`(id)
);

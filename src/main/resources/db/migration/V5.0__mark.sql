create table mark (
    id int primary key auto_increment,
    task_id int references task(id),
    user_id int references users(id),
    score int
);
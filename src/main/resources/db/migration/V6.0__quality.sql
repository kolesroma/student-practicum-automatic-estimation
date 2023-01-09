create table quality (
    id int primary key auto_increment,
    case_coef int,
    linter_coef int,
    complexity_coef int,
    task_id int references task(id)
);
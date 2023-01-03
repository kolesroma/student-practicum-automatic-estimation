create table result (
    id int primary key auto_increment,
    expected nvarchar(512)
);

create table cases (
    id int primary key auto_increment,
    `value` nvarchar(512),
    param_id int references param(id),
    result_id int references result(id)
);


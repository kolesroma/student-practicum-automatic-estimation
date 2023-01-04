insert into task(name, description)
values('very first task', 'easy to pass');

insert into function(task_id, name)
values(1, 'delete_brackets');


insert into param(function_id, type, name)
values(1, 'string', 's');

insert into result(expected)
values('s'),
      ('vavava1');

insert into cases(val, param_id, result_id)
values('s()', 1, 1),
      ('vava(va(1', 1, 2);




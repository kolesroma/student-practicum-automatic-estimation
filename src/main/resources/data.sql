insert into role(authority)
values ('ROLE_STUDENT'),
       ('ROLE_TEACHER'),
       ('ROLE_ADMIN');

insert into university_group(description)
values ('IT-01');

insert into users(username, password, name, surname, email, birthday, role_id, group_id)
values ('roma',
        '$2y$10$uKGWj.y4BsfQyZcBhleup.HL.IofhV8l7hjABba/xGmh6HehS3s72',
        'admin',
        'roma',
        'admin@roma.com',
        '2000-12-27',
        3,
        null),
       ('bukasov',
        '$2y$10$uKGWj.y4BsfQyZcBhleup.HL.IofhV8l7hjABba/xGmh6HehS3s72',
        'max',
        'bukasov',
        'buka@sov.com',
        '1980-12-27',
        2,
        null),
       ('dima',
        '$2y$10$uKGWj.y4BsfQyZcBhleup.HL.IofhV8l7hjABba/xGmh6HehS3s72',
        'dima',
        'goncharenko',
        'gon@dima.com',
        '2002-10-15',
        1,
        1),
       ('kolesroma',
        '$2y$10$uKGWj.y4BsfQyZcBhleup.HL.IofhV8l7hjABba/xGmh6HehS3s72',
        'roma',
        'kolesnyk',
        'roma@koles.com',
        '2002-11-27',
        1,
        1);

insert into teacher_group(teacher_id, group_id)
values (2, 1);

insert into group_task(group_id, task_id)
values (1, 1);

insert into task(name, description, owner_id)
values('very first task', 'easy to pass', 2);

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
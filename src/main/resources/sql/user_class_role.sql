drop table user_class_role;

CREATE TABLE user_class_role
(
  `id`         int(11)       NOT NULL AUTO_INCREMENT,
  `user_id`     int(11) NOT NULL,
  `username`       varchar(1024) NOT NULL,
  `school`   varchar(1024) NOT NULL,
  `class_id` int(11) NOT NULL,
  `classname`      varchar(1024)   not null,
  `role`   varchar(1024) NOT NULL,
  PRIMARY KEY (`id`)
)
  DEFAULT CHARSET = utf8;

insert into user_class_role
values (1,
        1,
        '特工的特',
        '北京交通大学',
        1,
        '数据结构',
        '学生');
insert into user_class_role
values (2,
        3,
        'adf',
        '北京交通大学',
        1,
        '数据结构',
        'student');
insert into user_class_role
values (3,
        3,
        'adf',
        '北京交通大学',
        1,
        '数据结构',
        'teacher');


select * from user_class_role;
drop table user;

CREATE TABLE user
(
  `id`         int(11)       AUTO_INCREMENT,
  `name`       varchar(1024) default '' ,
  `nickname`       varchar(1024) default '' ,
  `password`   varchar(1024) NOT NULL,
  `phone`      varchar(20)   default  '',
  `email`   varchar(1024) NOT NULL,
  `avatar_url` varchar(1024) default 'default_avatar_url.jpg',

  PRIMARY KEY (`id`)
)
  DEFAULT CHARSET = utf8;

insert into user
values (1,
        '王一杰',
        '特工的特',
        'tegongdete',
        '18812342213',
        'wangyijieim@outlook.com',
        'https://b-ssl.duitang.com/uploads/item/201810/18/20181018162951_kgwzm.thumb.700_0.jpeg');


select * from user;
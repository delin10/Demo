create table workstation_group_seller (
    seller_id bigint not null default '60000' comment  '卖家id',
    group_id bigint not null default '60000' comment  '分组id',
    venture varchar(100) not null default '' comment '国家',
    primary key (venture, group_id, seller_id)
);

insert into workstation_group_seller values
(1, 1, 'SG'),
(2, 1, 'SG'),

(1, 2, 'SG'),
(3, 2, 'SG'),

(1, 3, 'ID'),
(3, 3, 'ID');
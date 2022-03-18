create table og_ordersearch_distribute_lock (
	resource_id varchar(127) not null default '' comment '资源id',
	holder_id varchar(127) not null default '' comment '持有者id',
	lock_status tinyint not null default '2' comment '锁定状态: 1-锁定 2-未锁定 3-失效',
	timeout bigint not null default '60000' comment  '超时时间',
  lock_time bigint not null default '0' comment '锁定时间, 单位ms',
  unlock_time bigint not null default '0' comment '释放锁的时间, 单位ms',
  primary key(resource_id)
);
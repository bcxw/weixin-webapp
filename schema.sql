create database zee_diancan ; 
use zee_diancan; 

CREATE TABLE `auth_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `wx_user_id` varchar(100) DEFAULT NULL COMMENT '微信user_id',
  `wx_name` varchar(100) DEFAULT NULL COMMENT '微信端成员名称',
  `wx_gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `wx_weixinid` varchar(100) DEFAULT NULL COMMENT '微信号',
  `wx_avatar` varchar(255) DEFAULT NULL COMMENT '微信头像',
  `wx_status` int(11) DEFAULT NULL COMMENT '关注状态: 1=已关注，2=已禁用，4=未关注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `wx_user_id_unique` (`wx_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `auth_user_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_meal_default` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) DEFAULT NULL COMMENT '本地用户id',
  `wx_user_id` varchar(100) DEFAULT NULL,
  `week0` varchar(11) DEFAULT NULL COMMENT '星期天默认用餐情况',
  `week1` varchar(11) DEFAULT NULL COMMENT '星期1默认用餐情况',
  `week2` varchar(11) DEFAULT NULL COMMENT '星期2默认用餐情况',
  `week3` varchar(11) DEFAULT NULL COMMENT '星期3默认用餐情况',
  `week4` varchar(11) DEFAULT NULL COMMENT '星期4默认用餐情况',
  `week5` varchar(11) DEFAULT NULL COMMENT '星期5默认用餐情况',
  `week6` varchar(11) DEFAULT NULL COMMENT '星期6默认用餐情况',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `wxid_unique` (`wx_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用餐默认情况';

CREATE TABLE `t_meal_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) DEFAULT NULL COMMENT '本地用户id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `weixinid` varchar(100) DEFAULT NULL COMMENT '姓名',
  `restaurant_id` int(11) DEFAULT NULL COMMENT '餐厅ID',
  `department_id` int(100) DEFAULT NULL COMMENT '部门',
  `gender` varchar(10) DEFAULT NULL COMMENT '性别',
  `meal_date` date DEFAULT NULL COMMENT '用餐日期',
  `meal_week` varchar(100) DEFAULT NULL COMMENT '周几',
  `breakfast` int(11) DEFAULT NULL COMMENT '早餐情况',
  `lunch` int(11) DEFAULT NULL COMMENT '午餐情况',
  `dinner` int(11) DEFAULT NULL COMMENT '晚餐情况',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用餐历史记录';

CREATE TABLE `t_restaurant_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `department_ids` set('1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20') DEFAULT '' COMMENT '餐厅对应的部门',
  `restaurant_name` varchar(128) NOT NULL COMMENT '餐厅名称',
  `restaurant_code` varchar(100) DEFAULT NULL COMMENT '餐厅编号',
  `restaurant_desc` varchar(128) NOT NULL COMMENT '餐厅描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='餐厅配置信息表';

INSERT INTO `t_restaurant_config` VALUES ('1', '2', '一号餐厅', '1111', '四楼的餐厅', '0000-00-00 00:00:00', '2017-05-17 10:03:51');

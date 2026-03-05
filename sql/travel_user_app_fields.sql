-- TravelUser 表结构调整：增加 APP 登录字段
-- 执行此 SQL 前请先备份数据

ALTER TABLE `travel_user` 
ADD COLUMN IF NOT EXISTS `total_achievements` int(11) DEFAULT 0 COMMENT '已获得成就数' AFTER `total_spots`,
ADD COLUMN IF NOT EXISTS `app_account` varchar(50) DEFAULT NULL COMMENT 'APP登录账号' AFTER `total_achievements`,
ADD COLUMN IF NOT EXISTS `app_password` varchar(100) DEFAULT NULL COMMENT 'APP登录密码' AFTER `app_account`;

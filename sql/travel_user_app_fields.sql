-- 为 travel_user 表添加 APP 登录相关字段
-- 执行此 SQL 来添加新字段

ALTER TABLE `travel_user` 
ADD COLUMN `app_account` varchar(50) DEFAULT NULL COMMENT 'APP登录账号' AFTER `total_achievements`,
ADD COLUMN `app_password` varchar(100) DEFAULT NULL COMMENT 'APP登录密码' AFTER `app_account`;

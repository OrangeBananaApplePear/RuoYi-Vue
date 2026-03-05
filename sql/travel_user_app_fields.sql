-- TravelUser 表结构调整：以 phone 作为主键，移除 user_id
-- 执行此 SQL 前请先备份数据

-- 1. 添加新字段（如果不存在）
ALTER TABLE `travel_user` 
ADD COLUMN IF NOT EXISTS `app_account` varchar(50) DEFAULT NULL COMMENT 'APP登录账号' AFTER `total_achievements`,
ADD COLUMN IF NOT EXISTS `app_password` varchar(100) DEFAULT NULL COMMENT 'APP登录密码' AFTER `app_account`;

-- 2. 删除 user_id 主键（如果有）
-- 注意：这将删除 user_id 列，请确保先备份数据
ALTER TABLE `travel_user` DROP PRIMARY KEY;
ALTER TABLE `travel_user` DROP COLUMN `user_id`;

-- 3. 将 phone 设置为主键
ALTER TABLE `travel_user` ADD PRIMARY KEY (`phone`);

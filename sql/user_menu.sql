-- 用户管理菜单SQL
-- 添加到旅途管理(2000)下

-- 用户管理菜单
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2019, '用户管理', 2000, 5, 'user', 'travel/user/index', 1, 0, 'C', '0', '0', 'travel:user:list', 'user', 'admin', NOW(), '', NULL, '');

-- 按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2020, '用户查询', 2019, 1, '', '', 1, 0, 'F', '0', '0', 'travel:user:query', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2021, '用户新增', 2019, 2, '', '', 1, 0, 'F', '0', '0', 'travel:user:add', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2022, '用户修改', 2019, 3, '', '', 1, 0, 'F', '0', '0', 'travel:user:edit', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2023, '用户删除', 2019, 4, '', '', 1, 0, 'F', '0', '0', 'travel:user:remove', '#', 'admin', NOW(), '', NULL, '');

-- 为travel_user表添加total_achievements字段(如果不存在)
-- ALTER TABLE travel_user ADD COLUMN total_achievements INT DEFAULT 0 COMMENT '已获得成就数';

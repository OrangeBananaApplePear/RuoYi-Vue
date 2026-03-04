-- 旅途管理菜单SQL
-- 注意：menu_id需要根据实际情况调整，这里假设从1000开始

-- 删除已存在的旅途菜单（如果需要重新创建）
-- DELETE FROM sys_menu WHERE menu_name IN ('旅途管理', '城市管理', '景点管理', '打卡管理');

-- 旅途管理一级菜单
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2000, '旅途管理', 0, 20, 'travel', NULL, 1, 0, 'M', '0', '0', '', 'travel', 'admin', NOW(), '', NULL, '旅途管理目录');

-- 城市管理菜单
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2001, '城市管理', 2000, 1, 'city', 'travel/city/index', 1, 0, 'C', '0', '0', 'travel:city:list', 'city', 'admin', NOW(), '', NULL, '');

-- 景点管理菜单
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2002, '景点管理', 2000, 2, 'spot', 'travel/spot/index', 1, 0, 'C', '0', '0', 'travel:spot:list', 'spot', 'admin', NOW(), '', NULL, '');

-- 打卡管理菜单
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2003, '打卡管理', 2000, 3, 'checkin', 'travel/checkin/index', 1, 0, 'C', '0', '0', 'travel:checkin:list', 'check', 'admin', NOW(), '', NULL, '');

-- 按钮权限 城市管理
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2004, '城市查询', 2001, 1, '', '', 1, 0, 'F', '0', '0', 'travel:city:query', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2005, '城市新增', 2001, 2, '', '', 1, 0, 'F', '0', '0', 'travel:city:add', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2006, '城市修改', 2001, 3, '', '', 1, 0, 'F', '0', '0', 'travel:city:edit', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2007, '城市删除', 2001, 4, '', '', 1, 0, 'F', '0', '0', 'travel:city:remove', '#', 'admin', NOW(), '', NULL, '');

-- 景点管理按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2008, '景点查询', 2002, 1, '', '', 1, 0, 'F', '0', '0', 'travel:spot:query', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2009, '景点新增', 2002, 2, '', '', 1, 0, 'F', '0', '0', 'travel:spot:add', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2010, '景点修改', 2002, 3, '', '', 1, 0, 'F', '0', '0', 'travel:spot:edit', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2011, '景点删除', 2002, 4, '', '', 1, 0, 'F', '0', '0', 'travel:spot:remove', '#', 'admin', NOW(), '', NULL, '');

-- 打卡管理按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2012, '打卡查询', 2003, 1, '', '', 1, 0, 'F', '0', '0', 'travel:checkin:query', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2013, '打卡删除', 2003, 2, '', '', 1, 0, 'F', '0', '0', 'travel:checkin:remove', '#', 'admin', NOW(), '', NULL, '');

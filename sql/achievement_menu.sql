-- 成就管理菜单SQL
-- 添加到旅途管理(2000)下

-- 成就管理菜单
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2014, '成就管理', 2000, 4, 'achievement', 'travel/achievement/index', 1, 0, 'C', '0', '0', 'travel:achievement:list', 'medal', 'admin', NOW(), '', NULL, '');

-- 按钮权限
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2015, '成就查询', 2014, 1, '', '', 1, 0, 'F', '0', '0', 'travel:achievement:query', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2016, '成就新增', 2014, 2, '', '', 1, 0, 'F', '0', '0', 'travel:achievement:add', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2017, '成就修改', 2014, 3, '', '', 1, 0, 'F', '0', '0', 'travel:achievement:edit', '#', 'admin', NOW(), '', NULL, '');
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2018, '成就删除', 2014, 4, '', '', 1, 0, 'F', '0', '0', 'travel:achievement:remove', '#', 'admin', NOW(), '', NULL, '');

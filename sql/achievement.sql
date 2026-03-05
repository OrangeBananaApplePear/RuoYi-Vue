-- 成就系统数据库脚本
-- 1. 成就表
DROP TABLE IF EXISTS travel_achievement;
CREATE TABLE travel_achievement (
    achievement_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '成就ID',
    achievement_name VARCHAR(100) NOT NULL COMMENT '成就名称',
    icon VARCHAR(500) DEFAULT '' COMMENT '成就图标',
    description VARCHAR(500) DEFAULT '' COMMENT '成就描述',
    condition_type VARCHAR(10) NOT NULL COMMENT '条件类型: city-城市, spot-景点',
    condition_cities TEXT COMMENT '条件城市ID列表,JSON格式',
    condition_spots TEXT COMMENT '条件景点ID列表,JSON格式',
    time_type VARCHAR(20) DEFAULT 'any' COMMENT '时间类型: any-任意时间, specific-指定月份',
    months VARCHAR(100) DEFAULT '' COMMENT '指定月份,JSON格式如[1,2,3]',
    status CHAR(1) DEFAULT '0' COMMENT '状态: 0-启用, 1-禁用',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (achievement_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成就表';

-- 2. 用户成就记录表
DROP TABLE IF EXISTS travel_user_achievement;
CREATE TABLE travel_user_achievement (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    user_id BIGINT(20) NOT NULL COMMENT '用户ID',
    achievement_id BIGINT(20) NOT NULL COMMENT '成就ID',
    achieved_time DATETIME DEFAULT NULL COMMENT '达成时间',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_achievement (user_id, achievement_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户成就记录表';

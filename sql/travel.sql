-- ==============================================
-- 旅途 App - 数据库表结构
-- ==============================================

-- 1. 城市表 (travel_city)
DROP TABLE IF EXISTS `travel_city`;
CREATE TABLE `travel_city` (
  `city_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '城市ID',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父城市ID(0=省份)',
  `city_name` varchar(100) NOT NULL COMMENT '城市名称',
  `city_code` varchar(50) DEFAULT NULL COMMENT '城市代码(如:110000)',
  `level` int(1) DEFAULT 1 COMMENT '层级(1=省,2=市)',
  `latitude` varchar(50) DEFAULT NULL COMMENT '纬度',
  `longitude` varchar(50) DEFAULT NULL COMMENT '经度',
  `sort` int(4) DEFAULT 0 COMMENT '显示顺序',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常,1停用)',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志(0代表存在,1代表删除)',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='城市表';

-- 2. 景点表 (travel_spot)
DROP TABLE IF EXISTS `travel_spot`;
CREATE TABLE `travel_spot` (
  `spot_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '景点ID',
  `city_id` bigint(20) NOT NULL COMMENT '所属城市ID',
  `spot_name` varchar(200) NOT NULL COMMENT '景点名称',
  `cover_image` varchar(500) DEFAULT NULL COMMENT '封面图URL',
  `introduction` varchar(1000) DEFAULT NULL COMMENT '简短介绍',
  `description` text COMMENT '详细介绍',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `latitude` varchar(50) DEFAULT NULL COMMENT '纬度',
  `longitude` varchar(50) DEFAULT NULL COMMENT '经度',
  `ticket_price` varchar(100) DEFAULT NULL COMMENT '门票参考',
  `open_time` varchar(200) DEFAULT NULL COMMENT '开放时间',
  `checkin_count` int(11) DEFAULT 0 COMMENT '打卡人数',
  `sort` int(4) DEFAULT 0 COMMENT '显示顺序',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常,1停用)',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`spot_id`),
  KEY `idx_city_id` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='景点表';

-- 3. 打卡记录表 (travel_checkin)
DROP TABLE IF EXISTS `travel_checkin`;
CREATE TABLE `travel_checkin` (
  `checkin_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '打卡ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `spot_id` bigint(20) NOT NULL COMMENT '景点ID',
  `city_id` bigint(20) NOT NULL COMMENT '城市ID',
  `content` varchar(1000) DEFAULT NULL COMMENT '打卡内容',
  `images` varchar(2000) DEFAULT NULL COMMENT '图片URLs(JSON数组)',
  `location_lat` varchar(50) DEFAULT NULL COMMENT '打卡时纬度',
  `location_lng` varchar(50) DEFAULT NULL COMMENT '打卡时经度',
  `is_public` char(1) DEFAULT '1' COMMENT '是否公开(0=私密,1=公开)',
  `like_count` int(11) DEFAULT 0 COMMENT '点赞数',
  `comment_count` int(11) DEFAULT 0 COMMENT '评论数',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常,1违规)',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`checkin_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_spot_id` (`spot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='打卡记录表';

-- 4. 打卡点赞表 (travel_checkin_like)
DROP TABLE IF EXISTS `travel_checkin_like`;
CREATE TABLE `travel_checkin_like` (
  `like_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
  `checkin_id` bigint(20) NOT NULL COMMENT '打卡ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`like_id`),
  UNIQUE KEY `uk_checkin_user` (`checkin_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='打卡点赞表';

-- 5. 打卡评论表 (travel_checkin_comment)
DROP TABLE IF EXISTS `travel_checkin_comment`;
CREATE TABLE `travel_checkin_comment` (
  `comment_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `checkin_id` bigint(20) NOT NULL COMMENT '打卡ID',
  `user_id` bigint(20) NOT NULL COMMENT '评论用户ID',
  `parent_id` bigint(20) DEFAULT 0 COMMENT '父评论ID',
  `content` varchar(500) NOT NULL COMMENT '评论内容',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`comment_id`),
  KEY `idx_checkin_id` (`checkin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='打卡评论表';

-- 6. 用户足迹表 (travel_footprint)
DROP TABLE IF EXISTS `travel_footprint`;
CREATE TABLE `travel_footprint` (
  `footprint_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '足迹ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `city_id` bigint(20) DEFAULT NULL COMMENT '城市ID(打卡城市时填)',
  `spot_id` bigint(20) DEFAULT NULL COMMENT '景点ID(打卡景点时填)',
  `type` char(1) DEFAULT '1' COMMENT '类型(1=城市,2=景点)',
  `create_time` datetime DEFAULT NULL COMMENT '首次打卡时间',
  PRIMARY KEY (`footprint_id`),
  UNIQUE KEY `uk_user_city` (`user_id`,`city_id`),
  UNIQUE KEY `uk_user_spot` (`user_id`,`spot_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='用户足迹表';

-- 7. 用户扩展表 (travel_user)
DROP TABLE IF EXISTS `travel_user`;
CREATE TABLE `travel_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID(对应sys_user)',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像URL',
  `total_checkins` int(11) DEFAULT 0 COMMENT '累计打卡次数',
  `total_cities` int(11) DEFAULT 0 COMMENT '已打卡城市数',
  `total_spots` int(11) DEFAULT 0 COMMENT '已打卡景点数',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户扩展表';

-- ==============================================
-- 旅途 App - 初始数据 (中国省份 + 热门城市 + 景点)
-- ==============================================

-- 1. 插入中国34个省级行政区
INSERT INTO `travel_city` (`city_id`, `parent_id`, `city_name`, `city_code`, `level`, `latitude`, `longitude`, `sort`, `status`, `del_flag`, `create_by`, `create_time`) VALUES
(1, 0, '北京市', '110000', 1, '39.9042', '116.4074', 1, '0', '0', 'admin', NOW()),
(2, 0, '天津市', '120000', 1, '39.1256', '117.1909', 2, '0', '0', 'admin', NOW()),
(3, 0, '河北省', '130000', 1, '38.0428', '114.5149', 3, '0', '0', 'admin', NOW()),
(4, 0, '山西省', '140000', 1, '37.8706', '112.5489', 4, '0', '0', 'admin', NOW()),
(5, 0, '内蒙古自治区', '150000', 1, '40.8424', '111.7499', 5, '0', '0', 'admin', NOW()),
(6, 0, '辽宁省', '210000', 1, '41.7968', '123.4315', 6, '0', '0', 'admin', NOW()),
(7, 0, '吉林省', '220000', 1, '43.8968', '125.3245', 7, '0', '0', 'admin', NOW()),
(8, 0, '黑龙江省', '230000', 1, '45.8038', '126.5340', 8, '0', '0', 'admin', NOW()),
(9, 0, '上海市', '310000', 1, '31.2304', '121.4737', 9, '0', '0', 'admin', NOW()),
(10, 0, '江苏省', '320000', 1, '32.0603', '118.7969', 10, '0', '0', 'admin', NOW()),
(11, 0, '浙江省', '330000', 1, '30.2873', '120.1536', 11, '0', '0', 'admin', NOW()),
(12, 0, '安徽省', '340000', 1, '31.8612', '117.2830', 12, '0', '0', 'admin', NOW()),
(13, 0, '福建省', '350000', 1, '26.0745', '119.2965', 13, '0', '0', 'admin', NOW()),
(14, 0, '江西省', '360000', 1, '28.6823', '115.8579', 14, '0', '0', 'admin', NOW()),
(15, 0, '山东省', '370000', 1, '36.6512', '117.1205', 15, '0', '0', 'admin', NOW()),
(16, 0, '河南省', '410000', 1, '34.7466', '113.6253', 16, '0', '0', 'admin', NOW()),
(17, 0, '湖北省', '420000', 1, '30.5928', '114.3055', 17, '0', '0', 'admin', NOW()),
(18, 0, '湖南省', '430000', 1, '28.2282', '112.9388', 18, '0', '0', 'admin', NOW()),
(19, 0, '广东省', '440000', 1, '23.1291', '113.2644', 19, '0', '0', 'admin', NOW()),
(20, 0, '广西壮族自治区', '450000', 1, '22.8170', '108.3665', 20, '0', '0', 'admin', NOW()),
(21, 0, '海南省', '460000', 1, '20.0444', '110.1999', 21, '0', '0', 'admin', NOW()),
(22, 0, '重庆市', '500000', 1, '29.5630', '106.5516', 22, '0', '0', 'admin', NOW()),
(23, 0, '四川省', '510000', 1, '30.5728', '104.0668', 23, '0', '0', 'admin', NOW()),
(24, 0, '贵州省', '520000', 1, '26.6470', '106.6302', 24, '0', '0', 'admin', NOW()),
(25, 0, '云南省', '530000', 1, '25.0453', '102.7097', 25, '0', '0', 'admin', NOW()),
(26, 0, '西藏自治区', '540000', 1, '29.6500', '91.1000', 26, '0', '0', 'admin', NOW()),
(27, 0, '陕西省', '610000', 1, '34.3416', '108.9398', 27, '0', '0', 'admin', NOW()),
(28, 0, '甘肃省', '620000', 1, '36.0611', '103.8343', 28, '0', '0', 'admin', NOW()),
(29, 0, '青海省', '630000', 1, '36.6232', '101.7782', 29, '0', '0', 'admin', NOW()),
(30, 0, '宁夏回族自治区', '640000', 1, '38.4872', '106.2309', 30, '0', '0', 'admin', NOW()),
(31, 0, '新疆维吾尔自治区', '650000', 1, '43.7930', '87.6271', 31, '0', '0', 'admin', NOW()),
(32, 0, '台湾省', '710000', 1, '25.0330', '121.5654', 32, '0', '0', 'admin', NOW()),
(33, 0, '香港特别行政区', '810000', 1, '22.3193', '114.1694', 33, '0', '0', 'admin', NOW()),
(34, 0, '澳门特别行政区', '820000', 1, '22.1987', '113.5439', 34, '0', '0', 'admin', NOW());

-- 2. 热门城市示例数据
INSERT INTO `travel_city` (`parent_id`, `city_name`, `city_code`, `level`, `latitude`, `longitude`, `sort`, `status`, `del_flag`) VALUES
(1, '北京市区', '110100', 2, '39.9042', '116.4074', 1, '0', '0'),
(9, '上海市区', '310100', 2, '31.2304', '121.4737', 1, '0', '0'),
(10, '南京市', '320100', 2, '32.0603', '118.7969', 1, '0', '0'),
(10, '苏州市', '320500', 2, '31.2989', '120.5853', 2, '0', '0'),
(11, '杭州市', '330100', 2, '30.2873', '120.1536', 1, '0', '0'),
(11, '宁波市', '330200', 2, '29.8683', '121.5440', 2, '0', '0'),
(23, '成都市', '510100', 2, '30.5728', '104.0668', 1, '0', '0'),
(27, '西安市', '610100', 2, '34.3416', '108.9398', 1, '0', '0'),
(19, '广州市', '440100', 2, '23.1291', '113.2644', 1, '0', '0'),
(19, '深圳市', '440300', 2, '22.5431', '114.0579', 2, '0', '0');

-- 3. 热门景点示例数据
INSERT INTO `travel_spot` (`city_id`, `spot_name`, `cover_image`, `introduction`, `description`, `address`, `latitude`, `longitude`, `ticket_price`, `open_time`, `checkin_count`, `sort`, `status`, `del_flag`) VALUES
-- 北京
(35, '故宫博物院', 'https://picsum.photos/400/300?random=1', '中国明清两代的皇家宫殿，世界上现存规模最大、保存最为完整的木质结构古建筑之一', '故宫又称紫禁城，位于北京市中心，旧称紫禁城，是中国古代宫廷建筑之精华', '北京市东城区景山前街4号', '39.9163', '116.3972', '60元', '08:30-17:00', 15000, 1, '0', '0'),
(35, '长城-八达岭', 'https://picsum.photos/400/300?random=2', '中国古代伟大的防御工程，万里长城的重要组成部分', '八达岭长城位于北京市延庆区军都山关沟古道北口，是万里长城中最著名的一段', '北京市延庆区G6京藏高速58号出口', '40.4319', '116.9704', '40元', '06:30-19:00', 12000, 2, '0', '0'),
(35, '天坛公园', 'https://picsum.photos/400/300?random=3', '明清两代帝王祭祀皇天、祈五谷丰登的场所', '天坛位于北京市东城区，是世界文化遗产，全国重点文物保护单位', '北京市东城区天坛内东里7号', '39.9025', '116.4106', '15元', '06:00-21:00', 8000, 3, '0', '0'),
(35, '颐和园', 'https://picsum.photos/400/300?random=4', '清代皇家园林，以昆明湖、万寿山为基址，汲取江南园林的设计手法建造', '颐和园是中国现存规模最大的皇家园林，被誉为"皇家园林博物馆"', '北京市海淀区新建宫门路19号', '39.9993', '116.2756', '30元', '06:30-18:00', 7500, 4, '0', '0'),
-- 上海
(36, '外滩', 'https://picsum.photos/400/300?random=5', '上海的象征，沿江黄浦堤岸上的历史建筑群', '外滩位于黄浦江畔，全长约1.5公里，是上海最具代表性的景观', '上海市黄浦区中山东一路', '31.2400', '121.4900', '免费', '全天', 18000, 1, '0', '0'),
(36, '东方明珠广播电视塔', 'https://picsum.photos/400/300?random=6', '上海的标志性文化景观之一，集观光、餐饮、购物于一体的多功能塔', '东方明珠塔高468米，是亚洲第一高塔', '上海市浦东新区世纪大道1号', '31.2450', '121.5050', '180元', '08:00-21:30', 12000, 2, '0', '0'),
-- 杭州
(37, '西湖', 'https://picsum.photos/400/300?random=7', '中国著名的风景名胜区，世界文化遗产', '西湖位于杭州市西部，是中国首批国家重点风景名胜区', '浙江省杭州市西湖区龙井路1号', '30.2467', '120.1476', '免费', '全天', 25000, 1, '0', '0'),
(37, '乌镇', 'https://picsum.photos/400/300?random=8', '中国最后的枕水人家，江南六大古镇之一', '乌镇位于桐乡市，是江南水乡的典型代表', '浙江省桐乡市乌镇', '30.7441', '120.4878', '150元', '08:00-17:30', 11000, 2, '0', '0'),
-- 苏州
(38, '拙政园', 'https://picsum.photos/400/300?random=9', '苏州古典园林的代表作品，世界文化遗产', '拙政园位于苏州市姑苏区，是苏州园林中面积最大的古典园林', '江苏省苏州市姑苏区东北街178号', '31.3915', '120.6117', '70元', '07:30-17:30', 15000, 1, '0', '0'),
-- 南京
(39, '中山陵', 'https://picsum.photos/400/300?random=10', '中国近代伟大的民主革命先行者孙中山先生的陵墓', '中山陵位于南京市紫金山南麓，建筑宏伟，庄严肃穆', '江苏省南京市玄武区石象路7号', '32.0603', '118.7969', '免费', '08:30-17:00', 12000, 1, '0', '0'),
-- 成都
(40, '大熊猫繁育研究基地', 'https://picsum.photos/400/300?random=11', '全球最大的大熊猫繁育研究机构', '基地位于成都市成华区，是观赏大熊猫的最佳地点', '四川省成都市成华区熊猫大道1375号', '30.7415', '104.3976', '55元', '07:30-18:00', 12000, 1, '0', '0'),
(40, '宽窄巷子', 'https://picsum.photos/400/300?random=12', '成都三大历史文化保护区之一', '宽窄巷子位于成都市青羊区，是成都遗留下来的较成规模的清朝古街道', '四川省成都市青羊区长顺上街127号', '30.6710', '104.0566', '免费', '全天', 9000, 2, '0', '0'),
-- 西安
(41, '秦始皇兵马俑', 'https://picsum.photos/400/300?random=13', '世界第八大奇迹，古代秦始皇陵的陪葬坑', '兵马俑位于西安市临潼区，是秦始皇陵的一部分', '陕西省西安市临潼区秦陵北路', '34.3844', '109.2785', '120元', '08:30-18:00', 20000, 1, '0', '0'),
(41, '大雁塔', 'https://picsum.photos/400/300?random=14', '唐代著名建筑，玄奘法师主持修建', '大雁塔位于西安市南郊，是西安的标志性建筑之一', '陕西省西安市雁塔区大雁塔南广场', '34.2194', '108.9593', '50元', '08:00-18:30', 10000, 2, '0', '0'),
-- 广州
(42, '广州塔', 'https://picsum.photos/400/300?random=15', '广州的标志性建筑，昵称"小蛮腰"', '广州塔高600米，是中国第二高塔', '广东省广州市海珠区阅江西路222号', '23.1065', '113.3255', '150元', '09:30-22:00', 8000, 1, '0', '0'),
-- 深圳
(43, '世界之窗', 'https://picsum.photos/400/300?random=17', '集世界著名景观于一体的主题公园', '世界之窗位于深圳市南山区，展示了全球著名景点', '广东省深圳市南山区华侨城', '22.5409', '114.0697', '200元', '09:00-22:00', 10000, 1, '0', '0');

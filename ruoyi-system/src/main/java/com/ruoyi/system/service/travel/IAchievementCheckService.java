package com.ruoyi.system.service.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelAchievement;

/**
 * 成就检测Service接口
 * 
 * @author ruoyi
 */
public interface IAchievementCheckService
{
    /**
     * 检测用户打卡后是否达成新成就
     * @param userId 用户ID
     * @param cityId 城市ID（可选）
     * @param spotId 景点ID（可选）
     * @param checkinTime 打卡时间
     * @return 新达成的成就列表
     */
    List<TravelAchievement> checkAndGrantAchievements(Long userId, Long cityId, Long spotId, java.util.Date checkinTime);
}

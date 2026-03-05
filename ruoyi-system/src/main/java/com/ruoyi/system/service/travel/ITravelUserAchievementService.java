package com.ruoyi.system.service.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelUserAchievement;

/**
 * 用户成就Service接口
 * 
 * @author ruoyi
 */
public interface ITravelUserAchievementService
{
    /**
     * 查询用户成就
     */
    TravelUserAchievement selectUserAchievementById(Long id);

    /**
     * 查询用户成就列表
     */
    List<TravelUserAchievement> selectUserAchievementList(TravelUserAchievement userAchievement);

    /**
     * 查询用户已达成成就ID列表
     */
    List<Long> selectAchievedAchievementIds(Long userId);

    /**
     * 检查用户是否已达成某成就
     */
    boolean checkUserAchievementExists(Long userId, Long achievementId);

    /**
     * 新增用户成就
     */
    int insertUserAchievement(TravelUserAchievement userAchievement);

    /**
     * 删除用户成就
     */
    int deleteUserAchievementById(Long id);

    /**
     * 批量删除用户成就
     */
    int deleteUserAchievementByIds(Long[] ids);
}

package com.ruoyi.system.service.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelAchievement;

/**
 * 成就Service接口
 * 
 * @author ruoyi
 */
public interface ITravelAchievementService
{
    /**
     * 查询成就
     */
    TravelAchievement selectTravelAchievementById(Long achievementId);

    /**
     * 查询成就列表
     */
    List<TravelAchievement> selectTravelAchievementList(TravelAchievement travelAchievement);

    /**
     * 查询所有启用的成就
     */
    List<TravelAchievement> selectEnabledAchievements();

    /**
     * 新增成就
     */
    int insertTravelAchievement(TravelAchievement travelAchievement);

    /**
     * 修改成就
     */
    int updateTravelAchievement(TravelAchievement travelAchievement);

    /**
     * 删除成就
     */
    int deleteTravelAchievementById(Long achievementId);

    /**
     * 批量删除成就
     */
    int deleteTravelAchievementByIds(Long[] achievementIds);
}

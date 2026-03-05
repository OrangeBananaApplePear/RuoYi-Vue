package com.ruoyi.system.mapper.travel;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.travel.TravelUserAchievement;

/**
 * 用户成就Mapper接口
 * 
 * @author ruoyi
 */
public interface TravelUserAchievementMapper
{
    /**
     * 查询用户成就记录
     */
    TravelUserAchievement selectUserAchievementById(Long id);

    /**
     * 查询用户的所有成就
     */
    List<TravelUserAchievement> selectUserAchievementList(TravelUserAchievement userAchievement);

    /**
     * 查询用户已达成成就ID列表
     */
    List<Long> selectAchievedAchievementIds(Long userId);

    /**
     * 检查用户是否已达成某成就
     */
    int checkUserAchievementExists(@Param("userId") Long userId, @Param("achievementId") Long achievementId);

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

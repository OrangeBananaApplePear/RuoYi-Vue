package com.ruoyi.system.mapper.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelUser;

/**
 * 用户扩展Mapper接口
 * 
 * @author 旅途
 */
public interface TravelUserMapper
{
    /**
     * 查询用户扩展信息
     */
    public TravelUser selectTravelUserById(Long userId);

    /**
     * 根据手机号查询
     */
    public TravelUser selectTravelUserByPhone(String phone);

    /**
     * 查询用户列表
     */
    public List<TravelUser> selectTravelUserList(TravelUser travelUser);

    /**
     * 新增用户扩展信息
     */
    public int insertTravelUser(TravelUser travelUser);

    /**
     * 修改用户扩展信息
     */
    public int updateTravelUser(TravelUser travelUser);

    /**
     * 删除用户
     */
    public int deleteTravelUserById(Long userId);

    /**
     * 批量删除用户
     */
    public int deleteTravelUserByIds(Long[] userIds);

    /**
     * 增加打卡次数
     */
    public int incrementCheckinCount(Long userId);

    /**
     * 增加已打卡城市数
     */
    public int incrementCityCount(Long userId);

    /**
     * 增加已打卡景点数
     */
    public int incrementSpotCount(Long userId);
}

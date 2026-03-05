package com.ruoyi.system.service.travel;

import java.util.List;
import com.ruoyi.system.domain.travel.TravelUser;

/**
 * 用户扩展Service接口
 * 
 * @author 旅途
 */
public interface ITravelUserService
{
    /**
     * 根据手机号查询用户
     */
    public TravelUser selectTravelUserByPhone(String phone);

    /**
     * 查询用户列表
     */
    public List<TravelUser> selectTravelUserList(TravelUser travelUser);

    /**
     * 注册/创建用户
     */
    public TravelUser createUser(String phone, String nickname);

    /**
     * 新增用户
     */
    public int insertTravelUser(TravelUser travelUser);

    /**
     * 更新用户信息
     */
    public int updateTravelUser(TravelUser travelUser);

    /**
     * 删除用户
     */
    public int deleteTravelUserByPhone(String phone);

    /**
     * 批量删除用户
     */
    public int deleteTravelUserByPhones(String[] phones);

    /**
     * 更新用户头像
     */
    public int updateAvatar(String phone, String avatar);

    /**
     * 更新用户昵称
     */
    public int updateNickname(String phone, String nickname);

    /**
     * 增加打卡次数
     */
    public void incrementCheckinCount(String phone);

    /**
     * 增加城市/景点计数
     */
    public void addNewCityCount(String phone);
    public void addNewSpotCount(String phone);
}

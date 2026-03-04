package com.ruoyi.web.controller.travel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.travel.TravelUser;
import com.ruoyi.system.service.travel.ITravelUserService;

/**
 * 用户Controller
 * 
 * @author 旅途
 */
@RestController
@RequestMapping("/travel/user")
public class TravelUserController extends BaseController
{
    @Autowired
    private ITravelUserService travelUserService;

    /**
     * 获取当前用户信息
     */
    @GetMapping("/my")
    public AjaxResult getMyInfo()
    {
        Long userId = SecurityUtils.getUserId();
        TravelUser user = travelUserService.selectTravelUserById(userId);
        
        if (user == null)
        {
            return error("用户信息不存在");
        }
        return success(user);
    }

    /**
     * 获取指定用户信息
     */
    @GetMapping("/{userId}")
    public AjaxResult getUserInfo(@PathVariable Long userId)
    {
        TravelUser user = travelUserService.selectTravelUserById(userId);
        if (user == null)
        {
            return error("用户不存在");
        }
        // 脱敏处理
        if (user.getPhone() != null && user.getPhone().length() > 7)
        {
            user.setPhone(user.getPhone().substring(0, 3) + "****" + user.getPhone().substring(7));
        }
        return success(user);
    }

    /**
     * 根据手机号查询用户
     */
    @GetMapping("/phone/{phone}")
    public AjaxResult getUserByPhone(@PathVariable String phone)
    {
        TravelUser user = travelUserService.selectTravelUserByPhone(phone);
        if (user == null)
        {
            return error("用户不存在");
        }
        return success(user);
    }

    /**
     * 更新用户昵称
     */
    @PutMapping("/nickname")
    public AjaxResult updateNickname(@RequestParam String nickname)
    {
        Long userId = SecurityUtils.getUserId();
        return toAjax(travelUserService.updateNickname(userId, nickname));
    }

    /**
     * 更新用户头像
     */
    @PutMapping("/avatar")
    public AjaxResult updateAvatar(@RequestParam String avatar)
    {
        Long userId = SecurityUtils.getUserId();
        return toAjax(travelUserService.updateAvatar(userId, avatar));
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    public AjaxResult update(@RequestBody TravelUser travelUser)
    {
        Long userId = SecurityUtils.getUserId();
        travelUser.setUserId(userId);
        return toAjax(travelUserService.updateTravelUser(travelUser));
    }

    /**
     * 创建/注册用户
     */
    @PostMapping
    public AjaxResult register(@RequestBody TravelUser travelUser)
    {
        TravelUser user = travelUserService.createUser(travelUser.getPhone(), travelUser.getNickname());
        if (user == null)
        {
            return error("注册失败");
        }
        return success(user);
    }

    /**
     * 用户列表(管理端)
     */
    @GetMapping("/list")
    public TableDataInfo list(TravelUser travelUser)
    {
        // 管理端用户列表暂时通过sys_user表实现
        return getDataTable(null);
    }
}

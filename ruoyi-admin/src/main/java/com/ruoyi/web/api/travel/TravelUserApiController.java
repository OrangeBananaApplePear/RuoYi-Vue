package com.ruoyi.web.api.travel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.travel.TravelUser;
import com.ruoyi.system.service.travel.ITravelUserService;

/**
 * 移动端-用户接口
 */
@RestController
@RequestMapping("/api/travel/user")
public class TravelUserApiController extends BaseController
{
    @Autowired
    private ITravelUserService travelUserService;

    @GetMapping("/my")
    public AjaxResult my()
    {
        Long userId = SecurityUtils.getUserId();
        TravelUser user = travelUserService.selectTravelUserById(userId);
        if (user == null)
        {
            return error("用户信息不存在");
        }
        return success(user);
    }

    @GetMapping("/{userId}")
    public AjaxResult get(@PathVariable Long userId)
    {
        TravelUser user = travelUserService.selectTravelUserById(userId);
        if (user == null)
        {
            return error("用户不存在");
        }
        if (user.getPhone() != null && user.getPhone().length() > 7)
        {
            user.setPhone(user.getPhone().substring(0, 3) + "****" + user.getPhone().substring(7));
        }
        return success(user);
    }

    @PutMapping("/nickname")
    public AjaxResult nickname(@RequestParam String nickname)
    {
        Long userId = SecurityUtils.getUserId();
        return toAjax(travelUserService.updateNickname(userId, nickname));
    }

    @PutMapping("/avatar")
    public AjaxResult avatar(@RequestParam String avatar)
    {
        Long userId = SecurityUtils.getUserId();
        return toAjax(travelUserService.updateAvatar(userId, avatar));
    }

    @PutMapping
    public AjaxResult update(@RequestBody TravelUser travelUser)
    {
        Long userId = SecurityUtils.getUserId();
        travelUser.setUserId(userId);
        return toAjax(travelUserService.updateTravelUser(travelUser));
    }

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
}

package com.ruoyi.web.controller.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.travel.TravelUser;
import com.ruoyi.system.service.travel.ITravelUserService;

/**
 * 管理端-用户管理
 */
@RestController
@RequestMapping("/travel/user")
public class TravelUserController extends BaseController
{
    @Autowired
    private ITravelUserService travelUserService;

    /**
     * 查询用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TravelUser travelUser)
    {
        startPage();
        List<TravelUser> list = travelUserService.selectTravelUserList(travelUser);
        return getDataTable(list);
    }

    /**
     * 获取用户详情
     */
    @GetMapping("/{userId}")
    public AjaxResult get(@PathVariable Long userId)
    {
        return success(travelUserService.selectTravelUserById(userId));
    }

    /**
     * 新增用户
     */
    @PostMapping
    public AjaxResult add(@RequestBody TravelUser travelUser)
    {
        return toAjax(travelUserService.insertTravelUser(travelUser));
    }

    /**
     * 修改用户
     */
    @PutMapping
    public AjaxResult edit(@RequestBody TravelUser travelUser)
    {
        return toAjax(travelUserService.updateTravelUser(travelUser));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(travelUserService.deleteTravelUserByIds(userIds));
    }
}

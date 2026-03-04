package com.ruoyi.web.api.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.travel.*;
import com.ruoyi.system.service.travel.*;

/**
 * 移动端-城市接口
 */
@RestController
@RequestMapping("/api/travel/city")
public class TravelCityApiController extends BaseController
{
    @Autowired
    private ITravelCityService travelCityService;

    @GetMapping("/provinces")
    public AjaxResult provinces()
    {
        return success(travelCityService.selectProvinceList());
    }

    @GetMapping("/children/{parentId}")
    public AjaxResult children(@PathVariable Long parentId)
    {
        return success(travelCityService.selectCityChildren(parentId));
    }

    @GetMapping("/{cityId}")
    public AjaxResult get(@PathVariable Long cityId)
    {
        return success(travelCityService.selectTravelCityById(cityId));
    }

    @GetMapping("/checked/{userId}")
    public AjaxResult checked(@PathVariable Long userId)
    {
        return success(travelCityService.selectCheckedCitiesByUserId(userId));
    }
}

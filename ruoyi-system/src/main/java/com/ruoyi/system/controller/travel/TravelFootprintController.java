package com.ruoyi.system.controller.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.domain.travel.TravelFootprint;
import com.ruoyi.system.service.travel.ITravelFootprintService;

/**
 * 用户足迹Controller
 * 
 * @author 旅途
 */
@RestController
@RequestMapping("/travel/footprint")
public class TravelFootprintController extends BaseController
{
    @Autowired
    private ITravelFootprintService travelFootprintService;

    /**
     * 获取当前用户的足迹统计
     */
    @GetMapping("/my")
    public AjaxResult getMyFootprint()
    {
        Long userId = SecurityUtils.getUserId();
        
        List<TravelFootprint> cities = travelFootprintService.selectCitiesByUserId(userId);
        List<TravelFootprint> spots = travelFootprintService.selectSpotsByUserId(userId);
        
        return success()
            .put("cities", cities)
            .put("spots", spots)
            .put("totalCities", cities.size())
            .put("totalSpots", spots.size());
    }

    /**
     * 获取指定用户的足迹
     */
    @GetMapping("/user/{userId}")
    public AjaxResult getUserFootprint(@PathVariable Long userId)
    {
        List<TravelFootprint> cities = travelFootprintService.selectCitiesByUserId(userId);
        List<TravelFootprint> spots = travelFootprintService.selectSpotsByUserId(userId);
        
        return success()
            .put("cities", cities)
            .put("spots", spots)
            .put("totalCities", cities.size())
            .put("totalSpots", spots.size());
    }

    /**
     * 检查是否已打卡某城市
     */
    @GetMapping("/check/city/{cityId}")
    public AjaxResult checkCity(@PathVariable Long cityId)
    {
        Long userId = SecurityUtils.getUserId();
        boolean checked = travelFootprintService.checkCityChecked(userId, cityId);
        return success(checked);
    }

    /**
     * 检查是否已打卡某景点
     */
    @GetMapping("/check/spot/{spotId}")
    public AjaxResult checkSpot(@PathVariable Long spotId)
    {
        Long userId = SecurityUtils.getUserId();
        boolean checked = travelFootprintService.checkSpotChecked(userId, spotId);
        return success(checked);
    }

    /**
     * 获取足迹列表(管理端)
     */
    @RequiresPermissions("travel:footprint:list")
    @GetMapping("/list")
    public TableDataInfo list(TravelFootprint travelFootprint)
    {
        startPage();
        List<TravelFootprint> list = travelFootprintService.selectTravelFootprintList(travelFootprint);
        return getDataTable(list);
    }
}

package com.ruoyi.web.api.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.domain.travel.TravelFootprint;
import com.ruoyi.system.service.travel.ITravelFootprintService;

/**
 * 移动端-足迹接口
 */
@RestController
@RequestMapping("/api/travel/footprint")
public class TravelFootprintApiController extends BaseController
{
    @Autowired
    private ITravelFootprintService travelFootprintService;

    @GetMapping("/my")
    public AjaxResult my()
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

    @GetMapping("/user/{userId}")
    public AjaxResult user(@PathVariable Long userId)
    {
        List<TravelFootprint> cities = travelFootprintService.selectCitiesByUserId(userId);
        List<TravelFootprint> spots = travelFootprintService.selectSpotsByUserId(userId);
        
        return success()
            .put("cities", cities)
            .put("spots", spots)
            .put("totalCities", cities.size())
            .put("totalSpots", spots.size());
    }

    @GetMapping("/check/city/{cityId}")
    public AjaxResult checkCity(@PathVariable Long cityId)
    {
        Long userId = SecurityUtils.getUserId();
        return success(travelFootprintService.checkCityChecked(userId, cityId));
    }

    @GetMapping("/check/spot/{spotId}")
    public AjaxResult checkSpot(@PathVariable Long spotId)
    {
        Long userId = SecurityUtils.getUserId();
        return success(travelFootprintService.checkSpotChecked(userId, spotId));
    }
}

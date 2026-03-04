package com.ruoyi.web.api.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.travel.TravelSpot;
import com.ruoyi.system.service.travel.ITravelSpotService;

/**
 * 移动端-景点接口
 */
@RestController
@RequestMapping("/api/travel/spot")
public class TravelSpotApiController extends BaseController
{
    @Autowired
    private ITravelSpotService travelSpotService;

    @GetMapping("/list/{cityId}")
    public AjaxResult list(@PathVariable Long cityId)
    {
        return success(travelSpotService.selectSpotListByCityId(cityId));
    }

    @GetMapping("/{spotId}")
    public AjaxResult get(@PathVariable Long spotId)
    {
        return success(travelSpotService.selectTravelSpotById(spotId));
    }

    @GetMapping("/checked/{userId}")
    public AjaxResult checked(@PathVariable Long userId)
    {
        return success(travelSpotService.selectCheckedSpotsByUserId(userId));
    }
}

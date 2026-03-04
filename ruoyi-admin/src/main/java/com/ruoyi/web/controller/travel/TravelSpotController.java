package com.ruoyi.web.controller.travel;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.travel.TravelSpot;
import com.ruoyi.system.service.travel.ITravelSpotService;

/**
 * 景点Controller
 * 
 * @author 旅途
 */
@RestController
@RequestMapping("/travel/spot")
public class TravelSpotController extends BaseController
{
    @Autowired
    private ITravelSpotService travelSpotService;

    /**
     * 获取某城市的所有景点
     */
    @GetMapping("/list/{cityId}")
    public AjaxResult getSpotsByCity(@PathVariable Long cityId)
    {
        List<TravelSpot> list = travelSpotService.selectSpotListByCityId(cityId);
        return success(list);
    }

    /**
     * 获取用户已打卡景点
     */
    @GetMapping("/checked/{userId}")
    public AjaxResult getCheckedSpots(@PathVariable Long userId)
    {
        List<TravelSpot> list = travelSpotService.selectCheckedSpotsByUserId(userId);
        return success(list);
    }

    /**
     * 查询景点列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TravelSpot travelSpot)
    {
        startPage();
        List<TravelSpot> list = travelSpotService.selectTravelSpotList(travelSpot);
        return getDataTable(list);
    }

    /**
     * 获取景点详细信息
     */
    @GetMapping(value = "/{spotId}")
    public AjaxResult getInfo(@PathVariable Long spotId)
    {
        return success(travelSpotService.selectTravelSpotById(spotId));
    }

    /**
     * 新增景点
     */
    @PostMapping
    public AjaxResult add(@RequestBody TravelSpot travelSpot)
    {
        travelSpot.setCreateBy(getUsername());
        return toAjax(travelSpotService.insertTravelSpot(travelSpot));
    }

    /**
     * 修改景点
     */
    @PutMapping
    public AjaxResult edit(@RequestBody TravelSpot travelSpot)
    {
        travelSpot.setUpdateBy(getUsername());
        return toAjax(travelSpotService.updateTravelSpot(travelSpot));
    }

    /**
     * 删除景点
     */
    @DeleteMapping("/{spotIds}")
    public AjaxResult remove(@PathVariable Long[] spotIds)
    {
        return toAjax(travelSpotService.deleteTravelSpotByIds(spotIds));
    }
}

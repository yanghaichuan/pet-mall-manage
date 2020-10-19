
package com.pet.mall.controller.admin;

import com.pet.mall.common.ServiceResultEnum;
import com.pet.mall.entity.Carousel;
import com.pet.mall.entity.PetMallNotify;
import com.pet.mall.service.PetMallNotifyService;
import com.pet.mall.util.PageQueryUtil;
import com.pet.mall.util.Result;
import com.pet.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author 托尼斯塔珂
 */
@Controller
@RequestMapping("/admin")
public class PetMallNotifyController {

    @Resource
    PetMallNotifyService petMallNotifyService;

    @GetMapping("/notify")
    public String carouselPage(HttpServletRequest request) {
        request.setAttribute("path", "notify");
        return "admin/notify";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/notify/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(petMallNotifyService.getPetMallNotifyPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/notify/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody PetMallNotify petMallNotify) {
        petMallNotify.setCreateTime(new Date());
        petMallNotify.setIsDeleted("0");
        String result = petMallNotifyService.savePetMallNotify(petMallNotify);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/notify/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody PetMallNotify petMallNotify) {
        String result = petMallNotifyService.updatePetMallNotify(petMallNotify);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/notify/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        PetMallNotify petMallNotify = petMallNotifyService.getPetMallNotifyById(id);
        if (petMallNotify == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(petMallNotify);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/notify/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (petMallNotifyService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}
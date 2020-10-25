
package com.pet.mall.controller.admin;

import com.pet.mall.common.ServiceResultEnum;
import com.pet.mall.entity.Carousel;
import com.pet.mall.service.PetMallCarouselService;
import com.pet.mall.util.PageQueryUtil;
import com.pet.mall.util.Result;
import com.pet.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @author 托尼斯塔珂
 */
@Controller
@RequestMapping("/admin")
public class PetMallCarouselController {

    @Resource
    PetMallCarouselService petMallCarouselService;

    @GetMapping("/carousels")
    public String carouselPage(HttpServletRequest request) {
        request.setAttribute("path", "carousel");
        return "admin/carousel";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/carousels/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(petMallCarouselService.getCarouselPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/carousels/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Carousel carousel) {
        if (StringUtils.isEmpty(carousel.getCarouselUrl())
                || Objects.isNull(carousel.getCarouselRank())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if(carousel.getCarouselUrl()!=null && carousel.getCarouselUrl().indexOf("upload")>0){
            String url = carousel.getCarouselUrl().substring(carousel.getCarouselUrl().indexOf("upload")-1,carousel.getCarouselUrl().length());
            carousel.setCarouselUrl(url);
            System.out.println(url);
        }
        String result = petMallCarouselService.saveCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/carousels/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Carousel carousel) {
        if (Objects.isNull(carousel.getCarouselId())
                || StringUtils.isEmpty(carousel.getCarouselUrl())
                || Objects.isNull(carousel.getCarouselRank())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if(carousel.getCarouselUrl()!=null && carousel.getCarouselUrl().indexOf("upload")>0){
            String url = carousel.getCarouselUrl().substring(carousel.getCarouselUrl().indexOf("upload")-1,carousel.getCarouselUrl().length());
            carousel.setCarouselUrl(url);
            System.out.println(url);
        }
        String result = petMallCarouselService.updateCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/carousels/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        Carousel carousel = petMallCarouselService.getCarouselById(id);
        if (carousel == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(carousel);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/carousels/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (petMallCarouselService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}
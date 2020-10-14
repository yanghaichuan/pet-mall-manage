
package com.pet.mall.controller.admin;

import com.pet.mall.common.Constants;
import com.pet.mall.common.PetMallCategoryLevelEnum;
import com.pet.mall.common.ServiceResultEnum;
import com.pet.mall.entity.GoodsCategory;
import com.pet.mall.entity.PetMallGoods;
import com.pet.mall.service.PetMallCategoryService;
import com.pet.mall.util.PageQueryUtil;
import com.pet.mall.util.Result;
import com.pet.mall.util.ResultGenerator;
import com.pet.mall.service.PetMallGoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 托尼斯塔珂
 */
@Controller
@RequestMapping("/admin")
public class PetMallGoodsController {

    @Resource
    private PetMallGoodsService petMallGoodsService;
    @Resource
    private PetMallCategoryService petMallCategoryService;

    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_goods");
        return "admin/newbee_mall_goods";
    }

    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        //查询所有的一级分类
        List<GoodsCategory> firstLevelCategories = petMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), PetMallCategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<GoodsCategory> secondLevelCategories = petMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), PetMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<GoodsCategory> thirdLevelCategories = petMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), PetMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "goods-edit");
                return "admin/newbee_mall_goods_edit";
            }
        }
        return "error/error_5xx";
    }

    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        request.setAttribute("path", "edit");
        PetMallGoods petMallGoods = petMallGoodsService.getNewBeeMallGoodsById(goodsId);
        if (petMallGoods == null) {
            return "error/error_400";
        }
        if (petMallGoods.getGoodsCategoryId() > 0) {
            if (petMallGoods.getGoodsCategoryId() != null || petMallGoods.getGoodsCategoryId() > 0) {
                //有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                GoodsCategory currentGoodsCategory = petMallCategoryService.getGoodsCategoryById(petMallGoods.getGoodsCategoryId());
                //商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (currentGoodsCategory != null && currentGoodsCategory.getCategoryLevel() == PetMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    //查询所有的一级分类
                    List<GoodsCategory> firstLevelCategories = petMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), PetMallCategoryLevelEnum.LEVEL_ONE.getLevel());
                    //根据parentId查询当前parentId下所有的三级分类
                    List<GoodsCategory> thirdLevelCategories = petMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentGoodsCategory.getParentId()), PetMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    //查询当前三级分类的父级二级分类
                    GoodsCategory secondCategory = petMallCategoryService.getGoodsCategoryById(currentGoodsCategory.getParentId());
                    if (secondCategory != null) {
                        //根据parentId查询当前parentId下所有的二级分类
                        List<GoodsCategory> secondLevelCategories = petMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), PetMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                        //查询当前二级分类的父级一级分类
                        GoodsCategory firestCategory = petMallCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            //所有分类数据都得到之后放到request对象中供前端读取
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentGoodsCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (petMallGoods.getGoodsCategoryId() == 0) {
            //查询所有的一级分类
            List<GoodsCategory> firstLevelCategories = petMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), PetMallCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                //查询一级分类列表中第一个实体的所有二级分类
                List<GoodsCategory> secondLevelCategories = petMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), PetMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    //查询二级分类列表中第一个实体的所有三级分类
                    List<GoodsCategory> thirdLevelCategories = petMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), PetMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    request.setAttribute("firstLevelCategories", firstLevelCategories);
                    request.setAttribute("secondLevelCategories", secondLevelCategories);
                    request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }
        request.setAttribute("goods", petMallGoods);
        request.setAttribute("path", "goods-edit");
        return "admin/newbee_mall_goods_edit";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(petMallGoodsService.getNewBeeMallGoodsPage(pageUtil));
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody PetMallGoods petMallGoods) {
        if (StringUtils.isEmpty(petMallGoods.getGoodsName())
                || StringUtils.isEmpty(petMallGoods.getGoodsIntro())
                || StringUtils.isEmpty(petMallGoods.getTag())
                || Objects.isNull(petMallGoods.getOriginalPrice())
                || Objects.isNull(petMallGoods.getGoodsCategoryId())
                || Objects.isNull(petMallGoods.getSellingPrice())
                || Objects.isNull(petMallGoods.getStockNum())
                || Objects.isNull(petMallGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(petMallGoods.getGoodsCoverImg())
                || StringUtils.isEmpty(petMallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if(petMallGoods.getGoodsCoverImg()!=null && petMallGoods.getGoodsCoverImg().indexOf("upload")>0){
            String url = petMallGoods.getGoodsCoverImg().substring(petMallGoods.getGoodsCoverImg().indexOf("upload")-1,petMallGoods.getGoodsCoverImg().length());
            url=url.replace("upload","goods-img");
            petMallGoods.setGoodsCoverImg(url);
            petMallGoods.setGoodsCarousel(url);
            System.err.println(url);
        }
        String result = petMallGoodsService.saveNewBeeMallGoods(petMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody PetMallGoods petMallGoods) {
        if (Objects.isNull(petMallGoods.getGoodsId())
                || StringUtils.isEmpty(petMallGoods.getGoodsName())
                || StringUtils.isEmpty(petMallGoods.getGoodsIntro())
                || StringUtils.isEmpty(petMallGoods.getTag())
                || Objects.isNull(petMallGoods.getOriginalPrice())
                || Objects.isNull(petMallGoods.getSellingPrice())
                || Objects.isNull(petMallGoods.getGoodsCategoryId())
                || Objects.isNull(petMallGoods.getStockNum())
                || Objects.isNull(petMallGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(petMallGoods.getGoodsCoverImg())
                || StringUtils.isEmpty(petMallGoods.getGoodsDetailContent())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if(petMallGoods.getGoodsCoverImg()!=null && petMallGoods.getGoodsCoverImg().indexOf("upload")>0){
            String url = petMallGoods.getGoodsCoverImg().substring(petMallGoods.getGoodsCoverImg().indexOf("upload")-1,petMallGoods.getGoodsCoverImg().length());
            url=url.replace("upload","goods-img");
            petMallGoods.setGoodsCoverImg(url);
            petMallGoods.setGoodsCarousel(url);
            System.err.println(url);
        }
        String result = petMallGoodsService.updateNewBeeMallGoods(petMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/goods/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        PetMallGoods goods = petMallGoodsService.getNewBeeMallGoodsById(id);
        if (goods == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(goods);
    }

    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (petMallGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }

}

package com.pet.mall.service;

import com.pet.mall.entity.PetMallGoods;
import com.pet.mall.util.PageQueryUtil;
import com.pet.mall.util.PageResult;

import java.util.List;

public interface PetMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveNewBeeMallGoods(PetMallGoods goods);

    /**
     * 批量新增商品数据
     *
     * @param petMallGoodsList
     * @return
     */
    void batchSaveNewBeeMallGoods(List<PetMallGoods> petMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateNewBeeMallGoods(PetMallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    PetMallGoods getNewBeeMallGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

}

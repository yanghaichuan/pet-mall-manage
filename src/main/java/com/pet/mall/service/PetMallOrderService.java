
package com.pet.mall.service;

import com.pet.mall.entity.PetMallOrder;
import com.pet.mall.entity.PetOrderItem;
import com.pet.mall.util.PageQueryUtil;
import com.pet.mall.util.PageResult;

import java.util.List;

public interface PetMallOrderService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallOrdersPage(PageQueryUtil pageUtil);

    /**
     * 订单信息修改
     *
     * @param petMallOrder
     * @return
     */
    String updateOrderInfo(PetMallOrder petMallOrder);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    List<PetOrderItem> getOrderItems(Long id);
}


package com.pet.mall.dao;

import com.pet.mall.entity.PetOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetMallOrderItemMapper {
    int deleteByPrimaryKey(Long orderItemId);

    int insert(PetOrderItem record);

    int insertSelective(PetOrderItem record);

    PetOrderItem selectByPrimaryKey(Long orderItemId);

    /**
     * 根据订单id获取订单项列表
     *
     * @param orderId
     * @return
     */
    List<PetOrderItem> selectByOrderId(Long orderId);

    /**
     * 根据订单ids获取订单项列表
     *
     * @param orderIds
     * @return
     */
    List<PetOrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    /**
     * 批量insert订单项数据
     *
     * @param orderItems
     * @return
     */
    int insertBatch(@Param("orderItems") List<PetOrderItem> orderItems);

    int updateByPrimaryKeySelective(PetOrderItem record);

    int updateByPrimaryKey(PetOrderItem record);
}

package com.pet.mall.dao;

import com.pet.mall.entity.PetMallOrder;
import com.pet.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetMallOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(PetMallOrder record);

    int insertSelective(PetMallOrder record);

    PetMallOrder selectByPrimaryKey(Long orderId);

    PetMallOrder selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(PetMallOrder record);

    int updateByPrimaryKey(PetMallOrder record);

    List<PetMallOrder> findNewBeeMallOrderList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallOrders(PageQueryUtil pageUtil);

    List<PetMallOrder> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    int checkOut(@Param("orderIds") List<Long> orderIds);

    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    int checkDone(@Param("orderIds") List<Long> asList);
}
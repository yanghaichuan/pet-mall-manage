
package com.pet.mall.service.impl;

import com.pet.mall.common.PetMallOrderStatusEnum;
import com.pet.mall.common.ServiceResultEnum;
import com.pet.mall.dao.PetMallOrderItemMapper;
import com.pet.mall.dao.PetMallOrderMapper;
import com.pet.mall.entity.PetMallOrder;
import com.pet.mall.entity.PetOrderItem;
import com.pet.mall.service.PetMallOrderService;
import com.pet.mall.util.PageQueryUtil;
import com.pet.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PetMallOrderServiceImpl implements PetMallOrderService {

    @Autowired
    private PetMallOrderMapper petMallOrderMapper;
    @Autowired
    private PetMallOrderItemMapper petMallOrderItemMapper;

    @Override
    public PageResult getNewBeeMallOrdersPage(PageQueryUtil pageUtil) {
        List<PetMallOrder> petMallOrders = petMallOrderMapper.findNewBeeMallOrderList(pageUtil);
        int total = petMallOrderMapper.getTotalNewBeeMallOrders(pageUtil);
        PageResult pageResult = new PageResult(petMallOrders, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    @Transactional
    public String updateOrderInfo(PetMallOrder petMallOrder) {
        PetMallOrder temp = petMallOrderMapper.selectByPrimaryKey(petMallOrder.getOrderId());
        //不为空且orderStatus>=0且状态为出库之前可以修改部分信息
        if (temp != null && temp.getOrderStatus() >= 0 && temp.getOrderStatus() < 3) {
            temp.setTotalPrice(petMallOrder.getTotalPrice());
            temp.setUpdateTime(new Date());
            if (petMallOrderMapper.updateByPrimaryKeySelective(temp) > 0) {
                return ServiceResultEnum.SUCCESS.getResult();
            }
            return ServiceResultEnum.DB_ERROR.getResult();
        }
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkDone(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<PetMallOrder> orders = petMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (PetMallOrder petMallOrder : orders) {
                if (petMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += petMallOrder.getOrderNo() + " ";
                    continue;
                }
                if (petMallOrder.getOrderStatus() != 1) {
                    errorOrderNos += petMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行配货完成操作 修改订单状态和更新时间
                if (petMallOrderMapper.checkDone(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功的订单，无法执行配货完成操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String checkOut(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<PetMallOrder> orders = petMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (PetMallOrder petMallOrder : orders) {
                if (petMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += petMallOrder.getOrderNo() + " ";
                    continue;
                }
                if (petMallOrder.getOrderStatus() != 1 && petMallOrder.getOrderStatus() != 2) {
                    errorOrderNos += petMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行出库操作 修改订单状态和更新时间
                if (petMallOrderMapper.checkOut(Arrays.asList(ids)) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行出库操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单的状态不是支付成功或配货完成无法执行出库操作";
                } else {
                    return "你选择了太多状态不是支付成功或配货完成的订单，无法执行出库操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }

    @Override
    @Transactional
    public String closeOrder(Long[] ids) {
        //查询所有的订单 判断状态 修改状态和更新时间
        List<PetMallOrder> orders = petMallOrderMapper.selectByPrimaryKeys(Arrays.asList(ids));
        String errorOrderNos = "";
        if (!CollectionUtils.isEmpty(orders)) {
            for (PetMallOrder petMallOrder : orders) {
                // isDeleted=1 一定为已关闭订单
                if (petMallOrder.getIsDeleted() == 1) {
                    errorOrderNos += petMallOrder.getOrderNo() + " ";
                    continue;
                }
                //已关闭或者已完成无法关闭订单
                if (petMallOrder.getOrderStatus() == 4 || petMallOrder.getOrderStatus() < 0) {
                    errorOrderNos += petMallOrder.getOrderNo() + " ";
                }
            }
            if (StringUtils.isEmpty(errorOrderNos)) {
                //订单状态正常 可以执行关闭操作 修改订单状态和更新时间
                if (petMallOrderMapper.closeOrder(Arrays.asList(ids), PetMallOrderStatusEnum.ORDER_CLOSED_BY_JUDGE.getOrderStatus()) > 0) {
                    return ServiceResultEnum.SUCCESS.getResult();
                } else {
                    return ServiceResultEnum.DB_ERROR.getResult();
                }
            } else {
                //订单此时不可执行关闭操作
                if (errorOrderNos.length() > 0 && errorOrderNos.length() < 100) {
                    return errorOrderNos + "订单不能执行关闭操作";
                } else {
                    return "你选择的订单不能执行关闭操作";
                }
            }
        }
        //未查询到数据 返回错误提示
        return ServiceResultEnum.DATA_NOT_EXIST.getResult();
    }
    @Override
    public List<PetOrderItem> getOrderItems(Long id) {
        PetMallOrder petMallOrder = petMallOrderMapper.selectByPrimaryKey(id);
        if (petMallOrder != null) {
            List<PetOrderItem> orderItems = petMallOrderItemMapper.selectByOrderId(petMallOrder.getOrderId());
            //获取订单项数据
            if (!CollectionUtils.isEmpty(orderItems)) {
                return orderItems;
            }
        }
        return null;
    }
}

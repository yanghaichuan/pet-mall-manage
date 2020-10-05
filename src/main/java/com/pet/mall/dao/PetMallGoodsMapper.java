
package com.pet.mall.dao;

import com.pet.mall.entity.PetMallGoods;
import com.pet.mall.entity.StockNumDTO;
import com.pet.mall.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetMallGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(PetMallGoods record);

    int insertSelective(PetMallGoods record);

    PetMallGoods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(PetMallGoods record);

    int updateByPrimaryKeyWithBLOBs(PetMallGoods record);

    int updateByPrimaryKey(PetMallGoods record);

    List<PetMallGoods> findNewBeeMallGoodsList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoods(PageQueryUtil pageUtil);

    List<PetMallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<PetMallGoods> findNewBeeMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("newBeeMallGoodsList") List<PetMallGoods> petMallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);

}
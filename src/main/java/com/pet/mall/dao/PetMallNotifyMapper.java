package com.pet.mall.dao;

import com.pet.mall.entity.Carousel;
import com.pet.mall.entity.PetMallNotify;
import com.pet.mall.util.PageQueryUtil;

import java.util.List;

public interface PetMallNotifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PetMallNotify notify);

    int insertSelective(PetMallNotify notify);

    PetMallNotify selectByPrimaryKey(Long id);

    List<PetMallNotify> selectNotifyList(PageQueryUtil pageUtil);

    List<Carousel> findNotifyList(PageQueryUtil pageUtil);

    int getTotalNotify(PageQueryUtil pageUtil);

    int updateByPrimaryKeySelective(PetMallNotify notify);

    int updateByPrimaryKey(PetMallNotify notify);

    int deleteBatch(Integer[] ids);
}

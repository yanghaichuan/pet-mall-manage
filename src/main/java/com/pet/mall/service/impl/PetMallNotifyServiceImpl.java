
package com.pet.mall.service.impl;

import com.pet.mall.common.ServiceResultEnum;
import com.pet.mall.dao.PetMallNotifyMapper;
import com.pet.mall.entity.Carousel;
import com.pet.mall.entity.PetMallNotify;
import com.pet.mall.service.PetMallNotifyService;
import com.pet.mall.util.PageQueryUtil;
import com.pet.mall.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetMallNotifyServiceImpl implements PetMallNotifyService {

    @Autowired
    private PetMallNotifyMapper petMallNotifyMapper;

    @Override
    public PageResult getPetMallNotifyPage(PageQueryUtil pageUtil) {
        List<Carousel> carousels = petMallNotifyMapper.findNotifyList(pageUtil);
        int total = petMallNotifyMapper.getTotalNotify(pageUtil);
        PageResult pageResult = new PageResult(carousels, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String savePetMallNotify(PetMallNotify petMallNotify) {
        if (petMallNotifyMapper.insertSelective(petMallNotify) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updatePetMallNotify(PetMallNotify petMallNotify) {
        PetMallNotify temp = petMallNotifyMapper.selectByPrimaryKey(petMallNotify.getId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        temp.setNotifyType(petMallNotify.getNotifyType());
        temp.setNotifyText(petMallNotify.getNotifyText());
        temp.setStatus(petMallNotify.getStatus());
        temp.setNotifyRank(petMallNotify.getNotifyRank());
        if (petMallNotifyMapper.updateByPrimaryKeySelective(temp) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public PetMallNotify getPetMallNotifyById(Long id) {
        return petMallNotifyMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return petMallNotifyMapper.deleteBatch(ids) > 0;
    }
}

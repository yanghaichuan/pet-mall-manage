
package com.pet.mall.service;

import com.pet.mall.entity.PetMallNotify;
import com.pet.mall.util.PageQueryUtil;
import com.pet.mall.util.PageResult;

public interface PetMallNotifyService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getPetMallNotifyPage(PageQueryUtil pageUtil);

    String savePetMallNotify(PetMallNotify petMallNotify);

    String updatePetMallNotify(PetMallNotify petMallNotify);

    PetMallNotify getPetMallNotifyById(Long id);

    Boolean deleteBatch(Integer[] ids);
}

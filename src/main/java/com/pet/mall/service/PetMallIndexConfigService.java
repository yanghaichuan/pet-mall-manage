
package com.pet.mall.service;

import com.pet.mall.entity.IndexConfig;
import com.pet.mall.util.PageQueryUtil;
import com.pet.mall.util.PageResult;

public interface PetMallIndexConfigService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    Boolean deleteBatch(Long[] ids);
}

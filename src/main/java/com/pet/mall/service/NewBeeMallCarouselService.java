
package com.pet.mall.service;

import com.pet.mall.entity.Carousel;
import com.pet.mall.util.PageQueryUtil;
import com.pet.mall.util.PageResult;

public interface NewBeeMallCarouselService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Integer[] ids);
}

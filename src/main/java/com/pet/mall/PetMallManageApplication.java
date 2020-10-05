
package com.pet.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 托尼斯塔珂
 */
@MapperScan("com.pet.mall.dao")
@SpringBootApplication
public class PetMallManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetMallManageApplication.class, args);
    }

}

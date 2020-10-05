
package com.pet.mall.common;

public class PetMallException extends RuntimeException {

    public PetMallException() {
    }

    public PetMallException(String message) {
        super(message);
    }

    /**
     * 丢出一个异常
     *
     * @param message
     */
    public static void fail(String message) {
        throw new PetMallException(message);
    }

}

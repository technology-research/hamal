package dev.hamal.common.exception;

/**
 * @fileName: HamalException.java
 * @description: Hamal自定义异常
 * @author: by echo huang
 * @date: 2020-02-29 18:06
 */
public class HamalException extends RuntimeException {
    public HamalException(String message) {
        super(message);
    }
}

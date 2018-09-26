package io.falcon.fe.exceptions;

/**
 * @since 26.09.2018
 * Generic Payload Exception
 *
 */
public class PayloadException extends RuntimeException {
    public PayloadException(String message) {
        super(message);
    }
}

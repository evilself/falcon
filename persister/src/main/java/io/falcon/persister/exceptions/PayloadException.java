package io.falcon.persister.exceptions;

/**
 * @since 26.09.2018
 * Generic Exception, related to Payload operations
 *
 */
public class PayloadException extends RuntimeException {
    public PayloadException(String message) {
        super(message);
    }
}

package se.nordnet.orderbook.common.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidOrderSideException extends RuntimeException {
    public InvalidOrderSideException(String value) {
        super("Invalid order side %s".formatted(value));
    }
}

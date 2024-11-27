package se.nordnet.orderbook.common.api;

import se.nordnet.orderbook.order.OrderSide;

import java.util.Optional;

public class OrderSideRequest {

    private final String value;

    public OrderSideRequest(String value) {
        this.value = Optional.ofNullable(value)
                .orElse("unknown")
                .toUpperCase();
    }

    public OrderSide convert() {
        return switch (value) {
            case "BUY" -> OrderSide.BUY;
            case "SELL" -> OrderSide.SELL;
            default -> throw new InvalidOrderSideException(value);
        };
    }
}

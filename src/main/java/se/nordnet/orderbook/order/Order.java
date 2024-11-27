package se.nordnet.orderbook.order;

import java.util.UUID;

public record Order(
        UUID orderId,
        UUID customerId,
        String ticker,
        OrderSide orderSide,
        long volume,
        Price price) {
}

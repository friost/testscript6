package se.nordnet.orderbook.order.api;

import se.nordnet.orderbook.order.Order;
import se.nordnet.orderbook.order.OrderSide;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

record OrderResponse(
        UUID orderId,
        UUID customerId,
        String ticker,
        String orderSide,
        long volume,
        PriceResponse price) {

    record PriceResponse(
            String currency,
            BigDecimal price) {
    }

    static OrderResponse of(Order order) {
        return new OrderResponse(
                order.orderId(),
                order.customerId(),
                order.ticker(),
                getOrderSide(order.orderSide()),
                order.volume(),
                new OrderResponse.PriceResponse(
                        order.price().currency().toString(),
                        order.price().price()));
    }

    private static String getOrderSide(OrderSide orderSide) {
        return switch (orderSide) {
            case BUY -> "BUY";
            case SELL -> "SELL";
        };
    }
}

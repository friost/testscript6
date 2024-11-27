package se.nordnet.orderbook.order;

import java.math.BigDecimal;

public record Price(
        Currency currency,
        BigDecimal price) {
}

package se.nordnet.orderbook.summary;

import java.math.BigDecimal;

public record OrderSummary(
        PriceSummary price,
        long numberOfOrders) {

    public record PriceSummary(
            BigDecimal average,
            BigDecimal min,
            BigDecimal max) {
    }
}

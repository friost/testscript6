package se.nordnet.orderbook.summary.api;

import se.nordnet.orderbook.summary.OrderSummary;

import java.math.BigDecimal;

record SummaryResponse(
        PriceSummaryResponse price,
        long numberOfOrders) {

    record PriceSummaryResponse(
            BigDecimal average,
            BigDecimal min,
            BigDecimal max) {
    }

    static SummaryResponse of(OrderSummary summary) {
        return new SummaryResponse(
                new SummaryResponse.PriceSummaryResponse(
                        summary.price().average(),
                        summary.price().min(),
                        summary.price().max()),
                summary.numberOfOrders());
    }
}

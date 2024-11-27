package se.nordnet.orderbook.summary;

import se.nordnet.orderbook.order.Order;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

class SummaryFilter {

    private final FilterOptions filterOptions;

    SummaryFilter(FilterOptions filterOptions) {
        this.filterOptions = filterOptions;
    }

    List<Order> filter(List<Order> orders) {
        return orders.stream()
                .filter(this::filterOrder)
                .toList();
    }

    private boolean filterOrder(Order order) {
        return isTickerEquals(order) &&
                isOrderSideEquals(order);
    }


    private boolean isTickerEquals(Order order) {
        if (filterOptions.ticker() == null) {
            return true;
        }

        return order.ticker().equalsIgnoreCase(filterOptions.ticker());
    }

    private boolean isOrderSideEquals(Order order) {
        if (filterOptions.orderSide() == null) {
            return true;
        }

        return order.orderSide() == filterOptions.orderSide();
    }
}

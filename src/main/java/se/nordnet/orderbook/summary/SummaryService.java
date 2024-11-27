package se.nordnet.orderbook.summary;

import org.springframework.stereotype.Service;
import se.nordnet.orderbook.order.Order;
import se.nordnet.orderbook.common.repository.OrderRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;

@Service
public class SummaryService {

    private final OrderRepository orderRepository;

    public SummaryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderSummary getSummary(FilterOptions filterOptions) {
        var summaryFilter = new SummaryFilter(filterOptions);
        var orders = orderRepository.getOrders();
        var filteredOrders = summaryFilter.filter(orders);

        return new OrderSummary(
                getPriceSummary(filteredOrders),
                filteredOrders.size()
        );
    }

    private OrderSummary.PriceSummary getPriceSummary(List<Order> filteredOrders) {
        return new OrderSummary.PriceSummary(
                getAveragePrice(filteredOrders).orElse(null),
                getMinPrice(filteredOrders),
                getMaxPrice(filteredOrders));
    }

    private Optional<BigDecimal> getAveragePrice(List<Order> orders) {
        return orders.stream()
                .map(o -> o.price().price())
                .reduce(BigDecimal::add)
                .map(sum -> sum.divide(BigDecimal.valueOf(orders.size())))
                .map(value -> value.setScale(2, RoundingMode.HALF_EVEN));
    }

    private BigDecimal getMinPrice(List<Order> orders) {
        return orders.stream()
                .map(o -> o.price().price())
                .min(Comparator.naturalOrder())
                .orElse(ZERO);
    }

    private BigDecimal getMaxPrice(List<Order> orders) {
        return orders.stream()
                .map(o -> o.price().price())
                .max(Comparator.naturalOrder())
                .orElse(ZERO);
    }
}

package se.nordnet.orderbook.common.repository;

import se.nordnet.orderbook.order.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Order create(Order order);
    Optional<Order> get(UUID id);
    List<Order> getOrders();
}

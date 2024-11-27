package se.nordnet.orderbook.order.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.nordnet.orderbook.common.repository.OrderRepository;
import se.nordnet.orderbook.order.Currency;
import se.nordnet.orderbook.order.Order;
import se.nordnet.orderbook.order.OrderSide;
import se.nordnet.orderbook.order.Price;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
class OrderController {

    private final OrderRepository orderRepository;

    OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreationRequest orderCreationRequest) {
        var order = orderRepository.create(convert(orderCreationRequest));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrderResponse.of(order));
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderResponse> getOrder(@PathVariable UUID id) {
        return orderRepository.get(id)
                .map(OrderResponse::of)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private Order convert(OrderCreationRequest request) {
        return new Order(
                UUID.randomUUID(),
                request.customerId(),
                request.ticker(),
                request.orderSide().convert(),
                request.volume(),
                new Price(
                        Currency.valueOf(request.price().currency()),
                        request.price().price()));
    }
}

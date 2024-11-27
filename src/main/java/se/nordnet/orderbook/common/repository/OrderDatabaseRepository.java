package se.nordnet.orderbook.common.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import se.nordnet.orderbook.order.Currency;
import se.nordnet.orderbook.order.Order;
import se.nordnet.orderbook.order.OrderSide;
import se.nordnet.orderbook.order.Price;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Repository
//@Profile("database")
public class OrderDatabaseRepository implements OrderRepository {

    private static final Logger log = LoggerFactory.getLogger(OrderDatabaseRepository.class);

    private static final List<String> ORDER_FIELDS_LIST = List.of(
            "order_id",
            "customer_id",
            "ticker",
            "order_side",
            "volume",
            "currency",
            "price");

    private static final String ORDER_FIELDS = String.join(", ", ORDER_FIELDS_LIST);
    private static final String ORDER_FIELDS_AS_ARGS = ORDER_FIELDS_LIST.stream()
            .map(s -> ":" + s)
            .collect(Collectors.joining(", "));

    private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) ->
            new Order(
                    UUID.fromString(requireNonNull(rs.getString("order_id"))),
                    UUID.fromString(requireNonNull(rs.getString("customer_id"))),
                    requireNonNull(rs.getString("ticker")),
                    OrderSide.valueOf(rs.getString("order_side")),
                    rs.getLong("volume"),
                    new Price(Currency.valueOf(rs.getString("currency")),
                            requireNonNull(rs.getBigDecimal("price")))
            );

    private static final String CREATE_ORDER =
            "insert into orders (" + ORDER_FIELDS + ") " +
                    "values (" + ORDER_FIELDS_AS_ARGS + ") " ;

    private static final String GET_ORDER =
            "select " + ORDER_FIELDS + " " +
                    "from orders " +
                    "where order_id = :order_id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrderDatabaseRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = jdbcTemplate;
    }

    public Order create(Order order) {
        namedParameterJdbcTemplate.update(CREATE_ORDER, orderParameterSource(order));
        List<Order> result = namedParameterJdbcTemplate.query(GET_ORDER, Map.of("order_id", order.orderId()), ORDER_ROW_MAPPER);
        if (result.size() != 1) {
            throw new IllegalStateException("Expected 1 changed element, was " + result.size());
        }
        return result.get(0);
    }

    public Optional<Order> get(UUID id) {
        List<Order> result = namedParameterJdbcTemplate.query(GET_ORDER, Map.of("order_id", id), ORDER_ROW_MAPPER);
        if (result.size() != 1) {
            log.debug("Expected 1 element, got " + result.size());
            return Optional.empty();
        }
        return result.stream().findFirst();
    }

    @Override
    public List<Order> getOrders() {
        return namedParameterJdbcTemplate.query("select * from orders",
                Map.of(),
                ORDER_ROW_MAPPER);
    }

    private MapSqlParameterSource orderParameterSource(Order order) {
        return new MapSqlParameterSource()
                .addValue("order_id", order.orderId())
                .addValue("customer_id", order.customerId())
                .addValue("ticker", order.ticker())
                .addValue("order_side", order.orderSide().toString())
                .addValue("volume", order.volume())
                .addValue("currency", order.price().currency().toString())
                .addValue("price", order.price().price());
    }
}

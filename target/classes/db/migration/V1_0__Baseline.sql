create table orders
(
    order_id    uuid primary key not null,
    customer_id uuid             not null,
    order_side  varchar          not null,
    ticker      varchar          not null,
    volume      numeric          not null,
    currency    varchar          not null,
    price       numeric          not null
);

create table instruments
(
    instrument_id    uuid primary key not null,
    instrument_name  varchar          not null,
    ticker          varchar     not null,
    last_price      numeric          not null,
    currency      varchar          not null
);

create index customer_id_idx on orders (customer_id);
create index instrument_id_idx on orders (ticker);

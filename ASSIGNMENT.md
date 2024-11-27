<img width="100" style="float:right;" src="nordnet_logo.png"/>

# Nordnet Home Assignment


## Instructions

We would like you to implement a very simplified [order book](https://www.investopedia.com/terms/o/order-book.asp) [1] as a microservice in Java.
The service should implement the endpoints below according to the given specification. If something is unclear make an assumption.
Add a README explaining how to run the application.

Recommended stack:

* Maven
* Spring Boot, https://start.spring.io/
* A database is not a requirement, but if you do use one we recommend SQLite on the filesystem or a PostgreSQL/MySQL database running using docker-compose.
* docker-compose or just a self-contained application.

Every endpoint where it makes sense should communicate in json over http.


## Endpoints

### Creating an order

An order should contain a ticker, e.g. `SAVE` for Nordnet, `GME` for GameStop, `TSLA` for Tesla, an order side indicating if the order is for buying or selling, 
a volume indicating how many stocks to purchase and price information indicating at which price to buy or sell and in which currency.


### Fetching an order

Should return an order given an `id` or a sensible response indicating an error.


### Fetching summary

Calculates average/max/min price and the number of orders on that order side for a given ticker and date.


## Assessment

When we assess your assignment we will look at these things:

* How you model your domain objects
* How you interact with the libraries you use
* How you design your API
* How you handle persistence
* How you verify behavior


[1] https://www.investopedia.com/terms/o/order-book.asp

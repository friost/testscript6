FROM nordnet.jfrog.io/docker-hub/amazoncorretto:22

ADD target/order-handler-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
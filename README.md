//TODO convert to .md

1. Have java and maven installed (verify by below)
java -version
should be 17+

mvn -version
Maven 3.8+


2. Build Both Services

cd inventory-service
mvn clean install

cd ../order-service
mvn clean install


3: Start Inventory Service (FIRST) (port 8081)


cd inventory-service
mvn spring-boot:run


You should see logs like:

verify by : 
http://localhost:8081/h2-console

http://localhost:8081/inventory/1005


4: Start Order Service (port 8082)


cd order-service
mvn spring-boot:run


test by bash (SWAGGER coming soon)


curl -X POST http://localhost:8082/order \
  -H "Content-Type: application/json" \
  -d '{
        "productId": 1005,
        "quantity": 3
      }'




# microservices-practice
Created microservices example for practice, followed from in28minutes tutorial.
This project consists of 2 microservices - currency-exchange-service and currency-conversion-service, an API-Gateway and a naming/eureka server.

# Features
This example consists of  below features:
-  Eureka naming server/**Service discovery**.
-  **Load Balancing** using spring cloud.
-  Fault tolerance using **Resilience4j circuit breaker**.
-  Logging and Routing using **Spring cloud api gateway**.
- Distributed tracing using **Zipkin**.

# Docker
## Images

- https://hub.docker.com/u/shalakap02
- Currency Exchange Service
-- shalakap02/shalakap02/microservices-currency-exchange-service:0.0.1-SNAPSHOT
- Currency Conversion Service
-- shalakap02/microservices-currency-conversion-service:0.0.1-SNAPSHOT
- Eureka
-- shalakap02/microservices-eureka-naming-server:0.0.1-SNAPSHOT
- API GATEWAY
-- shalakap02/microservices-api-gateway:0.0.1-SNAPSHOT

## Run Project
- Run the project using command :
**docker-compose up**

# Summary
Below are api/port details :

-- PORTS :

- Currency exchange service - 8000,8001..
- Currency conversion service - 8100, 8101, 8102..

- Spring cloud config server - 8888
- NetFlix eureka naming server - 8761
- Api gateway - 8765
- Zipkin distributed tracing serve -9411


-- URLS :

Currency Exchange -
	http://localhost:8000/currency-exchange/from/USD/to/INR

	{
	"id": 1001,
	"from": "USD",
	"to": "INR",
	"conversionMultiple": 65,
	"environment": "8000"
	}

Currency Conversion -
	http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
	
	{
	"id": 10,
	"from": "USD",
	"to": "INR",
	"quantity": 10,
	"conversionMultiple": 65,
	"totalCalculatedAmount": 650,
	"environment": "8000"
	}
	
	
Eureka Server -
	http://localhost:8761/
	
API Gateway -

	- http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10
	- http://localhost:8765/currency-exchange/from/USD/to/INR
	- http://localhost:8765/currency-conversion/feign/from/USD/to/INR/quantity/10
	
Zipkin - 
run command : docker run -p 9411:9411 openzipkin/zipkin
- http://localhost:9411/zipkin/

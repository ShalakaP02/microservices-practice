# microservices-practice
Created microservices example for practice, followed from in28minutes tutorial.
Created two microservices (currency-exchange-service and currency-conversion-service) with eureka server.

# Features
This example consists of  below features:
-  Eureka naming server/**Service discovery**.
-  **Load Balancing** using spring cloud.
-  Fault tolerance using **Resilience4j circuit breaker**.
-  Logging and Routing using **Spring cloud api gateway**.

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

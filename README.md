# sample-microservices-spring-boot

Hello everybody, this is an implementation Spring Boot based on these stack:
  * Spring Cloud Config
  * Spring Netflix Eureka
  * Spring Cloud Gateway
  * Spring Cloud Load-balancing
  * Spring Resilience4j
  * Spring Cloud Sleuth and Zipkin Client
The config repo here: 
---
## How to Run this project:

  1. Change the url in sample-microservices-spring-boot/configserver/src/main/resources/ to https clone:
[link](https://github.com/lenguyenkhoi21/configserver-file.git)

  2. With Docker: 
  * docker-compose build
  * docker-compose up –d
  
   With IDE (Vscode, IntelliJ, Escliped,…)
  * Open the termial in folder sample-microservices-spring-boot/configserver
  * Run the termial in folder sample-microservices-spring-boot/erukaservice
  * Run these services
  * Run the Gateway
  * Call API form Gateway

  Each project, you can run with gradle command: gradlew :bootRun or gradle :bootRun
  ---
  *Thanks to watching, if you like this project, you can fork this project.*

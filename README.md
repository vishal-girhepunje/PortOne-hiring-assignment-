## Portone Assignment
<img src="https://ruby.mobidev.biz/posts/stripe-payment-gateway-integration-in-ruby-on-rails-app/cover.png" alt="" style="margin: auto; display: block;">

## Stripe Payment Gateway Integration

 **REST API SERVICE**

The **Portone Assignment** is a REST API and it is integrate the Stripe Payment Gateway which allows to perform the 
- Create Payment Intent
- Capture the Created Payment Intent
- Create the Refund
- Get All Payment Intent.

The **Portone Assignment** REST API simplified Exception Handling for layman's easy understanding. 

The API's primary objective is to provide to the customer **with a seamless experience and user-friendly environment during online payment.**

The API's Services consist the following points with HTTP Methods:
|S.No. | End Point | HTTP Method |
|:-----:|:-------|:----------:|
| 1 | ```/api/v1/create_intent``` | POST |
| 2 | ```/api/v1/capture_intent/{id}``` | POST |
| 3 | ```/api/v1/create_refund/{id}``` | POST |
| 4 | ```/api/v1/get_intents``` | GET |

## Feature
- Create Payment
- Capture Payment
- Refund Payment
- Get All Payment Information

## Tech Stack

- JAVA
- SPRINGBOOT
- MAVEN
- SWAGGER UI

## Dependencies

- SPRING BOOT DEVTOOLS
- SPRING WEB
- LOMBOK
- SWAGGER UI
  
## Modules

- Payment Intent Module
- Capture Module
- Refund Module
- Exception Handler Module

  
## System Structure

The REST API allows a Customer to **Create Payment**, **Capture Payment** **Refund Payment** & **Get All Payment Information** through the **browser** with appropriate Http Methods.

## About
This assignment is for **portone.io**, This is an online accept the payment (payment gateway) Rest API. We built this Rest API assignment Within 2 days and Please [click here for Demo Video](https://drive.google.com/file/d/1h5YWO9Tjh-r-Aey1Fn6W5U5pkTKz143j/view?usp=sharg).

## Installation And Run Project

- copy this https://github.com/vishal-girhepunje/PortOne-assignment.git
- Select path where you want to store the project in your pc
- open the corresponding file / folder with editor
- open terminal of your editor
- use command --> git clone (paste link) <-- 
- after project cloned to your folder
- go to StripePaymentGatewayIntegrationApplication.java file inside io.portone folder/package
- Before the run this project, ensure LOMBOK should be proper installed in the IDE.
- run as Spring Boot
- This application run on the port 8080
- After the project run, [click here](http://localhost:8080/swagger-ui/index.html) to view the project in swagger-ui

### Postman link of Stripe Payment Gateway Integration
click here to see in the [Postman](https://www.postman.com/material-geologist-27820143/workspace/stripe-paymen-gateway).

### Deploy Link
[Click here]([http://34.0.8.234:402/swagger-ui/index.html](https://portone-hiring-assignment-production.up.railway.app/)) to see deploy in RailwayApp.

## Screenshot

### PostMan UI Page
<img src="Images/Screenshot 2024-06-19 230953.png" alt="Postman UI" />

### Payment Intent Parameter
<img src="Images/Screenshot 2024-06-19 231029.png" alt="Payment Intent Create" />

### Payment Capture Parameter
<img src="Images/Screenshot 2024-06-19 231252.png" alt="Payment capture Create" />

### Refund Payment Parameter
<img src="Images/Screenshot 2024-06-19 231204.png" alt="Refund Payment Create" />

### Response Body
<img src="images/Successfull response.PNG" alt="Successfull response" />

### Schemas
<img src="images/Schemas.PNG" alt="Schemas" />

Links to GitHub code with setup/exec instructions in Readme
Link to Postman collection for the APIs


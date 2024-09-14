# Bank Loan Microservices System

## Description

This system allows users to apply for a bank loan by filling out a form on the bank's website and attaching the required documents. The system consists of several microservices working together to handle loan requests, validate them, calculate payments, store documents, and log activities.

## System Diagram

[![image.png](https://i.postimg.cc/sxgCLR52/image.png)](https://postimg.cc/N9SnySJh)

## Microservices

- **Main Bank Loan Microservice**
  - Manages the loan application process and coordinates between different services.
  
- **Request Validator Microservice**
  - Validates loan requests to ensure they meet the required criteria.

- **Payment Calculator Microservice**
  - Calculates the monthly payment amount for the loan based on the user's input and loan terms.

- **Document Storage Microservice**
  - Stores PDF documents attached to the loan request in an object storage database.

- **Logger Microservice**
  - Logs activities and events for tracking and monitoring purposes.

- **RabbitMQ**
  - Acts as a message broker to enable asynchronous communication between microservices.

- **Loan Service Database**
  - Stores data related to loan requests, including risk scores and user information.

## Business Logic

1. **Frontend to Main Bank Loan Microservice:**
   - A frontend application sends an HTTP request to the **Main Bank Loan Microservice**, including the entered data and attached documents. (In this case we use Postman).

2. **Validation:**
   - **Main Bank Loan Microservice** forwards the request to the **Request Validator Microservice** via HTTP to validate the loan request.
   - **Request Validator Microservice** performs validation and sends the result back to the **Main Bank Loan Microservice**.

3. **Payment Calculation:**
   - **Main Bank Loan Microservice** forwards the validated request data to the **Payment Calculator Microservice** to calculate the monthly payment.

4. **Document Storage:**
   - **Main Bank Loan Microservice** uses the **Document Storage Microservice** to store PDF documents attached to the loan request.

5. **Asynchronous Messaging:**
   - **Main Bank Loan Microservice** and **Request Validator Microservice** send messages to **RabbitMQ** using the AMQP protocol for asynchronous processing and communication.

6. **Logging:**
   - **RabbitMQ** forwards the messages to the **Logger Microservice**.
   - **Logger Microservice** logs the information and stores it in the **Log Database**.

7. **Frontend Response:**
   - The result of the loan request is sent back to the frontend application.
   - The frontend displays the result, a simple message if successful or an error message if the validation failed.



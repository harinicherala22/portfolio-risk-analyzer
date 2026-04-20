# Portfolio Risk Analyzer

A secure Spring Boot backend application for managing investment portfolios and calculating risk insights.

## 🚀 Features

- User Registration & Login
- JWT Authentication
- Add / View / Delete Portfolio Assets
- Portfolio Summary Calculation
- Risk Score Engine (Low / Medium / High)
- Global Exception Handling
- Swagger API Documentation
- Unit Testing with JUnit & Mockito

## 🛠 Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT
- MySQL
- JPA / Hibernate
- Maven
- Swagger OpenAPI
- JUnit 5
- Mockito

## 📌 API Endpoints

### Auth APIs

- POST /api/auth/register
- POST /api/auth/login

### Portfolio APIs

- POST /api/portfolio/add
- GET /api/portfolio/all
- DELETE /api/portfolio/delete/{id}
- GET /api/portfolio/summary
- GET /api/portfolio/risk

## ▶️ Run Locally

1. Clone repository
2. Create MySQL database:

```sql
create database portfolio_db;

3.Update application.properties
4.Run Spring Boot app

🔮 Future Enhancements
Sharpe Ratio
Volatility Analytics
Redis Caching
Docker Deployment
AWS Hosting

👩‍💻 Author
Harini Cherala

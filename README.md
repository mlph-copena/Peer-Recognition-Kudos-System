# Peer Recognition & Kudos System

**Junior Java Training Program Final Project**

A system where anyone can submit kudos or comments to employees or teams, with an admin panel to manage employees and track kudos.

---

## How I Built This Project

### 1. Project Setup

- Created a new **Spring Boot** project in **IntelliJ IDEA**.
- Added dependencies for:
    - Spring Web
    - Spring Data JPA
    - MySQL Driver
    - Lombok
    - Spring Security (for Admin login)
    - Swagger (for API documentation)

### 2. Database Design

- Used **MySQL** as the database.
- Created tables for:
    - `employees` (id, name, email, department, teams)
    - `teams` (id, name)
    - `kudos` (id, sender_name, target_employee/team, message, anonymous, date)
    - `admin` (id, email, password)
- Configured `application.properties` to connect Spring Boot to MySQL.

### 3. Admin Panel

- Implemented **CSV upload** for employees using `MultipartFile`.
- Added validation to prevent duplicate employees.
- Implemented **login functionality** for Admin with Spring Security.
- Added optional **reset kudos counts** functionality.

### 4. Kudos & Comments System

- Public form to submit kudos/comments.
- Options for:
    - Sending to individual employees or entire teams.
    - Anonymous submissions.
- Validations to prevent:
    - Sending to non-existent employees/teams.
    - Duplicate submissions from the same sender per day.

### 5. Public Search & Leaderboards

- Created REST API endpoints to:
    - Search employees or teams by name.
    - Display recent kudos/comments.
    - Display top 5 employees/teams by kudos (monthly).
- Added filtering by department or team.

### 6. Testing

- Wrote unit tests for service layer methods.
- Tested API endpoints using **Swagger UI**.

### 7. Version Control

- Initialized Git repository and pushed project to **GitHub**.
- Committed incrementally with descriptive messages for each feature.

---

## How to Run the Project

1. **Clone the repository:**

```bash
git clone https://github.com/mlph-copena/Peer-Recognition-Kudos-System.git
cd YourRepo

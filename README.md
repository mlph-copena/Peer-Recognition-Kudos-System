🏆 Kudos System – Java & PHP Integration Project
📘 Overview

This project was developed as part of the [Java Training] Project Presentation by Christian Marty Opeña.
It showcases an employee recognition system (Kudos System) built using Java (Spring Boot), PHP, and MySQL, running under XAMPP with PHPMyAdmin for database management.

The system allows users to manage employees and teams, send kudos, and view leaderboards — integrating both backend logic and database operations through Java and MySQL, with a PHP-based UI.

🧩 Project Architecture
Technology Stack
Layer	Technology / Tool
Frontend (UI)	Pure PHP
Backend	Java (Spring Boot)
Database	MySQL
Database Management	PHPMyAdmin
Local Server	XAMPP
Development Tools	IntelliJ IDEA / VS Code
Concepts Used	DTOs, Domain, Repository Pattern, Error Handling
⚙️ System Features
Employee & Team Management

Add or update employee/team data via CSV file upload

CSV validation includes:

Duplicate entry detection

Missing field validation

Error/success logging after upload

Kudos Sending & History

Employees can send kudos with optional comments

Each kudo is logged and reflected in the history table and database

Kudos are displayed on the leaderboard

Leaderboard

Displays Top 5 Employees and Top 5 Teams

Filterable by department: Operations, Support, Marketing, and HR

Error Handling

Detects duplicate employees in CSV uploads

Displays user-friendly messages for upload results

Logs all invalid, successful, and duplicate entries

🏗️ Application Flow

Admin uploads CSV file containing employee and team data.

Backend validates and stores records in MySQL through Spring Boot services.

Employees can log in via the PHP interface to send kudos or view leaderboards.

Leaderboard dynamically updates based on kudos count.

🧰 Installation & Setup Guide
1. Prerequisites

Make sure you have the following installed:

🖥️ XAMPP (for Apache + PHPMyAdmin + MySQL)

☕ Java JDK 17+

🧩 Maven (for building Spring Boot)

💾 Git

💡 IDE (e.g., IntelliJ IDEA, VS Code)

2. Database Setup

Open XAMPP Control Panel → Start Apache and MySQL.

Access PHPMyAdmin via http://localhost/phpmyadmin
.

Create a database named:

kudos_db


Import the kudos_schema.sql file into this database.

3. Run the Backend

Navigate to the java-backend directory.

Run:

mvn spring-boot:run

Spring Boot will start on http://localhost:8080

Copy the php-ui folder into htdocs inside your XAMPP installation.
Example path:

C:\xampp\htdocs\kudos-system\


Access the UI via:
http://localhost/kudos-system

✅ Key Highlights

Applied Domain-Driven Design (DDD) concepts:

DTOs (Data Transfer Objects)

Domain separation

Repository pattern

Used Spring Boot for backend logic and PHP for presentation layer

Integrated MySQL database using PHPMyAdmin under XAMPP

Included error handling for duplicate or missing CSV data

💬 Feedback Summary (from Ma. Clarissa Estremos)
Positive Points

Commended structured backend using DTOs, domains, and repositories.

Recognized strong backend logic and data integration.

Areas for Improvement

Replace the server-side PHP UI with a modern front-end framework (React, Vue, Angular, or Next.js).

Add friendlier user messages and modal popups for CSV upload feedback.

Implement role-based access (separate employee and admin pages).

Enhance UI design and consistency for a better user experience.

🚀 Next Steps

Document all feedback and improvements (✅ Done)

Push final codebase to GitHub by Monday, 9:00 a.m.

Research and apply a modern UI framework (React, Vue, Angular, or Next.js)

Add success/error modals for user feedback

Refactor UI for better UX and visual design

🧠 Lessons Learned

Importance of clear architecture between backend, UI, and database.

Benefits of DTOs and repositories for clean, maintainable Java code.

Learned how PHPMyAdmin and Spring Boot can work together efficiently.

Realized how modern front-end frameworks can improve UX and client perception.

Understood the importance of regular communication in project development.

👤 Author

Christian Marty Opeña
Java Training Project – Kudos System
Monstarlab Philippines
📅 October 2025
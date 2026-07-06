
# Enterprise Resource Allocation & Skill Management System (ERASM)

The Enterprise Resource Allocation & Skill Management (ERASM) Platform is a modern enterprise backend application built with Spring Boot and Java 17. It provides a centralized system for employee management, skill tracking, project management, resource allocation, approval workflows, utilization reporting, and auditing.

The application follows layered architecture and enterprise development principles, utilizing Spring Data JPA, Spring Security, JWT authentication, DTO/Mapper patterns, global exception handling, and structured logging.

## Technology Stack

- Java 17
- Spring Boot 3.3.2
- Spring Data JPA / Hibernate
- MySQL
- Spring Security
- JWT (jjwt)
- BCrypt
- SLF4J + Logback
- JUnit 5
- Mockito
- Maven

## Features

### Authentication
- Register
- Login
- Logout
- JWT-based session handling
- Role-Based Access Control (RBAC)

### User Management
- Register User
- Update User
- Delete User
- Change Password
- Assign Roles

### Skill Management
- Add Skill
- Update Skill
- Delete Skill
- View Skill List

### Employee Skill Profile
- Add Skill
- Update Skill Level
- Add Experience
- Add Certifications

### Project Management
- Create Project
- Update Project
- Close Project
- Assign Technologies

### Resource Request Management
- Raise Resource Requests
- Define Required Skills & Headcount

### Approval Workflow
- Draft → Submitted → Resource Manager Review → Approved → Allocated → Completed

### Resource Allocation
- Allocate Employee
- Reallocate Employee
- Release Employee
- 100% Allocation Cap Validation

### Utilization Dashboard
- Billable %
- Bench %

### Audit
- Audit Logs
- Created/Modified By & Timestamps

### Reports
- Skill Report
- Utilization Report
- Project Allocation Report

## Project Structure


com.erasm/
├── controller/      # REST endpoints
├── service/         # Business logic interfaces
├── serviceimpl/      # Business logic implementations
├── repository/      # Spring Data JPA repositories
├── entity/          # JPA entities
├── dto/             # Request/response DTOs
├── mapper/          # Entity <-> DTO mapping
├── security/        # JWT filter, UserDetailsService, SecurityConfig
├── exception/        # Global exception handler + custom exceptions
├── dashboard/        # Utilization dashboard controller/service
└── Erasm1Application.java


## Data Architecture

Application state is persisted using Spring Data JPA with MySQL.

### Core Entities
- User
- Role
- Employee
- Skill
- EmployeeSkill
- Certification
- Project
- ResourceRequest
- Allocation
- AuditLog

### Entity Relationships
- **One-to-One**: User ↔ Employee
- **One-to-Many**: Project → ResourceRequest
- **Many-to-One**: Employee → Role
- **Many-to-Many**: Employee ↔ Skill

## API Routing

### Authentication Routes
- POST /auth/register
- POST /auth/login

### Protected Routes
- /api/users
- /api/employees
- /api/employee-skills
- /api/skills
- /api/skill-matching/{skillName}
- /api/projects
- /resource-requests
- /api/allocations
- /roles
- /certifications
- /api/auditlogs
- /api/search
- /dashboard

Role-based access is implemented using:
- @PreAuthorize("hasRole('ADMIN')")
- @PreAuthorize("hasRole('MANAGER')")
- Custom AuthenticationEntryPoint & AccessDeniedHandler

## Database

The application connects to a live MySQL database (no mock data layer).

### Tables
- users
- roles
- employees
- skills
- employee_skills
- certifications
- projects
- resource_requests
- allocations
- audit_logs

## Performance & Reliability

The application includes the following non-functional guarantees:
- API response time under 2 seconds
- Support for 1000+ concurrent users
- Layered, reusable architecture for maintainability
- Centralized error handling
- Structured logging

## Error Handling

Implemented error handling includes:
- Global Exception Handler (@RestControllerAdvice)
- Custom Exceptions: UserNotFoundException, ResourceNotFoundException, SkillNotFoundException, AllocationException
- Validation Exception Handler
- Consistent API error responses

## Logging

SLF4J + Logback, writing to `logs/erasm.log`.

- **INFO** — user login, project creation, resource allocation
- **WARN** — invalid requests, unauthorized access attempts
- **ERROR** — system exceptions, database failures
- Passwords, JWT tokens, and personal information are never logged

## Testing

Testing is implemented using:
- JUnit 5
- Mockito
- Spring Security Test

Covered modules include:
- Controllers (User, Employee, Project, Allocation, Skill, Resource Request, Authentication, etc.)
- Services (Skill, Allocation, Resource Request, User, Audit Log, Skill Matching, Auth, Project, Employee)
- Global Exception Handler
- Validation Exception Handler
- Application Context Load

## Prerequisites

Before running the project, ensure you have installed:
- Java 17 or later
- Maven 3.8 or later
- MySQL 8 or later

## Installation

Clone the repository:
bash
git clone https://github.com/nitheshnithesh596-lgtm/ERASM1.git


Navigate to the project directory:bash
cd ERASM1


Configure the database in `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/erasm
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Build the project:
```bash
./mvnw clean install
```

Run the application:
```bash
./mvnw spring-boot:run
```

Run the test suite:
```bash
./mvnw test
```

The API will start on `http://localhost:8080`.

## Project Deliverables

- Source Code
- Documentation (Project Overview, ER Diagram, API List, Screenshots, Test Cases)
- SQL Database Creation Script
- Postman Collection
- JUnit Test Execution Report

## Future Enhancements

- Frontend Integration (React Dashboard)
- Real-Time Notifications
- Multi-Tenant Support
- Advanced Analytics Dashboard
- Export Reports (PDF & Excel)
- Email Notifications for Approvals
- CI/CD Pipeline Integration

## Author

Nithesh J

## License

This project is developed for educational, demonstration, and portfolio purposes.

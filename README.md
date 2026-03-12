# Field Employee Management System

A Spring Boot application for managing field employees, their locations, tasks, attendance, and skills.

## Features

✅ **7 Database Tables** with proper relationships  
✅ **One-to-One** relationship (Employee ↔ EmployeeProfile)  
✅ **One-to-Many** relationships (Province → Employee, Site → Employee, etc.)  
✅ **Many-to-Many** relationship (Employee ↔ Skill)  
✅ **Pagination and Sorting** for employee listing  
✅ **Location Management** (Province/Location saving)  
✅ **existsBy()** methods for validation  
✅ **Province-based Employee Retrieval** (by code or name)  

## Technologies

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven

## Prerequisites

1. Java 17 or higher
2. PostgreSQL 12 or higher
3. Maven 3.6+
4. Postman (for API testing)

## Database Setup

1. Install and start PostgreSQL
2. Create database:
```sql
CREATE DATABASE field_employee_db;
```

3. Update credentials in `src/main/resources/application.properties` if needed:
```properties
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## Running the Application

### Option 1: Using Maven
```bash
cd field-employee-management
mvn clean install
mvn spring-boot:run
```

### Option 2: Using IDE
1. Open project in IntelliJ IDEA or Eclipse
2. Run `FieldEmployeeManagementApplication.java`

The application will start on **http://localhost:8080**

## Testing with Postman

### Import Collection
1. Open Postman
2. Click Import
3. Select `Postman_Collection.json` from project root
4. Collection will be imported with all API endpoints

### Test Sequence

**Step 1: Create Province**
```
POST http://localhost:8080/api/provinces
Body:
{
    "code": "WP",
    "name": "Western Province"
}
```

**Step 2: Create Site**
```
POST http://localhost:8080/api/sites
Body:
{
    "name": "Construction Site A",
    "location": "Colombo",
    "description": "Main construction site"
}
```

**Step 3: Create Skill**
```
POST http://localhost:8080/api/skills
Body:
{
    "name": "Welding",
    "description": "Metal welding certification"
}
```

**Step 4: Create Employee**
```
POST http://localhost:8080/api/employees
Body:
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phone": "0771234567",
    "jobRole": "Technician",
    "department": "Maintenance",
    "status": "Active",
    "provinceId": 1,
    "siteId": 1,
    "skillIds": [1]
}
```

**Step 5: Get Employees with Pagination**
```
GET http://localhost:8080/api/employees/paginated?page=0&size=5&sortBy=firstName&direction=asc
```

**Step 6: Get Employees by Province Code**
```
GET http://localhost:8080/api/employees/province/code/WP
```

**Step 7: Get Employees by Province Name**
```
GET http://localhost:8080/api/employees/province/name/Western Province
```

## API Endpoints

### Province APIs
- `POST /api/provinces` - Create province
- `GET /api/provinces` - Get all provinces
- `GET /api/provinces/{id}` - Get province by ID

### Employee APIs
- `POST /api/employees` - Create employee
- `GET /api/employees` - Get all employees
- `GET /api/employees/paginated` - Get employees with pagination and sorting
- `GET /api/employees/province/code/{code}` - Get employees by province code
- `GET /api/employees/province/name/{name}` - Get employees by province name

### Site APIs
- `POST /api/sites` - Create site
- `GET /api/sites` - Get all sites

### Skill APIs
- `POST /api/skills` - Create skill
- `GET /api/skills` - Get all skills

## Project Structure

```
src/main/java/com/fieldmanagement/system/
├── entity/              # JPA Entities (7 tables)
│   ├── Province.java
│   ├── Employee.java
│   ├── EmployeeProfile.java
│   ├── Site.java
│   ├── Task.java
│   ├── Attendance.java
│   └── Skill.java
├── repository/          # Spring Data JPA Repositories
│   ├── ProvinceRepository.java
│   ├── EmployeeRepository.java
│   ├── SiteRepository.java
│   ├── SkillRepository.java
│   ├── TaskRepository.java
│   ├── AttendanceRepository.java
│   └── EmployeeProfileRepository.java
├── service/             # Business Logic
│   ├── ProvinceService.java
│   ├── EmployeeService.java
│   ├── SiteService.java
│   └── SkillService.java
├── controller/          # REST Controllers
│   ├── ProvinceController.java
│   ├── EmployeeController.java
│   ├── SiteController.java
│   └── SkillController.java
├── dto/                 # Data Transfer Objects
│   ├── ProvinceDTO.java
│   └── EmployeeDTO.java
└── FieldEmployeeManagementApplication.java
```

## Assessment Criteria Coverage

✅ **ERD with 5+ tables** - 7 tables implemented  
✅ **Location saving** - Province entity with save logic  
✅ **Sorting** - Implemented using Sort in Spring Data JPA  
✅ **Pagination** - Implemented using Pageable  
✅ **Many-to-Many** - Employee ↔ Skill with join table  
✅ **One-to-Many** - Province → Employee, Site → Employee  
✅ **One-to-One** - Employee ↔ EmployeeProfile  
✅ **existsBy()** - existsByCode(), existsByEmail()  
✅ **Province-based retrieval** - By code and name  

## Documentation Files

- `ERD_DOCUMENTATION.md` - Database design and relationships
- `IMPLEMENTATION_GUIDE.md` - Detailed explanation of all implementations
- `Postman_Collection.json` - Ready-to-use API collection

## Troubleshooting

**Database Connection Error:**
- Ensure PostgreSQL is running
- Check credentials in application.properties
- Verify database exists

**Port Already in Use:**
- Change port in application.properties: `server.port=8081`

**Lombok Errors:**
- Enable annotation processing in IDE
- Install Lombok plugin

## Author

KWIZERA Samuel

Developed for Web Technology Practical Assessment

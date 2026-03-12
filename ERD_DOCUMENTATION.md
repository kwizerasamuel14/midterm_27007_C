# Entity Relationship Diagram (ERD)

## Tables and Relationships

### 1. Province (Location Table)
- **Purpose**: Stores province/location information
- **Fields**: id, code, name
- **Relationships**: One-to-Many with Employee

### 2. Employee
- **Purpose**: Core employee information
- **Fields**: id, firstName, lastName, email, phone, jobRole, department, status, provinceId
- **Relationships**: 
  - Many-to-One with Province (foreign key: provinceId)
  - One-to-One with EmployeeProfile
  - One-to-Many with Attendance
  - Many-to-One with Site
  - Many-to-Many with Skill (join table: employee_skills)

### 3. EmployeeProfile (One-to-One)
- **Purpose**: Extended employee details
- **Fields**: id, employeeId, address, emergencyContact, dateOfBirth, hireDate
- **Relationships**: One-to-One with Employee (foreign key: employeeId)

### 4. Site
- **Purpose**: Project site locations
- **Fields**: id, name, location, description
- **Relationships**: One-to-Many with Employee, One-to-Many with Task

### 5. Task
- **Purpose**: Work assignments
- **Fields**: id, title, description, assignedEmployeeId, siteId, deadline, status
- **Relationships**: 
  - Many-to-One with Employee (foreign key: assignedEmployeeId)
  - Many-to-One with Site (foreign key: siteId)

### 6. Attendance
- **Purpose**: Track employee check-in/out
- **Fields**: id, employeeId, checkInTime, checkOutTime, date, siteId
- **Relationships**: Many-to-One with Employee (foreign key: employeeId)

### 7. Skill (Many-to-Many)
- **Purpose**: Employee skills/certifications
- **Fields**: id, name, description
- **Relationships**: Many-to-Many with Employee (join table: employee_skills)

## Relationship Summary

1. **One-to-One**: Employee ↔ EmployeeProfile
2. **One-to-Many**: 
   - Province → Employee
   - Employee → Attendance
   - Site → Employee
   - Site → Task
3. **Many-to-One**: 
   - Employee → Province
   - Task → Employee
   - Task → Site
   - Attendance → Employee
4. **Many-to-Many**: Employee ↔ Skill (via employee_skills join table)

## Database Schema Logic

- **Province** serves as the location master data
- **Employee** is the central entity connecting to most tables
- **EmployeeProfile** extends employee data without cluttering the main table
- **Site** represents physical work locations
- **Task** tracks work assignments with deadlines
- **Attendance** records daily check-in/out
- **Skill** allows flexible skill assignment to employees
- **employee_skills** is the join table for Many-to-Many relationship

# Field Employee Management System - Implementation Documentation

## 1. Entity Relationship Diagram (ERD) - 7 Tables (3 Marks)

### Tables Created:
1. **provinces** - Stores location data
2. **employees** - Core employee information
3. **employee_profiles** - Extended employee details (One-to-One)
4. **sites** - Project site locations
5. **tasks** - Work assignments
6. **attendances** - Check-in/out records
7. **skills** - Employee skills (Many-to-Many)
8. **employee_skills** - Join table for Many-to-Many relationship

### Relationship Logic:
- **Province → Employee**: One province can have many employees (One-to-Many)
- **Employee ↔ EmployeeProfile**: Each employee has exactly one profile (One-to-One)
- **Site → Employee**: One site can have many employees (One-to-Many)
- **Employee ↔ Skill**: Employees can have multiple skills, skills can belong to multiple employees (Many-to-Many via employee_skills join table)
- **Employee → Attendance**: One employee can have many attendance records (One-to-Many)
- **Site → Task**: One site can have many tasks (One-to-Many)
- **Employee → Task**: One employee can be assigned many tasks (One-to-Many)

---

## 2. Implementation of Saving Location (2 Marks)

### File: ProvinceService.java

```java
public Province saveProvince(ProvinceDTO dto) {
    // Check if province code already exists using existsByCode()
    if (provinceRepository.existsByCode(dto.getCode())) {
        throw new RuntimeException("Province code already exists");
    }
    
    // Create new Province entity
    Province province = new Province();
    province.setCode(dto.getCode());
    province.setName(dto.getName());
    
    // Save to database using JPA repository
    return provinceRepository.save(province);
}
```

### Explanation:
1. **Data Validation**: First checks if province code exists to prevent duplicates
2. **Entity Creation**: Creates Province object from DTO
3. **Persistence**: Uses JpaRepository.save() which:
   - Generates ID automatically (@GeneratedValue)
   - Inserts record into provinces table
   - Returns saved entity with generated ID
4. **Relationship Handling**: Province has @OneToMany with Employee, managed by JPA cascade operations

---

## 3. Sorting and Pagination Implementation (5 Marks)

### File: EmployeeService.java

```java
public Page<Employee> getAllEmployeesWithPaginationAndSorting(
        int page, int size, String sortBy, String direction) {
    
    // Create Sort object based on direction
    Sort sort = direction.equalsIgnoreCase("desc") 
        ? Sort.by(sortBy).descending() 
        : Sort.by(sortBy).ascending();
    
    // Create Pageable with page number, size, and sort
    Pageable pageable = PageRequest.of(page, size, sort);
    
    // Execute query with pagination and sorting
    return employeeRepository.findAll(pageable);
}
```

### Sorting Explanation:
- **Sort.by(sortBy)**: Creates Sort object for specified field (e.g., "firstName", "email")
- **ascending()/descending()**: Determines sort order
- Spring Data JPA translates this to SQL: `ORDER BY sortBy ASC/DESC`

### Pagination Explanation:
- **PageRequest.of(page, size, sort)**: Creates Pageable object
  - `page`: Zero-based page number (page 0 = first page)
  - `size`: Number of records per page
  - `sort`: Sorting criteria
- **Performance Benefits**:
  - Reduces memory usage by loading only requested records
  - Faster query execution with LIMIT and OFFSET in SQL
  - Better user experience with manageable data chunks
- SQL Generated: `SELECT * FROM employees ORDER BY field LIMIT size OFFSET (page * size)`

### Controller Endpoint:
```
GET /api/employees/paginated?page=0&size=10&sortBy=firstName&direction=asc
```

---

## 4. Many-to-Many Relationship Implementation (3 Marks)

### Files: Employee.java and Skill.java

**Employee.java:**
```java
@ManyToMany
@JoinTable(
    name = "employee_skills",              // Join table name
    joinColumns = @JoinColumn(name = "employee_id"),        // FK to Employee
    inverseJoinColumns = @JoinColumn(name = "skill_id")     // FK to Skill
)
private List<Skill> skills;
```

**Skill.java:**
```java
@ManyToMany(mappedBy = "skills")
private List<Employee> employees;
```

### Explanation:
1. **Join Table**: `employee_skills` is automatically created with two foreign keys:
   - `employee_id` → references employees.id
   - `skill_id` → references skills.id
2. **@JoinTable**: Defines join table structure on owning side (Employee)
3. **mappedBy**: On inverse side (Skill), indicates Employee owns the relationship
4. **Relationship Logic**: 
   - One employee can have multiple skills (Java, Python, etc.)
   - One skill can belong to multiple employees
   - Join table stores all combinations without duplication

### Database Schema:
```
employee_skills table:
- employee_id (FK to employees)
- skill_id (FK to skills)
- Primary Key: (employee_id, skill_id)
```

---

## 5. One-to-Many Relationship Implementation (2 Marks)

### Files: Province.java and Employee.java

**Province.java (One side):**
```java
@OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
private List<Employee> employees;
```

**Employee.java (Many side):**
```java
@ManyToOne
@JoinColumn(name = "province_id", nullable = false)
private Province province;
```

### Explanation:
1. **Foreign Key**: `province_id` column in employees table references provinces.id
2. **@ManyToOne**: Many employees belong to one province
3. **@OneToMany**: One province has many employees
4. **mappedBy**: Indicates Employee owns the foreign key
5. **cascade = CascadeType.ALL**: Operations on Province cascade to Employees
6. **nullable = false**: Every employee must have a province

### Relationship Logic:
- Province "Western" can have employees: John, Jane, Bob
- Each employee has exactly one province
- Foreign key ensures referential integrity

---

## 6. One-to-One Relationship Implementation (2 Marks)

### Files: Employee.java and EmployeeProfile.java

**Employee.java:**
```java
@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
private EmployeeProfile profile;
```

**EmployeeProfile.java:**
```java
@OneToOne
@JoinColumn(name = "employee_id", unique = true, nullable = false)
private Employee employee;
```

### Explanation:
1. **Foreign Key**: `employee_id` in employee_profiles table
2. **unique = true**: Ensures one profile per employee (enforces One-to-One)
3. **@JoinColumn**: EmployeeProfile owns the relationship (has FK)
4. **mappedBy**: Employee references back without creating extra column
5. **Relationship Logic**:
   - Each Employee has exactly one EmployeeProfile
   - Each EmployeeProfile belongs to exactly one Employee
   - Separates core data (Employee) from extended data (Profile)

### Why One-to-One?
- Keeps Employee table lean for frequent queries
- Profile stores less-accessed data (address, emergency contact)
- Better performance and organization

---

## 7. existsBy() Method Implementation (2 Marks)

### Files: ProvinceRepository.java, EmployeeRepository.java

**ProvinceRepository.java:**
```java
boolean existsByCode(String code);
boolean existsByName(String name);
```

**EmployeeRepository.java:**
```java
boolean existsByEmail(String email);
```

### Explanation:
1. **Spring Data JPA Query Derivation**: Method name automatically generates query
2. **existsByCode(String code)** translates to:
   ```sql
   SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END 
   FROM provinces WHERE code = ?
   ```
3. **How it works**:
   - Spring parses method name: `existsBy` + `Code`
   - Generates query checking if record with given code exists
   - Returns boolean: true if exists, false otherwise
4. **Performance**: More efficient than findBy() because:
   - Only checks existence, doesn't load full entity
   - Uses COUNT query instead of SELECT *
   - Faster for validation purposes

### Usage in Service:
```java
if (provinceRepository.existsByCode(dto.getCode())) {
    throw new RuntimeException("Province code already exists");
}
```

---

## 8. Retrieve Users by Province Code OR Province Name (4 Marks)

### File: EmployeeRepository.java

```java
@Query("SELECT e FROM Employee e WHERE e.province.code = :provinceCode")
List<Employee> findByProvinceCode(@Param("provinceCode") String provinceCode);

@Query("SELECT e FROM Employee e WHERE e.province.name = :provinceName")
List<Employee> findByProvinceName(@Param("provinceName") String provinceName);
```

### Explanation:

**Query Logic:**
1. **JPQL (Java Persistence Query Language)**: Object-oriented query language
2. **e.province.code**: Navigates relationship from Employee to Province
3. **@Param**: Binds method parameter to query parameter
4. **JOIN**: JPA automatically performs JOIN between employees and provinces tables

**Generated SQL:**
```sql
SELECT e.* FROM employees e 
INNER JOIN provinces p ON e.province_id = p.id 
WHERE p.code = ?
```

**Repository Method Used:**
- Custom @Query annotation for complex queries
- Alternative: Could use `findByProvince_Code(String code)` (Spring method naming)
- @Query provides more control and readability

**Controller Endpoints:**
```java
GET /api/employees/province/code/WP     // Get employees from Western Province
GET /api/employees/province/name/Western Province
```

### Why Two Methods?
- Flexibility: Users can search by code (WP) or full name (Western Province)
- Code is unique and shorter for API calls
- Name is more user-friendly for UI displays

---

## API Testing with Postman

### 1. Create Province (Location)
```
POST http://localhost:8080/api/provinces
Body (JSON):
{
    "code": "WP",
    "name": "Western Province"
}
```

### 2. Create Site
```
POST http://localhost:8080/api/sites
Body (JSON):
{
    "name": "Construction Site A",
    "location": "Colombo",
    "description": "Main construction site"
}
```

### 3. Create Skill
```
POST http://localhost:8080/api/skills
Body (JSON):
{
    "name": "Welding",
    "description": "Metal welding certification"
}
```

### 4. Create Employee (with relationships)
```
POST http://localhost:8080/api/employees
Body (JSON):
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
    "skillIds": [1, 2]
}
```

### 5. Get Employees with Pagination and Sorting
```
GET http://localhost:8080/api/employees/paginated?page=0&size=5&sortBy=firstName&direction=asc
```

### 6. Get Employees by Province Code
```
GET http://localhost:8080/api/employees/province/code/WP
```

### 7. Get Employees by Province Name
```
GET http://localhost:8080/api/employees/province/name/Western Province
```

---

## Database Setup

### PostgreSQL Commands:
```sql
-- Create database
CREATE DATABASE field_employee_db;

-- Connect to database
\c field_employee_db

-- Tables will be auto-created by Hibernate (spring.jpa.hibernate.ddl-auto=update)
```

---

## Running the Application

1. **Start PostgreSQL** on port 5432
2. **Update credentials** in application.properties if needed
3. **Run application**:
   ```
   mvn spring-boot:run
   ```
   Or run FieldEmployeeManagementApplication.java from IDE
4. **Test APIs** using Postman on http://localhost:8080

---

## Key Technologies Used

- **Spring Boot 3.2.0**: Framework
- **Spring Data JPA**: Database operations
- **Hibernate**: ORM implementation
- **PostgreSQL**: Database
- **Lombok**: Reduces boilerplate code
- **Maven**: Dependency management

---

## Viva-Voce Preparation Topics

1. **JPA Relationships**: Explain @OneToOne, @OneToMany, @ManyToMany with examples
2. **Cascade Types**: What happens when you delete a Province?
3. **Lazy vs Eager Loading**: Default loading strategies
4. **Pagination Benefits**: Memory and performance advantages
5. **JPQL vs Native SQL**: When to use each
6. **existsBy() vs findBy()**: Performance differences
7. **Join Tables**: How Many-to-Many is implemented in database
8. **Foreign Keys**: Role in maintaining referential integrity
9. **DTO Pattern**: Why separate DTOs from Entities
10. **Repository Pattern**: Benefits of Spring Data JPA repositories

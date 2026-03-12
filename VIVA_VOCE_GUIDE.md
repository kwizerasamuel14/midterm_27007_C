# Viva-Voce Preparation Guide

## 1. Explain the ERD and Relationships

**Answer:**
Our system has 7 tables:
- **provinces**: Stores location data (code, name)
- **employees**: Core employee info with foreign keys to province and site
- **employee_profiles**: Extended employee details (One-to-One with employee)
- **sites**: Project locations
- **tasks**: Work assignments linked to employees and sites
- **attendances**: Check-in/out records
- **skills**: Employee skills
- **employee_skills**: Join table for Many-to-Many

**Relationships:**
- Province → Employee (One-to-Many): One province has many employees
- Employee ↔ EmployeeProfile (One-to-One): Each employee has one profile
- Employee ↔ Skill (Many-to-Many): Employees have multiple skills, skills belong to multiple employees
- Site → Employee (One-to-Many): One site has many employees

---

## 2. How is Location Data Stored?

**Answer:**
Location is stored in the `provinces` table with:
- `id`: Auto-generated primary key
- `code`: Unique province code (e.g., "WP")
- `name`: Province name (e.g., "Western Province")

When saving:
1. Check if code exists using `existsByCode()`
2. Create Province entity from DTO
3. Use `repository.save()` which generates ID and inserts into database
4. JPA handles the relationship with employees automatically

---

## 3. Explain Sorting Implementation

**Answer:**
Sorting is implemented using Spring Data JPA's `Sort` class:

```java
Sort sort = direction.equalsIgnoreCase("desc") 
    ? Sort.by(sortBy).descending() 
    : Sort.by(sortBy).ascending();
```

- `Sort.by(sortBy)`: Specifies which field to sort (e.g., firstName, email)
- `ascending()/descending()`: Determines order
- Spring translates this to SQL `ORDER BY` clause
- Can sort by any entity field

**Example:** Sorting by firstName ascending generates:
```sql
SELECT * FROM employees ORDER BY first_name ASC
```

---

## 4. Explain Pagination Implementation

**Answer:**
Pagination uses Spring Data JPA's `Pageable` interface:

```java
Pageable pageable = PageRequest.of(page, size, sort);
return employeeRepository.findAll(pageable);
```

**Parameters:**
- `page`: Page number (0-based, so page 0 is first page)
- `size`: Records per page
- `sort`: Sorting criteria

**How it works:**
- Spring generates SQL with `LIMIT` and `OFFSET`
- Example: `SELECT * FROM employees LIMIT 10 OFFSET 0`
- Returns `Page<Employee>` object containing:
  - Content (list of employees)
  - Total pages
  - Total elements
  - Current page number

**Performance Benefits:**
- Loads only requested records, not entire table
- Reduces memory usage
- Faster query execution
- Better user experience with manageable data chunks

---

## 5. Explain Many-to-Many Relationship

**Answer:**
Many-to-Many between Employee and Skill uses a join table:

**Employee side (owning):**
```java
@ManyToMany
@JoinTable(
    name = "employee_skills",
    joinColumns = @JoinColumn(name = "employee_id"),
    inverseJoinColumns = @JoinColumn(name = "skill_id")
)
private List<Skill> skills;
```

**Skill side (inverse):**
```java
@ManyToMany(mappedBy = "skills")
private List<Employee> employees;
```

**Join Table Structure:**
- Table name: `employee_skills`
- Columns: `employee_id` (FK to employees), `skill_id` (FK to skills)
- Composite primary key: (employee_id, skill_id)

**Logic:**
- One employee can have multiple skills (Java, Python, Welding)
- One skill can belong to multiple employees
- Join table stores all combinations without duplication
- `@JoinTable` defines structure on owning side (Employee)
- `mappedBy` on inverse side indicates Employee owns relationship

---

## 6. Explain One-to-Many Relationship

**Answer:**
One-to-Many between Province and Employee:

**Province (One side):**
```java
@OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
private List<Employee> employees;
```

**Employee (Many side):**
```java
@ManyToOne
@JoinColumn(name = "province_id", nullable = false)
private Province province;
```

**How it works:**
- Foreign key `province_id` in employees table references provinces.id
- `@ManyToOne` on Employee: Many employees belong to one province
- `@OneToMany` on Province: One province has many employees
- `mappedBy = "province"`: Employee owns the foreign key
- `cascade = CascadeType.ALL`: Operations on Province cascade to Employees
- `nullable = false`: Every employee must have a province

**Example:**
- Province "Western" (id=1) has employees: John, Jane, Bob
- Each employee has province_id = 1
- Database enforces referential integrity

---

## 7. Explain One-to-One Relationship

**Answer:**
One-to-One between Employee and EmployeeProfile:

**Employee:**
```java
@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
private EmployeeProfile profile;
```

**EmployeeProfile:**
```java
@OneToOne
@JoinColumn(name = "employee_id", unique = true, nullable = false)
private Employee employee;
```

**Key Points:**
- Foreign key `employee_id` in employee_profiles table
- `unique = true`: Ensures one profile per employee (enforces One-to-One)
- EmployeeProfile owns the relationship (has FK)
- `mappedBy` on Employee: References back without extra column
- Each Employee has exactly one EmployeeProfile
- Each EmployeeProfile belongs to exactly one Employee

**Why separate tables?**
- Keeps Employee table lean for frequent queries
- Profile stores less-accessed data (address, emergency contact)
- Better performance and organization

---

## 8. Explain existsBy() Method

**Answer:**
`existsBy()` is a Spring Data JPA query method for checking existence:

**Declaration:**
```java
boolean existsByCode(String code);
boolean existsByEmail(String email);
```

**How it works:**
1. Spring parses method name: `existsBy` + field name
2. Automatically generates query
3. Returns boolean (true if exists, false otherwise)

**Generated SQL:**
```sql
SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END 
FROM provinces WHERE code = ?
```

**Usage:**
```java
if (provinceRepository.existsByCode(dto.getCode())) {
    throw new RuntimeException("Province code already exists");
}
```

**Performance Benefits:**
- More efficient than `findBy()` methods
- Only checks existence, doesn't load full entity
- Uses COUNT query instead of SELECT *
- Faster for validation purposes

---

## 9. Explain Province-based Employee Retrieval

**Answer:**
Two methods to retrieve employees by province:

**By Province Code:**
```java
@Query("SELECT e FROM Employee e WHERE e.province.code = :provinceCode")
List<Employee> findByProvinceCode(@Param("provinceCode") String provinceCode);
```

**By Province Name:**
```java
@Query("SELECT e FROM Employee e WHERE e.province.name = :provinceName")
List<Employee> findByProvinceName(@Param("provinceName") String provinceName);
```

**Query Logic:**
- Uses JPQL (Java Persistence Query Language)
- `e.province.code`: Navigates relationship from Employee to Province
- `@Param`: Binds method parameter to query parameter
- JPA automatically performs JOIN between tables

**Generated SQL:**
```sql
SELECT e.* FROM employees e 
INNER JOIN provinces p ON e.province_id = p.id 
WHERE p.code = ?
```

**Repository Method:**
- Custom `@Query` annotation for complex queries
- Alternative: `findByProvince_Code(String code)` (method naming convention)
- `@Query` provides more control and readability

**Why two methods?**
- Flexibility: Search by code (WP) or full name (Western Province)
- Code is unique and shorter for API calls
- Name is more user-friendly for UI displays

---

## 10. What is JPA and Hibernate?

**Answer:**
- **JPA (Java Persistence API)**: Specification for ORM in Java
- **Hibernate**: Implementation of JPA specification
- **ORM (Object-Relational Mapping)**: Maps Java objects to database tables
- Eliminates need for manual SQL queries
- Provides automatic CRUD operations

---

## 11. Explain Cascade Types

**Answer:**
Cascade types define operations that propagate from parent to child:

- `CascadeType.ALL`: All operations cascade
- `CascadeType.PERSIST`: Save operations cascade
- `CascadeType.MERGE`: Update operations cascade
- `CascadeType.REMOVE`: Delete operations cascade
- `CascadeType.REFRESH`: Refresh operations cascade

**Example:** If Province has `cascade = CascadeType.ALL`:
- Deleting Province deletes all its Employees
- Saving Province saves all its Employees

---

## 12. Lazy vs Eager Loading

**Answer:**
**Lazy Loading (Default for @OneToMany, @ManyToMany):**
- Related entities loaded only when accessed
- Better performance for large datasets
- May cause LazyInitializationException if session closed

**Eager Loading (Default for @ManyToOne, @OneToOne):**
- Related entities loaded immediately with parent
- Can cause performance issues with large datasets
- No LazyInitializationException

**Example:**
```java
@OneToMany(fetch = FetchType.LAZY)  // Explicit lazy
@ManyToOne(fetch = FetchType.EAGER) // Explicit eager
```

---

## 13. What is DTO Pattern?

**Answer:**
**DTO (Data Transfer Object):**
- Separate object for transferring data between layers
- Doesn't expose entity structure to clients
- Allows validation and transformation

**Benefits:**
- Security: Hide sensitive entity fields
- Flexibility: Different views of same entity
- Decoupling: API changes don't affect entities
- Validation: Add constraints specific to API

**Example:**
```java
// DTO for API request
public class EmployeeDTO {
    private String firstName;
    private Long provinceId;  // Just ID, not full Province object
}
```

---

## 14. Explain Repository Pattern

**Answer:**
Repository pattern abstracts data access logic:

**Benefits:**
- Separation of concerns
- Testability (can mock repositories)
- Reusability of query methods
- Automatic CRUD operations

**Spring Data JPA provides:**
- `save()`, `findById()`, `findAll()`, `delete()`
- Custom query methods by naming convention
- `@Query` for complex queries
- Pagination and sorting support

---

## 15. What is @Transactional?

**Answer:**
`@Transactional` manages database transactions:

**Features:**
- Automatic commit on success
- Automatic rollback on exception
- Ensures data consistency
- Can be applied to methods or classes

**Example:**
```java
@Transactional
public Employee saveEmployee(EmployeeDTO dto) {
    // If any exception occurs, all changes are rolled back
}
```

---

## Quick Tips for Viva

1. **Be confident** - You built this system
2. **Use examples** - Reference your actual code
3. **Draw diagrams** - Visualize relationships
4. **Explain benefits** - Why you chose this approach
5. **Know SQL** - Understand generated queries
6. **Practice** - Explain each feature out loud

---

## Common Questions

**Q: Why use Spring Boot?**
A: Rapid development, auto-configuration, embedded server, production-ready features

**Q: Why PostgreSQL?**
A: Open-source, ACID compliant, supports complex queries, scalable

**Q: What is REST API?**
A: Representational State Transfer - stateless communication using HTTP methods

**Q: Explain HTTP methods:**
- GET: Retrieve data
- POST: Create new resource
- PUT: Update existing resource
- DELETE: Remove resource

**Q: What is JSON?**
A: JavaScript Object Notation - lightweight data format for API communication

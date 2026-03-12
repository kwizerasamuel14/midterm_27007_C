# Setup and Testing Checklist

## Pre-Exam Setup (Do this before exam day)

### 1. Software Installation
- [ ] Java 17 or higher installed
- [ ] PostgreSQL installed and running
- [ ] Maven installed (or use IDE's built-in Maven)
- [ ] Postman installed
- [ ] IDE installed (IntelliJ IDEA or Eclipse)

### 2. Database Setup
- [ ] PostgreSQL service is running
- [ ] Create database: `CREATE DATABASE field_employee_db;`
- [ ] Verify connection: `psql -U postgres -d field_employee_db`
- [ ] Update credentials in `application.properties` if needed

### 3. Project Setup
- [ ] Open project in IDE
- [ ] Run `mvn clean install` to download dependencies
- [ ] Verify no compilation errors
- [ ] Check all entity classes are recognized

### 4. First Run
- [ ] Run `FieldEmployeeManagementApplication.java`
- [ ] Check console for "Started FieldEmployeeManagementApplication"
- [ ] Verify tables are created in database
- [ ] Check for any errors in console

### 5. Postman Setup
- [ ] Import `Postman_Collection.json`
- [ ] Verify all 11 requests are imported
- [ ] Set base URL to `http://localhost:8080`

---

## Testing Sequence (Follow this order)

### Test 1: Create Province (Location Saving)
**Endpoint:** `POST /api/provinces`
```json
{
    "code": "WP",
    "name": "Western Province"
}
```
- [ ] Status: 201 Created
- [ ] Response contains id, code, name
- [ ] Verify in database: `SELECT * FROM provinces;`

**Create more provinces:**
```json
{"code": "CP", "name": "Central Province"}
{"code": "SP", "name": "Southern Province"}
```

### Test 2: Verify existsBy() Method
**Try duplicate province:**
```json
{
    "code": "WP",
    "name": "Western Province"
}
```
- [ ] Should return error: "Province code already exists"
- [ ] This proves existsByCode() is working

### Test 3: Create Site
**Endpoint:** `POST /api/sites`
```json
{
    "name": "Construction Site A",
    "location": "Colombo",
    "description": "Main construction site"
}
```
- [ ] Status: 201 Created
- [ ] Create 2-3 more sites

### Test 4: Create Skills
**Endpoint:** `POST /api/skills`
```json
{
    "name": "Welding",
    "description": "Metal welding certification"
}
```
- [ ] Create multiple skills: Welding, Electrical Work, Plumbing
- [ ] Verify in database: `SELECT * FROM skills;`

### Test 5: Create Employee (All Relationships)
**Endpoint:** `POST /api/employees`
```json
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
- [ ] Status: 201 Created
- [ ] Verify Many-to-One: employee has province
- [ ] Verify Many-to-One: employee has site
- [ ] Verify Many-to-Many: Check employee_skills table
- [ ] Create 5-10 more employees with different provinces

### Test 6: Get All Employees
**Endpoint:** `GET /api/employees`
- [ ] Returns list of all employees
- [ ] Check nested objects (province, site, skills)

### Test 7: Pagination and Sorting
**Endpoint:** `GET /api/employees/paginated?page=0&size=5&sortBy=firstName&direction=asc`
- [ ] Returns Page object with content, totalPages, totalElements
- [ ] Verify only 5 employees returned (if you have more)
- [ ] Verify sorted by firstName ascending

**Test different parameters:**
- [ ] `page=1&size=3` - Second page with 3 items
- [ ] `sortBy=lastName&direction=desc` - Sort by last name descending
- [ ] `sortBy=email&direction=asc` - Sort by email ascending

### Test 8: Get Employees by Province Code
**Endpoint:** `GET /api/employees/province/code/WP`
- [ ] Returns only employees from Western Province
- [ ] Verify all returned employees have province.code = "WP"
- [ ] Test with other province codes (CP, SP)

### Test 9: Get Employees by Province Name
**Endpoint:** `GET /api/employees/province/name/Western Province`
- [ ] Returns only employees from Western Province
- [ ] Verify all returned employees have province.name = "Western Province"
- [ ] Test with other province names

### Test 10: Verify One-to-One Relationship
**In database:**
```sql
SELECT e.first_name, e.last_name, ep.address, ep.hire_date
FROM employees e
LEFT JOIN employee_profiles ep ON e.id = ep.employee_id;
```
- [ ] Check if employee_profiles table exists
- [ ] Verify employee_id is unique in employee_profiles

### Test 11: Verify Many-to-Many Join Table
**In database:**
```sql
SELECT * FROM employee_skills;
```
- [ ] Table has employee_id and skill_id columns
- [ ] Multiple rows for same employee (multiple skills)
- [ ] Multiple rows for same skill (multiple employees)

---

## Database Verification Queries

### Check all tables exist:
```sql
SELECT table_name FROM information_schema.tables 
WHERE table_schema = 'public' 
ORDER BY table_name;
```
Expected tables:
- [ ] attendances
- [ ] employee_profiles
- [ ] employee_skills (join table)
- [ ] employees
- [ ] provinces
- [ ] sites
- [ ] skills
- [ ] tasks

### Check relationships:
```sql
-- One-to-Many: Province -> Employee
SELECT p.name, COUNT(e.id) as employee_count
FROM provinces p
LEFT JOIN employees e ON p.id = e.province_id
GROUP BY p.id, p.name;

-- Many-to-Many: Employee <-> Skill
SELECT e.first_name, e.last_name, COUNT(es.skill_id) as skill_count
FROM employees e
LEFT JOIN employee_skills es ON e.id = es.employee_id
GROUP BY e.id, e.first_name, e.last_name;
```

---

## Exam Day Checklist

### Before Presentation
- [ ] PostgreSQL is running
- [ ] Database has sample data
- [ ] Application is running on port 8080
- [ ] Postman is open with collection loaded
- [ ] All documentation files are ready

### During Presentation

#### 1. Show ERD (3 marks)
- [ ] Open `ERD_DOCUMENTATION.md`
- [ ] Explain 7 tables and their purpose
- [ ] Draw relationships on board/paper
- [ ] Explain foreign keys and join table

#### 2. Demonstrate Location Saving (2 marks)
- [ ] Show `ProvinceService.java` code
- [ ] Explain existsByCode() validation
- [ ] Run POST /api/provinces in Postman
- [ ] Show saved data in database
- [ ] Explain how JPA saves with relationships

#### 3. Demonstrate Sorting (2.5 marks)
- [ ] Show `EmployeeService.java` code
- [ ] Explain Sort.by() and ascending/descending
- [ ] Run GET /api/employees/paginated with sortBy parameter
- [ ] Show different sort orders in Postman
- [ ] Explain SQL ORDER BY generation

#### 4. Demonstrate Pagination (2.5 marks)
- [ ] Show `EmployeeService.java` code
- [ ] Explain PageRequest.of(page, size, sort)
- [ ] Run GET /api/employees/paginated with different pages
- [ ] Show Page object structure in response
- [ ] Explain performance benefits (LIMIT/OFFSET)

#### 5. Demonstrate Many-to-Many (3 marks)
- [ ] Show `Employee.java` @ManyToMany annotation
- [ ] Show `Skill.java` mappedBy
- [ ] Explain @JoinTable configuration
- [ ] Show employee_skills table in database
- [ ] Create employee with multiple skills in Postman

#### 6. Demonstrate One-to-Many (2 marks)
- [ ] Show `Province.java` @OneToMany
- [ ] Show `Employee.java` @ManyToOne
- [ ] Explain foreign key province_id
- [ ] Show relationship in database
- [ ] Explain cascade operations

#### 7. Demonstrate One-to-One (2 marks)
- [ ] Show `Employee.java` and `EmployeeProfile.java`
- [ ] Explain unique constraint
- [ ] Show employee_profiles table
- [ ] Explain why separate tables
- [ ] Show relationship in database

#### 8. Demonstrate existsBy() (2 marks)
- [ ] Show `ProvinceRepository.java` existsByCode()
- [ ] Show usage in `ProvinceService.java`
- [ ] Try creating duplicate province in Postman
- [ ] Show error message
- [ ] Explain query generation and performance

#### 9. Demonstrate Province Queries (4 marks)
- [ ] Show `EmployeeRepository.java` @Query annotations
- [ ] Explain JPQL syntax
- [ ] Run GET /api/employees/province/code/WP
- [ ] Run GET /api/employees/province/name/Western Province
- [ ] Show SQL JOIN in database logs
- [ ] Explain @Param binding

#### 10. Answer Viva Questions (7 marks)
- [ ] Have `VIVA_VOCE_GUIDE.md` ready
- [ ] Explain concepts clearly with examples
- [ ] Reference your actual code
- [ ] Draw diagrams if needed
- [ ] Be confident and clear

---

## Common Issues and Solutions

### Issue: Application won't start
**Solution:**
- Check PostgreSQL is running
- Verify database exists
- Check port 8080 is not in use
- Review application.properties credentials

### Issue: Tables not created
**Solution:**
- Check `spring.jpa.hibernate.ddl-auto=update` in properties
- Verify entity classes have @Entity annotation
- Check console for Hibernate errors

### Issue: Relationship not working
**Solution:**
- Verify @JoinColumn names match database
- Check mappedBy references correct field name
- Ensure cascade types are set

### Issue: Postman returns 404
**Solution:**
- Verify application is running
- Check URL: http://localhost:8080
- Verify endpoint path matches controller @RequestMapping

### Issue: JSON parsing error
**Solution:**
- Check Content-Type: application/json header
- Verify JSON syntax (commas, quotes)
- Match field names with DTO class

---

## Quick Reference

### Important Files to Know
1. **Entities:** `src/main/java/com/fieldmanagement/system/entity/`
2. **Repositories:** `src/main/java/com/fieldmanagement/system/repository/`
3. **Services:** `src/main/java/com/fieldmanagement/system/service/`
4. **Controllers:** `src/main/java/com/fieldmanagement/system/controller/`
5. **Config:** `src/main/resources/application.properties`

### Key Annotations
- `@Entity` - JPA entity
- `@Table` - Table name
- `@Id` - Primary key
- `@GeneratedValue` - Auto-increment
- `@OneToOne` - One-to-one relationship
- `@OneToMany` - One-to-many relationship
- `@ManyToOne` - Many-to-one relationship
- `@ManyToMany` - Many-to-many relationship
- `@JoinColumn` - Foreign key
- `@JoinTable` - Join table for many-to-many
- `@Query` - Custom JPQL query

### Port and URLs
- Application: http://localhost:8080
- Database: localhost:5432
- Base API: http://localhost:8080/api

---

## Final Preparation

### Day Before Exam
- [ ] Run through entire testing sequence
- [ ] Verify all APIs work
- [ ] Review all documentation files
- [ ] Practice explaining each feature
- [ ] Prepare to draw ERD on board

### Exam Day Morning
- [ ] Start PostgreSQL
- [ ] Start application
- [ ] Load sample data
- [ ] Test one API from each category
- [ ] Have all documentation open

### Confidence Boosters
- ✅ You have 7 tables (requirement: 5)
- ✅ All 3 relationship types implemented
- ✅ Pagination and sorting working
- ✅ Province queries implemented both ways
- ✅ existsBy() methods working
- ✅ Complete documentation prepared
- ✅ Working API ready to demonstrate

**You're fully prepared! Good luck! 🎉**

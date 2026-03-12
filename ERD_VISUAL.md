# Entity Relationship Diagram - Visual Representation

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                    FIELD EMPLOYEE MANAGEMENT SYSTEM - ERD                       │
└─────────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────┐
│     PROVINCES        │
│  (Location Table)    │
├──────────────────────┤
│ PK  id               │
│     code (UNIQUE)    │
│     name             │
└──────────┬───────────┘
           │
           │ 1
           │
           │ ONE-TO-MANY
           │
           │ N
           ▼
┌──────────────────────┐         ┌──────────────────────┐
│     EMPLOYEES        │◄────────┤  EMPLOYEE_PROFILES   │
│   (Core Entity)      │  1:1    │  (Extended Details)  │
├──────────────────────┤         ├──────────────────────┤
│ PK  id               │         │ PK  id               │
│     first_name       │         │ FK  employee_id      │
│     last_name        │         │     address          │
│     email (UNIQUE)   │         │     emergency_contact│
│     phone            │         │     date_of_birth    │
│     job_role         │         │     hire_date        │
│     department       │         └──────────────────────┘
│     status           │         ONE-TO-ONE RELATIONSHIP
│ FK  province_id      │
│ FK  site_id          │
└──────┬───────┬───────┘
       │       │
       │       │ N
       │       │
       │       │ MANY-TO-MANY
       │       │
       │       │ N
       │       ▼
       │    ┌──────────────────────┐
       │    │  EMPLOYEE_SKILLS     │
       │    │   (Join Table)       │
       │    ├──────────────────────┤
       │    │ FK  employee_id      │
       │    │ FK  skill_id         │
       │    └──────────┬───────────┘
       │               │
       │               │ N
       │               │
       │               │
       │               │ N
       │               ▼
       │    ┌──────────────────────┐
       │    │      SKILLS          │
       │    │                      │
       │    ├──────────────────────┤
       │    │ PK  id               │
       │    │     name (UNIQUE)    │
       │    │     description      │
       │    └──────────────────────┘
       │
       │ 1
       │
       │ ONE-TO-MANY
       │
       │ N
       ▼
┌──────────────────────┐
│    ATTENDANCES       │
│                      │
├──────────────────────┤
│ PK  id               │
│ FK  employee_id      │
│     check_in_time    │
│     check_out_time   │
│     date             │
│     site_id          │
└──────────────────────┘


┌──────────────────────┐
│       SITES          │
│  (Project Sites)     │
├──────────────────────┤
│ PK  id               │
│     name             │
│     location         │
│     description      │
└──────────┬───────────┘
           │
           │ 1
           │
           │ ONE-TO-MANY
           │
           │ N
           ▼
     (EMPLOYEES)
           │
           │ 1
           │
           │ ONE-TO-MANY
           │
           │ N
           ▼
┌──────────────────────┐
│       TASKS          │
│                      │
├──────────────────────┤
│ PK  id               │
│     title            │
│     description      │
│ FK  employee_id      │
│ FK  site_id          │
│     deadline         │
│     status           │
└──────────────────────┘


═══════════════════════════════════════════════════════════════════════════════

RELATIONSHIP SUMMARY:

1. ONE-TO-ONE (1:1)
   ┌──────────┐         ┌──────────────────┐
   │ EMPLOYEE │◄───────►│ EMPLOYEE_PROFILE │
   └──────────┘         └──────────────────┘
   - Each employee has exactly ONE profile
   - Each profile belongs to exactly ONE employee
   - FK: employee_id in employee_profiles (UNIQUE)

2. ONE-TO-MANY (1:N)
   
   a) Province → Employees
      ┌──────────┐ 1     N ┌──────────┐
      │ PROVINCE │─────────│ EMPLOYEE │
      └──────────┘         └──────────┘
      - One province has MANY employees
      - FK: province_id in employees
   
   b) Site → Employees
      ┌──────┐ 1     N ┌──────────┐
      │ SITE │─────────│ EMPLOYEE │
      └──────┘         └──────────┘
      - One site has MANY employees
      - FK: site_id in employees
   
   c) Employee → Attendances
      ┌──────────┐ 1     N ┌────────────┐
      │ EMPLOYEE │─────────│ ATTENDANCE │
      └──────────┘         └────────────┘
      - One employee has MANY attendance records
      - FK: employee_id in attendances
   
   d) Site → Tasks
      ┌──────┐ 1     N ┌──────┐
      │ SITE │─────────│ TASK │
      └──────┘         └──────┘
      - One site has MANY tasks
      - FK: site_id in tasks
   
   e) Employee → Tasks
      ┌──────────┐ 1     N ┌──────┐
      │ EMPLOYEE │─────────│ TASK │
      └──────────┘         └──────┘
      - One employee has MANY tasks
      - FK: employee_id in tasks

3. MANY-TO-MANY (N:M)
   ┌──────────┐ N     N ┌────────┐
   │ EMPLOYEE │◄───────►│ SKILL  │
   └──────────┘         └────────┘
           │                 │
           └────┐       ┌────┘
                │       │
                ▼       ▼
         ┌──────────────────┐
         │ EMPLOYEE_SKILLS  │
         │ (Join Table)     │
         ├──────────────────┤
         │ employee_id (FK) │
         │ skill_id (FK)    │
         └──────────────────┘
   - Many employees can have MANY skills
   - Many skills can belong to MANY employees
   - Join table: employee_skills

═══════════════════════════════════════════════════════════════════════════════

TABLE DETAILS:

1. PROVINCES (Location Master Data)
   - Stores province/location information
   - Unique code for quick lookup (e.g., "WP", "CP")
   - Referenced by employees

2. EMPLOYEES (Central Entity)
   - Core employee information
   - Links to province (location)
   - Links to site (work location)
   - Has one profile (extended details)
   - Has many attendance records
   - Has many skills (via join table)
   - Has many tasks assigned

3. EMPLOYEE_PROFILES (Extended Employee Data)
   - One-to-one with employee
   - Stores less frequently accessed data
   - Keeps employee table lean

4. SITES (Project Locations)
   - Physical work locations
   - Has many employees assigned
   - Has many tasks

5. TASKS (Work Assignments)
   - Assigned to one employee
   - Belongs to one site
   - Tracks work progress

6. ATTENDANCES (Time Tracking)
   - Records employee check-in/out
   - Links to employee
   - Tracks daily attendance

7. SKILLS (Employee Capabilities)
   - Master list of skills
   - Many-to-many with employees
   - Allows flexible skill assignment

8. EMPLOYEE_SKILLS (Join Table)
   - Implements many-to-many relationship
   - Composite key: (employee_id, skill_id)
   - Auto-created by JPA

═══════════════════════════════════════════════════════════════════════════════

FOREIGN KEY CONSTRAINTS:

employees.province_id    → provinces.id      (NOT NULL)
employees.site_id        → sites.id          (NULLABLE)
employee_profiles.employee_id → employees.id (UNIQUE, NOT NULL)
attendances.employee_id  → employees.id      (NOT NULL)
tasks.employee_id        → employees.id      (NOT NULL)
tasks.site_id            → sites.id          (NOT NULL)
employee_skills.employee_id → employees.id   (NOT NULL)
employee_skills.skill_id → skills.id         (NOT NULL)

═══════════════════════════════════════════════════════════════════════════════

DATABASE NORMALIZATION:

✓ 1NF: All attributes are atomic (no repeating groups)
✓ 2NF: No partial dependencies (all non-key attributes depend on entire PK)
✓ 3NF: No transitive dependencies (non-key attributes don't depend on other non-key attributes)

═══════════════════════════════════════════════════════════════════════════════
```

## Cardinality Notation

```
1   = Exactly one
N   = Many (zero or more)
1:1 = One-to-One
1:N = One-to-Many
N:M = Many-to-Many
```

## Key Symbols

```
PK  = Primary Key
FK  = Foreign Key
◄─► = Bidirectional relationship
─►  = Unidirectional relationship
```

-- Field Employee Management System - Sample Data Script
-- Run this after the application creates the tables

-- Insert Provinces
INSERT INTO provinces (code, name) VALUES 
('WP', 'Western Province'),
('CP', 'Central Province'),
('SP', 'Southern Province'),
('NP', 'Northern Province'),
('EP', 'Eastern Province');

-- Insert Sites
INSERT INTO sites (name, location, description) VALUES 
('Construction Site A', 'Colombo', 'Main construction site for building project'),
('Maintenance Hub B', 'Kandy', 'Central maintenance and repair facility'),
('Delivery Center C', 'Galle', 'Southern region delivery operations'),
('Tech Site D', 'Jaffna', 'Northern technology installation site');

-- Insert Skills
INSERT INTO skills (name, description) VALUES 
('Welding', 'Metal welding and fabrication'),
('Electrical Work', 'Electrical installation and maintenance'),
('Plumbing', 'Plumbing installation and repair'),
('Carpentry', 'Wood working and construction'),
('Heavy Machinery', 'Operation of heavy construction equipment'),
('First Aid', 'Basic medical emergency response'),
('Project Management', 'Team coordination and project planning');

-- Insert Employees
INSERT INTO employees (first_name, last_name, email, phone, job_role, department, status, province_id, site_id) VALUES 
('John', 'Doe', 'john.doe@example.com', '0771234567', 'Technician', 'Maintenance', 'Active', 1, 1),
('Jane', 'Smith', 'jane.smith@example.com', '0772345678', 'Supervisor', 'Construction', 'Active', 1, 1),
('Bob', 'Johnson', 'bob.johnson@example.com', '0773456789', 'Electrician', 'Electrical', 'Active', 2, 2),
('Alice', 'Williams', 'alice.williams@example.com', '0774567890', 'Plumber', 'Plumbing', 'Active', 3, 3),
('Charlie', 'Brown', 'charlie.brown@example.com', '0775678901', 'Carpenter', 'Construction', 'Active', 1, 1),
('Diana', 'Davis', 'diana.davis@example.com', '0776789012', 'Welder', 'Fabrication', 'Active', 2, 2),
('Edward', 'Miller', 'edward.miller@example.com', '0777890123', 'Driver', 'Logistics', 'Active', 3, 3),
('Fiona', 'Wilson', 'fiona.wilson@example.com', '0778901234', 'Technician', 'Maintenance', 'On Leave', 4, 4);

-- Insert Employee Profiles
INSERT INTO employee_profiles (employee_id, address, emergency_contact, date_of_birth, hire_date) VALUES 
(1, '123 Main St, Colombo', '0711111111', '1990-05-15', '2020-01-10'),
(2, '456 Park Ave, Colombo', '0722222222', '1985-08-22', '2018-03-15'),
(3, '789 Hill Rd, Kandy', '0733333333', '1992-11-30', '2019-06-20'),
(4, '321 Beach Rd, Galle', '0744444444', '1988-02-14', '2021-09-05'),
(5, '654 Lake View, Colombo', '0755555555', '1995-07-08', '2022-01-12'),
(6, '987 Mountain St, Kandy', '0766666666', '1991-04-25', '2020-11-18'),
(7, '147 Coast Rd, Galle', '0777777777', '1993-09-17', '2021-05-22'),
(8, '258 Temple Rd, Jaffna', '0788888888', '1989-12-03', '2019-08-30');

-- Insert Employee Skills (Many-to-Many)
INSERT INTO employee_skills (employee_id, skill_id) VALUES 
(1, 2), (1, 6),  -- John: Electrical Work, First Aid
(2, 7),          -- Jane: Project Management
(3, 2),          -- Bob: Electrical Work
(4, 3),          -- Alice: Plumbing
(5, 4), (5, 6),  -- Charlie: Carpentry, First Aid
(6, 1),          -- Diana: Welding
(7, 5),          -- Edward: Heavy Machinery
(8, 2), (8, 6);  -- Fiona: Electrical Work, First Aid

-- Insert Tasks
INSERT INTO tasks (title, description, employee_id, site_id, deadline, status) VALUES 
('Install Electrical Panel', 'Install main electrical distribution panel', 1, 1, '2024-04-15', 'In Progress'),
('Supervise Foundation Work', 'Oversee foundation pouring and inspection', 2, 1, '2024-04-20', 'Pending'),
('Repair Generator', 'Fix backup generator at maintenance hub', 3, 2, '2024-04-10', 'Completed'),
('Fix Water Leak', 'Repair water pipe leak in building B', 4, 3, '2024-04-12', 'In Progress'),
('Build Wooden Frame', 'Construct wooden frame for new structure', 5, 1, '2024-04-18', 'Pending'),
('Weld Metal Beams', 'Weld support beams for roof structure', 6, 2, '2024-04-25', 'Pending'),
('Deliver Materials', 'Transport construction materials to site', 7, 3, '2024-04-08', 'Completed'),
('Install Network Cables', 'Set up network infrastructure', 8, 4, '2024-04-30', 'Pending');

-- Insert Attendance Records
INSERT INTO attendances (employee_id, check_in_time, check_out_time, date, site_id) VALUES 
(1, '2024-04-01 08:00:00', '2024-04-01 17:00:00', '2024-04-01', 1),
(1, '2024-04-02 08:05:00', '2024-04-02 17:10:00', '2024-04-02', 1),
(2, '2024-04-01 07:55:00', '2024-04-01 18:00:00', '2024-04-01', 1),
(2, '2024-04-02 08:00:00', '2024-04-02 17:30:00', '2024-04-02', 1),
(3, '2024-04-01 08:10:00', '2024-04-01 17:05:00', '2024-04-01', 2),
(4, '2024-04-01 08:00:00', '2024-04-01 16:55:00', '2024-04-01', 3),
(5, '2024-04-01 08:15:00', '2024-04-01 17:20:00', '2024-04-01', 1),
(6, '2024-04-01 08:05:00', '2024-04-01 17:00:00', '2024-04-01', 2),
(7, '2024-04-01 07:50:00', '2024-04-01 16:50:00', '2024-04-01', 3);

-- Verify data
SELECT 'Provinces' as table_name, COUNT(*) as count FROM provinces
UNION ALL
SELECT 'Sites', COUNT(*) FROM sites
UNION ALL
SELECT 'Skills', COUNT(*) FROM skills
UNION ALL
SELECT 'Employees', COUNT(*) FROM employees
UNION ALL
SELECT 'Employee Profiles', COUNT(*) FROM employee_profiles
UNION ALL
SELECT 'Employee Skills', COUNT(*) FROM employee_skills
UNION ALL
SELECT 'Tasks', COUNT(*) FROM tasks
UNION ALL
SELECT 'Attendances', COUNT(*) FROM attendances;

-- Sample Queries for Testing

-- 1. Get all employees from Western Province (by code)
SELECT e.first_name, e.last_name, e.email, p.name as province
FROM employees e
JOIN provinces p ON e.province_id = p.id
WHERE p.code = 'WP';

-- 2. Get all employees from Western Province (by name)
SELECT e.first_name, e.last_name, e.email, p.name as province
FROM employees e
JOIN provinces p ON e.province_id = p.id
WHERE p.name = 'Western Province';

-- 3. Get employees with their skills (Many-to-Many)
SELECT e.first_name, e.last_name, s.name as skill
FROM employees e
JOIN employee_skills es ON e.id = es.employee_id
JOIN skills s ON es.skill_id = s.id
ORDER BY e.last_name;

-- 4. Get employee with profile (One-to-One)
SELECT e.first_name, e.last_name, e.email, 
       ep.address, ep.emergency_contact, ep.hire_date
FROM employees e
JOIN employee_profiles ep ON e.id = ep.employee_id;

-- 5. Get employees with their tasks
SELECT e.first_name, e.last_name, t.title, t.status, t.deadline
FROM employees e
JOIN tasks t ON e.id = t.employee_id
ORDER BY t.deadline;

-- 6. Get attendance summary by employee
SELECT e.first_name, e.last_name, COUNT(a.id) as days_present
FROM employees e
LEFT JOIN attendances a ON e.id = a.employee_id
GROUP BY e.id, e.first_name, e.last_name
ORDER BY days_present DESC;

-- 7. Get employees by site
SELECT s.name as site_name, e.first_name, e.last_name, e.job_role
FROM sites s
JOIN employees e ON s.id = e.site_id
ORDER BY s.name, e.last_name;

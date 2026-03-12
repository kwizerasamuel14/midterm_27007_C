@echo off
echo Creating database field_employee_db...
"C:\Program Files\PostgreSQL\18\bin\psql.exe" -U postgres -c "CREATE DATABASE field_employee_db;"
echo.
echo Database creation complete!
pause

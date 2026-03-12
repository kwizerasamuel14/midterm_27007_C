@echo off
setlocal

set "JAVA_HOME=C:\Program Files\Microsoft\jdk-25.0.0.36-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Using Java from: %JAVA_HOME%
java -version

echo.
echo Building project...
call mvn clean package -DskipTests

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Starting application...
    java -jar target\field-employee-management-1.0.0.jar
) else (
    echo Build failed!
    pause
)

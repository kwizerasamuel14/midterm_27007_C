@echo off
set JAVA_HOME=C:\Program Files\Microsoft\jdk-25.0.0.36-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
set MAVEN_OPTS=-Djava.version=17

echo Java version:
java -version

echo.
echo Starting Spring Boot application...
mvn spring-boot:run

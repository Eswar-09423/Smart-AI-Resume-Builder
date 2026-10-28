@echo off
title Smart AI Resume Creator - Backend
echo ========================================================
echo Starting Smart AI Resume Creator Backend (Spring Boot)...
echo ========================================================

REM Set JAVA_HOME if not already configured
if "%JAVA_HOME%"=="" (
    if exist "C:\Program Files\Java\jdk-26.0.1" (
        set "JAVA_HOME=C:\Program Files\Java\jdk-26.0.1"
    ) else if exist "C:\Program Files\Java\latest" (
        set "JAVA_HOME=C:\Program Files\Java\latest"
    )
)

cd /d "%~dp0backend"
call mvnw.cmd spring-boot:run
pause

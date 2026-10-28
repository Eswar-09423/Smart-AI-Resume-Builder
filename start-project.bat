@echo off
title Smart AI Resume Creator - Launcher
echo ========================================================
echo   Launching Smart AI Resume Creator (Full Project)
echo ========================================================

echo 1. Starting Spring Boot Backend (Port 5000)...
start "Smart Resume Backend" cmd /c "call "%~dp0run-backend.bat""

echo 2. Waiting for server initialization...
timeout /t 4 /nobreak >nul

echo 3. Opening Frontend UI in Browser...
start "" "%~dp0frontend\index.html"

echo ========================================================
echo Project successfully launched!
echo Backend:  http://localhost:5000
echo Frontend: Opened in browser
echo ========================================================
exit

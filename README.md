# 1) build your backend application
cd ./
docker run -p 8089:80 angelfym/student-api:1.0.7

# 2) build your frontend application
cd frontend-student
docker run -p 8089:80 angelfym/student-frontend:1.0.7

# 3) build your frontend application point to internal endpoint (selenium purpose)
cd frontend-student
docker run -p 8089:80 angelfym/student-frontend:1.0.7-inside

# 4) run your sonarqube local env
cd sonar
docker-compose up -d

# 5) execute your test suite
cd ./
docker-compose -f testing-compose.yml up
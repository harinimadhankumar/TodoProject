# 🖥️ Local Development Setup

## Test Everything Locally Before Deploying

### Prerequisites
- ✅ Java 17+ installed
- ✅ Maven installed  
- ✅ MySQL Server running locally
- ✅ Git installed
- ✅ Code editor (VS Code)

---

## Step 1: Setup Local MySQL Database

### Windows with MySQL
```bash
# 1. Start MySQL Service
# Open Services (services.msc) and start "MySQL80" service
# OR use command line:
net start MySQL80

# 2. Login to MySQL
mysql -u root -p

# 3. Create database (in MySQL console)
CREATE DATABASE springbootdb;
USE springbootdb;
EXIT;
```

### Verify Connection
```bash
# Test connection
mysql -u root -p springbootdb -e "SELECT 1;"
```

---

## Step 2: Start Spring Boot Backend Locally

```bash
# Navigate to backend folder
cd Backend/HelloWorld

# Install dependencies (first time only)
mvn clean install

# Run the application
mvn spring-boot:run
```

**Expected Output:**
```
Tomcat started on port(s): 8080 (http)
Started HelloWorldApplication in X.XXX seconds
```

**Verify it's working:**
```bash
# In another terminal:
curl http://localhost:8080/todo/path
# Should return: [] (empty array)
```

---

## Step 3: Test API Endpoints Locally

### Test with Browser
1. Open: http://localhost:8080/todo/path
2. Should show: `[]` (empty array)

### Test with cURL

```bash
# Get all todos
curl http://localhost:8080/todo/path

# Create a todo
curl -X POST http://localhost:8080/todo/create \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Spring Boot",
    "description": "Complete tutorial",
    "completed": false
  }'

# Get specific todo
curl http://localhost:8080/todo/get/1

# Update todo
curl -X PUT http://localhost:8080/todo \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "title": "Updated Title",
    "completed": true
  }'

# Delete todo
curl -X DELETE http://localhost:8080/todo/delete/1
```

---

## Step 4: Test Frontend Locally

### Option A: Open HTML Files Directly
```bash
# Navigate to Frontend
cd Frontend

# Open in browser:
# Right-click on todos.html → Open with → Browser
# OR double-click todos.html
```

### Option B: Run Local Web Server
```bash
# Using Python (if installed):
cd Frontend
python -m http.server 3000

# Then open: http://localhost:3000
```

### Option C: Use VS Code Live Server
1. Install "Live Server" extension in VS Code
2. Right-click on `todos.html`
3. Select "Open with Live Server"

---

## Step 5: Update Frontend for Local Testing

### Edit `Frontend/script.js`

Find all API calls and make sure they use:
```javascript
const API_BASE_URL = 'http://localhost:8080/todo';

// Example:
fetch(`${API_BASE_URL}/path`)
  .then(res => res.json())
  .then(data => console.log(data));
```

---

## Step 6: Test Full Flow Locally

1. **Start Backend**
   ```bash
   cd Backend/HelloWorld
   mvn spring-boot:run
   ```

2. **Start Frontend** (in new terminal)
   ```bash
   cd Frontend
   python -m http.server 3000
   ```

3. **Test in Browser**
   - Open: http://localhost:3000/todos.html
   - Press F12 → Console
   - Create a new todo
   - Verify it appears in the list
   - Update todo (mark complete)
   - Delete todo
   - Check for any console errors

---

## Common Local Issues

### Issue: "Cannot connect to database"
**Solution:**
1. Check MySQL is running: `mysql -u root -p -e "SELECT 1;"`
2. Verify database exists: `mysql -u root -p -e "SHOW DATABASES;"`
3. Create if missing: `mysql -u root -p -e "CREATE DATABASE springbootdb;"`

### Issue: "Port 8080 already in use"
**Solution:**
```bash
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID):
taskkill /PID [PID] /F
```

### Issue: "Java version mismatch"
**Solution:**
```bash
# Check Java version
java -version

# Should be Java 17+
# Install from: https://www.oracle.com/java/technologies/downloads/
```

### Issue: "Maven command not found"
**Solution:**
```bash
# Install Maven from: https://maven.apache.org/download.cgi
# Add to PATH environment variable

# Verify:
mvn -version
```

### Issue: CORS Error in Console
**This is expected** when using local frontend with local backend. The CorsConfig handles it.

---

## Debugging Tips

### View Live Logs
```bash
# Backend logs appear in the mvn spring-boot:run terminal
# Look for ERROR or WARN messages in red
```

### Enable SQL Debug
Edit `Backend/HelloWorld/src/main/resources/application.properties`:
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Restart backend to see SQL queries in console.

### Check Database
```bash
# Login to MySQL
mysql -u root -p springbootdb

# View tables
SHOW TABLES;

# View todos
SELECT * FROM todo;

# View one todo
SELECT * FROM todo WHERE id = 1;

# Exit
EXIT;
```

---

## Performance Testing

### Test Response Time
```bash
# Single request
time curl http://localhost:8080/todo/path

# Multiple requests (measure average)
for i in {1..10}; do curl -s http://localhost:8080/todo/path > /dev/null; done
```

### Load Testing
```bash
# Using Apache Bench (if installed):
ab -n 100 -c 10 http://localhost:8080/todo/path/
```

---

## Local Deployment Checklist

- [ ] MySQL running locally
- [ ] Backend running on port 8080
- [ ] Frontend accessible locally
- [ ] Can create todos
- [ ] Can read todos
- [ ] Can update todos
- [ ] Can delete todos
- [ ] No console errors
- [ ] No SQL errors in backend
- [ ] Response time < 100ms

---

## Ready for Cloud Deployment?

When all local tests pass:

1. ✅ Commit to GitHub
2. ✅ Create Railway database
3. ✅ Deploy backend to Render
4. ✅ Deploy frontend to Vercel
5. ✅ Test cloud deployment

Follow: [QUICK_START_COMMANDS.md](./QUICK_START_COMMANDS.md)

---

## Quick Reference: All Commands

```bash
# MySQL
mysql -u root -p
CREATE DATABASE springbootdb;

# Backend
cd Backend/HelloWorld
mvn clean install
mvn spring-boot:run

# Frontend
cd Frontend
python -m http.server 3000
# OR
Open todos.html in browser

# Test API
curl http://localhost:8080/todo/path
```

---

## Next Steps

1. ✅ Get it working locally
2. ✅ Test all features
3. ✅ Then deploy to cloud
4. ✅ Follow QUICK_START_COMMANDS.md

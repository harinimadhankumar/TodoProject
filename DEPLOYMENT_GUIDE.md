# Complete Free Deployment Guide

## 🚀 Project Architecture
- **Frontend**: Vanilla HTML/CSS/JS → Deploy on **Vercel**
- **Backend**: Spring Boot (Java 17, Maven) → Deploy on **Render**
- **Database**: MySQL → Deploy on **Railway**

---

## ⭐ PHASE 1: GitHub Setup (REQUIRED FIRST)

### Step 1: Create GitHub Repository

**Via Browser:**
1. Go to https://github.com/new
2. Repository name: `TodoProject`
3. Click "Create repository"
4. Copy the HTTPS URL (e.g., `https://github.com/YOUR_USERNAME/TodoProject.git`)

### Step 2: Initialize & Push to GitHub

**In Terminal (at project root):**

```bash
# Initialize git if not already done
cd /c/Users/harin/Downloads/TodoProject
git init
git add .
git commit -m "Initial commit: Full stack Todo app"

# Add remote and push
git remote add origin https://github.com/YOUR_USERNAME/TodoProject.git
git branch -M main
git push -u origin main
```

**Replace `YOUR_USERNAME` with your actual GitHub username**

---

## 🗄️ PHASE 2: Railway MySQL Database

### Step 1: Create Railway Account
1. Go to https://railway.app
2. Click "Start Project" → Sign in with GitHub
3. Authorize Railway

### Step 2: Create MySQL Database
1. Click "New Project"
2. Select "MySQL"
3. Wait for it to provision (takes ~2 minutes)
4. Click on the MySQL service

### Step 3: Get Database Credentials
In Railway dashboard:
- Expand the MySQL service card
- Copy these credentials:
  - **MYSQL_HOST**: `[host]`
  - **MYSQL_PORT**: `3306` or shown port
  - **MYSQL_DATABASE**: `[database]`
  - **MYSQL_USER**: `[username]`
  - **MYSQL_PASSWORD**: `[password]`

**Save these values - you'll need them for Spring Boot**

---

## 🔧 PHASE 3: Update Spring Boot Configuration

### Step 1: Create `application-cloud.properties`

Create file: `/Backend/HelloWorld/src/main/resources/application-cloud.properties`

```properties
spring.application.name=HelloWorld
spring.datasource.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DATABASE}
spring.datasource.username=${MYSQL_USER}
spring.datasource.password=${MYSQL_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
server.port=${PORT:8080}

# CORS Configuration
spring.web.cors.allowed-origins=${FRONTEND_URL:http://localhost:3000}
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
spring.web.cors.allow-credentials=true
```

### Step 2: Add CORS Configuration Class

Create file: `/Backend/HelloWorld/src/main/java/com/example/HelloWorld/config/CorsConfig.java`

```java
package com.example.HelloWorld.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String frontendUrl = System.getenv("FRONTEND_URL");
        if (frontendUrl == null) {
            frontendUrl = "http://localhost:3000";
        }
        
        registry.addMapping("/api/**")
                .allowedOrigins(frontendUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
```

### Step 3: Create Render Build Script

Create file: `/Backend/build.sh`

```bash
#!/bin/bash
cd Backend/HelloWorld
mvn clean package -DskipTests
```

---

## 🚀 PHASE 4: Deploy Spring Boot Backend on Render

### Step 1: Create Render Account
1. Go to https://render.com
2. Click "Get Started" → Sign up with GitHub
3. Authorize Render

### Step 2: Create Web Service
1. Click "New +" → "Web Service"
2. Select your GitHub repository
3. Configure:
   - **Name**: `todoapp-backend`
   - **Environment**: `Java 17`
   - **Build Command**: `cd Backend/HelloWorld && mvn clean package -DskipTests`
   - **Start Command**: `java -jar target/HelloWorld-0.0.1-SNAPSHOT.jar`

### Step 3: Add Environment Variables in Render
Click "Advanced" and add:

```
MYSQL_HOST = [from Railway]
MYSQL_PORT = 3306
MYSQL_DATABASE = [from Railway]
MYSQL_USER = [from Railway]
MYSQL_PASSWORD = [from Railway]
FRONTEND_URL = [your Vercel URL - add later]
SPRING_PROFILES_ACTIVE = cloud
PORT = 8080
```

### Step 4: Deploy
Click "Deploy" and wait (takes ~5-10 minutes)

**Copy your Render Backend URL** (e.g., `https://todoapp-backend.onrender.com`)

---

## 🎨 PHASE 5: Update Frontend for Production

### Step 1: Update API Endpoint

Edit file: `/Frontend/script.js`

Replace all `http://localhost:8080` with your Render Backend URL:

```javascript
const API_BASE_URL = 'https://todoapp-backend.onrender.com/api';
// Replace all fetch calls to use this
```

### Step 2: Create `vercel.json`

Create file: `/vercel.json`

```json
{
  "buildCommand": "npm run build",
  "outputDirectory": ".",
  "env": {
    "REACT_APP_API_URL": "@api_url"
  }
}
```

### Step 3: Create `.vercelignore`

Create file: `/.vercelignore`

```
Backend/
.github/
*.md
node_modules/
```

---

## ⚡ PHASE 6: Deploy Frontend on Vercel

### Step 1: Create Vercel Account
1. Go to https://vercel.com
2. Click "Sign Up" → Select "Continue with GitHub"
3. Authorize Vercel

### Step 2: Import Project
1. Click "Add New..." → "Project"
2. Select your `TodoProject` repository
3. Configure:
   - **Framework**: `Other` (since it's vanilla HTML)
   - **Root Directory**: `./Frontend`
   - **Build Command**: Leave empty (not needed for vanilla HTML)
   - **Output Directory**: `Frontend`

### Step 3: Add Environment Variables
Click "Environment Variables":
```
API_URL = [your Render Backend URL]
```

### Step 4: Deploy
Click "Deploy" and wait (usually 1-2 minutes)

**Copy your Vercel Frontend URL** (e.g., `https://todoproject-XXX.vercel.app`)

### Step 5: Update Backend CORS
Go back to Render dashboard:
1. Select your backend service
2. Go to "Environment"
3. Update `FRONTEND_URL` = your Vercel URL
4. Click "Deploy"

---

## 🔗 PHASE 7: Connect Frontend to Backend

### Step 1: Update Frontend API Calls

Edit `/Frontend/script.js`:

```javascript
// Replace all API calls
const API_URL = 'https://todoapp-backend.onrender.com/api';

// Example fetch calls:
fetch(`${API_URL}/todos`, {
  method: 'GET',
  headers: {
    'Content-Type': 'application/json',
  }
})
```

### Step 2: Verify Backend Endpoints
Make sure your Spring Boot has these endpoints:
- `GET /api/todos` - Get all todos
- `POST /api/todos` - Create todo
- `PUT /api/todos/{id}` - Update todo
- `DELETE /api/todos/{id}` - Delete todo

**If they don't exist, check your backend controller**

---

## ✅ PHASE 8: Testing Deployment

### Test 1: Check Backend Health
```bash
curl https://todoapp-backend.onrender.com/api/todos
```
Should return JSON array (might be empty) or `200 OK`

### Test 2: Check CORS Headers
```bash
curl -H "Origin: https://todoproject-XXX.vercel.app" \
     -H "Access-Control-Request-Method: POST" \
     https://todoapp-backend.onrender.com/api/todos -v
```
Look for: `Access-Control-Allow-Origin: https://todoproject-XXX.vercel.app`

### Test 3: Test in Browser
1. Go to your Vercel frontend URL
2. Open DevTools (F12) → Console
3. Create a new todo
4. Check if it appears in the list
5. Check console for errors

---

## 🐛 Common Errors & Fixes

### Error 1: "CORS Error" in Console
**Fix:**
```javascript
// Add to backend CorsConfig.java
.allowedOrigins("*")  // Temporary for testing
.allowedMethods("*")
.allowedHeaders("*")
```

### Error 2: "Cannot connect to database"
**Fix:** Check Railway credentials in Render environment variables
```bash
# Verify in Render logs
MYSQL_HOST, MYSQL_USER, MYSQL_PASSWORD all set correctly
```

### Error 3: "404 Not Found" on API calls
**Fix:** Check:
1. Backend URL is correct in `script.js`
2. Endpoint path matches Spring Boot controller
3. Backend is deployed and running

### Error 4: "Render Service Not Starting"
**Fix:** Check build command
```bash
# Must be exact path:
cd Backend/HelloWorld && mvn clean package -DskipTests
```

### Error 5: "Vercel Build Failed"
**Fix:** For vanilla HTML, delete build command
1. Go to Vercel settings
2. Build Command: leave EMPTY
3. Output Directory: `Frontend`

---

## 📋 Environment Variables Summary

### Railway MySQL
```
MYSQL_HOST=mysql.railway.internal
MYSQL_PORT=3306
MYSQL_DATABASE=railway
MYSQL_USER=root
MYSQL_PASSWORD=password
```

### Render Backend
```
MYSQL_HOST
MYSQL_PORT
MYSQL_DATABASE
MYSQL_USER
MYSQL_PASSWORD
FRONTEND_URL=https://todoproject-XXX.vercel.app
SPRING_PROFILES_ACTIVE=cloud
PORT=8080
```

### Vercel Frontend
```
API_URL=https://todoapp-backend.onrender.com/api
```

---

## 🚨 Important Notes

1. **Never hardcode credentials** - always use environment variables
2. **Render free tier** has limited CPU - first request takes 30+ seconds
3. **Railway free tier** includes $5 credit per month
4. **Vercel free tier** includes unlimited bandwidth for static sites
5. **Cold starts** - first request after inactivity takes longer

---

## ✨ Final Checklist

- [ ] GitHub repository created and pushed
- [ ] Railway MySQL database created
- [ ] Backend environment variables added to Render
- [ ] Frontend API URL updated
- [ ] Backend deployed on Render
- [ ] Frontend deployed on Vercel
- [ ] CORS configuration in place
- [ ] Tested all endpoints
- [ ] No console errors
- [ ] Todos can be created/read/updated/deleted

---

## 🆘 Quick Reference Commands

```bash
# Test backend
curl https://todoapp-backend.onrender.com/api/todos

# Check if API is responding
curl -i https://todoapp-backend.onrender.com/api/todos

# View Render logs
# Go to Render dashboard → Logs tab

# View Vercel logs
# Go to Vercel dashboard → Deployments → Logs
```

---

**Need Help?** Check your service logs:
- **Render**: https://dashboard.render.com → Logs tab
- **Railway**: https://railway.app → Logs tab
- **Vercel**: https://vercel.com/dashboard → Select project → Deployments → Logs

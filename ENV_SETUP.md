# Environment Variables Setup

## 🚨 IMPORTANT: Keep these SECRET - Never commit to GitHub

## Railway MySQL Credentials
```
MYSQL_HOST=mysql.railway.internal
MYSQL_PORT=3306
MYSQL_DATABASE=railway
MYSQL_USER=root
MYSQL_PASSWORD=[your_password_from_railway]
```

## Render Backend (Add in Render Dashboard)
**Path: Select Service → Environment Variables**

```
# Railway Database Connection
MYSQL_HOST=[from Railway dashboard]
MYSQL_PORT=3306
MYSQL_DATABASE=[from Railway dashboard]
MYSQL_USER=[from Railway dashboard]
MYSQL_PASSWORD=[from Railway dashboard]

# Frontend URL (add after Vercel deployment)
FRONTEND_URL=https://your-vercel-url.vercel.app

# Spring Boot Configuration
SPRING_PROFILES_ACTIVE=cloud
PORT=8080
JAVA_TOOL_OPTIONS=-Xmx512m -XX:+UseContainerSupport
```

## Vercel Frontend (Add in Vercel Dashboard)
**Path: Project Settings → Environment Variables**

```
API_URL=https://todoapp-backend.onrender.com/api
```

---

## Step-by-Step: Where to Add Environment Variables

### For Railway MySQL
1. Go to https://railway.app
2. Select your MySQL service
3. Look at the "Connect" tab
4. Copy the credentials shown

### For Render Backend
1. Go to https://dashboard.render.com
2. Select your web service: `todoapp-backend`
3. Click "Environment" tab
4. Add each variable one by one
5. Click "Deploy" button

### For Vercel Frontend
1. Go to https://vercel.com/dashboard
2. Select your project: `TodoProject`
3. Go to "Settings" tab
4. Select "Environment Variables"
5. Add `API_URL` variable

---

## .env File for Local Development (DO NOT COMMIT)

Create file: `.env` in project root

```
# Local Development Only
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_DATABASE=springbootdb
MYSQL_USER=root
MYSQL_PASSWORD=Harini@3108

FRONTEND_URL=http://localhost:3000
API_URL=http://localhost:8080/api
```

Add to `.gitignore`:
```
.env
.env.local
*.key
secrets/
```

---

## Quick Environment Variable Lookup

| Service | Variable | Value | Where to Get |
|---------|----------|-------|--------------|
| Railway | MYSQL_HOST | mysql.railway.internal | Railway → MySQL → Connect tab |
| Railway | MYSQL_PORT | 3306 | Railway → MySQL → Connect tab |
| Railway | MYSQL_DATABASE | railway | Railway → MySQL → Connect tab |
| Railway | MYSQL_USER | root | Railway → MySQL → Connect tab |
| Railway | MYSQL_PASSWORD | [hidden] | Railway → MySQL → Connect tab → Show Password |
| Render | FRONTEND_URL | https://xxx.vercel.app | Vercel Dashboard |
| Render | SPRING_PROFILES_ACTIVE | cloud | Default value |
| Vercel | API_URL | https://xxx.onrender.com/api | Render Dashboard |

---

## Troubleshooting Environment Variables

### Issue: "Cannot connect to database"
- [ ] Check MYSQL_HOST is exactly: `mysql.railway.internal`
- [ ] Check MYSQL_PASSWORD has no special characters or add quotes
- [ ] Verify MYSQL_USER and MYSQL_PASSWORD match Railway dashboard
- [ ] Check MYSQL_DATABASE name is correct

### Issue: "CORS error in browser console"
- [ ] Verify FRONTEND_URL in Render environment
- [ ] Restart Render service after changing FRONTEND_URL
- [ ] Check the exact URL without trailing slash

### Issue: "Frontend cannot reach backend"
- [ ] Verify API_URL in Vercel environment
- [ ] Ensure backend URL is accessible (no typos)
- [ ] Check Render service is deployed and running

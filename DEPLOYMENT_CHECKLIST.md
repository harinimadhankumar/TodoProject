# 📋 DEPLOYMENT CHECKLIST & TROUBLESHOOTING

## ✅ Pre-Deployment Checklist

### Before You Start
- [ ] Have GitHub account (free at github.com)
- [ ] Have Railway account (free at railway.app)
- [ ] Have Render account (free at render.com)
- [ ] Have Vercel account (free at vercel.app)
- [ ] Project pushed to GitHub
- [ ] All files committed (no uncommitted changes)

### Project Structure Check
```
TodoProject/
├── Frontend/
│   ├── script.js
│   ├── style.css
│   ├── login.html
│   ├── register.html
│   └── todos.html
├── Backend/
│   └── HelloWorld/
│       ├── pom.xml
│       └── src/main/...
├── DEPLOYMENT_GUIDE.md ✓
├── QUICK_START_COMMANDS.md ✓
├── API_DOCUMENTATION.md ✓
├── ENV_SETUP.md ✓
├── vercel.json ✓
├── .vercelignore ✓
└── build.sh ✓
```

---

## 🚀 Deployment Steps

### Step 1: GitHub ✅ (5 minutes)
- [ ] Navigate to project folder
- [ ] Run `git init`
- [ ] Run `git add .`
- [ ] Run `git commit -m "Initial commit"`
- [ ] Create new repo at github.com
- [ ] Run `git remote add origin https://github.com/YOUR_USERNAME/TodoProject.git`
- [ ] Run `git push -u origin main`
- [ ] Verify code appears on GitHub

### Step 2: Railway Database ✅ (5 minutes)
- [ ] Go to railway.app
- [ ] Create new MySQL database
- [ ] Wait for deployment
- [ ] Copy credentials:
  - [ ] MYSQL_HOST
  - [ ] MYSQL_PORT (3306)
  - [ ] MYSQL_DATABASE
  - [ ] MYSQL_USER
  - [ ] MYSQL_PASSWORD
- [ ] Save credentials in safe place

### Step 3: Render Backend ✅ (15 minutes)
- [ ] Go to render.com
- [ ] Create "Web Service"
- [ ] Select GitHub repository
- [ ] Fill in settings:
  - [ ] Name: `todoapp-backend`
  - [ ] Environment: `Java 17`
  - [ ] Build Command: `cd Backend/HelloWorld && mvn clean package -DskipTests`
  - [ ] Start Command: `java -jar target/HelloWorld-0.0.1-SNAPSHOT.jar`
- [ ] Add environment variables:
  - [ ] MYSQL_HOST (from Railway)
  - [ ] MYSQL_PORT (3306)
  - [ ] MYSQL_DATABASE (from Railway)
  - [ ] MYSQL_USER (from Railway)
  - [ ] MYSQL_PASSWORD (from Railway)
  - [ ] SPRING_PROFILES_ACTIVE = cloud
  - [ ] PORT = 8080
- [ ] Click "Deploy"
- [ ] Wait for deployment (5-10 minutes)
- [ ] Copy backend URL

### Step 4: Update Frontend ✅ (5 minutes)
- [ ] Open `Frontend/script.js`
- [ ] Find all `http://localhost:8080` 
- [ ] Replace with your Render backend URL
- [ ] Save file
- [ ] Commit to GitHub:
  ```bash
  git add Frontend/script.js
  git commit -m "Update API URL for production"
  git push
  ```

### Step 5: Vercel Frontend ✅ (5 minutes)
- [ ] Go to vercel.com
- [ ] Click "Add New" → "Project"
- [ ] Select GitHub repository
- [ ] Configure:
  - [ ] Framework: `Other`
  - [ ] Root Directory: `./Frontend`
  - [ ] Build Command: (leave EMPTY)
  - [ ] Output Directory: `Frontend`
- [ ] Click "Deploy"
- [ ] Wait for deployment (1-2 minutes)
- [ ] Copy frontend URL

### Step 6: Update Backend CORS ✅ (5 minutes)
- [ ] Go back to render.com
- [ ] Select backend service
- [ ] Go to "Environment" tab
- [ ] Add or update:
  - [ ] FRONTEND_URL = your Vercel URL (exact)
  - [ ] Example: `https://todoproject-abc123.vercel.app`
- [ ] Click "Deploy"
- [ ] Backend will restart (2-3 minutes)

---

## ✨ Testing Phase

### Test 1: Backend is Running
```bash
curl https://todoapp-backend.onrender.com/todo/path
```
**Expected:** `[]` or array of todos
**NOT expected:** Error or timeout

### Test 2: Frontend Loads
1. Open your Vercel URL in browser
2. Press F12 (DevTools)
3. Go to "Console" tab
4. **Should NOT see red errors**

### Test 3: Create Todo
1. On frontend page, create a new todo
2. Open DevTools → Network tab
3. Look for POST request to `/todo/create`
4. Should see "201 Created" status
5. Todo should appear on page

### Test 4: CORS is Working
```bash
curl -i https://todoapp-backend.onrender.com/todo/path
```
Look for:
```
Access-Control-Allow-Origin: https://your-vercel-url.vercel.app
Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS
```

### Test 5: End-to-End Flow
1. Go to frontend
2. Create new todo
3. See it in list
4. Update todo (mark complete)
5. Verify it updated
6. Delete todo
7. Verify removed from list

---

## 🐛 Troubleshooting Guide

### ❌ Problem: "Cannot GET /" on Vercel
**Solution:**
1. Check `Root Directory` is `./Frontend`
2. Check files are in Frontend folder
3. Redeploy on Vercel

### ❌ Problem: CORS Error in Console
```
Access to XMLHttpRequest blocked by CORS policy
```
**Solutions:**
1. [ ] Check FRONTEND_URL in Render is correct
2. [ ] No trailing slash: `https://todoproject-xxx.vercel.app`
3. [ ] Restart Render service (Deploy button)
4. [ ] Check CorsConfig.java exists in Backend

### ❌ Problem: "404 Not Found" on API call
**Solutions:**
1. [ ] Check API URL in script.js is correct
2. [ ] Verify endpoint path (should be `/todo/path`)
3. [ ] Backend is deployed and running
4. [ ] Check Render logs for errors

### ❌ Problem: "Cannot connect to database"
**Logs show:** `Connection refused` or `Unknown database`
**Solutions:**
1. [ ] Check Railway credentials in Render Environment
2. [ ] Verify MYSQL_HOST = `mysql.railway.internal`
3. [ ] Check database name matches
4. [ ] Copy credentials directly from Railway (no typos)

### ❌ Problem: Render Build Fails
**Solutions:**
1. [ ] Check build command exactly:
   ```
   cd Backend/HelloWorld && mvn clean package -DskipTests
   ```
2. [ ] Java version is 17
3. [ ] Maven installed locally
4. [ ] Check Render logs for error message

### ❌ Problem: "Timeout" or Very Slow Response
**Cause:** Render free tier has cold start
**Solution:** 
1. First request after inactivity takes 30+ seconds
2. Subsequent requests are fast
3. This is normal for free tier

### ❌ Problem: Changes Not Reflecting
**Solutions:**
1. [ ] Did you push to GitHub? (`git push`)
2. [ ] Hard refresh browser (Ctrl+Shift+R)
3. [ ] Check Render/Vercel is redeploying
4. [ ] Clear browser cache

---

## 📞 Where to Check Logs

### Render Backend Logs
1. Go to: https://dashboard.render.com
2. Click on your service: `todoapp-backend`
3. Select "Logs" tab
4. See real-time logs

**Look for:**
- ✓ "Listening on port 8080"
- ✓ "Successfully created database"
- ✗ Red text = errors

### Vercel Frontend Logs
1. Go to: https://vercel.com/dashboard
2. Select your project
3. Go to "Deployments" tab
4. Click latest deployment
5. Select "Logs" tab

**Look for:**
- ✓ Build completed
- ✗ Red text = build errors

### Railway Database Logs
1. Go to: https://railway.app
2. Select MySQL service
3. Look for connection status

---

## 📊 Monitoring After Deployment

### Check Status Daily
```bash
# Bash script to check all services
curl https://todoapp-backend.onrender.com/todo/path && echo "✓ Backend OK" || echo "✗ Backend DOWN"
curl https://todoproject-xxx.vercel.app && echo "✓ Frontend OK" || echo "✗ Frontend DOWN"
```

### Monitor Costs (All FREE)
- **Vercel:** Free static hosting, unlimited bandwidth
- **Render:** Free tier includes $7/month credit
- **Railway:** Free tier includes $5/month credit
- **Total:** Completely FREE ✓

---

## 🎯 Final Deployment Status

After successful deployment you should have:

```
✅ GitHub Repository
   └─ Your code backed up
   
✅ Railway MySQL Database
   └─ Cloud database running
   
✅ Render Spring Boot Backend
   └─ API available at https://todoapp-backend.onrender.com
   
✅ Vercel Frontend
   └─ Website available at https://todoproject-xxx.vercel.app
   
✅ CORS Configuration
   └─ Frontend can communicate with Backend
   
✅ All Services Connected
   └─ Frontend → Backend → Database (working)
```

---

## 🔄 Updating Your App

### Make Code Changes
```bash
# 1. Edit your code
# 2. Test locally
# 3. Commit
git add .
git commit -m "Fixed bug in todos"
git push
```

**Then:**
- Render auto-redeploys (watch logs)
- Vercel auto-redeploys (watch logs)
- Changes live in 2-5 minutes

---

## 🎉 Success Indicators

You'll know deployment is successful when:

1. [ ] `curl https://todoapp-backend.onrender.com/todo/path` returns `[]`
2. [ ] Frontend URL opens without errors
3. [ ] Create todo works
4. [ ] List shows newly created todos
5. [ ] Update todo works
6. [ ] Delete todo works
7. [ ] No CORS errors in console
8. [ ] All operations complete in < 2 seconds (except first request)

---

## 📚 Documentation Files Created

1. **DEPLOYMENT_GUIDE.md** - Complete step-by-step guide
2. **QUICK_START_COMMANDS.md** - All terminal commands
3. **API_DOCUMENTATION.md** - API endpoints reference
4. **ENV_SETUP.md** - Environment variables guide
5. **This file** - Troubleshooting & checklist

---

## 🆘 Still Stuck?

**Check these in order:**

1. [ ] Read the error message carefully
2. [ ] Check service logs (Render/Vercel)
3. [ ] Verify environment variables
4. [ ] Check GitHub has latest code
5. [ ] Restart/redeploy services
6. [ ] Clear browser cache (Ctrl+Shift+Delete)
7. [ ] Wait 5 minutes (cold start)
8. [ ] Try in incognito mode (no cache)

**Last resort:** Clear everything and start from beginning

---

**You've got this! 🚀 Happy deploying!**

# 🎯 Quick Start Commands

## ⚡ Step 1: Push to GitHub (Do this first!)

```bash
# Navigate to project
cd C:\Users\harin\Downloads\TodoProject

# Initialize git
git init

# Add all files
git add .

# Create first commit
git commit -m "Initial commit: Full stack Todo app - Spring Boot + Vanilla JS + MySQL"

# Add GitHub remote (REPLACE YOUR_USERNAME)
git remote add origin https://github.com/YOUR_USERNAME/TodoProject.git
git branch -M main
git push -u origin main
```

**Expected output:**
```
Counting objects: 100%
Writing objects: 100%
Compressing objects: 100%
Total X (delta X), reused X
```

---

## 🗄️ Step 2: Create Railway Database

**No commands needed - all GUI**

1. Go to: https://railway.app
2. Click "New Project" → Choose "MySQL"
3. Wait for deployment (~2 minutes)
4. Click on MySQL service → "Connect" tab
5. **Copy and save these values:**
   ```
   MYSQL_HOST = [copy this]
   MYSQL_PORT = 3306
   MYSQL_DATABASE = [copy this]
   MYSQL_USER = [copy this]
   MYSQL_PASSWORD = [click "Show Password", copy this]
   ```

---

## 🔧 Step 3: Deploy Backend on Render

**Via GUI (easiest for beginners):**

1. Go to: https://render.com
2. Click "New +" → "Web Service"
3. Select your GitHub repo
4. Fill in:
   - **Name:** `todoapp-backend`
   - **Environment:** `Java 17`
   - **Build Command:** 
     ```
     cd Backend/HelloWorld && mvn clean package -DskipTests
     ```
   - **Start Command:** 
     ```
     java -jar target/HelloWorld-0.0.1-SNAPSHOT.jar
     ```
5. Click "Advanced" → Add these Environment Variables:
   ```
   MYSQL_HOST = [from Railway]
   MYSQL_PORT = 3306
   MYSQL_DATABASE = [from Railway]
   MYSQL_USER = [from Railway]
   MYSQL_PASSWORD = [from Railway]
   SPRING_PROFILES_ACTIVE = cloud
   PORT = 8080
   ```
6. Click "Create Web Service"
7. **Wait 5-10 minutes for deployment**
8. Copy the URL (e.g., `https://todoapp-backend.onrender.com`)

**To check if deployed:**
```bash
curl https://todoapp-backend.onrender.com/api/todos
```

---

## 🎨 Step 4: Update Frontend & Deploy on Vercel

### 4a. Update API URL in Frontend

Edit file: `C:\Users\harin\Downloads\TodoProject\Frontend\script.js`

Find this line:
```javascript
// Old:
fetch('http://localhost:8080/api/todos')

// Replace with your Render URL:
fetch('https://todoapp-backend.onrender.com/api/todos')
```

**Find and replace ALL occurrences of `http://localhost:8080` with your backend URL**

### 4b. Deploy to Vercel

1. Go to: https://vercel.com/dashboard
2. Click "Add New" → "Project"
3. Select your `TodoProject` repository
4. Configure:
   - **Framework Preset:** `Other` (vanilla HTML)
   - **Root Directory:** `./` (or leave empty)
   - **Build Command:** Leave EMPTY
   - **Output Directory:** `Frontend`
5. Click "Deploy"
6. **Wait 1-2 minutes**
7. Copy the Vercel URL (e.g., `https://todoproject-xxx.vercel.app`)

---

## 🔗 Step 5: Connect Frontend & Backend

### Update Backend CORS

1. Go to: https://dashboard.render.com
2. Select your backend service: `todoapp-backend`
3. Click "Environment" tab
4. Find `FRONTEND_URL` variable
5. Change value to: `https://your-vercel-url.vercel.app`
6. Click "Save Changes"
7. **Service will redeploy automatically (2-5 minutes)**

---

## ✅ Step 6: Test Everything

### Test 1: Backend is running
```bash
curl https://todoapp-backend.onrender.com/api/todos
```
Expected: `[]` or list of todos (not error)

### Test 2: CORS is working
```bash
curl -i -H "Origin: https://todoproject-xxx.vercel.app" \
     -H "Access-Control-Request-Method: POST" \
     https://todoapp-backend.onrender.com/api/todos
```
Look for: `Access-Control-Allow-Origin: https://todoproject-xxx.vercel.app`

### Test 3: Frontend loads
1. Open: https://todoproject-xxx.vercel.app
2. Press F12 to open DevTools
3. Go to "Console" tab
4. No red errors should appear
5. Try creating a new todo
6. Check if it saves

---

## 🚨 Troubleshooting Commands

### Check if backend service is running
```bash
curl -v https://todoapp-backend.onrender.com
```

### Check backend logs (on Render)
```
1. Go to https://dashboard.render.com
2. Select service → Logs tab
3. Look for errors (red text)
```

### Check if frontend is deployed
```bash
curl https://todoproject-xxx.vercel.app
```
Should return HTML content

### Test database connection
```bash
curl https://todoapp-backend.onrender.com/actuator/health
```
Expected: `{"status":"UP"}`

---

## 📝 Important Notes

- **First request is slow**: Render free tier is cold-started. First request takes 30+ seconds
- **Environment variables**: After changing, click "Deploy" button on Render
- **CORS errors**: Usually means FRONTEND_URL in Render is wrong
- **Database errors**: Check Railway credentials are exactly copied

---

## 🆘 Emergency Checklist

If something breaks:

1. [ ] Check Render logs: https://dashboard.render.com (select service → Logs)
2. [ ] Check Vercel logs: https://vercel.com (select project → Deployments → Logs)
3. [ ] Verify all environment variables are set
4. [ ] Check API URL in Frontend script.js is correct
5. [ ] Verify FRONTEND_URL in Render backend is correct
6. [ ] Test backend directly: `curl https://todoapp-backend.onrender.com/api/todos`
7. [ ] Check if services are running (not in error state)

---

## ⏱️ Deployment Timeline

- GitHub push: ~1 minute
- Railway setup: ~5 minutes
- Render backend deploy: ~10 minutes (first time)
- Vercel frontend deploy: ~2 minutes
- **Total: ~20 minutes**

After that, each update takes:
- GitHub push: 1 minute
- Render redeploy: 2-3 minutes (automatic)
- Vercel redeploy: 1 minute (automatic)

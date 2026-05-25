# 📚 Backend API Documentation

## Available Endpoints

Your Spring Boot backend exposes these endpoints:

### Todos API

#### 1️⃣ Get All Todos
```
GET /todo/path
```
**Response:**
```json
[
  {
    "id": 1,
    "title": "Learn Spring Boot",
    "description": "Complete Spring Boot tutorial",
    "completed": false
  }
]
```

#### 2️⃣ Get Todo by ID
```
GET /todo/get/{id}
```
**Example:**
```
GET /todo/get/1
```
**Response:**
```json
{
  "id": 1,
  "title": "Learn Spring Boot",
  "description": "Complete Spring Boot tutorial",
  "completed": false
}
```

#### 3️⃣ Get Todos with Pagination
```
GET /todo/todos/page?page=0&size=10
```
**Response:** (Page object with paginated todos)

#### 4️⃣ Create New Todo
```
POST /todo/create
```
**Request Body:**
```json
{
  "title": "New Todo",
  "description": "Todo description",
  "completed": false
}
```
**Response:**
```json
{
  "id": 5,
  "title": "New Todo",
  "description": "Todo description",
  "completed": false
}
```

#### 5️⃣ Update Todo
```
PUT /todo
```
**Request Body:**
```json
{
  "id": 1,
  "title": "Updated Title",
  "description": "Updated description",
  "completed": true
}
```
**Response:**
```json
{
  "id": 1,
  "title": "Updated Title",
  "description": "Updated description",
  "completed": true
}
```

#### 6️⃣ Delete Todo by ID
```
DELETE /todo/delete/{id}
```
**Example:**
```
DELETE /todo/delete/1
```

#### 7️⃣ Delete Todo
```
DELETE /todo
```
**Request Body:**
```json
{
  "id": 1,
  "title": "Old Todo"
}
```

---

## 🌐 Frontend API Calls

Update your `script.js` with these API calls:

### JavaScript Fetch Examples

```javascript
// Base API URL
const API_BASE_URL = 'https://todoapp-backend.onrender.com/todo';

// 1. Get all todos
fetch(`${API_BASE_URL}/path`)
  .then(res => res.json())
  .then(todos => console.log(todos));

// 2. Get single todo
fetch(`${API_BASE_URL}/get/1`)
  .then(res => res.json())
  .then(todo => console.log(todo));

// 3. Create new todo
fetch(`${API_BASE_URL}/create`, {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    title: 'New Todo',
    description: 'Description here',
    completed: false
  })
})
  .then(res => res.json())
  .then(todo => console.log('Created:', todo));

// 4. Update todo
fetch(`${API_BASE_URL}`, {
  method: 'PUT',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    id: 1,
    title: 'Updated Title',
    description: 'Updated description',
    completed: true
  })
})
  .then(res => res.json())
  .then(todo => console.log('Updated:', todo));

// 5. Delete todo
fetch(`${API_BASE_URL}/delete/1`, {
  method: 'DELETE'
})
  .then(res => res.text())
  .then(msg => console.log('Deleted'));

// 6. With error handling
async function getTodos() {
  try {
    const response = await fetch(`${API_BASE_URL}/path`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const todos = await response.json();
    return todos;
  } catch (error) {
    console.error('Error fetching todos:', error);
  }
}
```

---

## ⚠️ CORS Configuration

Your backend is configured to accept requests from:
- Frontend URL (set in `FRONTEND_URL` environment variable)
- `http://localhost:3000` (for local development)
- `http://localhost:5173` (for Vite development)

**Headers allowed:**
- All headers
- Methods: GET, POST, PUT, DELETE, OPTIONS, PATCH
- Credentials: enabled

---

## 🔍 Testing Endpoints

### Test with cURL

```bash
# Get all todos
curl https://todoapp-backend.onrender.com/todo/path

# Get single todo
curl https://todoapp-backend.onrender.com/todo/get/1

# Create todo
curl -X POST https://todoapp-backend.onrender.com/todo/create \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Todo",
    "description": "Testing API",
    "completed": false
  }'

# Update todo
curl -X PUT https://todoapp-backend.onrender.com/todo \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "title": "Updated",
    "completed": true
  }'

# Delete todo
curl -X DELETE https://todoapp-backend.onrender.com/todo/delete/1
```

---

## 📊 Expected Database Schema

The Todo entity has:

| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT | Primary Key, Auto-increment |
| title | VARCHAR(255) | Todo title |
| description | TEXT | Todo description |
| completed | BOOLEAN | Status (true/false) |

---

## 🚀 API Response Format

### Success Response
```json
{
  "id": 1,
  "title": "Todo Title",
  "description": "Description",
  "completed": false
}
```

### Error Response
```json
{
  "error": "Todo not found",
  "status": 404
}
```

---

## 📝 Common Issues & Fixes

### Issue: 404 Not Found
**Cause:** Wrong endpoint path
**Fix:** Make sure you're using `/todo/path` not `/api/todos`

### Issue: 405 Method Not Allowed
**Cause:** Wrong HTTP method
**Fix:** Check if using GET when POST is required

### Issue: CORS Error in Console
**Error:** `Access to XMLHttpRequest blocked by CORS policy`
**Fix:** 
1. Check FRONTEND_URL is set in Render
2. Restart backend after changing FRONTEND_URL
3. Use exact URL without trailing slash

### Issue: 400 Bad Request
**Cause:** Missing required fields in request body
**Fix:** Check JSON structure matches schema

---

## 🧪 Testing Checklist

- [ ] Backend is running: `curl https://todoapp-backend.onrender.com/todo/path`
- [ ] Returns array: `[]` or list of todos
- [ ] Create endpoint works: POST returns 201
- [ ] Update endpoint works: PUT returns 200
- [ ] Delete endpoint works: DELETE returns 200
- [ ] CORS headers are present
- [ ] No console errors in frontend

---

## 🔗 Environment Variables Needed

**In Render:**
```
API_URL=https://todoapp-backend.onrender.com/todo
```

**In Frontend script.js:**
```javascript
const API_BASE_URL = 'https://todoapp-backend.onrender.com/todo';
```

---

## 📖 Additional Resources

- Spring Boot Docs: https://spring.io/projects/spring-boot
- MySQL Docs: https://dev.mysql.com/doc/
- MDN Fetch API: https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API

// Shared script for login, register, and todos pages
const SERVER_URL = "http://localhost:8080";
const token = localStorage.getItem("token");

// Login page logic
function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    body: JSON.stringify({ email, password })

    fetch(`${SERVER_URL}/auth/login`, {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify({email, password})
    })

    .then(response => {
        if(!response.ok) {
                throw new Error(data.message || "Login failed");
            }
            return response.json();
    })

    .then(data => {
        localStorage.setItem("token", data.token);
        window.location.href = "todos.html";
    })
    .catch(error => {
        alert(error.message);
    });
}

// Register page logic
function register() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    body: JSON.stringify({ email, password })

    fetch(`${SERVER_URL}/auth/register`,{
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify({email, password})
    })
    .then(response => {
        if(response.ok) {
            alert("Registration successful! Please login.");
            window.location.href = "login.html";
        }
        else {
            return response.json().then(data => {
                throw new Error(data.message || "Registration failed");
            });
        }
    })
    .catch(error => {
        alert(error.message);
    });
}

function createTodoCard(todo) {
    const card = document.createElement("div");
    card.className = "todo-card";

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.checked = todo.isCompleted;
    checkbox.addEventListener("change", function () {
        const updatedTodo = { ...todo, isCompleted: checkbox.checked };
        updateTodoStatus(updatedTodo);
    });

    const title = document.createElement("span");
    title.textContent = todo.title;

    if (todo.isCompleted) {
        title.style.textDecoration = "line-through";
        title.style.color = "#aaa";
    }

    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "X";
    deleteBtn.onclick = function () {
        deleteTodo(todo.id);
    };

    card.appendChild(checkbox);
    card.appendChild(title);
    card.appendChild(deleteBtn);

    return card;
}

function loadTodos() {
    if (!token) {
        alert("Please login first");
        window.location.href = "login.html";
        return;
    }

    fetch(`${SERVER_URL}/todo/path`, {
        method : "GET",
        headers : {
            "Authorization" : `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    })

    .then(response => {
        if(!response.ok) {
            throw new Error(data.message || "Failed to get todo");
        }
        return response.json();
    })

    .then((todos) => {
        const todoList = document.getElementById("todo-list");
        todoList.innerHTML = "";

        if(!todos || todos.length === 0) {
            todoList.innerHTML = `<p id = "empty-message"> No todos found. Add a new todo!</p>`;
            return;
        }
        else {
            todos.forEach(todo => {
                const todoCard = createTodoCard(todo);
                todoList.appendChild(todoCard);
            });
        }
    })

    .catch(error => {
        document.getElementById("todo-list").innerHTML = `<p style="color:red"> Error loading todos.</p>`;
    });
}

function addTodo() {
    const input = document.getElementById("new-todo");
    const todotext = input.value.trim();

    if(!todotext) {
        alert("Todo text cannot be empty");
        return;
    }

    fetch(`${SERVER_URL}/todo/create`, {
        method : "POST",
        headers : {
            "Content-Type" : "application/json",
            "Authorization" : `Bearer ${token}`
        },
        body : JSON.stringify({title : todotext, isCompleted : false})
    })

    .then(response => {
        if(!response.ok) {
            throw new Error(data.message || "Failed to create todo");
        }
        return response.json();
    })

    .then((newTodo) => {
        input.value = "";
        loadTodos()
    })

    .catch(error => {
        alert(error.message);
    });
}

function updateTodoStatus(todo) {
    fetch(`${SERVER_URL}/todo/${id}`, {
        method : "PUT",
        headers : {
            "Content-Type" : "application/json",
            "Authorization" : `Bearer ${token}`
        },
        body : JSON.stringify(todo)
    })

    .then(response => {
        if(!response.ok) {
            throw new Error(data.message || "Failed to update todo");
        }
        return response.json();
    })

    .then(() => loadTodos())
    
    .catch(error => {
        alert(error.message);
    });
}

function deleteTodo(id) {
    
    fetch(`${SERVER_URL}/todo/delete/${id}`, {
        method : "DELETE",
        headers : {
            "Authorization" : `Bearer ${token}`
        },
    })

    .then(response => {
        if(!response.ok) {
            throw new Error(data.message || "Failed to delete todo");
        }
        return response.text();
    })

    .then(() => loadTodos())

    .catch(error => {
        alert(error.message);
    });
}

// Page-specific initializations
document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("todo-list")) {
        loadTodos();
    }
});
const API_URL = 'http://localhost:8080';

document.addEventListener('DOMContentLoaded', () => {
    fetchTodos();

    const form = document.getElementById('todo-form');
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        const title = document.getElementById('todo-title').value;
        if (title) {
            await createTodo({ title, completed: false });
            document.getElementById('todo-title').value = ''; // 입력 필드 초기화
            fetchTodos(); // 목록 새로고침
        }
    });
});

async function fetchTodos() {
    const response = await fetch(`${API_URL}/`);
    const todos = await response.json();
    const todoList = document.getElementById('todo-list');
    todoList.innerHTML = '';

    todos.forEach(todo => {
        const listItem = document.createElement('li');
        listItem.className = todo.completed ? 'completed' : '';

        listItem.innerHTML = `
            <input type="checkbox" ${todo.completed ? 'checked' : ''} onchange="toggleCompleted(${todo.id}, this.checked)">
            <input type="text" value="${todo.title}" onchange="updateTitle(${todo.id}, this.value)">
            <button onclick="deleteTodo(${todo.id})">Delete</button>
        `;
        todoList.appendChild(listItem);
    });
}

async function createTodo(todo) {
    await fetch(`${API_URL}/`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(todo)
    });
}

async function deleteTodo(id) {
    await fetch(`${API_URL}/${id}`, {
        method: 'DELETE',
    });
    fetchTodos(); // 목록 새로고침
}

async function updateTitle(id, newTitle) {
    await fetch(`${API_URL}/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title: newTitle })
    });
    fetchTodos(); // 목록 새로고침
}

async function toggleCompleted(id, completed) {
    await fetch(`${API_URL}/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ completed: completed })
    });
    fetchTodos(); // 목록 새로고침
}
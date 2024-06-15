# 😶‍🌫️ TODO API

This is a simple TODO list API that allows you to create, read, update and delete tasks.

Built with Java, Spring Boot, and H2 database.

### Features

- ✅ CRUD your tasks
- ✅ Create your account
- ✅ Authentication and Authorization
- ✅ Get all tasks
- ✅ Get a task by id
- ✅ Searching, sorting and filtering

## 📚 Table of Contents

- [Getting Started](#-getting-started)
- [Endpoints](#-endpoints)
- [Cheklist](#-cheklist)
- [License](#-license)

## 🚀 Getting Started

> ⚠️ **Prerequisites:** Java 17 and Maven are required to run this application.

1. Clone the repository

```bash
git clone https://github.com/wesleybertipaglia/todo-api.git
```

2. Navigate to the project directory

```bash
cd todo-api
```

3. Run the application

```bash
./mvnw spring-boot:run
```

The application will start on port 8080.

## 🪧 Endpoints

- `GET /tasks`: Get all tasks
- `GET /tasks/{id}`: Get a task by id
- `POST /tasks`: Create a new task
- `PUT /tasks/{id}`: Update a task
- `DELETE /tasks/{id}`: Delete a task

## ☑️ Cheklist

The following checklist can be used to track the project progress:

- [ ] CRUD operations
- [ ] Pagination, sorting, and filtering
- [ ] Authentication and authorization
- [ ] Tests (unit, integration, and end-to-end)
- [ ] Docker containerization
- [ ] CI/CD
- [ ] Documentation
- [ ] Logging and error handling
- [ ] Deployment
- [ ] Monitoring

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

[Back to top](#-todo-api)

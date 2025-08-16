# Project Notes

## 🛠 Tech Stack
- **Java**: 17.0.16 (Eclipe Temurin)
- **Spring Boot**: 3.5.4
- **PostgreSQL**: 17 (Docker)
- **Maven**: 3.9.6
- **Spring Security**
- **Spring Data JPA**
- **springdoc-openapi**

## 🚀 Run project  
  ### Step 1
  #### Run maven 
  > mvn install clean

  ### Step 2
  #### Run Spring Boot
  > mvn spring-boot:run

## 🧱 Project structure in Backend
```
project-notes/
├─ pom.xml
├─ README.md
├─ src/main/java/com/example/demo/
│  ├─ ProjectNoteApplication.java
│  ├─ config/
│  │  ├─ OpenApiConfig.java
│  │  ├─ SecurityConfig.java
│  │  └─ SwaggerConfig.java
│  ├─ controller/
│  │  ├─ NoteController.java
│  │  ├─ TagController.java
│  │  └─ UserController.java
│  └─ exception/
│     └─ GlobalExceptionHandler.java
│  ├─ model/
│  │  ├─ Note.java
│  │  ├─ Tag.java
│  │  └─ User.java
│  ├─ repo/
│  │  ├─ NoteRepository.java
│  │  ├─ TagRepository.java
│  │  └─ UserRepository.java
│  ├─ service/
│  │  ├─ imple/
│  │  │   ├─ TagServiceImpl.java
│  │  │   ├─ NoteServiceImpl.java
│  │  │   └─ UserServiceImpl.java
│  │  └─ interfaces/
│  │      ├─ TagService.java
│  │      ├─ NoteService.java
│  │      └─ UserService.java
├─ src/main/resources/
│  ├─ application.properties
│  └─ application.yml
└─ src/test/java/... (placeholders)
```

---

## 📃Documentation
### Swagger-ui in localhost
> http://localhost:8080/swagger-ui/index.html
### Endpoint Lists
#### UserController - Endpoint
| HTTP Method | Endpoint | Description |  HTTP Responses |
| :---         |     :---:      |          :---: |          :--- |
| POST   | /api/v1/users     | Create a new user    | 201 (CREATED), 400 (BAD REQUEST) |
| GET     | /api/v1/users       | Get all the users      | 200 (OK) |
| GET     | /api/v1/users/{id}       | Get an user by ID      | 200 (OK), 404 (NOT FOUND) |
| DELETE     | /api/v1/users/{id}       | Delete user by ID      |  204 (NO CONTENT), 404 (NOT FOUND) |
| POST     | /api/v1/users/{id}/tags       | Assign tags by a user      |  201 (CREATED), 400 (BAD REQUEST), 404 (NOT FOUND) |
| GET     | /api/v1/users/{id}/tags     | Get tags by user      |  204 (NO CONTENT), 404 (NOT FOUND) |

#### NoteController - Endpoint
| HTTP Method | Endpoint | Description |  HTTP Responses |
| :---         |     :---:      |          :---: |          :--- |
| POST   | /api/v1/notes     | Create a new note    | 201 (CREATED), 400 (BAD REQUEST) |
| GET     | /api/v1/notes       | Get all the notes      | 200 (OK) |
| GET     | /api/v1/notes/{id}       | Get a note by ID      | 200 (OK), 404 (NOT FOUND) |
| PUT     | /api/v1/notes/{id}       | Updates an existing note by its ID      | 200 (OK), 404 (NOT FOUND) |
| DELETE     | /api/v1/notes/{id}       | Delete a note by ID      |  204 (NO CONTENT), 404 (NOT FOUND) |

#### TagController - Endpoint
| HTTP Method | Endpoint | Description |  HTTP Responses |
| :---         |     :---:      |          :---: |          :--- |
| POST   | /api/v1/tags     | Create a new tag    | 201 (CREATED), 400 (BAD REQUEST) |
| GET     | /api/v1/tags       | Get all the tags      | 200 (OK) |
| DELETE     | /api/v1/tags/{id}       | Delete a tags by ID      |  204 (NO CONTENT), 404 (NOT FOUND) |
| POST     | /api/v1/tags/{id}/notes       | Assign notes by tags      |  201 (CREATED), 400 (BAD REQUEST), 404 (NOT FOUND) |

## 💾Database Design
![alt text](https://github.com/The-Rigo/project-notes/blob/main/assets/Project-Notes.png)
## 🛠Architecture
- **Entity:** It is a data model, a class that represents the DB.
- **Repository:** It is data access, interfaces that handle CRUD operations with the DB
- **Service:** It is business logic, a class that contains the application logic.
- **Controller:** It is the presentation layer, it handles HTTP requests and returns a response.
![alt text](https://github.com/The-Rigo/project-notes/blob/main/assets/architecture.png)

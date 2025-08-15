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
  #### Run docker compose 
  > docker-compose up -d
  
  ### Step 2
  #### Run maven 
  > mvn install clean

  ### Step 3
  ### Run Spring Boot
  > mvn spring-boot:run

## Estructura del proyecto en Backend
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

## Documentacion
### Swagger-ui en Localhost
> http://localhost:8080/swagger-ui/index.html
### Listas de Endpoints
#### UserController - Endpoint
| HTTP Método | Endpoint | Descripción |  Respuestas HTTP |
| :---         |     :---:      |          :---: |          :--- |
| POST   | /api/v1/users     | registrar un nuevo user    | 201 (CREATED), 400 (BAD REQUEST) |
| GET     | /api/v1/users       | Obtiene todos los users      | 200 (OK) |
| GET     | /api/v1/users/{id}       | Obtiene user por su ID      | 200 (OK), 404 (NOT FOUND) |
| DELETE     | /api/v1/users/{id}       | Elimina user por su ID      |  204 (NO CONTENT), 404 (NOT FOUND) |

#### NoteController - Endpoint
| HTTP Método | Endpoint | Descripción |  Respuestas HTTP |
| :---         |     :---:      |          :---: |          :--- |
| POST   | /api/v1/notes     | registrar un nuevo notes    | 201 (CREATED), 400 (BAD REQUEST) |
| GET     | /api/v1/notes       | Obtiene todos los notes      | 200 (OK) |
| GET     | /api/v1/notes/{id}       | Obtiene un notes por su ID      | 200 (OK), 404 (NOT FOUND) |
| PUT     | /api/v1/notes/{id}       | Actualiza un notes existente por su ID      | 200 (OK), 404 (NOT FOUND) |
| DELETE     | /api/v1/notes/{id}       | Elimina un notes por su ID      |  204 (NO CONTENT), 404 (NOT FOUND) |

#### TagController - Endpoint
| HTTP Método | Endpoint | Descripción |  Respuestas HTTP |
| :---         |     :---:      |          :---: |          :--- |
| POST   | /api/v1/tags     | registrar un nuevo tags    | 201 (CREATED), 400 (BAD REQUEST) |
| GET     | /api/v1/tags       | Obtiene todos los tags      | 200 (OK) |
| DELETE     | /api/v1/tags/{id}       | Elimina un tags por su ID      |  204 (NO CONTENT), 404 (NOT FOUND) |


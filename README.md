
# ForoAPI

ForoAPI es una API RESTful desarrollada con Spring Boot para gestionar un foro en línea. La API permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los recursos principales del foro, que incluyen tópicos y usuarios.

## Características

- **Gestión de Tópicos**: Crear, listar, actualizar y eliminar tópicos.
- **Gestión de Usuarios**: Crear, listar, actualizar y eliminar usuarios.
- **Autenticación y Seguridad**: Implementación de autenticación utilizando Spring Security y JSON Web Tokens (JWT).
- **Base de Datos**: Configuración para utilizar H2 Database.
- **Documentación de la API**: Integración con Springdoc OpenAPI UI para documentar los endpoints.


## Requisitos

- Java 17 o superior
- Maven

## Instalación

1. Clona el repositorio:
2. Compila el proyecto utilizando Maven:

   ```bash
   ./mvnw clean install
   ```

## Ejecución

Para ejecutar la aplicación, utiliza el siguiente comando:

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`.

## Endpoints Principales

### Gestión de Tópicos

- **Listar Tópicos**: `GET /topicos`
- **Crear Tópico**: `POST /topicos`
- **Obtener Tópico**: `GET /topicos/{id}`
- **Actualizar Tópico**: `PUT /topicos/{id}`
- **Eliminar Tópico**: `DELETE /topicos/{id}`

### Gestión de Usuarios

- **Listar Usuarios**: `GET /usuarios`
- **Crear Usuario**: `POST /usuarios`
- **Obtener Usuario**: `GET /usuarios/{id}`
- **Actualizar Usuario**: `PUT /usuarios/{id}`
- **Eliminar Usuario**: `DELETE /usuarios/{id}`

## Seguridad

La seguridad se maneja utilizando Spring Security y JWT. Para acceder a los endpoints protegidos, es necesario autenticarse y obtener un token JWT.

### Autenticación

- **Login**: `POST /login`
  
  El cuerpo de la solicitud debe contener los campos `username` y `password`. Si las credenciales son correctas, la respuesta incluirá un token JWT.

## Base de Datos

Se utiliza H2 Database para el almacenamiento de datos. La configuración de la base de datos se encuentra en el archivo `src/main/resources/application.properties`.


## Pruebas

Para ejecutar las pruebas, utiliza el siguiente comando:

```bash
./mvnw test


python ForoAPI-Test01.py
```
Revisa el log foroapi-test.log para comprobar que todo esta bien.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue para discutir cualquier cambio que quieras realizar.



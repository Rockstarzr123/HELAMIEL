# HELAMIEL

## Descripción

HELAMIEL es una aplicación web desarrollada con **Spring Boot** para la gestión de una heladería artesanal. El sistema implementa un panel administrativo de productos (CRUD completo con búsqueda y filtros), un módulo de rutas de reparto y un módulo de autenticación con **API REST**, expuesto además como página de login web con control de sesión.

El proyecto evolucionó desde una versión de escritorio/JDBC hacia una aplicación web completa basada en Spring MVC, Spring Data JPA y Thymeleaf, manteniendo H2 en memoria para desarrollo y MySQL como motor de base de datos real.

## Tecnologías

- Java 17
- Spring Boot 3.5.0
- Spring Web (MVC)
- Spring Data JPA / Hibernate
- Spring Security (Password Encoder con BCrypt)
- Thymeleaf
- Bean Validation (Jakarta Validation)
- H2 Database (entorno de desarrollo, en memoria)
- MySQL + MySQL Connector/J (entorno de producción/real)
- Bootstrap 5 + Bootstrap Icons
- Maven
- Git y GitHub

## Arquitectura

El proyecto sigue una arquitectura en capas (Controller → Service → Repository → Model), propia de Spring Boot:

- `controller`: controladores web y REST.
  - `RutaController`: página de inicio (`/`) y registro de rutas de reparto.
  - `ProductoController`: panel de productos (`/productos`) — listar, buscar, filtrar, guardar, editar y eliminar.
  - `AuthController`: API REST de autenticación (`/api/auth/register`, `/api/auth/login`).
  - `LoginController`: página web de login (`/login`) y cierre de sesión (`/logout`).
- `service`: lógica de negocio (`ProductoService`, `RutaService`, `AuthService`).
- `repository`: interfaces `JpaRepository` para acceso a datos (`ProductoRepository`, `RutaRepository`, `UsuarioRepository`).
- `model`: entidades JPA (`Producto`, `Ruta`, `Usuario`).
- `dto`: objetos de transferencia usados en formularios y en la API (`ProductoDTO`, `LoginRequest`, `LoginResponse`, `RegistroUsuarioDTO`).
- `exception`: excepciones de negocio y manejador global (`GlobalExceptionHandler`) para respuestas de error consistentes.
- `config`: configuración de seguridad (`SecurityConfig`), interceptor de sesión (`SesionInterceptor`) y su registro (`WebConfig`).
- `dao` / `conexion`: clases heredadas de la versión JDBC previa del proyecto.
- `src/main/resources/templates`: vistas Thymeleaf (`index.html`, `login.html`, `productos/`, `fragments/layout.html`).

## Funcionalidades

### Panel de productos (`/productos`)
- Listado responsive de productos.
- Búsqueda por texto libre.
- Filtro por categoría y por estado (activo/inactivo).
- Registro de productos desde formulario modal.
- Edición de productos.
- Eliminación de productos.
- Validaciones de formulario en servidor (Bean Validation) con mensajes de error.

### Módulo de rutas (`/`)
- Página de inicio con listado de rutas registradas (origen, destino, distancia).
- Registro de nuevas rutas.

### Autenticación
- **API REST** (`/api/auth`):
  - `POST /api/auth/register` — registra un usuario nuevo (contraseña cifrada con BCrypt).
  - `POST /api/auth/login` — valida credenciales y responde el resultado en JSON.
- **Login web** (`/login`):
  - Formulario de acceso que reutiliza la misma lógica de `AuthService`.
  - Guarda al usuario autenticado en sesión HTTP.
  - Redirige a `/productos` tras un login correcto.
  - Cierre de sesión desde `/logout`.
  - Todas las páginas del panel (`/`, `/productos`) están protegidas por `SesionInterceptor`: si no hay sesión activa, redirige a `/login`.

## Requisitos

- JDK 17 instalado.
- Maven instalado (o usar el wrapper `mvnw` incluido en el proyecto).
- MySQL Server instalado y en ejecución (opcional para desarrollo; H2 en memoria funciona sin instalar nada).
- Editor recomendado: IntelliJ IDEA, Eclipse, NetBeans o VS Code.

## Configuración de base de datos

El proyecto trae **H2 en memoria** configurada por defecto en `application.properties`, ideal para desarrollo rápido y pruebas: no requiere instalar nada, pero los datos se pierden al reiniciar la aplicación.

```properties
spring.datasource.url=${DB_URL:jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE}
spring.datasource.username=${DB_USER:sa}
spring.datasource.password=${DB_PASSWORD:}
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
```

### Cambiar a MySQL (datos persistentes)

1. Cree la base de datos ejecutando el script:

```text
sql/productos.sql
```

2. Configure las variables de entorno antes de levantar la aplicación:

```powershell
set DB_URL=jdbc:mysql://localhost:3306/helamiel?useUnicode=true&characterEncoding=UTF-8&serverTimezone=America/Bogota
set DB_USER=root
set DB_PASSWORD=su_password
```

3. Cambie en `application.properties`:

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

Con `ddl-auto=update`, Hibernate crea/actualiza automáticamente las tablas faltantes (como `usuarios`) a partir de las entidades.

## Instalación

Clone o descargue el repositorio:

```powershell
git clone https://github.com/Rockstarzr123/HELAMIEL.git
cd HELAMIEL
```

## Ejecución

```powershell
./mvnw spring-boot:run
```

La aplicación queda disponible en:

```text
http://localhost:9090
```

- Página de inicio / rutas: `http://localhost:9090/`
- Panel de productos: `http://localhost:9090/productos`
- Login web: `http://localhost:9090/login`

## Testing de la API con Postman

El módulo de autenticación se probó con Postman. La colección con los casos de prueba (registro exitoso, usuario duplicado, validación de contraseña, login correcto e incorrecto) se encuentra en el repositorio de evidencias del curso.

| Método | Endpoint | Éxito | Error |
|---|---|---|---|
| POST | `/api/auth/register` | 201 Created | 409 Conflict / 400 Bad Request |
| POST | `/api/auth/login` | 200 OK | 401 Unauthorized |

## Capturas sugeridas para la evidencia

- Página de login.
- Login exitoso y redirección al panel de productos.
- Intento de acceso a `/productos` sin sesión (redirección a `/login`).
- Listado de productos.
- Registro, edición y eliminación de un producto.
- Búsqueda y filtros de productos.
- Registro de una ruta desde la página de inicio.
- Requests de Postman: registro y login (casos de éxito y error).
- Proyecto ejecutándose (`spring-boot:run`).
- Repositorio Git/GitHub actualizado.

## Autor

Alejandro

## Licencia

MIT

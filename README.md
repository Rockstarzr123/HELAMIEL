# HELAMIEL-WEB

## Descripcion

HELAMIEL-WEB es una aplicacion Java Web para la administracion de productos de una heladeria. El sistema implementa un CRUD completo de productos usando Servlets, JSP, JDBC y MySQL, manteniendo una arquitectura MVC y reutilizando el modelo, el DAO y la conexion del proyecto de escritorio desarrollado previamente.

La aplicacion permite registrar, consultar, buscar por ID, editar y eliminar productos desde una interfaz web responsive construida con HTML5, CSS3 y Bootstrap 5.

## Tecnologias

- Java 17
- Maven
- Apache Tomcat 10 o superior
- Jakarta Servlet
- JSP
- JSTL
- JDBC
- MySQL
- MySQL Connector/J
- HTML5
- CSS3
- Bootstrap 5
- Git y GitHub

## Arquitectura

El proyecto mantiene una separacion MVC:

- `modelo`: contiene la clase `Producto`.
- `dao`: contiene `ProductoDAO`, responsable de las operaciones CRUD con JDBC.
- `conexion`: contiene `Conexion`, responsable de abrir la conexion con MySQL.
- `servlet`: contiene los controladores web que reciben peticiones GET y POST.
- `util`: contiene validaciones y utilidades reutilizables.
- `src/main/webapp`: contiene recursos web, JSP, CSS, JS e imagenes.
- `WEB-INF/views`: contiene las paginas JSP protegidas de acceso directo.

## Funcionalidades

- Pagina principal del sistema.
- Listado responsive de productos.
- Busqueda de producto por ID.
- Registro de productos.
- Edicion de productos.
- Eliminacion con confirmacion previa.
- Validaciones en cliente mediante HTML5.
- Validaciones en servidor mediante Java.
- Mensajes de exito y error con alertas Bootstrap.
- Uso de GET para consultar, abrir formularios y confirmar eliminacion.
- Uso de POST para registrar, actualizar y eliminar definitivamente.

## Requisitos

- JDK 17 instalado.
- Maven instalado.
- Apache Tomcat 10 o superior instalado.
- MySQL Server instalado y en ejecucion.
- Base de datos `helamiel` creada con el script SQL del proyecto.
- Editor recomendado: IntelliJ IDEA, Eclipse, NetBeans o VS Code.

## Configuracion de MySQL

Ejecute el script ubicado en:

```text
src/main/resources/sql/helamiel.sql
```

Desde MySQL Workbench puede abrir el archivo y ejecutarlo completo.

Desde consola:

```powershell
mysql -u root -p < src\main\resources\sql\helamiel.sql
```

Luego verifique:

```sql
USE helamiel;
SELECT * FROM Productos;
```

Revise las credenciales de conexion en:

```text
src/main/java/conexion/Conexion.java
```

## Configuracion de Tomcat

1. Instale Apache Tomcat 10 o superior.
2. Configure Tomcat en el IDE.
3. Agregue el artefacto Maven generado por el proyecto.
4. Use el contexto recomendado:

```text
/HELAMIEL-WEB
```

5. Inicie el servidor y abra:

```text
http://localhost:8080/HELAMIEL-WEB/
```

## Instalacion

Clone o descargue el repositorio:

```powershell
git clone https://github.com/Rockstarzr123/HELAMIEL.git
cd HELAMIEL-JDBC
```

Compile el proyecto:

```powershell
mvn clean package
```

El archivo WAR se genera en:

```text
target/HELAMIEL-WEB.war
```

## Ejecucion

### Opcion 1: desde el IDE

1. Abra el proyecto como proyecto Maven.
2. Configure Apache Tomcat.
3. Despliegue el artefacto `HELAMIEL-WEB.war`.
4. Ejecute Tomcat.
5. Abra `http://localhost:8080/HELAMIEL-WEB/`.

### Opcion 2: despliegue manual

1. Ejecute:

```powershell
mvn clean package
```

2. Copie `target/HELAMIEL-WEB.war` en la carpeta `webapps` de Tomcat.
3. Inicie Tomcat.
4. Abra:

```text
http://localhost:8080/HELAMIEL-WEB/
```

## Capturas sugeridas para la evidencia

- Pagina principal.
- Listado de productos.
- Formulario de registro.
- Producto registrado correctamente.
- Busqueda por ID.
- Formulario de edicion.
- Producto actualizado correctamente.
- Confirmacion de eliminacion.
- Producto eliminado correctamente.
- Validacion de campos obligatorios.
- Validacion de precio negativo.
- Validacion de stock invalido.
- Mensaje de duplicidad de producto.
- Proyecto ejecutandose en Tomcat.
- Repositorio Git/GitHub actualizado.

## Versionamiento sugerido

```powershell
git status
git add .
git commit -m "config: convertir proyecto a aplicacion web con Maven"
git commit -m "feat: implementar CRUD de productos con Servlets y JSP"
git commit -m "style: crear interfaz Bootstrap para HELAMIEL"
git commit -m "test: agregar validaciones del modulo de productos"
git push origin main
```

## Autor

Alejandro

## Licencia

MIT

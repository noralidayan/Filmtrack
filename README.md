# Filmtrack

Proyecto en Java + Hibernate para manejar contenido audiovisual: películas y series. Permite crear usuarios, iniciar sesión, agregar favoritos, registrar visualizaciones y ver historial.

---

## 🔹 Contenido del repositorio

- `src/main/java/com/filmtrack/` → Código fuente en Java
- `src/main/resources/META-INF/persistence.xml` → Configuración de Hibernate/JPA
- `src/main/resources/filmtrack.sql` → Script SQL para crear la base de datos y cargar datos iniciales

---

## 🔹 Configuración de la base de datos

1. Abrir MySQL o phpMyAdmin.
2. Crear una nueva base de datos (por ejemplo `filmtrack`).
3. Ejecutar el script SQL que está en `src/main/resources/filmtrack.sql` para crear todas las tablas y cargar los datos iniciales:

```sql
source src/main/resources/filmtrack.sql;
Revisar que el usuario y contraseña en persistence.xml coincidan con tu configuración local de MySQL:

xml
Copiar código
<property name="jakarta.persistence.jdbc.user" value="root"/>
<property name="jakarta.persistence.jdbc.password" value=""/>
🔹 Cómo ejecutar
Abrir el proyecto en IntelliJ IDEA.

Correr la clase principal:

text
Copiar código
com.filmtrack.UI.PruebasInteractivo
Seguir el menú interactivo para:

Crear usuario

Iniciar sesión

Agregar favoritos

Agregar al historial

Ver favoritos o historial


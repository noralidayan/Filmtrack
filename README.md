# 🎬 FilmTrack

**FilmTrack** es una aplicación desarrollada en **Java + Spring Boot**, con integración de un **front-end en HTML, CSS y JavaScript**, que permite a los usuarios gestionar y registrar sus películas y series favoritas.
Incluye funcionalidades de inicio de sesión, historial de visualizaciones, favoritos y administración de contenidos.

---

## 🚀 Versión actual

**Versión funcional completa – Spring + Front integrado (noviembre 2025)**
Esta es la versión estable fusionada a `master`, con:

* Backend en **Spring Boot 3.5.x**
* Conexión a base de datos **MySQL / JPA / Hibernate**
* Front-end moderno (HTML + CSS + JS)
* Controladores REST y capa de servicios modular
* Manejo de entidades `Usuario`, `ContenidoAudiovisual`, `Visualizacion`, entre otras

---

## 🛠️ Tecnologías utilizadas

* **Java 21**
* **Spring Boot**
* **Hibernate / JPA**
* **MySQL**
* **Maven**
* **HTML5 / CSS3 / JavaScript**
* **IntelliJ IDEA**

---

## 🤖 IA como aliada de desarrollo

FilmTrack se desarrolló con el apoyo de **Inteligencia Artificial (ChatGPT)** como asistente técnico y creativo.
La IA fue una herramienta clave para potenciar mi aprendizaje y productividad, ayudándome a:

* Mejorar la arquitectura del proyecto (MVC + capas)
* Resolver errores en **Hibernate**, **Spring Boot** y la capa de persistencia
* Generar ideas para optimizar la estructura del código
* Crear interfaces web limpias, funcionales y coherentes con el diseño del sistema
* Documentar el proceso y mantener una visión clara del proyecto

💬 La IA fue utilizada como *asistente de desarrollo*, **sin reemplazar el razonamiento ni la lógica propia**, sino fortaleciendo mis conocimientos en **Programación Orientada a Objetos** y buenas prácticas en programación.

---

## 🗃️ Base de datos

El archivo `filmtrack-spring.sql` contiene el script de creación y carga inicial de datos del proyecto.
Antes de ejecutar la aplicación, importá este archivo en tu servidor **MySQL** para generar las tablas y relaciones necesarias.

---

## 💻 Ejecución

### 🧩 Requisitos previos

Para ejecutar el proyecto correctamente, asegurate de tener instalado:

* **Java JDK 21** o superior
* **Apache Maven**
* **Servidor MySQL** (por ejemplo, XAMPP o Workbench)
* **IDE recomendado:** IntelliJ IDEA

Configurá la conexión en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/filmtrack_spring
spring.datasource.username=root
spring.datasource.password=
```

### 🚀 Pasos para iniciar

1. Cloná el repositorio:

   ```bash
   git clone https://github.com/noralidayan/Filmtrack
   ```
2. Importá el script SQL (`filmtrack-spring.sql`) en tu base de datos local.
3. Ejecutá el proyecto:

   ```bash
   mvn spring-boot:run
   ```

---

## 🌐 Formas de ejecutar el Front-End

FilmTrack puede ejecutarse de **tres maneras diferentes**, según el entorno o la necesidad:

### 🩵 1. **Versión integrada (recomendada)**

👉 [http://localhost:8080/filmtrack.html](http://localhost:8080/filmtrack.html)

Ejecuta el backend y el frontend juntos en el mismo servidor Spring Boot.

---

### 💻 2. **Desde IntelliJ IDEA (modo desarrollo)**

Abrí el archivo:

```
src/main/resources/static/filmtrack.html
```

y seleccioná **Open in Browser** para visualizar la interfaz mientras el backend corre desde IntelliJ.

---

### 🌍 3. **Desde Live Server (Visual Studio Code)**

Abrí el archivo `filmtrack.html` con **Live Server**, que abrirá una URL similar a:

```
http://127.0.0.1:5500/filmtrack.html
```

Gracias a la configuración CORS en `WebConfig.java`, el front se comunica con el backend en `http://localhost:8080`.

---

## 📂 Estructura del proyecto

```
src/
 ├── main/
 │   ├── java/com/filmtrack/
 │   │   ├── controller/                  # Controladores REST (definen los endpoints)
 │   │   ├── service/                     # Lógica de negocio y validaciones
 │   │   ├── repository/                  # Acceso a datos (JPA / Repositorios)
 │   │   ├── model/                       # Entidades del dominio
 │   │   ├── WebConfig.java               # Configuración CORS y recursos estáticos
 │   │   ├── FilmtrackWebApplication.java # Clase principal (Spring Boot)
 │   │   └── TestApi.java                 # Clase auxiliar para pruebas locales
 │   └── resources/
 │       ├── META-INF/
 │       ├── static/                      # Archivos del front-end (HTML, CSS, JS)
 │       │   ├── filmtrack.html
 │       │   ├── login.html
 │       │   ├── favoritos.html
 │       │   ├── style.css
 │       │   └── script.js
 │       └── application.properties       # Configuración del entorno y DB
 ├── Filmtrack-spring.sql                 # Script SQL de base de datos
 ├── pom.xml                              # Dependencias y configuración de Maven
 └── target/                              # Archivos compilados generados
```
## 🌍 Repositorio y demo del Front-End

FilmTrack cuenta también con un **repositorio dedicado al front-end**, desarrollado de forma modular y desplegado en GitHub Pages para su visualización pública.

🔗 Repositorio del front-end: [Filmtrack_Frontend](https://github.com/noralidayan/Filmtrack_Frontend)  
🌐 Versión online: [https://noralidayan.github.io/Filmtrack_Frontend/](https://noralidayan.github.io/Filmtrack_Frontend/)

> El front-end consume los endpoints REST del backend alojado en este mismo repositorio (`http://localhost:8080` durante la ejecución local).

---

## ✨ Créditos

Desarrollado por **Norali Lucía Dayan**
📚 Proyecto académico y de práctica profesional – *Tecnicatura Superior en Análisis de Sistemas*
💻 GitHub: [noralidayan](https://github.com/noralidayan)

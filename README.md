# Red Académica UQ 🎓

**Red Académica UQ** es una plataforma digital de aprendizaje colaborativo diseñada para fortalecer el ecosistema académico de la **Universidad del Quindío**. El proyecto combina un repositorio inteligente de materiales educativos con un sistema de gestión de tutorías entre pares, actuando como una extensión innovadora del sistema institucional **SARA**.

---

## 👥 Autores

* **Hulbert A. Arango Fajardo**
* **Julián A. Ladino Moreno**
* **Mauricio Rios de la Ossa**
* **Nestor F. Castelblanco**

---

## 🎯 Propósito del Proyecto

Enfrentamos la necesidad de centralizar el conocimiento generado por los estudiantes (apuntes, videos, resúmenes) y facilitar el acceso a tutorías flexibles. **Red Academica UQ** busca:

* Reducir la deserción estudiantil.
* Fomentar la autogestión del conocimiento mediante Ambientes Virtuales de Aprendizaje (AVA).
* Promover la solidaridad académica entre semestres.

---

## ✨ Características Principales

1. **Repositorio Colaborativo:** Espacio para subir y calificar Objetos Virtuales de Aprendizaje (OVA) y microcursos tipo MOOC.
2. **Agenda de Asesorías:** Módulo para programar tutorías con mentores certificados de semestres superiores.
3. **Integración Institucional:** Diseñado para interoperar con el sistema SARA, permitiendo a los tutores obtener reconocimiento por sus horas sociales o créditos.
4. **Búsqueda Inteligente:** Filtros avanzados por facultad, programa y nivel de dificultad.

---

## 🛠️ Stack Tecnológico

### Backend

* **Lenguaje:** Java 21 (Microsoft Build of OpenJDK)
* **Framework:** Spring Boot 3.4.0
* **Gestor de Dependencias:** Gradle
* **Base de Datos:** MongoDB (Persistencia de documentos NoSQL)
* **Seguridad:** Firebase Auth & Spring Security

### Frontend

* **Framework:** Angular (TypeScript)
* **Estilos:** HTML5, CSS3 (Responsivo y Accesible)

---

## 🚀 Configuración del Entorno de Desarrollo

Para replicar el entorno de desarrollo actual, sigue estos pasos:

### Requisitos Previos

* **JDK 21** (Se recomienda Microsoft OpenJDK).
* **MongoDB Community Server** corriendo en `localhost:27017`.
* **IntelliJ IDEA** (o tu IDE de preferencia).

### Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/tu-usuario/red_academica-backend.git

```


2. Importa el proyecto como un proyecto Gradle.
3. Configura el archivo `src/main/resources/application.properties`:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/redacademicauq

```


4. Ejecuta la aplicación:
```bash
./gradlew bootRun

```



---

## 📈 Metodología de Trabajo

El proyecto se desarrolla bajo el marco de trabajo **Scrum**, con iteraciones cortas (Sprints) de validación continua en los laboratorios de la **Facultad de Ingeniería** de la Universidad del Quindío.

---

## ⚖️ Naturaleza del Proyecto

* **Tipo:** Público Universitario / Innovación Educativa.
* **Impacto:** Transformación digital, bienestar estudiantil y permanencia académica.

---

⭐ *Desarrollado con compromiso para la comunidad Uniquindiana.*
# 📚 Literalura

Aplicación de consola desarrollada en Java con Spring Boot que permite buscar libros desde la API de Gutendex, almacenarlos en una base de datos PostgreSQL y realizar consultas estadísticas sobre los libros y autores guardados.

---

## 🚀 Tecnologías utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

---

## 📌 Funcionalidades

✔ Buscar libro por título (consumo de API externa)  
✔ Guardar libro y autor en base de datos  
✔ Evitar duplicados de libros y autores  
✔ Listar libros guardados  
✔ Listar libros por idioma  
✔ Listar autores registrados  
✔ Listar autores vivos en determinado año  
✔ Mostrar estadísticas de libros por idioma  
✔ Mostrar estadísticas de descargas  
✔ Top 10 libros más descargados  
✔ Buscar autor por nombre  
✔ Listar autores nacidos después de determinado año  

---

## 🗄 Base de datos

El proyecto utiliza PostgreSQL.

Se debe crear manualmente la base de datos:

```sql
CREATE DATABASE literalura;
```

Las tablas se crean automáticamente gracias a:

```properties
spring.jpa.hibernate.ddl-auto=update
```

---

## ⚙ Configuración

En el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.main.web-application-type=none
```

⚠ Reemplazar usuario y contraseña según configuración local.

---

## ▶ Cómo ejecutar el proyecto

1. Clonar el repositorio
2. Crear la base de datos `literalura` en PostgreSQL
3. Configurar usuario y contraseña en `application.properties`
4. Ejecutar el proyecto desde el IDE o con:

```bash
mvn spring-boot:run
```

---

## 📊 Ejemplo de menú

```
1 - Buscar libro por título
2 - Listar libros guardados
3 - Listar libros por idioma
4 - Listar autores
5 - Listar autores vivos en determinado año
6 - Mostrar estadísticas
7 - Estadísticas de descargas
8 - Top 10 libros más descargados
9 - Buscar autor por nombre
10 - Listar autores nacidos después de determinado año
0 - Salir
```

---

## 🧠 Conceptos aplicados

- Consumo de API REST
- Conversión JSON a objetos Java
- Relaciones @ManyToOne en JPA
- Derived Queries
- Streams y DoubleSummaryStatistics
- Manejo de Optional
- Persistencia con Hibernate

---

## 📎 API utilizada

Gutendex API  
https://gutendex.com/

---

## 👨‍💻 Autor

Proyecto desarrollado por Alexandro Chamochumbi
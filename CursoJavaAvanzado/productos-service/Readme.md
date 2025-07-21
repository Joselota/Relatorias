# 🛍️ productos-service

Microservicio REST desarrollado con **Spring Boot** que permite gestionar productos.  
Este servicio es parte de una arquitectura orientada al dominio, y está pensado como ejemplo didáctico para enseñanza de backend moderno.

---

## 📦 Funcionalidades

- `GET /productos` → Listar todos los productos disponibles
- `POST /productos` → Agregar un nuevo producto enviando JSON

---

## ⚙️ Requisitos

- Java 17+
- Apache Maven
- IDE recomendado: VS Code o IntelliJ
- Postman o cURL para pruebas

---

## 🚀 ¿Cómo ejecutar?

1. Clona el repositorio o descarga la carpeta `productos-service`
2. En consola:

```bash
cd productos-service
mvn spring-boot:run

## Endpoints

- `GET /productos`  
  Lista todos los productos.

- `POST /productos`  
  Crea un nuevo producto.  
  Ejemplo de cuerpo JSON:
  ```json
  {
    "id": 1,
    "nombre": "Producto ejemplo",
    "precio": 10.5,
    "stock": 100
  }
  ```

## Estructura del proyecto

- Código fuente: `src/main/java/com/example/productos/`
- Configuración: `src/main/resources/application.properties`

## Autor
- Ingrid Solís
- Basado en Spring Boot Starter
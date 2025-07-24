# 🧩 Patrones de Diseño en Java con Spring Boot

Este proyecto es una demostración práctica de los patrones de diseño más comunes en entornos Java: **DAO**, **Singleton**, **Factory** y **Service**, integrados en una arquitectura Spring Boot básica.

---

## 📦 Estructura del Proyecto

- `controller/`: contiene los controladores REST.
- `service/`: lógica de negocio central.
- `dao/`: acceso a datos simulado.
- `singleton/`: clase `Logger` como Singleton.
- `factory/`: creación dinámica de clientes REST.
- `model/`: clase `Producto`.

---

## 🚀 Cómo ejecutar

1. Clona o descomprime el proyecto.
2. Ábrelo con IntelliJ IDEA o VS Code.
3. Ejecuta la clase `PatronesApplication.java`.
4. Accede a los endpoints:

```bash
GET http://localhost:8080/productos/1
POST http://localhost:8080/productos
Content-Type: application/json
{
  "id": 2,
  "nombre": "Producto nuevo"
}
```

---

## 🎯 Actividad guiada

### 1. Revisión del código

- Observa cómo el controlador llama al `ProductoService`.
- Revisa cómo `ProductoService` accede al DAO (`ProductoDaoImpl`).
- Observa el uso del `Logger` (Singleton) en el servicio.
- Explora la `Factory` que retorna diferentes tipos de clientes.

---

### 2. Ejercicios sugeridos

#### DAO
- Simula una lista en memoria que almacene productos nuevos.
- Agrega método `findAll()` y devuélvelo en un nuevo endpoint `GET /productos`.

#### Singleton
- Mejora el `Logger` para que incluya hora de log y tipo de mensaje.

#### Factory
- Crea un nuevo tipo de cliente: `ClienteRESTPremium`.

#### Service
- Agrega validación: si el nombre del producto está vacío, lanza excepción.

---

### 3. Desafío final

- Crea un nuevo microservicio `ClienteService` y `ClienteController`.
- Usa Factory para crear `Cliente` según rol (`admin`, `usuario`, `visitante`).
- Registra los accesos con el Singleton `Logger`.

---

## 🧠 Preguntas de reflexión

1. ¿Qué ventaja ofrece DAO frente a escribir directamente en la BD?
2. ¿Por qué es útil el patrón Singleton en aplicaciones backend?
3. ¿Cómo te ayuda Factory a extender funcionalidades fácilmente?
4. ¿Cuál patrón consideras más útil en una arquitectura de microservicios?

---

## 📚 Recursos recomendados

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Design Patterns - GoF](https://refactoring.guru/design-patterns/catalog)
- Libro: *Head First Design Patterns*


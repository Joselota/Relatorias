# 👥 Sistema de Gestión de Empleados

Una aplicación REST API desarrollada con **Spring Boot** para la gestión de empleados de una empresa.

## 📋 Descripción

Este proyecto forma parte del **Curso Java Avanzado** y demuestra la implementación de una API REST completa para la gestión de empleados, incluyendo operaciones CRUD y funcionalidades adicionales como filtros por departamento y estado.

## 🚀 Características

- ✅ **API REST completa** con endpoints para todas las operaciones CRUD
- ✅ **Gestión de empleados** con información detallada
- ✅ **Filtros avanzados** por departamento y estado
- ✅ **Simulación de base de datos** en memoria
- ✅ **Arquitectura MVC** siguiendo mejores prácticas
- ✅ **Documentación completa** de endpoints

## 🛠️ Tecnologías Utilizadas

- **Java 17+**
- **Spring Boot 3.5.4**
- **Spring Web** (para REST API)
- **Maven** (gestión de dependencias)
- **Spring Boot DevTools** (desarrollo)

## 📦 Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/isolis/demo/
│   │       ├── ProyectoApplication.java     # Clase principal
│   │       ├── controllers/
│   │       │   └── EjemploController.java   # Controlador REST
│   │       └── models/
│   │           └── Empleado.java            # Modelo de datos
│   └── resources/
│       ├── application.properties           # Configuración
│       ├── static/                         # Archivos estáticos
│       └── templates/                      # Plantillas
└── test/                                   # Tests unitarios
```

## 🏃‍♂️ Cómo Ejecutar

### Prerrequisitos
- Java 17 o superior
- Maven 3.6+ (o usar el Maven wrapper incluido)

### Ejecución

1. **Clona o descarga el proyecto**
   ```bash
   cd /Users/desarrollo/Relatorias/CursoJavaAvanzado/demo
   ```

2. **Ejecuta la aplicación**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   O si tienes Maven instalado:
   ```bash
   mvn spring-boot:run
   ```

3. **Accede a la aplicación**
   
   La aplicación estará disponible en: `http://localhost:8080`

## 📡 Endpoints de la API

### Base URL: `http://localhost:8080/api/empleados`

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/` | Obtener todos los empleados |
| `GET` | `/{id}` | Obtener empleado por ID |
| `POST` | `/` | Crear nuevo empleado |
| `PUT` | `/{id}` | Actualizar empleado completo |
| `DELETE` | `/{id}` | Eliminar empleado |
| `PATCH` | `/{id}/baja` | Dar de baja empleado |
| `PATCH` | `/{id}/reactivar` | Reactivar empleado |
| `GET` | `/departamento/{departamento}` | Buscar por departamento |
| `GET` | `/activos` | Obtener empleados activos |

## 📝 Ejemplos de Uso

### 1. Obtener todos los empleados
```bash
curl -X GET http://localhost:8080/api/empleados
```

### 2. Obtener empleado por ID
```bash
curl -X GET http://localhost:8080/api/empleados/1
```

### 3. Crear nuevo empleado
```bash
curl -X POST http://localhost:8080/api/empleados \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ana",
    "apellido": "López",
    "email": "ana.lopez@empresa.com",
    "telefono": "555-0000",
    "puesto": "Diseñadora",
    "departamento": "Marketing",
    "salario": 48000.0
  }'
```

### 4. Buscar empleados por departamento
```bash
curl -X GET http://localhost:8080/api/empleados/departamento/IT
```

### 5. Obtener empleados activos
```bash
curl -X GET http://localhost:8080/api/empleados/activos
```

## 👤 Modelo de Empleado

```json
{
  "id": 1,
  "nombre": "Juan",
  "apellido": "Pérez",
  "email": "juan.perez@empresa.com",
  "telefono": "555-1234",
  "puesto": "Desarrollador",
  "departamento": "IT",
  "salario": 50000.0,
  "fechaContratacion": "2020-01-15",
  "activo": true
}
```

## 📊 Datos de Ejemplo

La aplicación viene precargada con 3 empleados de ejemplo:

1. **Juan Pérez** - Desarrollador (IT)
2. **María González** - Analista (IT)  
3. **Carlos Rodríguez** - Gerente (Ventas)

## 🧪 Testing

Para ejecutar los tests:

```bash
./mvnw test
```

## 📈 Próximas Mejoras

- [ ] Integración con base de datos real (H2, MySQL, PostgreSQL)
- [ ] Implementación de Spring Data JPA
- [ ] Validaciones de entrada con Bean Validation
- [ ] Seguridad con Spring Security
- [ ] Documentación automática con Swagger/OpenAPI
- [ ] Tests unitarios y de integración
- [ ] Manejo de excepciones centralizado
- [ ] Logging estructurado

## 🤝 Contribución

Este proyecto es parte de un curso educativo. Si encuentras algún error o tienes sugerencias:

1. Abre un issue describiendo el problema
2. Propón mejoras en la implementación
3. Comparte ideas para nuevas funcionalidades

## 📄 Licencia

Este proyecto es con fines educativos como parte del **Curso Java Avanzado**.

## 👨‍💻 Autor

- **Desarrollador**: [Tu Nombre]
- **Curso**: Java Avanzado
- **Año**: 2025

---

## 📚 Referencias Útiles

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
- [Maven Documentation](https://maven.apache.org/guides/index.html)

---

💡 **¿Tienes preguntas?** No dudes en consultar la documentación o abrir un issue en el repositorio.

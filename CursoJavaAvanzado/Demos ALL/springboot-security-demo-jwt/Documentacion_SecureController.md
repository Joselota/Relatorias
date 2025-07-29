# 🔒 **SecureController.java - Controlador Protegido**

## Introducción
Este controlador maneja los **endpoints protegidos** que requieren autenticación JWT. Demuestra cómo funcionan las rutas que necesitan un token válido para ser accedidas.

---

## 🏷️ **Anotaciones de la clase**

```java
@RestController
@RequestMapping("/api/private")
public class SecureController {
```

### Explicación:
- **`@RestController`**: Combina `@Controller` + `@ResponseBody`
  - Todos los métodos devuelven datos directamente (JSON/texto)
  - Respuestas automáticamente serializadas
- **`@RequestMapping("/api/private")`**: 
  - Todas las rutas empiezan con `/api/private`
  - **Importante**: Esta ruta **NO** está en las rutas públicas del `SecurityConfig`
  - **Resultado**: Requiere autenticación JWT

---

## 🔐 **Endpoint Protegido**

```java
@GetMapping("/data")
public String getSecureData() {
    return "Este contenido está protegido con JWT 🔒";
}
```

### **Análisis detallado:**

#### **Anotación del método:**
- **`@GetMapping("/data")`**: Maneja peticiones GET a `/api/private/data`
- **¿Por qué GET?** Porque solo estamos obteniendo datos, no modificando nada

#### **Seguridad automática:**
- **Sin autenticación**: Devuelve HTTP 401 Unauthorized
- **Con token válido**: Devuelve el contenido protegido
- **Con token inválido**: Devuelve HTTP 401 Unauthorized

#### **Respuesta:**
- **Tipo**: Texto plano simple
- **Contenido**: Mensaje confirmando que el acceso fue exitoso
- **Status HTTP**: 200 OK (solo si está autenticado)

---

## 🔄 **Flujo de acceso protegido**

### **1. Sin token (ACCESO DENEGADO):**
```bash
GET /api/private/data
# Sin header Authorization
```

**Respuesta:**
```
HTTP/1.1 401 Unauthorized
Content-Type: application/json

{
  "timestamp": "2025-07-24T13:15:50.000+00:00",
  "status": 401,
  "error": "Unauthorized",
  "path": "/api/private/data"
}
```

### **2. Con token válido (ACCESO PERMITIDO):**
```bash
GET /api/private/data
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...
```

**Respuesta:**
```
HTTP/1.1 200 OK
Content-Type: text/plain

Este contenido está protegido con JWT 🔒
```

### **3. Con token inválido (ACCESO DENEGADO):**
```bash
GET /api/private/data
Authorization: Bearer token_malformado_o_expirado
```

**Respuesta:**
```
HTTP/1.1 401 Unauthorized
```

---

## 🛡️ **¿Cómo funciona la protección?**

### **Proceso automático de Spring Security:**

1. **📨 Petición llega** a `/api/private/data`

2. **🔍 JwtFilter intercepta**:
   - Busca header `Authorization: Bearer <token>`
   - Si encuentra token → lo valida con `JwtUtil`
   - Si es válido → establece autenticación en `SecurityContext`

3. **🛡️ SecurityConfig evalúa**:
   - Verifica que `/api/private/**` NO está en rutas públicas
   - Verifica si el usuario está autenticado
   - ✅ Autenticado → Permite acceso
   - ❌ No autenticado → Devuelve 401

4. **🎯 Controlador ejecuta** (solo si autenticado):
   - Ejecuta `getSecureData()`
   - Devuelve respuesta

---

## 🚀 **Casos de uso prácticos**

### **Obtener token primero:**
```bash
# 1. Login para obtener token
curl -X POST http://localhost:8080/api/auth/login \
  -d "username=ingrid&password=1234"

# Respuesta: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...
```

### **Usar token para acceder a datos protegidos:**
```bash
# 2. Usar el token para acceder a contenido protegido
curl -X GET http://localhost:8080/api/private/data \
  -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9..."

# Respuesta: Este contenido está protegido con JWT 🔒
```

### **Intentar acceso sin token:**
```bash
# 3. Intentar acceso sin autenticación
curl -X GET http://localhost:8080/api/private/data

# Respuesta: 401 Unauthorized
```

---

## 💡 **Ejemplo con JavaScript/Frontend**

### **Código JavaScript típico:**
```javascript
// 1. Obtener token del login
const loginResponse = await fetch('/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: 'username=ingrid&password=1234'
});
const token = await loginResponse.text(); // "Bearer eyJ0..."

// 2. Guardar token
localStorage.setItem('token', token);

// 3. Usar token para acceder a datos protegidos
const secureResponse = await fetch('/api/private/data', {
    method: 'GET',
    headers: { 'Authorization': token }
});

if (secureResponse.ok) {
    const data = await secureResponse.text();
    console.log(data); // "Este contenido está protegido con JWT 🔒"
} else {
    console.log('Acceso denegado'); // Si token es inválido
}
```

---

## 🔧 **Expansión para casos reales**

### **1. Endpoints adicionales típicos:**
```java
@RestController
@RequestMapping("/api/private")
public class SecureController {

    @GetMapping("/profile")
    public UserProfile getUserProfile(Authentication auth) {
        String username = auth.getName();
        return userService.getProfile(username);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody CreateOrderRequest request, Authentication auth) {
        String username = auth.getName();
        return orderService.createOrder(request, username);
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
```

### **2. Acceso a información del usuario autenticado:**
```java
@GetMapping("/my-info")
public String getMyInfo(Authentication authentication) {
    String username = authentication.getName();
    return "Hola " + username + ", estás autenticado!";
}
```

### **3. Diferentes niveles de autorización:**
```java
@GetMapping("/user-data")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public String getUserData() {
    return "Datos para usuarios normales y admins";
}

@GetMapping("/admin-data")
@PreAuthorize("hasRole('ADMIN')")
public String getAdminData() {
    return "Datos solo para administradores";
}
```

---

## 📱 **Integración con diferentes clientes**

### **React/Angular:**
```javascript
// Interceptor automático para añadir token
axios.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = token;
    }
    return config;
});

// Uso normal
const data = await axios.get('/api/private/data');
```

### **Aplicación móvil (Android/iOS):**
```java
// Android con Retrofit
@GET("/api/private/data")
Call<String> getSecureData(@Header("Authorization") String token);

// Uso
String token = "Bearer " + jwtToken;
retrofit.getSecureData(token).enqueue(callback);
```

### **Postman/Testing:**
```
GET http://localhost:8080/api/private/data
Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...
```

---

## 🔍 **Debugging y troubleshooting**

### **Problemas comunes:**

#### **1. Token no enviado:**
```bash
curl -X GET http://localhost:8080/api/private/data
# Error: Sin header Authorization
# Solución: Agregar -H "Authorization: Bearer <token>"
```

#### **2. Token malformado:**
```bash
curl -X GET http://localhost:8080/api/private/data \
  -H "Authorization: Bearer token_incorrecto"
# Error: JwtFilter no puede validar el token
# Solución: Verificar que el token esté bien copiado
```

#### **3. Token expirado:**
```bash
# Token generado hace más de 1 hora (según JwtUtil)
# Error: Token expirado
# Solución: Hacer login nuevamente para obtener token nuevo
```

#### **4. Formato incorrecto del header:**
```bash
curl -X GET http://localhost:8080/api/private/data \
  -H "Authorization: eyJ0eXAi..."  # Falta "Bearer "
# Error: JwtFilter espera "Bearer " al inicio
# Solución: Agregar "Bearer " antes del token
```

---

## 📚 **Conceptos clave a recordar**

1. **Endpoint protegido** = Requiere token JWT válido
2. **`/api/private/**`** = Ruta NO incluida en rutas públicas
3. **`Authorization` header** = Debe incluir "Bearer " + token
4. **Validación automática** = JwtFilter + SecurityConfig trabajan juntos
5. **401 Unauthorized** = Respuesta cuando no hay token válido
6. **Authentication object** = Contiene información del usuario autenticado
7. **Stateless** = Cada petición debe incluir el token

---

## 🔒 **Consideraciones de seguridad**

### **Buenas prácticas implementadas:**
- ✅ Validación automática de tokens
- ✅ Respuesta estándar 401 para acceso denegado
- ✅ No exposición de información sensible en errores

### **Mejoras adicionales recomendadas:**
- 🔧 Logging de intentos de acceso
- 🔧 Rate limiting por usuario
- 🔧 Validación de roles específicos
- 🔧 Auditoría de accesos exitosos
- 🔧 Renovación automática de tokens

---

*Documento creado para explicar el controlador de endpoints protegidos del proyecto Spring Boot con JWT*

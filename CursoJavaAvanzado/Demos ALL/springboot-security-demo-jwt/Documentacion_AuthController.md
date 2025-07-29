# 🔐 **AuthController.java - Controlador de Autenticación**

## Introducción
Este controlador maneja las operaciones de **autenticación** de la aplicación. Es el punto de entrada para que los usuarios inicien sesión y obtengan sus tokens JWT.

---

## 🏷️ **Anotaciones de la clase**

```java
@RestController
@RequestMapping("/api/auth")
public class AuthController {
```

### Explicación:
- **`@RestController`**: Combina `@Controller` + `@ResponseBody`
  - Todos los métodos devuelven datos directamente (JSON/texto)
  - No necesitas agregar `@ResponseBody` en cada método
- **`@RequestMapping("/api/auth")`**: 
  - Todas las rutas de este controlador empezarán con `/api/auth`
  - Ejemplo: `/api/auth/login`, `/api/auth/hello`

---

## 🔧 **Inyección de dependencias**

```java
@Autowired
private JwtUtil jwtUtil;
```

### Explicación:
- **Inyecta `JwtUtil`** para poder generar tokens JWT
- **`@Autowired`**: Spring busca automáticamente una instancia de JwtUtil
- **Uso**: Generar tokens cuando las credenciales son válidas

---

## 🔑 **Endpoint de Login**

```java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
    if ("ingrid".equals(username) && "1234".equals(password)) {
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok().body("Bearer " + token);
    } else {
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }
}
```

### **Análisis detallado:**

#### **Anotación del método:**
- **`@PostMapping("/login")`**: Maneja peticiones POST a `/api/auth/login`
- **¿Por qué POST?** Porque enviamos credenciales sensibles que no deben ir en la URL

#### **Parámetros:**
- **`@RequestParam String username`**: Recibe el nombre de usuario del formulario/body
- **`@RequestParam String password`**: Recibe la contraseña del formulario/body
- **Formato esperado**: `application/x-www-form-urlencoded` o como parámetros de consulta

#### **Lógica de autenticación:**
```java
if ("ingrid".equals(username) && "1234".equals(password)) {
```
- **⚠️ DEMO SIMPLIFICADO**: Las credenciales están hardcodeadas
- **En producción**: Deberías validar contra una base de datos
- **Seguridad**: Las contraseñas deberían estar hasheadas (bcrypt, etc.)

#### **Generación de token:**
```java
String token = jwtUtil.generateToken(username);
return ResponseEntity.ok().body("Bearer " + token);
```
- **Genera un JWT** con el nombre de usuario como subject
- **Formato de respuesta**: `Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...`
- **Status HTTP**: 200 OK

#### **Manejo de errores:**
```java
return ResponseEntity.status(401).body("Credenciales inválidas");
```
- **Status HTTP**: 401 Unauthorized
- **Mensaje claro**: Informa que las credenciales son incorrectas

---

## 🌐 **Endpoint Público**

```java
@GetMapping("/hello")
public String hello() {
    return "Hello public!";
}
```

### **Análisis:**
- **Ruta**: `/api/auth/hello`
- **Método HTTP**: GET
- **Acceso**: Público (según configuración en SecurityConfig)
- **Propósito**: Endpoint de prueba para verificar que la aplicación funciona
- **Respuesta**: Texto plano simple

---

## 🔄 **Flujo de autenticación completo**

### **1. Usuario solicita login:**
```bash
POST /api/auth/login
Content-Type: application/x-www-form-urlencoded

username=ingrid&password=1234
```

### **2. Controlador valida credenciales:**
- ✅ Si son correctas → Genera JWT
- ❌ Si son incorrectas → Devuelve 401

### **3. Respuesta exitosa:**
```json
HTTP/1.1 200 OK
Content-Type: text/plain

Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpbmdyaWQiLCJpYXQiOjE2...
```

### **4. Cliente guarda el token:**
- **LocalStorage, SessionStorage, o cookie segura**
- **Incluye en futuras peticiones**: `Authorization: Bearer <token>`

---

## 🚀 **Casos de uso prácticos**

### **Login exitoso:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -d "username=ingrid&password=1234"
```
**Respuesta**: `Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...`

### **Login fallido:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -d "username=wrong&password=wrong"
```
**Respuesta**: `Credenciales inválidas` (HTTP 401)

### **Endpoint público:**
```bash
curl -X GET http://localhost:8080/api/auth/hello
```
**Respuesta**: `Hello public!`

---

## 🔧 **Mejoras recomendadas para producción**

### **1. Validación de credenciales contra BD:**
```java
@Autowired
private UserService userService;

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    Optional<User> user = userService.validateCredentials(
        request.getUsername(), 
        request.getPassword()
    );
    
    if (user.isPresent()) {
        String token = jwtUtil.generateToken(user.get().getUsername());
        return ResponseEntity.ok(new LoginResponse(token, user.get()));
    }
    return ResponseEntity.status(401).body("Credenciales inválidas");
}
```

### **2. Usar DTOs en lugar de @RequestParam:**
```java
public class LoginRequest {
    private String username;
    private String password;
    // getters y setters
}

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
    // ...
}
```

### **3. Manejo de excepciones centralizado:**
```java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    try {
        // lógica de autenticación
    } catch (BadCredentialsException e) {
        return ResponseEntity.status(401).body("Credenciales inválidas");
    } catch (UserLockedException e) {
        return ResponseEntity.status(423).body("Usuario bloqueado");
    }
}
```

### **4. Rate limiting:**
```java
@RateLimited(maxAttempts = 5, timeWindow = "1m")
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    // ...
}
```

---

## 📚 **Conceptos clave a recordar**

1. **`@RestController`** = Controlador que devuelve datos directamente
2. **`@PostMapping`** = Maneja peticiones POST
3. **`@RequestParam`** = Extrae parámetros de la petición
4. **`ResponseEntity`** = Control total sobre la respuesta HTTP
5. **JWT Token** = Se genera después de validar credenciales
6. **Status 401** = Unauthorized (credenciales incorrectas)
7. **Bearer Token** = Formato estándar para tokens en headers

---

## 🔒 **Consideraciones de seguridad**

### **Vulnerabilidades actuales (DEMO):**
- ❌ Credenciales hardcodeadas
- ❌ Sin hash de contraseñas
- ❌ Sin rate limiting
- ❌ Sin validación de entrada

### **Mitigaciones recomendadas:**
- ✅ Usar base de datos para usuarios
- ✅ Hash de contraseñas con bcrypt
- ✅ Implementar rate limiting
- ✅ Validar y sanitizar entrada
- ✅ Logs de seguridad
- ✅ HTTPS obligatorio

---

*Documento creado para explicar el controlador de autenticación del proyecto Spring Boot con JWT*

# 🛡️ **JwtFilter.java - Filtro de Validación JWT**

## Introducción
Este filtro es el **guardián de la aplicación**. Intercepta **TODAS las peticiones HTTP** antes de que lleguen a los controladores y valida los tokens JWT. Es la primera línea de defensa del sistema de autenticación.

---

## 🏷️ **Anotaciones y herencia de la clase**

```java
@Component
public class JwtFilter extends OncePerRequestFilter {
```

### Explicación:
- **`@Component`**: Marca esta clase como un bean de Spring para inyección automática
- **`OncePerRequestFilter`**: Clase base que garantiza que el filtro se ejecute **exactamente una vez por petición**
  - Evita ejecuciones múltiples en redirects internos
  - Optimización importante para performance

---

## 🔧 **Inyección de dependencias**

```java
@Autowired
private JwtUtil jwtUtil;
```

### Explicación:
- **Inyecta `JwtUtil`** para validar y extraer información de tokens
- **Necesario para**:
  - Validar si el token es válido
  - Extraer el username del token
  - Verificar que no esté expirado

---

## 🔍 **Método principal del filtro**

```java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                FilterChain filterChain) throws ServletException, IOException {
```

### Explicación:
- **`doFilterInternal`**: Método que se ejecuta para cada petición HTTP
- **Parámetros**:
  - **`HttpServletRequest request`**: Información de la petición (headers, URL, etc.)
  - **`HttpServletResponse response`**: Para modificar la respuesta si es necesario
  - **`FilterChain filterChain`**: Cadena de filtros - permite continuar al siguiente filtro

---

## 🔎 **Extracción del token JWT**

```java
String header = request.getHeader("Authorization");

if (header != null && header.startsWith("Bearer ")) {
    String token = header.substring(7);
    // ...
}
```

### **Análisis paso a paso:**

#### **1. Obtener header Authorization:**
```java
String header = request.getHeader("Authorization");
```
- **Busca el header** `Authorization` en la petición HTTP
- **Formato esperado**: `Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...`
- **Si no existe**: `header` será `null`

#### **2. Validar formato del header:**
```java
if (header != null && header.startsWith("Bearer ")) {
```
- **Verifica que el header existe** (`!= null`)
- **Verifica el formato correcto** (debe empezar con "Bearer ")
- **Estándar RFC 6750**: Formato oficial para tokens Bearer

#### **3. Extraer el token:**
```java
String token = header.substring(7);
```
- **`substring(7)`**: Elimina "Bearer " (7 caracteres) del inicio
- **Resultado**: Solo el token JWT puro
- **Ejemplo**: `eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...`

---

## ✅ **Validación y establecimiento de autenticación**

```java
if (jwtUtil.validateToken(token)) {
    String username = jwtUtil.extractUsername(token);
    UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                    new User(username, "", Collections.emptyList()),
                    null,
                    Collections.emptyList()
            );
    SecurityContextHolder.getContext().setAuthentication(authToken);
}
```

### **Análisis detallado:**

#### **1. Validación del token:**
```java
if (jwtUtil.validateToken(token)) {
```
- **Llama a `JwtUtil.validateToken()`** que verifica:
  - Firma criptográfica correcta
  - Token no expirado
  - Formato válido
- **Si es válido**: Continúa con autenticación
- **Si es inválido**: No establece autenticación (usuario permanece no autenticado)

#### **2. Extracción del username:**
```java
String username = jwtUtil.extractUsername(token);
```
- **Extrae el subject** del token JWT
- **El subject** contiene el nombre de usuario que se autenticó
- **Usado para** identificar quién está haciendo la petición

#### **3. Creación del objeto de autenticación:**
```java
UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(
                new User(username, "", Collections.emptyList()),
                null,
                Collections.emptyList()
        );
```

**Desglose de parámetros:**
- **Principal** (`new User(username, "", Collections.emptyList())`):
  - `username`: Nombre del usuario autenticado
  - `""`: Contraseña vacía (no necesaria en JWT)
  - `Collections.emptyList()`: Autoridades/roles vacías (simplificado para demo)
- **Credentials** (`null`): No necesario en autenticación JWT
- **Authorities** (`Collections.emptyList()`): Permisos/roles del usuario (simplificado)

#### **4. Establecimiento en SecurityContext:**
```java
SecurityContextHolder.getContext().setAuthentication(authToken);
```
- **`SecurityContextHolder`**: Almacena información de seguridad del hilo actual
- **Efecto**: Spring Security reconoce al usuario como autenticado
- **Duración**: Solo durante esta petición (stateless)

---

## ⏭️ **Continuación de la cadena de filtros**

```java
filterChain.doFilter(request, response);
```

### Explicación:
- **SIEMPRE se ejecuta** (con o sin token válido)
- **Pasa el control** al siguiente filtro en la cadena
- **Eventualmente llega** al controlador correspondiente
- **Si no hay autenticación**: Spring Security puede denegar acceso según configuración

---

## 🔄 **Flujo completo del filtro**

### **Escenario 1: Petición con token válido**
```
1. 📨 Petición: GET /api/private/data
   Headers: Authorization: Bearer eyJ0eXAi...

2. 🔍 JwtFilter intercepta:
   ✅ Header existe y empieza con "Bearer "
   ✅ Token extraído correctamente
   ✅ JwtUtil.validateToken() → true
   ✅ Username extraído del token
   ✅ Autenticación establecida en SecurityContext

3. ⏭️ Continúa a siguiente filtro/controlador:
   ✅ Spring Security ve usuario autenticado
   ✅ Permite acceso al endpoint
   ✅ Controlador ejecuta normalmente
```

### **Escenario 2: Petición sin token**
```
1. 📨 Petición: GET /api/private/data
   Headers: (sin Authorization)

2. 🔍 JwtFilter intercepta:
   ❌ Header es null
   ❌ No se establece autenticación
   ⏭️ Continúa sin autenticación

3. ⏭️ Continúa a siguiente filtro:
   ❌ Spring Security ve usuario NO autenticado
   ❌ Ruta protegida → Devuelve 401 Unauthorized
```

### **Escenario 3: Petición con token inválido**
```
1. 📨 Petición: GET /api/private/data
   Headers: Authorization: Bearer token_malformado

2. 🔍 JwtFilter intercepta:
   ✅ Header existe y empieza con "Bearer "
   ✅ Token extraído
   ❌ JwtUtil.validateToken() → false
   ❌ No se establece autenticación
   ⏭️ Continúa sin autenticación

3. ⏭️ Resultado igual al Escenario 2
```

### **Escenario 4: Petición a ruta pública**
```
1. 📨 Petición: POST /api/auth/login
   Headers: (sin Authorization)

2. 🔍 JwtFilter intercepta:
   ❌ No hay token (pero no importa para rutas públicas)
   ⏭️ Continúa sin autenticación

3. ⏭️ Continúa a siguiente filtro:
   ✅ Spring Security ve que /api/auth/** es pública
   ✅ Permite acceso sin autenticación
   ✅ Controlador ejecuta normalmente
```

---

## 🚀 **Casos de uso prácticos**

### **1. Testing con curl:**

#### **Con token válido:**
```bash
curl -X GET http://localhost:8080/api/private/data \
  -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9..."
# ✅ Resultado: Contenido protegido devuelto
```

#### **Sin token:**
```bash
curl -X GET http://localhost:8080/api/private/data
# ❌ Resultado: 401 Unauthorized
```

#### **Token malformado:**
```bash
curl -X GET http://localhost:8080/api/private/data \
  -H "Authorization: Bearer token_invalido"
# ❌ Resultado: 401 Unauthorized
```

#### **Formato incorrecto (sin "Bearer "):**
```bash
curl -X GET http://localhost:8080/api/private/data \
  -H "Authorization: eyJ0eXAi..."
# ❌ Resultado: 401 Unauthorized (filtro no procesa el token)
```

---

## 🔧 **Mejoras para entornos de producción**

### **1. Manejo de roles/autoridades:**
```java
// En lugar de Collections.emptyList(), extraer roles del token
Claims claims = jwtUtil.getClaimsFromToken(token);
List<String> roles = claims.get("roles", List.class);
List<GrantedAuthority> authorities = roles.stream()
    .map(SimpleGrantedAuthority::new)
    .collect(Collectors.toList());

UsernamePasswordAuthenticationToken authToken =
    new UsernamePasswordAuthenticationToken(
        new User(username, "", authorities),
        null,
        authorities
    );
```

### **2. Logging de seguridad:**
```java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                FilterChain filterChain) throws ServletException, IOException {
    
    String clientIP = request.getRemoteAddr();
    String userAgent = request.getHeader("User-Agent");
    
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
        String token = header.substring(7);
        if (jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractUsername(token);
            logger.info("Usuario autenticado: {} desde IP: {}", username, clientIP);
            // establecer autenticación...
        } else {
            logger.warn("Token inválido desde IP: {}", clientIP);
        }
    }
    
    filterChain.doFilter(request, response);
}
```

### **3. Exclusión de rutas específicas:**
```java
@Override
protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    return path.startsWith("/api/public/") || 
           path.startsWith("/api/auth/") ||
           path.equals("/health") ||
           path.equals("/favicon.ico");
}
```

### **4. Manejo de excepciones:**
```java
try {
    if (jwtUtil.validateToken(token)) {
        // establecer autenticación...
    }
} catch (JwtException e) {
    logger.warn("Error validando token: {}", e.getMessage());
    // No establecer autenticación, continuar sin autenticar
} catch (Exception e) {
    logger.error("Error inesperado en filtro JWT", e);
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    return; // No continuar la cadena
}
```

---

## 🔍 **Debugging y troubleshooting**

### **Problemas comunes:**

#### **1. Filtro no se ejecuta:**
- **Causa**: Falta `@Component` en la clase
- **Solución**: Agregar `@Component` y verificar component scan

#### **2. Token no se extrae:**
- **Causa**: Header no empieza con "Bearer "
- **Debug**: `System.out.println("Header: " + header);`
- **Solución**: Verificar formato del header en cliente

#### **3. Autenticación no se establece:**
- **Causa**: `validateToken()` devuelve false
- **Debug**: `System.out.println("Token válido: " + jwtUtil.validateToken(token));`
- **Solución**: Verificar implementación de JwtUtil

#### **4. 401 en rutas públicas:**
- **Causa**: Configuración incorrecta en SecurityConfig
- **Solución**: Verificar que rutas estén en `.permitAll()`

### **Logging útil para debugging:**
```java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                FilterChain filterChain) throws ServletException, IOException {
    
    System.out.println("=== JWT FILTER DEBUG ===");
    System.out.println("URL: " + request.getRequestURL());
    System.out.println("Method: " + request.getMethod());
    
    String header = request.getHeader("Authorization");
    System.out.println("Auth Header: " + header);
    
    if (header != null && header.startsWith("Bearer ")) {
        String token = header.substring(7);
        System.out.println("Token extraído: " + token.substring(0, 20) + "...");
        
        boolean isValid = jwtUtil.validateToken(token);
        System.out.println("Token válido: " + isValid);
        
        if (isValid) {
            String username = jwtUtil.extractUsername(token);
            System.out.println("Username: " + username);
            // establecer autenticación...
        }
    }
    
    filterChain.doFilter(request, response);
}
```

---

## 📚 **Conceptos clave a recordar**

1. **`OncePerRequestFilter`** = Se ejecuta exactamente una vez por petición
2. **`doFilterInternal`** = Método principal que procesa cada petición
3. **Header Authorization** = Debe tener formato "Bearer <token>"
4. **`SecurityContextHolder`** = Almacena la autenticación del usuario actual
5. **`filterChain.doFilter()`** = SIEMPRE debe llamarse para continuar
6. **Stateless** = Autenticación se establece en cada petición
7. **Interceptor universal** = Procesa TODAS las peticiones HTTP

---

## ⚡ **Performance y optimización**

### **Consideraciones importantes:**
- ✅ **Eficiente**: Solo procesa tokens cuando existen
- ✅ **Stateless**: No mantiene estado entre peticiones
- ✅ **Thread-safe**: Cada petición tiene su propio contexto
- 🔧 **Optimización**: Considerar cache de validaciones para tokens frecuentes
- 🔧 **Monitoreo**: Métricas de tiempo de procesamiento del filtro

---

*Documento creado para explicar el filtro JWT del proyecto Spring Boot con JWT*

# 🔑 **JwtUtil.java - Utilidades para manejo de JWT**

## Introducción
Esta clase es el **núcleo de la funcionalidad JWT**. Se encarga de **generar**, **validar** y **extraer información** de los tokens JWT. Es el componente que maneja toda la criptografía y lógica de tokens.

---

## 🏷️ **Anotaciones de la clase**

```java
@Component
public class JwtUtil {
```

### Explicación:
- **`@Component`**: Marca esta clase como un bean de Spring para inyección automática
- **Singleton**: Spring crea una sola instancia que se reutiliza en toda la aplicación
- **Inyectable**: Puede ser inyectada en otros componentes (JwtFilter, AuthController)

---

## 🔐 **Configuración de seguridad**

```java
private final String SECRET_KEY = "mi_clave_secreta_debe_ser_al_menos_256_bits_para_HS256_seguridad";
private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
```

### **Análisis detallado:**

#### **1. Clave secreta:**
```java
private final String SECRET_KEY = "mi_clave_secreta_debe_ser_al_menos_256_bits_para_HS256_seguridad";
```
- **Propósito**: Clave para firmar y verificar tokens JWT
- **Longitud**: Suficientemente larga para cumplir requisitos de HS256 (256 bits mínimo)
- **⚠️ IMPORTANTE**: En producción debe estar en variables de entorno
- **Inmutable**: `final` garantiza que no puede cambiar

#### **2. Clave criptográfica:**
```java
private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
```
- **`Keys.hmacShaKeyFor()`**: Método de JJWT para crear clave HMAC válida
- **Conversión**: Convierte String a SecretKey apropiada para algoritmo HS256
- **Validación**: Automáticamente valida que la clave cumple requisitos mínimos
- **Performance**: Se crea una sola vez al inicializar la clase

---

## 🏗️ **Generación de tokens JWT**

```java
public String generateToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
}
```

### **Análisis paso a paso:**

#### **1. Builder pattern:**
```java
Jwts.builder()
```
- **Jwts.builder()**: Crea un constructor de JWT
- **Fluent API**: Permite encadenar métodos para configurar el token
- **Flexibilidad**: Fácil agregar o quitar claims según necesidades

#### **2. Subject (Usuario):**
```java
.setSubject(username)
```
- **Subject**: Identifica "sobre quién" es el token
- **Uso típico**: Nombre de usuario o ID de usuario
- **Estándar JWT**: Campo `sub` en el payload
- **Extracción**: Se puede recuperar con `extractUsername()`

#### **3. Fecha de emisión:**
```java
.setIssuedAt(new Date())
```
- **Issued At**: Momento exacto cuando se creó el token
- **Uso**: Auditoría, debugging, validaciones adicionales
- **Estándar JWT**: Campo `iat` (issued at) en el payload
- **Formato**: Timestamp Unix (segundos desde 1970)

#### **4. Fecha de expiración:**
```java
.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
```
- **Cálculo**: Tiempo actual + 1 hora (1000ms × 60s × 60min)
- **Seguridad**: Tokens con tiempo limitado reducen riesgo de abuso
- **Estándar JWT**: Campo `exp` (expiration) en el payload
- **Validación**: Automáticamente verificado en `validateToken()`

#### **5. Firma criptográfica:**
```java
.signWith(key, SignatureAlgorithm.HS256)
```
- **Algoritmo**: HMAC SHA-256 (simétrico, misma clave para firmar y verificar)
- **Seguridad**: Garantiza que el token no ha sido modificado
- **Clave**: Usa la `SecretKey` configurada anteriormente
- **Resultado**: Añade la firma al final del token

#### **6. Construcción final:**
```java
.compact()
```
- **Serialización**: Convierte el JWT a string Base64URL
- **Formato**: `header.payload.signature`
- **Ejemplo**: `eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjQw...`

---

## 🔍 **Extracción de información del token**

```java
public String extractUsername(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
}
```

### **Análisis detallado:**

#### **1. Parser builder:**
```java
Jwts.parserBuilder()
```
- **Nuevo API**: Reemplaza el deprecated `Jwts.parser()`
- **Builder pattern**: Configuración fluida del parser
- **Thread-safe**: Seguro para uso concurrente

#### **2. Configuración de clave:**
```java
.setSigningKey(key)
```
- **Clave de verificación**: Misma clave usada para firmar
- **Validación automática**: Verifica que la firma es correcta
- **Seguridad**: Si la clave no coincide, lanza excepción

#### **3. Construcción del parser:**
```java
.build()
```
- **Inmutable**: Crea un parser configurado e inmutable
- **Reutilizable**: Puede usarse múltiples veces
- **Performance**: Optimizado para validación repetida

#### **4. Parsing y validación:**
```java
.parseClaimsJws(token)
```
- **Parsing completo**: Verifica estructura, firma y expiración
- **Validación automática**: 
  - Formato correcto del token
  - Firma válida
  - No expirado
- **Excepción**: Lanza `JwtException` si algo falla

#### **5. Extracción del body:**
```java
.getBody()
```
- **Claims**: Obtiene el payload (cuerpo) del JWT
- **Contiene**: Toda la información del token (sub, iat, exp, claims personalizados)
- **Tipo**: Objeto `Claims` con métodos para acceder a datos

#### **6. Obtención del subject:**
```java
.getSubject()
```
- **Subject**: Extrae el campo `sub` del payload
- **Resultado**: El username que se usó al generar el token
- **Uso**: Identificar al usuario autenticado

---

## ✅ **Validación de tokens**

```java
public boolean validateToken(String token) {
    try {
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
        return true;
    } catch (JwtException | IllegalArgumentException e) {
        return false;
    }
}
```

### **Análisis detallado:**

#### **1. Proceso de validación:**
```java
Jwts.parserBuilder()
    .setSigningKey(key)
    .build()
    .parseClaimsJws(token);
```
- **Validación completa**: Mismo proceso que `extractUsername()`
- **Verificaciones automáticas**:
  - ✅ Formato JWT válido (header.payload.signature)
  - ✅ Firma criptográfica correcta
  - ✅ Token no expirado
  - ✅ Estructura interna válida

#### **2. Manejo de excepciones:**
```java
} catch (JwtException | IllegalArgumentException e) {
    return false;
}
```

**Tipos de excepciones capturadas:**
- **`ExpiredJwtException`**: Token expirado
- **`UnsupportedJwtException`**: Formato no soportado
- **`MalformedJwtException`**: Token malformado
- **`SignatureException`**: Firma inválida
- **`IllegalArgumentException`**: Token null o vacío

#### **3. Resultado boolean:**
- **`true`**: Token completamente válido y usable
- **`false`**: Token inválido por cualquier razón
- **Simplicidad**: Fácil de usar en condiciones if/else

---

## 🔄 **Flujo completo de uso**

### **1. Generación (Login exitoso):**
```java
// En AuthController
String username = "ingrid";
String token = jwtUtil.generateToken(username);
// Resultado: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9..."
```

### **2. Validación (En cada petición):**
```java
// En JwtFilter
String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9...";
boolean isValid = jwtUtil.validateToken(token);
if (isValid) {
    String username = jwtUtil.extractUsername(token);
    // Establecer autenticación para "ingrid"
}
```

---

## 🚀 **Ejemplos prácticos**

### **1. Testing de generación:**
```java
@Test
public void testTokenGeneration() {
    JwtUtil jwtUtil = new JwtUtil();
    String token = jwtUtil.generateToken("testuser");
    
    assertNotNull(token);
    assertTrue(token.contains("."));  // Tiene estructura JWT
    assertTrue(jwtUtil.validateToken(token));
    assertEquals("testuser", jwtUtil.extractUsername(token));
}
```

### **2. Testing de expiración:**
```java
@Test
public void testTokenExpiration() throws InterruptedException {
    // Configurar JwtUtil con expiración de 1 segundo para test
    String token = jwtUtil.generateToken("testuser");
    assertTrue(jwtUtil.validateToken(token));
    
    Thread.sleep(2000);  // Esperar 2 segundos
    assertFalse(jwtUtil.validateToken(token));  // Debería estar expirado
}
```

### **3. Uso en aplicación real:**
```java
// Login
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    if (authService.validate(request.getUsername(), request.getPassword())) {
        String token = jwtUtil.generateToken(request.getUsername());
        return ResponseEntity.ok(new LoginResponse(token));
    }
    return ResponseEntity.status(401).body("Invalid credentials");
}

// Endpoint protegido
@GetMapping("/profile")
public UserProfile getProfile(Authentication auth) {
    String username = auth.getName();  // Viene del token validado
    return userService.getProfile(username);
}
```

---

## 🔧 **Mejoras para producción**

### **1. Configuración externa:**
```java
@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.expiration:3600}")  // 1 hora por defecto
    private int expirationSeconds;
    
    private SecretKey key;
    
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    
    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
}
```

### **2. Claims personalizados:**
```java
public String generateTokenWithRoles(String username, List<String> roles) {
    return Jwts.builder()
        .setSubject(username)
        .claim("roles", roles)
        .claim("type", "access_token")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
        .setIssuer("mi-aplicacion")
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
}

public List<String> extractRoles(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.get("roles", List.class);
}
```

### **3. Refresh tokens:**
```java
public String generateRefreshToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .claim("type", "refresh_token")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationSeconds * 1000))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
}

public boolean isRefreshToken(String token) {
    Claims claims = extractAllClaims(token);
    return "refresh_token".equals(claims.get("type"));
}
```

### **4. Validación avanzada:**
```java
public TokenValidationResult validateTokenAdvanced(String token) {
    try {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
            
        return TokenValidationResult.valid(claims);
    } catch (ExpiredJwtException e) {
        return TokenValidationResult.expired(e.getClaims());
    } catch (JwtException e) {
        return TokenValidationResult.invalid(e.getMessage());
    }
}
```

---

## 🔍 **Debugging y troubleshooting**

### **Problemas comunes:**

#### **1. Token no se genera:**
```java
// Debug: Imprimir token generado
String token = jwtUtil.generateToken("test");
System.out.println("Token generado: " + token);
System.out.println("Longitud: " + token.length());
```

#### **2. Validación siempre falla:**
```java
// Debug: Verificar cada paso
try {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    System.out.println("Token válido. Subject: " + claims.getSubject());
    System.out.println("Expira: " + claims.getExpiration());
} catch (ExpiredJwtException e) {
    System.out.println("Token expirado: " + e.getMessage());
} catch (SignatureException e) {
    System.out.println("Firma inválida: " + e.getMessage());
} catch (JwtException e) {
    System.out.println("Error JWT: " + e.getMessage());
}
```

#### **3. Username no se extrae:**
```java
// Debug: Verificar claims
Claims claims = extractAllClaims(token);
System.out.println("Todos los claims: " + claims);
System.out.println("Subject: " + claims.getSubject());
```

---

## 📚 **Conceptos clave a recordar**

1. **`SecretKey`** = Clave criptográfica para firmar y verificar tokens
2. **Subject** = Campo que identifica al usuario en el token
3. **Expiration** = Tiempo límite de validez del token
4. **Claims** = Información contenida en el payload del JWT
5. **Signing** = Proceso de añadir firma criptográfica
6. **Parsing** = Proceso de verificar y extraer información del token
7. **HMAC-SHA256** = Algoritmo de firma simétrica usado

---

## ⚡ **Performance y seguridad**

### **Buenas prácticas implementadas:**
- ✅ **Clave fuerte**: Cumple requisitos mínimos de HS256
- ✅ **Expiración**: Tokens limitados en tiempo (1 hora)
- ✅ **Validación completa**: Firma, formato y expiración
- ✅ **Thread-safe**: Inmutable y seguro para concurrencia

### **Mejoras recomendadas:**
- 🔧 **Variables de entorno**: Clave secreta desde configuración externa
- 🔧 **Algoritmos modernos**: Considerar RS256 para mayor seguridad
- 🔧 **Refresh tokens**: Implementar renovación automática
- 🔧 **Blacklist**: Lista de tokens revocados
- 🔧 **Auditoría**: Logging de generación y validación de tokens

---

*Documento creado para explicar las utilidades JWT del proyecto Spring Boot con JWT*

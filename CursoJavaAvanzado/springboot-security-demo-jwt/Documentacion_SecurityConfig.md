# 📋 **SecurityConfig.java - Configuración de Seguridad**

## Introducción
Este archivo es el **corazón de la configuración de seguridad** de tu aplicación Spring Boot. Define cómo Spring Security debe manejar la autenticación y autorización de las peticiones HTTP.

---

## 🏷️ **Anotaciones de la clase**

```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
```

### Explicación:
- **`@Configuration`**: Le dice a Spring que esta clase contiene configuraciones que deben ser procesadas al iniciar la aplicación
- **`@EnableMethodSecurity`**: Habilita la seguridad a nivel de métodos, permitiendo usar anotaciones como:
  - `@PreAuthorize` - Verificar permisos antes de ejecutar un método
  - `@PostAuthorize` - Verificar permisos después de ejecutar un método
  - `@Secured` - Especificar roles requeridos

---

## 🔧 **Inyección de dependencias**

```java
private final JwtFilter jwtFilter;

public SecurityConfig(JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
}
```

### Explicación:
- **Inyecta el `JwtFilter` personalizado** que interceptará las peticiones para validar tokens JWT
- **Usa inyección por constructor** (recomendada por Spring por ser inmutable y thread-safe)
- El `JwtFilter` será el encargado de validar los tokens JWT en cada petición

---

## 🛡️ **Configuración principal de seguridad**

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
```

Este método configura cómo Spring Security debe manejar las peticiones HTTP. Es el **núcleo de la configuración de seguridad**.

### **1. Deshabilitación de CSRF**
```java
http.csrf(csrf -> csrf.disable())
```

**¿Qué es CSRF?**
- **CSRF** (Cross-Site Request Forgery) es un ataque donde un sitio malicioso hace peticiones en nombre del usuario
- **¿Por qué lo deshabilitamos?** Porque usamos JWT (tokens stateless)
- **Con JWT**: Cada petición es autenticada independientemente, no necesitamos protección CSRF
- **Cuándo SÍ usar CSRF**: En aplicaciones web tradicionales con sesiones y formularios

### **2. Configuración de autorización**
```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/public/**", "/api/auth/**").permitAll()
    .anyRequest().authenticated())
```

**Explicación detallada:**
- **`/api/public/**`**: 
  - Rutas públicas (no requieren autenticación)
  - Ejemplo: `/api/public/health`, `/api/public/info`
- **`/api/auth/**`**: 
  - Rutas de autenticación (login, registro)
  - Ejemplo: `/api/auth/login`, `/api/auth/register`
- **`anyRequest().authenticated()`**: 
  - Todas las demás rutas requieren autenticación
  - Si no tienes token válido → 401 Unauthorized

### **3. Gestión de sesiones**
```java
.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
```

**¿Qué significa STATELESS?**
- **No mantiene sesiones en el servidor**
- **Cada petición debe incluir el token JWT** para autenticarse
- **Ventajas**:
  - Escalabilidad: No consume memoria del servidor
  - Distribución: Funciona en múltiples servidores
  - Performance: No hay consultas a sesiones
- **Ideal para APIs REST**

### **4. Filtro JWT personalizado**
```java
http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
```

**¿Qué hace?**
- **Añade tu `JwtFilter` ANTES** del filtro estándar de autenticación
- **Intercepta TODAS las peticiones** para validar tokens JWT
- **Si el token es válido**: Establece la autenticación en el contexto de seguridad
- **Si el token es inválido o no existe**: Continúa con el flujo normal (puede ser ruta pública)

---

## 🔑 **AuthenticationManager Bean**

```java
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
}
```

### Explicación:
- **Proporciona el `AuthenticationManager`** como bean para ser usado en otros componentes
- **Necesario para el proceso de autenticación** (login)
- **Se usa en el controlador de autenticación** para validar credenciales de usuario
- **Spring Boot lo configura automáticamente**, nosotros solo lo exponemos como bean

---

## 🔄 **Flujo completo de funcionamiento**

### Cuando llega una petición HTTP:

1. **📨 Petición HTTP llega** al servidor
   
2. **🔍 JwtFilter intercepta** la petición
   - Busca el header `Authorization: Bearer <token>`
   - Si encuentra token → lo valida
   - Si es válido → establece autenticación en SecurityContext
   
3. **🛡️ Spring Security evalúa** la configuración:
   - ¿Es ruta pública (`/api/public/**`, `/api/auth/**`)? → ✅ Permitir
   - ¿Está autenticado el usuario? → ✅ Permitir
   - ¿No está autenticado y ruta protegida? → ❌ 401 Unauthorized

4. **🎯 Controlador procesa** la petición (si fue autorizada)

---

## 💡 **¿Por qué esta configuración específica?**

### **Arquitectura Stateless (Sin estado)**
- **Escalable**: Cada servidor puede manejar cualquier petición
- **Distribuible**: Funciona con múltiples instancias
- **Cacheable**: Los tokens pueden ser cacheados

### **JWT (JSON Web Tokens)**
- **Autocontenidos**: Toda la información está en el token
- **No requiere BD**: No necesitas consultar la base de datos para cada petición
- **Seguro**: Firmado criptográficamente

### **Filtros personalizados**
- **Control total**: Defines exactamente cómo validar la autenticación
- **Flexibilidad**: Puedes agregar lógica personalizada
- **Performance**: Optimizado para tu caso de uso específico

### **Rutas públicas bien definidas**
- **`/api/auth/**`**: Para login, registro, recuperación de contraseña
- **`/api/public/**`**: Para recursos que no requieren autenticación
- **Resto protegido**: Máxima seguridad por defecto

---

## 🚀 **Casos de uso prácticos**

### **Rutas que NO requieren autenticación:**
- `GET /api/public/health` - Estado de la aplicación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registrar usuario
- `GET /api/public/documentation` - Documentación de la API

### **Rutas que SÍ requieren autenticación:**
- `GET /api/users/profile` - Perfil del usuario
- `POST /api/orders` - Crear una orden
- `PUT /api/users/update` - Actualizar datos
- `DELETE /api/admin/users` - Operaciones administrativas

---

## 🔧 **Personalización adicional posible**

Si quisieras agregar más configuraciones, podrías:

```java
// Configurar CORS
.cors(cors -> cors.configurationSource(corsConfigurationSource()))

// Manejar excepciones de autenticación
.exceptionHandling(ex -> ex
    .authenticationEntryPoint(authenticationEntryPoint)
    .accessDeniedHandler(accessDeniedHandler))

// Configurar múltiples roles
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/admin/**").hasRole("ADMIN")
    .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
    .anyRequest().authenticated())
```

---

## 📚 **Conceptos clave a recordar**

1. **`@Configuration`** = Esta clase configura Spring
2. **`SecurityFilterChain`** = Define las reglas de seguridad
3. **STATELESS** = Sin sesiones en servidor
4. **JWT Filter** = Valida tokens en cada petición
5. **CSRF deshabilitado** = Apropiado para APIs REST con JWT
6. **Rutas públicas** = No requieren autenticación
7. **AuthenticationManager** = Valida credenciales de login

---

*Documento creado para explicar la configuración de seguridad del proyecto Spring Boot con JWT*

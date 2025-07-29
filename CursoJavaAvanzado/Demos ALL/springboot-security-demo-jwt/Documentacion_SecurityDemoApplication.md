# 🚀 **SecurityDemoApplication.java - Clase Principal de la Aplicación**

## Introducción
Esta es la **clase principal** que inicia toda la aplicación Spring Boot. Es el punto de entrada que configura y ejecuta el servidor web con todas las funcionalidades de seguridad JWT implementadas.

---

## 🏷️ **Anotaciones de la clase**

```java
@SpringBootApplication
public class SecurityDemoApplication {
```

### Explicación:
**`@SpringBootApplication`** es una anotación compuesta que incluye:

#### **1. `@Configuration`**
- Marca esta clase como una **fuente de configuración de beans**
- Permite definir métodos `@Bean` si fuera necesario
- Spring la procesa durante el inicio de la aplicación

#### **2. `@EnableAutoConfiguration`**
- **Configuración automática** basada en las dependencias del classpath
- **Detecta automáticamente**:
  - Spring Web (por `spring-boot-starter-web`)
  - Spring Security (por `spring-boot-starter-security`)
  - JWT libraries (por `jjwt-*` dependencies)
- **Configura automáticamente**:
  - Servidor web embebido (Tomcat)
  - Security filter chain básico
  - JSON serialization/deserialization
  - Error handling

#### **3. `@ComponentScan`**
- **Escanea automáticamente** el paquete actual y subpaquetes
- **Busca y registra** todos los componentes anotados:
  - `@Component` (como `JwtUtil`, `JwtFilter`)
  - `@Service`
  - `@Repository`
  - `@Controller` / `@RestController` (como `AuthController`, `SecureController`)
  - `@Configuration` (como `SecurityConfig`)

---

## 🎯 **Método principal**

```java
public static void main(String[] args) {
    SpringApplication.run(SecurityDemoApplication.class, args);
}
```

### **Análisis detallado:**

#### **1. Método estático:**
```java
public static void main(String[] args)
```
- **Punto de entrada estándar** de cualquier aplicación Java
- **Ejecutado por la JVM** cuando se inicia la aplicación
- **Parámetros**: `args` contiene argumentos de línea de comandos

#### **2. SpringApplication.run():**
```java
SpringApplication.run(SecurityDemoApplication.class, args)
```
- **Inicia el contexto de Spring Boot** completo
- **Parámetros**:
  - `SecurityDemoApplication.class`: Clase principal de configuración
  - `args`: Argumentos de línea de comandos (puertos, profiles, etc.)

---

## 🔄 **Proceso de inicio de la aplicación**

### **1. Carga de configuración:**
```
📂 Escaneo de componentes:
├── SecurityDemoApplication (clase principal)
├── config/
│   └── SecurityConfig (@Configuration)
├── controller/
│   ├── AuthController (@RestController)
│   └── SecureController (@RestController)
└── security/
    ├── JwtFilter (@Component)
    └── JwtUtil (@Component)
```

### **2. Autoconfiguration magic:**
```
🔧 Spring Boot detecta:
├── spring-boot-starter-web → Configura servidor web
├── spring-boot-starter-security → Configura Spring Security
├── jjwt-* dependencies → Disponibles para JWT
└── @EnableMethodSecurity → Activa seguridad de métodos
```

### **3. Inicialización de beans:**
```
🏗️ Orden de creación:
1. JwtUtil (@Component)
2. JwtFilter (@Component) - inyecta JwtUtil
3. SecurityConfig (@Configuration) - inyecta JwtFilter
4. AuthController (@RestController) - inyecta JwtUtil
5. SecureController (@RestController)
```

### **4. Configuración de seguridad:**
```
🛡️ Spring Security configura:
├── SecurityFilterChain (desde SecurityConfig)
├── JwtFilter añadido a la cadena de filtros
├── Rutas públicas: /api/auth/**, /api/public/**
└── Rutas protegidas: cualquier otra ruta
```

### **5. Inicio del servidor:**
```
🌐 Tomcat embedded inicia:
├── Puerto: 8080 (por defecto)
├── Context path: / (raíz)
├── Endpoints disponibles:
│   ├── POST /api/auth/login (público)
│   ├── GET /api/auth/hello (público)
│   └── GET /api/private/data (protegido)
└── Filtros de seguridad activos
```

---

## 📝 **Logs típicos de inicio**

Cuando ejecutas la aplicación, verás logs similares a:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.5)

2025-07-24 09:00:00.000  INFO --- [main] c.e.s.SecurityDemoApplication: Starting SecurityDemoApplication
2025-07-24 09:00:01.000  INFO --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer: Tomcat initialized with port(s): 8080 (http)
2025-07-24 09:00:02.000  INFO --- [main] o.s.security.web.DefaultSecurityFilterChain: Will secure any request with [
    org.springframework.security.web.session.DisableEncodeUrlFilter@123,
    org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter@456,
    org.springframework.security.web.context.SecurityContextPersistenceFilter@789,
    org.springframework.security.web.header.HeaderWriterFilter@abc,
    org.springframework.security.web.csrf.CsrfFilter@def,
    org.springframework.security.web.authentication.logout.LogoutFilter@ghi,
    com.example.securitydemo.security.JwtFilter@jkl,  ← Tu filtro personalizado
    org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@mno,
    ...
]
2025-07-24 09:00:03.000  INFO --- [main] o.s.b.w.embedded.tomcat.TomcatWebServer: Tomcat started on port(s): 8080 (http)
2025-07-24 09:00:03.000  INFO --- [main] c.e.s.SecurityDemoApplication: Started SecurityDemoApplication in 3.456 seconds
```

---

## 🚀 **Formas de ejecutar la aplicación**

### **1. Desde IDE (IntelliJ, Eclipse, VS Code):**
- Click derecho en `SecurityDemoApplication.java`
- "Run SecurityDemoApplication"
- ✅ **Ventaja**: Debug fácil, hot reload

### **2. Desde línea de comandos con Maven:**
```bash
# Compilar y ejecutar
mvn spring-boot:run

# O compilar JAR y ejecutar
mvn clean package
java -jar target/securitydemo-0.0.1-SNAPSHOT.jar
```

### **3. Con parámetros personalizados:**
```bash
# Cambiar puerto
java -jar app.jar --server.port=9090

# Activar profile específico
java -jar app.jar --spring.profiles.active=production

# Múltiples parámetros
java -jar app.jar --server.port=9090 --jwt.expiration=7200
```

### **4. Como aplicación Docker:**
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/securitydemo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

---

## 🔧 **Personalización avanzada**

### **1. Configuración de banner personalizado:**
```java
@SpringBootApplication
public class SecurityDemoApplication {
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SecurityDemoApplication.class);
        app.setBannerMode(Banner.Mode.OFF);  // Deshabilitar banner
        app.run(args);
    }
}
```

### **2. Configuración de startup listeners:**
```java
@SpringBootApplication
public class SecurityDemoApplication {
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SecurityDemoApplication.class);
        app.addListeners(new ApplicationStartupListener());
        app.run(args);
    }
    
    @EventListener
    public void handleApplicationReady(ApplicationReadyEvent event) {
        System.out.println("🚀 Aplicación lista para recibir peticiones!");
        System.out.println("📝 Documentación: http://localhost:8080/swagger-ui.html");
        System.out.println("🔐 Login: POST http://localhost:8080/api/auth/login");
    }
}
```

### **3. Configuración de properties programática:**
```java
@SpringBootApplication
public class SecurityDemoApplication {
    
    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        System.setProperty("spring.application.name", "JWT Security Demo");
        SpringApplication.run(SecurityDemoApplication.class, args);
    }
}
```

---

## 📋 **Configuración adicional típica**

### **1. Bean de configuración personalizada:**
```java
@SpringBootApplication
public class SecurityDemoApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

### **2. Configuración de base de datos:**
```java
@SpringBootApplication
public class SecurityDemoApplication implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Crear usuario admin por defecto
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User("admin", passwordEncoder.encode("admin123"));
            userRepository.save(admin);
            System.out.println("👤 Usuario admin creado");
        }
    }
}
```

---

## 🌐 **Testing de la aplicación iniciada**

### **1. Verificar que está funcionando:**
```bash
# Health check básico (si tienes actuator)
curl http://localhost:8080/actuator/health

# O probar endpoint público
curl http://localhost:8080/api/auth/hello
# Respuesta: "Hello public!"
```

### **2. Test completo de funcionalidad:**
```bash
# 1. Login
curl -X POST http://localhost:8080/api/auth/login \
  -d "username=ingrid&password=1234"
# Respuesta: Bearer eyJ0eXAi...

# 2. Usar token para acceder a contenido protegido
curl -X GET http://localhost:8080/api/private/data \
  -H "Authorization: Bearer eyJ0eXAi..."
# Respuesta: Este contenido está protegido con JWT 🔒
```

---

## 🔍 **Troubleshooting de inicio**

### **Problemas comunes:**

#### **1. Puerto ocupado:**
```
***************************
APPLICATION FAILED TO START
***************************
Description:
Web server failed to start. Port 8080 was already in use.
```
**Solución:**
```bash
# Cambiar puerto
java -jar app.jar --server.port=8081
# O matar proceso que usa el puerto
lsof -ti :8080 | xargs kill -9
```

#### **2. Beans no encontrados:**
```
No qualifying bean of type 'JwtUtil' available
```
**Solución:** Verificar que `@ComponentScan` incluye el paquete correcto

#### **3. Configuración de seguridad conflictiva:**
```
A circular dependency has been detected
```
**Solución:** Revisar dependencias circulares en configuración

---

## 📚 **Conceptos clave a recordar**

1. **`@SpringBootApplication`** = Configuración + ComponentScan + AutoConfiguration
2. **`main()` method** = Punto de entrada estándar Java
3. **`SpringApplication.run()`** = Inicia contexto completo de Spring
4. **Auto-configuration** = Spring Boot configura automáticamente según dependencias
5. **Component scanning** = Busca y registra todos los @Component/@Service/@Controller
6. **Embedded server** = Servidor web (Tomcat) incluido en la aplicación
7. **Application startup** = Proceso de inicialización completo del framework

---

## ⚡ **Performance y monitoreo**

### **Métricas de inicio típicas:**
- **Tiempo de inicio**: 2-5 segundos (desarrollo)
- **Memoria inicial**: ~100-200 MB
- **Beans creados**: ~200-300 (según dependencias)
- **Endpoints disponibles**: Los definidos en controladores

### **Optimizaciones para producción:**
```java
@SpringBootApplication(exclude = {
    DevToolsAutoConfiguration.class,  // Remover devtools
    H2ConsoleAutoConfiguration.class  // Remover consola H2 si no se usa
})
public class SecurityDemoApplication {
    
    public static void main(String[] args) {
        // Optimizaciones JVM para producción
        System.setProperty("spring.jmx.enabled", "false");
        System.setProperty("spring.main.lazy-initialization", "true");
        SpringApplication.run(SecurityDemoApplication.class, args);
    }
}
```

---

*Documento creado para explicar la clase principal de la aplicación Spring Boot con JWT*

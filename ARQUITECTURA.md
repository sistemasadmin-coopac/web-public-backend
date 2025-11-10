# 🏗️ Arquitectura - CAC El Salvador Backend

## Visión General

Arquitectura en capas con Spring Boot 3.5.7 siguiendo patrones REST y microservicios.

```
┌─────────────────────────────────────┐
│  Cliente (Frontend / Mobile)        │
└──────────────┬──────────────────────┘
               │
       ┌───────▼────────┐
       │  REST API      │
       │  Controllers   │
       └───────┬────────┘
               │
       ┌───────▼────────┐
       │  Services      │
       │  (Lógica)      │
       └───────┬────────┘
               │
       ┌───────▼────────┐
       │  Repositories  │
       │  (JPA)         │
       └───────┬────────┘
               │
┌──────────────┼──────────────┐
│              │              │
▼              ▼              ▼
PostgreSQL   GCS Bucket    Cache
```

## Componentes Principales

### 1. Controllers (`controller/`)
- Reciben solicitudes HTTP
- Validan entrada
- Llaman a servicios
- Retornan respuestas JSON

```java
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
```

### 2. Services (`service/`)
- Lógica de negocio
- Validaciones
- Transacciones
- Orquestación

```java
@Service
@Transactional
public class ProductService {
    public ProductDTO getById(Long id) {
        return repository.findById(id)
            .map(mapper::toDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}
```

### 3. Repositories (`repository/`)
- Acceso a datos JPA
- Queries personalizadas
- Proyecciones

```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
    Optional<Product> findByCode(String code);
}
```

### 4. Entities (`entity/`)
- Modelos JPA
- Mapeados a tablas BD
- Anotaciones de validación

```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
}
```

### 5. DTOs (`dto/`)
- Data Transfer Objects
- Separación de Entity y Response
- Validación con Bean Validation

```java
@Data
public class ProductDTO {
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotNull
    @Positive
    private BigDecimal price;
}
```

## Patrones de Diseño

### MVC Pattern
```
Request → Controller → Service → Repository → Database
```

### Inyección de Dependencias
```java
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
}
```

### Validación de Datos
```java
@PostMapping
public ResponseEntity<ProductDTO> create(
    @Valid @RequestBody CreateProductDTO dto) {
    // Bean Validation automático
}
```

## Base de Datos

### Configuración PostgreSQL

**Conexión**
```yaml
# application.yml
datasource:
  url: jdbc:postgresql://localhost:5432/cac-elsalvador-web
  username: ${DB_USERNAME}
  password: ${DB_PASSWORD}
  driver-class-name: org.postgresql.Driver
```

**Connection Pool (HikariCP)**
```yaml
hikari:
  maximum-pool-size: 3        # Máximo de conexiones
  minimum-idle: 0              # Conexiones ociosas mínimas
  connection-timeout: 30000    # Timeout de conexión (ms)
  idle-timeout: 600000         # Timeout de inactividad (ms)
  max-lifetime: 1800000        # Lifetime máximo de conexión (ms)
```

### Hibernate Configuration

```yaml
jpa:
  hibernate:
    ddl-auto: validate         # No crea/modifica tablas automáticamente
  show-sql: false              # No muestra SQL en logs
  properties:
    hibernate:
      dialect: PostgreSQLDialect
      format_sql: true
```

**Modos disponibles:**
- `validate` (Producción): Solo valida esquema
- `update` (Development): Crea tablas/columnas faltantes
- `create`: Crea tablas cada vez
- `create-drop`: Crea y elimina al finalizar

### Esquema de Base de Datos

Entidades principales mapeadas con JPA:

| Entidad | Tabla | Propósito |
|---------|-------|----------|
| Product | products | Productos y servicios |
| User | users | Usuarios del sistema |
| Role | roles | Roles de acceso |
| FileUpload | file_uploads | Archivos subidos |
| FinancialReport | financial_reports | Reportes financieros |
| Promotion | promotions | Promociones activas |

### Características de Conexión

**Validación de salud**
```yaml
management:
  health:
    probes:
      enabled: true          # Health checks habilitados
  endpoints:
    web:
      exposure:
        include: health,readiness,liveness
```

Endpoints disponibles:
- `/actuator/health` - Health general
- `/actuator/health/readiness` - Readiness (BD disponible)
- `/actuator/health/liveness` - Liveness (aplicación viva)

**Timezone**
```yaml
jackson:
  time-zone: America/Lima    # Zona horaria de la aplicación
```

### Migraciones

- **Ubicación:** `src/main/resources/db/migration/`
- **Estado actual:** Sin migraciones automáticas configuradas
- **Modo:** Manual o mediante herramientas externas
- **DDL:** Gestionado por Hibernate en modo `validate`

### Optimizaciones

**Init Spring más rápido**
```yaml
spring:
  main:
    lazy-initialization: true  # Inicializa beans bajo demanda
```

**Compresión de respuestas**
```yaml
server:
  compression:
    enabled: true              # Comprime respuestas HTTP
```

### Acceso a Datos (JPA)

```java
// Interface base para repositorios
@Repository
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
}

// Ejemplo: ProductRepository
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();
    Optional<Product> findByCode(String code);
}
```

### Variables de Entorno (Producción)

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `DB_USERNAME` | Usuario PostgreSQL | postgres |
| `DB_PASSWORD` | Contraseña BD | *** |
| `CLOUD_SQL_INSTANCE` | Cloud SQL Instance | `project:region:instance` |
| `GOOGLE_CLIENT_ID` | OAuth2 Client ID | xxx.apps.googleusercontent.com |
| `GOOGLE_CLIENT_SECRET` | OAuth2 Secret | *** |

Se configuran en Google Secret Manager y se inyectan en Cloud Run.

## Control de Versiones y Repositorio

### Bitbucket

**Repositorio principal**
```
URL: https://bitbucket.org/[workspace]/cac-elsalvador-backend
Tipo: Git
Acceso: SSH/HTTPS
```

### Ramas

```
main (Producción)
├─ stable
│  └─ releases/*
└─ develop (Desarrollo)
   ├─ feature/*
   ├─ bugfix/*
   └─ hotfix/*
```

**Convenciones de rama:**
- `main` - Código en producción (protegida)
- `develop` - Rama de integración de desarrollo
- `feature/nombre-feature` - Nuevas funcionalidades
- `bugfix/descripcion` - Corrección de bugs
- `hotfix/descripcion` - Correcciones críticas en producción
- `release/vX.Y.Z` - Preparación de releases

### Flujo de Trabajo (Git Flow)

```
┌─────────────────────────────────────────────┐
│ Rama feature/nueva-funcionalidad            │
└────────────┬────────────────────────────────┘
             │
             ├─ Push a Bitbucket
             ├─ Create Pull Request
             ├─ Code Review
             │
             ▼
┌─────────────────────────────────────────────┐
│ Merge a develop                             │
│ (CI/CD: build, test, deploy a dev)         │
└────────────┬────────────────────────────────┘
             │
             ├─ Cuando está listo para producción
             │
             ▼
┌─────────────────────────────────────────────┐
│ Pull Request develop → main                 │
│ (Code Review + Aprobación)                  │
└────────────┬────────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────────┐
│ Merge a main (Producción)                   │
│ (CI/CD: build, test, deploy a Cloud Run)   │
│ (Tag automático: vX.Y.Z)                    │
└─────────────────────────────────────────────┘
```

### Protecciones de Rama

| Rama | Protecciones | Requisitos |
|------|--------------|-----------|
| `main` | ✅ | 1 revisor, tests OK |
| `develop` | ✅ | Tests OK |
| Feature branches | ❌ | Ninguno |

### Pull Request (PR)

**Estructura de PR:**
```markdown
## Descripción
Breve descripción de los cambios

## Tipo de cambio
- [ ] Bug fix (corrección sin breaking changes)
- [ ] Feature (nueva funcionalidad)
- [ ] Breaking change (cambio que afecta API)

## Cambios realizados
- Cambio 1
- Cambio 2

## Testing realizado
- [ ] Tests unitarios
- [ ] Tests de integración
- [ ] Manual testing

## Checklist
- [ ] Código sigue convenciones del proyecto
- [ ] Documentación actualizada
- [ ] No hay warnings
- [ ] Build exitoso
```

### Commits

**Convención Conventional Commits:**
```
<tipo>(<scope>): <subject>

<body>

<footer>
```

**Tipos:**
- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `docs`: Cambios en documentación
- `style`: Cambios de formato (sin lógica)
- `refactor`: Refactorización de código
- `perf`: Mejoras de performance
- `test`: Agregación/actualización de tests
- `chore`: Cambios en herramientas/config

**Ejemplos:**
```
feat(products): agregar endpoint para obtener productos por categoría
fix(auth): corregir validación de token expirado
docs(readme): actualizar instrucciones de instalación
chore(deps): actualizar Spring Boot a 3.5.7
```

### Integración Continua

Bitbucket Pipelines (CI/CD):
```yaml
# bitbucket-pipelines.yml
image: gradle:latest

pipelines:
  branches:
    develop:
      - step:
          name: Build y Test
          script:
            - ./gradlew clean build
            - ./gradlew test
          after-script:
            - bash <(curl -s https://codecov.io/bash)
    
    main:
      - step:
          name: Build
          script:
            - ./gradlew clean build
      - step:
          name: Deploy a Producción
          script:
            - echo "Deploying to Cloud Run..."
            - gcloud run deploy web-public-backend-prod ...
```

### Webhook y Notificaciones

**Eventos de Bitbucket configurados:**
- Push a rama
- Pull Request creado/actualizado
- Pull Request aprobado/rechazado
- Merge completado

Se integran con:
- Slack (notificaciones
- Email (para equipos)
- GitHub Actions (si está conectado)

### Versionamiento

**Tags automáticos en main:**
```
v1.0.0  - Release production
v1.0.1  - Patch
v1.1.0  - Minor
v2.0.0  - Major
```

Se generan con `git tag -a vX.Y.Z -m "Release vX.Y.Z"`

### Acceso y Permisos

| Rol | Permisos |
|-----|----------|
| Developer | Ver, crear PR en develop |
| Lead Developer | Merge en develop, crear release |
| DevOps | Merge a main, trigger deploy |
| Admin | Todo |

---

## Seguridad

### Autenticación OAuth2
```java
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
            .oauth2Login()
            .and()
            .authorizeRequests()
            .requestMatchers("/api/v1/public/**").permitAll()
            .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated();
        return http.build();
    }
}
```

### Rutas Públicas vs Protegidas
```
Públicas:  GET  /api/v1/products
           GET  /api/v1/promotions

Admin:     POST   /api/v1/admin/products
           PUT    /api/v1/admin/products/{id}
           DELETE /api/v1/admin/products/{id}
```

## Flujo de una Solicitud

### GET /api/v1/products/1

```
1. ProductController.getProduct(1)
   ├─ Valida ID
   ├─ Autenticación (JWT)
   └─ Llama ProductService

2. ProductService.getById(1)
   ├─ Validaciones de negocio
   └─ ProductRepository.findById(1)

3. Repository → JPA → SQL
   └─ SELECT * FROM products WHERE id = 1

4. Entity → DTO conversion

5. Response 200 OK
   {
     "id": 1,
     "name": "Producto",
     "price": 100.00
   }
```

## Configuración por Entorno

### Development (application.yml)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cac_dev
  jpa:
    hibernate:
      ddl-auto: update

logging:
  level:
    com.elsalvador.coopac: DEBUG
```

### Production (application-prod.yml)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://${DB_HOST}:5432/${DB_NAME}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    hikari:
      maximum-pool-size: 20
  jpa:
    hibernate:
      ddl-auto: validate

logging:
  level:
    com.elsalvador.coopac: INFO
```

## Health Check

```bash
curl http://localhost:8080/api/v1/health
```

Response:
```json
{
  "status": "UP",
  "timestamp": "2024-01-10T15:30:00Z"
}
```

## Dependencias Clave

| Dependencia | Versión | Propósito |
|-------------|---------|----------|
| spring-boot-starter-web | 3.5.7 | REST API |
| spring-boot-starter-data-jpa | 3.5.7 | BD Access |
| spring-boot-starter-security | 3.5.7 | Security |
| spring-boot-starter-oauth2-client | 3.5.7 | OAuth2 |
| spring-cloud-dependencies | 2025.0.0 | Cloud |
| springdoc-openapi | 2.8.14 | Swagger |
| google-cloud-storage | 2.58.1 | GCS |
| google-cloud-sql-postgres | 1.25.3 | Cloud SQL |
| postgresql | Latest | DB Driver |
| lombok | Latest | Annotations |



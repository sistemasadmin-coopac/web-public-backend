# CAC El Salvador Backend

Backend REST API para la plataforma web de CAC El Salvador.

## 🛠️ Stack Tecnológico

- Java 21
- Spring Boot 3.5.7
- PostgreSQL
- Google Cloud (Cloud Run, Cloud SQL, Cloud Storage)
- OAuth2 Security
- OpenAPI/Swagger

## 🚀 Quick Start

```bash
# Build
./gradlew clean build

# Ejecutar
./gradlew bootRun

# API: http://localhost:8080/swagger-ui.html
```

## 📚 Endpoints

Todos los endpoints están documentados con **Swagger/OpenAPI**:

- **Local**: http://localhost:8080/swagger-ui.html
- **Producción**: https://web-public-backend-prod-xxxxx.run.app/swagger-ui.html

**Endpoints principales:**
- `GET /api/v1/products` - Listar productos
- `GET /api/v1/products/{id}` - Obtener producto
- `POST /api/v1/admin/products` - Crear producto (requiere autenticación)
- `GET /api/v1/promotions` - Listar promociones
- `GET /api/v1/financial-reports` - Listar reportes financieros
- `GET /api/v1/health` - Health check

Ver documentación completa en Swagger UI.

## 📁 Documentación

- **[ARQUITECTURA.md](./ARQUITECTURA.md)** - Arquitectura, componentes y patrones
- **[DESPLIEGUE.md](./DESPLIEGUE.md)** - Despliegue a producción en Google Cloud Run

## 📝 Licencia

Todos los derechos reservados.


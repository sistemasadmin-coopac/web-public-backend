# API Endpoints - About History Admin

Documentación de los endpoints de administración para gestionar la sección de historia y eventos del timeline.

**Base URL**: `/api/admin/about/history`

---

## 1. Crear Evento del Timeline

Crea un nuevo evento en el timeline de historia.

**Endpoint**: `POST /api/admin/about/history/timeline`

**Headers**:
```
Content-Type: application/json
```

### Request Body

```json
{
  "yearLabel": "2024",
  "title": "Expansión Regional",
  "description": "Apertura de nuevas sucursales en la región oriental del país",
  "displayOrder": 10,
  "isActive": true
}
```

### Campos

| Campo | Tipo | Requerido | Descripción |
|-------|------|-----------|-------------|
| yearLabel | String | Sí | Etiqueta del año (ej: "2024", "2020-2021") |
| title | String | Sí | Título del evento |
| description | String | No | Descripción detallada del evento |
| displayOrder | Integer | No | Orden de visualización (se asigna automáticamente si se omite) |
| isActive | Boolean | No | Estado activo/inactivo (default: true) |

### Response

**Status**: `201 Created`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "yearLabel": "2024",
  "title": "Expansión Regional",
  "description": "Apertura de nuevas sucursales en la región oriental del país",
  "displayOrder": 10,
  "isActive": true
}
```

### Ejemplo cURL

```bash
curl -X POST http://localhost:8080/api/admin/about/history/timeline ^
  -H "Content-Type: application/json" ^
  -d "{\"yearLabel\":\"2024\",\"title\":\"Expansión Regional\",\"description\":\"Apertura de nuevas sucursales en la región oriental del país\",\"displayOrder\":10,\"isActive\":true}"
```

---

## 2. Actualizar Evento del Timeline

Actualiza un evento existente del timeline.

**Endpoint**: `PUT /api/admin/about/history/timeline/{id}`

**Headers**:
```
Content-Type: application/json
```

### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | UUID | ID del evento del timeline |

### Request Body

```json
{
  "yearLabel": "2024",
  "title": "Expansión Regional Actualizada",
  "description": "Apertura de 5 nuevas sucursales en la región oriental y central del país",
  "displayOrder": 10,
  "isActive": true
}
```

### Response

**Status**: `200 OK`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "yearLabel": "2024",
  "title": "Expansión Regional Actualizada",
  "description": "Apertura de 5 nuevas sucursales en la región oriental y central del país",
  "displayOrder": 10,
  "isActive": true
}
```

### Ejemplo cURL

```bash
curl -X PUT http://localhost:8080/api/admin/about/history/timeline/550e8400-e29b-41d4-a716-446655440000 ^
  -H "Content-Type: application/json" ^
  -d "{\"yearLabel\":\"2024\",\"title\":\"Expansión Regional Actualizada\",\"description\":\"Apertura de 5 nuevas sucursales en la región oriental y central del país\",\"displayOrder\":10,\"isActive\":true}"
```

### Errores

**Status**: `404 Not Found`

```json
{
  "timestamp": "2025-10-03T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Evento del timeline no encontrado con ID: 550e8400-e29b-41d4-a716-446655440000",
  "path": "/api/admin/about/history/timeline/550e8400-e29b-41d4-a716-446655440000"
}
```

---

## 3. Eliminar Evento del Timeline

Elimina un evento del timeline.

**Endpoint**: `DELETE /api/admin/about/history/timeline/{id}`

### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | UUID | ID del evento del timeline |

### Response

**Status**: `204 No Content`

Sin contenido en el cuerpo de la respuesta.

### Ejemplo cURL

```bash
curl -X DELETE http://localhost:8080/api/admin/about/history/timeline/550e8400-e29b-41d4-a716-446655440000
```

### Errores

**Status**: `404 Not Found`

```json
{
  "timestamp": "2025-10-03T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Evento del timeline no encontrado con ID: 550e8400-e29b-41d4-a716-446655440000",
  "path": "/api/admin/about/history/timeline/550e8400-e29b-41d4-a716-446655440000"
}
```

---

## 4. Actualizar Sección de Historia

Actualiza la configuración general de la sección de historia.

**Endpoint**: `PUT /api/admin/about/history/section`

**Headers**:
```
Content-Type: application/json
```

### Request Body

```json
{
  "title": "Nuestra Historia",
  "subtitle": "Conoce la evolución de nuestra institución a través del tiempo",
  "isActive": true
}
```

### Campos

| Campo | Tipo | Requerido | Descripción |
|-------|------|-----------|-------------|
| title | String | Sí | Título de la sección |
| subtitle | String | No | Subtítulo de la sección |
| isActive | Boolean | Sí | Estado activo/inactivo de la sección |

### Response

**Status**: `200 OK`

```json
{
  "id": "660e8400-e29b-41d4-a716-446655440001",
  "title": "Nuestra Historia",
  "subtitle": "Conoce la evolución de nuestra institución a través del tiempo",
  "isActive": true
}
```

### Ejemplo cURL

```bash
curl -X PUT http://localhost:8080/api/admin/about/history/section ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"Nuestra Historia\",\"subtitle\":\"Conoce la evolución de nuestra institución a través del tiempo\",\"isActive\":true}"
```

---

## Ejemplos Completos de Flujo de Trabajo

### Crear múltiples eventos del timeline

```bash
# Evento 1 - Fundación
curl -X POST http://localhost:8080/api/admin/about/history/timeline ^
  -H "Content-Type: application/json" ^
  -d "{\"yearLabel\":\"1950\",\"title\":\"Fundación\",\"description\":\"Inicio de operaciones de la cooperativa\",\"displayOrder\":1,\"isActive\":true}"

# Evento 2 - Primera expansión
curl -X POST http://localhost:8080/api/admin/about/history/timeline ^
  -H "Content-Type: application/json" ^
  -d "{\"yearLabel\":\"1965\",\"title\":\"Primera Expansión\",\"description\":\"Apertura de la segunda sucursal\",\"displayOrder\":2,\"isActive\":true}"

# Evento 3 - Modernización
curl -X POST http://localhost:8080/api/admin/about/history/timeline ^
  -H "Content-Type: application/json" ^
  -d "{\"yearLabel\":\"1980\",\"title\":\"Modernización Tecnológica\",\"description\":\"Implementación de sistemas computarizados\",\"displayOrder\":3,\"isActive\":true}"

# Evento 4 - Era digital
curl -X POST http://localhost:8080/api/admin/about/history/timeline ^
  -H "Content-Type: application/json" ^
  -d "{\"yearLabel\":\"2000\",\"title\":\"Era Digital\",\"description\":\"Lanzamiento de servicios de banca en línea\",\"displayOrder\":4,\"isActive\":true}"

# Evento 5 - Presente
curl -X POST http://localhost:8080/api/admin/about/history/timeline ^
  -H "Content-Type: application/json" ^
  -d "{\"yearLabel\":\"2024\",\"title\":\"Innovación Continua\",\"description\":\"Líder en servicios financieros cooperativos\",\"displayOrder\":5,\"isActive\":true}"
```

### Actualizar evento para cambiar su visibilidad

```bash
# Desactivar un evento temporalmente
curl -X PUT http://localhost:8080/api/admin/about/history/timeline/550e8400-e29b-41d4-a716-446655440000 ^
  -H "Content-Type: application/json" ^
  -d "{\"yearLabel\":\"1980\",\"title\":\"Modernización Tecnológica\",\"description\":\"Implementación de sistemas computarizados\",\"displayOrder\":3,\"isActive\":false}"
```

### Reordenar eventos

```bash
# Cambiar el orden de un evento
curl -X PUT http://localhost:8080/api/admin/about/history/timeline/550e8400-e29b-41d4-a716-446655440000 ^
  -H "Content-Type: application/json" ^
  -d "{\"yearLabel\":\"2000\",\"title\":\"Era Digital\",\"description\":\"Lanzamiento de servicios de banca en línea\",\"displayOrder\":10,\"isActive\":true}"
```

---

## Notas Importantes

1. **displayOrder**: Si no se especifica al crear, se asigna automáticamente el siguiente número disponible
2. **yearLabel**: Puede ser un año específico (ej: "2024") o un rango (ej: "2020-2024")
3. **Eliminación**: Al eliminar un evento, se elimina permanentemente de la base de datos
4. **Sección de Historia**: Si no existe una configuración de sección, se crea automáticamente con valores por defecto
5. **Validaciones**: Todos los campos marcados como requeridos deben estar presentes en el request

---

## Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado exitosamente |
| 204 | No Content - Eliminación exitosa |
| 400 | Bad Request - Datos inválidos en el request |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error del servidor |


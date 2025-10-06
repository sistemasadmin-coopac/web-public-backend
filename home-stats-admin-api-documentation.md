# API Endpoints - Home Stats Admin

Documentación de los endpoints de administración para gestionar las estadísticas de la página de inicio.

**Base URL**: `/api/admin/home-stats`

---

## 1. Obtener Todas las Estadísticas

Obtiene todas las estadísticas registradas en el sistema (activas e inactivas).

**Endpoint**: `GET /api/admin/home-stats`

### Response

**Status**: `200 OK`

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "label": "Socios Activos",
    "valueText": "25,000+",
    "icon": "users",
    "displayOrder": 1,
    "isActive": true
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "label": "Años de Experiencia",
    "valueText": "50+",
    "icon": "calendar",
    "displayOrder": 2,
    "isActive": true
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "label": "Sucursales",
    "valueText": "15",
    "icon": "building",
    "displayOrder": 3,
    "isActive": true
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440003",
    "label": "Satisfacción del Cliente",
    "valueText": "98%",
    "icon": "star",
    "displayOrder": 4,
    "isActive": false
  }
]
```

### Ejemplo cURL

```bash
curl -X GET http://localhost:8080/api/admin/home-stats
```

---

## 2. Obtener Estadísticas Activas

Obtiene únicamente las estadísticas que están marcadas como activas.

**Endpoint**: `GET /api/admin/home-stats/active`

### Response

**Status**: `200 OK`

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "label": "Socios Activos",
    "valueText": "25,000+",
    "icon": "users",
    "displayOrder": 1,
    "isActive": true
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "label": "Años de Experiencia",
    "valueText": "50+",
    "icon": "calendar",
    "displayOrder": 2,
    "isActive": true
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440002",
    "label": "Sucursales",
    "valueText": "15",
    "icon": "building",
    "displayOrder": 3,
    "isActive": true
  }
]
```

### Ejemplo cURL

```bash
curl -X GET http://localhost:8080/api/admin/home-stats/active
```

---

## 3. Obtener Estadística por ID

Obtiene una estadística específica mediante su ID.

**Endpoint**: `GET /api/admin/home-stats/{id}`

### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | UUID | ID único de la estadística |

### Response

**Status**: `200 OK`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "label": "Socios Activos",
  "valueText": "25,000+",
  "icon": "users",
  "displayOrder": 1,
  "isActive": true
}
```

### Ejemplo cURL

```bash
curl -X GET http://localhost:8080/api/admin/home-stats/550e8400-e29b-41d4-a716-446655440000
```

### Errores

**Status**: `404 Not Found`

```json
{
  "timestamp": "2025-10-03T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Estadística no encontrada con ID: 550e8400-e29b-41d4-a716-446655440000",
  "path": "/api/admin/home-stats/550e8400-e29b-41d4-a716-446655440000"
}
```

---

## 4. Actualizar Estadística

Actualiza una estadística existente.

**Endpoint**: `PUT /api/admin/home-stats/{id}`

**Headers**:
```
Content-Type: application/json
```

### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | UUID | ID único de la estadística a actualizar |

### Request Body

```json
{
  "label": "Socios Activos",
  "valueText": "30,000+",
  "icon": "users",
  "displayOrder": 1,
  "isActive": true
}
```

### Campos

| Campo | Tipo | Requerido | Descripción | Restricciones |
|-------|------|-----------|-------------|---------------|
| label | String | Sí | Etiqueta descriptiva de la estadística | Máximo 255 caracteres |
| valueText | String | Sí | Valor a mostrar (puede incluir símbolos) | Máximo 50 caracteres |
| icon | String | No | Nombre del icono a mostrar | Máximo 50 caracteres |
| displayOrder | Integer | Sí | Orden de visualización | Número entero |
| isActive | Boolean | Sí | Estado activo/inactivo | true o false |

### Response

**Status**: `200 OK`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "label": "Socios Activos",
  "valueText": "30,000+",
  "icon": "users",
  "displayOrder": 1,
  "isActive": true
}
```

### Ejemplo cURL

```bash
curl -X PUT http://localhost:8080/api/admin/home-stats/550e8400-e29b-41d4-a716-446655440000 ^
  -H "Content-Type: application/json" ^
  -d "{\"label\":\"Socios Activos\",\"valueText\":\"30,000+\",\"icon\":\"users\",\"displayOrder\":1,\"isActive\":true}"
```

### Errores

**Status**: `404 Not Found`

```json
{
  "timestamp": "2025-10-03T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Estadística no encontrada con ID: 550e8400-e29b-41d4-a716-446655440000",
  "path": "/api/admin/home-stats/550e8400-e29b-41d4-a716-446655440000"
}
```

**Status**: `400 Bad Request` (Validación fallida)

```json
{
  "timestamp": "2025-10-03T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validación",
  "errors": [
    {
      "field": "label",
      "message": "El label es requerido"
    },
    {
      "field": "valueText",
      "message": "El valor de texto no puede exceder 50 caracteres"
    }
  ]
}
```

---

## Ejemplos Completos de Flujo de Trabajo

### Actualizar múltiples estadísticas

```bash
# Actualizar estadística de socios
curl -X PUT http://localhost:8080/api/admin/home-stats/550e8400-e29b-41d4-a716-446655440000 ^
  -H "Content-Type: application/json" ^
  -d "{\"label\":\"Socios Activos\",\"valueText\":\"30,000+\",\"icon\":\"users\",\"displayOrder\":1,\"isActive\":true}"

# Actualizar estadística de años de experiencia
curl -X PUT http://localhost:8080/api/admin/home-stats/550e8400-e29b-41d4-a716-446655440001 ^
  -H "Content-Type: application/json" ^
  -d "{\"label\":\"Años de Experiencia\",\"valueText\":\"55+\",\"icon\":\"calendar\",\"displayOrder\":2,\"isActive\":true}"

# Actualizar estadística de sucursales
curl -X PUT http://localhost:8080/api/admin/home-stats/550e8400-e29b-41d4-a716-446655440002 ^
  -H "Content-Type: application/json" ^
  -d "{\"label\":\"Sucursales a Nivel Nacional\",\"valueText\":\"20\",\"icon\":\"building\",\"displayOrder\":3,\"isActive\":true}"
```

### Desactivar una estadística temporalmente

```bash
curl -X PUT http://localhost:8080/api/admin/home-stats/550e8400-e29b-41d4-a716-446655440003 ^
  -H "Content-Type: application/json" ^
  -d "{\"label\":\"Satisfacción del Cliente\",\"valueText\":\"98%\",\"icon\":\"star\",\"displayOrder\":4,\"isActive\":false}"
```

### Reordenar estadísticas

```bash
# Cambiar el orden de visualización
curl -X PUT http://localhost:8080/api/admin/home-stats/550e8400-e29b-41d4-a716-446655440002 ^
  -H "Content-Type: application/json" ^
  -d "{\"label\":\"Sucursales\",\"valueText\":\"15\",\"icon\":\"building\",\"displayOrder\":1,\"isActive\":true}"
```

---

## Ejemplos de Estadísticas Comunes

### Estadísticas Financieras

```json
{
  "label": "Activos Totales",
  "valueText": "$500M+",
  "icon": "dollar-sign",
  "displayOrder": 1,
  "isActive": true
}
```

### Estadísticas de Clientes

```json
{
  "label": "Clientes Satisfechos",
  "valueText": "98%",
  "icon": "smile",
  "displayOrder": 2,
  "isActive": true
}
```

### Estadísticas de Cobertura

```json
{
  "label": "Municipios Atendidos",
  "valueText": "50+",
  "icon": "map-pin",
  "displayOrder": 3,
  "isActive": true
}
```

### Estadísticas de Productos

```json
{
  "label": "Productos y Servicios",
  "valueText": "100+",
  "icon": "package",
  "displayOrder": 4,
  "isActive": true
}
```

---

## Notas Importantes

1. **displayOrder**: Controla el orden en que se muestran las estadísticas en el frontend. Números menores aparecen primero.

2. **valueText**: Puede incluir símbolos, signos de suma (+), porcentajes (%), símbolos de moneda ($), etc. Máximo 50 caracteres.

3. **icon**: Representa el nombre del icono que se utilizará en el frontend. Los nombres deben coincidir con la biblioteca de iconos utilizada.

4. **isActive**: Permite ocultar/mostrar estadísticas sin eliminarlas de la base de datos.

5. **label**: Descripción clara y concisa de lo que representa la estadística. Máximo 255 caracteres.

6. **GET vs GET /active**: 
   - `GET /api/admin/home-stats` - Retorna todas las estadísticas (útil para administración)
   - `GET /api/admin/home-stats/active` - Retorna solo las activas (útil para vista pública)

---

## Validaciones del Sistema

### Campos Requeridos
- `label` - Debe estar presente y no vacío
- `valueText` - Debe estar presente y no vacío
- `displayOrder` - Debe ser un número entero
- `isActive` - Debe ser true o false

### Límites de Longitud
- `label`: Máximo 255 caracteres
- `valueText`: Máximo 50 caracteres
- `icon`: Máximo 50 caracteres

### Mensajes de Error de Validación
- "El label es requerido"
- "El label no puede exceder 255 caracteres"
- "El valor de texto es requerido"
- "El valor de texto no puede exceder 50 caracteres"
- "El icono no puede exceder 50 caracteres"
- "El orden de visualización es requerido"
- "El estado activo es requerido"

---

## Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 400 | Bad Request - Error de validación en los datos enviados |
| 404 | Not Found - Estadística no encontrada |
| 500 | Internal Server Error - Error del servidor |

---

## Tipos de Iconos Sugeridos

Los siguientes son ejemplos de iconos comúnmente usados (depende de la biblioteca de iconos del frontend):

- `users` - Para estadísticas de personas/socios
- `calendar` - Para años, tiempo, antigüedad
- `building` - Para sucursales, oficinas
- `star` - Para calificaciones, satisfacción
- `dollar-sign` - Para montos, activos
- `shield` - Para seguridad, confianza
- `award` - Para logros, premios
- `briefcase` - Para negocios, corporativo
- `heart` - Para comunidad, valores
- `trending-up` - Para crecimiento, mejoras
- `globe` - Para alcance geográfico
- `package` - Para productos y servicios
- `map-pin` - Para ubicaciones
- `smile` - Para satisfacción del cliente


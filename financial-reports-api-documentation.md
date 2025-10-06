# API Endpoints - Financial Reports Admin

Documentación completa de los endpoints de administración para gestionar categorías y reportes financieros.

---

## 📁 Categorías de Reportes Financieros

**Base URL**: `/api/admin/financial/categories`

### 1. Crear Categoría

Crea una nueva categoría de reportes financieros.

**Endpoint**: `POST /api/admin/financial/categories`

**Headers**:
```
Content-Type: application/json
```

#### Request Body

```json
{
  "name": "Estados Financieros",
  "slug": "estados-financieros",
  "description": "Informes trimestrales y anuales de la situación financiera de la cooperativa",
  "displayOrder": 1,
  "isActive": true
}
```

#### Campos

| Campo | Tipo | Requerido | Descripción | Validaciones |
|-------|------|-----------|-------------|--------------|
| name | String | Sí | Nombre de la categoría | Máximo 255 caracteres |
| slug | String | Sí | URL amigable única | Máximo 100 caracteres, solo letras minúsculas, números y guiones |
| description | String | No | Descripción de la categoría | Máximo 1000 caracteres |
| displayOrder | Integer | Sí | Orden de visualización | Auto-asignado si se omite o es 0 |
| isActive | Boolean | Sí | Estado activo/inactivo | Default: true |

#### Response

**Status**: `201 Created`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Estados Financieros",
  "slug": "estados-financieros",
  "description": "Informes trimestrales y anuales de la situación financiera de la cooperativa",
  "displayOrder": 1,
  "isActive": true
}
```

#### Ejemplo cURL

```bash
curl -X POST http://localhost:8080/api/admin/financial/categories ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Estados Financieros\",\"slug\":\"estados-financieros\",\"description\":\"Informes trimestrales y anuales de la situación financiera de la cooperativa\",\"displayOrder\":1,\"isActive\":true}"
```

---

### 2. Actualizar Categoría

Actualiza una categoría existente.

**Endpoint**: `PUT /api/admin/financial/categories/{id}`

#### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | UUID | ID de la categoría |

#### Request Body

```json
{
  "name": "Estados Financieros Auditados",
  "slug": "estados-financieros-auditados",
  "description": "Informes financieros trimestrales y anuales auditados externamente",
  "displayOrder": 1,
  "isActive": true
}
```

#### Response

**Status**: `200 OK`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Estados Financieros Auditados",
  "slug": "estados-financieros-auditados",
  "description": "Informes financieros trimestrales y anuales auditados externamente",
  "displayOrder": 1,
  "isActive": true
}
```

#### Ejemplo cURL

```bash
curl -X PUT http://localhost:8080/api/admin/financial/categories/550e8400-e29b-41d4-a716-446655440000 ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Estados Financieros Auditados\",\"slug\":\"estados-financieros-auditados\",\"description\":\"Informes financieros trimestrales y anuales auditados externamente\",\"displayOrder\":1,\"isActive\":true}"
```

---

### 3. Eliminar Categoría

Elimina una categoría de reportes.

**Endpoint**: `DELETE /api/admin/financial/categories/{id}`

#### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | UUID | ID de la categoría |

#### Response

**Status**: `204 No Content`

#### Ejemplo cURL

```bash
curl -X DELETE http://localhost:8080/api/admin/financial/categories/550e8400-e29b-41d4-a716-446655440000
```

#### Errores

**Status**: `400 Bad Request` (Tiene reportes asociados)

```json
{
  "timestamp": "2025-10-03T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "No se puede eliminar la categoría porque tiene 5 reportes asociados"
}
```

---

### 4. Obtener Categoría por ID

Obtiene una categoría específica.

**Endpoint**: `GET /api/admin/financial/categories/{id}`

#### Response

**Status**: `200 OK`

```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "Estados Financieros",
  "slug": "estados-financieros",
  "description": "Informes trimestrales y anuales de la situación financiera de la cooperativa",
  "displayOrder": 1,
  "isActive": true
}
```

#### Ejemplo cURL

```bash
curl -X GET http://localhost:8080/api/admin/financial/categories/550e8400-e29b-41d4-a716-446655440000
```

---

### 5. Obtener Todas las Categorías

Lista todas las categorías de reportes.

**Endpoint**: `GET /api/admin/financial/categories`

#### Response

**Status**: `200 OK`

```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "Estados Financieros",
    "slug": "estados-financieros",
    "description": "Informes trimestrales y anuales",
    "displayOrder": 1,
    "isActive": true
  },
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "name": "Memorias Anuales",
    "slug": "memorias-anuales",
    "description": "Informes anuales de gestión",
    "displayOrder": 2,
    "isActive": true
  }
]
```

#### Ejemplo cURL

```bash
curl -X GET http://localhost:8080/api/admin/financial/categories
```

---

## 📄 Reportes Financieros

**Base URL**: `/api/admin/financial/reports`

### 1. Subir Archivo de Reporte

Sube un archivo PDF, Excel o Word que será almacenado localmente o en GCS.

**Endpoint**: `POST /api/admin/financial/reports/upload-file`

**Headers**:
```
Content-Type: multipart/form-data
```

#### Form Data

| Campo | Tipo | Descripción |
|-------|------|-------------|
| file | File | Archivo a subir (PDF, XLS, XLSX, DOC, DOCX) |

#### Validaciones

- **Formatos permitidos**: PDF, XLS, XLSX, DOC, DOCX
- **Tamaño máximo**: 50 MB
- El sistema genera automáticamente un nombre único (UUID)

#### Response

**Status**: `200 OK`

```json
{
  "fileUrl": "http://localhost:8080/uploads/financial-reports/a1b2c3d4-e5f6-7890-abcd-ef1234567890.pdf",
  "fileName": "estado-financiero-q1-2024.pdf",
  "fileSizeBytes": 2458624,
  "fileFormat": "pdf"
}
```

#### Ejemplo cURL

```bash
curl -X POST http://localhost:8080/api/admin/financial/reports/upload-file ^
  -F "file=@C:\Documents\estado-financiero-q1-2024.pdf"
```

---

### 2. Subir Miniatura

Sube una imagen miniatura para el reporte.

**Endpoint**: `POST /api/admin/financial/reports/upload-thumbnail`

**Headers**:
```
Content-Type: multipart/form-data
```

#### Form Data

| Campo | Tipo | Descripción |
|-------|------|-------------|
| file | File | Imagen (JPG, PNG, GIF, WebP) |

#### Validaciones

- **Formatos permitidos**: Imágenes (JPG, PNG, GIF, WebP)
- **Tamaño máximo**: 5 MB
- Validación de Content-Type de imagen

#### Response

**Status**: `200 OK`

```json
{
  "fileUrl": "http://localhost:8080/uploads/financial-reports/thumbnails/b2c3d4e5-f6g7-8901-bcde-fg2345678901.jpg",
  "fileName": "thumbnail.jpg",
  "fileSizeBytes": 145820,
  "fileFormat": "jpg"
}
```

#### Ejemplo cURL

```bash
curl -X POST http://localhost:8080/api/admin/financial/reports/upload-thumbnail ^
  -F "file=@C:\Images\thumbnail.jpg"
```

---

### 3. Crear Reporte

Crea un nuevo reporte financiero usando las URLs de archivos ya subidos.

**Endpoint**: `POST /api/admin/financial/reports`

**Headers**:
```
Content-Type: application/json
```

#### Request Body

```json
{
  "categoryId": "550e8400-e29b-41d4-a716-446655440000",
  "slug": "estado-financiero-q1-2024",
  "title": "Estado Financiero Q1 2024",
  "summary": "Informe financiero del primer trimestre del año 2024, incluye balance general, estado de resultados y flujo de caja.",
  "year": 2024,
  "quarter": "Q1",
  "publishDate": "2024-04-15",
  "deliveryType": "file",
  "fileFormat": "pdf",
  "fileUrl": "http://localhost:8080/uploads/financial-reports/a1b2c3d4-e5f6-7890-abcd-ef1234567890.pdf",
  "fileSizeBytes": 2458624,
  "thumbnailUrl": "http://localhost:8080/uploads/financial-reports/thumbnails/b2c3d4e5-f6g7-8901-bcde-fg2345678901.jpg",
  "tags": ["trimestral", "2024", "auditado"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```

#### Campos

| Campo | Tipo | Requerido | Descripción | Validaciones |
|-------|------|-----------|-------------|--------------|
| categoryId | UUID | Sí | ID de la categoría | Debe existir |
| slug | String | Sí | URL amigable única | Máximo 150 caracteres, formato slug |
| title | String | Sí | Título del reporte | Máximo 500 caracteres |
| summary | String | No | Resumen o descripción | Máximo 2000 caracteres |
| year | Integer | No | Año del reporte | Entre 1900 y 2100 |
| quarter | String | No | Trimestre | Q1, Q2, Q3 o Q4 |
| publishDate | Date | No | Fecha de publicación | Formato: YYYY-MM-DD |
| deliveryType | String | Sí | Tipo de entrega | "file" o "link" |
| fileFormat | String | Sí | Formato del archivo | Máximo 10 caracteres |
| fileUrl | String | Sí | URL del archivo | Máximo 1000 caracteres |
| fileSizeBytes | Long | No | Tamaño en bytes | |
| thumbnailUrl | String | No | URL de la miniatura | Máximo 1000 caracteres |
| tags | String[] | No | Etiquetas/tags | Array de strings |
| isPublic | Boolean | Sí | Visible públicamente | Default: true |
| isActive | Boolean | Sí | Estado activo | Default: true |
| displayOrder | Integer | Sí | Orden de visualización | Auto-asignado si se omite |

#### Response

**Status**: `201 Created`

```json
{
  "id": "660e8400-e29b-41d4-a716-446655440002",
  "categoryId": "550e8400-e29b-41d4-a716-446655440000",
  "categoryName": "Estados Financieros",
  "slug": "estado-financiero-q1-2024",
  "title": "Estado Financiero Q1 2024",
  "summary": "Informe financiero del primer trimestre del año 2024, incluye balance general, estado de resultados y flujo de caja.",
  "year": 2024,
  "quarter": "Q1",
  "publishDate": "2024-04-15",
  "deliveryType": "file",
  "fileFormat": "pdf",
  "fileUrl": "http://localhost:8080/uploads/financial-reports/a1b2c3d4-e5f6-7890-abcd-ef1234567890.pdf",
  "fileSizeBytes": 2458624,
  "thumbnailUrl": "http://localhost:8080/uploads/financial-reports/thumbnails/b2c3d4e5-f6g7-8901-bcde-fg2345678901.jpg",
  "tags": ["trimestral", "2024", "auditado"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```

#### Ejemplo cURL

```bash
curl -X POST http://localhost:8080/api/admin/financial/reports ^
  -H "Content-Type: application/json" ^
  -d "{\"categoryId\":\"550e8400-e29b-41d4-a716-446655440000\",\"slug\":\"estado-financiero-q1-2024\",\"title\":\"Estado Financiero Q1 2024\",\"summary\":\"Informe financiero del primer trimestre del año 2024\",\"year\":2024,\"quarter\":\"Q1\",\"publishDate\":\"2024-04-15\",\"deliveryType\":\"file\",\"fileFormat\":\"pdf\",\"fileUrl\":\"http://localhost:8080/uploads/financial-reports/a1b2c3d4-e5f6-7890-abcd-ef1234567890.pdf\",\"fileSizeBytes\":2458624,\"thumbnailUrl\":\"http://localhost:8080/uploads/financial-reports/thumbnails/b2c3d4e5-f6g7-8901-bcde-fg2345678901.jpg\",\"tags\":[\"trimestral\",\"2024\",\"auditado\"],\"isPublic\":true,\"isActive\":true,\"displayOrder\":1}"
```

---

### 4. Actualizar Reporte

Actualiza un reporte existente. Si se cambia el archivo o la miniatura, el sistema elimina automáticamente los archivos anteriores.

**Endpoint**: `PUT /api/admin/financial/reports/{id}`

#### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | UUID | ID del reporte |

#### Request Body

```json
{
  "categoryId": "550e8400-e29b-41d4-a716-446655440000",
  "slug": "estado-financiero-q1-2024-revisado",
  "title": "Estado Financiero Q1 2024 - Revisado",
  "summary": "Informe financiero revisado del primer trimestre del año 2024",
  "year": 2024,
  "quarter": "Q1",
  "publishDate": "2024-04-20",
  "deliveryType": "file",
  "fileFormat": "pdf",
  "fileUrl": "http://localhost:8080/uploads/financial-reports/c3d4e5f6-g7h8-9012-cdef-gh3456789012.pdf",
  "fileSizeBytes": 2650000,
  "thumbnailUrl": "http://localhost:8080/uploads/financial-reports/thumbnails/d4e5f6g7-h8i9-0123-defg-hi4567890123.jpg",
  "tags": ["trimestral", "2024", "auditado", "revisado"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```

#### Response

**Status**: `200 OK`

```json
{
  "id": "660e8400-e29b-41d4-a716-446655440002",
  "categoryId": "550e8400-e29b-41d4-a716-446655440000",
  "categoryName": "Estados Financieros",
  "slug": "estado-financiero-q1-2024-revisado",
  "title": "Estado Financiero Q1 2024 - Revisado",
  "summary": "Informe financiero revisado del primer trimestre del año 2024",
  "year": 2024,
  "quarter": "Q1",
  "publishDate": "2024-04-20",
  "deliveryType": "file",
  "fileFormat": "pdf",
  "fileUrl": "http://localhost:8080/uploads/financial-reports/c3d4e5f6-g7h8-9012-cdef-gh3456789012.pdf",
  "fileSizeBytes": 2650000,
  "thumbnailUrl": "http://localhost:8080/uploads/financial-reports/thumbnails/d4e5f6g7-h8i9-0123-defg-hi4567890123.jpg",
  "tags": ["trimestral", "2024", "auditado", "revisado"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```

#### Ejemplo cURL

```bash
curl -X PUT http://localhost:8080/api/admin/financial/reports/660e8400-e29b-41d4-a716-446655440002 ^
  -H "Content-Type: application/json" ^
  -d "{\"categoryId\":\"550e8400-e29b-41d4-a716-446655440000\",\"slug\":\"estado-financiero-q1-2024-revisado\",\"title\":\"Estado Financiero Q1 2024 - Revisado\",\"summary\":\"Informe financiero revisado del primer trimestre del año 2024\",\"year\":2024,\"quarter\":\"Q1\",\"publishDate\":\"2024-04-20\",\"deliveryType\":\"file\",\"fileFormat\":\"pdf\",\"fileUrl\":\"http://localhost:8080/uploads/financial-reports/c3d4e5f6-g7h8-9012-cdef-gh3456789012.pdf\",\"fileSizeBytes\":2650000,\"tags\":[\"trimestral\",\"2024\",\"auditado\",\"revisado\"],\"isPublic\":true,\"isActive\":true,\"displayOrder\":1}"
```

---

### 5. Eliminar Reporte

Elimina un reporte y sus archivos asociados (archivo principal y miniatura).

**Endpoint**: `DELETE /api/admin/financial/reports/{id}`

#### Path Parameters

| Parámetro | Tipo | Descripción |
|-----------|------|-------------|
| id | UUID | ID del reporte |

#### Response

**Status**: `204 No Content`

#### Ejemplo cURL

```bash
curl -X DELETE http://localhost:8080/api/admin/financial/reports/660e8400-e29b-41d4-a716-446655440002
```

---

### 6. Obtener Reporte por ID

Obtiene un reporte específico con todos sus detalles.

**Endpoint**: `GET /api/admin/financial/reports/{id}`

#### Response

**Status**: `200 OK`

```json
{
  "id": "660e8400-e29b-41d4-a716-446655440002",
  "categoryId": "550e8400-e29b-41d4-a716-446655440000",
  "categoryName": "Estados Financieros",
  "slug": "estado-financiero-q1-2024",
  "title": "Estado Financiero Q1 2024",
  "summary": "Informe financiero del primer trimestre del año 2024",
  "year": 2024,
  "quarter": "Q1",
  "publishDate": "2024-04-15",
  "deliveryType": "file",
  "fileFormat": "pdf",
  "fileUrl": "http://localhost:8080/uploads/financial-reports/a1b2c3d4-e5f6-7890-abcd-ef1234567890.pdf",
  "fileSizeBytes": 2458624,
  "thumbnailUrl": "http://localhost:8080/uploads/financial-reports/thumbnails/b2c3d4e5-f6g7-8901-bcde-fg2345678901.jpg",
  "tags": ["trimestral", "2024", "auditado"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```

#### Ejemplo cURL

```bash
curl -X GET http://localhost:8080/api/admin/financial/reports/660e8400-e29b-41d4-a716-446655440002
```

---

### 7. Obtener Todos los Reportes

Lista todos los reportes con opción de filtrar por categoría.

**Endpoint**: `GET /api/admin/financial/reports`

#### Query Parameters

| Parámetro | Tipo | Requerido | Descripción |
|-----------|------|-----------|-------------|
| categoryId | UUID | No | Filtra por categoría específica |

#### Response (Sin filtro)

**Status**: `200 OK`

```json
[
  {
    "id": "660e8400-e29b-41d4-a716-446655440002",
    "categoryId": "550e8400-e29b-41d4-a716-446655440000",
    "categoryName": "Estados Financieros",
    "slug": "estado-financiero-q1-2024",
    "title": "Estado Financiero Q1 2024",
    "summary": "Informe financiero del primer trimestre",
    "year": 2024,
    "quarter": "Q1",
    "publishDate": "2024-04-15",
    "deliveryType": "file",
    "fileFormat": "pdf",
    "fileUrl": "http://localhost:8080/uploads/financial-reports/a1b2c3d4.pdf",
    "fileSizeBytes": 2458624,
    "thumbnailUrl": "http://localhost:8080/uploads/financial-reports/thumbnails/b2c3d4e5.jpg",
    "tags": ["trimestral", "2024"],
    "isPublic": true,
    "isActive": true,
    "displayOrder": 1
  },
  {
    "id": "660e8400-e29b-41d4-a716-446655440003",
    "categoryId": "550e8400-e29b-41d4-a716-446655440001",
    "categoryName": "Memorias Anuales",
    "slug": "memoria-anual-2023",
    "title": "Memoria Anual 2023",
    "summary": "Informe de gestión anual 2023",
    "year": 2023,
    "quarter": null,
    "publishDate": "2024-03-01",
    "deliveryType": "file",
    "fileFormat": "pdf",
    "fileUrl": "http://localhost:8080/uploads/financial-reports/e5f6g7h8.pdf",
    "fileSizeBytes": 5248960,
    "thumbnailUrl": null,
    "tags": ["anual", "2023"],
    "isPublic": true,
    "isActive": true,
    "displayOrder": 2
  }
]
```

#### Ejemplo cURL (Todos)

```bash
curl -X GET http://localhost:8080/api/admin/financial/reports
```

#### Ejemplo cURL (Por categoría)

```bash
curl -X GET "http://localhost:8080/api/admin/financial/reports?categoryId=550e8400-e29b-41d4-a716-446655440000"
```

---

## 🔄 Flujo de Trabajo Completo

### Crear un Reporte Financiero desde Cero

#### Paso 1: Crear la categoría (si no existe)

```bash
curl -X POST http://localhost:8080/api/admin/financial/categories ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Estados Financieros\",\"slug\":\"estados-financieros\",\"description\":\"Informes trimestrales\",\"displayOrder\":1,\"isActive\":true}"
```

Respuesta: `{ "id": "550e8400-e29b-41d4-a716-446655440000", ... }`

#### Paso 2: Subir el archivo PDF

```bash
curl -X POST http://localhost:8080/api/admin/financial/reports/upload-file ^
  -F "file=@C:\Documents\estado-financiero-q1-2024.pdf"
```

Respuesta:
```json
{
  "fileUrl": "http://localhost:8080/uploads/financial-reports/a1b2c3d4-e5f6-7890-abcd-ef1234567890.pdf",
  "fileName": "estado-financiero-q1-2024.pdf",
  "fileSizeBytes": 2458624,
  "fileFormat": "pdf"
}
```

#### Paso 3: Subir miniatura (opcional)

```bash
curl -X POST http://localhost:8080/api/admin/financial/reports/upload-thumbnail ^
  -F "file=@C:\Images\thumbnail.jpg"
```

Respuesta:
```json
{
  "fileUrl": "http://localhost:8080/uploads/financial-reports/thumbnails/b2c3d4e5-f6g7-8901-bcde-fg2345678901.jpg",
  "fileName": "thumbnail.jpg",
  "fileSizeBytes": 145820,
  "fileFormat": "jpg"
}
```

#### Paso 4: Crear el reporte con las URLs obtenidas

```bash
curl -X POST http://localhost:8080/api/admin/financial/reports ^
  -H "Content-Type: application/json" ^
  -d "{\"categoryId\":\"550e8400-e29b-41d4-a716-446655440000\",\"slug\":\"estado-financiero-q1-2024\",\"title\":\"Estado Financiero Q1 2024\",\"summary\":\"Informe del primer trimestre 2024\",\"year\":2024,\"quarter\":\"Q1\",\"publishDate\":\"2024-04-15\",\"deliveryType\":\"file\",\"fileFormat\":\"pdf\",\"fileUrl\":\"http://localhost:8080/uploads/financial-reports/a1b2c3d4-e5f6-7890-abcd-ef1234567890.pdf\",\"fileSizeBytes\":2458624,\"thumbnailUrl\":\"http://localhost:8080/uploads/financial-reports/thumbnails/b2c3d4e5-f6g7-8901-bcde-fg2345678901.jpg\",\"tags\":[\"trimestral\",\"2024\"],\"isPublic\":true,\"isActive\":true}"
```

---

## ⚠️ Errores Comunes

### Error 400 - Validación

```json
{
  "timestamp": "2025-10-03T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validación",
  "errors": [
    {
      "field": "slug",
      "message": "El slug solo puede contener letras minúsculas, números y guiones"
    }
  ]
}
```

### Error 404 - No Encontrado

```json
{
  "timestamp": "2025-10-03T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Reporte no encontrado con ID: 660e8400-e29b-41d4-a716-446655440002"
}
```

### Error 409 - Slug Duplicado

```json
{
  "timestamp": "2025-10-03T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Ya existe un reporte con el slug: estado-financiero-q1-2024"
}
```

---

## 📝 Notas Importantes

1. **Orden de operaciones**: Siempre sube los archivos primero, luego crea el reporte con las URLs obtenidas.

2. **Slug único**: Cada categoría y reporte debe tener un slug único en todo el sistema.

3. **Eliminación automática**: Al actualizar un reporte con nuevo archivo o al eliminarlo, el sistema borra automáticamente los archivos anteriores del sistema de almacenamiento.

4. **displayOrder**: Se auto-asigna el siguiente número disponible si se omite o es 0.

5. **Formatos de archivo**: 
   - Reportes: PDF, XLS, XLSX, DOC, DOCX (máx 50MB)
   - Miniaturas: JPG, PNG, GIF, WebP (máx 5MB)

6. **deliveryType**:
   - `"file"` - Archivo descargable
   - `"link"` - Enlace externo

7. **quarter**: Valores válidos: Q1, Q2, Q3, Q4 o null para reportes anuales.

---

## 🔐 Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 201 | Created - Recurso creado exitosamente |
| 204 | No Content - Eliminación exitosa |
| 400 | Bad Request - Error de validación o lógica de negocio |
| 404 | Not Found - Recurso no encontrado |
| 500 | Internal Server Error - Error del servidor |


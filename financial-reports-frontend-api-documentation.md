# API de Reportes Financieros - Guía para Frontend

**Última actualización:** 2025-10-04  
**Base URL:** `http://localhost:8080/api/admin/financial`

---

## 📋 Tabla de Contenidos
- [Gestión de Categorías](#gestión-de-categorías)
- [Gestión de Reportes](#gestión-de-reportes)
- [Carga de Archivos](#carga-de-archivos)
- [Notas Importantes](#notas-importantes)

---

## 🗂️ Gestión de Categorías

### 1. Crear Categoría

**Endpoint:** `POST /categories`

**⚠️ IMPORTANTE:** El campo `slug` NO se envía, se genera automáticamente desde el `name`

**Request Body:**
```json
{
  "name": "Estados Financieros",
  "description": "Reportes de estados financieros mensuales y anuales",
  "displayOrder": 1,
  "isActive": true
}
```

**Response (201 Created):**
```json
{
  "id": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  "name": "Estados Financieros",
  "slug": "estados-financieros",
  "description": "Reportes de estados financieros mensuales y anuales",
  "displayOrder": 1,
  "isActive": true
}
```

**Validaciones:**
- ✅ `name`: Requerido, máximo 255 caracteres
- ✅ `description`: Opcional, máximo 1000 caracteres
- ✅ `displayOrder`: Requerido, número entero
- ✅ `isActive`: Requerido, boolean
- ❌ `slug`: **NO ENVIAR** - se genera automáticamente

---

### 2. Actualizar Categoría

**Endpoint:** `PUT /categories/{id}`

**⚠️ IMPORTANTE:** Si cambias el `name`, el `slug` se regenerará automáticamente

**Request Body:**
```json
{
  "name": "Estados Financieros Anuales",
  "description": "Reportes financieros anuales actualizados",
  "displayOrder": 1,
  "isActive": true
}
```

**Response (200 OK):**
```json
{
  "id": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  "name": "Estados Financieros Anuales",
  "slug": "estados-financieros-anuales",
  "description": "Reportes financieros anuales actualizados",
  "displayOrder": 1,
  "isActive": true
}
```

---

### 3. Obtener Todas las Categorías

**Endpoint:** `GET /categories`

**Response (200 OK):**
```json
[
  {
    "id": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
    "name": "Estados Financieros",
    "slug": "estados-financieros",
    "description": "Reportes de estados financieros",
    "displayOrder": 1,
    "isActive": true
  },
  {
    "id": "b9e051de-7f98-5201-cb3f-642b0b07eb09",
    "name": "Informes de Sostenibilidad",
    "slug": "informes-de-sostenibilidad",
    "description": "Reportes de responsabilidad social",
    "displayOrder": 2,
    "isActive": true
  }
]
```

---

### 4. Obtener Categoría por ID

**Endpoint:** `GET /categories/{id}`

**Response (200 OK):**
```json
{
  "id": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  "name": "Estados Financieros",
  "slug": "estados-financieros",
  "description": "Reportes de estados financieros",
  "displayOrder": 1,
  "isActive": true
}
```

---

### 5. Eliminar Categoría

**Endpoint:** `DELETE /categories/{id}`

**Response (204 No Content):**
```
(Sin contenido)
```

**⚠️ Error si tiene reportes asociados (400 Bad Request):**
```json
{
  "timestamp": "2025-10-04T14:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "No se puede eliminar la categoría porque tiene 5 reportes asociados"
}
```

---

## 📄 Gestión de Reportes

### 1. Crear Reporte

**Endpoint:** `POST /reports`

**⚠️ IMPORTANTE:** El campo `slug` NO se envía, se genera automáticamente desde el `title`

**Request Body:**
```json
{
  "categoryId": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  "title": "Informe de Sostenibilidad 2024",
  "summary": "Reporte anual de sostenibilidad y responsabilidad social empresarial",
  "year": 2024,
  "quarter": null,
  "publishDate": "2024-06-15",
  "deliveryType": "file",
  "fileFormat": "PDF",
  "fileUrl": "/uploads/financial-reports/informe-2024.pdf",
  "fileSizeBytes": 4100000,
  "thumbnailUrl": "/uploads/financial-reports/thumbnails/informe-2024-thumb.jpg",
  "tags": ["sostenibilidad", "anual", "2024"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```

**Response (201 Created):**
```json
{
  "id": "bbd6f81e-fe92-441f-aea5-8c9fbb91941f",
  "categoryId": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  "categoryName": "Estados Financieros",
  "slug": "informe-de-sostenibilidad-2024",
  "title": "Informe de Sostenibilidad 2024",
  "summary": "Reporte anual de sostenibilidad y responsabilidad social empresarial",
  "year": 2024,
  "quarter": null,
  "publishDate": "2024-06-15",
  "deliveryType": "file",
  "fileFormat": "PDF",
  "fileUrl": "/uploads/financial-reports/informe-2024.pdf",
  "fileSizeBytes": 4100000,
  "thumbnailUrl": "/uploads/financial-reports/thumbnails/informe-2024-thumb.jpg",
  "tags": ["sostenibilidad", "anual", "2024"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```

**Validaciones:**
- ✅ `categoryId`: Requerido, UUID válido existente
- ✅ `title`: Requerido, máximo 500 caracteres
- ✅ `summary`: Opcional, máximo 2000 caracteres
- ✅ `year`: Opcional, entre 1900 y 2100
- ✅ `quarter`: Opcional, valores válidos: "Q1", "Q2", "Q3", "Q4" o null
- ✅ `publishDate`: Opcional, formato ISO "YYYY-MM-DD"
- ✅ `deliveryType`: Requerido, valores: "file", "link" o "binary"
- ✅ `fileFormat`: Requerido, máximo 10 caracteres (ej: "PDF", "XLSX")
- ✅ `fileUrl`: Requerido, máximo 1000 caracteres
- ✅ `fileSizeBytes`: Opcional, número entero
- ✅ `thumbnailUrl`: Opcional, máximo 1000 caracteres
- ✅ `tags`: Opcional, array de strings
- ✅ `isPublic`: Requerido, boolean
- ✅ `isActive`: Requerido, boolean
- ✅ `displayOrder`: Requerido, número entero
- ❌ `slug`: **NO ENVIAR** - se genera automáticamente

---

### 2. Actualizar Reporte

**Endpoint:** `PUT /reports/{id}`

**⚠️ IMPORTANTE:** 
- Si cambias el `title`, el `slug` se regenerará automáticamente
- Los campos `fileUrl`, `fileFormat` y `thumbnailUrl` son **OPCIONALES** en la actualización
- Si NO envías estos campos (o los envías vacíos/null), se mantendrán los valores existentes
- Esto permite actualizar solo los datos del reporte sin cambiar los archivos

**Request Body (Actualización SIN cambiar archivos):**
```json
{
  "categoryId": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  "title": "Informe de Sostenibilidad 2024 - Actualizado",
  "summary": "Reporte actualizado de sostenibilidad 2024",
  "year": 2024,
  "quarter": "Q4",
  "publishDate": "2024-12-31",
  "deliveryType": "binary",
  "tags": ["sostenibilidad", "anual", "2024", "actualizado"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```
**Nota:** En este ejemplo NO se envían `fileUrl`, `fileFormat` ni `thumbnailUrl`, por lo que se mantienen los archivos existentes.

**Request Body (Actualización CON nuevos archivos):**
```json
{
  "categoryId": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  "title": "Informe de Sostenibilidad 2024 - Actualizado",
  "summary": "Reporte actualizado de sostenibilidad 2024",
  "year": 2024,
  "quarter": "Q4",
  "publishDate": "2024-12-31",
  "deliveryType": "file",
  "fileFormat": "PDF",
  "fileUrl": "/uploads/financial-reports/informe-2024-v2.pdf",
  "fileSizeBytes": 4500000,
  "thumbnailUrl": "/uploads/financial-reports/thumbnails/informe-2024-v2-thumb.jpg",
  "tags": ["sostenibilidad", "anual", "2024", "actualizado"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```
**Nota:** En este ejemplo SÍ se envían nuevos archivos, por lo que se reemplazarán los existentes.

**Response (200 OK):**
```json
{
  "id": "bbd6f81e-fe92-441f-aea5-8c9fbb91941f",
  "categoryId": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  "categoryName": "Estados Financieros",
  "slug": "informe-de-sostenibilidad-2024-actualizado",
  "title": "Informe de Sostenibilidad 2024 - Actualizado",
  "summary": "Reporte actualizado de sostenibilidad 2024",
  "year": 2024,
  "quarter": "Q4",
  "publishDate": "2024-12-31",
  "deliveryType": "file",
  "fileFormat": "PDF",
  "fileUrl": "/uploads/financial-reports/informe-2024-v2.pdf",
  "fileSizeBytes": 4500000,
  "thumbnailUrl": "/uploads/financial-reports/thumbnails/informe-2024-v2-thumb.jpg",
  "tags": ["sostenibilidad", "anual", "2024", "actualizado"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```

---

### 3. Obtener Todos los Reportes

**Endpoint:** `GET /reports`

**Query Parameters (opcionales):**
- `categoryId`: Filtrar por categoría específica (UUID)

**Ejemplos:**
- Todos los reportes: `GET /reports`
- Por categoría: `GET /reports?categoryId=a8f940cd-6e87-4190-bb2e-531a9a96da98`

**Response (200 OK):**
```json
[
  {
    "id": "bbd6f81e-fe92-441f-aea5-8c9fbb91941f",
    "categoryId": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
    "categoryName": "Estados Financieros",
    "slug": "informe-de-sostenibilidad-2024",
    "title": "Informe de Sostenibilidad 2024",
    "summary": "Reporte anual de sostenibilidad",
    "year": 2024,
    "quarter": null,
    "publishDate": "2024-06-15",
    "deliveryType": "file",
    "fileFormat": "PDF",
    "fileUrl": "/uploads/financial-reports/informe-2024.pdf",
    "fileSizeBytes": 4100000,
    "thumbnailUrl": "/uploads/financial-reports/thumbnails/informe-2024-thumb.jpg",
    "tags": ["sostenibilidad", "anual", "2024"],
    "isPublic": true,
    "isActive": true,
    "displayOrder": 1
  }
]
```

---

### 4. Obtener Reporte por ID

**Endpoint:** `GET /reports/{id}`

**Response (200 OK):**
```json
{
  "id": "bbd6f81e-fe92-441f-aea5-8c9fbb91941f",
  "categoryId": "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  "categoryName": "Estados Financieros",
  "slug": "informe-de-sostenibilidad-2024",
  "title": "Informe de Sostenibilidad 2024",
  "summary": "Reporte anual de sostenibilidad",
  "year": 2024,
  "quarter": null,
  "publishDate": "2024-06-15",
  "deliveryType": "file",
  "fileFormat": "PDF",
  "fileUrl": "/uploads/financial-reports/informe-2024.pdf",
  "fileSizeBytes": 4100000,
  "thumbnailUrl": "/uploads/financial-reports/thumbnails/informe-2024-thumb.jpg",
  "tags": ["sostenibilidad", "anual", "2024"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```

---

### 5. Eliminar Reporte

**Endpoint:** `DELETE /reports/{id}`

**Response (204 No Content):**
```
(Sin contenido)
```

**Nota:** Los archivos asociados (PDF, miniatura) se eliminan automáticamente si son locales.

---

## 📤 Carga de Archivos

### 1. Subir Archivo de Reporte

**Endpoint:** `POST /reports/upload-file`

**Content-Type:** `multipart/form-data`

**Form Data:**
- `file`: El archivo a subir (PDF, XLS, XLSX, DOC, DOCX)

**Ejemplo con cURL:**
```bash
curl -X POST http://localhost:8080/api/admin/financial/reports/upload-file \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/informe-2024.pdf"
```

**Ejemplo con JavaScript (Fetch API):**
```javascript
const formData = new FormData();
formData.append('file', fileInput.files[0]);

const response = await fetch('http://localhost:8080/api/admin/financial/reports/upload-file', {
  method: 'POST',
  body: formData
});

const result = await response.json();
console.log(result);
```

**Response (200 OK):**
```json
{
  "fileUrl": "/uploads/financial-reports/6d2f9bdb-2824-4ecc-9de3-13d56a4b9945.pdf",
  "fileName": "informe-2024.pdf",
  "fileSizeBytes": 4100000,
  "fileFormat": "pdf"
}
```

**Restricciones:**
- ✅ Formatos permitidos: PDF, XLS, XLSX, DOC, DOCX
- ✅ Tamaño máximo: 50 MB
- ❌ El archivo no puede estar vacío

---

### 2. Subir Miniatura

**Endpoint:** `POST /reports/upload-thumbnail`

**Content-Type:** `multipart/form-data`

**Form Data:**
- `file`: La imagen a subir (JPG, PNG, GIF, etc.)

**Ejemplo con cURL:**
```bash
curl -X POST http://localhost:8080/api/admin/financial/reports/upload-thumbnail \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/thumbnail.jpg"
```

**Ejemplo con JavaScript (Fetch API):**
```javascript
const formData = new FormData();
formData.append('file', thumbnailInput.files[0]);

const response = await fetch('http://localhost:8080/api/admin/financial/reports/upload-thumbnail', {
  method: 'POST',
  body: formData
});

const result = await response.json();
console.log(result);
```

**Response (200 OK):**
```json
{
  "fileUrl": "/uploads/financial-reports/thumbnails/7e3g0cec-3935-5fdd-0bf4-24e67b5c0056.jpg",
  "fileName": "thumbnail.jpg",
  "fileSizeBytes": 250000,
  "fileFormat": "jpg"
}
```

**Restricciones:**
- ✅ Formatos permitidos: Cualquier imagen (JPG, PNG, GIF, WEBP, etc.)
- ✅ Tamaño máximo: 5 MB
- ❌ El archivo debe ser una imagen válida

---

## 📝 Notas Importantes

### 🔑 Generación Automática de Slugs

**El slug se genera SIEMPRE desde el backend y NO debe enviarse en los requests.**

#### Para Categorías:
- El slug se genera desde el campo `name`
- Ejemplos:
  - `"Estados Financieros"` → `"estados-financieros"`
  - `"Informes de Sostenibilidad"` → `"informes-de-sostenibilidad"`
  - `"Reportes Anuales 2024"` → `"reportes-anuales-2024"`

#### Para Reportes:
- El slug se genera desde el campo `title`
- Ejemplos:
  - `"Informe Anual 2024"` → `"informe-anual-2024"`
  - `"Estado Financiero Q1"` → `"estado-financiero-q1"`
  - `"Reporte de Sostenibilidad"` → `"reporte-de-sostenibilidad"`

#### Unicidad Automática:
Si un slug ya existe, el sistema agrega un número automáticamente:
- Primer reporte: `"informe-2024"`
- Segundo con mismo nombre: `"informe-2024-2"`
- Tercero: `"informe-2024-3"`

#### Normalización:
El sistema normaliza automáticamente caracteres especiales:
- Convierte a minúsculas
- Reemplaza espacios por guiones
- Normaliza: `ñ → n`, `á → a`, `é → e`, etc.
- Elimina caracteres no permitidos

---

### 🔄 Flujo Recomendado para Crear Reporte

```javascript
// 1. Subir el archivo principal
const formData = new FormData();
formData.append('file', pdfFile);

const fileUpload = await fetch('/api/admin/financial/reports/upload-file', {
  method: 'POST',
  body: formData
});
const fileData = await fileUpload.json();

// 2. Subir la miniatura (opcional)
const thumbFormData = new FormData();
thumbFormData.append('file', thumbnailFile);

const thumbUpload = await fetch('/api/admin/financial/reports/upload-thumbnail', {
  method: 'POST',
  body: thumbFormData
});
const thumbData = await thumbUpload.json();

// 3. Crear el reporte con las URLs obtenidas
const reportData = {
  categoryId: "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  title: "Informe de Sostenibilidad 2024",
  summary: "Reporte anual...",
  year: 2024,
  publishDate: "2024-06-15",
  deliveryType: "file",
  fileFormat: fileData.fileFormat,
  fileUrl: fileData.fileUrl,
  fileSizeBytes: fileData.fileSizeBytes,
  thumbnailUrl: thumbData.fileUrl,
  tags: ["sostenibilidad", "2024"],
  isPublic: true,
  isActive: true,
  displayOrder: 1
  // ❌ NO enviar slug - se genera automáticamente
};

const createReport = await fetch('/api/admin/financial/reports', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(reportData)
});

const report = await createReport.json();
console.log('Reporte creado con slug:', report.slug);
```

---

### 🔄 Flujo Recomendado para Actualizar Reporte

#### Caso 1: Actualizar SOLO información (sin cambiar archivos)

```javascript
// Solo actualizar título, descripción, etc. SIN tocar los archivos
const updateData = {
  categoryId: "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  title: "Informe de Sostenibilidad 2024 - Actualizado",
  summary: "Resumen actualizado...",
  year: 2024,
  quarter: "Q4",
  publishDate: "2024-12-31",
  deliveryType: "binary",
  tags: ["sostenibilidad", "2024", "actualizado"],
  isPublic: true,
  isActive: true,
  displayOrder: 1
  // ❌ NO enviar fileUrl, fileFormat ni thumbnailUrl
  // ✅ Se mantendrán los archivos existentes
};

const updateReport = await fetch('/api/admin/financial/reports/{id}', {
  method: 'PUT',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(updateData)
});

const report = await updateReport.json();
console.log('Reporte actualizado, archivos sin cambios');
```

#### Caso 2: Actualizar CON nuevos archivos

```javascript
// 1. Primero subir los nuevos archivos (si hay)
const formData = new FormData();
formData.append('file', newPdfFile);

const fileUpload = await fetch('/api/admin/financial/reports/upload-file', {
  method: 'POST',
  body: formData
});
const newFileData = await fileUpload.json();

// 2. Actualizar el reporte con las nuevas URLs
const updateData = {
  categoryId: "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  title: "Informe de Sostenibilidad 2024 - Actualizado",
  summary: "Resumen actualizado...",
  year: 2024,
  quarter: "Q4",
  publishDate: "2024-12-31",
  deliveryType: "file",
  fileFormat: newFileData.fileFormat,
  fileUrl: newFileData.fileUrl,
  fileSizeBytes: newFileData.fileSizeBytes,
  // thumbnailUrl: puede enviarse o no, según si cambió
  tags: ["sostenibilidad", "2024", "actualizado"],
  isPublic: true,
  isActive: true,
  displayOrder: 1
};

const updateReport = await fetch('/api/admin/financial/reports/{id}', {
  method: 'PUT',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(updateData)
});

const report = await updateReport.json();
console.log('Reporte y archivos actualizados');
```

#### Caso 3: Actualizar SOLO la miniatura (mantener archivo principal)

```javascript
// 1. Subir nueva miniatura
const thumbFormData = new FormData();
thumbFormData.append('file', newThumbnailFile);

const thumbUpload = await fetch('/api/admin/financial/reports/upload-thumbnail', {
  method: 'POST',
  body: thumbFormData
});
const newThumbData = await thumbUpload.json();

// 2. Actualizar solo con la nueva miniatura
const updateData = {
  categoryId: "a8f940cd-6e87-4190-bb2e-531a9a96da98",
  title: "Informe de Sostenibilidad 2024",
  summary: "Reporte anual...",
  year: 2024,
  publishDate: "2024-06-15",
  deliveryType: "file",
  thumbnailUrl: newThumbData.fileUrl,  // ✅ Nueva miniatura
  // ❌ NO enviar fileUrl ni fileFormat
  // ✅ El archivo principal se mantiene sin cambios
  tags: ["sostenibilidad", "2024"],
  isPublic: true,
  isActive: true,
  displayOrder: 1
};

const updateReport = await fetch('/api/admin/financial/reports/{id}', {
  method: 'PUT',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(updateData)
});

const report = await updateReport.json();
console.log('Miniatura actualizada, archivo principal sin cambios');
```

---

### 📊 Tipos de Entrega (deliveryType)

- **`"file"`**: Archivo subido al servidor local (o bucket GCP en producción)
- **`"link"`**: Enlace externo a un documento (ej: Google Drive, Dropbox)
- **`"binary"`**: Archivo binario almacenado directamente

---

### 🎯 Valores del Campo `quarter`

- `null` o no enviarlo: Sin trimestre específico (reporte anual completo)
- `"Q1"`: Primer trimestre (Enero - Marzo)
- `"Q2"`: Segundo trimestre (Abril - Junio)
- `"Q3"`: Tercer trimestre (Julio - Septiembre)
- `"Q4"`: Cuarto trimestre (Octubre - Diciembre)

---

### ⚠️ Errores Comunes

#### Error 400: Bad Request
```json
{
  "timestamp": "2025-10-04T14:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "El tipo de entrega debe ser 'file', 'link' o 'binary'"
}
```
**Solución:** Verifica que `deliveryType` tenga uno de los valores permitidos.

#### Error 404: Not Found
```json
{
  "timestamp": "2025-10-04T14:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Categoría no encontrada con ID: a8f940cd-..."
}
```
**Solución:** Verifica que el `categoryId` exista en la base de datos.

#### Error 500: Internal Server Error
```json
{
  "timestamp": "2025-10-04T14:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Error al procesar la solicitud"
}
```
**Solución:** Revisa los logs del servidor para más detalles.

---

### 📞 Soporte

Para más información o dudas, contacta al equipo de backend.

**Happy coding! 🚀**
